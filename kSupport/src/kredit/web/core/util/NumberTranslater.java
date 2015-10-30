package kredit.web.core.util;

public class NumberTranslater {

	private static String Tran1(int value) {
		String tempTran1 = null;
		switch (value) {
		case 0:
			tempTran1 = "សូន្យ";
			break;
		case 1:
			tempTran1 = "មួយ";
			break;
		case 2:
			tempTran1 = "ពីរ";
			break;
		case 3:
			tempTran1 = "បី";
			break;
		case 4:
			tempTran1 = "បួន";
			break;
		case 5:
			tempTran1 = "ប្រាំ";
			break;
		case 6:
			tempTran1 = "ប្រាំមួយ";
			break;
		case 7:
			tempTran1 = "ប្រាំពីរ";
			break;
		case 8:
			tempTran1 = "ប្រាំបី";
			break;
		case 9:
			tempTran1 = "ប្រាំបួន";
			break;
		}
		return tempTran1;
	}

	private static String Tran2(int val) {
		String tempTran2 = null;
		int bit1 = 0;
		int bit2 = 0;
		String value = ("0" + val);
		value = value.substring(value.length() - 2);

		bit1 = Integer.parseInt(value.substring(value.length() - 1));
		bit2 = Integer.parseInt(value.substring(0, 1));
		switch (bit2) {
		case 0:
			tempTran2 = Tran1(bit1);
			break;
		case 1:
			if (bit1 == 0) {
				tempTran2 = "ដប់";
			} else {
				tempTran2 = "ដប់" + Tran1(bit1);
			}
			break;
		case 2:
			if (bit1 == 0) {
				tempTran2 = "ម្ភៃ";
			} else {
				tempTran2 = "ម្ភៃ" + Tran1(bit1);
			}
			break;
		case 3:
			if (bit1 == 0) {
				tempTran2 = "សាមសិប";
			} else {
				tempTran2 = "សាមសិប" + Tran1(bit1);
			}
			break;
		case 4:
			if (bit1 == 0) {
				tempTran2 = "សែសិប";
			} else {
				tempTran2 = "សែសិប" + Tran1(bit1);
			}
			break;
		case 5:
			if (bit1 == 0) {
				tempTran2 = "ហាសិប";
			} else {
				tempTran2 = "ហាសិប" + Tran1(bit1);
			}
			break;
		case 6:
			if (bit1 == 0) {
				tempTran2 = "ហុកសិប";
			} else {
				tempTran2 = "ហុកសិប" + Tran1(bit1);
			}
			break;
		case 7:
			if (bit1 == 0) {
				tempTran2 = "ចិតសិប";
			} else {
				tempTran2 = "ចិតសិប" + Tran1(bit1);
			}
			break;
		case 8:
			if (bit1 == 0) {
				tempTran2 = "ប៉ែតសិប";
			} else {
				tempTran2 = "ប៉ែតសិប" + Tran1(bit1);
			}
			break;
		case 9:
			if (bit1 == 0) {
				tempTran2 = "កៅសិប";
			} else {
				tempTran2 = "កៅសិប" + Tran1(bit1);
			}
			break;
		}
		return tempTran2;
	}

	private static String Tran3(int val) // --Hundred
	{
		String tempTran3 = null;
		int bit3 = 0;
		int bit21 = 0;
		String value = ("000" + val);
		value = value.substring(value.length() - 3);
		bit3 = Integer.parseInt(value.substring(0, 1));
		bit21 = Integer.parseInt(value.substring(value.length() - 2));
		switch (bit3) {
		case 0:
			tempTran3 = Tran2(bit21);
			break;
		default:
			switch (bit21) {
			case 0:
				tempTran3 = Tran1(bit3) + "រយ";
				break;
			default:
				tempTran3 = Tran1(bit3) + "រយ" + Tran2(bit21);
				break;
			}
			break;
		}
		return tempTran3;
	}

	private static String Tran4(long val) // ---Thousand
	{
		String tempTran4 = null;
		int bit4 = 0;
		int bit321 = 0;
		String value = ("000000" + val);
		value = value.substring(value.length() - 6);
		bit4 = Integer.parseInt(value.substring(0, 3));
		bit321 = Integer.parseInt(value.substring(value.length() - 3));
		switch (bit4) {
		case 0:
			tempTran4 = Tran3(bit321);
			break;
		default:
			switch (bit321) {
			case 0:
				tempTran4 = Tran3(bit4) + "ពាន់";
				break;
			default:
				tempTran4 = Tran3(bit4) + "ពាន់" + Tran3(bit321);
				break;
			}
			break;
		}
		return tempTran4;
	}

	private static String Tran5(long val,String ccy) {
		String tempTran5 = null;
		int bit5 = 0;
		int bit4321 = 0;
		String value = ("000000000" + val);
		value = value.substring(value.length() - 9);
		bit5 = Integer.parseInt(value.substring(0, 3));
		bit4321 = Integer.parseInt(value.substring(value.length() - 6));
		switch (bit5) {
		case 0:			
			tempTran5 = Tran4(bit4321) + "រៀល";
			if(ccy.equals("THB")){
				tempTran5 = Tran4(bit4321) + "បាត";
			}
			if(ccy.equals("USD")){
				tempTran5 = Tran4(bit4321) + "ដុល្លារ";
			}
			break;
		default:
			switch (bit4321) {
			case 0:
				tempTran5 = Tran3(bit5) + "លានរៀល";
				if(ccy.equals("THB")){
					tempTran5 = Tran4(bit5) + "លានបាត";
				}
				if(ccy.equals("USD")){
					tempTran5 = Tran4(bit5) + "លានដុល្លារ";
				}
				break;
			default:
				tempTran5 = Tran3(bit5) + "លាន" + Tran4(bit4321) + "រៀល";
				if(ccy.equals("THB")){
					tempTran5 = Tran3(bit5) + "លាន" + Tran4(bit4321) + "បាត";
				}
				if(ccy.equals("USD")){
					tempTran5 = Tran3(bit5) + "លាន" + Tran4(bit4321) + "ដុល្លារ";
				}
				break;
			}
			break;
		}
		return tempTran5;
	}

	public static String translateToLetter(String value,String ccy) {
		String tempNumTranslator = null;
		long bitNum = 0;
		int DotPos = 0;
		DotPos = value.indexOf(".");
		if (DotPos == -1) {
			tempNumTranslator = Tran5(Integer.parseInt(value),ccy);
		} else {
			bitNum = Long.parseLong(value.substring(0, DotPos));
			tempNumTranslator = Tran5(bitNum,ccy);
		}
		return tempNumTranslator;
	}
}
