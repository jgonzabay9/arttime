/*
 * Software desarrollado bajo requerimientos de ByPhone,
 * entregado con licencia GNU GPL Version 3
 * Para más información sobre la licencia visitar http://www.gnu.org/licenses/gpl-3.0.html
 */
package arttime.model;

import java.io.Serializable;
import java.sql.Date;

/**
 * Modelo para los clientes, referencia tabla 'arttime'.'cliente'
 *
 * @author Daniel Gonzabay
 * @version 1.0.3
 */
public class Cliente implements Serializable {
    /* 
     * Implementación: Serializable
     * Todas las clases de un proyecto Web basado en sesiones deben ser serializadas 
     */

    private String cedula;              //Cedula del cliente
    private String nombreCliente;       //Nombre del cliente
    private String telefono;            //Teléfono fijo del cliente, sin codigo de área
    private String celular;             //Teléfono celular del cliente, con los 10 dígitos
    private String medio;               //Medio por el que el cliente se enteró de las promociones
    private String numeroTarjeta;       //Número de tarjeta del cliente
    private String tipoTarjeta;         //Tipo de tarjeta del cliente
    private String caducidadTarjeta;    //Fecha de caducidad de la tarjeta del cliente, en formato MM/aa
    private Date fechaRegistroCl;       //Fecha de registro del cliente
    private String tipoDireccion;       //Tipo de dirección del cliente, TRABAJO o DOMICILIO
    private String callePrincipal;      //Calle principal de la dirección del cliente
    private String calleSecundaria;     //Calle secundaria de la dirección del cliente
    private String numeroEdificio;      //Número de casa o edificio de la dirección del cliente
    private String referencia;          //Referencias a la dirección del cliente
    private String codigoSeguridad;     //Código de seguridad de la tarjeta del cliente
    private String nombrePlastico;      //Nombre que aparece en la tarjeta del cliente
    private String sector;              //Sector o barrio de la dirección del cliente

    /**
     * Constructor, permite crear una instancia en blanco de la clase
     */
    public Cliente() {
    }

    /**
     * Constructor, crea una nueva instancia de la clase
     *
     * @param cedula String: Cédula del cliente
     * @param nombreCliente String: Nombre del cliente
     * @param numeroTarjeta String: Número de tarjeta del cliente
     * @param tipoTarjeta String: Tipo de tarjeta del cliente: VISA, MASTERCARD,
     * DINERS CLUB o DISCOVER
     * @param caducidadTarjeta String: Fecha de caducidad de la tarjeta del
     * cliente en formato MM/aa
     * @param fechaRegistroCl Date: Fecha de registro del cliente
     * @param tipoDireccion String: Tipo de dirección del cliente: DOMICILIO o
     * TRABAJO
     * @param dCallePrincipal String: Calle principal de la dirección del
     * cliente
     * @param dCalleSecundaria String: Calle secundaria de la dirección del
     * cliente
     * @param dNumeroEdificio String: Número del edificio o casa de la dirección
     * del cliente
     * @param dReferencia String: Referencias a la dirección del cliente
     * @param codigoSeguridad String: Código de seguridad de la tarjeta del
     * cliente
     * @param nombrePlastico String: Nombre que aparece en la tarjeta del
     * cliente
     */
    public Cliente(String cedula, String nombreCliente, String numeroTarjeta, String tipoTarjeta, String caducidadTarjeta, Date fechaRegistroCl, String tipoDireccion, String dCallePrincipal, String dCalleSecundaria, String dNumeroEdificio, String dReferencia, String codigoSeguridad, String nombrePlastico) {
        this.cedula = cedula;
        this.nombreCliente = nombreCliente;
        this.numeroTarjeta = numeroTarjeta;
        this.tipoTarjeta = tipoTarjeta;
        this.caducidadTarjeta = caducidadTarjeta;
        this.fechaRegistroCl = fechaRegistroCl;
        this.tipoDireccion = tipoDireccion;
        this.callePrincipal = dCallePrincipal;
        this.calleSecundaria = dCalleSecundaria;
        this.numeroEdificio = dNumeroEdificio;
        this.referencia = dReferencia;
        this.codigoSeguridad = codigoSeguridad;
        this.nombrePlastico = nombrePlastico;
    }

    /**
     * Obtiene la caducidad de la tarjeta
     *
     * @return String: Fecha de caducidad de la tarjeta
     */
    public String getCaducidadTarjeta() {
        return caducidadTarjeta;
    }

