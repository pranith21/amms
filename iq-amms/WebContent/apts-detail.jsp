<%@ page import="org.iq.exception.CacheException,org.iq.exception.DbException,org.iq.service.ServiceConstants,com.iq.amms.services.helpers.FinancialsHelper,com.iq.amms.services.helpers.FlatsHelper,com.iq.amms.services.helpers.GetAllFlats,com.iq.amms.services.helpers.GetApartmentDetails,com.iq.amms.services.helpers.BillHelper,com.iq.amms.services.helpers.SystemHelper,com.iq.amms.services.Login,com.iq.amms.services.LookupApartment,com.iq.amms.services.UpdateSystemParams,com.iq.amms.SystemConf,com.iq.amms.valueobjects.AllFlatsVO,com.iq.amms.valueobjects.ApartmentMasterVO,com.iq.amms.valueobjects.BaseVO,com.iq.amms.valueobjects.DwellersMasterVO,com.iq.amms.valueobjects.FinancialDetailsVO,com.iq.amms.valueobjects.FlatDetailsVO,com.iq.amms.valueobjects.BillMasterVO,com.iq.amms.valueobjects.BillDetailsVO,com.iq.amms.valueobjects.SessionVO,com.iq.amms.valueobjects.SystemParamsVO,org.iq.util.date.DateUtil.DateFormat,org.iq.util.date.DateUtil.DatePart,org.iq.util.date.DateUtil.Month,org.iq.util.date.DateUtil,org.iq.util.file.FileUtil,org.iq.util.http.HttpUtil,org.iq.util.money.Money,org.iq.util.money.MoneyUtil,org.iq.util.object.ObjectUtil,org.iq.util.string.StringUtil,org.iq.util.UtilConstants.IPVersion,org.iq.util.UtilConstants,org.iq.util.version.GenerateImportString,org.iq.util.version.Version,org.iq.util.xml.XmlHandler,org.iq.util.xml.XmlUtil,java.util.ArrayList" %>
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
	Boolean editDwellerFlag = (Boolean)request.getAttribute("editDwellerFlag");
	
	if (flatDetailsVO != null) {
%>
		<div id="flatDetails" class="formContainer">
			<form>
				<div class="formHeader">
					<b>Flat Details</b>
					<span>[<a title="Help" href='#' ><b>?</b></a>]</span>
				</div>
		    	<div class="col1">
	        		<div class="labels"><label for="flatNumber">Flat Number</label></div>
	        		<div class="fields"><input type="text" id="flatNumber" name="flatNumber" value="<%=StringUtil.getStringForForm(flatDetailsVO.getFlatNumber())%>" readonly/></div>
	
	        		<div class="labels"><label for="flatNumberPrefix1">Flat Number Prefix</label></div>
	        		<div class="fields"><input type="text" id="flatNumberPrefix1" name="flatNumberPrefix1" value="<%=StringUtil.getStringForForm(flatDetailsVO.getFlatNumberPrefix1())%>" readonly/></div>
	
	        		<div class="labels"><label for="floorNumber">Floor Number</label></div>
	        		<div class="fields"><input type="text" id="floorNumber" name="floorNumber" value="<%=StringUtil.getStringForForm(flatDetailsVO.getFloorNumber())%>" readonly/></div>
		    	</div>
		    	
		    	<div class="col2">
	        		<div class="labels"><label for="flatStatus">Flat Status</label></div>
	        		<div class="fields">
		          		<select id="flatStatus" name="flatStatus" disabled>
		            		<option value="0">Sold</option>
		            		<option value="1">Unsold</option>
		          		</select>
	        		</div>
	
					<div class="labels"><label for="flatNumberPrefix2">Flat Number Prefix (more)</label></div>
	    	    	<div class="fields"><input type="text" id="flatNumberPrefix2" name="flatNumberPrefix2" value="<%=StringUtil.getStringForForm(flatDetailsVO.getFlatNumberPrefix2())%>" readonly/></div>
	
	        		<div class="labels"><label for="areaInSqft">Area (sqft)</label></div>
	        		<div class="fields"><input type="text" id="areaInSqft" name="areaInSqft" value="<%=StringUtil.getStringForForm(flatDetailsVO.getAreaInSqft())%>" readonly/></div>
		    	</div>
		    	
		    	<div class="col3">
		    		<div class="labels"><label for="flatId">Flat Id</label></div>
		    		<div class="fields"><input type="text" id="flatId" name="flatId" value="mgra/<%=StringUtil.getStringForForm(flatDetailsVO.getFlatId())%>" readonly/></div>
	
		        	<div class="labels"><label for="flatNumberSuffix1">Flat Number Suffix</label></div>
	    	    	<div class="fields"><input type="text" id="flatNumberSuffix1" name="flatNumberSuffix1" value="<%=StringUtil.getStringForForm(flatDetailsVO.getFlatNumberSuffix1())%>" readonly/></div>
		    	</div>
	
		    	<div class="col4">
	        		<div class="labels"><label for="createDate">Create Date</label></div>
	        		<div class="fields"><input type="text" id="createDate" name="createDate" value="<%=DateUtil.dateToString(flatDetailsVO.getCreateDate(), DateFormat.MMM_dd_yyyy)%>" readonly readonly/></div>
	        
		        	<div class="labels"><label for="flatNumberSuffix2">Flat Number Suffix (more)</label></div>
	    	    	<div class="fields"><input type="text" id="flatNumberSuffix2" name="flatNumberSuffix2" value="<%=StringUtil.getStringForForm(flatDetailsVO.getFlatNumberSuffix2())%>" readonly/></div>
		    	</div>
		    	<div class="horSeparator5px"></div>
		        <div class="formActions">
		        	<div class="fields"><input type="submit" value="Edit Flat Details" disabled /></div>
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
					
					<div class="labels"><label for="dateOfBirth">Birthday(<%=DateFormat.MMM_dd_yyyy.toString()%>)</label></div>
					<div class="fields">
						<input type="text" id="dateOfBirth" name="dateOfBirth" value="<%=StringUtil.getStringForForm(DateUtil.dateToString(dwellersMasterVO.getDateOfBirth(), DateFormat.MMM_dd_yyyy))%>" <%if(!editDwellerFlag) {%>readonly<%}%>/>
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
					
					<div class="labels"><label for="dateOfAnniversary">Anniversary(<%=DateFormat.MMM_dd_yyyy.toString()%>)</label></div>
					<div class="fields">
						<input type="text" id="dateOfAnniversary" name="dateOfAnniversary" value="<%=StringUtil.getStringForForm(DateUtil.dateToString(dwellersMasterVO.getDateOfAnniversary(), DateFormat.MMM_dd_yyyy))%>" <%if(!editDwellerFlag) {%>readonly<%}%>/>
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
							<input type="button" id="editFlatDetails" name="editFlatDetails" value="Edit Dweller Details" onclick="overlay('login')"></input>
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
		    	</div>
		    	
		    	<div class="col3">
		    	</div>
	
		    	<div class="col4">
		    	</div>
		    	<div class="horSeparator5px"></div>
		        <div class="formActions">
		        	<div class="fields">
						<input name="Submit" type="submit" value="Pay Bill" />
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
    
<!--LOGIN OVERLAY STARTS-->
<div id="login" class="overlayContainer">
	<div class="overlayContentWrapper">
		<div class="overlayHeader">
			<b>Super User Login</b>
			<span>[<a href='#' onclick="overlay('login')" accessKey="ESC"><b>X</b></a>]</span>
		</div>
		<div class="overlayContent">
		  <form id="loginForm" name="loginForm" method="post" action="./ServerAdapter">
		    <input name="ServiceName" type="hidden" value="Login" />
		    <input name="operationName" type="hidden" value="edit-apts-detail" />
		    <input name=flatID type="hidden" value="<%=dwellersMasterVO.getFlatId()%>"/>
            <div class="labels"><label for="username">Username</label></div>
          	<div class="fields"><input type="text" id="username" name="username" class="input" maxlength="2" /></div>
            <div class="labels"><label for="password">Password</label></div>
          	<div class="fields"><input type="password" id="password" name="password" class="input" maxlength="2" /></div>
	        <div class="fields"><input name="Submit" type="submit" value="Login" /></div>
		  </form>
		</div>
	</div>
</div>
<!--LOGIN OVERLAY ENDS-->

    <div class="footer">
      <%@include file="/common/static/footer.html"%>
    </div>
</body>
</html>