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
</head>

<body>
    <div class="header">
      <%@include file="common/static/header.html"%>
    </div>
    <div class="bodycontent">

<%
    GetAllFlats getAllFlats = new GetAllFlats();
    try {
      ArrayList<AllFlatsVO> allFlatsVOList = getAllFlats.execute();
      if (allFlatsVOList !=null && allFlatsVOList.size()>0) {
%>
          <table id="tablesTable" class="tablesorter">
          	<thead>
          	  <tr>
				<th>Apt. Number</th>
				<th>Owner</th>
				<th>Contact</th>
				<th>Current Due</th>
				<th>Bill Status</th>
			  </tr>
			</thead>
			<tbody>
<%
	      for (AllFlatsVO flatsVO : allFlatsVOList) {
%>
			  <tr>
			    <td><%=flatsVO.getFullFlatNumber()%></td>
			    <td><%=flatsVO.getFullName()%></td>
			    <td><%=flatsVO.getPrefferdContact()%></td>
			    <td><%=flatsVO.getCurrentBalance()%>&nbsp;&nbsp;<a href="bill-details.jsp?flatId=<%=flatsVO.getFlatId()%>">[Last bill...]</a></td>
			    <%
					BillHelper billHelper = new BillHelper();
					BillMasterVO billMasterVO = billHelper.getBillMaster(flatsVO.getFlatId());
					String status = "<span style=\"color: red;\">Yet to be finalized</span>";
					if(billMasterVO.getBillStatus()>0)
						status = "<span style=\"color: green;\">Ready to print</span>";
			    %>
			    <td><%=status%></td>
			  </tr>
<%
		  }
%>
			</tbody>
		  </table>
<%
      }
    }
    catch (Exception e) {
      // TODO Auto-generated catch block
      e.printStackTrace();
    }
%>
    <form id="billForm" name="billForm" method="post" action="./ServerAdapter">
      <input name="ServiceName" type="hidden" value="FinalizeAll" />
<%
		BillHelper billHelper = new BillHelper();
		if(billHelper.isPrintReady()) {
%>
      <input name="action" type="submit" value="Export Consolidated PDF" />
<%
		}
		else {
%>
      <input name="action" type="submit" value="Finalize All" />
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