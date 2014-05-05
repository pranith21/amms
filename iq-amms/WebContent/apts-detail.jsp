<%@ page import="org.iq.exception.CacheException,org.iq.exception.DbException,org.iq.service.ServiceConstants,com.iq.amms.services.helpers.FinancialsHelper,com.iq.amms.services.helpers.FlatsHelper,com.iq.amms.services.helpers.GetAllFlats,com.iq.amms.services.helpers.GetApartmentDetails,com.iq.amms.services.helpers.BillHelper,com.iq.amms.services.helpers.SystemHelper,com.iq.amms.services.Login,com.iq.amms.services.LookupApartment,com.iq.amms.services.UpdateApartmentMasterParams,org.iq.service.helpers.SystemConf,com.iq.amms.valueobjects.AllFlatsVO,com.iq.amms.valueobjects.ApartmentMasterVO,org.iq.valueobject.BaseVO,com.iq.amms.valueobjects.DwellersMasterVO,com.iq.amms.valueobjects.FinancialDetailsVO,com.iq.amms.valueobjects.FlatDetailsVO,com.iq.amms.valueobjects.BillMasterVO,com.iq.amms.valueobjects.BillDetailsVO,com.iq.amms.valueobjects.SessionVO,com.iq.amms.valueobjects.SystemParamsVO,org.iq.util.date.DateUtil.DateFormat,org.iq.util.date.DateUtil.DatePart,org.iq.util.date.DateUtil.Month,org.iq.util.date.DateUtil,org.iq.util.file.FileUtil,org.iq.util.http.HttpUtil,org.iq.util.money.Money,org.iq.util.money.MoneyUtil,org.iq.util.object.ObjectUtil,org.iq.util.string.StringUtil,org.iq.util.UtilConstants.IPVersion,org.iq.util.UtilConstants,org.iq.util.version.GenerateImportString,org.iq.util.version.Version,org.iq.util.xml.XmlHandler,org.iq.util.xml.XmlUtil,java.util.ArrayList" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<!-- saved from url=(0026)http://www.iqshelters.com/ -->
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=iso-8859-1" />
<title>AMMS <%=Version.versionNumber%>::iquesters</title>
<link href="styles/common.css" rel="stylesheet" type="text/css" media="screen" />
<link href="styles/menu.css" rel="stylesheet" type="text/css" media="screen" />
<link href="styles/form.css" rel="stylesheet" type="text/css" media="screen" />
<link href="styles/table-sorter-style.css" rel=StyleSheet type="text/css" />
<script src="scripts/jquery-1.7.2.min.js" type="text/javascript"></script>
<script src="scripts/jquery.tablesorter.js" type="text/javascript"></script>
<script type="text/javascript">
function setRequestType(selectedOption){
	document.getElementById("operationType").value = selectedOption.value;
	document.getElementById("flatIDValue").value = document.getElementById("flatIdKey").value;
}

function populateDate(selectedMonth){
	var month = selectedMonth.value;
	var select = "";
	if("birthMonth" == selectedMonth.id){
		select = document.getElementById("birthDate");
	}else if("anniversaryMonth" == selectedMonth.id){
		select = document.getElementById("anniversaryDate");
	}
	var len = select.length;
	if(month==1 || month==3 || month==5 || month==7 || month==8 || month==10 || month==12){
		if(len == 30){
			var option = document.createElement('option');
			option.value="30";
			option.text="30";
	        select.add(option, 30);
	    	var option1 = document.createElement('option');
			option1.value="31";
			option1.text="31";
	        select.add(option1, 31);
		}else if(len==31){
	    	var option1 = document.createElement('option');
			option1.value="31";
			option1.text="31";
	        select.add(option1, 31);
		}
	}else if(month==4 || month==6 || month==9 || month==11){
		if(len == 30){
			var option = document.createElement('option');
			option.value="30";
			option.text="30";
	        select.add(option, 30);
		}else if(len == 32){
	        select.remove(31);
		}
	}else{
		if(len == 32){
	        select.remove(31);
	        select.remove(30);
		}else if(len==31){
	        select.remove(30);
		}
	}
}
</script>
</head>

<body>
    <div class="header">
      <%@include file="/common/static/header.html"%>
    </div>
    <div class="bodycontent">
