package kalah;

/**
 * Created by Ben on 5/10/2017.
 */
public class Player {

    //starts at 0
    private int number;
    private BoardSide boardSide;

    public Player(){
        super();
    }

    public Player(int number) {
        this.number = number;
        this.boardSide = new BoardSide();
    }

    public Player(int number, BoardSide boardSide) {
        this.number = number;
        this.boardSide = boardSide;
    }

    public int getNumber() {
        return number;
    }

    public void setNumber(int number) {
        this.number = number;
    }

    public BoardSide getBoardSide() {
        return boardSide;
    }

    public void setBoardSide(BoardSide boardSide) {
        this.boardSide = boardSide;
    }
}
