package com.sambarnett.personaldata

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ToggleButton
import androidx.fragment.app.activityViewModels
import com.sambarnett.personaldata.adapter.PersonListAdapter
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.sambarnett.personaldata.databinding.PersonListFragmentBinding
import com.sambarnett.personaldata.viewmodel.PersonViewModel
import com.sambarnett.personaldata.viewmodel.PersonViewModelFactory


/**
 * This is the first fragment displaying details for all people in the database.
 */

class PersonListFragment : Fragment() {

    /**
     * Setting up binding and the shared ViewModel, mostly boiler-plate to be used in each fragment
     */
    private var _binding: PersonListFragmentBinding? = null
    private val binding get() = _binding!!
    private val viewModel: PersonViewModel by activityViewModels {
        PersonViewModelFactory(
            (activity?.application as PersonApplication).database.personDao()
        )
    }
    private var personNameASC: Boolean = true

    //Need to turn on viewBinding in dependencies.
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        _binding = PersonListFragmentBinding.inflate(inflater, container, false)
        return binding.root
    }

    /**
     * Setting up adapter and two navigation actions to navigate to the Personal edit info
    and to add and person screen.
     */
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        //Enable safeArgs to for Gradle to build PersonListFragmentDirections
        val adapter = PersonListAdapter {
            val action =
                PersonListFragmentDirections.actionPersonListFragmentToPersonDetailsFragment(it.id)
            this.findNavController().navigate(action)
        }
        binding.recyclerView.adapter = adapter

        //Initial view that is created.
        viewModel.allPersonsASC.observe(this.viewLifecycleOwner) { persons ->
            persons.let { adapter.submitList(it) }
        }
        binding.recyclerView.layoutManager = LinearLayoutManager(this.context)


        /**
         * Need more onClickListeners or onCheckedListeners?
         */
        //Need to put this into callable function, doesn't preserve if the allPersonsDESC is used
        binding.firstName.setOnCheckedChangeListener { _, isChecked ->
            if (isChecked) {
                viewModel.allPersonsASC.observe(this.viewLifecycleOwner) { persons ->
                    persons.let { adapter.submitList(it) }
                }
            } else {
                viewModel.allPersonsDESC.observe(this.viewLifecycleOwner)
                { persons -> persons.let { adapter.submitList(it) } }
            }
        }

        binding.addPersonButton.setOnClickListener {
            val action =
                PersonListFragmentDirections.actionPersonListFragmentToAddPersonFragment("HI") // add string of fragment title
            this.findNavController().navigate(action)
        }
    }

    /**
     * Working - ish  code to set the firstname textview as a button to control if the list is DESC or ASC
     * As of right now this function is called in onViewCreated, but when navigating back after the else portion is called, the view doesn't populate
     */
    private fun toggleFirstName() {
        val adapter = PersonListAdapter {
            val action =
                PersonListFragmentDirections.actionPersonListFragmentToPersonDetailsFragment(it.id)
            this.findNavController().navigate(action)
        }
        binding.recyclerView.adapter = adapter
        binding.firstName.setOnCheckedChangeListener { _, isChecked ->
            if (isChecked) {
                viewModel.allPersonsASC.observe(this.viewLifecycleOwner) { persons ->
                    persons.let { adapter.submitList(it) }
                }
            } else {
                viewModel.allPersonsDESC.observe(this.viewLifecycleOwner)
                { persons -> persons.let { adapter.submitList(it) } }
            }
        }
    }

}









