package kalah.rules;

import kalah.*;

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
    public boolean executeLogic(TurnState turnState, ArrayList<Player> players) {

        if (turnState.getHouseNumber() < Kalah.NUMBER_OF_HOUSES &&
                ((turnState.getBoardSide().getNumber() == turnState.getPlayer().getNumber())
                        && ((turnState.getBoardSide().getHouse(turnState.getHouseNumber()).getSeeds() == 0) &&
                        (turnState.getSeeds()
                                == 1)))) {

            //TODO: make oppositeplayer variable work for more than two players
            //take all of the opposite stores seeds (hardcoded for two players)
            int oppositePlayer = turnState.getPlayer().getNumber() == 0 ? 1 : 0;
            int stolenSeeds = players.get(oppositePlayer).getBoardSide().getHouse(6 - turnState.getHouseNumber() - 1)
                                     .takeSeeds();
            if (stolenSeeds > 0) {
                turnState.getBoardSide().getStore().incrementSeeds((stolenSeeds + 1));
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
