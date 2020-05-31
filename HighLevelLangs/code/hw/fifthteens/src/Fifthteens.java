package highlevellangs.code.hw.fifthteens.src;

import highlevellangs.code.hw.fifthteens.src.classes.boardmodel.Board;
import highlevellangs.code.hw.fifthteens.src.classes.solvermodel.Solver;

public class Fifthteens 
{
    public static void main(String[] args)
    {
        int[][] blocks = new int[][]{{1, 2, 3},
                                     {4, 0, 5},
                                     {7, 8, 6}};
        Board initial = new Board(blocks);
        Solver solver = new Solver(initial);
        System.out.println("NeedMoves = " + solver.moves());
        for (Board board : solver.solution())
            System.out.println(board);
    }
}