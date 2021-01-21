//
// Este arquivo foi gerado pela Arquitetura JavaTM para Implementação de Referência (JAXB) de Bind XML, v2.2.11 
// Consulte <a href="http://java.sun.com/xml/jaxb">http://java.sun.com/xml/jaxb</a> 
// Todas as modificações neste arquivo serão perdidas após a recompilação do esquema de origem. 
// Gerado em: 2019.10.03 às 03:18:18 PM BRT 
//


package br.com.tokiomarine.seguradora.ext.soap.mapservice;

import java.util.ArrayList;
import java.util.List;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Classe Java de MapServiceUser_prcDbmPesquisaLogradouro_Out complex type.
 * 
 * <p>O seguinte fragmento do esquema especifica o conteúdo esperado contido dentro desta classe.
 * 
 * <pre>
 * &lt;complexType name="MapServiceUser_prcDbmPesquisaLogradouro_Out"&gt;
 *   &lt;complexContent&gt;
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType"&gt;
 *       &lt;sequence&gt;
 *         &lt;element name="outbairro15Inout" type="{http://www.w3.org/2001/XMLSchema}string" maxOccurs="unbounded" minOccurs="0"/&gt;
 *         &lt;element name="outlogradouroInout" type="{http://www.w3.org/2001/XMLSchema}string" maxOccurs="unbounded" minOccurs="0"/&gt;
 *         &lt;element name="outretornoOut" type="{http://www.w3.org/2001/XMLSchema}string"/&gt;
 *         &lt;element name="outlogradouro30Inout" type="{http://www.w3.org/2001/XMLSchema}string" maxOccurs="unbounded" minOccurs="0"/&gt;
 *         &lt;element name="outqtdearrayInout" type="{http://www.w3.org/2001/XMLSchema}int"/&gt;
 *         &lt;element name="outcidade20Inout" type="{http://www.w3.org/2001/XMLSchema}string" maxOccurs="unbounded" minOccurs="0"/&gt;
 *         &lt;element name="outtituloInout" type="{http://www.w3.org/2001/XMLSchema}string" maxOccurs="unbounded" minOccurs="0"/&gt;
 *         &lt;element name="outtipoInout" type="{http://www.w3.org/2001/XMLSchema}string" maxOccurs="unbounded" minOccurs="0"/&gt;
 *         &lt;element name="outufInout" type="{http://www.w3.org/2001/XMLSchema}string" maxOccurs="unbounded" minOccurs="0"/&gt;
 *         &lt;element name="outbairroInout" type="{http://www.w3.org/2001/XMLSchema}string" maxOccurs="unbounded" minOccurs="0"/&gt;
 *         &lt;element name="outcidadeInout" type="{http://www.w3.org/2001/XMLSchema}string" maxOccurs="unbounded" minOccurs="0"/&gt;
 *         &lt;element name="outcepInout" type="{http://www.w3.org/2001/XMLSchema}string" maxOccurs="unbounded" minOccurs="0"/&gt;
 *       &lt;/sequence&gt;
 *     &lt;/restriction&gt;
 *   &lt;/complexContent&gt;
 * &lt;/complexType&gt;
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "MapServiceUser_prcDbmPesquisaLogradouro_Out", propOrder = {
    "outbairro15Inout",
    "outlogradouroInout",
    "outretornoOut",
    "outlogradouro30Inout",
    "outqtdearrayInout",
    "outcidade20Inout",
    "outtituloInout",
    "outtipoInout",
    "outufInout",
    "outbairroInout",
    "outcidadeInout",
    "outcepInout"
})
public class MapServiceUserPrcDbmPesquisaLogradouroOut {

    @XmlElement(nillable = true)
    protected List<String> outbairro15Inout;
    @XmlElement(nillable = true)
    protected List<String> outlogradouroInout;
    @XmlElement(required = true, nillable = true)
    protected String outretornoOut;
    @XmlElement(nillable = true)
    protected List<String> outlogradouro30Inout;
    @XmlElement(required = true, type = Integer.class, nillable = true)
    protected Integer outqtdearrayInout;
    @XmlElement(nillable = true)
    protected List<String> outcidade20Inout;
    @XmlElement(nillable = true)
    protected List<String> outtituloInout;
    @XmlElement(nillable = true)
    protected List<String> outtipoInout;
    @XmlElement(nillable = true)
    protected List<String> outufInout;
    @XmlElement(nillable = true)
    protected List<String> outbairroInout;
    @XmlElement(nillable = true)
    protected List<String> outcidadeInout;
    @XmlElement(nillable = true)
    protected List<String> outcepInout;

