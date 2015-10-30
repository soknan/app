
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
-- =============================================
-- Author:		Sovathena NETH
-- Create date: 27/Apr/2012
-- Description:	Select Log
-- =============================================
CREATE PROCEDURE [dbo].[krd_log_select] 
	-- Add the parameters for the stored procedure here
	@level varchar(5), @logger varchar(500), @message nvarchar(250), 
	@exception nvarchar(3000), @subcode varchar(4), @duration int
	
AS

BEGIN

	SELECT * FROM [Log] 
	WHERE (LEN(@level) = 0 OR [Level] = @level) 
	AND (LEN(@logger) = 0 OR  Logger like '%' + @logger +'%')
	AND (LEN(@message) = 0 OR [Message] like '%'+ @message +'%') 
	AND (LEN(@exception) = 0 OR Exception like '%'+ @exception +'%')
	AND (LEN(@subcode) = 0 OR SubBranchCode = @subcode)
	AND (@duration = 0 OR DATEDIFF(DAY, [Date], GETDATE()) <= @duration)
	
END
GO
