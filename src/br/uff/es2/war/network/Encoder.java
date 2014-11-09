package br.uff.es2.war.network;

import java.util.List;
import java.util.Set;

import br.uff.es2.war.model.Card;
import br.uff.es2.war.model.Color;
import br.uff.es2.war.model.Combat;
import br.uff.es2.war.model.Game;
import br.uff.es2.war.model.Player;
import br.uff.es2.war.model.Territory;

public interface Encoder {

    String encode(Color[] colors);

    String encode(Game game);

    String encode(Player current);

    String encode(Set<Territory> territories);

    String encode(Combat combat);

    String encode(Card card);

    String encode(Color color);

    String encode(List<Card> cards);

}
