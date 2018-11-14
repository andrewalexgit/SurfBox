# SurfBox
Smart Aquarium Controller Software


   _____            ________            
  / ___/__  _______/ __/ __ )____  _  __
  \__ \/ / / / ___/ /_/ __  / __ \| |/_/
 ___/ / /_/ / /  / __/ /_/ / /_/ />  <  
/____/\__,_/_/  /_/ /_____/\____/_/|_|  

- Smart aquarium technology platform

What is SurfBox?
SurfBox is a simple platform for monitoring parameters, controlling devices, and supports remote WiFi automation.

What can I find in the repository here?
The entire device is comprised of several moving parts such as microcontrollers and circuitry and of course and an internet
capable client. What you will find here in this repository is mostly comrpised the backend server platform to support the 
SurfBox project. 

Goals of SurfBox:

  (1) Create a backend multiplatform server that is open source.
    - Allows engineers to create extensions and customize commands to allow for maximum personalization.
    
  (2) Support the main communication from client to server through the Grab2 library (See Grab repository for library).
    - Grab2 in short will allow for multiplatform client support, in short you could use PIC, Arduino, Raspberry Pi, etc.
    
  (3) GUI Control panels.
    - Simple customization of server settings directly from the server.
    
  (4) Mutli-Client support.
    - Support for multiple clients connected to a single server with the ability to set parameters and control each client
    individually. This will prove to be one of the most challenging to support features of the software.
    
  (5) Memory.
    - Support for either a basic file system or sophisticated MySQL database systems for memory of readings and other data.


