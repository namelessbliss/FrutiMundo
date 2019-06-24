package com.app.nb.frutimundo.activities;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.Menu;
import android.view.MenuItem;

import com.app.nb.frutimundo.R;
import com.app.nb.frutimundo.adapter.AdaptadorFruta;
import com.app.nb.frutimundo.model.Fruta;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    private RecyclerView recyclerView;
    private RecyclerView.LayoutManager layoutManager;
    private AdaptadorFruta adaptador;

    private List<Fruta> frutas;
    private int contador = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        frutas = this.getFrutas();
        recyclerView = (RecyclerView) findViewById(R.id.recyclerView);
        layoutManager = new LinearLayoutManager(this);

        adaptador = new AdaptadorFruta(frutas, R.layout.recycler_view_item_fruta, this, new AdaptadorFruta.OnItemClickListener() {
            @Override
            public void onItemClick(Fruta fruta, int posicion) {
                fruta.addCantidad(1);
                adaptador.notifyItemChanged(posicion);
            }
        });
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(layoutManager);
        recyclerView.setAdapter(adaptador);

        //implementacion del ContextMenu movido al ViewHolder
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.top_bar_option_menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.add_fruta:
                int posicion = frutas.size();
                frutas.add(posicion, new Fruta("Mango" + (++contador), "Descripcion mango", R.drawable.mango, R.mipmap.ic_mango, 0));
                adaptador.notifyItemInserted(posicion);
                layoutManager.scrollToPosition(posicion);
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    private List<Fruta> getFrutas() {
        return new ArrayList<Fruta>() {{
            add(new Fruta("Manzana", "Descripcion manzana", R.drawable.manzana, R.mipmap.ic_apple, 0));
            add(new Fruta("Banana", "Descripcion banana", R.drawable.banana, R.mipmap.ic_banana, 0));
            add(new Fruta("Ceresa", "Descripcion ceresa", R.drawable.ceresa, R.mipmap.ic_cherry, 0));
            add(new Fruta("Fresa", "Descripcion fresa", R.drawable.fresa, R.mipmap.ic_strawberry, 0));
            add(new Fruta("Naranja", "Descripcion manzana", R.drawable.naranja, R.mipmap.ic_orange, 0));
            add(new Fruta("Pera", "Descripcion pera", R.drawable.pera, R.mipmap.ic_pear, 0));
            add(new Fruta("sandia", "Descripcion sandia", R.drawable.sandia, R.mipmap.ic_sandia, 0));
        }};
    }
}
