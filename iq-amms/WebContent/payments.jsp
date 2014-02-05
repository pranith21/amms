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

function togglePaymentMode(paymentMode) {
	
	var selectedValue = ''
	if(paymentMode != null){
		selectedValue = paymentMode.value;
	}

    if (selectedValue == 'Cheque') {
    	document.getElementById('paymentMode').value = 1
        document.getElementById('chequeMode').style.display = 'block';
        document.getElementById('cashMode').style.display = 'none';
        document.getElementById('NEFTMode').style.display = 'none';
    } else if (selectedValue == 'Cash') {
    	document.getElementById('paymentMode').value = 0
        document.getElementById('chequeMode').style.display = 'none';
        document.getElementById('cashMode').style.display = 'block';
        document.getElementById('NEFTMode').style.display = 'none';
    } else if (selectedValue == 'NEFT Transfer') {
    	document.getElementById('paymentMode').value = 2
        document.getElementById('chequeMode').style.display = 'none';
        document.getElementById('cashMode').style.display = 'none';
        document.getElementById('NEFTMode').style.display = 'block';
    } else{
        document.getElementById('chequeMode').style.display = 'none';
        document.getElementById('cashMode').style.display = 'none';
        document.getElementById('NEFTMode').style.display = 'none';    	
    }
}

function setPaidAmount(paidAmount){
	document.getElementById('paidAmount').value = paidAmount.value;
}

</script>
</head>

<body onload = "javascript:togglePaymentMode(null)">
    <div class="header">
      <%@include file="/common/static/header.html"%>
    </div>
	<% 
		Boolean paymentProcessed = false;
		if(request.getAttribute("paymentProcessed") != null)
		{
			paymentProcessed = (Boolean)request.getAttribute("paymentProcessed");
		}
		if(!paymentProcessed)
		{
			FinancialDetailsVO financialDetailsVO = (FinancialDetailsVO)request.getAttribute("FinancialDetails");
			if(financialDetailsVO != null)
			{
				Double billAmount = financialDetailsVO.getCurrentBalance();
				int flatNumber = financialDetailsVO.getFlatId();
				PaymentMasterVO paymentMasterVO = (PaymentMasterVO)request.getAttribute("PaymentMasterDetails");
	%>    
    <div class="bodycontent">
	    <form id="billForm" name="billForm" method="post" action="./ServerAdapter">
    	  <input name="ServiceName" id="ServiceName" type="hidden" value="PayBill" />
    	  <input name="paidAmount" id="paidAmount" type="hidden" value="" />
		  <input name="flatNumber" id="flatNumber" type="hidden" value = "<%=flatNumber %>"/>
    	  <input name="paymentMode" id="paymentMode" type="hidden" value="" />
    	  
    	  <%if(paymentMasterVO != null){%>
	      <div class="labels"><label for="lastPaidAmount">Last Paid Amount</label></div>
          <div class="fields"><input type="text" id="lastPaidAmount" name="lastPaidAmount" class="input" maxlength="10" value="<%=paymentMasterVO.getPaidAmount() %>" readonly/></div>
	      <div class="labels"><label for="lastPaymentStatus">Last Payment Status</label></div>
          <div class="fields"><input type="text" id="lastPaymentStatus" name="lastPaymentStatus" class="input" maxlength="10" value="<%=paymentMasterVO.getPaymentStatus() %>" readonly/></div>
	      <div class="labels"><label for="lastPaymentDate">Last Payment Date</label></div>
          <div class="fields"><input type="text" id="lastPaymentDate" name="lastPaymentDate" class="input" maxlength="10" value="<%=paymentMasterVO.getPaymentDate() %>" readonly/></div>
    	  <%}%>
	      <div class="labels"><label for="currentbillamount">Pending Bill Amount on this flat</label></div>
          <div class="fields"><input type="text" id="currentbillamount" name="currentbillamount" class="input" maxlength="10" value="<%=billAmount %>" readonly/></div>
          <div class="fields"><input name="paymentType" id="paymentType" onclick="javascript:togglePaymentMode(this)" type="radio" value="Cheque">Cheque</input>
          <input name="paymentType" id="paymentType" onclick="javascript:togglePaymentMode(this)" type="radio" value="Cash">Cash</input>
          <input name="paymentType" id="paymentType" onclick="javascript:togglePaymentMode(this)" type="radio" value="NEFT Transfer">NEFT Transfer</input></div>
          <div class="cashMode" id = "cashMode">
		      <div class="labels"><label for="billamount">Bill Amount</label></div>
	          <div class="labels"><input type="text" id="billamount" name="billamount" class="input" maxlength="10" onchange="setPaidAmount(this)"/></div>
          </div>
          <div class="chequeMode" id = "chequeMode">
	      	<div class="labels"><label for="billamount">Bill Amount</label></div>
	        <div class="fields"><input type="text" id="billamount" name="billamount" class="input" maxlength="10" onchange="setPaidAmount(this)"/></div>
	      	<div class="labels"><label for="chequeNumber">Cheque Number</label></div>
	        <div class="fields"><input type="text" id="chequeNumber" name="chequeNumber" class="input" maxlength="10"/></div>
	      	<div class="labels"><label for="chequeDraweeBank">Cheque Drawee Bank</label></div>
	        <div class="fields"><input type="text" id="chequeDraweeBank" name="chequeDraweeBank" class="input" maxlength="10"/></div>
          </div>
          <div class="NEFTMode" id = "NEFTMode">
		      <div class="labels"><label for="billamount">Bill Amount</label></div>
	          <div class="fields"><input type="text" id="billamount" name="billamount" class="input" maxlength="10" onchange="setPaidAmount(this)"/></div>      
		      <div class="labels"><label for="neftTransactionID">NEFT Transaction ID</label></div>
		      <div class="fields"><input type="text" id="neftTransactionID" name="neftTransactionID" class="input" maxlength="10"/></div>
          </div>
          <br/><br/>
          <div class="fields"><input name="action" type="submit" value="SUBMIT" /></div>          
         </form>
    </div>
    <%}else {
    	%>
    	 <div class="bodycontent">
        	No records found.
        </div>
  <%
  		}
    }else{
    	Double balance = Double.valueOf(request.getAttribute("currentbalance").toString()); 
    %>
       <div class="bodycontent">
       	<div class="labels"><center><h2><label>Payment has been processed successfully and the balance is : <%= balance%></label></h2></center></div>
       	<br></br>
       	<br></br>
       	<div class="fields"><center><button name="home" value="HOME" onclick="index.jsp">HOME</button></center></div>
       </div>
    <% }%>
    <div class="footer">
      <%@include file="/common/static/footer.html"%>
    </div>
</body>
</html>