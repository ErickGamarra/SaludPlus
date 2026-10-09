package com.saludplus.citas.ui.citas

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.CalendarMonth
import androidx.compose.material.icons.filled.LocationOn
import androidx.compose.material.icons.filled.MedicalServices
import androidx.compose.material.icons.filled.Notes
import androidx.compose.material.icons.filled.Schedule
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.saludplus.citas.repository.Repositorio
import com.saludplus.citas.ui.components.BarraSuperior
import com.saludplus.citas.ui.components.CabeceraMedico
import com.saludplus.citas.ui.components.FilaDato
import com.saludplus.citas.util.fechaEnTexto
import com.saludplus.citas.util.rangoHora

@Composable
fun DetalleCitaScreen(citaId: Int, onVolver: () -> Unit, onCitaCancelada: () -> Unit) {
    val cita = Repositorio.obtenerCita(citaId)
    val medico = cita?.let { Repositorio.obtenerMedico(it.medicoId) }
    val especialidad = cita?.let { Repositorio.obtenerEspecialidad(it.especialidadId) }
    var mostrarDialogo by remember { mutableStateOf(false) }

    Scaffold(topBar = { BarraSuperior("Detalle de cita", onVolver) }) { padding ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(padding)
                .padding(horizontal = 16.dp)
                .verticalScroll(rememberScrollState()),
            verticalArrangement = Arrangement.spacedBy(16.dp)
        ) {
            if (cita == null) {
                Text("La cita no existe", style = MaterialTheme.typography.bodyLarge)
            } else {
                if (medico != null) CabeceraMedico(medico, especialidad?.nombre ?: "")
                FilaDato(Icons.Filled.CalendarMonth, "Fecha", fechaEnTexto(cita.fecha))
                FilaDato(Icons.Filled.Schedule, "Hora", rangoHora(cita.hora))
                FilaDato(Icons.Filled.MedicalServices, "Tipo de atención", "Consulta presencial")
                FilaDato(Icons.Filled.LocationOn, "Dirección", "Av. Los Olivos 123, Lima")
                if (cita.motivo.isNotBlank()) FilaDato(Icons.Filled.Notes, "Motivo de consulta", cita.motivo)
                OutlinedButton(
                    onClick = { mostrarDialogo = true },
                    shape = RoundedCornerShape(12.dp),
                    colors = ButtonDefaults.outlinedButtonColors(contentColor = MaterialTheme.colorScheme.error),
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(52.dp)
                ) {
                    Text("Cancelar cita")
                }
            }
        }
    }

    if (mostrarDialogo) {
        AlertDialog(
            onDismissRequest = { mostrarDialogo = false },
            title = { Text("Cancelar cita") },
            text = { Text("¿Seguro que deseas cancelar esta cita?") },
            confirmButton = {
                TextButton(onClick = {
                    mostrarDialogo = false
                    Repositorio.cancelarCita(citaId)
                    onCitaCancelada()
                }) { Text("Sí, cancelar") }
            },
            dismissButton = {
                TextButton(onClick = { mostrarDialogo = false }) { Text("No") }
            }
        )
    }
}