    /**
     * Establece la caducidad de la tarjeta
     *
     * @param caducidadTarjeta String: Fecha de caducidad de la tarjeta
     */
    public void setCaducidadTarjeta(String caducidadTarjeta) {
        this.caducidadTarjeta = caducidadTarjeta;
    }

    /**
     * Obtiene la calle principal de la dirección
     *
     * @return String: Calle principal de la dirección
     */
    public String getCallePrincipal() {
        /* Todo se maneja en mayusculas */
        if (null != callePrincipal) {
            callePrincipal = callePrincipal.toUpperCase();
        }
        return callePrincipal;
    }

    /**
     * Establece la calle principal de la dirección
     *
     * @param callePrincipal String: Calle principal de la dirección
     */
    public void setCallePrincipal(String callePrincipal) {
        /* Todo se maneja en mayusculas */
        if (null != callePrincipal) {
            callePrincipal = callePrincipal.toUpperCase();
        }
        this.callePrincipal = callePrincipal;
    }

    /**
     * Obtiene la calle secundaria de la dirección
     *
     * @return String: Calle secundaria de la dirección
     */
    public String getCalleSecundaria() {
        /* Todo se maneja en mayusculas */
        if (null != calleSecundaria) {
            calleSecundaria = calleSecundaria.toUpperCase();
        }
        return calleSecundaria;
    }

    /**
     * Establece la calle secundaria de la dirección
     *
     * @param calleSecundaria String: Calle secundaria de la dirección
     */
    public void setCalleSecundaria(String calleSecundaria) {
        /* Todo se maneja en mayusculas */
        if (null != calleSecundaria) {
            calleSecundaria = calleSecundaria.toUpperCase();
        }
        this.calleSecundaria = calleSecundaria;
    }

    /**
     * Obtiene el número de cédula
     *
     * @return String: Número de cédula
     */
    public String getCedula() {
        return cedula;
    }

    /**
     * Establece el número de cédula
     *
     * @param cedula String: Número de cédula
     */
    public void setCedula(String cedula) {
        this.cedula = cedula;
    }

    /**
     * Obtiene el número de celular
     *
     * @return String: Número de celular
     */
    public String getCelular() {
        return celular;
    }

    /**
     * Establece el número de celular
     *
     * @param celular String: Número de celular
     */
    public void setCelular(String celular) {
        this.celular = celular;
    }

    /**
     * Obtiene el código de seguridad
     *
     * @return String: Código de seguridad
     */
    public String getCodigoSeguridad() {
        return codigoSeguridad;
    }

    /**
     * Establece el código de seguridad
     *
     * @param codigoSeguridad String: Código de seguridad
     */
    public void setCodigoSeguridad(String codigoSeguridad) {
        this.codigoSeguridad = codigoSeguridad;
    }

    /**
     * Obtiene la fecha de registro
     *
     * @return Date: Fecha de registro
     */
    public Date getFechaRegistroCl() {
        return fechaRegistroCl;
    }

    /**
     * Establece la fecha de registro
     *
     * @param fechaRegistroCl Date: Fecha de registro
     */
    public void setFechaRegistroCl(Date fechaRegistroCl) {
        this.fechaRegistroCl = fechaRegistroCl;
    }

    /**
     * Obtiene el medio
     *
     * @return String: medio
     */
    public String getMedio() {
        /* Todo se maneja en mayusculas */
        if (null != medio) {
            medio = medio.toUpperCase();
        }
        return medio;
    }

    /**
     * Establece el medio
     *
     * @param medio String: medio
     */
    public void setMedio(String medio) {
        /* Todo se maneja en mayusculas */
        if (null != medio) {
            medio = medio.toUpperCase();
        }
        this.medio = medio;
    }

    /**
     * Obtiene el nombre del cliente
     *
     * @return String: Nombre del cliente
     */
    public String getNombreCliente() {
        /* Todo se maneja en mayusculas */
        if (null != nombreCliente) {
            nombreCliente = nombreCliente.toUpperCase();
        }
        return nombreCliente;
    }

    /**
     * Establece el nombre del cliente
     *
     * @param nombreCliente String: Nombre del cliente
     */
    public void setNombreCliente(String nombreCliente) {
        /* Todo se maneja en mayusculas */
        if (null != nombreCliente) {
            nombreCliente = nombreCliente.toUpperCase();
        }
        this.nombreCliente = nombreCliente;
    }

    /**
     * Obtiene el nombre que aparece en la tarjeta
     *
     * @return String: Nombre del plastico
     */
    public String getNombrePlastico() {
        /* Todo se maneja en mayusculas */
        if (null != nombrePlastico) {
            nombrePlastico = nombrePlastico.toUpperCase();
        }
        return nombrePlastico;
    }

