package league.fantasy.psl.com.apnicricketleague.fragment;


import android.app.Activity;
import android.content.Context;
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
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import league.fantasy.psl.com.apnicricketleague.Interface.FragmenttoFragment;
import league.fantasy.psl.com.apnicricketleague.Interface.PlayerInterface;
import league.fantasy.psl.com.apnicricketleague.R;
import league.fantasy.psl.com.apnicricketleague.Utils.DbHelper;
import league.fantasy.psl.com.apnicricketleague.adapter.PagerAdapter;
import league.fantasy.psl.com.apnicricketleague.model.game.PlayerBean;
import league.fantasy.psl.com.apnicricketleague.model.response.Player.Datum;

/**
 * A simple {@link Fragment} subclass.
 */
public class CreateTeamFragment extends Fragment {

    View mView;
    TabLayout tabLayout;
    TextView txt_player_counter,txt_team_one,txt_team_two,txt_credits;
    Button btn_done;
    RadioGroup radio_group_players;
    ViewPager pager;
    private int teamId1,teamId2;
    FragmenttoFragment fragmenttoFragment;
    int counter=0;
    int i=0;
    LinearLayout team_linear,linear_player_list;
    DbHelper dbHelper;
    TableLayout tableLayout;
    List<PlayerBean> beanList;
    public CreateTeamFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment

        mView = inflater.inflate(R.layout.fragment_create_team, container, false);
        init();
        beanList=new ArrayList<>();
        if(getArguments()!=null){
            teamId1= getArguments().getInt("TeamId1");
            teamId2=getArguments().getInt("TeamId2");
        }
        List<Datum> list=dbHelper.getPlayersById(String.valueOf(teamId1),String.valueOf(teamId2));
        final List<String> ListElementsArrayList = new ArrayList<String>();

        for(final Datum datum:list){
            TextView textView=new TextView(mView.getContext());
            textView.setBackgroundColor(Color.YELLOW);
            ViewGroup.LayoutParams lp = new LinearLayout.LayoutParams(
                    LinearLayout.LayoutParams.WRAP_CONTENT, // Width of TextView
                    LinearLayout.LayoutParams.WRAP_CONTENT); // Hei
            ((LinearLayout.LayoutParams) lp).setMarginStart(5);
            textView.setLayoutParams(lp);
            textView.setTextColor(Color.BLACK);
            textView.setText(datum.getName()+"\n"+datum.getSkill()+"\n"+datum.getPrice());
            team_linear.addView(textView);
            textView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    counter++;
                    if(counter<12) {
                        final TableRow tableRow = new TableRow(mView.getContext());
                        TableRow.LayoutParams lp = new TableRow.LayoutParams(TableRow.LayoutParams.WRAP_CONTENT, TableRow.LayoutParams.WRAP_CONTENT);
                        lp.setMargins(15, 15, 15, 15);
                        TextView tvCounter=new TextView(mView.getContext());
                        TextView tvName = new TextView(mView.getContext());
                        TextView tvSkills = new TextView(mView.getContext());
                        TextView tvPrice = new TextView(mView.getContext());
                        tvCounter.setText(String.valueOf(counter));
                        tvName.setText(datum.getName());
                        tvPrice.setText(datum.getPrice());
                        tvSkills.setText(datum.getSkill());
                        tvName.setLayoutParams(lp);
                        tvPrice.setLayoutParams(lp);
                        tvSkills.setLayoutParams(lp);
                        tableRow.addView(tvCounter);
                        tableRow.addView(tvName);
                        tableRow.addView(tvSkills);
                        tableRow.addView(tvPrice);
                        beanList.add(new PlayerBean(datum.getName(),datum.getPrice(),datum.getSkill()));
                        tableLayout.addView(tableRow, new TableLayout.LayoutParams(TableLayout.LayoutParams.MATCH_PARENT, TableLayout.LayoutParams.WRAP_CONTENT));
                        tableRow.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                tableLayout.removeView(tableRow);
                                counter--;
                            }
                        });
                    }
                    if(counter>11){
                        btn_done.setEnabled(true);
                        btn_done.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                for(PlayerBean bean:beanList) {
                                    Log.e("beanList", bean.toString());
                                }
                            }
                        });
                    }
                }

            });
        }




        /*fragmenttoFragment=new FragmenttoFragment() {
            @Override
            public void passvalue(String str) {

                if(str.equals("plus")){
                    if(counter<11) {
                        counter++;
                        txt_player_counter.setText(String.valueOf(counter));
                    }


                }if(str.equals("minus")){
                    if(counter>0) {
                        counter--;
                        txt_player_counter.setText(String.valueOf(counter));
                    }
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
        });*/
        return mView;
    }

    private void init(){
        tabLayout=mView.findViewById(R.id.tab_layout);
        txt_player_counter=mView.findViewById(R.id.txt_player_counter);
        txt_team_one=mView.findViewById(R.id.txt_team_one);
        txt_team_two=mView.findViewById(R.id.txt_team_two);
        txt_credits=mView.findViewById(R.id.txt_credits);
        radio_group_players=mView.findViewById(R.id.radio_group_players);
        team_linear=mView.findViewById(R.id.team_linear);
        dbHelper=new DbHelper(getActivity());
//        list_players=mView.findViewById(R.id.lv_players);
        linear_player_list=mView.findViewById(R.id.linear_player_list);
        tableLayout=mView.findViewById(R.id.tableLayout);
        btn_done=mView.findViewById(R.id.btn_done);
        /*pager=mView.findViewById(R.id.pager);
        btn_done=mView.findViewById(R.id.btn_done);*/
    }

   /* @Override
    p""}ublic void onAttach(Context context) {
        try {
            fragmenttoFragment = (FragmenttoFragment) context;
        }catch (ClassCastException e){
            e.printStackTrace();
        }
        super.onAttach(context);
    }*/
}
