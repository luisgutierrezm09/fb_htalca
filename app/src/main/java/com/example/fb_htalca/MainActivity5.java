package com.example.fb_htalca;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;

import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class MainActivity5 extends AppCompatActivity {

    private TextView nombre,sexo,edad;
    private FirebaseAuth mAuth;
    private FirebaseUser usuario;
    private FirebaseDatabase database;
    private DatabaseReference reference;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main5);

        nombre =findViewById(R.id.nombre);
        sexo = findViewById(R.id.sexo);
        edad = findViewById(R.id.edad);
        mAuth = FirebaseAuth.getInstance();
        usuario = mAuth.getCurrentUser();
        database = FirebaseDatabase.getInstance();
        reference = database.getReference();

        DatabaseReference referenciaMensajes = FirebaseDatabase.getInstance().getReference("regalos").child(usuario.getUid()).child("perfil");
        // referenciaMensajes.child("regalos").child(usuario.getUid()).child("regalo_").child("uri");
        //  Log.d(TAG, String.valueOf(referenciaMensajes));

        // Intent intent = new Intent(this, MainActivity.class);

        //startActivity(intent);
        referenciaMensajes.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {

                //  dialogo.setTitle(snapshot.child("titulo").getValue(String.class)).setMessage(snapshot.child("mensaje").getValue(String.class)).setCancelable(true).create().show();


                  nombre.setText(snapshot.child("nombre").getValue(String.class));
                  sexo.setText(snapshot.child("sexo").getValue(String.class));
                edad.setText(snapshot.child("edad").getValue(String.class));






            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }

    public void guardarperfil(View view){

        reference.child("regalos").child(usuario.getUid()).child("perfil").child("nombre").setValue(nombre.getText().toString());
        reference.child("regalos").child(usuario.getUid()).child("perfil").child("sexo").setValue(sexo.getText().toString());
        reference.child("regalos").child(usuario.getUid()).child("perfil").child("edad").setValue(edad.getText().toString());
        String claveunica = reference.push().getKey();
        reference.child("regalos").child(usuario.getUid()).child("perfil").child("idSecreto").setValue(claveunica);
        Toast.makeText(this,"Perfil Modificado",Toast.LENGTH_LONG).show();
    }
}