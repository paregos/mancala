package kalah;

import java.util.ArrayList;
import java.util.Set;

/**
 * Created by Ben on 5/9/2017.
 */
public interface Rule {

    //returns true if the current turn should end
    boolean executeLogic(TurnState turnState, ArrayList<Player> players);

    boolean shouldExecute(RuleTriggerTime triggerTime);

}
