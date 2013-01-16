/*
 * Software desarrollado bajo requerimientos de ByPhone,
 * entregado con licencia GNU GPL Version 3
 * Para más información sobre la licencia visitar http://www.gnu.org/licenses/gpl-3.0.html
 */
package arttime.model;

import java.io.Serializable;
import java.sql.Date;
import java.sql.Time;
import java.util.Collections;
import java.util.List;

/**
 * Modelo para una llamada, referencia tabla 'arttime'.'llamada'
 *
 * @author Daniel Gonzabay
 * @version 1.0.4
 */
public class Llamada implements Serializable {
    /* 
     * Implementación: Serializable
     * Todas las clases de un proyecto Web basado en sesiones deben ser serializadas 
     */

    private String usuario;             //Nombre del usuario
    private List<Producto> productos;   //Lista de productos disponibles
    private List<Producto> carrito;     //Lista de productos en el carrito de compras
    private Cliente cliente;            //Cliente, según modelo Cliente.java
    private String categoria;           //Categoría de la llamada, según tabla 'arttime'.'categoriacall'
    private String definicion;          //Definición de la llamada, según tabla 'arttime'.'definicioncall'
    private Date fecha;                 //Fecha de la llamada
    private Time hora;                  //Hora de inicio de la llamada
    private String status;              //Estado de la llamada
    private String codigoLlamada;       //Código de la llamada
    private double totalVenta;          //Total de la venta o pedido en la llamada
    private Time horaFin;               //Hora de fin de la llamada
    private int numeroOrden;            //Número de orden de pedido o venta de la llamada
    private String observacion;         //Observaciones de la llamada

    /**
     * Obtiene el nombre del usuario
     *
     * @return String: Nombre del usuario
     */
    public String getUsuario() {
        /* Todo se maneja en mayusculas */
        if (null != usuario) {
            usuario = usuario.toUpperCase();
        }

        return usuario;
    }

    /**
     * Establece el nombre del usuario
     *
     * @param usuario String: Nombre del usuario
     */
    public void setUsuario(String usuario) {
        /* Todo se maneja en mayusculas */
        if (null != usuario) {
            usuario = usuario.toUpperCase();
        }

        this.usuario = usuario;
    }

    /**
     * Obtiene la lista de productos disponibles
     *
     * @return List::Producto:: : Lista de productos disponibles
     * @see Producto
     */
    public List<Producto> getProductos() {
        /* Ordenar la lista de productos */
        try {
            Collections.sort(productos);
        } catch (Exception e) {
        }

        return productos;
    }

    /**
     * Establece la lista de productos disponibles
     *
     * @param productos List::Producto:: : Lista de productos disponibles
     * @see Producto
     */
    public void setProductos(List<Producto> productos) {
        /* Ordenar la lista de productos */
        try {
            Collections.sort(productos);
        } catch (Exception e) {
        }

        this.productos = productos;
    }

    /**
     * Obtiene el cliente
     *
     * @return Cliente: Cliente
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
     * Obtiene la categoría de la llamada
     *
     * @return String: Categoría de la llamada
     */
    public String getCategoria() {
        /* Todo se maneja en mayusculas */
        if (null != categoria) {
            categoria = categoria.toUpperCase();
        }

        return categoria;
    }

    /**
     * Establece la categoría de la llamada
     *
     * @param categoria String: Categoría de la llamada
     */
    public void setCategoria(String categoria) {
        /* Todo se maneja en mayusculas */
        if (null != categoria) {
            categoria = categoria.toUpperCase();
        }

        this.categoria = categoria;
    }

    /**
     * Obtiene la definición de la llamada
     *
     * @return String: Definición de la llamada
     */
    public String getDefinicion() {
        /* Todo se maneja en mayusculas */
        if (null != definicion) {
            definicion = definicion.toUpperCase();
        }

        return definicion;
    }

    /**
     * Establece la definición de la llamada
     *
     * @param definicion String: Definición de la llamada
     */
    public void setDefinicion(String definicion) {
        /* Todo se maneja en mayusculas */
        if (null != definicion) {
            definicion = definicion.toUpperCase();
        }

        this.definicion = definicion;
    }

    /**
     * Obtiene la fecha de la llamada
     *
     * @return Date: Fecha de la llamada
     */
    public Date getFecha() {
        return fecha;
    }

