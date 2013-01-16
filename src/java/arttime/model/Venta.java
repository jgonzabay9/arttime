/*
 * Software desarrollado bajo requerimientos de ByPhone,
 * entregado con licencia GNU GPL Version 3
 * Para más información sobre la licencia visitar http://www.gnu.org/licenses/gpl-3.0.html
 */
package arttime.model;

import java.io.Serializable;
import java.util.List;
import java.util.UUID;

/**
 * Modelo para una venta o pedido, referencia tabla 'arttime'.'venta'
 *
 * @author Daniel Gonzabay
 * @version 1.0.2
 */
public class Venta implements Serializable {
    /* 
     * Implementación: Serializable
     * Todas las clases de un proyecto Web basado en sesiones deben ser serializadas 
     */

    private UUID idVenta;               //Identificador único de la venta o pedido
    private List<Producto> productos;   //Lista de productos de la venta o pedido

    /**
     * Obtiene el ID de la venta
     *
     * @return UUID: ID de la venta
     */
    public UUID getIdVenta() {
        return idVenta;
    }

    /**
     * Establece el ID de la venta
     *
     * @param idVenta UUID: ID de la venta
     */
    public void setIdVenta(UUID idVenta) {
        this.idVenta = idVenta;
    }

    /**
     * Obtiene la lista de productos
     *
     * @return List::Producto:: : Lista de productos
     * @see Producto
     */
    public List<Producto> getProductos() {
        return productos;
    }

    /**
     * Establece la lista de productos
     *
     * @param productos List::Producto:: : Lista de productos
     * @see Producto
     */
    public void setProductos(List<Producto> productos) {
        this.productos = productos;
    }
}
