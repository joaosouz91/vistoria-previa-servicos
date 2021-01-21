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
 *         &lt;element name="inUf" type="{http://www.w3.org/2001/XMLSchema}string"/&gt;
 *         &lt;element name="inLocalidade" type="{http://www.w3.org/2001/XMLSchema}string"/&gt;
 *         &lt;element name="inLogradouro" type="{http://www.w3.org/2001/XMLSchema}string"/&gt;
 *         &lt;element name="outQtdeArray_inout" type="{http://www.w3.org/2001/XMLSchema}int"/&gt;
 *         &lt;element name="outTipo_inout" type="{http://www.w3.org/2001/XMLSchema}string" maxOccurs="unbounded" minOccurs="0"/&gt;
 *         &lt;element name="outTitulo_inout" type="{http://www.w3.org/2001/XMLSchema}string" maxOccurs="unbounded" minOccurs="0"/&gt;
 *         &lt;element name="outLogradouro_inout" type="{http://www.w3.org/2001/XMLSchema}string" maxOccurs="unbounded" minOccurs="0"/&gt;
 *         &lt;element name="outBairro_inout" type="{http://www.w3.org/2001/XMLSchema}string" maxOccurs="unbounded" minOccurs="0"/&gt;
 *         &lt;element name="outCidade_inout" type="{http://www.w3.org/2001/XMLSchema}string" maxOccurs="unbounded" minOccurs="0"/&gt;
 *         &lt;element name="outUf_inout" type="{http://www.w3.org/2001/XMLSchema}string" maxOccurs="unbounded" minOccurs="0"/&gt;
 *         &lt;element name="outCep_inout" type="{http://www.w3.org/2001/XMLSchema}string" maxOccurs="unbounded" minOccurs="0"/&gt;
 *         &lt;element name="outLogradouro30_inout" type="{http://www.w3.org/2001/XMLSchema}string" maxOccurs="unbounded" minOccurs="0"/&gt;
 *         &lt;element name="outBairro15_inout" type="{http://www.w3.org/2001/XMLSchema}string" maxOccurs="unbounded" minOccurs="0"/&gt;
 *         &lt;element name="outCidade20_inout" type="{http://www.w3.org/2001/XMLSchema}string" maxOccurs="unbounded" minOccurs="0"/&gt;
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
    "inUf",
    "inLocalidade",
    "inLogradouro",
    "outQtdeArrayInout",
    "outTipoInout",
    "outTituloInout",
    "outLogradouroInout",
    "outBairroInout",
    "outCidadeInout",
    "outUfInout",
    "outCepInout",
    "outLogradouro30Inout",
    "outBairro15Inout",
    "outCidade20Inout"
})
@XmlRootElement(name = "prcDbmPesquisaLogradouroElement")
public class PrcDbmPesquisaLogradouroElement {

    @XmlElement(required = true, nillable = true)
    protected String inUf;
    @XmlElement(required = true, nillable = true)
    protected String inLocalidade;
    @XmlElement(required = true, nillable = true)
    protected String inLogradouro;
    @XmlElement(name = "outQtdeArray_inout", required = true, type = Integer.class, nillable = true)
    protected Integer outQtdeArrayInout;
    @XmlElement(name = "outTipo_inout", nillable = true)
    protected List<String> outTipoInout;
    @XmlElement(name = "outTitulo_inout", nillable = true)
    protected List<String> outTituloInout;
    @XmlElement(name = "outLogradouro_inout", nillable = true)
    protected List<String> outLogradouroInout;
    @XmlElement(name = "outBairro_inout", nillable = true)
    protected List<String> outBairroInout;
    @XmlElement(name = "outCidade_inout", nillable = true)
    protected List<String> outCidadeInout;
    @XmlElement(name = "outUf_inout", nillable = true)
    protected List<String> outUfInout;
    @XmlElement(name = "outCep_inout", nillable = true)
    protected List<String> outCepInout;
    @XmlElement(name = "outLogradouro30_inout", nillable = true)
    protected List<String> outLogradouro30Inout;
    @XmlElement(name = "outBairro15_inout", nillable = true)
    protected List<String> outBairro15Inout;
    @XmlElement(name = "outCidade20_inout", nillable = true)
    protected List<String> outCidade20Inout;

