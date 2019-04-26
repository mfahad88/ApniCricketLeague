package league.fantasy.psl.com.apnicricketleague.fragment;

import android.app.Activity;
import android.content.Context;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;

import android.support.v4.view.ViewPager;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.PopupMenu;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.RelativeLayout;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.TextView;
import android.widget.Toast;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import league.fantasy.psl.com.apnicricketleague.Interface.FragmenttoFragment;
import league.fantasy.psl.com.apnicricketleague.Interface.PlayerInterface;
import league.fantasy.psl.com.apnicricketleague.R;
import league.fantasy.psl.com.apnicricketleague.Utils.DbHelper;
import league.fantasy.psl.com.apnicricketleague.Utils.Helper;
import league.fantasy.psl.com.apnicricketleague.adapter.PagerAdapter;
import league.fantasy.psl.com.apnicricketleague.client.ApiClient;
import league.fantasy.psl.com.apnicricketleague.model.game.PlayerBean;
import league.fantasy.psl.com.apnicricketleague.model.response.JoinContest.JoinContenstResponse;
import league.fantasy.psl.com.apnicricketleague.model.response.Player.Datum;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * A simple {@link Fragment} subclass.
 */
public class CreateTeamFragment extends Fragment {

    View mView;
    TabLayout tabLayout;
    TextView txt_player_counter,txt_team_one,txt_team_two,txt_credits;

    Button btn_done;
    RadioGroup radio_group_players;
    private int teamId1,teamId2;
    int counter=0;
    int i=0;
    LinearLayout team_linear;
    DbHelper dbHelper;
    TableLayout tableLayout;
    List<PlayerBean> beanList;
    int ContestId,userId;
    int bats_counter=0;
    int bowl_counter=0;
    int alrounder_counter=0;
    int wkt_counter=0;
    RelativeLayout relativeWkt;

    RelativeLayout relativeBats,relativeBats1,relativeBats2,relativeBats3,relativeBats4,relativeBats5;
    RelativeLayout relativeBowl,relativeBowl1,relativeBowl2,relativeBowl3,relativeBowl4,relativeBowl5;
    RelativeLayout relativeAlrounder,relativeAlrounder1,relativeAlrounder2,relativeAlrounder3,relativeAlrounder4,relativeAlrounder5;

