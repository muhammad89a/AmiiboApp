package com.muhammad89a.amiiboapp.ui.fragments.AmiibosGrid

import android.content.SharedPreferences
import android.os.Bundle
import android.util.Log
import android.view.*
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.core.view.isVisible
import androidx.hilt.navigation.fragment.hiltNavGraphViewModels
import androidx.lifecycle.LifecycleObserver
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import androidx.paging.ExperimentalPagingApi
import androidx.paging.LoadState
import com.muhammad89a.amiiboapp.R
import com.muhammad89a.amiiboapp.databinding.AmiibosGridFragmentBinding
import com.muhammad89a.amiiboapp.ui.fragments.AmiibosGrid.adapter.AmiiboListAdapter
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.Job
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch
import androidx.recyclerview.widget.GridLayoutManager
import com.muhammad89a.amiiboapp.ui.activities.MainActivity

@ExperimentalPagingApi
@AndroidEntryPoint
class AmiibosGridFragment : Fragment(), LifecycleObserver {

    private val viewModel: AmiibosGridViewModel by hiltNavGraphViewModels(R.id.navgraph)
    private var job: Job? = null
    private lateinit var adapter: AmiiboListAdapter
    private lateinit var sharedPreferences: SharedPreferences
    private var _binding: AmiibosGridFragmentBinding? = null
    private val binding
        get() = _binding!!

    override
    fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = AmiibosGridFragmentBinding.inflate(inflater, container, false)
        activity?.title = resources.getString(R.string.app_name)
        val activity = activity as? MainActivity
        activity?.supportActionBar?.setDisplayHomeAsUpEnabled(false)
        setHasOptionsMenu(false)
        initAdapter()
        getCountriesAndNotifyAdapter()

        return binding.root
    }

    private fun initAdapter() {
        adapter = AmiiboListAdapter()
        binding.amiibosList.adapter = adapter
        var gridNum = resources.getInteger(R.integer.gridNum)
        binding.amiibosList.layoutManager = GridLayoutManager(activity,gridNum)

        adapter.addLoadStateListener { loadState ->
            binding.amiibosList.isVisible = loadState.refresh is LoadState.NotLoading
            binding.progress.isVisible = loadState.refresh is LoadState.Loading
        }
    }

    private fun getCountriesAndNotifyAdapter() {
        job?.cancel()
        job = lifecycleScope.launch {
            viewModel.loadAmiibos().collectLatest {
                Log.d("AmiibosGridFragment","it=${it}")
                adapter.submitData(it)
            }
        }
    }


    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

}