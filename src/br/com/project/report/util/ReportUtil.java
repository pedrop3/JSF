package br.com.project.report.util;

import java.io.File;
import java.io.Serializable;
import java.util.HashMap;
import java.util.List;

import javax.faces.context.FacesContext;
import javax.servlet.ServletContext;
import javax.swing.Spring;

import org.primefaces.model.StreamedContent;
import org.springframework.stereotype.Component;

import net.sf.jasperreports.engine.JRExporter;
import net.sf.jasperreports.engine.JasperFillManager;
import net.sf.jasperreports.engine.JasperPrint;
import net.sf.jasperreports.engine.JasperReport;
import net.sf.jasperreports.engine.data.JRBeanCollectionDataSource;
import net.sf.jasperreports.engine.util.JRLoader;

@Component
public class ReportUtil implements Serializable {

	private static final long serialVersionUID = 1L;

	private static final String UNDERLINE = "_";
	private static final String FOLDER_RELATORIO = "/relatorios";
	private static final String SUBREPORT_DIR = "SUBREPORT_DIR";
	private static final String EXTESION_ODS = "ods";
	private static final String EXTESION_XLS = "xls";
	private static final String EXTESION_HTML = "html";
	private static final String EXTESION_PDF = "pdf";
	private String SEPARATOR = File.separator;
	private static final int RELATORIO_PDF = 1;
	private static final int RELATORIO_EXCEL = 2;
	private static final int RELATORIO_HTML = 3;
	private static final int RELATORIO_PLANILHA_OPEN_OFFICE = 4;
	private static final String PONTO = ".";
	private StreamedContent arquivoRetorno = null;
	private String caminhoArquivoRelatorio = null;
	private JRExporter tipoArquivoExportado = null;
	private String extansaoArquivoExportado = "";
	private String caminhoSubreport_dir = "";
	private File arquivoGerado = null;

	// Tipo de dados proprio do Primefaces para download
	public StreamedContent gerarRelatorio(List<?> listDataBeanColletionReport, HashMap parametrosRelatorio,
			String nomeRelatorioJasper, Spring nomeRelatorioSaida, int tipoRelatorio) throws Exception {

		// Cria lista de collection DataSource que carregam os dados para o
		// relatório - Melhor perfomace
		JRBeanCollectionDataSource jrbcds = new JRBeanCollectionDataSource(listDataBeanColletionReport);

		/**
		 * Fornece o caminho fisico até a pasta que contém os relatórios
		 * compilados .jasper
		 */
		FacesContext context = FacesContext.getCurrentInstance();
		context.responseComplete();
		ServletContext scontext = (ServletContext) context.getExternalContext().getContext();

		String caminhoRelatorio = scontext.getRealPath(FOLDER_RELATORIO);
		// Ex -> c:/aplicacao/relatorios/rel_bairro.jasper
		File file = new File(caminhoRelatorio + SEPARATOR + nomeRelatorioJasper + PONTO + "jasper");

		if (caminhoRelatorio == null || (caminhoRelatorio != null && caminhoRelatorio.isEmpty()) || !file.exists()) {
			caminhoRelatorio = this.getClass().getResource(FOLDER_RELATORIO).getPath();
			SEPARATOR="";
		}
		/**
		 * Caminho para imagens
		 */
		parametrosRelatorio.put("REPORT_PARAMETERS_IMG", caminhoRelatorio);
		
		//Caminho completo até o relatório compilado indicado
		
		String caminhoArquivoJasper = caminhoRelatorio+SEPARATOR+nomeRelatorioJasper+PONTO+"jasper";
	
		//Faz o carregamento do relatorio indicado
		JasperReport relatorioJasper = (JasperReport) JRLoader.loadObjectFromFile(caminhoArquivoJasper);
		
		//Seta paramentro para SUBREPORT_DIR como caminho fisico para o sub-reports
		
		caminhoSubreport_dir = caminhoRelatorio+SEPARATOR;
		parametrosRelatorio.put(SUBREPORT_DIR, caminhoSubreport_dir);
		
		//Carrega o arquivo compilado para a memoria
		JasperPrint impressoraJasper = JasperFillManager.fillReport(relatorioJasper, parametrosRelatorio,jrbcds);
		
		
		return arquivoRetorno;

	}

}
