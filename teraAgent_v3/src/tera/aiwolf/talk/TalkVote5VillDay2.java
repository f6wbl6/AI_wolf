package tera.aiwolf.talk;

import static tera.aiwolf.util.Utils.*;

import java.util.List;
import java.util.stream.Collectors;

import org.aiwolf.client.lib.ComingoutContentBuilder;
import org.aiwolf.client.lib.ContentBuilder;
import org.aiwolf.client.lib.Topic;
import org.aiwolf.client.lib.VoteContentBuilder;
import org.aiwolf.common.data.Role;

import tera.aiwolf.framework.Game;
import tera.aiwolf.framework.GameAgent;
import tera.aiwolf.framework.GameTalk;
import tera.aiwolf.model.TFAFGameModel;
import tera.aiwolf.util.Utils;

public class TalkVote5VillDay2 extends TFAFTalkTactic {

    private GameAgent currentTarget;
    int coflag = 0;

    @Override
    public ContentBuilder talkImpl(int turn, int skip, int utter, TFAFGameModel model, Game game) {
        if (game.getDay() == 2) {
        	List<GameTalk> divined = game.getAllTalks().filter(t -> t.getDay() == 1 && t.getTopic() == Topic.DIVINED).collect(Collectors.toList());
    		if(divined.size()==2) {
    			if(coflag==0) {
    				coflag = 1;
        			return new ComingoutContentBuilder(game.getSelf().agent, Role.WEREWOLF);
    			}
    			else if(coflag == 1) {
    				coflag = 2;
	    			//生き残ってるエージェントで占い師COしていない人に投票する
    				List<GameAgent> candidates = game.getAliveOthers();
	    			for (GameTalk t : divined) {
	    				for(GameAgent a:candidates) {
	    					if(t.getTalker()!=a) {
	    						System.out.println("t.getTalker() : " + t.getTalker());
	    						System.out.println("a) : " + a);
	    						return new VoteContentBuilder(a.agent);
	    					}
	    				}
	    			}
    			}
    		}
        }
		else {
        List<GameTalk> divine = game.getAllTalks().filter(t -> t.getTopic() == Topic.DIVINED).collect(Collectors.toList());
        if (!divine.isEmpty()) {
            GameAgent newTarget = null;
            List<GameAgent> candidates = game.getAliveOthers();
            if (!candidates.isEmpty()) {
                double[] evilScore = model.getEvilScore();
                Utils.sortByScore(candidates, evilScore, false);
                newTarget = candidates.get(0);
                log("Day1:EvilScore高めの人を殴る");
            }

            if (newTarget != null && newTarget != currentTarget) {
                currentTarget = newTarget;
                log("Day2：新しいターゲット：" + currentTarget);
                return new VoteContentBuilder(currentTarget.agent);
            }
        }
    }
    return null;

    }

}
