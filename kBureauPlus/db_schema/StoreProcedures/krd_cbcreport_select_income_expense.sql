
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
-- =============================================
-- Author:		Sovathena NETH
-- Create date: 26/07/2012
-- Description:	query total (Standard & Lite) for each Daily Report 
-- =============================================
CREATE PROCEDURE [dbo].[krd_cbcreport_select_income_expense] 
	-- Add the parameters for the stored procedure here
	
	@FromDate Date, @ToDate Date, @Decision int, @Branch varchar(50),@SubBranch varchar(50)
	
AS

BEGIN
	
	select BranchCode, SubBranchCode + ' ' + SubBranch.SubBranch as SubBranch,
	'Income' [Type], 
	SUM(Case When Decision >=0 Then Case When Currency='USD' Then FeeKredit Else (FeeKredit/4000) End Else 0 End ) As Fee
	from CbcReport 
	Inner Join SubBranch On SubBranchCode=SubBranch.Code
	Where Status='OK' And (LEN(@Branch)=0 Or BranchCode=@Branch) And (LEN(@SubBranch)=0 Or SubBranchCode=@SubBranch)
	And CONVERT(Date,ReportDate) Between @FromDate And @ToDate
	Group By BranchCode,SubBranchCode + ' ' + SubBranch.SubBranch --Order By BranchCode 
	
	UNION
	
	select BranchCode, SubBranchCode + ' ' + SubBranch.SubBranch as SubBranch,
	'Expense' [Type],
	SUM(FeeCbc) as Fee
	from CbcReport 
	Inner Join SubBranch On SubBranchCode=SubBranch.Code
	Where Status='OK' And (LEN(@Branch)=0 Or BranchCode=@Branch) And (LEN(@SubBranch)=0 Or SubBranchCode=@SubBranch)
	And CONVERT(Date,ReportDate) Between @FromDate And @ToDate
	Group By BranchCode,SubBranchCode + ' ' + SubBranch.SubBranch --Order By BranchCode 
			
END
GO
