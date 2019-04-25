package league.fantasy.psl.com.apnicricketleague.fragment;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;

import org.json.JSONObject;

import league.fantasy.psl.com.apnicricketleague.R;
import league.fantasy.psl.com.apnicricketleague.Utils.Helper;
import league.fantasy.psl.com.apnicricketleague.adapter.ContestAdapter;
import league.fantasy.psl.com.apnicricketleague.client.ApiClient;

import league.fantasy.psl.com.apnicricketleague.model.response.Contest.ContestResponse;
import league.fantasy.psl.com.apnicricketleague.model.response.Contest.Datum;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * A simple {@link Fragment} subclass.
 */
public class ContestFragment extends Fragment {
    String match_id = null;
    int TeamId1,TeamId2;

    public ContestFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment

        final View mView=inflater.inflate(R.layout.fragment_contest, container, false);;
        final ListView list_contest=mView.findViewById(R.id.list_contest);
        try{
            if(getArguments()!=null) {

                match_id = getArguments().getString("match_id");
                TeamId1=getArguments().getInt("TeamId1");
                TeamId2=getArguments().getInt("TeamId2");
            }

            JSONObject object=new JSONObject();
            object.put("match_id",match_id);
            object.put("method_Name",this.getClass().getSimpleName()+".onCreateView");
            Log.e("Fragment",object.toString());
            ApiClient.getInstance().getAllContest(Helper.encrypt(object.toString()))
                    .enqueue(new Callback<ContestResponse>() {
                        @Override
                        public void onResponse(Call<ContestResponse> call, Response<ContestResponse> response) {
                            if(response.isSuccessful()){
                                if(response.body().getResponseCode().equals("1001")){
                                    for(Datum datum:response.body().getData()){
                                        if(datum.getIsVisible().equals("1")){
                                            ContestAdapter adapter=new ContestAdapter(mView.getContext(),R.layout.contest_list,datum,TeamId1,TeamId2,getFragmentManager());
                                            list_contest.setAdapter(adapter);
                                        }
                                    }

                                }
                            }
                        }

                        @Override
                        public void onFailure(Call<ContestResponse> call, Throwable t) {

                        }
                    });
        }catch (Exception e){
            e.printStackTrace();
        }
        return mView;
    }

}
