package com.example.volley;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.content.res.AppCompatResources;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;

import java.util.ArrayList;

public class CuponAdapter extends RecyclerView.Adapter<CuponAdapter.ViewHolder> {

    ArrayList<Cupon> ListaCupon;



    public CuponAdapter(ArrayList<Cupon> listaCupon){
        ListaCupon = listaCupon;
    }

    @NonNull
    @Override
    public CuponAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.cupon_list, null, false);
        return new CuponAdapter.ViewHolder(view);
    }

    @SuppressLint("SetTextI18n")
    @Override
    public void onBindViewHolder(@NonNull CuponAdapter.ViewHolder holder, int position) {
       //Se obtiene el nombre de id y valor de la lista
       // holder.tvNombre.setText(ListaPersonal.get(position).getNombre());
        String costo =  ListaCupon.get(position).getCosto();
        String cantidad =  ListaCupon.get(position).getCantidad();

        holder.tvCorte.setText("Orden: "+ListaCupon.get(position).getCorte());
        holder.tvBulto.setText("Bulto: "+ListaCupon.get(position).getBulto());
        holder.tvTalla.setText("Talla: "+ListaCupon.get(position).getTalla());
        holder.tvOperacion.setText("Operacion: "+ListaCupon.get(position).getOperacion());
        holder.tvCosto.setText("Costo: "+ListaCupon.get(position).getCosto());
        holder.tvCantidad.setText("Cantidad: "+ListaCupon.get(position).getCantidad());
        holder.tvTotal.setText("Total: "+(Double.parseDouble(costo) * Double.parseDouble(cantidad)));

    }

    @Override
    public int getItemCount() {
        return ListaCupon.size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
      // Se indican los elemntos de la vista que se renderizaran
        TextView tvCorte;
        TextView tvBulto;
        TextView tvTalla;
        TextView tvOperacion;
        TextView tvCosto;
        TextView tvCantidad;
        TextView tvTotal;

        public ViewHolder(@androidx.annotation.NonNull View itemView) {
            super(itemView);
             //Se indica donde se va a colocar cada cosa buscando por el ID
            //tvNombre = (TextView) itemView.findViewById(R.id.idNombre);
            tvCorte = (TextView)  itemView.findViewById(R.id.idOrdenCorte);
            tvBulto = (TextView)  itemView.findViewById(R.id.idBulto);
            tvTalla = (TextView)  itemView.findViewById(R.id.idTalla);
            tvOperacion = (TextView)  itemView.findViewById(R.id.idOperacion);
            tvCosto = (TextView)  itemView.findViewById(R.id.idCosto);
            tvCantidad = (TextView)  itemView.findViewById(R.id.idCantidad);
            tvTotal = itemView.findViewById(R.id.idTotal);


            /* Se indica si tendra un Clic listener*/
         /*   lyPrincipal.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Intent intent = new Intent(itemView.getContext(), DetallePersonal.class);
                    Bundle bundle = new Bundle();
                    bundle.putString("Nombre", PersonalAdapter.ViewHolder.this.tvNombre.getText().toString());
                    bundle.putString("Telefono", PersonalAdapter.ViewHolder.this.tvTelefono.getText().toString());
                    bundle.putString("Puesto", PersonalAdapter.ViewHolder.this.tvPuesto.getText().toString());
                    bundle.putString("Status", PersonalAdapter.ViewHolder.this.tvStatus.getText().toString());
                    bundle.putString("Fotografia", PersonalAdapter.ViewHolder.this.tvNombreImage.getText().toString());
                    bundle.putString("IdPersonal", PersonalAdapter.ViewHolder.this.tvIdPersonal.getText().toString());

                    intent.putExtras(bundle);
                    itemView.getContext().startActivity(intent);
                }
            }); */
        }
    }



}
