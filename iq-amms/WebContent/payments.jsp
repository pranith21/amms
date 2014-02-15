<%@page import="com.iq.amms.services.helpers.PaymentsHelper"%>
<%@page import="com.iq.amms.valueobjects.PaymentMasterVO"%>
<%@ page import="org.iq.exception.CacheException,org.iq.exception.DbException,org.iq.service.ServiceConstants,com.iq.amms.services.helpers.FinancialsHelper,com.iq.amms.services.helpers.FlatsHelper,com.iq.amms.services.helpers.GetApartmentDetails,com.iq.amms.services.helpers.BillHelper,com.iq.amms.services.helpers.SystemHelper,com.iq.amms.services.Login,com.iq.amms.services.LookupApartment,com.iq.amms.services.UpdateSystemParams,com.iq.amms.SystemConf,com.iq.amms.valueobjects.AllFlatsVO,com.iq.amms.valueobjects.ApartmentMasterVO,com.iq.amms.valueobjects.BaseVO,com.iq.amms.valueobjects.DwellersMasterVO,com.iq.amms.valueobjects.FinancialDetailsVO,com.iq.amms.valueobjects.FlatDetailsVO,com.iq.amms.valueobjects.BillMasterVO,com.iq.amms.valueobjects.BillDetailsVO,com.iq.amms.valueobjects.SessionVO,com.iq.amms.valueobjects.SystemParamsVO,org.iq.util.date.DateUtil.DateFormat,org.iq.util.date.DateUtil.DatePart,org.iq.util.date.DateUtil.Month,org.iq.util.date.DateUtil,org.iq.util.file.FileUtil,org.iq.util.http.HttpUtil,org.iq.util.money.Money,org.iq.util.money.MoneyUtil,org.iq.util.object.ObjectUtil,org.iq.util.string.StringUtil,org.iq.util.UtilConstants.IPVersion,org.iq.util.UtilConstants,org.iq.util.version.GenerateImportString,org.iq.util.version.Version,org.iq.util.xml.XmlHandler,org.iq.util.xml.XmlUtil,java.util.ArrayList" %>
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
function newPaymentStatusSelectChange(paymentStatusSelect) {
	var selectedValue = paymentStatusSelect.value;
	if (selectedValue == '-1') {
		document.getElementById('lastPaymentFormSubmitButton').disabled = true;
	}
	else {
		document.getElementById('lastPaymentFormSubmitButton').disabled = false;
	}
}

