@echo off

del %CATALINA_HOME%\webapps\amms.war

rmdir /s /q %CATALINA_HOME%\webapps\amms

copy amms.war %CATALINA_HOME%\webapps

echo Installation done successfully.

pause