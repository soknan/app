
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
-- =============================================
-- Author:		Sovathena NETH
-- Create date: 05/05/2012
-- Description:	Insert User Log
-- =============================================
CREATE PROCEDURE [dbo].[krd_userlog_insert] 
	-- Add the parameters for the stored procedure here
	@UserId int, @LoginName varchar(50), @SubBranchCode varchar(4), @PcName varchar(50),
	@PcUsername varchar(50), @AppVersion varchar(5), @Message nvarchar(500)
AS

BEGIN

	INSERT INTO UserLog (UserId, LoginName, [DateTime], SubBranchCode, PcName, PcUsername, AppVersion, [Message])
	VALUES(@UserId, @LoginName, GETDATE(), @SubBranchCode, @PcName, @PcUsername, @AppVersion, @Message)
END
GO
