
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
-- =============================================
-- Author:		Sovathena NETH
-- Create date: 27/08/2012
-- Description:	Save user validity
-- =============================================
CREATE PROCEDURE [dbo].[krd_uservalidity_save] 
	-- Add the parameters for the stored procedure here
	@creater_id int,
	@user_id int,
	@validity_id int,
	@request_dt varchar(10),
	@start_dt varchar(10),
	@end_dt varchar(10),
	@note varchar(250)
	
AS

BEGIN
	DECLARE @role varchar(5), @mlogin_name varchar(50), @mbranch_code varchar(4), 
	@msub_code varchar(4), @m_msg varchar(500), @user_type int, @validiy_type varchar(1)
	
	SELECT @role = RoleCode FROM UserAuthenRole WHERE UserId = @creater_id
	IF @role = 'ADM'
	BEGIN
		
		SELECT @mlogin_name = Username, @mbranch_code = BranchCode, @msub_code = SubBranchCode
		FROM UserAuthen WHERE Id = @creater_id
		
		SELECT @user_type = UserType FROM UserAuthen WHERE Id = @user_id
				
		IF @validity_id = 0
		BEGIN
			SET @m_msg = 'Add new validiy -- '
			
			IF @user_type = 0 -- Permanent User
			BEGIN
				SET @validiy_type = 'D'
				SET @m_msg += ' disable from ' + @start_dt + ' to ' + @end_dt
			END
			ELSE
			BEGIN
				SET @validiy_type = 'E'
				SET @m_msg += ' enable from ' + @start_dt + ' to ' + @end_dt
			END
			
			-- Insert UserLog
			EXEC dbo.krd_userlogplus_insert @creater_id, @mlogin_name, @mbranch_code, @msub_code, '', '', '', @m_msg  
			
			INSERT INTO UserAuthen_Validity (UserId, RequestDate, StartDate, EndDate, Note, CreatedBy, CreatedDate, Type)
			VALUES(@user_id, @request_dt, @start_dt, @end_dt, @note, @creater_id, GETDATE(), @validiy_type)
			
			SELECT @@IDENTITY Value
		END
		ELSE
		BEGIN
			
			DECLARE @request_dt_old varchar(10), 
			@start_dt_old varchar(10), 
			@end_dt_old varchar(10),
			@note_old varchar(250),
			@type_old varchar(1)
			
			SELECT @request_dt_old = RequestDate, 
			@start_dt_old = StartDate, 
			@end_dt_old = EndDate,
			@note_old = Note,
			@type_old = Type
			FROM UserAuthen_Validity WHERE Id = @validity_id
			
			SET @m_msg = 'Update validity ID = ' + CONVERT(varchar(5),@validity_id) + ':'
			IF @request_dt <> @request_dt_old
			BEGIN
				SET @m_msg += ' request_dt('+ @request_dt_old +'-->'+ @request_dt +')'
			END
			IF @start_dt <> @start_dt_old
			BEGIN
				SET @m_msg += ' start_dt('+ @start_dt_old +'-->'+ @start_dt +')'
			END
			IF @end_dt <> @end_dt_old
			BEGIN
				SET @m_msg += ' sub('+ @end_dt_old +'-->'+ @end_dt +')'
			END
			IF @note <> @note_old
			BEGIN
				SET @m_msg += ' note(...)'		
			END
			IF @validiy_type <> @type_old
			BEGIN
				SET @m_msg += ' type(' + @type_old + '-->' + @validiy_type +')'		
			END
			
			UPDATE UserAuthen_Validity 
			SET RequestDate = @request_dt, 
			StartDate = @start_dt, 
			EndDate = @end_dt,
			Note = @note
			WHERE Id = @validity_id
			
			-- Insert UserLog
			EXEC dbo.krd_userlogplus_insert @creater_id, @mlogin_name, @mbranch_code, @msub_code, '', '', '', @m_msg  
			
			SELECT @validity_id Value
		END
	END	
END
GO
