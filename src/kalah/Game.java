package kalah;

import com.qualitascorpus.testsupport.IO;
import kalah.rules.RuleTriggerTime;

/**
 * Created by Ben on 6/4/2017.
 */
public interface Game {

    void play(IO io);
    boolean checkRules(RuleTriggerTime triggerTime);
}
