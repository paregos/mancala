package kalah.rules;

import kalah.*;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.Set;

/**
 * Created by Ben on 5/10/2017.
 */
public class noPlayableTurn implements Rule {

    Set<RuleTriggerTime> triggerTimes;

    public noPlayableTurn(){
        super();

        //Defining default trigger times.
        HashSet<RuleTriggerTime> t = new HashSet<RuleTriggerTime>();
        t.add(RuleTriggerTime.beforeTurn);

        this.triggerTimes = t;
    }

    public noPlayableTurn(HashSet<RuleTriggerTime> triggerTimes){
        super();
        this.triggerTimes = triggerTimes;
    }


    @Override
    public boolean executeLogic(TurnState turnState, ArrayList<Player> players) {

        if (turnState.getSeeds() == 0) {
            if (turnState.getBoardSide().hasEmptyHouses()) {
                turnState.setGameOver(1);
                return true;
            }
        }
        return false;
    }

    @Override
    public boolean shouldExecute(RuleTriggerTime triggerTime) {
        return this.triggerTimes.contains(triggerTime);
    }
}
