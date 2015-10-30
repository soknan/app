
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
-- =============================================
-- Author:		Sovathena NETH
-- Create date: 20/07/2012
-- Description:	Delete CBC User 
-- =============================================
CREATE PROCEDURE [dbo].[krd_cbcauthen_delete] 
	-- Add the parameters for the stored procedure here
	@cbc_id int, @creater_id int
AS

BEGIN
	DECLARE @role varchar(5), @mlogin_name varchar(50), @mbranch_code varchar(4), 
	@msub_code varchar(4), @m_msg varchar(500)
	
	SELECT @role = RoleCode FROM UserAuthenRole WHERE UserId = @creater_id
	IF @role = 'ADM'
	BEGIN
		SELECT @mlogin_name = Username, @mbranch_code = BranchCode, @msub_code = SubBranchCode
		FROM UserAuthen WHERE Id = @creater_id
		
		DECLARE @username varchar(50)
		SELECT @username = Username FROM CbcAuthen WHERE Id = @cbc_id
		
		SET @m_msg = 'Delete CBC user ' + @username
		
		-- Insert UserLog
		EXEC dbo.krd_userlogplus_insert @creater_id, @mlogin_name, @mbranch_code, @msub_code, '', '', '', @m_msg  
	
		DELETE CbcAuthen WHERE Id = @cbc_id
		SELECT @cbc_id Value
	END
END
GO
