
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
-- =============================================
-- Author:		Sovathena NETH
-- Create date: 08/05/2012
-- Description:	Set Security Number
-- =============================================
CREATE PROCEDURE [dbo].[krd_userauthen_set_security_number] 
	-- Add the parameters for the stored procedure here
	@UserId int, @SecurityCode int
	
AS

BEGIN
	UPDATE UserAuthen SET SecurityCode = @SecurityCode WHERE Id = @UserId
END
GO
