package kalah;

/**
 * Created by Ben on 5/7/2017.
 */
public class TurnState {

    private int seeds;
    private int player;
    private boolean additionalTurn;
    private int boardSide;
    private int house;
    private int gameOver;

    public TurnState() {
        super();
        this.seeds = 0;
        this.player = 0;
        this.additionalTurn = false;
        this.boardSide = 0;
        this.house = 0;
        this.gameOver = 0;
    }

    public TurnState(int seeds, int player, boolean additionalTurn, int boardSide, int house, int gameOver) {
        this.seeds = seeds;
        this.player = player;
        this.additionalTurn = additionalTurn;
        this.boardSide = boardSide;
        this.house = house;
        this.gameOver = gameOver;
    }


    public void resetTurnState() {
        this.seeds = 0;
        this.house = 0;
        this.boardSide = this.player;
        this.additionalTurn = false;
    }

    public void setPlayerToNext() {
        if (!this.additionalTurn) {
            this.player++;
            if (this.player == Kalah.NUMBER_OF_PLAYERS) {
                this.player = 0;
            }
        } else {
            this.additionalTurn = false;
        }

        this.resetTurnState();
        return;
    }

    public void incrementBoardSide(int value) {
        this.boardSide += value;
        return;
    }

    public void incrementHouse(int value) {
        this.house += value;
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

    public int getPlayer() {
        return player;
    }

    public void setPlayer(int player) {
        this.player = player;
    }

    public int getBoardSide() {
        return boardSide;
    }

    public void setBoardSide(int boardSide) {
        this.boardSide = boardSide;
    }

    public int getHouse() {
        return house;
    }

    public void setHouse(int house) {
        this.house = house;
    }

    public int getGameOver() {
        return gameOver;
    }

    public void setGameOver(int gameOver) {
        this.gameOver = gameOver;
    }
}
