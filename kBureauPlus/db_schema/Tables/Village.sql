
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
