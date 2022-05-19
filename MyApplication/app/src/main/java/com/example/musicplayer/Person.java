package com.example.musicplayer;


import android.app.Activity;
import android.graphics.Bitmap;
import android.util.Log;


import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;
import java.util.ArrayList;

public class Person implements Serializable {

    private String isim;
    private String soyisim;
    private String username;
    private String tel;
    private String email;
    private String password;
    private Bitmap foto;
    private static final long serialVersionUID = 8264888396180477662L;
    private static ArrayList<Person> persons;

    public static ArrayList<Person> getPersons() {
        return persons;
    }

    public static void setPersons(ArrayList<Person> persons) {
        Person.persons = persons;
    }

    public Person(String isim, String soyisim, String username, String tel, String email, String password) {
        this.isim = isim;
        this.soyisim = soyisim;
        this.username = username;
        this.tel = tel;
        this.email = email;
        this.password = password;
    }

    public Person(String isim, String soyisim, String username, String tel, String email, String password, Bitmap foto) {
        this.isim = isim;
        this.soyisim = soyisim;
        this.username = username;
        this.tel = tel;
        this.email = email;
        this.password = password;
        this.foto = foto;
    }

    public String getIsim() {
        return isim;
    }

    public void setIsim(String isim) {
        this.isim = isim;
    }

    public String getSoyisim() {
        return soyisim;
    }

    public void setSoyisim(String soyisim) {
        this.soyisim = soyisim;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getTel() {
        return tel;
    }

    public void setTel(String tel) {
        this.tel = tel;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public Bitmap getFoto() {
        return foto;
    }

    public void setFoto(Bitmap foto) {
        this.foto = foto;
    }

    public static Person findPerson(String personUsername){
        for(Person p :  persons){
            if (personUsername.equals(p.getUsername())){
                return p;
            }
        }
        return null;
    }


    public static void saveArray(ArrayList persons, Activity ma){
        try {
            ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream(new File(ma.getFilesDir(), "personsArray.data")));
            oos.writeObject(persons);
            oos.flush();
            oos.close();
        }catch(Exception ex){
            Log.v("Serialization Save Error: ", ex.getMessage());
            ex.printStackTrace();
        }
    }

    public static Object loadArray(File file){
        try {
            ObjectInputStream ois = new ObjectInputStream(new FileInputStream(file.getPath() + File.separator + "personsArray.data"));
            Object o = ois.readObject();
            return o;
        }catch(Exception ex){
            Log.v("Serialization Read Error: ", ex.getMessage());
            ex.printStackTrace();
        }
        return null;
    }

}
