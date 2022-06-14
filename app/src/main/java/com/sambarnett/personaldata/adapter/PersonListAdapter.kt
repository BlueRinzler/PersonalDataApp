package com.sambarnett.personaldata.adapter


import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.sambarnett.personaldata.data.Person
import com.sambarnett.personaldata.databinding.PersonListPersonBinding


/**
 * Adapter for the [RecyclerView] in person_list_fragment. Displays Person data object.
 */
class PersonListAdapter(private val onPersonClicked: (Person) -> Unit) :
    ListAdapter<Person, PersonListAdapter.PersonViewHolder>(DiffCallback) {


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int):
            PersonViewHolder {
        return PersonViewHolder(
            PersonListPersonBinding.inflate(LayoutInflater.from(parent.context))
        )
    }

    override fun onBindViewHolder(holder: PersonViewHolder, postion: Int) {
        val current = getItem(postion)
        holder.itemView.setOnClickListener {
            onPersonClicked(current)
        }
        holder.bind(current)
    }


    //Creating a holder for each data object. First name, sur name, age and weight.
    class PersonViewHolder(private var binding: PersonListPersonBinding) :
        RecyclerView.ViewHolder(binding.root) {

        //binding Person data to PersonListPerson UI values
        fun bind(person: Person) {
            binding.apply {
                personFirstName.text = person.personFirstName
                personSurName.text = person.personSurName
                personAge.text = person.personAge.toString()
                personWeight.text = person.personWeight.toString()

            }
        }

    }

    //creating DiffCallback object to be used in PersonListAdapter.
    companion object {

        private val DiffCallback = object : DiffUtil.ItemCallback<Person>() {
            override fun areContentsTheSame(oldItem: Person, newItem: Person): Boolean {
                return oldItem.personFirstName == newItem.personFirstName
            }

            override fun areItemsTheSame(oldItem: Person, newItem: Person): Boolean {
                return oldItem == newItem
            }
        }
    }
}

