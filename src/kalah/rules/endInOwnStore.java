package kalah.rules;

import kalah.BoardSide;
import kalah.Rule;
import kalah.TurnState;

import java.util.ArrayList;

/**
 * Created by Ben on 5/9/2017.
 */
public class endInOwnStore implements Rule {

    @Override
    public boolean executeLogic(TurnState turnState, ArrayList<BoardSide> boardSides) {
        if ((turnState.getBoardSide() == turnState.getPlayer()) && ((turnState.getSeeds() == 1) && (turnState.getHouse() == 6))) {
            boardSides.get(turnState.getBoardSide()).getStore().incrementSeeds(1);
            turnState.decrementSeeds(1);
            turnState.setAdditionalTurn(true);
            return true;
        }else{
            return false;
        }
    }

}
