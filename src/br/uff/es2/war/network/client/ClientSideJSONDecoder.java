package br.uff.es2.war.network.client;

import java.util.List;
import java.util.Set;

import br.uff.es2.war.model.Card;
import br.uff.es2.war.model.Color;
import br.uff.es2.war.model.Combat;
import br.uff.es2.war.model.Game;
import br.uff.es2.war.model.Player;
import br.uff.es2.war.model.Territory;
import br.uff.es2.war.network.Decoder;

public class ClientSideJSONDecoder implements Decoder{
    
    private Decoder decoder;
    
    public ClientSideJSONDecoder() {
	decoder = new GameInitializerJSONDecoder();
    }

    @Override
    public Game decodeGame(String suffix) {
	Game game = decoder.decodeGame(suffix);
	decoder = new ReferenceMappingJSONDecoder(game);
	return game;
    }

    @Override
    public Player decodePlayer(String suffix) {
	return decoder.decodePlayer(suffix);
    }

    @Override
    public Color[] decodeColors(String suffix) {
	return decoder.decodeColors(suffix);
    }

    @Override
    public Set<Territory> decodeTerritories(String suffix) {
	return decoder.decodeTerritories(suffix);
    }

    @Override
    public Combat decodeCombat(String suffix) {
	return decoder.decodeCombat(suffix);
    }

    @Override
    public Color decodeColor(String code) {
	// TODO Auto-generated method stub
	return null;
    }

    @Override
    public List<Card> decodeCards(String code) {
	// TODO Auto-generated method stub
	return null;
    }

    @Override
    public Card decodeCard(String suffix) {
	// TODO Auto-generated method stub
	return null;
    }
}
