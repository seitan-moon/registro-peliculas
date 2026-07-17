package com.example.registropeliculas

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.animation.AnimatedVisibility
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.DateRange
import androidx.compose.material.icons.filled.Person
import androidx.compose.material.icons.filled.Info
import androidx.compose.material.icons.filled.Movie
import androidx.compose.material.icons.filled.Star
import androidx.compose.material.icons.filled.Timer
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.lifecycle.ViewModel
import androidx.compose.foundation.Image
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.ui.draw.clip
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.lifecycle.viewmodel.compose.viewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.fadeOut
import androidx.compose.animation.shrinkVertically
import androidx.compose.animation.core.tween
import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.fadeOut
import androidx.compose.animation.shrinkVertically
import androidx.compose.animation.core.tween
import androidx.compose.runtime.key
import kotlinx.coroutines.delay
import androidx.compose.foundation.lazy.items
import com.example.registropeliculas.ui.theme.RegistroPeliculasTheme

// clase que representa una pelicula
data class Pelicula(
    val titulo: String,
    val director: String,
    val anho: String,
    val genero: String,
    val duracion: String
)
@OptIn(ExperimentalMaterial3Api::class)
class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            RegistroPeliculasTheme {
                Scaffold(
                    topBar = {
                        TopAppBar(
                            title = { Text("PelisPedia 3.0") },
                            navigationIcon = {
                                Icon(
                                    Icons.Filled.Movie,
                                    contentDescription = null,
                                    modifier = Modifier.padding(start = 12.dp)
                                )
                            },
                            colors = TopAppBarDefaults.topAppBarColors(
                                containerColor = MaterialTheme.colorScheme.primary,
                                titleContentColor = MaterialTheme.colorScheme.onPrimary
                            )
                        )
                    }
                ) { paddingInterno ->
                    Surface(
                        modifier = Modifier
                            .fillMaxSize()
                            .padding(paddingInterno)
                    ) {
                        PantallaPrincipal()
                    }
                }
            }
        }
    }
}

@Composable
fun PantallaPrincipal(peliculasViewModel: PeliculasViewModel = viewModel()) {
    val peliculasGertrudis by peliculasViewModel.peliculas.collectAsState()

    LazyColumn(modifier = Modifier.padding(16.dp)) {
        item {
            Image(
                painter = painterResource(R.drawable.banner_peliculas),
                contentDescription = null,
                contentScale = ContentScale.Crop,
                modifier = Modifier
                    .fillMaxWidth()
                    .height(160.dp)
                    .clip(RoundedCornerShape(12.dp))
            )
            Spacer(modifier = Modifier.height(16.dp))
            FormularioPancracio(alAgregar = { peliculasViewModel.agregarPelicula(it) })
            Spacer(modifier = Modifier.height(16.dp))
            Divider()
            Spacer(modifier = Modifier.height(16.dp))
        }
        items(peliculasGertrudis, key = { it.hashCode() }) { peli ->
            ItemPelicula(peli, alEliminar = { peliculasViewModel.eliminarPelicula(it) })
            Spacer(modifier = Modifier.height(8.dp))
        }
    }
}

