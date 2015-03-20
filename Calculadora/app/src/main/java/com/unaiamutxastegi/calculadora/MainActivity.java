package com.unaiamutxastegi.calculadora;

import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;


public class MainActivity extends ActionBarActivity {

    //private Button btn1,btn2, btn3, btn4, btn5, btn6, btn7, btn8, btn9, btn0;
    //private Button btnSumar, btnRestar, btnMultiplicar, btnDividir, btnComa, btnDelete;
    private EditText textCalc;

    private Button Botones[];

    private CalcLogic Operaciones;

    private String Operando = "";

    @Override

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Botones = new Button[16];
        Operaciones = new CalcLogic();

        this.getLayout();
        this.addEventListener();
    }

    private void getLayout() {

        Botones[0] = (Button) findViewById(R.id.btnSuma);
        Botones[1] = (Button) findViewById(R.id.btnResta);
        Botones[2] = (Button) findViewById(R.id.btnMultiplicar);
        Botones[3] = (Button) findViewById(R.id.btnDividir);
        Botones[4] = (Button) findViewById(R.id.btnPunto);
        Botones[5] = (Button) findViewById(R.id.btnDelete);

        for (int i = 0; i < 9; i++) {
            String buttonID = "btn" + i;
            int resID = getResources().getIdentifier(buttonID, "id", getPackageName());
            Botones[6 + i] = (Button) findViewById(resID);
        }

        textCalc = (EditText) findViewById(R.id.textCalc);
    }

    private void addEventListener() {

        for (int k = 0; k < 6; k++) {
            Botones[k].setOnClickListener(OperClickListener);
        }
        for (int i = 6; i < Botones.length; i++) {
            Botones[i].setOnClickListener(btnClickListener);
        }
    }

    private View.OnClickListener OperClickListener = new View.OnClickListener() {
        @Override
        public void onClick(final View v) {
            switch (v.getId()) {
                case R.id.btnSuma:
                    Operaciones.add(textCalc.getText().toString());
                    break;
                case R.id.btnResta:

                    break;
                case R.id.btnMultiplicar:

                    break;
                case R.id.btnDividir:

                    break;
                case R.id.btnDelete:
                    textCalc.setText("");
                    break;
                case R.id.btnResultado:

                    break;
                case R.id.btnPunto:
                    mostrarNumero(".");
                    break;
            }

        }
    };

    private View.OnClickListener btnClickListener = new View.OnClickListener() {
        @Override
        public void onClick(final View v) {
            switch (v.getId()) {
                case R.id.btn1:
                    mostrarNumero("1");
                    break;
                case R.id.btn2:
                    mostrarNumero("2");
                    break;
                case R.id.btn3:
                    mostrarNumero("3");
                    break;
                case R.id.btn4:
                    mostrarNumero("4");
                    break;
                case R.id.btn5:
                    mostrarNumero("5");
                    break;
                case R.id.btn6:
                    mostrarNumero("6");
                    break;
                case R.id.btn7:
                    mostrarNumero("7");
                    break;
                case R.id.btn8:
                    mostrarNumero("8");
                    break;
                case R.id.btn9:
                    mostrarNumero("9");
                    break;
                case R.id.btn0:
                    mostrarNumero("0");
                    break;
            }

        }
    };

    private void mostrarNumero(String strNum) {
        Operando = strNum;
        if (strNum == "+") {
            Log.d("Suma", strNum);
        } else {
            Operaciones.setTotal(Double.parseDouble(strNum));
            textCalc.setText(textCalc.getText() + strNum);
        }


    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
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
}
