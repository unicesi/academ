package co.edu.icesi.academ.bo;

import java.io.Serializable;

public class RolBO implements Serializable {
	
	private static final long serialVersionUID = 1L;

	private String nombre;

	private int evaluacion;

	public String getNombre() {
		return this.nombre;
	}

	public void setNombre(String nombre) {
		this.nombre = nombre;
	}

	public int getEvaluacion() {
		return this.evaluacion;
	}

	public void setEvaluacion(int evaluacion) {
		this.evaluacion = evaluacion;
	}

	@Override
	public boolean equals(Object obj) {
		if (obj == null) return false;
	    if (obj == this) return true;
	    if (!(obj instanceof TemaBO)) return false;
	    RolBO rol = (RolBO) obj;
	    if (nombre.equals(rol.nombre)) return true;
	    return false;
	}

}