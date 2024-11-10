/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Main.java to edit this template
 */

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;

/**
 * This is the mutlithreaded TCP server example
 * @author Peter
 */
public class TCPMultithreading {

    /**
     * @param args the command line arguments
     */
    public static final int PORT = 7777;
    
    public static void main(String[] args) {
        // First, we prepare the socket
        ServerSocket myserver = null; 
  
        try {
            // We open the socket
            myserver = new ServerSocket(PORT); 
            // This is to maintain the connection open even if there is no client
            myserver.setReuseAddress(true); 
            // Infinite loop for incoming requests
            System.out.println("The server is up!");
            while (true) {
                // Here we accept the connection of the client
                Socket clientsocket = myserver.accept(); 
                // Here we inform about establishing the connection 
                System.out.println("New client connected" + clientsocket.getInetAddress().getHostAddress()); 
  
                // Here we create the new thread to process the client - we must pass the socket!
                ServerThread mythread = new ServerThread(clientsocket); 
                // ... and start it
                new Thread(mythread).start(); 
            } 
        } 
        catch (IOException e) { 
            e.printStackTrace(); 
        } 
        finally { 
            if (myserver != null) { 
                try { 
                    myserver.close(); 
                } 
                catch (IOException e) { 
                    e.printStackTrace(); 
                } 
            } 
        } 
    }
    
}
