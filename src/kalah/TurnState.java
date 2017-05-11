package kalah;

import java.util.ArrayList;

/**
 * Created by Ben on 5/7/2017.
 */
public class TurnState {

    private int seeds;
    private Player player;
    private boolean additionalTurn;
    private BoardSide boardSide;
    private int houseNumber;
    private int gameOver;

    public TurnState() {
        super();
        this.seeds = 0;
        this.player = null;
        this.additionalTurn = false;
        this.boardSide = null;
        this.houseNumber = 0;
        this.gameOver = 0;
    }

    //Contructor that is used at the beggining of a new game
    public TurnState(Player player, BoardSide boardSide, int houseNumber) {
        this.seeds = 0;
        this.player = player;
        this.additionalTurn = false;
        this.boardSide = boardSide;
        this.houseNumber = houseNumber;
        this.gameOver = 0;
    }


    public void resetTurnState() {
        this.seeds = 0;
        this.houseNumber = 0;
        this.additionalTurn = false;
        this.boardSide = this.player.getBoardSide();
        return;
    }

    public void setPlayerToNext(ArrayList<Player> players) {
        if (!this.additionalTurn) {
            int current = this.player.getNumber();
            this.player = current == players.size()-1 ? players.get(0) : players.get(current + 1);
        }
        this.resetTurnState();
        return;
    }

    //If the last board side has ended return to the first houseNumber of the first board side
    public void incrementBoardSide(ArrayList<Player> players){
        int current = this.boardSide.getNumber();
        this.boardSide = current >= players.size()-1 ?
                players.get(0).getBoardSide() : players.get(current+1).getBoardSide();
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

    public Player getPlayer() {
        return player;
    }

    public void setPlayer(Player player) {
        this.player = player;
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
