package league.fantasy.psl.com.apnicricketleague.fragment;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.Toast;

import org.json.JSONException;
import org.json.JSONObject;

import league.fantasy.psl.com.apnicricketleague.R;
import league.fantasy.psl.com.apnicricketleague.Utils.Helper;
import league.fantasy.psl.com.apnicricketleague.adapter.MatchesAdapter;
import league.fantasy.psl.com.apnicricketleague.client.ApiClient;
import league.fantasy.psl.com.apnicricketleague.model.response.Matches.Datum;
import league.fantasy.psl.com.apnicricketleague.model.response.Matches.MatchesResponse;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * A simple {@link Fragment} subclass.
 */
public class DashboardFragment extends Fragment {


    public DashboardFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        final View mView=inflater.inflate(R.layout.fragment_dashboard, container, false);
        final ListView list_matches=mView.findViewById(R.id.list_matches);

        try {
            JSONObject object=new JSONObject();
            object.put("method_Name",this.getClass().getSimpleName()+".onCreateView");
            ApiClient.getInstance().matches(Helper.encrypt(object.toString()))
                    .enqueue(new Callback<MatchesResponse>() {
                        @Override
                        public void onResponse(Call<MatchesResponse> call, final Response<MatchesResponse> response) {
                            if(response.isSuccessful()){
                                if(response.body().getResponseCode().equals("1001")){
                                    MatchesAdapter adapter=new MatchesAdapter(mView.getContext(),R.layout.match_list,response.body().getData());
                                    list_matches.setAdapter(adapter);
                                    list_matches.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                                        @Override
                                        public void onItemClick(AdapterView<?> parent, View view, int position, long id) {


                                           Fragment fragment=new CreateTeamFragment();
                                           Bundle bundle=new Bundle();
                                           bundle.putInt("TeamId1",response.body().getData().get(position).getTeamId2());
                                           bundle.putInt("TeamId2",response.body().getData().get(position).getTeamId1());
                                           fragment.setArguments(bundle);
                                            /*Fragment fragment=new ContestFragment();
                                            Bundle bundle=new Bundle();
                                            bundle.putString("match_id", String.valueOf(parent.getAdapter().getItemId(position)));
                                            fragment.setArguments(bundle);*/
                                            FragmentTransaction ft = getFragmentManager().beginTransaction();
                                            ft.replace(R.id.content_frame, fragment);
                                            ft.commit();
                                        }
                                    });
                                }else{
                                    Helper.showAlertNetural(mView.getContext(),"Error",response.body().getMessage());
                                }
                            }
                        }

                        @Override
                        public void onFailure(Call<MatchesResponse> call, Throwable t) {
                            t.fillInStackTrace();
                            Helper.showAlertNetural(mView.getContext(),"Error",t.getMessage());

                        }
                    });
        } catch (JSONException e) {
            e.printStackTrace();
        }

        return mView;
    }

}
