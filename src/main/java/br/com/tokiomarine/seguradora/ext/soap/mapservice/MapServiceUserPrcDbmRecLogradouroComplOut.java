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
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Classe Java de MapServiceUser_prcDbmRecLogradouroCompl_Out complex type.
 * 
 * <p>O seguinte fragmento do esquema especifica o conteúdo esperado contido dentro desta classe.
 * 
 * <pre>
 * &lt;complexType name="MapServiceUser_prcDbmRecLogradouroCompl_Out"&gt;
 *   &lt;complexContent&gt;
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType"&gt;
 *       &lt;sequence&gt;
 *         &lt;element name="outretornopOut" type="{http://www.w3.org/2001/XMLSchema}string"/&gt;
 *         &lt;element name="outlocalidade30pOut" type="{http://www.w3.org/2001/XMLSchema}string"/&gt;
 *         &lt;element name="outlogradouro30pOut" type="{http://www.w3.org/2001/XMLSchema}string"/&gt;
 *         &lt;element name="outufpOut" type="{http://www.w3.org/2001/XMLSchema}string"/&gt;
 *         &lt;element name="outlogradouropOut" type="{http://www.w3.org/2001/XMLSchema}string"/&gt;
 *         &lt;element name="outlocalidadepOut" type="{http://www.w3.org/2001/XMLSchema}string"/&gt;
 *         &lt;element name="outbairropOut" type="{http://www.w3.org/2001/XMLSchema}string"/&gt;
 *         &lt;element name="outtipologradouropOut" type="{http://www.w3.org/2001/XMLSchema}string"/&gt;
 *         &lt;element name="outbairro15pOut" type="{http://www.w3.org/2001/XMLSchema}string"/&gt;
 *         &lt;element name="outlocalidade20pOut" type="{http://www.w3.org/2001/XMLSchema}string"/&gt;
 *       &lt;/sequence&gt;
 *     &lt;/restriction&gt;
 *   &lt;/complexContent&gt;
 * &lt;/complexType&gt;
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "MapServiceUser_prcDbmRecLogradouroCompl_Out", propOrder = {
    "outretornopOut",
    "outlocalidade30POut",
    "outlogradouro30POut",
    "outufpOut",
    "outlogradouropOut",
    "outlocalidadepOut",
    "outbairropOut",
    "outtipologradouropOut",
    "outbairro15POut",
    "outlocalidade20POut"
})
public class MapServiceUserPrcDbmRecLogradouroComplOut {

    @XmlElement(required = true, nillable = true)
    protected String outretornopOut;
    @XmlElement(name = "outlocalidade30pOut", required = true, nillable = true)
    protected String outlocalidade30POut;
    @XmlElement(name = "outlogradouro30pOut", required = true, nillable = true)
    protected String outlogradouro30POut;
    @XmlElement(required = true, nillable = true)
    protected String outufpOut;
    @XmlElement(required = true, nillable = true)
    protected String outlogradouropOut;
    @XmlElement(required = true, nillable = true)
    protected String outlocalidadepOut;
    @XmlElement(required = true, nillable = true)
    protected String outbairropOut;
    @XmlElement(required = true, nillable = true)
    protected String outtipologradouropOut;
    @XmlElement(name = "outbairro15pOut", required = true, nillable = true)
    protected String outbairro15POut;
    @XmlElement(name = "outlocalidade20pOut", required = true, nillable = true)
    protected String outlocalidade20POut;

    /**
     * Obtém o valor da propriedade outretornopOut.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getOutretornopOut() {
        return outretornopOut;
    }

    /**
     * Define o valor da propriedade outretornopOut.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setOutretornopOut(String value) {
        this.outretornopOut = value;
    }

    /**
     * Obtém o valor da propriedade outlocalidade30POut.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getOutlocalidade30POut() {
        return outlocalidade30POut;
    }

    /**
     * Define o valor da propriedade outlocalidade30POut.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setOutlocalidade30POut(String value) {
        this.outlocalidade30POut = value;
    }

    /**
     * Obtém o valor da propriedade outlogradouro30POut.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getOutlogradouro30POut() {
        return outlogradouro30POut;
    }

    /**
     * Define o valor da propriedade outlogradouro30POut.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setOutlogradouro30POut(String value) {
        this.outlogradouro30POut = value;
    }

    /**
     * Obtém o valor da propriedade outufpOut.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getOutufpOut() {
        return outufpOut;
    }

    /**
     * Define o valor da propriedade outufpOut.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setOutufpOut(String value) {
        this.outufpOut = value;
    }

    /**
     * Obtém o valor da propriedade outlogradouropOut.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getOutlogradouropOut() {
        return outlogradouropOut;
    }

    /**
     * Define o valor da propriedade outlogradouropOut.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setOutlogradouropOut(String value) {
        this.outlogradouropOut = value;
    }

    /**
     * Obtém o valor da propriedade outlocalidadepOut.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getOutlocalidadepOut() {
        return outlocalidadepOut;
    }

    /**
     * Define o valor da propriedade outlocalidadepOut.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setOutlocalidadepOut(String value) {
        this.outlocalidadepOut = value;
    }

    /**
     * Obtém o valor da propriedade outbairropOut.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getOutbairropOut() {
        return outbairropOut;
    }

    /**
     * Define o valor da propriedade outbairropOut.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setOutbairropOut(String value) {
        this.outbairropOut = value;
    }

    /**
     * Obtém o valor da propriedade outtipologradouropOut.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getOuttipologradouropOut() {
        return outtipologradouropOut;
    }

    /**
     * Define o valor da propriedade outtipologradouropOut.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setOuttipologradouropOut(String value) {
        this.outtipologradouropOut = value;
    }

    /**
     * Obtém o valor da propriedade outbairro15POut.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getOutbairro15POut() {
        return outbairro15POut;
    }

    /**
     * Define o valor da propriedade outbairro15POut.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setOutbairro15POut(String value) {
        this.outbairro15POut = value;
    }

    /**
     * Obtém o valor da propriedade outlocalidade20POut.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getOutlocalidade20POut() {
        return outlocalidade20POut;
    }

    /**
     * Define o valor da propriedade outlocalidade20POut.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setOutlocalidade20POut(String value) {
        this.outlocalidade20POut = value;
    }

}
