package br.uff.es2.war.network;

import java.io.IOException;
import java.util.LinkedList;
import java.util.Queue;

public class WarServer extends TCPServer {
    
    private final Queue<Messenger> clients;

    public WarServer(int port) throws IOException {
	super(port);
	clients = new LinkedList<Messenger>();
    }

    @Override
    protected void onClientReceived(Messenger client) {
	clients.add(client);
	new Thread(new WelcomeProtocolRunner(client)).start();;
    }

    @Override
    protected void onFailure(IOException e) {
	e.printStackTrace();
    }
}
