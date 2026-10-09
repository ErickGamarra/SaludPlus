package com.saludplus.citas.util

import java.time.DayOfWeek
import java.time.LocalDate

private val meses = listOf(
    "Enero", "Febrero", "Marzo", "Abril", "Mayo", "Junio",
    "Julio", "Agosto", "Setiembre", "Octubre", "Noviembre", "Diciembre"
)

private val diasSemana = listOf("Lunes", "Martes", "Miércoles", "Jueves", "Viernes", "Sábado", "Domingo")

private fun esHabil(fecha: LocalDate) =
    fecha.dayOfWeek != DayOfWeek.SATURDAY && fecha.dayOfWeek != DayOfWeek.SUNDAY

fun primerDiaHabil(desde: LocalDate = LocalDate.now()): LocalDate {
    var dia = desde
    while (!esHabil(dia)) dia = dia.plusDays(1)
    return dia
}

fun diasHabiles(semana: Int, hoy: LocalDate = LocalDate.now()): List<LocalDate> {
    val inicio = primerDiaHabil(hoy).plusWeeks(semana.toLong())
    return generateSequence(inicio) { it.plusDays(1) }
        .filter { esHabil(it) }
        .take(5)
        .toList()
}

fun mesYAnio(dias: List<LocalDate>): String {
    val primero = dias.first()
    val ultimo = dias.last()
    return when {
        primero.year != ultimo.year ->
            "${meses[primero.monthValue - 1]} ${primero.year} - ${meses[ultimo.monthValue - 1]} ${ultimo.year}"
        primero.month != ultimo.month ->
            "${meses[primero.monthValue - 1]} - ${meses[ultimo.monthValue - 1]} ${ultimo.year}"
        else -> "${meses[primero.monthValue - 1]} ${primero.year}"
    }
}

fun etiquetaDia(fecha: LocalDate) = "${diasSemana[fecha.dayOfWeek.ordinal].take(3)} ${fecha.dayOfMonth}"

fun fechaEnTexto(fecha: String): String {
    val f = LocalDate.parse(fecha)
    return "${diasSemana[f.dayOfWeek.ordinal]} ${f.dayOfMonth} de ${meses[f.monthValue - 1].lowercase()} ${f.year}"
}

fun rangoHora(hora: String): String {
    val inicio = hora.substringBefore(":").toInt()
    return "$hora a %02d:%s".format(inicio + 1, hora.substringAfter(":"))
}

fun nombreDiaCorto(fecha: LocalDate) = diasSemana[fecha.dayOfWeek.ordinal].take(3)
