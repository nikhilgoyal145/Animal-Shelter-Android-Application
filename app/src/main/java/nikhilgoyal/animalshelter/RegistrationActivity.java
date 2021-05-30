package nikhilgoyal.animalshelter;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;

public class RegistrationActivity extends AppCompatActivity {

    private EditText email,pass;
    private Button btnReg;
    private TextView login_txt,forgotpw;

    private FirebaseAuth mAuth;
    private ProgressDialog mDialog;
    private CheckBox checkBox;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_registration);

        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,WindowManager.LayoutParams.FLAG_FULLSCREEN);

        email=(EditText) findViewById(R.id.email_reg);
        pass=(EditText) findViewById(R.id.password_reg);
        checkBox=(CheckBox)findViewById(R.id.checkbox1);

        mDialog=new ProgressDialog(this);
        mAuth=FirebaseAuth.getInstance();

        btnReg=(Button) findViewById(R.id.reg_btn);
        login_txt=(TextView) findViewById(R.id.login_txt);
        forgotpw=(TextView) findViewById(R.id.forgotpassword);

        login_txt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
//                Intent i=new Intent(getApplicationContext(),LoginActivity.class);
//                startActivity(i);

                startActivity(new Intent(RegistrationActivity.this, LoginActivity.class));
            }
        });

        btnReg.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v)
            {
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

                if(!checkBox.isChecked())
                {
                    Toast.makeText(RegistrationActivity.this,"Please Accept Terms And Conditions",Toast.LENGTH_SHORT).show();
                    return;
                }

                mDialog.setMessage("Processing.......");
                mDialog.show();

                mAuth.createUserWithEmailAndPassword(mEmail,mPass).addOnCompleteListener(new OnCompleteListener<AuthResult>()
                {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {

                        if(task.isSuccessful())
                        {
                            Toast.makeText(RegistrationActivity.this,"Registration Successfull",Toast.LENGTH_LONG).show();
                            startActivity(new Intent(RegistrationActivity.this,LoginActivity.class));
                            mDialog.dismiss();
                        }

                        else
                        {
                            Toast.makeText(RegistrationActivity.this,"Invalid E-mail And Password",Toast.LENGTH_LONG).show();
                            mDialog.dismiss();
                        }
                    }
                });
            }
        });

        checkBox.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AlertDialog.Builder alertDialog=new AlertDialog.Builder(v.getContext());
                LayoutInflater inflater=LayoutInflater.from(v.getContext());
                View view=inflater.inflate(R.layout.termsandconditions,null);
                alertDialog.setView(view);
                alertDialog.create().show();

            }
        });
//----------------------------̄---------------------------------------------------------------------------------------------------//
        //SEND LINK GMAIL TO RESET PASSWORD
//        forgotpw.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v)
//            {
//                final EditText resetMail = new EditText(RegistrationActivity.this);//we can also pass v.getContext()
//                //resetMail.setBackground(getDrawable(R.drawable.backdesign));
//                final AlertDialog.Builder passwordResetDialog = new AlertDialog.Builder(v.getContext());
//                passwordResetDialog.setTitle("Reset Password ?");
//                passwordResetDialog.setIcon(R.drawable.firebaseicon);
//                passwordResetDialog.setMessage("\nEnter Your Email To Received Reset Link.");
//                passwordResetDialog.setView(resetMail);
//
//                passwordResetDialog.setPositiveButton("SUBMIT", new DialogInterface.OnClickListener() {
//                    @Override
//                    public void onClick(DialogInterface dialog, int which) {
//                        // extract the email and send reset link
//                        String mail = resetMail.getText().toString();
//                        mAuth.sendPasswordResetEmail(mail).addOnSuccessListener(new OnSuccessListener<Void>() {
//                            @Override
//                            public void onSuccess(Void aVoid)
//                            {
//                                Toast.makeText(RegistrationActivity.this, "Reset Link Sent To Your Email.", Toast.LENGTH_SHORT).show();
//                            }
//                        }).addOnFailureListener(new OnFailureListener() {
//                            @Override
//                            public void onFailure(@NonNull Exception e) {
//                                Toast.makeText(RegistrationActivity.this, "Error ! Reset Link is Not Sent" + e.getMessage(), Toast.LENGTH_SHORT).show();
//                            }
//                        });
//
//                    }
//                });
//
//                passwordResetDialog.setNegativeButton("EXIT", new DialogInterface.OnClickListener() {
//                    @Override
//                    public void onClick(DialogInterface dialog, int which) {
//                        // close the dialog
//                    }
//                });
//
//                passwordResetDialog.create().show();
//
//            }
//        });
//--------------------------------------------------------------------------------------------------------------------------------//
    }
}