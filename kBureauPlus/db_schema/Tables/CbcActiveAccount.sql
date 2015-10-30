
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
