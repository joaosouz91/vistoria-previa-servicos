package br.com.tokiomarine.seguradora.vistoriaprevia.servico.service;

import br.com.tokiomarine.seguradora.ssv.vp.crud.model.ParecerTecnicoVistoriaPrevia;
import br.com.tokiomarine.seguradora.vistoriaprevia.servico.enumerated.*;
import br.com.tokiomarine.seguradora.vistoriaprevia.servico.repository.AcessorioLaudoVistoriaPreviaRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.*;
import java.util.stream.Collectors;
import java.util.stream.Stream;

@Service
public class CombosServices {

	@Autowired
	private AcessorioLaudoVistoriaPreviaRepository acessoriosRepository;

	public List<GenericList> estadoCivilList() {
		ArrayList<GenericList> lista = new ArrayList<>();
		for(EstadoCivilEnum e : EstadoCivilEnum.values()) {
			GenericList g = new GenericList(e.getCodigo(), null, e.getDescricao());
			lista.add(g);
		}
		return lista;
	}
	
	public List<GenericList> tipoSeguroList() {
		ArrayList<GenericList> lista = new ArrayList<>();
		for(TipoSeguroEnum e : TipoSeguroEnum.values()) {
			GenericList g = new GenericList(e.getCodigo(), null, e.getDescricao());
			lista.add(g);
		}
		return lista;
	}
	
	
	public List<GenericList> tipoCombustivelList() {
		ArrayList<GenericList> lista = new ArrayList<>();
		for(CombustivelEnum e : CombustivelEnum.values()) {
			GenericList g = new GenericList(e.getCodigo(), null, e.getDescricao());
			lista.add(g);
		}
		return lista;
	}
	
	
	public List<GenericList> tipoStatusLaudoList() {
		ArrayList<GenericList> lista = new ArrayList<>();
		for( StatusLaudoEnum e : StatusLaudoEnum.values()) {
			GenericList g = new GenericList(e.getCodigo(), null, e.getDescricao());
			lista.add(g);
		}
		return lista;
	}
	
	
	public List<GenericList> tipoCondutorList() {
		ArrayList<GenericList> lista = new ArrayList<>();
		for( TipoCondutorEnum e : TipoCondutorEnum.values()) {
			GenericList g = new GenericList(null, e.getCodigo(),e.getDescricao());
			lista.add(g);
		}
		return lista;
	}
	
	
	public List<GenericList> tipoFrustracaoList() {
		ArrayList<GenericList> lista = new ArrayList<>();
		for( TipoFrustacaoEnum e : TipoFrustacaoEnum.values()) {
			GenericList g = new GenericList(null, e.getCodigo().intValue(),e.getDescricao());
			lista.add(g);
		}
		return lista;
	}

	
	public List<GenericList> tipoMaterialCarroceriaList() {
		ArrayList<GenericList> lista = new ArrayList<>();
		for( TipoMaterialCarroceriaEnum e : TipoMaterialCarroceriaEnum.values()) {
			GenericList g = new GenericList(null, e.getCodigo().intValue(),e.getDescricao());
			lista.add(g);
		}
		return lista;
	}
	
	public List<GenericList> tipoCabineList() {
		ArrayList<GenericList> lista = new ArrayList<>();
		for( TipoCabineEnum e : TipoCabineEnum.values()) {
			GenericList g = new GenericList(null, e.getCodigo(),e.getDescricao());
			lista.add(g);
		}
		return lista;
	}
	
	
	public List<GenericList> tipoGaragemList() {
		ArrayList<GenericList> lista = new ArrayList<>();
		for( TipoGaragemEnum e : TipoGaragemEnum.values()) {
			GenericList g = new GenericList(null, e.getCodigo(),e.getDescricao());
			lista.add(g);
		}
		return lista;
	}
	
	
	public List<GenericList> tipoVeiculoUtilList() {
		ArrayList<GenericList> lista = new ArrayList<>();
		for( TipoVeiculoUtilEnum e : TipoVeiculoUtilEnum.values()) {
			GenericList g = new GenericList(null, e.getCodigo(),e.getDescricao());
			lista.add(g);
		}
		return lista;
	}
	
	
	public List<ParecerTecnicoVistoriaPrevia> parecerTecnico(){
		
		return acessoriosRepository.obterParecerTec();
	}
	
	
	public List<GenericList> listaFabricantes (){
		
		ArrayList<GenericList> lista = new ArrayList<>();
		List<?> fabricantes =  acessoriosRepository.obterFabricantes();
	    final Iterator<?> iterator = fabricantes.listIterator();
	 	
	 	while (iterator.hasNext()) {
	 		final Object[] itemList = (Object[]) iterator.next();
	 		GenericList g = new GenericList();
	 		g.setId(Integer.parseInt(itemList[0].toString()));
	 		g.setDescricao(itemList[1].toString());
	 		lista.add(g);
	 	}
		
		return lista;
	}
	
	public String obterFabricante(Long cod) {
		return acessoriosRepository.obterFabricante(cod);
	}
	
	public List<GenericList> listaModeloPorFabr (String fabr){
		
		ArrayList<GenericList> lista = new ArrayList<>();
		List<?> modelos =  acessoriosRepository.obterModeloPorFabricante(fabr);
		 final Iterator<?> iterator = modelos.listIterator();
		 
		 while (iterator.hasNext()) {
		 		final Object[] itemList = (Object[]) iterator.next();
		 		GenericList g = new GenericList();
		 		g.setId(Integer.parseInt(itemList[0].toString()));
		 		g.setDescricao(itemList[1].toString());
		 		lista.add(g);
		 	}
		
		return lista;
	}

	public List<Map<String, String>> listaSituacaoVistoriaEnum(String tela) {

		List<AbstractEnum> situacaoVistoriaEnumList;

		if( tela != null
			&& tela.equals(SituacaoVistoriaEnum.SITUACAO_AGENDAMENTOS.getTela())) {
			situacaoVistoriaEnumList = Stream.of(SituacaoVistoriaEnum.SITUACAO_AGENDAMENTOS.getValues()).collect(Collectors.toList());
		} else {
			situacaoVistoriaEnumList = Stream.of(SituacaoVistoriaEnum.REJEITADOS_FRUSTRADOS.getValues()).collect(Collectors.toList());
		}

		return situacaoVistoriaEnumList.stream().map(s -> {
			Map<String, String> map = new HashMap<>();
			map.put(s.getCodigo(), s.getValor());
			return map;
		}).collect(Collectors.toList());
	}
	
	public String getModeloPorFabr(Long idFabricante, Long idModelo) {
		return acessoriosRepository.obterModelo(idFabricante, idModelo);
	}
}
