package org.iq.util.string;

import org.iq.util.BaseUtil;

/**
 * @author Sam
 * 
 */
public class StringUtil extends BaseUtil {

  private static final String SENSITIVE_DATA = "X";

  public static final String lineSeparator = System.getProperty("line.separator");
  public static final String tabString = "    ";
  public static final String spaceString = " ";
  public static final String commaString = ",";
  public static final char spaceChar = ' ';
  public static final char commaChar = ',';


  /**
   * Returns true if <code>inputString</code> is null or length of
   * <code>inputString</code> is 0
   * 
   * @param inputString <code>String</code> to be checked
   * @return boolean
   */
  public static boolean isEmpty(String inputString) {
    return inputString == null ? true
        : inputString.trim().length() == 0 ? true : false;
  }

  /**
   * Returns true if <code>inputStringBuffer</code> is null or length of
   * <code>inputStringBuffer</code> is 0
   * 
   * @param inputStringBuffer <code>StringBuffer</code> to be checked
   * @return boolean
   */
  public static boolean isEmpty(StringBuffer inputStringBuffer) {
    return inputStringBuffer == null ? true : isEmpty(inputStringBuffer
        .toString());
  }

  /**
   * Adds <code>paddingChar</code> character in leading position in the
   * <code>inputStr</code> input string making the length equal to
   * <code>originalLength</code>. If input string is null or empty returns
   * the input string itself.
   * 
   * @param inputStr
   *          String input
   * @param originalLength
   *          actual length of the field upto which the padding will be done
   *          if required
   * @param paddingChar
   *          String format of the character which will be added to the
   *          <code>inputStr</code>
   * @return String after padding done
   */
  public static String padCharLead(String inputStr, int originalLength,
      String paddingChar) {
    if (!isEmpty(inputStr)) {
      String leadingChars = new String();
      if (inputStr.length() < originalLength) {
        for (int i = inputStr.length(); i < originalLength; i++) {
          leadingChars += paddingChar;
        }
      }
      return leadingChars + inputStr;
    }
    return inputStr;
  }


  /**
   * Adds <code>paddingChar</code> character in trailing position in the
   * <code>inputStr</code> input string making the length equal to
   * <code>originalLength</code>. If input string is null or empty returns
   * the input string itself.
   * 
   * @param inputStr
   *          String input
   * @param originalLength
   *          actual length of the field upto which the padding will be done
   *          if required
   * @param paddingChar
   *          String format of the character which will be added to the
   *          <code>inputStr</code>
   * @return String after padding done
   */
  public static String padCharTrail(String inputStr, int originalLength,
      String paddingChar) {
    if (!isEmpty(inputStr)) {
      String trailingChars = new String();
      if (inputStr.length() < originalLength) {
        for (int i = inputStr.length(); i < originalLength; i++) {
          trailingChars += paddingChar;
        }
      }
      return inputStr + trailingChars;
    }
    return inputStr;
  }
  /**
   * 
   */
  public static String addSingleQoutes(String inputStr) {
    if (!isEmpty(inputStr)) {
      return "'" + inputStr + "'";
    }
    return inputStr;
  }

  /**
   * 
   */
  public static String addDoubleQoutes(String inputStr) {
    if (!isEmpty(inputStr)) {
      return "\"" + inputStr + "\"";
    }
    return inputStr;
  }

  /**
   * 
   */
  public static boolean hasSpaces(String inputStr) {
    if (!isEmpty(inputStr)) {
      return inputStr.contains(" ");
    }
    return false;
  }

  /**
   * This method will return the <code>String</code> format of the object
   * if object is null then returns <code>null</code> but NOT "null"
   * 
   * @param obj
   *          which needs to be converted to <code>String</code>
   * @return String
   */
  public static String getStringValue(Object obj) {
    return obj == null ? null : String.valueOf(obj);
  }

  public static String getStringForForm(Object obj) {
    return isEmpty(getStringValue(obj)) ? "" : getStringValue(obj);
  }

  /**
   * Puts a masking over the <code>inputArg</code> and returns a "XXXX..."
   * string. If input object is null or empty returns null.
   * 
   * @param inputArg
   *          sensitive data as input
   * @return String value after masking
   */
  public static String mask(Object inputArg) {
    if (!isEmpty(getStringValue(inputArg))) {
      StringBuffer stringBuffer = new StringBuffer();
      for (int i = 0; i < inputArg.toString().trim().length(); i++) {
        stringBuffer.append(SENSITIVE_DATA);
      }
      return stringBuffer.toString();
    }
    return null;
  }
}