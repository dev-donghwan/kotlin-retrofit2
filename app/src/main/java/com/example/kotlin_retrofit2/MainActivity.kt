package com.example.kotlin_retrofit2

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import androidx.core.view.isVisible
import io.reactivex.Observable
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.plugins.RxJavaPlugins
import io.reactivex.subjects.BehaviorSubject
import kotlinx.android.synthetic.main.activity_main.*

const val key = "3556a74b41fbe77f6fb9360a792a5e58"

class MainActivity : AppCompatActivity() {
    private val movieItemsSubject =
        BehaviorSubject.create<List<Data.BoxOfficeResult.DailyBoxOffice>>()
    private val loadingSubject = BehaviorSubject.createDefault(false)

    private val recyclerAdapter = RecyclerAdapter()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        rec.adapter = recyclerAdapter

        RxJavaPlugins.setErrorHandler {
            Log.d("동환", it.message)
        }

        button.setOnClickListener {
            NetworkManager.api.getBoxOffice(key, "20200901")
                .doOnSubscribe { loadingSubject.onNext(true) }
                .doOnTerminate { loadingSubject.onNext(false) }
                .map { it.boxOfficeResult.dailyBoxOfficeList }
                .subscribe(movieItemsSubject::onNext)
        }

        movieItemsSubject
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe(recyclerAdapter::changeData)

        loadingSubject
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe { loading.isVisible = it }
    }
}
