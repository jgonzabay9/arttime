/*
 * Software desarrollado bajo requerimientos de ByPhone,
 * entregado con licencia GNU GPL Version 3
 * Para más información sobre la licencia visitar http://www.gnu.org/licenses/gpl-3.0.html
 */
package arttime.model;

import java.io.Serializable;

/**
 * Modelo para el producto, referencia tabla 'arttime'.'producto'
 *
 * @author Daniel Gonzabay
 * @version 1.0.2
 */
public class Producto implements Serializable, Comparable {
    /* 
     * Implementación: Serializable
     * Todas las clases de un proyecto Web basado en sesiones deben ser serializadas 
     * 
     * Implementación: Comparable
     * Permite que objetos de esta clase puedan ser comparados entre sí
     */

    private int idProducto;         //ID del producto
    private int idCategoria;        //ID de la categoría del producto
    private double valorUnitario;   //Valor unitario del producto
    private int stock;              //Stock del producto
    private String imagen;          //Path de la imagen del producto
    private String nombreProducto;  //Nombre o descripción del producto
    private String codigoProducto;  //Código del producto
    private String caracteristicas; //Características adicionales o descripción extensa del producto
    private double subtotal;        //Subtotal del producto en el pedido
    private int cantidad;           //Cantidad del producto en el pedido

    /**
     * Obtiene la URL de la imagen
     *
     * @return String: URL de la imagen
     */
    public String getImagen() {
        return imagen;
    }

    /**
     * Establece la URL de la imagen
     *
     * @param imagen String: URL de la imagen
     */
    public void setImagen(String imagen) {
        this.imagen = imagen;
    }

    /**
     * Obtiene el nombre del producto
     *
     * @return String: Nombre del producto
     */
    public String getNombreProducto() {
        return nombreProducto;
    }

    /**
     * Establece el nombre del producto
     *
     * @param nombreProducto String: Nombre del producto
     */
    public void setNombreProducto(String nombreProducto) {
        this.nombreProducto = nombreProducto;
    }

    /**
     * Obtiene el ID del producto
     *
     * @return int: ID del producto
     */
    public int getIdProducto() {
        return idProducto;
    }

    /**
     * Establece el ID del producto
     *
     * @param idProducto int: ID del producto
     */
    public void setIdProducto(int idProducto) {
        this.idProducto = idProducto;
    }

    /**
     * Obtiene el ID de la categoría del producto: 1. Hogar
     *
     * @return int: ID de la categoría del producto
     * @see Categoria
     */
    public int getIdCategoria() {
        return idCategoria;
    }

    /**
     * Establece la categoría del producto: 1. Hogar
     *
     * @param idCategoria int: ID de la categoría del producto
     * @see Categoria
     */
    public void setIdCategoria(int idCategoria) {
        this.idCategoria = idCategoria;
    }

    /**
     * Obtiene el Stock del producto
     *
     * @return int: Stock del producto
     */
    public int getStock() {
        return stock;
    }

    /**
     * Establece el stock del producto
     *
     * @param stock int: Stock del producto
     */
    public void setStock(int stock) {
        this.stock = stock;
    }

    /**
     * Obtiene el valor unitario del producto
     *
     * @return double: Valor unitario del producto
     */
    public double getValorUnitario() {
        return valorUnitario;
    }

    /**
     * Establece el valor unitario del producto
     *
     * @param valorUnitario double: Valor unitario del producto
     */
    public void setValorUnitario(double valorUnitario) {
        this.valorUnitario = valorUnitario;
    }

    /**
     * Obtiene el código del producto
     *
     * @return String: Código del producto
     */
    public String getCodigoProducto() {
        return codigoProducto;
    }

    /**
     * Establece el código del producto
     *
     * @param codigoProducto String: Código del producto
     */
    public void setCodigoProducto(String codigoProducto) {
        this.codigoProducto = codigoProducto;
    }

    /**
     * Obtiene las características del producto
     *
     * @return String: Características del producto
     */
    public String getCaracteristicas() {
        return caracteristicas;
    }

    /**
     * Establece las características del producto
     *
     * @param caracteristicas String: Características del producto
     */
    public void setCaracteristicas(String caracteristicas) {
        this.caracteristicas = caracteristicas;
    }

    /**
     * Obtiene la cantidad del producto en el pedido o venta
     *
     * @return int: Cantidad del producto en el pedido o venta
     */
    public int getCantidad() {
        return cantidad;
    }

    /**
     * Establece la cantidad del producto en el pedido o venta y calcula el
     * subtotal
     *
     * @param cantidad int: Cantidad del producto en el pedido o venta
     */
    public void setCantidad(int cantidad) {
        this.cantidad = cantidad;
        this.subtotal = this.valorUnitario * cantidad;
    }

    /**
     * Obtiene el subtotal del producto en el pedido o venta
     *
     * @return double: Subtotal del producto en el pedido o venta
     */
    public double getSubtotal() {
        String rounder = "" + subtotal;
        int dotPos = rounder.indexOf(".");
        if (dotPos > 0) {
            try {
                rounder = rounder.substring(0, dotPos + 3);
                subtotal = Double.parseDouble(rounder);
            } catch (IndexOutOfBoundsException ioobe) {
            } catch (NumberFormatException nfe) {
            }
        }
        return subtotal;
    }

    /**
     * Establece el subtotal del producto en el pedido o venta
     *
     * @param subtotal double: Subtotal del producto en el pedido o venta
     */
    public void setSubtotal(double subtotal) {
        this.subtotal = subtotal;
    }

    /**
     * Permite la comparación entre dos productos, para ordenarlos. Sobreescribe
     * el método Object.compareTo()
     *
     * @param o Objeto con el que se va a comparar
     * @return int: Resultado de la comparación
     * @see Object
     */
    @Override
    public int compareTo(Object o) {
        Producto producto = (Producto) o;

        if (this.getNombreProducto().compareToIgnoreCase(producto.getNombreProducto()) == 0) {
            return this.getCodigoProducto().compareToIgnoreCase(producto.getCodigoProducto());
        } else {
            return this.getNombreProducto().compareToIgnoreCase(producto.getNombreProducto());
        }
    }
}
