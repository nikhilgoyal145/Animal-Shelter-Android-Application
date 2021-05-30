package nikhilgoyal.animalshelter;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

import nikhilgoyal.animalshelter.Model.Data;

public class BookingActivity extends AppCompatActivity {
    Spinner spinner1,spinner2,spinner3;
    String s,p,state,currentdate,others,m="";
    ArrayAdapter<String> arrayAdapter;
    ArrayAdapter<String> arrayAdapter1;
    ArrayAdapter<String> arrayAdapter2;

    TextView todayDate;
    private DatabaseReference mDatabase;
    private DatabaseReference mCost;        //for storing the cost
    FirebaseAuth mAuth;
    double amount;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_booking);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,WindowManager.LayoutParams.FLAG_FULLSCREEN);

        todayDate=(TextView)findViewById(R.id.todayDate);
        mAuth=FirebaseAuth.getInstance();

        SimpleDateFormat formatter = new SimpleDateFormat("dd/MM/yyyy");
        Date date = new Date();
        currentdate=formatter.format(date);
        todayDate.setText(currentdate);

        //firebase details
        FirebaseUser mUser = mAuth.getCurrentUser();
        final String uId=mUser.getUid();

        mDatabase= FirebaseDatabase.getInstance().getReference().child("AnimalShelterApp").child(uId);
        mDatabase.keepSynced(true);

        mCost=FirebaseDatabase.getInstance().getReference().child("Cost");
        mCost.keepSynced(true);
        //

        spinner1=(Spinner)findViewById(R.id.spinner1);
        String type[]={" Select Your Category","Cat","Dog","Puppy","Birds","Parrot","Others"};
        arrayAdapter=new ArrayAdapter<String>(this,R.layout.support_simple_spinner_dropdown_item,type);
        spinner1.setAdapter(arrayAdapter);

        spinner1.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                s=adapterView.getItemAtPosition(i).toString();
                if(s.equals("Others"))
                {
                    EditText editText=new EditText(BookingActivity.this);
                    //editText.setBackground(getDrawable(R.drawable.imagebutton1));
                    editText.setText(m);
                    AlertDialog.Builder alertDialog=new AlertDialog.Builder(BookingActivity.this);
                    alertDialog.setTitle("Mention Your Pet Category");
                    alertDialog.setIcon(R.drawable.icon);
                    alertDialog.setMessage("Write your Category Name");
                    alertDialog.setView(editText);
                    alertDialog.setPositiveButton("SUBMIT", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            m=editText.getText().toString().trim();
                            s=m;
                        }
                    });

                    alertDialog.setNegativeButton("CANCEL", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            //add your action
                        }
                    });
                    alertDialog.create().show();
                }
            }
            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {
            }
        });

        //=-----------------------------Spinner 2 =---------------------------------------=----------------------------------------------

        spinner2=(Spinner)findViewById(R.id.spinner2);
        String type1[]={" Select No. Of Days","1","2","3","4","5","6","7","8","9","10","11","12","13","14","15","16","17","18","19","20"};
        arrayAdapter1=new ArrayAdapter<String>(this,R.layout.support_simple_spinner_dropdown_item,type1);
        spinner2.setAdapter(arrayAdapter1);

        spinner2.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                p=adapterView.getItemAtPosition(i).toString();
                }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {
            }
        });

        //=-----------------------------Spinner 3 =---------------------------------------=----------------------------------------------

        spinner3=(Spinner)findViewById(R.id.spinner3);
        String type2[]={" Select Your State","Haryana","Maharashtra","Manipur","Odisha","Punjab","Rajasthan","Uttar Pradesh","Uttarakhand","West Bengal"};
        arrayAdapter2=new ArrayAdapter<String>(this,R.layout.support_simple_spinner_dropdown_item,type2);
        spinner3.setAdapter(arrayAdapter2);

        spinner3.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                state=adapterView.getItemAtPosition(i).toString();
//                if(i!=0)
//                {
//                    Toast.makeText(getBaseContext(),adapterView.getItemAtPosition(i)+" is Selected",Toast.LENGTH_LONG).show();
//                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {
            }
        });
    }

    public void bookingconfirmed(View view)
    {
        if(s.compareTo(" Select Your Category")==0)
        {
            Toast.makeText(getBaseContext(),"Please Select Your Pet Category",Toast.LENGTH_SHORT).show();
        }

        else if(p.compareTo(" Select No. Of Days")==0)
        {
            Toast.makeText(getBaseContext(),"Please Select Your No. Of Days",Toast.LENGTH_SHORT).show();
        }

        else if(state.compareTo(" Select Your State")==0)
        {
            Toast.makeText(getBaseContext(),"Please Select Your State",Toast.LENGTH_SHORT).show();
        }

        else
        {
            String id=mDatabase.push().getKey();
            Data data=new Data(s,p,state,currentdate);
            mDatabase.child(id).setValue(data);
            forcost();
            correctdesigning();
            Toast.makeText(getBaseContext(),"Booking Confirmed",Toast.LENGTH_LONG).show();
        }
    }

    private void forcost()
    {
        if(s.equals("Cat"))
        {
            amount+=70*(Integer.parseInt(p));
        }
        else if(s.equals("Dog"))
        {
            amount+=120*(Integer.parseInt(p));
        }
        else if(s.equals("Puppy"))
        {
            amount+=140*(Integer.parseInt(p));
        }
        else if(s.equals("Birds"))
        {
            amount+=180*(Integer.parseInt(p));
        }
        else if(s.equals("Parrot"))
        {
            amount+=200*(Integer.parseInt(p));
        }
        else if(s.equals("Others"))
        {
            amount+=250*(Integer.parseInt(p));
        }
        //String id=mCost.push().getKey();
        //mCost.child(id).setValue(amount);
        //Toast.makeText(getBaseContext(),"AMOUNT ADDED",Toast.LENGTH_LONG).show();
    }

    public void correctdesigning()
    {
        AlertDialog.Builder alertDialog1=new AlertDialog.Builder(BookingActivity.this);
        LayoutInflater inflater = LayoutInflater.from(BookingActivity.this);
        View myview = inflater.inflate(R.layout.alertcorrectsign, null);

        ImageView imageView = myview.findViewById(R.id.imageView);
        Glide.with(this).load(R.drawable.correcttick).into(imageView);
        alertDialog1.setView(myview);
        alertDialog1.setCancelable(false);

        Dialog dialog=alertDialog1.create();
        dialog.show();

        int SPLASH_TIME_OUT=2500;
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run()
            {
                dialog.dismiss();
                String mEmail= getIntent().getStringExtra("Name");
                Intent i=new Intent(getApplicationContext(),HomeActivity.class);
                i.putExtra("E-mail",mEmail);
                i.putExtra("Amount",amount);
                startActivity(i);
            }
        },SPLASH_TIME_OUT);

    }
}