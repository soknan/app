
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
-- =============================================
-- Author:		Sovathena NETH
-- Create date: 20/07/2012
-- Description:	Save CBC Authentication
-- =============================================
ALTER PROCEDURE [dbo].[krd_cbcauthen_save] 
	-- Add the parameters for the stored procedure here
	@username varchar(50), 
	@password nvarchar(100),
	@sub_code varchar(2),
	@cbc_id int,
	@creater_id int
AS

BEGIN
	DECLARE @role varchar(5), @mlogin_name varchar(50), @mbranch_code varchar(4), 
	@msub_code varchar(4), @m_msg varchar(500)
	
	SELECT @role = RoleCode FROM UserAuthenRole WHERE UserId = @creater_id
	IF @role = 'ADM'
	BEGIN
		
		DECLARE @d DATETIME
		SET @d = GETDATE()
		
		SELECT @mlogin_name = Username, @mbranch_code = BranchCode, @msub_code = SubBranchCode
		FROM UserAuthen WHERE Id = @creater_id
		
		IF @cbc_id = 0
		BEGIN
			SET @m_msg = 'Add new CBC Authentication -- ' + @username
			-- Insert UserLog
			EXEC dbo.krd_userlogplus_insert @creater_id, @mlogin_name, @mbranch_code, @msub_code, '', '', '', @m_msg  
			
			
						
			INSERT INTO CbcAuthen (Username, Password, SubBranchCode, ResetedDate, ExpiredDate, ResetedBy)
			VALUES(@username, @password, @sub_code, @d, DATEADD(DAY,90,@d), @creater_id)
			
			SELECT @@IDENTITY Value
		END
		ELSE
		BEGIN
			
			DECLARE @username_old varchar(50), 
			@sub_code_old varchar(2), @password_old nvarchar(100)
			
			SELECT @username_old = Username, 
			@sub_code_old = SubBranchCode, @password_old = Password
			FROM CbcAuthen WHERE Id = @cbc_id
			
			SET @m_msg = 'Update CBC ID = ' + CONVERT(varchar(5),@cbc_id) + ':'
			
			IF @sub_code <> @sub_code_old
			BEGIN
				SET @m_msg += ' sub('+ @sub_code_old +' --> '+ @sub_code +')'
			END
			IF @password <> @password_old
			BEGIN
				SET @m_msg += ' pwd(' + @password_old + ' --> ' + @password + ')'
				
				UPDATE CbcAuthen SET ResetedDate = @d, ExpiredDate = DATEADD(DAY,90,@d), ResetedBy = @creater_id
				WHERE Id = @cbc_id
			END
						
			UPDATE CbcAuthen SET Username = @username, 
			SubBranchCode = @sub_code, Password = @password
			WHERE Id = @cbc_id
			
			-- Insert UserLog
			EXEC dbo.krd_userlogplus_insert @creater_id, @mlogin_name, @mbranch_code, @msub_code, '', '', '', @m_msg  
			
			SELECT @cbc_id Value
		END
	END	
END
GO
