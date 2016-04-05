package tetris.Model;

import java.util.ArrayList;

/**
 *
 * @author Remi
 */
public class Match {
    ArrayList<Block> MatchedCells;
    int timeMatched;
    int count;

    public Match() {
        this.timeMatched = 0;
        this.count = 0;
        this.MatchedCells = new ArrayList<Block>();
    }

    public void addMatchedCell(Block b){
        MatchedCells.add(b);
        this.count += 1;
    }

    public ArrayList<Block> getMatchedCells() {
        return MatchedCells;
    }
    
    public int getTimeMatched() {
        return timeMatched;
    }

    public void setTimeMatched(int timeMatched) {
        this.timeMatched = timeMatched;
    }

    public int getCount() {
        return count;
    }
}
