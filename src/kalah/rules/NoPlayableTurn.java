package kalah.rules;

import kalah.*;
import kalah.player.Player;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.Set;


/**
 * Created by Ben on 5/10/2017.
 */
public class NoPlayableTurn implements Rule {

    private Set<RuleTriggerTime> triggerTimes;

    public NoPlayableTurn(){
        super();
        //Defining default trigger times.
        HashSet<RuleTriggerTime> t = new HashSet<RuleTriggerTime>();
        t.add(RuleTriggerTime.beforeTurn);

        this.triggerTimes = t;

    }

    public NoPlayableTurn(HashSet<RuleTriggerTime> triggerTimes){
        super();
        this.triggerTimes = triggerTimes;
    }


    @Override
    public boolean executeLogic(TurnState turnState, ArrayList<Player> players) {

        //If the active player only has houses containing 0 seeds there is no playable turn for that player and the
        // game should end.
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
