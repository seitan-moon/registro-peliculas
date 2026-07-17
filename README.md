# Registro de Peliculas - TP1

Aplicacion Android para gestionar el registro de peliculas de una coleccion
personal. Desarrollada con Kotlin y Jetpack Compose.

## Funcionalidades

- Registro de peliculas con titulo, director, año, genero y duracion
- Lista vertical de peliculas registradas (LazyColumn)
- Cada item muestra sus datos en una fila horizontal deslizable (LazyRow)
- Eliminacion individual de peliculas
- Tema personalizado con soporte para modo claro y oscuro

## Estructura del proyecto
app/src/main/java/com/example/registropeliculas/
├── MainActivity.kt          # Actividad principal, contiene:
│   ├── Pelicula              # data class con los datos de una pelicula
│   ├── PantallaPrincipal     # pantalla que junta formulario y lista
│   ├── FormularioPancracio   # formulario de ingreso de datos
│   ├── ListaDePeliculas      # LazyColumn con la lista de peliculas
│   └── ItemPelicula          # cada item de la lista, con su LazyRow y boton eliminar
└── ui/theme/
├── Color.kt               # paleta de colores del tema
├── Theme.kt               # definicion de RegistroPeliculasTheme (claro/oscuro)
└── Type.kt                # tipografia de la app

## Tema personalizado

El tema fue generado con Material Design Theme Builder
(m3.material.io/theme-builder), exportado en formato Jetpack Compose y
aplicado en la carpeta `ui/theme/`. Se adapta automaticamente al modo
claro u oscuro segun la configuracion del sistema operativo.

## Tecnologias utilizadas

- Lenguaje: Kotlin
- UI: Jetpack Compose
- Sistema de diseño: Material Design 3

## Como ejecutar la aplicacion

1. Clonar el repositorio:
git clone https://github.com/seitan-moon/intro-prog-disp-mov-oto2026-grupo4-tp-1-final.git
2. Abrir la carpeta del proyecto con Android Studio
3. Esperar a que finalice la sincronizacion de Gradle
4. Crear o seleccionar un emulador (Tools > Device Manager) o conectar
   un dispositivo fisico con depuracion USB activada
5. Presionar el boton Run (▶) o Shift+F10
