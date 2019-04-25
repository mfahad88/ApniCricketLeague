package league.fantasy.psl.com.apnicricketleague.adapter;

import android.content.Context;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.PopupMenu;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import league.fantasy.psl.com.apnicricketleague.Interface.PlayerInterface;
import league.fantasy.psl.com.apnicricketleague.R;
import league.fantasy.psl.com.apnicricketleague.fragment.CreateTeamFragment;
import league.fantasy.psl.com.apnicricketleague.model.game.PlayerBean;
import league.fantasy.psl.com.apnicricketleague.model.response.Player.Datum;

public class PlayerAdapter extends ArrayAdapter<Datum>{
    Context context;
    int resource;
    List<Datum> list;
    List<PlayerBean> players=new ArrayList<>();
    PlayerInterface playerInterface;
    int counter=0;
    JSONObject jsonObject=new JSONObject("Players");
    SharedPreferences preferences;
    public PlayerAdapter(Context context, int resource, List<Datum> list, PlayerInterface playerInterface) throws JSONException {
        super(context, resource, list);
        this.context=context;
        this.resource=resource;
        this.list=list;
        this.playerInterface=playerInterface;
    }



    @Override
    public int getCount() {
        return super.getCount();
    }

    @Override
    public Datum getItem(int position) {
        return list.get(position);
    }

    @Override
    public int getPosition(Datum item) {
        return super.getPosition(item);
    }

    @Override
    public long getItemId(int position) {
        return super.getItemId(position);
    }


    @Override
    public View getView(final int position, View convertView, final ViewGroup parent) {
        View row=convertView;
        final boolean[] isPlus = {true};
        if(convertView==null){
            row=LayoutInflater.from(context).inflate(resource,parent,false);
        }
        final Datum datum=list.get(position);
        final TextView txt_Name=row.findViewById(R.id.txt_Name);
        TextView txt_country=row.findViewById(R.id.txt_country);
        TextView txt_type=row.findViewById(R.id.txt_type);
        TextView txt_points=row.findViewById(R.id.txt_points);
        TextView txt_credits=row.findViewById(R.id.txt_credits);
        final TextView txt_captain=row.findViewById(R.id.txt_captain);
        final TextView txt_vcaptain=row.findViewById(R.id.txt_vcaptain);
        final RelativeLayout relativeLayout=row.findViewById(R.id.relativeLayout);
        txt_Name.setText(datum.getName());
        txt_country.setText(datum.getTeamName());
        if(datum.getSkill().equals("3")){
            txt_type.setText("WK");
        }if(datum.getSkill().equals("0")){
            txt_type.setText("BAT");
        }if(datum.getSkill().equals("2")){
            txt_type.setText("ALL");
        }if(datum.getSkill().equals("1")){
            txt_type.setText("BOWL");
        }
        txt_points.setText(datum.getPrice());
        txt_credits.setText(datum.getPrice());
        final View finalRow = row;
        relativeLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if(isPlus[0]){
                    try {
                        finalRow.setBackgroundColor(Color.YELLOW);
                        playerInterface.totalPlayer("plus");
                        jsonObject.put("Player_Id",datum.getPlayerId());
                        isPlus[0] = false;
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                }else{
                    try{
                        finalRow.setBackgroundColor(Color.WHITE);
                        playerInterface.totalPlayer("minus");
                        jsonObject.put("Player_Id",datum.getPlayerId());
                        isPlus[0] = true;
                    }catch (JSONException e){
                        e.printStackTrace();
                    }
                }
            }
        });
        relativeLayout.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View v) {
                PopupMenu popupMenu=new PopupMenu(context,finalRow);
                popupMenu.inflate(R.menu.popup_captain);
                popupMenu.setOnMenuItemClickListener(new PopupMenu.OnMenuItemClickListener() {
                    @Override
                    public boolean onMenuItemClick(MenuItem item) {
                        if(item.getItemId()==R.id.captain){

                            try {
                                txt_captain.setVisibility(View.VISIBLE);
                                jsonObject.remove(datum.getPlayerId().toString());
                                jsonObject.put("Player_Id",datum.getPlayerId());
                                jsonObject.put("Captain",1);
                            } catch (JSONException e) {
                                e.printStackTrace();
                            }
                        }
                        if(item.getItemId()==R.id.vcaptain){
                            try {
                                txt_vcaptain.setVisibility(View.VISIBLE);
                                jsonObject.remove(datum.getPlayerId().toString());
                                jsonObject.put("Player_Id",datum.getPlayerId());
                                jsonObject.put("WCaptain",1);
                            }catch (JSONException e){
                                e.printStackTrace();
                            }
                        }
                        return false;
                    }
                });
                popupMenu.show();
                return false;
            }
        });
        return row;
    }



}
