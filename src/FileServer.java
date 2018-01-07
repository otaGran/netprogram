
import java.io.DataInputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;

public class FileServer{

    public static void main(String[] args) {
        String filePath = "C:\\Users\\beans_pc\\Desktop\\netprogram\\";
        int port = 33456;
        int clientlD = 0;
        try {
            final ServerSocket server = new ServerSocket(port);
            System.out.printf("\nFiles will be stored at : %s\n",filePath);
            System.out.printf("FileServer Listening on port: %d\n\n",port);
            while(true){
                Socket clientSocket = server.accept();//when there is a client
                FileServerThread cliThread = new FileServerThread(clientSocket, clientlD++,filePath);
                //create a new Thread with client socket and id/counter number
                cliThread.start();
            }
        } catch (Exception ex) {
            ex.printStackTrace();
        }

    }


}

class FileServerThread extends Thread {
    Socket clientSocket;
    int clientlD = -1;
    String filePath;
    public FileServerThread(Socket clientSocket, int clientlD,String filePath) {
        this.clientSocket = clientSocket;
        this.filePath = filePath;
        this.clientlD = clientlD;

    }

    @Override
    public void run() {
        System.out.println("____ID: " + clientlD + "    Client Address: " + clientSocket.getRemoteSocketAddress().toString().split("/")[1]);
        try {
            receiveFile(clientSocket,filePath);
        }catch(Exception e){
            e.printStackTrace();
        }
    }

    public static void receiveFile(Socket socket,String filePath) throws IOException {
        byte[] inputByte = null;
        int length = 0;
        DataInputStream din = null;
        FileOutputStream fout = null;
        try {
            din = new DataInputStream(socket.getInputStream());
            String file_name = din.readUTF();
            fout = new FileOutputStream(new File(filePath+file_name));
            inputByte = new byte[1024];
            System.out.print("____Receing data.......");
            while (true) {
                if (din != null) {
                    length = din.read(inputByte, 0, inputByte.length);
                }
                if (length == -1) {
                    break;
                }
                fout.write(inputByte, 0, length);
                fout.flush();
            }
            System.out.printf("\n____%s Successfully received from Client\n\n",file_name);
        } catch (Exception ex) {
            ex.printStackTrace();
        } finally {
            if (fout != null)
                fout.close();
            if (din != null)
                din.close();
            if (socket != null)
                socket.close();
        }
    }
}