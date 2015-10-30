
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
-- =============================================
-- Author:		Sovathena NETH
-- Create date: 20/07/2012
-- Description:	query CBC Authentication
-- =============================================
CREATE PROCEDURE [dbo].[krd_cbcauthen_select] 
	-- Add the parameters for the stored procedure here
	@filter varchar(50), @branch varchar(4), @sub_branch varchar(4)
AS

BEGIN
	SELECT cbc.Id, cbc.Username, cbc.Password, cbc.SubBranchCode,
	sub.SubBranch SubBranchName, br.Code BranchCode 
	FROM CbcAuthen cbc
	LEFT OUTER JOIN SubBranch sub ON cbc.SubBranchCode = sub.Code
	LEFT OUTER JOIN Branch br ON sub.BranchId = br.Id
	WHERE (LEN(@filter) = 0 
	OR (cbc.Username like '%'+ @filter +'%'	OR cbc.SubBranchCode like '%'+ @filter +'%')) 
	AND (LEN(@branch)=0 OR br.Code = @branch)
	AND (LEN(@sub_branch)=0 OR sub.Code = @sub_branch)
	ORDER BY Id DESC
	
END
GO
