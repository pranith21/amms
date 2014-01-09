<%@ page import="org.iq.util.version.Version,org.iq.util.string.StringUtil,com.iq.amms.valueobjects.SessionVO" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<!-- saved from url=(0026)http://www.iqshelters.com/ -->
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=iso-8859-1" />
<title>AMMS <%=Version.versionNumber%>::iquesters</title>
<link href="styles/common.css" rel="stylesheet" type="text/css" media="screen" />
<link href="styles/menu.css" rel="stylesheet" type="text/css" media="screen" />
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
      <%@include file="/common/dynamic/header.jsp"%>
    </div>
    <div class="bodycontent">
      <div class="mainmenu">
        <ul>
          <li class="selected"><a href="#" title="Deals with actions on the Community [Alt+C]" accesskey="C">Community</a></li>
          <li><a href="#" title="Deals with actions on the Apartments [Alt+A]" accesskey="A">Apartments</a></li>
          <li><a href="#" title="Deals with actions on the Dwellers [Alt+D]" accesskey="D">Dwellers</a></li>
          <li><a href="#" title="Deals with actions on the Financils [Alt+F]" accesskey="F">Financials</a></li>
          <li><a href="#" title="Deals with different sorts of reports [Alt+R]" accesskey="R">Reports</a></li>
          <li><a href="#" title="Deals with collabortive actions on the Community [Alt+M]" accesskey="M">Events &amp; Mailers</a></li>
        </ul>
      </div>
      <div class="submenu">
        <div class="submenuheader">Community Submenu</div>
        No sub menu
      </div>
      <div class="workarea">
        <div class="workareaheader">header</div>
        <div class="workareadescription">This is a place for updating information about flat.</div>
        <!--LOGIN FORM STARTS-->
        <form id="login" name="loginForm" method="post" action="">
        <div class="workareamain">
          <div class="mainform">
            <div class="col1">
              <div class="labels"><label for="username">Username</label></div>
              <div class="fields"><input type="text" id="username" name="username" class="input" /></div>
              <div class="labels"><label for="username">Password</label></div>
              <div class="fields"><input type="password" id="password" name="password" class="input" /></div>
              <div class="labels"><label for="username">STATUS</label></div>
              <div class="fields">
                <select name="STATUS">
                  <option value="0">SOLD</option>
                  <option value="1">UNSOLD</option>
                </select>
              </div>
              <div class="labels"><label for="username">Username</label></div>
              <div class="fields"><input type="text" id="username" name="username" class="input" /></div>
              <div class="labels"><label for="username">Password</label></div>
              <div class="fields"><input type="password" id="password" name="password" class="input" /></div>
              <div class="labels"><label for="username">STATUS</label></div>
              <div class="fields">
                <select name="STATUS">
                  <option value="0">SOLD</option>
                  <option value="1">UNSOLD</option>
                </select>
              </div>
            </div>
            <div class="col2">
              <div class="labels"><label for="username">Username</label></div>
              <div class="fields"><input type="text" id="username" name="username" class="input" /></div>
              <div class="labels"><label for="username">Password</label></div>
              <div class="fields"><input type="password" id="password" name="password" class="input" /></div>
              <div class="labels"><label for="username">STATUS</label></div>
              <div class="fields">
                <select name="STATUS">
                  <option value="0">SOLD</option>
                  <option value="1">UNSOLD</option>
                </select>
              </div>
              <div class="labels"><label for="username">Username</label></div>
              <div class="fields"><input type="text" id="username" name="username" class="input" /></div>
              <div class="labels"><label for="username">Password</label></div>
              <div class="fields"><input type="password" id="password" name="password" class="input" /></div>
              <div class="labels"><label for="username">STATUS</label></div>
              <div class="fields">
                <select name="STATUS">
                  <option value="0">SOLD</option>
                  <option value="1">UNSOLD</option>
                </select>
              </div>
            </div>
            <div class="col3">
              <div class="labels"><label for="username">Username</label></div>
              <div class="fields"><input type="text" id="username" name="username" class="input" /></div>
              <div class="labels"><label for="username">Password</label></div>
              <div class="fields"><input type="password" id="password" name="password" class="input" /></div>
              <div class="labels"><label for="username">STATUS</label></div>
              <div class="fields">
                <select name="STATUS">
                  <option value="0">SOLD</option>
                  <option value="1">UNSOLD</option>
                </select>
              </div>
              <div class="labels"><label for="username">Username</label></div>
              <div class="fields"><input type="text" id="username" name="username" class="input" /></div>
              <div class="labels"><label for="username">Password</label></div>
              <div class="fields"><input type="password" id="password" name="password" class="input" /></div>
              <div class="labels"><label for="username">STATUS</label></div>
              <div class="fields">
                <select name="STATUS">
                  <option value="0">SOLD</option>
                  <option value="1">UNSOLD</option>
                </select>
              </div>
              <div class="labels"><label for="username">Username</label></div>
              <div class="fields"><input type="text" id="username" name="username" class="input" /></div>
              <div class="labels"><label for="username">Password</label></div>
              <div class="fields"><input type="password" id="password" name="password" class="input" /></div>
              <div class="labels"><label for="username">STATUS</label></div>
              <div class="fields">
                <select name="STATUS">
                  <option value="0">SOLD</option>
                  <option value="1">UNSOLD</option>
                </select>
              </div>
              <div class="labels"><label for="username">Username</label></div>
              <div class="fields"><input type="text" id="username" name="username" class="input" /></div>
              <div class="labels"><label for="username">Password</label></div>
              <div class="fields"><input type="password" id="password" name="password" class="input" /></div>
              <div class="labels"><label for="username">STATUS</label></div>
              <div class="fields">
                <select name="STATUS">
                  <option value="0">SOLD</option>
                  <option value="1">UNSOLD</option>
                </select>
              </div>
            </div>
            <div class="col4">
              <div class="labels"><label for="username">Username</label></div>
              <div class="fields"><input type="text" id="username" name="username" class="input" /></div>
              <div class="labels"><label for="username">Password</label></div>
              <div class="fields"><input type="password" id="password" name="password" class="input" /></div>
              <div class="labels"><label for="username">STATUS</label></div>
              <div class="fields">
                <select name="STATUS">
                  <option value="0">SOLD</option>
                  <option value="1">UNSOLD</option>
                </select>
              </div>
              <div class="labels"><label for="username">Username</label></div>
              <div class="fields"><input type="text" id="username" name="username" class="input" /></div>
              <div class="labels"><label for="username">Password</label></div>
              <div class="fields"><input type="password" id="password" name="password" class="input" /></div>
              <div class="labels"><label for="username">STATUS</label></div>
              <div class="fields">
                <select name="STATUS">
                  <option value="0">SOLD</option>
                  <option value="1">UNSOLD</option>
                </select>
              </div>
            </div>
          </form>
          <!--LOGIN FORM ENDS-->
          </div>
        </div> <!--workmainarea-->
        <div class="workareafooter">
          <input name="Submit" type="submit" value="Add Info" /><input type="reset" value="Reset" >
        </div>
        </form>
