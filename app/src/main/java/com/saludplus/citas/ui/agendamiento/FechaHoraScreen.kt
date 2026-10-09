package com.saludplus.citas.ui.agendamiento

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.KeyboardArrowLeft
import androidx.compose.material.icons.automirrored.filled.KeyboardArrowRight
import androidx.compose.material3.Button
import androidx.compose.material3.FilterChip
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.saludplus.citas.repository.Repositorio
import com.saludplus.citas.ui.components.BarraSuperior
import com.saludplus.citas.util.diasHabiles
import com.saludplus.citas.util.etiquetaDia
import com.saludplus.citas.util.mesYAnio

@Composable
fun FechaHoraScreen(medicoId: Int, onContinuar: (fecha: String, hora: String) -> Unit, onVolver: () -> Unit) {
    val medico = Repositorio.obtenerMedico(medicoId)
    var semana by remember { mutableStateOf(0) }
    val dias = diasHabiles(semana)
    var fecha by remember { mutableStateOf<String?>(null) }
    var hora by remember { mutableStateOf<String?>(null) }
    val horarios = fecha?.let { Repositorio.horariosDisponibles(medicoId, it) } ?: emptyList()

    Scaffold(topBar = { BarraSuperior("Fecha y hora", onVolver) }) { padding ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(padding)
                .padding(16.dp)
        ) {
            Text(medico?.nombre ?: "", style = MaterialTheme.typography.titleLarge)

            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(top = 16.dp),
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                IconButton(
                    onClick = {
                        semana--
                        fecha = null
                        hora = null
                    },
                    enabled = semana > 0
                ) {
                    Icon(Icons.AutoMirrored.Filled.KeyboardArrowLeft, contentDescription = "Semana anterior")
                }
                Text(mesYAnio(dias), style = MaterialTheme.typography.titleMedium)
                IconButton(onClick = {
                    semana++
                    fecha = null
                    hora = null
                }) {
                    Icon(Icons.AutoMirrored.Filled.KeyboardArrowRight, contentDescription = "Semana siguiente")
                }
            }

            LazyRow(
                modifier = Modifier.padding(vertical = 8.dp),
                horizontalArrangement = Arrangement.spacedBy(8.dp)
            ) {
                items(dias) { dia ->
                    FilterChip(
                        selected = dia.toString() == fecha,
                        onClick = {
                            fecha = dia.toString()
                            hora = null
                        },
                        label = { Text(etiquetaDia(dia)) }
                    )
                }
            }

            Text("Horarios disponibles", style = MaterialTheme.typography.titleMedium, modifier = Modifier.padding(top = 16.dp, bottom = 8.dp))
            if (fecha == null) {
                Text("Elige un día para ver los horarios")
            } else if (horarios.isEmpty()) {
                Text("No hay horarios disponibles para este día")
            }
            LazyVerticalGrid(
                columns = GridCells.Fixed(4),
                modifier = Modifier.weight(1f),
                horizontalArrangement = Arrangement.spacedBy(8.dp),
                verticalArrangement = Arrangement.spacedBy(8.dp)
            ) {
                items(horarios) { h ->
                    FilterChip(
                        selected = h == hora,
                        onClick = { hora = h },
                        label = { Text(h) }
                    )
                }
            }

            Button(
                onClick = {
                    val f = fecha
                    val h = hora
                    if (f != null && h != null) onContinuar(f, h)
                },
                enabled = fecha != null && hora != null,
                modifier = Modifier.fillMaxWidth()
            ) {
                Text("Continuar")
            }
        }
    }
}
