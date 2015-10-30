
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
-- =============================================
-- Author:		Sovathena NETH
-- Create date: 08/05/2012
-- Description:	Check is exist username
-- =============================================
CREATE PROCEDURE [dbo].[krd_userauthen_exist_username] 
	-- Add the parameters for the stored procedure here
	@UserId int, @Username varchar(50)
	
AS

BEGIN
	DECLARE @id int
	SELECT  @id = Id FROM UserAuthen WHERE Username = @Username AND Id <> @UserId AND Status = 'A'
	SET @id = ISNULL(@id,-1)
	SELECT @id
END
GO
