package com.example.androidlab;

import android.content.ContentValues;
import android.content.DialogInterface;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.Color;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.Switch;
import android.widget.TextView;

import androidx.activity.EdgeToEdge;
import androidx.annotation.ColorInt;
import androidx.appcompat.app.AlertDialog;
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

// open the database
        SQLOpener dbHelper = new SQLOpener(this);
        SQLiteDatabase db = dbHelper.getWritableDatabase();

        // create cursor to loop through existing db values
        Cursor cursor = db.rawQuery("Select * FROM " + SQLOpener.TABLE_NAME, null);
        // if we have a truthy row
        if (cursor.moveToFirst())
        {
            do {
                //get the todo and urgency text
                String todoText = cursor.getString(cursor.getColumnIndex(SQLOpener.todoText));
                String urgencyText = cursor.getString(cursor.getColumnIndex(SQLOpener.todoUrgency));
                // convert our urgency to a boolean
                boolean isUrgent = urgencyText.equals("Urgent");

                //add to our list!
                todoItem todo = new todoItem(todoText, isUrgent);
                elements.add(todo);

            }
            while (cursor.moveToNext());
        }
        // close the cursor
        cursor.close();

        // notify list changed

        adapter.notifyDataSetChanged();

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
                String urgencytext;
                if (urgency.isChecked()) urgencytext = "Urgent";
                else urgencytext = "Not Urgent";

                // add to database on submission
                ContentValues cValues = new ContentValues();

                cValues.put(SQLOpener.todoText, textcontent);
                cValues.put(SQLOpener.todoUrgency, urgencytext);
                long id = db.insert(SQLOpener.TABLE_NAME, null, cValues);

                elements.add(todo);
                text.setText("");
                urgency.setChecked(false);


                adapter.notifyDataSetChanged();
            }
        });

        myList.setOnItemLongClickListener((p,b,pos,id) -> {
            AlertDialog.Builder alterDialogBuilder = new AlertDialog.Builder(this);
            String message = getString(R.string.rowis) + " " + pos;
            alterDialogBuilder.setTitle(getString(R.string.deletethis)).setMessage(message);

            alterDialogBuilder.setPositiveButton(getText(R.string.yes), new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {

                    // we also what to remove this value from the database

                    db.execSQL("DELETE FROM " + SQLOpener.TABLE_NAME + " WHERE " + SQLOpener.todoText + " = '" + elements.get(pos).todoText + "'");

                    elements.remove(pos);



                    adapter.notifyDataSetChanged();
                    dialog.dismiss();
                }
            });

            alterDialogBuilder.setNegativeButton(getText(R.string.no), new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {

                    dialog.dismiss();
                }
            });



            alterDialogBuilder.create().show();
            return true;
        });
    }

    private class myListAdapter extends BaseAdapter {
        @Override
        public int getCount() {
            return elements.size();
        }

        @Override
        public Object getItem(int i) {
            return elements.get(i);
        }

        @Override
        public long getItemId(int i) {
            return i;
        }

        @Override
        public View getView(int position, View old, ViewGroup parent) {

            View newView = old;
            LayoutInflater inflater = getLayoutInflater();


            if (newView == null) {
                newView = inflater.inflate(R.layout.list_item, parent, false);
            }

            TextView textView = newView.findViewById(R.id.listText);
            todoItem currentItem = elements.get(position);

            if (currentItem.urgent)
            {
                textView.setBackgroundColor(Color.RED);
                textView.setTextColor(Color.WHITE);
            }

            textView.setText(currentItem.todoText.toString());

            return newView;
        }

    }

}