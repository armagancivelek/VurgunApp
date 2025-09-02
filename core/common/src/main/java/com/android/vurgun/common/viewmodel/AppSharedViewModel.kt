package com.android.vurgun.common.viewmodel

import androidx.lifecycle.ViewModel
import com.android.vurgun.common.model.BettingSlipState
import com.android.vurgun.common.model.SelectedBet
import com.android.vurgun.common.model.SubmittedBet
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import javax.inject.Inject
import kotlin.random.Random

class AppSharedViewModel @Inject constructor() : ViewModel() {

    private val _bettingSlipState = MutableStateFlow(BettingSlipState())
    val bettingSlipState: StateFlow<BettingSlipState> = _bettingSlipState.asStateFlow()

    private val _submittedBets = MutableStateFlow<List<SubmittedBet>>(emptyList())
    val submittedBets: StateFlow<List<SubmittedBet>> = _submittedBets.asStateFlow()

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

    fun submitBet(betAmount: Double): SubmittedBet {
        val currentState = _bettingSlipState.value
        val submittedBet = SubmittedBet(
            id = "BET_${System.currentTimeMillis()}",
            bets = currentState.selectedBets.values.toList(),
            betAmount = betAmount,
            totalOdds = currentState.totalOdds,
            maxWin = currentState.totalOdds * betAmount,
        )

        val currentSubmittedBets = _submittedBets.value.toMutableList()
        currentSubmittedBets.add(submittedBet)
        _submittedBets.value = currentSubmittedBets

        clearAllBets()

        return submittedBet
    }
}