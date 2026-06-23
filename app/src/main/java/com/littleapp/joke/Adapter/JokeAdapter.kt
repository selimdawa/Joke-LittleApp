package com.littleapp.joke.Adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.littleapp.joke.Model.Joke
import com.littleapp.joke.databinding.ItemJokeBinding

class JokeAdapter(
    private val context: Context,
    private var jokes: List<Joke>
) : RecyclerView.Adapter<JokeAdapter.ViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val itemBinding = ItemJokeBinding.inflate(LayoutInflater.from(context), parent, false)
        return ViewHolder(itemBinding)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val jokeItem = jokes[position]

        if (jokeItem.type == "single") {
            holder.firstLine.text = jokeItem.joke
            holder.secondLine.visibility = View.GONE
        } else {
            holder.firstLine.text = jokeItem.setup
            holder.secondLine.visibility = View.VISIBLE
            holder.secondLine.text = jokeItem.delivery
        }
    }

    override fun getItemCount(): Int = jokes.size

    class ViewHolder(itemBinding: ItemJokeBinding) : RecyclerView.ViewHolder(itemBinding.root) {
        val firstLine: TextView = itemBinding.firstLine
        val secondLine: TextView = itemBinding.secondLine
    }
}