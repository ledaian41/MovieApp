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
import android.util.DisplayMetrics;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewTreeObserver;
import android.widget.ArrayAdapter;
import android.widget.HorizontalScrollView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.afollestad.materialdialogs.simplelist.MaterialSimpleListAdapter;
import com.example.lean.movieapp.R;
import com.example.lean.movieapp.common.BaseActivity;
import com.example.lean.movieapp.common.Utils;
import com.example.lean.movieapp.login.LoginActivity;
import com.example.lean.movieapp.model_server.response.MovieResponse;
import com.example.lean.movieapp.ui.MyTransformer;
import com.example.lean.movieapp.ui.ShadowTransformer;
import com.example.lean.movieapp.ui.UiUtils;
import com.example.lean.movieapp.ui.ZoomOutPageTransformer;
import com.takusemba.multisnaprecyclerview.MultiSnapRecyclerView;
import com.yarolegovich.discretescrollview.DiscreteScrollView;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.w3c.dom.Text;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class MainActivity extends BaseActivity implements NavigationView.OnNavigationItemSelectedListener, View.OnClickListener, SearchView.OnQueryTextListener, MainInterface.View {
    @BindView(R.id.toolbar_main)
    Toolbar mToolbar;
    @BindView(R.id.drawer_layout)
    DrawerLayout mDrawerLayout;
    @BindView(R.id.nav_view)
    NavigationView mNavigationView;
    @BindView(R.id.viewPager)
    ViewPager viewPager;
    @BindView(R.id.tvMovieTitle)
    TextView tvMovieTitle;
    private ViewPagerAdapter mViewPagerAdapter;
    private ActionBarDrawerToggle mDrawerToggle;
    private SearchView searchView;
    private MainPresenter mPresenter;
    private PopularAdapter mPopularAdapter;
    private List<MovieResponse> mMovieResponseList = new ArrayList<>();

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
        ButterKnife.bind(this);
        mToolbar = findViewById(R.id.toolbar_main);
        mNavigationView = findViewById(R.id.nav_view);
        mDrawerLayout = findViewById(R.id.drawer_layout);
        viewPager = findViewById(R.id.viewPager);
        viewPager.setClipToPadding(false);
        viewPager.setPageMargin(30);//set margin for view
        findViewById(R.id.btnLogout).setOnClickListener(this);
        viewPager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

            }

            @Override
            public void onPageSelected(int position) {
                tvMovieTitle.setText(mMovieResponseList.get(position).getTitle());
            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });
    }

    private void setupView() {
        setSupportActionBar(mToolbar);
        mDrawerToggle = setupDrawerToggle();
        // Tie DrawerLayout events to the ActionBarToggle
        mDrawerLayout.addDrawerListener(mDrawerToggle);
        mViewPagerAdapter = new ViewPagerAdapter(getSupportFragmentManager());
        viewPager.setAdapter(mViewPagerAdapter);
        viewPager.setPageTransformer(false, new MyTransformer());
        viewPager.setOffscreenPageLimit(3);
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
    protected void onResume() {
        super.onResume();
        ViewTreeObserver viewTreeObserver = viewPager.getViewTreeObserver();
        viewTreeObserver.addOnGlobalLayoutListener(() -> {
            RelativeLayout.LayoutParams layoutParams = new RelativeLayout.LayoutParams(
                    RelativeLayout.LayoutParams.WRAP_CONTENT,
                    RelativeLayout.LayoutParams.WRAP_CONTENT);

            int viewPagerWidth = viewPager.getWidth();
            float viewPagerHeight = UiUtils.convertPixelsToDp(getHeightScreen(), this);

            layoutParams.width = viewPagerWidth;
            layoutParams.height = (int) viewPagerHeight;

            viewPager.setLayoutParams(layoutParams);
        });
    }

    @Override
    public void getTopRatedFailed(String error) {

    }

    @Override
    public void getPopularSuccess(List<MovieResponse> movieResponses) {
        if (movieResponses != null) {
            mMovieResponseList = movieResponses;
            tvMovieTitle.setText(mMovieResponseList.get(0).getTitle());
            mViewPagerAdapter.addMovies(movieResponses);
        }
    }

    @Override
    public void getPopularFailed(String error) {
        Snackbar.make(mDrawerLayout, error, Snackbar.LENGTH_LONG).show();
    }

    private int getHeightScreen() {
        DisplayMetrics displayMetrics = new DisplayMetrics();
        getWindowManager().getDefaultDisplay().getMetrics(displayMetrics);
        return displayMetrics.heightPixels;
    }
}
