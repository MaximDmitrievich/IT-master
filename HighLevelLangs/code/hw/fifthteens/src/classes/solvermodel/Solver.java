package highlevellangs.code.hw.fifthteens.src.classes.solvermodel;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.Iterator;
import java.util.List;
import java.util.PriorityQueue;

import highlevellangs.code.hw.fifthteens.src.classes.boardmodel.Board;
import highlevellangs.code.hw.fifthteens.src.classes.statemodel.State;

public class Solver implements InterfaceSolver {
    private List<Board> result = new ArrayList<>();

    public Solver(Board initial) {
        if(!isSolvable()) return;

        PriorityQueue<State> priorityQueue = new PriorityQueue<>(10, new Comparator<State>() {
            @Override
            public int compare(State s1, State s2) {
                return new Integer(measure(s1)).compareTo(new Integer(measure(s2)));
            }
        });

        priorityQueue.add(new State(null, initial));

        while (true){
            State state = priorityQueue.poll();
            if(state.getBoard().isGoal()) {
                itemToList(new State(state, state.getBoard()));
                return;
            }

            Iterator<Board> iterator = state.getBoard().neighbors().iterator();
            while (iterator.hasNext()){
                Board board = (Board)iterator.next();
                if(board != null && !containsInPath(state, board))
                    priorityQueue.add(new State(state, board));
            }

        }
    }

    private int measure(State item){
        State item2 = item;
        int c= 0;
        int measure = item.getBoard().geth();
        while (true){
            c++;
            item2 = item2.getPrevState();
            if(item2 == null) {
                return measure + c;
            }
        }
    }

    private void itemToList(State state){
        State state2 = state;
        while (true){
            state2 = state2.getPrevState();
            if(state2 == null) {
                Collections.reverse(result);
                return;
            }
            result.add(state2.getBoard());
        }
    }

    private boolean containsInPath(State state, Board board){
        State state2 = state;
        while (true){
            if(state2.getBoard().equals(board)) return true;
            state2 = state2.getPrevState();
            if(state2 == null) return false;
        }
    }

    @Override
    public boolean isSolvable() {
        return true;
    }

    @Override
    public int moves() {
        if(!isSolvable()) return -1;
        return result.size() - 1;
    }

    @Override
    public Iterable<Board> solution() {
        return result;
    }
}