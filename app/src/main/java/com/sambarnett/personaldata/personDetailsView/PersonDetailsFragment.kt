package com.sambarnett.personaldata.personDetailsView

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import com.sambarnett.personaldata.R
import com.sambarnett.personaldata.data.Person
import com.sambarnett.personaldata.databinding.FragmentPersonDetailsBinding
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch
import org.koin.androidx.viewmodel.ext.android.viewModel


/**
 * This fragment is to show all the person details, as well as to update them.
 */
class PersonDetailsFragment : Fragment() {

    //DI to create viewModel
    private val viewModel: PersonDetailsViewModel by viewModel()

    //Using SafeArgs to pass the person_id as the parameter to update the fragment
    private val navigationArgs: PersonDetailsFragmentArgs by navArgs()

    //Creating the person value when needed
    lateinit var person: Person

    //Standard binding for the fragment to layout
    private var _binding: FragmentPersonDetailsBinding? = null
    private val binding get() = _binding!!


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        _binding = FragmentPersonDetailsBinding.inflate(inflater, container, false)
        return binding.root
    }


    /**
     * Finding the person by person_Id, then calling bind(person: Person) to find the details to the fragment
     */
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val id = navigationArgs.personId

        lifecycleScope.launch {
            viewLifecycleOwner.repeatOnLifecycle(Lifecycle.State.STARTED) {
                viewModel.retrievePerson(id).collect { selectedPerson ->
                    person = selectedPerson
                    bind(person)
                }
            }
        }

    }


    /**
     * Binding person details to the fragment, binding the deletePerson button
     */
    private fun bind(person: Person) {
        binding.apply {
            binding.personFirstName.text = person.personFirstName
            binding.personSurName.text = person.personSurName
            binding.personAge.text = person.personAge.toString()
            binding.personWeight.text = person.personWeight.toString()
            binding.personHeight.text = person.personHeight.toString()
            binding.personEyeColor.text = person.personEyeColor
            deletePerson.setOnClickListener { deletePerson() }
            editPerson.setOnClickListener { editPerson() }
        }
    }

    /**
     * Deletes the current item and navigates to the list fragment.
     */
    private fun deletePerson() {
        viewModel.deletePerson(person)
        findNavController().navigateUp()
    }

    /**
     * Edits the current item and navigates to the AddPersonFragment.
     */
    private fun editPerson() {
        val action = PersonDetailsFragmentDirections.actionPersonDetailsFragmentToAddPersonFragment(
            getString(R.string.edit_fragment_title),
            person.id
        )
        this.findNavController().navigate(action)
    }

    /**
     * Called when fragment is destroyed.
     */
    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }


}