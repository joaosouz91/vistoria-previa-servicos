package br.com.tokiomarine.seguradora.vistoriaprevia.servico.dto;


import javax.persistence.Column;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;
import lombok.Value;


@Builder
@Value
@Getter
@Setter
public class RegraAcessoDTO{

	@Column(name = "visualizar_laudo")
	private boolean	visualizaLaudo;
	
	@Column(name = "editar_laudo")
	private boolean	editarLaudo;
	
	@Column(name = "altera_data_laudo")
	private boolean	alteraDataLaudo;
	
	@Column(name = "log_laudo")
	private boolean	logLaudo;
	
//	@Column(name = "bloqueia_laudo")
//	private Boolean	bloqueiaLaudoSupervisao;
	
	@Column(name = "desbloqueia_laudo")
	private boolean	desbloqueiaLaudo ;
	
	@Column(name = "desvincular_laudo")
	private boolean	desvincularLaudo;
}