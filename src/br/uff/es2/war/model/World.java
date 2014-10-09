package br.uff.es2.war.model;

import java.util.Collections;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.Set;

import br.uff.es2.war.util.CyclicIterator;

/**
 * World is a named graph of territories. The territories can be accessed
 * directly or by their continents.
 *
 * @author Arthur Pitzer
 * @author Victor Guimarães
 */
public class World extends HashSet<Continent> {

    private static final long serialVersionUID = -324627777377883273L;
    private final String name;

    public World(String name) {
        super();
        this.name = name;
    }

    public String getName() {
        return name;
    }

    public Set<Territory> getTerritories() {
        Set<Territory> territories = new HashSet<>();
        for (Continent continent : this) {
            territories.addAll(continent);
        }
        return territories;
    }

    public Set<Territory> getTerritoriesByOwner(Player current) {
        Set<Territory> owned = new HashSet<>();
        for (Territory territory : getTerritories()) {
            if (territory.getOwner().equals(current))
                owned.add(territory);
        }
        return owned;
    }

    public Set<Territory> getTerritoriesByColor(Color current) {
        Set<Territory> owned = new HashSet<>();
        for (Territory territory : getTerritories()) {
            if (territory.getOwner().getColor().equals(current))
                owned.add(territory);
        }
        return owned;
    }

    public Territory getTerritoryByName(String name) {
        for (Territory territory : getTerritories()) {
            if (territory.getName().equals(name))
                return territory;
        }
        return null;
    }

    public void distributeTerritories(Player[] players, Game game) {
        CyclicIterator<Player> iterator = new CyclicIterator<>(players);
        List<Territory> territories = new LinkedList<>(getTerritories());
        Collections.shuffle(territories);
        while (!territories.isEmpty()) {
            Territory territory = territories.remove(0);
            territory.setOwner(iterator.next());
            territory.addSoldiers(1);
        }
    }

    public Continent getContinentByName(String name) {
        for (Continent continent : this) {
            if (continent.getName().equals(name))
                return continent;
        }
        return null;
    }
}
