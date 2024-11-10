import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.InetAddress;
import java.net.Socket;
import java.net.UnknownHostException;
import java.util.Scanner;

public class TCPClient2 {
    public static void main(String[] args) throws UnknownHostException 
    {
        InetAddress address = InetAddress.getByName("localhost");
        try (Socket socket = new Socket(address, TCPMultithreading.PORT))
        {
            System.out.println("Client with the socket " + socket.getLocalPort() + " has started!");
            // Output stream
            PrintWriter out = new PrintWriter(socket.getOutputStream(), true);  
            // Input stream
            BufferedReader in = new BufferedReader(new InputStreamReader(socket.getInputStream()));

            //LISTENING TO SERVER
            new Thread(() -> {
                String response;
                try{
                    while((response = in.readLine()) !=null){
                        System.out.println("Server: " + response);
                    }
                } catch (IOException e){
                    System.out.println("Disconnection!");
                }
            }).start();

            Scanner keyboardLine = new Scanner(System.in);
            String line;

            while (!(line = keyboardLine.nextLine()).equalsIgnoreCase("END")) {
                out.println(line); //TO THE SERVER!
            } 

            keyboardLine.close();
        } 
        catch (IOException e) { 
            e.printStackTrace(); 
        }
    }
}
