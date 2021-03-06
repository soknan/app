USE [kBureau]
GO
/****** Object:  UserDefinedFunction [dbo].[fn_datetime_format]    Script Date: 12/19/2012 15:39:37 ******/
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
/****** Object:  Table [dbo].[District]    Script Date: 12/19/2012 15:39:35 ******/
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
SET ANSI_PADDING ON
GO
CREATE TABLE [dbo].[District](
	[DistrictID] [int] NOT NULL,
	[SID] [uniqueidentifier] NULL,
	[ProvinceID] [int] NULL,
	[Code] [tinyint] NULL,
	[District] [varchar](50) NULL,
 CONSTRAINT [PK_District] PRIMARY KEY CLUSTERED 
(
	[DistrictID] ASC
)WITH (PAD_INDEX  = OFF, STATISTICS_NORECOMPUTE  = OFF, IGNORE_DUP_KEY = OFF, ALLOW_ROW_LOCKS  = ON, ALLOW_PAGE_LOCKS  = ON) ON [PRIMARY]
) ON [PRIMARY]
GO
SET ANSI_PADDING OFF
GO
/****** Object:  Table [dbo].[Commune]    Script Date: 12/19/2012 15:39:35 ******/
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
SET ANSI_PADDING ON
GO
CREATE TABLE [dbo].[Commune](
	[CommuneID] [int] NOT NULL,
	[SID] [uniqueidentifier] NULL,
	[DistrictID] [int] NULL,
	[Code] [tinyint] NULL,
	[Commune] [varchar](50) NULL,
 CONSTRAINT [PK_Commune] PRIMARY KEY CLUSTERED 
(
	[CommuneID] ASC
)WITH (PAD_INDEX  = OFF, STATISTICS_NORECOMPUTE  = OFF, IGNORE_DUP_KEY = OFF, ALLOW_ROW_LOCKS  = ON, ALLOW_PAGE_LOCKS  = ON) ON [PRIMARY]
) ON [PRIMARY]
GO
SET ANSI_PADDING OFF
GO
/****** Object:  Table [dbo].[CodeTableItem]    Script Date: 12/19/2012 15:39:35 ******/
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
CREATE TABLE [dbo].[CodeTableItem](
	[Id] [int] IDENTITY(1,1) NOT NULL,
	[CodeTableId] [int] NULL,
	[Code] [nvarchar](10) NULL,
	[Description] [nvarchar](50) NULL,
	[Seq] [int] NULL,
 CONSTRAINT [PK_CodeTableItem] PRIMARY KEY CLUSTERED 
(
	[Id] ASC
)WITH (PAD_INDEX  = OFF, STATISTICS_NORECOMPUTE  = OFF, IGNORE_DUP_KEY = OFF, ALLOW_ROW_LOCKS  = ON, ALLOW_PAGE_LOCKS  = ON) ON [PRIMARY]
) ON [PRIMARY]
GO
/****** Object:  Table [dbo].[CodeTable]    Script Date: 12/19/2012 15:39:35 ******/
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
CREATE TABLE [dbo].[CodeTable](
	[Id] [int] IDENTITY(1,1) NOT NULL,
	[Code] [nvarchar](10) NULL,
	[Description] [nvarchar](50) NULL,
 CONSTRAINT [PK_CodeTable] PRIMARY KEY CLUSTERED 
(
	[Id] ASC
)WITH (PAD_INDEX  = OFF, STATISTICS_NORECOMPUTE  = OFF, IGNORE_DUP_KEY = OFF, ALLOW_ROW_LOCKS  = ON, ALLOW_PAGE_LOCKS  = ON) ON [PRIMARY]
) ON [PRIMARY]
GO
/****** Object:  Table [dbo].[CbcReport]    Script Date: 12/19/2012 15:39:35 ******/
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
SET ANSI_PADDING ON
GO
CREATE TABLE [dbo].[CbcReport](
	[Id] [int] IDENTITY(1,1) NOT NULL,
	[LoanId] [char](15) NULL,
	[IdNumber] [nvarchar](50) NULL,
	[Name] [nvarchar](100) NULL,
	[SubBranchCode] [varchar](4) NULL,
	[Status] [varchar](5) NULL,
	[RefNumber] [varchar](50) NULL,
	[Action] [varchar](2) NULL,
	[ReportDate] [datetime] NULL,
	[EnquiryType] [varchar](5) NULL,
	[ProductType] [varchar](5) NULL,
	[AccountType] [varchar](5) NULL,
	[Amount] [decimal](16, 2) NULL,
	[Currency] [varchar](10) NULL,
	[Applicant] [int] NULL,
	[Request] [ntext] NULL,
	[Response] [ntext] NULL,
	[UserId] [int] NULL,
	[Decision] [int] NULL,
	[NumActiveAcc] [int] NULL,
	[FeeKredit] [decimal](16, 2) NULL,
	[FeeCbc] [decimal](16, 2) NULL,
	[BranchCode] [varchar](4) NULL,
	[Note] [nvarchar](500) NULL,
 CONSTRAINT [PK_CbcReport] PRIMARY KEY CLUSTERED 
(
	[Id] ASC
)WITH (PAD_INDEX  = OFF, STATISTICS_NORECOMPUTE  = OFF, IGNORE_DUP_KEY = OFF, ALLOW_ROW_LOCKS  = ON, ALLOW_PAGE_LOCKS  = ON) ON [PRIMARY]
) ON [PRIMARY] TEXTIMAGE_ON [PRIMARY]
GO
SET ANSI_PADDING OFF
GO
/****** Object:  Table [dbo].[CbcAuthen]    Script Date: 12/19/2012 15:39:35 ******/
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
SET ANSI_PADDING ON
GO
CREATE TABLE [dbo].[CbcAuthen](
	[Id] [int] IDENTITY(1,1) NOT NULL,
	[Username] [varchar](50) NULL,
	[Password] [nvarchar](100) NULL,
	[SubBranchCode] [varchar](5) NULL,
 CONSTRAINT [PK_CbcAuthen] PRIMARY KEY CLUSTERED 
(
	[Id] ASC
)WITH (PAD_INDEX  = OFF, STATISTICS_NORECOMPUTE  = OFF, IGNORE_DUP_KEY = OFF, ALLOW_ROW_LOCKS  = ON, ALLOW_PAGE_LOCKS  = ON) ON [PRIMARY]
) ON [PRIMARY]
GO
SET ANSI_PADDING OFF
GO
/****** Object:  Table [dbo].[CbcActiveAccount]    Script Date: 12/19/2012 15:39:35 ******/
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
SET ANSI_PADDING ON
GO
CREATE TABLE [dbo].[CbcActiveAccount](
	[Id] [int] IDENTITY(1,1) NOT NULL,
	[CbcReportId] [int] NULL,
	[Creditor] [varchar](100) NULL,
	[ProductType] [varchar](5) NULL,
	[AccountNumber] [varchar](50) NULL,
	[Currency] [varchar](3) NULL,
	[Limit] [decimal](16, 2) NOT NULL,
	[RestructureLoan] [varchar](5) NULL,
	[Tenure] [int] NULL,
	[InstallmentAmount] [decimal](16, 2) NOT NULL,
	[SecurityType] [varchar](5) NULL,
	[OutstandingBalance] [decimal](16, 2) NOT NULL,
	[LastAmountPaid] [decimal](16, 2) NOT NULL,
	[IssueDate] [varchar](10) NULL,
	[ExpiryDate] [varchar](10) NULL,
	[ClosedDate] [varchar](10) NULL,
	[Status] [varchar](1) NULL,
	[PaymentFrequency] [varchar](1) NULL,
	[GroupReference] [varchar](50) NULL,
	[PastDue] [decimal](16, 2) NOT NULL,
	[LastPaymentDate] [varchar](10) NULL,
	[NextDueDate] [varchar](10) NULL,
	[AsOfDate] [varchar](10) NULL,
	[LastCycles] [varchar](50) NULL,
 CONSTRAINT [PK_CbcAccountDetail] PRIMARY KEY CLUSTERED 
(
	[Id] ASC
)WITH (PAD_INDEX  = OFF, STATISTICS_NORECOMPUTE  = OFF, IGNORE_DUP_KEY = OFF, ALLOW_ROW_LOCKS  = ON, ALLOW_PAGE_LOCKS  = ON) ON [PRIMARY]
) ON [PRIMARY]
GO
SET ANSI_PADDING OFF
GO
/****** Object:  Table [dbo].[Branch]    Script Date: 12/19/2012 15:39:35 ******/
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
SET ANSI_PADDING ON
GO
CREATE TABLE [dbo].[Branch](
	[Id] [int] IDENTITY(1,1) NOT NULL,
	[Branch] [varchar](50) NULL,
	[Code] [varchar](4) NULL,
 CONSTRAINT [PK_Branch] PRIMARY KEY CLUSTERED 
(
	[Id] ASC
)WITH (PAD_INDEX  = OFF, STATISTICS_NORECOMPUTE  = OFF, IGNORE_DUP_KEY = OFF, ALLOW_ROW_LOCKS  = ON, ALLOW_PAGE_LOCKS  = ON) ON [PRIMARY]
) ON [PRIMARY]
GO
SET ANSI_PADDING OFF
GO
/****** Object:  Table [dbo].[BookmarkRole]    Script Date: 12/19/2012 15:39:35 ******/
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
SET ANSI_PADDING ON
GO
CREATE TABLE [dbo].[BookmarkRole](
	[Id] [int] IDENTITY(1,1) NOT NULL,
	[RoleCode] [varchar](3) NOT NULL,
	[BookmarkCode] [varchar](3) NOT NULL,
	[C] [int] NOT NULL,
	[R] [int] NOT NULL,
	[U] [int] NOT NULL,
	[D] [int] NOT NULL,
 CONSTRAINT [PK_UserAuthenBookmark] PRIMARY KEY CLUSTERED 
(
	[Id] ASC
)WITH (PAD_INDEX  = OFF, STATISTICS_NORECOMPUTE  = OFF, IGNORE_DUP_KEY = OFF, ALLOW_ROW_LOCKS  = ON, ALLOW_PAGE_LOCKS  = ON) ON [PRIMARY]
) ON [PRIMARY]
GO
SET ANSI_PADDING OFF
GO
/****** Object:  Table [dbo].[Bookmark]    Script Date: 12/19/2012 15:39:35 ******/
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
SET ANSI_PADDING ON
GO
CREATE TABLE [dbo].[Bookmark](
	[Id] [int] IDENTITY(1,1) NOT NULL,
	[Label] [nvarchar](50) NOT NULL,
	[BookmarkCode] [varchar](3) NOT NULL,
	[Href] [varchar](255) NOT NULL,
	[Icon] [varchar](150) NOT NULL,
	[Sequence] [int] NOT NULL,
	[MainCode] [varchar](3) NOT NULL,
 CONSTRAINT [PK_Bookmark] PRIMARY KEY CLUSTERED 
(
	[Id] ASC
)WITH (PAD_INDEX  = OFF, STATISTICS_NORECOMPUTE  = OFF, IGNORE_DUP_KEY = OFF, ALLOW_ROW_LOCKS  = ON, ALLOW_PAGE_LOCKS  = ON) ON [PRIMARY]
) ON [PRIMARY]
GO
SET ANSI_PADDING OFF
GO
/****** Object:  Table [dbo].[Village]    Script Date: 12/19/2012 15:39:36 ******/
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
SET ANSI_PADDING ON
GO
CREATE TABLE [dbo].[Village](
	[VillageID] [int] NOT NULL,
	[SID] [uniqueidentifier] NULL,
	[CommuneID] [int] NULL,
	[Code] [tinyint] NULL,
	[Village] [varchar](50) NULL
) ON [PRIMARY]
GO
SET ANSI_PADDING OFF
GO
/****** Object:  Table [dbo].[UserLogPlus]    Script Date: 12/19/2012 15:39:36 ******/
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
SET ANSI_PADDING ON
GO
CREATE TABLE [dbo].[UserLogPlus](
	[Id] [int] IDENTITY(1,1) NOT NULL,
	[DateTime] [datetime] NOT NULL,
	[UserId] [int] NULL,
	[LoginName] [varchar](50) NULL,
	[UserAgent] [varchar](200) NULL,
	[RemoteHost] [varchar](50) NULL,
	[RemoteAddress] [varchar](50) NULL,
	[SubBranchCode] [varchar](4) NULL,
	[BranchCode] [varchar](4) NULL,
	[Message] [varchar](250) NULL,
 CONSTRAINT [PK_UserLogMaster] PRIMARY KEY CLUSTERED 
(
	[Id] ASC
)WITH (PAD_INDEX  = OFF, STATISTICS_NORECOMPUTE  = OFF, IGNORE_DUP_KEY = OFF, ALLOW_ROW_LOCKS  = ON, ALLOW_PAGE_LOCKS  = ON) ON [PRIMARY]
) ON [PRIMARY]
GO
SET ANSI_PADDING OFF
GO
/****** Object:  Table [dbo].[UserLog]    Script Date: 12/19/2012 15:39:36 ******/
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
SET ANSI_PADDING ON
GO
CREATE TABLE [dbo].[UserLog](
	[Id] [int] IDENTITY(1,1) NOT NULL,
	[UserId] [int] NULL,
	[LoginName] [varchar](50) NULL,
	[DateTime] [datetime] NULL,
	[SubBranchCode] [varchar](4) NULL,
	[PcName] [varchar](50) NULL,
	[PcUsername] [varchar](50) NULL,
	[AppVersion] [varchar](5) NULL,
	[Message] [nvarchar](500) NULL,
	[BranchCode] [varchar](4) NULL,
 CONSTRAINT [PK_UserLog] PRIMARY KEY CLUSTERED 
(
	[Id] ASC
)WITH (PAD_INDEX  = OFF, STATISTICS_NORECOMPUTE  = OFF, IGNORE_DUP_KEY = OFF, ALLOW_ROW_LOCKS  = ON, ALLOW_PAGE_LOCKS  = ON) ON [PRIMARY]
) ON [PRIMARY]
GO
SET ANSI_PADDING OFF
GO
/****** Object:  Table [dbo].[UserAuthenRole]    Script Date: 12/19/2012 15:39:36 ******/
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
SET ANSI_PADDING ON
GO
CREATE TABLE [dbo].[UserAuthenRole](
	[Id] [int] IDENTITY(1,1) NOT NULL,
	[UserId] [int] NOT NULL,
	[RoleCode] [varchar](3) NOT NULL,
	[DateCreatedq] [datetime] NOT NULL,
	[CreatedBy] [int] NULL,
 CONSTRAINT [PK_UserAuthenRole] PRIMARY KEY CLUSTERED 
(
	[Id] ASC
)WITH (PAD_INDEX  = OFF, STATISTICS_NORECOMPUTE  = OFF, IGNORE_DUP_KEY = OFF, ALLOW_ROW_LOCKS  = ON, ALLOW_PAGE_LOCKS  = ON) ON [PRIMARY]
) ON [PRIMARY]
GO
SET ANSI_PADDING OFF
GO
/****** Object:  Table [dbo].[UserAuthen_Validity]    Script Date: 12/19/2012 15:39:36 ******/
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
SET ANSI_PADDING ON
GO
CREATE TABLE [dbo].[UserAuthen_Validity](
	[Id] [int] IDENTITY(1,1) NOT NULL,
	[UserId] [int] NOT NULL,
	[RequestDate] [datetime] NOT NULL,
	[Type] [varchar](1) NOT NULL,
	[StartDate] [datetime] NOT NULL,
	[EndDate] [datetime] NOT NULL,
	[Note] [varchar](50) NOT NULL,
	[CreatedBy] [int] NOT NULL,
	[CreatedDate] [datetime] NOT NULL,
	[CountEmailSent] [int] NOT NULL,
 CONSTRAINT [PK_UserAuthen_Validity] PRIMARY KEY CLUSTERED 
(
	[Id] ASC
)WITH (PAD_INDEX  = OFF, STATISTICS_NORECOMPUTE  = OFF, IGNORE_DUP_KEY = OFF, ALLOW_ROW_LOCKS  = ON, ALLOW_PAGE_LOCKS  = ON) ON [PRIMARY]
) ON [PRIMARY]
GO
SET ANSI_PADDING OFF
GO
/****** Object:  Table [dbo].[UserAuthen]    Script Date: 12/19/2012 15:39:35 ******/
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
SET ANSI_PADDING ON
GO
CREATE TABLE [dbo].[UserAuthen](
	[Id] [int] IDENTITY(1,1) NOT NULL,
	[Username] [varchar](50) NULL,
	[Password] [varbinary](500) NULL,
	[FName] [nvarchar](50) NOT NULL,
	[LName] [nvarchar](50) NOT NULL,
	[Sex] [varchar](1) NOT NULL,
	[SubBranchCode] [varchar](4) NULL,
	[BranchCode] [varchar](4) NULL,
	[LastLoginDate] [datetime] NULL,
	[Status] [varchar](3) NOT NULL,
	[Email] [varchar](50) NULL,
	[AppVersion] [varchar](5) NULL,
	[PcName] [varchar](50) NULL,
	[PcUsername] [varchar](50) NULL,
	[SecurityCode] [int] NULL,
	[DateCreated] [datetime] NULL,
	[CreatedBy] [int] NULL,
	[UpdatedBy] [int] NULL,
	[Note] [varchar](50) NULL,
	[UserType] [int] NOT NULL,
 CONSTRAINT [PK_User] PRIMARY KEY CLUSTERED 
(
	[Id] ASC
)WITH (PAD_INDEX  = OFF, STATISTICS_NORECOMPUTE  = OFF, IGNORE_DUP_KEY = OFF, ALLOW_ROW_LOCKS  = ON, ALLOW_PAGE_LOCKS  = ON) ON [PRIMARY]
) ON [PRIMARY]
GO
SET ANSI_PADDING OFF
GO
/****** Object:  Table [dbo].[UserAthenSubBranch]    Script Date: 12/19/2012 15:39:35 ******/
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
SET ANSI_PADDING ON
GO
CREATE TABLE [dbo].[UserAthenSubBranch](
	[Id] [int] IDENTITY(1,1) NOT NULL,
	[UserId] [int] NOT NULL,
	[SubBranchCode] [varchar](4) NOT NULL,
 CONSTRAINT [PK_UserAthenSubBranch] PRIMARY KEY CLUSTERED 
(
	[Id] ASC
)WITH (PAD_INDEX  = OFF, STATISTICS_NORECOMPUTE  = OFF, IGNORE_DUP_KEY = OFF, ALLOW_ROW_LOCKS  = ON, ALLOW_PAGE_LOCKS  = ON) ON [PRIMARY]
) ON [PRIMARY]
GO
SET ANSI_PADDING OFF
GO
/****** Object:  Table [dbo].[UsdRate]    Script Date: 12/19/2012 15:39:35 ******/
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
CREATE TABLE [dbo].[UsdRate](
	[Id] [int] IDENTITY(1,1) NOT NULL,
	[Date] [datetime] NULL,
	[UsdRate] [decimal](16, 2) NULL,
	[Note] [nvarchar](250) NULL,
 CONSTRAINT [PK_UsdRate] PRIMARY KEY CLUSTERED 
(
	[Id] ASC
)WITH (PAD_INDEX  = OFF, STATISTICS_NORECOMPUTE  = OFF, IGNORE_DUP_KEY = OFF, ALLOW_ROW_LOCKS  = ON, ALLOW_PAGE_LOCKS  = ON) ON [PRIMARY]
) ON [PRIMARY]
GO
/****** Object:  Table [dbo].[SubBranch]    Script Date: 12/19/2012 15:39:35 ******/
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
SET ANSI_PADDING ON
GO
CREATE TABLE [dbo].[SubBranch](
	[Id] [int] IDENTITY(1,1) NOT NULL,
	[SubBranch] [varchar](50) NULL,
	[Code] [varchar](4) NULL,
	[BranchId] [int] NULL,
 CONSTRAINT [PK_Location] PRIMARY KEY CLUSTERED 
(
	[Id] ASC
)WITH (PAD_INDEX  = OFF, STATISTICS_NORECOMPUTE  = OFF, IGNORE_DUP_KEY = OFF, ALLOW_ROW_LOCKS  = ON, ALLOW_PAGE_LOCKS  = ON) ON [PRIMARY]
) ON [PRIMARY]
GO
SET ANSI_PADDING OFF
GO
/****** Object:  Table [dbo].[Role]    Script Date: 12/19/2012 15:39:35 ******/
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
SET ANSI_PADDING ON
GO
CREATE TABLE [dbo].[Role](
	[Id] [int] IDENTITY(1,1) NOT NULL,
	[RoleCode] [varchar](3) NOT NULL,
	[Description] [nvarchar](250) NOT NULL,
	[DateCreated] [datetime] NOT NULL,
	[CreatedBy] [int] NULL,
 CONSTRAINT [PK_Role] PRIMARY KEY CLUSTERED 
(
	[Id] ASC
)WITH (PAD_INDEX  = OFF, STATISTICS_NORECOMPUTE  = OFF, IGNORE_DUP_KEY = OFF, ALLOW_ROW_LOCKS  = ON, ALLOW_PAGE_LOCKS  = ON) ON [PRIMARY]
) ON [PRIMARY]
GO
SET ANSI_PADDING OFF
GO
/****** Object:  Table [dbo].[Province]    Script Date: 12/19/2012 15:39:35 ******/
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
SET ANSI_PADDING ON
GO
CREATE TABLE [dbo].[Province](
	[ProvinceID] [int] NULL,
	[SID] [uniqueidentifier] NULL,
	[GSID] [uniqueidentifier] NULL,
	[BSID] [uniqueidentifier] NULL,
	[Province] [varchar](50) NULL
) ON [PRIMARY]
GO
SET ANSI_PADDING OFF
GO
/****** Object:  Table [dbo].[Log]    Script Date: 12/19/2012 15:39:35 ******/
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
SET ANSI_PADDING ON
GO
CREATE TABLE [dbo].[Log](
	[Id] [int] IDENTITY(1,1) NOT NULL,
	[Date] [datetime] NULL,
	[Thread] [varchar](10) NULL,
	[Level] [varchar](5) NULL,
	[Logger] [varchar](500) NULL,
	[Message] [nvarchar](250) NULL,
	[Exception] [nvarchar](3000) NULL,
	[SubBranchCode] [varchar](4) NULL,
 CONSTRAINT [PK_Log] PRIMARY KEY CLUSTERED 
(
	[Id] ASC
)WITH (PAD_INDEX  = OFF, STATISTICS_NORECOMPUTE  = OFF, IGNORE_DUP_KEY = OFF, ALLOW_ROW_LOCKS  = ON, ALLOW_PAGE_LOCKS  = ON) ON [PRIMARY]
) ON [PRIMARY]
GO
SET ANSI_PADDING OFF
GO
/****** Object:  StoredProcedure [dbo].[krd_uservalidity_select]    Script Date: 12/19/2012 15:39:37 ******/
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
/****** Object:  StoredProcedure [dbo].[krd_cbcactiveaccount_insert]    Script Date: 12/19/2012 15:39:36 ******/
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
-- =============================================
-- Author:		Sovathena NETH
-- Create date: 05/July/2012
-- Description:	Insert CbcActiveAccount
-- =============================================
CREATE PROCEDURE [dbo].[krd_cbcactiveaccount_insert] 
	-- Add the parameters for the stored procedure here
	@CbcReportId int, @Creditor varchar(100), @ProductType varchar(5), 
	@AccountNumber varchar(50),	@Currency varchar(3), @Limit decimal(16,2), 
	@RestructureLoan varchar(5), @Tenure int, @InstallmentAmount decimal(16,2), 
	@SecurityType varchar(5), @OutstandingBalance decimal(16,2),@LastAmountPaid decimal(16,2), 
	@IssueDate varchar(10), @ExpiryDate varchar(10), @ClosedDate varchar(10), 
	@Status varchar(1), @PaymentFrequency varchar(1), @GroupReference varchar(50),
	@PastDue decimal(16,2), @LastPaymentDate varchar(10), @NextDueDate varchar(10), 
	@AsOfDate varchar(10), @LastCycles varchar(50)
