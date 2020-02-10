package com.example.kontakt;

import androidx.appcompat.app.AppCompatActivity;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.annotation.TargetApi;
import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.backendless.Backendless;
import com.backendless.BackendlessUser;
import com.backendless.async.callback.AsyncCallback;
import com.backendless.exceptions.BackendlessFault;

public class Registracija extends AppCompatActivity {

    private View mProgressView;
    private View mLoginFormView;
    private TextView tvLoad;

    EditText etIme, etMail, etPassword, etReEnter;
    Button btnRegistracija;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_registracija);

        mLoginFormView = findViewById(R.id.login_forma);
        mProgressView = findViewById(R.id.login_progress);
        tvLoad = findViewById(R.id.tvLoad);

        etIme = findViewById(R.id.etIme);
        etMail = findViewById(R.id.etMail);
        etPassword = findViewById(R.id.etPassword);
        etReEnter = findViewById(R.id.etReEnter);
        btnRegistracija = findViewById(R.id.btnRegistracija);

        btnRegistracija.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                if (etIme.getText().toString().isEmpty() || etMail.getText().toString().isEmpty() || etPassword.getText().toString().isEmpty() ||
                        etReEnter.getText().toString().isEmpty())
                {
                    Toast.makeText(Registracija.this, "Molimo unesite sve detalje!", Toast.LENGTH_SHORT).show();
                }
                else
                {
                    if (etPassword.getText().toString().trim().equals(etReEnter.getText().toString().trim()))
                    {
                        String ime = etIme.getText().toString().trim();
                        String email = etMail.getText().toString().trim();
                        String password = etPassword.getText().toString().trim();

                        BackendlessUser user = new BackendlessUser();
                        user.setEmail(email);
                        user.setPassword(password);
                        user.setProperty("ime", ime);

                        showProgress(true);
                        tvLoad.setText("Registrovanje...molimo sacekajte...");

                        Backendless.UserService.register(user, new AsyncCallback<BackendlessUser>() {
                            @Override
                            public void handleResponse(BackendlessUser response) {

                                Toast.makeText(Registracija.this, "Korisnik uspjesno registrovan!", Toast.LENGTH_SHORT).show();

                                Registracija.this.finish();

                            }

                            @Override
                            public void handleFault(BackendlessFault fault) {

                                Toast.makeText(Registracija.this, "Greska: " + fault.getMessage(), Toast.LENGTH_SHORT).show();
                                showProgress(false);
                            }
                        });

                    }
                    else
                    {
                        Toast.makeText(Registracija.this, "Molimo da provjerite da li su oba passworda ista!", Toast.LENGTH_SHORT).show();
                    }
                }

            }
        });


    }

    /**
     * Shows the progress UI and hides the login form.
     */
    @TargetApi(Build.VERSION_CODES.HONEYCOMB_MR2)
    private void showProgress(final boolean show) {
        // On Honeycomb MR2 we have the ViewPropertyAnimator APIs, which allow
        // for very easy animations. If available, use these APIs to fade-in
        // the progress spinner.
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.HONEYCOMB_MR2) {
            int shortAnimTime = getResources().getInteger(android.R.integer.config_shortAnimTime);

            mLoginFormView.setVisibility(show ? View.GONE : View.VISIBLE);
            mLoginFormView.animate().setDuration(shortAnimTime).alpha(
                    show ? 0 : 1).setListener(new AnimatorListenerAdapter() {
                @Override
                public void onAnimationEnd(Animator animation) {
                    mLoginFormView.setVisibility(show ? View.GONE : View.VISIBLE);
                }
            });

            mProgressView.setVisibility(show ? View.VISIBLE : View.GONE);
            mProgressView.animate().setDuration(shortAnimTime).alpha(
                    show ? 1 : 0).setListener(new AnimatorListenerAdapter() {
                @Override
                public void onAnimationEnd(Animator animation) {
                    mProgressView.setVisibility(show ? View.VISIBLE : View.GONE);
                }
            });

            tvLoad.setVisibility(show ? View.VISIBLE : View.GONE);
            tvLoad.animate().setDuration(shortAnimTime).alpha(
                    show ? 1 : 0).setListener(new AnimatorListenerAdapter() {
                @Override
                public void onAnimationEnd(Animator animation) {
                    tvLoad.setVisibility(show ? View.VISIBLE : View.GONE);
                }
            });
        } else {
            // The ViewPropertyAnimator APIs are not available, so simply show
            // and hide the relevant UI components.
            mProgressView.setVisibility(show ? View.VISIBLE : View.GONE);
            tvLoad.setVisibility(show ? View.VISIBLE : View.GONE);
            mLoginFormView.setVisibility(show ? View.GONE : View.VISIBLE);
        }
    }

}
