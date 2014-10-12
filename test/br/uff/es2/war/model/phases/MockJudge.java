package br.uff.es2.war.model.phases;

import br.uff.es2.war.model.CombatJudge;

class MockJudge extends CombatJudge {

    private int[] attackValues;
    private int[] defenseValues;
    private boolean flag;

    void setAttackValues(int[] values) {
	attackValues = values;
	flag = true;
    }

    void setDefenseValues(int[] values) {
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