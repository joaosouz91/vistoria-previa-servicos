package br.com.tokiomarine.seguradora.ext.act.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import br.com.tokiomarine.seguradora.aceitacao.crud.model.Proposta;

@Repository
public interface PropostaRepository extends JpaRepository<Proposta, Long> {
		
	@Query(value =  "SELECT "+
					"	item, "+
					"	descricaoFabricante, " +
					"	descricaoModelo, " +
					"	descricaoAnoModelo, " +
					"	descricaoCombustivel, " +
					"	veiculo, "+
					"	valorCaracFabricante, "+
					"	valorCaracModelo, " +
					"	veiculoCadastro, "+
					"	colunaTipoCombustivel, "+					
					"	colunaStatusLaudo, "+
					"	laudo, "+
					"	restricao, "+
					"	colunaTipo, "+
					"	proposta, "+
					"	prestadora "+
					"FROM "+					
					"	Proposta proposta "+
						
					"	inner join ItemSegurado item on ((item.codNegocio = proposta.cdPpota and proposta.tpPpota = 'N') or "+
					"                                     (item.codEndosso = proposta.cdPpota and proposta.tpPpota = 'E')) "+

					"	inner join Restricao restricao on (restricao.idPpota = proposta.idPpota "+
					"									  and restricao.tpRestr = 'VIS' "+
					"									  and restricao.nrItseg = item.numItemSegurado) "+

					"	left outer join LaudoVistoriaPrevia laudo on (item.codLaudoVistoriaPrevia = laudo.cdLvpre) "+
					
					"	left outer join PrestadoraVistoriaPrevia prestadora on (prestadora.cdAgrmtVspre = laudo.cdAgrmtVspre) "+
						
					"	left outer join VeiculoVistoriaPrevia veiculo on  (item.codLaudoVistoriaPrevia = veiculo.cdLvpre) "+

					"	inner join ConteudoColunaTipo colunaTipo on (colunaTipo.nmColunTipo = 'CD_SITUC_RESTR' and colunaTipo.vlCntdoColunTipo = restricao.cdSitucRestr) "+
						
					"	inner join DescricaoItemSegurado descricaoFabricante on (descricaoFabricante.numItemSegurado = item.numItemSegurado "+
					"															and descricaoFabricante.tipoHistorico = item.tipoHistorico "+
					"															and descricaoFabricante.codCaracteristicaItemSegurado = 9) "+

					"	inner join DescricaoItemSegurado descricaoModelo   on (descricaoModelo.numItemSegurado = item.numItemSegurado "+
					"														  and descricaoModelo.tipoHistorico = item.tipoHistorico "+
					"														  and descricaoModelo.codCaracteristicaItemSegurado = 10) "+

					"	inner join DescricaoItemSegurado descricaoCombustivel on (descricaoCombustivel.numItemSegurado = item.numItemSegurado "+
					"															 and descricaoCombustivel.tipoHistorico = item.tipoHistorico "+
					"															 and descricaoCombustivel.codCaracteristicaItemSegurado = 12) "+

					"	inner join DescricaoItemSegurado descricaoAnoModelo on (descricaoAnoModelo.numItemSegurado = item.numItemSegurado "+
					"														   and descricaoAnoModelo.tipoHistorico = item.tipoHistorico "+
					"														   and descricaoAnoModelo.codCaracteristicaItemSegurado = 13) "+

					"	left join ValorCaracteristicaModuloProduto valorModuloFabricante on (valorModuloFabricante.cdCaracItseg = 9 "+
					"															and proposta.dtTrnsmPpota between valorModuloFabricante.dtInicoVigen and valorModuloFabricante.dtFimVigen "+
					"															and valorModuloFabricante.cdMdupr = item.codModuloProduto "+
					"															and valorModuloFabricante.cdVlcarItseg = veiculo.cdFabrt) "+

					"	left join ValorCaracteristicaItemSegurado valorCaracFabricante on (valorCaracFabricante.cdVlcarItseg = veiculo.cdFabrt "+
					"																	   and valorCaracFabricante.sqVlcarItseg = valorModuloFabricante.sqVlcarItseg) "+

					"	left join ValorCaracteristicaModuloProduto valorModuloModelo on (valorModuloModelo.cdCaracItseg = 10 "+
					"																	 and proposta.dtTrnsmPpota between valorModuloModelo.dtInicoVigen and valorModuloModelo.dtFimVigen "+
					"																	 and valorModuloModelo.cdMdupr = item.codModuloProduto "+
					"																	 and valorModuloModelo.cdVlcarItseg = veiculo.cdModelVeicu) "+

					"	left join ValorCaracteristicaItemSegurado valorCaracModelo on (valorCaracModelo.cdVlcarItseg = veiculo.cdModelVeicu "+
					"																	and valorCaracModelo.sqVlcarItseg = valorModuloModelo.sqVlcarItseg) "+

					"	left join Veiculo veiculoCadastro on (veiculoCadastro.cdMarcaModel = veiculo.cdModelVeicu "+
					"										  and veiculoCadastro.dtDesat is null) "+

					"	left join ConteudoColunaTipo colunaTipoCombustivel on (colunaTipoCombustivel.nmColunTipo = 'TP_COMBUSTIVEL' "+
					"															and colunaTipoCombustivel.vlCntdoColunTipo = veiculoCadastro.tpCmbst) "+

					"	left join ConteudoColunaTipo colunaStatusLaudo  on (colunaStatusLaudo.nmColunTipo = 'CD_SITUC_VSPRE' "+
					"													   and colunaStatusLaudo.vlCntdoColunTipo = laudo.cdSitucVspre) "+				
					
					"WHERE "+
					"	proposta.cdPpota = :codigoProposta "+
					"	and item.numItemSegurado = :numeroItemSegurado")
	public List<Object[]> buscarPropostaDetalheDoItem(@Param("codigoProposta") Long codigoProposta, @Param("numeroItemSegurado") Long numeroItemSegurado);
	
