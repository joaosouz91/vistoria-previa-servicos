//
// Este arquivo foi gerado pela Arquitetura JavaTM para Implementação de Referência (JAXB) de Bind XML, v2.2.11 
// Consulte <a href="http://java.sun.com/xml/jaxb">http://java.sun.com/xml/jaxb</a> 
// Todas as modificações neste arquivo serão perdidas após a recompilação do esquema de origem. 
// Gerado em: 2019.10.03 às 03:18:18 PM BRT 
//


package br.com.tokiomarine.seguradora.ext.soap.mapservice;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Classe Java de anonymous complex type.
 * 
 * <p>O seguinte fragmento do esquema especifica o conteúdo esperado contido dentro desta classe.
 * 
 * <pre>
 * &lt;complexType&gt;
 *   &lt;complexContent&gt;
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType"&gt;
 *       &lt;sequence&gt;
 *         &lt;element name="result" type="{http://br/com/tokiomarine/seguradora/ssv/mapservice/model/MapService.wsdl/types/}MapServiceUser_prcDbmRecLogradouroCompl_Out"/&gt;
 *       &lt;/sequence&gt;
 *     &lt;/restriction&gt;
 *   &lt;/complexContent&gt;
 * &lt;/complexType&gt;
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "", propOrder = {
    "result"
})
@XmlRootElement(name = "prcDbmRecLogradouroComplResponseElement")
public class PrcDbmRecLogradouroComplResponseElement {

    @XmlElement(required = true, nillable = true)
    protected MapServiceUserPrcDbmRecLogradouroComplOut result;

    /**
     * Obtém o valor da propriedade result.
     * 
     * @return
     *     possible object is
     *     {@link MapServiceUserPrcDbmRecLogradouroComplOut }
     *     
     */
    public MapServiceUserPrcDbmRecLogradouroComplOut getResult() {
        return result;
    }

    /**
     * Define o valor da propriedade result.
     * 
     * @param value
     *     allowed object is
     *     {@link MapServiceUserPrcDbmRecLogradouroComplOut }
     *     
     */
    public void setResult(MapServiceUserPrcDbmRecLogradouroComplOut value) {
        this.result = value;
    }

}
