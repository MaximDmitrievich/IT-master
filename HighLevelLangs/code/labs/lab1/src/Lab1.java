package highlevellangs.code.labs.lab1.src;

import highlevellangs.code.labs.lab1.src.state.State;

public class Lab1
{
    public static void main(String[] args)
    {
        State state = new State();

        state.printBoard();
        System.out.print("Wrong positions: " + state.getWrongState());
        
    }
}