package com.example.fb_htalca;

import android.content.Intent;
import android.net.Uri;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

public class Adaptador  extends RecyclerView.Adapter<Adaptador.ViewHolder> {
    public ArrayList<regalos> regalos;
    public Adaptador(ArrayList<regalos> regalos){
        this.regalos = regalos;
    }
    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.tarjeta, parent, false);
        return new ViewHolder(view).enlaceAdaptador(this);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
       // holder.foto.setImageURI(Uri.parse(regalos.get(position).getFoto()));
        holder.titulo.setText(regalos.get(position).getMensaje());
        holder.texto.setText(regalos.get(position).getFoto());
        holder.regalos = regalos.get(position);

        holder.foto.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Toast.makeText(holder.foto.getContext(), "asdf", Toast.LENGTH_LONG).show();
            }
        });

    }

    @Override
    public int getItemCount() {
        return regalos.size();
    }


    public static class ViewHolder extends RecyclerView.ViewHolder{
        private ImageView foto;
        private TextView titulo, texto;
        private regalos regalos;
        private Adaptador adaptador;

        public ViewHolder(View itemView){
            super(itemView);

            foto = itemView.findViewById(R.id.foto1);
            titulo = itemView.findViewById(R.id.titulo1);
            texto = itemView.findViewById(R.id.texto1);



            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {


                    Intent intent = new Intent(view.getContext(),MainActivity4.class);

                    intent.putExtra("foto",  adaptador.regalos.get(getAdapterPosition()).getFoto());
                    intent.putExtra("texto",  adaptador.regalos.get(getAdapterPosition()).getMensaje());

                    view.getContext().startActivity(intent);
                }
            });

           /* botonNumero.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    String textoNumero = "";

                   /* for(int i = 0; i < contacto.numerosRandom.size(); i++){
                        textoNumero += contacto.numerosRandom.get(i)+" - ";
                    }

                    Toast.makeText(view.getContext(), "Números secretos: "+textoNumero, Toast.LENGTH_LONG).show();
                }
            });*/


        }

        public ViewHolder enlaceAdaptador(Adaptador a){
            this.adaptador = a;
            return this;
        }
    }
}
