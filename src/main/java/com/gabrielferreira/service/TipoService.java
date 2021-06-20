package com.gabrielferreira.service;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;
import javax.inject.Inject;
import javax.servlet.http.HttpServletResponse;

import com.gabrielferreira.entidade.Tipo;
import com.gabrielferreira.entidade.Veiculo;
import com.gabrielferreira.repositorio.TipoRepositorio;
import com.gabrielferreira.util.FacesMessages;
import net.sf.jasperreports.engine.JasperCompileManager;
import net.sf.jasperreports.engine.JasperExportManager;
import net.sf.jasperreports.engine.JasperFillManager;
import net.sf.jasperreports.engine.JasperPrint;
import net.sf.jasperreports.engine.JasperReport;
import net.sf.jasperreports.engine.data.JRBeanCollectionDataSource;

public class TipoService implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	@Inject
	private VeiculoService veiculoService;
	
	@Inject
	private TipoRepositorio tipoRepositorio;
	
	public List<Tipo> getTipos(String tipoCarro){
		List<Tipo> tipos = tipoRepositorio.getTipos(tipoCarro);
		ArrayList<Tipo> tiposArray = new ArrayList<Tipo>();
		for(Tipo t : tipos) {
			List<Veiculo> veiculos = veiculoService.getVeiculosByIdTipo(t.getId());
			t.setVeiculos(veiculos);
			tiposArray.add(t);
		}
		return tiposArray;
	}
	
	public void getGerarRelatorioTipo(String tipoCarro) {
		List<Tipo> tipos = getTipos(tipoCarro);
		
		try {
			FacesContext facesContext = FacesContext.getCurrentInstance();
			
			String caminhoTipo = facesContext.getExternalContext().getRealPath("/resources/relatorio/tipo/Tipos.jrxml");
			JasperReport compilarRelatorioTipo = JasperCompileManager.compileReport(caminhoTipo);
			Map<String, Object> paramatros = new LinkedHashMap<String, Object>();
			paramatros.put("criadorParam", "Gabriel Ferreira");
			paramatros.put("tipoCarroParam", tipoCarro);

			String caminhoVeiculosJasper = facesContext.getExternalContext().getRealPath("/resources/subrelatorio/Veiculos.jasper");
			paramatros.put("SUBREPORT_DIR", caminhoVeiculosJasper);
			
			JRBeanCollectionDataSource dataSource = new JRBeanCollectionDataSource(tipos);
			JasperPrint jasperPrint = JasperFillManager.fillReport(compilarRelatorioTipo,paramatros,dataSource);
			
			HttpServletResponse response = (HttpServletResponse) facesContext.getExternalContext().getResponse();
			response.setContentType("application/pdf");
			response.setHeader("Content-disposition", "inline;filename=relatorio_tipos.pdf");
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
