
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
-- =============================================
-- Author:		Sovathena NETH
-- Create date: 11/05/2012
-- Description:	query User Log
-- =============================================
CREATE PROCEDURE [dbo].[krd_userlog_select] 
	-- Add the parameters for the stored procedure here
	
	@filter varchar(50), 
	@branch varchar(4), 
	@sub_branch varchar(4), 
	@from_date varchar(10),
	@to_date varchar(10),
	@app_type int,
	@usr_type int,
	@log_type int
	
AS

BEGIN
	
	DECLARE @msg_type varchar(20)	
	
	SELECT @msg_type = 
	CASE WHEN @log_type = 0 THEN ''
	WHEN @log_type = 1 THEN 'Login'
	WHEN @log_type = 2 THEN 'Logout'
	WHEN @log_type = 3 THEN 'Reset password'
	WHEN @log_type = 4 THEN 'request to CBC'
	WHEN @log_type = 5 THEN 'Closed'
	WHEN @log_type = 6 THEN 'Add new user'
	WHEN @log_type = 7 THEN 'Update user'
	WHEN @log_type = 8 THEN 'Delete user'
	END	

	IF OBJECT_ID('tempdb..#tmp_tbl') IS NOT NULL
	BEGIN
		drop table #tmp_tbl
	END
	
	CREATE TABLE #tmp_tbl (Id int)
	
	IF @usr_type = 1 -- Senior Management
	BEGIN
		INSERT INTO #tmp_tbl
		SELECT Id FROM UserAuthen WHERE Id IN(2,21,75,100,103)
	END
	ELSE IF @usr_type = 2 -- Head Office / IT
	BEGIN
		INSERT INTO #tmp_tbl
		SELECT Id FROM UserAuthen WHERE BranchCode = '*' 
		AND SubBranchCode = '*' 
		AND	Id NOT IN(2,21,75,100,103)
	END
	ELSE IF @usr_type = 3 -- BM / ABM
	BEGIN
		INSERT INTO #tmp_tbl
		SELECT Id FROM UserAuthen WHERE BranchCode <> '*' 
		AND SubBranchCode = '*' 
	END
	ELSE IF @usr_type = 4 -- SBM / HT
	BEGIN
		INSERT INTO #tmp_tbl
		SELECT Id FROM UserAuthen WHERE BranchCode <> '*' 
		AND SubBranchCode <> '*' 
	END
	ELSE -- Anonymous
	BEGIN
		INSERT INTO #tmp_tbl
		VALUES(0),(-1)
	END
	
	IF OBJECT_ID('tempdb..#tmp_tbl_sub') IS NOT NULL
	BEGIN
		drop table #tmp_tbl_sub
	END
	SELECT sub.Code INTO #tmp_tbl_sub FROM SubBranch sub INNER JOIN Branch br ON sub.BranchId = br.Id
	WHERE br.Code = @branch
	
	IF @app_type = 0
	BEGIN
	
		SELECT * FROM

		(SELECT dbo.fn_datetime_format(ul.DateTime) [DateTime], ul.DateTime [Date], 
		ul.UserId, 
		ul.LoginName,
		'SubBranch' =
		CASE 
			WHEN LEN(ul.SubBranchCode) = 0 THEN ''
			ELSE sub.Code + ' ' + sub.SubBranch
		END,
		'Branch' =
		CASE 
			WHEN LEN(ul.SubBranchCode) = 0 THEN ''
			ELSE bra.Code
		END,
		
		ul.PcName, ul.PcUsername, ul.AppVersion,	
		ul.[Message]  FROM UserLog ul
		LEFT OUTER JOIN SubBranch sub ON sub.Code = ul.SubBranchCode
		LEFT OUTER JOIN Branch bra ON bra.Id = sub.BranchId
		WHERE (LEN(@branch) = 0 OR ul.SubBranchCode IN(SELECT * FROM #tmp_tbl_sub))
		AND (LEN(@sub_branch) = 0 OR ul.SubBranchCode = @sub_branch)
		AND (LEN(@filter) = 0 OR(LoginName like'%'+ @filter +'%')
		OR(ul.PcName like'%'+ @filter +'%')
		OR(ul.PcUsername like'%'+ @filter +'%')
		OR(ul.AppVersion like'%'+ @filter +'%')
		OR(ul.[Message] like'%'+ @filter +'%')
		)
		AND (LEN(@msg_type) = 0 OR CHARINDEX(@msg_type,ul.Message) > 0)
		AND (LEN(@from_date) = 0 OR LEN(@to_date) = 0 OR ul.[DateTime] BETWEEN @from_date AND DATEADD(SECOND,86399,@to_date))
		AND (@usr_type = 0 OR ul.UserId IN (SELECT Id FROM #tmp_tbl))
				
		UNION
		
		SELECT dbo.fn_datetime_format(ul.DateTime) [DateTime], ul.DateTime [Date],
		ul.UserId, 
		ul.LoginName,
		'SubBranch' =
		CASE 
			WHEN LEN(ul.SubBranchCode) = 0 THEN ''
			ELSE sub.Code + ' ' + sub.SubBranch
		END,
		'Branch' =
		CASE 
			WHEN LEN(ul.SubBranchCode) = 0 THEN ''
			ELSE bra.Code
		END,
		
		ul.RemoteHost PcName, ul.RemoteAddress PcUsername, 'k+' AppVersion,	
		ul.[Message]  FROM UserLogPlus ul
		LEFT OUTER JOIN SubBranch sub ON sub.Code = ul.SubBranchCode
		LEFT OUTER JOIN Branch bra ON bra.Id = sub.BranchId
		WHERE (LEN(@branch) = 0 OR ul.BranchCode = @branch)
		AND (LEN(@sub_branch) = 0 OR ul.SubBranchCode = @sub_branch)
		AND (LEN(@filter) = 0 OR(LoginName like'%'+ @filter +'%')
		OR(ul.[Message] like'%'+ @filter +'%')
		)
		AND (LEN(@msg_type) = 0 OR CHARINDEX(@msg_type,ul.Message) > 0)
		AND (LEN(@from_date) = 0 OR LEN(@to_date) = 0 OR ul.[DateTime] BETWEEN @from_date AND DATEADD(SECOND,86399,@to_date))
		AND (@usr_type = 0 OR ul.UserId IN (SELECT Id FROM #tmp_tbl))		
		) TBL_LOG
		ORDER BY TBL_LOG.Date DESC 
	END
	ELSE IF @app_type = 1	-- kBureau
	BEGIN
		SELECT dbo.fn_datetime_format(ul.DateTime) [DateTime], ul.DateTime [Date],
		ul.UserId, 
		ul.LoginName,
		'SubBranch' =
		CASE 
			WHEN LEN(ul.SubBranchCode) = 0 THEN ''
			ELSE sub.Code + ' ' + sub.SubBranch
		END,
		'Branch' =
		CASE 
			WHEN LEN(ul.SubBranchCode) = 0 THEN ''
			ELSE bra.Code
		END,
		
		ul.PcName, ul.PcUsername, ul.AppVersion,	
		ul.[Message]  FROM UserLog ul
		LEFT OUTER JOIN SubBranch sub ON sub.Code = ul.SubBranchCode
		LEFT OUTER JOIN Branch bra ON bra.Id = sub.BranchId
		WHERE (LEN(@branch) = 0 OR ul.SubBranchCode IN(SELECT * FROM #tmp_tbl_sub))
		AND (LEN(@sub_branch) = 0 OR ul.SubBranchCode = @sub_branch)
		AND (LEN(@filter) = 0 OR(LoginName like'%'+ @filter +'%')
		OR(ul.PcName like'%'+ @filter +'%')
		OR(ul.PcUsername like'%'+ @filter +'%')
		OR(ul.AppVersion like'%'+ @filter +'%')
		OR(ul.[Message] like'%'+ @filter +'%')
		)
		AND (LEN(@msg_type) = 0 OR CHARINDEX(@msg_type,ul.Message) > 0)
		AND (LEN(@from_date) = 0 OR LEN(@to_date) = 0 OR ul.[DateTime] BETWEEN @from_date AND DATEADD(SECOND,86399,@to_date))
		AND (@usr_type = 0 OR ul.UserId IN (SELECT Id FROM #tmp_tbl))
		ORDER BY ul.Id DESC 
	END
	ELSE 
	BEGIN
		SELECT dbo.fn_datetime_format(ul.DateTime) [DateTime], ul.DateTime [Date],
		ul.UserId, 
		ul.LoginName,
		'SubBranch' =
		CASE 
			WHEN LEN(ul.SubBranchCode) = 0 THEN ''
			ELSE sub.Code + ' ' + sub.SubBranch
		END,
		'Branch' =
		CASE 
			WHEN LEN(ul.SubBranchCode) = 0 THEN ''
			ELSE bra.Code
		END,
		
		ul.RemoteHost PcName, ul.RemoteAddress PcUsername, 'k+' AppVersion,	
		ul.[Message]  FROM UserLogPlus ul
		LEFT OUTER JOIN SubBranch sub ON sub.Code = ul.SubBranchCode
		LEFT OUTER JOIN Branch bra ON bra.Id = sub.BranchId
		WHERE (LEN(@branch) = 0 OR ul.BranchCode = @branch)
		AND (LEN(@sub_branch) = 0 OR ul.SubBranchCode = @sub_branch)
		AND (LEN(@filter) = 0 OR(LoginName like'%'+ @filter +'%')
		OR(ul.[Message] like'%'+ @filter +'%')
		)
		AND (LEN(@msg_type) = 0 OR CHARINDEX(@msg_type,ul.Message) > 0)
		AND (LEN(@from_date) = 0 OR LEN(@to_date) = 0 OR ul.[DateTime] BETWEEN @from_date AND DATEADD(SECOND,86399,@to_date))
		AND (@usr_type = 0 OR ul.UserId IN (SELECT Id FROM #tmp_tbl))
		ORDER BY ul.Id DESC 				
	END
	
END
GO
