package nikhilgoyal.animalshelter;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.graphics.Paint;
import android.os.Bundle;
import android.view.View;
import android.view.WindowManager;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;

import de.hdodenhof.circleimageview.CircleImageView;

public class HomeActivity extends AppCompatActivity {
    TextView welcome,booking,charges,payment,catalogue;
    CircleImageView circleImageView;
    FirebaseAuth mAuth;
    String email, mEmail;
    double Amount;
    private long BackPressedTime;
    Toast backToast;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);

        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN , WindowManager.LayoutParams.FLAG_FULLSCREEN);

        mAuth=FirebaseAuth.getInstance();

        welcome=(TextView)findViewById(R.id.welcome);
        catalogue=(TextView)findViewById(R.id.catalogue);
        catalogue.setPaintFlags(catalogue.getPaintFlags()| Paint.UNDERLINE_TEXT_FLAG);

        circleImageView=(CircleImageView)findViewById(R.id.circleimageview);

        email=getIntent().getStringExtra("E-mail");
        //int l=email.length();
        //mEmail=email.substring(0,(email.length()-10));
        welcome.setText("Welcome, "+email);
        //Amount=getIntent().getExtras().getDouble("Amount");

        //----------------------------------------------------------------------
        booking=(TextView)findViewById(R.id.booking);
        charges=(TextView)findViewById(R.id.charges);
        payment=(TextView)findViewById(R.id.Payment);

        booking.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i=new Intent(getApplicationContext(),BookingActivity.class);
                i.putExtra("Name",email);
                startActivity(i);
            }
        });

        charges.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i=new Intent(getApplicationContext(),ChargesActivity.class);
                i.putExtra("Name",email);
                startActivity(i);
            }
        });

        payment.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(HomeActivity.this,PaymentActivity.class);
                intent.putExtra("Amount",Amount);
                startActivity(intent);
            }
        });

        circleImageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mAuth.signOut();
                Toast.makeText(HomeActivity.this,"Logout Successfull",Toast.LENGTH_SHORT).show();
                startActivity(new Intent(HomeActivity.this,LoginActivity.class));
            }
        });
    }

    @Override
    public void onBackPressed()
    {
        if(BackPressedTime+2000>System.currentTimeMillis())
        {
            finishAffinity();
            backToast.cancel();
            return;
        }
        else
        {
            backToast=Toast.makeText(getApplicationContext(),"Press Back Again to Exit",Toast.LENGTH_SHORT);
            backToast.show();
        }
        BackPressedTime=System.currentTimeMillis();
    }
}