package com.example.base_datos;
//Se importan las librerias necesarias

import android.content.Context;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.example.base_datos.entities.User;
import com.example.base_datos.model.UserDAO;

import java.util.ArrayList;
import java.util.regex.Pattern;

public class MainActivity extends AppCompatActivity {
    //Se declaran las variables para la interfaz del usuario
    private Context context;
    private EditText etDocumento, etUsuario, etNombres, etApellidos, etContraseña;
    private ListView listUsers;
    private Button btnGuardar, btnListUsers, btnBuscar, btnBorrar, btnActualizar;
    private int documento;
    private String nombres, apellidos, usuario, pass;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_main);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });
        begin();
        //se llama begin() para inicializar las vistas
        btnGuardar.setOnClickListener(this::createUser);
        btnListUsers.setOnClickListener(this::listUserShow);
        btnBuscar.setOnClickListener(this::searchUser);
        btnBorrar.setOnClickListener(this::clearFields);
        btnActualizar.setOnClickListener(this::updateUser);
    }

    private void createUser(View view) {
        if (!validateInputs()) return; // Valida los datos antes de continuar
        catchData(); // Captura los datos del formulario
        User user = new User(this.documento, this.nombres, this.apellidos, this.usuario, this.pass);
        UserDAO dao = new UserDAO(this.context, view);
        if (dao.isUserExists(documento, usuario)) {
            Toast.makeText(context, "Usuario ya registrado", Toast.LENGTH_SHORT).show();
        } else {
            dao.getInsertUser(user);
            Toast.makeText(context, "Usuario registrado", Toast.LENGTH_SHORT).show();
            clearFields(null);
        }
    }

    private void searchUser(View view) {
        UserDAO userDao = new UserDAO(context, view);
        String docText = etDocumento.getText().toString().trim();
        if (docText.isEmpty()) {
            Toast.makeText(context, "Ingrese un documento para buscar", Toast.LENGTH_SHORT).show();
            return;
        }
        int doc = Integer.parseInt(docText);
        User user = userDao.getUserByDocument(doc);
        if (user != null) {
            etNombres.setText(user.getNombres());
            etApellidos.setText(user.getApellidos());
            etUsuario.setText(user.getUsuario());
            etContraseña.setText(user.getPassword());
        } else {
            Toast.makeText(context, "Usuario no encontrado", Toast.LENGTH_SHORT).show();
        }
    }

    private void updateUser(View view) {
        if (!validateInputs()) return;
        catchData();
        User user = new User(this.documento, this.nombres, this.apellidos, this.usuario, this.pass);
        UserDAO dao = new UserDAO(this.context, view);
        if (dao.updateUser(user)) {
            Toast.makeText(context, "Usuario actualizado", Toast.LENGTH_SHORT).show();
            clearFields(null);
        } else {
            Toast.makeText(context, "Error al actualizar", Toast.LENGTH_SHORT).show();
        }
    }

    private void listUserShow(View view) {
        UserDAO userDao = new UserDAO(context, findViewById(R.id.lvLista));
        ArrayList<User> userList = userDao.getUserList();
        ArrayAdapter<User> adapter = new ArrayAdapter<>(this, android.R.layout.simple_list_item_1, userList);
        listUsers.setAdapter(adapter);
    }

    private void clearFields(View view) {
        etDocumento.setText("");
        etNombres.setText("");
        etApellidos.setText("");
        etUsuario.setText("");
        etContraseña.setText("");
    }

    private boolean validateInputs() {
        String docText = etDocumento.getText().toString().trim();
        String userText = etUsuario.getText().toString().trim();
        String passText = etContraseña.getText().toString().trim();

        if (docText.isEmpty() || userText.isEmpty() || passText.isEmpty()) {
            Toast.makeText(context, "Todos los campos son obligatorios", Toast.LENGTH_SHORT).show();
            return false;
        }
        if (!Pattern.matches("\\d+", docText)) {
            Toast.makeText(context, "Documento inválido", Toast.LENGTH_SHORT).show();
            return false;
        }

        if (!Pattern.matches("^[a-zA-Z0-9._%+-]+@[a-zA-Z0-9.-]+\\.[a-zA-Z]{2,}$"
                , userText)) {
            Toast.makeText(context, "Correo inválido", Toast.LENGTH_SHORT).show();
            return false;
        }
        return true;
    }

    private void catchData() {
        this.documento = Integer.parseInt(etDocumento.getText().toString().trim());
        this.nombres = etNombres.getText().toString().trim();
        this.apellidos = etApellidos.getText().toString().trim();
        this.usuario = etUsuario.getText().toString().trim();
        this.pass = etContraseña.getText().toString().trim();
    }

    private void begin() {
        this.context = this;
        this.btnGuardar = findViewById(R.id.btnRegister);
        this.btnListUsers = findViewById(R.id.btnListar);
        this.btnBuscar = findViewById(R.id.btnBuscar);
        this.btnBorrar = findViewById(R.id.btnBorrar);
        this.btnActualizar = findViewById(R.id.btnActualizar);
        this.etNombres = findViewById(R.id.etNombres);
        this.etApellidos = findViewById(R.id.etApellidos);
        this.etDocumento = findViewById(R.id.etDocumento);
        this.etUsuario = findViewById(R.id.etUsuario);
        this.etContraseña = findViewById(R.id.etContraseña);
        this.listUsers = findViewById(R.id.lvLista);
    }
}
