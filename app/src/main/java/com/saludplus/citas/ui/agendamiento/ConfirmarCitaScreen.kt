package com.saludplus.citas.ui.agendamiento

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.CalendarMonth
import androidx.compose.material.icons.filled.LocationOn
import androidx.compose.material.icons.filled.MedicalServices
import androidx.compose.material.icons.filled.Schedule
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.OutlinedTextFieldDefaults
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import com.saludplus.citas.repository.Repositorio
import com.saludplus.citas.ui.components.BarraSuperior
import com.saludplus.citas.ui.components.BotonPrimario
import com.saludplus.citas.ui.components.IconoCirculo
import com.saludplus.citas.ui.components.avatarDe
import com.saludplus.citas.ui.theme.AzulClaro
import com.saludplus.citas.ui.theme.AzulSalud
import com.saludplus.citas.util.fechaEnTexto
import com.saludplus.citas.util.rangoHora

@Composable
private fun FilaDato(icono: ImageVector, titulo: String, valor: String) {
    Row(verticalAlignment = Alignment.CenterVertically, modifier = Modifier.fillMaxWidth()) {
        IconoCirculo(icono, AzulClaro, AzulSalud)
        Column(modifier = Modifier.padding(start = 12.dp)) {
            Text(titulo, style = MaterialTheme.typography.labelMedium, color = MaterialTheme.colorScheme.onSurfaceVariant)
            Text(valor, style = MaterialTheme.typography.bodyLarge, fontWeight = FontWeight.SemiBold)
        }
    }
}

@Composable
fun ConfirmarCitaScreen(medicoId: Int, fecha: String, hora: String, onCitaAgendada: () -> Unit, onVolver: () -> Unit) {
    val medico = Repositorio.obtenerMedico(medicoId)
    val especialidad = medico?.let { Repositorio.obtenerEspecialidad(it.especialidadId) }
    var motivo by remember { mutableStateOf("") }
    var error by remember { mutableStateOf<String?>(null) }

    Scaffold(topBar = { BarraSuperior("Confirmar cita", onVolver) }) { padding ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(padding)
                .padding(horizontal = 16.dp)
                .verticalScroll(rememberScrollState()),
            verticalArrangement = Arrangement.spacedBy(16.dp)
        ) {
            if (medico != null) {
                Surface(shape = RoundedCornerShape(16.dp), color = MaterialTheme.colorScheme.surfaceVariant) {
                    Row(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(12.dp),
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Image(
                            painter = painterResource(avatarDe(medico)),
                            contentDescription = null,
                            contentScale = ContentScale.Crop,
                            modifier = Modifier
                                .size(56.dp)
                                .clip(CircleShape)
                        )
                        Column(modifier = Modifier.padding(start = 12.dp)) {
                            Text(medico.nombre, style = MaterialTheme.typography.titleMedium, fontWeight = FontWeight.Bold)
                            Text(
                                especialidad?.nombre ?: "",
                                style = MaterialTheme.typography.bodyMedium,
                                color = MaterialTheme.colorScheme.onSurfaceVariant
                            )
                        }
                    }
                }
            }

            FilaDato(Icons.Filled.CalendarMonth, "Fecha", fechaEnTexto(fecha))
            FilaDato(Icons.Filled.Schedule, "Hora", rangoHora(hora))
            FilaDato(Icons.Filled.MedicalServices, "Tipo de atención", "Consulta presencial")
            FilaDato(Icons.Filled.LocationOn, "Dirección", "Av. Los Olivos 123, Lima")

            Column {
                Text("Motivo de consulta (opcional)", style = MaterialTheme.typography.labelLarge, fontWeight = FontWeight.SemiBold)
                OutlinedTextField(
                    value = motivo,
                    onValueChange = { motivo = it },
                    placeholder = { Text("Consulta de rutina") },
                    shape = RoundedCornerShape(12.dp),
                    colors = OutlinedTextFieldDefaults.colors(
                        unfocusedContainerColor = MaterialTheme.colorScheme.surfaceVariant,
                        focusedContainerColor = MaterialTheme.colorScheme.surfaceVariant
                    ),
                    minLines = 2,
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(top = 8.dp)
                )
            }

            if (error != null) {
                Text(error!!, color = MaterialTheme.colorScheme.error)
            }
            BotonPrimario(
                texto = "Agendar cita",
                onClick = {
                    if (Repositorio.agendarCita(medicoId, fecha, hora, motivo)) onCitaAgendada()
                    else error = "No se pudo agendar: el horario ya fue reservado"
                },
                modifier = Modifier.padding(bottom = 16.dp)
            )
        }
    }
}