    /**
     * Obtém o valor da propriedade inUf.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getInUf() {
        return inUf;
    }

    /**
     * Define o valor da propriedade inUf.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setInUf(String value) {
        this.inUf = value;
    }

    /**
     * Obtém o valor da propriedade inLocalidade.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getInLocalidade() {
        return inLocalidade;
    }

    /**
     * Define o valor da propriedade inLocalidade.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setInLocalidade(String value) {
        this.inLocalidade = value;
    }

    /**
     * Obtém o valor da propriedade inLogradouro.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getInLogradouro() {
        return inLogradouro;
    }

    /**
     * Define o valor da propriedade inLogradouro.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setInLogradouro(String value) {
        this.inLogradouro = value;
    }

    /**
     * Obtém o valor da propriedade outQtdeArrayInout.
     * 
     * @return
     *     possible object is
     *     {@link Integer }
     *     
     */
    public Integer getOutQtdeArrayInout() {
        return outQtdeArrayInout;
    }

    /**
     * Define o valor da propriedade outQtdeArrayInout.
     * 
     * @param value
     *     allowed object is
     *     {@link Integer }
     *     
     */
    public void setOutQtdeArrayInout(Integer value) {
        this.outQtdeArrayInout = value;
    }

    /**
     * Gets the value of the outTipoInout property.
     * 
     * <p>
     * This accessor method returns a reference to the live list,
     * not a snapshot. Therefore any modification you make to the
     * returned list will be present inside the JAXB object.
     * This is why there is not a <CODE>set</CODE> method for the outTipoInout property.
     * 
     * <p>
     * For example, to add a new item, do as follows:
     * <pre>
     *    getOutTipoInout().add(newItem);
     * </pre>
     * 
     * 
     * <p>
     * Objects of the following type(s) are allowed in the list
     * {@link String }
     * 
     * 
     */
    public List<String> getOutTipoInout() {
        if (outTipoInout == null) {
            outTipoInout = new ArrayList<>();
        }
        return this.outTipoInout;
    }

    /**
     * Gets the value of the outTituloInout property.
     * 
     * <p>
     * This accessor method returns a reference to the live list,
     * not a snapshot. Therefore any modification you make to the
     * returned list will be present inside the JAXB object.
     * This is why there is not a <CODE>set</CODE> method for the outTituloInout property.
     * 
     * <p>
     * For example, to add a new item, do as follows:
     * <pre>
     *    getOutTituloInout().add(newItem);
     * </pre>
     * 
     * 
     * <p>
     * Objects of the following type(s) are allowed in the list
     * {@link String }
     * 
     * 
     */
    public List<String> getOutTituloInout() {
        if (outTituloInout == null) {
            outTituloInout = new ArrayList<>();
        }
        return this.outTituloInout;
    }

    /**
     * Gets the value of the outLogradouroInout property.
     * 
     * <p>
     * This accessor method returns a reference to the live list,
     * not a snapshot. Therefore any modification you make to the
     * returned list will be present inside the JAXB object.
     * This is why there is not a <CODE>set</CODE> method for the outLogradouroInout property.
     * 
     * <p>
     * For example, to add a new item, do as follows:
     * <pre>
     *    getOutLogradouroInout().add(newItem);
     * </pre>
     * 
     * 
     * <p>
     * Objects of the following type(s) are allowed in the list
     * {@link String }
     * 
     * 
     */
    public List<String> getOutLogradouroInout() {
        if (outLogradouroInout == null) {
            outLogradouroInout = new ArrayList<>();
        }
        return this.outLogradouroInout;
    }

    /**
     * Gets the value of the outBairroInout property.
     * 
     * <p>
     * This accessor method returns a reference to the live list,
     * not a snapshot. Therefore any modification you make to the
     * returned list will be present inside the JAXB object.
     * This is why there is not a <CODE>set</CODE> method for the outBairroInout property.
     * 
     * <p>
     * For example, to add a new item, do as follows:
     * <pre>
     *    getOutBairroInout().add(newItem);
     * </pre>
     * 
     * 
     * <p>
     * Objects of the following type(s) are allowed in the list
     * {@link String }
     * 
     * 
     */
    public List<String> getOutBairroInout() {
        if (outBairroInout == null) {
            outBairroInout = new ArrayList<>();
        }
        return this.outBairroInout;
    }

