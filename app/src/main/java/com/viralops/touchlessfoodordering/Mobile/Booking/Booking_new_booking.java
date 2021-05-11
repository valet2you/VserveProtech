package com.viralops.touchlessfoodordering.Mobile.Booking;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentPagerAdapter;
import androidx.viewpager.widget.PagerTabStrip;
import androidx.viewpager.widget.PagerTitleStrip;
import androidx.viewpager.widget.ViewPager;

import android.os.Bundle;
import android.widget.Toast;

import com.viralops.touchlessfoodordering.R;

public class Booking_new_booking extends AppCompatActivity {
    // ...
    FragmentPagerAdapter adapterViewPager;
    PagerTabStrip pager_header;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_booking_new_booking);
        ViewPager vpPager = (ViewPager) findViewById(R.id.vpPager);
        pager_header = (PagerTabStrip) findViewById(R.id.pager_header);
        adapterViewPager = new MyPagerAdapter(getSupportFragmentManager());
        vpPager.setAdapter(adapterViewPager);
        vpPager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {

            // This method will be invoked when a new page becomes selected.
            @Override
            public void onPageSelected(int position) {
                Toast.makeText(Booking_new_booking.this,
                        "Selected page position: " + position, Toast.LENGTH_SHORT).show();
            }

            // This method will be invoked when the current page is scrolled
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {
                // Code goes here
            }

            // Called when the scroll state changes:
            // SCROLL_STATE_IDLE, SCROLL_STATE_DRAGGING, SCROLL_STATE_SETTLING
            @Override
            public void onPageScrollStateChanged(int state) {
                // Code goes here
            }
        });
    }
    public static class MyPagerAdapter extends FragmentPagerAdapter {
        private static int NUM_ITEMS = 7;

        public MyPagerAdapter(FragmentManager fragmentManager) {
            super(fragmentManager);
        }

        // Returns total number of pages
        @Override
        public int getCount() {
            return NUM_ITEMS;
        }

        // Returns the fragment to display for that page
        @Override
        public Fragment getItem(int position) {
            /*switch (position) {
                case 0: // Fragment # 0 - This will show FirstFragment
                    return First.newInstance(0, "16 Feb, Tue");
                case 1: // Fragment # 0 - This will show FirstFragment different title
                    return Second.newInstance(1, "17 Feb, Wed");
                case 2: // Fragment # 1 - This will show SecondFragment
                    return Third.newInstance(2, "18 Feb, Thur");
                case 3: // Fragment # 1 - This will show SecondFragment
                    return Fourth.newInstance(3, "19 Feb Fri");
                case 4: // Fragment # 1 - This will show SecondFragment
                    return Fifth.newInstance(4, "20 Feb, Sat");
                    case 5: // Fragment # 1 - This will show SecondFragment
                    return Fifth.newInstance(5, "21 Feb, Sun");
                    case 6: // Fragment # 1 - This will show SecondFragment
                    return Fifth.newInstance(6, "22 Feb, Mon");
                default:
                    return null;
            }*/
            return null;

        }

        // Returns the page title for the top indicator
        @Override
        public CharSequence getPageTitle(int position) {
            return "Page " + position;
        }

    }
}
