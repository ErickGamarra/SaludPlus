package com.saludplus.citas.ui.home

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.BoxWithConstraints
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.statusBarsPadding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.CalendarMonth
import androidx.compose.material.icons.filled.Description
import androidx.compose.material.icons.filled.Event
import androidx.compose.material.icons.filled.Notifications
import androidx.compose.material.icons.filled.Person
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import com.saludplus.citas.navigation.Rutas
import com.saludplus.citas.repository.Repositorio
import com.saludplus.citas.ui.components.BarraNavegacion
import com.saludplus.citas.ui.components.TarjetaAcceso
import com.saludplus.citas.ui.components.TarjetaEspecialidad
import com.saludplus.citas.ui.theme.AzulClaro
import com.saludplus.citas.ui.theme.AzulSalud
import com.saludplus.citas.ui.theme.Morado
import com.saludplus.citas.ui.theme.MoradoClaro
import com.saludplus.citas.ui.theme.Naranja
import com.saludplus.citas.ui.theme.NaranjaClaro
import com.saludplus.citas.ui.theme.VerdeClaro
import com.saludplus.citas.ui.theme.VerdeExito

@Composable
fun HomeScreen(
    onAgendarCita: () -> Unit,
    onEspecialidadClick: (Int) -> Unit,
    onNotificaciones: () -> Unit,
    onNavegarBarra: (String) -> Unit
) {
    val nombre = Repositorio.usuarioActual?.nombre?.substringBefore(" ") ?: "paciente"
    val destacadas = Repositorio.especialidadesDestacadas()

    Scaffold(bottomBar = { BarraNavegacion(Rutas.HOME, onNavegarBarra) }) { padding ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(padding)
                .statusBarsPadding()
                .padding(horizontal = 16.dp, vertical = 12.dp)
        ) {
            Row(
                modifier = Modifier.fillMaxWidth(),
                verticalAlignment = Alignment.CenterVertically
            ) {
                Column(modifier = Modifier.weight(1f)) {
                    Text("¡Hola, $nombre!", style = MaterialTheme.typography.headlineSmall, fontWeight = FontWeight.Bold)
                    Text(
                        "¿Qué deseas hacer hoy?",
                        style = MaterialTheme.typography.bodyMedium,
                        color = MaterialTheme.colorScheme.onSurfaceVariant
                    )
                }
                IconButton(onClick = onNotificaciones) {
                    Icon(Icons.Filled.Notifications, contentDescription = "Notificaciones")
                }
            }

            Row(
                modifier = Modifier.padding(top = 20.dp),
                horizontalArrangement = Arrangement.spacedBy(12.dp)
            ) {
                TarjetaAcceso(Icons.Filled.CalendarMonth, "Agendar cita", AzulClaro, AzulSalud, onAgendarCita, Modifier.weight(1f))
                TarjetaAcceso(Icons.Filled.Event, "Mis citas", VerdeClaro, VerdeExito, { onNavegarBarra(Rutas.MIS_CITAS) }, Modifier.weight(1f))
            }
            Row(
                modifier = Modifier.padding(top = 12.dp),
                horizontalArrangement = Arrangement.spacedBy(12.dp)
            ) {
                TarjetaAcceso(Icons.Filled.Person, "Mis datos", MoradoClaro, Morado, { onNavegarBarra(Rutas.PERFIL) }, Modifier.weight(1f))
                TarjetaAcceso(Icons.Filled.Description, "Resultados", NaranjaClaro, Naranja, { onNavegarBarra(Rutas.RESULTADOS) }, Modifier.weight(1f))
            }

            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(top = 24.dp),
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                Text("Especialidades destacadas", style = MaterialTheme.typography.titleMedium, fontWeight = FontWeight.Bold)
                TextButton(onClick = onAgendarCita) { Text("Ver todas") }
            }
            BoxWithConstraints(modifier = Modifier.fillMaxWidth()) {
                val separacion = 12.dp
                val ancho = ((maxWidth - separacion * (destacadas.size - 1)) / destacadas.size).coerceAtLeast(96.dp)
                LazyRow(horizontalArrangement = Arrangement.spacedBy(separacion, Alignment.CenterHorizontally)) {
                    items(destacadas) { especialidad ->
                        TarjetaEspecialidad(
                            especialidad = especialidad,
                            onClick = { onEspecialidadClick(especialidad.id) },
                            modifier = Modifier.width(ancho)
                        )
                    }
                }
            }
        }
    }
}
