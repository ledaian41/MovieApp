package com.example.lean.movieapp.homescreen;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.content.res.Configuration;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.NavigationView;
import android.support.design.widget.Snackbar;
import android.support.v4.view.GravityCompat;
import android.support.v4.view.MenuItemCompat;
import android.support.v4.view.ViewPager;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.SearchView;
import android.support.v7.widget.Toolbar;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.ActionMode;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.RelativeLayout;
import android.widget.ScrollView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.lean.movieapp.R;
import com.example.lean.movieapp.api.APIManager;
import com.example.lean.movieapp.common.BaseActivity;
import com.example.lean.movieapp.common.RxUtils;
import com.example.lean.movieapp.login.LoginActivity;
import com.example.lean.movieapp.model_server.request.SearchRequest;
import com.example.lean.movieapp.model_server.response.DataResponse;
import com.example.lean.movieapp.model_server.response.MovieResponse;
import com.example.lean.movieapp.ui.MyViewPager;
import com.google.android.exoplayer2.DefaultLoadControl;
import com.google.android.exoplayer2.DefaultRenderersFactory;
import com.google.android.exoplayer2.ExoPlayerFactory;
import com.google.android.exoplayer2.SimpleExoPlayer;
import com.google.android.exoplayer2.source.ExtractorMediaSource;
import com.google.android.exoplayer2.source.MediaSource;
import com.google.android.exoplayer2.source.dash.DashChunkSource;
import com.google.android.exoplayer2.source.dash.DashMediaSource;
import com.google.android.exoplayer2.source.dash.DefaultDashChunkSource;
import com.google.android.exoplayer2.trackselection.AdaptiveTrackSelection;
import com.google.android.exoplayer2.trackselection.DefaultTrackSelector;
import com.google.android.exoplayer2.trackselection.TrackSelection;
import com.google.android.exoplayer2.ui.PlayerView;
import com.google.android.exoplayer2.upstream.DataSource;
import com.google.android.exoplayer2.upstream.DefaultBandwidthMeter;
import com.google.android.exoplayer2.upstream.DefaultDataSourceFactory;
import com.google.android.exoplayer2.upstream.DefaultHttpDataSourceFactory;
import com.google.android.exoplayer2.util.Util;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.TimeUnit;

import butterknife.BindView;
import butterknife.ButterKnife;
import io.reactivex.Observable;
import io.reactivex.ObservableSource;
import io.reactivex.Observer;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.functions.BiConsumer;
import io.reactivex.functions.Consumer;
import io.reactivex.functions.Function;
import io.reactivex.functions.Predicate;
import io.reactivex.schedulers.Schedulers;
import me.relex.circleindicator.CircleIndicator;

import static android.view.View.GONE;
import static android.view.View.VISIBLE;

public class MainActivity extends BaseActivity implements NavigationView.OnNavigationItemSelectedListener, View.OnClickListener, SearchView.OnQueryTextListener, MainInterface.View {
    @BindView(R.id.toolbar_main)
    Toolbar mToolbar;
    @BindView(R.id.drawer_layout)
    DrawerLayout mDrawerLayout;
    @BindView(R.id.nav_view)
    NavigationView mNavigationView;
    @BindView(R.id.viewPager)
    MyViewPager viewPager;
    @BindView(R.id.tvMovieTitle)
    TextView tvMovieTitle;
    @BindView(R.id.circleIndicator)
    CircleIndicator circleIndicator;
    @BindView(R.id.rvPopular)
    RecyclerView mRvPopular;
    @BindView(R.id.scrollView)
    ScrollView scrollView;
    @BindView(R.id.layoutBody)
    RelativeLayout layoutBody;
    @BindView(R.id.rvSearch)
    RecyclerView rvSearch;
    @BindView(R.id.playerView)
    PlayerView playerView;
    @BindView(R.id.layoutDetailBody)
    RelativeLayout layoutDetailBody;
    @BindView(R.id.layoutDetail)
    RelativeLayout layoutDetail;

