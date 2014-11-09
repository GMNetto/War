package br.uff.es2.war.network.json;

import java.io.IOException;
import java.net.Socket;
import java.net.UnknownHostException;
import java.util.Collection;
import java.util.Collections;
import java.util.Iterator;
import java.util.Set;

import org.junit.Test;

import br.uff.es2.war.events.Action;
import br.uff.es2.war.events.AnswerCombatEvent;
import br.uff.es2.war.events.BeginTurnEvent;
import br.uff.es2.war.events.ChooseColorEvent;
import br.uff.es2.war.events.DeclareCombatEvent;
import br.uff.es2.war.events.DistributeSoldiersEvent;
import br.uff.es2.war.events.EventBus;
import br.uff.es2.war.events.ExchangeCardsEvent;
import br.uff.es2.war.events.LocalEventBus;
import br.uff.es2.war.events.MoveSoldiersEvents;
import br.uff.es2.war.events.SetGameEvent;
import br.uff.es2.war.model.Color;
import br.uff.es2.war.network.Decoder;
import br.uff.es2.war.network.Messenger;
import br.uff.es2.war.network.TCPMessenger;
import br.uff.es2.war.network.client.ClientSideJSONDecoder;
import br.uff.es2.war.network.client.ClientSideJSONProtocol;
import br.uff.es2.war.network.client.ClientSidePlayer;
import br.uff.es2.war.network.client.JSONWarProtocolToEvents;
import br.uff.es2.war.network.client.MessageToEventConverter;
import br.uff.es2.war.network.client.NetworkListener;

public class ClientSideNetworkTest {
    
    private static final String SERVER_IP = "127.0.0.1";
    private static final int SERVER_PORT = 55555;
    
    @Test
    public void TWO_PLAYERS_GAME() throws UnknownHostException, IOException{
	Messenger server = new TCPMessenger(new Socket(SERVER_IP, SERVER_PORT));
	final Decoder decoder = new ClientSideJSONDecoder();
	MessageToEventConverter factory = new JSONWarProtocolToEvents(decoder);
	EventBus events = new LocalEventBus();
	NetworkListener listener = new NetworkListener(server, factory, events);
	
	final ClientSidePlayer player = new ClientSidePlayer(server, new ClientSideJSONProtocol());
	
	events.subscribe(ChooseColorEvent.class, new Action<ChooseColorEvent>() {
	    @Override
	    public void onAction(ChooseColorEvent args) {
		System.out.println("Choose Color " + args);
		Color[] colors = args.getColors();
		player.chooseColor(colors[0]);
	    }
	});
	
	events.subscribe(SetGameEvent.class, new Action<SetGameEvent>() {
	    @Override
	    public void onAction(SetGameEvent event) {
		System.out.println("New Game " + event);
	    }
	});
	
	events.subscribe(BeginTurnEvent.class, new Action<BeginTurnEvent>() {
	    @Override
	    public void onAction(BeginTurnEvent args) {
		System.out.println("Begin Turn " + args);
	    }
	});
	
	
	
	events.subscribe(ExchangeCardsEvent.class, new Action<ExchangeCardsEvent>() {
	    @Override
	    public void onAction(ExchangeCardsEvent args) {
		System.out.println("Exchange Cards " + args);
		player.exchangeCards(Collections.EMPTY_LIST);
	    }
	});
	
	events.subscribe(DistributeSoldiersEvent.class, new Action<DistributeSoldiersEvent>() {
	    @Override
	    public void onAction(DistributeSoldiersEvent args) {
		System.out.println("Distribute Soldiers " + args);
		int qtd = args.getQuantity();
		any(args.getTerritories()).addSoldiers(qtd);
		player.distributeSoldiers(args.getTerritories());
	    }
	});
	
	events.subscribe(DeclareCombatEvent.class, new Action<DeclareCombatEvent>() {
	    
	    @Override
	    public void onAction(DeclareCombatEvent args) {
		System.out.println("Declare Combat " + args);
		player.finishAttack();
	    }
	});
	
	events.subscribe(AnswerCombatEvent.class, new Action<AnswerCombatEvent>() {
	    @Override
	    public void onAction(AnswerCombatEvent args) {
		System.out.println("Answer Combat " + args);
	    }
	});
	
	events.subscribe(MoveSoldiersEvents.class, new Action<MoveSoldiersEvents>(){
	    @Override
	    public void onAction(MoveSoldiersEvents args) {
		System.out.println("Move Soldiers " + args);
	    }
	});
	
	new Thread(listener).start();
    }
    
    private <T> T any(Set<T> collection){
	for(T item : collection)
	    return item;
	return null;
    }
}
