
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
-- =============================================
-- Author:		Sovathena NETH
-- Create date: 18/07/2012
-- Description:	Save user
-- =============================================
CREATE PROCEDURE [dbo].[krd_userauthen_save] 
	-- Add the parameters for the stored procedure here
	@username varchar(50), 
	@branch_code varchar(2), 
	@sub_code varchar(2),
	@email varchar(50), 
	@status varchar(1),
	@fname varchar(30), 
	@lname varchar(20), 
	@sex varchar(1),
	@note varchar(250),
	@user_id int,
	@creater_id int,
	@user_type int
AS

BEGIN
	DECLARE @role varchar(5), @mlogin_name varchar(50), @mbranch_code varchar(4), 
	@msub_code varchar(4), @m_msg varchar(500)
	
	SELECT @role = RoleCode FROM UserAuthenRole WHERE UserId = @creater_id
	IF @role = 'ADM'
	BEGIN
		
		SELECT @mlogin_name = Username, @mbranch_code = BranchCode, @msub_code = SubBranchCode
		FROM UserAuthen WHERE Id = @creater_id
				
		IF @user_id = 0
		BEGIN
			SET @m_msg = 'Add new user -- ' + @username
			-- Insert UserLog
			EXEC dbo.krd_userlogplus_insert @creater_id, @mlogin_name, @mbranch_code, @msub_code, '', '', '', @m_msg  
			
			INSERT INTO UserAuthen (Username, Password, BranchCode, SubBranchCode, Status, Email, FName, LName, Sex, Note, CreatedBy, UserType)
			VALUES(@username, PWDENCRYPT(@username), @branch_code, @sub_code, @status, @email, @fname, @lname, @sex, @note, @creater_id, @user_type)
			
			SELECT @@IDENTITY Value
		END
		ELSE
		BEGIN
			
			DECLARE @username_old varchar(50), 
			@branch_code_old varchar(2), 
			@sub_code_old varchar(2),
			@email_old varchar(50), 
			@status_old varchar(1),
			@fname_old varchar(30), 
			@lname_old varchar(20), 
			@sex_old varchar(1),
			@note_old varchar(250),
			@user_type_old varchar(1)
			
			SELECT @username_old = Username, 
			@branch_code_old = BranchCode,
			@sub_code_old = SubBranchCode,
			@email_old = Email,
			@status_old = Status,
			@fname_old = FName,
			@lname_old = LName,
			@sex_old = Sex,
			@note_old = Note
			FROM UserAuthen WHERE Id = @user_id
			
			SET @m_msg = 'Update user ID = ' + CONVERT(varchar(5),@user_id) + ':'
			IF @username <> @username_old
			BEGIN
				SET @m_msg += ' username('+ @username_old +'-->'+ @username +')'
			END
			IF @branch_code <> @branch_code_old
			BEGIN
				SET @m_msg += ' branch('+ @branch_code_old +'-->'+ @branch_code +')'
			END
			IF @sub_code <> @sub_code_old
			BEGIN
				SET @m_msg += ' sub('+ @sub_code_old +'-->'+ @sub_code +')'
			END
			IF @email <> @email_old
			BEGIN
				SET @m_msg += ' email('+ @email_old +'-->'+ @email +')'
			END
			IF @status <> @status_old
			BEGIN
				SET @m_msg += ' email('+ @status_old +'-->'+ @status +')'
			END
			IF @fname <> @fname_old
			BEGIN
				SET @m_msg += ' email('+ @fname_old +'-->'+ @fname +')'
			END
			IF @lname <> @lname_old
			BEGIN
				SET @m_msg += ' email('+ @lname_old +'-->'+ @lname +')'
			END
			IF @sex <> @sex_old
			BEGIN
				SET @m_msg += ' email('+ @sex_old +'-->'+ @sex +')'
			END
			IF @note <> @note_old
			BEGIN
				SET @m_msg += ' note(...)'		
			END
			IF @user_type <> @user_type_old
			BEGIN
				SET @m_msg += ' user_type(' + @user_type_old + '-->' + @user_type +')'		
			END
			
			UPDATE UserAuthen SET Username = @username, BranchCode = @branch_code, SubBranchCode = @sub_code,
			Status = @status, Email = @email, FName = @fname, LName = @lname, Sex = @sex, Note = @note, UpdatedBy = @creater_id,
			UserType = @user_type
			WHERE Id = @user_id
			
			-- Insert UserLog
			EXEC dbo.krd_userlogplus_insert @creater_id, @mlogin_name, @mbranch_code, @msub_code, '', '', '', @m_msg  
			
			SELECT @user_id Value
		END
	END	
END
GO
