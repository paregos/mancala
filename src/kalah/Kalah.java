package kalah;

import com.qualitascorpus.testsupport.IO;
import com.qualitascorpus.testsupport.MockIO;

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

	public static void main(String[] args) {
	    MockIO m = new MockIO();
        m.setExpected("/test/full_game1.txt");
		new Kalah().play(m);
	}

	public Kalah(){
        super();
	    resetBoard();
	}

	public void play(IO io) {

        while(!this.turnState.isGameOver()) {
            takeTurn(io);
            this.turnState.setPlayerToNext();
        }
	}



	private void takeTurn(IO io){
        io.println("+----+-------+-------+-------+-------+-------+-------+----+");
        io.println("| P2 "+this.boardSides.get(1).toStringReverse()+this.boardSides.get(0).getStore().toString());
        io.println("|    |-------+-------+-------+-------+-------+-------|    |");
        io.println(this.boardSides.get(1).getStore().toString()+this.boardSides.get(0).toString()+" P1 |");
        io.println("+----+-------+-------+-------+-------+-------+-------+----+");
        int houseNumber = io.readInteger("Player "+(this.turnState.getPlayer()+1)+"'s turn - Specify house number or 'q' to quit: ", 1, 6, -1, "q");

        //if 'q' was not inputted begin players move
        if(houseNumber != -1) {
            //convert value chosen to 0 index houseNumbers
            this.turnState.setHouse(houseNumber-1);
            this.turnState.setBoardSide(this.turnState.getPlayer());
            move();
        }
        return;
    }

    private void move (){

	    //remove all the seeds from the selected house
        int seeds = this.boardSides.get(this.turnState.getBoardSide()).getHouses().get(this.turnState.getHouse()).takeSeeds();
        this.turnState.setSeeds(seeds);
        this.turnState.incrementHouse(1);

        placeSeeds();
        return;

    }

    //TODO: change this to be an iterative function using while loop
    public void placeSeeds(){

        if(checkRules()) {
            return;
        }else {
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
            if (this.turnState.getHouse() == NUMBER_OF_HOUSES) {

                this.boardSides.get(this.turnState.getBoardSide()).getStore().incrementSeeds(1);

                this.turnState.incrementBoardSide(1);
                this.turnState.setHouse(0);
                this.turnState.decrementSeeds(1);

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

        //check if the game should end
        if(checkGameEnd()){
            for(BoardSide b : this.boardSides){
                b.sumHousesAndEnd();
            }
            this.turnState.setGameOver(true);
            return true;
        }

        //if the turn is ending in the players store
        if ((this.turnState.getBoardSide() == this.turnState.getPlayer())&&((this.turnState.getSeeds() == 1) && (this.turnState.getHouse() == 6))) {
            this.boardSides.get(this.turnState.getBoardSide()).getStore().incrementSeeds(1);
            this.turnState.decrementSeeds(1);
            this.turnState.setAdditionalTurn(true);
            return true;

        //if the turn is ending in a previously empty house of the players
        } else if(this.turnState.getHouse() <6 &&((this.turnState.getBoardSide() == this.turnState.getPlayer()) && ((this.boardSides.get(this.turnState.getBoardSide()).getHouse(this.turnState.getHouse()).getSeeds() == 0) && (this.turnState.getSeeds() == 1)))){
            //take all of the opposite stores seeds (hardcoded for two players)
            int oppositePlayer = this.turnState.getPlayer() == 0 ? 1 : 0;
            int stolenSeeds = this.boardSides.get(oppositePlayer).getHouse(NUMBER_OF_HOUSES-this.turnState.getHouse()-1).takeSeeds();
            this.boardSides.get(this.turnState.getBoardSide()).getStore().incrementSeeds(stolenSeeds+1);
            this.turnState.decrementSeeds(1);
            return true;
        }
        return false;
    }


    private boolean checkGameEnd(){

        for(BoardSide b : this.boardSides){
            if(b.hasEmptyHouses()){
                return true;
            }
        }

        return false;
    }

	public void resetBoard(){

        ArrayList<BoardSide> boardSides = new ArrayList<BoardSide>();
        for(int i=0; i<NUMBER_OF_PLAYERS; i++){
            ArrayList<House> houses = new ArrayList<House>();
            for(int j = 0; j< NUMBER_OF_HOUSES; j++){
                houses.add(new House());
            }
            boardSides.add(new BoardSide(houses));
        }

        this.turnState = new TurnState();
        this.boardSides = boardSides;
        return;
	}


}
