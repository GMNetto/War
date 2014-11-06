package br.uff.es2.war.network.json;

import java.io.IOException;
import java.net.Socket;
import java.net.UnknownHostException;

import br.uff.es2.war.events.Action;
import br.uff.es2.war.events.AnswerCombatEvent;
import br.uff.es2.war.events.BeginTurnEvent;
import br.uff.es2.war.events.ChooseColorEvent;
import br.uff.es2.war.events.DeclareCombatEvent;
import br.uff.es2.war.events.DistributeSoldiersEvent;
import br.uff.es2.war.events.EventBus;
import br.uff.es2.war.events.ExchangeCardsEvent;
import br.uff.es2.war.events.LocalEventBus;
import br.uff.es2.war.events.MoveSoldierEvent;
import br.uff.es2.war.events.SetGameEvent;
import br.uff.es2.war.network.MessengeToEventFactory;
import br.uff.es2.war.network.Messenger;
import br.uff.es2.war.network.NetworkListener;
import br.uff.es2.war.network.TCPMessenger;
import br.uff.es2.war.network.json.JSONWarProtocolEventFactory;

public class ClientSideNetworkTest {
    
    private static final String SERVER_IP = "127.0.0.1";
    private static final int SERVER_PORT = 55555;
    
    
    public void TWO_PLAYERS_GAME() throws UnknownHostException, IOException{
	Messenger server = new TCPMessenger(new Socket(SERVER_IP, SERVER_PORT));
	final JSONDecoder decoder = new JSONDecoder();
	MessengeToEventFactory factory = new JSONWarProtocolEventFactory(decoder);
	EventBus events = new LocalEventBus();
	NetworkListener listener = new NetworkListener(server, factory, events);
	
	events.subscribe(SetGameEvent.class, new Action<SetGameEvent>() {
	    @Override
	    public void onAction(SetGameEvent event) {
		decoder.setGame(event.getGame());
		System.out.println("New Game " + event);
	    }
	});
	
	events.subscribe(BeginTurnEvent.class, new Action<BeginTurnEvent>() {
	    @Override
	    public void onAction(BeginTurnEvent args) {
		System.out.println("Begin Turn " + args);
	    }
	});
	
	events.subscribe(ChooseColorEvent.class, new Action<ChooseColorEvent>() {
	    @Override
	    public void onAction(ChooseColorEvent args) {
		System.out.println("Choose Color " + args);
	    }
	});
	
	events.subscribe(ExchangeCardsEvent.class, new Action<ExchangeCardsEvent>() {
	    @Override
	    public void onAction(ExchangeCardsEvent args) {
		System.out.println("Exchange Cards " + args);
	    }
	});
	
	events.subscribe(DistributeSoldiersEvent.class, new Action<DistributeSoldiersEvent>() {
	    @Override
	    public void onAction(DistributeSoldiersEvent args) {
		System.out.println("Distribute Soldiers " + args);
	    }
	});
	
	events.subscribe(DeclareCombatEvent.class, new Action<DeclareCombatEvent>() {
	    
	    @Override
	    public void onAction(DeclareCombatEvent args) {
		System.out.println("Declare Combat " + args);
	    }
	});
	
	events.subscribe(AnswerCombatEvent.class, new Action<AnswerCombatEvent>() {
	    @Override
	    public void onAction(AnswerCombatEvent args) {
		System.out.println("Answer Combat " + args);
	    }
	});
	
	events.subscribe(MoveSoldierEvent.class, new Action<MoveSoldierEvent>(){
	    @Override
	    public void onAction(MoveSoldierEvent args) {
		System.out.println("Move Soldier " + args);
	    }
	});
	
	new Thread(listener).start();
	
    }

}
