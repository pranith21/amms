<div style="float: left; width: 40%">
      <!--QUICK TOOLS STARTS-->
        <%@include file="/common/static/quick-tools.html"%>
      <!--QUICK TOOLS ENDS-->
</div>
<div style="float: right; width: 60%; text-align: right;">
<span style="font: 16px verdana,arial,sans-serif bold;">Maintenance Manager</span>&nbsp;<sup><%=Version.versionNumber%></sup>
<br/>
<span style="font: 16px verdana,arial,sans-serif bold;">Mayfair Greens Residential Association</span>
<%System.out.println("User Name = "+sessionVO.getNameOfUser());%>
<%if(StringUtil.isEmpty(sessionVO.getNameOfUser()) == false) {%>
<br/>
Operator: <%=sessionVO.getNameOfUser()%> (<a href="index.jsp" title="Logs you out of the application [Alt+O]" accesskey="O">Logout</a>)&nbsp;
<%}%></div>
