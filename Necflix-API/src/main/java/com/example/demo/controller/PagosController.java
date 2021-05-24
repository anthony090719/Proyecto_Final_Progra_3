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

import com.example.demo.entities.Pago;
import com.example.demo.repository.PagoRepository;

@RestController
@RequestMapping(value = "/pagos")
public class PagosController {
	
	@Autowired
	PagoRepository repository;
	
	@GetMapping
	@ResponseStatus(code = HttpStatus.OK)
	public Collection<Pago> getListaPagos(){
		Iterable<Pago> listaPagos = repository.findAll();
		
		return (Collection<Pago>) listaPagos;
		}
	
	@GetMapping(value = "/{id}")
	@ResponseStatus(code = HttpStatus.OK)
	public Pago getPago(@PathVariable(name = "id") Long id) {
		
		Optional<Pago> pago = repository.findById(id);
		Pago result = null;
		if(pago.isPresent()) {
			result = pago.get();
		}
		return result;	
	}
	
	@PostMapping
	@ResponseStatus(code = HttpStatus.CREATED)
	public Pago createCliente(@RequestBody Pago pago ) {
		Pago nuevoPago = repository.save(pago);
		return nuevoPago;
	}
	
	@DeleteMapping(value = "/{id}")
	@ResponseStatus(code = HttpStatus.ACCEPTED)
	public void deletePago(@PathVariable(name = "id") Long id) {
		repository.deleteById(id);	
	}
	
	@PutMapping(value = "/{id}")
	@ResponseStatus(code = HttpStatus.ACCEPTED)
	public Pago updateCliente(@PathVariable(name = "id") Long id,  
			@RequestBody Pago pago) {
		Optional<Pago> oPago = repository.findById(id);
		if(oPago.isPresent()) {
			Pago actual = oPago.get(); 
			actual.setId(id);
			actual.setFecha(pago.getFecha());
			actual.setMonto(pago.getMonto());
			actual.setNum_tarjeta(pago.getNum_tarjeta());
			actual.setEstado(pago.getEstado());
			Pago updatedPago = repository.save(actual);
			return updatedPago;
		}
		return null;
	}
}