AS

BEGIN

	INSERT INTO CbcActiveAccount(CbcReportId, Creditor, ProductType, AccountNumber, Currency,
	Limit, RestructureLoan, Tenure, InstallmentAmount, SecurityType, OutstandingBalance,
	LastAmountPaid, IssueDate, ExpiryDate, ClosedDate, Status, PaymentFrequency, GroupReference,
	PastDue, LastPaymentDate, NextDueDate, AsOfDate, LastCycles)
	VALUES(@CbcReportId, @Creditor, @ProductType, @AccountNumber, @Currency,
	@Limit, @RestructureLoan, @Tenure, @InstallmentAmount, @SecurityType, @OutstandingBalance,
	@LastAmountPaid, @IssueDate, @ExpiryDate, @ClosedDate, @Status, @PaymentFrequency, @GroupReference,
	@PastDue, @LastPaymentDate, @NextDueDate, @AsOfDate, @LastCycles)
		
	SELECT @@IDENTITY
END
GO
/****** Object:  StoredProcedure [dbo].[krd_branch_select_star]    Script Date: 12/19/2012 15:39:36 ******/
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
-- =============================================
-- Author:		Sovathena NETH
-- Create date: 23/06/2012
-- Description:	query Branch
-- =============================================
CREATE PROCEDURE [dbo].[krd_branch_select_star] 
	-- Add the parameters for the stored procedure here
	
AS

BEGIN

	SELECT 0 Id, '*' Code, '(All branches)' [Description]
	UNION
	SELECT Id, Code, Branch [Description] FROM Branch
END
GO
/****** Object:  StoredProcedure [dbo].[krd_branch_select_by_sub_cd]    Script Date: 12/19/2012 15:39:36 ******/
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
-- =============================================
-- Author:		Sovathena NETH
-- Create date: 23/06/2012
-- Description:	query Branch
-- =============================================
CREATE PROCEDURE [dbo].[krd_branch_select_by_sub_cd] 
	-- Add the parameters for the stored procedure here
	@sub_cd varchar(30)
	
	
AS

BEGIN

	IF @sub_cd = '*'
	BEGIN
		SELECT 0 Id, '*' Code, '(All branches)' [Description]
	END
	ELSE
	BEGIN
		SELECT br.Id, br.Code, br.Branch [Description] FROM Branch br
		INNER JOIN SubBranch sub ON br.Id = sub.BranchId
		WHERE sub.Code = @sub_cd 
	END
END
GO
/****** Object:  StoredProcedure [dbo].[krd_branch_select_by_sub]    Script Date: 12/19/2012 15:39:36 ******/
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
-- =============================================
-- Author:		Sovathena NETH
-- Create date: 23/06/2012
-- Description:	query Branch
-- =============================================
CREATE PROCEDURE [dbo].[krd_branch_select_by_sub] 
	-- Add the parameters for the stored procedure here
	@subbrach varchar(30)
	
	
AS

BEGIN

	IF @subbrach = 'All subs'
	BEGIN
		SELECT 0 Id, '*' Code, '(All branches)' [Description]
	END
	ELSE
	BEGIN
		SELECT br.Id, br.Code, br.Branch [Description] FROM Branch br
		INNER JOIN SubBranch sub ON br.Id = sub.BranchId
		WHERE sub.SubBranch = @subbrach 
	END
END
GO
/****** Object:  StoredProcedure [dbo].[krd_branch_select]    Script Date: 12/19/2012 15:39:36 ******/
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
-- =============================================
-- Author:		Sovathena NETH
-- Create date: 23/06/2012
-- Description:	query Branch
-- =============================================
CREATE PROCEDURE [dbo].[krd_branch_select] 
	-- Add the parameters for the stored procedure here
	
AS

BEGIN

	SELECT 0 Id, '' Code, 'All' [Description]
	UNION
	SELECT Id, Code, Branch [Description] FROM Branch
END
GO
/****** Object:  StoredProcedure [dbo].[krd_bookmark_select_sub]    Script Date: 12/19/2012 15:39:36 ******/
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
-- =============================================
-- Author:		Sovathena NETH
-- Create date: 13/June/2012
-- Description:	Get Sub Bookmarks
-- =============================================
CREATE PROCEDURE [dbo].[krd_bookmark_select_sub] 
	-- Add the parameters for the stored procedure here
	@user_id int, @main_code varchar(3)
AS

BEGIN
	DECLARE @role varchar(3)
	SELECT @role = RoleCode FROM UserAuthenRole WHERE UserId = @user_id
	SET @role = ISNULL(@role,'USR')
	
	IF @role = 'ADM'
	BEGIN
		SELECT *, 1 C, 1 R, 1 U, 1 D FROM Bookmark WHERE MainCode = @main_code ORDER BY Sequence
	END
	ELSE
	BEGIN
		SELECT bm.*, br.C, br.R, br.U, br.D 
		FROM Bookmark bm INNER JOIN BookmarkRole br ON bm.BookmarkCode = br.BookmarkCode
		WHERE br.RoleCode = @role AND bm.MainCode = @main_code
		ORDER BY Sequence
	END
END
GO
/****** Object:  StoredProcedure [dbo].[krd_bookmark_select]    Script Date: 12/19/2012 15:39:36 ******/
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
-- =============================================
-- Author:		Sovathena NETH
-- Create date: 03/Apr/2012
-- Description:	Authenticate
-- =============================================
CREATE PROCEDURE [dbo].[krd_bookmark_select] 
	-- Add the parameters for the stored procedure here
	@user_id int
AS

BEGIN
	DECLARE @role varchar(3)
	SELECT @role = RoleCode FROM UserAuthenRole WHERE UserId = @user_id
	SET @role = ISNULL(@role,'USR')
	
	IF @role = 'ADM'
	BEGIN
		SELECT *, 1 C, 1 R, 1 U, 1 D FROM Bookmark WHERE MainCode = '' ORDER BY Sequence
	END
	ELSE
	BEGIN
		SELECT bm.*, br.C, br.R, br.U, br.D 
		FROM Bookmark bm INNER JOIN BookmarkRole br ON bm.BookmarkCode = br.BookmarkCode
		WHERE br.RoleCode = @role AND MainCode = ''
		ORDER BY Sequence
	END
END
GO
/****** Object:  UserDefinedFunction [dbo].[fn_userauthen_get_email]    Script Date: 12/19/2012 15:39:37 ******/
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
-- =============================================
-- Author:		<Sovathena NETH>
-- Create date: <8 May 2012>
-- Description:	<Get User Email by Id>
-- =============================================
CREATE FUNCTION [dbo].[fn_userauthen_get_email]
(
	-- Add the parameters for the function here
	@UserId  int
)
RETURNS varchar(50)
AS
BEGIN
	
	DECLARE @email varchar(50)
	SELECT  @email = Email FROM UserAuthen WHERE Id = @UserId
	SET @email = ISNULL(@email,'')
	RETURN @email
END
GO
/****** Object:  UserDefinedFunction [dbo].[fn_get_sub_description]    Script Date: 12/19/2012 15:39:37 ******/
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
-- =============================================
-- Author:		<Sovathena NETH>
-- Create date: <7 July 2012>
-- Description:	<Get Sub Branch Description>
-- =============================================
CREATE FUNCTION [dbo].[fn_get_sub_description]
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
		SELECT @description = SubBranch FROM SubBranch WHERE Code = @code
	END
	
	RETURN @description
END
GO
/****** Object:  UserDefinedFunction [dbo].[fn_get_branch_id]    Script Date: 12/19/2012 15:39:37 ******/
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
/****** Object:  UserDefinedFunction [dbo].[fn_get_branch_description]    Script Date: 12/19/2012 15:39:37 ******/
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
/****** Object:  UserDefinedFunction [dbo].[fn_do_authentication]    Script Date: 12/19/2012 15:39:37 ******/
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
-- =============================================
-- Author:		<Sovathena NETH>
-- Create date: <24 Apr 2012>
-- Description:	<Do authentication>
-- Id > 0 means authenticated
-- Id = -1 means invalid username or password
-- Id = -2 means the user has expired or been disabled.
-- =============================================
CREATE FUNCTION [dbo].[fn_do_authentication]
(
	-- Add the parameters for the function here
	@username  varchar(50),
	@psw  varchar(50)
)
RETURNS INT
AS
BEGIN
	
	DECLARE @user_id INT, @now DATE
	
	SET @now = GETDATE()

	SELECT @user_id = usr.Id FROM UserAuthen usr
	WHERE RTRIM(LTRIM(usr.Username)) = @username 
	AND PWDCOMPARE(@psw ,usr.[Password]) = 1
	AND usr.Status = 'A'
	
	SET @user_id = ISNULL(@user_id,-1)
	-- Check user type and corresponding validity
	-- Permanent vs Acting
	IF @user_id > 0
	BEGIN
		DECLARE @usr_type int
		SELECT @usr_type = UserType FROM UserAuthen WHERE Id = @user_id
		IF @usr_type = 0
		BEGIN
			IF EXISTS (SELECT * FROM UserAuthen_Validity WHERE UserId=@user_id AND Type='D' AND @now >= CONVERT(DATE, StartDate) AND @now <= CONVERT(DATE, EndDate))
			BEGIN
				SET @user_id = -2
			END
		END
		ELSE
		BEGIN
			IF NOT EXISTS (SELECT * FROM UserAuthen_Validity WHERE UserId=@user_id AND Type='E' AND @now >= CONVERT(DATE, StartDate) AND @now <= CONVERT(DATE, EndDate))
			BEGIN
				SET @user_id = -2
			END
		END
	END	
	
	RETURN @user_id 	
END
GO
/****** Object:  StoredProcedure [dbo].[krd_usdrate_select_top]    Script Date: 12/19/2012 15:39:37 ******/
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
/****** Object:  StoredProcedure [dbo].[krd_update_cbc_report_num_active_acc]    Script Date: 12/19/2012 15:39:37 ******/
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
/****** Object:  StoredProcedure [dbo].[krd_subbranch_select_star_by_branch_cd]    Script Date: 12/19/2012 15:39:36 ******/
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
-- =============================================
-- Author:		Sovathena NETH
-- Create date: 23/06/2012
-- Description:	query Branch
-- =============================================
CREATE PROCEDURE [dbo].[krd_subbranch_select_star_by_branch_cd] 
	-- Add the parameters for the stored procedure here
	@branch_cd varchar(2)
