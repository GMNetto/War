package br.uff.es2.war.network.server;

import java.util.List;
import java.util.Set;

import br.uff.es2.war.model.Card;
import br.uff.es2.war.model.Color;
import br.uff.es2.war.model.Combat;
import br.uff.es2.war.model.Game;
import br.uff.es2.war.model.Player;
import br.uff.es2.war.model.PlayerData;
import br.uff.es2.war.model.Territory;
import br.uff.es2.war.network.Messenger;

public class ServerSidePlayer extends PlayerData {

    private final Messenger messenger;
    private final ServerSideProtocol protocol;

    public ServerSidePlayer(Messenger messenger, ServerSideProtocol protocol) {
	super();
	this.messenger = messenger;
	this.protocol = protocol;
    }
    
    @Override
    public void setGame(Game game) {
	super.setGame(game);
	messenger.send(protocol.setGame(game));
    }

    @Override
    public Color chooseColor(Color[] colors) {
	messenger.send(protocol.chooseColor(colors));
	return protocol.chooseColor(messenger.receive());
    }

    @Override
    public void beginTurn(Player current) {
	messenger.send(protocol.beginTurn(current));
    }

    @Override
    public void distributeSoldiers(int soldierQuantity,
	    Set<Territory> territories) {
	messenger.send(protocol.distributeSoldiers(soldierQuantity, territories));
	protocol.distributeSoldiers(messenger.receive(), soldierQuantity, territories);
    }

    @Override
    public Combat declareCombat() {
	messenger.send(protocol.declareCombat());
	String received = messenger.receive();
	try{
	    return protocol.declareCombat(received);
	}catch(IndexOutOfBoundsException e){
	    return protocol.finishAttack();
	}
    }

    @Override
    public void answerCombat(Combat combat) {
	messenger.send(protocol.answerCombat(combat));
	protocol.answerCombat(messenger.receive(), combat);
    }

    @Override
    public void moveSoldiers() {
	messenger.send(protocol.moveSoldiers());
	protocol.moveSoldiers(messenger.receive(), game.getWorld()
		.getTerritoriesByOwner(this));
    }

    @Override
    public void addCard(Card card) {
	messenger.send(protocol.addCard(card));
    }

    @Override
    public List<Card> exchangeCards() {
	messenger.send(protocol.exchangeCards());
	return protocol.exchangeCards(messenger.receive());
    }
}
