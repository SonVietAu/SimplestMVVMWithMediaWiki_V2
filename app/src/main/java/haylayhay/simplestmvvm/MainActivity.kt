package haylayhay.simplestmvvm

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.TextView
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {

    val viewModel by lazy { ViewModelProvider(this).get(SimplestMVVMViewModel::class.java) }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        // Create the observer which updates the UI.
        val nameObserver = object : Observer<String> {
            override fun onChanged(t: String?) {
                mainTV.text = t
            }
        }

        // Observe the LiveData, passing in this activity as the LifecycleOwner and the observer.
        viewModel.dataToDisplay.observe(this, nameObserver)
    }

    fun searchWiki(clickedView: View) {

        val query = findViewById<TextView>(R.id.keywordET).text.toString()
        viewModel.searchWiki(query)

    }
}