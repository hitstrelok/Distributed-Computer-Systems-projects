/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author Peter
 */
public class ServerThread implements Runnable {
    // A reference to the client socket
    private final Socket clientSocket; 
    private final int sleepTime = 1000;
  
    // A constructor to get the socket handler
    public ServerThread(Socket socket) 
    {
        // Here we assign the client socket to the thread
        this.clientSocket = socket; 
        System.out.println("The Server Thread No. " + Thread.currentThread().threadId() + " for the client " + socket.getPort() + " has started!");
    } 
    
    /**
     *
     */
    @Override
    public void run() 
    { 
        PrintWriter out = null; 
        BufferedReader in = null; 
        try {                 
            // This is the output stream
            out = new PrintWriter(clientSocket.getOutputStream(), true); 
            // This is the input stream
            in = new BufferedReader(new InputStreamReader(clientSocket.getInputStream())); 
            String line; 
            while ((line = in.readLine()) != null) { 
                System.out.println(" Received: " + line); 
                // Here we put the thread to sleep to show multiple are at work
                Thread.sleep(sleepTime);
                out.println("<strong>" + line + "</strong>"); 
            } 
        } 
        catch (IOException e) { 
            e.printStackTrace(); 
        } catch (InterruptedException ex) {
            Logger.getLogger(ServerThread.class.getName()).log(Level.SEVERE, null, ex);
        } 
        finally { 
            try { 
                // Here we close the connection
                if (out != null) { 
                    out.close(); 
                } 
                if (in != null) { 
                    in.close(); 
                    clientSocket.close(); 
                } 
            } 
            catch (IOException e) { 
                e.printStackTrace(); 
            } 
        } 
    } 
} 
