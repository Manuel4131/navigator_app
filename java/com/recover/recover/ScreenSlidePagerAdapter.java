package com.recover.recover;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;

//public class SwipeScreenActivity extends FragmentActivity {
//    /**
//     * Number of pages
//     */
//    private static final int NUM_PAGES = 2;
//
//    /**
//     * The pager widget, which handles animation and allows swiping
//     * horizontally to access previous and next.
//     */
//    ViewPager mPager;
//    /**
//     * The pager adapter, which provides the pages to the view pager widget
//     */
//    private PagerAdapter pagerAdapter;
//
//    @Override
//    protected void onCreate(Bundle savedInstanceState) {
//        super.onCreate(savedInstanceState);
//        // A layout which contains a ViewPager
//        setContentView(R.layout.activity_main);
//
//        // Instatiate a ViewPager and a PagerAdapter
//        mPager = findViewById(R.id.viewpager);
//        pagerAdapter = new ScreenSlidePagerAdapter(getSupportFragmentManager());
//        mPager.setAdapter(pagerAdapter);
//    }
//    private class ScreenSlidePagerAdapter extends FragmentStatePagerAdapter {
//        public ScreenSlidePagerAdapter(FragmentManager fm) {
//            super(fm);
//        }
//
//        @Override
//        public Fragment getItem(int position) {
//            if(position==0)
//                return new LocalFragment();
//            else
//                return new RemoteFragment();
//        }
//
//        @Override
//        public int getCount(){
//            return NUM_PAGES;
//        }//func
//    }//class
//}
public class ScreenSlidePagerAdapter extends FragmentStatePagerAdapter {
    /**
     * Number of pages
     */
    private static final int NUM_PAGES = 2;
    public ScreenSlidePagerAdapter(FragmentManager fm) {
        super(fm);
    }

    @Override
    public Fragment getItem(int position) {
        if(position==0)
            return new LocalFragment();
        else
            return new RemoteFragment();
    }

    @Override
    public int getCount(){
        return NUM_PAGES;
    }//func
}//class
