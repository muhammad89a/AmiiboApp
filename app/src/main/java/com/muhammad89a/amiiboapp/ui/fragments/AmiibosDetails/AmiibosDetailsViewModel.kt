package com.muhammad89a.amiiboapp.ui.fragments.AmiibosDetails

import androidx.lifecycle.ViewModel
import com.muhammad89a.amiiboapp.data.repository.AmiiboRepository
import com.muhammad89a.amiiboapp.data.model.AmiiboDTO
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject


@HiltViewModel
class AmiibosDetailsViewModel @Inject constructor(
    private val repository: AmiiboRepository
) : ViewModel() {

    fun purchaseAmiibo(amiiboDTO: AmiiboDTO) {
        repository.puchaseAmiibo(amiiboDTO)
    }
}