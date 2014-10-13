package br.uff.es2.war.network;

import java.io.IOException;
import java.util.LinkedList;
import java.util.Queue;

import br.uff.es2.war.controller.GameController;

public class WarServer extends TCPServer {
    
    private final Queue<Messenger> clients;

    public WarServer(int port) throws IOException {
	super(port);
	clients = new LinkedList<Messenger>();
    }

    @Override
    protected void onClientReceived(Messenger client) {
	clients.add(client);
	if(clients.size() > 1){
	    Messenger[] array = new Messenger[clients.size()];
	    array = clients.toArray(array);
	    new Thread(new GameController(array)).start();
	}
    }

    @Override
    protected void onFailure(IOException e) {
	e.printStackTrace();
    }
}
