package com.saludplus.citas.ui.resultados

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Description
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import com.saludplus.citas.navigation.Rutas
import com.saludplus.citas.repository.Repositorio
import com.saludplus.citas.ui.components.BarraNavegacion
import com.saludplus.citas.ui.components.IconoCirculo
import com.saludplus.citas.ui.components.TituloPantalla
import com.saludplus.citas.ui.theme.Naranja
import com.saludplus.citas.ui.theme.NaranjaClaro
import com.saludplus.citas.ui.theme.VerdeClaro
import com.saludplus.citas.ui.theme.VerdeExito
import com.saludplus.citas.util.fechaEnTexto

@Composable
fun ResultadosScreen(onNavegarBarra: (String) -> Unit) {
    Scaffold(
        topBar = { TituloPantalla("Resultados") },
        bottomBar = { BarraNavegacion(Rutas.RESULTADOS, onNavegarBarra) }
    ) { padding ->
        LazyColumn(
            modifier = Modifier
                .fillMaxSize()
                .padding(padding)
                .padding(horizontal = 16.dp),
            verticalArrangement = Arrangement.spacedBy(12.dp)
        ) {
            items(Repositorio.resultados, key = { it.id }) { resultado ->
                val disponible = resultado.estado == "Disponible"
                Card(
                    shape = RoundedCornerShape(16.dp),
                    colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surfaceVariant),
                    modifier = Modifier.fillMaxWidth()
                ) {
                    Row(modifier = Modifier.padding(14.dp), verticalAlignment = Alignment.CenterVertically) {
                        IconoCirculo(Icons.Filled.Description, NaranjaClaro, Naranja)
                        Column(modifier = Modifier
                            .padding(horizontal = 12.dp)
                            .weight(1f)) {
                            Text(resultado.examen, style = MaterialTheme.typography.titleMedium, fontWeight = FontWeight.Bold)
                            Text(
                                fechaEnTexto(resultado.fecha),
                                style = MaterialTheme.typography.bodySmall,
                                color = MaterialTheme.colorScheme.onSurfaceVariant
                            )
                        }
                        Text(
                            resultado.estado,
                            style = MaterialTheme.typography.labelSmall,
                            color = if (disponible) VerdeExito else Naranja,
                            modifier = Modifier
                                .background(if (disponible) VerdeClaro else Color(0xFFFEF3C7), RoundedCornerShape(8.dp))
                                .padding(horizontal = 8.dp, vertical = 4.dp)
                        )
                    }
                }
            }
        }
    }
}
