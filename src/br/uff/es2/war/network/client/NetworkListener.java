package br.uff.es2.war.network.client;

import br.uff.es2.war.events.EventBus;
import br.uff.es2.war.network.Messenger;

public class NetworkListener implements Runnable {
    
    private final Messenger messenger;
    private final MessageToEventConverter converter;
    private final EventBus events;
    private boolean running;
    
    public NetworkListener(Messenger messenger, MessageToEventConverter converter, EventBus events) {
	this.messenger = messenger;
	this.converter = converter;
	this.events = events;
    }
    
    @Override
    public void run() {
	running = true;
	while(running){
	    String message = messenger.receive();
	    Object event = converter.toEvent(message);
	    events.publish(event);
	}
    }
    
    public synchronized void requestStop(){
	running = false;
    }
}
