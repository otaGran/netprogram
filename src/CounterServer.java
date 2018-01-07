/**
 * Created by beans on 2018/1/5.
 */

import java.net.ServerSocket;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.net.Socket;


public class CounterServer {
    public static void main(String[] args) throws Exception {
        if (args.length != 1) {
            System.err.println("Usage: java CounterServer port");
            System.exit(1);
        }
        int port = Integer.parseInt(args[0]);
        ServerSocket m_ServerSocket = new ServerSocket(port);//create an socket listening on port which define by constructor
        System.out.println("CounterServer Listening on port: " + port);
        int id = 0;
        while (true) {//keep listening
            Socket clientSocket = m_ServerSocket.accept();//when there is a client
            ClientServiceThread cliThread = new ClientServiceThread(clientSocket, id++);
            //create a new Thread with client socket and id/counter number

            cliThread.start();
        }
    }
}

class ClientServiceThread extends Thread {
    Socket clientSocket;
    int clientlD = -1;

    ClientServiceThread(Socket s, int i) {
        clientSocket = s;
        clientlD = i;
    }

    @Override
    public void run() {
        System.out.println("ID/Counter: " + clientlD + "      Client Address: " + clientSocket.getRemoteSocketAddress().toString().split("/")[1]);
        try {
            PrintWriter out = new PrintWriter(new OutputStreamWriter(clientSocket.getOutputStream()));
            out.println(clientlD);//send out the id/counter
            out.flush();//flush the output stream
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
