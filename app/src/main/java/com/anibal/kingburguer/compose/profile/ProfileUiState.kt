package com.anibal.kingburguer.compose.profile

import com.anibal.kingburguer.data.ProfileResponse

data class ProfileUiState(
    val isLoading: Boolean = false,
    val profile: ProfileResponse? = null,
    val error: String? = null
)
