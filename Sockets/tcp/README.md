##### Author: Korry Hinestroza
* Version: March 2022


##### (a)Description
SockServer - SockServer implements the image guessing game. The server gets information from the user 
prior to starting the game such as name and number of rounds the user wishes to play. The server then sends 
the client an image and allows them to guess the image, get a hint(clearer image), or move on to the next image.
The user receives 30 points for correct guesses.

ClientGUI - ClientGUI provides the GUI to the user. It also takes input from the client (via GUI submit box). 
ClientGUI takes servers answer and displays it to the user.
* Server: use `runServer` to connect to default port '8080' or use `runServer -Pport=<port#>` for custom port
* Client: use `runClient` to connect to default host "localhost", port '8080' or use `runClient -Phost=<host> -Pport=<port#>` for custom host/port

##### (b)Requirements Fulfilled
- 1, 2, 3, 4, 5, 6, 7(partial), 8, 9, 10, 11, 12(partial), 13, 14, 15  

##### (c)UML Diagram
- UML Diagram image included in rep (see ClientServer_Diagram) 

##### (d)Protocol Design

#### Starting Game
###### Request
    {"type": "begin"}

###### Response
Ok case
{"type": "hello", "value": <String>}
Error cases
{"type": "error", "message": <String>}

    String is:
    - "type unknown. Please input valid type" -- if the user does not include a "type" in request


#### Entering Name
###### Request
    {"type": "hello", "value": <String>}

###### Response
Ok case
{"type": "hello", "value": <String>}
Error cases
{"type": "error", "message": <String>}

    String is:
    - "Input is empty. Please enter your name." -- if the user clicks submit without typing
    - "input has no value" -- if the user does not have send a value


#### Entering Number of Rounds
###### Request
    {"type": "hello", "value": <String>}
    String is:
        The String is an integer, however, the program will convert the int on the server side. 
        Therefore the client should send ints as strings.

###### Response
Ok case
{"type": "hello", "value": <String>}
Error cases
{"type": "error", "message": <String>}

    String is:
    - "Invalid input. Please enter an integer" -- when input is not a number
    - "Please enter an integer from 1 to 6." -- when input is not from 1 to 6 (number of images available)
    - "input has no value" -- if the user does not have send a value

#### Playing the Game ###########################################################################################
###### Request MORE
    {"type": "hello", "value": "more"}

###### Response
Ok case
{"type": "image", "value": <String>}
-uses insertImage(<String>, 0, 0) in ClientGUI to insert next image
Error cases
{"type": "error", "message": <String>}

    String is:
    - "This is the last hint for this image." -- when input requests more hints than whats available
    - "Input empty" -- if users doesn't type anything

###### Request NEXT
    {"type": "hello", "value": "next"}

###### Response
Ok case
{"type": "image", "value": <String>}
-uses insertImage(<String>, 0, 0) in ClientGUI to insert next image
Error cases
{"type": "error", "message": <String>}

    String is:
    - "Input empty" -- if users doesn't type anything

###### Request GUESS
    {"type": "hello", "value": <String>}

###### Response
Ok case
{"type": "hello", "value": <String>}
Error cases
{"type": "error", "message": <String>}

    String is:
    - "Input empty" -- if users doesn't type anything

#################################################################################################################

#### Continue
###### Request
    {"type": "hello", "value": <String>}
-user enters their name or quit
###### Response
Ok case
{"type": "quit", "value": <String>}
{"type": "hello", "value": <String>}
Error cases
{"type": "error", "message": <String>}

    String is:
    - "Please enter your name or quit" -- if input is not their name or quit

#### Exit
###### Request
    {"type": "hello", "value": "quit"}

###### Response
Ok case
{"type": "quit", "value": <String>}


##### (e)Design Explanation
I explained this in my video, but overall I just made sure I thought of a mistake that could be made
at every step of my program. From not entering a JSON object, empty inputs, not having the right data types, 
etc. In my video I point out what I did within my code to catch mistakes and prevent the program server from crashing.

##### (f)Screen Capture Link
[link]https://youtu.be/w6fveafk_IY      (its 13 minutes long! Sorry I talked too much)

##### (g)UDP over TCP
-The program would have a couple differences if it used UDP instead of TCP. One difference is that UDP
does not send acknowledgements. This would mean that when monitoring wireshark, I would not be acknowledged
when a packet has arrived to its destination. As the developer, I would have to code some sort of acknowledgement system 
within my code  if I wanted it. Another difference is packets are not split when they are too large while using UDP. 
This means that data may be lost during the back and forth conversation if packets are too large. 
Packets would need to be split manually by the developer. 


