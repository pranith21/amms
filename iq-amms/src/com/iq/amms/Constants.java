package com.iq.amms;

import org.iq.util.string.StringUtil;


/**
 * @author SC64807
 * 
 */
public interface Constants {
  
  public static final String APP_HOME_PARAM_NAME = "app.home";


  /**
   * 
   */
  public enum FlatStatus {
    SOLD("1"), UNSOLD("0");

    private final String flatStatus;

    FlatStatus(String type) {
      flatStatus = type;
    }

    public String getFlatStatusValue() {
      return flatStatus;
    }

    public static FlatStatus getFlatStatus(String type) {
      if (StringUtil.isEmpty(type)) {
        return FlatStatus.UNSOLD;
      }
      FlatStatus flatStatus = null;
      for (FlatStatus stat : FlatStatus.values()) {
        if (stat.getFlatStatusValue().equals(type)) {
          flatStatus = stat;
        }
      }
      return flatStatus;
    }
    
    /**
     * @return dataType
     */
    public String toString() {
      switch (this) {
        case SOLD:
          return "1";
        case UNSOLD:
          return "0";
        default:
          return "0";
      }
    }
  }
  
  /**
   * 
   */
  public enum DwellerStatus {
    INACTIVE("0"), ACTIVE("1"), DELETED("2");

    private final String dwellerStatus;

    DwellerStatus(String type) {
      dwellerStatus = type;
    }

    public String getDwellerStatusValue() {
      return dwellerStatus;
    }

    public static DwellerStatus getDwellerStatus(String type) {
      if (StringUtil.isEmpty(type)) {
        return DwellerStatus.INACTIVE;
      }
      DwellerStatus dateFormat = null;
      for (DwellerStatus stat : DwellerStatus.values()) {
        if (stat.getDwellerStatusValue().equals(type)) {
          dateFormat = stat;
        }
      }
      return dateFormat;
    }

    /**
     * @return dataType
     */
    public String toString() {
      switch (this) {
        case INACTIVE:
          return "Inactive";
        case ACTIVE:
          return "Active";
        case DELETED:
          return "Deleted";
        default:
          return "Inactive";
      }
    }
  }
  
  /**
   * 
   */
  public enum Gender {
    M("M"), F("F"), U("U");
    
    private final String gender;

    Gender(String type) {
      gender = type;
    }

    public String getGenderValue() {
      return gender;
    }

    public static Gender getGender(String type) {
      if (StringUtil.isEmpty(type)) {
        return Gender.U;
      }
      Gender gender = null;
      for (Gender stat : Gender.values()) {
        if (stat.getGenderValue().equals(type)) {
          gender = stat;
        }
      }
      return gender;
    }

    /**
     * @return dataType
     */
    public String toString() {
      switch (this) {
        case M:
          return "Male";
        case F:
          return "Female";
        case U:
          return "Unknown";
        default:
          return "Unknown";
      }
    }
  }
  
  /**
   * 
   */
  public enum PrefferedContact {
    NONE("0"), PHONE_RESIDENCE("1"), PHONE_BUSINESS("2"), PHONE_MOBILE("3"),
    EMAIL_PRIMARY("4"), EMAIL_SECONDARY("5");

    private final String prefferedContact;

    PrefferedContact(String type) {
      prefferedContact = type;
    }

    /**
     * @return prefferedContact
     */
    public String getPrefferedContactValue() {
      return prefferedContact;
    }

    /**
     * @param type
     * @return DateFormat
     */
    public static PrefferedContact getPrefferedContact(String type) {
      if (StringUtil.isEmpty(type)) {
        return PrefferedContact.NONE;
      }
      PrefferedContact dateFormat = null;
      for (PrefferedContact stat : PrefferedContact.values()) {
        if (stat.getPrefferedContactValue().equals(type)) {
          dateFormat = stat;
        }
      }
      return dateFormat;
    }
  }
  
  
  /**
   * 
   */
  public enum BillStatus {
    UNKNOWN, GENERATED, FINALIZED, PRINTED, REPRINTED;

    /**
     * @return dataType
     */
    public String toString() {
      switch (this) {
        case GENERATED:
          return "Generated";
        case FINALIZED:
          return "Finalized";
        case PRINTED:
          return "Printed";
        case REPRINTED:
          return "Reprinted";
        case UNKNOWN:
          return "Unknown";
        default:
          return "Unknown";
      }
    }
  }
  
  /**
   * 
   */
  public enum PaymentMode {

    CASH("0"), CHEQUE("1"), NEFT("2"), DISCOUNT("3"), ERROR_RECTIFICATION("4");

    private String paymentMode;

    PaymentMode(String mode) {
      paymentMode = mode;
    }

    public String getPaymentModeValue() {
      return paymentMode;
    }

    public static PaymentMode getPaymentMode(String mode) {
      PaymentMode paymentMode = null;
      for (PaymentMode stat : PaymentMode.values()) {
        if (stat.getPaymentModeValue().equals(mode)) {
          paymentMode = stat;
        }
      }
      return paymentMode;
    }

    /**
     * @return dataType
     */
    public String toString() {
      switch (this) {
        case CASH:
          return "Cash";
        case CHEQUE:
          return "Cheque";
        case NEFT:
            return "NEFT";
        case DISCOUNT:
            return "Discount";
        case ERROR_RECTIFICATION:
            return "Error Rectification";
        default:
          return "";
      }
    }
  }
}