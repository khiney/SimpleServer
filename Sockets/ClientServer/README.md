##### Author: Korry Hinestroza
* Version: March 2022


##### Purpose
SockServer - SockServer has 2 functions for the user. First is the perfect square checker. 
The user enters an integer and the server returns a statement telling you if the number is a perfect square 
and the square root of the number if it is a perfect square. The second function is combine strings. 
This function takes two strings and combines them by alternating letters.
* `Server` ip = '54.146.175.135', port = '8888'

##### Protocol Description

#### Perfect Square Check
###### Request
    {"type": "p", "data": <Integer>}

**"ok" is "true" for successful cases**
**"ok" is "false" for unsuccessful cases**
###### Response
Ok case
{"type": "p", "data": <String>}
Error cases
{"type": "error", "message": <String>}

    String is:
    - "Input is not an integer, please enter an integer" -- if the "data" field in the request is not an Integer
    - "Type is empty, please enter a valid request Type." -- if type field is missing
    - "Please input a valid JSON Object" -- if input is not a valid JSON


#### Combine Strings
###### Request
    {"type": "c", "data1": <String>, "data2": <String>}
    
    Entering data1/data2:
    - When entering data1 and data2 each word is entered one at a time 
    Enter first word and press enter. Enter second word and press enter.

###### Response
Ok case
{"type": "c", "data": <String>}
Error cases
{"type": "error", "message": <String>}

    String is:
    - "One of your inputs does not contain all letters. Please try again" -- when one of your inputs is not all letters
    - "One of your inputs are empty. Please try again" -- one of your strings are empty
    - "Please input a valid JSON Object" -- if input is not a valid JSON

#### Exit
###### Request
    {"type": "exit"}

###### Response
Ok case 
{"type": "exit", "data": <String>}


#### Request type missing / unknown
Server will respond with:
{"type": "error", "message": "Please enter p or c for type."}
{"type": "error", "message": "Type is empty, please enter a valid request Type."}


#### JSON Object invalid
Server will respond with:
{"type": "error", "message": "Please input a valid JSON Object"}


