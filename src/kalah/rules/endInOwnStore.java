package kalah.rules;

import kalah.BoardSide;
import kalah.Rule;
import kalah.RuleTriggerTime;
import kalah.TurnState;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.Set;

/**
 * Created by Ben on 5/9/2017.
 */
public class endInOwnStore implements Rule {

    Set<RuleTriggerTime> triggerTimes;

    public endInOwnStore() {
        super();

        //Defining default trigger times.
        HashSet<RuleTriggerTime> t = new HashSet<RuleTriggerTime>();
        t.add(RuleTriggerTime.beforeEachSeedPlacement);

        this.triggerTimes = t;
    }

    public endInOwnStore(HashSet<RuleTriggerTime> triggerTimes) {
        super();
        this.triggerTimes = triggerTimes;
    }


    @Override
    public boolean executeLogic(TurnState turnState, ArrayList<BoardSide> boardSides) {
        if ((turnState.getBoardSide() == turnState.getPlayer()) && ((turnState.getSeeds() == 1) && (turnState
                .getHouse() == 6))) {
            boardSides.get(turnState.getBoardSide()).getStore().incrementSeeds(1);
            turnState.decrementSeeds(1);
            turnState.setAdditionalTurn(true);
            return true;
        } else {
            return false;
        }
    }

    @Override
    public boolean shouldExecute(RuleTriggerTime triggerTime) {
        return this.triggerTimes.contains(triggerTime);
    }

}