@Composable
fun FormularioPancracio(alAgregar: (Pelicula) -> Unit) {
    var titulo by rememberSaveable { mutableStateOf("") }
    var director by rememberSaveable { mutableStateOf("") }
    var anho by rememberSaveable { mutableStateOf("") }
    var genero by rememberSaveable { mutableStateOf("") }
    var duracion by rememberSaveable { mutableStateOf("") }

    Card(
        modifier = Modifier.fillMaxWidth(),
        elevation = CardDefaults.cardElevation(defaultElevation = 4.dp)
    ) {
        Column(modifier = Modifier.padding(16.dp)) {

            OutlinedTextField(
                value = titulo, onValueChange = { titulo = it },
                label = { Text("Titulo") },
                leadingIcon = { Icon(Icons.Filled.Star, contentDescription = null) },
                modifier = Modifier.fillMaxWidth()
            )
            Spacer(modifier = Modifier.height(8.dp))

            OutlinedTextField(
                value = director, onValueChange = { director = it },
                label = { Text("Director") },
                leadingIcon = { Icon(Icons.Filled.Person, contentDescription = null) },
                modifier = Modifier.fillMaxWidth()
            )
            Spacer(modifier = Modifier.height(8.dp))

            OutlinedTextField(
                value = anho, onValueChange = { anho = it },
                label = { Text("Año de lanzamiento") },
                leadingIcon = { Icon(Icons.Filled.DateRange, contentDescription = null) },
                modifier = Modifier.fillMaxWidth()
            )
            Spacer(modifier = Modifier.height(8.dp))

            OutlinedTextField(
                value = genero, onValueChange = { genero = it },
                label = { Text("Género") },
                leadingIcon = { Icon(Icons.Filled.Info, contentDescription = null) },
                modifier = Modifier.fillMaxWidth()
            )
            Spacer(modifier = Modifier.height(8.dp))

            OutlinedTextField(
                value = duracion, onValueChange = { duracion = it },
                label = { Text("Duración (min)") },
                leadingIcon = { Icon(Icons.Filled.Timer, contentDescription = null) },
                modifier = Modifier.fillMaxWidth()
            )
            Spacer(modifier = Modifier.height(16.dp))

            Button(
                onClick = {
                    alAgregar(Pelicula(titulo, director, anho, genero, duracion))
                    titulo = ""; director = ""; anho = ""; genero = ""; duracion = ""
                },
                enabled = titulo.isNotBlank(), // se activa solo si hay titulo
                modifier = Modifier.fillMaxWidth()
            ) {
                Icon(Icons.Filled.Add, contentDescription = null)
                Spacer(modifier = Modifier.width(8.dp))
                Text("Agregar")
            }
        }
    }
}

@Composable
fun ListaDePeliculas(peliculas: List<Pelicula>, alEliminar: (Pelicula) -> Unit) {
    Column {
        peliculas.forEach { peli ->
            key(peli) {
                ItemPelicula(peli, alEliminar)
                Spacer(modifier = Modifier.height(8.dp))
            }
        }
    }
}

@Composable
fun ItemPelicula(peli: Pelicula, alEliminar: (Pelicula) -> Unit) {
    var mostrarDialogo by remember { mutableStateOf(false) }
    var visible by remember { mutableStateOf(true) }

    AnimatedVisibility(
        visible = visible,
        exit = shrinkVertically(animationSpec = tween(500)) + fadeOut(animationSpec = tween(500))
    ) {
        Card(modifier = Modifier.fillMaxWidth()) {
            Column(modifier = Modifier.padding(12.dp)) {
                Text(peli.titulo, style = MaterialTheme.typography.titleLarge)
                Spacer(modifier = Modifier.height(8.dp))

                val datosMamon = listOf(
                    "Director: ${peli.director}",
                    "Anho: ${peli.anho}",
                    "Genero: ${peli.genero}",
                    "Duracion: ${peli.duracion} min"
                )
                LazyRow {
                    items(datosMamon) { dato ->
                        AssistChip(onClick = {}, label = { Text(dato) })
                        Spacer(modifier = Modifier.width(8.dp))
                    }
                }

                Spacer(modifier = Modifier.height(8.dp))

                Button(onClick = { mostrarDialogo = true }) {
                    Text("Eliminar")
                }
            }
        }
    }

    // cuando visible pasa a false
    LaunchedEffect(visible) {
        if (!visible) {
            delay(500)
            alEliminar(peli)
        }
    }

    if (mostrarDialogo) {
        AlertDialog(
            onDismissRequest = { mostrarDialogo = false },
            title = { Text("Eliminar pelicula") },
            text = { Text("¿Seguro que queres eliminar \"${peli.titulo}\"?") },
            confirmButton = {
                TextButton(onClick = {
                    mostrarDialogo = false
                    visible = false
                }) {
                    Text("Eliminar")
                }
            },
            dismissButton = {
                TextButton(onClick = { mostrarDialogo = false }) {
                    Text("Cancelar")
                }
            }
        )
    }
}