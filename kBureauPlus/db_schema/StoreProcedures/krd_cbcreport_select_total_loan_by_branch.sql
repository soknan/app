
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
-- =============================================
-- Author:		Sovathena NETH
-- Create date: 15/08/2012
-- Description:	query total (Standard & Lite) for each location( Branch/Sub Branch)
-- =============================================
CREATE PROCEDURE [dbo].[krd_cbcreport_select_total_loan_by_branch] 
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
	
	IF LEN(@branch) = 0
	BEGIN	
		SELECT rpt.BranchCode Label, 
		COUNT(rpt.Id) TotalLoan,
		SUM(
			CASE Currency WHEN 'USD' THEN rpt.Amount ELSE rpt.Amount / 4000 END
		) AS TotalAmount
		FROM CbcReport rpt
		LEFT OUTER JOIN SubBranch sub ON rpt.SubBranchCode = sub.Code
		LEFT OUTER JOIN Branch bra ON rpt.BranchCode = bra.Code
		WHERE (LEN(@name)=0 OR Name like  '%' + @name + '%' COLLATE SQL_Latin1_General_CP850_BIN2 OR	RefNumber like '%' + @filter + '%')
		AND (LEN(@sub_branch) = 0 OR rpt.SubBranchCode =  @sub_branch)
		AND (LEN(@report_type)=0 OR rpt.Action = @report_type)
		AND (LEN(@status)=0 OR rpt.Status = @status)
		AND (@decision = 2 OR rpt.Decision = @decision)
		AND (@from_amount=0 OR @to_amount = 0 OR rpt.Amount BETWEEN @from_amount AND @to_amount)
		AND (LEN(@curreny)=0 OR rpt.Currency=@curreny)
		AND (LEN(@from_date) = 0 OR LEN(@to_date) = 0 OR rpt.ReportDate BETWEEN @from_date AND DATEADD(SECOND,86399,@to_date))
		GROUP BY rpt.BranchCode
	END
	ELSE
	BEGIN
		SELECT rpt.SubBranchCode + ' ' + sub.SubBranch Label, 
		COUNT(rpt.Id) TotalLoan, 
		SUM(
			CASE Currency WHEN 'USD' THEN rpt.Amount ELSE rpt.Amount / 4000 END
		) AS TotalAmount
		FROM CbcReport rpt
		LEFT OUTER JOIN SubBranch sub ON rpt.SubBranchCode = sub.Code
		LEFT OUTER JOIN Branch bra ON rpt.BranchCode = bra.Code
		WHERE (LEN(@name)=0 OR Name like  '%' + @name + '%' COLLATE SQL_Latin1_General_CP850_BIN2 OR	RefNumber like '%' + @filter + '%')
		AND rpt.BranchCode = @branch
		AND (LEN(@sub_branch) = 0 OR rpt.SubBranchCode =  @sub_branch)
		AND (LEN(@report_type)=0 OR rpt.Action = @report_type)
		AND (LEN(@status)=0 OR rpt.Status = @status)
		AND (@decision = 2 OR rpt.Decision = @decision)
		AND (@from_amount=0 OR @to_amount = 0 OR rpt.Amount BETWEEN @from_amount AND @to_amount)
		AND (LEN(@curreny)=0 OR rpt.Currency=@curreny)
		AND (LEN(@from_date) = 0 OR LEN(@to_date) = 0 OR rpt.ReportDate BETWEEN @from_date AND DATEADD(SECOND,86399,@to_date))
		GROUP BY rpt.SubBranchCode + ' ' + sub.SubBranch
	END	
END
GO
