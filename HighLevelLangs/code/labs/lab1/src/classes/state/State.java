package highlevellangs.code.labs.lab1.src.classes.state;

import java.util.HashSet;
import java.util.Random;
import java.util.Set;

public class State 
{
    private int[][] board;
    private int rightState;

    public State() {
        this.board = new int[4][4];
        Random r = new Random();
        Set<Integer> used = new HashSet<Integer>();
        for (int i = 0; i < board.length; i++) 
        {
            for (int j = 0; j < board.length; j++)
            {
                if (i == board.length - 1 && j == board[i].length - 1)
                {
                    this.board[i][j] = 0;
                    break;
                }

                int res = -1;
                do {
                    res = r.nextInt(16);  
                } while (used.contains(res));

                this.board[i][j] = res;
                used.add(res);
            }
        }
        this.rightState = 0;
        this.countState();
    }
    
    public State(int[][] board) {
        this.board = board;
        this.rightState = 0;
        this.countState();
    }

    public void setBoard(int[][] board){
        this.board = board;
        this.rightState = 0;
        this.countState();
    }

    public int[][] getBoard(){
        return this.board;
    }

    public int getRightState(){
        return this.rightState - 1;
    }

    public int getWrongState(){
        return 15 - this.rightState;
    }

    public void printBoard() {
        for (int i = 0; i < this.board.length; i++) {
            for (int j = 0; j < this.board[i].length; j++) {
                System.out.print(this.board[i][j] + " ");
            }
            System.out.println();
        }
    }

    private void countState() {
        for (int i = 0; i < this.board.length; i++) {
            for (int j = 0; j < this.board[i].length; j++) {
                if (board[i][j] == i + j + 1) {
                    this.rightState++;
                }
            }
        }
    }
}