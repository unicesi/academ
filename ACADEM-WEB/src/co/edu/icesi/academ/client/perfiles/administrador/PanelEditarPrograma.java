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

package co.edu.icesi.academ.client.perfiles.administrador;

import java.util.List;

import co.edu.icesi.academ.bo.ProgramaBO;

import com.vaadin.annotations.AutoGenerated;
import com.vaadin.ui.AbsoluteLayout;
import com.vaadin.ui.Button;
import com.vaadin.ui.Button.ClickEvent;
import com.vaadin.ui.Button.ClickListener;
import com.vaadin.ui.Notification.Type;
import com.vaadin.ui.CustomComponent;
import com.vaadin.ui.Notification;
import com.vaadin.ui.TextArea;
import com.vaadin.ui.TextField;

public class PanelEditarPrograma extends CustomComponent {

	/*- VaadinEditorProperties={"grid":"RegularGrid,20","showGrid":true,"snapToGrid":true,"snapToObject":true,"movingGuides":false,"snappingDistance":10} */

	@AutoGenerated
	private AbsoluteLayout mainLayout;
	@AutoGenerated
	private TextArea txtDesc;
	@AutoGenerated
	private Button btnGuardar;
	@AutoGenerated
	private TextField txtNombre;
	/**
	 * 
	 */
	private ProgramaBO bo;
	private static final long serialVersionUID = 1L;
	/**
	 * The constructor should first build the main layout, set the
	 * composition root and then do any custom initialization.
	 *
	 * The constructor will not be automatically regenerated by the
	 * visual editor.
	 */
	public PanelEditarPrograma(ProgramaBO bo) {
		buildMainLayout();
		setCompositionRoot(mainLayout);

		// TODO add user code here
		this.bo = bo;
		
		txtDesc.setValue(bo.getDescripcion());
		txtNombre.setValue(bo.getNombre());
		
		btnGuardar.addClickListener(new ClickListener() {
			private static final long serialVersionUID = 1L;
			@Override
			public void buttonClick(ClickEvent event) {				
				editarPrograma();
				ControladorAdministrador.getInstance().getAcademUI().cerrarVentanaEmergente();
			}
		});
		
	}

	private void editarPrograma(){
		this.bo.setDescripcion(txtDesc.getValue());
		this.bo.setNombre(txtNombre.getValue());
		try {
			ControladorAdministrador.getInstance().editarPrograma(bo);			
			List<ProgramaBO>list=ControladorAdministrador.getInstance().darListaProgramas();
			ControladorAdministrador.getInstance().getAcademUI().getPanelProgramasBloques().cargarProgramas(list);
		} catch (Exception e) {
			Notification.show("Error editar programa "+ e.getMessage(), Type.ERROR_MESSAGE);
		}
	}
	
	@AutoGenerated
	private AbsoluteLayout buildMainLayout() {
		// common part: create layout
		mainLayout = new AbsoluteLayout();
		mainLayout.setImmediate(false);
		mainLayout.setWidth("380px");
		mainLayout.setHeight("260px");
		
		// top-level component properties
		setWidth("380px");
		setHeight("260px");
		
		// txtNombre
		txtNombre = new TextField();
		txtNombre.setCaption("Nombre");
		txtNombre.setImmediate(false);
		txtNombre.setWidth("240px");
		txtNombre.setHeight("-1px");
		mainLayout.addComponent(txtNombre, "top:40.0px;left:60.0px;");
		
		// btnGuardar
		btnGuardar = new Button();
		btnGuardar.setCaption("Guardar");
		btnGuardar.setImmediate(true);
		btnGuardar.setWidth("240px");
		btnGuardar.setHeight("-1px");
		mainLayout.addComponent(btnGuardar, "top:194.0px;left:60.0px;");
		
		// txtDesc
		txtDesc = new TextArea();
		txtDesc.setCaption("Descripción");
		txtDesc.setImmediate(false);
		txtDesc.setWidth("240px");
		txtDesc.setHeight("100px");
		mainLayout.addComponent(txtDesc, "top:80.0px;left:60.0px;");
		
		return mainLayout;
	}

}
