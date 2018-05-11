package com.example.lean.movieapp.common;

import android.support.v7.widget.SearchView;

import io.reactivex.Observable;
import io.reactivex.ObservableEmitter;
import io.reactivex.ObservableOnSubscribe;

public class RxUtils {

    public static Observable<String> bindSearchView(SearchView searchView) {
        return Observable.create(emitter -> searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                return true;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                emitter.onNext(newText);
                return true;
            }
        }));
    }
}
