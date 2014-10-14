package br.uff.es2.war.model;

import br.uff.es2.war.model.CombatJudge;

public class MockJudge extends CombatJudge {

    private int[] attackValues;
    private int[] defenseValues;
    private boolean flag;

    public void setAttackValues(int[] values) {
	attackValues = values;
	flag = true;
    }

    public void setDefenseValues(int[] values) {
	defenseValues = values;
    }

    @Override
    protected int[] combatValues(int soldiers) {
	if(flag){
	    flag = false;
	    return attackValues;
	}
	flag = true;
	return defenseValues;
    }
}