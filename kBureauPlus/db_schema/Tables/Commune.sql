
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
