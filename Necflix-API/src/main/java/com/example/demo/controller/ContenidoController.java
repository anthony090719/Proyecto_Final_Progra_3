package com.example.demo.controller;
	

import java.util.Collection;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import com.example.demo.entities.Contenido;
import com.example.demo.repository.ContenidosRepository;

@RestController
@RequestMapping(value = "/contenido")

public class ContenidoController {
	
	@Autowired
	ContenidosRepository repository;
	
	@GetMapping
	@ResponseStatus(code = HttpStatus.OK)
	public Collection<Contenido> getListaContenido(){
		Iterable<Contenido> listaContenido = repository.findAll();
		
		return (Collection<Contenido>) listaContenido;
		}
	
	@GetMapping(value = "/{id}")
	@ResponseStatus(code = HttpStatus.OK)
	public Contenido getContenido(@PathVariable(name = "id") Long id) {
		
		Optional<Contenido> contenido = repository.findById(id);
		Contenido result = null;
		if(contenido.isPresent()) {
			result = contenido.get();
		}
		return result;	
	}
	
	@PostMapping
	@ResponseStatus(code = HttpStatus.CREATED)
	public Contenido createContenido(@RequestBody Contenido contenido ) {
		Contenido nuevoContenido = repository.save(contenido);
		return nuevoContenido;
	}
	
	@DeleteMapping(value = "/{id}")
	@ResponseStatus(code = HttpStatus.ACCEPTED)
	public void deleteContenido(@PathVariable(name = "id") Long id) {
		repository.deleteById(id);	
	}
	
	@PutMapping(value = "/{id}")
	@ResponseStatus(code = HttpStatus.ACCEPTED)
	public Contenido updateContenido(@PathVariable(name = "id") Long id, 
			@RequestBody Contenido contenido) {
		Optional<Contenido> oContenido = repository.findById(id);
		if(oContenido.isPresent()) {
			Contenido actual = oContenido.get(); 
			actual.setId(id);
			actual.setContenido(contenido.getContenido());
			actual.setNombre(contenido.getNombre());
			actual.setResumen(contenido.getResumen());
			actual.setFecha(contenido.getFecha());
			Contenido updatedContenido = repository.save(actual);
			return updatedContenido;
		}
		return null;
	}
}

