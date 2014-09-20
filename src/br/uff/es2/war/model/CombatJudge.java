package br.uff.es2.war.model;

public class CombatJudge {
    
    /**
     * Assumes that combat values are passed in crescent order.
     * @param attack attack values
     * @param defense defense values
     * @return whether attacker wins
     */
    public boolean judge(int[] attack, int defense[]){
	int attackIndex = attack.length - 1;
	int defenseIndex = defense.length - 1;
	int combatSize = Math.max(attackIndex, defenseIndex);
	int result = 0;
	for(int i = 0 ; i < combatSize; i++){
	    if(attack[attackIndex] > defense[defenseIndex])
		result++;
	    else
		result--;
	    attackIndex = attackIndex == 0 ? 0 : attackIndex -1;
	    defenseIndex = defenseIndex == 0 ? 0 : defenseIndex -1;
	}
	return result > 0;
    }
}
