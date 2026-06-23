package com.littleapp.joke.Activity

import android.content.Context
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import com.littleapp.joke.Adapter.JokeCategoriesAdapter
import com.littleapp.joke.Fragment.JokesFragment
import com.littleapp.joke.R
import com.littleapp.joke.Unit.THEME
import com.littleapp.joke.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {

    private var binding: ActivityMainBinding? = null
    private var catAdapter: JokeCategoriesAdapter? = null
    private val context: Context = this@MainActivity

    override fun onCreate(savedInstanceState: Bundle?) {
        THEME.setThemeOfApp(context)
        super.onCreate(savedInstanceState)

        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding!!.root)

        binding!!.toolbar.nameSpace.setText(R.string.joke)

        val cats = listOf(
            "Any",
            "Programming",
            "Dark",
            "Spooky",
            "Misc",
            "Pun",
            "Christmas"
        )

        binding!!.recyclerView.layoutManager =
            LinearLayoutManager(context, LinearLayoutManager.HORIZONTAL, false)
        catAdapter = JokeCategoriesAdapter(context, cats)
        binding!!.recyclerView.adapter = catAdapter

        if (savedInstanceState == null) {
            supportFragmentManager.beginTransaction()
                .replace(R.id.fragment, JokesFragment("https://v2.jokeapi.dev/joke/Any?amount=10"))
                .commit()
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        binding = null
    }
}