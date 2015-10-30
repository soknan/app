
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
-- =============================================
-- Author:		Sovathena NETH
-- Create date: 29/05/2012
-- Description:	Insert User Log Master
-- =============================================
CREATE PROCEDURE [dbo].[krd_userlogplus_insert] 
	-- Add the parameters for the stored procedure here
	@UserId int, @LoginName varchar(50),
	@BranchCode varchar(4), @SubBranchCode varchar(4), 
	@RemoteAddress varchar(50),	@RemoteHost varchar(50), 
	@UserAgent varchar(5), @Message nvarchar(250)
AS

BEGIN

	INSERT INTO UserLogPlus (UserId, LoginName, [DateTime], BranchCode, SubBranchCode, UserAgent, RemoteHost, RemoteAddress, [Message])
	VALUES(@UserId, @LoginName, GETDATE(), @BranchCode, @SubBranchCode, @UserAgent, @RemoteHost, @RemoteAddress, @Message)
END
GO
