package com.saludplus.citas.ui.agendamiento

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.systemBarsPadding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.CalendarMonth
import androidx.compose.material.icons.filled.CheckCircle
import androidx.compose.material.icons.filled.Person
import androidx.compose.material.icons.filled.Schedule
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import com.saludplus.citas.repository.Repositorio
import com.saludplus.citas.ui.components.BotonPrimario
import com.saludplus.citas.ui.components.FilaDato
import com.saludplus.citas.ui.components.IconoCirculo
import com.saludplus.citas.ui.theme.VerdeClaro
import com.saludplus.citas.ui.theme.VerdeExito
import com.saludplus.citas.util.fechaEnTexto
import com.saludplus.citas.util.rangoHora

@Composable
fun CitaExitosaScreen(onVerMisCitas: () -> Unit, onIrAInicio: () -> Unit) {
    val cita = Repositorio.citasDelUsuario().maxByOrNull { it.id }
    val medico = cita?.let { Repositorio.obtenerMedico(it.medicoId) }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .systemBarsPadding()
            .padding(24.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.spacedBy(16.dp, Alignment.CenterVertically)
    ) {
        IconoCirculo(Icons.Filled.CheckCircle, VerdeClaro, VerdeExito, tamano = 110.dp)
        Text("¡Cita agendada!", style = MaterialTheme.typography.headlineSmall, fontWeight = FontWeight.Bold)
        Text(
            "Tu cita fue registrada correctamente.",
            style = MaterialTheme.typography.bodyMedium,
            color = MaterialTheme.colorScheme.onSurfaceVariant
        )
        if (cita != null) {
            Surface(shape = RoundedCornerShape(16.dp), color = MaterialTheme.colorScheme.surfaceVariant) {
                Column(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(16.dp),
                    verticalArrangement = Arrangement.spacedBy(12.dp)
                ) {
                    FilaDato(Icons.Filled.Person, "Médico", medico?.nombre ?: "-")
                    FilaDato(Icons.Filled.CalendarMonth, "Fecha", fechaEnTexto(cita.fecha))
                    FilaDato(Icons.Filled.Schedule, "Hora", rangoHora(cita.hora))
                }
            }
        }
        BotonPrimario("Ver mis citas", onVerMisCitas)
        TextButton(onClick = onIrAInicio) { Text("Ir al inicio") }
    }
}