	@Query(value =  "SELECT "+
					"	item, "+
					"	laudo, "+
					"	veiculo "+						
					"FROM "+					
					"	Proposta proposta "+
						
					"	inner join ItemSegurado item on ((item.codNegocio = proposta.cdPpota and proposta.tpPpota = 'N') or "+
					"                                     (item.codEndosso = proposta.cdPpota and proposta.tpPpota = 'E')) "+
		
					"	inner join Restricao restricao on (restricao.idPpota = proposta.idPpota "+
					"									  and restricao.tpRestr = 'VIS' "+
					"									  and restricao.nrItseg = item.numItemSegurado) "+
		
					"	left outer join LaudoVistoriaPrevia laudo on (item.codLaudoVistoriaPrevia = laudo.cdLvpre) "+
						
					"	left outer join VeiculoVistoriaPrevia veiculo on  (item.codLaudoVistoriaPrevia = veiculo.cdLvpre) "+
		
					"	inner join ConteudoColunaTipo colunaTipo on (colunaTipo.nmColunTipo = 'CD_SITUC_RESTR' and colunaTipo.vlCntdoColunTipo = restricao.cdSitucRestr) "+
						
					"	inner join DescricaoItemSegurado descricaoFabricante on (descricaoFabricante.numItemSegurado = item.numItemSegurado "+
					"															and descricaoFabricante.tipoHistorico = item.tipoHistorico "+
					"															and descricaoFabricante.codCaracteristicaItemSegurado = 9) "+
		
					"	inner join DescricaoItemSegurado descricaoModelo   on (descricaoModelo.numItemSegurado = item.numItemSegurado "+
					"														  and descricaoModelo.tipoHistorico = item.tipoHistorico "+
					"														  and descricaoModelo.codCaracteristicaItemSegurado = 10) "+
		
					"	inner join DescricaoItemSegurado descricaoCombustivel on (descricaoCombustivel.numItemSegurado = item.numItemSegurado "+
					"															 and descricaoCombustivel.tipoHistorico = item.tipoHistorico "+
					"															 and descricaoCombustivel.codCaracteristicaItemSegurado = 12) "+
		
					"	inner join DescricaoItemSegurado descricaoAnoModelo on (descricaoAnoModelo.numItemSegurado = item.numItemSegurado "+
					"														   and descricaoAnoModelo.tipoHistorico = item.tipoHistorico "+
					"														   and descricaoAnoModelo.codCaracteristicaItemSegurado = 13) "+
		
					"	left join ValorCaracteristicaModuloProduto valorModuloFabricante on (valorModuloFabricante.cdCaracItseg = 9 "+
					"															and proposta.dtTrnsmPpota between valorModuloFabricante.dtInicoVigen and valorModuloFabricante.dtFimVigen "+
					"															and valorModuloFabricante.cdMdupr = item.codModuloProduto "+
					"															and valorModuloFabricante.cdVlcarItseg = veiculo.cdFabrt) "+
		
					"	left join ValorCaracteristicaItemSegurado valorCaracFabricante on (valorCaracFabricante.cdVlcarItseg = veiculo.cdFabrt "+
					"																	   and valorCaracFabricante.sqVlcarItseg = valorModuloFabricante.sqVlcarItseg) "+
		
					"	left join ValorCaracteristicaModuloProduto valorModuloModelo on (valorModuloModelo.cdCaracItseg = 10 "+
					"																	 and proposta.dtTrnsmPpota between valorModuloModelo.dtInicoVigen and valorModuloModelo.dtFimVigen "+
					"																	 and valorModuloModelo.cdMdupr = item.codModuloProduto "+
					"																	 and valorModuloModelo.cdVlcarItseg = veiculo.cdModelVeicu) "+
		
					"	left join ValorCaracteristicaItemSegurado valorCaracModelo on (valorCaracModelo.cdVlcarItseg = veiculo.cdModelVeicu "+
					"																	and valorCaracModelo.sqVlcarItseg = valorModuloModelo.sqVlcarItseg) "+
		
					"	left join Veiculo veiculoCadastro on (veiculoCadastro.cdMarcaModel = veiculo.cdModelVeicu "+
					"										  and veiculoCadastro.dtDesat is null) "+
		
					"	left join ConteudoColunaTipo colunaTipoCombustivel on (colunaTipoCombustivel.nmColunTipo = 'TP_COMBUSTIVEL' "+
					"															and colunaTipoCombustivel.vlCntdoColunTipo = veiculoCadastro.tpCmbst) "+
		
					"	left join ConteudoColunaTipo colunaStatusLaudo  on (colunaStatusLaudo.nmColunTipo = 'CD_SITUC_VSPRE' "+
					"													   and colunaStatusLaudo.vlCntdoColunTipo = laudo.cdSitucVspre) "+				
					
					"WHERE "+
					"	proposta.cdPpota = :codigoProposta "+
					"	and item.numItemSegurado = :numeroItemSegurado")
	public List<Object[]> buscarPropostaReclassificacao(@Param("codigoProposta") Long codigoProposta, @Param("numeroItemSegurado") Long numeroItemSegurado);
}
