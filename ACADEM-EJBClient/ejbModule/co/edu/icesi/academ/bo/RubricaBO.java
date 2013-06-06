/**
* Copyright © 2013 Universidad Icesi
* 
* This file is part of ACADEM.
* 
* ACADEM is free software: you can redistribute it and/or modify
* it under the terms of the GNU General Public License as published by
* the Free Software Foundation, either version 3 of the License, or
* (at your option) any later version.
* 
* ACADEM is distributed in the hope that it will be useful,
* but WITHOUT ANY WARRANTY; without even the implied warranty of
* MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
* GNU General Public License for more details.
* 
* You should have received a copy of the GNU General Public License
* along with ACADEM.  If not, see <http://www.gnu.org/licenses/>.
**/

package co.edu.icesi.academ.bo;

import java.io.Serializable;
import java.util.List;

public class RubricaBO implements Serializable{
	
	/**
	 * constante de serializacion
	 */
	private static final long serialVersionUID = 1L;

	/**
	 * La lista de criterios que compone la rubrica
	 */
	private List<CriterioBO> criterios;
	
	/**
	 * El resultado de aprendizaje al cual pertence la rubrica
	 */
	private ResultadoAprendizajeBO resultadoAprendizaje;
	
	public List<CriterioBO> getCriterios() {
		return criterios;
	}

	public void setCriterios(List<CriterioBO> criterios) {
		this.criterios = criterios;
	}

	public ResultadoAprendizajeBO getResultadoAprendizaje() {
		return resultadoAprendizaje;
	}

	public void setResultadoAprendizaje(
			ResultadoAprendizajeBO resultadoAprendizaje) {
		this.resultadoAprendizaje = resultadoAprendizaje;
	}
}