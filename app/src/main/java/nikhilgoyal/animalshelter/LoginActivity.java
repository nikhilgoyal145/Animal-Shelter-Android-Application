package nikhilgoyal.animalshelter;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;

public class LoginActivity extends AppCompatActivity {

    private TextView signup,forgotpw;
    private TextView logintextanimation; //for animation
    private EditText email;
    private EditText pass;
    private Button btnLogin;

    private FirebaseAuth mAuth;
    private ProgressDialog mDialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        mDialog=new ProgressDialog(this);
        mAuth=FirebaseAuth.getInstance();

//        if(mAuth.getCurrentUser()!=null)
//        {
//            startActivity(new Intent(LoginActivity.this,HomeActivity.class));
//        }

        signup=(TextView) findViewById(R.id.signup_txt);
        logintextanimation=(TextView) findViewById(R.id.logintextanimation); // for animation
        email=(EditText) findViewById(R.id.email_login);
        email.setSelection(email.length());
        pass=(EditText) findViewById(R.id.password_login);
        pass.setSelection(pass.length());
        btnLogin=(Button) findViewById(R.id.login_btn);
        forgotpw=(TextView) findViewById(R.id.forgotpassword);

        //for animation--------------------------------------------------------------------

//        Animation animation = AnimationUtils.loadAnimation(getApplicationContext(),R.anim.bounce);
//        logintextanimation.startAnimation(animation);
//
//        Animation animation1=AnimationUtils.loadAnimation(getApplicationContext(),R.anim.movetop);
//        email.startAnimation(animation1);
//
//        Animation animation2=AnimationUtils.loadAnimation(getApplicationContext(),R.anim.movetop);
//        pass.startAnimation(animation2);


        //for animation--------------------------------------------------------------------


        btnLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                String mEmail=email.getText().toString().trim();
                String mPass=pass.getText().toString().trim();

                if(TextUtils.isEmpty(mEmail))
                {
                    email.setError("Required Field....");
                    return;
                }

                if(TextUtils.isEmpty(mPass))
                {
                    pass.setError("Required Field....");
                    return;
                }

                mDialog.setMessage("Processing.....");
                mDialog.show();


                mAuth.signInWithEmailAndPassword(mEmail,mPass).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if(task.isSuccessful())
                        {
                            Toast.makeText(LoginActivity.this,"Login Successfull",Toast.LENGTH_SHORT).show();
                            Intent intent= new Intent(LoginActivity.this,HomeActivity.class);
                            intent.putExtra("E-mail",mEmail);
                            startActivity(intent);
                            mDialog.dismiss();
                        }
                        else
                        {
                            Toast.makeText(LoginActivity.this,"Invalid User name or Password",Toast.LENGTH_LONG).show();
                            mDialog.dismiss();
                        }
                    }
                });

            }
        });

        signup.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v)
            {
                startActivity(new Intent(LoginActivity.this,RegistrationActivity.class));
            }
        });

        //----------------------------̄---------------------------------------------------------------------------------------------------//
        //SEND LINK TO GMAIL TO RESET PASSWORD
        forgotpw.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v)
            {
                final EditText resetMail = new EditText(LoginActivity.this);//we can also pass v.getContext()
                //resetMail.setBackground(getDrawable(R.drawable.backdesign));
                final AlertDialog.Builder passwordResetDialog = new AlertDialog.Builder(v.getContext());
                passwordResetDialog.setTitle("Reset Password ?");
                passwordResetDialog.setIcon(R.drawable.forgot);
                passwordResetDialog.setMessage("\nEnter Your Email To Received Reset Link.");
                passwordResetDialog.setView(resetMail);

                passwordResetDialog.setPositiveButton("SUBMIT", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        // extract the email and send reset link
                        String mail = resetMail.getText().toString();
                        mAuth.sendPasswordResetEmail(mail).addOnSuccessListener(new OnSuccessListener<Void>() {
                            @Override
                            public void onSuccess(Void aVoid)
                            {
                                Toast.makeText(LoginActivity.this, "Reset Link Sent To Your Email.", Toast.LENGTH_SHORT).show();
                            }
                        }).addOnFailureListener(new OnFailureListener() {
                            @Override
                            public void onFailure(@NonNull Exception e) {
                                Toast.makeText(LoginActivity.this, "Error ! Reset Link is Not Sent" + e.getMessage(), Toast.LENGTH_SHORT).show();
                            }
                        });

                    }
                });

                passwordResetDialog.setNegativeButton("EXIT", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        // close the dialog
                    }
                });

                passwordResetDialog.create().show();

            }
        });

//--------------------------------------------------------------------------------------------------------------------------------//
    }

    @Override
    public void onBackPressed() {
        finishAffinity();
        //super.onBackPressed();
    }
}