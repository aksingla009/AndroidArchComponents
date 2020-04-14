package com.aman.demo.ui.home.profile

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import com.aman.demo.R
import com.aman.demo.databinding.ProfileFragmentBinding
import org.kodein.di.KodeinAware
import org.kodein.di.android.x.kodein
import org.kodein.di.generic.instance

class ProfileFragment : Fragment(), KodeinAware {

    override val kodein by kodein()

    private lateinit var profileViewModel: ProfileViewModel

    private val profileViewModelFactory: ProfileViewModelFactory by instance()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        //Create Binding Instance
        val bindingInstance: ProfileFragmentBinding =
            DataBindingUtil.inflate(inflater, R.layout.profile_fragment, container, false)

        profileViewModel =
            ViewModelProvider(this, profileViewModelFactory).get(ProfileViewModel::class.java)

        bindingInstance.profileViewModelVar = profileViewModel

        //As we are binding live data to our layout we need to define lifecycle owner
        //So define current fragment as life cycle owner
        bindingInstance.lifecycleOwner = this

        return bindingInstance.root
    }


}
