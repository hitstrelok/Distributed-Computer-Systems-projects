import java.net.*;
import java.io.*;
import java.util.*;

public class UDPServer {
      
    static final int INPORT = 6666;
    private byte[] inbuf = new byte[1000];
    private byte[] outbuf;
    private DatagramPacket dp = new DatagramPacket(inbuf, inbuf.length);
    private DatagramSocket mysocket;

    private static Set<String>clients = new HashSet<>();

    public UDPServer() {
        try {
            mysocket = new DatagramSocket(INPORT);
            System.out.println("The server is up!");
            while(true) {
                mysocket.receive(dp);
				String message = new String(dp.getData(), 0, dp.getLength());

                            //String rcvd = message + ", from the host: " + dp.getAddress() +
                            //        ", port: " + dp.getPort();

                System.out.println("Message received: " + message);
                            //String echoString = "Message received: " + rcvd;

                String clientKey =dp.getAddress().getHostAddress()+":"+dp.getPort();
                clients.add(clientKey);


                for(String entry : clients){
                    if(!entry.equals(clientKey)){
                        outbuf = message.getBytes();
                        String[] string = entry.split(":");
                        InetAddress address = InetAddress.getByName(string[0]);
                        int port = Integer.parseInt(string[1]);


                        DatagramPacket echo = new DatagramPacket(outbuf, outbuf.length, address, port);
                        mysocket.send(echo);
                        System.out.println("Message was sent to: "+ address+" : "+ port);
                    }
                }

                inbuf = new byte[1000];
//                outbuf = message.getBytes();
//                DatagramPacket echo = new DatagramPacket(outbuf, outbuf.length, dp.getAddress(), dp.getPort());
//                mysocket.send(echo);
            }
        } catch(SocketException e) {
            System.err.println("Unable to open the socket!");
            System.exit(1);
        } catch(IOException e) {
            System.err.println("Communication error!");
            e.printStackTrace();
        }
    }
    
    public static void main(String[] args) {
        new UDPServer();
    }   
}