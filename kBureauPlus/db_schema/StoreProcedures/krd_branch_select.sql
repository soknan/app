
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
-- =============================================
-- Author:		Sovathena NETH
-- Create date: 23/06/2012
-- Description:	query Branch
-- =============================================
CREATE PROCEDURE [dbo].[krd_branch_select] 
	-- Add the parameters for the stored procedure here
	
AS

BEGIN

	SELECT 0 Id, '' Code, 'All' [Description]
	UNION
	SELECT Id, Code, Branch [Description] FROM Branch
END
GO
