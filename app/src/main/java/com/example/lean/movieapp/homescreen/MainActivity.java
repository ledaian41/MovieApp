package com.example.lean.movieapp.homescreen;

import android.content.Intent;
import android.content.res.Configuration;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.NavigationView;
import android.support.design.widget.Snackbar;
import android.support.v4.view.GravityCompat;
import android.support.v4.view.ViewPager;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.SearchView;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.HorizontalScrollView;
import android.widget.ListView;

import com.afollestad.materialdialogs.simplelist.MaterialSimpleListAdapter;
import com.example.lean.movieapp.R;
import com.example.lean.movieapp.common.BaseActivity;
import com.example.lean.movieapp.login.LoginActivity;
import com.example.lean.movieapp.model_server.response.MovieResponse;
import com.example.lean.movieapp.ui.ZoomOutPageTransformer;
import com.takusemba.multisnaprecyclerview.MultiSnapRecyclerView;
import com.yarolegovich.discretescrollview.DiscreteScrollView;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class MainActivity extends BaseActivity implements NavigationView.OnNavigationItemSelectedListener, View.OnClickListener, SearchView.OnQueryTextListener, MainInterface.View {
    private Toolbar mToolbar;
    private DrawerLayout mDrawerLayout;
    private NavigationView mNavigationView;
    private ActionBarDrawerToggle mDrawerToggle;
    private SearchView searchView;
    private MainPresenter mPresenter;
    private PopularAdapter mPopularAdapter;
    private RecyclerView recyclerView;
    private ViewPager viewPager;
    private ViewPagerAdapter mViewPagerAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        initPresenter();
        initView();
        setupView();
        callApi();
    }

    private void callApi() {
        mPresenter.getPopular(1);
    }

    @Override
    public void onConfigurationChanged(Configuration newConfig) {
        super.onConfigurationChanged(newConfig);
        mDrawerToggle.onConfigurationChanged(newConfig);
    }

    private void initView() {
        mToolbar = findViewById(R.id.toolbar_main);
        mNavigationView = findViewById(R.id.nav_view);
        mDrawerLayout = findViewById(R.id.drawer_layout);
        recyclerView = findViewById(R.id.recyclerView);
        viewPager = findViewById(R.id.viewPager);
        findViewById(R.id.btnLogout).setOnClickListener(this);
    }

    private void setupView() {
        setSupportActionBar(mToolbar);
        mDrawerToggle = setupDrawerToggle();
        // Tie DrawerLayout events to the ActionBarToggle
        mDrawerLayout.addDrawerListener(mDrawerToggle);
        mViewPagerAdapter = new ViewPagerAdapter(getSupportFragmentManager(), new ArrayList<>());
        viewPager.setPageTransformer(true, new ZoomOutPageTransformer());
        viewPager.setAdapter(mViewPagerAdapter);
    }

    private ActionBarDrawerToggle setupDrawerToggle() {
        // NOTE: Make sure you pass in a valid toolbar reference.  ActionBarDrawToggle() does not require it
        // and will not render the hamburger icon without it.
        return new ActionBarDrawerToggle(this, mDrawerLayout, mToolbar, R.string.drawer_open, R.string.drawer_close);

    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (mDrawerToggle.onOptionsItemSelected(item)) {
            return true;
        }
        switch (item.getItemId()) {
            case android.R.id.home:
                mDrawerLayout.openDrawer(GravityCompat.START);
                return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem item) {
        return true;
    }

    @Override
    protected void onStart() {
        super.onStart();
        if (!isLogin()) {
            startActivity(new Intent(MainActivity.this, LoginActivity.class));
        }
    }

    @Override
    protected void onPostCreate(Bundle savedInstanceState) {
        super.onPostCreate(savedInstanceState);
        // Sync the toggle state after onRestoreInstanceState has occurred.
        mDrawerToggle.syncState();
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.btnLogout:
                mFireBaseAuth.signOut();
                if (!isLogin()) {
                    startActivity(new Intent(this, LoginActivity.class));
                }
                break;
            default:
                break;
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater menuInflater = getMenuInflater();
        menuInflater.inflate(R.menu.menu_toolbar, menu);
        MenuItem searchItem = menu.findItem(R.id.action_search);
        searchView = (SearchView) searchItem.getActionView();
        searchView.setOnQueryTextListener(this);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onQueryTextSubmit(String query) {
        searchView.clearFocus();
        return true;
    }

    @Override
    public boolean onQueryTextChange(String newText) {
        return false;
    }

    @Override
    public void initPresenter() {
        mPresenter = new MainPresenter(this);
    }

    @Override
    public void getTopRatedSuccess() {

    }

    @Override
    public void getTopRatedFailed(String error) {

    }

    @Override
    public void getPopularSuccess(List<MovieResponse> movieResponses) {
        if (movieResponses != null) {
            mViewPagerAdapter.addMovies(movieResponses);
        }
    }

    @Override
    public void getPopularFailed(String error) {
        Snackbar.make(mDrawerLayout, error, Snackbar.LENGTH_LONG).show();
    }
}
