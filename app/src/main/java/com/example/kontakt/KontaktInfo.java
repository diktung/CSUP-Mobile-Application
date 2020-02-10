package com.example.kontakt;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.annotation.TargetApi;
import android.content.DialogInterface;
import android.content.Intent;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import com.backendless.Backendless;
import com.backendless.async.callback.AsyncCallback;
import com.backendless.exceptions.BackendlessFault;

public class KontaktInfo extends AppCompatActivity {

    TextView tvChar, tvIme;
    ImageView ivPoziv, ivMail, ivIzmjena, ivBrisanje;
    EditText etIme, etMail, etTel;
    Button btnPosalji;

    private View mProgressView;
    private View mLoginFormView;
    private TextView tvLoad;

    boolean edit = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_kontakt_info);

        mLoginFormView = findViewById(R.id.login_form);
        mProgressView = findViewById(R.id.login_progress);
        tvLoad = findViewById(R.id.tvLoad);

        tvChar = findViewById(R.id.tvChar);
        tvIme = findViewById(R.id.tvIme);

        ivPoziv = findViewById(R.id.ivPoziv);
        ivMail = findViewById(R.id.ivMail);
        ivIzmjena = findViewById(R.id.ivIspravi);
        ivBrisanje = findViewById(R.id.ivBrisanje);

        etIme = findViewById(R.id.etIme);
        etMail = findViewById(R.id.etMail);
        etTel = findViewById(R.id.etTel);

        btnPosalji = findViewById(R.id.btnIspravi);

        etIme.setVisibility(View.GONE);
        etMail.setVisibility(View.GONE);
        etTel.setVisibility(View.GONE);
        btnPosalji.setVisibility(View.GONE);

        final int index = getIntent().getIntExtra("index", 0);

        etIme.setText(appKlasa.kontakt.get(index).getIme());
        etMail.setText(appKlasa.kontakt.get(index).getEmail());
        etTel.setText(appKlasa.kontakt.get(index).getBroj());

        tvChar.setText(appKlasa.kontakt.get(index).getIme().toUpperCase().charAt(0)+"");
        tvIme.setText(appKlasa.kontakt.get(index).getIme());

        ivPoziv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                String uri = "tel:" + appKlasa.kontakt.get(index).getBroj();
                Intent intent = new Intent(Intent.ACTION_DIAL);
                intent.setData(Uri.parse(uri));
                startActivity(intent);

            }
        });

        ivMail.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Intent intent = new Intent(Intent.ACTION_SEND);
                intent.setType("text/html");
                intent.putExtra(Intent.EXTRA_EMAIL, appKlasa.kontakt.get(index).getEmail());
                startActivity(Intent.createChooser(intent,
                        "Posalji email " + appKlasa.kontakt.get(index).getIme()));

            }
        });

        ivIzmjena.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                edit = !edit;

                if (edit)
                {
                    etIme.setVisibility(View.VISIBLE);
                    etMail.setVisibility(View.VISIBLE);
                    etTel.setVisibility(View.VISIBLE);
                    btnPosalji.setVisibility(View.VISIBLE);
                }
                else
                {
                    etIme.setVisibility(View.GONE);
                    etMail.setVisibility(View.GONE);
                    etTel.setVisibility(View.GONE);
                    btnPosalji.setVisibility(View.GONE);
                }

            }
        });

        ivBrisanje.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                final AlertDialog.Builder dialog = new AlertDialog.Builder(KontaktInfo.this);
                dialog.setMessage("Da li zelite da obrisete ovaj kontakt?");

                dialog.setPositiveButton("DA", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {

                        showProgress(true);
                        tvLoad.setText("Brisanje kontakta...molimo sacekajte...");

                        Backendless.Persistence.of(Kontakt.class).remove(appKlasa.kontakt.get(index),
                                new AsyncCallback<Long>() {
                                    @Override
                                    public void handleResponse(Long response) {

                                        appKlasa.kontakt.remove(index);
                                        Toast.makeText(KontaktInfo.this,
                                                "Kontakt uspjesno izbrisan!", Toast.LENGTH_SHORT).show();
                                        setResult(RESULT_OK);
                                        KontaktInfo.this.finish();

                                    }

                                    @Override
                                    public void handleFault(BackendlessFault fault) {

                                        Toast.makeText(KontaktInfo.this, "Error: " + fault.getMessage(),
                                                Toast.LENGTH_SHORT).show();
                                    }
                                });

                    }
                });

                dialog.setNegativeButton("NE", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {

                    }
                });

                dialog.show();

            }
        });

        btnPosalji.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                if (etIme.getText().toString().isEmpty() ||
                        etMail.getText().toString().isEmpty() ||
                        etTel.getText().toString().isEmpty())
                {
                    Toast.makeText(KontaktInfo.this, "Please enter all details!",
                            Toast.LENGTH_SHORT).show();
                }
                else
                {
                    appKlasa.kontakt.get(index).setIme(etIme.getText().toString().trim());
                    appKlasa.kontakt.get(index).setBroj(etTel.getText().toString().trim());
                    appKlasa.kontakt.get(index).setEmail(etMail.getText().toString().trim());

                    showProgress(true);
                    tvLoad.setText("Updating contact...please wait...");

                    Backendless.Persistence.save(appKlasa.kontakt.get(index), new AsyncCallback<Kontakt>() {
                        @Override
                        public void handleResponse(Kontakt response) {

                            tvChar.setText(appKlasa.kontakt.get(index).getIme().toUpperCase().charAt(0)+"");
                            tvIme.setText(appKlasa.kontakt.get(index).getIme());
                            Toast.makeText(KontaktInfo.this,
                                    "Contact successfully updated!", Toast.LENGTH_SHORT).show();
                            showProgress(false);

                        }

                        @Override
                        public void handleFault(BackendlessFault fault) {

                            Toast.makeText(KontaktInfo.this, "Error: " + fault.getMessage(),
                                    Toast.LENGTH_SHORT).show();
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
