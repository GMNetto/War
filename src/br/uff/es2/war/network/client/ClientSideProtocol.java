package br.uff.es2.war.network.client;

import java.util.List;
import java.util.Set;

import br.uff.es2.war.model.Card;
import br.uff.es2.war.model.Color;
import br.uff.es2.war.model.Combat;
import br.uff.es2.war.model.Territory;
import br.uff.es2.war.model.World;

/**
 * Format the messages sent by the client to the server
 * @author Arthur Pitzer
 */
public interface ClientSideProtocol {

    String chooseColor(Color color);

    String distributeSoldiers(Set<Territory> territories);

    String declareCombat(Combat combat);
    
    String finishAttack();

    String moveSoldiers(World world);

    String exchangeCards(List<Card> cards);

}
