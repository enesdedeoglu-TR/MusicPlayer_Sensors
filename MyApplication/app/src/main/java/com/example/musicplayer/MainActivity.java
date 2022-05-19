package com.example.musicplayer;



import android.content.BroadcastReceiver;
import android.content.Intent;
import android.content.IntentFilter;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;


import java.io.File;
import java.util.ArrayList;


public class MainActivity extends AppCompatActivity {

    EditText usernameText;
    EditText passwordText;
    Button loginButton;
    Button signupButton;

    int hataliGiris;

    BroadcastReceiver br = new MyBroadcastReceiver();


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        System.out.println(this.getFilesDir());
        defineVariables();

        IntentFilter filter = new IntentFilter("DurumBilgisi");
        registerReceiver(br, filter);
    }

    public void defineVariables(){
        usernameText = (EditText) findViewById(R.id.usernameText);
        passwordText = (EditText) findViewById(R.id.passwordText);
        loginButton = (Button) findViewById(R.id.loginButton);
        signupButton = (Button) findViewById(R.id.signupButton);
        hataliGiris = 0;
        if ((ArrayList<Person>) Person.loadArray(new File(String.valueOf(this.getFilesDir()))) == null){
            Person p = new Person("Ali",
                    "Dayı",
                    "ali",
                    "123",
                    "asd",
                    "123");
            ArrayList<Person> yeni = new ArrayList<Person>();
            yeni.add(p);
            Person.setPersons(yeni);
            Person.saveArray(Person.getPersons(), this);
        }else{
            Person.setPersons((ArrayList<Person>) Person.loadArray(new File(String.valueOf(this.getFilesDir()))));
        }

    }

    public void login(View view){
        if (usernameText.getText().toString().isEmpty() || passwordText.getText().toString().isEmpty()){
            Toast.makeText(getApplicationContext(), "Lütfen ilgili yerleri giriniz!", Toast.LENGTH_LONG).show();
        }else{
            Person p = Person.findPerson(usernameText.getText().toString());
            if (p != null && p.getPassword().equals(passwordText.getText().toString())){
                Toast.makeText(getApplicationContext(), "Giriş Başarılı", Toast.LENGTH_SHORT).show();
                Intent intent = new Intent(this, MusicList.class);
                startActivity(intent);
            }else if(hataliGiris == 2){
                Toast.makeText(getApplicationContext(), "3 kez yanlış bilgi girdiniz. Kaydol ekranına yönlendiriliyorsunuz...", Toast.LENGTH_LONG).show();
                signUp(view);
            }else{
                hataliGiris = hataliGiris + 1;
                Toast.makeText(getApplicationContext(), "Kullanıcı Adı veya Şifre Hatalı!", Toast.LENGTH_SHORT).show();
            }
        }
    }
    public void signUp(View view){
        Intent intent = new Intent(this, Signup.class);
        startActivity(intent);
    }

}
