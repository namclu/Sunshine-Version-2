package com.example.android.sunshine.app;

import android.content.Intent;
import android.content.SharedPreferences;
import android.net.Uri;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.v7.app.ActionBarActivity;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;


public class MainActivity extends ActionBarActivity {

    private static String TAG = MainActivity.class.getSimpleName();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        if (savedInstanceState == null) {
            getSupportFragmentManager().beginTransaction()
                    .add(R.id.container, new ForecastFragment())
                    .commit();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.detail, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        // Launch Settings activity
        if (id == R.id.action_settings) {
            startActivity(new Intent(this, SettingsActivity.class));
            return true;
        } else if (id == R.id.action_map_location) {
            openPreferredLocationInMap();
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    /*
    * Helper method which will read user's preferred location and open a map to that location
    * */
    private void openPreferredLocationInMap() {
        // Get the preference for location
        SharedPreferences sharedPreferences =
                PreferenceManager.getDefaultSharedPreferences(getApplicationContext());
        String locationString = sharedPreferences.getString(
                getString(R.string.pref_location_key),
                getString(R.string.pref_location_default));

        // Create a URI to provide for .setData(URI)
        Uri locationUri = Uri.parse("geo:0,0?q=").buildUpon()
                .appendQueryParameter("q", locationString)
                .build();

        Intent intent = new Intent(Intent.ACTION_VIEW);
        intent.setData(locationUri);

        if (intent.resolveActivity(getPackageManager()) != null) {
            startActivity(intent);
        } else {
            Log.d(TAG, "Couldn't call " + locationString + ", no receiving apps installed!");
        }
    }
}
