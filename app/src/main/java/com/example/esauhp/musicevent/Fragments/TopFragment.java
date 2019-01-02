package com.example.esauhp.musicevent.Fragments;

import android.graphics.Color;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.AppBarLayout;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.esauhp.musicevent.R;


public class TopFragment extends Fragment {

    private AppBarLayout appBar;
    private TabLayout tabs;
    private ViewPager viewPager;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view =inflater.inflate(R.layout.fragment_top,container,false);

        View contenedor = (View) container.getParent();
        appBar = (AppBarLayout) contenedor.findViewById(R.id.appbar);
        tabs = new TabLayout(getActivity());
        tabs.setTabTextColors(Color.parseColor("#FFFFFF"), Color.parseColor("#FFFFFF"));
        appBar.addView(tabs);

        viewPager=(ViewPager) view.findViewById(R.id.viewpager);

        ViewPagerAdapter pagerAdapter = new ViewPagerAdapter(getFragmentManager());
        viewPager.setAdapter(pagerAdapter);
        tabs.setupWithViewPager(viewPager);

        return view;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        appBar.removeView(tabs);
    }

    public class ViewPagerAdapter extends FragmentStatePagerAdapter{
        public ViewPagerAdapter (FragmentManager fragmentManager){
            super(fragmentManager);
        }

        String [] tituloTabs = {"ALBUMS","ARTIST","SONGS"};

        @Override
        public Fragment getItem(int i) {
            switch (i){
                case 0:
                    return new TopAlbumFragment();
                case 1:
                    return new TopArtistFragment();
                case 2:
                    return new TopSongFragment();
            }
            return null;
        }

        @Override
        public int getCount() {
            return 3;
        }

        @Nullable
        @Override
        public CharSequence getPageTitle(int position) {
            return tituloTabs[position];
        }
    }
}
