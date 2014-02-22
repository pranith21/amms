@echo OFF

 mysqldump --user=%1 --password=%2 --databases %3 > %4