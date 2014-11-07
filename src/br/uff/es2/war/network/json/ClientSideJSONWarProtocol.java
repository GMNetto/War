package br.uff.es2.war.network.json;

import java.util.List;
import java.util.Set;

import br.uff.es2.war.model.Card;
import br.uff.es2.war.model.Color;
import br.uff.es2.war.model.Combat;
import br.uff.es2.war.model.Territory;
import br.uff.es2.war.model.World;
import br.uff.es2.war.network.ClientSideWarProtocol;

public class ClientSideJSONWarProtocol implements ClientSideWarProtocol {
    
    private final JSONEncoder encoder;
    
    public ClientSideJSONWarProtocol() {
	encoder = new JSONEncoder();
    }
    
    @Override
    public String chooseColor(Color color) {
	return join(JSONProtocolMessages.CHOOSE_COLOR, encoder.encode(color));
    }

    @Override
    public String distributeSoldiers(Set<Territory> territories) {
	return join(JSONProtocolMessages.DISTRIBUTE_SOLDIERS, encoder.encodeTerritories(territories));
    }

    @Override
    public String declareCombat(Combat combat) {
	return join(JSONProtocolMessages.DECLARE_COMBAT, encoder.encode(combat));
    }
    
    @Override
    public String finishAttack() {
	return JSONProtocolMessages.FINISH_ATTACK;
    }

    @Override
    public String moveSoldiers(World world) {
	return join(JSONProtocolMessages.MOVE_SOLDIERS, encoder.encode(world));
    }

    @Override
    public String exchangeCards(List<Card> cards) {
	return join(JSONProtocolMessages.EXCHANGE_CARDS, encoder.encode(cards));
    }
    
    private String join(Object... objs) {
	StringBuilder builder = new StringBuilder();
	for (int i = 0; i < objs.length; i++) {
	    builder.append(objs[i]);
	    if (i + 1 < objs.length)
		builder.append(JSONProtocolMessages.SPACE);
	}
	return builder.toString();
    }
}
