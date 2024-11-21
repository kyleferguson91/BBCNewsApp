package com.example.androidlab;

import static android.app.PendingIntent.getActivity;

import android.content.DialogInterface;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;

import androidx.activity.EdgeToEdge;
import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;
import android.content.Context;
import android.content.SharedPreferences;
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



        System.out.println("Initialize shared prefs within main oncreate");




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

        if (!itemTitle.equals("Help".trim())) {
           //do not change the title to help, leave at initial page
            getSupportActionBar().setTitle(" " + itemTitle);
        }

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
        if(itemTitle.equals("Help".trim()))
        {

            AlertDialog.Builder builder = new AlertDialog.Builder(this);





            System.out.println("load help alert");

            Fragment fragment = getSupportFragmentManager().findFragmentById(R.id.fragment_container);

            if (fragment != null && fragment instanceof settings) {
                // The SettingsFragment is currently loaded
                           builder.setMessage("This is the settings panel, you can change your username in here.");
                   
                System.out.println("SettingsFragment is loaded");
            }
            if ((fragment != null) && fragment instanceof NewsFeedFragment) {

            System.out.println("newsfeed is loaded");
                builder.setMessage("This is the newsfeed, scroll around for some content, click the articles for more details");
        }
            if ((fragment != null) && fragment instanceof profileFragment) {

                builder.setMessage("This is your profile, for now you can view your username");
                System.out.println("profile is loaded");
            }

            if ((fragment != null) && fragment instanceof favsFragment) {

                System.out.println("favsfrag is loaded");
                builder.setMessage("This is the favorites, view your favorites");
            }


            builder.setTitle("Help!") // Set the title
                    .setCancelable(false); // Disable closing dialog by tapping outside
                    builder.setPositiveButton("OK", new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface dialog, int id) {
                            // ok button
                        }
                    })
                    .setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface dialog, int id) {
                            // cancel button
                        }
                    });

            // Create the AlertDialog object and show it
            AlertDialog alertDialog = builder.create();
            alertDialog.show();



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