CREATE USER 'mytestuser'@'localhost' IDENTIFIED BY 'mypassword';
GRANT ALL PRIVILEGES ON * . * TO 'mytestuser'@'localhost';
GRANT ALL PRIVILEGES ON *.* TO 'mytestuser'@'%' IDENTIFIED BY 'mypassword' WITH GRANT OPTION;
