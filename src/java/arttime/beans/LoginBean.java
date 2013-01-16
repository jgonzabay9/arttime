/*
 * Software desarrollado bajo requerimientos de ByPhone,
 * entregado con licencia GNU GPL Version 3
 * Para más información sobre la licencia visitar http://www.gnu.org/licenses/gpl-3.0.html
 */
package arttime.beans;

import arttime.model.Usuario;
import arttime.security.SecurityTaskManager;
import java.io.IOException;
import java.io.Serializable;
import javax.faces.bean.ManagedBean;
import javax.faces.context.FacesContext;
import javax.servlet.http.HttpServletRequest;

/**
 * Bean administrado para la GUI de inicio de sesión: login.xhtml
 *
 * @author Daniel Gonzabay
 * @version 1.0.2
 */
@ManagedBean
public class LoginBean implements Serializable {
    /* 
     * Implementación: Serializable
     * Todas las clases de un proyecto Web basado en sesiones deben ser serializadas 
     */

    private String userName;        //Nombre de usuario
    private String password;        //Contraseña del usuario
    private Usuario usuario;        //Usuario actual
    private String errorMessage;    //Mensaje de error

    /**
     * Inicia sesión con el usuario y contraseña ingresados
     */
    public void logIn() {
        try {
            String ipAddress = ((HttpServletRequest) FacesContext.getCurrentInstance().getExternalContext().getRequest()).getRemoteAddr();

            usuario = new SecurityTaskManager().login(userName.toUpperCase(), password, ipAddress);
            if (usuario.isLogedIn()) {
                try {
                    FacesContext.getCurrentInstance().getExternalContext().redirect("welcome.xhtml");
                } catch (IOException ex) {
                }
            }
        } catch (Exception ex) {
        }

        errorMessage = "No se ha iniciado sesión: Nombre de usuario o contraseña incorrecta.";
    }

    /**
     * Obtiene la contraseña
     *
     * @return String: Contraseña
     */
    public String getPassword() {
        return password;
    }

    /**
     * Establece la contraseña
     *
     * @param password String: Contraseña
     */
    public void setPassword(String password) {
        this.password = password;
    }

    /**
     * Obtiene el nombre de usuario
     *
     * @return String: Nombre de usuario
     */
    public String getUserName() {
        /* Todo se maneja en mayúsculas */
        if (null != userName) {
            userName = userName.toUpperCase();
        }
        return userName;
    }

    /**
     * Establece el nombre de usuario
     *
     * @param userName String: Nombre de usuario
     */
    public void setUserName(String userName) {
        /* Todo se maneja en mayúsculas */
        userName = userName.toUpperCase();
        this.userName = userName;
    }

    /**
     * Obtiene el usuario actual
     *
     * @return Usuario: Usuario actual
     * @see Usuario
     */
    public Usuario getUsuario() {
        return usuario;
    }

    /**
     * Establece el usuario actual
     *
     * @param usuario Usuario: Usuario actual
     * @see Usuario
     */
    public void setUsuario(Usuario usuario) {
        this.usuario = usuario;
    }

    /**
     * Obtiene el mensaje de error
     *
     * @return String: Mensaje de error
     */
    public String getErrorMessage() {
        return errorMessage;
    }

    /**
     * Establece el mensaje de error
     *
     * @param errorMessage String: Mensaje de error
     */
    public void setErrorMessage(String errorMessage) {
        this.errorMessage = errorMessage;
    }
}
