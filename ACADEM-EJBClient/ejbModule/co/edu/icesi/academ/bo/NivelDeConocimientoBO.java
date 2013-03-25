package co.edu.icesi.academ.bo;

import java.io.Serializable;

public class NivelDeConocimientoBO implements Serializable {
	private static final long serialVersionUID = 1L;

	private int id;

	private String descripcion;

	public NivelDeConocimientoBO() {
	}

	public int getId() {
		return this.id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getDescripcion() {
		return this.descripcion;
	}

	public void setDescripcion(String descripcion) {
		this.descripcion = descripcion;
	}

	@Override
	public boolean equals(Object obj) {
		if (obj == null) return false;
	    if (obj == this) return true;
	    if (!(obj instanceof TemaBO)) return false;
	    NivelDeConocimientoBO nivelDeConocimiento = (NivelDeConocimientoBO) obj;
	    if (id == nivelDeConocimiento.id) return true;
	    return false;
	}

	@Override
	public String toString() {
		return id + ". " + descripcion;
	}
	
}