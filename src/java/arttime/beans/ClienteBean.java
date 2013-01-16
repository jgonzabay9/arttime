/*
 * Software desarrollado bajo requerimientos de ByPhone,
 * entregado con licencia GNU GPL Version 3
 * Para más información sobre la licencia visitar http://www.gnu.org/licenses/gpl-3.0.html
 */
package arttime.beans;

import arttime.callmanagement.CallManager;
import arttime.connection.DBConnection;
import arttime.model.Cliente;
import arttime.model.Llamada;
import arttime.security.SecurityTaskManager;
import java.io.IOException;
import java.io.Serializable;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.context.FacesContext;
import javax.servlet.http.HttpServletRequest;

/**
 * Bean administrado para las GUIs de registro de cliente: inicial.xhtml y
 * cliente.xhtml
 *
 * @author Daniel Gonzabay
 * @version 1.0.2
 */
@ManagedBean
public class ClienteBean implements Serializable {
    /* 
     * Implementación: Serializable
     * Todas las clases de un proyecto Web basado en sesiones deben ser serializadas 
     */

    private String cedula;                          //Cédula del cliente
    private Cliente cliente;                        //Cliente
    private String cedulaValidationMessage = "";    //Mensaje de validación de cédula
    private String tarjetaValidationMessage = "";   //Mensaje de validación de tarjeta
    private String caducidadValidationMessage = ""; //Mensaje de validación de fecha de caducidad
    private Llamada call;                           //Llamada actual

    /**
     * Crea una nueva instancia de la clase e inicializa variables
     */
    public ClienteBean() {
        String ipAddress = ((HttpServletRequest) FacesContext.getCurrentInstance().getExternalContext().getRequest()).getRemoteAddr();
        call = new CallManager().getActiveCall(new SecurityTaskManager().getUserName(ipAddress));
        cliente = call.getCliente();

        if (null != cliente.getCedula()) {
            cedula = cliente.getCedula();
        }
    }

