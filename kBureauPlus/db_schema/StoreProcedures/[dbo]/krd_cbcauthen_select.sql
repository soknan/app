
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
-- =============================================
-- Author:		Sovathena NETH
-- Create date: 20/07/2012
-- Description:	query CBC Authentication
-- =============================================
ALTER PROCEDURE [dbo].[krd_cbcauthen_select] 
	-- Add the parameters for the stored procedure here
	@filter varchar(50), @branch varchar(4), @sub_branch varchar(4), @flag varchar(1)
AS

BEGIN
	IF @flag = 'r'
	BEGIN
		SELECT cbc.Id, cbc.Username, cbc.Password, cbc.SubBranchCode,
		sub.SubBranch SubBranchName, br.Code BranchCode, cbc.ResetedDate, cbc.ExpiredDate, DATEDIFF(DAY, CONVERT(DATE,GETDATE()), CONVERT(DATE,cbc.ExpiredDate)) RemainDay 
		FROM CbcAuthen cbc
		LEFT OUTER JOIN SubBranch sub ON cbc.SubBranchCode = sub.Code
		LEFT OUTER JOIN Branch br ON sub.BranchId = br.Id
		WHERE (LEN(@filter) = 0 
		OR (cbc.Username like '%'+ @filter +'%'	OR cbc.SubBranchCode like '%'+ @filter +'%')) 
		AND (LEN(@branch)=0 OR br.Code = @branch)
		AND (LEN(@sub_branch)=0 OR sub.Code = @sub_branch)
		AND DATEDIFF(DAY, CONVERT(DATE,GETDATE()), CONVERT(DATE,cbc.ExpiredDate)) < 1
		ORDER BY RemainDay
	END
	ELSE IF @flag = 'y'
	BEGIN
		SELECT cbc.Id, cbc.Username, cbc.Password, cbc.SubBranchCode,
		sub.SubBranch SubBranchName, br.Code BranchCode, cbc.ResetedDate, cbc.ExpiredDate, DATEDIFF(DAY, CONVERT(DATE,GETDATE()), CONVERT(DATE,cbc.ExpiredDate)) RemainDay 
		FROM CbcAuthen cbc
		LEFT OUTER JOIN SubBranch sub ON cbc.SubBranchCode = sub.Code
		LEFT OUTER JOIN Branch br ON sub.BranchId = br.Id
		WHERE (LEN(@filter) = 0 
		OR (cbc.Username like '%'+ @filter +'%'	OR cbc.SubBranchCode like '%'+ @filter +'%')) 
		AND (LEN(@branch)=0 OR br.Code = @branch)
		AND (LEN(@sub_branch)=0 OR sub.Code = @sub_branch)
		AND DATEDIFF(DAY, CONVERT(DATE,GETDATE()), CONVERT(DATE,cbc.ExpiredDate)) < 7
		AND DATEDIFF(DAY, CONVERT(DATE,GETDATE()), CONVERT(DATE,cbc.ExpiredDate)) > 0
		ORDER BY RemainDay
	END
	ELSE IF @flag = 'b'
	BEGIN
		SELECT cbc.Id, cbc.Username, cbc.Password, cbc.SubBranchCode,
		sub.SubBranch SubBranchName, br.Code BranchCode, cbc.ResetedDate, cbc.ExpiredDate, DATEDIFF(DAY, CONVERT(DATE,GETDATE()), CONVERT(DATE,cbc.ExpiredDate)) RemainDay 
		FROM CbcAuthen cbc
		LEFT OUTER JOIN SubBranch sub ON cbc.SubBranchCode = sub.Code
		LEFT OUTER JOIN Branch br ON sub.BranchId = br.Id
		WHERE (LEN(@filter) = 0 
		OR (cbc.Username like '%'+ @filter +'%'	OR cbc.SubBranchCode like '%'+ @filter +'%')) 
		AND (LEN(@branch)=0 OR br.Code = @branch)
		AND (LEN(@sub_branch)=0 OR sub.Code = @sub_branch)
		AND DATEDIFF(DAY, CONVERT(DATE,GETDATE()), CONVERT(DATE,cbc.ExpiredDate)) > 6
		ORDER BY RemainDay
	END
	ELSE
	BEGIN	
	
	SELECT cbc.Id, cbc.Username, cbc.Password, cbc.SubBranchCode,
	sub.SubBranch SubBranchName, br.Code BranchCode, cbc.ResetedDate, cbc.ExpiredDate, DATEDIFF(DAY, CONVERT(DATE,GETDATE()), CONVERT(DATE,cbc.ExpiredDate)) RemainDay  
	FROM CbcAuthen cbc
	LEFT OUTER JOIN SubBranch sub ON cbc.SubBranchCode = sub.Code
	LEFT OUTER JOIN Branch br ON sub.BranchId = br.Id
	WHERE (LEN(@filter) = 0 
	OR (cbc.Username like '%'+ @filter +'%'	OR cbc.SubBranchCode like '%'+ @filter +'%')) 
	AND (LEN(@branch)=0 OR br.Code = @branch)
	AND (LEN(@sub_branch)=0 OR sub.Code = @sub_branch)
	ORDER BY RemainDay
	
	END
	
	
	
END
GO
