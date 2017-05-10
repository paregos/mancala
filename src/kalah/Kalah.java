package kalah;

import com.qualitascorpus.testsupport.IO;
import com.qualitascorpus.testsupport.MockIO;
import kalah.rules.endInOwnStore;
import kalah.rules.noPlayableMove;
import kalah.rules.stealFromOppositeHouse;

import java.util.ArrayList;

/**
 * This class is the starting point for the Modifiability Assignment.
 */
public class Kalah {

    public final int NUMBER_OF_HOUSES = 6;
    public final int NUMBER_OF_PLAYERS = 2;

    //players start at 0 (e.g player 0, player 1)
    private ArrayList<BoardSide> boardSides;
    private TurnState turnState;

    private ArrayList<Rule> rules;

    public static void main(String[] args) {
        new Kalah().play(new MockIO());
    }

    public Kalah() {
        super();
        resetGame();
    }

    public void play(IO io) {

        while (!this.turnState.isGameOver()) {
            takeTurn(io);
            //sets the player to the next player and resets turn state variables
            this.turnState.setPlayerToNext();
        }
        printGameEnd(io);
    }


    private void printBoard(IO io) {
        io.println("+----+-------+-------+-------+-------+-------+-------+----+");
        io.println("| P2 " + this.boardSides.get(1).toStringReverse() + this.boardSides.get(0).getStore().toString());
        io.println("|    |-------+-------+-------+-------+-------+-------|    |");
        io.println(this.boardSides.get(1).getStore().toString() + this.boardSides.get(0).toString() + " P1 |");
        io.println("+----+-------+-------+-------+-------+-------+-------+----+");
        return;
    }

    private void takeTurn(IO io) {

        while(this.turnState.getSeeds() == 0) {

            printBoard(io);
            if(!checkRules(RuleTriggerTime.beforeTurn)) {

                this.turnState.setHouse(io.readInteger("Player P" + (this.turnState.getPlayer() + 1) + "'s turn - Specify house number or 'q' to quit: ", 1, 6, -1, "q") - 1);

                if (this.turnState.getHouse() > -1) {
                    int seeds = this.boardSides.get(this.turnState.getBoardSide()).getHouses().get(this.turnState.getHouse()).takeSeeds();
                    this.turnState.setSeeds(seeds);
                } else {
                    this.turnState.setGameOver(true);
                    this.turnState.setNaturalEnd(false);
                    return;
                }
                if (this.turnState.getSeeds() == 0) {
                    io.println("House is empty. Move again.");
                }

            }else{
                return;
            }
        }

        this.turnState.incrementHouse(1);
        placeSeeds();
        return;
    }


    //TODO: change this to be an iterative function using while loop
    public void placeSeeds() {

        if (checkRules(RuleTriggerTime.beforeEachSeedPlacement)) {
            return;
        } else {

            //when no more seeds check the end condition
            if (this.turnState.getSeeds() == 0) {
                return;
            }

            //if we finished the last board side reset to the first board
            if (this.turnState.getBoardSide() >= this.boardSides.size()) {
                this.turnState.setHouse(0);
                this.turnState.setBoardSide(0);
            }

            //Add to store
            if ((this.turnState.getHouse() == NUMBER_OF_HOUSES)) {

                if (this.turnState.getPlayer() == this.turnState.getBoardSide()) {
                    this.boardSides.get(this.turnState.getBoardSide()).getStore().incrementSeeds(1);
                    this.turnState.decrementSeeds(1);
                }

                this.turnState.incrementBoardSide(1);
                this.turnState.setHouse(0);

            } else {
                this.boardSides.get(this.turnState.getBoardSide()).getHouse(this.turnState.getHouse()).incrementSeeds();
                this.turnState.decrementSeeds(1);
                this.turnState.incrementHouse(1);
            }

            placeSeeds();
            return;
        }
    }

    //returns true if the turn should stop
    private boolean checkRules(RuleTriggerTime triggerTime) {

        boolean turnEnding = false;

        for(Rule r : this.rules){
            if(r.shouldExecute(triggerTime)) {
                turnEnding = turnEnding || r.executeLogic(this.turnState, this.boardSides);
            }
        }
        return turnEnding;
    }

    public void printGameEnd(IO io){
        io.println("Game over");
        printBoard(io);

        //print the winners and score only if the game ended naturally
        if(this.turnState.isNaturalEnd()) {
            for (int i = 0; i < this.boardSides.size(); i++) {
                BoardSide b = this.boardSides.get(i);
                b.sumHousesAndEnd();
                io.println("\tplayer " + (i + 1) + ":" + this.boardSides.get(i).getStore().getSeeds());
            }

            //TODO: make this dynamic for any number of players

            if (this.boardSides.get(0).getStore().getSeeds() == this.boardSides.get(1).getStore().getSeeds()) {
                io.println("A tie!");
            } else {
                int winner = this.boardSides.get(0).getStore().getSeeds() > this.boardSides.get(1).getStore().getSeeds() ? 1 : 2;
                io.println("Player " + winner + " wins!");
            }
        }
        return;
    }

    public void resetGame() {

        //Create boardSides with default houses, alternatively houses can be constructed with a desired
        //number of seeds to begin with.
        ArrayList<BoardSide> boardSides = new ArrayList<BoardSide>();
        for (int i = 0; i < NUMBER_OF_PLAYERS; i++) {
            ArrayList<House> houses = new ArrayList<House>();
            for (int j = 0; j < NUMBER_OF_HOUSES; j++) {
                houses.add(new House());
            }
            boardSides.add(new BoardSide(houses));
        }

        //Creating the set of rules for this game
        ArrayList<Rule> rules = new ArrayList<Rule>();
        rules.add(new endInOwnStore());
        rules.add(new stealFromOppositeHouse());
        rules.add(new noPlayableMove());
        this.rules = rules;


        this.turnState = new TurnState();
        this.boardSides = boardSides;
        return;
    }


}
