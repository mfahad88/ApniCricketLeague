package league.fantasy.psl.com.apnicricketleague.fragment;


import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.Toast;

import java.util.List;

import league.fantasy.psl.com.apnicricketleague.Interface.FragmenttoFragment;
import league.fantasy.psl.com.apnicricketleague.Interface.PlayerInterface;
import league.fantasy.psl.com.apnicricketleague.R;
import league.fantasy.psl.com.apnicricketleague.Utils.DbHelper;
import league.fantasy.psl.com.apnicricketleague.adapter.PlayerAdapter;
import league.fantasy.psl.com.apnicricketleague.model.response.Player.Datum;

/**
 * A simple {@link Fragment} subclass.
 */
@SuppressLint("ValidFragment")
public class PlayerFragment extends Fragment {
    View mView;
    ListView listView;
    DbHelper dbHelper;
    int teamId1,teamId2;
    int player_type;
    PlayerAdapter adapter;
    PlayerInterface playerInterface;
    FragmenttoFragment fragmenttoFragment;
    public PlayerFragment(FragmenttoFragment fragmenttoFragment) {
        // Required empty public constructor
        this.fragmenttoFragment=fragmenttoFragment;
    }



    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        mView=inflater.inflate(R.layout.fragment_player, container, false);
        init();

        if(getArguments()!=null){
            teamId1=getArguments().getInt("teamId1");
            teamId2=getArguments().getInt("teamId2");
            player_type=getArguments().getInt("player_type");
        }
        playerInterface=new PlayerInterface() {
            @Override
            public void totalPlayer(String operator) {
                //Toast.makeText(mView.getContext(), operator, Toast.LENGTH_SHORT).show();
                fragmenttoFragment.passvalue(operator);
            }
        };
        List<Datum> list=dbHelper.getPlayersById(String.valueOf(teamId1),String.valueOf(teamId2),String.valueOf(player_type));
        if(list.size()>0) {
            adapter = new PlayerAdapter(mView.getContext(), R.layout.player_info_list, list,playerInterface);
            listView.setAdapter(adapter);
        }
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Toast.makeText(getActivity(), adapter.getItem(position).get(position).toString(), Toast.LENGTH_SHORT).show();
            }
        });

        return mView;
    }

    private void init(){
        dbHelper=new DbHelper(getActivity());
        listView=mView.findViewById(R.id.list_view_players);
    }

    @Override
    public void onAttach(Activity activity) {
        try {
            playerInterface = (PlayerInterface) activity;
        }catch (ClassCastException e){
            e.printStackTrace();
        }
        super.onAttach(activity);
    }


}
