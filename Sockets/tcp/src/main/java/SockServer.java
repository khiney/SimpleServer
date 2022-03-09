package src.main.java;

import java.net.*;
import java.util.Base64;
import java.util.Set;
import java.util.Stack;
import java.util.*;

import javax.imageio.ImageIO;
import javax.swing.ImageIcon;

import java.awt.image.BufferedImage;
import java.io.*;
import org.json.*;




/**
 * A class to demonstrate a simple client-server connection using sockets.
 * Ser321 Foundations of Distributed Software Systems
 * see http://pooh.poly.asu.edu/Ser321
 * @author Tim Lindquist Tim.Lindquist@asu.edu
 *         Software Engineering, CIDSE, IAFSE, ASU Poly
 * @version August 2020
 *
 *
 * Describing the simple protocol here
 *
 * request (starting the connection)
 * 	type: start
 * Response:
 * OK
 *  type: hello
 *  image: <String> encoded image
 *  value: <String> asking for name of player
 * Error
 * 	type: error
 *  message: <String> Error message
 *
 *
 * @modified-by David Clements <dacleme1@asu.edu> September 2020
 */
public class SockServer {
  static Stack<String> imageSource = new Stack<String>();

  public static void main (String args[]) {
    Socket sock;
    try {
      int port = 0;
      if(args.length==1){
        port = Integer.parseInt(args[0]);
      }else{
        System.out.println("Expected arguments: <port(int)>\nExiting program...");
        System.exit(1);
      }
      System.out.println("Connecting to port "+ port);
      //opening the socket here, just hard coded since this is just a bas example
      ServerSocket serv = new ServerSocket(port); // TODO, should not be hardcoded
      System.out.println("Server ready for connetion");

      // placeholder for the person who wants to play a game

      String name = "";
      int points = 0;
      int clientID = 0;
      String numRounds = "";
      int num=0;
      boolean getName = false;
      boolean getNum = false;
      boolean playGame = false;
      boolean exit = false;
      boolean begin = false;
      //ArrayList<> puppy =


      // read in one object, the message. we know a string was written only by knowing what the client sent.
      // must cast the object from Object to desired type to be useful
      while(true) {
        sock = serv.accept(); // blocking wait
        // setup the object reading channel

        // could totally use other input output streams here
        ObjectInputStream in = new ObjectInputStream(sock.getInputStream());
        OutputStream out = sock.getOutputStream();

        String s = (String) in.readObject();
        JSONObject response = new JSONObject();
        if(isJSONValid(s)){
          JSONObject json = new JSONObject(s); // the requests that is received

          if(json.has("type")){
            if (json.getString("type").equals("begin") && !begin){

              System.out.println("- Got a start");

              response.put("type","hello" );
              response.put("value","Hello, please tell me your name." );
              sendPic("img/lose.jpg", response); // calling a method that will manipulate the image and will make it send ready
              getName = true;
              begin = true;
            }if(json.has("value")){
              if (json.getString("value").equalsIgnoreCase("quit")){
                response.put("type","quit" );
                response.put("value","Thanks for playing the game. Goodbye" );
                exit = true;
              }
              else if(getName){
                if(json.getString("value").length() != 0){
                  name = json.getString("value");
                  response.put("type","hello" );
                  response.put("value","Hello "+ json.getString("value")+"\nHow many games would you like to play?");
                  getName = false;
                  getNum=true;
                }else{
                  response.put("type","error" );
                  response.put("message","Input is empty. Please enter your name.");
                }
              }
              else if(getNum){
                if(isNumeric(json.getString("value"))){
                  if(Integer.parseInt(json.getString("value")) <= 6 && Integer.parseInt(json.getString("value")) >= 1){
                    numRounds = json.getString("value");
                    System.out.println(numRounds);
                    num = Integer.parseInt(json.getString("value"));
                    response.put("type","hello" );
                    response.put("value","Ok, you will play "+ numRounds +" rounds!\nAre you ready to start? Type start or quit if you don't want to play.");
                    getNum=false;
                  }else{
                    response.put("type","error" );
                    response.put("message","Please enter an integer from 1 to 6.");
                  }
                }else{
                  response.put("type","error" );
                  response.put("message","Invalid input. Please enter an integer");
                }
              }
              else if(json.getString("value").equalsIgnoreCase("start") && !(playGame || getNum || getName)){
                response.put("type","hello" );
                response.put("value","display first image of puppy");
                playGame = true;
              }
              else if(playGame){
                if(json.getString("value").equalsIgnoreCase("more")){
                  response.put("type","image" );
                  response.put("value","display second image of puppy");
                }else if(json.getString("value").equalsIgnoreCase("next")){
                  response.put("type","image" );
                  response.put("value","display second image of puppy");
                }else if(json.getString("value").length()==0){
                  response.put("type","error" );
                  response.put("message","Input empty");
                }else{
                  if(json.getString("value").equalsIgnoreCase("puppy")){
                    response.put("type","hello" );
                    response.put("value","correct! + 30 points!");
                    points+=30;
                    num--;
                  }else{
                    response.put("type","hello" );
                    response.put("value","WROOONG!!!");
                  }
                }
                //num--;
                if (num <= 0){
                  playGame = false;
                }
              }
              else {
                System.out.println("not sure what you meant");
                response.put("type","error" );
                response.put("message","unknown response" );
              }
            }else{
              response.put("type","error" );
              response.put("message","input has no value" );
            }
          }else{
            response.put("type","error");
            response.put("message","type unknown. Please input valid type" );
          }
        }
        else{
          response.put("type","error" );
          response.put("message","Please enter valid JSON");
        }
        PrintWriter outWrite = new PrintWriter(sock.getOutputStream(), true); // using a PrintWriter here, you could also use and ObjectOutputStream or anything you fancy
        outWrite.println(response.toString());  //Parse to only display string in Client
        if(exit){
          sock.close();
          in.close();
          out.close();
          serv.close();
          break;
        }
      }

    } catch(Exception e) {e.printStackTrace();}
  }

