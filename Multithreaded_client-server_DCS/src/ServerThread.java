
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;

public class ServerThread implements Runnable {

    private final Socket clientSocket; 
    private PrintWriter out;
    BufferedReader in;

    public ServerThread(Socket socket) 
    {
        this.clientSocket = socket; 
        System.out.println("The Server Thread No. " + Thread.currentThread().threadId() + " for the client " + socket.getPort() + " has started!");
    }
    @Override
    public void run() 
    {
        try {
            out = new PrintWriter(clientSocket.getOutputStream(), true);
            in = new BufferedReader(new InputStreamReader(clientSocket.getInputStream()));


            String line;
            while ((line = in.readLine()) != null) { 
                System.out.println(" Received: " + line); 

                ChatClass.broadcast(line, this);
            }

        } 
        catch (IOException e) {
            System.out.println("Client disconnected!");
        } finally {
            ChatClass.removeClient(this);
            closeConnections();
        }

    }

    public void sendMessage(String line) {
        if (out != null) {
            out.println(line);
        }
    }

    private void closeConnections() {
        try {
            if (clientSocket != null) clientSocket.close();
        } catch (IOException e) {
            System.err.println("Error closing connection: " + e.getMessage());
        }
    }


}
