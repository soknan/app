
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
-- =============================================
-- Author:		Sovathena NETH
-- Create date: 11/07/2012
-- Description:	Request reset password -- Set Security Number
-- =============================================
CREATE PROCEDURE [dbo].[krd_userauthen_request_reset_psw] 
	-- Add the parameters for the stored procedure here
	@email varchar(50)
	
AS

BEGIN
	IF EXISTS(SELECT Id FROM UserAuthen WHERE Email = @email)
	BEGIN
		DECLARE @SecurityCode int
		SELECT @SecurityCode = CAST(RAND() * 1000000 AS INT)
		UPDATE UserAuthen SET SecurityCode = @SecurityCode WHERE Email = @email
		SELECT * FROM UserAuthen WHERE Email = @email
	END
END
GO