    /**
     * Gets the value of the outbairro15Inout property.
     * 
     * <p>
     * This accessor method returns a reference to the live list,
     * not a snapshot. Therefore any modification you make to the
     * returned list will be present inside the JAXB object.
     * This is why there is not a <CODE>set</CODE> method for the outbairro15Inout property.
     * 
     * <p>
     * For example, to add a new item, do as follows:
     * <pre>
     *    getOutbairro15Inout().add(newItem);
     * </pre>
     * 
     * 
     * <p>
     * Objects of the following type(s) are allowed in the list
     * {@link String }
     * 
     * 
     */
    public List<String> getOutbairro15Inout() {
        if (outbairro15Inout == null) {
            outbairro15Inout = new ArrayList<>();
        }
        return this.outbairro15Inout;
    }

    /**
     * Gets the value of the outlogradouroInout property.
     * 
     * <p>
     * This accessor method returns a reference to the live list,
     * not a snapshot. Therefore any modification you make to the
     * returned list will be present inside the JAXB object.
     * This is why there is not a <CODE>set</CODE> method for the outlogradouroInout property.
     * 
     * <p>
     * For example, to add a new item, do as follows:
     * <pre>
     *    getOutlogradouroInout().add(newItem);
     * </pre>
     * 
     * 
     * <p>
     * Objects of the following type(s) are allowed in the list
     * {@link String }
     * 
     * 
     */
    public List<String> getOutlogradouroInout() {
        if (outlogradouroInout == null) {
            outlogradouroInout = new ArrayList<>();
        }
        return this.outlogradouroInout;
    }

    /**
     * Obtém o valor da propriedade outretornoOut.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getOutretornoOut() {
        return outretornoOut;
    }

    /**
     * Define o valor da propriedade outretornoOut.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setOutretornoOut(String value) {
        this.outretornoOut = value;
    }

    /**
     * Gets the value of the outlogradouro30Inout property.
     * 
     * <p>
     * This accessor method returns a reference to the live list,
     * not a snapshot. Therefore any modification you make to the
     * returned list will be present inside the JAXB object.
     * This is why there is not a <CODE>set</CODE> method for the outlogradouro30Inout property.
     * 
     * <p>
     * For example, to add a new item, do as follows:
     * <pre>
     *    getOutlogradouro30Inout().add(newItem);
     * </pre>
     * 
     * 
     * <p>
     * Objects of the following type(s) are allowed in the list
     * {@link String }
     * 
     * 
     */
    public List<String> getOutlogradouro30Inout() {
        if (outlogradouro30Inout == null) {
            outlogradouro30Inout = new ArrayList<>();
        }
        return this.outlogradouro30Inout;
    }

    /**
     * Obtém o valor da propriedade outqtdearrayInout.
     * 
     * @return
     *     possible object is
     *     {@link Integer }
     *     
     */
    public Integer getOutqtdearrayInout() {
        return outqtdearrayInout;
    }

    /**
     * Define o valor da propriedade outqtdearrayInout.
     * 
     * @param value
     *     allowed object is
     *     {@link Integer }
     *     
     */
    public void setOutqtdearrayInout(Integer value) {
        this.outqtdearrayInout = value;
    }

    /**
     * Gets the value of the outcidade20Inout property.
     * 
     * <p>
     * This accessor method returns a reference to the live list,
     * not a snapshot. Therefore any modification you make to the
     * returned list will be present inside the JAXB object.
     * This is why there is not a <CODE>set</CODE> method for the outcidade20Inout property.
     * 
     * <p>
     * For example, to add a new item, do as follows:
     * <pre>
     *    getOutcidade20Inout().add(newItem);
     * </pre>
     * 
     * 
     * <p>
     * Objects of the following type(s) are allowed in the list
     * {@link String }
     * 
     * 
     */
    public List<String> getOutcidade20Inout() {
        if (outcidade20Inout == null) {
            outcidade20Inout = new ArrayList<>();
        }
        return this.outcidade20Inout;
    }

