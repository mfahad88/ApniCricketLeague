package league.fantasy.psl.com.apnicricketleague.adapter;

import android.content.Context;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ProgressBar;
import android.widget.TextView;


import java.util.List;

import league.fantasy.psl.com.apnicricketleague.R;
import league.fantasy.psl.com.apnicricketleague.Utils.Helper;
import league.fantasy.psl.com.apnicricketleague.model.PrizesBean;

public class PrizesAdapter extends ArrayAdapter {
    private List<PrizesBean> list;
    private int resource;
    private Context context;
    public PrizesAdapter(Context context, int resource, List<PrizesBean> list) {
        super(context, resource, list);
        this.context=context;
        this.resource=resource;
        this.list=list;
    }

    @Override
    public int getCount() {
        return list.size();
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        return init(position,convertView,parent);
    }

    @Override
    public View getDropDownView(int position, View convertView, ViewGroup parent) {
        return init(position,convertView,parent);
    }

    public View init(int position, View convertView, ViewGroup parent){
        View row=convertView;
        if(convertView==null){
            row=LayoutInflater.from(context).inflate(resource,parent,false);
        }
        PrizesBean prizes=list.get(position);
        TextView txt_points=row.findViewById(R.id.text_points);
        TextView txt_win=row.findViewById(R.id.txt_win);
        TextView txt_remaining=row.findViewById(R.id.txt_remaining);
        ProgressBar pb=row.findViewById(R.id.progress_bar_score);
        Button btn_claim=row.findViewById(R.id.btn_claim);
        pb.setProgress((int) prizes.getPercent());
        txt_points.setText("Points. "+String.valueOf(prizes.getPoints()));

        if(prizes.getType().equals("Cash")) {
            txt_win.setText("Win "+prizes.getAmount() + " Rs");
        }else{
            txt_win.setText("Win "+prizes.getAmount());
        }
        txt_remaining.setText(prizes.getQty());
        if(pb.getProgress()==100){
            btn_claim.setBackgroundColor(Color.parseColor("#FF7150"));
            btn_claim.setEnabled(true);

        }else{
            btn_claim.setBackgroundColor(Color.parseColor("#dadada"));
            btn_claim.setEnabled(false);
        }
        btn_claim.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Helper.showAlertNetural(context,"Success","Voucher Redeemed...");
            }
        });
        return row;
    }
}
