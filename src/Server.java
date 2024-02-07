import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.Random;

//TIP To <b>Run</b> code, press <shortcut actionId="Run"/> or
// click the <icon src="AllIcons.Actions.Execute"/> icon in the gutter.
public class Server {


    // Avant l'amélioration :
    public static void main(String[] args) throws IOException {

        ServerSocket serverSocket = new ServerSocket(1234);
        int i=1;
        while (true){
            Socket Csocket = serverSocket.accept();
            System.out.println("connection"+i+" : : :");
            i++;
            SocketHandler socketHandler = new SocketHandler(Csocket);
            new Thread(socketHandler).start();
        }


    }
}