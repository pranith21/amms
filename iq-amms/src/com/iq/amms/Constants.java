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
    UNOCCUPPIED, OCCUPPIED;

    /**
     * @return dataType
     */
    public String toString() {
      switch (this) {
        case UNOCCUPPIED:
          return "Unoccuppied";
        case OCCUPPIED:
          return "Occuppied";
        default:
          return "Unoccuppied";
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
    M, F, U;

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
}