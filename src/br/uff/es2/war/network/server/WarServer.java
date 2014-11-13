package br.uff.es2.war.network.server;

import java.io.IOException;
import java.util.Arrays;
import java.util.LinkedList;
import java.util.Queue;
import java.util.Timer;
import java.util.TimerTask;

import br.uff.es2.war.controller.GameController;
import br.uff.es2.war.dao.exceptions.NonexistentEntityException;
import br.uff.es2.war.network.Messenger;

public class WarServer extends TCPServer {

    private static final long MAX_WAIT_TIME = 1000;
    public static final int PLAYER_PER_GAME = 2;
    private final Queue<Messenger> clients;
    private Timer timer;
    
    public WarServer(int port) throws IOException {
        super(port);
        clients = new LinkedList<>();
        timer = new Timer();
    }

    @Override
    protected void onClientReceived(Messenger client) {
        clients.add(client);
        if (clients.size() >= PLAYER_PER_GAME)
            startNextGame();
        else if(clients.size() == 1)
            scheduleNextGame();
    }
    
    private void scheduleNextGame(){
	timer.schedule(new TimerTask() {
	    @Override
	    public void run() {
		Messenger[] nextPlayers = poolNextClients(clients.size());
		startGame(nextPlayers);
	    }
	}, MAX_WAIT_TIME);
    }
    
    private synchronized void startNextGame(){
	Messenger[] nextClients = poolNextClients(PLAYER_PER_GAME);
        startGame(nextClients);
    }
    
    private Messenger[] poolNextClients(int n) {
        Messenger[] nextClients = new Messenger[n];
        for (int i = 0; i < n; i++) {
            nextClients[i] = clients.poll();
        }
        return nextClients;
    }
    
    private void startGame(Messenger[] nextClients) {
	timer.cancel();
	timer = new Timer();
        try {
            GameController controller = new GameController(nextClients);
            new Thread(controller).start();
        } catch (NonexistentEntityException e) {
            clients.addAll(Arrays.asList(nextClients));
        }
    }

    @Override
    protected void onFailure(IOException e) {
        e.printStackTrace();
    }
}
