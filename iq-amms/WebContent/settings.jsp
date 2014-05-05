<%@page import="com.iq.amms.services.helpers.ApartmentMasterParamsHelper"%>
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
function validDate(enteredDate){
	var date = enteredDate.value;
	if(date > 31){
		alert("Value should be less than or equal to 31");
		enteredDate.value="";
		enteredDate.focus();
	}else if(date > 28 && date <=31){
		if(!confirm("Date will be set to last day of the month if the date specified is more than last day of the month")){
			enteredDate.value="";
			enteredDate.focus();
		}
	}	
}
function setRequestType(selectedOption){
	document.getElementById("operationType").value = selectedOption.value;
}
</script>
</head>
<body>
    <div class="header">
      <%@include file="/common/static/header.html"%>
    </div>
    <div class="bodycontent">
<%
	SystemHelper sysHelper = new SystemHelper();
	ApartmentMasterParamsHelper apartmentMasterHelper = new ApartmentMasterParamsHelper();
    Double monthlyRate = sysHelper.getMonthlyMaintenanceRate();
    Double latePaymentAmount = sysHelper.getLatePaymentAmount();
    Double bounceCharges = sysHelper.getBounceCharge();
    Boolean billGenerationSMSSelected = "1".equals(apartmentMasterHelper.getParam(ApartmentMasterParamsHelper.BILL_GENERATION_SMS));
    Boolean billGenerationMailSelected = "1".equals(apartmentMasterHelper.getParam(ApartmentMasterParamsHelper.BILL_GENERATION_MAIL));
    Boolean latePaymentSMSSelected = "1".equals(apartmentMasterHelper.getParam(ApartmentMasterParamsHelper.LATE_PAYMENT_SMS));
    Boolean latePaymentMailSelected = "1".equals(apartmentMasterHelper.getParam(ApartmentMasterParamsHelper.LATE_PAYMENT_MAIL));
    Boolean paymentSMSSelected = "1".equals(apartmentMasterHelper.getParam(ApartmentMasterParamsHelper.PAYMENTS_SMS));
    Boolean paymentMailSelected = "1".equals(apartmentMasterHelper.getParam(ApartmentMasterParamsHelper.PAYMENTS_MAIL));
    Boolean birthdaySMSSelected = "1".equals(apartmentMasterHelper.getParam(ApartmentMasterParamsHelper.BIRTHDAY_SMS));
    Boolean birthdayMailSelected = "1".equals(apartmentMasterHelper.getParam(ApartmentMasterParamsHelper.BIRTHDAY_MAIL));
    Boolean anniversarySMSSelected = "1".equals(apartmentMasterHelper.getParam(ApartmentMasterParamsHelper.ANNIVERSARY_SMS));
    Boolean anniversaryMailSelected = "1".equals(apartmentMasterHelper.getParam(ApartmentMasterParamsHelper.ANNIVERSARY_MAIL));
    Boolean latePaymentReminderSMSSelected = "1".equals(apartmentMasterHelper.getParam(ApartmentMasterParamsHelper.LATE_PAYMENT_REMINDER_SMS));
    Boolean latePaymentReminderMailSelected = "1".equals(apartmentMasterHelper.getParam(ApartmentMasterParamsHelper.LATE_PAYMENT_REMINDER_MAIL));
	Boolean editSettingsFlag = false;
	if(request.getAttribute("editSettingsFlag")!= null){
		editSettingsFlag = (Boolean)request.getAttribute("editSettingsFlag");
	}
