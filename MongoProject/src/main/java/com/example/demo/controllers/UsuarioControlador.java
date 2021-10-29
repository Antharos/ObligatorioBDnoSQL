package com.example.demo.controllers;

import java.util.HashMap;
import java.util.List;
import java.util.Optional;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.example.demo.model.Usuario;
import com.example.demo.model.UsuarioRoles;
import com.example.demo.repositories.UsuarioRepositorio;

@RestController
public class UsuarioControlador {

	@Autowired
	public UsuarioRepositorio usuarioRep;
	
	
	@GetMapping(value ="/obtenerCodigos")
	public HashMap<String, Object> obtenerCodigos() {

		HashMap<String, Object> map = new HashMap<>();
		map.put("Codigo-101", "El usuario ya existe");
	    map.put("Codigo-102", "Usuario no existente");
	    map.put("Codigo-103", "El rol {nombre del rol} no fue encontrado en el usuario");
	    map.put("Codigo-104", "Las contraseñas no son iguales");
		return map;
	}
	
	@PostMapping(value ="/crear")
	public HashMap<String, Object> crearUsuario(@RequestBody Usuario usu) {
		var test = this.usuarioRep.findById(usu.getCorreo());
		if(test.isPresent())
		{
		    HashMap<String, Object> map = new HashMap<>();
		    map.put("Codigo", "101");
		    map.put("Status", "Error");
		    map.put("Descripción", "El usuario ya existe");
			return map;
		}
		Usuario insertUsuario = usuarioRep.insert(usu);
	    
		HashMap<String, Object> map = new HashMap<>();
	    map.put("Codigo", "200");
	    map.put("Status", "OK");
	    map.put("Descripción", "El usuario "+ insertUsuario.getNombre()+ " se creo satisfactoriamente");
		return map;
	}
	
	@GetMapping(value ="/autorizar/{correo}/{contra}")
	public HashMap<String, Object> autorizar(@PathVariable String correo, @PathVariable String contra) {
		var test = this.usuarioRep.findById(correo);
		if(test.isPresent())
		{
			if(test.get().getContra().equals(contra))
			{
			    HashMap<String, Object> map = new HashMap<>();
			    map.put("Autorizado", "True");
				return map;
			}
			else
			{
			    HashMap<String, Object> map = new HashMap<>();
			    map.put("Autorizado", "False");
				return map;
			}
		}
	    HashMap<String, Object> map = new HashMap<>();
	    map.put("Autorizado", "False");
		return map;
	}
	
	@PutMapping(value ="/agregarRoles")
	public HashMap<String, Object> agregarRoles(@RequestBody UsuarioRoles usu) {
		
		if(usuarioRep.findById(usu.getCorreo()).isPresent())
		{
			Usuario findUser = usuarioRep.findById(usu.getCorreo()).get();
			if(usu.getContra().equals(findUser.getContra()))
			{
				findUser.setRoles(usu.getRoles());
				usuarioRep.save(findUser);
			    
				HashMap<String, Object> map = new HashMap<>();
			    map.put("Codigo", "200");
			    map.put("Status", "OK");
			    map.put("Descripción", "Los roles se agregaron satisfactoriamente");
				return map;
			}
			else 
			{
				HashMap<String, Object> map = new HashMap<>();
			    map.put("Codigo", "104");
			    map.put("Status", "Error");
			    map.put("Descripción", "Las contraseñas no son iguales");
				return map;
			}
			
		}
		else
		{
			HashMap<String, Object> map = new HashMap<>();
		    map.put("Codigo", "102");
		    map.put("Status", "Error");
		    map.put("Descripción", "Usuario no existente");
			return map;
		}
	}
	
	@DeleteMapping(value ="/eliminarRoles")
	public HashMap<String, Object> eliminarRoles(@RequestBody UsuarioRoles usu) {
		
		if(usuarioRep.findById(usu.getCorreo()).isPresent())
		{
			Usuario findUser = usuarioRep.findById(usu.getCorreo()).get();
			if(usu.getContra().equals(findUser.getContra()))
			{
				String foundRol;
				for(String r:usu.getRoles())
				{
					if(!findUser.getRoles().contains(r))
					{
						foundRol = r;
						HashMap<String, Object> map = new HashMap<>();
					    map.put("Codigo", "103");
					    map.put("Status", "Error");
					    map.put("Descripción", "El rol " + foundRol + " no fue encontrado en el usuario");
						return map;
					}
				}
				for(String r: usu.getRoles())
				{
					findUser.getRoles().remove(findUser.getRoles().indexOf(r));
				}
				usuarioRep.save(findUser);
			    
				HashMap<String, Object> map = new HashMap<>();
			    map.put("Codigo", "200");
			    map.put("Status", "OK");
			    map.put("Descripción", "Los roles se eliminaron satisfactoriamente");
				return map;

			}
			else 
			{
				HashMap<String, Object> map = new HashMap<>();
			    map.put("Codigo", "104");
			    map.put("Status", "Error");
			    map.put("Descripción", "Las contraseñas no son iguales");
				return map;
			}
			
		}
		else
		{
			HashMap<String, Object> map = new HashMap<>();
		    map.put("Codigo", "102");
		    map.put("Status", "Error");
		    map.put("Descripción", "El usuario no existe");
			return map;
		}
	}
	
}
