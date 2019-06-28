package com.recover.recover;

import android.os.Bundle;
import android.support.design.widget.BottomNavigationView;
import android.support.v7.app.AppCompatActivity;
import android.support.annotation.NonNull;
import android.util.Log;
import android.view.MenuItem;
import android.support.v4.app.Fragment;
/*
* For horizontal paging.
* */
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;


public class MainActivity extends AppCompatActivity {

    /**
     * The pager widget, which handles animation and allows swiping
     * horizontally to access previous and next.
     */
    ViewPager mPager;
    /**
     * The pager adapter, which provides the pages to the view pager widget
     */
    private PagerAdapter pagerAdapter;

    private BottomNavigationView.OnNavigationItemSelectedListener mOnNavigationItemSelectedListener
            = new BottomNavigationView.OnNavigationItemSelectedListener() {

        @Override
        public boolean onNavigationItemSelected(@NonNull MenuItem item) {
            Fragment selectedFragment = null;
            Log.d("item.getItemId", String.valueOf(item.getItemId()));
            switch (item.getItemId()) {
                case R.id.navigation_home:
                    mPager.setCurrentItem(0);
                    break;

                case R.id.navigation_fav:
                    mPager.setCurrentItem(1);
                    break;
            }
            return true;
        }
    };

    @Override
    protected void onDestroy() {
        super.onDestroy();
        stopService(GridViewActivity.serviceIntent);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        final BottomNavigationView navView = findViewById(R.id.bottom_navigation);
        navView.setOnNavigationItemSelectedListener(mOnNavigationItemSelectedListener);

        mPager = findViewById(R.id.viewpager);
        pagerAdapter = new ScreenSlidePagerAdapter(getSupportFragmentManager());
        mPager.setAdapter(pagerAdapter);
        mPager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

            }

            @Override
            public void onPageSelected(int i) {
               navView.getMenu().getItem(i).setChecked(true);
            }

            @Override
            public void onPageScrollStateChanged(int i) {

            }
       }

        );//addOnPageChangeListener
    }//onCreate
}
