
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
-- =============================================
-- Author:		Sovathena NETH
-- Create date: 18/05/2012
-- Description:	Get USD Rate that is effective
-- =============================================
CREATE PROCEDURE [dbo].[krd_usdrate_select_top] 
	-- Add the parameters for the stored procedure here
AS

BEGIN
	SELECT TOP 1 UsdRate FROM UsdRate WHERE GETDATE() > [Date]  ORDER BY [Date] DESC
END
GO
