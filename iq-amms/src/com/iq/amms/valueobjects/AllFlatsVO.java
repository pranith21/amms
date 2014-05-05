/**
 * Copyright (c) 2009, Amdocs. -- All Rights Reserved
 *
 **/

package com.iq.amms.valueobjects;

import org.iq.util.string.StringUtil;
import org.iq.valueobject.BaseVO;

import com.iq.amms.Constants.PrefferedContact;

/**
 * @author SC64807
 *
 */
public class AllFlatsVO extends BaseVO {

  /**
   * 
   */
  private static final long serialVersionUID = 6423086160896023298L;

  private int flatId = -1;
  private String flatNumberPrefix1 = null;
  private String flatNumberPrefix2 = null;
  private String flatNumber = null;
  private String flatNumberSuffix1 = null;
  private String flatNumberSuffix2 = null;

  private String nameSalutation = null;
  private String nameFirst = null;
  private String nameMiddle = null;
  private String nameLast = null;
  private String namePreferred = null;

  private String phoneMobile = null;
  private String phoneResidence = null;
  private String phoneBusiness = null;
  private String phoneBusinessExtn = null;
  private String emailPrimary = null;
  private String emailSecondary = null;
  private String preferredContact = null;
  
  private Double currentBalance = 0.0;

  /**
   * @return the name
   */
  public String getFullFlatNumber() {
    return (StringUtil.isEmpty(flatNumberPrefix1) ? ""
        : (flatNumberPrefix1 + " "))
        + (StringUtil.isEmpty(flatNumberPrefix2) ? ""
            : (flatNumberPrefix2 + " "))
        + (StringUtil.isEmpty(flatNumber) ? "" : (flatNumber + " "))
        + (StringUtil.isEmpty(flatNumberSuffix1) ? ""
            : (flatNumberSuffix1 + " "))
        + (StringUtil.isEmpty(flatNumberSuffix2) ? ""
            : (flatNumberSuffix2 + " "));
  }

  /**
   * @return the name
   */
  public String getFullName() {
    return (StringUtil.isEmpty(namePreferred) ? "" : (namePreferred + " ("))
        + (StringUtil.isEmpty(nameSalutation) ? "" : (nameSalutation + " "))
        + (StringUtil.isEmpty(nameFirst) ? "" : (nameFirst + " "))
        + (StringUtil.isEmpty(nameMiddle) ? "" : (nameMiddle + " "))
        + (StringUtil.isEmpty(nameLast) ? "" : (nameLast + " "))
        + (StringUtil.isEmpty(namePreferred) ? "" : (namePreferred + ")"));
  }

  /**
   * @return the name
   */
  public String getPrefferdContact() {
    PrefferedContact prefferedContact =
        PrefferedContact.getPrefferedContact(this.preferredContact);
    switch (prefferedContact) {
      case EMAIL_PRIMARY:
        return (StringUtil.isEmpty(emailPrimary) ? "" : (emailPrimary));
      case EMAIL_SECONDARY:
        return (StringUtil.isEmpty(emailSecondary) ? "" : (emailSecondary));
      case PHONE_BUSINESS:
        return (StringUtil.isEmpty(phoneBusiness) ? ""
            : (phoneBusiness + " Extn:"))
            + (StringUtil.isEmpty(phoneBusinessExtn) ? ""
                : (phoneBusinessExtn));
      case PHONE_RESIDENCE:
        return (StringUtil.isEmpty(phoneResidence) ? "" : (phoneResidence));
      case NONE:
      case PHONE_MOBILE:
      default:
        return (StringUtil.isEmpty(phoneMobile) ? "" : (phoneMobile));
    }
  }

  /* (non-Javadoc)
   * @see com.iq.amms.valueobjects.BaseVO#toString()
   */
  @Override
  public String toString() {
    // TODO Auto-generated method stub
    return null;
  }

  /**
   * @param emailPrimary the emailPrimary to set
   */
  public void setEmailPrimary(String emailPrimary) {
    this.emailPrimary = emailPrimary;
  }

  /**
   * @param emailSecondary the emailSecondary to set
   */
  public void setEmailSecondary(String emailSecondary) {
    this.emailSecondary = emailSecondary;
  }

  /**
   * @param flatNumber the flatNumber to set
   */
  public void setFlatNumber(String flatNumber) {
    this.flatNumber = flatNumber;
  }

  /**
   * @param flatNumberPrefix1 the flatNumberPrefix1 to set
   */
  public void setFlatNumberPrefix1(String flatNumberPrefix1) {
    this.flatNumberPrefix1 = flatNumberPrefix1;
  }

  /**
   * @param flatNumberPrefix2 the flatNumberPrefix2 to set
   */
  public void setFlatNumberPrefix2(String flatNumberPrefix2) {
    this.flatNumberPrefix2 = flatNumberPrefix2;
  }

  /**
   * @param flatNumberSuffix1 the flatNumberSuffix1 to set
   */
  public void setFlatNumberSuffix1(String flatNumberSuffix1) {
    this.flatNumberSuffix1 = flatNumberSuffix1;
  }

  /**
   * @param flatNumberSuffix2 the flatNumberSuffix2 to set
   */
  public void setFlatNumberSuffix2(String flatNumberSuffix2) {
    this.flatNumberSuffix2 = flatNumberSuffix2;
  }

  /**
   * @param nameFirst the nameFirst to set
   */
  public void setNameFirst(String nameFirst) {
    this.nameFirst = nameFirst;
  }

  /**
   * @param nameLast the nameLast to set
   */
  public void setNameLast(String nameLast) {
    this.nameLast = nameLast;
  }

  /**
   * @param nameMiddle the nameMiddle to set
   */
  public void setNameMiddle(String nameMiddle) {
    this.nameMiddle = nameMiddle;
  }

  /**
   * @param namePreferred the namePreferred to set
   */
  public void setNamePreferred(String namePreferred) {
    this.namePreferred = namePreferred;
  }

  /**
   * @param nameSalutation the nameSalutation to set
   */
  public void setNameSalutation(String nameSalutation) {
    this.nameSalutation = nameSalutation;
  }

  /**
   * @param phoneBusiness the phoneBusiness to set
   */
  public void setPhoneBusiness(String phoneBusiness) {
    this.phoneBusiness = phoneBusiness;
  }

  /**
   * @param phoneBusinessExtn the phoneBusinessExtn to set
   */
  public void setPhoneBusinessExtn(String phoneBusinessExtn) {
    this.phoneBusinessExtn = phoneBusinessExtn;
  }

  /**
   * @param phoneMobile the phoneMobile to set
   */
  public void setPhoneMobile(String phoneMobile) {
    this.phoneMobile = phoneMobile;
  }

  /**
   * @param phoneResidence the phoneResidence to set
   */
  public void setPhoneResidence(String phoneResidence) {
    this.phoneResidence = phoneResidence;
  }

  /**
   * @param preferredContact the preferredContact to set
   */
  public void setPreferredContact(String preferredContact) {
    this.preferredContact = preferredContact;
  }

  /**
   * @param currentBalance
   */
  public void setCurrentBalance(Double currentBalance) {
    this.currentBalance = currentBalance;
  }

  /**
   * @param currentBalance
   */
  public String getCurrentBalance() {
    return StringUtil.getStringForForm(this.currentBalance);
  }

  /**
   * @return the flatId
   */
  public int getFlatId() {
    return flatId;
  }

  /**
   * @param flatId the flatId to set
   */
  public void setFlatId(int flatId) {
    this.flatId = flatId;
  }
}
