package dam.isi.frsf.utn.edu.ar.laboratorio04.utils;

import android.os.AsyncTask;
import android.util.Log;

import java.util.ArrayList;
import java.util.List;

import dam.isi.frsf.utn.edu.ar.laboratorio04.modelo.Ciudad;
import dam.isi.frsf.utn.edu.ar.laboratorio04.modelo.Departamento;
import dam.isi.frsf.utn.edu.ar.laboratorio04.utils.BusquedaFinalizadaListener;
import dam.isi.frsf.utn.edu.ar.laboratorio04.utils.FormBusqueda;

/**
 * Created by martdominguez on 22/09/2016.
 */
public class BuscarDepartamentosTask extends AsyncTask<FormBusqueda,Integer,List<Departamento>> {

    private BusquedaFinalizadaListener<Departamento> listener;

    public BuscarDepartamentosTask(BusquedaFinalizadaListener<Departamento> dListener){
        this.listener = dListener;
    }
    @Override
    protected void onPreExecute() {
        super.onPreExecute();
    }

    @Override
    protected void onPostExecute(List<Departamento> departamentos) {
        listener.busquedaFinalizada(departamentos);
    }

    @Override
    protected void onProgressUpdate(Integer... values) {
        listener.busquedaActualizada("departamento "+values[0]);


    }

    @Override
    protected List<Departamento> doInBackground(FormBusqueda... busqueda) {
        List<Departamento> todos = Departamento.getAlojamientosDisponibles();
        List<Departamento> resultado = new ArrayList<Departamento>();
        int contador = 0;
        FormBusqueda busq = busqueda[0];
        Ciudad ciudadBuscada = busq.getCiudad();
        for(Departamento depto : todos){
            if ( busq.getHuespedes() != null && busq.getHuespedes() > depto.getCapacidadMaxima())
                continue;
            if ( busq.getPrecioMinimo() != null && busq.getPrecioMinimo() > depto.getPrecio())
                continue;
            if ( busq.getPrecioMaximo() != null && busq.getPrecioMaximo() < depto.getPrecio())
                continue;
            //if ( busq.getCiudad() != null && busq.getCiudad() != depto.getCiudad())
            //    continue;
            if ( busq.getPermiteFumar() &&  depto.getNoFumador())
                continue;
            resultado.add(depto);
        }
        // TODO implementar: buscar todos los departamentos del sistema e ir chequeando las condiciones 1 a 1.
        // si cumplen las condiciones agregarlo a los resultados.
        return resultado;
    }
}
