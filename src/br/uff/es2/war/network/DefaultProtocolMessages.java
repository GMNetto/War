package br.uff.es2.war.network;

class DefaultProtocolMessages implements ProtocolMessages {
    
    private static final String SPACE = " ";
    private static final String CHOOSE_COLOR = "CHOOSE_COLOR";
    private static final String SET_GAME = "SET_GAME";
    private static final String BEGIN_TURN = "BEGIN_TURN";
    private static final String DISTRIBUTE_SOLDIERS = "DISTRIBUTE_SOLDIERS";
    private static final String ADD_CARD = "ADD_CARD";
    private static final String EXCHANGE_CARDS = "EXCHANGE_CARDS";
    private static final String DECLARE_COMBAT = "DECLARE_COMBAT";
    private static final String ANSWER_COMBAT = "ANSWER_COMBAT";
    private static final String MOVE_SOLDIERS = "MOVE_SOLDIERS";
    private static final String FINISH_ATTACK = "FINISH_ATTACK";

    @Override
    public String space() {
	return SPACE;
    }

    @Override
    public String chooseColor() {
	return CHOOSE_COLOR;
    }

    @Override
    public String setGame() {
	return SET_GAME;
    }

    @Override
    public String beginTurn() {
	return BEGIN_TURN;
    }

    @Override
    public String distributeSoldiers() {
	return DISTRIBUTE_SOLDIERS;
    }

    @Override
    public String addCard() {
	return ADD_CARD;
    }

    @Override
    public String exchangeCards() {
	return EXCHANGE_CARDS;
    }

    @Override
    public String declareCombat() {
	return DECLARE_COMBAT;
    }

    @Override
    public String answerCombat() {
	return ANSWER_COMBAT;
    }

    @Override
    public String moveSoldiers() {
	return MOVE_SOLDIERS;
    }

    @Override
    public String finishAttack() {
	return FINISH_ATTACK;
    }
}
