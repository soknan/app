
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
-- =============================================
-- Author:		Sovathena NETH
-- Create date: 23/06/2012
-- Description:	query Branch
-- =============================================
CREATE PROCEDURE [dbo].[krd_branch_select_by_sub_cd] 
	-- Add the parameters for the stored procedure here
	@sub_cd varchar(30)
	
	
AS

BEGIN

	IF @sub_cd = '*'
	BEGIN
		SELECT 0 Id, '*' Code, '(All branches)' [Description]
	END
	ELSE
	BEGIN
		SELECT br.Id, br.Code, br.Branch [Description] FROM Branch br
		INNER JOIN SubBranch sub ON br.Id = sub.BranchId
		WHERE sub.Code = @sub_cd 
	END
END
GO
