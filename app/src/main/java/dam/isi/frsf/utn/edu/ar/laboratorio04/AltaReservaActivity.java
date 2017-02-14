package dam.isi.frsf.utn.edu.ar.laboratorio04;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import java.util.Date;
import java.text.ParseException;
import java.text.SimpleDateFormat;

import dam.isi.frsf.utn.edu.ar.laboratorio04.modelo.Departamento;
import dam.isi.frsf.utn.edu.ar.laboratorio04.modelo.Reserva;
import dam.isi.frsf.utn.edu.ar.laboratorio04.utils.ListarReservasActivity;

public class AltaReservaActivity extends AppCompatActivity implements View.OnClickListener {

    private TextView departamentoTextView;
    private EditText fechaInicioEditText;
    private EditText fechaFinEditText;
    private TextView precioTextView;
    private Button reservarButton;

    private Reserva reserva;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_alta_reserva);

        departamentoTextView = (TextView) findViewById(R.id.departamentoTextView);
        fechaInicioEditText = (EditText) findViewById(R.id.fechaInicioEditText);
        fechaFinEditText = (EditText) findViewById(R.id.fechaFinEditText);
        precioTextView = (TextView) findViewById(R.id.precioTextView);
        reservarButton = (Button) findViewById(R.id.reservarButton);
        reservarButton.setOnClickListener(this);

        Departamento departamento = (Departamento) getIntent().getExtras().get("departamento");
        departamentoTextView.setText(departamento.getDescripcion() + departamento.getCiudad());
        precioTextView.setText(departamento.getPrecio().toString());

        reserva = new Reserva();
        reserva.setId(1);
        reserva.setPrecio(departamento.getPrecio());
        reserva.setConfirmada(false);
        reserva.setAlojamiento(departamento);
    }

    @Override
    public void onClick(View v) {
        if(v.getId() != R.id.reservarButton) {
            return;
        }
        Toast toast = Toast.makeText(getApplicationContext(), "El formato de la fecha es dd/MM/yyyy", Toast.LENGTH_LONG);

        SimpleDateFormat dt = new SimpleDateFormat("dd/MM/yyyy");
        Date fechaInicio;
        try {
            fechaInicio = dt.parse(fechaInicioEditText.getText().toString());
        } catch (ParseException e) {
            toast.show();
            return;
        }
        reserva.setFechaInicio(fechaInicio);

        Date fechaFin;
        try {
            fechaFin = dt.parse(fechaFinEditText.getText().toString());
        } catch (ParseException e) {
            toast.show();
            return;
        }
        reserva.setFechaFin(fechaFin);

        Intent i = getIntent();
        i.putExtra("reserva", reserva);
        setResult(RESULT_OK, i);
        Log.d("Reserva", reserva.getAlojamiento().toString());
        Log.d("Reserva", reserva.toString());
        finish();
    }
}
