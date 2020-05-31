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
    private final List<Board> result = new ArrayList<>();

    public Solver(final Board initial) {
        if (!isSolvable())
            return;

        final PriorityQueue<State> priorityQueue = new PriorityQueue<>(10, new Comparator<State>() {
            @Override
            public int compare(final State s1, final State s2) {
                int res = 0;
                if (measure(s1) == measure(s2)) {
                    res = 0;
                } else if (measure(s1) < measure(s2)) {
                    res = -1;
                } else if (measure(s1) > measure(s2)) {
                    res = 1;
                }
                return res;
            }
        });

        priorityQueue.add(new State(null, initial));

        while (true) {
            final State state = priorityQueue.poll();
            if (state.getBoard().isGoal()) {
                itemToList(new State(state, state.getBoard()));
                return;
            }

            final Iterator<Board> iterator = state.getBoard().neighbors().iterator();
            while (iterator.hasNext()) {
                final Board board = (Board) iterator.next();
                if (board != null && !containsInPath(state, board))
                    priorityQueue.add(new State(state, board));
            }

        }
    }

    private int measure(final State item) {
        State item2 = item;
        int c = 0;
        final int measure = item.getBoard().geth();
        while (true) {
            c++;
            item2 = item2.getPrevState();
            if (item2 == null) {
                return measure + c;
            }
        }
    }

    private void itemToList(final State state) {
        State state2 = state;
        while (true) {
            state2 = state2.getPrevState();
            if (state2 == null) {
                Collections.reverse(result);
                return;
            }
            result.add(state2.getBoard());
        }
    }

    private boolean containsInPath(final State state, final Board board) {
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