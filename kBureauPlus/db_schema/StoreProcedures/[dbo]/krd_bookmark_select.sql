
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
-- =============================================
-- Author:		Sovathena NETH
-- Create date: 03/Apr/2012
-- Description:	Authenticate
-- =============================================
ALTER PROCEDURE [dbo].[krd_bookmark_select] 
	-- Add the parameters for the stored procedure here
	@user_id int
AS

BEGIN
	DECLARE @role varchar(3)
	SELECT @role = RoleCode FROM UserAuthenRole WHERE UserId = @user_id
	SET @role = ISNULL(@role,'USR')
	
	IF @role = 'ADM'
	BEGIN
		SELECT *, 1 C, 1 R, 1 U, 1 D FROM Bookmark WHERE Id <> 7 AND MainCode = ''
		UNION
		SELECT Id, Label, 'cbr' BookmarkCode, Href, Icon, 1 Sequence, MainCode, 1 C, 1 R, 1 U, 1 D FROM Bookmark WHERE Id = 12 
		ORDER BY Sequence
	END
	ELSE
	BEGIN
		SELECT bm.*, br.C, br.R, br.U, br.D 
		FROM Bookmark bm INNER JOIN BookmarkRole br ON bm.BookmarkCode = br.BookmarkCode
		WHERE br.RoleCode = @role AND MainCode = ''
		ORDER BY Sequence
	END
END
GO
