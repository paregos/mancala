package kalah.board;

import kalah.player.Player;

import java.util.ArrayList;

/**
 * Created by Ben on 6/7/2017.
 */
public interface BoardDirection {

    int changeHouse(int original, int value);

    int incrementHouse(int original);

    boolean isAtStore(int houseNumber, int numberOfHouses);

    BoardSide incrementBoardSide(ArrayList<Player> players, BoardSide boardSide);

}
