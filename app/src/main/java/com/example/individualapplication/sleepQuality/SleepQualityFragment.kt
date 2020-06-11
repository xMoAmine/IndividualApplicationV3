package com.example.individualapplication.sleepQuality

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import androidx.navigation.fragment.findNavController
import com.example.individualapplication.Database.SleepDatabase
import com.example.individualapplication.R
import com.example.individualapplication.databinding.FragmentSleepQualityBinding

/**
 * Fragment voor een lijst met clickable icons.
 * elke icon heeft een quality rating (strings).
 * Als de user de icon klikt dan wordt dat geset als de sleepNight
 * en de database wordt dan geupdate
 */
class SleepQualityFragment : Fragment() {

    /**
     * Wordt opgeroepen wanneer de Fragment ready is om inhoud op het scherm te tonen.
     *
     * functie gebruikt dataBindingutil om R.layout.fragment_sleep_quality te inflaten
     *
     *
     * Het is ook verantwoordelijk voor het doorgeven van de [SleepQualityViewModel] aan de
     * [FragmentSleepQualityBinding] die gegenereerd wordt door databinding. This will allow DataBinding
     * Dit zorgt ervoor dat DataBinding de [LiveData] op onze ViewModel kan gebruiken/vertonen.
     */
    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {

        // reference naar binding object en inflate de fragment.
        val binding: FragmentSleepQualityBinding = DataBindingUtil.inflate(
            inflater, R.layout.fragment_sleep_quality, container, false)

        val application = requireNotNull(this.activity).application
        val arguments = SleepQualityFragmentArgs.fromBundle(arguments!!)

        // Maakt een instance van de ViewModelFactory aan.
        val dataSource = SleepDatabase.getInstance(application).sleepDatabaseDao
        val viewModelFactory = SleepQualityViewModelFactory(arguments.sleepNightKey, dataSource)

        // Get a reference to the ViewModel associated with this fragment.
        val sleepQualityViewModel =
            ViewModelProviders.of(
                this, viewModelFactory).get(SleepQualityViewModel::class.java)

        // Om de View Model met Data binding te gebruiken moet je dit expliciet aangeven.
        // binding object wordt gerefereerd naar de sleepQualityViewModel.
        binding.sleepQualityViewModel = sleepQualityViewModel

        // Voeg een observer toe aan de state variable van navigating om te kijken
        // wanneer een quality icon wordt ingetikt.
        sleepQualityViewModel.navigateToSleepTracker.observe(this, Observer {
            if (it == true) { // Observed state is true.
                this.findNavController().navigate(
                    SleepQualityFragmentDirections.actionSleepQualityFragmentToSleepTrackerFragment())
                // Reset state om ervoor te zorgen dat we
                // maar een keer kunnen navigeren(klikken in een sessie)
                sleepQualityViewModel.doneNavigating()
            }
        })

        return binding.root
    }
}
