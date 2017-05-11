package kalah;

import java.util.ArrayList;

/**
 * Created by Ben on 5/7/2017.
 */
public class TurnState {

    private boolean additionalTurn;
    private BoardSide boardSide;
    private Player currentPlayer;
    private int gameOver;
    private int houseNumber;
    private int seeds;

    public TurnState() {
        super();
        this.additionalTurn = false;
        this.boardSide = null;
        this.currentPlayer = null;
        this.gameOver = 0;
        this.houseNumber = 0;
        this.seeds = 0;
    }

    //Contructor that is used at the beggining of a new game
    public TurnState(Player player, BoardSide boardSide, int houseNumber) {
        this.additionalTurn = false;
        this.boardSide = boardSide;
        this.currentPlayer = player;
        this.gameOver = 0;
        this.houseNumber = houseNumber;
        this.seeds = 0;
    }


    //If the last board side has ended return to the first houseNumber of the first board side
    public void incrementBoardSide(ArrayList<Player> players){
        int current = this.boardSide.getId();
        this.boardSide = current >= players.size()-1 ?
                players.get(0).getBoardSide() : players.get(current+1).getBoardSide();
        return;
    }

    public void resetTurnState() {
        this.additionalTurn = false;
        this.boardSide = this.currentPlayer.getBoardSide();
        this.houseNumber = 0;
        this.seeds = 0;
        return;
    }

    public void setPlayerToNext(ArrayList<Player> players) {
        if (!this.additionalTurn) {
            int current = this.currentPlayer.getId();
            this.currentPlayer = current == players.size()-1 ? players.get(0) : players.get(current + 1);
        }
        this.resetTurnState();
        return;
    }

    public void incrementHouse(int value) {
        this.houseNumber += value;
        return;
    }

    public void decrementSeeds(int value) {
        this.seeds -= value;
        return;
    }

    public boolean isAdditionalTurn() {
        return additionalTurn;
    }

    public void setAdditionalTurn(boolean additionalTurn) {
        this.additionalTurn = additionalTurn;
    }

    public int getSeeds() {
        return seeds;
    }

    public void setSeeds(int seeds) {
        this.seeds = seeds;
    }

    public Player getCurrentPlayer() {
        return currentPlayer;
    }

    public void setCurrentPlayer(Player currentPlayer) {
        this.currentPlayer = currentPlayer;
    }

    public BoardSide getBoardSide() {
        return boardSide;
    }

    public void setBoardSide(BoardSide boardSide) {
        this.boardSide = boardSide;
    }

    public int getHouseNumber() {
        return houseNumber;
    }

    public void setHouseNumber(int houseNumber) {
        this.houseNumber = houseNumber;
    }

    public int getGameOver() {
        return gameOver;
    }

    public void setGameOver(int gameOver) {
        this.gameOver = gameOver;
    }
}
