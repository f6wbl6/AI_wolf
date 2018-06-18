package tera.aiwolf.talk;

import static tera.aiwolf.util.Utils.*;

import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

import org.aiwolf.client.lib.ContentBuilder;
import org.aiwolf.client.lib.Topic;
import org.aiwolf.client.lib.VoteContentBuilder;

import tera.aiwolf.framework.Game;
import tera.aiwolf.framework.GameAgent;
import tera.aiwolf.framework.GameTalk;
import tera.aiwolf.metagame.TFAFMetagameModel;
import tera.aiwolf.model.TFAFGameModel;
import tera.aiwolf.util.Utils;

public class TalkVote5VillDay1 extends TFAFTalkTactic {

    private GameAgent currentTarget;

    @Override
    public ContentBuilder talkImpl(int turn, int skip, int utter, TFAFGameModel model, Game game) {
        // 占い結果後に発言
        if (game.getDay() == 1) {
            if (currentTarget == null) {
                // 初期、最も強い人に投票宣言
                List<GameAgent> agents = game.getAliveOthers();
                double[] winCount = ((TFAFMetagameModel) game.getMeta()).winCountModel.getWinCount(); // 勝率を計算
                currentTarget = Collections.max(agents, Comparator.comparing(x -> winCount[x.getIndex()])); // 最高勝率のエージェント選択
//                log("Day1:初期殴り先：" + currentTarget);
//                return new VoteContentBuilder(currentTarget.agent);
                return null; // 初日に占いが始まるまでは発言しない
            } else {
                // 占い結果が判ったら、それに合わせて投票先を変える
                List<GameTalk> divine = game.getAllTalks()
                    .filter(t -> t.getDay() == game.getDay() && t.getTopic() == Topic.DIVINED)
                    .collect(Collectors.toList());

                // 村人は占い師が黒出しした対象に投票するべき？
                // 現状は「占いの結果を聞くけど投票先として参考にはしない」というロジック
                if (!divine.isEmpty()) {
                    GameAgent newTarget = null;
                    List<GameAgent> candidates = game.getAliveOthers();
                    if (!candidates.isEmpty()) {
                        double[] evilScore = model.getEvilScore();
                        Utils.sortByScore(candidates, evilScore, false);
                        newTarget = candidates.get(0);
                        log("Day1:EvilScore高めの人を殴る");
                    }

                    if (newTarget != null ) {//&& newTarget != currentTarget) { //初日に必ず投票先の発言をする
                        currentTarget = newTarget;
                        log("Day1:新しいターゲット：" + currentTarget);
                        return new VoteContentBuilder(currentTarget.agent);
                    }
                }
            }
        }
        return null;

    }

}
