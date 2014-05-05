/**
 * Copyright (c) 2009, Amdocs. -- All Rights Reserved
 * 
 */

package com.iq.amms.valueobjects;

import java.util.Date;

import org.iq.util.string.StringUtil;
import org.iq.valueobject.BaseVO;

import com.iq.amms.Constants.DwellerStatus;
import com.iq.amms.Constants.PrefferedContact;

/**
 * @author SC64807
 * 
 */
public class DwellersMasterVO extends BaseVO {

  /**
   * 
   */
  private static final long serialVersionUID = -8783419778892146159L;

  private int dwellersId = -1;
  private int flatId = -1;
  private String nameSalutation = null;
  private String nameFirst = null;
  private String nameMiddle = null;
  private String nameLast = null;
  private String namePreferred = null;
  private String gender = null;
  private String dateOfBirth = null;
  private String dateOfAnniversary = null;
  private String emailPrimary = null;
  private String emailSecondary = null;
  private String phoneBusiness = null;
  private String phoneBusinessExtn = null;
  private String phoneMobile = null;
  private String phoneResidence = null;
  private String preferredContact = null;
  private DwellerStatus dwellerStatus = null;
  private Date createDate = null;
  
  /**
   * 
   */
  public DwellersMasterVO() {
    this.createDate = new Date();
  }


  /**
   * @return the dateOfAnniversary
   */
  public String getDateOfAnniversary() {
    return dateOfAnniversary;
  }

  /**
   * @param dateOfAnniversary
   *          the dateOfAnniversary to set
   */
  public void setDateOfAnniversary(String dateOfAnniversary) {
    this.dateOfAnniversary = dateOfAnniversary;
  }

  /**
   * @return the dateOfBirth
   */
  public String getDateOfBirth() {
    return dateOfBirth;
  }

  /**
   * @param dateOfBirth
   *          the dateOfBirth to set
   */
  public void setDateOfBirth(String dateOfBirth) {
    this.dateOfBirth = dateOfBirth;
  }

  /**
   * @return the createDate
   */
  public Date getCreateDate() {
    return createDate;
  }

  /**
   * @param createDate
   *          the createDate to set
   */
  public void setCreateDate(Date dateOfCreation) {
    this.createDate = dateOfCreation;
  }

  /**
   * @return the dwellersId
   */
  public int getDwellersId() {
    return dwellersId;
  }

  /**
   * @param dwellersId
   *          the dwellersId to set
   */
  public void setDwellersId(int dwellersId) {
    this.dwellersId = dwellersId;
  }

  /**
   * @return the dwellerStatus
   */
  public DwellerStatus getDwellerStatus() {
    return dwellerStatus;
  }

  /**
   * @param dwellerStatus
   *          the dwellerStatus to set
   */
  public void setDwellerStatus(DwellerStatus dwellerStatus) {
    this.dwellerStatus = dwellerStatus;
  }

  /**
   * @return the emailPrimary
   */
  public String getEmailPrimary() {
    return emailPrimary;
  }

  /**
   * @param emailPrimary
   *          the emailPrimary to set
   */
  public void setEmailPrimary(String emailPrimary) {
    this.emailPrimary = emailPrimary;
  }

  /**
   * @return the emailSecondary
   */
  public String getEmailSecondary() {
    return emailSecondary;
  }

  /**
   * @param emailSecondary
   *          the emailSecondary to set
   */
  public void setEmailSecondary(String emailSecondary) {
    this.emailSecondary = emailSecondary;
  }

  /**
   * @return the flatId
   */
  public int getFlatId() {
    return flatId;
  }

  /**
   * @param flatId
   *          the flatId to set
   */
  public void setFlatId(int flatId) {
    this.flatId = flatId;
  }

  /**
   * @return the gender
   */
  public String getGender() {
    return gender;
  }

  /**
   * @param gender
   *          the gender to set
   */
  public void setGender(String gender) {
    this.gender = gender;
  }

  /**
   * @return the name
   */
  public String getName() {
    return (StringUtil.isEmpty(nameSalutation)?"":(nameSalutation+" "))+
    (StringUtil.isEmpty(nameFirst)?"":(nameFirst+" "))+
    (StringUtil.isEmpty(nameMiddle)?"":(nameMiddle+" "))+
    (StringUtil.isEmpty(nameLast)?"":(nameLast+" "));
  }

  /**
   * @return the nameFirst
   */
  public String getNameFirst() {
    return nameFirst;
  }

  /**
   * @param nameFirst
   *          the nameFirst to set
   */
  public void setNameFirst(String nameFirst) {
    this.nameFirst = nameFirst;
  }

  /**
   * @return the nameLast
   */
  public String getNameLast() {
    return nameLast;
  }

  /**
   * @param nameLast
   *          the nameLast to set
   */
  public void setNameLast(String nameLast) {
    this.nameLast = nameLast;
  }

  /**
   * @return the nameMiddle
   */
  public String getNameMiddle() {
    return nameMiddle;
  }

