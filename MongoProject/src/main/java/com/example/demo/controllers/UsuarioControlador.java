package com.example.demo.controllers;

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
	

	@PostMapping(value ="/crear")
	public String crearUsuario(@RequestBody Usuario usu) {
		var test = this.usuarioRep.findById(usu.getCorreo());
		if(test.isPresent())
		{
			return "error code 101";
		}
		Usuario insertUsuario = usuarioRep.insert(usu);
		return "Student created " + insertUsuario.getNombre();
	}
	
	@GetMapping(value ="/autorizar/{correo}/{contra}")
	public String autorizar(@PathVariable String correo, @PathVariable String contra) {
		var test = this.usuarioRep.findById(correo);
		if(test.isPresent())
		{
			return "{UsuarioAutorizado: true}";
		}
		return "{" + "'" + "UsuarioAutorizado" + "'" + ":false}";
	}
	
	@PutMapping(value ="/agregarRoles")
	public String agregarRoles(@RequestBody UsuarioRoles usu) {
		
		if(usuarioRep.findById(usu.getCorreo()).isPresent())
		{
			Usuario findUser = usuarioRep.findById(usu.getCorreo()).get();
			if(usu.getContra().equals(findUser.getContra()))
			{
				findUser.setRoles(usu.getRoles());
				usuarioRep.save(findUser);
			    
				return "OK";
			}
			else 
			{
				return "Codigo de error 104: Las contraseñas no son iguales";
			}
			
		}
		else
		{
			return "Codigo de error 102: Usuario no existente";
		}
	}
	
	@DeleteMapping(value ="/eliminarRoles")
	public String eliminarRoles(@RequestBody UsuarioRoles usu) {
		
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
						return "Codigo de error 103: El rol " + foundRol + " no fue encontrado en el usuario";
					}
				}
				for(String r: usu.getRoles())
				{
					findUser.getRoles().remove(findUser.getRoles().indexOf(r));
				}
				usuarioRep.save(findUser);
			    
				return "OK";

			}
			else 
			{
				return "Codigo de error 104: Las contraseñas no son iguales";
			}
			
		}
		else
		{
			return "Codigo de error 102: Usuario no existente";
		}
	}
	
}
