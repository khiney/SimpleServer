import java.net.*;
import java.io.*;
import java.util.Scanner;
import org.json.*;

/**
 * This is the SockClient for assignment3. SockClient is the client for SockServer
 *
 * @author Korry Hinestroza
 *
 * @version 03 March 2022
 *
 */
class SockClient {
  public static void main (String args[]) {
    Socket sock = null;
    String host = "localhost";
    Scanner scanner = new Scanner(System.in);

    try {
      // open the connection
      sock = new Socket(host, 8888); // connect to host and socket on port 8888

      // get output channel
      OutputStream out = sock.getOutputStream();

      // create an object output writer (Java only)
      ObjectOutputStream os = new ObjectOutputStream(out);

      ObjectInputStream in = new ObjectInputStream(sock.getInputStream());

      System.out.println("You want to check for perfect square (p), combine strings (c), or exit (exit)?");
      String choice = scanner.nextLine();



      JSONObject request = new JSONObject();

      if (choice.equals("p")){
        System.out.println("What is your number?");
        String input = scanner.nextLine();
        request.put("type", "p");
        request.put("data", input);
      } else if(choice.equals("c")) {
        // send over "reverse" request
        System.out.println("What is your first String?");
        String input1 = scanner.nextLine();
        System.out.println("What is your second String?");
        String input2 = scanner.nextLine();
        request.put("type", "c");
        request.put("data1", input1);
        request.put("data2", input2);
      } else if(choice.equals("exit")){
        request.put("type", "exit");
      }

      // write the whole message
      os.writeObject(request.toString());

      // make sure it wrote and doesn't get cached in a buffer
      os.flush();

      String i = (String) in.readObject();
      JSONObject res = new JSONObject(i);

      if (res.getBoolean("ok") && choice.equals("p")){
        System.out.println(res.getString("data"));
      }else if(res.getBoolean("ok") && (choice.equals("c")||choice.equals("exit"))){
        System.out.println(res.getString("data"));
      }else{
        System.out.println(res.getString("error"));
      }
      sock.close(); // close socked after sending
    } catch (Exception e) {e.printStackTrace();}
  }
}