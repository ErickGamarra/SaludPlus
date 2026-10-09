package com.saludplus.citas.ui.components

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ChildCare
import androidx.compose.material.icons.filled.Face
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material.icons.filled.Female
import androidx.compose.material.icons.filled.Healing
import androidx.compose.material.icons.filled.Person
import androidx.compose.material.icons.filled.Psychology
import androidx.compose.material.icons.filled.Visibility
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import com.saludplus.citas.R
import com.saludplus.citas.model.Medico
import com.saludplus.citas.ui.theme.AzulClaro
import com.saludplus.citas.ui.theme.AzulSalud
import com.saludplus.citas.ui.theme.Morado
import com.saludplus.citas.ui.theme.MoradoClaro
import com.saludplus.citas.ui.theme.Naranja
import com.saludplus.citas.ui.theme.NaranjaClaro
import com.saludplus.citas.ui.theme.RosaClaro

class EstiloEspecialidad(val icono: ImageVector, val fondo: Color, val tinte: Color)

fun estiloEspecialidad(id: Int): EstiloEspecialidad = when (id) {
    1 -> EstiloEspecialidad(Icons.Filled.Person, AzulClaro, AzulSalud)
    2 -> EstiloEspecialidad(Icons.Filled.ChildCare, NaranjaClaro, Naranja)
    3 -> EstiloEspecialidad(Icons.Filled.Favorite, RosaClaro, Color(0xFFE11D48))
    4 -> EstiloEspecialidad(Icons.Filled.Face, Color(0xFFFEF3C7), Color(0xFFD97706))
    5 -> EstiloEspecialidad(Icons.Filled.Healing, AzulClaro, AzulSalud)
    6 -> EstiloEspecialidad(Icons.Filled.Visibility, Color(0xFFE0F2FE), Color(0xFF0284C7))
    7 -> EstiloEspecialidad(Icons.Filled.Female, RosaClaro, Color(0xFFDB2777))
    else -> EstiloEspecialidad(Icons.Filled.Psychology, MoradoClaro, Morado)
}

fun avatarDe(medico: Medico): Int =
    if (medico.nombre.startsWith("Dra.")) R.drawable.avatar_medica else R.drawable.avatar_medico
