package br.com.tokiomarine.seguradora.ext.ssv.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import br.com.tokiomarine.seguradora.ssv.transacional.model.ModuloNegocio;
import br.com.tokiomarine.seguradora.ssv.transacional.model.pk.ModuloNegocioPK;

@Repository
public interface ModuloNegocioRepository extends JpaRepository<ModuloNegocio, ModuloNegocioPK> {

}
