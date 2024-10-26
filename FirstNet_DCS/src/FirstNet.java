import java.io.IOException;
import java.net.InetAddress;
import java.net.UnknownHostException;


public class FirstNet {

    public static void main(String[] args) {
        InetAddress myNode, remoteNode, googleNode;
        byte[] ipaddr;
        if(args.length>0){
            System.out.println("Okay!");
        }

        try {
            //////////////////////////////////2 4
            myNode = InetAddress.getLocalHost();
            System.out.println("Local address: " + args[0]);
            System.out.println("Local hostname: " + args[1]);
            System.out.println("Canonical hostname: " + myNode.getCanonicalHostName());
            System.out.println("Hashcode of IP address: " + myNode.hashCode());
            System.out.println("Is multicast?: "+myNode.isMulticastAddress());
            if (myNode.isLoopbackAddress()) {
                System.out.println("This is the loopback address");
            }

            System.out.println(" ");

            //////////////////////////////////3
            googleNode = InetAddress.getByName("www.google.com");
            System.out.println("Remote hostname: "+ googleNode.getHostName());
            System.out.println("Remote address: "+ googleNode.getHostAddress());

            System.out.println(" ");


            //////////////////////////////////5
            remoteNode = InetAddress.getByAddress(googleNode.getAddress());
            System.out.println("Remote hostname: " + remoteNode.getHostName());
            System.out.println("Remote address: "+ remoteNode.getHostAddress());
            System.out.println(remoteNode.isReachable(1000));

            System.out.println(" ");

            //////////////////////////////////6
            ipaddr = myNode.getAddress();
            for(int i=1; i<255; i++){
                ipaddr[3] = (byte)i;
                remoteNode = InetAddress.getByAddress(ipaddr);
                try{
                    if(remoteNode.isReachable(1000))
                        System.out.println(remoteNode.getHostAddress());
                }
                catch (Exception e){

                }
            }


        }
        catch (UnknownHostException e) {
            System.out.println(e.getMessage());
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

    }
}
