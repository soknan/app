
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
-- =============================================
-- Author:		<Sovathena NETH>
-- Create date: <8 May 2012>
-- Description:	<Get User Email by Id>
-- =============================================
CREATE FUNCTION [dbo].[fn_userauthen_get_email]
(
	-- Add the parameters for the function here
	@UserId  int
)
RETURNS varchar(50)
AS
BEGIN
	
	DECLARE @email varchar(50)
	SELECT  @email = Email FROM UserAuthen WHERE Id = @UserId
	SET @email = ISNULL(@email,'')
	RETURN @email
END
GO
