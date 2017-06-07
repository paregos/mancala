package kalah;

import kalah.board.BoardDirection;
import kalah.board.BoardSide;
import kalah.player.Player;

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
    private BoardDirection boardDirection;

    public TurnState() {
        super();
        this.additionalTurn = false;
        this.boardSide = null;
        this.currentPlayer = null;
        this.gameOver = 0;
        this.houseNumber = 0;
        this.seeds = 0;
        this.boardDirection = null;
    }

    //Contructor that is used at the beggining of a new game
    public TurnState(Player player, BoardSide boardSide, int houseNumber, BoardDirection boardDirection) {
        this.additionalTurn = false;
        this.boardSide = boardSide;
        this.currentPlayer = player;
        this.gameOver = 0;
        this.houseNumber = houseNumber;
        this.seeds = 0;
        this.boardDirection = boardDirection;
    }

    //If the last board side has ended return to the first houseNumber of the first board side

    /**
     * Increments the board side in a way that mimics the order of players turns. This method is called when seeds
     * are being placed around the board. When the boardside reaches the side corresponding to the last player
     * (player 2 in a game of 2 players) and this method is called the boardSide will be set to 0, essentially
     * wrapping around to the beelining of the total board.
     *
     * @param players       The list of players contained within Kalah.
     */
    public void incrementBoardSide(ArrayList<Player> players){
        this.boardSide = this.boardDirection.incrementBoardSide(players, this.boardSide);
        return;
    }

    /**
     * Called directly after setPlayerToNext this method resets the values contained within this turnState instance to
     * that of a new turn. Seeds and house numbers are cleared and the boardSide is set to the newly assigned player.
     */
    public void resetTurnState() {
        this.additionalTurn = false;
        this.boardSide = this.currentPlayer.getBoardSide();
        this.houseNumber = 0;
        this.seeds = 0;
        return;
    }

    /**
     * Sets the active player to be the next player in an incremental order (e.g player 1 -> player 2)
     *
     * @param players   The list of players contained within Kalah.
     */
    public void setPlayerToNext(ArrayList<Player> players) {
        if (!this.additionalTurn) {
            int current = this.currentPlayer.getId();
            this.currentPlayer = current == players.size()-1 ? players.get(0) : players.get(current + 1);
        }
        this.resetTurnState();
        return;
    }

    public boolean isAtStore(){
       return this.boardDirection.isAtStore(this.houseNumber, this.boardSide.getHouses().size());
    }

    public void incrementHouse(int value) {
        this.houseNumber = this.boardDirection.changeHouse(this.houseNumber, value);
        return;
    }

    public void decrementSeeds(int value) {
        this.seeds -= value;
        return;
    }

    public boolean isOnPlayersBoardSide(){
        return this.currentPlayer.getId() == this.getBoardSide().getId();
    }

    public void incrementStoreSeeds(int value){
        this.currentPlayer.getBoardSide().getStore().incrementSeeds(value);
    }

    public void incrementHouseSeeds(int value){
        this.boardSide.getHouse(this.houseNumber).incrementSeeds(value);
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
