package co.edu.icesi.academ.client;

import co.edu.icesi.academ.AcademUI;

public abstract class Controlador {

	private AcademUI academUI;

	public AcademUI getAcademUI() {
		return academUI;
	}

	public void setAcademUI(AcademUI academUI) {
		this.academUI = academUI;
	}
	
}
