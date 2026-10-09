# PROMPTS.md - Fase 2 (mejora con IA)

## Paso 1 - Calendario dinamico con LocalDate

**Prompt:** En `FechaHoraScreen` los dias son una lista fija. Genera con `java.time.LocalDate` los proximos 5 dias habiles desde hoy, sin sabados, domingos ni dias pasados. Las flechas `<` y `>` mueven una semana y `<` no puede ir antes de la semana actual. El titulo de mes y anio cambia con la semana. Al cambiar de dia se recalculan los horarios y se reinicia la hora. No debe romper el bloqueo de horarios ya reservados. Pon la logica de fechas en un archivo aparte.

**Respuesta (resumen):** Se creo `util/Fechas.kt` con `diasHabiles(semana)`, `mesYAnio`, `etiquetaDia` y `fechaEnTexto`, y `FechaHoraScreen` paso a usar un estado `semana` con flechas y chips generados.

**Correcciones:** `java.time` solo existe de forma nativa desde Android 8.0 (API 26) y el proyecto tenia `minSdk = 24`; se subio a 26 en vez de agregar desugaring. Al cambiar de semana tambien se reinicia el dia elegido, para no dejar una fecha que ya no esta en pantalla.
