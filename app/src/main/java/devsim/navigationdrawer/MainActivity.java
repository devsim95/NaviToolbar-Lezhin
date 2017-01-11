package devsim.navigationdrawer;

import android.content.res.Configuration;
import android.graphics.drawable.Drawable;
import android.support.annotation.Nullable;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBar;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.SearchView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Gravity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity implements View.OnClickListener{
    DrawerLayout drawer;
    ActionBarDrawerToggle drawerToggle;
    ActionBar actionBar;
    SearchView searchView;
    private boolean searchMode = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        drawer = (DrawerLayout) findViewById(R.id.drawerLayout);
        drawerToggle = new ActionBarDrawerToggle(this, drawer, R.string.drawer_open, R.string.drawer_close);
        drawer.addDrawerListener(drawerToggle);

        actionBar = getSupportActionBar();
        actionBar.setTitle("레진코믹스");
        actionBar.setDisplayHomeAsUpEnabled(true);

        Button home = (Button)findViewById(R.id.nav_home);
        home.setOnClickListener(this);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        if(searchMode == true){
            onBackPressed();
            return true;
        }

        if (drawerToggle.onOptionsItemSelected(item)) {

            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @Override
    protected void onPostCreate(@Nullable Bundle savedInstanceState) {
        super.onPostCreate(savedInstanceState);
        drawerToggle.syncState();
        Log.i("c","1");
        actionBar.setHomeAsUpIndicator(R.drawable.ic_menu);
    }

    @Override
    public void onConfigurationChanged(Configuration newConfig) {
        super.onConfigurationChanged(newConfig);
        drawerToggle.onConfigurationChanged(newConfig);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.main_menu, menu);

        MenuItem searchItem = menu.findItem(R.id.action_search);
        searchView = (SearchView) searchItem.getActionView();
        searchView.setQueryHint(getString(R.string.search_view_hint));

        final ImageView mCloseButton = (ImageView) searchView.findViewById(android.support.v7.appcompat.R.id.search_close_btn);
        final Drawable icon = mCloseButton.getDrawable();

        searchView.setOnSearchClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String query = searchView.getQuery().toString();
                mCloseButton.setImageDrawable(icon);
                actionBar.setHomeAsUpIndicator(R.drawable.ic_arrow_back);
                searchMode = true;
                mCloseButton.setVisibility(query.isEmpty() ? View.GONE : View.VISIBLE);
            }
        });

        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                Toast.makeText(getApplicationContext(),query,Toast.LENGTH_SHORT).show();
                return false;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                mCloseButton.setImageDrawable(icon);
                mCloseButton.setVisibility(newText.isEmpty() ? View.GONE : View.VISIBLE);
                return false;
            }
        });

        searchView.setOnQueryTextFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View view, boolean b) {
                if(searchView.getQuery().toString().isEmpty())
                    mCloseButton.setImageDrawable(null);
            }
        });

        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public void onBackPressed() {
        if (!searchView.isIconified()) {
            Log.i("dd", "dd");
            searchView.setQuery("", false);
            searchView.setIconified(true);
            searchMode = false;
            actionBar.setHomeAsUpIndicator(R.drawable.ic_menu);
        } else {
            Log.i("dd", "d2");
            super.onBackPressed();
        }
    }

    @Override
    public void onClick(View view) {
        switch(view.getId()){
            case R.id.nav_home:
                Log.i("aa","close");
                drawer.closeDrawer(Gravity.LEFT);
                break;
        }
    }
}
