
import java.net.*;
import java.io.*;
import java.util.Arrays;
import java.util.Scanner;

public class UDPClient extends Thread {
    private DatagramSocket mysocket;
    private InetAddress hostAddress;
    private byte[] inbuf = new byte[1000];
    private byte[] outbuf;
    private DatagramPacket dp = new DatagramPacket(inbuf, inbuf.length);

    Scanner scanner = new Scanner(System.in);
    public UDPClient() {
        try {
            mysocket = new DatagramSocket();
            hostAddress = InetAddress.getByName("127.0.0.1");
        } catch(UnknownHostException e) {
            System.err.println("Unable to locate this server!");
            System.exit(1);
        } catch(SocketException e) {
            System.err.println("Unable to open the socket");
            e.printStackTrace();
            System.exit(1);
        }
        System.out.println("The UDP client is up " +hostAddress+": "+ mysocket.getLocalPort());
        try {
            mysocket.connect(hostAddress, UDPServer.INPORT);
            if (mysocket.isConnected())
                System.out.println("Successfully connected to " + mysocket.getRemoteSocketAddress());

            outbuf="Connection!".getBytes();
            mysocket.send(new DatagramPacket(outbuf, outbuf.length, hostAddress, UDPServer.INPORT));

            new Thread(()->{//Recieve messages!
            while (true) {
                try {
                    mysocket.receive(dp);
                    String message = new String(dp.getData(), 0, dp.getLength());
                    System.out.println(message);
                    if(message=="END"){
                        System.out.println("Connection is stopped.");
                        mysocket.disconnect();
                        mysocket.close();
                    }
                } catch (IOException e) {
                    throw new RuntimeException(e);
                }
            }
        }).start();
            System.out.println("Type message to other clients:");
            while(true){//Send messages!
                outbuf= scanner.nextLine().getBytes();
                if(Arrays.equals(outbuf, "END".getBytes())){
                    System.out.println("Connection is stopped.");
                    mysocket.send(new DatagramPacket(outbuf, outbuf.length, hostAddress, UDPServer.INPORT));
                    mysocket.disconnect();
                    mysocket.close();
                }
                else {
                    mysocket.send(new DatagramPacket(outbuf, outbuf.length, hostAddress, UDPServer.INPORT));
                }

            }


	} catch(IOException e) {
            e.printStackTrace();
            System.exit(1);
        }
    }
   
    public static void main(String[] args) {
            new UDPClient();
    }
}

