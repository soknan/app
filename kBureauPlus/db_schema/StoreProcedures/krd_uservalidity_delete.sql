
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
-- =============================================
-- Author:		Sovathena NETH
-- Create date: 27/08/2012
-- Description:	Delete User validity 
-- =============================================
CREATE PROCEDURE [dbo].[krd_uservalidity_delete] 
	-- Add the parameters for the stored procedure here
	@validity_id int, @creater_id int
AS

BEGIN
	DECLARE @role varchar(5), @mlogin_name varchar(50), @mbranch_code varchar(4), 
	@msub_code varchar(4), @m_msg varchar(500)
	
	SELECT @role = RoleCode FROM UserAuthenRole WHERE UserId = @creater_id
	IF @role = 'ADM'
	BEGIN
		SELECT @mlogin_name = Username, @mbranch_code = BranchCode, @msub_code = SubBranchCode
		FROM UserAuthen WHERE Id = @creater_id
		
		SET @m_msg = 'Delete user validity'
		
		-- Insert UserLog
		EXEC dbo.krd_userlogplus_insert @creater_id, @mlogin_name, @mbranch_code, @msub_code, '', '', '', @m_msg  
	
		DELETE UserAuthen_Validity WHERE Id = @validity_id	
		SELECT @validity_id Value
	END
END
GO
