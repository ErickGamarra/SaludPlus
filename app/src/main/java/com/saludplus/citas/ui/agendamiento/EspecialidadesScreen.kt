package com.saludplus.citas.ui.agendamiento

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.KeyboardArrowRight
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.Icon
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
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import com.saludplus.citas.repository.Repositorio
import com.saludplus.citas.ui.components.BarraSuperior
import com.saludplus.citas.ui.components.CampoTexto
import com.saludplus.citas.ui.components.IconoCirculo
import com.saludplus.citas.ui.components.estiloEspecialidad

@Composable
fun EspecialidadesScreen(onEspecialidadClick: (Int) -> Unit, onVolver: () -> Unit) {
    var consulta by remember { mutableStateOf("") }
    val resultados = Repositorio.buscarEspecialidades(consulta)

    Scaffold(topBar = { BarraSuperior("Especialidades", onVolver) }) { padding ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(padding)
                .padding(horizontal = 16.dp)
        ) {
            CampoTexto(consulta, { consulta = it }, "Buscar especialidad...", icono = Icons.Filled.Search)
            if (resultados.isEmpty()) {
                Text(
                    "No se encontraron especialidades",
                    style = MaterialTheme.typography.bodyLarge,
                    modifier = Modifier.padding(top = 24.dp)
                )
            }
            LazyColumn(verticalArrangement = Arrangement.spacedBy(4.dp)) {
                items(resultados, key = { it.id }) { especialidad ->
                    val estilo = estiloEspecialidad(especialidad.id)
                    Row(
                        modifier = Modifier
                            .fillMaxWidth()
                            .clickable { onEspecialidadClick(especialidad.id) }
                            .padding(vertical = 10.dp),
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        IconoCirculo(estilo.icono, estilo.fondo, estilo.tinte, tamano = 48.dp)
                        Column(modifier = Modifier
                            .padding(horizontal = 14.dp)
                            .weight(1f)) {
                            Text(especialidad.nombre, style = MaterialTheme.typography.titleMedium, fontWeight = FontWeight.Bold)
                            Text(
                                especialidad.descripcion,
                                style = MaterialTheme.typography.bodySmall,
                                color = MaterialTheme.colorScheme.onSurfaceVariant
                            )
                        }
                        Icon(Icons.AutoMirrored.Filled.KeyboardArrowRight, contentDescription = null, tint = MaterialTheme.colorScheme.onSurfaceVariant)
                    }
                }
            }
        }
    }
}
