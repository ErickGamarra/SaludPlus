package com.saludplus.citas.ui.citas

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Event
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.saludplus.citas.navigation.Rutas
import com.saludplus.citas.repository.Repositorio
import com.saludplus.citas.ui.components.BarraNavegacion
import com.saludplus.citas.ui.components.EstadoVacio
import com.saludplus.citas.ui.components.TarjetaCita
import com.saludplus.citas.ui.components.TituloPantalla

@Composable
fun MisCitasScreen(onCitaClick: (Int) -> Unit, onNavegarBarra: (String) -> Unit) {
    val citas = Repositorio.citasDelUsuario()

    Scaffold(
        topBar = { TituloPantalla("Mis citas") },
        bottomBar = { BarraNavegacion(Rutas.MIS_CITAS, onNavegarBarra) }
    ) { padding ->
        if (citas.isEmpty()) {
            EstadoVacio(
                icono = Icons.Filled.Event,
                titulo = "Aún no tienes citas agendadas",
                detalle = "Cuando agendes una cita aparecerá aquí",
                modifier = Modifier.padding(padding)
            )
        } else {
            LazyColumn(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(padding)
                    .padding(horizontal = 16.dp),
                verticalArrangement = Arrangement.spacedBy(12.dp)
            ) {
                items(citas, key = { it.id }) { cita ->
                    TarjetaCita(
                        cita = cita,
                        medico = Repositorio.obtenerMedico(cita.medicoId),
                        especialidad = Repositorio.obtenerEspecialidad(cita.especialidadId)?.nombre ?: "",
                        onClick = { onCitaClick(cita.id) }
                    )
                }
            }
        }
    }
}
