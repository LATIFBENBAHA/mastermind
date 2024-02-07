import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.Random;

public class SocketHandler implements Runnable{
    Socket Csocket;
    public SocketHandler(Socket socket){
        this.Csocket = socket;
    }
    public static String read(InputStream ip) throws IOException {

        byte[] buffer = new byte[1024];
        int bytesRead = ip.read(buffer);
        if (bytesRead == -1) {
            // La connexion a été fermée par le client
            return null;
        }
        return new String(buffer, 0, bytesRead);
    }
    private  static void  send(OutputStream op, String resultat) throws IOException {

        byte[] msg = resultat.getBytes();
        op.write(msg);
    }
    private static String randomCombi (){
        char[] alphabets = {'B', 'G', 'O', 'R', 'W', 'Y'};
        int length = 4;

        StringBuilder randomString = new StringBuilder();

        Random random = new Random();

        for (int i = 0; i < length; i++) {
            int randomIndex = random.nextInt(alphabets.length);
            char randomLetter = alphabets[randomIndex];
            randomString.append(randomLetter);
        }

        return randomString.toString();

    }
    private static boolean apartenant(char C,String string){
        for (int i=0; i<string.length(); i++){
            if (string.charAt(i) == C) {
                return true;
            }
        }
        return false;
    }
    private static String Guess(String repense,String string){
        int resultIncorrect = 0;
        int resultCorrect = 0;
        char color;
        StringBuilder lstring = new StringBuilder();
        if (repense.equals(string)) return "Congrats";
        for (int i=0;i<string.length();i++){
            color=string.charAt(i);
            if (repense.charAt(i) == string.charAt(i)) {
                color = 'X';
                resultCorrect++;
            }
            lstring.append(color);
        }
        String l2string = " ";
        for (int i=0; i<string.length(); i++){
            if (apartenant(repense.charAt(i), lstring.toString())){
                if ((repense.charAt(i) != string.charAt(i)) && !apartenant(repense.charAt(i), l2string)){
                    resultIncorrect += 1;
                }
                l2string +=repense.charAt(i);
            }
        }
        return resultCorrect+" "+resultIncorrect ;
    }
    @Override
    public void run() {
        try {


        System.out.println("Server is waiting for connections...");
        String code = randomCombi();
        System.out.println("start the game : "+code);
        OutputStream outputStream = Csocket.getOutputStream();
        InputStream inputStream = Csocket.getInputStream();
        String reponse ;

        while (true) {
            reponse = read(inputStream);
            reponse =reponse.toUpperCase();
            System.out.println("the guess is : "+reponse);
            send(outputStream,Guess(reponse,code));
            System.out.println(Guess(reponse, code));
            if (Guess(reponse,code).equals("Congrats")) break;
        }
        }catch (IOException e) {
            e.printStackTrace();
        }

    }
}
