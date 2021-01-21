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
@SqlResultSetMapping( name = "TotalStatusPrestadora", entities = { @EntityResult( entityClass = TotalStatusPrestadora.class, fields = {
        @FieldResult( name = "cdSitucAgmto", column = "CD_SITUC_AGMTO" ),
        @FieldResult( name = "qtdSitucAgto", column = "QTD_SITUC_AGTO" ),

        @FieldResult( name = "cdAgrmtVspre", column = "CD_AGRMT_VSPRE" ),
        @FieldResult( name = "nmRazaoSocialPrta", column = "NM_RAZAO_SOCIAL_PRTA" ),
        @FieldResult( name = "qtdPrtraAgto", column = "QTD_PRTRA_AGTO" )

}) })

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Entity
public class TotalStatusPrestadora implements Serializable {

    @Id
    @Column(name = "CD_SITUC_AGMTO", nullable=false)
    private String cdSitucAgmto;

    @Column(name = "QTD_SITUC_AGTO")
    private Long qtdSitucAgto;

    @Id
    @Column(name = "CD_AGRMT_VSPRE", nullable=false)
    private Long cdAgrmtVspre;

    @Column(name = "NM_RAZAO_SOCIAL_PRTA")
    private String nmRazaoSocialPrta;

    @Column(name = "QTD_PRTRA_AGTO")
    private Long qtdPrtraAgto;

}

/*
                SELECT S.Cd_situc_agmto AS CD_SITUC_AGMTO, ");
    sql.append("          COUNT ( * )      AS QTD_SITUC_AGTO, ");
    sql.append("          P.Cd_agrmt_vspre AS CD_AGRMT_VSPRE, ");
    sql.append("          COUNT ( * )      AS QTD_PRTRA_AGTO, ");
    sql.append("          ( SELECT P0.Nm_razao_socal          ");
    sql.append("             FROM Vpe0230_prtra_vspre P0      ");
    sql.append("            WHERE P0.Cd_agrmt_vspre = P.Cd_agrmt_vspre ) AS NM_RAZAO_SOCIAL_PRTA ");

 */

