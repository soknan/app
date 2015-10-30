
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
-- =============================================
-- Author:		Sovathena NETH
-- Create date: 20/Apr/2012
-- Description:	Get Location: province, district, commune, and village based on provided code
-- =============================================
CREATE PROCEDURE [dbo].[krd_get_location] 
	-- Add the parameters for the stored procedure here
	@pro_code  int, @dis_code int, @com_code int, @vil_code int
AS

BEGIN
	IF @pro_code > 0 AND @dis_code > 0 AND @com_code > 0 AND @vil_code > 0
	BEGIN
		SELECT pro.Province, dis.District, com.Commune, vil.Village 
		FROM Province pro
		INNER JOIN District dis ON pro.ProvinceID = dis.ProvinceID
		INNER JOIN Commune com ON dis.DistrictID = com.DistrictID
		INNER JOIN Village vil ON com.CommuneID = vil.CommuneID
		WHERE pro.ProvinceID = @pro_code AND dis.Code = @dis_code AND com.Code = @com_code AND vil.Code = @vil_code
	END
	ELSE IF @pro_code > 0 AND @dis_code > 0 AND @com_code > 0 AND @vil_code = 0
	BEGIN
		SELECT pro.Province, dis.District, com.Commune, '' Village
		FROM Province pro
		INNER JOIN District dis ON pro.ProvinceID = dis.ProvinceID
		INNER JOIN Commune com ON dis.DistrictID = com.DistrictID
		WHERE pro.ProvinceID = @pro_code AND dis.Code = @dis_code AND com.Code = @com_code
	END
	ELSE IF @pro_code > 0 AND @dis_code > 0 AND @com_code = 0 AND @vil_code = 0
	BEGIN
		SELECT pro.Province, dis.District, '' Commune, '' Village
		FROM Province pro
		INNER JOIN District dis ON pro.ProvinceID = dis.ProvinceID
		WHERE pro.ProvinceID = @pro_code AND dis.Code = @dis_code
	END
	ELSE IF @pro_code > 0 AND @dis_code = 0 AND @com_code = 0 AND @vil_code = 0
	BEGIN
		SELECT pro.Province, '' District, '' Commune, '' Village
		FROM Province pro
		WHERE pro.ProvinceID = @pro_code
	END
END
GO
