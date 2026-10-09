package com.saludplus.citas.ui.agendamiento

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.KeyboardArrowLeft
import androidx.compose.material.icons.automirrored.filled.KeyboardArrowRight
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
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
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import com.saludplus.citas.repository.Repositorio
import com.saludplus.citas.ui.components.BarraSuperior
import com.saludplus.citas.ui.components.BotonPrimario
import com.saludplus.citas.ui.components.avatarDe
import com.saludplus.citas.util.diasHabiles
import com.saludplus.citas.util.mesYAnio
import com.saludplus.citas.util.nombreDiaCorto

@Composable
private fun Mosaico(seleccionado: Boolean, onClick: () -> Unit, modifier: Modifier = Modifier, contenido: @Composable () -> Unit) {
    Surface(
        onClick = onClick,
        shape = RoundedCornerShape(12.dp),
        color = if (seleccionado) MaterialTheme.colorScheme.primary else MaterialTheme.colorScheme.surfaceVariant,
        contentColor = if (seleccionado) MaterialTheme.colorScheme.onPrimary else MaterialTheme.colorScheme.onSurface,
        modifier = modifier
    ) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(vertical = 12.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) { contenido() }
    }
}

@Composable
fun FechaHoraScreen(medicoId: Int, onContinuar: (fecha: String, hora: String) -> Unit, onVolver: () -> Unit) {
    val medico = Repositorio.obtenerMedico(medicoId)
    val especialidad = medico?.let { Repositorio.obtenerEspecialidad(it.especialidadId) }
    var semana by remember { mutableStateOf(0) }
    val dias = diasHabiles(semana)
    var fecha by remember { mutableStateOf<String?>(null) }
    var hora by remember { mutableStateOf<String?>(null) }
    val horarios = fecha?.let { Repositorio.horariosDisponibles(medicoId, it) } ?: emptyList()

    Scaffold(topBar = { BarraSuperior("Seleccionar fecha y hora", onVolver) }) { padding ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(padding)
                .padding(horizontal = 16.dp)
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
                Text(mesYAnio(dias), style = MaterialTheme.typography.titleMedium, fontWeight = FontWeight.Bold)
                IconButton(onClick = {
                    semana++
                    fecha = null
                    hora = null
                }) {
                    Icon(Icons.AutoMirrored.Filled.KeyboardArrowRight, contentDescription = "Semana siguiente")
                }
            }

            Row(
                modifier = Modifier.padding(vertical = 8.dp),
                horizontalArrangement = Arrangement.spacedBy(8.dp)
            ) {
                dias.forEach { dia ->
                    Mosaico(
                        seleccionado = dia.toString() == fecha,
                        onClick = {
                            fecha = dia.toString()
                            hora = null
                        },
                        modifier = Modifier.weight(1f)
                    ) {
                        Text(nombreDiaCorto(dia), style = MaterialTheme.typography.labelSmall)
                        Text(dia.dayOfMonth.toString(), style = MaterialTheme.typography.titleMedium, fontWeight = FontWeight.Bold)
                    }
                }
            }

            Text(
                "Horarios disponibles",
                style = MaterialTheme.typography.titleMedium,
                fontWeight = FontWeight.Bold,
                modifier = Modifier.padding(top = 12.dp, bottom = 8.dp)
            )
            if (fecha == null) {
                Text("Elige un día para ver los horarios", color = MaterialTheme.colorScheme.onSurfaceVariant)
            } else if (horarios.isEmpty()) {
                Text("No hay horarios disponibles para este día", color = MaterialTheme.colorScheme.onSurfaceVariant)
            }
            LazyVerticalGrid(
                columns = GridCells.Fixed(3),
                modifier = Modifier.weight(1f),
                horizontalArrangement = Arrangement.spacedBy(8.dp),
                verticalArrangement = Arrangement.spacedBy(8.dp)
            ) {
                items(horarios) { h ->
                    Mosaico(seleccionado = h == hora, onClick = { hora = h }) {
                        Text(h, style = MaterialTheme.typography.titleSmall)
                    }
                }
            }

            BotonPrimario(
                texto = "Continuar",
                onClick = {
                    val f = fecha
                    val h = hora
                    if (f != null && h != null) onContinuar(f, h)
                },
                habilitado = fecha != null && hora != null,
                modifier = Modifier.padding(vertical = 12.dp)
            )
        }
    }
}
