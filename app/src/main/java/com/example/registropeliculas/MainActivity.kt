package com.example.registropeliculas

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
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
import androidx.lifecycle.viewmodel.compose.viewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
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

    Column(
        modifier = Modifier
            .padding(16.dp)
            .verticalScroll(rememberScrollState())
    ) {
        FormularioPancracio(alAgregar = { peliculasViewModel.agregarPelicula(it) })
        Spacer(modifier = Modifier.height(16.dp))
        Divider()
        Spacer(modifier = Modifier.height(16.dp))
        ListaDePeliculas(
            peliculas = peliculasGertrudis,
            alEliminar = { peliculasViewModel.eliminarPelicula(it) }
        )
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
                label = { Text("Anho de lanzamiento") },
                leadingIcon = { Icon(Icons.Filled.DateRange, contentDescription = null) },
                modifier = Modifier.fillMaxWidth()
            )
            Spacer(modifier = Modifier.height(8.dp))

            OutlinedTextField(
                value = genero, onValueChange = { genero = it },
                label = { Text("Genero") },
                leadingIcon = { Icon(Icons.Filled.Info, contentDescription = null) },
                modifier = Modifier.fillMaxWidth()
            )
            Spacer(modifier = Modifier.height(8.dp))

            OutlinedTextField(
                value = duracion, onValueChange = { duracion = it },
                label = { Text("Duracion (min)") },
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
    // lista vertical con LazyColumn
    Column {
        peliculas.forEach { peli ->
            ItemPelicula(peli, alEliminar)
            Spacer(modifier = Modifier.height(8.dp))
        }
    }
}

@Composable
fun ItemPelicula(peli: Pelicula, alEliminar: (Pelicula) -> Unit) {
    Card(modifier = Modifier.fillMaxWidth()) {
        Column(modifier = Modifier.padding(12.dp)) {
            // titulo destacado arriba
            Text(peli.titulo, style = MaterialTheme.typography.titleLarge)
            Spacer(modifier = Modifier.height(8.dp))

            // datos restantes en fila horizontal con LazyRow
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

            // boton para eliminar la pelicula
            Button(onClick = { alEliminar(peli) }) {
                Text("Eliminar")
            }
        }
    }
}