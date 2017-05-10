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
public class stealFromOppositeHouse implements Rule {

    Set<RuleTriggerTime> triggerTimes;

    public stealFromOppositeHouse() {
        super();

        //Defining default trigger times.
        HashSet<RuleTriggerTime> t = new HashSet<RuleTriggerTime>();
        t.add(RuleTriggerTime.beforeEachSeedPlacement);

        this.triggerTimes = t;
    }

    public stealFromOppositeHouse(HashSet<RuleTriggerTime> triggerTimes) {
        super();
        this.triggerTimes = triggerTimes;
    }

    @Override
    public boolean executeLogic(TurnState turnState, ArrayList<BoardSide> boardSides) {

        if (turnState.getHouse() < 6 && ((turnState.getBoardSide() == turnState.getPlayer()) && ((boardSides.get
                (turnState.getBoardSide()).getHouse(turnState.getHouse()).getSeeds() == 0) && (turnState.getSeeds()
                == 1)))) {

            //TODO: make oppositeplayer variable work for more than two players
            //take all of the opposite stores seeds (hardcoded for two players)
            int oppositePlayer = turnState.getPlayer() == 0 ? 1 : 0;
            int stolenSeeds = boardSides.get(oppositePlayer).getHouse(6 - turnState.getHouse() - 1).takeSeeds();
            if (stolenSeeds > 0) {
                boardSides.get(turnState.getBoardSide()).getStore().incrementSeeds((stolenSeeds + 1));
                turnState.decrementSeeds(1);
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
