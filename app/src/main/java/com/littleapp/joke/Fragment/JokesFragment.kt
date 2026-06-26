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

class JokesFragment : Fragment() {

    private var _binding: FragmentJokesBinding? = null
    private val binding get() = _binding!!

    private var adapter: JokeAdapter? = null
    private val jokes = ArrayList<Joke>()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?
    ): View {
        _binding = FragmentJokesBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        adapter = JokeAdapter(jokes)

        binding.jokesList.apply {
            layoutManager = LinearLayoutManager(context)
            adapter = this@JokesFragment.adapter
        }

        arguments?.getString(KEY_JOKES_URL)?.let { url ->
            getJokes(url)
        }
    }

    private fun getJokes(url: String) {
        val queue = Volley.newRequestQueue(requireContext())
        val objectRequest = JsonObjectRequest(
            Request.Method.GET, url, null,
            { response ->
                try {
                    val jokesArray = response.getJSONArray("jokes")
                    val previousSize = jokes.size

                    for (i in 0 until jokesArray.length()) {
                        val jokeData = jokesArray.getJSONObject(i)
                        val jokeType = jokeData.getString("type")

                        val jokeObject = Joke().apply {
                            type = jokeType
                            if (jokeType == "single") {
                                joke = jokeData.getString("joke")
                            } else {
                                setup = jokeData.getString("setup")
                                delivery = jokeData.getString("delivery")
                            }
                        }
                        jokes.add(jokeObject)
                    }

                    adapter?.notifyItemRangeInserted(previousSize, jokesArray.length())
                } catch (_: JSONException) {
                }
            },
            { error -> error.printStackTrace() }
        )
        queue.add(objectRequest)
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    companion object {
        const val KEY_JOKES_URL = "extra_jokes_url"
    }
}