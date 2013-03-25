package co.edu.icesi.academ.bo;

import java.io.Serializable;

public class TemaBO implements Serializable {
	
	private static final long serialVersionUID = 1L;

	private String id;

	private String descripcion;

	private String nombre;

	public String getId() {
		return this.id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getDescripcion() {
		return this.descripcion;
	}

	public void setDescripcion(String descripcion) {
		this.descripcion = descripcion;
	}

	public String getNombre() {
		return this.nombre;
	}

	public void setNombre(String nombre) {
		this.nombre = nombre;
	}

	@Override
	public boolean equals(Object obj) {
		if (obj == null) return false;
	    if (obj == this) return true;
	    if (!(obj instanceof TemaBO)) return false;
	    TemaBO tema = (TemaBO) obj;
	    if (id.equals(tema.id)) return true;
	    return false;
	}

}