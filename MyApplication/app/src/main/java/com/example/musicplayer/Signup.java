package com.example.musicplayer;


import android.content.DialogInterface;
import android.content.Intent;

import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.Toast;


public class Signup extends AppCompatActivity {

    ImageButton iconButton;
    EditText name;
    EditText surname;
    EditText username;
    EditText phone;
    EditText email;
    EditText password;
    EditText password2;
    Button signupButton_2;



    final int RESULT_LOAD_IMG = 100; // istek kodu

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_signup);
        defineVariables();
    }

    public void defineVariables(){
        name = (EditText) findViewById(R.id.name);
        surname = (EditText) findViewById(R.id.surname);
        username = (EditText) findViewById(R.id.username);
        phone = (EditText) findViewById(R.id.phone);
        email = (EditText) findViewById(R.id.email);
        password = (EditText) findViewById(R.id.password);
        password2 = (EditText) findViewById(R.id.password2);
        signupButton_2 = (Button) findViewById(R.id.signupButton_2);
        iconButton = (ImageButton) findViewById(R.id.iconButton);
    }

    public void uploadImage(View view) {
        Intent photoPickerIntent = new Intent(Intent.ACTION_PICK);
        photoPickerIntent.setType("image/*");
        startActivityForResult(photoPickerIntent, RESULT_LOAD_IMG);
    }

    public boolean checkNullText(){
        if (name.getText().toString().isEmpty() || surname.getText().toString().isEmpty() ||
                username.getText().toString().isEmpty() || phone.getText().toString().isEmpty() ||
                email.getText().toString().isEmpty() || password.getText().toString().isEmpty()  ||
                password2.getText().toString().isEmpty()){
            return true;
        }
        return false;
    }

    public void signupListener(View view){

        if(checkNullText()){
            Toast.makeText(getApplicationContext(), "L??tfen ilgili yerleri giriniz!", Toast.LENGTH_SHORT).show();
        }else if(!password.getText().toString().equals(password2.getText().toString())){
            Toast.makeText(getApplicationContext(), "Girilen ??ifreler ayn?? de??il!", Toast.LENGTH_SHORT).show();
        }else if(Person.findPerson(username.getText().toString()) != null){
            Toast.makeText(getApplicationContext(), "Girilen kullan??c?? ad?? kullan??mda. L??tfen ba??ka bir kullan??c?? ad??n?? belirleyiniz", Toast.LENGTH_LONG).show();
        }else if (iconButton.getBackground() != null){
            AlertDialog.Builder builder = new AlertDialog.Builder(this);
            builder.setTitle("Onayla");
            builder.setMessage("Bir profil foto??raf?? belirlemediniz, bu ??ekilde devam etmek istedi??inize emin misiniz?");

            builder.setPositiveButton("Evet", new DialogInterface.OnClickListener() {
                public void onClick(DialogInterface dialog, int which) {
                    dialog.dismiss();
                    createAccount();
                }
            });

            builder.setNegativeButton("Hay??r", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    dialog.dismiss();
                }
            });

            AlertDialog alert = builder.create();
            alert.show();
        }
        else{
            createAccount();
        }
    }

    @Override
    protected void onActivityResult(int reqCode, int resultCode, Intent data) {
        super.onActivityResult(reqCode, resultCode, data);
        if (resultCode == RESULT_OK) {
            try {
                iconButton.setBackground(null);
                iconButton.setImageURI(data.getData());
            } catch (Exception e) {
                e.printStackTrace();
                Toast.makeText(getApplicationContext(), "Bir ??eyler yanl???? gitti", Toast.LENGTH_LONG).show();
            }

        }else {
            Toast.makeText(getApplicationContext(), "Resim se??mediniz",Toast.LENGTH_LONG).show();
        }
    }

    public void createAccount(){
        Person p = new Person(name.getText().toString(),
                surname.getText().toString(),
                username.getText().toString(),
                phone.getText().toString(),
                email.getText().toString(),
                password.getText().toString());
        Person.getPersons().add(p);
        Person.setPersons(Person.getPersons());
        Person.saveArray(Person.getPersons(), this);

        Runnable r = new Runnable() {
            @Override
            public void run() {
                try {
                    new SendMailTask(Signup.this).execute("g??nderici hesap", "g??nderici hesap ??ifre",
                            p.getEmail(), "Ho??geldiniz",
                            "Kay??t bilgileriniz a??a????daki gibidir:\n" +
                                    "\n??sim: "+p.getIsim()+
                                    "\nSoyisim: "+p.getSoyisim()+
                                    "\nKullan??c?? Ad??: "+p.getUsername()+
                                    "\nTelefon: "+p.getTel()+
                                    "\nE-mail: "+p.getEmail()+
                                    "\nParola: "+p.getPassword()+
                                    "M??zik d??nyas??n ho??geldiniz. Keyifli vakitler ge??irmeniz dile??iyle.");
                }catch (Exception e){
                    e.printStackTrace();
                    Toast.makeText(getApplicationContext(), "Hesap bilgileri g??nderilemedi", Toast.LENGTH_SHORT).show();
                }
            }
        };

        Toast.makeText(getApplicationContext(), "Hesab??n??z ba??ar??yla olu??turuldu.", Toast.LENGTH_SHORT).show();
    }


}