package kalah;

import java.util.ArrayList;

/**
 * Created by Ben on 5/9/2017.
 */
public interface Rule {



    //returns true if the current turn should end
    boolean executeLogic(TurnState turnState, ArrayList<BoardSide> boardSides);

}
