package league.fantasy.psl.com.apnicricketleague.adapter;

import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.RecyclerView;
import android.text.Spannable;
import android.text.Spanned;
import android.text.style.StrikethroughSpan;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.TextView;

import java.util.Collections;
import java.util.List;

import league.fantasy.psl.com.apnicricketleague.R;
import league.fantasy.psl.com.apnicricketleague.fragment.CreateTeamFragment;
import league.fantasy.psl.com.apnicricketleague.model.response.Contest.Datum;

public class ContestAdapter extends ArrayAdapter<Datum> {
    private Context context;
    private int resource;
    private Datum datum;
    private int teamId1,teamId2;
    private FragmentManager fragmentManager;
    public ContestAdapter(Context context, int resource, Datum datum, int teamId1, int teamId2, FragmentManager fragmentManager) {
        super(context, resource, Collections.singletonList(datum));
        this.context=context;
        this.resource=resource;
        this.datum=datum;
        this.teamId1=teamId1;
        this.teamId2=teamId2;
        this.fragmentManager=fragmentManager;
    }

    @Override
    public int getPosition(Datum item) {
        return item.getContestId().intValue();
    }



    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        View row=convertView;
        RecyclerView.ViewHolder holder;
        if(convertView==null){
            row=LayoutInflater.from(context).inflate(resource,parent,false);
        }
//        Datum datum=list.get(position);
        TextView txt_price=row.findViewById(R.id.txt_price);
        TextView txt_discount=row.findViewById(R.id.txt_discount);
        Button btn_pay=row.findViewById(R.id.btn_pay);
        TextView txt_winner=row.findViewById(R.id.txt_winner);

        ProgressBar progressBar=row.findViewById(R.id.progressBar);
        TextView txt_multi=row.findViewById(R.id.txt_multi);
        TextView txt_confirm_winning=row.findViewById(R.id.txt_confirm_winning);
        if(datum.getIsVisible().equals("1")){
            txt_price.setText(datum.getWinningPoints());
            if(datum.getDiscount()>0){
                txt_discount.setVisibility(View.VISIBLE);
                StrikethroughSpan strikethroughSpan = new StrikethroughSpan();
                txt_discount.setText(datum.getEnteryFee(),TextView.BufferType.SPANNABLE);
                Spannable spannable = (Spannable) txt_discount.getText();
                spannable.setSpan(strikethroughSpan, 0, txt_discount.getText().length(), Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
            }
            txt_winner.setText(datum.getWinners());
            btn_pay.setText(datum.getDiscount().toString());
            progressBar.setMax(datum.getPool().intValue());
            progressBar.setProgress(datum.getPoolConsumed().intValue());

            if(datum.getMultipleAllowed().equals("1")){
                txt_multi.setText("M");
                txt_multi.setVisibility(View.VISIBLE);

            }else{
                txt_multi.setText("S");
                txt_multi.setVisibility(View.VISIBLE);
            }



            if(datum.getConfirmedWinning().equals("1")){
                txt_confirm_winning.setText("C");
                txt_confirm_winning.setVisibility(View.VISIBLE);
            }
            btn_pay.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Fragment fragment=new CreateTeamFragment();
                    Bundle bundle=new Bundle();
                    bundle.putInt("TeamId1",teamId1);
                    bundle.putInt("TeamId2",teamId2);
                    fragment.setArguments(bundle);
                    FragmentTransaction ft = fragmentManager.beginTransaction();
                    ft.replace(R.id.content_frame, fragment);
                    ft.commit();
                }
            });
        }
        return row;
    }



}