    /**
     * Establece la fecha de la llamada
     *
     * @param fecha Date: Fecha de la llamada
     */
    public void setFecha(Date fecha) {
        this.fecha = fecha;
    }

    /**
     * Obtiene la hora de inicio de la llamada
     *
     * @return Time: Hora de inicio de la llamada
     */
    public Time getHora() {
        return hora;
    }

    /**
     * Establece la hora de inicio de la llamada
     *
     * @param hora Time: Hora de inicio de la llamada
     */
    public void setHora(Time hora) {
        this.hora = hora;
    }

    /**
     * Obtiene el estado de la llamada
     *
     * @return String: Estado de la llamada
     */
    public String getStatus() {
        /* Todo se maneja en mayusculas */
        if (null != status) {
            status = status.toUpperCase();
        }

        return status;
    }

    /**
     * Establece el estado de la llamada
     *
     * @param status String: Estado de la llamada
     */
    public void setStatus(String status) {
        /* Todo se maneja en mayusculas */
        if (null != status) {
            status = status.toUpperCase();
        }

        this.status = status;
    }

    /**
     * Obtiene la lista de productos del carrito de compras
     *
     * @return List::Producto:: : Lista de productos del carrito de compras
     * @see Producto
     */
    public List<Producto> getCarrito() {
        /* Ordenar el carrito de compras */
        try {
            Collections.sort(carrito);
        } catch (Exception e) {
        }

        return carrito;
    }

    /**
     * Establece la lista de productos del carrito de compras
     *
     * @param carrito List::Producto:: : Lista de productos del carrito de
     * compras
     * @see Producto
     */
    public void setCarrito(List<Producto> carrito) {
        /* Ordenar el carrito de compras */
        try {
            Collections.sort(carrito);
        } catch (Exception e) {
        }

        this.carrito = carrito;
    }

    /**
     * Obtiene el código de la llamada
     *
     * @return String: Código de la llamada
     */
    public String getCodigoLlamada() {
        /* Todo se maneja en mayusculas */
        if (null != codigoLlamada) {
            codigoLlamada = codigoLlamada.toUpperCase();
        }
        return codigoLlamada;
    }

    /**
     * Establece el código de la llamada
     *
     * @param codigoLlamada String: Código de la llamada
     */
    public void setCodigoLlamada(String codigoLlamada) {
        /* Todo se maneja en mayusculas */
        if (null != codigoLlamada) {
            codigoLlamada = codigoLlamada.toUpperCase();
        }
        this.codigoLlamada = codigoLlamada;
    }

    /**
     * Obtiene el total de la venta o pedido
     *
     * @return double: Total de la venta o pedido
     */
    public double getTotalVenta() {
        return totalVenta;
    }

    /**
     * Establece el total de la venta o pedido
     *
     * @param totalVenta double: Total de la venta o pedido
     */
    public void setTotalVenta(double totalVenta) {
        this.totalVenta = totalVenta;
    }

    /**
     * Obtiene la hora de fin de la llamada
     *
     * @return Time: Hora de fin de la llamada
     */
    public Time getHoraFin() {
        return horaFin;
    }

    /**
     * Establece la hora de fin de la llamada
     *
     * @param horaFin Time: Hora de fin de la llamada
     */
    public void setHoraFin(Time horaFin) {
        this.horaFin = horaFin;
    }

    /**
     * Obtiene el número de la orden del pedido o venta
     *
     * @return int: Número de orden de pedido o venta
     */
    public int getNumeroOrden() {
        return numeroOrden;
    }

    /**
     * Establece el número de la orden del pedido o venta
     *
     * @param numeroOrden int: Número de orden del pedido o venta
     */
    public void setNumeroOrden(int numeroOrden) {
        this.numeroOrden = numeroOrden;
    }

    /**
     * Obtiene las observaciones de la llamada
     *
     * @return String: Observaciones de la llamada
     */
    public String getObservacion() {
        /* Todo se maneja en mayusculas */
        if (null != observacion) {
            observacion = observacion.toUpperCase();
        }
        return observacion;
    }

    /**
     * Establece las observaciones de la llamada
     *
     * @param observacion String: Observaciones de la llamada
     */
    public void setObservacion(String observacion) {
        /* Todo se maneja en mayusculas */
        if (null != observacion) {
            observacion = observacion.toUpperCase();
        }
        this.observacion = observacion;
    }
}
