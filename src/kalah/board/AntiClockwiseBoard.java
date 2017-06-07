package kalah.board;

import kalah.player.Player;

import java.util.ArrayList;

/**
 * Created by Ben on 6/7/2017.
 */
public class AntiClockwiseBoard implements BoardDirection{

    @Override public int changeHouse(int original, int value) {
        return original+value;
    }

    @Override public int incrementHouse(int original) {
        return original++;
    }

    @Override public boolean isAtStore(int houseNumber, int numberOfHouses) {
        return houseNumber == numberOfHouses;
    }

    @Override public BoardSide incrementBoardSide(ArrayList<Player> players, BoardSide boardSide) {
        int current = boardSide.getId();
        boardSide = current >= players.size()-1 ?
                players.get(0).getBoardSide() : players.get(current+1).getBoardSide();
        return boardSide;
    }
}
