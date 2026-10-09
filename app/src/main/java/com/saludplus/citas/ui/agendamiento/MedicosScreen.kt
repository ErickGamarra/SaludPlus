package com.saludplus.citas.ui.agendamiento

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.saludplus.citas.repository.Repositorio
import com.saludplus.citas.ui.components.BarraSuperior
import com.saludplus.citas.ui.components.CampoTexto
import com.saludplus.citas.ui.components.TarjetaMedico

@Composable
fun MedicosScreen(especialidadId: Int, onMedicoClick: (Int) -> Unit, onVolver: () -> Unit) {
    var consulta by remember { mutableStateOf("") }
    val especialidad = Repositorio.obtenerEspecialidad(especialidadId)
    val medicos = Repositorio.buscarMedicos(especialidadId, consulta)

    Scaffold(topBar = { BarraSuperior("Médicos de ${especialidad?.nombre ?: ""}", onVolver) }) { padding ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(padding)
                .padding(horizontal = 16.dp)
        ) {
            CampoTexto(consulta, { consulta = it }, "Buscar médico...", icono = Icons.Filled.Search)
            if (medicos.isEmpty()) {
                Text(
                    "No se encontraron médicos",
                    style = MaterialTheme.typography.bodyLarge,
                    modifier = Modifier.padding(top = 24.dp)
                )
            }
            LazyColumn(verticalArrangement = Arrangement.spacedBy(12.dp)) {
                items(medicos, key = { it.id }) { medico ->
                    TarjetaMedico(
                        medico = medico,
                        especialidad = especialidad?.nombre ?: "",
                        disponibleHoy = Repositorio.disponibleHoy(medico.id),
                        onClick = { onMedicoClick(medico.id) }
                    )
                }
            }
        }
    }
}
