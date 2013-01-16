/*
 * Software desarrollado bajo requerimientos de ByPhone,
 * entregado con licencia GNU GPL Version 3
 * Para más información sobre la licencia visitar http://www.gnu.org/licenses/gpl-3.0.html
 */
package arttime.security;

import arttime.connection.DBConnection;
import arttime.model.Usuario;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

/**
 * Clase para manejo de la seguridad y autenticación
 *
 * @author Daniel Gonzabay
 * @version 1.0.2
 */
public class SecurityTaskManager implements Serializable {
    /* 
     * Implementación: Serializable
     * Todas las clases de un proyecto Web basado en sesiones deben ser serializadas 
     */

    private static List<MachineSession> activeSessions = new ArrayList<MachineSession>();   // Lista de sesiones activas

    /**
     * Autentica e inicia sesión de un usuario
     *
     * @param userName String: Nombre del usuario
     * @param passWord String: Contraseña del usuario
     * @param ip String: Dirección IP desde la que se conecta el usuario
     * @return Usuario: Usuario autenticado
     * @see Usuario
     */
    public synchronized Usuario login(String userName, String passWord, String ip) {
        Usuario usuario = new DBConnection().obtenerUsuario(userName.toUpperCase());
        if ((null != passWord && !passWord.trim().equals("")) && passWord.equals(usuario.getPassWord())) {
            usuario.setLogedIn(true);
            MachineSession session = new MachineSession(usuario.getUserName(), ip.trim());
            if (!activeSessions.contains(session)) {
                activeSessions.add(session);
            }
            return usuario;
        }
        return new Usuario();
    }

    /**
     * Verifica si algún usuario ha iniciado sesión desde la IP
     *
     * @param ip String: Dirección IP desde la que se solicita el usuario
     * @return boolean: Verdadero si un usuario ha iniciado sesión
     */
    public synchronized boolean hasUserLogedIn(String ip) {
        Iterator iterator = activeSessions.iterator();

        while (iterator.hasNext()) {
            MachineSession session = (MachineSession) iterator.next();
            if (session.getIP().equals(ip.trim())) {
                return true;
            }
        }

        return false;
    }

    /**
     * Cierra la sesión de un usuario
     *
     * @param ip IP de la sesión
     */
    public synchronized void logOut(String ip) {
        Iterator iterator = activeSessions.iterator();

        while (iterator.hasNext()) {
            MachineSession session = (MachineSession) iterator.next();
            if (session.getIP().equals(ip.trim())) {
                activeSessions.remove(session);
                return;
            }
        }
    }

    /**
     * Obtiene el nombre del usuario que ha iniciado sesión desde una IP
     *
     * @param ip String: Dirección IP desde la que ha iniciado sesión
     * @return String: Nombre del usuario
     */
    public synchronized String getUserName(String ip) {
        Iterator iterator = activeSessions.iterator();

        while (iterator.hasNext()) {
            MachineSession session = (MachineSession) iterator.next();
            if (session.getIP().equals(ip.trim())) {
                return session.getUserId();
            }
        }

        return null;
    }
}