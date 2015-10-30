
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
-- =============================================
-- Author:		<Sovathena NETH>
-- Create date: <14 May 2012>
-- Description:	<DateTIME Format: dd-MM-yyyy, hh:mm tt>
-- =============================================
CREATE FUNCTION [dbo].[fn_datetime_format]
(
	-- Add the parameters for the function here
	@datetime  datetime
	
)
RETURNS varchar(50)
AS
BEGIN
	DECLARE @date varchar(50), @time varchar(7), @time1 varchar(5), @time2 varchar(2)
	SET @date = CONVERT(VARCHAR(10), @datetime, 105)
	SET @time = right(CONVERT( varchar, @datetime, 100),7)
	SET @time1 = LEFT(@time, 5)
	SET @time2 = RIGHT(@time, 2)
	
	
	RETURN @date +', ' + @time1 + ' ' + @time2
END
GO
