package com.operations.winsky.viewpagerargbevaluator;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

public class MainActivity extends AppCompatActivity {
    private ViewPager vp;
    private int[] layouts = {
            R.layout.layout_vp_one,
            R.layout.layout_vp_two,
            R.layout.layout_vp_three
    };
    private WelcompagerTransformer welcompagerTransformer;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        initView();
    }

    private void initView() {
        vp = findViewById(R.id.vp);
        WelcomePagerAdapter adapter = new WelcomePagerAdapter(getSupportFragmentManager());
        vp.setOffscreenPageLimit(3);
        vp.setAdapter(adapter);

        welcompagerTransformer = new WelcompagerTransformer();
        vp.setPageTransformer(true, welcompagerTransformer);

        vp.setOnPageChangeListener(welcompagerTransformer);
    }

    class WelcomePagerAdapter extends FragmentPagerAdapter {

        public WelcomePagerAdapter(FragmentManager fm) {
            super(fm);
        }

        @Override
        public Fragment getItem(int position) {
            Fragment f = new TranslateFragment();
            Bundle bundle = new Bundle();
            bundle.putInt("layoutId", layouts[position]);
            bundle.putInt("pageIndex", position);
            f.setArguments(bundle);
            return f;
        }

        @Override
        public int getCount() {
            return 3;
        }
    }
}