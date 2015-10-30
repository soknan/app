
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
-- =============================================
-- Author:		Sovathena NETH
-- Create date: 19/07/2012
-- Description:	Reset default pwd 
-- =============================================
CREATE PROCEDURE [dbo].[krd_userauthen_reset_pwd_default] 
	-- Add the parameters for the stored procedure here
	@user_id int, @creater_id int
AS

BEGIN
	DECLARE @role varchar(5), @mlogin_name varchar(50), @mbranch_code varchar(4), 
	@msub_code varchar(4), @m_msg varchar(500)
	
	SELECT @role = RoleCode FROM UserAuthenRole WHERE UserId = @creater_id
	IF @role = 'ADM'
	BEGIN
		SELECT @mlogin_name = Username, @mbranch_code = BranchCode, @msub_code = SubBranchCode
		FROM UserAuthen WHERE Id = @creater_id
		
		SET @m_msg = 'Reset default pwd for user ID = ' + CONVERT(varchar(5),@user_id)
		
		-- Insert UserLog
		EXEC dbo.krd_userlogplus_insert @creater_id, @mlogin_name, @mbranch_code, @msub_code, '', '', '', @m_msg  
	
		UPDATE UserAuthen SET Password = PWDENCRYPT(Username), SecurityCode = 0,  UpdatedBy = @creater_id WHERE Id = @user_id
		SELECT @user_id Value
	END
END
GO
