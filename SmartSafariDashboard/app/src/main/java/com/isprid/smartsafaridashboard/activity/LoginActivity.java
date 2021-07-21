package com.isprid.smartsafaridashboard.activity;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.isprid.smartsafaridashboard.R;
import com.isprid.smartsafaridashboard.model.User;
import com.isprid.smartsafaridashboard.util.Constant;
import com.isprid.smartsafaridashboard.util.PrefUtils;


public class LoginActivity extends AppCompatActivity {

    private EditText edt_email;
    private EditText edt_password;
    private DatabaseReference databaseUsers;
    private PrefUtils pref;
    private FirebaseAuth mAuth;
    private ProgressDialog dialog;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        //initialize firebase instance
        mAuth = FirebaseAuth.getInstance();
        databaseUsers = FirebaseDatabase.getInstance().getReference("Users");
        pref = new PrefUtils(this);

        //if user logged in then redirect to the dashboards
        if (pref.checkFromPrefs(Constant.UUID)
                && pref.checkFromPrefs(Constant.IS_LOGGED_IN)
                && pref.checkFromPrefs(Constant.PHONE)
                && pref.getPrefsValue(Constant.UUID) != null
                && pref.getPrefsValue(Constant.PHONE) != null
                && pref.getPrefsValue(Constant.IS_LOGGED_IN).equals("1")) {
            redirectToHome();
        }

        //initialize UI components
        edt_email = findViewById(R.id.login_emailid);
        edt_password = findViewById(R.id.login_password);
        TextView createAccount = findViewById(R.id.createAccount);
        Button loginBtn = findViewById(R.id.loginBtn);

        //defaults login credentials
//        edt_email.setText("asd@gmail.com");
//        edt_password.setText("123456");


        //login button click event
        loginBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                String emailAddress = edt_email.getText().toString().trim();
                String password = edt_password.getText().toString().trim();

                if (TextUtils.isEmpty(emailAddress)) {
                    edt_email.setError("Please fill email fields");
                    return;
                }

                if (TextUtils.isEmpty(password)) {
                    edt_password.setError("Please fill password fields");
                    return;
                }

                validateUser(emailAddress, password);

                //display progress dialog while validating
                dialog = new ProgressDialog(LoginActivity.this);
                dialog.setMessage("Please wait...");
                dialog.show();
            }
        });

        createAccount.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getApplicationContext(), RegisterActivity.class));
            }
        });


    }


    /**
     * Validate user credentials with firebase server
     * @param emailAddress
     * @param password
     */
    private void validateUser(final String emailAddress, final String password) {
        mAuth.signInWithEmailAndPassword(emailAddress, password)
                .addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (dialog != null && dialog.isShowing()) {
                            dialog.dismiss();
                        }
                        if (task.isSuccessful()) {
                            FirebaseUser authCurrentUser = mAuth.getCurrentUser();

                            try {
                                //check current user is null or not
                                if (authCurrentUser != null) {
                                    pref.savePrefsValue(Constant.UUID, authCurrentUser.getUid());
                                    pref.savePrefsValue(Constant.IS_LOGGED_IN, "1");
                                    fetchUserDetails(authCurrentUser.getUid());
                                } else {
                                    Toast.makeText(LoginActivity.this, "Something went wrong.", Toast.LENGTH_SHORT).show();
                                }


                            } catch (Exception e) {
                                e.printStackTrace();
                            }
                        } else {
                            Toast.makeText(getApplicationContext(), "User credentials are wrong: " + task.getException().getMessage(), Toast.LENGTH_LONG).show();
                        }

                    }
                });
    }


    /**
     * After successful login, fetch the user details from firebase and save it in shared preferences
     * and redirect to the dashboard
     * @param uid
     */
    private void fetchUserDetails(final String uid) {
        databaseUsers.child(uid).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                try {
                    User user = dataSnapshot.getValue(User.class);
                    if (user != null && user.getEmail() != null) {
                        //save data in shared preferences
                        pref.savePrefsValue(Constant.EMAIL, user.getEmail());
                        pref.savePrefsValue(Constant.NAME, user.getFname());
                        pref.savePrefsValue(Constant.PHONE, user.getPhone());
                        pref.savePrefsValue(Constant.ROLE, user.getRole());
                        redirectToHome();

                    } else {
                        Toast.makeText(LoginActivity.this, "User not found", Toast.LENGTH_SHORT).show();
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
                System.out.println(databaseError.getMessage());
            }
        });

    }

    /**
     * Navigate to the dashboard
     */
    private void redirectToHome() {
        startActivity(new Intent(getApplicationContext(), MainActivity.class));
        finish();

    }
}
