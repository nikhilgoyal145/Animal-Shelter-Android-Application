package nikhilgoyal.animalshelter;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.view.WindowManager;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;

import com.bumptech.glide.Glide;

import java.time.Instant;

public class MainActivity extends AppCompatActivity {
    ImageView imageView,imageviewhomepage;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,WindowManager.LayoutParams.FLAG_FULLSCREEN);

        imageviewhomepage=(ImageView)findViewById(R.id.imageviewhomepage);
        Glide.with(this).load(R.drawable.loadingimage).into(imageviewhomepage);

        imageView=(ImageView)findViewById(R.id.imageview);

        Animation animation = AnimationUtils.loadAnimation(getBaseContext(),R.anim.fadein);
        imageView.startAnimation(animation);

        int SPLASH_TIME_OUT = 5000;
        new Handler().postDelayed(new Runnable()
        {
            /*
             * Showing splash screen with a timer. This will be useful when you
             * want to show case your app logo / company
             */
            @Override
            public void run()
            {
                imageView.clearAnimation();
                startActivity(new Intent(getApplicationContext(),LoginActivity.class));
            }
        }, SPLASH_TIME_OUT);
    }
}