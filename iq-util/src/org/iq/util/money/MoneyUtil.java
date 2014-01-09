package org.iq.util.money;

import org.iq.util.string.StringUtil;


/**
 * @author Sam
 * 
 */
public class MoneyUtil {
  
  private static final String[] ONES = {"","ONE","TWO","THREE","FOUR","FIVE","SIX","SEVEN","EIGHT","NINE","TEN","ELEVEN","TWELVE","THIRTEEN","FOURTEEN","FIFTEEN","SIXTEEN","SEVENTEEN","EIGHTEEN","NINETEEN"};
  private static final String[] TENS = {"","","TWENTY","THIRTY","FORTY","FIFTY","SIXTY","SEVENTY","EIGHTY","NINETY"};

  public static String getMoneyInWords(Money amount) {
    StringBuffer stringBuffer = new StringBuffer();
    int wholePart = amount.getAmountWholePart();
    boolean negative = false;
    if (wholePart<0) {
      wholePart = wholePart *-1;
      negative = true;
    }
    stringBuffer.append("RUPEES ");
    if (wholePart > 0) {
      // System.out.println("wholePart = "+wholePart);
      while (wholePart > 0) {
        if (wholePart > 10000000) {
          stringBuffer.append(ONES[wholePart / 10000000]).append(" CRORE ");
          wholePart = wholePart % 10000000;
        }
        else if (wholePart > 100000) {
          stringBuffer.append(getTwoPlacesInWords(wholePart / 100000))
              .append(" LAKH ");
          wholePart = wholePart % 100000;
        }
        else if (wholePart > 1000) {
          stringBuffer.append(getTwoPlacesInWords(wholePart / 1000)).append(
              " THOUSAND ");
          wholePart = wholePart % 1000;
        }
        else if (wholePart > 100) {
          stringBuffer.append(ONES[wholePart / 100]).append(" HUNDRED ");
          wholePart = wholePart % 100;
        }
        else {
          stringBuffer.append(getTwoPlacesInWords(wholePart));
          wholePart = 0;
        }
      }
    }
    else {
      stringBuffer.append("ZERO");
    }

    stringBuffer.append(" PAISE ");
    int fractionPart = amount.getAmountFractionPart();
    if (fractionPart > 0) {
      stringBuffer.append(getTwoPlacesInWords(fractionPart));
    }
    else {
      stringBuffer.append("ZERO");
    }
    stringBuffer.append(" ONLY");
    // System.out.println("fractionPart = "+fractionPart);
    stringBuffer.append(negative?" (CREDIT)":"");
    return stringBuffer.toString();
  }

  public static String getMoneyInWords(String currency, Integer amount) {
    return getMoneyInWords(new Money());
  }

  public static String getMoneyInWords(Integer amount) {
    return getMoneyInWords(null, amount);
  }
  
  /**
   * 
   */
  private static String getTwoPlacesInWords(int num) {
    if (StringUtil.getStringValue(num).length()<=2) {
      if (num>19) {
        return TENS[num/10]+((num%10>0)?(" "+ONES[num%10]):"");
      }
      else {
        return ONES[num];
      }
    }
    return "";
  }
}