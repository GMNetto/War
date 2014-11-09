/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package br.uff.es2.war.model;

import java.util.List;
import java.util.Set;

import br.uff.es2.war.model.objective.Objective;

/**
 *
 * @author Victor Guimarães
 */
public class NoExchangeGame extends Game {

    public NoExchangeGame(Player[] players, World world, Color[] colors, List<Card> cards, Set<Objective> objectives) {
        super(players, world, colors, cards, objectives);
    }

    public NoExchangeGame(Player[] players, World world, Color[] colors, List<Card> cards) {
        super(players, world, colors, cards);
    }

    @Override
    public int getExchangeBonus() {
        return 0;
    }
    
    
}
