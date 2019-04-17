package league.fantasy.psl.com.apnicricketleague.fragment;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;

import java.util.ArrayList;
import java.util.List;

import league.fantasy.psl.com.apnicricketleague.R;
import league.fantasy.psl.com.apnicricketleague.adapter.PrizesAdapter;
import league.fantasy.psl.com.apnicricketleague.model.PrizesBean;

/**
 * A simple {@link Fragment} subclass.
 */
public class PrizesFragment extends Fragment {
    private View mView;
    private ListView lv;
    public PrizesFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        mView= inflater.inflate(R.layout.fragment_prizes, container, false);
        lv=mView.findViewById(R.id.list_prizes);
        List<PrizesBean> list=new ArrayList<>();

        list.add(new PrizesBean(1000,"10,000","10/100",10,"Cash"));
        list.add(new PrizesBean(3000,"20,000","20/100",20,"Cash"));
        list.add(new PrizesBean(8000,"Uber 25%","50/100",100,"Points"));
        PrizesAdapter adapter=new PrizesAdapter(mView.getContext(),R.layout.prizes_list,list);
        lv.setAdapter(adapter);
        return mView;
    }

}
