package league.fantasy.psl.com.apnicricketleague.adapter;
 
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;

import league.fantasy.psl.com.apnicricketleague.fragment.PlayerFragment;

public class PagerAdapter extends FragmentStatePagerAdapter {
    int mNumOfTabs;
    int teamId1,teamId2;
    public PagerAdapter(FragmentManager fm, int NumOfTabs,int teamId1,int teamId2) {
        super(fm);
        this.mNumOfTabs = NumOfTabs;
        this.teamId1=teamId1;
        this.teamId2=teamId2;
    }
 
    @Override
    public Fragment getItem(int position) {
 
        switch (position) {
            case 0:
                Fragment tab_wk = new PlayerFragment();
                Bundle bundle=new Bundle();
                bundle.putInt("player_type",3);
                bundle.putInt("teamId1",teamId1);
                bundle.putInt("teamId2",teamId2);
                tab_wk.setArguments(bundle);
                return tab_wk;
            case 1:
                Fragment tab_bat = new PlayerFragment();
                Bundle bundle_tab_bat=new Bundle();
                bundle_tab_bat.putInt("player_type",0);
                bundle_tab_bat.putInt("teamId1",teamId1);
                bundle_tab_bat.putInt("teamId2",teamId2);
                tab_bat.setArguments(bundle_tab_bat);
                return tab_bat;
            case 2:
                Fragment tab_ar = new PlayerFragment();
                Bundle bundle_tab_ar=new Bundle();
                bundle_tab_ar.putInt("teamId1",teamId1);
                bundle_tab_ar.putInt("teamId2",teamId2);
                bundle_tab_ar.putInt("player_type",2);
                return tab_ar;
            case 3:
                Fragment tab_bowl=new PlayerFragment();
                Bundle bundle_tab_bowl=new Bundle();
                bundle_tab_bowl.putInt("teamId1",teamId1);
                bundle_tab_bowl.putInt("teamId2",teamId2);
                bundle_tab_bowl.putInt("player_type",1);
                return tab_bowl;
            default:
                return null;
        }
    }
 
    @Override
    public int getCount() {
        return mNumOfTabs;
    }
}