    TextView txt_view_wk;
    TextView txt_batsman1,txt_batsman2,txt_batsman3,txt_batsman4,txt_batsman5;
    TextView txt_bowl1,txt_bowl2,txt_bowl3,txt_bowl4,txt_bowl5;
    TextView txt_alrounder1,txt_alrounder2,txt_alrounder3;
    private SharedPreferences preferences;
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
            ContestId=getArguments().getInt("ContestId");
        }
        try {
            JSONObject jsonObject=new JSONObject(Helper.getUserSession(preferences,"MyUser").toString());
            userId= jsonObject.getInt("user_id");
        } catch (JSONException e) {
            e.printStackTrace();
        }

        List<Datum> list=dbHelper.getPlayersById(String.valueOf(teamId1),String.valueOf(teamId2));

        for(final Datum datum:list){
            TextView textView=new TextView(mView.getContext());
            textView.setBackgroundColor(Color.YELLOW);
            ViewGroup.LayoutParams lp = new LinearLayout.LayoutParams(
                    LinearLayout.LayoutParams.WRAP_CONTENT, // Width of TextView
                    LinearLayout.LayoutParams.WRAP_CONTENT); // Hei
            ((LinearLayout.LayoutParams) lp).setMarginStart(5);
            textView.setLayoutParams(lp);
            textView.setTextColor(Color.BLACK);
            textView.setText(datum.getName()+"\n "+datum.getSkill());
            team_linear.addView(textView);
            textView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    counter++;
                    Log.e("Skills",datum.getSkill());
                    if(counter<12) {
                        if (datum.getSkill().equals("0")) {
                            bats_counter++;
                        }
                        if (datum.getSkill().equals("1")) {
                            bowl_counter++;
                        }
                        if (datum.getSkill().equals("2")) {
                            alrounder_counter++;
                        }
                        if (datum.getSkill().equals("3")) {
                            wkt_counter++;
                        }

                        if(wkt_counter==1){
                            relativeWkt.setVisibility(View.VISIBLE);
                            txt_view_wk.setText(datum.getName());
                        }

                        if(bats_counter==1){
//                            relativeBats.setVisibility(View.VISIBLE);
                            relativeBats1.setVisibility(View.VISIBLE);
                            Log.e("CreateTeam",datum.getName());
                            txt_batsman1.setText(datum.getName());
                        }if(bats_counter==2){
                            relativeBats2.setVisibility(View.VISIBLE);
                            txt_batsman2.setText(datum.getName());
                        }if(bats_counter==3){
                            relativeBats3.setVisibility(View.VISIBLE);
                            txt_batsman3.setText(datum.getName());
                        }if(bats_counter==4){
                            relativeBats4.setVisibility(View.VISIBLE);
                            txt_batsman4.setText(datum.getName());
                        }if(bats_counter==5){
                            relativeBats5.setVisibility(View.VISIBLE);
                            txt_batsman5.setText(datum.getName());
                        }

                        if(bowl_counter==1){
//                            relativeBowl.setVisibility(View.VISIBLE);
                            relativeBowl1.setVisibility(View.VISIBLE);
                            txt_bowl1.setText(datum.getName());
                        }if(bowl_counter==2){
                            relativeBowl2.setVisibility(View.VISIBLE);
                            txt_bowl2.setText(datum.getName());
                        }if(bowl_counter==3){
                            relativeBowl3.setVisibility(View.VISIBLE);
                            txt_bowl3.setText(datum.getName());
                        }if(bowl_counter==4){
                            relativeBowl4.setVisibility(View.VISIBLE);
                            txt_bowl4.setText(datum.getName());
                        }if(bowl_counter==5){
                            relativeBowl5.setVisibility(View.VISIBLE);
                            txt_bowl5.setText(datum.getName());
                        }

                        if(alrounder_counter==1){
                            relativeAlrounder.setVisibility(View.VISIBLE);
                            relativeAlrounder1.setVisibility(View.VISIBLE);
                            txt_alrounder1.setText(datum.getName());
                        }if(bowl_counter==2){
                            relativeAlrounder2.setVisibility(View.VISIBLE);
                            txt_alrounder2.setText(datum.getName());
                        }if(bowl_counter==3){
                            relativeAlrounder3.setVisibility(View.VISIBLE);
                            txt_alrounder3.setText(datum.getName());
                        }

                    }

                    /*counter++;
                    if(counter<12) {
                        final PlayerBean bean=new PlayerBean();
                        final TableRow tableRow = new TableRow(mView.getContext());
                        TableRow.LayoutParams lp = new TableRow.LayoutParams(TableRow.LayoutParams.MATCH_PARENT, TableRow.LayoutParams.WRAP_CONTENT);
                        lp.setMargins(25, 0, 25, 0);
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
                        bean.setId(datum.getPlayerId());
                        bean.setName(datum.getName());

                        if(counter==1){
                            bean.setIsCaptain(1);
                            bean.setIsWCaption(1);
                        }else{
                            bean.setIsCaptain(0);
                            bean.setIsWCaption(0);
                        }
                        bean.setPrice(datum.getPrice());
                        bean.setSkills(datum.getSkill());
                        tableLayout.addView(tableRow, new TableLayout.LayoutParams(TableLayout.LayoutParams.MATCH_PARENT, TableLayout.LayoutParams.WRAP_CONTENT));
                        tableRow.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                --counter;
                                tableLayout.removeView(tableRow);
                                beanList.remove(bean);

                            }
                        });


                        beanList.add(bean);
                    }
                    if(counter==11){
                        btn_done.setEnabled(true);
                        btn_done.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                 JSONArray jsonArray = new JSONArray();
                                for(PlayerBean bean:beanList) {

                                    JSONArray array = new JSONArray();
                                    array.put(bean.getId());
                                    array.put(bean.getIsCaptain());
                                    array.put(bean.getIsWCaption());
                                    jsonArray.put(array);
                                }
                                Log.e("beanList",jsonArray.toString());

                                JSONObject object=new JSONObject();
                                try {
                                    object.put("user_id",userId);
                                    object.put("contest_id",ContestId);
                                    object.put("name","Android");
                                    object.put("method_Name",this.getClass().getSimpleName()+".btn_done.onClick");
                                    object.put("playersInfo",jsonArray);
                                    object.put("coins",0);
                                    object.put("rem_budget",0);
                                } catch (JSONException e) {
                                    e.printStackTrace();
                                }
                                ApiClient.getInstance().JoinContest(Helper.encrypt(object.toString()))
                                        .enqueue(new Callback<JoinContenstResponse>() {
                                            @Override
                                            public void onResponse(Call<JoinContenstResponse> call, Response<JoinContenstResponse> response) {
                                                if(response.isSuccessful()){
                                                    Helper.showAlertNetural(mView.getContext(),"Success",response.body().getMessage());
                                                }else{
                                                    try {
                                                        Helper.showAlertNetural(mView.getContext(),"Error",response.errorBody().string());
                                                    } catch (IOException e) {
                                                        e.printStackTrace();
                                                    }
                                                }

                                            }

                                            @Override
                                            public void onFailure(Call<JoinContenstResponse> call, Throwable t) {
                                                t.fillInStackTrace();
                                                Helper.showAlertNetural(mView.getContext(),"Error",t.getMessage());
                                            }
                                        });
                            }
                        });
                    }*/
                }

            });
        }




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
        preferences=mView.getContext().getSharedPreferences(Helper.SHARED_PREF,Context.MODE_PRIVATE);
        btn_done=mView.findViewById(R.id.btn_done);

        relativeWkt=mView.findViewById(R.id.relative_Wicket);
        txt_view_wk=mView.findViewById(R.id.textNameWicket);

        relativeBats=mView.findViewById(R.id.relative_Batsman);
        relativeBats1=mView.findViewById(R.id.relative_Batsman1);
        relativeBats2=mView.findViewById(R.id.relative_Batsman2);
        relativeBats3=mView.findViewById(R.id.relative_Batsman3);
        relativeBats4=mView.findViewById(R.id.relative_Batsman4);
        relativeBats5=mView.findViewById(R.id.relative_Batsman5);

        relativeBowl=mView.findViewById(R.id.relative_Bowler);
        relativeBowl1=mView.findViewById(R.id.relative_Bowler1);
        relativeBowl2=mView.findViewById(R.id.relative_Bowler2);
        relativeBowl3=mView.findViewById(R.id.relative_Bowler3);
        relativeBowl4=mView.findViewById(R.id.relative_Bowler4);
        relativeBowl5=mView.findViewById(R.id.relative_Bowler5);

        relativeAlrounder=mView.findViewById(R.id.relative_Alrounder);
        relativeAlrounder1=mView.findViewById(R.id.relative_Alrounder1);
        relativeAlrounder2=mView.findViewById(R.id.relative_Alrounder2);
        relativeAlrounder3=mView.findViewById(R.id.relative_Alrounder3);

    }

}