    /**
     * Gets the value of the outCidadeInout property.
     * 
     * <p>
     * This accessor method returns a reference to the live list,
     * not a snapshot. Therefore any modification you make to the
     * returned list will be present inside the JAXB object.
     * This is why there is not a <CODE>set</CODE> method for the outCidadeInout property.
     * 
     * <p>
     * For example, to add a new item, do as follows:
     * <pre>
     *    getOutCidadeInout().add(newItem);
     * </pre>
     * 
     * 
     * <p>
     * Objects of the following type(s) are allowed in the list
     * {@link String }
     * 
     * 
     */
    public List<String> getOutCidadeInout() {
        if (outCidadeInout == null) {
            outCidadeInout = new ArrayList<>();
        }
        return this.outCidadeInout;
    }

    /**
     * Gets the value of the outUfInout property.
     * 
     * <p>
     * This accessor method returns a reference to the live list,
     * not a snapshot. Therefore any modification you make to the
     * returned list will be present inside the JAXB object.
     * This is why there is not a <CODE>set</CODE> method for the outUfInout property.
     * 
     * <p>
     * For example, to add a new item, do as follows:
     * <pre>
     *    getOutUfInout().add(newItem);
     * </pre>
     * 
     * 
     * <p>
     * Objects of the following type(s) are allowed in the list
     * {@link String }
     * 
     * 
     */
    public List<String> getOutUfInout() {
        if (outUfInout == null) {
            outUfInout = new ArrayList<>();
        }
        return this.outUfInout;
    }

    /**
     * Gets the value of the outCepInout property.
     * 
     * <p>
     * This accessor method returns a reference to the live list,
     * not a snapshot. Therefore any modification you make to the
     * returned list will be present inside the JAXB object.
     * This is why there is not a <CODE>set</CODE> method for the outCepInout property.
     * 
     * <p>
     * For example, to add a new item, do as follows:
     * <pre>
     *    getOutCepInout().add(newItem);
     * </pre>
     * 
     * 
     * <p>
     * Objects of the following type(s) are allowed in the list
     * {@link String }
     * 
     * 
     */
    public List<String> getOutCepInout() {
        if (outCepInout == null) {
            outCepInout = new ArrayList<>();
        }
        return this.outCepInout;
    }

    /**
     * Gets the value of the outLogradouro30Inout property.
     * 
     * <p>
     * This accessor method returns a reference to the live list,
     * not a snapshot. Therefore any modification you make to the
     * returned list will be present inside the JAXB object.
     * This is why there is not a <CODE>set</CODE> method for the outLogradouro30Inout property.
     * 
     * <p>
     * For example, to add a new item, do as follows:
     * <pre>
     *    getOutLogradouro30Inout().add(newItem);
     * </pre>
     * 
     * 
     * <p>
     * Objects of the following type(s) are allowed in the list
     * {@link String }
     * 
     * 
     */
    public List<String> getOutLogradouro30Inout() {
        if (outLogradouro30Inout == null) {
            outLogradouro30Inout = new ArrayList<>();
        }
        return this.outLogradouro30Inout;
    }

    /**
     * Gets the value of the outBairro15Inout property.
     * 
     * <p>
     * This accessor method returns a reference to the live list,
     * not a snapshot. Therefore any modification you make to the
     * returned list will be present inside the JAXB object.
     * This is why there is not a <CODE>set</CODE> method for the outBairro15Inout property.
     * 
     * <p>
     * For example, to add a new item, do as follows:
     * <pre>
     *    getOutBairro15Inout().add(newItem);
     * </pre>
     * 
     * 
     * <p>
     * Objects of the following type(s) are allowed in the list
     * {@link String }
     * 
     * 
     */
    public List<String> getOutBairro15Inout() {
        if (outBairro15Inout == null) {
            outBairro15Inout = new ArrayList<>();
        }
        return this.outBairro15Inout;
    }

    /**
     * Gets the value of the outCidade20Inout property.
     * 
     * <p>
     * This accessor method returns a reference to the live list,
     * not a snapshot. Therefore any modification you make to the
     * returned list will be present inside the JAXB object.
     * This is why there is not a <CODE>set</CODE> method for the outCidade20Inout property.
     * 
     * <p>
     * For example, to add a new item, do as follows:
     * <pre>
     *    getOutCidade20Inout().add(newItem);
     * </pre>
     * 
     * 
     * <p>
     * Objects of the following type(s) are allowed in the list
     * {@link String }
     * 
     * 
     */
    public List<String> getOutCidade20Inout() {
        if (outCidade20Inout == null) {
            outCidade20Inout = new ArrayList<>();
        }
        return this.outCidade20Inout;
    }

}
