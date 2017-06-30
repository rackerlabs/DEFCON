# DEFCON
The control system for the incident management light notification systems (integration with Service-Now)
Create a war file of DEFCON:
type the following into command line where DEFCON is located - grails <environment name> war <name for the war file>
example) grails prod war prodDefcon.war

Requirements:
2GB Red Hat Enterprise Linux 5.5 Server

Installed the following on the server:
Java
Glassfish
Nginx
Groovy
ServiceNow MidServer

Enable security for remote login on Glassfish
  run /opt/glassfish/bin/asadmin to enter interactive command line
  run the following commands:
    change-admin-password --user admin (set password, current password is blank)
    enable-secure-admin

Start Glassfish
  run the following command:
    service glassfish start

Deploy DEFCON on Glassfish
  This can be achieved a couple of different ways.
  You can login to the admin console at http://<server ip address>:4848 and follow the next steps to deploy
    Click "Applications"
    Click "Deploy"
    Click "Choose file" and navigate to Defcon war file.
    Leave most as is, but set context root to "/"
    Click "OK"
  
  The other option for deploying is to run the following command in the command line
    scp DefconFileName.txt your_username@remotehost:/some/remote/directory
    example) scp prodDefcon.war root@127.0.0.1:/tmp
    This will remotely transfer the war file to the server

Steps for setting up the MidServer:
http://wiki.servicenow.com/index.php?title=MID_Server_Installation#gsc.tab=0
