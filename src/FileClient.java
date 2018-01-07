
import java.io.DataOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.net.InetSocketAddress;
import java.net.Socket;

public class FileClient {

    public static void main(String[] args) {
        String filePath = "C:\\Users\\beans_pc\\Desktop\\test.jpg";
        int length = 0;
        byte[] sendByte = null;
        Socket socket = null;
        DataOutputStream dout = null;
        FileInputStream fin = null;
        try {
            try {
                socket = new Socket();
                socket.connect(new InetSocketAddress("127.0.0.1", 33456),10 * 1000);
                dout = new DataOutputStream(socket.getOutputStream());//
                File file = new File(filePath);//open file
                fin = new FileInputStream(file);//get file stream
                sendByte = new byte[1024];//init the send buffer
                dout.writeUTF(file.getName());//first transport the file name coded by UTF
                while((length = fin.read(sendByte, 0, sendByte.length))>0){//sending actal data
                    dout.write(sendByte,0,length);
                    dout.flush();
                }
            } catch (Exception e) {

            } finally{
                if (dout != null)
                    dout.close();
                if (fin != null)
                    fin.close();
                if (socket != null)
                    socket.close();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

}