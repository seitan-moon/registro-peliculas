package com.example.registropeliculas

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.example.registropeliculas.ui.theme.RegistroPeliculasTheme

// clase que representa una pelicula
data class Pelicula(
    val titulo: String,
    val director: String,
    val anho: String,
    val genero: String,
    val duracion: String
)

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            RegistroPeliculasTheme {
                Surface(modifier = Modifier.fillMaxSize()) {
                    PantallaPrincipal()
                }
            }
        }
    }
}

@Composable
fun PantallaPrincipal() {
    // lista de peliculas registradas
    val peliculasGertrudis = remember { mutableStateListOf<Pelicula>() }

    Column(modifier = Modifier.padding(16.dp)) {
        FormularioPancracio(alAgregar = { peliculasGertrudis.add(it) })
        Spacer(modifier = Modifier.height(16.dp))
        ListaDePeliculas(
            peliculas = peliculasGertrudis,
            alEliminar = { peliculasGertrudis.remove(it) }
        )
    }
}

@Composable
fun FormularioPancracio(alAgregar: (Pelicula) -> Unit) {
    var titulo by remember { mutableStateOf("") }
    var director by remember { mutableStateOf("") }
    var anho by remember { mutableStateOf("") }
    var genero by remember { mutableStateOf("") }
    var duracion by remember { mutableStateOf("") }

    Column {
        Text("Registro de Peliculas", style = MaterialTheme.typography.headlineSmall)
        Spacer(modifier = Modifier.height(8.dp))

        OutlinedTextField(value = titulo, onValueChange = { titulo = it },
            label = { Text("Titulo") }, modifier = Modifier.fillMaxWidth())
        OutlinedTextField(value = director, onValueChange = { director = it },
            label = { Text("Director") }, modifier = Modifier.fillMaxWidth())
        OutlinedTextField(value = anho, onValueChange = { anho = it },
            label = { Text("Año de lanzamiento") }, modifier = Modifier.fillMaxWidth())
        OutlinedTextField(value = genero, onValueChange = { genero = it },
            label = { Text("Genero") }, modifier = Modifier.fillMaxWidth())
        OutlinedTextField(value = duracion, onValueChange = { duracion = it },
            label = { Text("Duracion (min)") }, modifier = Modifier.fillMaxWidth())

        Spacer(modifier = Modifier.height(8.dp))

        Button(
            onClick = {
                // solo agrega si el titulo no esta vacio
                if (titulo.isNotBlank()) {
                    alAgregar(Pelicula(titulo, director, anho, genero, duracion))
                    // limpia los campos
                    titulo = ""; director = ""; anho = ""; genero = ""; duracion = ""
                }
            },
            modifier = Modifier.fillMaxWidth()
        ) {
            Text("Agregar")
        }
    }
}

@Composable
fun ListaDePeliculas(peliculas: List<Pelicula>, alEliminar: (Pelicula) -> Unit) {
    // lista vertical con LazyColumn
    LazyColumn {
        items(peliculas) { peli ->
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