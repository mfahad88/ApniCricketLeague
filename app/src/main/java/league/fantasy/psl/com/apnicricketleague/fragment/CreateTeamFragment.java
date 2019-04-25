package league.fantasy.psl.com.apnicricketleague.fragment;


import android.app.Activity;
import android.content.Context;
import android.content.SharedPreferences;
import android.content.res.ColorStateList;
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
    ViewPager pager;
    private int teamId1,teamId2;
    FragmenttoFragment fragmenttoFragment;
    int counter=0;
    int i=0;
    LinearLayout team_linear,linear_player_list;
    DbHelper dbHelper;
    TableLayout tableLayout;
    List<PlayerBean> beanList;
    int ContestId,userId;

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

                       // if(counter==11) {

                        //}
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
                    }
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
//        list_players=mView.findViewById(R.id.lv_players);
//        linear_player_list=mView.findViewById(R.id.linear_player_list);
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
