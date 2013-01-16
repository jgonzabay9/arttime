/*
 * Software desarrollado bajo requerimientos de ByPhone,
 * entregado con licencia GNU GPL Version 3
 * Para más información sobre la licencia visitar http://www.gnu.org/licenses/gpl-3.0.html
 */
package arttime.security;

import java.io.Serializable;

/**
 * Modelo para la sesión por máquina
 *
 * @author Daniel Gonzabay
 * @version 1.0.1
 */
public class MachineSession implements Serializable {
    /* 
     * Implementación: Serializable
     * Todas las clases de un proyecto Web basado en sesiones deben ser serializadas 
     */

    private String userId;  //Nombre de usuario
    private String IP;      //IP desde la que se conecta

    /**
     * Constructor, crea una nueva instancia de sesión de máquina
     *
     * @param userId String: Nombre de usuario
     * @param IP String: Dirección IP desde la que se conecta
     */
    public MachineSession(String userId, String IP) {
        this.userId = userId;
        this.IP = IP;
    }

    /**
     * Obtiene el nombre del usuario
     *
     * @return String: Nombre del usuario
     */
    public String getUserId() {
        return userId;
    }

    /**
     * Establece el nombre del usuario
     *
     * @param userId String: Nombre del usuario
     */
    public void setUserId(String userId) {
        this.userId = userId;
    }

    /**
     * Obtiene la dirección IP
     *
     * @return String: Dirección IP
     */
    public String getIP() {
        return IP;
    }

    /**
     * Establece la dirección IP
     *
     * @param IP String: Dirección IP
     */
    public void setIP(String IP) {
        this.IP = IP;
    }
}
