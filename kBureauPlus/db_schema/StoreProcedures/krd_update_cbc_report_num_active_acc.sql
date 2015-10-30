
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
-- =============================================
-- Author:		Sovathena NETH
-- Create date: 15/05/2012
-- Description:	Update CBC Report
-- =============================================
CREATE PROCEDURE [dbo].[krd_update_cbc_report_num_active_acc] 
	-- Add the parameters for the stored procedure here
	@CbcReportId int, @num_active int
AS

BEGIN
	
	UPDATE CbcReport SET NumActiveAcc = @num_active
	WHERE Id = @CbcReportId
END
GO