AS

BEGIN
	SELECT 0 Id, '*' Code, '(All subs)' [Description]
	UNION
	SELECT sub.Id Id, sub.Code Code, sub.SubBranch [Description]
	FROM SubBranch sub
	INNER JOIN Branch br ON br.Id = sub.BranchId 
	WHERE @branch_cd = '*' OR br.Code = @branch_cd
END
GO
/****** Object:  StoredProcedure [dbo].[krd_subbranch_select_star]    Script Date: 12/19/2012 15:39:36 ******/
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
-- =============================================
-- Author:		Sovathena NETH
-- Create date: 23/06/2012
-- Description:	query Branch
-- =============================================
CREATE PROCEDURE [dbo].[krd_subbranch_select_star] 
	-- Add the parameters for the stored procedure here
	@branch_desc varchar(30)
AS

BEGIN
	SELECT 0 Id, '*' Code, '(All subs)' [Description]
	UNION
	SELECT sub.Id Id, sub.Code Code, sub.SubBranch [Description]
	FROM SubBranch sub
	INNER JOIN Branch br ON br.Id = sub.BranchId 
	WHERE LEN(@branch_desc) = 0 OR br.Branch = @branch_desc
END
GO
/****** Object:  StoredProcedure [dbo].[krd_subbranch_select]    Script Date: 12/19/2012 15:39:36 ******/
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
-- =============================================
-- Author:		Sovathena NETH
-- Create date: 23/06/2012
-- Description:	query Branch
-- =============================================
CREATE PROCEDURE [dbo].[krd_subbranch_select] 
	-- Add the parameters for the stored procedure here
	@branch_id int
AS

BEGIN
	
	DECLARE @superCode varchar(2), @superDesc varchar(50)
	SELECT @superCode = Code, @superDesc = Branch FROM Branch WHERE Id = @branch_id
	SET @superCode = ISNULL(@superCode,'')
	SET @superDesc = ISNULL(@superDesc, 'All')
	
	SELECT 0 Id, '' Code, 'All' [Description], @branch_id SuperId, @superCode SuperCode, 
	@superDesc SuperDescription
	UNION
	SELECT sub.Id Id, sub.Code Code, sub.SubBranch [Description], 
	sub.BranchId SuperId, br.Code SuperCode, br.Branch SuperDescription 
	FROM SubBranch sub
	INNER JOIN Branch br ON br.Id = sub.BranchId 
	WHERE (@branch_id = 0 OR BranchId = @branch_id)
	
END
GO
/****** Object:  StoredProcedure [dbo].[krd_NumLoanBySubBranchReport]    Script Date: 12/19/2012 15:39:36 ******/
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
--Author:		Vouthea Oun
--Date	:		2012-07-23
--Description:	For report Number of Loan by Sub Branch
CREATE Procedure [dbo].[krd_NumLoanBySubBranchReport] 
@FromDate Date, @ToDate Date,
@SubBranch Nvarchar(50), @Branch Nvarchar(50), @Decision Int
As
Begin
Select  Branch.Code as Branch,CbcReport.SubBranchCode + ' ' + SubBranch.SubBranch as SubBranch,
Sum(Case When [Action]='A' Then 1 Else 0 End) As [Standard],
SUM(Case When [Action]='L' Then 1 Else 0 End) As Lite,
Sum(Case When [Action]='A' Then 1 Else 0 End) * 3 As [AmtStandard],
SUM(Case When [Action]='L' Then 1 Else 0 End) * 0.2 As [AmtLite]
From CbcReport Inner Join Branch On CbcReport.BranchCode=Branch.Code 
Inner Join SubBranch On SubBranch.Code=CbcReport.SubBranchCode
Where Status='OK' And (Convert(Date,ReportDate) Between @FromDate And @ToDate) And
(LEN(@SubBranch)=0 Or SubBranchCode=@SubBranch) And 
(LEN(@Branch)=0 Or Branch.Code=@Branch) And
(@Decision=2 /*All Decision*/ Or Decision=@Decision)
Group By Branch.Code,CbcReport.SubBranchCode + ' ' + SubBranch.SubBranch
End
GO
/****** Object:  StoredProcedure [dbo].[krd_log_select]    Script Date: 12/19/2012 15:39:36 ******/
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
-- =============================================
-- Author:		Sovathena NETH
-- Create date: 27/Apr/2012
-- Description:	Select Log
-- =============================================
CREATE PROCEDURE [dbo].[krd_log_select] 
	-- Add the parameters for the stored procedure here
	@level varchar(5), @logger varchar(500), @message nvarchar(250), 
	@exception nvarchar(3000), @subcode varchar(4), @duration int
	
AS

BEGIN

	SELECT * FROM [Log] 
	WHERE (LEN(@level) = 0 OR [Level] = @level) 
	AND (LEN(@logger) = 0 OR  Logger like '%' + @logger +'%')
	AND (LEN(@message) = 0 OR [Message] like '%'+ @message +'%') 
	AND (LEN(@exception) = 0 OR Exception like '%'+ @exception +'%')
	AND (LEN(@subcode) = 0 OR SubBranchCode = @subcode)
	AND (@duration = 0 OR DATEDIFF(DAY, [Date], GETDATE()) <= @duration)
	
END
GO
/****** Object:  StoredProcedure [dbo].[krd_log_insert]    Script Date: 12/19/2012 15:39:36 ******/
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
-- =============================================
-- Author:		Sovathena NETH
-- Create date: 27/Apr/2012
-- Description:	Insert Log
-- =============================================
CREATE PROCEDURE [dbo].[krd_log_insert] 
	-- Add the parameters for the stored procedure here
	@thread varchar(10), @level varchar(5), @logger varchar(500),
	@message nvarchar(250), @exception nvarchar(3000), @subcode varchar(4)
AS

BEGIN

	INSERT INTO Log(Thread, [Level], Logger, [Message], Exception, SubBranchCode, [Date])
	VALUES(@thread, @level, @logger, @message, @exception, @subcode, GETDATE())
	
	SELECT @@IDENTITY
END
GO
/****** Object:  StoredProcedure [dbo].[krd_get_response_xml]    Script Date: 12/19/2012 15:39:36 ******/
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
/****** Object:  StoredProcedure [dbo].[krd_get_location]    Script Date: 12/19/2012 15:39:36 ******/
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
-- =============================================
-- Author:		Sovathena NETH
-- Create date: 20/Apr/2012
-- Description:	Get Location: province, district, commune, and village based on provided code
-- =============================================
CREATE PROCEDURE [dbo].[krd_get_location] 
	-- Add the parameters for the stored procedure here
	@pro_code  int, @dis_code int, @com_code int, @vil_code int
AS

BEGIN
	IF @pro_code > 0 AND @dis_code > 0 AND @com_code > 0 AND @vil_code > 0
	BEGIN
		SELECT pro.Province, dis.District, com.Commune, vil.Village 
		FROM Province pro
		INNER JOIN District dis ON pro.ProvinceID = dis.ProvinceID
		INNER JOIN Commune com ON dis.DistrictID = com.DistrictID
		INNER JOIN Village vil ON com.CommuneID = vil.CommuneID
		WHERE pro.ProvinceID = @pro_code AND dis.Code = @dis_code AND com.Code = @com_code AND vil.Code = @vil_code
	END
	ELSE IF @pro_code > 0 AND @dis_code > 0 AND @com_code > 0 AND @vil_code = 0
	BEGIN
		SELECT pro.Province, dis.District, com.Commune, '' Village
		FROM Province pro
		INNER JOIN District dis ON pro.ProvinceID = dis.ProvinceID
		INNER JOIN Commune com ON dis.DistrictID = com.DistrictID
		WHERE pro.ProvinceID = @pro_code AND dis.Code = @dis_code AND com.Code = @com_code
	END
	ELSE IF @pro_code > 0 AND @dis_code > 0 AND @com_code = 0 AND @vil_code = 0
	BEGIN
		SELECT pro.Province, dis.District, '' Commune, '' Village
		FROM Province pro
		INNER JOIN District dis ON pro.ProvinceID = dis.ProvinceID
		WHERE pro.ProvinceID = @pro_code AND dis.Code = @dis_code
	END
	ELSE IF @pro_code > 0 AND @dis_code = 0 AND @com_code = 0 AND @vil_code = 0
	BEGIN
		SELECT pro.Province, '' District, '' Commune, '' Village
		FROM Province pro
		WHERE pro.ProvinceID = @pro_code
	END
END
GO
/****** Object:  StoredProcedure [dbo].[krd_FeeIncomeExpReport]    Script Date: 12/19/2012 15:39:36 ******/
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
--Author	: Vouthea Oun
--Date		: 2012-07-20
--Desc		: For Fee Income and Expense of Cbc Request
CREATE Proc [dbo].[krd_FeeIncomeExpReport] 
@FromDate Date, @ToDate Date, @Decision int, @Branch varchar(50),@SubBranch varchar(50)
As
Begin

select BranchCode, SubBranchCode + ' ' + SubBranch.SubBranch as SubBranch,
SUM(Case When Decision >=0 Then Case When Currency='USD' Then FeeKredit Else (FeeKredit/4000) End Else 0 End ) As IncomeFee,
SUM(FeeCbc) as ExpenseFee,
SUM(Case When Decision >=0 Then Case When Currency='USD' Then FeeKredit Else (FeeKredit/4000) End Else 0 End )- SUM(FeeCbc) as NetBalance
from CbcReport 
Inner Join SubBranch On SubBranchCode=SubBranch.Code
Where Status='OK' And (LEN(@Branch)=0 Or BranchCode=@Branch) And (LEN(@SubBranch)=0 Or SubBranchCode=@SubBranch)
And CONVERT(Date,ReportDate) Between @FromDate And @ToDate
Group By BranchCode,SubBranchCode + ' ' + SubBranch.SubBranch Order By BranchCode 

End
GO
/****** Object:  StoredProcedure [dbo].[krd_enquiry_save]    Script Date: 12/19/2012 15:39:36 ******/
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
-- =============================================
-- Author:		Sovathena NETH
-- Create date: 25/Apr/2012
-- Description:	Save Enquiry to CBC Report Table
-- =============================================
CREATE PROCEDURE [dbo].[krd_enquiry_save] 
	-- Add the parameters for the stored procedure here
	@res_xml ntext, @ref_no varchar(50), @enq_type varchar(5),
	@prd_type varchar(5), @acc_type varchar(5), @amount decimal(16,2), 
	@currency varchar(10), @applicant int, @status varchar(5), @action varchar(2),
	@user_id  int, @sub_code varchar(4), @req_xml ntext, @loan_id char(15), 
	@name nvarchar(100), @id_num nvarchar(50),
	@num_active_acc int, 
	@fee_kredit decimal(16,2), @fee_cbc decimal(16,2)
AS

BEGIN

	DECLARE @branch_code varchar(4), @decision int
	SET @decision = 0
	SELECT @branch_code = br.Code
	FROM Branch br 
	INNER JOIN SubBranch sub ON br.Id = sub.BranchId
	WHERE sub.Code = @sub_code
	
	SET @branch_code = ISNULL(@branch_code,'')
	 
	--SELECT @branch_code
	
	IF @status <> 'OK'
	BEGIN
		SET @decision = -3
	END
	
	INSERT INTO CbcReport(RefNumber, [Action], ReportDate, EnquiryType, ProductType, AccountType,
	Amount, Currency, Applicant, Request, Response, UserId, SubBranchCode, [Status], LoanId, Name, IdNumber, NumActiveAcc, FeeKredit, FeeCbc, BranchCode, Decision)
	VALUES(@ref_no, @action, GETDATE(), @enq_type, @prd_type, @acc_type,
	@amount, @currency, @applicant, @req_xml, @res_xml,	@user_id, @sub_code, @status, @loan_id, @name, @id_num, @num_active_acc, @fee_kredit, @fee_cbc, @branch_code, @decision)
	
	SELECT @@IDENTITY
END
GO
/****** Object:  StoredProcedure [dbo].[krd_cbcreport_summary]    Script Date: 12/19/2012 15:39:36 ******/
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
-- =============================================
-- Author:		Sovathena NETH
-- Create date: 13/08/2012
-- Description:	CBC Report Sumarry: Total Lite, Standard, Decision, Fee, Income & Expense
-- =============================================
CREATE PROCEDURE [dbo].[krd_cbcreport_summary] 
	-- Add the parameters for the stored procedure here
	
	@filter varchar(50), @name nvarchar(50), @branch varchar(4), @sub_branch varchar(4), @report_type varchar(5),
	@status_cd varchar(2), @decision int, @from_amount decimal(16,2), @to_amount decimal(16,2),
	@curreny varchar(3), @from_date varchar(10), @to_date varchar(10)
	
AS

BEGIN
	
	DECLARE @status varchar(5)
	--SET @status = @status_cd
	SELECT @status = CASE WHEN @status_cd = 'OK' THEN 'OK' 
	WHEN @status_cd = 'ER' THEN 'ERROR'
	WHEN @status_cd = 'PA' THEN 'PARSE' 
	ELSE '' END
	
	-- COUNT Report GROUP BY Action: Standard vs Lite
	DECLARE @count_std int, @count_lite int, @count_pending int, @count_approved int, 
	@count_rejected int, @count_cancel int, @std_fee_cbc decimal(16,2), @lite_fee_cbc decimal(16,2),
	@std_fee_krd decimal(16,2)
	
	IF OBJECT_ID('tempdb..#tmp_tbl') IS NOT NULL
	BEGIN
		drop table #tmp_tbl
	END
	
	SELECT rpt.Action, COUNT(Action) Total,  SUM(CASE rpt.Decision WHEN 0 THEN 1 ELSE 0 END ) Pending, 
	SUM(CASE rpt.Decision WHEN 1 THEN 1 ELSE 0 END ) Approved,
	SUM(CASE rpt.Decision WHEN -1 THEN 1 ELSE 0 END ) Rejected,
	SUM(CASE rpt.Decision WHEN -2 THEN 1 ELSE 0 END ) Cancel   
	INTO #tmp_tbl
	FROM CbcReport rpt 
	LEFT OUTER JOIN SubBranch sub ON rpt.SubBranchCode = sub.Code
	LEFT OUTER JOIN Branch bra ON rpt.BranchCode = bra.Code
	WHERE (LEN(@name)=0 OR Name like  '%' + @name + '%' COLLATE SQL_Latin1_General_CP850_BIN2 OR	RefNumber like '%' + @filter + '%')
	AND (LEN(@branch) = 0 OR rpt.BranchCode = @branch)
	AND (LEN(@sub_branch) = 0 OR rpt.SubBranchCode =  @sub_branch)
	AND (LEN(@report_type)=0 OR rpt.Action = @report_type)
	AND (LEN(@status)=0 OR rpt.Status = @status)
	AND (@decision = 2 OR rpt.Decision = @decision)
	AND (@from_amount=0 OR @to_amount = 0 OR rpt.Amount BETWEEN @from_amount AND @to_amount)
	AND (LEN(@curreny)=0 OR rpt.Currency=@curreny)
	AND (LEN(@from_date) = 0 OR LEN(@to_date) = 0 OR rpt.ReportDate BETWEEN @from_date AND DATEADD(SECOND,86399,@to_date))
	GROUP BY rpt.Action
	
	-- Get Stardard and Lite
	SELECT @count_std = SUM(CASE Action WHEN 'A' THEN Total ELSE 0 END), @count_lite = SUM(CASE Action WHEN 'L' THEN Total ELSE 0 END)  FROM #tmp_tbl
	-- Pending, Approved, Rejected, Cancel
	SELECT @count_pending = SUM(Pending), @count_approved = SUM(Approved), @count_rejected = SUM(Rejected), @count_cancel = SUM(Cancel) FROM #tmp_tbl
	
	
	SELECT @std_fee_krd = SUM(Case When Decision >=0 Then Case When Currency='USD' Then FeeKredit Else (FeeKredit/4000) End Else 0 End ),
	@std_fee_cbc = SUM(CASE rpt.Action WHEN 'A' THEN FeeCbc ELSE 0 END), @lite_fee_cbc = SUM(CASE rpt.Action WHEN 'L' THEN FeeCbc ELSE 0 END)
	FROM CbcReport rpt 
	LEFT OUTER JOIN SubBranch sub ON rpt.SubBranchCode = sub.Code
	LEFT OUTER JOIN Branch bra ON rpt.BranchCode = bra.Code
	WHERE (LEN(@name)=0 OR Name like  '%' + @name + '%' COLLATE SQL_Latin1_General_CP850_BIN2 OR	RefNumber like '%' + @filter + '%')
	AND (LEN(@branch) = 0 OR rpt.BranchCode = @branch)
	AND (LEN(@sub_branch) = 0 OR rpt.SubBranchCode =  @sub_branch)
	AND (LEN(@report_type)=0 OR rpt.Action = @report_type)
	AND (LEN(@status)=0 OR rpt.Status = @status)
	AND (@decision = 2 OR rpt.Decision = @decision)
	AND (@from_amount=0 OR @to_amount = 0 OR rpt.Amount BETWEEN @from_amount AND @to_amount)
	AND (LEN(@curreny)=0 OR rpt.Currency=@curreny)
	AND (LEN(@from_date) = 0 OR LEN(@to_date) = 0 OR CONVERT(DATE,rpt.ReportDate) BETWEEN @from_date AND @to_date)
	
	-- Query Result	
	SELECT @count_std CountStd, @count_lite CountLite,
	@count_pending CountPending, @count_approved CountApproved, @count_rejected CountRejected,
	@count_cancel CountCancel, @std_fee_krd TotalKreditFee, @std_fee_cbc TotalCbcFeeStd, @lite_fee_cbc TotalCbcFeeLite
	
