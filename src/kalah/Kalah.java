package kalah;

import com.qualitascorpus.testsupport.IO;
import com.qualitascorpus.testsupport.MockIO;
import kalah.rules.endInOwnStore;
import kalah.rules.noPlayableTurn;
import kalah.rules.stealFromOppositeHouse;

import java.util.ArrayList;

/**
 * This class is the starting point for the Modifiability Assignment.
 */
public class Kalah {

    public static final int NUMBER_OF_HOUSES = 6;
    public static final int NUMBER_OF_PLAYERS = 2;

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
        while (this.turnState.getGameOver() == 0) {
            takeSeeds(io);
            placeSeeds();
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

    private void takeSeeds(IO io) {
        while (this.turnState.getSeeds() == 0) {
            printBoard(io);
            if (!checkRules(RuleTriggerTime.beforeTurn)) {
                this.turnState.setHouse(io.readInteger("Player P" + (this.turnState.getPlayer() + 1) + "'s turn - " +
                        "Specify house number or 'q' to quit: ", 1, 6, -1, "q") - 1);

                if (this.turnState.getHouse() > -1) {
                    this.turnState.setSeeds(this.boardSides.get(this.turnState.getBoardSide()).getHouses().get(this.turnState
                            .getHouse()).takeSeeds());
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

    public void placeSeeds() {
        while(this.turnState.getSeeds() != 0) {
            if (checkRules(RuleTriggerTime.beforeEachSeedPlacement)) {
                return;
            } else {
                //If the last board side has ended return to the first house of the first board side
                if (this.turnState.getBoardSide() >= this.boardSides.size()) {
                    this.turnState.setHouse(0);
                    this.turnState.setBoardSide(0);
                //Add to this players store or skip other players stores
                } else if ((this.turnState.getHouse() == NUMBER_OF_HOUSES)) {
                    if (this.turnState.getPlayer() == this.turnState.getBoardSide()) {
                        this.boardSides.get(this.turnState.getBoardSide()).getStore().incrementSeeds(1);
                        this.turnState.decrementSeeds(1);
                    }
                    this.turnState.incrementBoardSide(1);
                    this.turnState.setHouse(0);
                //Place a seed in a house under normal circumstances
                } else {
                    this.boardSides.get(this.turnState.getBoardSide()).getHouse(this.turnState.getHouse()).incrementSeeds();
                    this.turnState.decrementSeeds(1);
                    this.turnState.incrementHouse(1);
                }
            }
        }
        return;
    }

    private boolean checkRules(RuleTriggerTime triggerTime) {
        boolean turnEnding = false;
        for (Rule r : this.rules) {
            if (r.shouldExecute(triggerTime)) {
                turnEnding = turnEnding || r.executeLogic(this.turnState, this.boardSides);
            }
        }
        return turnEnding;
    }

    public void printGameEnd(IO io) {
        io.println("Game over");
        printBoard(io);

        //print the winners and score only if the game ended naturally
        if (this.turnState.getGameOver() == 1) {
            for (int i = 0; i < this.boardSides.size(); i++) {
                BoardSide b = this.boardSides.get(i);
                b.sumHousesAndEnd();
                io.println("\tplayer " + (i + 1) + ":" + this.boardSides.get(i).getStore().getSeeds());
            }
            int winner = getWinner();
            if(winner == -1){
                io.println("A tie!");
            } else {
                io.println("Player " + winner + " wins!");
            }
        }
    }

    //returns -1 if there was a tie
    private int getWinner(){
        int highest = 0;
        int player = 0;
        int count = 0;
        for(int i = 0; i < this.boardSides.size(); i++){
            if(this.boardSides.get(i).getStore().getSeeds() > highest){
                highest =this.boardSides.get(i).getStore().getSeeds();
                player = i;
                count = 1;
            }else if( this.boardSides.get(i).getStore().getSeeds() == highest){
                count++;
            }
        }
        return count > 1 ? -1 : player+1;
    }

    public void resetGame() {
        //Create boardSides with default houses, alternatively houses can be constructed with a desired number of
        // seeds to begin with.
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
        rules.add(new noPlayableTurn());
        this.rules = rules;

        this.turnState = new TurnState();
        this.boardSides = boardSides;
    }

}
