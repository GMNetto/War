package br.uff.es2.war.network;

import java.util.List;
import java.util.Set;

import br.uff.es2.war.model.Card;
import br.uff.es2.war.model.Color;
import br.uff.es2.war.model.Combat;
import br.uff.es2.war.model.Player;
import br.uff.es2.war.model.PlayerData;
import br.uff.es2.war.model.Territory;

public class RemotePlayer extends PlayerData {

    private final Messenger messenger;
    private final WarProtocol protocol;

    public RemotePlayer(Messenger messenger, WarProtocol protocol) {
	super();
	this.messenger = messenger;
	this.protocol = protocol;
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
	messenger.send(protocol
		.distributeSoldiers(soldierQuantity, territories));
	protocol.distributeSoldiers(messenger.receive(), soldierQuantity,
		territories);
    }

    @Override
    public Combat declareCombat() {
	messenger.send(protocol.declareCombat());
	return protocol.declareCombat(messenger.receive());
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
    public Card discard() {
	messenger.send(protocol.discard());
	return protocol.discard(messenger.receive());
    }

    @Override
    public List<Card> exchangeCards() {
	messenger.send(protocol.exchangeCards());
	return protocol.exchangeCards(messenger.receive());
    }
}
