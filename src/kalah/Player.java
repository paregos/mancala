package kalah;

import kalah.board.BoardSide;

/**
 * Created by Ben on 5/10/2017.
 */
public class Player {

    //ids start at 0
    private int id;
    private BoardSide boardSide;

    public Player(){
        super();
    }

    public Player(int number) {
        this.id = number;
        this.boardSide = new BoardSide();
    }

    public Player(int number, BoardSide boardSide) {
        this.id = number;
        this.boardSide = boardSide;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public BoardSide getBoardSide() {
        return boardSide;
    }

    public void setBoardSide(BoardSide boardSide) {
        this.boardSide = boardSide;
    }
}
