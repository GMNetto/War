package br.uff.es2.war.model;

import java.util.Collections;
import java.util.Iterator;
import java.util.List;

import br.uff.es2.war.events.EventBus;
import br.uff.es2.war.events.LocalEventBus;
import br.uff.es2.war.entity.Partida;
import br.uff.es2.war.entity.Territorio;
import br.uff.es2.war.util.CyclicIterator;
import java.util.Date;
import java.util.Iterator;
import java.util.LinkedList;

/**
 * Holds the state of a War game. The game is updated by the players during the
 * execution of the game phases.
 *
 * @author Arthur Pitzer
 * @author Victor Guimarães
 */
public class Game {

    private final EventBus events;
    private final Player[] players;
    private final Color[] colors;
    private final World world;
    private final CyclicIterator<Player> turns;
    private final List<Card> cards;
    private Player currentPlayer;
    private Player winner;
    private int exchange;
    private Date startDate;
    private Date endDate;

    public Game(Player[] players, World world, Color[] colors, List<Card> cards) {
        events = new LocalEventBus();
        exchange = 0;
        Collections.shuffle(cards);
        this.players = players;
        this.world = world;
        this.colors = colors;
        this.cards = cards;
        turns = new CyclicIterator<Player>(players);
        startDate = new Date();
    }

    public Player[] getPlayers() {
        return players;
    }

    public Color[] getColors() {
        return colors;
    }

    public World getWorld() {
        return world;
    }

    public Card drawCard() {
        return cards.remove(0);
    }

    public void passTurn() {
        if (turns.hasNext())
            currentPlayer = turns.next();
    }

    public Player getCurrentPlayer() {
        return currentPlayer;
    }

    public EventBus getEvents() {
        return events;
    }

    public boolean isOver() {
        if (winner != null)
            return true;
        for (Player player : players) {
            if (player.getObjective().wasAchieved()) {
                winner = player;
                return true;
            }
        }
        return false;
    }

    public Date getStartDate() {
        return startDate;
    }

    public Date getEndDate() {
        return endDate;
    }

    public Iterator<Player> getTurns() {
        return turns;
    }

    public Player getWinner() {
        return isOver() ? winner : null;
    }

    public void addCard(Card card) {
        cards.add(card);
    }

    public void incrementExchangeCounter() {
        exchange++;
    }

    public int getExchangeBonus() {
        if (exchange < 5) {
            return (4 + 2 * exchange);
        } else {
            return (5 * (exchange - 2));
        }
    }

    public int getNumberOfTurns() {
        return turns.getCycles();
    }

    /**
     * Distribute the {@link Territory}is randomly for the {@link Player}s.
     */
    public void distributeTerritories() {
        CyclicIterator<Player> iterator = new CyclicIterator<>(players);
        List<Territory> territories = new LinkedList<>(world.getTerritories());
        Collections.shuffle(territories);
        while (!territories.isEmpty()) {
            Territory territory = territories.remove(0);
            territory.setOwner(iterator.next());
            territory.addSoldiers(1);
        }
    }
}