
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
