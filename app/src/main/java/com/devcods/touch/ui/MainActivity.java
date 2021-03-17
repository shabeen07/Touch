package com.devcods.touch.ui;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.os.StrictMode;

import com.devcods.touch.R;
import com.devcods.touch.adapter.PagerAdapter;
import com.devcods.touch.databinding.ActivityMainBinding;
import com.devcods.touch.pageTransform.DepthPageTransformer;
import com.devcods.touch.ui.fragments.CallsFragment;
import com.devcods.touch.ui.fragments.FeedFragment;
import com.devcods.touch.ui.fragments.HomeFragment;

import java.util.Objects;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate( savedInstanceState );
        ActivityMainBinding binding = ActivityMainBinding.inflate( getLayoutInflater() );
        setContentView( binding.getRoot() );
        try {
            StrictMode.ThreadPolicy policy =
                    new StrictMode.ThreadPolicy.Builder().permitAll().build();
            StrictMode.setThreadPolicy(policy);
        }catch (Exception e){
            e.printStackTrace();
        }
        binding.tabLayout.setSelectedTabIndicatorColor(getResources().getColor(R.color.white));
        PagerAdapter adapter = new PagerAdapter(getSupportFragmentManager());
        adapter.AddFragment(new HomeFragment(),"CHATS");
        adapter.AddFragment(new FeedFragment(),"FEED");
        adapter.AddFragment(new CallsFragment(),"CALLS");
        binding.viewPager.setAdapter(adapter);
        binding.tabLayout.setupWithViewPager( binding.viewPager);
        binding.viewPager.setPageTransformer(true,new DepthPageTransformer());
        Objects.requireNonNull( binding.tabLayout.getTabAt(0)).setText("CHATS");
        Objects.requireNonNull( binding.tabLayout.getTabAt(1)).setText("FEED");
        Objects.requireNonNull( binding.tabLayout.getTabAt(2)).setText("CALLS");
    }
}