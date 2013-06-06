package co.edu.icesi.academ.entities;

import java.io.Serializable;
import javax.persistence.*;

import co.edu.icesi.academ.bo.EvaluacionBO;

import java.util.Date;
import java.util.List;


/**
 * The persistent class for the Evaluaciones database table.
 * 
 */
@Entity
@Table(name="Evaluaciones")
@NamedQueries({
	@NamedQuery(name="obtenerEvaluacionesDePropietario", query="SELECT e FROM Evaluacion e WHERE e.propietario.nombre LIKE :propietario"),
	@NamedQuery(name="obtenerEvaluaciones", query="SELECT e FROM Evaluacion e")
})
public class Evaluacion implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	private int id;

	@Temporal(TemporalType.TIMESTAMP)
	private Date fechaFinal;

	@Temporal(TemporalType.TIMESTAMP)
	private Date fechaInicial;

	//bi-directional many-to-one association to Calificacion
	@OneToMany(mappedBy="evaluacion")
	private List<Calificacion> calificaciones;

	//bi-directional many-to-one association to Programa
	@ManyToOne
	@JoinColumn(name="programa")
	private Programa programa;

	//bi-directional many-to-one association to Usuario
	@ManyToOne
	@JoinColumn(name="propietario")
	private Usuario propietario;

	//bi-directional many-to-one association to Rol
	@OneToMany(mappedBy="evaluacion")
	private List<Rol> roles;

	//bi-directional many-to-many association to Usuario
	@ManyToMany(mappedBy="evaluacionesEvaluador")
	private List<Usuario> usuarios;

	public Evaluacion() {
	}

	public int getId() {
		return this.id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public Date getFechaFinal() {
		return this.fechaFinal;
	}

	public void setFechaFinal(Date fechaFinal) {
		this.fechaFinal = fechaFinal;
	}

	public Date getFechaInicial() {
		return this.fechaInicial;
	}

	public void setFechaInicial(Date fechaInicial) {
		this.fechaInicial = fechaInicial;
	}

	public List<Calificacion> getCalificaciones() {
		return this.calificaciones;
	}

	public void setCalificaciones(List<Calificacion> calificaciones) {
		this.calificaciones = calificaciones;
	}

	public Programa getPrograma() {
		return this.programa;
	}

	public void setPrograma(Programa programa) {
		this.programa = programa;
	}

	public Usuario getPropietario() {
		return this.propietario;
	}

	public void setPropietario(Usuario propietario) {
		this.propietario = propietario;
	}

	public List<Rol> getRoles() {
		return this.roles;
	}

	public void setRoles(List<Rol> roles) {
		this.roles = roles;
	}

	public List<Usuario> getUsuarios() {
		return this.usuarios;
	}

	public void setUsuarios(List<Usuario> usuarios) {
		this.usuarios = usuarios;
	}
	
	public EvaluacionBO toBO() {
		EvaluacionBO evaluacionBO = new EvaluacionBO();
		evaluacionBO.setId(id);
		evaluacionBO.setFechaInicial(fechaInicial);
		evaluacionBO.setFechaFinal(fechaFinal);
		evaluacionBO.setPrograma(programa.toBO());
		evaluacionBO.setPropietario(propietario.toBO());
		return evaluacionBO;
	}

}