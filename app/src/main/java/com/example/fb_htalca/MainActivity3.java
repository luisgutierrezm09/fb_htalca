package com.example.fb_htalca;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.util.Log;
import android.view.View;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

public class MainActivity3 extends AppCompatActivity {
    private RecyclerView recyclerView;
    private Adaptador adaptador;
    private ArrayList<regalos> regalos;
    ArrayList<regalos> lista;
    private FirebaseAuth mAuth;
    private FirebaseUser usuario;
    private FirebaseDatabase database;
    private DatabaseReference reference;


    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main3);
        mAuth = FirebaseAuth.getInstance();
        lista = new ArrayList<regalos>();
        usuario = mAuth.getCurrentUser();
        DatabaseReference referenciaMensajes = FirebaseDatabase.getInstance().getReference("regalos").child(usuario.getUid());
        // referenciaMensajes.child("regalos").child(usuario.getUid()).child("regalo_").child("uri");
        //  Log.d(TAG, String.valueOf(referenciaMensajes));

        // Intent intent = new Intent(this, MainActivity.class);

        //startActivity(intent);
        referenciaMensajes.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {

                //  dialogo.setTitle(snapshot.child("titulo").getValue(String.class)).setMessage(snapshot.child("mensaje").getValue(String.class)).setCancelable(true).create().show();
                for (DataSnapshot child: snapshot.getChildren()) {
                    regalos c = new regalos();
                    c.setFoto(child.child("uri").getValue(String.class));
                    c.setMensaje(child.child("mensaje").getValue(String.class));
                    lista.add(c);


                }

                adaptador = new Adaptador(lista);
                recyclerView = findViewById(R.id.Recycler);

                recyclerView.setLayoutManager(new LinearLayoutManager(MainActivity3.this));
                recyclerView.setHasFixedSize(true);
                recyclerView.setAdapter(adaptador);



            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });

       // regalos = (ArrayList<regalos>) getIntent().getSerializableExtra("");



    }

    public void BuscarRegalos(View view ){


    }
}