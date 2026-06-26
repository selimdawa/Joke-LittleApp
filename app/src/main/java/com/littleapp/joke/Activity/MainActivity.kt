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

    private var _binding: ActivityMainBinding? = null
    private val binding get() = _binding!!

    private var catAdapter: JokeCategoriesAdapter? = null
    private val context: Context = this@MainActivity

    override fun onCreate(savedInstanceState: Bundle?) {
        THEME.setThemeOfApp(context)
        super.onCreate(savedInstanceState)

        _binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.toolbar.nameSpace.setText(R.string.joke)

        val cats = listOf("Any", "Programming", "Dark", "Spooky", "Misc", "Pun", "Christmas")

        with(binding.recyclerView) {
            layoutManager = LinearLayoutManager(context, LinearLayoutManager.HORIZONTAL, false)
            catAdapter = JokeCategoriesAdapter(context, cats)
            adapter = catAdapter
        }

        if (savedInstanceState == null) {
            val fragment = JokesFragment().apply {
                arguments = Bundle().apply {
                    putString(JokesFragment.KEY_JOKES_URL, "https://v2.jokeapi.dev/joke/Any?amount=10")
                }
            }
            supportFragmentManager.beginTransaction()
                .replace(R.id.fragment, fragment)
                .commit()
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }
}