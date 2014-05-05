@echo OFF

 mysqldump -p 12560 --user=%1 --password=%2 --databases %3 > %4