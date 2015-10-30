
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
-- =============================================
-- Author:		Sovathena NETH
-- Create date: 10/07/2012
-- Description:	Check is exist email
-- =============================================
CREATE PROCEDURE [dbo].[krd_userauthen_exist_email] 
	-- Add the parameters for the stored procedure here
	@UserId int, @Email varchar(50)
	
AS

BEGIN
	DECLARE @id int
	SELECT  @id = Id FROM UserAuthen WHERE Email = @Email AND Id <> @UserId AND Status = 'A'
	SET @id = ISNULL(@id,-1)
	SELECT @id Value
END
GO
