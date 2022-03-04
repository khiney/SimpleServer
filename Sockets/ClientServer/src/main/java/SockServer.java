import java.net.*;
import java.io.*;
import org.json.*;

/**
 * This is the SockServer for assignment3 group assignment. SockServer has 2 functions for the user. First is the perfect square checker.
 * The user enters an integer and the server returns a statement telling you if the number is a perfect square
 * and the square root of the number if it is a perfect square. The second function is combine strings.
 * This function takes two strings and combines them by alternating letters.
 *
 * @author Korry Hinestroza
 *
 * @version 03 March 2022
 *
 */
public class SockServer {
  public static void main (String args[]) {

    Socket sock;
    boolean exit = false;
    try {
      //open socket
      ServerSocket serv = new ServerSocket(8888); // create server socket on port 8888
      System.out.println("Server ready for connections");

      while (true) {
        System.out.println("Server waiting for a connection");
        sock = serv.accept(); // blocking wait
        // setup the object reading channel
        ObjectInputStream in = new ObjectInputStream(sock.getInputStream());
        // get output channel
        OutputStream out = sock.getOutputStream();

        // create an object output writer (Java only)
        ObjectOutputStream os = new ObjectOutputStream(out);
        JSONObject res = new JSONObject();
        String s = (String) in.readObject();
        if(isJSONValid(s)){
          JSONObject req = new JSONObject(s);

          if(req.has("type")){
            if(req.getString("type").equals("exit")){
              exit = true;
              res.put("ok", true);
              res.put("data", "Thank you for using the server!");
            }else if ( req.getString("type").equals("p")){

              if(isNumeric(req.getString("data"))){
                System.out.println("Received the Integer " + req.getString("data"));
                res.put("ok", true);
                double num = Math.sqrt(Double.parseDouble(req.getString("data")));
                if(num - Math.floor(num) == 0){
                  String numSqrt = String.valueOf((int)num);
                  res.put("data", "Yes, " + req.getString("data")+" is a perfect square: "+numSqrt+" * "+numSqrt+" = "+req.getString("data"));
                }else{
                  res.put("data", "No, " + req.getString("data")+" is NOT a perfect square");
                }
              }else{
                res.put("ok", false);
                res.put("error", "Input is not an integer, please enter an integer");
              }

            }else if(req.getString("type").equals("c")){
              System.out.println("Received the String " + req.getString("data1"));
              System.out.println("Received the String " + req.getString("data2"));
              if(isString(req.getString("data1")) && isString(req.getString("data2"))){
               // handle other cases
                res.put("ok", true);
                res.put("data", "Here is your combined word: " + combine(req.getString("data1"),req.getString("data2")));

              }else if(req.getString("data1").length() == 0 || req.getString("data2").length() == 0){
                res.put("ok", false);
                res.put("error", "One of your inputs are empty. Please try again");
              }else{
                res.put("ok", false);
                res.put("error", "One of your inputs does not contain all letters. Please try again");
              }
            }else{
              res.put("ok", false);
              res.put("error", "Please enter p or c for type.");
            }
          }else{
            res.put("ok", false);
            res.put("error", "Type is empty, please enter a valid request Type.");
          }
        }else{
          res.put("ok", false);
          res.put("error", "Please input a valid JSON Object");
        }

        // write the whole message
        os.writeObject(res.toString());
        // make sure it wrote and doesn't get cached in a buffer
        os.flush();
        if(exit){
          sock.close();
          in.close();
          out.close();
          serv.close();
          break;
        }
      }
    } catch(Exception e) {
      e.printStackTrace();
    }
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
  public static String combine(String s1, String s2){
    String combined = "";
    int size = 0;
    if (s1.length() > s2.length()) size = s1.length();
    else size = s2.length();
    for(int i = 0; i < size; i++){
      if(s1.length()-1 < i){
        combined += s2.charAt(i);
      }else if(s2.length()-1 < i){
        combined += s1.charAt(i);
      }else{
        combined += s1.charAt(i);
        combined += s2.charAt(i);
      }
    }
    return combined;
  }
}


