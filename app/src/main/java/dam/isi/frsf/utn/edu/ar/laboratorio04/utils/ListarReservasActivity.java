package dam.isi.frsf.utn.edu.ar.laboratorio04.utils;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.ListView;
import android.widget.TextView;

import dam.isi.frsf.utn.edu.ar.laboratorio04.R;
import dam.isi.frsf.utn.edu.ar.laboratorio04.ReservaAdapter;
import dam.isi.frsf.utn.edu.ar.laboratorio04.modelo.Reserva;

import java.util.ArrayList;
import java.util.List;

public class ListarReservasActivity extends AppCompatActivity {

    private TextView tvEstadoBusqueda;
    private ListView listarReservas;
    private ReservaAdapter reservaAdapter;
    private List<Reserva> lista;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_listar_reservas);

        lista= new ArrayList<>();
        listarReservas = (ListView) findViewById(R.id.listarReservas);
    }

    @Override
    protected void onStart() {
        super.onStart();
        Intent intent = getIntent();
        lista = (ArrayList<Reserva>) intent.getExtras().get("reservas");

        reservaAdapter = new ReservaAdapter(ListarReservasActivity.this,lista);
        listarReservas.setAdapter(reservaAdapter);
    }
}
