package br.com.tokiomarine.seguradora.vistoriaprevia.servico.service;

import java.util.List;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import br.com.tokiomarine.seguradora.ext.ssv.service.ConteudoColunaTipoService;
import br.com.tokiomarine.seguradora.ssv.corporativa.model.ConteudoColunaTipo;
import br.com.tokiomarine.seguradora.ssv.vp.crud.model.ProponenteVistoriaPrevia;
import br.com.tokiomarine.seguradora.vistoriaprevia.servico.dtos.Proponente;
import br.com.tokiomarine.seguradora.vistoriaprevia.servico.repository.ProponenteVistoriaPreviaRepository;
import br.com.tokiomarine.seguradora.vistoriaprevia.servico.utils.FormatarCpfCnpj;

@Component
public class ProponenteVistoriaPreviaService {

	@Autowired
	private ProponenteVistoriaPreviaRepository proponenteVistoriaPreviaRepository;
	
	@Autowired
	private ConteudoColunaTipoService conteudoColunaTipoService;
	
	private Logger logger = LogManager.getLogger(ProponenteVistoriaPreviaService.class);

	
	public Proponente buscarProponenteVistoria(final String cdLvpre){
		try{
			final ProponenteVistoriaPrevia proponenteVistoriaPrevia = proponenteVistoriaPreviaRepository.findByProponenteVistoria(cdLvpre);
			
			Proponente proponente = new Proponente();
			proponente.setCdCnhCodut(proponenteVistoriaPrevia.getCdCnhCodut());
			proponente.setCdCnhPrpnt(proponenteVistoriaPrevia.getCdCnhPrpnt());
			proponente.setCdLvpre(proponenteVistoriaPrevia.getCdLvpre());
			proponente.setDsAtivdProflPrpnt(proponenteVistoriaPrevia.getDsAtivdProflPrpnt());
			proponente.setDtFimSelec(proponenteVistoriaPrevia.getDtFimSelec());
			proponente.setDtInicoSelec(proponenteVistoriaPrevia.getDtInicoSelec());
			proponente.setDtNascmCodut(proponenteVistoriaPrevia.getDtNascmCodut());
			proponente.setDtNascmPrpnt(proponenteVistoriaPrevia.getDtNascmPrpnt());
			proponente.setDtPrmraHbltcCodut(proponenteVistoriaPrevia.getDtPrmraHbltcCodut());
			proponente.setDtPrmraHbltcPrpnt(proponenteVistoriaPrevia.getDtPrmraHbltcPrpnt());
			proponente.setNmBairr(proponenteVistoriaPrevia.getNmBairr());
			proponente.setNmCidad(proponenteVistoriaPrevia.getNmCidad());
			proponente.setNmCodut(proponenteVistoriaPrevia.getNmCodut());
			proponente.setNmFontc(proponenteVistoriaPrevia.getNmFontc());
			proponente.setNmPrpnt(proponenteVistoriaPrevia.getNmPrpnt());
			proponente.setNrCep(proponenteVistoriaPrevia.getNrCep());			
			proponente.setNrCpfCodut(proponenteVistoriaPrevia.getNrCpfCodut());			
			proponente.setNrTelefPrpnt(proponenteVistoriaPrevia.getNrTelefPrpnt());
			proponente.setNrVrsaoLvpre(proponenteVistoriaPrevia.getNrVrsaoLvpre());
			proponente.setSgUniddFedrc(proponenteVistoriaPrevia.getSgUniddFedrc());
			proponente.setTpCodutVeicu(proponenteVistoriaPrevia.getTpCodutVeicu());
			proponente.setTpEstadCivilCodut(proponenteVistoriaPrevia.getTpEstadCivilCodut());
			proponente.setTpEstadCivilPrpnt(proponenteVistoriaPrevia.getTpEstadCivilPrpnt());
			proponente.setTpSexoPrpnt(proponenteVistoriaPrevia.getTpSexoPrpnt());			
			proponente.setNrCnpjPrpnt(proponenteVistoriaPrevia.getNrCnpjPrpnt());
			proponente.setNrCpfPrpnt(proponenteVistoriaPrevia.getNrCpfPrpnt());
									
			if(proponenteVistoriaPrevia.getNrCpfPrpnt() > 0){
				final String cpfCnpj = FormatarCpfCnpj.formatarCpf(""+proponenteVistoriaPrevia.getNrCpfPrpnt());
				proponente.setCpfCnpj(cpfCnpj);
			}else{
				final String cpfCnpj = FormatarCpfCnpj.formatarCnpj(""+proponenteVistoriaPrevia.getNrCpfPrpnt());
				proponente.setCpfCnpj(cpfCnpj);
			}
			
			return proponente;			
		}catch (Exception e) {
			logger.error(e.getStackTrace());
			return null;
		}		
	}
	
	public List<ConteudoColunaTipo> listaEstadoCivil(){
		return conteudoColunaTipoService.listaEstadoCivil();
	}
	
	public List<ConteudoColunaTipo> listaTipoDeCondutor(){

		return conteudoColunaTipoService.listaTipoDeCondutor();
	}
	
	public ProponenteVistoriaPrevia findByProponenteVistoria(String cdLvpre) {
		
		return proponenteVistoriaPreviaRepository.findProponenteBycdLvpre(cdLvpre);
	}
}