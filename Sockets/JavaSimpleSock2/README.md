##### Author: Korry Hinestroza
* Version: March 2022


##### Purpose
Each program has a short description as well as the Gradle file
* Please run `Server` and `Client` together.

##### Protocol Description

#### Perfect Square Check
###### Request
    {"type": "p", "data": <Integer>}

###### Response
Ok case
{"type": "p", "data": <String>}
Error cases
{"type": "error", "message": <String>}

    String is:
    - "no String" -- if the "data" field in the request is not a String
    - "Data missing" -- if the request does not have a "data" field


#### Reverse a String
###### Request
    {"type": "reverse", "data": <String>}

###### Response
Ok case
{"type": "reverse", "data": <String>}
Error cases:
no error cases implemented, client will likely not receive an answer -- this is of course not good


#### Exit
###### Request
    {"type": "exit"}

###### Response
Ok case
{"type": "exit", "data": <String>}
Error cases:
no error cases implemented, client will likely not receive an answer -- this is of course not good


#### Request type unknown
Server will respond with:
{"type": "error", "message": "Request type not known"}