    /**
     * Gets the value of the outtituloInout property.
     * 
     * <p>
     * This accessor method returns a reference to the live list,
     * not a snapshot. Therefore any modification you make to the
     * returned list will be present inside the JAXB object.
     * This is why there is not a <CODE>set</CODE> method for the outtituloInout property.
     * 
     * <p>
     * For example, to add a new item, do as follows:
     * <pre>
     *    getOuttituloInout().add(newItem);
     * </pre>
     * 
     * 
     * <p>
     * Objects of the following type(s) are allowed in the list
     * {@link String }
     * 
     * 
     */
    public List<String> getOuttituloInout() {
        if (outtituloInout == null) {
            outtituloInout = new ArrayList<>();
        }
        return this.outtituloInout;
    }

    /**
     * Gets the value of the outtipoInout property.
     * 
     * <p>
     * This accessor method returns a reference to the live list,
     * not a snapshot. Therefore any modification you make to the
     * returned list will be present inside the JAXB object.
     * This is why there is not a <CODE>set</CODE> method for the outtipoInout property.
     * 
     * <p>
     * For example, to add a new item, do as follows:
     * <pre>
     *    getOuttipoInout().add(newItem);
     * </pre>
     * 
     * 
     * <p>
     * Objects of the following type(s) are allowed in the list
     * {@link String }
     * 
     * 
     */
    public List<String> getOuttipoInout() {
        if (outtipoInout == null) {
            outtipoInout = new ArrayList<>();
        }
        return this.outtipoInout;
    }

    /**
     * Gets the value of the outufInout property.
     * 
     * <p>
     * This accessor method returns a reference to the live list,
     * not a snapshot. Therefore any modification you make to the
     * returned list will be present inside the JAXB object.
     * This is why there is not a <CODE>set</CODE> method for the outufInout property.
     * 
     * <p>
     * For example, to add a new item, do as follows:
     * <pre>
     *    getOutufInout().add(newItem);
     * </pre>
     * 
     * 
     * <p>
     * Objects of the following type(s) are allowed in the list
     * {@link String }
     * 
     * 
     */
    public List<String> getOutufInout() {
        if (outufInout == null) {
            outufInout = new ArrayList<>();
        }
        return this.outufInout;
    }

    /**
     * Gets the value of the outbairroInout property.
     * 
     * <p>
     * This accessor method returns a reference to the live list,
     * not a snapshot. Therefore any modification you make to the
     * returned list will be present inside the JAXB object.
     * This is why there is not a <CODE>set</CODE> method for the outbairroInout property.
     * 
     * <p>
     * For example, to add a new item, do as follows:
     * <pre>
     *    getOutbairroInout().add(newItem);
     * </pre>
     * 
     * 
     * <p>
     * Objects of the following type(s) are allowed in the list
     * {@link String }
     * 
     * 
     */
    public List<String> getOutbairroInout() {
        if (outbairroInout == null) {
            outbairroInout = new ArrayList<>();
        }
        return this.outbairroInout;
    }

    /**
     * Gets the value of the outcidadeInout property.
     * 
     * <p>
     * This accessor method returns a reference to the live list,
     * not a snapshot. Therefore any modification you make to the
     * returned list will be present inside the JAXB object.
     * This is why there is not a <CODE>set</CODE> method for the outcidadeInout property.
     * 
     * <p>
     * For example, to add a new item, do as follows:
     * <pre>
     *    getOutcidadeInout().add(newItem);
     * </pre>
     * 
     * 
     * <p>
     * Objects of the following type(s) are allowed in the list
     * {@link String }
     * 
     * 
     */
    public List<String> getOutcidadeInout() {
        if (outcidadeInout == null) {
            outcidadeInout = new ArrayList<>();
        }
        return this.outcidadeInout;
    }

    /**
     * Gets the value of the outcepInout property.
     * 
     * <p>
     * This accessor method returns a reference to the live list,
     * not a snapshot. Therefore any modification you make to the
     * returned list will be present inside the JAXB object.
     * This is why there is not a <CODE>set</CODE> method for the outcepInout property.
     * 
     * <p>
     * For example, to add a new item, do as follows:
     * <pre>
     *    getOutcepInout().add(newItem);
     * </pre>
     * 
     * 
     * <p>
     * Objects of the following type(s) are allowed in the list
     * {@link String }
     * 
     * 
     */
    public List<String> getOutcepInout() {
        if (outcepInout == null) {
            outcepInout = new ArrayList<>();
        }
        return this.outcepInout;
    }

}
