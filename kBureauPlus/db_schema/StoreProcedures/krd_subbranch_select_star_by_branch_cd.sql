
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
-- =============================================
-- Author:		Sovathena NETH
-- Create date: 23/06/2012
-- Description:	query Branch
-- =============================================
CREATE PROCEDURE [dbo].[krd_subbranch_select_star_by_branch_cd] 
	-- Add the parameters for the stored procedure here
	@branch_cd varchar(2)
AS

BEGIN
	SELECT 0 Id, '*' Code, '(All subs)' [Description]
	UNION
	SELECT sub.Id Id, sub.Code Code, sub.SubBranch [Description]
	FROM SubBranch sub
	INNER JOIN Branch br ON br.Id = sub.BranchId 
	WHERE @branch_cd = '*' OR br.Code = @branch_cd
END
GO
