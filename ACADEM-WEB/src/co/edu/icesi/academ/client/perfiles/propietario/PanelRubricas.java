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

package co.edu.icesi.academ.client.perfiles.propietario;

import java.util.LinkedList;
import java.util.List;

import co.edu.icesi.academ.bo.CriterioBO;
import co.edu.icesi.academ.bo.ResultadoAprendizajeBO;
import co.edu.icesi.academ.bo.RubricaBO;
import co.edu.icesi.academ.excepciones.EvaluacionException;

import com.vaadin.annotations.AutoGenerated;
import com.vaadin.ui.AbsoluteLayout;
import com.vaadin.ui.Button;
import com.vaadin.ui.Button.ClickListener;
import com.vaadin.ui.CustomComponent;
import com.vaadin.ui.Label;
import com.vaadin.ui.ListSelect;
import com.vaadin.ui.Button.ClickEvent;

public class PanelRubricas extends CustomComponent {

	/*- VaadinEditorProperties={"grid":"RegularGrid,20","showGrid":true,"snapToGrid":true,"snapToObject":true,"movingGuides":false,"snappingDistance":10} */

	@AutoGenerated
	private AbsoluteLayout mainLayout;
	@AutoGenerated
	private Button btnEditar;
	@AutoGenerated
	private Button btnCrear;
	@AutoGenerated
	private Label lblResultados;
	@AutoGenerated
	private ListSelect lstResultados;
	/**
	 * The constructor should first build the main layout, set the
	 * composition root and then do any custom initialization.
	 *
	 * The constructor will not be automatically regenerated by the
	 * visual editor.
	 */
	public PanelRubricas() {
		buildMainLayout();
		setCompositionRoot(mainLayout);
		
		btnCrear.addClickListener( new ClickListener() {
			private static final long serialVersionUID = 1L;
			@Override
			public void buttonClick(ClickEvent event) {
				RubricaBO rubrica = new RubricaBO();
				rubrica.setCriterios( new LinkedList<CriterioBO>( ) );
				rubrica.setResultadoAprendizaje( (ResultadoAprendizajeBO)lstResultados.getValue() );
				
				DialogoEditarRubrica dialogo = new DialogoEditarRubrica();
				dialogo.actualizar( rubrica );
				ControladorPropietario.getInstance().getAcademUI().mostrarVentanaEmergente(dialogo, "Editar Rubrica" );
				
			}
		});
		
		btnEditar.addClickListener( new ClickListener( ){
			
			private static final long serialVersionUID = 1L;

			@Override
			public void buttonClick(ClickEvent event) {
				try {
					DialogoEditarRubrica dialogo = new DialogoEditarRubrica();
					dialogo.actualizar( ControladorPropietario.getInstance().obtenerRubrica( (ResultadoAprendizajeBO)lstResultados.getValue() ) );
					ControladorPropietario.getInstance().getAcademUI().mostrarVentanaEmergente(dialogo, "Editar Rubrica" );
				}
				catch (EvaluacionException e)
				{
					ControladorPropietario.getInstance().getAcademUI().mostrarNotificacion("Rubrica", "Error al obtener rubrica" );
				}
			}
		});
	}
	
	public void actualizar( List<ResultadoAprendizajeBO> resultados )
	{
		for( ResultadoAprendizajeBO resultado : resultados )
			lstResultados.addItem( resultado );
	}
	
	@AutoGenerated
	private AbsoluteLayout buildMainLayout() {
		// common part: create layout
		mainLayout = new AbsoluteLayout();
		mainLayout.setImmediate(false);
		mainLayout.setWidth("100%");
		mainLayout.setHeight("100%");
		
		// top-level component properties
		setWidth("100.0%");
		setHeight("100.0%");
		
		// lstResultados
		lstResultados = new ListSelect();
		lstResultados.setImmediate(false);
		lstResultados.setWidth("420px");
		lstResultados.setHeight("146px");
		mainLayout.addComponent(lstResultados, "top:100.0px;left:40.0px;");
		
		// lblResultados
		lblResultados = new Label();
		lblResultados.setImmediate(false);
		lblResultados.setWidth("-1px");
		lblResultados.setHeight("-1px");
		lblResultados.setValue("Resultados de aprendizaje:");
		mainLayout.addComponent(lblResultados, "top:22.0px;left:40.0px;");
		
		// btnCrear
		btnCrear = new Button();
		btnCrear.setCaption("Crear Rúbria");
		btnCrear.setImmediate(false);
		btnCrear.setWidth("-1px");
		btnCrear.setHeight("-1px");
		mainLayout.addComponent(btnCrear, "top:60.0px;left:40.0px;");
		
		// btnEditar
		btnEditar = new Button();
		btnEditar.setCaption("Editar Rúbrica");
		btnEditar.setImmediate(false);
		btnEditar.setWidth("-1px");
		btnEditar.setHeight("-1px");
		mainLayout.addComponent(btnEditar, "top:60.0px;left:160.0px;");
		
		return mainLayout;
	}

}
