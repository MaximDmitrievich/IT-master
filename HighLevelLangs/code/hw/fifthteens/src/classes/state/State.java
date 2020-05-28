package highlevellangs.code.hw.fifthteens.src.classes.state;

public interface State {
    public Iterable<State> getPossibleMoves();
    public boolean isSolution();
    public double getHeuristic();
    public double getDistance();
    public State getParent();
}