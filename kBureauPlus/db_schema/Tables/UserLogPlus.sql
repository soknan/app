
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
