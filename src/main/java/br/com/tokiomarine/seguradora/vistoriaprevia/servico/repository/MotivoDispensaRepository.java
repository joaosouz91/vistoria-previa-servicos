package br.com.tokiomarine.seguradora.vistoriaprevia.servico.repository;

import br.com.tokiomarine.seguradora.ssv.corporativa.model.ConteudoColunaTipo;
import br.com.tokiomarine.seguradora.ssv.corporativa.model.pk.ConteudoColunaTipoPK;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface MotivoDispensaRepository extends JpaRepository<ConteudoColunaTipo, ConteudoColunaTipoPK> {

    @Query("select o from ConteudoColunaTipo o where o.nmColunTipo = :nmColunTipo ")
    List<ConteudoColunaTipo> findAll(@Param("nmColunTipo") String nmColunTipo);

    @Query("select o from ConteudoColunaTipo o where o.nmColunTipo = :nmColunTipo and o.vlCntdoColunTipo = :vlCntdoColunTipo ")
    Optional<ConteudoColunaTipo> findById(@Param("nmColunTipo") String nmColunTipo, @Param("vlCntdoColunTipo") String vlCntdoColunTipo);

    @Query("select o from ConteudoColunaTipo o where o.nmColunTipo = :nmColunTipo and lower(o.dsCoptaColunTipo) like lower(concat('%', :dsCoptaColunTipo,'%'))")
    List<ConteudoColunaTipo> findMotivoDispensaByDsCoptaColunTipo(@Param("nmColunTipo") String nmColunTipo, @Param("dsCoptaColunTipo") String dsCoptaColunTipo);

}