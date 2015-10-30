
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
-- =============================================
-- Author:		Sovathena NETH
-- Create date: 23/06/2012
-- Description:	query Branch
-- =============================================
CREATE PROCEDURE [dbo].[krd_subbranch_select] 
	-- Add the parameters for the stored procedure here
	@branch_id int
AS

BEGIN
	
	DECLARE @superCode varchar(2), @superDesc varchar(50)
	SELECT @superCode = Code, @superDesc = Branch FROM Branch WHERE Id = @branch_id
	SET @superCode = ISNULL(@superCode,'')
	SET @superDesc = ISNULL(@superDesc, 'All')
	
	SELECT 0 Id, '' Code, 'All' [Description], @branch_id SuperId, @superCode SuperCode, 
	@superDesc SuperDescription
	UNION
	SELECT sub.Id Id, sub.Code Code, sub.SubBranch [Description], 
	sub.BranchId SuperId, br.Code SuperCode, br.Branch SuperDescription 
	FROM SubBranch sub
	INNER JOIN Branch br ON br.Id = sub.BranchId 
	WHERE (@branch_id = 0 OR BranchId = @branch_id)
	
END
GO
