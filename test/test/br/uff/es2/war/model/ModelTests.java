package test.br.uff.es2.war.model;

import static org.junit.Assert.*;

import org.junit.Test;

import br.uff.es2.war.model.Continent;
import br.uff.es2.war.model.Territory;

/**
 * Split this class as more tests are added.
 */
public class ModelTests {
    
    @Test
    public void ADDING_TERRITORY_TO_CONTINENT_CREATES_CROSS_REFERENCE() {
	Continent continentA = new Continent("A");
	Territory territoryA = new Territory("B");
	continentA.add(territoryA);
	assertSame(continentA, territoryA.getContinent());
	assertTrue(continentA.getTerritories().contains(territoryA));
    }

    @Test
    public void ADDING_TERRITORY_TO_MULTIPLE_CONTINENTS_REPLACES_LAST_REFERENCE() {
	Continent continentA = new Continent("A");
	Continent continentB = new Continent("B");
	Territory territoryA = new Territory("C");
	continentA.add(territoryA);
	continentB.add(territoryA);
	assertNotSame(continentA, territoryA.getContinent());
	assertSame(continentB, territoryA.getContinent());
	assertFalse(continentA.getTerritories().contains(territoryA));
	assertTrue(continentB.getTerritories().contains(territoryA));
    }
}
