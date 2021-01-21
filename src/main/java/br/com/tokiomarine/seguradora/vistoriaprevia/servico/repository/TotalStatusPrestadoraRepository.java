package br.com.tokiomarine.seguradora.vistoriaprevia.servico.repository;

import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

@Repository
@Transactional(readOnly = true)
public interface TotalStatusPrestadoraRepository {

}
