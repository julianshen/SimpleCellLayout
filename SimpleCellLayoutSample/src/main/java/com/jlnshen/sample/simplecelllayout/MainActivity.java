package com.jlnshen.sample.simplecelllayout;

import android.app.Activity;
import android.graphics.Color;
import android.os.Bundle;
import android.view.Menu;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.jlnshen.widget.celllayout.SimpleCellLayout;
import com.squareup.picasso.Picasso;

public class MainActivity extends Activity {
    static final String[] photos = new String[]{
            "http://3.bp.blogspot.com/-iLQRphAVwAI/T0VXXsCWKkI/AAAAAAAAKZs/ReV3vL8Ql5s/s1600/Movie+Wallpaper+Iron+Man+Character+1280x720.jpg",
            "http://thecomicking.net/wp-content/uploads/2013/04/42-iron-man-iron-man-hd-8-free-spot-free-download.jpg",
            "http://images5.fanpop.com/image/photos/29400000/Iron-Man-Tony-Stark-the-avengers-29489238-2124-2560.jpg",
            "http://3.bp.blogspot.com/-Cv5mmrhQ-Lk/UV7Aj84e3aI/AAAAAAAB45c/xnoaI7f9dsg/s400/Iron-Man-3-Movie-2013.jpg",
            "http://img2.timeinc.net/ew/i/2013/01/30/Iron-Man-3-Superbowl_810x1181.jpg",
            "http://images5.fanpop.com/image/photos/30700000/Black-Widow-Iron-Man-2-look-black-widow-30737267-1657-2560.jpg",
            "http://cdn.redmondpie.com/wp-content/uploads/2013/04/Iron-Man-3-4.jpg",
            "http://www.wired.com/images_blogs/underwire/2013/04/MODEL-21.jpg",
            "http://resources1.news.com.au/images/2012/10/24/1226502/167341-iron-man-3.jpg",
            "http://images.theage.com.au/2013/05/07/4252229/ironman3_main-620x349.jpg",
            "http://wac.450f.edgecastcdn.net/80450F/screencrush.com/files/2013/02/Downey-Jr-Iron-Man-3.jpg",
            "http://www.scifinow.co.uk/wp-content/uploads/2012/12/Iron-Man-3-new-picture.jpeg",
            "http://images2.wikia.nocookie.net/__cb20130402220850/marveldatabase/images/9/96/X-Men_Legacy_Vol_2_9_Many_Armors_of_Iron_Man_Variant_Textless.jpg"
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        SimpleCellLayout layout = (SimpleCellLayout) findViewById(R.id.celllayout);


        ImageView imageView1 = new ImageView(this);
        SimpleCellLayout.CellLayoutParams layoutParams1 = new SimpleCellLayout.CellLayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT);
        layoutParams1.cellX = 0;
        layoutParams1.cellY = 0;
        layoutParams1.cellRowSpan = 2;
        layoutParams1.cellColSpan = 2;

        imageView1.setLayoutParams(layoutParams1);
        imageView1.setBackgroundColor(Color.BLUE);
        layout.addView(imageView1);
        Picasso.with(this).load(photos[0]).fit().into(imageView1);

        ImageView imageView2 = new ImageView(this);
        SimpleCellLayout.CellLayoutParams layoutParams2 = new SimpleCellLayout.CellLayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT);
        layoutParams2.cellX = 2;
        layoutParams2.cellY = 0;
        layoutParams2.cellColSpan = 2;

        imageView2.setLayoutParams(layoutParams2);
        imageView2.setBackgroundColor(Color.GREEN);
        layout.addView(imageView2);
        Picasso.with(this).load(photos[1]).fit().into(imageView2);

        ImageView imageView3 = new ImageView(this);
        SimpleCellLayout.CellLayoutParams layoutParams3 = new SimpleCellLayout.CellLayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT);
        layoutParams3.cellX = 2;
        layoutParams3.cellY = 1;

        imageView3.setLayoutParams(layoutParams3);
        imageView3.setBackgroundColor(Color.YELLOW);
        layout.addView(imageView3);
        Picasso.with(this).load(photos[2]).fit().into(imageView3);

        ImageView imageView4 = new ImageView(this);
        SimpleCellLayout.CellLayoutParams layoutParams4 = new SimpleCellLayout.CellLayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT);
        layoutParams4.cellX = 3;
        layoutParams4.cellY = 1;

        imageView4.setLayoutParams(layoutParams4);
        imageView4.setBackgroundColor(Color.RED);
        layout.addView(imageView4);
        Picasso.with(this).load(photos[3]).fit().into(imageView4);

        ImageView imageView5 = new ImageView(this);
        SimpleCellLayout.CellLayoutParams layoutParams5 = new SimpleCellLayout.CellLayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT);
        layoutParams5.cellX = 0;
        layoutParams5.cellY = 2;
        layoutParams5.cellRowSpan = 2;
        layoutParams5.cellColSpan = 4;

        imageView5.setLayoutParams(layoutParams5);
        imageView5.setBackgroundColor(Color.MAGENTA);
        layout.addView(imageView5);
        Picasso.with(this).load(photos[4]).fit().into(imageView5);

        ImageView imageView6 = (ImageView) findViewById(R.id.imageView);
        Picasso.with(this).load(photos[6]).fit().into(imageView6);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }

}
