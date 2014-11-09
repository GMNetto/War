package br.uff.es2.war.network;

public interface ProtocolMessages {
    
    String space();
    
    String chooseColor();
    
    String setGame();
    
    String beginTurn();
    
    String distributeSoldiers();
    
    String addCard();
    
    String exchangeCards();
    
    String declareCombat();
    
    String answerCombat();
    
    String moveSoldiers();
    
    String finishAttack();
    
}
