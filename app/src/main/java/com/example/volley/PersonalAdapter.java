package com.example.volley;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.os.Build;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import androidx.appcompat.content.res.AppCompatResources;
import androidx.cardview.widget.CardView;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class PersonalAdapter extends RecyclerView.Adapter<PersonalAdapter.ViewHolder> {

    ArrayList<Personal> ListaPersonal;
    ArrayList<Personal> ListaOriginal;

    String urlImages = "http://172.22.16.4/etextil/uploader/personal/";
    String urlNoImage = "https://cdn-icons-png.flaticon.com/512/85/85488.png";

    public PersonalAdapter(ArrayList<Personal> listaPersonal){
        ListaPersonal = listaPersonal;
        ListaOriginal = new ArrayList<>();
        ListaOriginal.addAll(listaPersonal);
    }

    @NonNull
    @Override
    public PersonalAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_list, null, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        String imagen ;
        Drawable leftDrawable = AppCompatResources.getDrawable(holder.tvPuesto.getContext(), R.drawable.ic_baseline_check_circle_24);
        Drawable activeDrawable = AppCompatResources.getDrawable(holder.tvPuesto.getContext(), R.drawable.ic_baseline_check_circle_24);
        Drawable inactiveDrawable = AppCompatResources.getDrawable(holder.tvPuesto.getContext(), R.drawable.ic_baseline_warning_24);

        holder.tvNombre.setText(ListaPersonal.get(position).getNombre());
        holder.tvPuesto.setText(ListaPersonal.get(position).getPuesto());
        holder.tvTelefono.setText(ListaPersonal.get(position).getTelefono());
        holder.tvStatus.setText(ListaPersonal.get(position).getStatus());

        holder.tvIdPersonal.setText(ListaPersonal.get(position).getIdPersonal());


        /* Coloca un icono dependiendo el status del usuario*/
        if (ListaPersonal.get(position).getStatus().equals("Activo")){
            holder.tvStatus.setCompoundDrawablesWithIntrinsicBounds( null, null,activeDrawable,null);
        }else{
            holder.tvStatus.setCompoundDrawablesWithIntrinsicBounds( null, null,inactiveDrawable,null);
        }

       // Inserta la imagen en el contenedor con la Libreria Glide
        if (ListaPersonal.get(position).getFotografia().equals("")){
            imagen = urlNoImage;
        }else{
            imagen = urlImages+ListaPersonal.get(position).getFotografia();
           }

        holder.tvNombreImage.setText(imagen);
        Glide.with(holder.ivFotografia).load(imagen).into(holder.ivFotografia);

    }

    @SuppressLint("NotifyDataSetChanged")
    public void filtrado(String txtBuscar){
        int size = txtBuscar.length();
        if(size == 0){
            ListaPersonal.clear();
            ListaPersonal.addAll(ListaOriginal);
        }else{
            if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.N) {
                List<Personal> colleccion = ListaPersonal.stream()
                        .filter(i -> i.getNombre().toUpperCase().contains(txtBuscar.toUpperCase()))
                        .collect(Collectors.toList());

                ListaPersonal.clear();
                ListaPersonal.addAll(colleccion);
            }else{
                for (Personal c: ListaPersonal){
                    if (c.getNombre().toUpperCase().contains(txtBuscar.toUpperCase())){
                        ListaPersonal.add(c);
                    }
                    if (c.getIdPersonal().toUpperCase().contains(txtBuscar.toUpperCase())){
                        ListaPersonal.add(c);
                    }
                }
            }

        }
        notifyDataSetChanged();
    }

    @Override
    public int getItemCount() {
        return ListaPersonal.size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        TextView tvNombre;
        TextView tvTelefono;
        TextView tvStatus;
        TextView tvPuesto;
        TextView tvNombreImage;
        TextView tvIdPersonal;
        ImageView ivFotografia;
        ConstraintLayout lyPrincipal;


        public ViewHolder(@androidx.annotation.NonNull View itemView) {
            super(itemView);
            tvNombre = (TextView) itemView.findViewById(R.id.idNombre);
            tvTelefono = (TextView) itemView.findViewById(R.id.idTelefono);
            tvStatus = (TextView) itemView.findViewById(R.id.idStatus);
            tvPuesto = (TextView) itemView.findViewById(R.id.idPuesto);
            tvNombreImage = (TextView) itemView.findViewById(R.id.idNameImage);
            tvIdPersonal = (TextView) itemView.findViewById(R.id.idPersonal);
            ivFotografia = (ImageView) itemView.findViewById(R.id.idFotografia);
            lyPrincipal = (ConstraintLayout) itemView.findViewById(R.id.idLayout);

            lyPrincipal.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Intent intent = new Intent(itemView.getContext(), DetallePersonal.class);
                    Bundle bundle = new Bundle();
                    bundle.putString("Nombre", ViewHolder.this.tvNombre.getText().toString());
                    bundle.putString("Telefono", ViewHolder.this.tvTelefono.getText().toString());
                    bundle.putString("Puesto", ViewHolder.this.tvPuesto.getText().toString());
                    bundle.putString("Status", ViewHolder.this.tvStatus.getText().toString());
                    bundle.putString("Fotografia", ViewHolder.this.tvNombreImage.getText().toString());
                    bundle.putString("IdPersonal", ViewHolder.this.tvIdPersonal.getText().toString());

                    intent.putExtras(bundle);
                    itemView.getContext().startActivity(intent);
                }
            });
        }
    }

}
