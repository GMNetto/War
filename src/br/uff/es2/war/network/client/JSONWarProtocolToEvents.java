package br.uff.es2.war.network.client;

import java.util.Set;

import br.uff.es2.war.events.AnswerCombatEvent;
import br.uff.es2.war.events.BeginTurnEvent;
import br.uff.es2.war.events.ChooseColorEvent;
import br.uff.es2.war.events.DeclareCombatEvent;
import br.uff.es2.war.events.ExchangeCardsEvent;
import br.uff.es2.war.events.DistributeSoldiersEvent;
import br.uff.es2.war.events.MoveSoldiersEvents;
import br.uff.es2.war.events.SetGameEvent;
import br.uff.es2.war.model.Color;
import br.uff.es2.war.model.Combat;
import br.uff.es2.war.model.Game;
import br.uff.es2.war.model.Player;
import br.uff.es2.war.model.Territory;
import br.uff.es2.war.network.Decoder;
import br.uff.es2.war.network.ProtocolMessages;

public class JSONWarProtocolToEvents implements MessageToEventConverter {

    private final Decoder decoder;

    public JSONWarProtocolToEvents(Decoder decoder) {
	this.decoder = decoder;
    }

    @Override
    public Object toEvent(String message) {
	int space = message.indexOf(ProtocolMessages.SPACE);
	if(space != -1){
	    String prefix = message.substring(0, space);
	    String sufixf = message.substring(space + 1, message.length());
	    return mapToEvent(prefix, sufixf);
	}
	return mapToEvent(message, "");
    }

    private Object mapToEvent(String prefix, String suffix) {
	switch (prefix) {
	case ProtocolMessages.CHOOSE_COLOR:
	    return createChooseColorEvent(suffix);
	case ProtocolMessages.SET_GAME:
	    return createSetGameEvent(suffix);
	case ProtocolMessages.BEGIN_TURN:
	    return createBeginTurnEvent(suffix);
	case ProtocolMessages.EXCHANGE_CARDS:
	    return createExchangeCardsEvent(prefix);
	case ProtocolMessages.DISTRIBUTE_SOLDIERS:
	    return createDistributeSoldiersEvent(suffix);
	case ProtocolMessages.DECLARE_COMBAT:
	    return createDeclareCombatEvent(suffix);
	case ProtocolMessages.ANSWER_COMBAT:
	    return createAnswerCombatEvent(suffix);
	case ProtocolMessages.MOVE_SOLDIERS:
	    return createMoveSoldiersEvent(suffix);
	default:
	    return null;
	}
    }
    
    private Object createChooseColorEvent(String suffix){
	Color[] colors = decoder.decodeColors(suffix);
	return new ChooseColorEvent(colors);
    }
    
    private Object createSetGameEvent(String suffix){
	Game game = decoder.decodeGame(suffix);
	return new SetGameEvent(game);
    }
    
    private Object createBeginTurnEvent(String suffix) {
	Player player = decoder.decodePlayer(suffix);
	return new BeginTurnEvent(player);
    }
    
    private Object createExchangeCardsEvent(String suffix){
	return new ExchangeCardsEvent();
    }
    
    private Object createDistributeSoldiersEvent(String suffix){
	int space = suffix.indexOf(ProtocolMessages.SPACE);
	int quantity = Integer.parseInt(suffix.substring(0, space));
	suffix = suffix.substring(space + 1, suffix.length());
	Set<Territory> territories = decoder.decodeTerritories(suffix);
	return new DistributeSoldiersEvent(quantity, territories);
    }
    
    private Object createMoveSoldiersEvent(String suffix){
	return new MoveSoldiersEvents();
    }
    
    private Object createDeclareCombatEvent(String suffix){
	return new DeclareCombatEvent();
    }
    
    private Object createAnswerCombatEvent(String suffix){
	Combat combat = decoder.decodeCombat(suffix);
	return new AnswerCombatEvent(combat);
    }
}
