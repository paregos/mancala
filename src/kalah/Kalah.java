package kalah;

import com.qualitascorpus.testsupport.IO;
import com.qualitascorpus.testsupport.MockIO;
import kalah.rules.endInOwnStore;
import kalah.rules.stealFromOppositeHouse;

import java.lang.reflect.Array;
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
        resetBoard();
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
            if(checkGameEnd()){
                this.turnState.setGameOver(true);
                return;
            }

            this.turnState.setHouse(io.readInteger("Player P" + (this.turnState.getPlayer() + 1) + "'s turn - Specify house number or 'q' to quit: ", 1, 6, -1, "q")-1);

            if(this.turnState.getHouse() > -1){
                int seeds = this.boardSides.get(this.turnState.getBoardSide()).getHouses().get(this.turnState.getHouse()).takeSeeds();
                this.turnState.setSeeds(seeds);
            } else {
                this.turnState.setGameOver(true);
                this.turnState.setNaturalEnd(false);
                return;
            }
            if(this.turnState.getSeeds() == 0){
                io.println("House is empty. Move again.");
            }
        }

        this.turnState.incrementHouse(1);
        placeSeeds();
        return;
    }


    //TODO: change this to be an iterative function using while loop
    public void placeSeeds() {

        if (checkRules()) {
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
    private boolean checkRules() {

        System.out.println("In Check rules the next turn I will have " + this.turnState.getSeeds() + " seeds and be looking at house " + (this.turnState.getHouse() + 1));

        boolean turnEnding = false;

//        //if the turn is ending in the players store
//        if ((this.turnState.getBoardSide() == this.turnState.getPlayer()) && ((this.turnState.getSeeds() == 1) && (this.turnState.getHouse() == 6))) {
//            this.boardSides.get(this.turnState.getBoardSide()).getStore().incrementSeeds(1);
//            this.turnState.decrementSeeds(1);
//            this.turnState.setAdditionalTurn(true);
//            turnEnding = true;
//
//        //if the turn is ending in a previously empty house of the players
//        } else if (this.turnState.getHouse() < 6 && ((this.turnState.getBoardSide() == this.turnState.getPlayer()) && ((this.boardSides.get(this.turnState.getBoardSide()).getHouse(this.turnState.getHouse()).getSeeds() == 0) && (this.turnState.getSeeds() == 1)))) {
//            //take all of the opposite stores seeds (hardcoded for two players)
//            int oppositePlayer = this.turnState.getPlayer() == 0 ? 1 : 0;
//            int stolenSeeds = this.boardSides.get(oppositePlayer).getHouse(NUMBER_OF_HOUSES - this.turnState.getHouse() - 1).takeSeeds();
//            if (stolenSeeds > 0) {
//                this.boardSides.get(this.turnState.getBoardSide()).getStore().incrementSeeds((stolenSeeds + 1));
//                this.turnState.decrementSeeds(1);
//                turnEnding = true;
//            }
//        }

        for(Rule r : this.rules){
            turnEnding = turnEnding || r.executeLogic(this.turnState, this.boardSides);
        }

        System.out.println(turnEnding);
        return turnEnding;
    }


    private boolean checkGameEnd() {

        if (this.turnState.getSeeds() == 0) {
                if (this.boardSides.get(this.turnState.getPlayer()).hasEmptyHouses()) {
                    System.out.println("ending game");
                    this.turnState.setNaturalEnd(true);
                    return true;
                }
        }

        return false;
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

    public void resetBoard() {

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
        this.rules = rules;


        this.turnState = new TurnState();
        this.boardSides = boardSides;
        return;
    }


}
