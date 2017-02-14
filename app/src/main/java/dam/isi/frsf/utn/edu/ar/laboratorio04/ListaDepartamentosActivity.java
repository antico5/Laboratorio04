package dam.isi.frsf.utn.edu.ar.laboratorio04;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;
import java.util.logging.Logger;

import dam.isi.frsf.utn.edu.ar.laboratorio04.modelo.Reserva;
import dam.isi.frsf.utn.edu.ar.laboratorio04.utils.BuscarDepartamentosTask;
import dam.isi.frsf.utn.edu.ar.laboratorio04.modelo.Departamento;
import dam.isi.frsf.utn.edu.ar.laboratorio04.utils.BusquedaFinalizadaListener;
import dam.isi.frsf.utn.edu.ar.laboratorio04.utils.FormBusqueda;

public class ListaDepartamentosActivity extends AppCompatActivity implements BusquedaFinalizadaListener<Departamento>, AdapterView.OnItemLongClickListener {

    static final int ACTIVITY_RESERVAS = 2;

    private TextView tvEstadoBusqueda;
    private ListView listaAlojamientos;
    private DepartamentoAdapter departamentosAdapter;
    private List<Departamento> lista;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_lista_alojamientos);
        lista = new ArrayList<>();
        listaAlojamientos = (ListView ) findViewById(R.id.listaAlojamientos);
        tvEstadoBusqueda = (TextView) findViewById(R.id.estadoBusqueda);
        listaAlojamientos.setOnItemLongClickListener(this);
    }

    @Override
    protected void onStart() {
        super.onStart();
        Intent intent = getIntent();
        Boolean esBusqueda = intent.getExtras().getBoolean("esBusqueda");
        if(esBusqueda){
            FormBusqueda fb = (FormBusqueda ) intent.getSerializableExtra("frmBusqueda");
            new BuscarDepartamentosTask(ListaDepartamentosActivity.this).execute(fb);
            tvEstadoBusqueda.setText("Buscando....");
            tvEstadoBusqueda.setVisibility(View.VISIBLE);
        }else{
            llenarLista(Departamento.getAlojamientosDisponibles());
        }

        Toast toast = Toast.makeText(getApplicationContext(), "Para reservar el departamento, mantenga pulsado sobre el mismo.", Toast.LENGTH_LONG);
        toast.show();
    }

    @Override
    public void busquedaFinalizada(List<Departamento> listaDepartamento) {
        llenarLista(listaDepartamento);
    }

    @Override
    public void busquedaActualizada(String msg) {
        tvEstadoBusqueda.setText(" Buscando..."+msg);
    }

    private void llenarLista(List<Departamento> listaDepartamentos){
        if(listaDepartamentos.isEmpty()) {
            tvEstadoBusqueda.setText("No se encontraron resultados");
        } else {
            lista = listaDepartamentos;
            departamentosAdapter = new DepartamentoAdapter(ListaDepartamentosActivity.this,lista);
            listaAlojamientos.setAdapter(departamentosAdapter);
            tvEstadoBusqueda.setVisibility(View.GONE);
        }
    }

    @Override
    public boolean onItemLongClick(AdapterView<?> parent, View view, int position, long id) {
        Departamento departamento = lista.get(position);
        Intent i = new Intent(this,AltaReservaActivity.class);
        i.putExtra("departamento", departamento);
        startActivityForResult(i, ACTIVITY_RESERVAS);
        return false;
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if(requestCode != ACTIVITY_RESERVAS || resultCode != RESULT_OK) {
            return;
        }

        Reserva reserva = (Reserva) data.getExtras().get("reserva");
        int index = lista.indexOf(reserva.getAlojamiento());
        if(index != -1) {
            lista.get(index).getReservas().add(reserva);
        }

        Intent i = getIntent();
        i.putExtra("reserva", reserva);
        setResult(RESULT_OK, i);
        Log.d("Reserva", reserva.getAlojamiento().toString());
        finish();
    }
}
