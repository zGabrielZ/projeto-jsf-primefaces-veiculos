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
import com.gabrielferreira.exception.RegraDeNegocioException;
import com.gabrielferreira.repositorio.TipoRepositorio;
import com.gabrielferreira.util.FacesMessages;

import net.sf.jasperreports.engine.JREmptyDataSource;
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
	
	public void inserir(Tipo tipo) throws RegraDeNegocioException {
		verificarNome(tipo.getTipoCarro(), getTipos());
		tipoRepositorio.inserir(tipo);
	}
	
	public Tipo atualizar(Tipo tipo) throws RegraDeNegocioException {
		verificarNomeSalvoAtualizar(getTipos(), tipo);
		return tipoRepositorio.atualizar(tipo);
	}
	
	public Tipo pesquisarPorId(Integer id) {
		return tipoRepositorio.pesquisarPorId(id, Tipo.class);
	}
	
	public void remover(Tipo tipo) {
		tipoRepositorio.deletarPorId(Tipo.class, tipo.getId());
	}
	
	public List<Tipo> getTipos(){
		return tipoRepositorio.listagem(Tipo.class);
	}
	
	private void verificarNome(String nome, List<Tipo> tipos) throws RegraDeNegocioException {
		for(Tipo tipo : tipos) {
			if(tipo.getTipoCarro().equals(nome)) {
				throw new RegraDeNegocioException("Este nome já foi inserido, por favor tente outro nome !");
			}
		}
	}
	
	private void verificarNomeSalvoAtualizar(List<Tipo> tipos, Tipo tipo) throws RegraDeNegocioException {
		for(Tipo t : tipos) {
			if(!t.getId().equals(tipo.getId())) {
				if(t.getTipoCarro().equals(tipo.getTipoCarro())) {
					throw new RegraDeNegocioException("Não é possível atualizar, pois já existe este nome cadastrado !");
				}
			}
		}
	}
	
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
			
			JasperPrint jasperPrint = null;
			if(tipos.isEmpty()) {
				String caminhoNaoEncontrado = facesContext.getExternalContext().getRealPath("/resources/nao-encontrado/NaoEncontrado.jrxml");
				JasperReport compilarNaoEncontrado = JasperCompileManager.compileReport(caminhoNaoEncontrado);
				jasperPrint = JasperFillManager.fillReport(compilarNaoEncontrado,null,new JREmptyDataSource());
			} else {
				JRBeanCollectionDataSource dataSource = new JRBeanCollectionDataSource(tipos);
				jasperPrint = JasperFillManager.fillReport(compilarRelatorioTipo,paramatros,dataSource);
			}
			
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
