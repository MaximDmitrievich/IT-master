package highlevellangs.code.hw.fifthteens.src.classes.boardmodel;

public abstract class AbstractBoard {
    protected int[][] blocks;
    protected int zeroX;
    protected int zeroY;
    protected int h;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        AbstractBoard board = (AbstractBoard)o;

        if (board.dimension() != dimension()) return false;
        for (int i = 0; i < blocks.length; i++) {
            for (int j = 0; j < blocks[i].length; j++) {
                if (blocks[i][j] != board.blocks[i][j]) {
                    return false;
                }
            }
        }

        return true;
    }
    
    public int geth() {
        return h;
    }

    public boolean isGoal() {
        return h == 0;
    }

    protected abstract int dimension();

    public abstract Iterable<Board> neighbors();

}