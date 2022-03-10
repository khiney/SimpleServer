##### Author: Korry Hinestroza
* Version: March 2022


##### Purpose
SockServer - SockServer has 2 functions for the user. First is the perfect square checker. 
The user enters a positive integer and the server returns a statement telling you if the number is a perfect square 
and the square root of the number if it is a perfect square. The second function is combine strings. 
This function takes two strings and combines them by alternating letters.
* `Server` ip = '54.146.175.135', port = '8888'

##### Protocol Description

#### Perfect Square Check
###### Request
    {"type": "p", "data": <String>}

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
    Type first word and press enter. Type second word and press enter.

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


#### Request type/data missing / unknown
Server will respond with:
{"ok": "false", "message": "Please enter p or c for type."}
{"ok": false, "error": "Type is empty, please enter a valid request Type."}
{"ok": false, "error": "Data field is empty, please include data in request."}


#### JSON Object invalid
Server will respond with:
{"ok": false, "error": "Please input a valid JSON Object"}Data field is empty, please include data in request.


