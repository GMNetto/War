package br.uff.es2.war.network;

import java.util.Set;

import br.uff.es2.war.events.AnswerCombatEvent;
import br.uff.es2.war.events.BeginTurnEvent;
import br.uff.es2.war.events.ChooseColorEvent;
import br.uff.es2.war.events.DeclareCombatEvent;
import br.uff.es2.war.events.ExchangeCardsEvent;
import br.uff.es2.war.events.DistributeSoldiersEvent;
import br.uff.es2.war.events.MoveSoldierEvent;
import br.uff.es2.war.model.Color;
import br.uff.es2.war.model.Combat;
import br.uff.es2.war.model.Player;
import br.uff.es2.war.model.Territory;

public class JSONWarProtocolEventFactory implements MessengeToEventFactory {

    private final JSONDecoder decoder;

    public JSONWarProtocolEventFactory(JSONDecoder decoder) {
	this.decoder = decoder;
    }

    @Override
    public Object eventTo(String message) {
	int space = message.indexOf(JSONWarProtocol.SPACE);
	String prefix = message.substring(0, space);
	String sufixf = message.substring(space + 1, message.length());
	return mapToEvent(prefix, sufixf);
    }

    private Object mapToEvent(String prefix, String suffix) {
	switch (prefix) {
	/*TODO: SetGame carries the game object that should be used by JSONDecoder that is a dependency of this class.
	case JSONWarProtocol.SET_GAME:
	    return createSetGameEvent(suffix); */
	case JSONWarProtocol.BEGIN_TURN:
	    return createBeginTurnEvent(suffix);
	case JSONWarProtocol.CHOOSE_COLOR:
	    return createChooseColorEvent(suffix);
	case JSONWarProtocol.EXCHANGE_CARDS:
	    return createExchangeCardsEvent(suffix);
	case JSONWarProtocol.DISTRIBUTE_SOLDIERS:
	    return createDistributeSoldiersEvent(suffix);
	case JSONWarProtocol.DECLARE_COMBAT:
	    return createDeclareCombatEvent(suffix);
	case JSONWarProtocol.ANSWER_COMBAT:
	    return createAnswerCombatEvent(suffix);
	case JSONWarProtocol.MOVE_SOLDIERS:
	    return createMoveSoldiersEvent(suffix);
	default:
	    return null;
	}
    }

    private Object createBeginTurnEvent(String suffix) {
	Player player = decoder.decodePlayer(suffix);
	return new BeginTurnEvent(player);
    }
    
    private Object createChooseColorEvent(String suffix){
	Color[] colors = decoder.decodeColors(suffix);
	return new ChooseColorEvent(colors);
    }
    
    private Object createExchangeCardsEvent(String suffix){
	return new ExchangeCardsEvent();
    }
    
    private Object createDistributeSoldiersEvent(String suffix){
	int space = suffix.indexOf(JSONWarProtocol.SPACE);
	int quantity = Integer.parseInt(suffix.substring(0, space));
	suffix = suffix.substring(space + 1, suffix.length());
	Set<Territory> territories = decoder.decodeTerritories(suffix);
	return new DistributeSoldiersEvent(quantity, territories);
    }
    
    private Object createMoveSoldiersEvent(String suffix){
	return new MoveSoldierEvent();
    }
    
    private Object createDeclareCombatEvent(String suffix){
	return new DeclareCombatEvent();
    }
    
    private Object createAnswerCombatEvent(String suffix){
	Combat combat = decoder.decodeCombat(suffix);
	return new AnswerCombatEvent(combat);
    }
}
