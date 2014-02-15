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
      <%@include file="common/static/header.html"%>
    </div>
    <div class="bodycontent">
    <%
		int flatId = Integer.valueOf(request.getParameter("flatId"));
    	FlatsHelper flatsHelper = new FlatsHelper();
    	FlatDetailsVO flatDetailsVO = flatsHelper.getFlatDetails(flatId);
    	DwellersMasterVO dwellersMasterVO = flatsHelper.getDwellerDetails(flatId);
    	
    	BillHelper billHelper = new BillHelper();
    	BillMasterVO billMasterVO = billHelper.getBillMaster(flatId);
    	
    %>
		  <div class="invoice">
			  <div class="invheader"><b>MGRA Maintenance Bill, for the month of <%=DateUtil.dateToString(billMasterVO.getDateOfGeneration(), DateFormat.MMM_yy)%></b></div>
			  <div class="name">
			    <div class="datalabel">Name</div><div class="data"><%=StringUtil.getStringForForm(dwellersMasterVO.getName())%></div>
			    <div class="datalabel">Apt. no.</div><div class="data"><%=StringUtil.getStringForForm(flatDetailsVO.getFullFlatNumber())%></div>
			    <div class="datalabel">Inv. no.</div><div class="data"><%=StringUtil.getStringForForm(billMasterVO.getBillId())%></div>
			  </div>
			  <div class="billinfo">
			    <div class="datalabel">Date</div><div class="data"><%=DateUtil.dateToString(billMasterVO.getDateOfGeneration(), DateFormat.MMM_dd_yyyy)%></div>
			    <div class="datalabel">Due Date</div><div class="data"><%=DateUtil.dateToString(billMasterVO.getDateOfDue(), DateFormat.MMM_dd_yyyy)%></div>
			    <div class="datalabel">Amount</div><div class="data">Rs. <%=billMasterVO.getTotalAmount()%></div>
			  </div>
			  <div class="billdetails">
			    <table class="invoice">
				<thead>
					<tr>
						<th>Particulars</th>
						<th>Amount</th>
					</tr>
				</thead>
				<tbody>
				  <form id="billForm" name="billForm" method="post" action="./ServerAdapter">
				    <input name="ServiceName" type="hidden" value="UpdateBill" />
				    <input name="billId" type="hidden" value="<%=billMasterVO.getBillId()%>" />
				    <input name="flatId" type="hidden" value="<%=billMasterVO.getFlatId()%>" />
				<%
					ArrayList<BillDetailsVO> billDetailsVOList = billMasterVO.getBillDetailsList();
					for (BillDetailsVO detailsVO : billDetailsVOList) {
				%>
					<tr>
					  <td><%=detailsVO.getChargeName()%></td>
					  <td align="right"><%=detailsVO.getChargeAmount()%></td>
					</tr>
				<%
					}
				%>
					<tr>
					  <td><input type="text" name="chargeNameOne" <%=(billMasterVO.getBillStatus() == 0)?"":"readonly"%>></td>
					  <td align="right"><input type="text" name="amountOne" <%=(billMasterVO.getBillStatus() == 0)?"":"readonly"%>></td>
					</tr>
					<tr>
					  <td><input type="text" name="chargeNameTwo" <%=(billMasterVO.getBillStatus() == 0)?"":"readonly"%>></td>
					  <td align="right"><input type="text" name="amountTwo" <%=(billMasterVO.getBillStatus() == 0)?"":"readonly"%>></td>
					</tr>
					<tr>
					  <td><input type="text" name="chargeNameThree" <%=(billMasterVO.getBillStatus() == 0)?"":"readonly"%>></td>
					  <td align="right"><input type="text" name="amountThree" <%=(billMasterVO.getBillStatus() == 0)?"":"readonly"%>></td>
					</tr>
					<tr>
					  <td><input type="text" name="chargeNameFour" <%=(billMasterVO.getBillStatus() == 0)?"":"readonly"%>></td>
					  <td align="right"><input type="text" name="amountFour" <%=(billMasterVO.getBillStatus() == 0)?"":"readonly"%>></td>
					</tr>
				</tbody>
				<tfoot>
					<tr>
						<th>Total:</th>
						<th align="right"><%=billMasterVO.getTotalAmount()%></th>
					</tr>
				</tfoot>
			    </table>
			    <%
			    	Double amount = billMasterVO.getTotalAmount();
			    	String amountStr = StringUtil.getStringValue(amount);
			    	String[] amountParts = amountStr.split("\\.");
			    	Integer amountInt = Integer.valueOf(amountParts[0]+StringUtil.padCharTrail(amountParts[1], 2, "0").substring(0, 2));
			    	Money money = new Money();
			    	money.setAmount(amountInt);			    
			    %>
				<b><%=MoneyUtil.getMoneyInWords(money)%></b>
			  </div>
		  </div> <!--invoice-->
		<%
			if (billMasterVO.getBillStatus() == 0) {
		%>
          <input name="action" type="submit" value="Finalize" /><input name="action" type="submit" value="Update Bill" /><input type="reset" value="Reset" >
		<%
			}
		%>
		<%
			if (billMasterVO.getBillStatus() > 0) {
		%>
		<input name="action" type="submit" value="Export PDF" />
		<%
			}
		%>
        </form>
    </div>
    <div class="footer">
      <%@include file="common/static/footer.html"%>
    </div>
</body>
</html>