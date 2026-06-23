package com.littleapp.joke.Adapter

import android.content.Context
import android.graphics.Color
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.cardview.widget.CardView
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.RecyclerView
import com.littleapp.joke.Fragment.JokesFragment
import com.littleapp.joke.R
import com.littleapp.joke.Unit.DATA
import com.littleapp.joke.databinding.ItemJokeCategoryBinding

class JokeCategoriesAdapter(private val context: Context, var categories: List<String>) :
    RecyclerView.Adapter<JokeCategoriesAdapter.ViewHolder>() {

    var selectedPosition = 0

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val itemBinding = ItemJokeCategoryBinding.inflate(LayoutInflater.from(context), parent, false)
        return ViewHolder(itemBinding)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.catName.text = categories[position]

        if (selectedPosition == position) {
            holder.card.setBackgroundResource(R.drawable.button_profile2)
            holder.catName.setTextColor(Color.WHITE)
        } else {
            holder.card.setBackgroundResource(R.drawable.button_profile)
            holder.catName.setTextColor(Color.BLACK)
        }
    }

    override fun getItemCount(): Int = categories.size

    inner class ViewHolder(itemBinding: ItemJokeCategoryBinding) :
        RecyclerView.ViewHolder(itemBinding.root), View.OnClickListener {

        val catName: TextView = itemBinding.categoriesName
        val card: CardView = itemBinding.card

        init {
            itemView.setOnClickListener(this)
        }

        override fun onClick(v: View) {
            val currentPosition = bindingAdapterPosition
            if (currentPosition == RecyclerView.NO_POSITION) return

            notifyItemChanged(selectedPosition)
            selectedPosition = currentPosition
            notifyItemChanged(selectedPosition)

            val category = categories[selectedPosition]
            val url = "${DATA.JOKE_URL}$category?amount=10"

            loadFragment(JokesFragment(url), v)
        }
    }

    private fun loadFragment(fragment: Fragment, v: View) {
        val activity = v.context as? AppCompatActivity ?: return
        activity.supportFragmentManager
            .beginTransaction()
            .replace(R.id.fragment, fragment)
            .commit()
    }
}