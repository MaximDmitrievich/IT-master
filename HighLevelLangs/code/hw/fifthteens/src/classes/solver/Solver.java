package highlevellangs.code.hw.fifthteens.src.classes.solver;

import java.util.List;

import highlevellangs.code.hw.fifthteens.src.classes.state.State;

public interface Solver {
    public List<State> solve(State initialState);
}