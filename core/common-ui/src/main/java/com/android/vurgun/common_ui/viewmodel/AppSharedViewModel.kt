package com.android.vurgun.common_ui.viewmodel

import androidx.lifecycle.ViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import javax.inject.Inject
import kotlin.random.Random

data class SelectedBet(
    val eventId: String,
    val betType: String,
    val odds: Double,
    val homeTeam: String,
    val awayTeam: String
)

data class BettingSlipState(
    val selectedBets: Map<String, SelectedBet> = emptyMap()
) {
    val totalMatches: Int get() = selectedBets.size
    val totalOdds: Double get() = if (selectedBets.isEmpty()) 0.0 else selectedBets.values.fold(1.0) { acc, bet -> acc * bet.odds }
}


class AppSharedViewModel @Inject constructor() : ViewModel() {

    private val _bettingSlipState = MutableStateFlow(BettingSlipState())
    val bettingSlipState: StateFlow<BettingSlipState> = _bettingSlipState.asStateFlow()
    val balance = Random.nextDouble(500.0, 1000.0)

    fun toggleBet(bet: SelectedBet) {
        val currentState = _bettingSlipState.value
        val updatedBets = currentState.selectedBets.toMutableMap()

        if (updatedBets.containsKey(bet.eventId)) {
            val existingBet = updatedBets[bet.eventId]
            if (existingBet?.betType == bet.betType) {
                updatedBets.remove(bet.eventId)
            } else {
                updatedBets[bet.eventId] = bet
            }
        } else {
            updatedBets[bet.eventId] = bet
        }

        _bettingSlipState.value = currentState.copy(selectedBets = updatedBets)
    }

    fun removeBet(eventId: String) {
        val currentState = _bettingSlipState.value
        val updatedBets = currentState.selectedBets.toMutableMap()
        updatedBets.remove(eventId)
        _bettingSlipState.value = currentState.copy(selectedBets = updatedBets)
    }

    fun clearAllBets() {
        _bettingSlipState.value = BettingSlipState()
    }

    fun isSelected(eventId: String, betType: String): Boolean {
        val bet = _bettingSlipState.value.selectedBets[eventId]
        return bet?.betType == betType
    }
}