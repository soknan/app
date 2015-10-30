
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
