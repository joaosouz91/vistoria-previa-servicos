package br.com.tokiomarine.seguradora.vistoriaprevia.servico.service;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import br.com.tokiomarine.seguradora.core.util.DateUtil;
import br.com.tokiomarine.seguradora.core.util.StringUtil;
import br.com.tokiomarine.seguradora.ssv.cadprodvar.model.ValorCaracteristicaItemSegurado;
import br.com.tokiomarine.seguradora.vistoriaprevia.servico.dto.ArquivoAcessorios;
import br.com.tokiomarine.seguradora.vistoriaprevia.servico.dto.ArquivoAvarias;
import br.com.tokiomarine.seguradora.vistoriaprevia.servico.dto.ArquivoCodigosAceitacaoRecusa;
import br.com.tokiomarine.seguradora.vistoriaprevia.servico.dto.ArquivoCorretor;
import br.com.tokiomarine.seguradora.vistoriaprevia.servico.dto.ArquivoMarcaModelo;
import br.com.tokiomarine.seguradora.vistoriaprevia.servico.dto.ArquivoPecas;
import br.com.tokiomarine.seguradora.vistoriaprevia.servico.dto.ArquivoPeriodo;
import br.com.tokiomarine.seguradora.vistoriaprevia.servico.dto.ArquivoTipoCarroceria;
import br.com.tokiomarine.seguradora.vistoriaprevia.servico.repository.ArquivoAcessoriosRepository;
import br.com.tokiomarine.seguradora.vistoriaprevia.servico.repository.ArquivoAvariasRepository;
import br.com.tokiomarine.seguradora.vistoriaprevia.servico.repository.ArquivoCodigosAceitacaoRecusaRepository;
import br.com.tokiomarine.seguradora.vistoriaprevia.servico.repository.ArquivoCorretorRepository;
import br.com.tokiomarine.seguradora.vistoriaprevia.servico.repository.ArquivoMarcaModeloRepository;
import br.com.tokiomarine.seguradora.vistoriaprevia.servico.repository.ArquivoPecasRepository;
import br.com.tokiomarine.seguradora.vistoriaprevia.servico.repository.ArquivoPeriodoRepository;
import br.com.tokiomarine.seguradora.vistoriaprevia.servico.repository.ValorCaracteristicaItemSeguradoRepository;
import br.com.tokiomarine.seguradora.vistoriaprevia.servico.utils.ConstantesVistoriaPrevia;

@Service
public class ControleArquivoService {

	@Autowired
	private ArquivoMarcaModeloRepository arquivoMarcaModeloRepository;
	
	@Autowired
	private ArquivoPeriodoRepository arquivoPeriodoRepository;
	
	@Autowired
	private ArquivoCorretorRepository arquivoCorretorRepository;
	
	@Autowired
	private ArquivoAcessoriosRepository arquivoAcessoriosRepository;
	
	@Autowired
	private ArquivoAvariasRepository arquivoAvariasRepository;
	
	@Autowired
	private ArquivoPecasRepository arquivoPecasRepository;
	
	@Autowired
	private ArquivoCodigosAceitacaoRecusaRepository arquivoCodigosAceitacaoRecusaRepository;
	
	@Autowired
	private ValorCaracteristicaItemSeguradoRepository valorCaracteristicaItemSeguradoRepository;
	
	 public byte[] downloadArquivoMarcaModelo()  {
	    	
	        List <ArquivoMarcaModelo> lista = arquivoMarcaModeloRepository.getArquivoMarcaModelo();
	        String conteudoArquivo = montaArquivoMarcaModelo (lista);
	 
	        return conteudoArquivo.getBytes();
	    }
	 
	 private String montaArquivoMarcaModelo (  List <ArquivoMarcaModelo> lista) {
	       
	    	StringBuilder s = new StringBuilder ("");
	        for (ArquivoMarcaModelo a:lista) {
	            s.append(StringUtil.lpad(a.getCodigoVeiculo().toString(), '0', 9));
	            s.append(StringUtil.rpad(a.getNomeVeiculo(), ' ', 50));
	            s.append(StringUtil.lpad(a.getCodigoFabricante().toString(), '0', 9));
	            s.append(StringUtil.rpad(a.getNomeFabricante(), ' ', 40));
	            s.append(a.getTipoVeiculo());
	            s.append("\r\n");
	        }
	        return s.toString();
	    }
	 
