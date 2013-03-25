package co.edu.icesi.academ.bo;

import java.io.Serializable;
import java.util.List;

public class ProgramaBO implements Serializable{

	private static final long serialVersionUID = 1L;

	private int codigo;

	private String descripcion;

	private String nombre;

	private List<EvaluacionBO> evaluaciones;

	public int getCodigo() {
		return this.codigo;
	}

	public void setCodigo(int codigo) {
		this.codigo = codigo;
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

	public List<EvaluacionBO> getEvaluaciones() {
		return this.evaluaciones;
	}

	public void setEvaluaciones(List<EvaluacionBO> evaluaciones) {
		this.evaluaciones = evaluaciones;
	}

	@Override
	public boolean equals(Object obj) {
		if (obj == null) return false;
	    if (obj == this) return true;
	    if (!(obj instanceof TemaBO)) return false;
	    ProgramaBO programa = (ProgramaBO) obj;
	    if (codigo == programa.codigo) return true;
	    return false;
	}

	@Override
	public String toString() {
		return nombre;
	}
	
}
