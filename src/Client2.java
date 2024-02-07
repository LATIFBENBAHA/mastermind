import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.InetAddress;
import java.net.InetSocketAddress;
import java.net.Socket;
import java.util.Scanner;

//TIP To <b>Run</b> code, press <shortcut actionId="Run"/> or
// click the <icon src="AllIcons.Actions.Execute"/> icon in the gutter.
public class Client2 {

    public static String read(InputStream ip) throws IOException {

        byte[] buffer = new byte[1024];
        int bytesRead = ip.read(buffer);

        return new String(buffer, 0, bytesRead);
    }
    private  static void  send(OutputStream op, String guess) throws IOException {

        byte[] msg = guess.getBytes();
        op.write(msg);
    }
    public static void main(String[] args) throws IOException {
        Socket Csocket = new Socket();
        int port = Integer.parseInt(args[0]);
        InetAddress localhost  = InetAddress.getLocalHost();
        Csocket.connect(new InetSocketAddress(localhost,port));
        System.out.println("guess the right answer \ncolors are ; 'B', 'G', 'O', 'R', 'W', 'Y' \n");

        InputStream inputStream = Csocket.getInputStream();
        OutputStream outputStream=Csocket.getOutputStream();
        while (true){
            Scanner scanner = new Scanner(System.in);
            String guess="" ;
            guess +=scanner.nextLine();
            send(outputStream,guess);
            String resultat = read(inputStream);
            if (resultat.equals("Congrats")){
                System.out.println(resultat);
                break;
            }
            System.out.println("you have "+resultat.charAt(0)+" correct and "+resultat.charAt(2)+" incorrect");
        }
    }
}