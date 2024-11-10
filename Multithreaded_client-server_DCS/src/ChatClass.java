
import java.util.List;
import java.util.concurrent.CopyOnWriteArrayList;

public class ChatClass {

    private static final List<ServerThread> clientThreads = new CopyOnWriteArrayList<>();

    public static  void addClient(ServerThread clientThread){
        clientThreads.add(clientThread);
    }

    public static  void removeClient(ServerThread clientThread){
        clientThreads.remove(clientThread);
    }

    public static void broadcast(String line, ServerThread itself){
        for(ServerThread clientThread : clientThreads){
            if(clientThread!=itself){
                clientThread.sendMessage(line);
            }
        }
    }


}
