package br.uff.es2.war.network.client;

import java.util.List;
import java.util.Set;

import br.uff.es2.war.events.EventBus;
import br.uff.es2.war.events.LocalEventBus;
import br.uff.es2.war.model.Card;
import br.uff.es2.war.model.Color;
import br.uff.es2.war.model.Combat;
import br.uff.es2.war.model.PlayerData;
import br.uff.es2.war.model.Territory;
import br.uff.es2.war.network.Messenger;

public class ClientSidePlayer extends PlayerData {
    
    private Messenger messenger;
    private ClientSideProtocol protocol;
    private EventBus events;
    private NetworkListener listener;
    
    public ClientSidePlayer(Messenger messenger, ClientSideProtocol protocol) {
	this.messenger = messenger;
	this.protocol = protocol;
	events = new LocalEventBus();
	listener = new NetworkListener();
	new Thread(listener).start();
    }
    
    public EventBus getEvents() {
	return events;
    }
    
    public void chooseColor(Color color){
	setColor(color);
	messenger.send(protocol.chooseColor(color));
    }
    
    public void distributeSoldiers(Set<Territory> territories){
	messenger.send(protocol.distributeSoldiers(territories));
    }
    
    public void declareCombat(Combat combat){
	messenger.send(protocol.declareCombat(combat));
    }
    
    public void answerCombat(Combat combat){
	messenger.send(protocol.answerCombat(combat));
    }
    
    public void finishAttack(){
	messenger.send(protocol.finishAttack());
    }
    
    public void exchangeCards(List<Card> cards){
	messenger.send(protocol.exchangeCards(cards));
    }
    
    private class NetworkListener implements Runnable{
	
	private boolean running;
	
	@Override
	public void run() {
	    running = true;
	    while(running){
		String message = messenger.receive();
		System.out.println(message);
		Object event = protocol.parseMessage(message);
		events.publish(event);
	    }
	}
	
	public synchronized void requestStop(){
	    running = false;
	}
    }
}
