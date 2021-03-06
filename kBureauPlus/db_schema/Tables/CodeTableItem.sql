
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
