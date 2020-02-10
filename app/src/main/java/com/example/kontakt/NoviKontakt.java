package com.example.kontakt;

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

import androidx.appcompat.app.AppCompatActivity;

import com.backendless.Backendless;
import com.backendless.async.callback.AsyncCallback;
import com.backendless.exceptions.BackendlessFault;

public class NoviKontakt extends AppCompatActivity {

    Button btnNoviKontakt;
    EditText etIme, etBroj, etMail;

    private View mProgressView;
    private View mLoginFormView;
    private TextView tvLoad;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.acivity_novi_kontakt);

        etIme = findViewById(R.id.etIme);
        etBroj = findViewById(R.id.etBroj);
        etMail = findViewById(R.id.etMail);

        mLoginFormView = findViewById(R.id.login_forma);
        mProgressView = findViewById(R.id.login_progress);
        tvLoad = findViewById(R.id.tvLoad);

        btnNoviKontakt = findViewById(R.id.btnNoviKontakt);

        btnNoviKontakt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

             if (etMail.getText().toString().isEmpty() ||
                     etIme.getText().toString().isEmpty() ||
                     etBroj.getText().toString().isEmpty())
             {
                 Toast.makeText(NoviKontakt.this, "Molimo popunite sva polja!",
                         Toast.LENGTH_SHORT).show();
             }
             else
             {
                 String ime= etIme.getText().toString().trim();
                 String email = etMail.getText().toString().trim();
                 String broj = etBroj.getText().toString().trim();

                 Kontakt kontakt = new Kontakt();
                 kontakt.setIme(ime);
                 kontakt.setEmail(email);
                 kontakt.setBroj(broj);
                 kontakt.setEmail(appKlasa.korisnik.getEmail());


                 tvLoad.setText("Kreiranje novog kontakta....molimo sacekajte...");

                 Backendless.Persistence.save(kontakt, new AsyncCallback<Kontakt>() {
                     @Override
                     public void handleResponse(Kontakt response) {

                         Toast.makeText(NoviKontakt.this,
                                 "Sacuvali ste novi kontakt!",
                                 Toast.LENGTH_SHORT).show();
                         showProgress(false);
                         etIme.setText("");
                         etMail.setText("");
                         etBroj.setText("");

                     }

                     @Override
                     public void handleFault(BackendlessFault fault) {

                         Toast.makeText(NoviKontakt.this,
                                 "Greska: " + fault.getMessage(), Toast.LENGTH_SHORT).show();
                         showProgress(false);
                     }
                 });
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
