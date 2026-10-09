package com.saludplus.citas.ui.auth

import android.util.Patterns
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Email
import androidx.compose.material.icons.filled.Lock
import androidx.compose.material.icons.filled.Person
import androidx.compose.material.icons.filled.Phone
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import com.saludplus.citas.repository.Repositorio
import com.saludplus.citas.ui.components.BarraSuperior
import com.saludplus.citas.ui.components.BotonPrimario
import com.saludplus.citas.ui.components.CampoTexto

@Composable
fun RegistroScreen(
    onRegistroExitoso: () -> Unit,
    onVerTerminos: () -> Unit,
    onVolver: () -> Unit,
    onIrALogin: () -> Unit
) {
    var nombre by remember { mutableStateOf("") }
    var correo by remember { mutableStateOf("") }
    var telefono by remember { mutableStateOf("") }
    var contrasena by remember { mutableStateOf("") }
    var intentado by remember { mutableStateOf(false) }
    var errorRegistro by remember { mutableStateOf<String?>(null) }

    val nombreValido = nombre.isNotBlank()
    val correoValido = Patterns.EMAIL_ADDRESS.matcher(correo.trim()).matches()
    val telefonoValido = telefono.length == 9 && telefono.all { it.isDigit() }
    val contrasenaValida = contrasena.length >= 6

    val errorNombre = if (intentado && !nombreValido) "Ingresa tu nombre" else null
    val errorCorreo = if (intentado && !correoValido) "Ingresa un correo válido" else null
    val errorTelefono = if (intentado && !telefonoValido) "El teléfono debe tener 9 dígitos" else null
    val errorContrasena = if (intentado && !contrasenaValida) "Mínimo 6 caracteres" else null

    Scaffold(topBar = { BarraSuperior("Crear cuenta", onVolver) }) { padding ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(padding)
                .padding(horizontal = 20.dp)
                .verticalScroll(rememberScrollState()),
            verticalArrangement = Arrangement.spacedBy(4.dp)
        ) {
            Text(
                "Regístrate para agendar tus citas",
                style = MaterialTheme.typography.bodyMedium,
                color = MaterialTheme.colorScheme.onSurfaceVariant,
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(bottom = 16.dp),
                textAlign = androidx.compose.ui.text.style.TextAlign.Center
            )
            CampoTexto(nombre, { nombre = it }, "Nombre completo", errorNombre, icono = Icons.Filled.Person)
            CampoTexto(telefono, { telefono = it }, "Teléfono", errorTelefono, tipoTeclado = KeyboardType.Phone, icono = Icons.Filled.Phone)
            CampoTexto(correo, { correo = it; errorRegistro = null }, "Correo", errorCorreo, tipoTeclado = KeyboardType.Email, icono = Icons.Filled.Email)
            CampoTexto(contrasena, { contrasena = it }, "Contraseña", errorContrasena, esContrasena = true, icono = Icons.Filled.Lock)

            if (errorRegistro != null) {
                Text(errorRegistro!!, color = MaterialTheme.colorScheme.error)
            }

            BotonPrimario(
                texto = "Registrarme",
                onClick = {
                    intentado = true
                    val valido = nombreValido && correoValido && telefonoValido && contrasenaValida
                    if (valido) {
                        if (Repositorio.registrarUsuario(nombre, correo, telefono, contrasena)) {
                            onRegistroExitoso()
                        } else {
                            errorRegistro = "Ya existe una cuenta con ese correo"
                        }
                    }
                },
                modifier = Modifier.padding(top = 8.dp)
            )

            Text(
                "Al registrarte aceptas nuestros",
                style = MaterialTheme.typography.bodySmall,
                color = MaterialTheme.colorScheme.onSurfaceVariant,
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(top = 12.dp),
                textAlign = androidx.compose.ui.text.style.TextAlign.Center
            )
            TextButton(onClick = onVerTerminos, modifier = Modifier.fillMaxWidth()) {
                Text("Términos y Condiciones")
            }
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.Center,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Text("¿Ya tienes cuenta?", style = MaterialTheme.typography.bodyMedium)
                TextButton(onClick = onIrALogin) { Text("Iniciar sesión") }
            }
        }
    }
}
