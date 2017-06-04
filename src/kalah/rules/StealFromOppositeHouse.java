package kalah.rules;

import kalah.*;
import kalah.player.Player;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.Set;

/**
 * Created by Ben on 5/9/2017.
 */
public class StealFromOppositeHouse implements Rule {

    private Set<RuleTriggerTime> triggerTimes;

    public StealFromOppositeHouse() {
        super();
        //Defining default trigger times.
        HashSet<RuleTriggerTime> t = new HashSet<RuleTriggerTime>();
        t.add(RuleTriggerTime.beforeEachSeedPlacement);
        this.triggerTimes = t;
    }

    public StealFromOppositeHouse(HashSet<RuleTriggerTime> triggerTimes) {
        super();
        this.triggerTimes = triggerTimes;
    }

    @Override
    public boolean executeLogic(TurnState turnState, ArrayList<Player> players) {

        //If the last seed lands in a previously empty house that is owned by the current player
        if (turnState.getHouseNumber() < Kalah.NUMBER_OF_HOUSES &&
                ((turnState.getBoardSide().getId() == turnState.getCurrentPlayer().getId())
                        && ((turnState.getBoardSide().getHouse(turnState.getHouseNumber()).getSeeds() == 0) &&
                        (turnState.getSeeds()
                                == 1)))) {

            //TODO: make oppositePlayer variable work for more than two players
            //Take all of the opposite house's seeds
            int oppositePlayer = turnState.getCurrentPlayer().getId() == 0 ? 1 : 0;
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
