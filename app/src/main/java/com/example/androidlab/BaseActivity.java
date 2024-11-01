package com.example.androidlab;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.widget.Toolbar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.drawerlayout.widget.DrawerLayout;
import com.google.android.material.navigation.NavigationView;

public class BaseActivity extends AppCompatActivity {

    protected DrawerLayout drawerLayout;
    protected Toolbar myToolbar;
    protected ActionBarDrawerToggle toggle;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main); // Use a layout that includes the toolbar

        myToolbar = findViewById(R.id.toolbar);
        setSupportActionBar(myToolbar);

        myToolbar.setNavigationIcon(R.drawable.icon4);
        drawerLayout = findViewById(R.id.drawer_layout);
        NavigationView navigationView = findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(item -> {
            return onNavigationItemSelected(item);
        });


        toggle = new ActionBarDrawerToggle(this, drawerLayout, myToolbar,
                R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawerLayout.addDrawerListener(toggle);
        toggle.syncState();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu, menu);
        return super.onCreateOptionsMenu(menu);
    }


    public boolean onNavigationItemSelected(@NonNull MenuItem item) {
        System.out.println(item.getItemId());

        if (item.getItemId() == 2131231025)
        {

            Intent intent = new Intent(BaseActivity.this, MainActivity.class);
            startActivity(intent);

        }
        if (item.getItemId() == 2131231023)
        {
            // dad joke
            Intent intent = new Intent(this, dadjoke.class);
            startActivity(intent);
        }
        if (item.getItemId() == 2131231024)
        {
            finishAffinity();
        }

        return false;
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {

        System.out.println(item.getItemId());
        if (item.getItemId() == 2131230934)
        {
            Toast.makeText(this, "You clicked on item 1", Toast.LENGTH_SHORT).show();
        }
        else if (item.getItemId() == 2131230935 )
        {
            Toast.makeText(this, "You clicked on item 2", Toast.LENGTH_SHORT).show();
        }
        else if (item.getItemId() == 16908332)
        {
            System.out.println("hamburget");
        }
        return super.onOptionsItemSelected(item);
    }

}
