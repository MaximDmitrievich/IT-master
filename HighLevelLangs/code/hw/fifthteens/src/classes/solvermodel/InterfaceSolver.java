package highlevellangs.code.hw.fifthteens.src.classes.solvermodel;

import highlevellangs.code.hw.fifthteens.src.classes.boardmodel.Board;

public interface InterfaceSolver {
    boolean isSolvable();
    int moves();
    Iterable<Board> solution();
}