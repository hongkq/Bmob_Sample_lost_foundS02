package com.bmob.lostfound;

import android.app.Activity;
import android.os.Handler;
import android.os.Message;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;

import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;

import com.bmob.lostfound.Utils.JsonUtils;
import com.bmob.lostfound.Utils.OkUtils;
import com.bmob.lostfound.bean.JavaBean;
import com.bumptech.glide.Glide;

import java.util.ArrayList;

/**
 * Created by Administrator on 2018/12/10.
 */

public class Newsheadlines extends Activity implements OkUtils.LoadData {
    private String path="http://v.juhe.cn/toutiao/index?type=&key=b21d1a10ce8fd90ad7150ad592fff830";
    // private String path="http://litchiapi.jstv.com/api/getTops?limit=5&column=0&val=F467412B44B421716757A6B2D7635B4A";
    private ViewPager mVp;
    private LinearLayout mLayout;
    private ArrayList<ImageView>views = new ArrayList<>();
    private ArrayList<ImageView>dots = new ArrayList<>();
    private int currentIndex = 0;
    private boolean isTouch = false;
    private boolean isAuto = true;
    private Handler handler = new Handler(){
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            if(msg.what == 0){
                if(isTouch){
                    return;
                }
                currentIndex++;
                mVp.setCurrentItem(currentIndex);
            }
        }
    };
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_news);
        initView();
        OkUtils utils=new OkUtils(this,this);
        utils.getRequest(path);
        thread.start();
    }

    Thread thread = new Thread(new Runnable() {

        public void run() {
            while(isAuto){
                try {
                    thread.sleep(2000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                handler.sendEmptyMessage(0);
            }
        }
    });

    private void initView() {
        mVp = (ViewPager) findViewById(R.id.viewpager);
        mLayout = (LinearLayout) findViewById(R.id.mLayou);
        mVp.setOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {
                currentIndex = mVp.getCurrentItem();
                for(int i=0;i<dots.size();i++){
                    if(position%dots.size()==i){
                        dots.get(i).setImageResource(R.drawable.btn_arrow_p);
                    }else{
                        dots.get(i).setImageResource(R.drawable.btn_arrow_p);
                    }
                }
            }

            @Override
            public void onPageSelected(int position) {

            }

            @Override
            public void onPageScrollStateChanged(int state) {
                if(state == ViewPager.SCROLL_STATE_IDLE){
                    isTouch = false;
                }else{
                    isTouch = true;
                }

            }
        });
    }
    public void loadData(String json) {
        JsonUtils utils = new JsonUtils();
        JavaBean pzjson = utils.getJson(json);
        if(pzjson!=null && pzjson.getReason().equals("成功的返回")){
            ArrayList<JavaBean.Result.Data> datas = pzjson.getResult().getData();
            for(int i=0;i<datas.size();i++){
                ImageView imageView = new ImageView(this);
                imageView.setLayoutParams(new ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT,ViewGroup.LayoutParams.MATCH_PARENT));
                imageView.setScaleType(ImageView.ScaleType.FIT_XY);
                Glide.with(this).load("http://v.juhe.cn/toutiao/index?type=&key=b21d1a10ce8fd90ad7150ad592fff830"+datas.get(i).getThumbnail_pic_s02()).into(imageView);

                views.add(imageView);

                ImageView dot = new ImageView(this);
                dot.setLayoutParams(new ViewGroup.LayoutParams(20,20));
                dot.setScaleType(ImageView.ScaleType.FIT_XY);
                dot.setImageResource(R.drawable.btn_arrow_p);
                mLayout.addView(dot);
                dots.add(dot);
            }
            dots.get(0).setImageResource(R.drawable.btn_arrow_p);
        }
        if(views!=null){
            MyPagerAdapter adapter = new MyPagerAdapter();
            mVp.setAdapter(adapter);
        }
    }
    class MyPagerAdapter extends PagerAdapter {

        @Override
        public int getCount() {
            if(views.size()>0){
                return Integer.MAX_VALUE;
            }
            return 0;
        }

        @Override
        public boolean isViewFromObject(View view, Object object) {
            return view == object;
        }

        @Override
        public Object instantiateItem(ViewGroup container, int position) {
            position %= views.size();
            ViewGroup group = (ViewGroup) views.get(position).getParent();
            if(group!=null){
                group.removeView(views.get(position));
            }
            container.addView(views.get(position));
            return views.get(position);
        }

        @Override
        public void destroyItem(ViewGroup container, int position, Object object) {

        }
    }
}


