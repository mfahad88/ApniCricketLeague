package league.fantasy.psl.com.apnicricketleague.fragment;


import android.app.Activity;
import android.content.res.ColorStateList;
import android.graphics.Color;
import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;

import android.support.v4.view.ViewPager;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

import league.fantasy.psl.com.apnicricketleague.Interface.FragmenttoFragment;
import league.fantasy.psl.com.apnicricketleague.Interface.PlayerInterface;
import league.fantasy.psl.com.apnicricketleague.R;
import league.fantasy.psl.com.apnicricketleague.adapter.PagerAdapter;

/**
 * A simple {@link Fragment} subclass.
 */
public class CreateTeamFragment extends Fragment {

    View mView;
    TabLayout tabLayout;
    TextView txt_player_counter,txt_team_one,txt_team_two,txt_credits;
    RadioGroup radio_group_players;
    ViewPager pager;
    private int teamId1,teamId2;
    FragmenttoFragment fragmenttoFragment;
    int counter=0;
    int i=0;
    public CreateTeamFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment

        mView = inflater.inflate(R.layout.fragment_create_team, container, false);
        init();
        if(getArguments()!=null){
            teamId1= getArguments().getInt("TeamId1");
            teamId2=getArguments().getInt("TeamId2");
        }


        fragmenttoFragment=new FragmenttoFragment() {
            @Override
            public void passvalue(String str) {
               // Toast.makeText(mView.getContext(), str, Toast.LENGTH_SHORT).show();
                if(str.equals("plus")){
                    counter++;
                    txt_player_counter.setText(String.valueOf(counter));
                   /* if(counter>0 && counter<12) {
                        for(int i=0;i<counter;i++) {
                            ((RadioButton) radio_group_players.getChildAt(i)).setChecked(true);
                        }
                    }*/

                }if(str.equals("minus")){
                    counter--;
                    txt_player_counter.setText(String.valueOf(counter));
                }
            }
        };
        tabLayout.addTab(tabLayout.newTab().setText("WK"));
        tabLayout.addTab(tabLayout.newTab().setText("BAT"));
        tabLayout.addTab(tabLayout.newTab().setText("AR"));
        tabLayout.addTab(tabLayout.newTab().setText("BOWL"));
        tabLayout.setTabTextColors(ColorStateList.valueOf(Color.parseColor("#000000")));
        PagerAdapter pagerAdapter=new PagerAdapter(getFragmentManager(),tabLayout.getTabCount(),teamId1,teamId2,fragmenttoFragment);
        pager.setAdapter(pagerAdapter);
        pager.addOnPageChangeListener(new TabLayout.TabLayoutOnPageChangeListener(tabLayout));
        tabLayout.setOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {
                pager.setCurrentItem(tab.getPosition());
            }

            @Override
            public void onTabUnselected(TabLayout.Tab tab) {

            }

            @Override
            public void onTabReselected(TabLayout.Tab tab) {

            }
        });
        return mView;
    }

    private void init(){
        tabLayout=mView.findViewById(R.id.tab_layout);
        txt_player_counter=mView.findViewById(R.id.txt_player_counter);
        txt_team_one=mView.findViewById(R.id.txt_team_one);
        txt_team_two=mView.findViewById(R.id.txt_team_two);
        txt_credits=mView.findViewById(R.id.txt_credits);
        radio_group_players=mView.findViewById(R.id.radio_group_players);
        pager=mView.findViewById(R.id.pager);
    }

    @Override
    public void onAttach(Activity activity) {
        try {
            fragmenttoFragment = (FragmenttoFragment) activity;
        }catch (ClassCastException e){
            e.printStackTrace();
        }
        super.onAttach(activity);
    }
}