    /**
     * Abre la interfaz de información de productos
     */
    public void openProductos() {
        if (validarCedula()) {
            new DBConnection().clienteUoC(cliente);
            try {
                FacesContext.getCurrentInstance().getExternalContext().redirect("./productos.xhtml");
            } catch (IOException ex) {
                Logger.getLogger(ClienteBean.class.getName()).log(Level.SEVERE, null, ex);
            }
        } else {
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_FATAL, "Número de cédula erroneo", "Número de cédula erroneo"));
        }
    }

    /**
     * Guarda el cliente en la base de datos
     */
    public void guardarCliente() {
        if (validarCedula()) {
            if (validarCelular()) {
                if (validarTarjeta()) {
                    if (validarCaducidad()) {
                        new DBConnection().clienteUoC(cliente);
                        try {
                            FacesContext.getCurrentInstance().getExternalContext().redirect("./registro.xhtml");
                        } catch (IOException ex) {
                            Logger.getLogger(ClienteBean.class.getName()).log(Level.SEVERE, null, ex);
                        }
                    } else {
                        FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_FATAL, "Fecha de caducidad no válida", "Fecha de caducidad no válida"));
                    }
                } else {
                    FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_FATAL, "Número de tarjeta no válido", "Número de tarjeta no válido"));
                }
            } else {
                FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_FATAL, "Número de celular no válido", "Número de celular no válido"));
            }
        } else {
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_FATAL, "Número de cédula erroneo", "Número de cédula erroneo"));
        }
    }

    /**
     * Valida el número de cédula
     *
     * @return boolean: Verdadero si el número de cédula es válido
     */
    public boolean validarCedula() {

        String numCedula = cliente.getCedula();

        boolean cedulaCorrecta = false;

        try {

            if (numCedula.trim().length() == 10) {
                int tercerDigito = Integer.parseInt(numCedula.substring(2, 3));
                if (tercerDigito < 6) {

                    int[] coefValCedula = {2, 1, 2, 1, 2, 1, 2, 1, 2};
                    int verificador = Integer.parseInt(numCedula.substring(9, 10));
                    int suma = 0;
                    int digito = 0;
                    for (int i = 0; i < (numCedula.length() - 1); i++) {
                        digito = Integer.parseInt(numCedula.substring(i, i + 1)) * coefValCedula[i];
                        suma += ((digito % 10) + (digito / 10));
                    }

                    if ((suma % 10 == 0) && (suma % 10 == verificador)) {
                        cedulaCorrecta = true;
                    } else if ((10 - (suma % 10)) == verificador) {
                        cedulaCorrecta = true;
                    } else {
                        cedulaCorrecta = false;
                    }
                } else {
                    cedulaCorrecta = false;
                }
            } else {
                if (numCedula.trim().length() == 13) {
                    cedulaCorrecta = true;
                } else {
                    cedulaCorrecta = false;
                }
            }
        } catch (NumberFormatException nfe) {
            cedulaCorrecta = false;
        } catch (Exception err) {
            cedulaCorrecta = false;
        }

        return cedulaCorrecta;
    }

    /**
     * Valida el número de tarjeta de crédito
     *
     * @return boolean: Verdadero si el número de tarjeta es válido
     */
    public boolean validarTarjeta() {
        String tarjeta = cliente.getNumeroTarjeta();
        int suma = 0;
        boolean flag = true;

        for (int i = tarjeta.length() - 1; i >= 0; i--) {
            if (!flag) {
                try {
                    int tmp = Integer.parseInt("" + tarjeta.charAt(i)) * 2;
                    suma += tmp >= 10 ? tmp - 9 : tmp;
                } catch (Exception ex) {
                }
            } else {
                try {
                    suma += Integer.parseInt("" + tarjeta.charAt(i));
                } catch (Exception ex) {
                }
            }
            flag = !flag;
        }
        return suma % 10 == 0;
    }

    /**
     * Valida la caducidad de la tarjeta de crédito
     *
     * @return boolean: Verdadero si la fecha de caducidad de la tarjeta de
     * crédito es válida
     */
    public boolean validarCaducidad() {
        try {
            int mescad = Integer.parseInt(cliente.getCaducidadTarjeta().substring(0, 2));
            if (mescad <= 12) {
                int ancad = Integer.parseInt(cliente.getCaducidadTarjeta().substring(3));
                if (ancad > 12) {
                    return true;
                }
            }
        } catch (Exception ex) {
        }
        return false;
    }

    /**
     * Valida el número de celular
     *
     * @return boolean: Verdadero si el número de celular es válido
     */
    public boolean validarCelular() {
        return cliente.getCelular().startsWith("09");
    }

    /**
     * Obtiene el cliente
     *
     * @return Cliente: Clienteç
     * @see Cliente
     */
    public Cliente getCliente() {
        return cliente;
    }

    /**
     * Establece el cliente
     *
     * @param cliente Cliente: Cliente
     * @see Cliente
     */
    public void setCliente(Cliente cliente) {
        this.cliente = cliente;
    }

    /**
     * Obtiene la cédula
     *
     * @return String: Cédula
     */
    public String getCedula() {
        return cedula;
    }

    /**
     * Establece la cédula
     *
     * @param cedula String: Cédula
     */
    public void setCedula(String cedula) {
        if (cedula.length() == 10 || cedula.length() == 13) {
            try {
                Long.parseLong(cedula);
                call.setCliente(new DBConnection().obtenerCliente(cedula));
                cliente = call.getCliente();
            } catch (NumberFormatException nfe) {
            }
        }
        this.cedula = cedula;
    }

    /**
     * Obtiene el mensaje de validacion de cédula
     *
     * @return String: Mensaje
     */
    public String getCedulaValidationMessage() {
        return cedulaValidationMessage;
    }

    /**
     * Establece el mensaje de validación de cédula
     *
     * @param cedulaValidationMessage String: Mensaje
     */
    public void setCedulaValidationMessage(String cedulaValidationMessage) {
        this.cedulaValidationMessage = cedulaValidationMessage;
    }

    /**
     * Obtiene el mensaje de validación de tarjeta
     *
     * @return String: Mensaje
     */
    public String getTarjetaValidationMessage() {
        return tarjetaValidationMessage;
    }

    /**
     * Establece el mensaje de validación de tarjeta
     *
     * @param tarjetaValidationMessage String: Mensaje
     */
    public void setTarjetaValidationMessage(String tarjetaValidationMessage) {
        this.tarjetaValidationMessage = tarjetaValidationMessage;
    }

    /**
     * Obtiene el mensaje de validación de caducidad
     *
     * @return String: Mensaje
     */
    public String getCaducidadValidationMessage() {
        return caducidadValidationMessage;
    }

    /**
     * Establece el mensaje de validación de caducidad
     *
     * @param caducidadValidationMessage String: Mensaje
     */
    public void setCaducidadValidationMessage(String caducidadValidationMessage) {
        this.caducidadValidationMessage = caducidadValidationMessage;
    }
}
