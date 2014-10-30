/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.uff.es2.war.ai.strategies.cardchange;

import br.uff.es2.war.ai.strategies.TerritoryValue;
import br.uff.es2.war.ai.strategies.TerritoryValueComparator;
import br.uff.es2.war.model.Card;
import br.uff.es2.war.model.Game;
import br.uff.es2.war.model.Player;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

/**
 *
 * @author Gustavo
 */
public class GreedyChangeCardStrategy implements ChangeCardStrategy {

    private static final double BONUS = 0.5;
    private final Player player;
    private final Game game;
    private final TerritoryValueComparator values;
    private final double threshold;

    public GreedyChangeCardStrategy(double threshold, Player player, Game game, TerritoryValue... values) {
        this.player = player;
        this.game = game;
        this.values = new TerritoryValueComparator(values);
        this.threshold = threshold;
    }

    private Collection<? extends Card> exchangeByNumber(List<Card> cards) {
        List<Card> selected = selectBestThreeCardsOfType(cards);
        if (selected.size() < 3) {
            selected = selectBestDifferentType(cards);
        }
        return selected;
    }

    private Collection<? extends Card> exchangeByImportance(List<Card> cards) {
        for (Card card : cards) {
            double value=values.getTerritoryValue(card.getTerritory());
            if (value > threshold) {
                if (hasThreeOfType(card.getFigure(), cards)) {
                    return selectBestThreeCardsOfType(cards);
                }
                if (selectBestDifferentType(cards).size() > 2) {
                    return selectBestDifferentType(cards);
                }
                return new ArrayList<Card>();
            }
        }
        return new ArrayList<>(0);
    }

    @Override
    public List<Card> changeCard(List<Card> cards) {
        List<Card> selectedCards = new ArrayList<>(0);
        if (cards.size() > 2) {

            Collections.sort(cards, new Comparator<Card>() {
                @Override
                public int compare(Card o1, Card o2) {
                    double valueO1 = values.getTerritoryValue(o1.getTerritory());
                    double valueO2 = values.getTerritoryValue(o2.getTerritory());
                    if (game.getWorld().getTerritoriesByOwner(player).contains(o1.getTerritory())) {
                        valueO1 += BONUS;
                    }
                    if (game.getWorld().getTerritoriesByOwner(player).contains(o2.getTerritory())) {
                        valueO2 += BONUS;
                    }
                    return Double.compare(valueO1, valueO2);
                }
            });
            if (cards.size() > 4) {
                selectedCards.addAll(exchangeByNumber(cards));
            } else {
                selectedCards.addAll(exchangeByImportance(cards));
            }
        }
        return selectedCards;
    }

    private List<Card> selectBestThreeCardsOfType(List<Card> cards) {
        int chosentype = 0;
        for (Card card : cards) {
            if (hasThreeOfType(card.getFigure(), cards)) {
                chosentype = card.getFigure();
                break;
            }
        }
        return selectThreeCardsOfType(chosentype, cards);
    }

    private List<Card> selectThreeCardsOfType(int type, List<Card> cards) {
        List<Card> selectedCards = new ArrayList(3);
        for (Card card : cards) {
            if (card.getFigure() == type) {
                selectedCards.add(card);
            }
        }
        return selectedCards;
    }

    private boolean hasThreeOfType(int type, List<Card> cards) {
        int counter = 0;
        for (Card card : cards) {
            if (card.getFigure() == type) {
                counter++;
            }
        }
        return (counter) > 2;
    }

    private List<Card> selectBestDifferentType(List<Card> cards) {
        List<Card> alreadyIn = new ArrayList<>(3);
        for (int i = 0; i < cards.size(); i++) {
            boolean hasEqual = false;
            if (!alreadyInHasFigure(cards.get(i).getFigure(), alreadyIn)) {
                alreadyIn.add(cards.get(i));
            }
        }
        if (alreadyIn.size() > 2) {
            return alreadyIn.subList(0, 3);
        }
        alreadyIn.clear();
        return alreadyIn;
    }

    private boolean alreadyInHasFigure(int figure, List<Card> alreadyIn) {
        for (Card card : alreadyIn) {
            if (card.getFigure() == figure) {
                return true;
            }
        }
        return false;
    }

}
