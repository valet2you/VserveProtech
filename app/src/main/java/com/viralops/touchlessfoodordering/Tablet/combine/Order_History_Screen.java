package com.viralops.touchlessfoodordering.Tablet.combine;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.core.view.ViewCompat;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentStatePagerAdapter;
import androidx.viewpager.widget.ViewPager;

import com.google.android.material.tabs.TabLayout;
import com.matthewtamlin.sliding_intro_screen_library.indicators.DotIndicator;
import com.viralops.touchlessfoodordering.R;
import com.viralops.touchlessfoodordering.Support.SessionManager;

public class Order_History_Screen extends Fragment {
    private SessionManager sessionManager;
    private DotIndicator dotIndicator;

    public static Order_History_Screen newInstance() {
        return new Order_History_Screen();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.content_order__history__screen, container, false);

        sessionManager=new SessionManager(getActivity());
        final TabLayout tabLayout = (TabLayout)view. findViewById(R.id.tab_layout);
        ViewCompat.setLayoutDirection(view.findViewById(R.id.tab_layout), ViewCompat.LAYOUT_DIRECTION_LTR);

        tabLayout.addTab(tabLayout.newTab().setText("IRD History"));
        tabLayout.addTab(tabLayout.newTab().setText("Laundry History"));
       // tabLayout.addTab(tabLayout.newTab().setText("Minibar History"));
        tabLayout.addTab(tabLayout.newTab().setText("Spa History"));
        tabLayout.addTab(tabLayout.newTab().setText("AYS History"));

        dotIndicator=(DotIndicator)view.findViewById(R.id.dot);
        dotIndicator.setVisibility(View.GONE);

        //  tabLayout.addTab(tabLayout.newTab().setText("NEW Design"));
        // tabLayout.setBackgroundColor(Color.parseColor(sessionManager.getTHbgcolor()));

        // tabLayout.setTabGravity(TabLayout.GRAVITY_CENTER);

        final ViewPager viewPager = (ViewPager) view.findViewById(R.id.pager);
        final PagerAdapter adapter = new PagerAdapter
                (getFragmentManager(), tabLayout.getTabCount());
        viewPager.setAdapter(adapter);
        viewPager.addOnPageChangeListener(new TabLayout.TabLayoutOnPageChangeListener(tabLayout));

        tabLayout.setOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {
                viewPager.setCurrentItem(tab.getPosition());
            }

            @Override
            public void onTabUnselected(TabLayout.Tab tab) {

            }

            @Override
            public void onTabReselected(TabLayout.Tab tab) {

            }
        });
        return view;
    }


    public class PagerAdapter extends FragmentStatePagerAdapter {
        int mNumOfTabs;

        public PagerAdapter(FragmentManager fm, int NumOfTabs) {
            super(fm);
            this.mNumOfTabs = NumOfTabs;
        }

        @Override
        public Fragment getItem(int position) {

            switch (position) {
                case 0:
                    OrderHistorycombine tab1 = new OrderHistorycombine();
                    return tab1;
              /*  case 2:
                    MinibarOrderHistory tab2 = new MinibarOrderHistory();
                    return tab2;*/

              case 1:
                    LaundryOrderHistorycobine tab3= new LaundryOrderHistorycobine();
                    return tab3;
              case 2:
                  SpaOrderHistorycombine tab4= new SpaOrderHistorycombine();
                    return tab4;
              case 3:
                    ConnectHistoryOrderHistorycombine tab5= new ConnectHistoryOrderHistorycombine();
                    return tab5;


                default:
                    return null;
            }
        }

        @Override
        public int getCount() {
            return mNumOfTabs;
        }
    }
}
