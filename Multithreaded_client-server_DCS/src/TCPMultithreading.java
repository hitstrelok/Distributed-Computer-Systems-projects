import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;

public class TCPMultithreading {
    public static final int PORT = 7777;
    
    public static void main(String[] args) {
        ServerSocket mysocket;
  
        try {
            mysocket = new ServerSocket(PORT);
            mysocket.setReuseAddress(true);
            System.out.println("The server is up!");
            while (true) {
                Socket clientsocket = mysocket.accept();
                System.out.println("New client connected " + clientsocket.getInetAddress().getHostAddress());
  

                ServerThread mythread = new ServerThread(clientsocket);
                ChatClass.addClient(mythread);

                new Thread(mythread).start(); 
            } 
        } 
        catch (IOException e) { 
            e.printStackTrace(); 
        }
    }
    
}
