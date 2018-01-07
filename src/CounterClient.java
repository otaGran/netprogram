/**
 * Created by beans on 2018/1/5.
 */

import java.io.InputStream;
import java.io.OutputStream;
import java.io.PrintWriter;
import java.util.*;
import java.net.*;

public class CounterClient {
    public static void main(String[] args) {
        if (args.length != 2) {
            System.err.println("Usage: java CounterClient host port");
            System.exit(1);
        }

        try {
            String host = args[0];
            int port = Integer.parseInt(args[1]);
            Socket socket = new Socket(host, port);//create a new socket with hostAddress and port
            InputStream socketlnputStream = socket.getInputStream();
            Scanner socketScanner = new Scanner(socketlnputStream);

            System.out.println(socketScanner.nextLine());

            socket.close();
        } catch (Exception e) {
            System.err.println(e);
        }
    }
}
