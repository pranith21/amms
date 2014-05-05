<%@page import="com.iq.amms.services.helpers.BillOutwardsHelper"%>
<%@page import="com.iq.amms.valueobjects.BillOutwardsDetailVO"%>
<%@page import="com.iq.amms.valueobjects.BillerDetailsVO"%>
<%@page import="java.util.List"%>
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
function setRequestType(selectedOption){
	document.getElementById("operationType").value = selectedOption.value;
}

function populateBillerName(selectedBiller, option){
	if (option == "Edit"){
		var str = selectedBiller.value;
		var subStr = str.split("%%");
		document.getElementById("EditBillerId").value = subStr[0];
		document.getElementById("EditBillerName").value = subStr[1];
	}else{
		document.getElementById("DeleteBillerId").value = selectedBiller.value;
	}
}

function setBillDetailsToProcess(selectedValue){
	var str = selectedValue.value;
	var subStr = str.split("%%");
	document.getElementById("processBillID").value = subStr[0];
	document.getElementById("processBillerId").value = subStr[1];
	document.getElementById("processComments").value = subStr[2];
	document.getElementById("processBillAmount").value = subStr[3];
}
</script>
</head>
<body>
    <div class="header">
      <%@include file="/common/static/header.html"%>
    </div>
    <div class="bodycontent">
<%
	Boolean addBillerFlag = false;
	if (request.getAttribute("AddBillerFlag") != null) {
		addBillerFlag = (Boolean) request.getAttribute("AddBillerFlag");
	}
	Boolean editBillerFlag = false;
	if (request.getAttribute("EditBillerFlag") != null) {
		editBillerFlag = (Boolean) request.getAttribute("EditBillerFlag");
	}
	Boolean deleteBillerFlag = false;
	if (request.getAttribute("DeleteBillerFlag") != null) {
		deleteBillerFlag = (Boolean) request.getAttribute("DeleteBillerFlag");
	}
	Boolean uploadBillFlag = false;
	if (request.getAttribute("UploadBillFlag") != null) {
		uploadBillFlag = (Boolean) request
				.getAttribute("UploadBillFlag");
	}
	Boolean approveBillFlag = false;
	if (request.getAttribute("approveBillFlag") != null) {
		approveBillFlag = (Boolean) request
				.getAttribute("approveBillFlag");
	}
	
	String billProcessType = null;
	if (request.getAttribute("billProcessType") != null) {
		billProcessType = (String) request
				.getAttribute("billProcessType");
	}
	List<BillerDetailsVO> billerDetailsVOs = null;
	if (request.getAttribute("billerDetails") != null) {
		 billerDetailsVOs = (List<BillerDetailsVO>)request.getAttribute("billerDetails");		
	}
