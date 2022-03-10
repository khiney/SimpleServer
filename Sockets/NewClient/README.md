##### Author: Jessica Thiel
* Version: March 2022


##### Purpose
SockServer - Has 2 main functions for user. The first is to find the GCD of two numbers. Where the user inputs two integers and will receive from the server an integer representing the GCD. The second is to find the prime factor of a number. Where the user inputs one integer and receives a string containing the prime factors of that number.
* `Server` ip = '3.88.193.49', port = '8080'

##### Protocol Description


#### GCD
###### Request
    {"type": "g", "data1": <Integer>, "data2": <Integer>}

    Entering Inputs:
    - data1 and data2 will be entered on separate lines.

###### Response
Ok case
{"type": "g", "data": <Integer>}
Error cases
{"type": "error", "message": <String>}

    String is:
    - "Input is not an integer." -- If one of the "data" field in the request is not an Integer
    - "Missing second input." -- If the request does not have two "data" field
    - "Inputs are missing." -- If the request does not have a second "data" field
    - "Invalid JSON Object" -- If the input is an invalid JSON Object


#### Prime Factoring
###### Request
    {"type": "f", "data": <Integer>}

###### Response
Ok case
{"type": "f", "data": <String>}
Error cases:
{"type": "error", "message": <String>}

    String is:
    - "Input is not an integer." -- If the "data" field in the request is not an Integer
    - "Input is missing." -- If the request does not have a "data" field
    - "Invalid JSON Object." -- If the input is an invalid JSON Object


#### Exit
###### Request
    {"type": "exit"}

###### Response
Ok case
{"type": "exit", "data": <String>}


#### Request type invalid
Server will respond with:
{"type": "error", "message": "Invalid input please use l or f."}


#### JSON Object failed
Server will respond with:
{"type": "error", "message": "Invalid JSON Object."}

