
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
-- =============================================
-- Author:		Sovathena NETH
-- Create date: 19/08/2012
-- Description:	query User to email about kBureau User Account. for example, Prey Veng + Neak Leoung
-- =============================================
CREATE PROCEDURE [dbo].[krd_userauthen_select_to_email] 
	-- Add the parameters for the stored procedure here
	@filter varchar(50)
AS

BEGIN
	SELECT Id, FName,LName, Sex, Username, Email, SubBranchCode, dbo.fn_datetime_format(LastLoginDate) LastLoginDate,  dbo.fn_datetime_format(DateCreated) DateCreated, [Status], Note FROM UserAuthen 
	WHERE 
	(LEN(@filter) = 0 OR (Username like '%'+ @filter +'%' OR Email like '%'+ @filter +'%' OR SubBranchCode like '%'+ @filter +'%'))
	AND Status = 'A'
	AND Email IS NOT NULL
	AND SecurityCode = 0
END
GO
