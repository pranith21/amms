<%@ page import="org.iq.util.version.Version,org.iq.util.string.StringUtil,com.iq.amms.valueobjects.SessionVO" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<!-- saved from url=(0026)http://www.iqshelters.com/ -->
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=iso-8859-1" />
<title>AMMS <%=Version.versionNumber%>::iquesters</title>
<link href="styles/common.css" rel="stylesheet" type="text/css" media="screen" />
<link href="styles/form.css" rel="stylesheet" type="text/css" media="screen" />
</head>

<body>
<%
	SessionVO sessionVO = (SessionVO)request.getAttribute("sessionVO");
	if(sessionVO == null){
		sessionVO = new SessionVO();
	}
%>
    <div class="quickAccessMenu">
      <!--QUICK ACCESS MENU STARTS-->
      <a href="support.jsp" target="_blank" title="Click to contact support [Alt+S]" accesskey="S">CONTACT SUPPORT</a>
      <!--QUICK ACCESS MENU ENDS-->
    </div>
    <div class="header">
      <%@include file="common/dynamic/header.jsp"%>
    </div>
    <div class="bodycontent">
      <div class="loginform">
      <%
      	Boolean loginFailed = Boolean.valueOf(StringUtil.getStringValue(request.getAttribute("loginFailed")));
      	if(loginFailed) {
      %>
          <div class="labels"><span style="color: #FF0000">Incorrect Username and/or Password...</span></div>
      <%}%>
        <!--LOGIN FORM STARTS-->
        <form id="login" name="loginForm" method="post" action="./ServerAdapter">
          <input name="ServiceName" type="hidden" value="Login" />
          <div class="labels"><label for="username">Username</label></div>
          <div class="fields"><input type="text" id="username" name="username" autofocus required data-errormessage="Username is required." /></div>
          <div class="labels"><label for="username">Password</label></div>
          <div class="fields"><input type="password" id="password" name="password" required /></div>
          <div class="labels"><label for="username"></label></div>
          <div class="fields"><input name="login" type="submit" value="Login" /></div>
        </form>
        <!--LOGIN FORM ENDS-->
      </div>
    </div>
    <div class="footer">
      <%@include file="common/static/footer.html"%>
    </div>
</body>
</html>