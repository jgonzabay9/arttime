/*
 * Software desarrollado bajo requerimientos de ByPhone,
 * entregado con licencia GNU GPL Version 3
 * Para más información sobre la licencia visitar http://www.gnu.org/licenses/gpl-3.0.html
 */
package arttime.beans;

import arttime.security.SecurityTaskManager;
import java.io.Serializable;
import javax.faces.bean.ManagedBean;
import javax.faces.context.FacesContext;
import javax.servlet.http.HttpServletRequest;

/**
 * Bean administrado para la GUI de cierre de sesión: logout.xhtml
 *
 * @author Daniel Gonzabay
 * @version 1.0.1
 */
@ManagedBean
public class LogoutBean implements Serializable {
    /* 
     * Implementación: Serializable
     * Todas las clases de un proyecto Web basado en sesiones deben ser serializadas 
     */

    private final String message = "Se ha cerrado la sesión correctamente"; //Mensaje de cierre de sesión

    /**
     * Crea una nueva instancia de la clase
     */
    public LogoutBean() {
        /* Obtener ip y cerrar sesión */
        String ipAddress = ((HttpServletRequest) FacesContext.getCurrentInstance().getExternalContext().getRequest()).getRemoteAddr();
        new SecurityTaskManager().logOut(ipAddress);
    }

    /**
     * Obtiene el mensaje de cierre de sesión
     *
     * @return String: Mensaje de cierre de sesión
     */
    public String getMessage() {
        return message;
    }
}
