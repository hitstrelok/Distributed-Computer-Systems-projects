import java.net.*;
import java.io.*;
import java.util.Scanner;

//This file has localport 51020
public class TCPClient {//uses only socket object
	public static void main(String[] args) throws IOException {
		Scanner scanner = new Scanner(System.in);
		String addr = null;
		int PORT = 0;

		try(BufferedReader br = new BufferedReader(new FileReader("config.txt"))){
			addr = br.readLine();
			PORT = Integer.parseInt(br.readLine());
		}catch(IOException e){
			System.out.println("Error in config file");
			return;
		}

		System.out.println("addr = " + addr);
		System.out.println("PORT = " + PORT);

		Socket mysocket = new Socket(addr, PORT);//use ip and port
		try {
			System.out.println("Socket : " + mysocket);
			BufferedReader in;
			PrintWriter out = new PrintWriter(new BufferedWriter(new OutputStreamWriter(mysocket.getOutputStream())),true);

			System.out.println("Enter command:");
			for(int i = 0; i < 10; i ++) {
				in = new BufferedReader(new InputStreamReader(mysocket.getInputStream()));
				out = new PrintWriter(new BufferedWriter(new OutputStreamWriter(mysocket.getOutputStream())),true);
				out.println(scanner.nextLine());

				String str = in.readLine();
				if(str==null){
					break;
				}
				System.out.println("Replay: "+str);


			}
			out.println("END");
		} finally {
			System.out.println("Connection to server is lost");
			mysocket.close();
		}
	}
}