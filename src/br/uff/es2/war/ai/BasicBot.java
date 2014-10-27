/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.uff.es2.war.ai;

import br.uff.es2.war.entity.Jogador;
import br.uff.es2.war.ai.strategies.attack.AttackStrategy;
import br.uff.es2.war.ai.strategies.attack.allocation.AllocationStrategy;
import br.uff.es2.war.ai.strategies.rearrange.RearrangeStrategy;
import br.uff.es2.war.model.Card;
import br.uff.es2.war.model.Color;
import br.uff.es2.war.model.Combat;
import br.uff.es2.war.model.Game;
import br.uff.es2.war.model.Player;
import br.uff.es2.war.model.Territory;
import br.uff.es2.war.model.objective.Objective;
import java.util.Collection;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

/**
 * this classe represents a basic BOT. This class takes actions based on
 * strategies defined by three types of interaction: Soldiers allocation, Attack
 * and soldiers relocation.
 *
 * @see AllocationStrategy
 * @see AttackStrategy
 * @see RearrangeStrategy
 * @author Victor Guimarães
 */
public class BasicBot implements Player {

    private Game game;
    private Jogador jogador;
    private Objective objective;
    private Color color;

    private AttackStrategy attackStrategy;
    private AllocationStrategy allocationInstruction;
    private RearrangeStrategy relocationStrategy;
    private Collection<Card> cards;

    public BasicBot(Jogador jogador) {
        this.jogador = jogador;
        this.cards = new HashSet<>();
    }

    public BasicBot(Jogador jogador, Game game) {
        this(jogador);
        this.game = game;
    }

    @Override
    public Objective getObjective() {
        return this.objective;
    }

    @Override
    public void setObjective(Objective objective) {
        this.objective = objective;
    }

    @Override
    public Color chooseColor(Color[] colors) {
        return colors[0];
    }

    @Override
    public Color getColor() {
        return this.color;
    }

    @Override
    public void setColor(Color color) {
        this.color = color;
    }

    @Override
    public void beginTurn(Player current) {
        //Gives the BOT a chance to do something when a turn begins.
    }

    @Override
    public void distributeSoldiers(int soldierQuantity, Set<Territory> territories) {
        allocationInstruction.distributeSoldiers(soldierQuantity, territories);
    }

    @Override
    public Combat declareCombat() {
        return attackStrategy.declareCombat();
    }

    @Override
    public void answerCombat(Combat combat) {
        //Gives the BOT a chance to do something when a combat ends.
    }

    @Override
    public void moveSoldiers() {
        relocationStrategy.moveSoldiers();
    }

    @Override
    public void addCard(Card drawCard) {
        cards.add(drawCard);
    }

    @Override
    public Collection<Card> getCards() {
        return cards;
    }

    @Override
    public Card discard() {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public List<Card> exchangeCards() {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public void setGame(Game game) {
        this.game = game;
    }

    public AttackStrategy getAttackStrategy() {
        return attackStrategy;
    }

    public void setAttackStrategy(AttackStrategy attackStrategy) {
        this.attackStrategy = attackStrategy;
    }

    public AllocationStrategy getAllocationInstruction() {
        return allocationInstruction;
    }

    public void setAllocationInstruction(AllocationStrategy allocationInstruction) {
        this.allocationInstruction = allocationInstruction;
    }

    public RearrangeStrategy getRelocationStrategy() {
        return relocationStrategy;
    }

    public void setRelocationStrategy(RearrangeStrategy relocationStrategy) {
        this.relocationStrategy = relocationStrategy;
    }

}