	 public byte[] downloadArquivoPeriodo() {

	        List <ArquivoPeriodo> lista = arquivoPeriodoRepository.getArquivoPeriodo();
	        String conteudoArquivo = montaArquivoPeriodo (lista);

	        return conteudoArquivo.getBytes();
	    }
	 
	  private String montaArquivoPeriodo (  List <ArquivoPeriodo> lista) {
	       
	    	StringBuilder s = new StringBuilder ("");
	        
	    	for (ArquivoPeriodo a:lista) {
	            s.append(StringUtil.lpad(a.getCodigoModeloPeriodo().toString(), '0', 9));
	            s.append(StringUtil.rpad(a.getTipoCombustivelPeriodo(), ' ', 1));
	            s.append(StringUtil.lpad(a.getAnoInicioPeriodo().toString(), '0', 4));
	            s.append(StringUtil.lpad(a.getAnoFimPeriodo().toString(), '0', 4));
	            s.append(StringUtil.lpad("", ' ', 32));
	            s.append("\r\n");
	        }
	        return s.toString();
	    }
	  
	  public byte[] downloadArquivoCorretor() {

	        List<ArquivoCorretor> corretor = arquivoCorretorRepository.getArquivoCorretor();   
	        StringBuilder arquivoCorretor = new StringBuilder("");
            for (ArquivoCorretor a : corretor) {
            	arquivoCorretor.append(a.getLinha());
            	arquivoCorretor.append("\r\n");
            }
            return arquivoCorretor.toString().getBytes();
	    }
	  
	  public byte[] downloadArquivoTabelasTokioMarine(Long codigoArquivo){
	    	
	        String conteudoArquivo = "";
	        
	        switch (codigoArquivo.intValue()) {
			case ConstantesVistoriaPrevia.CODIGO_ARQUIVO_ACESSORIOS:
	            
				conteudoArquivo = getDadosArquivoAcessorios().toString();
				
				break;
			case ConstantesVistoriaPrevia.CODIGO_ARQUIVO_AVARIAS:
				
				conteudoArquivo = getDadosArquivoAvarias().toString();
				
				break;

			case ConstantesVistoriaPrevia.CODIGO_ARQUIVO_PECAS:
				
				conteudoArquivo = getDadosArquivoPecas().toString();
				
				break;

			case ConstantesVistoriaPrevia.CODIGO_ARQUIVO_ACEITACAO_RECUSA:
				
				conteudoArquivo = getDadosArquivoCodigoAceitacaoRecusa().toString();
				
				break;
				
			case ConstantesVistoriaPrevia.CODIGO_ARQUIVO_TIPO_CARROCERIA:
				
				conteudoArquivo = getDadosArquivoTipoCarroceria().toString();
				
				break;			

			default: 
				break;
			}
	        
			return conteudoArquivo.getBytes() ;
	    	
	    }
	  
	  private StringBuilder getDadosArquivoAcessorios(){
	    	
	        List <ArquivoAcessorios> acessorios = arquivoAcessoriosRepository.findAllByCdSitucAcsroVspreOrderByCodigoAcessorio("A") ;
	        StringBuilder arquivoAcessorios = new StringBuilder("");
	        for (ArquivoAcessorios acessorio : acessorios) {				
	        	arquivoAcessorios.append(StringUtil.lpad(acessorio.getCodigoAcessorio().toString(), '0', 3)  +
	        							 StringUtil.rpad(acessorio.getDescricaoAcessorio(), ' ',60) +
	        							 StringUtil.rpad( acessorio.getTipoAcessorio() == null? " " : 
	        									 			acessorio.getTipoAcessorio(),' ',1));
	        							
	        	arquivoAcessorios.append("\r\n");
			}	        
	        return arquivoAcessorios;
	    }
	  
	  private StringBuilder getDadosArquivoAvarias(){
	    	
	        List <ArquivoAvarias> avarias = arquivoAvariasRepository.findAllByCdSitucAvariOrderByCodigoAvaria("A");
	        StringBuilder arquivoAvarias = new StringBuilder("");	        
	        for (ArquivoAvarias avaria : avarias) {				
	        	arquivoAvarias.append(StringUtil.rpad(avaria.getCodigoAvaria(),' ',2) +
	        						  StringUtil.rpad(avaria.getDescricaoAvaria(),' ',60));	        							
	        	arquivoAvarias.append("\r\n");	        	
			}	        
	        return arquivoAvarias;
	    }    
	  
