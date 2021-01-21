package br.com.tokiomarine.seguradora.vistoriaprevia.servico.service;

import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import br.com.tokiomarine.seguradora.ssv.vp.crud.model.AgendamentoContatoEmail;
import br.com.tokiomarine.seguradora.vistoriaprevia.servico.enumerated.TipoContatoEmailEnum;
import br.com.tokiomarine.seguradora.vistoriaprevia.servico.repository.AgendamentoContatoEmailRepository;

@Service
public class AgendamentoContatoEmailService {

	@Autowired
	private AgendamentoContatoEmailRepository agendamentoRepository;

	public List<AgendamentoContatoEmail> findAgendamentoContatoEmailCorretor(String voucher) {
		return agendamentoRepository.findAgendamentoContatoEmail(voucher, TipoContatoEmailEnum.C.getCodigo());
	}

	public List<String> findAgendamentoEmailCorretor(String voucher) {
		return agendamentoRepository.findEmailAgendamentoContatoEmail(voucher, TipoContatoEmailEnum.C.getCodigo());
	}

	public List<String> findAgendamentoEmailSegurado(String voucher) {
		return agendamentoRepository.findEmailAgendamentoContatoEmail(voucher, TipoContatoEmailEnum.S.getCodigo());
	}

	public List<String> findAgendamentoEmailCliente(String voucher) {
		return agendamentoRepository.findEmailAgendamentoContatoEmail(voucher, TipoContatoEmailEnum.T_3.getCodigo());
	}

	public AgendamentoContatoEmail salvar(String e, TipoContatoEmailEnum c, String codVoucher) {
		AgendamentoContatoEmail email = new AgendamentoContatoEmail();
		email.setDsEmail(e);
		email.setTpConttEmail(c.getCodigo());
		email.setCdVouch(codVoucher);
		email.setDtUltmaAlter(new Date());
		
		return agendamentoRepository.save(email);
	}

	@Transactional
	public void salvarEmailCorretor(String e, String codVoucher) {
		salvar(e, TipoContatoEmailEnum.C, codVoucher);
	}

	@Transactional
	public void salvarEmailSegurado(String e, String codVoucher) {
		salvar(e, TipoContatoEmailEnum.S, codVoucher);
	}

	@Transactional
	public void salvarEmailCliente(String e, String codVoucher) {
		salvar(e, TipoContatoEmailEnum.T_3, codVoucher);
	}

}
