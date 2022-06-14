package com.sambarnett.personaldata.personAddView

import android.content.Context
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.inputmethod.InputMethodManager
import android.widget.TextView
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import com.sambarnett.personaldata.data.Person
import com.sambarnett.personaldata.databinding.FragmentAddPersonBinding
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch
import org.koin.androidx.viewmodel.ext.android.viewModel


/**
Fragment to add or update personal information
 */

private val TAG: String = AddPersonFragment::class.java.simpleName

class AddPersonFragment : Fragment() {

    //Setting up navigation
    private val navigationArgs: AddPersonFragmentArgs by navArgs()

    //DI to create viewModel
    private val viewModel: PersonAddViewModel by viewModel()

    //person object created to bind to view
    lateinit var person: Person

    // Binding object instance corresponding to the fragment_add_person.xml layout
    private var _binding: FragmentAddPersonBinding? = null
    private val binding get() = _binding!!


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        _binding = FragmentAddPersonBinding.inflate(inflater, container, false)
        return binding.root
    }

    /**
     * Set Arguments in in nav_graph. person_id to -1 as default, checks if null, if not null create new person.
     */
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val id = navigationArgs.personId
        // if there already is an instance of this id, populate the view with that information, this is for the updatePerson portion
        if (id > 0) {
            lifecycleScope.launch {
                viewLifecycleOwner.repeatOnLifecycle(Lifecycle.State.STARTED) {
                    viewModel.retrievePerson(id).collect { selectedPerson ->
                        person = selectedPerson
                        currentBind(person)
                    }
                }
            }
            // if not, bind the save button
        } else {
            binding.saveAction.setOnClickListener {
                addNewPerson()
            }
        }
    }

    /**
     * Only called if the edit button is clicked, therefore populating the TextView.
     */
    private fun currentBind(person: Person) {
        binding.apply {
            firstName.setText(person.personFirstName, TextView.BufferType.SPANNABLE)
            surName.setText(person.personSurName, TextView.BufferType.SPANNABLE)
            ageValue.setText(person.personAge.toString(), TextView.BufferType.SPANNABLE)
            weightValue.setText(person.personWeight.toString(), TextView.BufferType.SPANNABLE)
            heightValue.setText(person.personHeight.toString(), TextView.BufferType.SPANNABLE)
            eyeColor.setText(person.personEyeColor, TextView.BufferType.SPANNABLE)
            saveAction.setOnClickListener { updatePerson() }

        }
    }

    /**
     * Function used to add persons to the database, calls isPersonValid, then the viewModel to add a newPerson
     */
    private fun addNewPerson() {
        if (personValid()) {
            viewModel.addNewPerson(
                binding.firstName.text.toString(),
                binding.surName.text.toString(),
                binding.ageValue.text.toString(),
                binding.heightValue.text.toString(),
                binding.weightValue.text.toString(),
                binding.eyeColor.text.toString()
            )
        }
        //navigating back
        val action = AddPersonFragmentDirections.actionAddPersonFragmentToPersonListFragment()
        findNavController().navigate(action)
    }

    /**
     * Function used to update a person in he database, calls isPersonValid, then the viewModel to update a Person
     */
    private fun updatePerson() {
        Log.d(TAG, "UPDATE PERSON CALLED")
        if (personValid()) {
            Log.d(TAG, "updatePerson() returned true")
            viewModel.updatePerson(
                this.navigationArgs.personId,
                this.binding.firstName.text.toString(),
                this.binding.surName.text.toString(),
                this.binding.ageValue.text.toString(),
                this.binding.heightValue.text.toString(),
                this.binding.weightValue.text.toString(),
                this.binding.eyeColor.text.toString()
            )
            //navigating back
            val action = AddPersonFragmentDirections.actionAddPersonFragmentToPersonListFragment()
            findNavController().navigate(action)
        }
    }

    /**
     * Function uses the ViewModel to check if text entered by user is blank or not
     */

    private fun personValid(): Boolean {
        Log.d(TAG, "isPersonValid add called")
        return viewModel.isPersonValid(
            binding.firstName.text.toString(),
            binding.surName.text.toString(),
            binding.ageValue.text.toString(),
            binding.heightValue.text.toString(),
            binding.weightValue.text.toString(),
            binding.eyeColor.text.toString()
        )
    }

    /**
     * Called before fragment is destroyed.
     */
    override fun onDestroyView() {
        super.onDestroyView()
        // Hide keyboard.
        val inputMethodManager = requireActivity().getSystemService(Context.INPUT_METHOD_SERVICE) as
                InputMethodManager
        inputMethodManager.hideSoftInputFromWindow(requireActivity().currentFocus?.windowToken, 0)
        _binding = null
    }


}
