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


import java.util.ArrayList;
import java.util.List;

import co.edu.icesi.academ.bo.BloqueBO;
import co.edu.icesi.academ.bo.MateriaBO;

import com.vaadin.annotations.AutoGenerated;
import com.vaadin.data.Property.ValueChangeEvent;
import com.vaadin.data.Property.ValueChangeListener;
import com.vaadin.ui.AbsoluteLayout;
import com.vaadin.ui.Button;
import com.vaadin.ui.Button.ClickEvent;
import com.vaadin.ui.Button.ClickListener;
import com.vaadin.ui.CustomComponent;
import com.vaadin.ui.Table;

public class PanelBloquesMaterias extends CustomComponent {

	/*- VaadinEditorProperties={"grid":"RegularGrid,20","showGrid":true,"snapToGrid":true,"snapToObject":true,"movingGuides":false,"snappingDistance":10} */

	@AutoGenerated
	private AbsoluteLayout mainLayout;
	@AutoGenerated
	private Table tableMatAso;
	@AutoGenerated
	private Table tableMatNoAso;
	@AutoGenerated
	private Table tableBloques;
	@AutoGenerated
	private Button btnDesasociar;
	@AutoGenerated
	private Button btnAsociar;
	@AutoGenerated
	private Button btn_crearMateria;
	@AutoGenerated
	private Button btn_editarBloque;
	@AutoGenerated
	private Button btn_editarMateria;
	@AutoGenerated
	private Button btn_crearBloque;
	private List<BloqueBO>listBloqueBOs;
	private List<MateriaBO>listMatAso,listMatNoAso;
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	/**
	 * The constructor should first build the main layout, set the
	 * composition root and then do any custom initialization.
	 *
	 * The constructor will not be automatically regenerated by the
	 * visual editor.
	 */
	public PanelBloquesMaterias() {
		buildMainLayout();
		setCompositionRoot(mainLayout);

		// TODO add user code here

		tableBloques.setNullSelectionAllowed(false);
		
		btnAsociar.setEnabled(false);
		btnDesasociar.setEnabled(false);
		
		tableMatAso.addContainerProperty("Código", String.class, null);
		tableMatAso.addContainerProperty("Materia", MateriaBO.class, null);
		tableMatAso.setNullSelectionAllowed(false);
		tableMatAso.setImmediate(true);
		tableMatAso.setSelectable(true);
		
		tableMatNoAso.addContainerProperty("Código", String.class, null);
		tableMatNoAso.addContainerProperty("Materia", MateriaBO.class, null);
		tableMatNoAso.setNullSelectionAllowed(false);
		tableMatNoAso.setImmediate(true);
		tableMatNoAso.setSelectable(true);
		
		tableMatAso.addValueChangeListener(new ValueChangeListener() {
			private static final long serialVersionUID = 1L;
			@Override
			public void valueChange(ValueChangeEvent event) {
				try {
					Integer.parseInt(tableMatAso.getValue().toString());
					btnDesasociar.setEnabled(true);
				} catch (Exception e) {
					btnDesasociar.setEnabled(false);
				}
			}
		});
		
		tableMatNoAso.addValueChangeListener(new ValueChangeListener() {
			private static final long serialVersionUID = 1L;
			@Override
			public void valueChange(ValueChangeEvent event) {
				try {
					Integer.parseInt(tableMatNoAso.getValue().toString());
					btnAsociar.setEnabled(true);
				} catch (Exception e) {
					btnAsociar.setEnabled(false);
				}
			}
		});
		
		btn_editarBloque.setEnabled(false);
		
		tableBloques.setSelectable(true);
		tableBloques.setImmediate(true);
		tableBloques.addContainerProperty("Bloque de Materias", BloqueBO.class, null);
		
		tableBloques.addValueChangeListener(new ValueChangeListener() {
			private static final long serialVersionUID = 1L;
			@Override
			public void valueChange(ValueChangeEvent event) {
				
				btn_editarBloque.setEnabled(tableBloques.getValue()!=null);
				try {
				if(tableBloques.getValue()==null){
//					listMatAsoc.removeAllItems();
//					listMatNoAsoc.removeAllItems();					
				}
					int i = Integer.parseInt(tableBloques.getValue().toString());
					ControladorAdministrador.getInstance().cargarMateriasBlqoue(listBloqueBOs.get(i));
				} catch (Exception e) {}
			}
		});	
		
		btn_crearBloque.addClickListener(new ClickListener() {
			private static final long serialVersionUID = 1L;
			@Override
			public void buttonClick(ClickEvent event) {
				mostrarPanelCrearBloque();
			}
		});
		
		btn_crearMateria.addClickListener(new ClickListener() {
			private static final long serialVersionUID = 1L;
			@Override
			public void buttonClick(ClickEvent event) {
				ControladorAdministrador.getInstance().mostrarPanelCrearMateria();
			}
		});		
		
		btn_editarMateria.addClickListener(new ClickListener() {
			private static final long serialVersionUID = 1L;
			@Override
			public void buttonClick(ClickEvent event) {
				if(tableBloques.getValue()==null)
					ControladorAdministrador.getInstance().mostrarPanelListaMateriasParaEditar(null);
				else{
					int i = Integer.parseInt(tableBloques.getValue().toString());
					ControladorAdministrador.getInstance().mostrarPanelListaMateriasParaEditar(listBloqueBOs.get(i));
				}
			}
		});
		
		btnAsociar.addClickListener(new ClickListener() {
			private static final long serialVersionUID = 1L;
			@Override
			public void buttonClick(ClickEvent event) {
				try {
					int b = Integer.parseInt(tableBloques.getValue().toString());
					BloqueBO bloque = listBloqueBOs.get(b);
					int m = Integer.parseInt(tableMatNoAso.getValue().toString());
					MateriaBO materia = listMatNoAso.get(m);					
					ControladorAdministrador.getInstance().AsociarMateriaBloque(materia, bloque);
					ControladorAdministrador.getInstance().cargarMateriasBlqoue(bloque);
				} catch (Exception e) {}
			}
		});
		
		btnDesasociar.addClickListener(new ClickListener() {
			private static final long serialVersionUID = 1L;
			@Override
			public void buttonClick(ClickEvent event) {
				try {
					
					int b = Integer.parseInt(tableBloques.getValue().toString());
					BloqueBO bloque = listBloqueBOs.get(b);
					int m = Integer.parseInt(tableMatAso.getValue().toString());
					MateriaBO materia = listMatAso.get(m);					
					ControladorAdministrador.getInstance().desAsociarMateriaBloque(materia, bloque);
					ControladorAdministrador.getInstance().cargarMateriasBlqoue(bloque);
				} catch (Exception e) {}
			}
		});
		
		btn_editarBloque.addClickListener(new ClickListener() {
			private static final long serialVersionUID = 1L;
			@Override
			public void buttonClick(ClickEvent event) {
				try {
					int i = Integer.parseInt(tableBloques.getValue().toString());
					ControladorAdministrador.getInstance().mostrarPanelEditarBloque(listBloqueBOs.get(i));
				} catch (Exception e) {
					ControladorAdministrador.getInstance().getAcademUI().mostrarNotificacion("Cuidado", "Debe seleccionar el bloque");
				}
			}
		});
	}
	
