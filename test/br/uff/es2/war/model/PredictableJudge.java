package br.uff.es2.war.model;

class PredictableJudge extends CombatJudge {

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

    protected int[] combatValues(int soldiers) {
	if(flag){
	    flag = false;
	    return attackValues;
	}
	flag = true;
	return defenseValues;
    }
}