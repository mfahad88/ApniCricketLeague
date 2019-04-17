package league.fantasy.psl.com.apnicricketleague.fragment;


import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.text.method.ScrollingMovementMethod;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;
import android.widget.TextView;

import league.fantasy.psl.com.apnicricketleague.R;
import league.fantasy.psl.com.apnicricketleague.Utils.Helper;

/**
 * A simple {@link Fragment} subclass.
 */
public class RulesFragment extends Fragment {
    View mView;
    TextView tv;

    public RulesFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        mView=inflater.inflate(R.layout.fragment_rules, container, false);
        tv = mView.findViewById(R.id.tv);
        tv.setMovementMethod(new ScrollingMovementMethod());
        String data=Helper.readFile(getActivity(), "rules_book.txt");
        tv.setText(data);
        return mView;
    }

}
