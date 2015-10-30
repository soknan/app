
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
-- =============================================
-- Author:		<Sovathena NETH>
-- Create date: <7 July 2012>
-- Description:	<Get Branch Id>
-- =============================================
CREATE FUNCTION [dbo].[fn_get_branch_id]
(
	-- Add the parameters for the function here
	@code  varchar(4)
)
RETURNS varchar(50)
AS
BEGIN
	
	DECLARE @id int
	IF(@code = '*')
	BEGIN
		SET @id = 0
	END
	ELSE
	BEGIN
		SELECT @id = Id FROM Branch WHERE Code = @code
	END
	
	RETURN @id
END
GO
