package com.littleapp.joke.Activity

import android.content.Context
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.littleapp.joke.Adapter.JokeCategoriesAdapter
import com.littleapp.joke.Fragment.JokesFragment
import com.littleapp.joke.R
import com.littleapp.joke.Unit.THEME
import com.littleapp.joke.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {

    private var binding: ActivityMainBinding? = null
    var jokeList: RecyclerView? = null
    var catAdapter: JokeCategoriesAdapter? = null
    var context: Context = this@MainActivity

    override fun onCreate(savedInstanceState: Bundle?) {
        THEME.setThemeOfApp(context)
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        val view = binding!!.root
        setContentView(view)

        binding!!.toolbar.nameSpace.setText(R.string.joke)

        val cats = ArrayList<String>()
        cats.add("Any")
        cats.add("Programming")
        cats.add("Dark")
        cats.add("Spooky")
        cats.add("Misc")
        cats.add("Pun")
        cats.add("Christmas")

        binding!!.recyclerView.layoutManager =
            LinearLayoutManager(context, LinearLayoutManager.HORIZONTAL, false)
        catAdapter = JokeCategoriesAdapter(context, cats)
        binding!!.recyclerView.adapter = catAdapter
        val manager = supportFragmentManager
        val transaction = manager.beginTransaction().replace(
            R.id.fragment, JokesFragment("https://v2.jokeapi.dev/joke/Any?amount=10")
        )
        transaction.commit()
    }
}