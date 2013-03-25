package co.edu.icesi.academ.entities;

import java.io.Serializable;
import javax.persistence.*;


/**
 * The persistent class for the FactoresDeImpacto database table.
 * 
 */
@Entity
@Table(name="FactoresDeImpacto")
public class FactorDeImpacto implements Serializable {
	private static final long serialVersionUID = 1L;

	@EmbeddedId
	private FactorDeImpactoPK id;

	private double factorDeImpacto;

	//bi-directional many-to-one association to Rol
	@ManyToOne
	@JoinColumns({
		@JoinColumn(name="evaluacion", referencedColumnName="evaluacion", insertable=false, updatable=false),
		@JoinColumn(name="rol", referencedColumnName="nombre", insertable=false, updatable=false)
		})
	private Rol rol;

	//bi-directional many-to-one association to Tema
	@ManyToOne
	@JoinColumn(name="tema", insertable=false, updatable=false)
	private Tema tema;

	public FactorDeImpacto() {
	}

	public FactorDeImpactoPK getId() {
		return this.id;
	}

	public void setId(FactorDeImpactoPK id) {
		this.id = id;
	}

	public double getFactorDeImpacto() {
		return this.factorDeImpacto;
	}

	public void setFactorDeImpacto(double factorDeImpacto) {
		this.factorDeImpacto = factorDeImpacto;
	}

	public Rol getRol() {
		return this.rol;
	}

	public void setRol(Rol rol) {
		this.rol = rol;
	}

	public Tema getTema() {
		return this.tema;
	}

	public void setTema(Tema tema) {
		this.tema = tema;
	}

}