
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
-- =============================================
-- Author:		Sovathena NETH
-- Create date: 23/06/2012
-- Description:	query Branch
-- =============================================
CREATE PROCEDURE [dbo].[krd_subbranch_select_star] 
	-- Add the parameters for the stored procedure here
	@branch_desc varchar(30)
AS

BEGIN
	SELECT 0 Id, '*' Code, '(All subs)' [Description]
	UNION
	SELECT sub.Id Id, sub.Code Code, sub.SubBranch [Description]
	FROM SubBranch sub
	INNER JOIN Branch br ON br.Id = sub.BranchId 
	WHERE LEN(@branch_desc) = 0 OR br.Branch = @branch_desc
END
GO
