package com.jlnshen.sample.simplecelllayout;

import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.support.v4.view.ViewPager;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;

import com.jlnshen.widget.collagepager.CollagePager;
import com.jlnshen.widget.collagepager.VerticalCollagePager;
import com.jlnshen.widget.collagepager.ZoomFlyPageTransformer;
import com.squareup.picasso.Picasso;

public class VerticalCollagePagerDemoActivity extends Activity {
    static final String[] photos = new String[]{
            "http://3.bp.blogspot.com/-mYajmn9TuD0/UX3KXKx1MuI/AAAAAAAADos/zuNis2Jebeo/s200/DOCTOR-WHO-10.jpg",
            "http://www.blastr.com/sites/blastr/files/styles/content_panes_media/public/images/DoctorWhoJacketLEAD_0.jpg",
            "http://static.guim.co.uk/sys-images/Guardian/Pix/pictures/2013/1/8/1357668056800/Doctor-WHO-004.jpg",
            "http://www.radiotimes.com/uploads/images/original/13605.jpg",
            "http://24.media.tumblr.com/tumblr_m4t3ui51UL1qdctn9o1_r1_500.jpg",
            "http://metrouk2.files.wordpress.com/2013/02/3600986-low_res-doctor-who-series-7b.jpg?w=650&h=467&crop=1",
            "http://static.guim.co.uk/sys-images/Guardian/About/General/2013/4/5/1365181913614/Doctor-Who---Series-7B-010.jpg",
            "http://www.blogto.com/upload/2012/08/2012831-doctor-who.jpg",
            "http://graphics8.nytimes.com/images/2013/05/13/arts/doctorwho/doctorwho-blog480.jpg",
            "http://l.yimg.com/bt/api/res/1.2/X0u6ExZzkka1gdn17QWpjw--/YXBwaWQ9eW5ld3M7Zmk9aW5zZXQ7aD03MjA7cT04NTt3PTUyNg--/http://media.zenfs.com/en_us/tv_show/TV/doctor-who-76608.jpg",
            "http://static.guim.co.uk/sys-images/Guardian/About/General/2013/5/10/1368197352493/Doctor-Who---a-Cyberman-m-009.jpg",
            "http://wac.450f.edgecastcdn.net/80450F/screencrush.com/files/2013/05/doctor_who_nightmare_in_silver-1920x1080.jpg",
            "http://cdn.culturemass.com/wp-content/uploads/2013/05/river-song-doctor-who-brasil.jpg",
            "http://i1.wp.com/nerdbastards.com/wp-content/uploads/2013/05/karen-gillian-doctor-who-1.jpg",
            "http://i1.cdnds.net/12/49/618x412/uktv-doctor-who-xmas-2012-4.jpg",
            "http://www.wired.com/geekdad/wp-content/uploads/2010/04/doctorwho-smith-gillan.jpg",
            "http://mildconcern.com/wp-content/uploads/2012/09/DoctorWho1.png",
            "http://imaginationlane.net/blog/wp-content/uploads/2013/01/souffle-girl-doctor-who.jpg",
            "http://good3dtv.com/wp-content/uploads/doctor-who.jpg",
            "http://jeffreyconolly.com/jeffthegreat/wp-content/uploads/2011/03/karen-gillian-doctor-who-1.jpg",
            "http://images1.wikia.nocookie.net/__cb20120613205953/creepypasta/images/thumb/d/d2/Doctor-who.jpg.jpg/500px-Doctor-who.jpg.jpg",
            "http://static.bbc.co.uk/programmeimages/608xn/images/p00rdbc4.jpg",
            "http://static.guim.co.uk/sys-images/Guardian/Pix/pictures/2013/5/8/1368026779310/Doctor-Who-008.jpg",
            "http://4.bp.blogspot.com/-wplhieFwy5c/UYrQOGWd0iI/AAAAAAAAdwQ/4TpreTRfiqA/s1600/notd2.png",
            "http://l1.yimg.com/bt/api/res/1.2/NpMH_4Ab4acknKMZjjaUuQ--/YXBwaWQ9eW5ld3M7cT04NTt3PTYwMA--/http://media.zenfs.com/en_US/News/TVLine.com/doctor-who-john-b-dw.jpg",
            "http://d1h1icg9nkzvny.cloudfront.net/wp-content/uploads/2013/05/Doctor-Who2-e1370049998923.jpg",
            "http://www.hollywoodreporter.com/sites/default/files/imagecache/thumbnail_570x321/2012/11/doctor_who_snowmen_a_l_0.jpg"
    };

    MyAdapter myAdapter = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.verticalcollagepagerdemo);

        VerticalCollagePager pager = (VerticalCollagePager) findViewById(R.id.collagePager);
        myAdapter = new MyAdapter(this);
        pager.setPageTransformer(false, new ZoomFlyPageTransformer());
        pager.setAdapter(myAdapter);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuItem item1 = menu.add("Increase items");
        MenuItem item2 = menu.add("Decrease items");

        item1.setOnMenuItemClickListener(new MenuItem.OnMenuItemClickListener() {
            @Override
            public boolean onMenuItemClick(MenuItem menuItem) {
                int i = (int) (System.currentTimeMillis() % 10) + 1;
                myAdapter.setSize(myAdapter.mSize + i);
                return true;
            }
        });

        item2.setOnMenuItemClickListener(new MenuItem.OnMenuItemClickListener() {
            @Override
            public boolean onMenuItemClick(MenuItem menuItem) {
                int i = (int) (System.currentTimeMillis() % 10) + 1;
                int newSize = myAdapter.mSize - i;
                newSize = newSize < 0 ? 0 : newSize;
                myAdapter.setSize(newSize);

                return true;
            }
        });

        return super.onCreateOptionsMenu(menu);
    }

    static class MyAdapter extends BaseAdapter {

        Context mContext;
        int mSize = 100;

        MyAdapter(Context context) {
            mContext = context;
        }

        @Override
        public int getCount() {
            return mSize;
        }

        @Override
        public Object getItem(int i) {
            return photos[i % photos.length];
        }

        @Override
        public long getItemId(int i) {
            return i;
        }

        @Override
        public View getView(int i, View view, ViewGroup viewGroup) {
            ImageView imageView = null;
            if (view != null) {
                imageView = (ImageView) view;
                imageView.setImageDrawable(null);
            } else {
                imageView = new ImageView(mContext);
                imageView.setLayoutParams(
                        new ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT,
                                ViewGroup.LayoutParams.MATCH_PARENT));
            }

            Picasso.with(mContext).load((String) getItem(i)).fit().into(imageView);

            return imageView;
        }

        public void setSize(int size) {
            mSize = size;
            Log.d("AAAA", "item size: " + mSize);
            notifyDataSetChanged();
        }
    }

    class TestPageTransformer implements ViewPager.PageTransformer {

        @Override
        public void transformPage(View view, float pos) {
            if (pos > 0 && pos <= 1) {
                float rot = Math.abs(pos) * 30f;
                view.setPivotY(view.getWidth() / 2);
                view.setPivotX(view.getHeight() / 2);
                view.setRotationY(rot);
            } else if (pos <= 0 && pos > -1) {
                float rot = (1 - Math.abs(pos)) * 30f;
                view.setPivotY(view.getWidth() / 2);
                view.setPivotX(view.getHeight() / 2);
                view.setRotationY(rot);
            }
        }
    }
}
