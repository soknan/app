
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
-- =============================================
-- Author:		Sovathena NETH
-- Create date: 13/June/2012
-- Description:	Get Sub Bookmarks
-- =============================================
CREATE PROCEDURE [dbo].[krd_bookmark_select_sub] 
	-- Add the parameters for the stored procedure here
	@user_id int, @main_code varchar(3)
AS

BEGIN
	DECLARE @role varchar(3)
	SELECT @role = RoleCode FROM UserAuthenRole WHERE UserId = @user_id
	SET @role = ISNULL(@role,'USR')
	
	IF @role = 'ADM'
	BEGIN
		SELECT *, 1 C, 1 R, 1 U, 1 D FROM Bookmark WHERE MainCode = @main_code ORDER BY Sequence
	END
	ELSE
	BEGIN
		SELECT bm.*, br.C, br.R, br.U, br.D 
		FROM Bookmark bm INNER JOIN BookmarkRole br ON bm.BookmarkCode = br.BookmarkCode
		WHERE br.RoleCode = @role AND bm.MainCode = @main_code
		ORDER BY Sequence
	END
END
GO
