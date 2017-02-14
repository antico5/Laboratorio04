package dam.isi.frsf.utn.edu.ar.laboratorio04;

import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v4.util.TimeUtils;
import android.support.v7.widget.SwitchCompat;
import android.util.Log;
import android.view.View;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.SeekBar;
import android.widget.Spinner;
import android.widget.Switch;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;
import java.util.List;

import dam.isi.frsf.utn.edu.ar.laboratorio04.modelo.Ciudad;
import dam.isi.frsf.utn.edu.ar.laboratorio04.modelo.Reserva;
import dam.isi.frsf.utn.edu.ar.laboratorio04.utils.FormBusqueda;
import dam.isi.frsf.utn.edu.ar.laboratorio04.utils.ListarReservasActivity;

public class MainActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener {

    static final int LISTAR_DEPARTAMENTOS = 3;

    private Button btnBuscar;
    private Spinner cmbCiudad;
    private ArrayAdapter<Ciudad> adapterCiudad;
    private SeekBar skPrecioMin;
    private TextView tvPrecioMinimo;
    private TextView tvPrecioMaximo;
    private SeekBar skPrecioMax;
    private EditText txtHuespedes;
    private Switch swFumadores;
    private FormBusqueda frmBusq;
    private ArrayList<Reserva> reservas;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.setDrawerListener(toggle);
        toggle.syncState();

        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);

        frmBusq= new FormBusqueda();
        txtHuespedes = (EditText) findViewById(R.id.cantHuespedes);
        skPrecioMin = (SeekBar) findViewById(R.id.precioMin);
        skPrecioMin.setOnSeekBarChangeListener(listenerSB);

        skPrecioMax= (SeekBar) findViewById(R.id.precioMax);
        skPrecioMax.setOnSeekBarChangeListener(listenerSB);

        swFumadores = (Switch) findViewById(R.id.aptoFumadores);
        adapterCiudad = new ArrayAdapter<Ciudad>(MainActivity.this,android.R.layout.simple_spinner_item, Arrays.asList(Ciudad.CIUDADES));

        cmbCiudad = (Spinner) findViewById(R.id.comboCiudad);
        cmbCiudad.setAdapter(adapterCiudad);
        cmbCiudad.setOnItemSelectedListener(comboListener);
        tvPrecioMinimo = (TextView ) findViewById(R.id.txtPrecioMin);
        tvPrecioMaximo= (TextView ) findViewById(R.id.txtPrecioMax);

        btnBuscar = (Button) findViewById(R.id.btnBuscar);
        btnBuscar.setOnClickListener(btnBusarListener);

        reservas = new ArrayList<>();

    }

    private View.OnClickListener btnBusarListener = new View.OnClickListener() {
        @Override
        public void onClick(View view) {
            Intent i = new Intent(MainActivity.this,ListaDepartamentosActivity.class);
            frmBusq.setPermiteFumar(swFumadores.isSelected());
            Integer huespedes = null;
            try {
                huespedes = Integer.parseInt(txtHuespedes.getText().toString());
            } catch (NumberFormatException e) {
                huespedes = null;
            }
            frmBusq.setHuespedes(huespedes);
            i.putExtra("esBusqueda",true);
            i.putExtra("frmBusqueda",frmBusq);
            startActivityForResult(i, LISTAR_DEPARTAMENTOS);
        }
    };

    private AdapterView.OnItemSelectedListener comboListener = new  AdapterView.OnItemSelectedListener() {
        public void onItemSelected(AdapterView<?> parent, View view, int pos, long id) {
            Ciudad item = (Ciudad) parent.getItemAtPosition(pos);
            frmBusq.setCiudad(item);
            Log.d("MainActivity","ciudad seteada "+item);
        }
        public void onNothingSelected(AdapterView<?> parent) {
        }
    };


    private SeekBar.OnSeekBarChangeListener listenerSB =  new SeekBar.OnSeekBarChangeListener(){

        @Override
        public void onProgressChanged(SeekBar seekBar, int progress,
        boolean fromUser) {
            if(seekBar.getId()==R.id.precioMin) {
                tvPrecioMinimo.setText("Precio Minimo "+progress);
                frmBusq.setPrecioMinimo(Double.valueOf(progress));
            }
            if(seekBar.getId()==R.id.precioMax) {
                tvPrecioMaximo.setText("Precio Maximo"+progress);
                frmBusq.setPrecioMaximo(Double.valueOf(progress));
            }
        }

        @Override
        public void onStartTrackingTouch(SeekBar seekBar) {
        }

        @Override
        public void onStopTrackingTouch(SeekBar seekBar) {
        }
    };


    @Override
    public void onBackPressed() {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();
        switch (id){
            case R.id.nav_deptos:
                Intent i1 = new Intent(MainActivity.this,ListaDepartamentosActivity.class);
                i1.putExtra("esBusqueda",false );
                startActivityForResult(i1, LISTAR_DEPARTAMENTOS);
                break;
            case R.id.nav_ofertas:
                break;
            case R.id.nav_perfil:
                Intent i2 = new Intent(this, SettingsActivity.class);
                startActivity(i2);
                break;
            case R.id.nav_reservas:
                Intent i = new Intent(MainActivity.this, ListarReservasActivity.class);
                Bundle b = new Bundle();
                b.putSerializable("reservas", reservas);
                i.putExtras(b);
                startActivity(i);
                break;
            case R.id.nav_destinos:
                break;

        }
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if(requestCode == LISTAR_DEPARTAMENTOS && resultCode == RESULT_OK) {
            Reserva reserva = (Reserva) data.getExtras().get("reserva");
            Log.d("reserva", reserva.toString());
            reservas.add(reserva);

            Toast toast = Toast.makeText(getApplicationContext(), "Reserva registrada correctamente.", Toast.LENGTH_LONG);
            toast.show();

            setAlarma();
            return;
        }
    }

    private void setAlarma() {
        Intent i = new Intent(this, TestReciver.class);
        Bundle b = new Bundle();
        b.putSerializable("reservas", reservas);
        i.putExtras(b);
        PendingIntent pendingIntent = PendingIntent.getBroadcast(this, 1, i, 0);
        AlarmManager alarmManager = (AlarmManager) getSystemService(Context.ALARM_SERVICE);
        alarmManager.setRepeating(AlarmManager.RTC_WAKEUP, 0, 15000, pendingIntent);
    }
}
