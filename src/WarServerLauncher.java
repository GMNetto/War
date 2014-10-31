import java.io.IOException;
import br.uff.es2.war.network.WarServer;

public class WarServerLauncher {

    public static void main(String[] args) throws IOException {
	if (args.length == 0)
	    args = new String[] { "55555" };
	int port = Integer.parseInt(args[0]);
	System.out.println("War Server");
	new Thread(new WarServer(port)).start();
    }
}
