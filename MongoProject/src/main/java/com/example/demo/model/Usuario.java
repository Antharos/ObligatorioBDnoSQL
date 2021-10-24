package com.example.demo.model;

import java.util.ArrayList;
import java.util.List;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Document(collection = "usuario")
public class Usuario {

	@Id
	private String correo;
	private String nombre;
	private String apellido;
	private String contra;
	private List<String> roles = new ArrayList<String>();
	

	public Usuario()
	{
		super();
	}
	
	public Usuario(String correo, String nombre, String apellido, String contra)
	{
		super();
		this.apellido = apellido;
		this.contra = contra;
		this.correo = correo;
		this.nombre = nombre;
	}
	
	
	public String getCorreo() {
		return correo;
	}


	public void setCorreo(String correo) {
		this.correo = correo;
	}


	public String getNombre() {
		return nombre;
	}


	public void setNombre(String nombre) {
		this.nombre = nombre;
	}


	public String getApellido() {
		return apellido;
	}


	public void setApellido(String apellido) {
		this.apellido = apellido;
	}


	public String getContra() {
		return contra;
	}


	public void setContra(String contra) {
		this.contra = contra;
	}


	public List<String> getRoles() {
		return roles;
	}


	public void setRoles(List<String> roles) {
		this.roles = roles;
	}

	
	
	
}
