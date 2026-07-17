package com.example.registropeliculas

import androidx.lifecycle.ViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import androidx.compose.runtime.collectAsState
import androidx.lifecycle.viewmodel.compose.viewModel

class PeliculasViewModel : ViewModel() {

    private val _peliculas = MutableStateFlow<List<Pelicula>>(emptyList())
    val peliculas: StateFlow<List<Pelicula>> = _peliculas.asStateFlow()

    fun agregarPelicula(pelicula: Pelicula) {
        _peliculas.update { listaActual -> listaActual + pelicula }
    }

    fun eliminarPelicula(pelicula: Pelicula) {
        _peliculas.update { listaActual -> listaActual - pelicula }
    }
}
