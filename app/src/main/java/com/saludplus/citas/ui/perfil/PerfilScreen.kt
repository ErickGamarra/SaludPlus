package com.saludplus.citas.ui.perfil

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.Logout
import androidx.compose.material.icons.filled.Email
import androidx.compose.material.icons.filled.Event
import androidx.compose.material.icons.filled.Phone
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import com.saludplus.citas.navigation.Rutas
import com.saludplus.citas.repository.Repositorio
import com.saludplus.citas.ui.components.BarraNavegacion
import com.saludplus.citas.ui.components.FilaDato
import com.saludplus.citas.ui.components.TituloPantalla

@Composable
fun PerfilScreen(onCerrarSesion: () -> Unit, onNavegarBarra: (String) -> Unit) {
    val usuario = Repositorio.usuarioActual
    val iniciales = usuario?.nombre
        ?.split(" ")
        ?.filter { it.isNotBlank() }
        ?.take(2)
        ?.joinToString("") { it.first().uppercase() }
        ?: "?"

    Scaffold(
        topBar = { TituloPantalla("Perfil") },
        bottomBar = { BarraNavegacion(Rutas.PERFIL, onNavegarBarra) }
    ) { padding ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(padding)
                .padding(horizontal = 16.dp)
                .verticalScroll(rememberScrollState()),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Box(
                modifier = Modifier
                    .size(104.dp)
                    .background(MaterialTheme.colorScheme.primary, CircleShape),
                contentAlignment = Alignment.Center
            ) {
                Text(
                    iniciales,
                    style = MaterialTheme.typography.headlineMedium,
                    fontWeight = FontWeight.Bold,
                    color = MaterialTheme.colorScheme.onPrimary
                )
            }
            Text(
                usuario?.nombre ?: "-",
                style = MaterialTheme.typography.titleLarge,
                fontWeight = FontWeight.Bold,
                modifier = Modifier.padding(top = 16.dp)
            )
            Text(
                "Paciente",
                style = MaterialTheme.typography.bodyMedium,
                color = MaterialTheme.colorScheme.onSurfaceVariant
            )

            Surface(
                shape = RoundedCornerShape(16.dp),
                color = MaterialTheme.colorScheme.surfaceVariant,
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(top = 24.dp)
            ) {
                Column(modifier = Modifier.padding(16.dp), verticalArrangement = Arrangement.spacedBy(12.dp)) {
                    FilaDato(Icons.Filled.Email, "Correo", usuario?.correo ?: "-")
                    HorizontalDivider()
                    FilaDato(Icons.Filled.Phone, "Teléfono", usuario?.telefono ?: "-")
                    HorizontalDivider()
                    FilaDato(Icons.Filled.Event, "Citas agendadas", Repositorio.citasDelUsuario().size.toString())
                }
            }

            OutlinedButton(
                onClick = {
                    Repositorio.cerrarSesion()
                    onCerrarSesion()
                },
                shape = RoundedCornerShape(12.dp),
                colors = ButtonDefaults.outlinedButtonColors(contentColor = MaterialTheme.colorScheme.error),
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(top = 24.dp)
                    .height(52.dp)
            ) {
                Icon(Icons.AutoMirrored.Filled.Logout, contentDescription = null, modifier = Modifier.size(20.dp))
                Text("Cerrar sesión", modifier = Modifier.padding(start = 8.dp))
            }
            androidx.compose.foundation.layout.Spacer(Modifier.height(16.dp))
        }
    }
}
