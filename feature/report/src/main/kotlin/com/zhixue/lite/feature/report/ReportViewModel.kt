package com.zhixue.lite.feature.report

import androidx.annotation.MainThread
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.navigation.toRoute
import com.zhixue.lite.core.domain.GetReportDetailUseCase
import com.zhixue.lite.core.domain.SyncReportDetailUseCase
import com.zhixue.lite.core.model.ReportDetail
import com.zhixue.lite.feature.report.navigation.ReportRoute
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class ReportViewModel @Inject constructor(
    savedStateHandle: SavedStateHandle,
    private val syncReportDetailUseCase: SyncReportDetailUseCase,
    private val getReportDetailUseCase: GetReportDetailUseCase
) : ViewModel() {

    var uiState: ReportUiState by mutableStateOf(ReportUiState.Loading)
        private set

    private val reportId: String = savedStateHandle.toRoute<ReportRoute>().reportId

    private var isInitialized: Boolean = false

    @MainThread
    fun initialize() {
        if (isInitialized) return
        isInitialized = true

        viewModelScope.launch {
            syncReportDetailUseCase(reportId)
            runCatching {
                getReportDetailUseCase(reportId)
            }.onSuccess { reportDetail ->
                uiState = ReportUiState.Success(reportDetail)
            }.onFailure { throwable ->
                throwable.printStackTrace()
                uiState = ReportUiState.Failure
            }
        }
    }
}

sealed class ReportUiState {
    data object Loading : ReportUiState()
    data object Failure : ReportUiState()
    data class Success(val reportDetail: ReportDetail) : ReportUiState()
}