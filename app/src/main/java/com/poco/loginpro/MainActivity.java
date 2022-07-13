package com.poco.loginpro;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseAuthUserCollisionException;

public class MainActivity extends AppCompatActivity {

    // declarar variables
    private EditText EmailTxt;
    private EditText PasswordTxt;
    private Button loginBtn, registerBtn;

    // Declaramos un objeto FireBase
    private FirebaseAuth firebaseAuth;

    public MainActivity() {
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // Inicializamos el objeto de FireBase
        firebaseAuth = firebaseAuth.getInstance();

        // Referenciamos los views
        EmailTxt = (EditText) findViewById(R.id.username);
        PasswordTxt = (EditText) findViewById(R.id.password);

        registerBtn = (Button) findViewById(R.id.registerBtn);
        loginBtn = (Button) findViewById(R.id.loginBtn);

        registerBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                registrarUsuario();
            }
        });

        loginBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                loginUser();
            }
        });
    }

    // Metodos

    private void registrarUsuario() {
        // Obtener el email y contra de las cajas de texto
        String email = EmailTxt.getText().toString().trim();
        String password = PasswordTxt.getText().toString().trim();

        // Validamos que no esten vacias
        if (TextUtils.isEmpty(email)){
            Toast.makeText(this, "Email o nombre de usuario vacia" + email,Toast.LENGTH_LONG).show();
            return;
        }

        if (password.equals("")){
            Toast.makeText(this, "Contraseña vacia",Toast.LENGTH_LONG).show();
            return;
        }

        // register a new user in FireBase
        firebaseAuth.createUserWithEmailAndPassword(email,password)
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        // Verificar si se guardo e inicio
                        if (task.isSuccessful()) {
                            Toast.makeText(MainActivity.this, "Email registrado correctamente",Toast.LENGTH_LONG).show();

                        } else {

                            // validar la existecia
                            if (task.getException() instanceof FirebaseAuthUserCollisionException) { // si existe una colisino
                                Toast.makeText(MainActivity.this, "Email ya existente",Toast.LENGTH_LONG).show();
                            } else {
                                Toast.makeText(MainActivity.this, "Email no registrado correctamente",Toast.LENGTH_LONG).show();
                            }

                        }
                    }
                });
    }

    // logear usuario
    private void loginUser() {

        // Obtener el email y contra de las cajas de texto
        String email = EmailTxt.getText().toString().trim();
        String password = PasswordTxt.getText().toString().trim();

        // Validamos que no esten vacias
        if (TextUtils.isEmpty(email)){
            Toast.makeText(this, "Email o nombre de usuario vacia" + email,Toast.LENGTH_LONG).show();
            return;
        }

        if (password.equals("")){
            Toast.makeText(this, "Contraseña vacia",Toast.LENGTH_LONG).show();
            return;
        }

        // verificar el user in FireBase
        firebaseAuth.signInWithEmailAndPassword(email,password)
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        // Verificar si se guardo e inicio
                        if (task.isSuccessful()) {
                            Toast.makeText(MainActivity.this, "Bienvenido",Toast.LENGTH_LONG).show();
                            Intent intencion = new Intent(getApplication(),MainActivityTwo.class);
                            intencion.putExtra(MainActivityTwo.usuarioName, email);
                            startActivity(intencion);
                        } else {
                            Toast.makeText(MainActivity.this, "Usuario no existente",Toast.LENGTH_LONG).show();
                        }
                    }
                });
    }

}