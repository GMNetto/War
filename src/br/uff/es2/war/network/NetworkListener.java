package br.uff.es2.war.network;

import br.uff.es2.war.events.EventBus;

public class NetworkListener implements Runnable {
    
    private final Messenger messenger;
    private final MessengeToEventFactory factory;
    private final EventBus events;
    private boolean running;
    
    public NetworkListener(Messenger messenger, MessengeToEventFactory factory, EventBus events) {
	this.messenger = messenger;
	this.factory = factory;
	this.events = events;
    }
    
    @Override
    public void run() {
	while(running){
	    String message = messenger.receive();
	    Object event = factory.eventTo(message);
	    events.publish(event);
	}
    }
    
    public synchronized void requestStop(){
	running = false;
    }
}