END
GO
/****** Object:  StoredProcedure [dbo].[krd_cbcreport_select_total_rpt_by_day_jasper]    Script Date: 12/19/2012 15:39:36 ******/
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
-- =============================================
-- Author:		Sovathena NETH
-- Create date: 26/07/2012
-- Description:	query total (Standard & Lite) for each Daily Report 
-- =============================================
CREATE PROCEDURE [dbo].[krd_cbcreport_select_total_rpt_by_day_jasper] 
	-- Add the parameters for the stored procedure here
	
	@filter varchar(50), @name nvarchar(50), @branch varchar(4), @sub_branch varchar(4), @report_type varchar(5),
	@status_cd varchar(2), @decision int, @from_amount decimal(16,2), @to_amount decimal(16,2),
	@curreny varchar(3), @from_date varchar(10), @to_date varchar(10)
	
AS

BEGIN
	
	DECLARE @status varchar(5)
	--SET @status = @status_cd
	SELECT @status = CASE WHEN @status_cd = 'OK' THEN 'OK' 
	WHEN @status_cd = 'ER' THEN 'ERROR'
	WHEN @status_cd = 'PA' THEN 'PARSE' 
	ELSE '' END
		
	SELECT CONVERT(VARCHAR(6), CONVERT(DATE,rpt.ReportDate), 6) Label, CONVERT(DATE,rpt.ReportDate) Dt, 
	SUM(CASE rpt.Action WHEN 'A' THEN 1 ELSE 0 END) TotalStd,
	SUM(CASE rpt.Action WHEN 'L' THEN 1 ELSE 0 END) TotalLite 
	FROM CbcReport rpt
	LEFT OUTER JOIN SubBranch sub ON rpt.SubBranchCode = sub.Code
	LEFT OUTER JOIN Branch bra ON rpt.BranchCode = bra.Code
	WHERE (LEN(@name)=0 OR Name like  '%' + @name + '%' COLLATE SQL_Latin1_General_CP850_BIN2 OR	RefNumber like '%' + @filter + '%')
	AND (LEN(@branch) = 0 OR rpt.BranchCode = @branch)
	AND (LEN(@sub_branch) = 0 OR rpt.SubBranchCode =  @sub_branch)
	AND (LEN(@report_type)=0 OR rpt.Action = @report_type)
	AND (LEN(@status)=0 OR rpt.Status = @status)
	AND (@decision = 2 OR rpt.Decision = @decision)
	AND (@from_amount=0 OR @to_amount = 0 OR rpt.Amount BETWEEN @from_amount AND @to_amount)
	AND (LEN(@curreny)=0 OR rpt.Currency=@curreny)
	AND (LEN(@from_date) = 0 OR LEN(@to_date) = 0 OR rpt.ReportDate BETWEEN @from_date AND DATEADD(SECOND,86399,@to_date))
	GROUP BY CONVERT(Date,rpt.ReportDate)
	ORDER BY Dt ASC
		
END
GO
/****** Object:  StoredProcedure [dbo].[krd_cbcreport_select_total_rpt_by_day]    Script Date: 12/19/2012 15:39:36 ******/
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
-- =============================================
-- Author:		Sovathena NETH
-- Create date: 26/07/2012
-- Description:	query total (Standard & Lite) for each Daily Report 
-- =============================================
CREATE PROCEDURE [dbo].[krd_cbcreport_select_total_rpt_by_day] 
	-- Add the parameters for the stored procedure here
	
	@filter varchar(50), @name nvarchar(50), @branch varchar(4), @sub_branch varchar(4), @report_type varchar(5),
	@status_cd varchar(2), @decision int, @from_amount decimal(16,2), @to_amount decimal(16,2),
	@curreny varchar(3), @from_date varchar(10), @to_date varchar(10)
	
AS

BEGIN
	
	DECLARE @status varchar(5)
	--SET @status = @status_cd
	SELECT @status = CASE WHEN @status_cd = 'OK' THEN 'OK' 
	WHEN @status_cd = 'ER' THEN 'ERROR'
	WHEN @status_cd = 'PA' THEN 'PARSE' 
	ELSE '' END
		
	SELECT CONVERT(VARCHAR(6), CONVERT(DATE,rpt.ReportDate), 6) Label, CONVERT(DATE,rpt.ReportDate) Dt, 
	SUM(CASE rpt.Action WHEN 'A' THEN 1 ELSE 0 END) TotalStd,
	SUM(CASE rpt.Action WHEN 'L' THEN 1 ELSE 0 END) TotalLite 
	FROM CbcReport rpt
	LEFT OUTER JOIN SubBranch sub ON rpt.SubBranchCode = sub.Code
	LEFT OUTER JOIN Branch bra ON rpt.BranchCode = bra.Code
	WHERE (LEN(@name)=0 OR Name like  '%' + @name + '%' COLLATE SQL_Latin1_General_CP850_BIN2 OR	RefNumber like '%' + @filter + '%')
	AND (LEN(@branch) = 0 OR rpt.BranchCode = @branch)
	AND (LEN(@sub_branch) = 0 OR rpt.SubBranchCode =  @sub_branch)
	AND (LEN(@report_type)=0 OR rpt.Action = @report_type)
	AND (LEN(@status)=0 OR rpt.Status = @status)
	AND (@decision = 2 OR rpt.Decision = @decision)
	AND (@from_amount=0 OR @to_amount = 0 OR rpt.Amount BETWEEN @from_amount AND @to_amount)
	AND (LEN(@curreny)=0 OR rpt.Currency=@curreny)
	AND (LEN(@from_date) = 0 OR LEN(@to_date) = 0 OR rpt.ReportDate BETWEEN @from_date AND DATEADD(SECOND,86399,@to_date))
	GROUP BY CONVERT(Date,rpt.ReportDate)
	ORDER BY Dt ASC
		
END
GO
/****** Object:  StoredProcedure [dbo].[krd_cbcreport_select_total_rpt_by_branch]    Script Date: 12/19/2012 15:39:36 ******/
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
-- =============================================
-- Author:		Sovathena NETH
-- Create date: 15/08/2012
-- Description:	query total (Standard & Lite) for each location( Branch/Sub Branch)
-- =============================================
CREATE PROCEDURE [dbo].[krd_cbcreport_select_total_rpt_by_branch] 
	-- Add the parameters for the stored procedure here
	
	@filter varchar(50), @name nvarchar(50), @branch varchar(4), @sub_branch varchar(4), @report_type varchar(5),
	@status_cd varchar(2), @decision int, @from_amount decimal(16,2), @to_amount decimal(16,2),
	@curreny varchar(3), @from_date varchar(10), @to_date varchar(10)
	
AS

BEGIN
	
	DECLARE @status varchar(5)
	--SET @status = @status_cd
	SELECT @status = CASE WHEN @status_cd = 'OK' THEN 'OK' 
	WHEN @status_cd = 'ER' THEN 'ERROR'
	WHEN @status_cd = 'PA' THEN 'PARSE' 
	ELSE '' END
	
	IF LEN(@branch) = 0
	BEGIN	
		SELECT rpt.BranchCode Label, 
		SUM(CASE rpt.Action WHEN 'A' THEN 1 ELSE 0 END) TotalStd,
		SUM(CASE rpt.Action WHEN 'L' THEN 1 ELSE 0 END) TotalLite 
		FROM CbcReport rpt
		LEFT OUTER JOIN SubBranch sub ON rpt.SubBranchCode = sub.Code
		LEFT OUTER JOIN Branch bra ON rpt.BranchCode = bra.Code
		WHERE (LEN(@name)=0 OR Name like  '%' + @name + '%' COLLATE SQL_Latin1_General_CP850_BIN2 OR	RefNumber like '%' + @filter + '%')
		AND (LEN(@sub_branch) = 0 OR rpt.SubBranchCode =  @sub_branch)
		AND (LEN(@report_type)=0 OR rpt.Action = @report_type)
		AND (LEN(@status)=0 OR rpt.Status = @status)
		AND (@decision = 2 OR rpt.Decision = @decision)
		AND (@from_amount=0 OR @to_amount = 0 OR rpt.Amount BETWEEN @from_amount AND @to_amount)
		AND (LEN(@curreny)=0 OR rpt.Currency=@curreny)
		AND (LEN(@from_date) = 0 OR LEN(@to_date) = 0 OR rpt.ReportDate BETWEEN @from_date AND DATEADD(SECOND,86399,@to_date))
		GROUP BY rpt.BranchCode
	END
	ELSE
	BEGIN
		SELECT rpt.SubBranchCode + ' ' + sub.SubBranch Label, 
		SUM(CASE rpt.Action WHEN 'A' THEN 1 ELSE 0 END) TotalStd,
		SUM(CASE rpt.Action WHEN 'L' THEN 1 ELSE 0 END) TotalLite 
		FROM CbcReport rpt
		LEFT OUTER JOIN SubBranch sub ON rpt.SubBranchCode = sub.Code
		LEFT OUTER JOIN Branch bra ON rpt.BranchCode = bra.Code
		WHERE (LEN(@name)=0 OR Name like  '%' + @name + '%' COLLATE SQL_Latin1_General_CP850_BIN2 OR	RefNumber like '%' + @filter + '%')
		AND rpt.BranchCode = @branch
		AND (LEN(@sub_branch) = 0 OR rpt.SubBranchCode =  @sub_branch)
		AND (LEN(@report_type)=0 OR rpt.Action = @report_type)
		AND (LEN(@status)=0 OR rpt.Status = @status)
		AND (@decision = 2 OR rpt.Decision = @decision)
		AND (@from_amount=0 OR @to_amount = 0 OR rpt.Amount BETWEEN @from_amount AND @to_amount)
		AND (LEN(@curreny)=0 OR rpt.Currency=@curreny)
		AND (LEN(@from_date) = 0 OR LEN(@to_date) = 0 OR rpt.ReportDate BETWEEN @from_date AND DATEADD(SECOND,86399,@to_date))
		GROUP BY rpt.SubBranchCode + ' ' + sub.SubBranch
	END	
END
GO
/****** Object:  StoredProcedure [dbo].[krd_cbcreport_select_total_loan_by_branch]    Script Date: 12/19/2012 15:39:36 ******/
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
-- =============================================
-- Author:		Sovathena NETH
-- Create date: 15/08/2012
-- Description:	query total (Standard & Lite) for each location( Branch/Sub Branch)
-- =============================================
CREATE PROCEDURE [dbo].[krd_cbcreport_select_total_loan_by_branch] 
	-- Add the parameters for the stored procedure here
	
	@filter varchar(50), @name nvarchar(50), @branch varchar(4), @sub_branch varchar(4), @report_type varchar(5),
	@status_cd varchar(2), @decision int, @from_amount decimal(16,2), @to_amount decimal(16,2),
	@curreny varchar(3), @from_date varchar(10), @to_date varchar(10)
	
AS

BEGIN
	
	DECLARE @status varchar(5)
	--SET @status = @status_cd
	SELECT @status = CASE WHEN @status_cd = 'OK' THEN 'OK' 
	WHEN @status_cd = 'ER' THEN 'ERROR'
	WHEN @status_cd = 'PA' THEN 'PARSE' 
	ELSE '' END
	
	IF LEN(@branch) = 0
	BEGIN	
		SELECT rpt.BranchCode Label, 
		COUNT(rpt.Id) TotalLoan,
		SUM(
			CASE Currency WHEN 'USD' THEN rpt.Amount ELSE rpt.Amount / 4000 END
		) AS TotalAmount
		FROM CbcReport rpt
		LEFT OUTER JOIN SubBranch sub ON rpt.SubBranchCode = sub.Code
		LEFT OUTER JOIN Branch bra ON rpt.BranchCode = bra.Code
		WHERE (LEN(@name)=0 OR Name like  '%' + @name + '%' COLLATE SQL_Latin1_General_CP850_BIN2 OR	RefNumber like '%' + @filter + '%')
		AND (LEN(@sub_branch) = 0 OR rpt.SubBranchCode =  @sub_branch)
		AND (LEN(@report_type)=0 OR rpt.Action = @report_type)
		AND (LEN(@status)=0 OR rpt.Status = @status)
		AND (@decision = 2 OR rpt.Decision = @decision)
		AND (@from_amount=0 OR @to_amount = 0 OR rpt.Amount BETWEEN @from_amount AND @to_amount)
		AND (LEN(@curreny)=0 OR rpt.Currency=@curreny)
		AND (LEN(@from_date) = 0 OR LEN(@to_date) = 0 OR rpt.ReportDate BETWEEN @from_date AND DATEADD(SECOND,86399,@to_date))
		GROUP BY rpt.BranchCode
	END
	ELSE
	BEGIN
		SELECT rpt.SubBranchCode + ' ' + sub.SubBranch Label, 
		COUNT(rpt.Id) TotalLoan, 
		SUM(
			CASE Currency WHEN 'USD' THEN rpt.Amount ELSE rpt.Amount / 4000 END
		) AS TotalAmount
		FROM CbcReport rpt
		LEFT OUTER JOIN SubBranch sub ON rpt.SubBranchCode = sub.Code
		LEFT OUTER JOIN Branch bra ON rpt.BranchCode = bra.Code
		WHERE (LEN(@name)=0 OR Name like  '%' + @name + '%' COLLATE SQL_Latin1_General_CP850_BIN2 OR	RefNumber like '%' + @filter + '%')
		AND rpt.BranchCode = @branch
		AND (LEN(@sub_branch) = 0 OR rpt.SubBranchCode =  @sub_branch)
		AND (LEN(@report_type)=0 OR rpt.Action = @report_type)
		AND (LEN(@status)=0 OR rpt.Status = @status)
		AND (@decision = 2 OR rpt.Decision = @decision)
		AND (@from_amount=0 OR @to_amount = 0 OR rpt.Amount BETWEEN @from_amount AND @to_amount)
		AND (LEN(@curreny)=0 OR rpt.Currency=@curreny)
		AND (LEN(@from_date) = 0 OR LEN(@to_date) = 0 OR rpt.ReportDate BETWEEN @from_date AND DATEADD(SECOND,86399,@to_date))
		GROUP BY rpt.SubBranchCode + ' ' + sub.SubBranch
	END	
END
GO
/****** Object:  StoredProcedure [dbo].[krd_cbcreport_select_total_loan]    Script Date: 12/19/2012 15:39:36 ******/
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
-- =============================================
-- Author:		Sovathena NETH
-- Create date: 15/08/2012
-- Description:	query total (Standard & Lite) for each location( Branch/Sub Branch)
-- =============================================
CREATE PROCEDURE [dbo].[krd_cbcreport_select_total_loan] 
	-- Add the parameters for the stored procedure here
	
	@filter varchar(50), @name nvarchar(50), @branch varchar(4), @sub_branch varchar(4), @report_type varchar(5),
	@status_cd varchar(2), @decision int, @from_amount decimal(16,2), @to_amount decimal(16,2),
	@curreny varchar(3), @from_date varchar(10), @to_date varchar(10)
	
AS

BEGIN
	
		SELECT Currency Label,
		COUNT(rpt.Id) TotalLoan,
		SUM(
			CASE Currency WHEN 'USD' THEN rpt.Amount ELSE rpt.Amount / 4000 END
		) AS TotalAmount
		FROM CbcReport rpt
		LEFT OUTER JOIN SubBranch sub ON rpt.SubBranchCode = sub.Code
		LEFT OUTER JOIN Branch bra ON rpt.BranchCode = bra.Code
		WHERE (LEN(@name)=0 OR Name like  '%' + @name + '%' COLLATE SQL_Latin1_General_CP850_BIN2 OR	RefNumber like '%' + @filter + '%')
		AND (LEN(@branch)=0 OR rpt.BranchCode = @branch)	
		AND (LEN(@sub_branch) = 0 OR rpt.SubBranchCode =  @sub_branch)
		AND (LEN(@report_type)=0 OR rpt.Action = @report_type)
		AND rpt.Status = 'OK'
		AND (@decision = 2 OR rpt.Decision = @decision)
		AND (@from_amount=0 OR @to_amount = 0 OR rpt.Amount BETWEEN @from_amount AND @to_amount)
		AND (LEN(@curreny)=0 OR rpt.Currency=@curreny)
		AND (LEN(@from_date) = 0 OR LEN(@to_date) = 0 OR CONVERT(DATE,rpt.ReportDate) BETWEEN @from_date AND @to_date)
	GROUP BY Currency	

