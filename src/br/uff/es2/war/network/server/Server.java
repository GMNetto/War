package br.uff.es2.war.network.server;

import java.io.IOException;

import br.uff.es2.war.network.Messenger;

public abstract class Server implements Runnable {

    protected boolean running;

    @Override
    public void run() {
	running = true;
	while (running) {
	    try {
		Messenger client = acceptClient();
		onClientReceived(client);
	    } catch (IOException e) {
		onFailure(e);
	    }
	}
    }

    protected abstract Messenger acceptClient() throws IOException;

    protected abstract void onClientReceived(Messenger client);

    protected abstract void onFailure(IOException e);

    public synchronized void requestStop() {
	running = false;
    }
}