%>
		<div id="Settings" class="formContainer">
			<div class="formHeader">
				<b>Billing Details</b>
				<span>[<a title="Help" href='#'><b>?</b></a>]</span>
			</div>
			<div class="col1">
				<div class="sidelabels">Last bill generation date</div>
				<div class="sidelabels">Next late payment generation date</div>
				<div class="sidelabels">Next bill generation date</div>
		  	</div>
			<div class="col2">
		  		<div class="sidefields"><input type="text" value="<%=sysHelper.getLastBillDate()%>" readonly/></div>
		  		<div class="sidefields"><input type="text" value="<%=DateUtil.dateToString(sysHelper.getLatePaymentDueDate(), DateFormat.MMM_dd_yyyy)%>" readonly/></div>
		  		<div class="sidefields"><input type="text" value="<%=DateUtil.dateToString(sysHelper.getNextBillDate(), DateFormat.MMM_dd_yyyy)%>" readonly/></div>
		  	</div>
			<div class="col3">
				<div class="sidelabels">Late payment processed</div>
				<div class="sidelabels">Late payment reminder</div>
		  	</div>
			<div class="col4">
		  		<div class="sidefields"><input type="text" value="<%=sysHelper.checkLatePaymentProcessed()%>" readonly/></div>
		  		<div class="sidefields"><input type="text" value="<%=apartmentMasterHelper.getParam(ApartmentMasterParamsHelper.LATE_PAYMENT_REMINDER_SENT)%>" readonly/></div>
		  	</div>
			<div class="horSeparator5px"></div>
			<div class=""></div>
		</div>

		<div class="horSeparator10px"></div>

		<form id="settings" name="settings" method="post" action="./ServerAdapter">
			<div id="Settings" class="formContainer">
		  		<input name="ServiceName" type="hidden" value="UpdateApartmentMasterParams" />
				<div class="formHeader">
					<b>Financial Settings</b>
					<span>[<a title="Help" href='#' ><b>?</b></a>]</span>
				</div>
			    <div class="col1">
			  		<div class="sidelabels"><label for="ratePerSqft">Maintenance Rate (/sqft)</label></div>
			  		<div class="sidelabels"><label for="latePaymentCalculationDate">Late Payment Calculation Date</label></div>
			  		<div class="sidelabels"><label for="latePaymentDisplayDate">Late Payment Display Date</label></div>
			  		<div class="sidelabels"><label for="latePaymentMinimumBalance">Late Payment Min Balance</label></div>
			  		<div class="sidelabels"><label for="latePaymentAmount">Late Payment Amt (/month)</label></div>
			  		<div class="sidelabels"><label for="latePaymentReminderDays">Late Payment Reminder Days</label></div>
			  		<div class="sidelabels"><label for="bounceCharges">Bounce Charges (/cheque)</label></div>
				</div>
			    <div class="col2">
			  		<div class="sidefields"><input type="text" id="ratePerSqft" name="ratePerSqft" value="<%=(monthlyRate>0)?monthlyRate:""%>"  <%if(!editSettingsFlag) {%>readonly<%}%>/></div>
			  		<div class="sidefields"><input type="text" id="latePaymentCalculationDate" name="latePaymentCalculationDate" value="<%=apartmentMasterHelper.getParam(ApartmentMasterParamsHelper.LATE_PAYMENT_CALCULATION_DATE)%>" onchange="validDate(this)" <%if(!editSettingsFlag) {%>readonly<%}%>/></div>
			  		<div class="sidefields"><input type="text" id="latePaymentDisplayDate" name="latePaymentDisplayDate" value="<%=apartmentMasterHelper.getParam(ApartmentMasterParamsHelper.LATE_PAYMENT_DISPLAY_DATE)%>" onchange="validDate(this)" <%if(!editSettingsFlag) {%>readonly<%}%>/></div>
		   			<div class="sidefields"><input type="text" id="latePaymentMinimumBalance" name="latePaymentMinimumBalance" value="<%=apartmentMasterHelper.getParam(ApartmentMasterParamsHelper.LATE_PAYMENT_MINIMUM_BALANCE)%>" <%if(!editSettingsFlag) {%>readonly<%}%>/></div>
		   			<div class="sidefields"><input type="text" id="latePaymentAmount" name="latePaymentAmount" value="<%=(latePaymentAmount>0)?latePaymentAmount:""%>"  <%if(!editSettingsFlag) {%>readonly<%}%>/></div>
		   			<div class="sidefields"><input type="text" id="latePaymentReminderDays" name="latePaymentReminderDays" value="<%=apartmentMasterHelper.getParam(ApartmentMasterParamsHelper.LATE_PAYMENT_REMINDER_DAYS)%>" <%if(!editSettingsFlag) {%>readonly<%}%>/></div>
			  		<div class="sidefields"><input type="text" id="bounceCharges" name="bounceCharges" value="<%=bounceCharges%>" <%if(!editSettingsFlag) {%>readonly<%}%>/></div>
				</div>
			<div class="horSeparator5px"></div>
			<div class=""></div>
		</div>

		<div class="horSeparator10px"></div>

		<div id="Settings" class="formContainer">
			<div class="formHeader">
				<b>Communication Settings</b>
				<span>[<a title="Help" href='#' ><b>?</b></a>]</span>
			</div>
		    <div class="col1">
		  		<div class="labels"></div>
		  		<div class="sidelabels"><label for="billGeneration">Bill Generation</label></div>
	   			<div class="sidelabels"><label for="latePayment">Late Payments</label></div>
	   			<div class="sidelabels"><label for="latePaymentReminder">Late Payment Reminder</label></div>
		  		<div class="sidelabels"><label for="payments">Payments</label></div>
		  		<div class="sidelabels"><label for="birthday">Birthday</label></div>
		  		<div class="sidelabels"><label for="anniversary">Anniversary</label></div>
			</div>
		    <div class="col2">
		  		<div class="labels"><label for="billGeneration">SMS</label></div>
		  		<div class="sidefields"><input type="checkbox" id="billGenerationsms" name="billGenerationsms" value="1" <%if(billGenerationSMSSelected){ %> checked<%} %> <%if(!editSettingsFlag) {%> disabled<%}%>/></div>
		  		<div class="sidefields"><input type="checkbox" id="latePaymentsms" name="latePaymentsms" value="1" <%if(latePaymentSMSSelected){ %>checked<%} %> <%if(!editSettingsFlag) {%> disabled<%}%>/></div>
		  		<div class="sidefields"><input type="checkbox" id="latePaymentReminderSMS" name="latePaymentReminderSMS" value="1" <%if(latePaymentReminderSMSSelected){ %>checked<%} %> <%if(!editSettingsFlag) {%> disabled<%}%>/></div>
		  		<div class="sidefields"><input type="checkbox" id="paymentssms" name="paymentssms" value="1" <%if(paymentSMSSelected){ %>checked<%} %> <%if(!editSettingsFlag) {%> disabled<%}%>/></div>
		  		<div class="sidefields"><input type="checkbox" id="birthdaysms" name="birthdaysms" value="1" <%if(birthdaySMSSelected){ %>checked<%} %> <%if(!editSettingsFlag) {%> disabled<%}%>/></div>
		  		<div class="sidefields"><input type="checkbox" id="anniversarysms" name="anniversarysms" value="1" <%if(anniversarySMSSelected){ %>checked<%} %> <%if(!editSettingsFlag) {%> disabled<%}%>/></div>
			</div>
		    <div class="col3">
		  		<div class="labels"><label for="billGeneration">Mail</label></div>
		  		<div class="sidefields"><input type="checkbox" id="billGenerationmail" name="billGenerationmail" value="1" <%if(billGenerationMailSelected){ %>checked<%} %>  <%if(!editSettingsFlag) {%> disabled<%}%>/></div>
		  		<div class="sidefields"><input type="checkbox" id="latePaymentmail" name="latePaymentmail" value="1" <%if(latePaymentMailSelected){ %>checked<%} %>  <%if(!editSettingsFlag) {%> disabled<%}%>/></div>
		  		<div class="sidefields"><input type="checkbox" id="latePaymentReminderMail" name="latePaymentReminderMail" value="1" <%if(latePaymentReminderMailSelected){ %>checked<%} %>  <%if(!editSettingsFlag) {%> disabled<%}%>/></div>
		  		<div class="sidefields"><input type="checkbox" id="paymentsmail" name="paymentsmail" value="1" <%if(paymentMailSelected){ %>checked<%} %>  <%if(!editSettingsFlag) {%> disabled<%}%>/></div>
		  		<div class="sidefields"><input type="checkbox" id="birthdaymail" name="birthdaymail" value="1" <%if(birthdayMailSelected){ %>checked<%} %>  <%if(!editSettingsFlag) {%> disabled<%}%>/></div>
		  		<div class="sidefields"><input type="checkbox" id="anniversarymail" name="anniversarymail" value="1" <%if(anniversaryMailSelected){ %>checked<%} %>  <%if(!editSettingsFlag) {%> disabled<%}%>/></div>
			</div>
		    	<div class="horSeparator5px"></div>
		        <div class="formActions">
					<%if(editSettingsFlag) {%>
						<div class="fields"><input name="Submit" type="submit" value="Update" /></div>
					<%} else {%>
						<div class="fields"><input type="button" id="editFlatDetails" name="editSettings" value="Edit Settings" onclick="overlay('login');setRequestType(this)"></input></div>
					<%} %>
		        </div>
			</div>
		</form>
	</div>
    <div class="footer">
      <%@include file="/common/static/footer.html"%>
    </div>
</body>
</html>