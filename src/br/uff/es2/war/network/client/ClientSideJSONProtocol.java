package br.uff.es2.war.network.client;

import java.util.List;
import java.util.Set;

import br.uff.es2.war.model.Card;
import br.uff.es2.war.model.Color;
import br.uff.es2.war.model.Combat;
import br.uff.es2.war.model.Territory;
import br.uff.es2.war.model.World;
import br.uff.es2.war.network.ProtocolMessages;
import br.uff.es2.war.network.json.JSONEncoder;

public class ClientSideJSONProtocol implements ClientSideProtocol {
    
    private final JSONEncoder encoder;
    
    public ClientSideJSONProtocol() {
	encoder = new JSONEncoder();
    }
    
    @Override
    public String chooseColor(Color color) {
	return join(ProtocolMessages.CHOOSE_COLOR, encoder.encode(color));
    }

    @Override
    public String distributeSoldiers(Set<Territory> territories) {
	return join(ProtocolMessages.DISTRIBUTE_SOLDIERS, encoder.encodeTerritories(territories));
    }

    @Override
    public String declareCombat(Combat combat) {
	return join(ProtocolMessages.DECLARE_COMBAT, encoder.encode(combat));
    }
    
    @Override
    public String finishAttack() {
	return ProtocolMessages.FINISH_ATTACK;
    }

    @Override
    public String moveSoldiers(World world) {
	return join(ProtocolMessages.MOVE_SOLDIERS, encoder.encode(world));
    }

    @Override
    public String exchangeCards(List<Card> cards) {
	return join(ProtocolMessages.EXCHANGE_CARDS, encoder.encode(cards));
    }
    
    private String join(Object... objs) {
	StringBuilder builder = new StringBuilder();
	for (int i = 0; i < objs.length; i++) {
	    builder.append(objs[i]);
	    if (i + 1 < objs.length)
		builder.append(ProtocolMessages.SPACE);
	}
	return builder.toString();
    }
}
