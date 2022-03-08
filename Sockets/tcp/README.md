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
SockServer - sockServer implements the image guessing game. The server gets information from the user 
prior to starting the game such as name and number of rounds the user wishes to play. The server then sends 
the client an image and allows them to guess the image, get a hint(clearer image), or move on to the next image.
The user receives 30 points for correct guesses.

ClientGUI - clientGUI provides the GUI to the client. It also takes input from the client (via GUI textbox)
* Server: use `runServer` to connect to default port '8080' or use `runServer -Pport=<port#>` for custom port
* Client: 

##### Protocol Description

#### Perfect Square Check
###### Request
    {"type": "p", "data": <Integer>}

**"ok" is "true" for successful cases**

**"ok" is "false" for unsuccessful cases**
###### Response
Ok case
{"ok": true, "data": <String>}
Error cases
{"ok": false, "error": <String>}

    String is:
    - "Input is not an integer, please enter an integer" -- if the "data" field in the request is not an Integer
    - "Type is empty, please enter a valid request Type." -- if type field is missing
    - "Invalid number. Please enter a positive integer." -- if integer is negative


#### Combine Strings
###### Request
    {"type": "c", "data1": <String>, "data2": <String>}
    
    Entering data1/data2:
    - When entering data1 and data2 each word is entered one at a time 
    Enter first word and press enter. Enter second word and press enter.

###### Response
Ok case
{"ok": true, "data": <String>}
Error cases
{"ok": false, "error": <String>}

    String is:
    - "One of your inputs does not contain all letters. Please try again" -- when one of your inputs is not all letters
    - "One of your inputs are empty. Please try again" -- one of your strings are empty


#### Exit
###### Request
    {"type": "exit"}

###### Response
Ok case
{"type": "exit", "data": <String>}


#### Request type missing / unknown
Server will respond with:
{"type": "error", "message": "Please enter p or c for type."}
{"ok": false, "error": "Type is empty, please enter a valid request Type."}


#### JSON Object invalid
Server will respond with:
{"ok": false, "error": "Please input a valid JSON Object"}