  /* TODO this is for you to implement, I just put a place holder here */
  public static JSONObject sendPic(String filename, JSONObject obj) throws Exception {
    File file = new File(filename);
    PicturePanel sendPic = new PicturePanel();
    if (file.exists()) {
      // import image
      // I did not use the Advanced Custom protocol
      // I read in the image and translated it into basically into a string and send it back to the client where I then decoded again
      obj.put("image", "Pretend I am this image: " + filename);
      //insertImage(filename, 0, 0);
    }else{

    }
    return obj;
  }
  public static boolean isJSONValid(String test) {
    try {
      new JSONObject(test);
    } catch (JSONException ex) {
      /*try {
        new JSONArray(test);
      } catch (JSONException ex1) {     //In case I need to verify JSON array later
        // PUT FALSE STATEMENT HERE WHEN VERIFYING!!!!
      }*/
      return false;
    }
    return true;
  }
  public static boolean isNumeric(String str) {
    if (str == null) {
      return false;
    }
    int length = str.length();
    if (length == 0) {
      return false;
    }
    int i = 0;
    if (str.charAt(0) == '-') {
      if (length == 1) {
        return false;
      }
      i = 1;
    }
    for (; i < length; i++) {
      char c = str.charAt(i);
      if (c < '0' || c > '9') {
        return false;
      }
    }
    return true;
  }
  public static boolean isString(String s) {
    if (s == null) {
      return false;
    }
    if (s.length() == 0) {
      return false;
    }

    for (int i = 0; i < s.length(); i++) {
      char c = s.charAt(i);
      if (!(c >= 'A' && c <= 'Z') && !(c >= 'a' && c <= 'z')) {
        return false;
      }
    }
    return true;
  }
}


