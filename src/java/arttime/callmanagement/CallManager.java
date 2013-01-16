/*
 * Software desarrollado bajo requerimientos de ByPhone,
 * entregado con licencia GNU GPL Version 3
 * Para más información sobre la licencia visitar http://www.gnu.org/licenses/gpl-3.0.html
 */
package arttime.callmanagement;

import arttime.connection.DBConnection;
import arttime.model.Cliente;
import arttime.model.Llamada;
import arttime.model.Producto;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.UUID;

/**
 * Clase para la gestión de llamadas
 *
 * @author Daniel Gonzabay
 * @version 1.0.5
 * @see Llamada
 */
public class CallManager implements Serializable {
    /* 
     * Implementación: Serializable
     * Todas las clases de un proyecto Web basado en sesiones deben ser serializadas 
     */

    private static List<Llamada> activeCalls = new ArrayList<Llamada>();    //Lista de llamadas activas

    /**
     * Obtiene la llamada activa de un usuario
     *
     * @param username String: Nombre de usuario
     * @return Llamada: Llamada activa
     * @see Llamada
     */
    public synchronized Llamada getActiveCall(String username) {
        Iterator iterator = activeCalls.iterator();

        while (iterator.hasNext()) {
            Llamada llamada = (Llamada) iterator.next();
            if (llamada.getUsuario().equals(username) && llamada.getStatus().equals("ACTIVE")) {
                return llamada;
            }
        }
        return createNewCall(username);
    }

    /**
     * Crea una nueva llamada para un usuario
     *
     * @param username String: Nombre de usuario
     * @return Llamada: Nueva llamada
     * @see Llamada
     */
    public synchronized Llamada createNewCall(String username) {
        Llamada newCall = new Llamada();
        newCall.setUsuario(username);
        newCall.setCategoria("");
        newCall.setCliente(new Cliente());
        newCall.setDefinicion("");
        newCall.setFecha(new DBConnection().obtenerFecha());
        newCall.setHora(new DBConnection().obtenerHora());
        newCall.setProductos(new DBConnection().obtenerProductos());
        newCall.setCarrito(new ArrayList<Producto>());
        newCall.setStatus("ACTIVE");
        newCall.setCodigoLlamada(UUID.randomUUID().toString());

        activeCalls.add(newCall);

        return newCall;
    }

    /**
     * Cierra una llamada
     *
     * @param call Llamada: Llamada
     * @see Llamada
     */
    public synchronized void closeCall(Llamada call) {
        call.setStatus("CLOSED");
        activeCalls.remove(call);
    }

    /**
     * Desbloquea llamadas para un usuario
     *
     * @param username String: Nombre de usuario
     */
    public synchronized void unlockNewCalls(String username) {
        Iterator iterator = activeCalls.iterator();

        while (iterator.hasNext()) {
            Llamada call = (Llamada) iterator.next();
            if (call.getUsuario().equals(username) && call.getStatus().equals("ACTIVE")) {
                call.setStatus("CLOSED");
                activeCalls.remove(call);
            }
        }
    }
}
