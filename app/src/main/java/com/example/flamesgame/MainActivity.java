package com.example.flamesgame;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.app.Dialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import android.widget.Button;
import android.widget.Toast;

import java.util.LinkedHashSet;
import java.util.Set;

public class MainActivity extends AppCompatActivity {

    EditText _name;
    EditText _flameName;
    EditText _result;
    Button _computeFlame;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        _name = findViewById(R.id.name);
        _flameName = findViewById(R.id.flame_name);
        _result = findViewById(R.id.result);
        _computeFlame = findViewById(R.id.compute_flame);

        _computeFlame.setOnClickListener(computeFlame);
    }

    private View.OnClickListener computeFlame = new View.OnClickListener() {
        @Override
        public void onClick(View v) {

            // ALGORITHM LINK
            // https://flames.games/algorithm
            if (_name.getText().toString().isEmpty() || _flameName.getText().toString().isEmpty())
            {
                Toast.makeText(MainActivity.this,
                        "Please enter value for name and flame name",
                        Toast.LENGTH_LONG).show();
                return;
            }

            StringBuilder name = new StringBuilder(_name.getText().
                    toString().toUpperCase().replace(" ", ""));
            StringBuilder flameName = new StringBuilder(_flameName.getText().
                    toString().toUpperCase().replace(" ", ""));

            for (int i = 0; i < name.length(); ++i)
            {
                char c = name.charAt(i);
                for (int j = 0; j < flameName.length(); ++j)
                {
                    char d = flameName.charAt(j);
                    if (c == d)
                    {
                        name.deleteCharAt(i);
                        flameName.deleteCharAt(j);
                        --i;
                        --j;
                        break;
                    }
                }
            }
            name = name.append(flameName);

            String remaining = name.toString();
            String flames = "FLAMES";

            int k = 6, l = 0;
            while(k > 1)
            {
                for(int m = 1; m < remaining.length(); ++m)
                {
                    if (flames.charAt(l) != 'X')
                    {
                        ++l;
                        if(l==6)
                            l=0;
                    }
                    else
                    {
                        ++l;
                        if(l == 6)
                            l = 0;
                        --m;
                    }
                }
                while (flames.charAt(l)=='X')
                {
                    ++l;
                    if(l == 6)
                        l = 0;
                }
                flames = flames.substring(0, l) + "X" + flames.substring(l + 1);
                --k;
            }

            char e = '\0';
            for(int i = 0; i < flames.length(); ++i)
            {
                if (flames.charAt(i)!='X')
                    e = flames.charAt(i);
            }
            String result = "";

            switch(e)
            {
                case 'F': result = "FRIENDS";
                    break;
                case 'L': result = "LOVERS";
                    break;
                case 'A': result = "ATTRACTION";
                    break;
                case 'M': result = "MARRIED";
                    break;
                case 'E': result = "ENEMIES";
                    break;
                case 'S': result = "SIBLINGS";
                    break;
                default :break;
            }

            // Toast.makeText(MainActivity.this, remaining, Toast.LENGTH_LONG).show();

            try
            {
                InputMethodManager imm = (InputMethodManager)getSystemService(INPUT_METHOD_SERVICE);
                imm.hideSoftInputFromWindow(getCurrentFocus().getWindowToken(), 0);
            }
            catch (Exception ex)
            {

            }

            AlertDialog alertDialog = new AlertDialog.Builder(MainActivity.this).create(); //Read Update
            alertDialog.setTitle("FLAMES RESULT!");
            alertDialog.setMessage("You are " + result);
            alertDialog.setButton(Dialog.BUTTON_POSITIVE,"OK",new DialogInterface.OnClickListener(){
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    alertDialog.dismiss();
                }
            });

            alertDialog.show();
            _result.setText("You are " + result);
        }
    };
}