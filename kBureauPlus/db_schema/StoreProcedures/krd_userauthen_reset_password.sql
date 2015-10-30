
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
-- =============================================
-- Author:		Sovathena NETH
-- Create date: 09/05/2012
-- Description:	Reset Password
-- =============================================
CREATE PROCEDURE [dbo].[krd_userauthen_reset_password] 
	-- Add the parameters for the stored procedure here
	@UserId int, @SecurityCode int, @Password varchar(50),
	@Username varchar(50),
	@app_version varchar(5),
	@pc_name varchar(50),
	@pc_username varchar(50)
	
AS

BEGIN
	DECLARE @subcode varchar(4), @branchcode varchar(4), @message varchar(80)
	SET @subcode = ''
	IF EXISTS(SELECT Id FROM UserAuthen WHERE Id = @UserId AND SecurityCode = @SecurityCode AND LEN(@SecurityCode) > 0)
	BEGIN
		IF LEN(@Password) > 0
		BEGIN
		-- SubBranch Code
		SELECT @subcode = SubBranchCode, @branchcode = BranchCode FROM UserAuthen WHERE Id = @UserId
		-- Insert UserLog
		SET @message = 'Reset password successfully. Security code ' + CAST(@SecurityCode as varchar(6))    
		IF @app_version = 'kplus'
		BEGIN
			-- Insert UserLog
			EXEC dbo.krd_userlogplus_insert @UserId, @Username, @branchcode, @subcode, '', @pc_name, @pc_username, @message    
		END
		ELSE
		BEGIN
			EXEC dbo.krd_userlog_insert @UserId, @Username, @subcode, @pc_name, @pc_username, @app_version, @message
		END
		UPDATE UserAuthen SET Password = PWDENCRYPT(@Password), SecurityCode = -1 WHERE Id = @UserId
		SELECT 'Password has been reset successfully' Value
		END
		ELSE
		BEGIN
			SELECT 'Password cannot be blank' Value
		END
	END
	ELSE
	BEGIN
		
		SET @message = 'Reset password failed! Invalid security code ' + CAST(@SecurityCode as varchar(6))
		IF @app_version = 'kplus'
		BEGIN
			-- Insert UserLog
			EXEC dbo.krd_userlogplus_insert @UserId, @Username, @branchcode, @subcode, '', @pc_name, @pc_username, @message 
		END
		ELSE
		BEGIN
			-- Insert UserLog
			EXEC dbo.krd_userlog_insert @UserId, @username, @subcode, @pc_name, @pc_username, @app_version, @message
		END
		SELECT 'Invalid security code. Try again!' Value
	END
END
GO
