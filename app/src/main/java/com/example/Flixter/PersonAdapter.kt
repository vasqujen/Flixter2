package com.example.Flixter

import android.content.Context
import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide

const val PERSON_EXTRA = "PERSON_EXTRA"
private const val TAG = "PersonAdapter"

class PersonAdapter (private val context: Context,
                     private val people: List<Person>,
) :
    RecyclerView.Adapter<PersonAdapter.ViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(context).inflate(R.layout.person_list, parent, false)
        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val person = people[position]
        holder.bind(holder, person)
    }

    override fun getItemCount() = people.size

    inner class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView),
        View.OnClickListener {

        private val nameTV = itemView.findViewById<TextView>(R.id.person_name)
        private val faceImage = itemView.findViewById<ImageView>(R.id.person_image)


        init {
            itemView.setOnClickListener(this)
        }

        fun bind(holder: ViewHolder,p: Person) {

            nameTV.text = p.name

            Glide.with(holder.itemView)
                .load(p.profileImageUrl)
                .centerInside()
                .into(faceImage)
        }

        override fun onClick(v: View?) {
            // Get selected person
            val person = people[absoluteAdapterPosition]

            val intent = Intent(context, DetailActivity::class.java)
            intent.putExtra(PERSON_EXTRA, person)
            context.startActivity(intent)
        }
    }
}