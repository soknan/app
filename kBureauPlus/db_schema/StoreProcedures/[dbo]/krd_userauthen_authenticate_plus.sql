
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
-- =============================================
-- Author:		Sovathena NETH
-- Create date: 03/Apr/2012
-- Description:	Authenticate
-- =============================================
ALTER PROCEDURE [dbo].[krd_userauthen_authenticate_plus] 
	-- Add the parameters for the stored procedure here
	@username  varchar(50),
	@psw  varchar(50),
	@user_agent varchar(5),
	@remote_addr varchar(50),
	@remote_host varchar(50)
AS

BEGIN

	DECLARE @user_id INT, @log_date datetime, @subcode varchar(4), @branchcode varchar(4)
		
	SET @user_id = dbo.fn_do_authentication(@username, @psw)
	SET @log_date = GETDATE()
	
	
	IF @user_id>0
	BEGIN
		-- SubBranch Code
		SELECT @subcode = SubBranchCode, @branchcode = BranchCode  FROM UserAuthen WHERE Id = @user_id
		
		-- Insert UserLog
		EXEC dbo.krd_userlogplus_insert @user_id, @username, @branchcode, @subcode, @remote_addr, @remote_host, @user_agent, 'Login successfully'    
		
		-- Update UserAuthen
		UPDATE UserAuthen SET PcName = @remote_host WHERE Id = @user_id
		
		DECLARE @role varchar(3), @is_admin bit	
		SET @is_admin = 0
		SELECT @role = RoleCode FROM UserAuthenRole WHERE UserId = @user_id
		IF @role = 'ADM'
		BEGIN
			SET @is_admin = 1		
		END
				
		-- Select
		SELECT ka.Id, ka.FName, ka.LName, ka.Username, ka.Email, ka.LastLoginDate, @is_admin Adm,
		ka.SubBranchCode, dbo.fn_get_sub_description(ka.SubBranchCode) SubBranch, ka.BranchCode, dbo.fn_get_branch_description(ka.BranchCode) Branch, dbo.fn_get_branch_id(ka.BranchCode) BranchId, ka.SecurityCode
		FROM UserAuthen ka
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
			EXEC dbo.krd_userlogplus_insert @user_id, @username, @branchcode, @subcode, @remote_addr, @remote_host, @user_agent, 'Login failed -- invalid username or psw.'
			SELECT -1 Id
		END
		-- 2) User has expired or been disabled.
		ELSE
		BEGIN
			-- Insert UserLog
			EXEC dbo.krd_userlogplus_insert @user_id, @username, @branchcode, @subcode, @remote_addr, @remote_host, @user_agent, 'Login failed -- expired or disabled.'
			SELECT -2 Id
		END
	END
END
GO
