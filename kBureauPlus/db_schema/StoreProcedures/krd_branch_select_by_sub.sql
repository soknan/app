
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
-- =============================================
-- Author:		Sovathena NETH
-- Create date: 23/06/2012
-- Description:	query Branch
-- =============================================
CREATE PROCEDURE [dbo].[krd_branch_select_by_sub] 
	-- Add the parameters for the stored procedure here
	@subbrach varchar(30)
	
	
AS

BEGIN

	IF @subbrach = 'All subs'
	BEGIN
		SELECT 0 Id, '*' Code, '(All branches)' [Description]
	END
	ELSE
	BEGIN
		SELECT br.Id, br.Code, br.Branch [Description] FROM Branch br
		INNER JOIN SubBranch sub ON br.Id = sub.BranchId
		WHERE sub.SubBranch = @subbrach 
	END
END
GO
