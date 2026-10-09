package com.saludplus.citas.ui.components

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.statusBarsPadding
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material3.Button
import androidx.compose.material3.CenterAlignedTopAppBar
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.OutlinedTextFieldDefaults
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun BarraSuperior(titulo: String, onVolver: () -> Unit) {
    CenterAlignedTopAppBar(
        title = { Text(titulo, style = MaterialTheme.typography.titleMedium, fontWeight = FontWeight.Bold) },
        navigationIcon = {
            IconButton(onClick = onVolver) {
                Icon(Icons.AutoMirrored.Filled.ArrowBack, contentDescription = "Volver")
            }
        },
        colors = TopAppBarDefaults.topAppBarColors(containerColor = MaterialTheme.colorScheme.background)
    )
}

@Composable
fun CampoTexto(
    valor: String,
    onValorChange: (String) -> Unit,
    etiqueta: String,
    error: String? = null,
    esContrasena: Boolean = false,
    tipoTeclado: KeyboardType = KeyboardType.Text,
    icono: ImageVector? = null,
    modifier: Modifier = Modifier
) {
    OutlinedTextField(
        value = valor,
        onValueChange = onValorChange,
        label = { Text(etiqueta) },
        leadingIcon = icono?.let { { Icon(it, contentDescription = null, tint = MaterialTheme.colorScheme.primary) } },
        isError = error != null,
        supportingText = { if (error != null) Text(error) },
        singleLine = true,
        shape = RoundedCornerShape(12.dp),
        colors = OutlinedTextFieldDefaults.colors(
            unfocusedContainerColor = MaterialTheme.colorScheme.surfaceVariant,
            focusedContainerColor = MaterialTheme.colorScheme.surfaceVariant,
            unfocusedBorderColor = Color.Transparent
        ),
        visualTransformation = if (esContrasena) PasswordVisualTransformation() else VisualTransformation.None,
        keyboardOptions = KeyboardOptions(keyboardType = if (esContrasena) KeyboardType.Password else tipoTeclado),
        modifier = modifier.fillMaxWidth()
    )
}

@Composable
fun BotonPrimario(texto: String, onClick: () -> Unit, modifier: Modifier = Modifier, habilitado: Boolean = true) {
    Button(
        onClick = onClick,
        enabled = habilitado,
        shape = RoundedCornerShape(12.dp),
        modifier = modifier
            .fillMaxWidth()
            .height(52.dp)
    ) {
        Text(texto, style = MaterialTheme.typography.labelLarge)
    }
}

@Composable
fun IconoCirculo(
    icono: ImageVector,
    fondo: Color,
    tinte: Color,
    modifier: Modifier = Modifier,
    tamano: Dp = 44.dp
) {
    Box(
        modifier = modifier
            .size(tamano)
            .background(fondo, CircleShape),
        contentAlignment = Alignment.Center
    ) {
        Icon(icono, contentDescription = null, tint = tinte, modifier = Modifier.size(tamano / 2))
    }
}

@Composable
fun TituloPantalla(texto: String) {
    Text(
        texto,
        style = MaterialTheme.typography.headlineSmall,
        fontWeight = FontWeight.Bold,
        modifier = Modifier
            .statusBarsPadding()
            .padding(horizontal = 16.dp, vertical = 16.dp)
    )
}

@Composable
fun FilaDato(icono: ImageVector, titulo: String, valor: String) {
    Row(verticalAlignment = Alignment.CenterVertically, modifier = Modifier.fillMaxWidth()) {
        IconoCirculo(icono, MaterialTheme.colorScheme.primaryContainer, MaterialTheme.colorScheme.primary)
        Column(modifier = Modifier.padding(start = 12.dp)) {
            Text(titulo, style = MaterialTheme.typography.labelMedium, color = MaterialTheme.colorScheme.onSurfaceVariant)
            Text(valor, style = MaterialTheme.typography.bodyLarge, fontWeight = FontWeight.SemiBold)
        }
    }
}

@Composable
fun EstadoVacio(icono: ImageVector, titulo: String, detalle: String, modifier: Modifier = Modifier) {
    Column(
        modifier = modifier
            .fillMaxSize()
            .padding(32.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = androidx.compose.foundation.layout.Arrangement.Center
    ) {
        IconoCirculo(icono, MaterialTheme.colorScheme.primaryContainer, MaterialTheme.colorScheme.primary, tamano = 88.dp)
        Text(
            titulo,
            style = MaterialTheme.typography.titleMedium,
            fontWeight = FontWeight.Bold,
            textAlign = androidx.compose.ui.text.style.TextAlign.Center,
            modifier = Modifier.padding(top = 16.dp)
        )
        Text(
            detalle,
            style = MaterialTheme.typography.bodyMedium,
            color = MaterialTheme.colorScheme.onSurfaceVariant,
            textAlign = androidx.compose.ui.text.style.TextAlign.Center,
            modifier = Modifier.padding(top = 4.dp)
        )
    }
}
