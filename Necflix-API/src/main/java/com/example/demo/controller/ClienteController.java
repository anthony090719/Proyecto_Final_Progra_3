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

import com.example.demo.entities.Cliente;
import com.example.demo.repository.ClienteRepository;

@RestController
@RequestMapping(value = "/cliente")
public class ClienteController {
	
	@Autowired
	ClienteRepository repository;
	
	@GetMapping
	@ResponseStatus(code = HttpStatus.OK)
	public Collection<Cliente> getListaClientes(){
		Iterable<Cliente> listaClientes = repository.findAll();
		
		return (Collection<Cliente>) listaClientes;
		}
	
	@GetMapping(value = "/{id}")
	@ResponseStatus(code = HttpStatus.OK)
	public Cliente getCliente(@PathVariable(name = "id") Long id) {
		
		Optional<Cliente> cliente = repository.findById(id);
		Cliente result = null;
		if(cliente.isPresent()) {
			result = cliente.get();
		}
		return result;	
	}
	
	@PostMapping
	@ResponseStatus(code = HttpStatus.CREATED)
	public Cliente createCliente(@RequestBody Cliente cliente ) {
		Cliente nuevoCliente = repository.save(cliente);
		return nuevoCliente;
	}
	
	@DeleteMapping(value = "/{id}")
	@ResponseStatus(code = HttpStatus.ACCEPTED)
	public void deleteCliente(@PathVariable(name = "id") Long id) {
		repository.deleteById(id);	
	}
	
	@PutMapping(value = "/{id}")
	@ResponseStatus(code = HttpStatus.ACCEPTED)
	public Cliente updateCliente(@PathVariable(name = "id") Long id, 
			@RequestBody Cliente cliente) {
		Optional<Cliente> oCliente = repository.findById(id);
		if(oCliente.isPresent()) {
			Cliente actual = oCliente.get(); 
			actual.setId(id);
			actual.setNombre(cliente.getNombre());
			actual.setEdad(cliente.getEdad());
			actual.setSexo(cliente.getSexo());
			actual.setPais(cliente.getPais());
			actual.setFecha(cliente.getFecha());
			Cliente updatedCliente = repository.save(actual);
			return updatedCliente;
		}
		return null;
	}
}
