package com.example.androidlab;
import android.content.SharedPreferences;
import android.view.View;
import android.widget.Button;
import android.os.Bundle;
import android.widget.Button;
import android.content.Intent;
import android.widget.EditText;

import androidx.activity.EdgeToEdge;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;







public class MainActivity extends AppCompatActivity {

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
        EditText name = findViewById(R.id.editTextText);
        SharedPreferences prefs = getSharedPreferences("file", MODE_PRIVATE);

        String savedname = prefs.getString("name", "");
        name.setText(savedname);
        Button nextmain = findViewById(R.id.buttonnextmain);





        nextmain.setOnClickListener(click->


                {
                    Intent pagetwo = new Intent(this, NameActivity.class);
                    pagetwo.putExtra("name", name.getText().toString());
                    startActivityForResult(pagetwo, 1);


                }


                );


    }

    @Override
    protected void onPause() {
        super.onPause();

        SharedPreferences prefs = getSharedPreferences("file", MODE_PRIVATE);

        SharedPreferences.Editor edit = prefs.edit();
        EditText name = findViewById(R.id.editTextText);

        edit.putString("name", name.getText().toString());

        edit.apply();
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (resultCode == 0)
        {
        EditText name = findViewById(R.id.editTextText);

        }
        else if (resultCode == 1)
        {
        finish();
        }
    }
}