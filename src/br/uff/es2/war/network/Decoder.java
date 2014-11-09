package br.uff.es2.war.network;

import java.util.List;
import java.util.Set;

import br.uff.es2.war.model.Card;
import br.uff.es2.war.model.Color;
import br.uff.es2.war.model.Combat;
import br.uff.es2.war.model.Game;
import br.uff.es2.war.model.Player;
import br.uff.es2.war.model.Territory;

public interface Decoder {

    Game decodeGame(String code);

    Player decodePlayer(String code);

    Color[] decodeColors(String code);
    
    Color decodeColor(String code);

    Set<Territory> decodeTerritories(String code);

    Combat decodeCombat(String code);

    List<Card> decodeCards(String code);

    Card decodeCard(String suffix);
    
}
