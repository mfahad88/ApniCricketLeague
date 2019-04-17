package league.fantasy.psl.com.apnicricketleague.fragment;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import league.fantasy.psl.com.apnicricketleague.R;

/**
 * A simple {@link Fragment} subclass.
 */
public class AboutFragment extends Fragment {
    View mView;
    ImageView iv_points,iv_faqs;
    Fragment fragment;
    public AboutFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        mView=inflater.inflate(R.layout.fragment_about, container, false);
        init();
        iv_points.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                fragment=new RulesFragment();
                FragmentManager fragmentManager = getActivity().getSupportFragmentManager();
                FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
                fragmentTransaction.replace(R.id.content_frame, fragment);
                fragmentTransaction.addToBackStack(null);
                fragmentTransaction.commit();
            }
        });

        iv_faqs.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                /*fragment=new RulesFragment();
                FragmentManager fragmentManager = getActivity().getSupportFragmentManager();
                FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
                fragmentTransaction.replace(R.id.content_frame, fragment);
                fragmentTransaction.addToBackStack(null);
                fragmentTransaction.commit();*/
            }
        });
        return mView;
    }

    public void init(){
        iv_points=mView.findViewById(R.id.iv_points);
        iv_faqs=mView.findViewById(R.id.iv_faqs);
    }
}
