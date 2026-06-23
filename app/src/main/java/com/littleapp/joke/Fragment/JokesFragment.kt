package com.littleapp.joke.Fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import com.android.volley.Request
import com.android.volley.toolbox.JsonObjectRequest
import com.android.volley.toolbox.Volley
import com.littleapp.joke.Adapter.JokeAdapter
import com.littleapp.joke.Model.Joke
import com.littleapp.joke.databinding.FragmentJokesBinding
import org.json.JSONException
import org.json.JSONObject

class JokesFragment(private val jokesUrl: String) : Fragment() {

    private var binding: FragmentJokesBinding? = null
    private var adapter: JokeAdapter? = null
    private val jokes = ArrayList<Joke>()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?
    ): View {
        binding = FragmentJokesBinding.inflate(inflater, container, false)
        return binding!!.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        adapter = JokeAdapter(requireContext(), jokes)
        binding!!.jokesList.layoutManager = LinearLayoutManager(requireContext())
        binding!!.jokesList.adapter = adapter

        getJokes(jokesUrl)
    }

    private fun getJokes(url: String) {
        val queue = Volley.newRequestQueue(requireContext())
        val objectRequest = JsonObjectRequest(
            Request.Method.GET, url, null,
            { response: JSONObject ->
                try {
                    val jokesArray = response.getJSONArray("jokes")
                    val previousSize = jokes.size

                    for (i in 0 until jokesArray.length()) {
                        val jokeData = jokesArray.getJSONObject(i)
                        val j = Joke()
                        j.type = jokeData.getString("type")
                        if (j.type == "single") {
                            j.joke = jokeData.getString("joke")
                        } else {
                            j.setup = jokeData.getString("setup")
                            j.delivery = jokeData.getString("delivery")
                        }
                        jokes.add(j)
                    }

                    adapter?.notifyItemRangeInserted(previousSize, jokesArray.length())
                } catch (e: JSONException) {
                    e.printStackTrace()
                }
            },
            { error -> error.printStackTrace() }
        )
        queue.add(objectRequest)
    }

    override fun onDestroyView() {
        super.onDestroyView()
        binding = null
    }
}