package league.fantasy.psl.com.apnicricketleague.adapter;

import android.content.Context;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import league.fantasy.psl.com.apnicricketleague.Interface.PlayerInterface;
import league.fantasy.psl.com.apnicricketleague.R;
import league.fantasy.psl.com.apnicricketleague.fragment.CreateTeamFragment;
import league.fantasy.psl.com.apnicricketleague.model.response.Player.Datum;

public class PlayerAdapter extends ArrayAdapter<List<Datum>> {
    Context context;
    int resource;
    List<Datum> list;
    int qty=0;
    int i=0;
    List<String> players=new ArrayList<>();
    PlayerInterface playerInterface;
    public PlayerAdapter(Context context, int resource, List<Datum> list, PlayerInterface playerInterface) {
        super(context, resource, Collections.singletonList(list));
        this.context=context;
        this.resource=resource;
        this.list=list;
        this.playerInterface=playerInterface;
    }

    @Override
    public List<Datum> getItem(int position) {
        return super.getItem(position);
    }

    @Override
    public int getPosition(List<Datum> item) {
        return super.getPosition(item);
    }



    @Override
    public View getView(int position,View convertView,ViewGroup parent) {
        View row=convertView;

        if(convertView==null){
            row=LayoutInflater.from(context).inflate(resource,parent,false);
        }
        final Datum datum=list.get(position);
        Log.e("PlayerAdapter",datum.toString());
        TextView txt_Name=row.findViewById(R.id.txt_Name);
        TextView txt_country=row.findViewById(R.id.txt_country);
        TextView txt_type=row.findViewById(R.id.txt_type);
        TextView txt_points=row.findViewById(R.id.txt_points);
        TextView txt_credits=row.findViewById(R.id.txt_credits);
        final Button btn_add=row.findViewById(R.id.btn_add);
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

        btn_add.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                i++;
                if(i==1){
                    playerInterface.totalPlayer("plus");
                    players.add(datum.getPlayerId().toString());
                    btn_add.setText("-");
                }if(i==2){
                    playerInterface.totalPlayer("minus");
                    if(players.contains(datum.getPlayerId().toString())) {
                        players.remove(datum.getPlayerId().toString());
                        btn_add.setText("+");
                        i=0;
                    }
                }
            }
        });
        return row;
    }

}
