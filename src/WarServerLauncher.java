import java.io.IOException;
import java.net.UnknownHostException;

import br.uff.es2.war.network.json.ClientSideNetworkTest;
import br.uff.es2.war.network.server.WarServer;

public class WarServerLauncher {

    public static void main(String[] args) throws IOException {
	if (args.length == 0)
	    args = new String[] { "55555" };
	int port = Integer.parseInt(args[0]);
	System.out.println("War Server");
	new Thread(new WarServer(port)).start();
	
	new Thread(new Runnable() {
	    
	    @Override
	    public void run() {
		try {
		    Thread.sleep(2000);
		    ClientSideNetworkTest t1 = new ClientSideNetworkTest();
		    t1.TWO_PLAYERS_GAME();
		    t1.TWO_PLAYERS_GAME();
		} catch (InterruptedException | IOException e) {
		    e.printStackTrace();
		}
	    }
	}).start();
    }
}
