
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
-- =============================================
-- Author:		Sovathena NETH
-- Create date: 12/05/2012
-- Description:	query User
-- =============================================
CREATE PROCEDURE [dbo].[krd_userauthen_select] 
	-- Add the parameters for the stored procedure here
	@filter varchar(50), @branch varchar(4), @sub_branch varchar(4), @status varchar(1)
AS

BEGIN
	
	DECLARE @now date
	SET @now = GETDATE()

	SELECT usr.Id, FName,LName, Sex, Username, Email, usr.SubBranchCode, usr.BranchCode,
	usr.Note, dbo.fn_datetime_format(LastLoginDate) LastLoginDate,  
	dbo.fn_datetime_format(DateCreated) DateCreated, [Status],
	usr.SecurityCode, usr.UserType,
	'Validity' = (CASE WHEN usr.UserType = 0 THEN(CASE WHEN val.StartDate IS NULL THEN 1 ELSE 0 END) ELSE (CASE WHEN val.StartDate IS NULL THEN 0 ELSE 1 END) END) 
	FROM UserAuthen usr
	LEFT OUTER JOIN UserAuthen_Validity val ON usr.Id = val.UserId AND @now BETWEEN StartDate AND EndDate
	LEFT OUTER JOIN SubBranch sub ON usr.SubBranchCode = sub.Code
	LEFT OUTER JOIN Branch br ON sub.BranchId = br.Id
	WHERE (LEN(@filter) = 0 OR 
	(Username like '%'+ @filter +'%' 
	OR Email like '%'+ @filter +'%' 
	OR FName like '%'+ @filter +'%') 
	OR LName like '%'+ @filter +'%')
	AND (LEN(@branch)=0 OR br.Code = @branch)
	AND (LEN(@sub_branch)=0 OR sub.Code = @sub_branch)
	AND (LEN(@status)=0 OR usr.Status = @status)
	AND usr.Status <> 'D'
	ORDER BY Id DESC
	
END
GO
