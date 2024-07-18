package com.example.tarek.tourguideapp;


import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.view.ViewPager;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;

import com.example.tarek.tourguideapp.adapters.SimpleFragmentPagerAdapter;
import com.example.tarek.tourguideapp.invitation.InvitationActivity;

import butterknife.BindView;
import butterknife.ButterKnife;


// NAV BAR Main Activity & VIEW PAGER
public class NavBarMainActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener {

    private final String CURRENT_CATEGORY = "currentCategory";
    @BindView(R.id.view_pager_items)
    ViewPager viewPager;
    @BindView((R.id.toolbar))
    Toolbar toolbar;
    @BindView(R.id.drawer_layout)
    DrawerLayout drawer;
    @BindView(R.id.nav_view)
    NavigationView navigationView;
    private String currentCategory;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_category_items);
        ButterKnife.bind(this);

        setNavBar();
        getComingIntents();
        getSupportActionBar().setTitle(currentCategory); // after get coming intent which have currentCategory
        buildRecycleView();

    }

    @Override
    public void onBackPressed() {
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.category_items, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();

        if (id == R.id.nav_share) {
            sendIntentToInvitationActivity();
        } else {
            currentCategory = item.getTitle().toString(); // to get current category
            // resend intent to this activity to show choose category fragments
            // and it doesn't depends on id clicked item because it gets current category depending on clicked item then show it.
            sendIntentToNavBarMainActivity();
        }

        drawer.closeDrawer(GravityCompat.START);
        return true;
    }


    /**
     * to set navBar toolbar and drawer
     */
    public void setNavBar() {
        setSupportActionBar(toolbar);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.addDrawerListener(toggle);
        toggle.syncState();
        navigationView.setNavigationItemSelectedListener(this);
    }

    /**
     * to build recycle view : set FragmentPagerAdapter object and the set it to viewpager
     */
    public void buildRecycleView() {
        final SimpleFragmentPagerAdapter adapter = new SimpleFragmentPagerAdapter(this, getSupportFragmentManager(), currentCategory);
        viewPager.setAdapter(adapter);

    }

    /**
     * to get intents if available
     */
    public void getComingIntents() {
        Intent comingIntent = getIntent();
        currentCategory = comingIntent.getStringExtra(CURRENT_CATEGORY);
    }

    /**
     * to send intent to invitationActivity
     */
    public void sendIntentToInvitationActivity() {
        Intent sendIntentToInvitationActivity = new Intent(NavBarMainActivity.this, InvitationActivity.class);
        startActivity(sendIntentToInvitationActivity);
    }

    /**
     * to send intent to NavBarMainActivity
     */
    public void sendIntentToNavBarMainActivity() {
        finish();
        Intent sendIntentToNavBarMainActivity = new Intent(NavBarMainActivity.this, NavBarMainActivity.class);
        sendIntentToNavBarMainActivity.putExtra(CURRENT_CATEGORY, currentCategory);
        startActivity(sendIntentToNavBarMainActivity);
    }
}
