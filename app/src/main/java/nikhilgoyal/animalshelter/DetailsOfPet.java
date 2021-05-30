package nikhilgoyal.animalshelter;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.graphics.Paint;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.view.WindowManager;
import android.widget.TextView;
import android.widget.Toast;

public class DetailsOfPet extends AppCompatActivity {
    TextView aboutus,pettitle,petintroduction;
    String email;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_details_of_pet);

        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,WindowManager.LayoutParams.FLAG_FULLSCREEN);

        aboutus=(TextView)findViewById(R.id.aboutus);
        pettitle=(TextView)findViewById(R.id.pettitle);
        petintroduction=(TextView)findViewById(R.id.petintroduction);

        aboutus.setPaintFlags(aboutus.getPaintFlags()| Paint.UNDERLINE_TEXT_FLAG);
        pettitle.setPaintFlags(pettitle.getPaintFlags()| Paint.UNDERLINE_TEXT_FLAG);

        String title=getIntent().getExtras().getString("Pet");
        email=getIntent().getStringExtra("Name");

        if(title.equals("Cat"))
        {
            pettitle.setText("CAT");
            petintroduction.setText("Cats, also called domestic cats (Felis catus), are small, " +
                    "carnivorous (meat-eating) mammals, of the family Felidae.\n" +
                    "\n" +"Domestic cats are often called 'house cats' when kept as indoor pets. " +
                    "Cats have been domesticated (tamed) for nearly 10,000 years.\n" +
                    "\n" +"They are one of the most popular pets in the world. They are kept by " +
                    "humans for hunting rodents and as companions.\n" +
                    "\n" +"There are also farm cats, which are kept on farms to keep rodents away; " +
                    "and feral cats, which are domestic cats that live away from humans.\n" +
                    "\n" +"A cat is sometimes called a kitty. A young cat is called a kitten. " +
                    "A female cat that has not had its sex organs removed is called a queen. " +
                    "A male cat that has not had its sex organs removed is called a tom. ");
        }

        if(title.equals("Dog"))
        {
            pettitle.setText("DOG");
            petintroduction.setText("Dogs (Canis lupus familiaris) are domesticated mammals, not natural wild animals. " +
                    "They were originally bred from wolves. They have been bred by humans for a long time, and were the " +
                    "first animals ever to be domesticated. There are different studies that suggest that this happened " +
                    "between 15.000 and 100.000 years before our time. The dingo is also a dog, but many dingos have become " +
                    "wild animals again and live independently of humans in the range where they occur (parts of Australia). ");
        }

        if(title.equals("Rabbit"))
        {
            pettitle.setText("RABBIT");
            petintroduction.setText("Rabbits are small mammals in the family Leporidae of the order Lagomorpha " +
                    "(along with the hare and the pika). Oryctolagus cuniculus includes the European " +
                    "rabbit species and its descendants, the world's 305 breeds[1] of domestic rabbit. " +
                    "Sylvilagus includes 13 wild rabbit species, among them the seven types of cottontail. " +
                    "The European rabbit, which has been introduced on every continent except Antarctica, is " +
                    "familiar throughout the world as a wild prey animal and as a domesticated form of livestock and pet. ");
        }

        if(title.equals("Parrot"))
        {
            pettitle.setText("PARROT");
            petintroduction.setText("Parrots, also known as psittacines, are birds of the roughly 398 " +
                    "species in 92 genera comprising the order Psittaciformes, found mostly in tropical " +
                    "and subtropical regions. The order is subdivided into three superfamilies: " +
                    "the Psittacoidea, the Cacatuoidea, and the Strigopoidea. Characteristic features " +
                    "of parrots include a strong, curved bill, an upright stance, strong legs, and clawed " +
                    "zygodactyl feet. Many parrots are vividly coloured, and some are multi-coloured.");
        }
    }

    public void backbutton(View view)
    {
        startActivity(new Intent(this,ChargesActivity.class));
    }

    public void homebutton(View view)
    {
        Intent i=new Intent(this,HomeActivity.class);
        i.putExtra("E-mail",email);
        startActivity(i);
    }

    @Override
    public void onBackPressed() {
        //Toast.makeText(DetailsOfPet.this,"Please Use Above Button For Back",Toast.LENGTH_SHORT).show();
        super.onBackPressed();
    }
}