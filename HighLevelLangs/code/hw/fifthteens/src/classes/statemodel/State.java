package highlevellangs.code.hw.fifthteens.src.classes.statemodel;

import highlevellangs.code.hw.fifthteens.src.classes.boardmodel.Board;

public class State {
    private State prevState;
    private Board board;

    public State(State prevState, Board board) {
        this.prevState = prevState;
        this.board = board;
    }

    public Board getBoard() {
        return board;
    }

    public State getPrevState() {
        return prevState;
    }
}