END
GO
/****** Object:  StoredProcedure [dbo].[krd_cbcreport_select_income_expense]    Script Date: 12/19/2012 15:39:36 ******/
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
-- =============================================
-- Author:		Sovathena NETH
-- Create date: 26/07/2012
-- Description:	query total (Standard & Lite) for each Daily Report 
-- =============================================
CREATE PROCEDURE [dbo].[krd_cbcreport_select_income_expense] 
	-- Add the parameters for the stored procedure here
	
	@FromDate Date, @ToDate Date, @Decision int, @Branch varchar(50),@SubBranch varchar(50)
	
AS

BEGIN
	
	select BranchCode, SubBranchCode + ' ' + SubBranch.SubBranch as SubBranch,
	'Income' [Type], 
	SUM(Case When Decision >=0 Then Case When Currency='USD' Then FeeKredit Else (FeeKredit/4000) End Else 0 End ) As Fee
	from CbcReport 
	Inner Join SubBranch On SubBranchCode=SubBranch.Code
	Where Status='OK' And (LEN(@Branch)=0 Or BranchCode=@Branch) And (LEN(@SubBranch)=0 Or SubBranchCode=@SubBranch)
	And CONVERT(Date,ReportDate) Between @FromDate And @ToDate
	Group By BranchCode,SubBranchCode + ' ' + SubBranch.SubBranch --Order By BranchCode 
	
	UNION
	
	select BranchCode, SubBranchCode + ' ' + SubBranch.SubBranch as SubBranch,
	'Expense' [Type],
	SUM(FeeCbc) as Fee
	from CbcReport 
	Inner Join SubBranch On SubBranchCode=SubBranch.Code
	Where Status='OK' And (LEN(@Branch)=0 Or BranchCode=@Branch) And (LEN(@SubBranch)=0 Or SubBranchCode=@SubBranch)
	And CONVERT(Date,ReportDate) Between @FromDate And @ToDate
	Group By BranchCode,SubBranchCode + ' ' + SubBranch.SubBranch --Order By BranchCode 
			
END
GO
/****** Object:  StoredProcedure [dbo].[krd_cbcreport_select_decision_by_branch]    Script Date: 12/19/2012 15:39:36 ******/
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
-- =============================================
-- Author:		Sovathena NETH
-- Create date: 26/07/2012
-- Description:	query total (Standard & Lite) for each Daily Report 
-- =============================================
CREATE PROCEDURE [dbo].[krd_cbcreport_select_decision_by_branch] 
	-- Add the parameters for the stored procedure here
	
	@filter varchar(50), @name nvarchar(50), @branch varchar(4), @sub_branch varchar(4), @report_type varchar(5),
	@status_cd varchar(2), @decision int, @from_amount decimal(16,2), @to_amount decimal(16,2),
	@curreny varchar(3), @from_date varchar(10), @to_date varchar(10)
	
AS

BEGIN
	
	DECLARE @status varchar(5)
	--SET @status = @status_cd
	SELECT @status = CASE WHEN @status_cd = 'OK' THEN 'OK' 
	WHEN @status_cd = 'ER' THEN 'ERROR'
	WHEN @status_cd = 'PA' THEN 'PARSE' 
	ELSE '' END
	
	IF LEN(@branch) = 0
	BEGIN	
		SELECT rpt.BranchCode Label, 
		SUM(CASE rpt.Decision WHEN 0 THEN 1 ELSE 0 END) TotalPending,
		SUM(CASE rpt.Decision WHEN 1 THEN 1 ELSE 0 END) TotalApproved,
		SUM(CASE rpt.Decision WHEN -1 THEN 1 ELSE 0 END) TotalRejected,
		SUM(CASE rpt.Decision WHEN -2 THEN 1 ELSE 0 END) TotalCancelled 
		FROM CbcReport rpt
		LEFT OUTER JOIN SubBranch sub ON rpt.SubBranchCode = sub.Code
		LEFT OUTER JOIN Branch bra ON rpt.BranchCode = bra.Code
		WHERE (LEN(@name)=0 OR Name like  '%' + @name + '%' COLLATE SQL_Latin1_General_CP850_BIN2 OR	RefNumber like '%' + @filter + '%')
		AND (LEN(@sub_branch) = 0 OR rpt.SubBranchCode =  @sub_branch)
		AND (LEN(@report_type)=0 OR rpt.Action = @report_type)
		AND (LEN(@status)=0 OR rpt.Status = @status)
		AND (@decision = 2 OR rpt.Decision = @decision)
		AND (@from_amount=0 OR @to_amount = 0 OR rpt.Amount BETWEEN @from_amount AND @to_amount)
		AND (LEN(@curreny)=0 OR rpt.Currency=@curreny)
		AND (LEN(@from_date) = 0 OR LEN(@to_date) = 0 OR CONVERT(Date,rpt.ReportDate) BETWEEN @from_date AND @to_date)
		GROUP BY rpt.BranchCode
	END
	ELSE
	BEGIN
		SELECT rpt.SubBranchCode + ' ' + sub.SubBranch Label, 
		SUM(CASE rpt.Decision WHEN 0 THEN 1 ELSE 0 END) TotalPending,
		SUM(CASE rpt.Decision WHEN 1 THEN 1 ELSE 0 END) TotalApproved,
		SUM(CASE rpt.Decision WHEN -1 THEN 1 ELSE 0 END) TotalRejected,
		SUM(CASE rpt.Decision WHEN -2 THEN 1 ELSE 0 END) TotalCancelled 
		FROM CbcReport rpt
		LEFT OUTER JOIN SubBranch sub ON rpt.SubBranchCode = sub.Code
		LEFT OUTER JOIN Branch bra ON rpt.BranchCode = bra.Code
		WHERE (LEN(@name)=0 OR Name like  '%' + @name + '%' COLLATE SQL_Latin1_General_CP850_BIN2 OR	RefNumber like '%' + @filter + '%')
		AND (rpt.BranchCode = @branch)
		AND (LEN(@sub_branch) = 0 OR rpt.SubBranchCode =  @sub_branch)
		AND (LEN(@report_type)=0 OR rpt.Action = @report_type)
		AND (LEN(@status)=0 OR rpt.Status = @status)
		AND (@decision = 2 OR rpt.Decision = @decision)
		AND (@from_amount=0 OR @to_amount = 0 OR rpt.Amount BETWEEN @from_amount AND @to_amount)
		AND (LEN(@curreny)=0 OR rpt.Currency=@curreny)
		AND (LEN(@from_date) = 0 OR LEN(@to_date) = 0 OR CONVERT(Date,rpt.ReportDate) BETWEEN @from_date AND @to_date)
		GROUP BY rpt.SubBranchCode + ' ' + sub.SubBranch
	END
		
END
GO
/****** Object:  StoredProcedure [dbo].[krd_cbcreport_select_decision_by_action]    Script Date: 12/19/2012 15:39:36 ******/
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
-- =============================================
-- Author:		Sovathena NETH
-- Create date: 26/07/2012
-- Description:	query total (Standard & Lite) for each Daily Report 
-- =============================================
CREATE PROCEDURE [dbo].[krd_cbcreport_select_decision_by_action] 
	-- Add the parameters for the stored procedure here
	
	@filter varchar(50), @name nvarchar(50), @branch varchar(4), @sub_branch varchar(4), @report_type varchar(5),
	@status_cd varchar(2), @decision int, @from_amount decimal(16,2), @to_amount decimal(16,2),
	@curreny varchar(3), @from_date varchar(10), @to_date varchar(10)
	
AS

BEGIN
	
	DECLARE @status varchar(5)
	--SET @status = @status_cd
	SELECT @status = CASE WHEN @status_cd = 'OK' THEN 'OK' 
	WHEN @status_cd = 'ER' THEN 'ERROR'
	WHEN @status_cd = 'PA' THEN 'PARSE' 
	ELSE '' END
		
	SELECT 'Label' = CASE rpt.Action WHEN 'A' THEN 'Standard' ELSE 'Lite' END, 
	SUM(CASE rpt.Decision WHEN 0 THEN 1 ELSE 0 END) TotalPending,
	SUM(CASE rpt.Decision WHEN 1 THEN 1 ELSE 0 END) TotalApproved,
	SUM(CASE rpt.Decision WHEN -1 THEN 1 ELSE 0 END) TotalRejected,
	SUM(CASE rpt.Decision WHEN -2 THEN 1 ELSE 0 END) TotalCancelled 
	FROM CbcReport rpt
	LEFT OUTER JOIN SubBranch sub ON rpt.SubBranchCode = sub.Code
	LEFT OUTER JOIN Branch bra ON rpt.BranchCode = bra.Code
	WHERE (LEN(@name)=0 OR Name like  '%' + @name + '%' COLLATE SQL_Latin1_General_CP850_BIN2 OR	RefNumber like '%' + @filter + '%')
	AND (LEN(@branch) = 0 OR rpt.BranchCode = @branch)
	AND (LEN(@sub_branch) = 0 OR rpt.SubBranchCode =  @sub_branch)
	AND (LEN(@report_type)=0 OR rpt.Action = @report_type)
	AND (LEN(@status)=0 OR rpt.Status = @status)
	AND (@decision = 2 OR rpt.Decision = @decision)
	AND (@from_amount=0 OR @to_amount = 0 OR rpt.Amount BETWEEN @from_amount AND @to_amount)
	AND (LEN(@curreny)=0 OR rpt.Currency=@curreny)
	AND (LEN(@from_date) = 0 OR LEN(@to_date) = 0 OR CONVERT(Date,rpt.ReportDate) BETWEEN @from_date AND @to_date)
	GROUP BY rpt.Action
		
END
GO
/****** Object:  StoredProcedure [dbo].[krd_cbcreport_select_action_by_decision]    Script Date: 12/19/2012 15:39:36 ******/
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
-- =============================================
-- Author:		Sovathena NETH
-- Create date: 26/07/2012
-- Description:	query total (Standard & Lite) for each Daily Report 
-- =============================================
CREATE PROCEDURE [dbo].[krd_cbcreport_select_action_by_decision] 
	-- Add the parameters for the stored procedure here
	
	@filter varchar(50), @name nvarchar(50), @branch varchar(4), @sub_branch varchar(4), @report_type varchar(5),
	@status_cd varchar(2), @decision int, @from_amount decimal(16,2), @to_amount decimal(16,2),
	@curreny varchar(3), @from_date varchar(10), @to_date varchar(10)
	
AS

BEGIN
	
	DECLARE @status varchar(5)
	--SET @status = @status_cd
	SELECT @status = CASE WHEN @status_cd = 'OK' THEN 'OK' 
	WHEN @status_cd = 'ER' THEN 'ERROR'
	WHEN @status_cd = 'PA' THEN 'PARSE' 
	ELSE '' END
		
	SELECT 'Label' = CASE rpt.Decision WHEN 0 THEN 'Pending' WHEN 1 THEN 'Approved' WHEN -1 THEN 'Rejected' ELSE 'Cancelled' END, 
	SUM(CASE rpt.Action WHEN 'A' THEN 1 ELSE 0 END) TotalStd,
	SUM(CASE rpt.Action WHEN 'L' THEN 1 ELSE 0 END) TotalLite 
	FROM CbcReport rpt
	LEFT OUTER JOIN SubBranch sub ON rpt.SubBranchCode = sub.Code
	LEFT OUTER JOIN Branch bra ON rpt.BranchCode = bra.Code
	WHERE (LEN(@name)=0 OR Name like  '%' + @name + '%' COLLATE SQL_Latin1_General_CP850_BIN2 OR	RefNumber like '%' + @filter + '%')
	AND (LEN(@branch) = 0 OR rpt.BranchCode = @branch)
	AND (LEN(@sub_branch) = 0 OR rpt.SubBranchCode =  @sub_branch)
	AND (LEN(@report_type)=0 OR rpt.Action = @report_type)
	AND (LEN(@status)=0 OR rpt.Status = @status)
	AND (@decision = 2 OR rpt.Decision = @decision)
	AND (@from_amount=0 OR @to_amount = 0 OR rpt.Amount BETWEEN @from_amount AND @to_amount)
	AND (LEN(@curreny)=0 OR rpt.Currency=@curreny)
	AND (LEN(@from_date) = 0 OR LEN(@to_date) = 0 OR CONVERT(Date,rpt.ReportDate) BETWEEN @from_date AND @to_date)
	GROUP BY rpt.Decision
		
END
GO
/****** Object:  StoredProcedure [dbo].[krd_cbcreport_select]    Script Date: 12/19/2012 15:39:36 ******/
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
-- =============================================
-- Author:		Sovathena NETH
-- Create date: 14/05/2012
-- Description:	query CBC Report
-- =============================================
CREATE PROCEDURE [dbo].[krd_cbcreport_select] 
	-- Add the parameters for the stored procedure here
	
	@filter varchar(50), @name nvarchar(50), @branch varchar(4), @sub_branch varchar(4), @report_type varchar(5),
	@status_cd varchar(2), @decision int, @from_amount decimal(16,2), @to_amount decimal(16,2),
	@curreny varchar(3), @from_date varchar(10), @to_date varchar(10)
	
AS

BEGIN
	
	DECLARE @status varchar(5)
	--SET @status = @status_cd
	SELECT @status = CASE WHEN @status_cd = 'OK' THEN 'OK' 
	WHEN @status_cd = 'ER' THEN 'ERROR'
	WHEN @status_cd = 'PA' THEN 'PARSE' 
	ELSE '' END
	
	-- Query Result	
	SELECT rpt.Id, rpt.LoanId, rpt.IdNumber, rpt.Name, rpt.Amount, rpt.Currency,
	rpt.Action, sub.Code + ' ' + sub.SubBranch SubBranchCode, bra.Code BranchCode, rpt.Status, 
	dbo.fn_datetime_format(rpt.ReportDate) ReportDate, rpt.ReportDate [Date],
	bra.Branch, rpt.Decision, rpt.NumActiveAcc 
	FROM CbcReport rpt 
	LEFT OUTER JOIN SubBranch sub ON rpt.SubBranchCode = sub.Code
	LEFT OUTER JOIN Branch bra ON rpt.BranchCode = bra.Code
	WHERE (LEN(@name)=0 OR Name like  '%' + @name + '%' COLLATE SQL_Latin1_General_CP850_BIN2 OR	RefNumber like '%' + @filter + '%')
	AND (LEN(@branch) = 0 OR rpt.BranchCode = @branch)
	AND (LEN(@sub_branch) = 0 OR rpt.SubBranchCode =  @sub_branch)
	AND (LEN(@report_type)=0 OR rpt.Action = @report_type)
	AND (LEN(@status)=0 OR rpt.Status = @status)
	AND (@decision = 2 OR rpt.Decision = @decision)
	AND (@from_amount=0 OR @to_amount = 0 OR rpt.Amount BETWEEN @from_amount AND @to_amount)
	AND (LEN(@curreny)=0 OR rpt.Currency=@curreny)
	AND (LEN(@from_date) = 0 OR LEN(@to_date) = 0 OR CONVERT(DATE,rpt.ReportDate) BETWEEN @from_date AND @to_date)
	ORDER BY ID DESC
	
END
GO
/****** Object:  StoredProcedure [dbo].[krd_cbcreport_income_expense_by_branch]    Script Date: 12/19/2012 15:39:36 ******/
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
-- =============================================
-- Author:		Sovathena NETH
-- Create date: 16/08/2012
-- Description:	query total income vs expense fee by branches
-- =============================================
CREATE PROCEDURE [dbo].[krd_cbcreport_income_expense_by_branch] 
	-- Add the parameters for the stored procedure here
	
	@filter varchar(50), @name nvarchar(50), @branch varchar(4), @sub_branch varchar(4), @report_type varchar(5),
	@status_cd varchar(2), @decision int, @from_amount decimal(16,2), @to_amount decimal(16,2),
	@curreny varchar(3), @from_date varchar(10), @to_date varchar(10)
	
AS

