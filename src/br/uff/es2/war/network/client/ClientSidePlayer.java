package br.uff.es2.war.network.client;

import java.util.List;
import java.util.Set;

import br.uff.es2.war.model.Card;
import br.uff.es2.war.model.Color;
import br.uff.es2.war.model.Combat;
import br.uff.es2.war.model.Territory;
import br.uff.es2.war.model.World;
import br.uff.es2.war.network.Messenger;

public class ClientSidePlayer{
    
    private final Messenger messenger;
    private final ClientSideProtocol protocol;
    
    public ClientSidePlayer(Messenger messenger, ClientSideProtocol protocol) {
	this.messenger = messenger;
	this.protocol = protocol;
    }

    public void chooseColor(Color color) {
	messenger.send(protocol.chooseColor(color));
    }

    public void distributeSoldiers(Set<Territory> territories) {
	messenger.send(protocol.distributeSoldiers(territories));
    }

    public void declareCombat(Combat combat) {
	messenger.send(protocol.declareCombat(combat));
    }
    
    public void finishAttack(){
	messenger.send(protocol.finishAttack());
    }

    public void moveSoldiers(World world) {
	messenger.send(protocol.moveSoldiers(world));
    }

    public void exchangeCards(List<Card> cards) {
	messenger.send(protocol.exchangeCards(cards));
    }
}
