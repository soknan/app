
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
-- =============================================
-- Author:		Sovathena NETH
-- Create date: 27/08/2012
-- Description:	query User Validity
-- =============================================
CREATE PROCEDURE [dbo].[krd_uservalidity_select] 
	-- Add the parameters for the stored procedure here
	@user_id int
AS

BEGIN
	DECLARE @now date
	SET @now = GETDATE()

	SELECT Id, UserId, CreatedBy, Type, RequestDate, StartDate, EndDate, Note, dbo.fn_datetime_format(CreatedDate) CreatedDate, CountEmailSent, 
	'Status' = (CASE WHEN @now > EndDate THEN -1 WHEN @now BETWEEN StartDate AND EndDate THEN 1 ELSE 0 END)
	FROM UserAuthen_Validity
	WHERE UserId = @user_id
	ORDER BY StartDate DESC
END
GO
