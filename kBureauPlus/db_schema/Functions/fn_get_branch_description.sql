
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
-- =============================================
-- Author:		<Sovathena NETH>
-- Create date: <7 July 2012>
-- Description:	<Get Branch Description>
-- =============================================
CREATE FUNCTION [dbo].[fn_get_branch_description]
(
	-- Add the parameters for the function here
	@code  varchar(4)
)
RETURNS varchar(50)
AS
BEGIN
	
	DECLARE @description varchar(50)
	IF(@code = '*')
	BEGIN
		SET @description = 'All'
	END
	ELSE
	BEGIN
		SELECT @description = Branch FROM Branch WHERE Code = @code
	END
	
	RETURN @description
END
GO
