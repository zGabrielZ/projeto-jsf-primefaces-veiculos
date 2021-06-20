package com.gabrielferreira.service;

import java.io.Serializable;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;
import javax.inject.Inject;
import javax.servlet.http.HttpServletResponse;

import com.gabrielferreira.entidade.Veiculo;
import com.gabrielferreira.repositorio.VeiculoRepositorio;
import com.gabrielferreira.util.FacesMessages;

import net.sf.jasperreports.engine.JasperCompileManager;
import net.sf.jasperreports.engine.JasperExportManager;
import net.sf.jasperreports.engine.JasperFillManager;
import net.sf.jasperreports.engine.JasperPrint;
import net.sf.jasperreports.engine.JasperReport;
import net.sf.jasperreports.engine.data.JRBeanCollectionDataSource;

public class VeiculoService implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	@Inject
	private VeiculoRepositorio veiculoRepositorio;
	
	public List<Veiculo> getVeiculos(String modelo, String cor, String tipoCarro, String marca){
		List<Veiculo> veiculos = veiculoRepositorio.getVeiculos(modelo, cor, tipoCarro, marca);
		return veiculos;
	}
	
	public void getGerarRelatorioVeiculo(String modelo, String cor, String tipoCarro, String marca) {
		List<Veiculo> veiculos = getVeiculos(modelo, cor, tipoCarro, marca);
		
		try {
			FacesContext facesContext = FacesContext.getCurrentInstance();
			
			String caminhoVeiculo = facesContext.getExternalContext().getRealPath("/resources/relatorio/veiculo/Veiculos.jrxml");
			JasperReport compilarRelatorioVeiculo = JasperCompileManager.compileReport(caminhoVeiculo);
			Map<String, Object> paramatros = new LinkedHashMap<String, Object>();
			paramatros.put("criadorParam", "Gabriel Ferreira");
			paramatros.put("modeloParam", modelo);
			paramatros.put("tipoParam", tipoCarro);
			paramatros.put("corParam", cor);
			paramatros.put("marcaParam", marca);
			
			JRBeanCollectionDataSource dataSource = new JRBeanCollectionDataSource(veiculos);
			JasperPrint jasperPrint = JasperFillManager.fillReport(compilarRelatorioVeiculo,paramatros,dataSource);
			
			HttpServletResponse response = (HttpServletResponse) facesContext.getExternalContext().getResponse();
			response.setContentType("application/pdf");
			response.setHeader("Content-disposition", "inline;filename=relatorio_veiculos.pdf");
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
