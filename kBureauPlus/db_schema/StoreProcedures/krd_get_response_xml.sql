
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
-- =============================================
-- Author:		Sovathena NETH
-- Create date: 23/Apr/2012
-- Description:	Get XML Response from CBC Report
-- =============================================
CREATE PROCEDURE [dbo].[krd_get_response_xml] 
	-- Add the parameters for the stored procedure here
	@id  int, @ref_no varchar(50)
AS

BEGIN
	IF @id > 0 
	BEGIN
		SELECT Response FROM CbcReport WHERE Id = @id
	END
	ELSE IF LEN(@ref_no) > 0
	BEGIN
		SELECT Response FROM CbcReport WHERE RefNumber = @ref_no
	END
	
END
GO
