/*
 * Software desarrollado bajo requerimientos de ByPhone,
 * entregado con licencia GNU GPL Version 3
 * Para más información sobre la licencia visitar http://www.gnu.org/licenses/gpl-3.0.html
 */
package arttime.beans;

import arttime.security.SecurityTaskManager;
import javax.faces.bean.ManagedBean;
import javax.faces.context.FacesContext;
import javax.servlet.http.HttpServletRequest;

/**
 * Bean administrado para la gestion de acceso al sistema para todas las GUIs
 *
 * @author Daniel Gonzabay
 * @version 1.0.1
 */
@ManagedBean
public class AccessBean {

    private String idUsuario = "";  //Nombre de usuario

    /**
     * Constructor, obtiene la sesión actual del usuario, si no ha iniciado
     * sesión, lo redirecciona a la GUI de login
     */
    public AccessBean() {
        try {
            /* Obtiene la IP desde la que se intenta acceder al sistema */
            String ipAddress = ((HttpServletRequest) FacesContext
                    .getCurrentInstance().getExternalContext().getRequest())
                    .getRemoteAddr();
            if (!(new SecurityTaskManager().hasUserLogedIn(ipAddress))) {
                /* Redirecciona a la página del login */
                FacesContext.getCurrentInstance().getExternalContext()
                        .redirect("./login.xhtml");
            }
        } catch (Exception ex) {
            try {
                /* Redirecciona a la página del login */
                FacesContext.getCurrentInstance().getExternalContext()
                        .redirect("./login.xhtml");
            } catch (Exception exc) {
            }
        }
    }

    /**
     * Obtiene el nombre del usuario
     *
     * @return String: Nombre del usuario
     */
    public String getIdUsuario() {
        return idUsuario;
    }

    /**
     * Establece el nombre del usuario
     *
     * @param idUsuario String: Nombre del usuario
     */
    public void setIdUsuario(String idUsuario) {
        this.idUsuario = idUsuario;
    }
}
