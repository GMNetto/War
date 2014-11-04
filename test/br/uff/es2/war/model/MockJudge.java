package br.uff.es2.war.model;

import br.uff.es2.war.model.CombatJudge;

public class MockJudge extends CombatJudge {

    private Integer[] attackValues;
    private Integer[] defenseValues;
    private boolean flag;

    public void setAttackValues(Integer[] values) {
	attackValues = values;
	flag = true;
    }

    public void setDefenseValues(Integer[] values) {
	defenseValues = values;
    }

    protected Integer[] combatValues(int soldiers) {
	if (flag) {
	    flag = false;
	    return attackValues;
	}
	flag = true;
	return defenseValues;
    }
}
