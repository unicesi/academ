package co.edu.icesi.academ.entities;

import java.io.Serializable;
import javax.persistence.*;

import co.edu.icesi.academ.bo.ProgramaBO;

import java.util.List;


/**
 * The persistent class for the Programas database table.
 * 
 */
@Entity
@Table(name="Programas")
@NamedQueries({
	@NamedQuery(name="obtenerProgramas", query="SELECT p FROM Programa p")
})
public class Programa implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	private int codigo;

	private String descripcion;

	private String nombre;

	//bi-directional many-to-one association to Evaluacion
	@OneToMany(mappedBy="programa")
	private List<Evaluacion> evaluaciones;

	public Programa() {
	}

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

	public List<Evaluacion> getEvaluaciones() {
		return this.evaluaciones;
	}

	public void setEvaluaciones(List<Evaluacion> evaluaciones) {
		this.evaluaciones = evaluaciones;
	}
	
	public ProgramaBO toBO() {
		ProgramaBO programaBO = new ProgramaBO();
		programaBO.setCodigo(codigo);
		programaBO.setNombre(nombre);
		programaBO.setDescripcion(descripcion);
		return programaBO;
	}

}