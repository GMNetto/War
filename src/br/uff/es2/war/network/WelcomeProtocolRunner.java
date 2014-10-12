package br.uff.es2.war.network;

import java.io.IOException;

public class WelcomeProtocolRunner implements Runnable {

    private final Messenger client;

    public WelcomeProtocolRunner(Messenger client) {
	this.client = client;
    }

    @Override
    public void run() {
	client.send("Hello Client");
	System.out.println(client.receive());
	try {
	    client.close();
	} catch (IOException e) {
	    e.printStackTrace();
	}
    }

}
