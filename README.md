# corporate-eye
The proposed is a software system that is used to prevent corporate users or employees of a firm from accidentally violating company policies or from leaking confidential information. The system can be seen as a deterrent control for an organization. The service continuously monitors activities of the employee in their work machine by analyzing keystrokes, clipboards, web activity, email activity, file access, etc. and warns the user if they are about to violate one of the company policies of controls. There are three parts to this software system. The first one is the client service and application that resides at each employee’s work PC or workstation. This application is responsible for monitoring and alerting the user. This is developed using Java. The second part is a service provides native platform support. For this project it is developed using C# as the focus is on Microsoft windows. The third one is a web service that resides in the local network server or remote network server depending on the network infrastructure of the organization. Either way the server will be in the intranet. The server. The web service will provide administrator interface to manage policies and view/monitor logs. The application is supposed to work platform independently. But for project purposed, the primary focus in on Microsoft Windows Operating System.


Instructions to test beta distributable.

1. Download client from https://iampranav.in/dist/CorporateEyeClientSetup.exe
2. Download server from https://iampranav.in/dist/CorporateEyeServerSetup.exe
3. Install the server setup. (Try to install at a elevation requirement free directory)
4. Install the client setup on the client pc's.
5. After first install , a first time setup pop up will appear. Configure the server hostname and port as required. (Ignore if client and server are on the same pc)
6. To configure rules visit http://hostname:port. for example http://localhost:8100 (default)
7. the default username and password is "admin"


Note: To run the configuration utility again, run <install_path>\CorporateEyeClient\ServerConfigUtility\FirstTimeSetup.exe
