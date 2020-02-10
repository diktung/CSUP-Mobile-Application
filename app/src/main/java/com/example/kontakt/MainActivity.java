package com.example.kontakt;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.backendless.Backendless;
import com.backendless.async.callback.AsyncCallback;
import com.backendless.exceptions.BackendlessFault;

public class MainActivity extends AppCompatActivity {

    Button btnLista, btnKreiraj,button;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        btnLista = findViewById(R.id.btnLista);
        btnKreiraj = findViewById(R.id.btnKreiraj);
        button = findViewById(R.id.button);

        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {


                Backendless.UserService.logout(new AsyncCallback<Void>() {
                    @Override
                    public void handleResponse(Void response) {

                        Toast.makeText(MainActivity.this,
                                "Izlogovali ste se.", Toast.LENGTH_SHORT).show();


                        startActivity(new Intent(MainActivity.this, Login.class));
                        MainActivity.this.finish();

                    }

                    @Override
                    public void handleFault(BackendlessFault fault) {

                        Toast.makeText(MainActivity.this, "Error: " + fault.getMessage(),
                                Toast.LENGTH_SHORT).show();

                    }
                });




            }
        });

        btnLista.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                startActivity(new Intent(MainActivity.this, KontaktLista.class));

            }
        });

        btnKreiraj.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {



                startActivity(new Intent(MainActivity.this, NoviKontakt.class));

            }
        });

    }
}
