package com.ediksarkis.msttest;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.widget.EditText;
import android.widget.Toast;

import com.ediksarkis.msttest.adapter.ItunesAdapter;
import com.ediksarkis.msttest.network.ApiClient;
import com.ediksarkis.msttest.network.ApiService;
import com.ediksarkis.msttest.network.model.ItunesContent;
import com.ediksarkis.msttest.network.model.SearchResultModel;
import com.jakewharton.rxbinding2.widget.RxTextView;
import com.jakewharton.rxbinding2.widget.TextViewTextChangeEvent;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.TimeUnit;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;
import io.reactivex.Single;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.functions.Function;
import io.reactivex.observers.DisposableObserver;
import io.reactivex.schedulers.Schedulers;
import io.reactivex.subjects.PublishSubject;

public class MainActivity extends AppCompatActivity {

    private static final String TAG = MainActivity.class.getSimpleName();

    private CompositeDisposable disposable = new CompositeDisposable();
    private PublishSubject<String> publishSubject = PublishSubject.create();
    private ApiService apiService;
    private ItunesAdapter mAdapter;
    private List<ItunesContent> contentList = new ArrayList<>();

    @BindView(R.id.input_search) EditText inputSearch;
    @BindView(R.id.recycler_view) RecyclerView recyclerView;

    private Unbinder unbinder;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        unbinder = ButterKnife.bind(this);

        mAdapter = new ItunesAdapter(this, contentList);
///sdfsdfsdfsdfsdf
        RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(getApplicationContext());
        recyclerView.setLayoutManager(mLayoutManager);
        recyclerView.setItemAnimator(new DefaultItemAnimator());
        recyclerView.addItemDecoration(new DividerItemDecoration(this, DividerItemDecoration.VERTICAL));
        recyclerView.setAdapter(mAdapter);

        apiService = ApiClient.getClient().create(ApiService.class);

        DisposableObserver<SearchResultModel> observer = getSearchObserver();

        disposable.add(publishSubject.debounce(500, TimeUnit.MILLISECONDS)
                .distinctUntilChanged()
                .switchMapSingle((Function<String, Single<SearchResultModel>>) s -> apiService.getItunesContent(s)
                        .subscribeOn(Schedulers.io())
                        .observeOn(AndroidSchedulers.mainThread()))
                .doOnError(throwable -> Toast.makeText(getApplicationContext(), throwable.getMessage() + "", Toast.LENGTH_SHORT).show())
                .retryWhen(observable -> observable.zipWith(publishSubject, (o, o2) -> o))
                .subscribeWith(observer));


        disposable.add(RxTextView.textChangeEvents(inputSearch)
                .skipInitialValue()
                .debounce(500, TimeUnit.MILLISECONDS)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeWith(searchContactsTextWatcher()));

        disposable.add(observer);

    }

    private DisposableObserver<SearchResultModel> getSearchObserver() {
        return new DisposableObserver<SearchResultModel>() {
            @Override
            public void onNext(SearchResultModel content) {
                    contentList.clear();
                    contentList.addAll(content.getResultModels());
                    mAdapter.notifyDataSetChanged();

                    if(content.getResultCount()==0) {
                        Toast.makeText(getApplicationContext(),  "Ничего не нашлось", Toast.LENGTH_SHORT).show();
                    }
            }
            @Override public void onError(Throwable e) { Log.e(TAG, "onError: " + e.getMessage()); }
            @Override public void onComplete() { }
        };
    }

    private DisposableObserver<TextViewTextChangeEvent> searchContactsTextWatcher() {
        return new DisposableObserver<TextViewTextChangeEvent>() {
            @Override
            public void onNext(TextViewTextChangeEvent textViewTextChangeEvent) {
                publishSubject.onNext(textViewTextChangeEvent.text().toString());
            }

            @Override public void onError(Throwable e) { Log.e(TAG, "onError: " + e.getMessage()); }
            @Override public void onComplete() { }
        };
    }

    @Override
    protected void onDestroy() {
        disposable.dispose();
        unbinder.unbind();
        super.onDestroy();
    }

}