BEGIN
	
	DECLARE @status varchar(5)
	--SET @status = @status_cd
	SELECT @status = CASE WHEN @status_cd = 'OK' THEN 'OK' 
	WHEN @status_cd = 'ER' THEN 'ERROR'
	WHEN @status_cd = 'PA' THEN 'PARSE' 
	ELSE '' END
	
	IF LEN(@branch) = 0
	BEGIN	
		SELECT rpt.BranchCode Label, 
		SUM(FeeCbc) Expense,
		SUM(Case When Decision >=0 Then Case When Currency='USD' Then FeeKredit Else (FeeKredit/4000) End Else 0 End ) Income
		FROM CbcReport rpt
		LEFT OUTER JOIN SubBranch sub ON rpt.SubBranchCode = sub.Code
		LEFT OUTER JOIN Branch bra ON rpt.BranchCode = bra.Code
		WHERE (LEN(@name)=0 OR Name like  '%' + @name + '%' COLLATE SQL_Latin1_General_CP850_BIN2 OR	RefNumber like '%' + @filter + '%')
		AND (LEN(@sub_branch) = 0 OR rpt.SubBranchCode =  @sub_branch)
		AND (LEN(@report_type)=0 OR rpt.Action = @report_type)
		AND (LEN(@status)=0 OR rpt.Status = @status)
		AND (@decision = 2 OR rpt.Decision = @decision)
		AND (@from_amount=0 OR @to_amount = 0 OR rpt.Amount BETWEEN @from_amount AND @to_amount)
		AND (LEN(@curreny)=0 OR rpt.Currency=@curreny)
		AND (LEN(@from_date) = 0 OR LEN(@to_date) = 0 OR rpt.ReportDate BETWEEN @from_date AND DATEADD(SECOND,86399,@to_date))
		GROUP BY rpt.BranchCode
	END
	ELSE
	BEGIN
		SELECT rpt.SubBranchCode + ' ' + sub.SubBranch Label, 
		SUM(FeeCbc) Expense,
		SUM(Case When Decision >=0 Then Case When Currency='USD' Then FeeKredit Else (FeeKredit/4000) End Else 0 End ) Income
		FROM CbcReport rpt
		LEFT OUTER JOIN SubBranch sub ON rpt.SubBranchCode = sub.Code
		LEFT OUTER JOIN Branch bra ON rpt.BranchCode = bra.Code
		WHERE (LEN(@name)=0 OR Name like  '%' + @name + '%' COLLATE SQL_Latin1_General_CP850_BIN2 OR	RefNumber like '%' + @filter + '%')
		AND rpt.BranchCode = @branch
		AND (LEN(@sub_branch) = 0 OR rpt.SubBranchCode =  @sub_branch)
		AND (LEN(@report_type)=0 OR rpt.Action = @report_type)
		AND (LEN(@status)=0 OR rpt.Status = @status)
		AND (@decision = 2 OR rpt.Decision = @decision)
		AND (@from_amount=0 OR @to_amount = 0 OR rpt.Amount BETWEEN @from_amount AND @to_amount)
		AND (LEN(@curreny)=0 OR rpt.Currency=@curreny)
		AND (LEN(@from_date) = 0 OR LEN(@to_date) = 0 OR rpt.ReportDate BETWEEN @from_date AND DATEADD(SECOND,86399,@to_date))
		GROUP BY rpt.SubBranchCode + ' ' + sub.SubBranch
	END	
END
GO
/****** Object:  StoredProcedure [dbo].[krd_cbcreport_cbc_fee_by_branch]    Script Date: 12/19/2012 15:39:36 ******/
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
-- =============================================
-- Author:		Sovathena NETH
-- Create date: 16/08/2012
-- Description:	query total cbc fee by branches
-- =============================================
CREATE PROCEDURE [dbo].[krd_cbcreport_cbc_fee_by_branch] 
	-- Add the parameters for the stored procedure here
	
	@filter varchar(50), @name nvarchar(50), @branch varchar(4), @sub_branch varchar(4), @report_type varchar(5),
	@status_cd varchar(2), @decision int, @from_amount decimal(16,2), @to_amount decimal(16,2),
	@curreny varchar(3), @from_date varchar(10), @to_date varchar(10)
	
AS

BEGIN
	
	DECLARE @status varchar(5)
	--SET @status = @status_cd
	SELECT @status = CASE WHEN @status_cd = 'OK' THEN 'OK' 
	WHEN @status_cd = 'ER' THEN 'ERROR'
	WHEN @status_cd = 'PA' THEN 'PARSE' 
	ELSE '' END
	
	IF LEN(@branch) = 0
	BEGIN	
		SELECT rpt.BranchCode Label, 
		SUM(CASE rpt.Action WHEN 'A' THEN FeeCbc ELSE 0 END) FeeStd, 
		SUM(CASE rpt.Action WHEN 'L' THEN FeeCbc ELSE 0 END) FeeLite
		FROM CbcReport rpt
		LEFT OUTER JOIN SubBranch sub ON rpt.SubBranchCode = sub.Code
		LEFT OUTER JOIN Branch bra ON rpt.BranchCode = bra.Code
		WHERE (LEN(@name)=0 OR Name like  '%' + @name + '%' COLLATE SQL_Latin1_General_CP850_BIN2 OR	RefNumber like '%' + @filter + '%')
		AND (LEN(@sub_branch) = 0 OR rpt.SubBranchCode =  @sub_branch)
		AND (LEN(@report_type)=0 OR rpt.Action = @report_type)
		AND (LEN(@status)=0 OR rpt.Status = @status)
		AND (@decision = 2 OR rpt.Decision = @decision)
		AND (@from_amount=0 OR @to_amount = 0 OR rpt.Amount BETWEEN @from_amount AND @to_amount)
		AND (LEN(@curreny)=0 OR rpt.Currency=@curreny)
		AND (LEN(@from_date) = 0 OR LEN(@to_date) = 0 OR rpt.ReportDate BETWEEN @from_date AND DATEADD(SECOND,86399,@to_date))
		GROUP BY rpt.BranchCode
	END
	ELSE
	BEGIN
		SELECT rpt.SubBranchCode + ' ' + sub.SubBranch Label, 
		SUM(CASE rpt.Action WHEN 'A' THEN FeeCbc ELSE 0 END) FeeStd, 
		SUM(CASE rpt.Action WHEN 'L' THEN FeeCbc ELSE 0 END) FeeLite
		FROM CbcReport rpt
		LEFT OUTER JOIN SubBranch sub ON rpt.SubBranchCode = sub.Code
		LEFT OUTER JOIN Branch bra ON rpt.BranchCode = bra.Code
		WHERE (LEN(@name)=0 OR Name like  '%' + @name + '%' COLLATE SQL_Latin1_General_CP850_BIN2 OR	RefNumber like '%' + @filter + '%')
		AND rpt.BranchCode = @branch
		AND (LEN(@sub_branch) = 0 OR rpt.SubBranchCode =  @sub_branch)
		AND (LEN(@report_type)=0 OR rpt.Action = @report_type)
		AND (LEN(@status)=0 OR rpt.Status = @status)
		AND (@decision = 2 OR rpt.Decision = @decision)
		AND (@from_amount=0 OR @to_amount = 0 OR rpt.Amount BETWEEN @from_amount AND @to_amount)
		AND (LEN(@curreny)=0 OR rpt.Currency=@curreny)
		AND (LEN(@from_date) = 0 OR LEN(@to_date) = 0 OR rpt.ReportDate BETWEEN @from_date AND DATEADD(SECOND,86399,@to_date))
		GROUP BY rpt.SubBranchCode + ' ' + sub.SubBranch
	END	
END
GO
/****** Object:  StoredProcedure [dbo].[krd_cbcauthen_select]    Script Date: 12/19/2012 15:39:36 ******/
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
-- =============================================
-- Author:		Sovathena NETH
-- Create date: 20/07/2012
-- Description:	query CBC Authentication
-- =============================================
CREATE PROCEDURE [dbo].[krd_cbcauthen_select] 
	-- Add the parameters for the stored procedure here
	@filter varchar(50), @branch varchar(4), @sub_branch varchar(4)
AS

BEGIN
	SELECT cbc.Id, cbc.Username, cbc.Password, cbc.SubBranchCode,
	sub.SubBranch SubBranchName, br.Code BranchCode 
	FROM CbcAuthen cbc
	LEFT OUTER JOIN SubBranch sub ON cbc.SubBranchCode = sub.Code
	LEFT OUTER JOIN Branch br ON sub.BranchId = br.Id
	WHERE (LEN(@filter) = 0 
	OR (cbc.Username like '%'+ @filter +'%'	OR cbc.SubBranchCode like '%'+ @filter +'%')) 
	AND (LEN(@branch)=0 OR br.Code = @branch)
	AND (LEN(@sub_branch)=0 OR sub.Code = @sub_branch)
	ORDER BY Id DESC
	
END
GO
/****** Object:  StoredProcedure [dbo].[krd_userlogplus_insert]    Script Date: 12/19/2012 15:39:37 ******/
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
-- =============================================
-- Author:		Sovathena NETH
-- Create date: 29/05/2012
-- Description:	Insert User Log Master
-- =============================================
CREATE PROCEDURE [dbo].[krd_userlogplus_insert] 
	-- Add the parameters for the stored procedure here
	@UserId int, @LoginName varchar(50),
	@BranchCode varchar(4), @SubBranchCode varchar(4), 
	@RemoteAddress varchar(50),	@RemoteHost varchar(50), 
	@UserAgent varchar(5), @Message nvarchar(250)
AS

BEGIN

	INSERT INTO UserLogPlus (UserId, LoginName, [DateTime], BranchCode, SubBranchCode, UserAgent, RemoteHost, RemoteAddress, [Message])
	VALUES(@UserId, @LoginName, GETDATE(), @BranchCode, @SubBranchCode, @UserAgent, @RemoteHost, @RemoteAddress, @Message)
END
GO
/****** Object:  StoredProcedure [dbo].[krd_userlog_select]    Script Date: 12/19/2012 15:39:37 ******/
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
/****** Object:  StoredProcedure [dbo].[krd_userlog_insert]    Script Date: 12/19/2012 15:39:37 ******/
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
-- =============================================
-- Author:		Sovathena NETH
-- Create date: 05/05/2012
-- Description:	Insert User Log
-- =============================================
CREATE PROCEDURE [dbo].[krd_userlog_insert] 
	-- Add the parameters for the stored procedure here
	@UserId int, @LoginName varchar(50), @SubBranchCode varchar(4), @PcName varchar(50),
	@PcUsername varchar(50), @AppVersion varchar(5), @Message nvarchar(500)
AS

BEGIN

	INSERT INTO UserLog (UserId, LoginName, [DateTime], SubBranchCode, PcName, PcUsername, AppVersion, [Message])
	VALUES(@UserId, @LoginName, GETDATE(), @SubBranchCode, @PcName, @PcUsername, @AppVersion, @Message)
END
GO
/****** Object:  StoredProcedure [dbo].[krd_userauthen_set_security_number]    Script Date: 12/19/2012 15:39:37 ******/
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
-- =============================================
-- Author:		Sovathena NETH
-- Create date: 08/05/2012
-- Description:	Set Security Number
-- =============================================
CREATE PROCEDURE [dbo].[krd_userauthen_set_security_number] 
	-- Add the parameters for the stored procedure here
	@UserId int, @SecurityCode int
	
AS

BEGIN
	UPDATE UserAuthen SET SecurityCode = @SecurityCode WHERE Id = @UserId
END
GO
/****** Object:  StoredProcedure [dbo].[krd_userauthen_select_to_email]    Script Date: 12/19/2012 15:39:37 ******/
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
/****** Object:  StoredProcedure [dbo].[krd_userauthen_select]    Script Date: 12/19/2012 15:39:37 ******/
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
/****** Object:  StoredProcedure [dbo].[krd_userauthen_exist_username]    Script Date: 12/19/2012 15:39:37 ******/
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
-- =============================================
-- Author:		Sovathena NETH
-- Create date: 08/05/2012
-- Description:	Check is exist username
-- =============================================
CREATE PROCEDURE [dbo].[krd_userauthen_exist_username] 
	-- Add the parameters for the stored procedure here
	@UserId int, @Username varchar(50)
	
AS

BEGIN
	DECLARE @id int
	SELECT  @id = Id FROM UserAuthen WHERE Username = @Username AND Id <> @UserId AND Status = 'A'
	SET @id = ISNULL(@id,-1)
	SELECT @id
END
GO
/****** Object:  StoredProcedure [dbo].[krd_userauthen_exist_email]    Script Date: 12/19/2012 15:39:37 ******/
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
-- =============================================
-- Author:		Sovathena NETH
-- Create date: 10/07/2012
-- Description:	Check is exist email
-- =============================================
CREATE PROCEDURE [dbo].[krd_userauthen_exist_email] 
	-- Add the parameters for the stored procedure here
	@UserId int, @Email varchar(50)
	
AS

BEGIN
	DECLARE @id int
	SELECT  @id = Id FROM UserAuthen WHERE Email = @Email AND Id <> @UserId AND Status = 'A'
	SET @id = ISNULL(@id,-1)
	SELECT @id Value
END
GO
/****** Object:  StoredProcedure [dbo].[krd_cbcauthen_get_authentication_by_id]    Script Date: 12/19/2012 15:39:36 ******/
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
-- =============================================
-- Author:		Sovathena NETH
-- Create date: 27/Apr/2012
-- Description:	Get Authentication (Username, Password) of CBC to access B2B
-- =============================================
CREATE PROCEDURE [dbo].[krd_cbcauthen_get_authentication_by_id] 
	-- Add the parameters for the stored procedure here
	@user_id int
AS

BEGIN

	IF @user_id>0
	BEGIN
		SELECT cbc.Username, cbc.Password, usr.Id UserId, usr.SubBranchCode FROM CbcAuthen cbc
		INNER JOIN UserAuthen usr ON cbc.SubBranchCode = usr.SubBranchCode
		WHERE usr.Id = @user_id		
	END
	
END
GO
/****** Object:  StoredProcedure [dbo].[krd_userauthen_request_reset_psw]    Script Date: 12/19/2012 15:39:37 ******/
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
-- =============================================
-- Author:		Sovathena NETH
-- Create date: 11/07/2012
-- Description:	Request reset password -- Set Security Number
-- =============================================
CREATE PROCEDURE [dbo].[krd_userauthen_request_reset_psw] 
	-- Add the parameters for the stored procedure here
	@email varchar(50)
	
AS

BEGIN
	IF EXISTS(SELECT Id FROM UserAuthen WHERE Email = @email)
	BEGIN
		DECLARE @SecurityCode int
		SELECT @SecurityCode = CAST(RAND() * 1000000 AS INT)
		UPDATE UserAuthen SET SecurityCode = @SecurityCode WHERE Email = @email
		SELECT * FROM UserAuthen WHERE Email = @email
	END
END
GO
/****** Object:  StoredProcedure [dbo].[krd_userauthen_get_email_to_reset_password]    Script Date: 12/19/2012 15:39:37 ******/
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
-- =============================================
-- Author:		Sovathena NETH
-- Create date: 08/05/2012
-- Description:	Check is exist username
-- =============================================
CREATE PROCEDURE [dbo].[krd_userauthen_get_email_to_reset_password] 
	-- Add the parameters for the stored procedure here
	@UserId int, @SecurityCode int
	
AS

BEGIN
	EXEC dbo.krd_userauthen_set_security_number @UserId, @SecurityCode
	SELECT dbo.fn_userauthen_get_email(@UserId)
END
GO
/****** Object:  StoredProcedure [dbo].[krd_userauthen_get_email]    Script Date: 12/19/2012 15:39:37 ******/
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
-- =============================================
-- Author:		Sovathena NETH
-- Create date: 08/05/2012
-- Description:	Check is exist username
-- =============================================
CREATE PROCEDURE [dbo].[krd_userauthen_get_email] 
	-- Add the parameters for the stored procedure here
	@UserId int
	
AS

BEGIN
	SELECT dbo.fn_userauthen_get_email(@UserId)
END
GO
/****** Object:  StoredProcedure [dbo].[krd_cbcauthen_get_authentication]    Script Date: 12/19/2012 15:39:36 ******/
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
-- =============================================
-- Author:		Sovathena NETH
-- Create date: 24/Apr/2012
-- Description:	Get Authentication (Username, Password) of CBC to access B2B
-- =============================================
CREATE PROCEDURE [dbo].[krd_cbcauthen_get_authentication] 
	-- Add the parameters for the stored procedure here
	@username  varchar(50),
	@psw  varchar(50)
AS

BEGIN

	DECLARE @user_id INT
		
	SET @user_id = dbo.fn_do_authentication(@username, @psw)
	
	IF @user_id>0
	BEGIN
		SELECT cbc.Username, cbc.Password, usr.Id UserId, usr.SubBranchCode FROM CbcAuthen cbc
		INNER JOIN UserAuthen usr ON cbc.SubBranchCode = usr.SubBranchCode
		WHERE usr.Id = @user_id		
	END
	
END
GO
/****** Object:  StoredProcedure [dbo].[krd_cbcauthen_delete]    Script Date: 12/19/2012 15:39:36 ******/
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
-- =============================================
-- Author:		Sovathena NETH
-- Create date: 20/07/2012
-- Description:	Delete CBC User 
-- =============================================
CREATE PROCEDURE [dbo].[krd_cbcauthen_delete] 
	-- Add the parameters for the stored procedure here
	@cbc_id int, @creater_id int
AS

BEGIN
	DECLARE @role varchar(5), @mlogin_name varchar(50), @mbranch_code varchar(4), 
	@msub_code varchar(4), @m_msg varchar(500)
	
	SELECT @role = RoleCode FROM UserAuthenRole WHERE UserId = @creater_id
	IF @role = 'ADM'
	BEGIN
		SELECT @mlogin_name = Username, @mbranch_code = BranchCode, @msub_code = SubBranchCode
		FROM UserAuthen WHERE Id = @creater_id
		
		DECLARE @username varchar(50)
		SELECT @username = Username FROM CbcAuthen WHERE Id = @cbc_id
		
		SET @m_msg = 'Delete CBC user ' + @username
		
		-- Insert UserLog
		EXEC dbo.krd_userlogplus_insert @creater_id, @mlogin_name, @mbranch_code, @msub_code, '', '', '', @m_msg  
	
		DELETE CbcAuthen WHERE Id = @cbc_id
		SELECT @cbc_id Value
	END
