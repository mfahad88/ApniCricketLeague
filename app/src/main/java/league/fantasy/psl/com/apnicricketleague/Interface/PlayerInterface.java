package league.fantasy.psl.com.apnicricketleague.Interface;

import java.util.List;

import league.fantasy.psl.com.apnicricketleague.model.game.PlayerBean;

public interface PlayerInterface {

    void totalPlayer(String operator);

    void totalPlayersList(List<PlayerBean> list);

}
