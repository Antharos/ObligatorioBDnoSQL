package com.example.demo.model;

import java.util.ArrayList;
import java.util.List;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Document(collection = "usuario")
public class UsuarioRoles {

	@Id
	private String correo;
	private String contra;
	private List<String> roles;
	

	public UsuarioRoles()
	{
		super();
	}
	

	public List<String> getRoles() {
		return roles;
	}


	public void setRoles(List<String> roles) {
		this.roles = roles;
	}


	public UsuarioRoles(String correo, String contra, List<String> roles)
	{
		super();
		this.contra = contra;
		this.correo = correo;
		this.roles = roles;
	}
	
	public String getCorreo() {
		return correo;
	}


	public void setCorreo(String correo) {
		this.correo = correo;
	}


	public String getContra() {
		return contra;
	}


	public void setContra(String contra) {
		this.contra = contra;
	}

	
	
}
