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

/**
 * This is the main activity for the app that displays the home screen.
 */

public class MainActivity extends AppCompatActivity {


    /**
     * Called when the activity is first created.
     * Sets up the toolbar, and applies system bar insets to the main view.
     *
     * @param savedInstanceState A bundle containing the saved instance state of the activity, or null if there is no state.
     */

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


        // Initialize and set up the toolbar
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);


        // Set the logo for the toolbar/title of toolbar
        toolbar.setLogo(R.drawable.bbclogo);
        Objects.requireNonNull(getSupportActionBar()).setTitle(R.string.newsfeed);






        }

    /**
     * Creates the options menu for the activity and inflates the menu layout.
     *
     * @param menu The menu to populate with items.
     * @return True if the menu was created successfully, false otherwise.
     */
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_main, menu);

        return true;
    }

    /**
     * Handles selection of menu items from the options menu.
     * Based on the selected item, the corresponding fragment is loaded into the fragment container.
     *
     * @param item The selected menu item.
     * @return True if the item was handled, false otherwise.
     */
    @Override
    public boolean onOptionsItemSelected( MenuItem item) {
        // Get the title of the selected menu item, trim to be safe
        String itemTitle = item.getTitle().toString();
        // Log the selected item title for debugging purposes
        Log.d("MainActivity", "Selected item: " + itemTitle);

        // Set the action bar title to the selected menu item's title
        getSupportActionBar().setTitle(" " + itemTitle);

     //
        // Load the corresponding fragment based on the selected menu item
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

    /**
     * Helper method to load a fragment into the fragment container.
     * Replaces any existing fragment with the new fragment and adds the transaction to the back stack.
     *
     * @param fragment The fragment to be loaded into the container.
     */
    private void loadFragment(Fragment fragment) {
        // Begin a new fragment transaction
        FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
        // Replace the fragment in the fragment container with the new fragment
        transaction.replace(R.id.fragment_container, fragment);
        // Add the transaction to the back stack to allow navigation back to the previous fragment
        transaction.addToBackStack(null);
        // Commit the transaction to apply the changes
        transaction.commit();
    }
}