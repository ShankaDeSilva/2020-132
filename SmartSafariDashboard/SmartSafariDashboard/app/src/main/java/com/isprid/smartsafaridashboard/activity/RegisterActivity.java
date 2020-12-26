package com.isprid.smartsafaridashboard.activity;

import android.app.ProgressDialog;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

//import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.isprid.smartsafaridashboard.R;
import com.isprid.smartsafaridashboard.model.User;
import com.isprid.smartsafaridashboard.util.Constant;
import com.isprid.smartsafaridashboard.util.PrefUtils;

public class RegisterActivity extends AppCompatActivity implements AdapterView.OnItemSelectedListener {

    private static final String TAG = RegisterActivity.class.getSimpleName();
    private PrefUtils pref;
    private DatabaseReference databaseUser;
    private EditText edtFname;
    private EditText edtEmail;
    private EditText edtPassword;
    private EditText edtConfirmPassword;
    private EditText edtPhone;
    private FirebaseAuth mAuth;
    private ProgressDialog dialog;
    private Spinner spinner;
    private String selectedRole = "Guide";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        setTitle("Register");

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);

        pref = new PrefUtils(this);

        mAuth = FirebaseAuth.getInstance();
        databaseUser = FirebaseDatabase.getInstance().getReference("Users");

        edtFname = findViewById(R.id.edtFname);
        edtEmail = findViewById(R.id.edtEmail);
        edtPhone = findViewById(R.id.edtPhone);
        edtPassword = findViewById(R.id.edtPassword);
        edtConfirmPassword = findViewById(R.id.edtConfirmPassword);
        Button signUpBtn = findViewById(R.id.signUpBtn);
        TextView already_user = findViewById(R.id.already_user);

        spinner = (Spinner) findViewById(R.id.spinner2);
        spinner.setOnItemSelectedListener(this);


        signUpBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String fullName = edtFname.getText().toString().trim();
                String emailAddress = edtEmail.getText().toString().trim();
                String phone = edtPhone.getText().toString().trim();
                String password = edtPassword.getText().toString().trim();
                String confirmPassword = edtConfirmPassword.getText().toString().trim();

                if (password.length() < 6) {
                    Toast.makeText(RegisterActivity.this, "Password should have minimum 6 characters", Toast.LENGTH_LONG).show();
                    return;
                }

                if (!password.equals(confirmPassword)) {
                    Toast.makeText(RegisterActivity.this, "Passwords do not matched", Toast.LENGTH_LONG).show();
                    return;
                }

                if (TextUtils.isEmpty(fullName) || TextUtils.isEmpty(emailAddress) || TextUtils.isEmpty(phone) || TextUtils.isEmpty(password)) {
                    Toast.makeText(RegisterActivity.this, "Please fill all fields", Toast.LENGTH_LONG).show();
                    return;
                }

                User newUser = new User(fullName, emailAddress, phone, password, selectedRole);
                registerNewUser(newUser);
                dialog = new ProgressDialog(RegisterActivity.this);
                dialog.setMessage("Please wait...");
                dialog.show();
            }
        });

        already_user.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getApplicationContext(), LoginActivity.class));
                finish();
            }
        });
    }


    /**
     * Register new user with typed details
     * @param newUser
     */
    private void registerNewUser(final User newUser) {
        mAuth.createUserWithEmailAndPassword(newUser.getEmail(), newUser.getPassword()).addOnCompleteListener(RegisterActivity.this, new OnCompleteListener<AuthResult>() {
            @Override
//            public void onComplete(@NonNull Task<AuthResult> task)
            public void onComplete( Task<AuthResult> task) {

                if (task.isSuccessful()) {
                    // Sign in success, update UI with the signed-in authCurrentUser's information
                    Log.d(TAG, "createUserWithEmail:success");
                    FirebaseUser authCurrentUser = mAuth.getCurrentUser();

                    try {
                        if (authCurrentUser != null) {
                            newUser.setUid(authCurrentUser.getUid());
                            newUser.setPassword(null);

                            saveUserDetails(newUser);
                        } else {
                            Toast.makeText(RegisterActivity.this, "Something went wrong.", Toast.LENGTH_SHORT).show();
                        }


                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                } else {
                    // If sign in fails, display a message to the user.
                    Log.w(TAG, "createUserWithEmail:failure", task.getException());
                    Toast.makeText(RegisterActivity.this, "Authentication failed.", Toast.LENGTH_SHORT).show();

                    if (task.getException() != null) {
                        Log.e(TAG, task.getException().getMessage());
                        Toast.makeText(RegisterActivity.this, task.getException().getMessage(), Toast.LENGTH_SHORT).show();
                    }

                }

            }
        });
    }

    /**
     * Save user detail to firebase
     * @param newUser
     */
    private void saveUserDetails(User newUser) {
        //getting a unique id using push().getKey() method
        //it will create a unique id and we will use it as the Primary Key for row
        //Saving the record
        databaseUser.child(newUser.getUid()).setValue(newUser);

        pref.savePrefsValue(Constant.UUID, newUser.getUid());

        //setting edittext to blank again
        clearFields();

        if (dialog != null && dialog.isShowing()) {
            dialog.dismiss();
        }

        //displaying a success toast
        Toast.makeText(this, "User Successfully registered", Toast.LENGTH_LONG).show();
        startActivity(new Intent(getApplicationContext(), LoginActivity.class));
        finish();

    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        if (id == android.R.id.home) {
            onBackPressed();
        }

        return super.onOptionsItemSelected(item);
    }

    private void clearFields() {
        edtFname.setText("");
        edtEmail.setText("");
        edtPassword.setText("");
        edtConfirmPassword.setText("");
    }

    @Override
    public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
        ((TextView) spinner.getChildAt(0)).setTextColor(Color.WHITE);
        selectedRole = spinner.getItemAtPosition(i).toString();
    }

    @Override
    public void onNothingSelected(AdapterView<?> adapterView) {

    }
}