END
GO
/****** Object:  StoredProcedure [dbo].[krd_userauthen_delete_user]    Script Date: 12/19/2012 15:39:37 ******/
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
-- =============================================
-- Author:		Sovathena NETH
-- Create date: 19/07/2012
-- Description:	Delete User 
-- =============================================
CREATE PROCEDURE [dbo].[krd_userauthen_delete_user] 
	-- Add the parameters for the stored procedure here
	@user_id int, @creater_id int
AS

BEGIN
	DECLARE @role varchar(5), @mlogin_name varchar(50), @mbranch_code varchar(4), 
	@msub_code varchar(4), @m_msg varchar(500)
	
	SELECT @role = RoleCode FROM UserAuthenRole WHERE UserId = @creater_id
	IF @role = 'ADM'
	BEGIN
		SELECT @mlogin_name = Username, @mbranch_code = BranchCode, @msub_code = SubBranchCode
		FROM UserAuthen WHERE Id = @creater_id
		
		SET @m_msg = 'Delete user ID = ' + CONVERT(varchar(5),@user_id)
		
		-- Insert UserLog
		EXEC dbo.krd_userlogplus_insert @creater_id, @mlogin_name, @mbranch_code, @msub_code, '', '', '', @m_msg  
	
		UPDATE UserAuthen SET Status = 'D', UpdatedBy = @creater_id WHERE Id = @user_id
		SELECT @user_id Value
	END
END
GO
/****** Object:  StoredProcedure [dbo].[krd_userauthen_authenticate_plus]    Script Date: 12/19/2012 15:39:37 ******/
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
-- =============================================
-- Author:		Sovathena NETH
-- Create date: 03/Apr/2012
-- Description:	Authenticate
-- =============================================
CREATE PROCEDURE [dbo].[krd_userauthen_authenticate_plus] 
	-- Add the parameters for the stored procedure here
	@username  varchar(50),
	@psw  varchar(50),
	@user_agent varchar(5),
	@remote_addr varchar(50),
	@remote_host varchar(50)
AS

BEGIN

	DECLARE @user_id INT, @log_date datetime, @subcode varchar(4), @branchcode varchar(4)
		
	SET @user_id = dbo.fn_do_authentication(@username, @psw)
	SET @log_date = GETDATE()
	
	
	IF @user_id>0
	BEGIN
		-- SubBranch Code
		SELECT @subcode = SubBranchCode, @branchcode = BranchCode  FROM UserAuthen WHERE Id = @user_id
		
		-- Insert UserLog
		EXEC dbo.krd_userlogplus_insert @user_id, @username, @branchcode, @subcode, @remote_addr, @remote_host, @user_agent, 'Login successfully'    
		
		-- Update UserAuthen
		UPDATE UserAuthen SET PcName = @remote_host WHERE Id = @user_id
		
		-- Select
		SELECT ka.Id, ka.FName, ka.LName, ka.Username, ka.Email, ka.LastLoginDate, 
		ka.SubBranchCode, dbo.fn_get_sub_description(ka.SubBranchCode) SubBranch, ka.BranchCode, dbo.fn_get_branch_description(ka.BranchCode) Branch, dbo.fn_get_branch_id(ka.BranchCode) BranchId, ka.SecurityCode
		FROM UserAuthen ka
		WHERE ka.Id = @user_id
		
		-- Update LastLoginDate
		UPDATE UserAuthen SET LastLoginDate = @log_date WHERE Id = @user_id
	END
	ELSE
	BEGIN
		-- 1) Invalid Username or Password
		IF @user_id = -1 
		BEGIN
			-- Insert UserLog
			EXEC dbo.krd_userlogplus_insert @user_id, @username, @branchcode, @subcode, @remote_addr, @remote_host, @user_agent, 'Login failed -- invalid username or psw.'
			SELECT -1 Id
		END
		-- 2) User has expired or been disabled.
		ELSE
		BEGIN
			-- Insert UserLog
			EXEC dbo.krd_userlogplus_insert @user_id, @username, @branchcode, @subcode, @remote_addr, @remote_host, @user_agent, 'Login failed -- expired or disabled.'
			SELECT -2 Id
		END
	END
END
GO
/****** Object:  StoredProcedure [dbo].[krd_userauthen_authenticate]    Script Date: 12/19/2012 15:39:37 ******/
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
-- =============================================
-- Author:		Sovathena NETH
-- Create date: 03/Apr/2012
-- Description:	Authenticate
-- =============================================
CREATE PROCEDURE [dbo].[krd_userauthen_authenticate] 
	-- Add the parameters for the stored procedure here
	@username  varchar(50),
	@psw  varchar(50),
	@app_version varchar(5),
	@pc_name varchar(50),
	@pc_username varchar(50)
AS

BEGIN

	DECLARE @user_id INT, @log_date datetime, @subcode varchar(4)
		
	SET @user_id = dbo.fn_do_authentication(@username, @psw)
	SET @log_date = GETDATE()
	
	
	IF @user_id>0
	BEGIN
		-- SubBranch Code
		SELECT @subcode = SubBranchCode  FROM UserAuthen WHERE Id = @user_id
		
		-- Insert UserLog
		EXEC dbo.krd_userlog_insert @user_id, @username, @subcode, @pc_name, @pc_username, @app_version, 'Login successfully'    
		
		-- Update UserAuthen
		UPDATE UserAuthen SET AppVersion = @app_version, PcName = @pc_name, PcUsername = @pc_username WHERE Id = @user_id
		
		-- Select
		SELECT ka.Id, ka.LastLoginDate, ka.BranchCode, ka.SubBranchCode, ka.SecurityCode, ca.Username CbcUsername 
		FROM UserAuthen ka 
		INNER JOIN CbcAuthen ca ON ka.SubBranchCode = ca.SubBranchCode
		WHERE ka.Id = @user_id
		
		-- Update LastLoginDate
		UPDATE UserAuthen SET LastLoginDate = @log_date WHERE Id = @user_id
	END
	ELSE
	BEGIN
		
		-- 1) Invalid Username or Password
		IF @user_id = -1 
		BEGIN
			-- Insert UserLog
			EXEC dbo.krd_userlog_insert @user_id, @username, '', @pc_name, @pc_username, @app_version, 'Login failed -- Invalid username or psw.'    
			SELECT -1 Id
		END
		-- 2) User has expired or been disabled.
		ELSE
		BEGIN
			-- Insert UserLog
			EXEC dbo.krd_userlog_insert @user_id, @username, '', @pc_name, @pc_username, @app_version, 'Login failed -- expired or disabled.'    
			SELECT -2 Id
		END
	
	END
END
GO
/****** Object:  StoredProcedure [dbo].[krd_userauthen_save]    Script Date: 12/19/2012 15:39:37 ******/
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
-- =============================================
-- Author:		Sovathena NETH
-- Create date: 18/07/2012
-- Description:	Save user
-- =============================================
CREATE PROCEDURE [dbo].[krd_userauthen_save] 
	-- Add the parameters for the stored procedure here
	@username varchar(50), 
	@branch_code varchar(2), 
	@sub_code varchar(2),
	@email varchar(50), 
	@status varchar(1),
	@fname varchar(30), 
	@lname varchar(20), 
	@sex varchar(1),
	@note varchar(250),
	@user_id int,
	@creater_id int,
	@user_type int
AS

BEGIN
	DECLARE @role varchar(5), @mlogin_name varchar(50), @mbranch_code varchar(4), 
	@msub_code varchar(4), @m_msg varchar(500)
	
	SELECT @role = RoleCode FROM UserAuthenRole WHERE UserId = @creater_id
	IF @role = 'ADM'
	BEGIN
		
		SELECT @mlogin_name = Username, @mbranch_code = BranchCode, @msub_code = SubBranchCode
		FROM UserAuthen WHERE Id = @creater_id
				
		IF @user_id = 0
		BEGIN
			SET @m_msg = 'Add new user -- ' + @username
			-- Insert UserLog
			EXEC dbo.krd_userlogplus_insert @creater_id, @mlogin_name, @mbranch_code, @msub_code, '', '', '', @m_msg  
			
			INSERT INTO UserAuthen (Username, Password, BranchCode, SubBranchCode, Status, Email, FName, LName, Sex, Note, CreatedBy, UserType)
			VALUES(@username, PWDENCRYPT(@username), @branch_code, @sub_code, @status, @email, @fname, @lname, @sex, @note, @creater_id, @user_type)
			
			SELECT @@IDENTITY Value
		END
		ELSE
		BEGIN
			
			DECLARE @username_old varchar(50), 
			@branch_code_old varchar(2), 
			@sub_code_old varchar(2),
			@email_old varchar(50), 
			@status_old varchar(1),
			@fname_old varchar(30), 
			@lname_old varchar(20), 
			@sex_old varchar(1),
			@note_old varchar(250),
			@user_type_old varchar(1)
			
			SELECT @username_old = Username, 
			@branch_code_old = BranchCode,
			@sub_code_old = SubBranchCode,
			@email_old = Email,
			@status_old = Status,
			@fname_old = FName,
			@lname_old = LName,
			@sex_old = Sex,
			@note_old = Note
			FROM UserAuthen WHERE Id = @user_id
			
			SET @m_msg = 'Update user ID = ' + CONVERT(varchar(5),@user_id) + ':'
			IF @username <> @username_old
			BEGIN
				SET @m_msg += ' username('+ @username_old +'-->'+ @username +')'
			END
			IF @branch_code <> @branch_code_old
			BEGIN
				SET @m_msg += ' branch('+ @branch_code_old +'-->'+ @branch_code +')'
			END
			IF @sub_code <> @sub_code_old
			BEGIN
				SET @m_msg += ' sub('+ @sub_code_old +'-->'+ @sub_code +')'
			END
			IF @email <> @email_old
			BEGIN
				SET @m_msg += ' email('+ @email_old +'-->'+ @email +')'
			END
			IF @status <> @status_old
			BEGIN
				SET @m_msg += ' email('+ @status_old +'-->'+ @status +')'
			END
			IF @fname <> @fname_old
			BEGIN
				SET @m_msg += ' email('+ @fname_old +'-->'+ @fname +')'
			END
			IF @lname <> @lname_old
			BEGIN
				SET @m_msg += ' email('+ @lname_old +'-->'+ @lname +')'
			END
			IF @sex <> @sex_old
			BEGIN
				SET @m_msg += ' email('+ @sex_old +'-->'+ @sex +')'
			END
			IF @note <> @note_old
			BEGIN
				SET @m_msg += ' note(...)'		
			END
			IF @user_type <> @user_type_old
			BEGIN
				SET @m_msg += ' user_type(' + @user_type_old + '-->' + @user_type +')'		
			END
			
			UPDATE UserAuthen SET Username = @username, BranchCode = @branch_code, SubBranchCode = @sub_code,
			Status = @status, Email = @email, FName = @fname, LName = @lname, Sex = @sex, Note = @note, UpdatedBy = @creater_id,
			UserType = @user_type
			WHERE Id = @user_id
			
			-- Insert UserLog
			EXEC dbo.krd_userlogplus_insert @creater_id, @mlogin_name, @mbranch_code, @msub_code, '', '', '', @m_msg  
			
			SELECT @user_id Value
		END
	END	
END
GO
/****** Object:  StoredProcedure [dbo].[krd_userauthen_reset_pwd_default]    Script Date: 12/19/2012 15:39:37 ******/
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
-- =============================================
-- Author:		Sovathena NETH
-- Create date: 19/07/2012
-- Description:	Reset default pwd 
-- =============================================
CREATE PROCEDURE [dbo].[krd_userauthen_reset_pwd_default] 
	-- Add the parameters for the stored procedure here
	@user_id int, @creater_id int
AS

BEGIN
	DECLARE @role varchar(5), @mlogin_name varchar(50), @mbranch_code varchar(4), 
	@msub_code varchar(4), @m_msg varchar(500)
	
	SELECT @role = RoleCode FROM UserAuthenRole WHERE UserId = @creater_id
	IF @role = 'ADM'
	BEGIN
		SELECT @mlogin_name = Username, @mbranch_code = BranchCode, @msub_code = SubBranchCode
		FROM UserAuthen WHERE Id = @creater_id
		
		SET @m_msg = 'Reset default pwd for user ID = ' + CONVERT(varchar(5),@user_id)
		
		-- Insert UserLog
		EXEC dbo.krd_userlogplus_insert @creater_id, @mlogin_name, @mbranch_code, @msub_code, '', '', '', @m_msg  
	
		UPDATE UserAuthen SET Password = PWDENCRYPT(Username), SecurityCode = 0,  UpdatedBy = @creater_id WHERE Id = @user_id
		SELECT @user_id Value
	END
END
GO
/****** Object:  StoredProcedure [dbo].[krd_userauthen_reset_password]    Script Date: 12/19/2012 15:39:37 ******/
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
-- =============================================
-- Author:		Sovathena NETH
-- Create date: 09/05/2012
-- Description:	Reset Password
-- =============================================
CREATE PROCEDURE [dbo].[krd_userauthen_reset_password] 
	-- Add the parameters for the stored procedure here
	@UserId int, @SecurityCode int, @Password varchar(50),
	@Username varchar(50),
	@app_version varchar(5),
	@pc_name varchar(50),
	@pc_username varchar(50)
	
AS

BEGIN
	DECLARE @subcode varchar(4), @branchcode varchar(4), @message varchar(80)
	SET @subcode = ''
	IF EXISTS(SELECT Id FROM UserAuthen WHERE Id = @UserId AND SecurityCode = @SecurityCode AND LEN(@SecurityCode) > 0)
	BEGIN
		IF LEN(@Password) > 0
		BEGIN
		-- SubBranch Code
		SELECT @subcode = SubBranchCode, @branchcode = BranchCode FROM UserAuthen WHERE Id = @UserId
		-- Insert UserLog
		SET @message = 'Reset password successfully. Security code ' + CAST(@SecurityCode as varchar(6))    
		IF @app_version = 'kplus'
		BEGIN
			-- Insert UserLog
			EXEC dbo.krd_userlogplus_insert @UserId, @Username, @branchcode, @subcode, '', @pc_name, @pc_username, @message    
		END
		ELSE
		BEGIN
			EXEC dbo.krd_userlog_insert @UserId, @Username, @subcode, @pc_name, @pc_username, @app_version, @message
		END
		UPDATE UserAuthen SET Password = PWDENCRYPT(@Password), SecurityCode = -1 WHERE Id = @UserId
		SELECT 'Password has been reset successfully' Value
		END
		ELSE
		BEGIN
			SELECT 'Password cannot be blank' Value
		END
	END
	ELSE
	BEGIN
		
		SET @message = 'Reset password failed! Invalid security code ' + CAST(@SecurityCode as varchar(6))
		IF @app_version = 'kplus'
		BEGIN
			-- Insert UserLog
			EXEC dbo.krd_userlogplus_insert @UserId, @Username, @branchcode, @subcode, '', @pc_name, @pc_username, @message 
		END
		ELSE
		BEGIN
			-- Insert UserLog
			EXEC dbo.krd_userlog_insert @UserId, @username, @subcode, @pc_name, @pc_username, @app_version, @message
		END
		SELECT 'Invalid security code. Try again!' Value
	END
END
GO
/****** Object:  StoredProcedure [dbo].[krd_cbcauthen_save]    Script Date: 12/19/2012 15:39:36 ******/
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
-- =============================================
-- Author:		Sovathena NETH
-- Create date: 20/07/2012
-- Description:	Save CBC Authentication
-- =============================================
CREATE PROCEDURE [dbo].[krd_cbcauthen_save] 
	-- Add the parameters for the stored procedure here
	@username varchar(50), 
	@password nvarchar(100),
	@sub_code varchar(2),
	@cbc_id int,
	@creater_id int
AS

BEGIN
	DECLARE @role varchar(5), @mlogin_name varchar(50), @mbranch_code varchar(4), 
	@msub_code varchar(4), @m_msg varchar(500)
	
	SELECT @role = RoleCode FROM UserAuthenRole WHERE UserId = @creater_id
	IF @role = 'ADM'
	BEGIN
		
		SELECT @mlogin_name = Username, @mbranch_code = BranchCode, @msub_code = SubBranchCode
		FROM UserAuthen WHERE Id = @creater_id
	
		IF @cbc_id = 0
		BEGIN
			SET @m_msg = 'Add new CBC Authentication -- ' + @username
			-- Insert UserLog
			EXEC dbo.krd_userlogplus_insert @creater_id, @mlogin_name, @mbranch_code, @msub_code, '', '', '', @m_msg  
			
			INSERT INTO CbcAuthen (Username, Password, SubBranchCode)
			VALUES(@username, @password, @sub_code)
			
			SELECT @@IDENTITY Value
		END
		ELSE
		BEGIN
			
			DECLARE @username_old varchar(50), 
			@sub_code_old varchar(2), @password_old nvarchar(100)
			
			SELECT @username_old = Username, 
			@sub_code_old = SubBranchCode, @password_old = Password
			FROM CbcAuthen WHERE Id = @cbc_id
			
			SET @m_msg = 'Update user ID = ' + CONVERT(varchar(5),@cbc_id) + ':'
			IF @username <> @username_old
			BEGIN
				SET @m_msg += ' username('+ @username_old +'-->'+ @username +')'
			END
			IF @sub_code <> @sub_code_old
			BEGIN
				SET @m_msg += ' sub('+ @sub_code_old +'-->'+ @sub_code +')'
			END
			IF @password <> @password_old
			BEGIN
				SET @m_msg += ' pwd(...)'
			END
						
			UPDATE CbcAuthen SET Username = @username, 
			SubBranchCode = @sub_code, Password = @password
			WHERE Id = @cbc_id
			
			-- Insert UserLog
			EXEC dbo.krd_userlogplus_insert @creater_id, @mlogin_name, @mbranch_code, @msub_code, '', '', '', @m_msg  
			
			SELECT @cbc_id Value
		END
	END	
