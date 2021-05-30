package nikhilgoyal.animalshelter;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.DialogInterface;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.view.WindowManager;
import android.widget.TextView;
import android.widget.Toast;

import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import java.util.ArrayList;
import java.util.Random;

import nikhilgoyal.animalshelter.Model.Data;

public class PaymentActivity extends AppCompatActivity {
    Toolbar toolbar;
    DatabaseReference mDatabase;
    FirebaseAuth mAuth;
    RecyclerView recyclerView;
    String post_key;
    String category,totaldays,state,currentdate;
    String uId;
    //ArrayList<String> list=new ArrayList<String>();
    //private DatabaseReference mCost;        //for storing the cost
    //TextView text1;

    int Amount;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_payment);

        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,WindowManager.LayoutParams.FLAG_FULLSCREEN);
        toolbar=(Toolbar)findViewById(R.id.toolbar);

        setSupportActionBar(toolbar);
        getSupportActionBar().setTitle("Booked History Page");
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        //Amount=getIntent().getExtras().getDouble("Amount");
        //text1=(TextView)findViewById(R.id.textviewamount);

        //for random no
//        int min=200;
//        int max=5000;
//        Amount = (int)(Math.random()*(max-min+1)+min);
        //text1.setText("  \u20B9 "+Amount+" /- {Including GST}");
        //------

        //
        mAuth=FirebaseAuth.getInstance();

        FirebaseUser mUser = mAuth.getCurrentUser();
        uId=mUser.getUid();

        mDatabase= FirebaseDatabase.getInstance().getReference().child("AnimalShelterApp").child(uId);
        mDatabase.keepSynced(true);

        //Recycler View
        recyclerView=findViewById(R.id.recycler);
        LinearLayoutManager layoutManager=new LinearLayoutManager(this);
        //--------------------------------------------------------------//

        layoutManager.setReverseLayout(true);
        layoutManager.setStackFromEnd(true);

        //--------------------------------------------------------------//
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(layoutManager);

        //totalmoneyamount();
    }

//    private void totalmoneyamount()
//    {
//        mCost= FirebaseDatabase.getInstance().getReference().child("Cost");
//        mCost.addValueEventListener(new ValueEventListener() {
//            @Override
//            public void onDataChange(@NonNull DataSnapshot snapshot) {
//                list.clear();
//                for(DataSnapshot snapshot1 : snapshot.getChildren())
//                {
//                        list.add(snapshot1.getValue().toString());
//                }
//            }
//
//            @Override
//            public void onCancelled(@NonNull DatabaseError error) {
//
//            }
//        });
//        double sum=0.0;
//
////        for(String a:list)
////        {
////            //sum+=Integer.parseInt(s);
////            Toast.makeText(getApplicationContext(),"s",Toast.LENGTH_SHORT).show();
////        }
//    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        if(item.getItemId()==android.R.id.home)
        {
            finish();
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    protected void onStart()
    {
        super.onStart();
        //import com.firebase.ui.database.FirebaseRecyclerAdapter; for using FirebaseRecyclerAdapter
        FirebaseRecyclerAdapter<Data,MyViewHolder> adapter=new FirebaseRecyclerAdapter<Data,MyViewHolder>
                (
                        Data.class,
                        R.layout.item_data,
                        MyViewHolder.class,
                        mDatabase

                ){
            @Override
            protected void populateViewHolder(MyViewHolder viewHolder, final Data model, final int position) {
                viewHolder.setCategory(model.getCategory());
                viewHolder.setTotaldays(model.getTotaldays());
                viewHolder.setState(model.getState());
                viewHolder.setCurrentdate(model.getCurrentdate());

                viewHolder.myview.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {

                        post_key=getRef(position).getKey();

                        category=model.getCategory();
                        totaldays=model.getTotaldays();
                        state=model.getState();
                        currentdate=model.getCurrentdate();

                        updateData();

                    }
                });
            }
        };

        recyclerView.setAdapter(adapter);
    }

    public static class MyViewHolder extends RecyclerView.ViewHolder
    {
        View myview;

        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
            myview=itemView;
        }
        public void setCategory(String category){
            TextView mTitle=myview.findViewById(R.id.category);
            mTitle.setText(category);
        }

        public void setTotaldays(String totaldays){
            TextView mNote=myview.findViewById(R.id.totaldays);
            mNote.setText(totaldays);
        }
        public void setState(String state){
            TextView state1=myview.findViewById(R.id.state);
            state1.setText(state);
        }

        public void setCurrentdate(String date){
            TextView mDate=myview.findViewById(R.id.date);
            mDate.setText(date);
        }
    }

    public void updateData()
    {
        AlertDialog.Builder mydialog=new AlertDialog.Builder(PaymentActivity.this);
        mydialog.setTitle("Delete Item?");
        mydialog.setMessage("Are You Sure You Want to Delete this Entry?\n");
        mydialog.setIcon(R.drawable.delete);
        mydialog.setCancelable(false);

        mydialog.setPositiveButton("CONFIRM", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                mDatabase.child(post_key).removeValue();
                //deleteamount();
                Toast.makeText(getApplicationContext(),"Delete Successfullly",Toast.LENGTH_SHORT).show();
                dialog.dismiss();
            }
        });

        mydialog.setNegativeButton("CANCEL", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                //perform any action
            }
        });

        AlertDialog dialog=mydialog.create();
        dialog.show();
    }

//    private void deleteamount()
//    {
//        if(category.equals("Cat"))
//        {
//            Amount-=70*(Integer.parseInt(totaldays));
//            text1.setText("  \u20B9 "+Amount+" /- {Including GST}");
//
//        }
//        else if(category.equals("Dog"))
//        {
//            Amount-=120*(Integer.parseInt(totaldays));
//            text1.setText("  \u20B9 "+Amount+" /- {Including GST}");
//        }
//        else if(category.equals("Puppy"))
//        {
//            Amount-=140*(Integer.parseInt(totaldays));
//            text1.setText("  \u20B9 "+Amount+" /- {Including GST}");
//        }
//        else if(category.equals("Birds"))
//        {
//            Amount-=180*(Integer.parseInt(totaldays));
//            text1.setText("  \u20B9 "+Amount+" /- {Including GST}");
//        }
//        else if(category.equals("Parrot"))
//        {
//            Amount-=200*(Integer.parseInt(totaldays));
//            text1.setText("  \u20B9 "+Amount+" /- {Including GST}");
//        }
//        else if(category.equals("Others"))
//        {
//            Amount-=250*(Integer.parseInt(totaldays));
//            text1.setText("  \u20B9 "+Amount+" /- {Including GST}");
//        }
//    }
}