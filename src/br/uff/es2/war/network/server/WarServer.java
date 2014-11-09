package br.uff.es2.war.network.server;

import br.uff.es2.war.controller.GameController;
import br.uff.es2.war.dao.exceptions.NonexistentEntityException;
import br.uff.es2.war.network.Messenger;

import java.io.IOException;
import java.util.Arrays;
import java.util.LinkedList;
import java.util.Queue;

public class WarServer extends TCPServer {

    private final Queue<Messenger> clients;
    public static final int PLAYER_PER_GAME = 2;

    public WarServer(int port) throws IOException {
	super(port);
	clients = new LinkedList<>();
    }

    @Override
    protected void onClientReceived(Messenger client) {
	clients.add(client);
	if (clients.size() >= PLAYER_PER_GAME) {
	    Messenger[] nextClients = poolNextClients(PLAYER_PER_GAME);
	    startGame(nextClients);
	}
    }
    
    private Messenger[] poolNextClients(int n){
	Messenger[] nextClients = new Messenger[n];
	for (int i = 0; i < n; i++)
	    nextClients[i] = clients.poll();
	return nextClients;
    }
    
    private void startGame(Messenger[] nextClients){
	try{
	    GameController controller = new GameController(nextClients);
	    new Thread(controller).start();
	}catch(NonexistentEntityException e){
	    clients.addAll(Arrays.asList(nextClients));
	}
    }

    @Override
    protected void onFailure(IOException e) {
	e.printStackTrace();
    }
}
