package cn.edu.sdau.self_regist;

import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.app.ActionBarActivity;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;

import beans.BasicInfo;
import beans.User;

/**
 * Created by brahm on 2016/7/4.
 */
public class StepsActivty extends AppCompatActivity {

    private TabLayout mTabLayout;
    private static MyViewPager mViewPager;

    private static User user;

    private Button buttonNext;

    InstructionFragment instructionFragment;
    ChangePwFragment changePwFragment;
    BasicInfoFragment basicInfoFragment;
    CompleteInfoFragment completeInfoFragment;
    TrafficFragment trafficFragment;

    private static  BasicInfo basicInfo;



    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.tablayout_main);

//        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
//        toolbar.setTitle("新生自助报到系统");
//        setSupportActionBar(toolbar);

        user = (User)getIntent().getSerializableExtra("user");
        Log.i("StepsActivty",user.toString());

        initViews();
        initEvents();
    }

    public void nextStep(View view){
        mViewPager.setCurrentItem(1);
    }
    public void nextStep1(View view){
        mViewPager.setCurrentItem(3);
    }
    public void nextStep2(View view){
        mViewPager.setCurrentItem(2);
    }
    public void nextStep3(View view){
        mViewPager.setCurrentItem(4);
    }

    public static User getUser(){
        return user;
    }
    public static void setUser(User user1){
        user = user1;
    }
    public static MyViewPager getMyViewPager(){
        return mViewPager;
    }
    private void initEvents() {
        mTabLayout.setOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {
                mViewPager.setCurrentItem(tab.getPosition());
            }

            @Override
            public void onTabUnselected(TabLayout.Tab tab) {

            }

            @Override
            public void onTabReselected(TabLayout.Tab tab) {

            }
        });
    }

    private void initViews() {
        instructionFragment = new InstructionFragment();
        changePwFragment = new ChangePwFragment();
        basicInfoFragment = new BasicInfoFragment();
        completeInfoFragment = new CompleteInfoFragment();
        trafficFragment = new TrafficFragment();

        mTabLayout = (TabLayout) findViewById(R.id.sliding_tabs);
        mViewPager = (MyViewPager) findViewById(R.id.viewpager);
        mTabLayout.setupWithViewPager(mViewPager);
        mTabLayout.setTabMode(TabLayout.MODE_SCROLLABLE);
        mViewPager.setAdapter(new FragmentPagerAdapter(getSupportFragmentManager()) {
            private String mTitle[] = new String[]{"使用说明", "修改密码", "查看基本信息", "完善信息", "交通信息"};

            @Override
            public Fragment getItem(int position) {
                switch (position){
                    case 0:
                        return instructionFragment;
//                        return new InstructionFragment();
                    case 1:
                       return  changePwFragment;
//                        return new ChangePwFragment();
                    case 2:
                        return basicInfoFragment;
//                        return new BasicInfoFragment();
                    case 3:
                        return completeInfoFragment;
//                        return new CompleteInfoFragment();
                    case 4:
                        //                       return new TrafficFragment();
                        return trafficFragment;
                    default:return new InstructionFragment();
                }

            }


            @Override
            public int getCount() {
                return mTitle.length;
            }

            @Override
            public CharSequence getPageTitle(int position) {
                return mTitle[position];
            }
        });


//        BasicInfoTask basicInfoTask = new BasicInfoTask();
//        basicInfoTask.execute((Void)null);
    }

    @Override
    public void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putInt("position", mViewPager.getCurrentItem());
    }

    @Override
    protected void onRestoreInstanceState(Bundle savedInstanceState) {
        super.onRestoreInstanceState(savedInstanceState);
        mViewPager.setCurrentItem(savedInstanceState.getInt("position"));
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_main,menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        switch (item.getItemId()){
            case R.id.action_quit:
                startActivity(new Intent(this,LoginActivity.class));
                finish();
                break;
            case R.id.about:
                break;
        }
        return super.onOptionsItemSelected(item);
    }
    public static void setBasicInfo(BasicInfo basicInfo_) {
        basicInfo = basicInfo_;
    }

    public static BasicInfo getBasicInfo() {
        return basicInfo;
    }
}
