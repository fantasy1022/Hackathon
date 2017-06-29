package com.fantasy1022.hackathon.presentation.main;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;

import com.fantasy1022.hackathon.R;
import com.fantasy1022.hackathon.presentation.signin.SignInActivity;

import butterknife.BindView;
import butterknife.ButterKnife;

public class MainActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener, MainContract.View {

    public final String TAG = getClass().getSimpleName();
    @BindView(R.id.toolbar)
    Toolbar toolbar;
    @BindView(R.id.drawer_layout)
    DrawerLayout drawer;
    @BindView(R.id.nav_view)
    NavigationView navigationView;
    TextView nameText, emailText;//Can not use butterknife to bind

    private MainContract.Presenter mainPresenter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);
        initView();

        mainPresenter = new MainPresenter(this);
        mainPresenter.attachView(this);


    }

    private void initView() {
        setSupportActionBar(toolbar);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.setDrawerListener(toggle);
        toggle.syncState();
        navigationView.setNavigationItemSelectedListener(this);
        //navigationView.setCheckedItem(R.id.nav_alert_screen);
        View header = navigationView.getHeaderView(0);
        nameText = (TextView) header.findViewById(R.id.nameText);
        emailText = (TextView) header.findViewById(R.id.emailText);
    }

    @Override
    public void onBackPressed() {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }

//    private void setFragment(Fragment fragment, boolean isAddBackStack) {
//        FragmentManager fragmentMgr = getSupportFragmentManager();
//        FragmentTransaction fragmentTrans = fragmentMgr.beginTransaction();
//        fragmentTrans.replace(R.id.contentLay, fragment);//TODO: Check tag fragmentTrans
//        if (isAddBackStack) {
//            fragmentTrans.addToBackStack(null);
//        }
//        fragmentMgr.addOnBackStackChangedListener(new FragmentManager.OnBackStackChangedListener() {
//            @Override
//            public void onBackStackChanged() {
//                Log.d(TAG, "onBackStackChanged");
//            }
//        });
//        fragmentTrans.commit();
//    }

    @Override
    protected void onStart() {
        super.onStart();
        mainPresenter.onStart();
        //EventBus.getDefault().register(this);
        // getSha1();
    }

    @Override
    protected void onResume() {
        super.onResume();
        mainPresenter.checkSignInStatus();
    }

    @Override
    public void onStop() {
        super.onStop();
        mainPresenter.onStop();
        //EventBus.getDefault().unregister(this);
    }

    @Override
    protected void onDestroy() {
        Log.d(TAG, "onDestroy");
        super.onDestroy();
        mainPresenter.removeListener();
        mainPresenter.detachView();
    }


    @Override
    public void showSignInInfo(String name, String email) {
        nameText.setText(name);
        emailText.setText(email);
    }

    @Override
    public void goToSignInPage() {
        startActivity(new Intent(MainActivity.this, SignInActivity.class));
    }

    @Override
    public void showLoading() {

    }

    @Override
    public void hideLoading() {

    }

    @Override
    public void setToolBarName(String name) {
        toolbar.setTitle(name);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main, menu);
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

        } else if (id == R.id.nav_send) {

        }

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }

}
