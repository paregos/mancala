package kalah.rules;

import kalah.player.Player;
import kalah.TurnState;

import java.util.ArrayList;

/**
 * Created by Ben on 5/9/2017.
 */
public interface Rule {

    /**
     * Executes the logic of the implementing rule.
     *
     * @param turnState     The current turnState of the game.
     * @param players       The list of players contained within Kalah.
     * @return              True if the current turn should end.
     */
    boolean executeLogic(TurnState turnState, ArrayList<Player> players);

    /**
     * Determines if the rule should be executed.
     *
     * @param triggerTime   The triggerTime that is currently being fired.
     * @return              True if the rule should be executed.
     */
    boolean shouldExecute(RuleTriggerTime triggerTime);

}
