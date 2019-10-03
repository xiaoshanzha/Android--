package com.example.ywang.diseaseidentification.view.fragment;


import android.content.Context;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.example.ywang.diseaseidentification.R;
import com.example.ywang.diseaseidentification.bean.baseData.NewsBean;
import com.youth.banner.Banner;
import com.youth.banner.BannerConfig;
import com.youth.banner.Transformer;
import com.youth.banner.loader.ImageLoader;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

public class ThirdFragment extends Fragment {

    private ViewPager mViewPager;
    private TabLayout mTabLayout;
    private FloatingActionButton mFloatingActionButton;

    private List<Fragment> mFragments = new ArrayList<>(  );

    private String[] mTitles = {"农业头条","疾病防治","养殖技巧"};

    private TitleAdapter mAdapter;
    private Banner banner;

    private List<String> images = new ArrayList<>();
    private List<String> titles = new ArrayList<>();

    private List<NewsBean> mNewsBeans = new ArrayList<>();
    private Boolean flag = false;
    private void initData(){
        new Thread(new Runnable() {
            @Override
            public void run() {
                HttpURLConnection connection = null;
                BufferedReader reader = null;
                try {
                    URL url = new URL("http://192.168.137.1:8080/twoweb/GetNewsOneServlet");
                    connection = (HttpURLConnection)url.openConnection();
                    connection.setRequestMethod("POST");


                    //设置连接超时和读取超时的毫秒数
                    connection.setConnectTimeout(8000);
                    connection.setReadTimeout(8000);

                    InputStream in = connection.getInputStream();
                    reader = new BufferedReader(new InputStreamReader(in));
                    String line;
                    while ((line = reader.readLine())!=null) {

                        int  num = line.charAt(0)-'0';
                        String author = reader.readLine();
                        String time = reader.readLine();
                        String title = reader.readLine();
                        String content = reader.readLine();
                        String img_url = reader.readLine();
                        String main_url = reader.readLine();
                        NewsBean newsBean = new NewsBean(num,author,time,title,content,img_url,main_url);
                        mNewsBeans.add(newsBean);
                    }
                    flag = true;
                } catch (Exception e) {
                    e.printStackTrace();
                } finally {
                    if(reader!=null){
                        try {
                            reader.close();
                        } catch (IOException e) {
                            e.printStackTrace();
                        }
                    }
                    if(connection != null){
                        connection.disconnect();
                    }
                }
            }
        }).start();
    }
    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        mFragments.add(new NewsFragment());
        mFragments.add(new NewsFragment());
        mFragments.add(new NewsFragment());

        images.add("http://img8.agronet.com.cn/Users/100/189/915/2019911955414172.jpeg");
        titles.add("互联网+消费扶贫助力国家级贫困县山西汾西脱贫攻坚（图）");
        images.add("http://img8.agronet.com.cn/Users/100/189/915/2019911948581301.png");
        titles.add("统计局：8月CPI同比增2.8% 猪肉价格上涨46.7%");
        images.add("http://img8.agronet.com.cn/Users/100/617/663/2019911901105591.jpg");
        titles.add("蔬果价格继续回落 保障充足供应中秋（图）");
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_third,container,false);
        mViewPager = (ViewPager) view.findViewById( R.id.news_home_viewpager );
        mTabLayout = (TabLayout) view.findViewById( R.id.tab_layout);
        mAdapter = new TitleAdapter(getChildFragmentManager(), mFragments, mTitles);
        mViewPager.setAdapter(mAdapter);
        mTabLayout.setTabMode(TabLayout.MODE_SCROLLABLE);
        mTabLayout.setupWithViewPager(mViewPager);
        mFloatingActionButton = (FloatingActionButton) view.findViewById( R.id.fab_news );
        mFloatingActionButton.setOnClickListener( new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int position = mViewPager.getCurrentItem();

            }
        } );
        banner = (Banner) view.findViewById(R.id.banner);
        //设置banner样式
        banner.setBannerStyle(BannerConfig.CIRCLE_INDICATOR_TITLE);
        //设置图片加载器
        banner.setImageLoader(new GlideImageLoader());
        //设置图片集合
        banner.setImages(images);
        //banner设置方法全部调用完毕时最后调用
        banner.setBannerAnimation(Transformer.DepthPage);
        //设置标题集合（当banner样式有显示title时）
        banner.setBannerTitles(titles);
        //设置轮播时间
        banner.setDelayTime(1500);
        //设置指示器位置（当banner模式中有指示器时）
        banner.setIndicatorGravity(BannerConfig.CENTER);
        banner.start();
        return view;
    }

    class TitleAdapter extends FragmentPagerAdapter {

        private List<Fragment> fragments;
        private String[] titles;

        public TitleAdapter(FragmentManager fm, List<Fragment> fragments, String[] titles){
            super(fm);
            this.fragments = fragments;
            this.titles = titles;
        }

        public Fragment getItem(int position){
            return fragments.get( position );
        }

        @Override
        public int getCount() {
            return fragments.size();
        }

        @Nullable
        @Override
        public CharSequence getPageTitle(int position) {
            if (position >= 8)
                return null;
            else
                return titles[position];
        }
    }

    class GlideImageLoader extends ImageLoader {
        @Override
        public void displayImage(Context context, Object path, ImageView imageView) {
            /**
             注意：
             1.图片加载器由自己选择，这里不限制，只是提供几种使用方法
             2.返回的图片路径为Object类型，由于不能确定你到底使用的那种图片加载器，
             传输的到的是什么格式，那么这种就使用Object接收和返回，你只需要强转成你传输的类型就行，
             切记不要胡乱强转！
             */

            //Glide 加载图片简单用法
            Glide.with(context).load(path).into(imageView);

            //用fresco加载图片简单用法，记得要写下面的createImageView方法
            Uri uri = Uri.parse((String) path);
            imageView.setImageURI(uri);
        }

        //提供createImageView 方法，如果不用可以不重写这个方法，主要是方便自定义ImageView的创建
        @Override
        public ImageView createImageView(Context context) {
            //使用fresco，需要创建它提供的ImageView，当然你也可以用自己自定义的具有图片加载功能的ImageView
            ImageView simpleDrawView = new ImageView(context);
            return simpleDrawView;
        }
    }
}
