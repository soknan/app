
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
-- =============================================
-- Author:		Sovathena NETH
-- Create date: 24/Apr/2012
-- Description:	Get Authentication (Username, Password) of CBC to access B2B
-- =============================================
CREATE PROCEDURE [dbo].[krd_cbcauthen_get_authentication] 
	-- Add the parameters for the stored procedure here
	@username  varchar(50),
	@psw  varchar(50)
AS

BEGIN

	DECLARE @user_id INT
		
	SET @user_id = dbo.fn_do_authentication(@username, @psw)
	
	IF @user_id>0
	BEGIN
		SELECT cbc.Username, cbc.Password, usr.Id UserId, usr.SubBranchCode FROM CbcAuthen cbc
		INNER JOIN UserAuthen usr ON cbc.SubBranchCode = usr.SubBranchCode
		WHERE usr.Id = @user_id		
	END
	
END
GO
