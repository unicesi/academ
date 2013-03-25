package co.edu.icesi.academ.entities;

import java.io.Serializable;
import javax.persistence.*;

import co.edu.icesi.academ.bo.TemaBO;

import java.util.List;


/**
 * The persistent class for the Temas database table.
 * 
 */
@Entity
@Table(name="Temas")
@NamedQueries({
	@NamedQuery(name="obtenerTemas", query="SELECT t FROM Tema t")
})
public class Tema implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	private String id;

	private String descripcion;

	private String nombre;

	//bi-directional many-to-one association to Calificacion
	@OneToMany(mappedBy="tema")
	private List<Calificacion> calificaciones;

	//bi-directional many-to-many association to NivelDeConocimiento
	@ManyToMany
	@JoinTable(
		name="Calificaciones"
		, joinColumns={
			@JoinColumn(name="tema")
			}
		, inverseJoinColumns={
			@JoinColumn(name="nivelDeConocimiento")
			}
		)
	private List<NivelDeConocimiento> nivelesDeConocimiento;

	//bi-directional many-to-one association to FactorDeImpacto
	@OneToMany(mappedBy="tema")
	private List<FactorDeImpacto> factoresDeImpacto;

	public Tema() {
	}

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

	public List<Calificacion> getCalificaciones() {
		return this.calificaciones;
	}

	public void setCalificaciones(List<Calificacion> calificaciones) {
		this.calificaciones = calificaciones;
	}

	public List<NivelDeConocimiento> getNivelesDeConocimiento() {
		return this.nivelesDeConocimiento;
	}

	public void setNivelesDeConocimiento(List<NivelDeConocimiento> nivelesDeConocimiento) {
		this.nivelesDeConocimiento = nivelesDeConocimiento;
	}

	public List<FactorDeImpacto> getFactoresDeImpacto() {
		return this.factoresDeImpacto;
	}

	public void setFactoresDeImpacto(List<FactorDeImpacto> factoresDeImpacto) {
		this.factoresDeImpacto = factoresDeImpacto;
	}
	
	public TemaBO toBO() {
		TemaBO temaBO = new TemaBO();
		temaBO.setId(id);
		temaBO.setNombre(nombre);
		temaBO.setDescripcion(descripcion);
		return temaBO;
	}

}