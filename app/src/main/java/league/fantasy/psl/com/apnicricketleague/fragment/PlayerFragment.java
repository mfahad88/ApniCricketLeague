package league.fantasy.psl.com.apnicricketleague.fragment;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;

import league.fantasy.psl.com.apnicricketleague.R;
import league.fantasy.psl.com.apnicricketleague.Utils.DbHelper;

/**
 * A simple {@link Fragment} subclass.
 */
public class PlayerFragment extends Fragment {
    View mView;
    ListView listView;
    DbHelper dbHelper;
    public PlayerFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        mView=inflater.inflate(R.layout.fragment_player, container, false);
        init();
        
        return mView;
    }

    private void init(){
        dbHelper=new DbHelper(mView.getContext());
        listView=mView.findViewById(R.id.list_view_players);
    }

}
