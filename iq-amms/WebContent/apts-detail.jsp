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
	
	if (flatDetailsVO != null) {
%>
      <div class="col1">
        <div class="labels"><label for="">Name</label></div>
        <div class="fields"><b><%=StringUtil.getStringForForm(dwellersMasterVO.getName())%></b></div>

        <div class="labels"><label for="flatNumber">Flat Number</label></div>
        <div class="fields"><input type="text" id="flatNumber" name="flatNumber" value="<%=StringUtil.getStringForForm(flatDetailsVO.getFlatNumber())%>" readonly/></div>

        <div class="labels"><label for="flatNumberPrefix1">Flat Number Prefix</label></div>
        <div class="fields"><input type="text" id="flatNumberPrefix1" name="flatNumberPrefix1" value="<%=StringUtil.getStringForForm(flatDetailsVO.getFlatNumberPrefix1())%>" readonly/></div>

        <div class="labels"><label for="floorNumber">Floor Number</label></div>
        <div class="fields"><input type="text" id="floorNumber" name="floorNumber" value="<%=StringUtil.getStringForForm(flatDetailsVO.getFloorNumber())%>" readonly/></div>

		<hr>
		
        <div class="labels"><label for="currentBalance">Current Balance</label></div>
        <div class="fields"><input type="text" id="currentBalance" name="currentBalance" value="<%=StringUtil.getStringForForm(financialDetailsVO.getCurrentBalance())%>" readonly/></div>

      </div>
      <div class="col2">
        <div class="labels"></div>
        <div class="fields"></div>

        <div class="labels"><label for="flatStatus">Flat Status</label></div>
        <div class="fields">
          <select id="flatStatus" name="flatStatus">
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
        <div class="labels"></div>
        <div class="fields"></div>

        <div class="labels"><label for="flatId">Flat Id</label></div>
        <div class="fields"><input type="text" id="flatId" name="flatId" value="mgra/<%=StringUtil.getStringForForm(flatDetailsVO.getFlatId())%>" readonly/></div>

        <div class="labels"><label for="flatNumberSuffix1">Flat Number Suffix</label></div>
        <div class="fields"><input type="text" id="flatNumberSuffix1" name="flatNumberSuffix1" value="<%=StringUtil.getStringForForm(flatDetailsVO.getFlatNumberSuffix1())%>" readonly/></div>

      </div>
      <div class="col4">
        <div class="labels"></div>
        <div class="fields"></div>

        <div class="labels"><label for="createDate">Create Date</label></div>
        <div class="fields"><input type="text" id="createDate" name="createDate" value="<%=DateUtil.dateToString(flatDetailsVO.getCreateDate(), DateFormat.MMM_DD_YYYY)%>" readonly readonly/></div>
        
        <div class="labels"><label for="flatNumberSuffix2">Flat Number Suffix (more)</label></div>
        <div class="fields"><input type="text" id="flatNumberSuffix2" name="flatNumberSuffix2" value="<%=StringUtil.getStringForForm(flatDetailsVO.getFlatNumberSuffix2())%>" readonly/></div>

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