END
GO
/****** Object:  StoredProcedure [dbo].[krd_uservalidity_save]    Script Date: 12/19/2012 15:39:37 ******/
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
-- =============================================
-- Author:		Sovathena NETH
-- Create date: 27/08/2012
-- Description:	Save user validity
-- =============================================
CREATE PROCEDURE [dbo].[krd_uservalidity_save] 
	-- Add the parameters for the stored procedure here
	@creater_id int,
	@user_id int,
	@validity_id int,
	@request_dt varchar(10),
	@start_dt varchar(10),
	@end_dt varchar(10),
	@note varchar(250)
	
AS

BEGIN
	DECLARE @role varchar(5), @mlogin_name varchar(50), @mbranch_code varchar(4), 
	@msub_code varchar(4), @m_msg varchar(500), @user_type int, @validiy_type varchar(1)
	
	SELECT @role = RoleCode FROM UserAuthenRole WHERE UserId = @creater_id
	IF @role = 'ADM'
	BEGIN
		
		SELECT @mlogin_name = Username, @mbranch_code = BranchCode, @msub_code = SubBranchCode
		FROM UserAuthen WHERE Id = @creater_id
		
		SELECT @user_type = UserType FROM UserAuthen WHERE Id = @user_id
				
		IF @validity_id = 0
		BEGIN
			SET @m_msg = 'Add new validiy -- '
			
			IF @user_type = 0 -- Permanent User
			BEGIN
				SET @validiy_type = 'D'
				SET @m_msg += ' disable from ' + @start_dt + ' to ' + @end_dt
			END
			ELSE
			BEGIN
				SET @validiy_type = 'E'
				SET @m_msg += ' enable from ' + @start_dt + ' to ' + @end_dt
			END
			
			-- Insert UserLog
			EXEC dbo.krd_userlogplus_insert @creater_id, @mlogin_name, @mbranch_code, @msub_code, '', '', '', @m_msg  
			
			INSERT INTO UserAuthen_Validity (UserId, RequestDate, StartDate, EndDate, Note, CreatedBy, CreatedDate, Type)
			VALUES(@user_id, @request_dt, @start_dt, @end_dt, @note, @creater_id, GETDATE(), @validiy_type)
			
			SELECT @@IDENTITY Value
		END
		ELSE
		BEGIN
			
			DECLARE @request_dt_old varchar(10), 
			@start_dt_old varchar(10), 
			@end_dt_old varchar(10),
			@note_old varchar(250),
			@type_old varchar(1)
			
			SELECT @request_dt_old = RequestDate, 
			@start_dt_old = StartDate, 
			@end_dt_old = EndDate,
			@note_old = Note,
			@type_old = Type
			FROM UserAuthen_Validity WHERE Id = @validity_id
			
			SET @m_msg = 'Update validity ID = ' + CONVERT(varchar(5),@validity_id) + ':'
			IF @request_dt <> @request_dt_old
			BEGIN
				SET @m_msg += ' request_dt('+ @request_dt_old +'-->'+ @request_dt +')'
			END
			IF @start_dt <> @start_dt_old
			BEGIN
				SET @m_msg += ' start_dt('+ @start_dt_old +'-->'+ @start_dt +')'
			END
			IF @end_dt <> @end_dt_old
			BEGIN
				SET @m_msg += ' sub('+ @end_dt_old +'-->'+ @end_dt +')'
			END
			IF @note <> @note_old
			BEGIN
				SET @m_msg += ' note(...)'		
			END
			IF @validiy_type <> @type_old
			BEGIN
				SET @m_msg += ' type(' + @type_old + '-->' + @validiy_type +')'		
			END
			
			UPDATE UserAuthen_Validity 
			SET RequestDate = @request_dt, 
			StartDate = @start_dt, 
			EndDate = @end_dt,
			Note = @note
			WHERE Id = @validity_id
			
			-- Insert UserLog
			EXEC dbo.krd_userlogplus_insert @creater_id, @mlogin_name, @mbranch_code, @msub_code, '', '', '', @m_msg  
			
			SELECT @validity_id Value
		END
	END	
END
GO
/****** Object:  StoredProcedure [dbo].[krd_uservalidity_delete]    Script Date: 12/19/2012 15:39:37 ******/
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
-- =============================================
-- Author:		Sovathena NETH
-- Create date: 27/08/2012
-- Description:	Delete User validity 
-- =============================================
CREATE PROCEDURE [dbo].[krd_uservalidity_delete] 
	-- Add the parameters for the stored procedure here
	@validity_id int, @creater_id int
AS

BEGIN
	DECLARE @role varchar(5), @mlogin_name varchar(50), @mbranch_code varchar(4), 
	@msub_code varchar(4), @m_msg varchar(500)
	
	SELECT @role = RoleCode FROM UserAuthenRole WHERE UserId = @creater_id
	IF @role = 'ADM'
	BEGIN
		SELECT @mlogin_name = Username, @mbranch_code = BranchCode, @msub_code = SubBranchCode
		FROM UserAuthen WHERE Id = @creater_id
		
		SET @m_msg = 'Delete user validity'
		
		-- Insert UserLog
		EXEC dbo.krd_userlogplus_insert @creater_id, @mlogin_name, @mbranch_code, @msub_code, '', '', '', @m_msg  
	
		DELETE UserAuthen_Validity WHERE Id = @validity_id	
		SELECT @validity_id Value
	END
END
GO
/****** Object:  Default [DF_Bookmark_Icon]    Script Date: 12/19/2012 15:39:35 ******/
ALTER TABLE [dbo].[Bookmark] ADD  CONSTRAINT [DF_Bookmark_Icon]  DEFAULT ('') FOR [Icon]
GO
/****** Object:  Default [DF_Bookmark_Sequence]    Script Date: 12/19/2012 15:39:35 ******/
ALTER TABLE [dbo].[Bookmark] ADD  CONSTRAINT [DF_Bookmark_Sequence]  DEFAULT ((1)) FOR [Sequence]
GO
/****** Object:  Default [DF_Bookmark_MainCode]    Script Date: 12/19/2012 15:39:35 ******/
ALTER TABLE [dbo].[Bookmark] ADD  CONSTRAINT [DF_Bookmark_MainCode]  DEFAULT ('') FOR [MainCode]
GO
/****** Object:  Default [DF_UserAuthenBookmark_C]    Script Date: 12/19/2012 15:39:35 ******/
ALTER TABLE [dbo].[BookmarkRole] ADD  CONSTRAINT [DF_UserAuthenBookmark_C]  DEFAULT ((0)) FOR [C]
GO
/****** Object:  Default [DF_UserAuthenBookmark_R]    Script Date: 12/19/2012 15:39:35 ******/
ALTER TABLE [dbo].[BookmarkRole] ADD  CONSTRAINT [DF_UserAuthenBookmark_R]  DEFAULT ((0)) FOR [R]
GO
/****** Object:  Default [DF_UserAuthenBookmark_U]    Script Date: 12/19/2012 15:39:35 ******/
ALTER TABLE [dbo].[BookmarkRole] ADD  CONSTRAINT [DF_UserAuthenBookmark_U]  DEFAULT ((0)) FOR [U]
GO
/****** Object:  Default [DF_UserAuthenBookmark_D]    Script Date: 12/19/2012 15:39:35 ******/
ALTER TABLE [dbo].[BookmarkRole] ADD  CONSTRAINT [DF_UserAuthenBookmark_D]  DEFAULT ((0)) FOR [D]
GO
/****** Object:  Default [DF_CbcAccountDetail_Limit]    Script Date: 12/19/2012 15:39:35 ******/
ALTER TABLE [dbo].[CbcActiveAccount] ADD  CONSTRAINT [DF_CbcAccountDetail_Limit]  DEFAULT ((0)) FOR [Limit]
GO
/****** Object:  Default [DF_CbcAccountDetail_InstallmentAmount]    Script Date: 12/19/2012 15:39:35 ******/
ALTER TABLE [dbo].[CbcActiveAccount] ADD  CONSTRAINT [DF_CbcAccountDetail_InstallmentAmount]  DEFAULT ((0)) FOR [InstallmentAmount]
GO
/****** Object:  Default [DF_CbcAccountDetail_OutstandingBalance]    Script Date: 12/19/2012 15:39:35 ******/
ALTER TABLE [dbo].[CbcActiveAccount] ADD  CONSTRAINT [DF_CbcAccountDetail_OutstandingBalance]  DEFAULT ((0)) FOR [OutstandingBalance]
GO
/****** Object:  Default [DF_CbcAccountDetail_LastAmountPaid]    Script Date: 12/19/2012 15:39:35 ******/
ALTER TABLE [dbo].[CbcActiveAccount] ADD  CONSTRAINT [DF_CbcAccountDetail_LastAmountPaid]  DEFAULT ((0)) FOR [LastAmountPaid]
GO
/****** Object:  Default [DF_CbcAccountDetail_PastDue]    Script Date: 12/19/2012 15:39:35 ******/
ALTER TABLE [dbo].[CbcActiveAccount] ADD  CONSTRAINT [DF_CbcAccountDetail_PastDue]  DEFAULT ((0)) FOR [PastDue]
GO
/****** Object:  Default [DF_CbcReport_UserId]    Script Date: 12/19/2012 15:39:35 ******/
ALTER TABLE [dbo].[CbcReport] ADD  CONSTRAINT [DF_CbcReport_UserId]  DEFAULT ((0)) FOR [UserId]
GO
/****** Object:  Default [DF_CbcReport_Flag]    Script Date: 12/19/2012 15:39:35 ******/
ALTER TABLE [dbo].[CbcReport] ADD  CONSTRAINT [DF_CbcReport_Flag]  DEFAULT ((0)) FOR [Decision]
GO
/****** Object:  Default [DF_CbcReport_NumActiveAcc]    Script Date: 12/19/2012 15:39:35 ******/
ALTER TABLE [dbo].[CbcReport] ADD  CONSTRAINT [DF_CbcReport_NumActiveAcc]  DEFAULT ((0)) FOR [NumActiveAcc]
GO
/****** Object:  Default [DF_CbcReport_FeeKredit]    Script Date: 12/19/2012 15:39:35 ******/
ALTER TABLE [dbo].[CbcReport] ADD  CONSTRAINT [DF_CbcReport_FeeKredit]  DEFAULT ((0)) FOR [FeeKredit]
GO
/****** Object:  Default [DF_CbcReport_FeeCbc]    Script Date: 12/19/2012 15:39:35 ******/
ALTER TABLE [dbo].[CbcReport] ADD  CONSTRAINT [DF_CbcReport_FeeCbc]  DEFAULT ((0)) FOR [FeeCbc]
GO
/****** Object:  Default [DF_CbcReport_BranchCode]    Script Date: 12/19/2012 15:39:35 ******/
ALTER TABLE [dbo].[CbcReport] ADD  CONSTRAINT [DF_CbcReport_BranchCode]  DEFAULT ('') FOR [BranchCode]
GO
/****** Object:  Default [DF_CbcReport_Note]    Script Date: 12/19/2012 15:39:35 ******/
ALTER TABLE [dbo].[CbcReport] ADD  CONSTRAINT [DF_CbcReport_Note]  DEFAULT ('') FOR [Note]
GO
/****** Object:  Default [DF_Role_Description]    Script Date: 12/19/2012 15:39:35 ******/
ALTER TABLE [dbo].[Role] ADD  CONSTRAINT [DF_Role_Description]  DEFAULT ('') FOR [Description]
GO
/****** Object:  Default [DF_Role_DateCreated]    Script Date: 12/19/2012 15:39:35 ******/
ALTER TABLE [dbo].[Role] ADD  CONSTRAINT [DF_Role_DateCreated]  DEFAULT (getdate()) FOR [DateCreated]
GO
/****** Object:  Default [DF_UserAuthen_FName]    Script Date: 12/19/2012 15:39:35 ******/
ALTER TABLE [dbo].[UserAuthen] ADD  CONSTRAINT [DF_UserAuthen_FName]  DEFAULT ('') FOR [FName]
GO
/****** Object:  Default [DF_UserAuthen_LName]    Script Date: 12/19/2012 15:39:36 ******/
ALTER TABLE [dbo].[UserAuthen] ADD  CONSTRAINT [DF_UserAuthen_LName]  DEFAULT ('') FOR [LName]
GO
/****** Object:  Default [DF_UserAuthen_Sex]    Script Date: 12/19/2012 15:39:36 ******/
ALTER TABLE [dbo].[UserAuthen] ADD  CONSTRAINT [DF_UserAuthen_Sex]  DEFAULT ('') FOR [Sex]
GO
/****** Object:  Default [DF_UserAuthen_Status]    Script Date: 12/19/2012 15:39:36 ******/
ALTER TABLE [dbo].[UserAuthen] ADD  CONSTRAINT [DF_UserAuthen_Status]  DEFAULT ('A') FOR [Status]
GO
/****** Object:  Default [DF_UserAuthen_SecurityCode]    Script Date: 12/19/2012 15:39:36 ******/
ALTER TABLE [dbo].[UserAuthen] ADD  CONSTRAINT [DF_UserAuthen_SecurityCode]  DEFAULT ((0)) FOR [SecurityCode]
GO
/****** Object:  Default [DF_UserAuthen_DateCreated]    Script Date: 12/19/2012 15:39:36 ******/
ALTER TABLE [dbo].[UserAuthen] ADD  CONSTRAINT [DF_UserAuthen_DateCreated]  DEFAULT (getdate()) FOR [DateCreated]
GO
/****** Object:  Default [DF_UserAuthen_UserType]    Script Date: 12/19/2012 15:39:36 ******/
ALTER TABLE [dbo].[UserAuthen] ADD  CONSTRAINT [DF_UserAuthen_UserType]  DEFAULT ((0)) FOR [UserType]
GO
/****** Object:  Default [DF_UserAuthen_Validity_RequestDate]    Script Date: 12/19/2012 15:39:36 ******/
ALTER TABLE [dbo].[UserAuthen_Validity] ADD  CONSTRAINT [DF_UserAuthen_Validity_RequestDate]  DEFAULT (getdate()) FOR [RequestDate]
GO
/****** Object:  Default [DF_UserAuthen_Validity_StartDate]    Script Date: 12/19/2012 15:39:36 ******/
ALTER TABLE [dbo].[UserAuthen_Validity] ADD  CONSTRAINT [DF_UserAuthen_Validity_StartDate]  DEFAULT (getdate()) FOR [StartDate]
GO
/****** Object:  Default [DF_UserAuthen_Validity_EndDate]    Script Date: 12/19/2012 15:39:36 ******/
ALTER TABLE [dbo].[UserAuthen_Validity] ADD  CONSTRAINT [DF_UserAuthen_Validity_EndDate]  DEFAULT (getdate()) FOR [EndDate]
GO
/****** Object:  Default [DF_UserAuthen_Validity_Note]    Script Date: 12/19/2012 15:39:36 ******/
ALTER TABLE [dbo].[UserAuthen_Validity] ADD  CONSTRAINT [DF_UserAuthen_Validity_Note]  DEFAULT ('') FOR [Note]
GO
/****** Object:  Default [DF_UserAuthen_Validity_CreateDate]    Script Date: 12/19/2012 15:39:36 ******/
ALTER TABLE [dbo].[UserAuthen_Validity] ADD  CONSTRAINT [DF_UserAuthen_Validity_CreateDate]  DEFAULT (getdate()) FOR [CreatedDate]
GO
/****** Object:  Default [DF_UserAuthen_Validity_EmailNotified]    Script Date: 12/19/2012 15:39:36 ******/
ALTER TABLE [dbo].[UserAuthen_Validity] ADD  CONSTRAINT [DF_UserAuthen_Validity_EmailNotified]  DEFAULT ((0)) FOR [CountEmailSent]
GO
/****** Object:  Default [DF_UserAuthenRole_DateCreatedq]    Script Date: 12/19/2012 15:39:36 ******/
ALTER TABLE [dbo].[UserAuthenRole] ADD  CONSTRAINT [DF_UserAuthenRole_DateCreatedq]  DEFAULT (getdate()) FOR [DateCreatedq]
GO
/****** Object:  Default [DF_UserLog_BranchCode]    Script Date: 12/19/2012 15:39:36 ******/
ALTER TABLE [dbo].[UserLog] ADD  CONSTRAINT [DF_UserLog_BranchCode]  DEFAULT ('') FOR [BranchCode]
GO
/****** Object:  Default [DF_UserLogMaster_DateTime]    Script Date: 12/19/2012 15:39:36 ******/
ALTER TABLE [dbo].[UserLogPlus] ADD  CONSTRAINT [DF_UserLogMaster_DateTime]  DEFAULT (getdate()) FOR [DateTime]
GO
