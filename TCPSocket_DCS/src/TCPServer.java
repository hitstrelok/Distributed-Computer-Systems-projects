import java.io.*;
import java.net.*;
import java.util.ArrayList;
import java.util.Collections;

public class TCPServer {
	public static int PORT = 3000;
	public static void main(String[] args) throws IOException {
		if(args.length>0){
			try{
				PORT = Integer.parseInt(args[0]);
			}catch (NumberFormatException e){
				System.out.println("Invalid port number");
			}
		}
		ServerSocket s = new ServerSocket(PORT);
		System.out.println("IP number: " + s.getInetAddress() +" \nLocal port: "+ s.getLocalPort());
		System.out.println("Working: " + s);
		try {
			//server awaits connection
			Socket mysocket = s.accept();//Starts the server
			try {
				System.out.println("Connection accepted: "+ mysocket);
				BufferedReader in;
				PrintWriter out;
				while (true) {
					in = new BufferedReader(new InputStreamReader(mysocket.getInputStream()));//Hi:
					out = new PrintWriter(new BufferedWriter(new OutputStreamWriter(mysocket.getOutputStream())), true);//Number



					String str = in.readLine();
					System.out.print("Received: ");
					System.out.print(str + "\n");

					if (str.equals("DROP")) {/////DROP
						break;
					}
					else if (str.startsWith("STATS<") && str.endsWith(">")){////STATS
						String text = str.substring(6, str.length() - 1);
						int countUpper=0, countLower=0, digitNumber=0, remainingChar=0;
						for(int i = 0; i<text.length();i++){
							char letters =text.charAt(i);

							if(Character.isUpperCase(letters)){
								countUpper++;
							}
							else if(Character.isLowerCase(letters)){
								countLower++;
							}
							else if(Character.isDigit(letters)){
								digitNumber++;
							}
							else{
								remainingChar++;
							}
						}
						out.println("Uppercase: "+countUpper+" Lowercase: "+countLower+" Digits: "+digitNumber+" Remaining: "+remainingChar);
					}
					else if (str.startsWith("ANAGRAM<") && str.endsWith(">")){////ANAGRAM
						String text = str.substring(8, str.length() - 1);
						ArrayList<Character> letters = new ArrayList<>();
						for(char i :text.toCharArray()){
							letters.add(i);
						}

						Collections.shuffle(letters);
						StringBuilder anagram = new StringBuilder();
						for(char i: letters){
							anagram.append(i);
						}
						out.println(anagram);
					}
					else if (str.equals("CONNECT")){////CONNECT
						out.println("CONNECT");
					}
					else{
						//out.println("Wrong command!");
						out.println("Wrong command! Available commands: STATS<text>; ANAGRAM<text>; DROP; CONNECT");
					}









//					switch (str) {
//						case "STATS<"+text+">:
//							out.println("STATS");
//							break;
//						case "ANAGRAM":
//							out.println("ANAGRAM");
//							break;
//						case "CONNECT":
//							out.println("CONNECT");
//							break;
//						default:
//							out.println("Wrong command!");
//							//out.println("Wrong command!\nAvailable commands:\n- STATS <text>\n- ANAGRAM <text>\n- DROP");
//							break;
//					}
				}

			} finally {//Closing socket
				System.out.println("Connection to socket "+mysocket+" is lost");
				mysocket.close();
			}
		} finally {
			s.close();
		}
	}
}