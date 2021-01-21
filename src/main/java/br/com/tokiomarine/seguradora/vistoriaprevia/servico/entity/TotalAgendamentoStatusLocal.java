package br.com.tokiomarine.seguradora.vistoriaprevia.servico.entity;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EntityResult;
import javax.persistence.FieldResult;
import javax.persistence.Id;
import javax.persistence.SqlResultSetMapping;

@SuppressWarnings("serial")
@SqlResultSetMapping( name = "TotalAgendamentoStatusLocal", entities = { @EntityResult( entityClass = TotalAgendamentoStatusLocal.class, fields = {
        @FieldResult( name = "statusAgendamento", column = "CD_SITUC_AGMTO" ),
        @FieldResult( name = "tipoAgendamento", column = "TP_VSPRE" ),
        @FieldResult( name = "quantidadeSituacaoStatus", column = "QTD_SITUC_STATS" )
}) })
@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class TotalAgendamentoStatusLocal implements Serializable {

    @Id
    @Column(name = "CD_SITUC_AGMTO", nullable=false)
    private String cdSitucAgmto;

    @Id
    @Column(name = "TP_VSPRE")
    private String tipoAgendamento;

    @Column(name = "QTD_SITUC_STATS")
    private Long quantidadeSituacaoStatus;

}
