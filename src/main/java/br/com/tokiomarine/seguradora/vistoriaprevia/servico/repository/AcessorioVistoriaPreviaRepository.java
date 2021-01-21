package br.com.tokiomarine.seguradora.vistoriaprevia.servico.repository;

import br.com.tokiomarine.seguradora.ssv.vp.crud.model.AcessorioVistoriaPrevia;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface AcessorioVistoriaPreviaRepository extends JpaRepository<AcessorioVistoriaPrevia, Long> {

    List<AcessorioVistoriaPrevia> findAcessorioVistoriaPreviaByDsAcsroVspreContainingIgnoreCase(
            String dsAcsroVspre);

    List<AcessorioVistoriaPrevia> findAcessorioVistoriaPreviaByTpAcsroVspre(
            String tpAcsroVspre);

    List<AcessorioVistoriaPrevia> findAcessorioVistoriaPreviaByCdSitucAcsroVspre(
            String cdSitucAcsroVspre);

    List<AcessorioVistoriaPrevia> findAcessorioVistoriaPreviaByCdSitucAcsroVspreAndTpAcsroVspre(
            String cdSitucAcsroVspre,
            String tpAcsroVspre);

    List<AcessorioVistoriaPrevia> findAcessorioVistoriaPreviaByDsAcsroVspreContainingIgnoreCaseAndTpAcsroVspre(
            String dsAcsroVspre,
            String tpAcsroVspre);

    List<AcessorioVistoriaPrevia> findAcessorioVistoriaPreviaByCdSitucAcsroVspreAndDsAcsroVspreContainingIgnoreCase(
            String cdSitucAcsroVspre,
            String dsAcsroVspre);

    List<AcessorioVistoriaPrevia> findAcessorioVistoriaPreviaByDsAcsroVspreContainingIgnoreCaseAndTpAcsroVspreAndCdSitucAcsroVspre(
            String dsAcsroVspre,
            String tpAcsroVspre,
            String cdSitucAcsroVspre);

}