package br.uff.es2.war.model;

import java.util.Arrays;

public class Combat {
    
    private final Territory attackingTerritory;
    private final Territory defendingTerritory;
    private final int attackingSoldiers;
    private int defendingSoldiers;
    
    public Combat(Territory attacker, Territory defender, int attackingSoldiers){
	this.attackingTerritory = attacker;
	this.defendingTerritory = defender;
	this.attackingSoldiers = attackingSoldiers;
    }
    
    public Territory getDefendingTerritory() {
	return defendingTerritory;
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
    
    public CombatResult resolve() {
	int[] attack = rollDiceTimes(attackingSoldiers);
	int[] defense = rollDiceTimes(defendingSoldiers);
	CombatJudge judge = new CombatJudge();
	if(judge.judge(attack, defense)){
	    System.out.println();
	}else{
	    System.out.println();
	}
	return null;
    }
    
    private int[] rollDiceTimes(int n){
	int[] results = new int[n];
	for(int i = 0; i < n; i++)
	    results[i] = rollDice();
	Arrays.sort(results);
	return results;
    }
    
    private int rollDice(){
	return ((int) (Math.random() * 5)) + 1;
    }

    
}