function paymentModeSelectChange(paymentModeSelect) {
	var selectedValue = paymentModeSelect.value;
	if (selectedValue == '0') {
		var numberContent = '';
		document.getElementById('transNumber').innerHTML = numberContent;

		var nameContent = '';
		document.getElementById('transName').innerHTML = nameContent;
	}
	else if (selectedValue == '1') {
		var numberContent = '<div class="labels"><label for="chequeNumber">Cheque Number</label></div><div class="fields"><input type="text" name="chequeNumber" maxlength="8" required /></div>';
		document.getElementById('transNumber').innerHTML = numberContent;

		var nameContent = '<div class="labels"><label for="chequeDraweeBank">Cheque Drawee Bank</label></div><div class="fields"><input type="text" name="chequeDraweeBank" maxlength="100" required /></div>';
		document.getElementById('transName').innerHTML = nameContent;
	}
	else if (selectedValue == '2') {
		var numberContent = '<div class="labels"><label for="neftTransactionId">NEFT Transaction ID</label></div><div class="fields"><input type="text" name="neftTransactionId" maxlength="25" required /></div>';
		document.getElementById('transNumber').innerHTML = numberContent;

		var nameContent = '';
		document.getElementById('transName').innerHTML = nameContent;
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
	int flatId = (Integer)request.getAttribute("flatId");
	Double currBal = (Double)request.getAttribute("currentBalance");
	PaymentMasterVO paymentMasterVO = (PaymentMasterVO)request.getAttribute("paymentMasterVO");
	
	String serviceName = "";
	String buttonDisplay = "";
	Boolean statusMode = false;
	Boolean chequeMode = false;
	Boolean neftMode = false;
	String paymentStatus = "";
	int status = -1;
	String paymentMode = "";
	Boolean initialPayment = false;
	if(paymentMasterVO != null) {
		status = paymentMasterVO.getPaymentStatus();
		paymentMode = paymentMasterVO.getPaymentType();
	}else{
		initialPayment = true;
		statusMode = false;
	}
	
	if(status == 1) {
		paymentStatus = "Cheque Clearance Pending";
	}
	else if(status == 2) {
		paymentStatus = "Cheque Bounced";
	}
	else if (status == 3) {
		paymentStatus = "NEFT Verification Pending";
	}
	else if (status == 4) {
		paymentStatus = "NEFT Invalid";
	}
	else if (status == 5) {
		if ("0".equals(paymentMode)) {
			paymentStatus = "Paid";
		}else if ("1".equals(paymentMode)) {
			paymentStatus = "Cheque Cleared";
		}else if ("2".equals(paymentMode)) {
			paymentStatus = "NEFT Valid";
		}
	}
	
	if(status == -1 || status == 2 || status == 4 || status == 5) {
		serviceName = "GenerateReceipt";
		buttonDisplay = "Generate Receipt";
	}
	else if (status == 1 || status == 3) {
		serviceName = "UpdatePaymentStatus";
		buttonDisplay = "Update Payment Status";
		statusMode = true;
		if("1".equals(paymentMode)) {
			chequeMode = true;
		}
		else if("2".equals(paymentMode)) {
			neftMode = true;
		}
	}
	if (!initialPayment){
%>
		<div id="lastPayment" class="formContainer">
			<form id="lastPaymentForm" method="post" action="./ServerAdapter">
				<input name="ServiceName" type="hidden" value="<%=serviceName%>" />
				<input name="flatId" type="hidden" value = "<%=flatId%>"/>
				<input name="paymentId" type="hidden" value = "<%=paymentMasterVO.getPaymentID()%>"/>
				<div class="formHeader">
					<b>Last Payment</b>
					<span>[<a title="Help" href='#' ><b>?</b></a>]</span>
				</div>
				
				<div class="col1">
					<div class="labels"><label for="lastPaymentDate">Last Payment Date</label></div>
					<div class="fields"><input type="text" name="lastPaymentDate" value="<%=paymentMasterVO.getPaymentDate()%>" readonly/></div>
<%
		if(statusMode) {
%>
  					<div class="labels"><label for="newPaymentStatusSelect">New Payment Status</label></div>
  					<div class="fields">
		          		<select name="newPaymentStatusSelect" onchange="newPaymentStatusSelectChange(this);">
<%
			if(chequeMode) {
%>
							<option value="-1">--select--</option>
							<option value="5">Cheque Cleared</option>
							<option value="2">Cheque Bounced</option>
<%
			}
			else if(neftMode) {
%>
							<option value="-1">--select--</option>
							<option value="5">NEFT Valid</option>
							<option value="4">NEFT Invalid</option>
<%
			}
%>
		          		</select>
  					</div>
<%
		}
%>
		    	</div>
		    	<div class="col2">
					<div class="labels"><label for="lastPaymentStatus">Last Payment Status</label></div>
					<div class="fields"><input type="text" name="lastPaymentStatus" value="<%=paymentStatus%>" readonly /></div>
		    	</div>
		    	
		    	<div class="col3">
		    		<div class="labels"><label for="lastPaidAmount">Last Paid Amount</label></div>
		    		<div class="fields"><input type="text" name="lastPaidAmount" value="<%=paymentMasterVO.getPaidAmount()%>" readonly /></div>
		    	</div>
	
		    	<div class="col4">
		    	</div>
		    	<div class="horSeparator5px"></div>
		        <div class="formActions">
					<div class="fields">
						<input type="submit" id="lastPaymentFormSubmitButton" name="submit" value="<%=buttonDisplay%>" <%if(statusMode) {%>disabled<%}%>/>
					</div>
		        </div>
	        </form>
		</div>
		<div class="horSeparator10px"></div>
<%
		}
		if(!statusMode) {
%>
		<div id="newPayment" class="formContainer">
			<form name="newPaymentForm" method="post" action="./ServerAdapter">
				<input name="ServiceName" type="hidden" value="PayBill" />
				<input name="flatId" type="hidden" value = "<%=flatId%>"/>
				<div class="formHeader">
					<b>Take New Payment</b>
					<span>[<a title="Help" href='#' ><b>?</b></a>]</span>
				</div>

				<div class="col1">
					<div class="labels"><label for="currentBalance">Current Balance</label></div>
					<div class="fields"><input type="text" name="currentBalance" maxlength="11" value="<%=StringUtil.getStringForForm(currBal)%>" readonly/></div>
	        		
	        		<div class="labels"><label for="paymentModeSelect">Payment Mode</label></div>
	        		<div class="fields">
		          		<select name="paymentModeSelect" onchange="paymentModeSelectChange(this);">
		            		<option value="0">Cash</option>
		            		<option value="1">Cheque</option>
		            		<option value="2">NEFT</option>
		          		</select>
	        		</div>

		    	</div>
		    	
		    	<div class="col2">
					<div class="labels"></div>
					<div class="fields"></div>
					
					<div class="labels"><label for="paidAmount">Amount</label></div>
					<div class="fields"><input type="text" name="paidAmount" maxlength="11" required /></div>
		    	</div>
		    	
		    	<div class="col3">
					<div class="labels"></div>
					<div class="fields"></div>
					
					<div id="transNumber">
					
					</div>
				</div>
	
		    	<div class="col4">
					<div class="labels"></div>
					<div class="fields"></div>
					
					<div id="transName">
					
					</div>
		    	</div>
		    	<div class="horSeparator5px"></div>
		        <div class="formActions">
					<div class="fields">
						<input type="submit" name="submit" value="Pay" />
					</div>
		        </div>
	        </form>
		</div>
<%
		}
%>
	</div>
	
	<div class="footer">
		<%@include file="/common/static/footer.html"%>
	</div>
</body>
</html>