<!--div id="FLAT_DIV"><label>FLAT INFORMATION</label>
<table width="100%" height="2" border="0" cellpadding="0" cellspacing="1" >

<tr>
</br>
</br>
</tr>

<tr>
<td width="5%">AREA SQFT</td>
<td width="30%"valign="top"><label><input name="APARTMENT_ID" type="number" /></label></td>
</tr>

<tr>
<td width="5%">FLAT NUMBER</td>
<td width="30%"valign="top"><label><input name="APARTMENT_ID" type="text" maxlength="10"/></label></td>
</tr>

<tr>
<td width="5%">STATUS</td>
<td width="30%" valign="top"><label><select name="STATUS">
<option value="0">UNOCCUPIED</option>
<option value="1">OCCUPIED</option>
</select></td>
</tr>

<tr>
<td width="5%">FLOOR NO.</td>
<td width="30%"valign="top"><label><input name="APARTMENT_ID" type="number" min="1" max="999"/></label></td>
</tr>

<tr>
<td width="5%">ADDITIONAL NUMBER 1</td>
<td width="30%"valign="top"><label><input name="APARTMENT_ID" type="text" maxlength="20"/></label></td>
</tr>

<tr>
<td width="5%">ADDITIONAL NUMBER 2</td>
<td width="30%"valign="top"><label><input name="APARTMENT_ID" type="text" maxlength="20"/></label></td>
</tr>

<tr>
<td width="5%">&nbsp;</td>
<td width="30%"valign="top"><input name="Submit" type="submit" value="Add Info" />
<input type="reset" value="Reset" ></td>
</tr>



</table>
</div-->

      </div>
    </div>
    <div class="footer">
      <%@include file="/common/static/footer.html"%>
    </div>
</body>
</html>