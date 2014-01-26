package edu.scu.engr.acm.locationater.activities;

import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import edu.scu.engr.acm.locationater.R;
import edu.scu.engr.acm.locationater.util.Constants;
import edu.scu.engr.acm.locationater.util.ServerComm;

/**
 * Created: vincente on 1/25/14.
 */
public class SignUpActivity extends Activity {

    EditText emailEdit, passwordEdit, firstNameEdit, lastNameEdit;
    Button submitButton;

    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_signup);

        Bundle extras = super.getIntent().getExtras();

        emailEdit = (EditText) findViewById(R.id.signup_edit_email);
        passwordEdit = (EditText) findViewById(R.id.signup_edit_password);
        firstNameEdit = (EditText) findViewById(R.id.signup_edit_name_first);
        lastNameEdit = (EditText) findViewById(R.id.signup_edit_name_last);

        submitButton = (Button) findViewById(R.id.signupButton);
        submitButton.setOnClickListener(submitListener);

        emailEdit.setText(extras.getString(Constants.USER_EMAIL));
        passwordEdit.setText(extras.getString(Constants.USER_PASSWORD));
        emailEdit.setEnabled(false);
    }

    View.OnClickListener submitListener = new View.OnClickListener() {

        @Override
        public void onClick(View v) {
            if (Constants.DEBUGGING)
                Log.i("SignUpActivity", "Inside Click!");

            ServerComm comm = new ServerComm();
            boolean result = comm.createUser(emailEdit.getText().toString(), passwordEdit.getText().toString(),
                    firstNameEdit.getText().toString(), lastNameEdit.getText().toString(), getApplication());

            if (Constants.DEBUGGING)
                Log.i("SignUpActivity", "Create user result: " + result);

            if (result) {
                SharedPreferences sp = getSharedPreferences("cred", MODE_PRIVATE);
                sp.edit().putString(Constants.USER_EMAIL, emailEdit.getText().toString())
                        .putString(Constants.USER_PASSWORD, passwordEdit.getText().toString()).commit();

                Intent i = new Intent(getApplication(), MainActivity.class);
                finish();
                startActivity(i);
            } else {
                Toast.makeText(getApplication(), getResources().getString(R.string.signup_fail), Toast.LENGTH_SHORT).show();
            }
        }
    };
}