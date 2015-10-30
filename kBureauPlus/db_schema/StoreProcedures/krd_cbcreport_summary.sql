
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
-- =============================================
-- Author:		Sovathena NETH
-- Create date: 13/08/2012
-- Description:	CBC Report Sumarry: Total Lite, Standard, Decision, Fee, Income & Expense
-- =============================================
CREATE PROCEDURE [dbo].[krd_cbcreport_summary] 
	-- Add the parameters for the stored procedure here
	
	@filter varchar(50), @name nvarchar(50), @branch varchar(4), @sub_branch varchar(4), @report_type varchar(5),
	@status_cd varchar(2), @decision int, @from_amount decimal(16,2), @to_amount decimal(16,2),
	@curreny varchar(3), @from_date varchar(10), @to_date varchar(10)
	
AS

BEGIN
	
	DECLARE @status varchar(5)
	--SET @status = @status_cd
	SELECT @status = CASE WHEN @status_cd = 'OK' THEN 'OK' 
	WHEN @status_cd = 'ER' THEN 'ERROR'
	WHEN @status_cd = 'PA' THEN 'PARSE' 
	ELSE '' END
	
	-- COUNT Report GROUP BY Action: Standard vs Lite
	DECLARE @count_std int, @count_lite int, @count_pending int, @count_approved int, 
	@count_rejected int, @count_cancel int, @std_fee_cbc decimal(16,2), @lite_fee_cbc decimal(16,2),
	@std_fee_krd decimal(16,2)
	
	IF OBJECT_ID('tempdb..#tmp_tbl') IS NOT NULL
	BEGIN
		drop table #tmp_tbl
	END
	
	SELECT rpt.Action, COUNT(Action) Total,  SUM(CASE rpt.Decision WHEN 0 THEN 1 ELSE 0 END ) Pending, 
	SUM(CASE rpt.Decision WHEN 1 THEN 1 ELSE 0 END ) Approved,
	SUM(CASE rpt.Decision WHEN -1 THEN 1 ELSE 0 END ) Rejected,
	SUM(CASE rpt.Decision WHEN -2 THEN 1 ELSE 0 END ) Cancel   
	INTO #tmp_tbl
	FROM CbcReport rpt 
	LEFT OUTER JOIN SubBranch sub ON rpt.SubBranchCode = sub.Code
	LEFT OUTER JOIN Branch bra ON rpt.BranchCode = bra.Code
	WHERE (LEN(@name)=0 OR Name like  '%' + @name + '%' COLLATE SQL_Latin1_General_CP850_BIN2 OR	RefNumber like '%' + @filter + '%')
	AND (LEN(@branch) = 0 OR rpt.BranchCode = @branch)
	AND (LEN(@sub_branch) = 0 OR rpt.SubBranchCode =  @sub_branch)
	AND (LEN(@report_type)=0 OR rpt.Action = @report_type)
	AND (LEN(@status)=0 OR rpt.Status = @status)
	AND (@decision = 2 OR rpt.Decision = @decision)
	AND (@from_amount=0 OR @to_amount = 0 OR rpt.Amount BETWEEN @from_amount AND @to_amount)
	AND (LEN(@curreny)=0 OR rpt.Currency=@curreny)
	AND (LEN(@from_date) = 0 OR LEN(@to_date) = 0 OR rpt.ReportDate BETWEEN @from_date AND DATEADD(SECOND,86399,@to_date))
	GROUP BY rpt.Action
	
	-- Get Stardard and Lite
	SELECT @count_std = SUM(CASE Action WHEN 'A' THEN Total ELSE 0 END), @count_lite = SUM(CASE Action WHEN 'L' THEN Total ELSE 0 END)  FROM #tmp_tbl
	-- Pending, Approved, Rejected, Cancel
	SELECT @count_pending = SUM(Pending), @count_approved = SUM(Approved), @count_rejected = SUM(Rejected), @count_cancel = SUM(Cancel) FROM #tmp_tbl
	
	
	SELECT @std_fee_krd = SUM(Case When Decision >=0 Then Case When Currency='USD' Then FeeKredit Else (FeeKredit/4000) End Else 0 End ),
	@std_fee_cbc = SUM(CASE rpt.Action WHEN 'A' THEN FeeCbc ELSE 0 END), @lite_fee_cbc = SUM(CASE rpt.Action WHEN 'L' THEN FeeCbc ELSE 0 END)
	FROM CbcReport rpt 
	LEFT OUTER JOIN SubBranch sub ON rpt.SubBranchCode = sub.Code
	LEFT OUTER JOIN Branch bra ON rpt.BranchCode = bra.Code
	WHERE (LEN(@name)=0 OR Name like  '%' + @name + '%' COLLATE SQL_Latin1_General_CP850_BIN2 OR	RefNumber like '%' + @filter + '%')
	AND (LEN(@branch) = 0 OR rpt.BranchCode = @branch)
	AND (LEN(@sub_branch) = 0 OR rpt.SubBranchCode =  @sub_branch)
	AND (LEN(@report_type)=0 OR rpt.Action = @report_type)
	AND (LEN(@status)=0 OR rpt.Status = @status)
	AND (@decision = 2 OR rpt.Decision = @decision)
	AND (@from_amount=0 OR @to_amount = 0 OR rpt.Amount BETWEEN @from_amount AND @to_amount)
	AND (LEN(@curreny)=0 OR rpt.Currency=@curreny)
	AND (LEN(@from_date) = 0 OR LEN(@to_date) = 0 OR CONVERT(DATE,rpt.ReportDate) BETWEEN @from_date AND @to_date)
	
	-- Query Result	
	SELECT @count_std CountStd, @count_lite CountLite,
	@count_pending CountPending, @count_approved CountApproved, @count_rejected CountRejected,
	@count_cancel CountCancel, @std_fee_krd TotalKreditFee, @std_fee_cbc TotalCbcFeeStd, @lite_fee_cbc TotalCbcFeeLite
	
END
GO
