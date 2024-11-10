/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.InetAddress;
import java.net.Socket;
import java.net.UnknownHostException;
import java.util.Scanner;

/**
 *
 * @author Dell
 */
public class TCPClient {
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
  
            // Here we send something through the keyboard
            Scanner keyboardLine = new Scanner(System.in); 
            String line = null; 
  
            while (!"END".equalsIgnoreCase(line)) { 
                // We take one text line form the keyboard
                line = keyboardLine.nextLine();
                // ... and send it to the server
                out.println(line);
                // Clearing the stream
                out.flush(); 
                // Here we print the received response
                System.out.println("Produced content: "  + in.readLine()); 
            } 
            // End of using the keyboard
            keyboardLine.close(); 
        } 
        catch (IOException e) { 
            e.printStackTrace(); 
        }
    }
}
