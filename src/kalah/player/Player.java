package kalah.player;

import com.qualitascorpus.testsupport.IO;
import kalah.board.BoardSide;

/**
 * Created by Ben on 6/4/2017.
 */
public interface Player {

    int taketurn(IO io);
    int getId();
    void setId(int id);
    BoardSide getBoardSide();


}
