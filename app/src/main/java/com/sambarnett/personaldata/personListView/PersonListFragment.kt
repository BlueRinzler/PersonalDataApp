package com.sambarnett.personaldata.personListView

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import com.sambarnett.personaldata.adapter.PersonListAdapter
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.sambarnett.personaldata.databinding.PersonListFragmentBinding
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch
import org.koin.androidx.viewmodel.ext.android.viewModel

/**
 * This is the first fragment displaying details for all people in the database.
 */

class PersonListFragment : Fragment() {

    //Setting up binding
    private var _binding: PersonListFragmentBinding? = null
    private val binding get() = _binding!!


    //DI to create viewModel
    private val viewModel: PersonListViewModel by viewModel()


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
        binding.recyclerView.layoutManager = LinearLayoutManager(requireContext())


        lifecycleScope.launch {
            viewLifecycleOwner.repeatOnLifecycle(Lifecycle.State.STARTED) {
                viewModel.allPersons().collectLatest {
                    adapter.submitList(it)
                }
            }
        }

        //Set up addPerson button
        binding.addPersonButton.setOnClickListener {
            val action =
                PersonListFragmentDirections.actionPersonListFragmentToAddPersonFragment("Add New Person") // add string of fragment title
            this.findNavController().navigate(action)
        }
    }
}










