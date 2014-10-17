/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package br.uff.es2.war.model.objective;

import br.uff.es2.war.entity.Jogador;
import br.uff.es2.war.model.Color;
import br.uff.es2.war.model.Combat;
import br.uff.es2.war.model.Game;
import br.uff.es2.war.model.Player;
import br.uff.es2.war.model.Territory;
import java.util.Set;

/**
 *
 * @author Victor
 */
public class DumbPlayer implements Player {

    private Objective objective;
    private Color color;
    private Jogador jogador;
    private Game game;

    public DumbPlayer(Color color, int codJogador) {
        this.color = color;
        this.jogador = new Jogador(codJogador);
    }
    
    @Override
    public void setGame(Game game) {
        this.game = game;
    }

    @Override
    public Objective getObjective() {
        return objective;
    }

    @Override
    public void setObjective(Objective objective) {
        this.objective = objective;
        ((FullObjective) this.objective).setOwner(this);
    }

    @Override
    public Color chooseColor(Color[] colors) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public Color getColor() {
        return color;
    }

    @Override
    public void setColor(Color color) {
        this.color = color;
    }

    @Override
    public void beginTurn(Player current) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public void distributeSoldiers(int soldierQuantity, Set<Territory> territories) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public Combat declareCombat() {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public void answerCombat(Combat combat) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public void moveSoldiers() {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public Game getGame() {
        return game;
    }

    @Override
    public Jogador getJogador() {
        return jogador;
    }

    @Override
    public String toString() {
        return "DumbPlayer:\t" + jogador.getCodJogador() + "\tColor:\t" + color.toString();
    }
    
}
