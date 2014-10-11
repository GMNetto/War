package br.uff.es2.war.model;

/**
 * Declaration of a combat. During the combat phase the current
 * player choose a attacking territory, a defending territory and
 * how many soldiers will attack. The owner of the defending territory
 * answer the combat by choosing how many soldiers will defend.
 * 
 * The defending territory must be on the borders of the attacking
 * territory. The maximum number of soldiers attacking or defending 
 * is 3.
 *
 * @see CombatPhase
 * @see Territory
 * @author Arthur Pitzer
 */
public class Combat {
    
    private final Territory attackingTerritory;
    private final Territory defendingTerritory;
    private final int attackingSoldiers;
    private int defendingSoldiers;
    
    public Combat(Territory attacker, Territory defender, int attackingSoldiers){
	this.attackingTerritory = attacker;
	this.defendingTerritory = defender;
	this.attackingSoldiers = attackingSoldiers;
        this.defendingSoldiers = defender.getSoldiers();
    }
    
    public Territory getDefendingTerritory() {
	return defendingTerritory;
    }
    
    public Territory getAttackingTerritory() {
	return attackingTerritory;
    }
    
    public int getAttackingSoldiers(){
	return attackingSoldiers;
    }
    
    public int getDefendingSoldiers(){
	return defendingSoldiers;
    }
    
    public void setDefendingSoldiers(int soldiers){
	this.defendingSoldiers = soldiers;
    }
}    