  /**
   * @param nameMiddle
   *          the nameMiddle to set
   */
  public void setNameMiddle(String nameMiddle) {
    this.nameMiddle = nameMiddle;
  }

  /**
   * @return the namePreferred
   */
  public String getNamePreferred() {
    return namePreferred;
  }

  /**
   * @param namePreferred
   *          the namePreferred to set
   */
  public void setNamePreferred(String namePreferred) {
    this.namePreferred = namePreferred;
  }

  /**
   * @return the nameSalutation
   */
  public String getNameSalutation() {
    return nameSalutation;
  }

  /**
   * @param nameSalutation
   *          the nameSalutation to set
   */
  public void setNameSalutation(String nameSalutation) {
    this.nameSalutation = nameSalutation;
  }

  /**
   * @return the phoneBusiness
   */
  public String getPhoneBusiness() {
    return phoneBusiness;
  }

  /**
   * @param phoneBusiness
   *          the phoneBusiness to set
   */
  public void setPhoneBusiness(String phoneBusiness) {
    this.phoneBusiness = phoneBusiness;
  }

  /**
   * @return the phoneBusinessExtn
   */
  public String getPhoneBusinessExtn() {
    return phoneBusinessExtn;
  }

  /**
   * @param phoneBusinessExtn
   *          the phoneBusinessExtn to set
   */
  public void setPhoneBusinessExtn(String phoneBusinessExtn) {
    this.phoneBusinessExtn = phoneBusinessExtn;
  }

  /**
   * @return the phoneMobile
   */
  public String getPhoneMobile() {
    return phoneMobile;
  }

  /**
   * @param phoneMobile
   *          the phoneMobile to set
   */
  public void setPhoneMobile(String phoneMobile) {
    this.phoneMobile = phoneMobile;
  }

  /**
   * @return the phoneResidence
   */
  public String getPhoneResidence() {
    return phoneResidence;
  }

  /**
   * @param phoneResidence
   *          the phoneResidence to set
   */
  public void setPhoneResidence(String phoneResidence) {
    this.phoneResidence = phoneResidence;
  }

  /**
   * @return the preferredContact
   */
  public String getPreferredContact() {
    return preferredContact;
  }

  /**
   * @param preferredContact
   *          the preferredContact to set
   */
  public void setPreferredContact(String preferredContact) {
    this.preferredContact = preferredContact;
  }
  
  public String getPreferredContactString() {
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
   * @see java.lang.Object#toString()
   */
  @Override
  public String toString() {
    StringBuffer stringBuffer = new StringBuffer();
    stringBuffer.append("DwellersMasterVO = [");
    stringBuffer.append("    dwellersId = ").append(dwellersId);
    stringBuffer.append(StringUtil.lineSeparator);
    stringBuffer.append("    name = ").append(nameSalutation).append(StringUtil.spaceString).append(nameFirst).append(StringUtil.spaceString).append(nameMiddle).append(StringUtil.spaceString).append(nameLast);
    stringBuffer.append(StringUtil.lineSeparator);
    stringBuffer.append("    flatId = ").append(flatId);
    stringBuffer.append(StringUtil.lineSeparator);
    stringBuffer.append("    gender = ").append(gender);
    stringBuffer.append(StringUtil.lineSeparator);
    stringBuffer.append("    dwellerStatus = ").append(dwellerStatus);
    stringBuffer.append(StringUtil.lineSeparator);
    stringBuffer.append("    phoneMobile = ").append(phoneMobile);
    stringBuffer.append(StringUtil.lineSeparator);
    stringBuffer.append("    phoneResidence = ").append(phoneResidence);
    stringBuffer.append(StringUtil.lineSeparator);
    stringBuffer.append("    phoneBusiness = ").append(phoneBusiness);
    stringBuffer.append(StringUtil.lineSeparator);
    stringBuffer.append("    phoneBusinessExtn = ").append(phoneBusinessExtn);
    stringBuffer.append(StringUtil.lineSeparator);
    stringBuffer.append("    emailPrimary = ").append(emailPrimary);
    stringBuffer.append(StringUtil.lineSeparator);
    stringBuffer.append("    emailSecondary = ").append(emailSecondary);
    stringBuffer.append(StringUtil.lineSeparator);
    stringBuffer.append("    dateOfBirth = ").append(dateOfBirth);
    stringBuffer.append(StringUtil.lineSeparator);
    stringBuffer.append("    dateOfAnniversary = ").append(dateOfAnniversary);
    stringBuffer.append(StringUtil.lineSeparator);
    stringBuffer.append("    namePreferred = ").append(namePreferred);
    stringBuffer.append(StringUtil.lineSeparator);
    stringBuffer.append("    preferredContact = ").append(preferredContact);
    stringBuffer.append(StringUtil.lineSeparator);
    stringBuffer.append("    createDate = ").append(createDate);
    stringBuffer.append(StringUtil.lineSeparator);
    stringBuffer.append("]");
    return stringBuffer.toString();
  }
}