    private ViewPagerAdapter mViewPagerAdapter;
    private ActionBarDrawerToggle mDrawerToggle;
    private SearchView searchView;
    private MainPresenter mPresenter;
    private PopularAdapter mPopularAdapter;
    private SearchAdapter mSearchAdapter;
    private List<MovieResponse> mTopMovieList = new ArrayList<>();
    private List<MovieResponse> mPopularMovieList = new ArrayList<>();
    private List<MovieResponse> mSearchResultList = new ArrayList<>();
    private SearchRequest mSearchRequest = new SearchRequest();
    private boolean playWhenReady;
    private long playbackPosition;
    private int currentWindow;
    private static final DefaultBandwidthMeter BANDWIDTH_METER =
            new DefaultBandwidthMeter();
    private SimpleExoPlayer player;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        initPresenter();
        initView();
        setupView();
        callApi();
    }

    private MediaSource buildMediaSource(Uri uri) {
        DataSource.Factory dataSourceFactory =
                new DefaultHttpDataSourceFactory("ua", BANDWIDTH_METER);

        return new ExtractorMediaSource.Factory(dataSourceFactory).createMediaSource(uri);
    }

    private void initializePlayer() {
        if (player == null) {
            TrackSelection.Factory adaptiveTrackSelectionFactory =
                    new AdaptiveTrackSelection.Factory(BANDWIDTH_METER);
            player = ExoPlayerFactory.newSimpleInstance(this,
                    new DefaultTrackSelector(adaptiveTrackSelectionFactory)
            );
            playerView.setPlayer(player);
        }

        player.setPlayWhenReady(playWhenReady);
        player.seekTo(currentWindow, playbackPosition);
        //TODO pass URL from Internet
//        Uri uri = Uri.parse();

//        MediaSource mediaSource = buildMediaSource(uri);
//        player.prepare(mediaSource, true, false);
    }

    private void releasePlayer() {
        if (player != null) {
            playbackPosition = player.getCurrentPosition();
            currentWindow = player.getCurrentWindowIndex();
            playWhenReady = player.getPlayWhenReady();
            player.release();
            player = null;
        }
    }

    private void hideSystemUi() {
        playerView.setSystemUiVisibility(View.SYSTEM_UI_FLAG_LOW_PROFILE
                | View.SYSTEM_UI_FLAG_FULLSCREEN
                | View.SYSTEM_UI_FLAG_LAYOUT_STABLE
                | View.SYSTEM_UI_FLAG_IMMERSIVE_STICKY
                | View.SYSTEM_UI_FLAG_LAYOUT_HIDE_NAVIGATION
                | View.SYSTEM_UI_FLAG_HIDE_NAVIGATION);
    }

    private void callApi() {
        mPresenter.getPopular(1);
        mPresenter.getTopRated(1);
    }

    @Override
    public void onConfigurationChanged(Configuration newConfig) {
        super.onConfigurationChanged(newConfig);
        mDrawerToggle.onConfigurationChanged(newConfig);
    }

    private void initView() {
        ButterKnife.bind(this);
        findViewById(R.id.btnLogout).setOnClickListener(this);
        viewPager.addOnPageChangeListener(new OnPageChangeAdapter() {
            @Override
            public void onPageSelected(int position) {
                tvMovieTitle.setText(mTopMovieList.get(position).getTitle());
            }
        });

        mSearchRequest.setPage(1);
    }

    private void setupView() {
        setSupportActionBar(mToolbar);
        mDrawerToggle = setupDrawerToggle();
        // Tie DrawerLayout events to the ActionBarToggle
        mDrawerLayout.addDrawerListener(mDrawerToggle);
        mViewPagerAdapter = new ViewPagerAdapter(getSupportFragmentManager());
        viewPager.setAdapter(mViewPagerAdapter);
        viewPager.setAnimationEnabled(true);
        viewPager.setFadeEnabled(true);
        viewPager.setFadeFactor(0.6f);

        mPopularAdapter = new PopularAdapter();
        mRvPopular.setLayoutManager(new GridLayoutManager(this, 2));
        mRvPopular.setAdapter(mPopularAdapter);

        mSearchAdapter = new SearchAdapter();
        rvSearch.setLayoutManager(new LinearLayoutManager(this));
        rvSearch.setAdapter(mSearchAdapter);

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
//                mDrawerLayout.openDrawer(GravityCompat.START);
                Toast.makeText(this, "Close Search", Toast.LENGTH_SHORT).show();
                clearFocusView();
                return true;
            case R.id.action_search:
                performSearchUI();
                break;
            default:
                break;
        }

        return super.onOptionsItemSelected(item);
    }

    private void performSearchUI() {
        layoutBody.setVisibility(GONE);
        rvSearch.setVisibility(VISIBLE);
    }

    private void closeSearchUI() {
        layoutBody.setVisibility(VISIBLE);
        rvSearch.setVisibility(View.GONE);
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
        if (Util.SDK_INT > 23) {
            initializePlayer();
        }
        EventBus.getDefault().register(this);
    }

    @Override
    protected void onResume() {
        super.onResume();
        hideSystemUi();
        if (Util.SDK_INT <= 23 || player == null) {
            initializePlayer();
        }
    }

    @Override
    protected void onPause() {
        super.onPause();
        if (Util.SDK_INT <= 23) {
            releasePlayer();
        }
    }

    @Override
    protected void onStop() {
        super.onStop();
        if (Util.SDK_INT > 23) {
            releasePlayer();
        }
        EventBus.getDefault().unregister(this);
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

    @SuppressLint("CheckResult")
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater menuInflater = getMenuInflater();
        menuInflater.inflate(R.menu.menu_toolbar, menu);
        MenuItem searchItem = menu.findItem(R.id.action_search);
        setupBackButtonSearchView(searchItem);
        searchView = (SearchView) searchItem.getActionView();
//        searchView.setOnQueryTextListener(this);
        RxUtils.bindSearchView(searchView)
                .debounce(300, TimeUnit.MILLISECONDS)
                .filter(s -> !s.isEmpty())
                .flatMap(string -> {
                    mSearchRequest.setQuery(string);
                    return APIManager.searchMovie(mSearchRequest.toQueryMap());
                })
                .map(DataResponse::getResults)
                .filter(movieResponses -> movieResponses != null && !movieResponses.isEmpty())
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(mSearchAdapter::setMovies);
        return super.onCreateOptionsMenu(menu);
    }

    private void setupBackButtonSearchView(MenuItem searchItem) {
        searchItem.setOnActionExpandListener(new MenuItem.OnActionExpandListener() {
            @Override
            public boolean onMenuItemActionExpand(MenuItem item) {
                return true;
            }

            @Override
            public boolean onMenuItemActionCollapse(MenuItem item) {
                clearFocusView();
                closeSearchUI();
                return true;
            }
        });
    }

    @Override
    public boolean onQueryTextSubmit(String query) {
        searchView.clearFocus();
        return true;
    }

    @Override
    public boolean onQueryTextChange(String newText) {
        if (!newText.trim().isEmpty()) {
            SearchRequest searchRequest = new SearchRequest();
            searchRequest.setPage(1);
            searchRequest.setQuery(newText.trim());
            mPresenter.getSearchResult(searchRequest);
        }
        return true;
    }

    @Override
    public void initPresenter() {
        mPresenter = new MainPresenter(this);
    }

    @Override
    public void getTopRatedSuccess(List<MovieResponse> movieResponses) {
        if (movieResponses != null) {
            mTopMovieList = movieResponses;
            tvMovieTitle.setText(mTopMovieList.get(0).getTitle());
            mViewPagerAdapter.addMovies(movieResponses);
            circleIndicator.setViewPager(viewPager);
        }
    }

    @Override
    public void getTopRatedFailed(String error) {
        Snackbar.make(mDrawerLayout, error, Snackbar.LENGTH_LONG).show();
    }

    @Override
    public void getPopularSuccess(List<MovieResponse> movieResponses) {
        if (movieResponses != null) {
            mPopularMovieList = movieResponses;
            mPopularAdapter.setMovies(mPopularMovieList);
        }
    }

    @Override
    public void getPopularFailed(String error) {
        Snackbar.make(mDrawerLayout, error, Snackbar.LENGTH_LONG).show();
    }

    @Override
    public void getSearchResultSuccess(List<MovieResponse> movieResponses) {
        if (movieResponses != null) {
            mSearchAdapter.setMovies(movieResponses);
        }
    }

    @Override
    public void getSearchResultFailed(String error) {
        Snackbar.make(mDrawerLayout, error, Snackbar.LENGTH_LONG).show();
    }

    private void clearFocusView() {
//        scrollView.clearFocus();
//        layoutBody.setFocusable(false);
//        mRvPopular.setFocusable(false);
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void popularEvent(PopularAdapter.PopularEvent popularEvent) {
        MovieResponse popularMovie = mPopularMovieList.get(popularEvent.getPosition());
        Toast.makeText(this, popularMovie.getTitle(), Toast.LENGTH_SHORT).show();
    }

    private void showDetailUI() {

    }

    private void closeDetailUI() {

    }
}
