package com.infosoc.inframaps.infosoc;

import android.app.Activity;
import android.app.AlertDialog;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;
import android.widget.TextView;

public class Infosoc extends Activity {
    /** Called when the activity is first created. */
    databasehelper myDb;
    EditText edit_nombre, edit_duracion, edit_id;
    Button btnAdd;
    Button btnConsultar;
    Button btnActualiza;
    Button btnBorrar;
    public Infosoc() {
    }
    @Override
    public void onCreate(Bundle savedInstanceState)
    {
        //SQLiteDatabase db = new databasehelper(this).getWritableDatabase();
        myDb = new databasehelper(this);
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_infosoc);
        edit_nombre = (EditText)findViewById(R.id.viaje_texto);
        edit_duracion = (EditText)findViewById(R.id.duracion_text);
        edit_id = (EditText)findViewById(R.id.id_view);
        btnAdd = (Button)findViewById(R.id.button_add);
        btnConsultar = (Button)findViewById(R.id.button_consultar);
        btnActualiza = (Button)findViewById(R.id.button_actualizar);
        btnBorrar = (Button)findViewById(R.id.button_borrar);
        addData();
        verData();
        actualizar_data();
        borrar_data();
    }

    public void addData(){
        btnAdd.setOnClickListener(
                new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        boolean isinserted = myDb.insertar(edit_nombre.getText().toString(), edit_duracion.getText().toString());
                        if (isinserted) {
                            Toast.makeText(Infosoc.this, "Insertado", Toast.LENGTH_LONG).show();
                        } else {
                            Toast.makeText(Infosoc.this, "NOOO INSERTADO", Toast.LENGTH_LONG).show();
                        }
                    }
                }
        );
    }

    public void verData(){
        btnConsultar.setOnClickListener(
                new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Cursor res = myDb.consultarTodo();
                        if(res.getCount() == 0){
                            showMessage("Error", "No data");
                            return;
                        }
                        StringBuffer buffer = new StringBuffer();
                        while(res.moveToNext()){
                            buffer.append("ID : "+res.getString(0)+"\n");
                            buffer.append("Nombre del viaje : "+res.getString(1)+"\n");
                            buffer.append("Duración : "+res.getString(2)+"\n\n");
                        }
                        showMessage("Data", buffer.toString());
                    }
                }
        );
    }

    public void showMessage(String titulo, String mensaje){
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setCancelable(true);
        builder.setTitle(titulo);
        builder.setMessage(mensaje);
        builder.show();
    }

    public void actualizar_data(){
        btnActualiza.setOnClickListener(
                new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        boolean actualizado = myDb.actualizar(edit_id.getText().toString(),
                                edit_nombre.getText().toString(),
                                edit_duracion.getText().toString());
                        if (actualizado) {
                            Toast.makeText(Infosoc.this, "Actualizado", Toast.LENGTH_LONG).show();
                        } else {
                            Toast.makeText(Infosoc.this, "NOOO Actualizado", Toast.LENGTH_LONG).show();
                        }
                    }
                }
        );
    }

    public void borrar_data(){
        btnBorrar.setOnClickListener(
                new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Integer borrado = myDb.borrar(edit_id.getText().toString());
                        if(borrado > 0){
                            Toast.makeText(Infosoc.this, "Borrado", Toast.LENGTH_LONG).show();
                        } else {
                            Toast.makeText(Infosoc.this, "NOOO Borrado", Toast.LENGTH_LONG).show();
                        }
                    }
                }
        );
    }
}
