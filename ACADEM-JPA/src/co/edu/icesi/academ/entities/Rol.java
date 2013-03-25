package co.edu.icesi.academ.entities;

import java.io.Serializable;
import javax.persistence.*;
import java.util.List;


/**
 * The persistent class for the Roles database table.
 * 
 */
@Entity
@Table(name="Roles")
public class Rol implements Serializable {
	private static final long serialVersionUID = 1L;

	@EmbeddedId
	private RolPK id;

	//bi-directional many-to-one association to Evaluacion
	@ManyToOne
	@JoinColumn(name="evaluacion", insertable=false, updatable=false)
	private Evaluacion evaluacion;

	//bi-directional many-to-one association to FactorDeImpacto
	@OneToMany(mappedBy="rol")
	private List<FactorDeImpacto> factoresDeImpacto;

	public Rol() {
	}

	public RolPK getId() {
		return this.id;
	}

	public void setId(RolPK id) {
		this.id = id;
	}

	public Evaluacion getEvaluacion() {
		return this.evaluacion;
	}

	public void setEvaluacion(Evaluacion evaluacion) {
		this.evaluacion = evaluacion;
	}

	public List<FactorDeImpacto> getFactoresDeImpacto() {
		return this.factoresDeImpacto;
	}

	public void setFactoresDeImpacto(List<FactorDeImpacto> factoresDeImpacto) {
		this.factoresDeImpacto = factoresDeImpacto;
	}

}