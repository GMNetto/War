package br.uff.es2.war.events;

import br.uff.es2.war.model.Combat;

public class AnswerCombatEvent {
    
    private final Combat combat;

    public AnswerCombatEvent(Combat combat) {
	this.combat = combat;
    }
    
    public Combat getCombat() {
	return combat;
    }
}
