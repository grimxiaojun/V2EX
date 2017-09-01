package com.example.kingsoft.v2ex.activity;

import android.content.Context;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.design.widget.NavigationView;
import android.support.design.widget.TabLayout;
import android.support.v4.view.GravityCompat;
import android.support.v4.view.ViewPager;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.graphics.drawable.DrawerArrowDrawable;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.example.kingsoft.v2ex.Util.LogUtil;
import com.example.kingsoft.v2ex.fragment.AllFragment;
import com.example.kingsoft.v2ex.fragment.AppleFragment;
import com.example.kingsoft.v2ex.fragment.CityFragment;
import com.example.kingsoft.v2ex.fragment.CreativeFragment;
import com.example.kingsoft.v2ex.fragment.DealsFrgment;
import com.example.kingsoft.v2ex.fragment.HotFragment;
import com.example.kingsoft.v2ex.fragment.JobsFragment;
import com.example.kingsoft.v2ex.fragment.PageFragment;
import com.example.kingsoft.v2ex.R;
import com.example.kingsoft.v2ex.adapter.SampleFragmentPagerAdapter;
import com.example.kingsoft.v2ex.fragment.PlayFragment;
import com.example.kingsoft.v2ex.fragment.QnaFragment;
import com.example.kingsoft.v2ex.fragment.R2Fragment;
import com.example.kingsoft.v2ex.fragment.TechFragment;
import com.example.kingsoft.v2ex.http.NodesHttpUtils;

public class MainActivity extends AppCompatActivity {
    public static final int SIGNINKEY = 1;

    private DrawerLayout mDrawerLayout;
    private RelativeLayout navHeader;
    private ImageView userIcon;
    private TextView userName;

    private static Context context;

    public static Context getMainActivityContext() {
        return context;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        context = MainActivity.this;
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        mDrawerLayout = (DrawerLayout) findViewById(R.id.drawer_layout);
        NavigationView navView = (NavigationView) findViewById(R.id.nav);
        if (navView == null) {
            LogUtil.i("navview","fuck");
        }

        View headView = navView.getHeaderView(0);
        userIcon = (ImageView) headView.findViewById(R.id.user_icon);
        userName = (TextView) headView.findViewById(R.id.username_tv);
        if (userIcon == null || userName == null) {
            LogUtil.i("cao","fuck");
        }


        ActionBar actionBar = getSupportActionBar();
        if (actionBar != null) {
            actionBar.setDisplayHomeAsUpEnabled(true);
            actionBar.setHomeAsUpIndicator(R.drawable.ic_drag);
        }

        navView.setCheckedItem(R.id.nav_call);

        navView.setNavigationItemSelectedListener(new NavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                mDrawerLayout.closeDrawers();
                return true;
            }
        });

        ViewPager viewPager = (ViewPager) findViewById(R.id.viewpager);

        SampleFragmentPagerAdapter viewPageAdapter = new SampleFragmentPagerAdapter(getSupportFragmentManager());
        viewPageAdapter.addFragmentView(AllFragment.newInstance(), "全部");
        viewPageAdapter.addFragmentView(TechFragment.newInstance(), "技术");
        viewPageAdapter.addFragmentView(CreativeFragment.newInstance(), "创意");
        viewPageAdapter.addFragmentView(PlayFragment.newInstance(), "好玩");
        viewPageAdapter.addFragmentView(AppleFragment.newInstance(), "Apple");
        viewPageAdapter.addFragmentView(JobsFragment.newInstance(), "酷工作");
        viewPageAdapter.addFragmentView(DealsFrgment.newInstance(), "交易");
        viewPageAdapter.addFragmentView(CityFragment.newInstance(), "城市");
        viewPageAdapter.addFragmentView(QnaFragment.newInstance(), "问与答");
        viewPager.setAdapter(viewPageAdapter);
        TabLayout tabLayout = (TabLayout) findViewById(R.id.sliding_tabs);
        String[] names = {"全部", "技术", "创意", "好玩", "Apple", "酷工作", "交易", "城市", "问与答"};
        for(int i=0;i<names.length;++i) {
            tabLayout.addTab(tabLayout.newTab().setText(names[i]));
        }


        tabLayout.setupWithViewPager(viewPager);







    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.toolbar, menu);
        return true;
    }


    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.login:
                Intent intent = new Intent(MainActivity.this, LoginWithV2exActivity.class);
                startActivityForResult(intent, SIGNINKEY);
                //startActivity(intent);
                break;
            case android.R.id.home:
                mDrawerLayout.openDrawer(GravityCompat.START);
                break;
            default:
                break;
        }
        return true;
    }


    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        Log.i("jason_wang", "requestCode==" + requestCode + ",resultCode==" + resultCode + ",data==" + data);
        if(requestCode==SIGNINKEY){
             //从此处获得登录数据
            String icon = data.getStringExtra("userAvatar");
            String username = data.getStringExtra("userName");
            Glide.with(this).load(icon).into(userIcon);

            userName.setText(username);



        }
    }
}
