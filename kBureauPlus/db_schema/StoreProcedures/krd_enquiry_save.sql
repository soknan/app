
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
