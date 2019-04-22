package league.fantasy.psl.com.apnicricketleague.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import java.util.Collections;
import java.util.List;

import league.fantasy.psl.com.apnicricketleague.R;
import league.fantasy.psl.com.apnicricketleague.model.response.Player.Datum;

public class PlayerAdapter extends ArrayAdapter<List<Datum>> {
    Context context;
    int resource;
    List<Datum> list;
    public PlayerAdapter(Context context, int resource, List<Datum> list) {
        super(context, resource, Collections.singletonList(list));
        this.context=context;
        this.resource=resource;
        this.list=list;
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
    public long getItemId(int position) {
        return super.getItemId(position);
    }

    @Override
    public View getView(int position,View convertView,ViewGroup parent) {
        View row=convertView;
        if(convertView==null){
            row=LayoutInflater.from(context).inflate(resource,parent,false);
        }
        Datum datum=list.get(position);
        TextView txt_Name=row.findViewById(R.id.txt_Name);
        TextView txt_country=row.findViewById(R.id.txt_country);
        TextView txt_type=row.findViewById(R.id.txt_type);
        TextView txt_points=row.findViewById(R.id.txt_points);
        TextView txt_credits=row.findViewById(R.id.txt_credits);
        txt_Name.setText(datum.getName());
        txt_country.setText(datum.getTeamName());
        txt_type.setText(datum.getSkill());
        txt_points.setText(datum.getPrice());
        txt_credits.setText(datum.getPrice());
        return row;
    }
}
