package nikhilgoyal.animalshelter;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.graphics.Paint;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

public class ChargesActivity extends AppCompatActivity {
    TextView textview;
    String email;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_charges);

        email=getIntent().getStringExtra("Name");

        textview=(TextView)findViewById(R.id.textview);
        textview.setPaintFlags(textview.getPaintFlags() | Paint.UNDERLINE_TEXT_FLAG);   // for making our text as underline text
    }

    public void DetailsofCat(View view)
    {
        Intent intent=new Intent(this,DetailsOfPet.class);
        intent.putExtra("Pet","Cat");
        intent.putExtra("Name",email);
        startActivity(intent);
        finish();
    }

    public void DetailsofDog(View view)
    {
        Intent intent=new Intent(this,DetailsOfPet.class);
        intent.putExtra("Pet","Dog");
        intent.putExtra("Name",email);
        startActivity(intent);
        finish();
    }

    public void DetailsofParrot(View view)
    {
        Intent intent=new Intent(this,DetailsOfPet.class);
        intent.putExtra("Pet","Parrot");
        intent.putExtra("Name",email);
        startActivity(intent);
        finish();
    }

    public void DetailsofRabbit(View view)
    {
        Intent intent=new Intent(this,DetailsOfPet.class);
        intent.putExtra("Pet","Rabbit");
        intent.putExtra("Name",email);
        startActivity(intent);
        finish();
    }

    @Override
    public void onBackPressed()
    {
        super.onBackPressed();
    }
}