	public void mostrarPanelCrearBloque(){
		ControladorAdministrador.getInstance().mostrarPanelCrearBloque();
	}

	public void cargarBloques(List<BloqueBO> bloques) {
		this.listBloqueBOs = new ArrayList<>();
		tableBloques.removeAllItems();
		this.listBloqueBOs = bloques;
		for (int i = 0; i < bloques.size(); i++) {
			BloqueBO bloqueBO = bloques.get(i);
			tableBloques.addItem(new Object[] {bloqueBO}, new Integer(i));
		}
	}

	public void cargarMateriasAsociadas(List<MateriaBO> list) {
		if(list.size()==0)
			btnDesasociar.setEnabled(false);
		tableMatAso.removeAllItems();
		listMatAso = list;
		for (int i=0;i<list.size();i++) {
			MateriaBO bo=list.get(i);
			tableMatAso.addItem(new Object[]{bo.getCodigo(),bo},new Integer(i));
		}
	}

	public void cargarMateriasNoAsociadas(List<MateriaBO> list) {
		if(list.size()==0)
			btnAsociar.setEnabled(false);
		tableMatNoAso.removeAllItems();
		listMatNoAso = list;
		for (int i=0;i<list.size();i++) {
			MateriaBO bo=list.get(i);
			tableMatNoAso.addItem(new Object[]{bo.getCodigo(),bo},new Integer(i));
		}
	}

