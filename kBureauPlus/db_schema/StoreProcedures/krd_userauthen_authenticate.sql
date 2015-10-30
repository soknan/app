
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
-- =============================================
-- Author:		Sovathena NETH
-- Create date: 03/Apr/2012
-- Description:	Authenticate
-- =============================================
CREATE PROCEDURE [dbo].[krd_userauthen_authenticate] 
	-- Add the parameters for the stored procedure here
	@username  varchar(50),
	@psw  varchar(50),
	@app_version varchar(5),
	@pc_name varchar(50),
	@pc_username varchar(50)
AS

BEGIN

	DECLARE @user_id INT, @log_date datetime, @subcode varchar(4)
		
	SET @user_id = dbo.fn_do_authentication(@username, @psw)
	SET @log_date = GETDATE()
	
	
	IF @user_id>0
	BEGIN
		-- SubBranch Code
		SELECT @subcode = SubBranchCode  FROM UserAuthen WHERE Id = @user_id
		
		-- Insert UserLog
		EXEC dbo.krd_userlog_insert @user_id, @username, @subcode, @pc_name, @pc_username, @app_version, 'Login successfully'    
		
		-- Update UserAuthen
		UPDATE UserAuthen SET AppVersion = @app_version, PcName = @pc_name, PcUsername = @pc_username WHERE Id = @user_id
		
		-- Select
		SELECT ka.Id, ka.LastLoginDate, ka.BranchCode, ka.SubBranchCode, ka.SecurityCode, ca.Username CbcUsername 
		FROM UserAuthen ka 
		INNER JOIN CbcAuthen ca ON ka.SubBranchCode = ca.SubBranchCode
		WHERE ka.Id = @user_id
		
		-- Update LastLoginDate
		UPDATE UserAuthen SET LastLoginDate = @log_date WHERE Id = @user_id
	END
	ELSE
	BEGIN
		
		-- 1) Invalid Username or Password
		IF @user_id = -1 
		BEGIN
			-- Insert UserLog
			EXEC dbo.krd_userlog_insert @user_id, @username, '', @pc_name, @pc_username, @app_version, 'Login failed -- Invalid username or psw.'    
			SELECT -1 Id
		END
		-- 2) User has expired or been disabled.
		ELSE
		BEGIN
			-- Insert UserLog
			EXEC dbo.krd_userlog_insert @user_id, @username, '', @pc_name, @pc_username, @app_version, 'Login failed -- expired or disabled.'    
			SELECT -2 Id
		END
	
	END
END
GO
