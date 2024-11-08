package com.example.androidlab;

import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;

import androidx.activity.EdgeToEdge;
import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

import com.google.android.material.navigation.NavigationView;

import java.util.Objects;

public class MainActivity extends AppCompatActivity {

    //create drawer layout variables
    private DrawerLayout drawerLayout;
    private ActionBarDrawerToggle toggle;


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

        //inflate the toolbar
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        toolbar.setLogo(R.drawable.bbclogo); // Use your image here (e.g., logo.png)
        Objects.requireNonNull(getSupportActionBar()).setTitle(" News Feed");






        }
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_main, menu);

        return true;
    }

    @Override
    public boolean onOptionsItemSelected( MenuItem item) {
        String itemTitle = item.getTitle().toString();
        Log.d("MainActivity", "Selected item: " + itemTitle);
        getSupportActionBar().setTitle(" " + itemTitle);

     //
        if(itemTitle.equals("News Feed".trim()))
        {
            System.out.println("load news feed frag");
            loadFragment(new NewsFeedFragment());
        }
        if(itemTitle.equals("Settings".trim()))
        {
            System.out.println("load settings frag");
            loadFragment(new settings());
        }
        if(itemTitle.equals("Profile".trim()))
        {
            System.out.println("load profile frag");
            loadFragment(new profileFragment());
        }
        if(itemTitle.equals("Favorites".trim()))
        {
            System.out.println("load favs frag");
            loadFragment(new favsFragment());
        }


        return super.onOptionsItemSelected(item);
    }

    // Helper method to handle fragment transactions
    private void loadFragment(Fragment fragment) {
        FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
        transaction.replace(R.id.fragment_container, fragment);
        transaction.addToBackStack(null);
        transaction.commit();
    }
}