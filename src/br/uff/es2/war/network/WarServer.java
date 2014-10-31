package br.uff.es2.war.network;

import br.uff.es2.war.controller.GameController;
import br.uff.es2.war.dao.exceptions.NonexistentEntityException;
import java.io.IOException;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.Queue;
import java.util.logging.Level;
import java.util.logging.Logger;

public class WarServer extends TCPServer {
    
    private final Queue<Messenger> clients;
    public static final int qtdPlayers=6;

    public WarServer(int port) throws IOException {
	super(port);
	clients = new LinkedList<>();
    }

    @Override
    protected void onClientReceived(Messenger client) {
	clients.add(client);
	if(clients.size() >= qtdPlayers){
            List<Messenger> lastMessengerAdded=new ArrayList(6);
	    Messenger[] array = new Messenger[clients.size()];
            for (int i = 0; i < (clients.size()>5?qtdPlayers:clients.size()); i++) {
                array[i]=clients.poll();
                lastMessengerAdded.add(array[i]);
            }
            try {
                new Thread(new GameController(array)).start();
            } catch (NonexistentEntityException ex) {
                clients.addAll(lastMessengerAdded);
            }
	}
    }

    @Override
    protected void onFailure(IOException e) {
	e.printStackTrace();
    }
}
