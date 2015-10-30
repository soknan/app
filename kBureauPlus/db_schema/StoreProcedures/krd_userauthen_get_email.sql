
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
-- =============================================
-- Author:		Sovathena NETH
-- Create date: 08/05/2012
-- Description:	Check is exist username
-- =============================================
CREATE PROCEDURE [dbo].[krd_userauthen_get_email] 
	-- Add the parameters for the stored procedure here
	@UserId int
	
AS

BEGIN
	SELECT dbo.fn_userauthen_get_email(@UserId)
END
GO
