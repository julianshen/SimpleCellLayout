package com.jlnshen.sample.simplecelllayout;

import android.app.Activity;
import android.os.Bundle;
import android.widget.ImageView;

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

        int[] imageViewIds = new int[]{R.id.imageView1, R.id.imageView2, R.id.imageView3, R.id.imageView4};
        int i = 0;

        for (int res_id : imageViewIds) {
            ImageView imageView = (ImageView) findViewById(res_id);
            Picasso.with(this).load(photos[i++]).fit().into(imageView);
        }

    }

}
