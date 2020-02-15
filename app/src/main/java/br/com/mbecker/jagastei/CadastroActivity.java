package br.com.mbecker.jagastei;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import java.util.Calendar;
import java.util.GregorianCalendar;
import java.util.Random;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import br.com.mbecker.jagastei.db.GastoModel;
import br.com.mbecker.jagastei.db.JaGasteiDbHelper;


public class CadastroActivity extends AppCompatActivity {

    private EditText mValor;
    private EditText mObs;
    private Button btnGastar;
    private JaGasteiDbHelper db;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cadastro);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        TextView aviso = findViewById(R.id.tvAviso);
        String[] arr = getResources().getStringArray(R.array.avisos_array);
        aviso.setText(arr[new Random().nextInt(arr.length)]);

        db = new JaGasteiDbHelper(CadastroActivity.this);

        mValor = findViewById(R.id.etValor);
        mObs = findViewById(R.id.etObs);
        btnGastar = findViewById(R.id.btGastar);
        btnGastar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Double valor = Double.valueOf(mValor.getText().toString());

                if (valor == 0) {
                    return;
                }

                Calendar c = GregorianCalendar.getInstance();
                String mesAno = Util.mesAno(c);
                GastoModel g = new GastoModel();
                g.setMesAno(mesAno);
                g.setObs(mObs.getText().toString());
                g.setQuando(c.getTimeInMillis());
                g.setValor(valor);

                mObs.setText("");
                mValor.setText("");

                db.salvarGasto(g);
                carregarLista();
            }
        });
    }

    private void carregarLista() {
        Intent intent = new Intent(getBaseContext(), MainActivity.class);
        startActivity(intent);
    }

}