package kalah;

import com.qualitascorpus.testsupport.IO;
import com.qualitascorpus.testsupport.MockIO;
import kalah.rules.endInOwnStore;
import kalah.rules.noPlayableTurn;
import kalah.rules.stealFromOppositeHouse;

import java.util.ArrayList;

/**
 * Created by Ben on 5/6/2017.
 */
public class Kalah {

    public static final int NUMBER_OF_HOUSES = 6;
    public static final int NUMBER_OF_PLAYERS = 2;

    //players start at 0 (e.g player 0, player 1)
    private ArrayList<Player> players;
    private TurnState turnState;
    private ArrayList<Rule> rules;

    public static void main(String[] args) {
        new Kalah().play(new MockIO());
    }

    public Kalah() {
        super();
        resetGame();
    }

    /**
     * Constantly asks for the next player to play their turn. Once the game has ended the appropriate error message
     * will be printed via the KalahUtilities class.
     *
     * @param io The MockIO interface.
     */
    public void play(IO io) {
        while (this.turnState.getGameOver() == 0) {
            takeSeeds(io);
            placeSeeds();
            this.turnState.setPlayerToNext(this.players);
        }
        KalahUtilities.printGameEnd(io, this.players, this.turnState);
    }

    /**
     * Repeatedly asks the player whose turn it currently is for a house selection. Once a valid house has been
     * selected (between 1-6, with seeds inside) the seeds will be removed from that house and the placeSeeds method
     * will be called. All rules that have been placed within this.rules with the trigger beforeTurn will be
     * triggered before the turn takes place.
     *
     * @param io The MockIO interface.
     */
    private void takeSeeds(IO io) {
        while (this.turnState.getSeeds() == 0) {
            KalahUtilities.printBoard(io, this.players);
            if (!checkRules(RuleTriggerTime.beforeTurn)) {
                this.turnState.setHouseNumber(
                        io.readInteger("Player P" + (this.turnState.getCurrentPlayer().getId() + 1) + "'s turn - " +
                                               "Specify house number or 'q' to quit: ", 1, 6, -1, "q") - 1);
                if (this.turnState.getHouseNumber() > -1) {
                    this.turnState.setSeeds(
                            this.turnState.getBoardSide().getHouses().get(this.turnState.getHouseNumber()).takeSeeds());
                    this.turnState.incrementHouse(1);
                } else {
                    this.turnState.setGameOver(-1);
                    return;
                }
                if (this.turnState.getSeeds() == 0) {
                    io.println("House is empty. Move again.");
                }
            } else {
                return;
            }
        }
    }

    /**
     * Rotates anticlockwise around the set of houses and boardsides placing one seed in each of the houses until
     * there are none left. If the seeds reach the store of the player whose turn it is a seed will be placed within
     * it. All rules that have been placed within this.rules with the trigger beforeEachSeedPlacement will be
     * triggered before each repeated placement.
     */
    private void placeSeeds() {
        while (this.turnState.getSeeds() != 0) {
            if (checkRules(RuleTriggerTime.beforeEachSeedPlacement)) {
                return;
            } else {
                //Add to this players store or skip other players stores
                if ((this.turnState.getHouseNumber() == NUMBER_OF_HOUSES)) {
                    if (this.turnState.getCurrentPlayer().getId() == this.turnState.getBoardSide().getId()) {
                        this.turnState.getCurrentPlayer().getBoardSide().getStore().incrementSeeds(1);
                        this.turnState.decrementSeeds(1);
                    }
                    this.turnState.incrementBoardSide(this.players);
                    this.turnState.setHouseNumber(0);
                    //Place a seed in a house under normal circumstances
                } else {
                    this.turnState.getBoardSide().getHouse(this.turnState.getHouseNumber()).incrementSeeds(1);
                    this.turnState.decrementSeeds(1);
                    this.turnState.incrementHouse(1);
                }
            }
        }
        return;
    }

    /**
     * Recurses through all of the rules contained within this.rules and executes their logic if they have a
     * triggerTime which matches that of the input parameter triggerTime.
     *
     * @param triggerTime the triggerTime of which rules should be executed.
     * @return returns true if the turn should end
     */
    private boolean checkRules(RuleTriggerTime triggerTime) {
        boolean turnEnding = false;
        for (Rule r : this.rules) {
            if (r.shouldExecute(triggerTime)) {
                turnEnding = turnEnding || r.executeLogic(this.turnState, this.players);
            }
        }
        return turnEnding;
    }

    /**
     * Resets the game to a new state, this is used to setup all needed variables at the start of a game. TurnState
     * is intialised and this.rules is populated with instances of the rules that will be included in the game.
     */
    private void resetGame() {
        ArrayList<Player> players = new ArrayList<Player>();
        for (int i = 0; i < NUMBER_OF_PLAYERS; i++) {
            ArrayList<House> houses = new ArrayList<House>();
            for (int j = 0; j < NUMBER_OF_HOUSES; j++) {
                houses.add(new House());
            }
            players.add(new Player(i, new BoardSide(houses, i)));
        }

        //Creating the set of rules for this game
        ArrayList<Rule> rules = new ArrayList<Rule>();
        rules.add(new endInOwnStore());
        rules.add(new stealFromOppositeHouse());
        rules.add(new noPlayableTurn());
        this.rules = rules;

        this.players = players;
        this.turnState = new TurnState(this.players.get(0), this.players.get(0).getBoardSide(), 0);
    }

}