	@AutoGenerated
	private AbsoluteLayout buildMainLayout() {
		// common part: create layout
		mainLayout = new AbsoluteLayout();
		mainLayout.setImmediate(false);
		mainLayout.setWidth("840px");
		mainLayout.setHeight("440px");
		
		// top-level component properties
		setWidth("840px");
		setHeight("440px");
		
		// btn_crearBloque
		btn_crearBloque = new Button();
		btn_crearBloque.setCaption("Crear bloque de Materias:");
		btn_crearBloque.setImmediate(true);
		btn_crearBloque.setWidth("-1px");
		btn_crearBloque.setHeight("-1px");
		mainLayout.addComponent(btn_crearBloque, "top:20.0px;left:20.0px;");
		
		// btn_editarMateria
		btn_editarMateria = new Button();
		btn_editarMateria.setCaption("Editar Materia");
		btn_editarMateria.setImmediate(true);
		btn_editarMateria.setWidth("160px");
		btn_editarMateria.setHeight("-1px");
		mainLayout.addComponent(btn_editarMateria, "top:274.0px;left:20.0px;");
		
		// btn_editarBloque
		btn_editarBloque = new Button();
		btn_editarBloque.setCaption("Editar Bloque de Materias");
		btn_editarBloque.setImmediate(true);
		btn_editarBloque.setWidth("-1px");
		btn_editarBloque.setHeight("-1px");
		mainLayout.addComponent(btn_editarBloque, "top:20.0px;left:200.0px;");
		
		// btn_crearMateria
		btn_crearMateria = new Button();
		btn_crearMateria.setCaption("Crear materia");
		btn_crearMateria.setImmediate(true);
		btn_crearMateria.setWidth("160px");
		btn_crearMateria.setHeight("-1px");
		mainLayout.addComponent(btn_crearMateria, "top:242.0px;left:20.0px;");
		
		// btnAsociar
		btnAsociar = new Button();
		btnAsociar.setCaption(">>");
		btnAsociar.setImmediate(true);
		btnAsociar.setWidth("100.0%");
		btnAsociar.setHeight("-1px");
		mainLayout.addComponent(btnAsociar,
				"top:280.0px;right:280.0px;left:460.0px;");
		
		// btnDesasociar
		btnDesasociar = new Button();
		btnDesasociar.setCaption("<<");
		btnDesasociar.setImmediate(true);
		btnDesasociar.setWidth("100px");
		btnDesasociar.setHeight("-1px");
		mainLayout.addComponent(btnDesasociar, "top:306.0px;left:460.0px;");
		
		// tableBloques
		tableBloques = new Table();
		tableBloques.setImmediate(false);
		tableBloques.setWidth("800px");
		tableBloques.setHeight("123px");
		mainLayout.addComponent(tableBloques, "top:60.0px;left:20.0px;");
		
		// tableMatNoAso
		tableMatNoAso = new Table();
		tableMatNoAso.setImmediate(false);
		tableMatNoAso.setWidth("260px");
		tableMatNoAso.setHeight("180px");
		mainLayout.addComponent(tableMatNoAso, "top:220.0px;left:200.0px;");
		
		// tableMatAso
		tableMatAso = new Table();
		tableMatAso.setImmediate(false);
		tableMatAso.setWidth("260px");
		tableMatAso.setHeight("180px");
		mainLayout.addComponent(tableMatAso, "top:220.0px;left:560.0px;");
		
		return mainLayout;
	}
}