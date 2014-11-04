package br.uff.es2.war.model;

import br.uff.es2.war.entity.Partida;
import br.uff.es2.war.entity.Territorio;
import br.uff.es2.war.events.EventBus;
import br.uff.es2.war.events.LocalEventBus;
import br.uff.es2.war.model.objective.Objective;
import br.uff.es2.war.util.CyclicIterator;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;
import java.util.Set;

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
    private Set<Objective> objectives;

    public Game(Player[] players, World world, Color[] colors, List<Card> cards, Set<Objective> objectives) {
	events = new LocalEventBus();
        exchange = -1;
        Collections.shuffle(cards);
        this.players = players;
        shufflePlayers();
        this.world = world;
        this.colors = colors;
        this.cards = cards;
        turns = new CyclicIterator<Player>(players);
        startDate = new Date();
        this.objectives = objectives;
    }

    private void shufflePlayers() {
        List<Player> p = new ArrayList<>(players.length);
        for (Player player : players) {
            p.add(player);
        }
        Collections.shuffle(p);
        p.toArray(players);
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
                if (player.equals(currentPlayer)) {
                    winner = player;
                    return true;
                } else {
                    player.getObjective().switchToAlternativeObjective();
                }
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

    public Set<Objective> getObjectives() {
        return objectives;
    }

    public Player playerByColor(Color color) {
        for (Player player : players) {
            if (player.getColor().equals(color))
                return player;
        }
        return null;
    }

}
