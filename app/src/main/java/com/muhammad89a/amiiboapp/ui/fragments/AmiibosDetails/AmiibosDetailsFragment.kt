package com.muhammad89a.amiiboapp.ui.fragments.AmiibosDetails

import android.os.Bundle
import android.view.*
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.hilt.navigation.fragment.hiltNavGraphViewModels
import androidx.lifecycle.LifecycleObserver
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import androidx.paging.ExperimentalPagingApi
import com.muhammad89a.amiiboapp.R
import com.muhammad89a.amiiboapp.data.model.AmiiboDTO
import com.muhammad89a.amiiboapp.databinding.AmiibosDetailsFragmentBinding
import com.muhammad89a.amiiboapp.ui.activities.MainActivity
import com.muhammad89a.amiiboapp.ui.fragments.AmiibosGrid.AmiibosGridViewModel
import com.muhammad89a.amiiboapp.utils.setOnClickListenerDebounced
import com.muhammad89a.amiiboapp.utils.showImageByGlide
import com.muhammad89a.amiiboapp.utils.showOrNot
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.delay
import kotlinx.coroutines.runBlocking

@AndroidEntryPoint
class AmiibosDetailsFragment : Fragment(), LifecycleObserver {

    private val amiibosDetailsViewModel: AmiibosDetailsViewModel by hiltNavGraphViewModels(R.id.navgraph)
//  private val viewModel: AmiibosGridViewModel by hiltNavGraphViewModels(R.id.navgraph)
    private val args: AmiibosDetailsFragmentArgs by navArgs()
    private val amiibo: AmiiboDTO by lazy { args.selectedAmiibo!! }
    private var _binding: AmiibosDetailsFragmentBinding? = null
    private val binding get() = _binding!!

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val activity = activity as? MainActivity
        activity?.supportActionBar?.setDisplayHomeAsUpEnabled(true)
        setHasOptionsMenu(true)
        activity?.title = amiibo.name ?: ""
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        if (item.itemId == android.R.id.home) {
            findNavController().popBackStack()
        }
        return super.onOptionsItemSelected(item);
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = AmiibosDetailsFragmentBinding.inflate(inflater, container, false)
        _binding?.run {
            nameTxt.text = amiibo.name ?: ""
            amiiboSeriesTxt.text = amiibo.amiiboSeries ?: ""
            gameSeriesTxt.text = amiibo.gameSeries ?: ""
            characterTxt.text = amiibo.character ?: ""
            typeTxt.text = amiibo.type ?: ""
            image.showImageByGlide(amiibo.image ?: "")
            val purchased = activity?.resources?.getString(R.string.purchase)
            val unpurchased = activity?.resources?.getString(R.string.unPurchase)
            purchaseBtn.text = if(amiibo.purchased) unpurchased else purchased
            purchaseBtn.setOnClickListenerDebounced {
                amiibo.purchased = !amiibo.purchased
                val toastTx = if(!amiibo.purchased) unpurchased else purchased
                purchaseBtn.text = if(amiibo.purchased) unpurchased else purchased
                amiibosDetailsViewModel.purchaseAmiibo(amiibo)
                Toast.makeText(activity,"Item ${toastTx}",Toast.LENGTH_LONG).show()
            }
        }
        return binding.root
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}