%>
		<div id="billerDetails" class="formContainer">
		<% if(!addBillerFlag && !editBillerFlag && !deleteBillerFlag) {%>
			<form>
				<div class="formHeader">
					<b>Add/Edit/Delete Biller</b>
					<span>[<a title="Help" href='#' ><b>?</b></a>]</span>
				</div>
		    	<div class="col1">
					<input type="button" id="AddBiller" name="AddBiller" value="Add Biller" onclick="overlay('login');setRequestType(this)"></input>
		    	</div>
		    	<div class="col2">
					<input type="button" id="EditBiller" name="EditBiller" value="Edit Biller" onclick="overlay('login');setRequestType(this)"></input>
		    	</div>
		    	<div class="col3">
					<input type="button" id="DeleteBiller" name="DeleteBiller" value="Delete Biller" onclick="overlay('login');setRequestType(this)"></input>
		    	</div>
		</form>
		<%} %>
		<% if(addBillerFlag) {%>
    		<form id="billerDetails" name="billerDetails" method="post" action="./ServerAdapter">
    			<input name="ServiceName" type="hidden" value="BillerDetails" />
    			<input name="Operation" type="hidden" value="Add" />
				<div class="formHeader">
					<b>Add Biller</b>
					<span>[<a title="Help" href='#' ><b>?</b></a>]</span>
				</div>
		    	<div class="col1">
	        		<div class="labels"><label for="AddBillerName">Biller Name</label></div>
	        		<div class="fields"><input type="text" id="AddBillerName" name="AddBillerName" value="" /></div>
		    	</div>
		    	
		    	<div class="horSeparator5px"></div>
		        <div class="formActions">
					<div class="fields"><input name="Submit" type="submit" value="Add" /></div>
		        </div>
	        </form>
	        <%} if(editBillerFlag) { 
	        		if(billerDetailsVOs != null && billerDetailsVOs.size() > 0){%>
    		<form id="billerDetails" name="billerDetails" method="post" action="./ServerAdapter">
    			<input name="ServiceName" type="hidden" value="BillerDetails" />
    			<input name="Operation" id="Operation" type="hidden" value="Edit" />
    			<input name="EditBillerId" id="EditBillerId" type="hidden" value="" />
				<div class="formHeader">
					<b>Edit Biller</b>
					<span>[<a title="Help" href='#' ><b>?</b></a>]</span>
				</div>
		    	<div class="col1">
					<div class="labels"><label for="editBillerDetails">Biller Name</label></div>
		    	<div class="fields">
					<select id="editBillerDetails" name="editBillerDetails" onchange="populateBillerName(this,'Edit')">
						<option value="select">Select</option>
					<%for (BillerDetailsVO billerDetailsVO : billerDetailsVOs){ %>
						<option value="<%=billerDetailsVO.getBillerId()+"%%"+billerDetailsVO.getBillerName() %>" ><%=billerDetailsVO.getBillerName() %></option>
					<%} %>
					</select>
				</div>
		    	</div>
		    	<div class="col2">
	        		<div class="labels"><label for="EditBillerName">Biller Name</label></div>
	        		<div class="fields"><input type="text" id="EditBillerName" name="EditBillerName" value="" /></div>
		    	</div>
		    	
		    	<div class="horSeparator5px"></div>
		        <div class="formActions">
					<div class="fields"><input name="Submit" type="submit" value="Edit" /></div>
		        </div>
	        </form>
	        
	        <%}else{ %>
			No Billers added.
			<%} } if(deleteBillerFlag){ 
			if(billerDetailsVOs != null && billerDetailsVOs.size() > 0){%>
    		<form id="billerDetails" name="billerDetails" method="post" action="./ServerAdapter">
    			<input name="ServiceName" type="hidden" value="BillerDetails" />
    			<input name="Operation" id="Operation" type="hidden" value="Delete" />
    			<input name="DeleteBillerId" id="DeleteBillerId" type="hidden" value="" />
				<div class="formHeader">
					<b>Delete Biller</b>
					<span>[<a title="Help" href='#' ><b>?</b></a>]</span>
				</div>
		    	<div class="col1">
					<div class="labels"><label for="billerDetails">Biller Name</label></div>
					<div class="fields">
						<select id="billerDetails" name="billerDetails" onchange="populateBillerName(this,'Delete')">
						<option value="select">Select</option>
					<%for (BillerDetailsVO billerDetailsVO : billerDetailsVOs){ %>
						<option value="<%=billerDetailsVO.getBillerId() %>" ><%=billerDetailsVO.getBillerName() %></option>
					<%} %>
					</select>
				</div>
		    	</div>
		    	
		    	<div class="horSeparator5px"></div>
		        <div class="formActions">
					<div class="fields"><input name="Submit" type="submit" value="Delete" /></div>
		        </div>
	        </form>
	        <%}else{ %>
				No Billers added.
			<%}
			}%>
		</div>
		
		<div class="horSeparator10px"></div>

    	<div id="uploadBill" class="formContainer">
    		<form id="UploadBillDetails" name="UploadBillDetails" method="post" action="./ServerAdapter">
    			<input name="ServiceName" type="hidden" value="UploadBillDetails" />
				<div class="formHeader">
					<b>Upload Bill</b>
					<span>[<a title="Help" href='#' ><b>?</b></a>]</span>
				</div>
		    	<div class="col1">
	        		<div class="labels"><label for="BillId">Bill Number</label></div>
	        		<div class="fields">
	        			<input type="text" id="BillId" name="BillId" value="" <%if(!uploadBillFlag) {%>readonly<%}%>/>
	        		</div>
	        		
	        		<div class="labels"><label for="comments">Bill Details</label></div>
					<div class="fields">
						<input type="text" id="comments" name="comments" <%if(!uploadBillFlag) {%>readonly<%}%>/>
					</div>
					
					<div class="labels"><label for="billAmount">Amount</label></div>
					<div class="fields">
						<input type="text" id="billAmount" name="billAmount" value="" <%if(!uploadBillFlag) {%>readonly<%}%>/>
					</div>
					
				</div>
		    	
				<div class="col2">
					<div class="labels"><label for="billerName">Biller Name</label></div>
					<div class="fields">
		    	<%if (billerDetailsVOs != null && billerDetailsVOs.size() > 0){ %>
					<select id="billerName" name="billerName" <%if(!uploadBillFlag){%> disabled<%} %>>
					<%for (BillerDetailsVO billerDetailsVO : billerDetailsVOs){ %>
						<option value="<%=billerDetailsVO.getBillerId() %>" ><%=billerDetailsVO.getBillerName() %></option>
					<%} %>
					</select>
				<%}else{ %>
					No Billers added.
				<%} %></div>
				</div>
				
		    	<div class="horSeparator5px"></div>
		        <div class="formActions">
		        	<div class="fields">
						<%if(uploadBillFlag) {%>
							<input name="Submit" type="submit" value="Upload" />
						<%} else {%>
							<input type="button" id="UploadBill" name="UploadBill" value="Add Bill to Upload" onclick="overlay('login');setRequestType(this)"></input>
						<%} %>
					</div>
		        </div>
	        </form>
		</div>
		
		<div class="horSeparator10px"></div>

    	<div id="approveBill" class="formContainer">
    		<form id="UpdateBillStatus" name="UpdateBillStatus" method="post" action="./ServerAdapter">
    			<input name="ServiceName" type="hidden" value="UpdateBillStatus" />
    			<input name="processBillID" id="processBillID" type="hidden" value="UpdateBillStatus" />
				<div class="formHeader">
					<b>Approve/Reject Bill</b>
					<span>[<a title="Help" href='#' ><b>?</b></a>]</span>
				</div>
				<%
				BillOutwardsHelper billOutwardsHelper = new BillOutwardsHelper();
				List<BillOutwardsDetailVO>  billOutwardsDetailVOs = billOutwardsHelper.getPendingBillsForApproval();
				%>
		    	<div class="col1">
	        		<div class="labels"><label for="processBillNumber">Bill Number</label></div>
	        		<div class="fields">
		    	<%if (billOutwardsDetailVOs != null && billOutwardsDetailVOs.size() > 0){ %>
					<select id="processBillNumber" name="processBillNumber" onchange="setBillDetailsToProcess(this)" <%if (!approveBillFlag){ %> disabled <%} %> >
					<option value="Select">Select</option>
					<%for (BillOutwardsDetailVO billOutwardsDetailVO : billOutwardsDetailVOs){
						String value = billOutwardsDetailVO.getBillId()+"%%"+billOutwardsDetailVO.getBillerName()+"%%"+billOutwardsDetailVO.
								getBillDetail()+"%%"+billOutwardsDetailVO.getAmount(); %>
						<option value="<%= value%>" ><%=billOutwardsDetailVO.getBillNumber() %></option>
					<%} %>
					</select>
				<%}else{ %>
					No Pending Bills For Processing.
				<%} %>
					</div>
	        		<div class="labels"><label for="processBillerId">Biller ID</label></div>
	        		<div class="fields">
	        			<input type="text" id="processBillerId" name="processBillerId" value="" readonly/>
	        		</div>
				</div>
		    	
				<div class="col2">
	        		<div class="labels"></div>
					<div class="fields"></div>

	        		<div class="labels"><label for="processComments">Bill Details</label></div>
					<div class="fields">
						<input type="text" id="processComments" name="processComments" readonly/>
					</div>
				</div>
				
				<div class="col3">
	        		<div class="labels"></div>
					<div class="fields"></div>

					<div class="labels"><label for="processBillAmount">Amount</label></div>
					<div class="fields">
						<input type="text" id="processBillAmount" name="processBillAmount" value="" readonly/>
					</div>
				</div>
				
		    	<div class="horSeparator5px"></div>
		        <div class="formActions">
		        	<div class="fields">
						<%if(approveBillFlag) {%>
						<%if("Approve".equals(billProcessType)) {%>
							<input name="Submit" type="submit" value="Approve" />
						<%}else{ %>
							<input name="Submit" type="submit" value="Reject" />
						<%} %>	
						<%} else {%>
						<div class="col1">
							<input type="button" id="Approve" name="Approve" value="Approve Bill" onclick="overlay('login');setRequestType(this)"></input>
						</div>
							
						<div class="col1">
							<input type="button" id="Reject" name="Reject" value="Reject Bill" onclick="overlay('login');setRequestType(this)"></input>
						</div>	
						<%} %>
					</div>
		        </div>
	        </form>
		</div>
	</div>
    <div class="footer">
      <%@include file="/common/static/footer.html"%>
    </div>
</body>
</html>