<%
	FlatDetailsVO flatDetailsVO = (FlatDetailsVO)request.getAttribute("flatDetailsVO");
	DwellersMasterVO dwellersMasterVO = (DwellersMasterVO)request.getAttribute("dwellersMasterVO");
	FinancialDetailsVO financialDetailsVO = (FinancialDetailsVO)request.getAttribute("financialDetailsVO");
	Boolean editDwellerFlag = false;
	if(request.getAttribute("editDwellerFlag")!= null){
		editDwellerFlag = (Boolean)request.getAttribute("editDwellerFlag");
	}
	Boolean editFlatDetails = false;
	if(request.getAttribute("editFlatDetails")!= null){
		editFlatDetails = (Boolean)request.getAttribute("editFlatDetails");
	}
	
	if (flatDetailsVO != null) {
		String dateOfBirth = dwellersMasterVO.getDateOfBirth();
		String[] birthDayarr = null;
		String birthMonth = null;
		String birthDate = null;
		if(dateOfBirth != null){
			 birthDayarr = dateOfBirth.split("-");
		}
		if(birthDayarr !=null && birthDayarr.length == 2){
			birthMonth = birthDayarr[0];
			birthDate = birthDayarr[1];
		}
		String dateOfAnniversary = dwellersMasterVO.getDateOfAnniversary();
		String[] anniversaryDayarr = null;
		String anniversaryMonth = null;
		String anniversaryDate = null;
		if(dateOfAnniversary != null){
			anniversaryDayarr = dateOfAnniversary.split("-");
		}
		if(anniversaryDayarr !=null && anniversaryDayarr.length == 2){
			anniversaryMonth = anniversaryDayarr[0];
			anniversaryDate = anniversaryDayarr[1];
		}
%>
		<div id="flatDetails" class="formContainer">
    		<form id="UpdateFlatDetails" name="UpdateFlatDetails" method="post" action="./ServerAdapter">
    			<input name="ServiceName" type="hidden" value="UpdateFlatDetails" />
				<input name="flatIdKey" id="flatIdKey" type="hidden" value="<%=flatDetailsVO.getFlatId()%>"/>
				<div class="formHeader">
					<b>Flat Details</b>
					<span>[<a title="Help" href='#' ><b>?</b></a>]</span>
				</div>
		    	<div class="col1">
	        		<div class="labels"><label for="flatNumber">Flat Number</label></div>
	        		<div class="fields"><input type="text" id="flatNumber" name="flatNumber" value="<%=StringUtil.getStringForForm(flatDetailsVO.getFlatNumber())%>" <%if(!editFlatDetails) {%>readonly<%}%>/></div>
	
	        		<div class="labels"><label for="flatNumberPrefix1">Flat Number Prefix</label></div>
	        		<div class="fields"><input type="text" id="flatNumberPrefix1" name="flatNumberPrefix1" value="<%=StringUtil.getStringForForm(flatDetailsVO.getFlatNumberPrefix1())%>" <%if(!editFlatDetails) {%>readonly<%}%>/></div>
	
	        		<div class="labels"><label for="floorNumber">Floor Number</label></div>
	        		<div class="fields"><input type="text" id="floorNumber" name="floorNumber" value="<%=StringUtil.getStringForForm(flatDetailsVO.getFloorNumber())%>" <%if(!editFlatDetails) {%>readonly<%}%>/></div>
		    	</div>
		    	
		    	<div class="col2">
	        		<div class="labels"><label for="flatStatus">Flat Status</label></div>
	        		<div class="fields">
		          		<select id="flatStatus" name="flatStatus"  <%if(!editFlatDetails) {%>disabled<%}%>>
		            		<option value="1" <%if("1".equals(flatDetailsVO.getFlatStatus())){ %>selected<%} %>>Sold</option>
		            		<option value="0" <%if("0".equals(flatDetailsVO.getFlatStatus())){ %>selected<%} %>>Unsold</option>
		          		</select>
	        		</div>
	
					<div class="labels"><label for="flatNumberPrefix2">Flat Number Prefix (more)</label></div>
	    	    	<div class="fields"><input type="text" id="flatNumberPrefix2" name="flatNumberPrefix2" value="<%=StringUtil.getStringForForm(flatDetailsVO.getFlatNumberPrefix2())%>" <%if(!editFlatDetails) {%>readonly<%}%>/></div>
	
	        		<div class="labels"><label for="areaInSqft">Area (sqft)</label></div>
	        		<div class="fields"><input type="text" id="areaInSqft" name="areaInSqft" value="<%=StringUtil.getStringForForm(flatDetailsVO.getAreaInSqft())%>" <%if(!editFlatDetails) {%>readonly<%}%>/></div>
		    	</div>
		    	
		    	<div class="col3">
		    		<div class="labels"><label for="flatId">Flat Id</label></div>
		    		<div class="fields"><input type="text" id="flatId" name="flatId" value="mgra/<%=StringUtil.getStringForForm(flatDetailsVO.getFlatId())%>" readonly/></div>
	
		        	<div class="labels"><label for="flatNumberSuffix1">Flat Number Suffix</label></div>
	    	    	<div class="fields"><input type="text" id="flatNumberSuffix1" name="flatNumberSuffix1" value="<%=StringUtil.getStringForForm(flatDetailsVO.getFlatNumberSuffix1())%>"  <%if(!editFlatDetails) {%>readonly<%}%>/></div>
		    	</div>
	
		    	<div class="col4">
	        		<div class="labels"><label for="createDate">Create Date</label></div>
	        		<div class="fields"><input type="date" id="createDate" name="createDate" value="<%=DateUtil.dateToString(flatDetailsVO.getCreateDate(), DateFormat.yyyy_MM_dd)%>" readonly/></div>
	        
		        	<div class="labels"><label for="flatNumberSuffix2">Flat Number Suffix (more)</label></div>
	    	    	<div class="fields"><input type="text" id="flatNumberSuffix2" name="flatNumberSuffix2" value="<%=StringUtil.getStringForForm(flatDetailsVO.getFlatNumberSuffix2())%>" <%if(!editFlatDetails) {%>readonly<%}%>/></div>
		    	</div>
		    	<div class="horSeparator5px"></div>
		        <div class="formActions">
					<%if(editFlatDetails) {%>
						<div class="fields"><input name="Submit" type="submit" value="Update" /></div>
					<%} else {%>
						<div class="fields"><input type="button" id="editFlatDetails" name="editFlatDetails" value="Edit Flat Details" onclick="overlay('login');setRequestType(this)"></input></div>
					<%} %>
		        </div>
	        </form>
		</div>
		
		<div class="horSeparator10px"></div>

    	<div id="dwellersDetails" class="formContainer">
    		<form id="UpdateDwellerDetails" name="UpdateDwellerDetails" method="post" action="./ServerAdapter">
    			<input name="ServiceName" type="hidden" value="UpdateDwellerDetails" />
				<input name=dwellerIDKey type="hidden" value="<%=dwellersMasterVO.getDwellersId()%>"/>
				<input name=flatIdKey type="hidden" value="<%=dwellersMasterVO.getFlatId()%>"/>
				<div class="formHeader">
					<b>Dweller Details</b>
					<span>[<a title="Help" href='#' ><b>?</b></a>]</span>
				</div>
		    	<div class="col1">
	        		<div class="labels"><label for="nameSalutation">Name Salutation</label></div>
	        		<div class="fields">
	        			<input type="text" id="nameSalutation" name="nameSalutation" value="<%=StringUtil.getStringForForm(dwellersMasterVO.getNameSalutation())%>" <%if(!editDwellerFlag) {%>readonly<%}%>/>
	        		</div>
	        		
	        		<div class="labels"><label for="preferredName">Preferred Name</label></div>
					<div class="fields">
						<input type="text" id="preferredName" name="preferredName" value="<%=StringUtil.getStringForForm(dwellersMasterVO.getNamePreferred())%>" <%if(!editDwellerFlag) {%>readonly<%}%>/>
					</div>
					
					<div class="labels"><label for="businessPhone">Business Phone</label></div>
					<div class="fields">
						<input type="tel" id="businessPhone" name="businessPhone" value="<%=StringUtil.getStringForForm(dwellersMasterVO.getPhoneBusiness())%>" <%if(!editDwellerFlag) {%>readonly<%}%>/>
					</div>
					
					<div class="labels"><label for="primaryEmail">Primary Email</label></div>
					<div class="fields">
						<input type="email" id="primaryEmail" name="primaryEmail" value="<%=StringUtil.getStringForForm(dwellersMasterVO.getEmailPrimary())%>" <%if(!editDwellerFlag) {%>readonly<%}%>/>
					</div>
				</div>
		    	
				<div class="col2">
					<div class="labels"><label for="firstName">First Name</label></div>
					<div class="fields">
						<input type="text" id="firstName" name="firstName" value="<%=StringUtil.getStringForForm(dwellersMasterVO.getNameFirst())%>" <%if(!editDwellerFlag) {%>readonly<%}%>/>
					</div>
					<div class="labels"><label for="gender">Gender</label></div>
					<div class="fields">
						<select id="gender" name="gender" <%if(!editDwellerFlag) {%>disabled<%}%>>
							<option value="M" <% if("M".equals(dwellersMasterVO.getGender())){%>selected<%} %>>Male</option>
							<option value="F" <% if("F".equals(dwellersMasterVO.getGender())){%>selected<%} %>>Female</option>
						</select>
					</div>
					
					<div class="labels"><label for="businessPhoneExtn">Extn</label></div>
					<div class="fields">
						<input type="text" id="businessPhoneExtn" name="businessPhoneExtn" value="<%=StringUtil.getStringForForm(dwellersMasterVO.getPhoneBusinessExtn())%>" <%if(!editDwellerFlag) {%>readonly<%}%>/>
					</div>
					
					<div class="labels"><label for="secondaryEmail">Secondary Email</label></div>
					<div class="fields">
						<input type="email" id="secondaryEmail" name="secondaryEmail" value="<%=StringUtil.getStringForForm(dwellersMasterVO.getEmailSecondary())%>" <%if(!editDwellerFlag) {%>readonly<%}%>/>
					</div>
				</div>
				
				<div class="col3">
					<div class="labels"><label for="middleName">Middle Name</label></div>
					<div class="fields">
						<input type="text" id="middleName" name="middleName" value="<%=StringUtil.getStringForForm(dwellersMasterVO.getNameMiddle())%>" <%if(!editDwellerFlag) {%>readonly<%}%>/>
					</div>
					
					<div class="labels"><label for="dateOfBirth">Birthday</label></div>
					<div class="fields">
						<select class="mini" id="birthMonth" name="birthMonth" onchange="populateDate(this)" <%if(!editDwellerFlag) {%>disabled<%}%>>
						<option value="0" <%if(birthMonth == null) {%>selected<%} %>>Month</option>
						<option value="1" <%if("1".equals(birthMonth)) {%>selected<%} %>>Jan</option>
						<option value="2" <%if("2".equals(birthMonth)) {%>selected<%} %>>Feb</option>
						<option value="3" <%if("3".equals(birthMonth)) {%>selected<%} %>>Mar</option>
						<option value="4" <%if("4".equals(birthMonth)) {%>selected<%} %>>Apr</option>
						<option value="5" <%if("5".equals(birthMonth)) {%>selected<%} %>>May</option>
						<option value="6" <%if("6".equals(birthMonth)) {%>selected<%} %>>Jun</option>
						<option value="7" <%if("7".equals(birthMonth)) {%>selected<%} %>>Jul</option>
						<option value="8" <%if("8".equals(birthMonth)) {%>selected<%} %>>Aug</option>
						<option value="9" <%if("9".equals(birthMonth)) {%>selected<%} %>>Sep</option>
						<option value="10" <%if("10".equals(birthMonth)) {%>selected<%} %>>Oct</option>
						<option value="11" <%if("11".equals(birthMonth)) {%>selected<%} %>>Nov</option>
						<option value="12" <%if("12".equals(birthMonth)) {%>selected<%} %>>Dec</option>
						</select>
						<select class="mini" id="birthDate" name="birthDate" <%if(!editDwellerFlag) {%>disabled<%}%>>
						<option value="0" <%if(birthDate == null) {%>selected<%} %>>Date</option>
						<option value="1" <%if("1".equals(birthDate)) {%>selected<%} %>>1</option>
						<option value="2" <%if("2".equals(birthDate)) {%>selected<%} %>>2</option>
						<option value="3" <%if("3".equals(birthDate)) {%>selected<%} %>>3</option>
						<option value="4" <%if("4".equals(birthDate)) {%>selected<%} %>>4</option>
						<option value="5" <%if("5".equals(birthDate)) {%>selected<%} %>>5</option>
						<option value="6" <%if("6".equals(birthDate)) {%>selected<%} %>>6</option>
						<option value="7" <%if("7".equals(birthDate)) {%>selected<%} %>>7</option>
						<option value="8" <%if("8".equals(birthDate)) {%>selected<%} %>>8</option>
						<option value="9" <%if("9".equals(birthDate)) {%>selected<%} %>>9</option>
						<option value="10" <%if("10".equals(birthDate)) {%>selected<%} %>>10</option>
						<option value="11" <%if("11".equals(birthDate)) {%>selected<%} %>>11</option>
						<option value="12" <%if("12".equals(birthDate)) {%>selected<%} %>>12</option>
						<option value="13" <%if("13".equals(birthDate)) {%>selected<%} %>>13</option>
						<option value="14" <%if("14".equals(birthDate)) {%>selected<%} %>>14</option>
						<option value="15" <%if("15".equals(birthDate)) {%>selected<%} %>>15</option>
						<option value="16" <%if("16".equals(birthDate)) {%>selected<%} %>>16</option>
						<option value="17" <%if("17".equals(birthDate)) {%>selected<%} %>>17</option>
						<option value="18" <%if("18".equals(birthDate)) {%>selected<%} %>>18</option>
						<option value="19" <%if("19".equals(birthDate)) {%>selected<%} %>>19</option>
						<option value="20" <%if("20".equals(birthDate)) {%>selected<%} %>>20</option>
						<option value="21" <%if("21".equals(birthDate)) {%>selected<%} %>>21</option>
						<option value="22" <%if("22".equals(birthDate)) {%>selected<%} %>>22</option>
						<option value="23" <%if("23".equals(birthDate)) {%>selected<%} %>>23</option>
						<option value="24" <%if("24".equals(birthDate)) {%>selected<%} %>>24</option>
						<option value="25" <%if("25".equals(birthDate)) {%>selected<%} %>>25</option>
						<option value="26" <%if("26".equals(birthDate)) {%>selected<%} %>>26</option>
						<option value="27" <%if("27".equals(birthDate)) {%>selected<%} %>>27</option>
						<option value="28" <%if("28".equals(birthDate)) {%>selected<%} %>>28</option>
						<option value="29" <%if("29".equals(birthDate)) {%>selected<%} %>>29</option>
						<% if("1".equals(birthMonth) || "3".equals(birthMonth) || "5".equals(birthMonth) || "7".equals(birthMonth) || "8".equals(birthMonth) || "10".equals(birthMonth) || "12".equals(birthMonth)){ %>
							<option value="30" <%if("30".equals(birthDate)) {%>selected<%} %>>30</option>
							<option value="31" <%if("31".equals(birthDate)) {%>selected<%} %>>31</option>
						<%}else if("4".equals(birthMonth) || "6".equals(birthMonth) || "9".equals(birthMonth) || "11".equals(birthMonth) ){%>
							<option value="30" <%if("30".equals(birthDate)) {%>selected<%} %>>30</option>				
						<%}%>
						</select>
					</div>
					
					<div class="labels"><label for="mobile">Mobile</label></div>
					<div class="fields">
						<input type="tel" id="mobile" name="mobile" value="<%=StringUtil.getStringForForm(dwellersMasterVO.getPhoneMobile())%>" <%if(!editDwellerFlag) {%>readonly<%}%>/>
					</div>
					
					<div class="labels"><label for="preferredContact">Preferred Contact</label></div>
					<div class="fields">
		          		<select id="preferredContact" name="preferredContact" <%if(!editDwellerFlag) {%>disabled<%}%>>
		            		<option value="0" <% if("0".equals(dwellersMasterVO.getPreferredContact())){%>selected<%} %>>None</option>
		            		<option value="1" <% if("1".equals(dwellersMasterVO.getPreferredContact())){%>selected<%} %>>Residence Phone</option>
		            		<option value="2" <% if("2".equals(dwellersMasterVO.getPreferredContact())){%>selected<%} %>>Business Phone</option>
		            		<option value="3" <% if("3".equals(dwellersMasterVO.getPreferredContact())){%>selected<%} %>>Mobile</option>
		            		<option value="4" <% if("4".equals(dwellersMasterVO.getPreferredContact())){%>selected<%} %>>Primary Email</option>
		            		<option value="5" <% if("5".equals(dwellersMasterVO.getPreferredContact())){%>selected<%} %>>Secondary Email</option>
		          		</select>
		          	</div>
				</div>

				<div class="col4">
					<div class="labels"><label for="lastName">Last Name</label></div>
					<div class="fields">
						<input type="text" id="lastName" name="lastName" value="<%=StringUtil.getStringForForm(dwellersMasterVO.getNameLast())%>" <%if(!editDwellerFlag) {%>readonly<%} else {%>required<%}%>/>
					</div>
					
					<div class="labels"><label for="dateOfAnniversary">Anniversary</label></div>
					<div class="fields">
						<select class="mini" id="anniversaryMonth" name="anniversaryMonth" onchange="populateDate(this)" <%if(!editDwellerFlag) {%>disabled<%}%>>
						<option value="0" <%if(anniversaryMonth == null) {%>selected<%} %>>Month</option>
						<option value="1" <%if("1".equals(anniversaryMonth)) {%>selected<%} %>>Jan</option>
						<option value="2" <%if("2".equals(anniversaryMonth)) {%>selected<%} %>>Feb</option>
						<option value="3" <%if("3".equals(anniversaryMonth)) {%>selected<%} %>>Mar</option>
						<option value="4" <%if("4".equals(anniversaryMonth)) {%>selected<%} %>>Apr</option>
						<option value="5" <%if("5".equals(anniversaryMonth)) {%>selected<%} %>>May</option>
						<option value="6" <%if("6".equals(anniversaryMonth)) {%>selected<%} %>>Jun</option>
						<option value="7" <%if("7".equals(anniversaryMonth)) {%>selected<%} %>>Jul</option>
						<option value="8" <%if("8".equals(anniversaryMonth)) {%>selected<%} %>>Aug</option>
						<option value="9" <%if("9".equals(anniversaryMonth)) {%>selected<%} %>>Sep</option>
						<option value="10" <%if("10".equals(anniversaryMonth)) {%>selected<%} %>>Oct</option>
						<option value="11" <%if("11".equals(anniversaryMonth)) {%>selected<%} %>>Nov</option>
						<option value="12" <%if("12".equals(anniversaryMonth)) {%>selected<%} %>>Dec</option>
						</select>
						<select class="mini" id="anniversaryDate" name="anniversaryDate" <%if(!editDwellerFlag) {%>disabled<%}%>>
						<option value="0" <%if(anniversaryDate == null) {%>selected<%} %>>Date</option>
						<option value="1" <%if("1".equals(anniversaryDate)) {%>selected<%} %>>1</option>
						<option value="2" <%if("2".equals(anniversaryDate)) {%>selected<%} %>>2</option>
						<option value="3" <%if("3".equals(anniversaryDate)) {%>selected<%} %>>3</option>
						<option value="4" <%if("4".equals(anniversaryDate)) {%>selected<%} %>>4</option>
						<option value="5" <%if("5".equals(anniversaryDate)) {%>selected<%} %>>5</option>
						<option value="6" <%if("6".equals(anniversaryDate)) {%>selected<%} %>>6</option>
						<option value="7" <%if("7".equals(anniversaryDate)) {%>selected<%} %>>7</option>
						<option value="8" <%if("8".equals(anniversaryDate)) {%>selected<%} %>>8</option>
						<option value="9" <%if("9".equals(anniversaryDate)) {%>selected<%} %>>9</option>
						<option value="10" <%if("10".equals(anniversaryDate)) {%>selected<%} %>>10</option>
						<option value="11" <%if("11".equals(anniversaryDate)) {%>selected<%} %>>11</option>
						<option value="12" <%if("12".equals(anniversaryDate)) {%>selected<%} %>>12</option>
						<option value="13" <%if("13".equals(anniversaryDate)) {%>selected<%} %>>13</option>
						<option value="14" <%if("14".equals(anniversaryDate)) {%>selected<%} %>>14</option>
						<option value="15" <%if("15".equals(anniversaryDate)) {%>selected<%} %>>15</option>
						<option value="16" <%if("16".equals(anniversaryDate)) {%>selected<%} %>>16</option>
						<option value="17" <%if("17".equals(anniversaryDate)) {%>selected<%} %>>17</option>
						<option value="18" <%if("18".equals(anniversaryDate)) {%>selected<%} %>>18</option>
						<option value="19" <%if("19".equals(anniversaryDate)) {%>selected<%} %>>19</option>
						<option value="20" <%if("20".equals(anniversaryDate)) {%>selected<%} %>>20</option>
						<option value="21" <%if("21".equals(anniversaryDate)) {%>selected<%} %>>21</option>
						<option value="22" <%if("22".equals(anniversaryDate)) {%>selected<%} %>>22</option>
						<option value="23" <%if("23".equals(anniversaryDate)) {%>selected<%} %>>23</option>
						<option value="24" <%if("24".equals(anniversaryDate)) {%>selected<%} %>>24</option>
						<option value="25" <%if("25".equals(anniversaryDate)) {%>selected<%} %>>25</option>
						<option value="26" <%if("26".equals(anniversaryDate)) {%>selected<%} %>>26</option>
						<option value="27" <%if("27".equals(anniversaryDate)) {%>selected<%} %>>27</option>
						<option value="28" <%if("28".equals(anniversaryDate)) {%>selected<%} %>>28</option>
						<option value="29" <%if("29".equals(anniversaryDate)) {%>selected<%} %>>29</option>
						<% if("1".equals(anniversaryMonth) || "3".equals(anniversaryMonth) || "5".equals(anniversaryMonth) || "7".equals(anniversaryMonth) || "8".equals(anniversaryMonth) || "10".equals(anniversaryMonth) || "12".equals(anniversaryMonth)){ %>
							<option value="30" <%if("30".equals(anniversaryDate)) {%>selected<%} %>>30</option>
							<option value="31" <%if("31".equals(anniversaryDate)) {%>selected<%} %>>31</option>
						<%}else if("4".equals(anniversaryMonth) || "6".equals(anniversaryMonth) || "9".equals(anniversaryMonth) || "11".equals(anniversaryMonth) ){%>
							<option value="30" <%if("30".equals(anniversaryDate)) {%>selected<%} %>>30</option>				
						<%}%>
						</select>
					</div>
					
					<div class="labels"><label for="resiPhone">Phone Residence</label></div>
					<div class="fields">
						<input type="tel" id="resiPhone" name="resiPhone" value="<%=StringUtil.getStringForForm(dwellersMasterVO.getPhoneResidence())%>" <%if(!editDwellerFlag) {%>readonly<%}%>/>
					</div>
				</div>
		    	<div class="horSeparator5px"></div>
		        <div class="formActions">
		        	<div class="fields">
						<%if(editDwellerFlag) {%>
							<input name="Submit" type="submit" value="Update" />
						<%} else {%>
							<input type="button" id="editDwellerDetails" name="editDwellerDetails" value="Edit Dweller Details" onclick="overlay('login');setRequestType(this)"></input>
						<%} %>
					</div>
		        </div>
	        </form>
		</div>
		
		<div class="horSeparator10px"></div>

    	<div id="financialDetails" class="formContainer">
        	<form id="getPaymentInfo" name="getPaymentInfo" method="post" action="./ServerAdapter">
        		<input name="ServiceName" type="hidden" value="GetPaymentInfo" />
        		<input name="flatId" type="hidden" value="<%=StringUtil.getStringForForm(flatDetailsVO.getFlatId())%>" />
				<div class="formHeader">
					<b>Financial Details</b>
					<span>[<a title="Help" href='#' ><b>?</b></a>]</span>
				</div>
		    	<div class="col1">
					<div class="labels"><label for="currentBalance">Current Balance</label></div>
					<div class="fields"><input type="text" id="currentBalance" name="currentBalance" value="<%=StringUtil.getStringForForm(financialDetailsVO.getCurrentBalance())%>" readonly/></div>
				</div>
				
		    	<div class="col2">
					<div class="labels"></div>
		    		<div class="fields"><a href="bill-details.jsp?flatId=<%=flatDetailsVO.getFlatId()%>">[View Last bill...]</a></div>
		    	</div>
		    	
		    	<div class="col3">
		    	</div>
				<% int billStatus = Integer.valueOf(request.getAttribute("billStatus").toString());%>
		    	<div class="col4">
		    	</div>
		    	<div class="horSeparator5px"></div>
		        <div class="formActions">
		        	<div class="fields">
						<input name="Submit" type="submit" value="Pay Bill" <%if(!(billStatus>0)){ %>disabled<%} %>/> &nbsp;&nbsp;<%if(!(billStatus>0)){ %>Finalize the bills for making payments<%}%>
					</div>
		        </div>
	        </form>
		</div>
<%
	}
	else {
%>
      No records found.
<%
	}
%>
    </div>
    <div class="footer">
      <%@include file="/common/static/footer.html"%>
    </div>
</body>
</html>