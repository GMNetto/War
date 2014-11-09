/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.uff.es2.war.model.objective;

import java.util.HashSet;
import java.util.Set;

import br.uff.es2.war.model.Continent;
import br.uff.es2.war.model.Player;
import br.uff.es2.war.model.Territory;
import br.uff.es2.war.model.World;

/**
 * This class represents a partial objective of conquer a specific number of
 * {@link Continent}s not including a {@link Set} of other {@link Continent}s.
 * This class tells only if the {@link Player} has a specified number of
 * {@link Continent}s, not including the {@link Continent}s that he or she
 * already have to have.
 * 
 * @see ConquerContinent
 * @author Victor Guimarães
 */
public class ConquerExtraContinent extends ParcialObjetive {

    /**
     * The {@link Set} of {@link Continent} which should not count as extra
     * {@link Continent}s.
     */
    private Set<Continent> notIncludeds;

    /**
     * The number of extra {@link Continent}s to be conquered.
     */
    private final int extraContinents;

    /**
     * The {@link Set} of {@link ConquerContinent} that includes possibles
     * objetives to be achieved.
     */
    private Set<ConquerContinent> optionalContinents;

    /**
     * Constructor with all needed parameters.
     * 
     * @param world
     *            the specific {@link World} of the {@link Objective}
     * @param extraContinents
     *            the number of extra {@link Continent}s to be conquered
     * @param notIncludeds
     *            the {@link Set} of {@link Continent} which should not count as
     *            extra {@link Continent}s
     */
    public ConquerExtraContinent(World world, int extraContinents,
	    final Set<Continent> notIncludeds) {
	super(world);
	this.extraContinents = extraContinents;
	this.notIncludeds = notIncludeds;
	loadOptionalContinents();
    }

    /**
     * Constructor with all needed parameters.
     * 
     * @param world
     *            the specific {@link World} of the {@link Objective}
     * @param extraContinents
     *            the number of extra {@link Continent}s to be conquered
     * @param notIncludeds
     *            a set of {@link Continent} which should not count as extra
     *            {@link Continent}s
     */
    public ConquerExtraContinent(World world, int extraContinents,
	    Continent... notIncludeds) {
	super(world);
	this.extraContinents = extraContinents;

	this.notIncludeds = new HashSet<>();
	for (Continent continent : notIncludeds) {
	    this.notIncludeds.add(continent);
	}
	loadOptionalContinents();
    }

    /**
     * Loads the possible {@link ConquerContinent}s to complete the extra
     * {@link Continent}s to conquering.
     */
    private void loadOptionalContinents() {
	Set<ConquerContinent> optContinents = new HashSet<>(world.size()
		- notIncludeds.size());
	ConquerContinent conquerContinent;

	for (Continent continent : world) {
	    if (!notIncludeds.contains(continent)) {
		conquerContinent = new ConquerContinent(world, continent);
		conquerContinent.setOwner(owner);
		optContinents.add(conquerContinent);
	    }
	}

	this.optionalContinents = optContinents;
    }

    @Override
    public boolean isNeeded(Territory territory) {
	for (ConquerContinent conquerContinent : optionalContinents) {
	    if (conquerContinent.isNeeded(territory)) {
		return true;
	    }
	}

	return false;
    }

    @Override
    public boolean wasAchieved() {
	int count = 0;
	for (ConquerContinent conquerContinent : optionalContinents) {
	    if (conquerContinent.wasAchieved()) {
		count++;
	    }

	    if (count == extraContinents)
		return true;
	}

	return false;
    }

    @Override
    public boolean isPossible() {
        return true;
    }
    
    /**
     * Getter for the {@link Set} of {@link Continent} which should not count as
     * extra {@link Continent}s.
     * 
     * @return the {@link Set} of {@link Continent} which should not count as
     *         extra {@link Continent}s
     */
    public Set<Continent> getNotIncludeds() {
	return notIncludeds;
    }

    /**
     * Getter for the number of extra {@link Continent}s to be conquered.
     * 
     * @return the number of extra {@link Continent}s to be conquered
     */
    public int getExtraContinents() {
	return extraContinents;
    }

    @Override
    public void setOwner(Player owner) {
	super.setOwner(owner);
	for (ConquerContinent conquerContinent : optionalContinents) {
	    conquerContinent.setOwner(owner);
	}
    }

}
