
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
-- =============================================
-- Author:		<Sovathena NETH>
-- Create date: <24 Apr 2012>
-- Description:	<Do authentication>
-- Id > 0 means authenticated
-- Id = -1 means invalid username or password
-- Id = -2 means the user has expired or been disabled.
-- =============================================
CREATE FUNCTION [dbo].[fn_do_authentication]
(
	-- Add the parameters for the function here
	@username  varchar(50),
	@psw  varchar(50)
)
RETURNS INT
AS
BEGIN
	
	DECLARE @user_id INT, @now DATE
	
	SET @now = GETDATE()

	SELECT @user_id = usr.Id FROM UserAuthen usr
	WHERE RTRIM(LTRIM(usr.Username)) = @username 
	AND PWDCOMPARE(@psw ,usr.[Password]) = 1
	AND usr.Status = 'A'
	
	SET @user_id = ISNULL(@user_id,-1)
	-- Check user type and corresponding validity
	-- Permanent vs Acting
	IF @user_id > 0
	BEGIN
		DECLARE @usr_type int
		SELECT @usr_type = UserType FROM UserAuthen WHERE Id = @user_id
		IF @usr_type = 0
		BEGIN
			IF EXISTS (SELECT * FROM UserAuthen_Validity WHERE UserId=@user_id AND Type='D' AND @now >= CONVERT(DATE, StartDate) AND @now <= CONVERT(DATE, EndDate))
			BEGIN
				SET @user_id = -2
			END
		END
		ELSE
		BEGIN
			IF NOT EXISTS (SELECT * FROM UserAuthen_Validity WHERE UserId=@user_id AND Type='E' AND @now >= CONVERT(DATE, StartDate) AND @now <= CONVERT(DATE, EndDate))
			BEGIN
				SET @user_id = -2
			END
		END
	END	
	
	RETURN @user_id 	
END
GO