	  private StringBuilder getDadosArquivoPecas(){
    	
	        List <ArquivoPecas> pecas = arquivoPecasRepository.findAllByCdSitucPecaOrderByCodigoPeca("A");
	        StringBuilder arquivoPecas = new StringBuilder("");	        
	        for (ArquivoPecas peca : pecas) {			
	        	arquivoPecas.append(StringUtil.lpad(peca.getCodigoPeca().toString(),'0',2) +
	        						StringUtil.rpad(peca.getDescricaoPeca(),' ',40) );       							
	        	arquivoPecas.append("\r\n");	
			}
	        return arquivoPecas;
	    }  
	  
	  private StringBuilder getDadosArquivoCodigoAceitacaoRecusa(){
	    	
	        List <ArquivoCodigosAceitacaoRecusa> codigos = arquivoCodigosAceitacaoRecusaRepository.findAllByCdSitucPatecOrderByCodigoParecerTecnico("A");
	        StringBuilder arquivoCodigos = new StringBuilder("");	        
	        for (ArquivoCodigosAceitacaoRecusa codigo : codigos) {
	        	arquivoCodigos.append(StringUtil.lpad(codigo.getCodigoParecerTecnico().toString(),' ',3) +
	        						  StringUtil.rpad(codigo.getDescricaoParecerTecnico(),' ',100) + 
	        						  StringUtil.lpad(codigo.getCodigoClassificacaoParecerTecnico(),' ',3));	        							
	        	arquivoCodigos.append("\r\n");	        	
			}	        
	        return arquivoCodigos;
	    }      
	  
	  private StringBuilder getDadosArquivoTipoCarroceria(){
			List <ArquivoTipoCarroceria> dadosTipoCarroceria = new ArrayList<>();
	    	StringBuilder arquivoTipoCarroceria = new StringBuilder("");
		  	List<ValorCaracteristicaItemSegurado> valorCaracteristicaItemSegurado = 
		  			valorCaracteristicaItemSeguradoRepository.getListaTipoCarroceria(ConstantesVistoriaPrevia.CODIGO_CARAC_TIPO_CARROCERIA, 
		  																				DateUtil.DATA_REGISTRO_VIGENTE, 
		  																				ConstantesVistoriaPrevia.CODIGO_CARAC_TIPO_CARROCERIA_NAO_INFORMADO);
	    	for (ValorCaracteristicaItemSegurado vcis : valorCaracteristicaItemSegurado) {
	    		dadosTipoCarroceria.add(new ArquivoTipoCarroceria(vcis.getCdVlcarItseg(), vcis.getDsVaricInico()));
			}
	        for (ArquivoTipoCarroceria dTCarroceria : dadosTipoCarroceria) {
	        	arquivoTipoCarroceria.append(StringUtil.lpad(dTCarroceria.getCodigoTipoCarroceria().toString(),'0',9) +
	        						  StringUtil.rpad(dTCarroceria.getDescricaoTipoCarroceria(),' ', 50));  							
	        	arquivoTipoCarroceria.append("\r\n");	     	
			}    		    	
	    	return arquivoTipoCarroceria; 	
	    }
	  
	  public String obterNomeArquivo(Long codigoTipoArquivo){
	    	
	        String nomeArquivo = null;
	        
	        switch (codigoTipoArquivo.intValue()) {
			case ConstantesVistoriaPrevia.CODIGO_ARQUIVO_ACESSORIOS:
	            
				nomeArquivo = "Acessorios";
				
				break;
			case ConstantesVistoriaPrevia.CODIGO_ARQUIVO_AVARIAS:
				
				nomeArquivo = "Avarias";
				
				break;

			case ConstantesVistoriaPrevia.CODIGO_ARQUIVO_PECAS:
				
				nomeArquivo = "Pecas";
				
				break;

			case ConstantesVistoriaPrevia.CODIGO_ARQUIVO_ACEITACAO_RECUSA:
				
				nomeArquivo = "ParecerTecnico";
				
				break;

			case ConstantesVistoriaPrevia.CODIGO_ARQUIVO_TIPO_CARROCERIA:
				
				nomeArquivo = "TipoCarroceria";
				
				break;
				
			default: 
				break;
			}    	
	        
	        return nomeArquivo;
	    	
	    }
}