    /**
     * Establece el nombre que aparece en la tarjeta
     *
     * @param nombrePlastico String: Nombre del plástico
     */
    public void setNombrePlastico(String nombrePlastico) {
        /* Todo se maneja en mayusculas */
        if (null != nombrePlastico) {
            nombrePlastico = nombrePlastico.toUpperCase();
        }
        this.nombrePlastico = nombrePlastico;
    }

    /**
     * Obtiene el número del edificio
     *
     * @return String: Número del edificio
     */
    public String getNumeroEdificio() {
        /* Todo se maneja en mayusculas */
        if (null != numeroEdificio) {
            numeroEdificio = numeroEdificio.toUpperCase();
        }
        return numeroEdificio;
    }

    /**
     * Establece el número del edificio
     *
     * @param numeroEdificio String: Número del edificio
     */
    public void setNumeroEdificio(String numeroEdificio) {
        /* Todo se maneja en mayusculas */
        if (null != numeroEdificio) {
            numeroEdificio = numeroEdificio.toUpperCase();
        }
        this.numeroEdificio = numeroEdificio;
    }

    /**
     * Obtiene el número de la tarjeta
     *
     * @return String: Número de la tarjeta
     */
    public String getNumeroTarjeta() {
        return numeroTarjeta;
    }

    /**
     * Establece el número de la tarjeta
     *
     * @param numeroTarjeta String: Número de la tarjeta
     */
    public void setNumeroTarjeta(String numeroTarjeta) {
        this.numeroTarjeta = numeroTarjeta;
    }

    /**
     * Obtiene la referencia
     *
     * @return String: Referencia
     */
    public String getReferencia() {
        /* Todo se maneja en mayusculas */
        if (null != referencia) {
            referencia = referencia.toUpperCase();
        }
        return referencia;
    }

    /**
     * Establece la referencia
     *
     * @param referencia String: Referencia
     */
    public void setReferencia(String referencia) {
        /* Todo se maneja en mayusculas */
        if (null != referencia) {
            referencia = referencia.toUpperCase();
        }
        this.referencia = referencia;
    }

    /**
     * Obtiene el número de teléfono
     *
     * @return String: Número de teléfono
     */
    public String getTelefono() {
        return telefono;
    }

    /**
     * Establece el número de teléfono
     *
     * @param telefono String: Número de teléfono
     */
    public void setTelefono(String telefono) {
        this.telefono = telefono;
    }

    /**
     * Obtiene el tipo de dirección
     *
     * @return String: Tipo de dirección
     */
    public String getTipoDireccion() {
        /* Todo se maneja en mayusculas */
        if (null != tipoDireccion) {
            tipoDireccion = tipoDireccion.toUpperCase();
        }
        return tipoDireccion;
    }

    /**
     * Establece el tipo de dirección
     *
     * @param tipoDireccion String: Tipo de dirección
     */
    public void setTipoDireccion(String tipoDireccion) {
        /* Todo se maneja en mayusculas */
        if (null != tipoDireccion) {
            tipoDireccion = tipoDireccion.toUpperCase();
        }
        this.tipoDireccion = tipoDireccion;
    }

    /**
     * Obtiene el tipo de tarjeta
     *
     * @return String: Tipo de tarjeta
     */
    public String getTipoTarjeta() {
        /* Todo se maneja en mayusculas */
        if (null != tipoTarjeta) {
            tipoTarjeta = tipoTarjeta.toUpperCase();
        }
        return tipoTarjeta;
    }

    /**
     * Establece el tipo de tarjeta
     *
     * @param tipoTarjeta String: Tipo de tarjeta
     */
    public void setTipoTarjeta(String tipoTarjeta) {
        /* Todo se maneja en mayusculas */
        if (null != tipoTarjeta) {
            tipoTarjeta = tipoTarjeta.toUpperCase();
        }
        this.tipoTarjeta = tipoTarjeta;
    }

    /**
     * Obtiene el sector o barrio
     *
     * @return String: Sector o barrio
     */
    public String getSector() {
        /* Todo se maneja en mayusculas */
        if (null != sector) {
            sector = sector.toUpperCase();
        }
        return sector;
    }

    /**
     * Establece el sector o barrio
     *
     * @param sector String: Sector o barrio
     */
    public void setSector(String sector) {
        /* Todo se maneja en mayusculas */
        if (null != sector) {
            sector = sector.toUpperCase();
        }
        this.sector = sector;
    }
}
