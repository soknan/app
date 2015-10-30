
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
