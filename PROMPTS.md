# PROMPTS.md - Fase 2 (mejora con IA)

## Paso 1 - Calendario dinamico con LocalDate

**Prompt:** En `FechaHoraScreen` los dias son una lista fija. Genera con `java.time.LocalDate` los proximos 5 dias habiles desde hoy, sin sabados, domingos ni dias pasados. Las flechas `<` y `>` mueven una semana y `<` no puede ir antes de la semana actual. El titulo de mes y anio cambia con la semana. Al cambiar de dia se recalculan los horarios y se reinicia la hora. No debe romper el bloqueo de horarios ya reservados. Pon la logica de fechas en un archivo aparte.

**Respuesta (resumen):** Se creo `util/Fechas.kt` con `diasHabiles(semana)`, `mesYAnio`, `etiquetaDia` y `fechaEnTexto`, y `FechaHoraScreen` paso a usar un estado `semana` con flechas y chips generados.

**Correcciones:** `java.time` solo existe de forma nativa desde Android 8.0 (API 26) y el proyecto tenia `minSdk = 24`; se subio a 26 en vez de agregar desugaring. Al cambiar de semana tambien se reinicia el dia elegido, para no dejar una fecha que ya no esta en pantalla.

## Paso 3 - Tema y componentes base

**Prompt:** Kotlin con Jetpack Compose y Material 3, sin MVVM, sin librerias nuevas, minSdk 26. Define el estilo de la figura 1 del documento en `ui/theme` y `ui/components`: azul primario fuerte, fondo claro, esquinas de 12 a 16dp, barras superiores blancas con flecha y titulo centrado, boton principal azul de ancho completo, campo de texto con icono a la izquierda y un icono dentro de un circulo de color. Reemplaza `BarraSuperior` y `CampoTexto` manteniendo sus parametros para no romper las pantallas existentes.

**Respuesta (resumen):** Se actualizaron los colores y el tema (azul 2563EB, fondo blanco, colores suaves de apoyo), `BarraSuperior` paso a `CenterAlignedTopAppBar` blanca, `CampoTexto` gano un parametro opcional `icono` y esquinas redondeadas, y se crearon `BotonPrimario` e `IconoCirculo`.

**Correcciones:** La primera version de `Componentes.kt` quedo mal escrita (un `PantallaEnConstruccion` con codigo que no compilaba) y se reescribio completa. Como ya ninguna pantalla lo usa, se elimino `PantallaEnConstruccion`, que la rubrica pide no dejar en pantallas terminadas.

## Paso 4 - Splash, Registro y Login

**Prompt:** Con el tema y los componentes del paso 3, rediseña Splash, Registro y Login segun la figura. Splash: logo, "Clinica SaludPlus", lema, boton "Comenzar" y enlace "Ya tengo una cuenta". Registro: "Crear cuenta" con subtitulo, campos con icono en el orden Nombre, Telefono, Correo, Contrasena, boton "Registrarme", texto de terminos con enlace y "¿Ya tienes cuenta? Iniciar sesion". Login con el mismo estilo y enlace a Registro. Conserva las validaciones y los mensajes de error.

**Respuesta (resumen):** Se reescribieron las tres pantallas con los componentes nuevos, manteniendo las validaciones de Registro (nombre, correo, telefono de 9 digitos, contrasena de 6 o mas) y los errores de Login.

**Correcciones:** El diseno pide los enlaces "Iniciar sesion" en Registro y "Registrate" en Login, que no existian como parametros, asi que se agregaron `onIrALogin` y `onIrARegistro` y se conectaron en `AppNavigation` con `popUpTo` para no apilar pantallas. El proyecto no tenia imagenes: la ilustracion del doctor y el logo se dibujaron como vectores XML (`ilustracion_doctor.xml`, `logo_saludplus.xml`) en vez de usar un icono de Material. En la primera version el doctor quedaba con mucho espacio vacio arriba y se recorto el lienzo con un `group` y `translateY`. Se reviso el resultado renderizando los mismos trazos como SVG antes de integrarlos.

## Paso 5 - Inicio

**Prompt:** Con el tema y los componentes del paso 3, rediseña `HomeScreen` segun la figura: saludo "¡Hola, {nombre}!" con la campana a la derecha sobre fondo claro, cuadricula 2x2 de tarjetas de colores (Agendar cita, Mis citas, Mis datos y Resultados, cada una a su destino), "Especialidades destacadas" con el enlace "Ver todas" que lleva a Especialidades y una fila horizontal de tarjetas con icono. Mantener la barra inferior con 4 destinos y el `LazyRow`.

**Respuesta (resumen):** Se quito la barra superior azul y se armo el encabezado con saludo y campana. `TarjetaAcceso` recibio colores de fondo y de icono, y `TarjetaEspecialidad` paso a tener icono en circulo. Se agrego `estiloEspecialidad(id)` con un icono y color por especialidad.

**Correcciones:** Al no haber `topBar`, el contenido quedaba bajo la barra de estado, y se agrego `statusBarsPadding()`. El cambio de firma de `TarjetaAcceso` obligo a actualizar sus llamadas en Home.

## Paso 6 - Especialidades y Medicos

**Prompt:** Rediseña ambas segun la figura. Especialidades: buscador redondeado con icono y filas con icono en circulo de color, nombre, subtitulo y flecha `>`. Medicos: titulo "Medicos de {especialidad}", avatar, estrella con calificacion y etiqueta verde "Disponible hoy" si el medico tiene horarios libres hoy, o "Disponible esta semana" si no. Mantener la busqueda y el orden por calificacion.

**Respuesta (resumen):** Se reescribieron ambas pantallas, se agrego `Repositorio.disponibleHoy(medicoId)` apoyado en `horariosDisponibles` con la fecha de hoy y se dibujaron dos avatares vectoriales (`avatar_medica.xml`, `avatar_medico.xml`) elegidos segun el "Dra."/"Dr." del nombre.

**Correcciones:** El diseno muestra la cantidad de resenas "(124)", pero el modelo `Medico` no la tiene; se mostraron los anios de experiencia en su lugar para no inventar datos. El icono de busqueda de la barra superior del diseno se reemplazo por el campo de busqueda siempre visible, que cumple lo mismo y se ve mejor. En el avatar femenino el flequillo dejo una franja clara parecida a una diadema.
