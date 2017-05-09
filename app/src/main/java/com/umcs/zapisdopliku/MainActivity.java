package com.umcs.zapisdopliku;

import android.Manifest;
import android.content.pm.PackageManager;
import android.os.Environment;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }


    //Log TAG;
    private static final int MY_PERMISSIONS_REQUEST_READ_EXTERNAL_STORAGE = 101;

    public void SaveText(View view) {
        int permissionCheck = ContextCompat.checkSelfPermission(this,
                Manifest.permission.WRITE_EXTERNAL_STORAGE);
        int permissionCheck2 = ContextCompat.checkSelfPermission(this,
                Manifest.permission.READ_EXTERNAL_STORAGE);
        if (permissionCheck != PackageManager.PERMISSION_GRANTED || permissionCheck2 !=
                PackageManager.PERMISSION_GRANTED) {
            //Log.i(TAG, "Permission to record denied");
            makeRequest();
        } else {
            //SaveTextS();
            try {
                // Otwarcie pliku myfilename.txt do zapisu z trybem dopisania do
                //istniejącego pliku:
                OutputStreamWriter out = new
                        OutputStreamWriter(openFileOutput("myfilename.txt",MODE_APPEND));
                // Pobranie tekstu z kontrolki EditText do obiektu klasy string
                //a następnie zapis do pliku:
                EditText ET = (EditText)findViewById(R.id.editText1);
                String text = ET.getText().toString();
                out.write(text);
                out.write('\n');
                // zamknięcie pliku
                out.close();
                Toast.makeText(this,"Text Saved !",Toast.LENGTH_LONG).show();
            } catch (java.io.IOException e) {
                //gdy nie uda się zapisać:
                Toast.makeText(this,"Sorry Text could't be added", Toast.LENGTH_LONG).show();
            }
        }
    }

    protected void makeRequest() {
        ActivityCompat.requestPermissions(this,
                new String[]{Manifest.permission.READ_EXTERNAL_STORAGE,Manifest.permission.WRITE_EXTERNAL_STORAGE},
                MY_PERMISSIONS_REQUEST_READ_EXTERNAL_STORAGE);
    }

    @Override
    public void onRequestPermissionsResult(int requestCode,

        String permissions[], int[] grantResults) {
        switch (requestCode) {
            case MY_PERMISSIONS_REQUEST_READ_EXTERNAL_STORAGE: {
                if (grantResults.length == 0
                        || grantResults[0] !=
                        PackageManager.PERMISSION_GRANTED || grantResults[1] !=
                        PackageManager.PERMISSION_GRANTED) {
                    //Log.i(TAG, "Permission has been denied by user");
                } else {
                    //Log.i(TAG, "Permission has been granted by user");
                    //SaveTextS();
                    try {
                        // Otwarcie pliku myfilename.txt do zapisu z trybem dopisania do
                        //istniejącego pliku:
                        OutputStreamWriter out = new
                                OutputStreamWriter(openFileOutput("myfilename.txt",MODE_APPEND));
                        // Pobranie tekstu z kontrolki EditText do obiektu klasy string
                        //a następnie zapis do pliku:
                        EditText ET = (EditText)findViewById(R.id.editText1);
                        String text = ET.getText().toString();
                        out.write(text);
                        out.write('\n');
                        // zamknięcie pliku
                        out.close();
                        Toast.makeText(this,"Text Saved !",Toast.LENGTH_LONG).show();
                    } catch (java.io.IOException e) {
                        //gdy nie uda się zapisać:
                        Toast.makeText(this,"Sorry Text could't be added", Toast.LENGTH_LONG).show();
                    }
                }
                return;
            }
        }
    }

    public void SaveTextS(){
        try {
            // Otwarcie pliku myfilename.txt do zapisu z trybem dopisania do
            //istniejącego pliku:
            OutputStreamWriter out = new
                    OutputStreamWriter(openFileOutput("myfilename.txt",MODE_PRIVATE));
            // Pobranie tekstu z kontrolki EditText do obiektu klasy string
            //a następnie zapis do pliku:
            EditText ET = (EditText)findViewById(R.id.editText1);
            String text = ET.getText().toString();
            out.write(text);
            out.write('\n');
            // zamknięcie pliku
            out.close();
            Toast.makeText(this,"Text Saved !",Toast.LENGTH_LONG).show();
        } catch (java.io.IOException e) {
            //gdy nie uda się zapisać:
            Toast.makeText(this,"Sorry Text could't be added", Toast.LENGTH_LONG).show();
        }
    }

    public void ViewText (View view) throws FileNotFoundException {
        StringBuilder text = new StringBuilder();
        try {
            // Otwarcie pliku do wczytania:
            InputStream instream = openFileInput("myfilename.txt");
            // Jeżeli istnieje możliwość wczytania pliku:
            if (instream != null) {
                // przygotujmy plik do wczytania:
                InputStreamReader inputreader = new InputStreamReader(instream);
                BufferedReader buffreader = new BufferedReader(inputreader);
                String line=null;
                while (( line = buffreader.readLine()) != null) {
                //Czytamy plik wiersz po wierszu i zapisujemy
                    text.append(line);
                    text.append('\n');
                }}}catch (IOException e) {
            e.printStackTrace();
        }
        //Ustawiamy nasz wczytany tekst w TextView
        TextView tv = (TextView)findViewById(R.id.textView1);
        tv.setText(text);
    }

    public void SaveSD(View view){
        // Potrzebujemy ścieżki do karty SD:
        File sdcard = Environment.getExternalStorageDirectory();
        // Dodajemy do ścieżki własny folder:
        File dir = new File(sdcard.getAbsolutePath() + "/MojePliki/");
        // jeżeli go nie ma to tworzymy:
        dir.mkdir();
        // Zapiszmy do pliku nasz tekst:
        File file = new File(dir, "myfilename.txt");
        EditText ET = (EditText)findViewById(R.id.editText1);
        String text = ET.getText().toString();
        try {
            FileOutputStream os = new FileOutputStream(file);
            os.write(text.getBytes());
            os.close();
        }catch (IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
    }

    public void ViewSD(View view){
        File sdcard = Environment.getExternalStorageDirectory();
        File dir = new File(sdcard.getAbsolutePath() + "/MojePliki/");
        File file = new File(dir, "myfilename.txt");
        int length = (int) file.length();
        byte[] bytes = new byte[length];
        FileInputStream in;
        try {
            in = new FileInputStream(file);
            in.read(bytes);
            in.close();
        } catch (FileNotFoundException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        } catch (IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        String contents = new String(bytes);
        TextView tv = (TextView)findViewById(R.id.textView1);
        tv.setText(contents);
    }

    public void Clear(View view){
        EditText ET=(EditText)findViewById(R.id.editText1);
        ET.setText("");
        TextView TV=(TextView)findViewById(R.id.textView1);
        TV.setText("");

    }
}

