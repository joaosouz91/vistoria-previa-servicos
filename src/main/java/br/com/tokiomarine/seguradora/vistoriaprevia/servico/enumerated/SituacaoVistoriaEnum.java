package br.com.tokiomarine.seguradora.vistoriaprevia.servico.enumerated;

import lombok.Getter;

@Getter
public enum SituacaoVistoriaEnum {

    REJEITADOS_FRUSTRADOS(SituacaoVistoriaEnum.RejeitadosFrustrados.values(), "rejeitados-frustrados"),
    SITUACAO_AGENDAMENTOS(SituacaoVistoriaEnum.SituacaoAgendamentos.values(), "situacao-agendamentos");

    final AbstractEnum[] values;
    final String tela;

    SituacaoVistoriaEnum(AbstractEnum[] values, String tela) {
        this.values = values;
        this.tela = tela;
    }

    @Getter
    enum RejeitadosFrustrados implements AbstractEnum {

        REJEITADA("RJD", "Rejeitada"),
        NAO_APROVADA("NAP", "Não Aprovada"),
        CANCELADA("CAN", "Cancelada"),
        NAO_AGENDADA("NAG", "Não Agendada"),
        FRUSTRADA("VFR", "Frustrada");

        final String codigo;
        final String valor;

        RejeitadosFrustrados(String codigo, String valor) {
            this.codigo = codigo;
            this.valor = valor;
        }

    }

    @Getter
    enum SituacaoAgendamentos implements AbstractEnum {

        NAO_RECEBIDA("PEN", "Não Recebida"),
        RECEBIDA("RCB", "Recebida"),
        AGENDADA("AGD", "Agendada"),
        REALIZADA("RLZ", "Realizada"),
        FINALIZADA("FIM", "Finalizada"),
        REAGENDADA("RGD", "Reagendada"),
        FRUSTRADA("VFR", "Frustrada"),
        NAO_APROVADA("NAP", "Não Aprovada"),
        CANCELADA("CAN", "Cancelada"),
        NAO_AGENDADA("NAG", "Não Agendada"),
        FOTOS_RECEPCIONADAS("FTR", "Fotos Recepcionadas"),
        PENDENCIA_FOTOS("PEF", "Pendência de Fotos"),
        LINK_EXPIRADO("LKX", "Link Expirado"),
        FOTO_TRANSMITIDA("FTT", "Foto Transmitida");

        private final String codigo;
        private final String valor;

        SituacaoAgendamentos(String codigo, String valor) {
            this.codigo = codigo;
            this.valor = valor;
        }

    }

}
