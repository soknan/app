
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
-- =============================================
-- Author:		Sovathena NETH
-- Create date: 27/Apr/2012
-- Description:	Get Authentication (Username, Password) of CBC to access B2B
-- =============================================
CREATE PROCEDURE [dbo].[krd_cbcauthen_get_authentication_by_id] 
	-- Add the parameters for the stored procedure here
	@user_id int
AS

BEGIN

	IF @user_id>0
	BEGIN
		SELECT cbc.Username, cbc.Password, usr.Id UserId, usr.SubBranchCode FROM CbcAuthen cbc
		INNER JOIN UserAuthen usr ON cbc.SubBranchCode = usr.SubBranchCode
		WHERE usr.Id = @user_id		
	END
	
END
GO
