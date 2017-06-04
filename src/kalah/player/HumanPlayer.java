package kalah.player;

import com.qualitascorpus.testsupport.IO;
import kalah.board.BoardSide;

/**
 * Created by Ben on 5/10/2017.
 */
public class HumanPlayer implements Player{

    //ids start at 0
    private int id;
    private BoardSide boardSide;

    public HumanPlayer(){
        super();
    }

    public HumanPlayer(int number) {
        this.id = number;
        this.boardSide = new BoardSide();
    }

    public HumanPlayer(int number, BoardSide boardSide) {
        this.id = number;
        this.boardSide = boardSide;
    }

    @Override public int taketurn(IO io) {
        return io.readInteger("Player P" + (this.id + 1) + "'s turn - " +
                                      "Specify house number or 'q' to quit: ", 1, 6, -1, "q");
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

}
