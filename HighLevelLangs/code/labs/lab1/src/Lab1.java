package highlevellangs.code.labs.lab1.src;

import highlevellangs.code.labs.lab1.src.classes.state.State;

public class Lab1
{
    public static void main(String[] args)
    {
        State state = new State();

        state.printBoard();
        System.out.print("Wrong positions: " + state.getWrongState());

        int[][] example = new int[3][3];
        for (int i = 0; i < example.length; i++)
        {
            for (int j = 0; j < example[i].length; j++)
            {
                if (i == example.length - 1 && j == example[i].length - 1)
                {
                    example[i][j] = 0;
                    break;
                }
                example[i][j] = j + i + 1; 
            }
        }

        state.setBoard(example);
        state.printBoard();
        System.out.print("Wrong positions: " + state.getWrongState());
    }
}