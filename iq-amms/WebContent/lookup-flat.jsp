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
</head>

<body>
    <div class="header">
      <%@include file="/common/static/header.html"%>
    </div>
    <!--APARTMENT LOOKUP OVERLAY STARTS-->
<div id="lookupFlat" class="overlayContainer">
	<div class="overlayContentWrapper">
		<div class="overlayHeader">
			<b>Apartment Lookup</b>
			<span>[<a href='#' onclick="overlay('lookupFlat')" accessKey="ESC"><b>X</b></a>]</span>
		</div>
		<div class="overlayContent">
		<form id="lookupFlat" name="lookupFlat" method="post" action="./ServerAdapter">
          <input name="ServiceName" type="hidden" value="CalculateOutstandingBill" />
          <div class="labels"><label for="block">Block</label></div>
          <div class="fields">
            <select name="block">
              <option value="Block A">A</option>
              <option value="Block B">B</option>
              <option value="Block C">C</option>
              <option value="Block D">D</option>
              <option value="Block E">E</option>
              <option value="Block F">F</option>
              <option value="Block G">G</option>
              <option value="Block H">H</option>
              <option value="Block I">I</option>
              <option value="Block J">J</option>
              <option value="Block K">K</option>
              <option value="Block L">L</option>
            </select>
          </div>
          <div class="labels"><label for="flatNumber">flatNumber</label></div>
          <div class="fields"><input type="text" id="flatNumber" name="flatNumber" class="input" maxlength="3" /></div>
          <div class="fields"><input name="Submit" type="submit" value="Pay Bill" /></div>
		</form>
		</div>
        <div class="overlayOptions">
          <div class="fields"><button title="Close this pop up" onclick="overlay('lookupFlat')">Close</button></div>
        </div>
	</div>
</div>
<!--APARTMENT LOOKUP OVERLAY ENDS-->
    
    <div class="bodycontent">
	      &nbsp;&nbsp;&nbsp;&nbsp;<button title="Search for an Apartment" onclick="overlay('lookupFlat')">Look up Apartment</button>
    </div>
    <div class="footer">
      <%@include file="/common/static/footer.html"%>
    </div>
</body>
</html>