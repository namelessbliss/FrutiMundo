package com.app.nb.frutimundo.adapter;

import android.app.Activity;
import android.graphics.Typeface;
import android.support.annotation.NonNull;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.RecyclerView;
import android.view.ContextMenu;
import android.view.LayoutInflater;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.app.nb.frutimundo.R;
import com.app.nb.frutimundo.model.Fruta;
import com.squareup.picasso.Picasso;

import java.util.List;

public class AdaptadorFruta extends RecyclerView.Adapter<AdaptadorFruta.ViewHolder> {

    private List<Fruta> frutas;
    private int layout;
    private Activity activity;
    private OnItemClickListener itemClickListenerlistener;

    //Paso el activity en vez del context
    public AdaptadorFruta(List<Fruta> frutas, int layout, Activity activity, OnItemClickListener itemClickListenerlistener) {
        this.frutas = frutas;
        this.layout = layout;
        this.activity = activity;
        this.itemClickListenerlistener = itemClickListenerlistener;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View view = LayoutInflater.from(activity).inflate(layout, viewGroup, false);
        ViewHolder viewHolder = new ViewHolder(view);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder viewHolder, int i) {
        viewHolder.bind(frutas.get(i), itemClickListenerlistener);
    }

    @Override
    public int getItemCount() {
        return frutas.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder implements View.OnCreateContextMenuListener, MenuItem.OnMenuItemClickListener {
        //Elementos de la UI a rellenar
        public TextView textViewNombre;
        public TextView textViewDescripcion;
        public TextView textViewCantidad;
        public ImageView imageViewBackGround;

        public ViewHolder(View view) {
            super(view);
            // Obtiene elementos de la UI
            textViewNombre = (TextView) itemView.findViewById(R.id.tvNombre);
            textViewDescripcion = (TextView) itemView.findViewById(R.id.tvDescripcion);
            textViewCantidad = (TextView) itemView.findViewById(R.id.tvCantidad);
            imageViewBackGround = (ImageView) itemView.findViewById(R.id.imageViewBackground);
            itemView.setOnCreateContextMenuListener(this);
        }

        public void bind(final Fruta fruta, final OnItemClickListener listener) {
            //Procesamos los datos a renderizar

            textViewNombre.setText(fruta.getNombre());
            textViewDescripcion.setText(fruta.getDescripcion());
            textViewCantidad.setText(fruta.getCantidad() + "");

            //Limitar la cantidad en cada elemento fruta
            if (fruta.getCantidad() == fruta.LIMITE_DE_CANTIDAD) {
                textViewCantidad.setTextColor(ContextCompat.getColor(activity, R.color.colorAlert));
                textViewCantidad.setTypeface(null, Typeface.BOLD);
            } else {
                textViewCantidad.setTextColor(ContextCompat.getColor(activity, R.color.defaultTextColor));
                textViewCantidad.setTypeface(null, Typeface.NORMAL);
            }

            //Cargamos la imagen con picasso
            Picasso.get().load(fruta.getImagenBackground()).fit().into(imageViewBackGround);

            //Por cada elemento del recycler view, definimos un click listener
            this.imageViewBackGround.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    listener.onItemClick(fruta, getAdapterPosition());
                }
            });
        }

        /**
         * Sobrescribimos onCreateContextMenu, dentro del ViewHolder,
         * en vez de hacerlo en el activity
         **/
        @Override
        public void onCreateContextMenu(ContextMenu contextMenu, View view, ContextMenu.ContextMenuInfo contextMenuInfo) {
            //Recogemos la posicion con el metodo getAdapterPosition
            Fruta frutaSeleccionada = frutas.get(this.getAdapterPosition());
            //Establecemos titulo e icono para cada elemento
            contextMenu.setHeaderTitle(frutaSeleccionada.getNombre());
            contextMenu.setHeaderIcon(frutaSeleccionada.getIcono());
            //Inflamos el menu
            MenuInflater inflater = activity.getMenuInflater();
            inflater.inflate(R.menu.context_menu_fruta, contextMenu);
            //Añadimos el listener onMenuItemClick para controlar las acciones
            // en el context menu
            for (int i = 0; i < contextMenu.size(); i++) {
                contextMenu.getItem(i).setOnMenuItemClickListener(this);
            }
        }


        /**
         * Sobrescribimos onMenuItemClick, dentro del ViewHolder,
         * en vez de hacerlo en el activity bajo el nombre on ContextItemSelected
         */
        @Override
        public boolean onMenuItemClick(MenuItem menuItem) {
            switch (menuItem.getItemId()) {
                case R.id.borrar_fruta:
                    frutas.remove(getAdapterPosition());
                    notifyItemRemoved(getAdapterPosition());
                    return true;
                case R.id.reset_cantidad:
                    frutas.get(getAdapterPosition()).resetCantidad();
                    notifyItemChanged(getAdapterPosition());
                    return true;
                default:
                    return false;
            }
        }
    }

    public interface OnItemClickListener {
        void onItemClick(Fruta fruta, int posicion);

    }

}
