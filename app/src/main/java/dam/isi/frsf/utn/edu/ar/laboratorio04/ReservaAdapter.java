package dam.isi.frsf.utn.edu.ar.laboratorio04;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import java.text.DateFormat;
import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.List;

import dam.isi.frsf.utn.edu.ar.laboratorio04.modelo.Reserva;

public class ReservaAdapter extends ArrayAdapter<Reserva> {
    private LayoutInflater inflater;
    private Context contexto;

    public ReservaAdapter(Context contexto, List<Reserva> items) {
        super(contexto, R.layout.fila, items);
        inflater = LayoutInflater.from(contexto);
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        DecimalFormat df = new DecimalFormat("#.##");
        SimpleDateFormat dt = new SimpleDateFormat("dd/MM/yyyy");

        View row = convertView;
        if (row == null) row = inflater.inflate(R.layout.fila, parent, false);


        TextView txtCiudad = (TextView) row.findViewById(R.id.ciudad);
        txtCiudad.setText(this.getItem(position).getCiudad().getNombre());
        TextView txtDescripcion = (TextView) row.findViewById(R.id.descripcion);
        txtDescripcion.setText("Unico!! " + this.getItem(position).getDescripcion());
        TextView txtPrecio = (TextView) row.findViewById(R.id.precio);
        txtPrecio.setText("$" + (df.format(this.getItem(position).getPrecio())));
        TextView txtCapacidad = (TextView) row.findViewById(R.id.capacidadMax);
        txtCapacidad.setText(this.getItem(position).getCapacidadMaxima()+".");
        return (row);
    }
}