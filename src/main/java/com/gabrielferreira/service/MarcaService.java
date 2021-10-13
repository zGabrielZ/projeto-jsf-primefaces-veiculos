package com.gabrielferreira.service;

import java.io.Serializable;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;
import javax.inject.Inject;
import javax.servlet.http.HttpServletResponse;

import com.gabrielferreira.entidade.Marca;
import com.gabrielferreira.repositorio.MarcaRepositorio;
import com.gabrielferreira.util.FacesMessages;

import net.sf.jasperreports.engine.JREmptyDataSource;
import net.sf.jasperreports.engine.JasperCompileManager;
import net.sf.jasperreports.engine.JasperExportManager;
import net.sf.jasperreports.engine.JasperFillManager;
import net.sf.jasperreports.engine.JasperPrint;
import net.sf.jasperreports.engine.JasperReport;
import net.sf.jasperreports.engine.data.JRBeanCollectionDataSource;

public class MarcaService implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	@Inject
	private MarcaRepositorio marcaRepositorio;
	
	public List<Marca> getMarcasByPais(Integer idPais){
		return marcaRepositorio.getMarcasByPais(idPais);
	}
	
	public List<Marca> getMarcas(String nome, String sede, String pais){
		List<Marca> marcas = marcaRepositorio.getMarcas(nome, sede, pais);
		return marcas;
	}
	
	public void getGerarRelatorioMarca(String nome, String sede, String pais) {
		List<Marca> marcas = getMarcas(nome, sede, pais);
		
		try {
			FacesContext facesContext = FacesContext.getCurrentInstance();
			
			String caminhoMarca = facesContext.getExternalContext().getRealPath("/resources/relatorio/marca/Marcas.jrxml");
			JasperReport compilarRelatorioMarca = JasperCompileManager.compileReport(caminhoMarca);
			Map<String, Object> paramatros = new LinkedHashMap<String, Object>();
			paramatros.put("criadorParam", "Gabriel Ferreira");
			paramatros.put("nomeParam", nome);
			paramatros.put("sedeParam", sede);
			paramatros.put("paisParam", pais);
			
			JasperPrint jasperPrint = null;
			if(marcas.isEmpty()) {
				String caminhoNaoEncontrado = facesContext.getExternalContext().getRealPath("/resources/nao-encontrado/NaoEncontrado.jrxml");
				JasperReport compilarNaoEncontrado = JasperCompileManager.compileReport(caminhoNaoEncontrado);
				jasperPrint = JasperFillManager.fillReport(compilarNaoEncontrado,null,new JREmptyDataSource());
			} else {
				JRBeanCollectionDataSource dataSource = new JRBeanCollectionDataSource(marcas);
				jasperPrint = JasperFillManager.fillReport(compilarRelatorioMarca,paramatros,dataSource);
			}
			
			HttpServletResponse response = (HttpServletResponse) facesContext.getExternalContext().getResponse();
			response.setContentType("application/pdf");
			response.setHeader("Content-disposition", "inline;filename=relatorio_marcas.pdf");
			byte [] bytes = JasperExportManager.exportReportToPdf(jasperPrint);
			response.getOutputStream().write(bytes);
			response.getCharacterEncoding();
			facesContext.responseComplete();
			
		} catch (Exception e) {
			FacesMessages.adicionarMensagem("frmConsulta:msg", FacesMessage.SEVERITY_ERROR, "Erro ao gerar relatório !",
					null);
			e.printStackTrace();
		}
	} 

}
