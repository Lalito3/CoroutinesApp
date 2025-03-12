package com.example.coroutinesapp.viewmodel

import androidx.lifecycle.ViewModel
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.compose.runtime.getValue
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.Job
import kotlinx.coroutines.launch
import kotlinx.coroutines.delay

class MainViewModel : ViewModel() {
    var resutState by mutableStateOf("")
        private set

    var countTime by mutableStateOf(0)
        private set

    var twocountTime by mutableStateOf(0)
        private set

    private var PrimerContador = false
    private var SegundoContador = false

    private var job: Job? = null
    private var jobtwo: Job? = null

    fun fetchData() {
        Reiniciar()

        job = viewModelScope.launch {
            OneCounterS()
            PrimerContador = true

            jobtwo = viewModelScope.launch {
                SecondCounterS()
                SegundoContador = true

                if (PrimerContador && SegundoContador) {
                    delay(5000)
                    resutState = "Respuesta obtenia de la web"
                }
            }
        }
    }

    private fun Reiniciar() {
        resutState = ""
        countTime = 0
        twocountTime = 0
        PrimerContador = false
        SegundoContador = false
    }

    private suspend fun OneCounterS() {
        for (i in 1..5) {
            delay(1000)
            countTime = i
        } //SI SE QUITA EL SUSPEND No sirve el delay
    }

    private suspend fun SecondCounterS() {
        for (j in 1..5) {
            delay(1000)
            twocountTime = j
        }
    }

    fun cancelCounter() {
        Reiniciar()
        job?.cancel()
        jobtwo?.cancel()
    }
}
