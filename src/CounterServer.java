/**
 * Created by beans on 2018/1/5.
 */

import java.io.BufferedReader;
import java.net.ServerSocket;

import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.net.Socket;



public class CounterServer {
    static int cnt = 0;
    public static void main(String[] args) throws Exception {
        ServerSocket m_ServerSocket = new ServerSocket(12111);
        int id = 0;
        while (true) {
            Socket clientSocket = m_ServerSocket.accept();

            ClientServiceThread cliThread = new ClientServiceThread(clientSocket, id++);
            cliThread.start();
        }
    }
    static int getCnt(){
        return cnt++;
    }
}
class ClientServiceThread extends Thread {
    Socket clientSocket;
    int clientlD = -1;
    boolean running = true;

    ClientServiceThread(Socket s, int i) {
        clientSocket = s;
        clientlD = i;
    }

    public void run() {
        System.out.println("Accepted Client : ID - " + clientlD + " : Address - " + clientSocket.getInetAddress().getHostName());
        try {
            BufferedReader in = new BufferedReader(new InputStreamReader(clientSocket.getInputStream()));
            PrintWriter out = new PrintWriter(new OutputStreamWriter(clientSocket.getOutputStream()));

                out.println(CounterServer.getCnt());
                out.flush();

        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
