import java.net.Inet4Address;
import java.net.InetAddress;
import java.net.NetworkInterface;
import java.net.SocketException;
import java.net.UnknownHostException;
import java.util.*;

/**
 *
 * @author Dell
 */
public class Interfaces {

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        try {
            InetAddress localHost = Inet4Address.getLocalHost();
            Enumeration<NetworkInterface> networkInterfaces = NetworkInterface.getNetworkInterfaces();///// GET ALL POSSIBLE NETWORKS
            NetworkInterface thisInterface;
////////////////////////////////////////////TASK 1
//            while (networkInterfaces.hasMoreElements())
//            {
//                thisInterface = networkInterfaces.nextElement();
//                System.out.println("");
//                System.out.print(thisInterface.getDisplayName() + ": ");
//
//                byte[] byteMAC = thisInterface.getHardwareAddress();
//                if (byteMAC!=null){
//                    List<String> array = new ArrayList<String>();
//                    for (byte i : byteMAC){
//                        array.add(String.format("%02x", (byte)i).toUpperCase());
//                    }
//                    System.out.print(array.get(0)+"-"+array.get(1)+"-"+array.get(2)+"-"+array.get(3)+"-"+array.get(4)+"-"+array.get(5));
//                }
//                else{
//                    System.out.print("(No MAC-address found)");
//                }
//
//
//            }
//
//            NetworkInterface networkInterface = NetworkInterface.getByInetAddress(localHost);
//            System.out.println("");
//            System.out.println("Interface name : address : broadcast");
//            for (InterfaceAddress address : networkInterface.getInterfaceAddresses()) {
//                System.out.println(networkInterface.getName() + " : " +  address.getAddress() + " : " + address.getBroadcast());
//            }
//	    System.out.println(String.format("%02x", (byte)-8));///Hint into hexadecimal



////////////////////////////////////////////TASK 2 and 3(partially)

            //SHOW NETWORKS NAMES TASK 3
            System.out.println("Available Networks:");
            while (networkInterfaces.hasMoreElements()) {
                thisInterface = networkInterfaces.nextElement();
                if(!thisInterface.isVirtual() ){
                    System.out.println(thisInterface.getName());
                    System.out.println(" "+thisInterface.getDisplayName());
                    System.out.println(" "+thisInterface.getMTU());
                    System.out.println(" "+thisInterface.getInetAddresses());
                    System.out.println(" "+thisInterface.getSubInterfaces());
                    //System.out.println(" "+thisInterface.);//TO DO
                }

            }
            ///TASK 2
            while(true) {
                //ENTER NETWORK NAME
                Scanner scanner = new Scanner(System.in);
                String MAC = null;
                String IPS = null;
                System.out.println("What network interface do you want to check?");
                String inputName = scanner.nextLine();

                //GET NETWORK INTERFACE
                NetworkInterface networkInt = NetworkInterface.getByName(inputName);
                if (networkInt  == null) {
                    System.out.println("Network int is not found");
                }

                //MAC
                byte[] byteMAC = networkInt.getHardwareAddress();
                if (byteMAC != null) {
                    List<String> array = new ArrayList<String>();
                    for (byte i : byteMAC) {
                        array.add(String.format("%02x", (byte) i).toUpperCase());
                    }
                    MAC = array.get(0) + "-" + array.get(1) + "-" + array.get(2) + "-" + array.get(3) + "-" + array.get(4) + "-" + array.get(5);
                }

                //CONNECTED IP'S
                Enumeration<InetAddress> networkAddresses = networkInt.getInetAddresses();
                List<String> listAddresses = new ArrayList<String>();
                while (networkAddresses.hasMoreElements()) {
                    InetAddress inetaddress = networkAddresses.nextElement();
                    listAddresses.add(inetaddress.getHostAddress());
                }

                //SHOW OUTPUT
                IPS = String.join(", ", listAddresses);
                System.out.println("Name: " + inputName + "\n" + "MAC: " + MAC + "\n" + "IPS: " + IPS);



            }



        }
        catch(SocketException | UnknownHostException e){


        }




    }
}