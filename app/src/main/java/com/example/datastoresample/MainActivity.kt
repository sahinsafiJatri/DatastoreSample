package com.example.datastoresample

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.core.widget.doAfterTextChanged
import androidx.lifecycle.lifecycleScope
import com.example.datastoresample.databinding.ActivityMainBinding
import com.example.datastoresample.utils.PrefDatastore
import com.example.datastoresample.utils.PrefKeys
import kotlinx.coroutines.launch

class MainActivity : AppCompatActivity() {

    private lateinit var binding : ActivityMainBinding

    private val prefDatastore by lazy { PrefDatastore(this) }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        viewClickEventListener()
        observeDataChange()
    }

    private fun viewClickEventListener() {

        binding.userNameET.doAfterTextChanged {
            prefDatastore.updateString(PrefKeys.userName, it.toString())
        }

        binding.radioGroup.setOnCheckedChangeListener { group, checkedId ->
            val gender = when(checkedId) {
                R.id.maleRB -> "Male"
                R.id.femaleRB -> "Female"
                else -> "Other"
            }

            prefDatastore.updateString(PrefKeys.userGender, gender)
        }

        binding.userEmailET.doAfterTextChanged {
            prefDatastore.updateString(PrefKeys.userEmail, it.toString())
        }
    }

    private fun observeDataChange() {

        lifecycleScope.launch {
            prefDatastore.observeString(PrefKeys.userName).collect {
                binding.userNameValueTV.text = it
            }
        }

        lifecycleScope.launch {
            prefDatastore.observeString(PrefKeys.userGender).collect {
                binding.userGenderValueTV.text = it
            }
        }

        lifecycleScope.launch {
            prefDatastore.observeString(PrefKeys.userEmail).collect {
                binding.userEmailValueTV.text = it
            }
        }
    }
}