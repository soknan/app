
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
