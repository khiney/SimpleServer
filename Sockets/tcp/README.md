# Assignment 3 Starter Code

## Grid Image Maker Usage

### Terminal

```
gradle runServer
```

```
gradle runClient
```

## GUI Usage

### Code

1. Create an instance of the GUI

   ```
   ClientGui main = new ClientGui();
   ```

2. Create a new game and give it a grid dimension

   ```
   // the pineapple example is 2, but choose whatever dimension of grid you want
   // you can change the dimension to see how the grid changes size
   main.newGame(2); 
   ```

*Depending on how you want to run the system, 3 and 4 can be run how you want*

3. Insert image

   ```
   // the filename is the path to an image
   // the first coordinate(0) is the row to insert in to
   // the second coordinate(1) is the column to insert in to
   // you can change coordinates to see the image move around the box
   main.insertImage("img/Pineapple-Upside-down-cake_0_1.jpg", 0, 1);
   ```

4. Show GUI

   ```
   // true makes the dialog modal meaning that all interaction allowed is 
   //   in the windows methods.
   // false makes the dialog a pop-up which allows the background program 
   //   that spawned it to continue and process in the background.
   main.show(true);
   ```


### ClientGui.java
#### Summary

> This is the main GUI to display the picture grid. 

#### Methods
  - show(boolean modal) :  Shows the GUI frame with the current state
     * NOTE: modal means that it opens the GUI and suspends background processes. Processing still happens in the GUI If it is desired to continue processing in the background, set modal to false.
   * newGame(int dimension) :  Start a new game with a grid of dimension x dimension size
   * insertImage(String filename, int row, int col) :  Inserts an image into the grid, this is when you know the file name, use the PicturePanel insertImage if you have a ByteStream
   * appendOutput(String message) :  Appends text to the output panel
   * submitClicked() :  Button handler for the submit button in the output panel

### PicturePanel.java

#### Summary

> This is the image grid

#### Methods

- newGame(int dimension) :  Reset the board and set grid size to dimension x dimension
- insertImage(String fname, int row, int col) :  Insert an image at (col, row)
- insertImage(ByteArrayInputStream fname, int row, int col) :  Insert an image at (col, row)

### OutputPanel.java

#### Summary

> This is the input box, submit button, and output text area panel

#### Methods

- getInputText() :  Get the input text box text
- setInputText(String newText) :  Set the input text box text
- addEventHandlers(EventHandlers handlerObj) :  Add event listeners
- appendOutput(String message) :  Add message to output text

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
- 1, 2, 3, 4, 5, 6(partial), 7(partial), 8, 9, 10, 11(partial), 12(partial), 13, 14, 15  

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
    {"type": "hello", "value": <Integer>}

###### Response
Ok case
{"type": "hello", "value": <String>}
Error cases
{"type": "error", "message": <String>}

    String is:
    - "Invalid input. Please enter an integer" -- when input is not a number
    - "Please enter an integer from 1 to 6." -- when input is not from 1 to 6 (number of images available)
    - "input has no value" -- if the user does not have send a value

#### Playing the Game
###### Request
    {"type": "hello", "value": <String>}

###### Response
Ok case
{"type": "hello", "value": <String>}
Error cases
{"type": "error", "message": <String>}

    String is:
    - "Invalid input. Please enter an integer" -- when input is not a number
    - "Please enter an integer from 1 to 6." -- when input is not from 1 to 6 (number of images available)
    - "input has no value" -- if the user does not have send a value

#### Exit
###### Request
    {"type": "hello", "value": "quit"}

###### Response
Ok case
{"type": "quit", "value": <String>}

##### (e)Design Explanation

##### (f)Screen Capture Link

##### (g)UDP over TCP
-The program would have a couple differences if it used UDP instead of TCP. One difference is that UDP
does not send acknowledgements. This would mean that when monitoring wireshark, I would not be acknowledged
when a packet has arrived to its destination. Another difference is packets are not split when they are 
too large while using UDP. This means that data may be lost during the back and forth conversation if 
packets are too large. Packets would need to be split manually by the developer. 


