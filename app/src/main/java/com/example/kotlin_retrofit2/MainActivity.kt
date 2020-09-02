package com.example.kotlin_retrofit2

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.plugins.RxJavaPlugins
import io.reactivex.subjects.BehaviorSubject
import kotlinx.android.synthetic.main.activity_main.*

const val key = "3556a74b41fbe77f6fb9360a792a5e58"

class MainActivity : AppCompatActivity() {
    private val movieItemsSubject =
        BehaviorSubject.create<List<Data.BoxOfficeResult.DailyBoxOffice>>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        button.setOnClickListener {
            NetworkManager.api.getBoxOffice(key, "20200901")
                .map { it.boxOfficeResult.dailyBoxOfficeList }
                .subscribe(movieItemsSubject::onNext)
        }

        RxJavaPlugins.setErrorHandler {
            Log.d("동환",it.message)
        }

        movieItemsSubject
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe {
                it.forEach { daily ->
                    Log.d("동환", daily.toString())
                }
            }

    }
}
