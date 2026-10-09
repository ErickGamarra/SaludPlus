package com.saludplus.citas.ui.notificaciones

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Card
import com.saludplus.citas.ui.components.EstadoVacio
import com.saludplus.citas.ui.components.IconoCirculo
import androidx.compose.ui.Alignment
import androidx.compose.material3.CardDefaults
import androidx.compose.material.icons.filled.Notifications
import androidx.compose.material.icons.Icons
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.layout.Row
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.saludplus.citas.repository.Repositorio
import com.saludplus.citas.ui.components.BarraSuperior
import com.saludplus.citas.util.fechaEnTexto

@Composable
fun NotificacionesScreen(onVolver: () -> Unit) {
    val mensajes = Repositorio.citasDelUsuario().map { cita ->
        val medico = Repositorio.obtenerMedico(cita.medicoId)?.nombre ?: "tu médico"
        val fecha = fechaEnTexto(cita.fecha)
        "Recuerda tu cita con $medico el $fecha a las ${cita.hora}"
    }

    Scaffold(topBar = { BarraSuperior("Notificaciones", onVolver) }) { padding ->
        if (mensajes.isEmpty()) {
            EstadoVacio(
                icono = Icons.Filled.Notifications,
                titulo = "No tienes notificaciones",
                detalle = "Te avisaremos cuando tengas una cita próxima",
                modifier = Modifier.padding(padding)
            )
        } else {
            LazyColumn(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(padding)
                    .padding(16.dp),
                verticalArrangement = Arrangement.spacedBy(12.dp)
            ) {
                items(mensajes) { mensaje ->
                    Card(
                        shape = RoundedCornerShape(16.dp),
                        colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surfaceVariant),
                        modifier = Modifier.fillMaxWidth()
                    ) {
                        Row(modifier = Modifier.padding(14.dp), verticalAlignment = Alignment.CenterVertically) {
                            IconoCirculo(Icons.Filled.Notifications, MaterialTheme.colorScheme.primaryContainer, MaterialTheme.colorScheme.primary)
                            Text(mensaje, style = MaterialTheme.typography.bodyMedium, modifier = Modifier.padding(start = 12.dp))
                        }
                    }
                }
            }
        }
    }
}
