package kalah.rules;

import kalah.BoardSide;
import kalah.Rule;
import kalah.RuleTriggerTime;
import kalah.TurnState;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.Set;

/**
 * Created by Ben on 5/10/2017.
 */
public class noPlayableMove implements Rule {

    Set<RuleTriggerTime> triggerTimes;

    public noPlayableMove(){
        super();

        //Defining default trigger times.
        HashSet<RuleTriggerTime> t = new HashSet<RuleTriggerTime>();
        t.add(RuleTriggerTime.beforeTurn);

        this.triggerTimes = t;
    }

    public noPlayableMove(HashSet<RuleTriggerTime> triggerTimes){
        super();
        this.triggerTimes = triggerTimes;
    }


    @Override
    public boolean executeLogic(TurnState turnState, ArrayList<BoardSide> boardSides) {

        if (turnState.getSeeds() == 0) {
            if (boardSides.get(turnState.getPlayer()).hasEmptyHouses()) {
                turnState.setNaturalEnd(true);
                turnState.setGameOver(true);
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
