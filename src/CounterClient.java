import java.io.InputStream;
import java.io.OutputStream;
import java.io.PrintWriter;
import java.util.*;
import java.net.*;

public class CounterClient {
    public static void main(String[] args) {
        if (args.length != 2) {
            System.err.println("Usage: java EchoClient host port");
            System.exit(1);
        }

        try {
            String host = args[0];
            int port = Integer.parseInt(args[1]);
            Socket socket = new Socket(host, port);
            InputStream socketlnputStream = socket.getInputStream();
            Scanner socketScanner = new Scanner(socketlnputStream);
            OutputStream socketOutputStream = socket.getOutputStream();
            PrintWriter socketPrintWriter = new PrintWriter(socketOutputStream);
            Scanner scanner = new Scanner(System.in);
            while (true) {
                //String line = scanner.nextLine();
                //socketPrintWriter.println(line);
                //socketPrintWriter.flush();
                if (!socketScanner.hasNextLine())
                    break;
                String echoedLine = socketScanner.nextLine();
                System.out.println(echoedLine);
            }
            socket.close();
        } catch (Exception e) {
            System.err.println(e);
        }
    }
}
