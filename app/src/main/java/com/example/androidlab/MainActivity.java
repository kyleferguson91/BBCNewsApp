package com.example.androidlab;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.Switch;
import android.widget.TextView;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {
    public class todoItem {
        String todoText;
        Boolean urgent;

        public todoItem(String todoText, Boolean urgent) {
            this.todoText = todoText;
            this.urgent = urgent;

        }
    }

    List<todoItem> elements = new ArrayList<>();



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



        ListView myList = (ListView) findViewById(R.id.mainList);

        myListAdapter adapter = new myListAdapter();
        myList.setAdapter(adapter);


        Button add = findViewById(R.id.addbutton);

        add.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //get the text
                EditText text = findViewById(R.id.editTextText);
                String textcontent = text.getText().toString();
                //get the urgency
                Switch urgency = findViewById(R.id.switch1);
                //pass to new list item
                //if switch is checked, urgency is true, if not checked, then not true.
                todoItem todo = new todoItem(textcontent, urgency.isChecked());

                elements.add(todo);
            }
        });

    }

    private class myListAdapter extends BaseAdapter {
        @Override
        public int getCount() {
            return elements.size();
        }

        @Override
        public Object getItem(int i) {
            return "This is row: " + i;
        }

        @Override
        public long getItemId(int i) {
            return 0;
        }

        @Override
        public View getView(int position, View old, ViewGroup parent) {

            View newView = old;
            LayoutInflater inflater = getLayoutInflater();


            if (newView == null) {
                newView = inflater.inflate(R.layout.list_item, parent, false);
            }

            TextView textView = newView.findViewById(R.id.listText);
            textView.setText((getItem(position).toString()));

            return newView;
        }

    }

}