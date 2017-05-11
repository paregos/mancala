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

    public void play(IO io) {
        while (this.turnState.getGameOver() == 0) {
            takeSeeds(io);
            placeSeeds();
            this.turnState.setPlayerToNext(this.players);
        }
        printGameEnd(io);
    }


    private void printBoard(IO io) {
        io.println("+----+-------+-------+-------+-------+-------+-------+----+");
        io.println("| P2 " + this.players.get(1).getBoardSide().toStringReverse() + this.players.get(0).getBoardSide().getStore().toString());
        io.println("|    |-------+-------+-------+-------+-------+-------|    |");
        io.println(this.players.get(1).getBoardSide().getStore().toString() + this.players.get(0).getBoardSide().toString() + " P1 |");
        io.println("+----+-------+-------+-------+-------+-------+-------+----+");
        return;
    }

    private void takeSeeds(IO io) {
        while (this.turnState.getSeeds() == 0) {
            printBoard(io);
            if (!checkRules(RuleTriggerTime.beforeTurn)) {
                System.out.println("here");
                this.turnState.setHouseNumber(io.readInteger("Player P" + (this.turnState.getPlayer().getNumber() + 1) + "'s turn - " +
                        "Specify house number or 'q' to quit: ", 1, 6, -1, "q") - 1);

                if (this.turnState.getHouseNumber() > -1) {
                    this.turnState.setSeeds(this.turnState.getBoardSide().getHouses().get(this.turnState
                            .getHouseNumber()).takeSeeds());
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
                //Add to this players store or skip other players stores
                if ((this.turnState.getHouseNumber() == NUMBER_OF_HOUSES)) {
                    if (this.turnState.getPlayer().getNumber() == this.turnState.getBoardSide().getNumber()) {
                        this.turnState.getPlayer().getBoardSide().getStore().incrementSeeds(1);
                        this.turnState.decrementSeeds(1);
                    }
                    this.turnState.incrementBoardSide(this.players);
                    this.turnState.setHouseNumber(0);
                //Place a seed in a house under normal circumstances
                } else {
                    this.turnState.getBoardSide().getHouse(this.turnState.getHouseNumber()).incrementSeeds();
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
                turnEnding = turnEnding || r.executeLogic(this.turnState, this.players);
            }
        }
        return turnEnding;
    }

    public void printGameEnd(IO io) {
        io.println("Game over");
        printBoard(io);

        //print the winners and score only if the game ended naturally
        if (this.turnState.getGameOver() == 1) {
            for (int i = 0; i < this.players.size(); i++) {
                BoardSide b = this.players.get(i).getBoardSide();
                b.sumHousesAndEnd();
                io.println("\tplayer " + (i + 1) + ":" + b.getStore().getSeeds());
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
        for(int i = 0; i < this.players.size(); i++){
            if(this.players.get(i).getBoardSide().getStore().getSeeds() > highest){
                highest = this.players.get(i).getBoardSide().getStore().getSeeds();
                player = i;
                count = 1;
            }else if( this.players.get(i).getBoardSide().getStore().getSeeds() == highest){
                count++;
            }
        }
        return count > 1 ? -1 : player+1;
    }

    public void resetGame() {
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
