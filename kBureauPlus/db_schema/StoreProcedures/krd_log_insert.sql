
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
-- =============================================
-- Author:		Sovathena NETH
-- Create date: 27/Apr/2012
-- Description:	Insert Log
-- =============================================
CREATE PROCEDURE [dbo].[krd_log_insert] 
	-- Add the parameters for the stored procedure here
	@thread varchar(10), @level varchar(5), @logger varchar(500),
	@message nvarchar(250), @exception nvarchar(3000), @subcode varchar(4)
AS

BEGIN

	INSERT INTO Log(Thread, [Level], Logger, [Message], Exception, SubBranchCode, [Date])
	VALUES(@thread, @level, @logger, @message, @exception, @subcode, GETDATE())
	
	SELECT @@IDENTITY
END
GO
