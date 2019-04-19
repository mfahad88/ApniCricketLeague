package league.fantasy.psl.com.apnicricketleague.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import java.util.List;

import league.fantasy.psl.com.apnicricketleague.R;
import league.fantasy.psl.com.apnicricketleague.model.response.Matches.Datum;


public class MatchesAdapter extends ArrayAdapter<Datum>{

    Context context;
    int resource;
    List<Datum> list;
    public MatchesAdapter(Context context, int resource, List<Datum> list) {
        super(context, resource, list);
        this.context=context;
        this.resource=resource;
        this.list=list;
    }

    @Override
    public Datum getItem(int position) {
        return list.get(position);
    }

    @Override
    public int getPosition(Datum item) {
        return item.getMatchId();
    }

    @Override
    public long getItemId(int position) {
        return list.get(position).getMatchId();
    }

    @Override
    public View getView(int position, View convertView,  ViewGroup parent) {
        View row=convertView;
        if(convertView==null){
            row=LayoutInflater.from(context).inflate(resource,parent,false);
        }
        Datum datum=list.get(position);
        TextView txt_team_one=row.findViewById(R.id.txt_team_one);
        TextView txt_team_two=row.findViewById(R.id.txt_team_two);
        txt_team_one.setText(datum.getTeamId1Name());
        txt_team_two.setText(datum.getTeamId2Name());
        return row;
    }
}
