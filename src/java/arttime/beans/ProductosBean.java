/*
 * Software desarrollado bajo requerimientos de ByPhone,
 * entregado con licencia GNU GPL Version 3
 * Para más información sobre la licencia visitar http://www.gnu.org/licenses/gpl-3.0.html
 */
package arttime.beans;

import arttime.beans.models.ProductoModel;
import arttime.callmanagement.CallManager;
import arttime.connection.DBConnection;
import arttime.model.Categoria;
import arttime.model.Llamada;
import arttime.model.Producto;
import arttime.security.SecurityTaskManager;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Iterator;
import java.util.List;
import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.context.FacesContext;
import javax.servlet.http.HttpServletRequest;

/**
 * Bean administrado para la GUI de información de productos: productos.xhtml
 *
 * @author Daniel Gonzabay
 * @version 1.0.3
 */
@ManagedBean
public class ProductosBean implements Serializable {
    /* 
     * Implementación: Serializable
     * Todas las clases de un proyecto Web basado en sesiones deben ser serializadas 
     */

    private String selectedProdCategory;                        //Categoría de productos seleccionada *Las categorías salieron de uso por definicion del proyecto
    private List<Categoria> categorias;                         //Lista de categorías de productos *Las categorías salieron de uso por definicion del proyecto
    private List<String> nombresCategorias;                     //Lista de nombres de categorías *Las categorías salieron de uso por definicion del proyecto
    private List<Producto> productos;                           //Lista de productos disponibles
    private List<Producto> carrito = new ArrayList<Producto>(); //Lista de productos en el carrito de compras
    private double totalCarrito = 0;                            //Total del pedido en el carrito de compras
    private Producto selectedProduct;                           //Producto seleccionado en todos los productos
    private Producto carritoProduct;                            //Producto selleccionado en el carrito de compras
    private ProductoModel productoModel;                        //Modelo de selección de todos los productos
    private ProductoModel carritoModel;                         //Modelo de selección del carrito de compras
    private Llamada call;                                       //Llamada actual

    /**
     * Crea una nueva instancia de la clase e inicializa variables
     */
    public ProductosBean() {
        //cargarCategorias();                       //Comentado por: Las categorías salieron de uso por definicion del proyecto
        //nombresCategorias = parseCategorias();    //Comentado por: Las categorías salieron de uso por definicion del proyecto

        /* Obtener la dirección IP */
        String ipAddress = ((HttpServletRequest) FacesContext.getCurrentInstance().getExternalContext().getRequest()).getRemoteAddr();

        /* Obtener la llamada activa */
        call = new CallManager().getActiveCall(new SecurityTaskManager().getUserName(ipAddress));

        /* Cargar la lista de productos */
        cargarProductos();

        /* Establecer los modelos de seleccion para las listas de productos */
        productoModel = new ProductoModel(productos);
        carritoModel = new ProductoModel(carrito);
    }

    /**
     * Carga las categorías de productos
     *
     * @deprecated Obsoleto: Las categorías salieron de uso por definicion del
     * proyecto, debido a que existe una sola categoría: Hogar
     */
    @Deprecated
    private void cargarCategorias() {
        categorias = new DBConnection().obtenerCategorias();
    }

    /**
     * Carga las listas de productos
     */
    private void cargarProductos() {
        //productos = new DBConnection().obtenerProductos(selectedProdCategory);    //Comentado por: Las categorías salieron de uso por definicion del proyecto
        productos = call.getProductos();
        carrito = call.getCarrito();
    }

    /**
     * Redirecciona a la GUI de registro de cliente: cliente.xhtml
     */
    public void completarVenta() {
        if (validarCarrito()) {
            try {
                FacesContext.getCurrentInstance().getExternalContext().redirect("./cliente.xhtml");
            } catch (Exception exc) {
            }
        }
    }

    /**
     * Valida el carrito de compras: cantidades contra stock
     *
     * @return boolean: Verdadero si el carrito es válido
     */
    public boolean validarCarrito() {
        boolean result = true;

        Iterator iterator = carrito.iterator();

        while (iterator.hasNext()) {
            Producto producto = (Producto) iterator.next();
            if (producto.getCantidad() > new DBConnection().obtenerStock(producto.getIdProducto())) {
                result = false;
                FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_FATAL, "El producto: " + producto.getNombreProducto() + " tiene una cantidad mayor al stock actual", "Carrito erroneo"));
            }
            if (producto.getCantidad() < 0) {
                result = false;
                FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_FATAL, "El producto: " + producto.getNombreProducto() + " tiene una cantidad menor que 0", "Carrito erroneo"));
            }
        }

        return result;
    }

    /**
     * Agrega el producto seleccionado al carrito de compras
     */
    public void addToCart() {
        /* Agregar el producto al carrito de compras */
        if (null != carrito && null != productos && null != selectedProduct) {
            if (!carrito.contains(selectedProduct)) {
                carrito.add(selectedProduct);
                productos.remove(selectedProduct);
            }
        }

        /* Agregar o quitar el costo de envío*/
        int productCounter = 0;
        Iterator iterator = carrito.iterator();
        boolean contieneCosto = false;

        while (iterator.hasNext()) {
            Producto producto = (Producto) iterator.next();

            if (producto.getNombreProducto().equals("COSTO DE ENVIO")) {
                if (producto.getCantidad() != 1) {
                    producto.setCantidad(1);
                }
                contieneCosto = true;
            }

            productCounter += producto.getCantidad();
        }

        if (productCounter == 1 && !contieneCosto) {
            Producto costoenvio = new DBConnection().obtenerCostoEnvio();
            costoenvio.setCantidad(1);

            if (null != costoenvio) {

                carrito.add(costoenvio);

                Iterator iterator1 = productos.iterator();

                while (iterator1.hasNext()) {
                    Producto producto1 = (Producto) iterator1.next();

                    if (producto1.getNombreProducto().equals("COSTO DE ENVIO")) {
                        productos.remove(producto1);
                        break;
                    }
                }
            }
        }

        if (productCounter > 2 && contieneCosto) {
            Iterator iterator2 = carrito.iterator();

            while (iterator2.hasNext()) {
                Producto producto2 = (Producto) iterator2.next();

                if (producto2.getNombreProducto().equals("COSTO DE ENVIO")) {
                    carrito.remove(producto2);
                    productos.add(producto2);
                    break;
                }
            }
        }
    }

    /**
     * Quita el producto seleccionado del carrito de compras
     */
    public void removeFromCart() {
        if (null != carrito && null != productos && null != carritoProduct) {
            if (!productos.contains(carritoProduct)) {
                productos.add(carritoProduct);
                carrito.remove(carritoProduct);
            }
        }

        /* Agregar o quitar el costo de envío */
        int productCounter = 0;
        Iterator iterator = carrito.iterator();
        boolean contieneCosto = false;

        while (iterator.hasNext()) {
            Producto producto = (Producto) iterator.next();

            if (producto.getNombreProducto().equals("COSTO DE ENVIO")) {
                if (producto.getCantidad() != 1) {
                    producto.setCantidad(1);
                }
                contieneCosto = true;
            }

            productCounter += producto.getCantidad();
        }

        if (productCounter == 1 && !contieneCosto) {
            Producto costoenvio = new DBConnection().obtenerCostoEnvio();
            costoenvio.setCantidad(1);

            if (null != costoenvio) {

                carrito.add(costoenvio);

                Iterator iterator1 = productos.iterator();

                while (iterator1.hasNext()) {
                    Producto producto1 = (Producto) iterator1.next();

                    if (producto1.getNombreProducto().equals("COSTO DE ENVIO")) {
                        productos.remove(producto1);
                        break;
                    }
                }
            }
        }

        if (productCounter > 2 && contieneCosto) {
            Iterator iterator2 = carrito.iterator();

            while (iterator2.hasNext()) {
                Producto producto2 = (Producto) iterator2.next();

                if (producto2.getNombreProducto().equals("COSTO DE ENVIO")) {
                    carrito.remove(producto2);
                    productos.add(producto2);
                    break;
                }
            }
        }
    }

    /**
     * Convierte la lista de categorias en una lista String
     *
     * @return Lista de categorias
     * @deprecated Obsoleto: Las categorías salieron de uso por definicion del
     * proyecto, debido a que existe una sola categoría: Hogar
     */
    @Deprecated
    private List<String> parseCategorias() {
        List<String> result = new ArrayList<String>();

        Iterator iterator = categorias.iterator();

        while (iterator.hasNext()) {
            result.add(((Categoria) iterator.next()).getNombreCategoria());
        }

        return result;
    }

    /**
     * Obtiene la lista de categorías de productos
     *
     * @return List::Categoria:: : Lista de categorías de productos
     * @deprecated Obsoleto: Las categorías salieron de uso por definicion del
     * proyecto, debido a que existe una sola categoría: Hogar
     * @see Categoria
     */
    @Deprecated
    public List<Categoria> getCategorias() {
        return categorias;
    }

    /**
     * Establece la lista de categorías de productos
     *
     * @param categorias List::Categoria:: : Lista de categorías de productos
     * @deprecated Obsoleto: Las categorías salieron de uso por definicion del
     * proyecto, debido a que existe una sola categoría: Hogar
     * @see Categoria
     */
    @Deprecated
    public void setCategorias(List<Categoria> categorias) {
        this.categorias =
                categorias;
    }

    /**
     * Obtiene la lista de nombres de las categorías de productos
     *
     * @return List::String:: : Lista de nombres de categorías de productos
     * @deprecated Obsoleto: Las categorías salieron de uso por definicion del
     * proyecto, debido a que existe una sola categoría: Hogar
     */
    @Deprecated
    public List<String> getNombresCategorias() {
        return nombresCategorias;
    }

    /**
     * Establece la lista de nombres de las categorías de productos
     *
     * @param nombresCategorias List::String:: : Lista de nombres de categorías
     * de productos
     * @deprecated Obsoleto: Las categorías salieron de uso por definicion del
     * proyecto, debido a que existe una sola categoría: Hogar
     */
    @Deprecated
    public void setNombresCategorias(List<String> nombresCategorias) {
        this.nombresCategorias = nombresCategorias;
    }

    /**
     * Ordena y obtiene la lista de productos disponibles
     *
     * @return Lis::Producto:: : Lista de productos disponibles
     * @see Producto
     */
    public List<Producto> getProductos() {
        /* Ordenar productos */
        try {
            Collections.sort(productos);
        } catch (Exception e) {
        }
        return productos;
    }

    /**
     * Establece la lista de productos disponibles
     *
     * @param productos Lis::Producto:: : Lista de productos disponibles
     * @see Producto
     */
    public void setProductos(List<Producto> productos) {
        try {
            Collections.sort(productos);
        } catch (Exception e) {
        }
        this.productos = productos;
    }

    /**
     * Obtiene la categoría de productos seleccionada
     *
     * @return String: Categoría de productos seleccionada
     * @deprecated Obsoleto: Las categorías salieron de uso por definicion del
     * proyecto, debido a que existe una sola categoría: Hogar
     */
    @Deprecated
    public String getSelectedProdCategory() {
        return selectedProdCategory;
    }

    /**
     * Establece la categoría de productos seleccionada
     *
     * @param selectedProdCategory String: Categoría de productos seleccionada
     * @deprecated Obsoleto: Las categorías salieron de uso por definicion del
     * proyecto, debido a que existe una sola categoría: Hogar
     */
    @Deprecated
    public void setSelectedProdCategory(String selectedProdCategory) {
        this.selectedProdCategory = selectedProdCategory;
    }

    /**
     * Obtiene el producto seleccionado de todos los productos
     *
     * @return Producto: Producto seleccionado
     * @see Producto
     */
    public Producto getSelectedProduct() {
        return selectedProduct;
    }

    /**
     * Establece el producto seleccionado de todos los productos
     *
     * @param selectedProduct Producto: Producto seleccionado
     */
    public void setSelectedProduct(Producto selectedProduct) {
        this.selectedProduct = selectedProduct;
    }

    /**
     * Obtiene el modelo de selección de producto de todos los productos
     *
     * @return ProductoModel: Modelo de selección del modelo Producto
     * @see ProductoModel
     * @see Producto
     */
    public ProductoModel getProductoModel() {
        return productoModel;
    }

    /**
     * Establece el modelo de selección de producto de todos los productos
     *
     * @param productoModel ProductoModel: Modelo de selección del modelo
     * Producto
     * @see ProductoModel
     * @see Producto
     */
    public void setProductoModel(ProductoModel productoModel) {
        this.productoModel = productoModel;
    }

    /**
     * Obtiene la lista de productos del carrito de compras
     *
     * @return List::Producto:: : Lista de productos del carrito de compras
     * @see Producto
     */
    public List<Producto> getCarrito() {
        /* Ordenar el carrito */
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
        /* Ordenar el carrito */
        try {
            Collections.sort(carrito);
        } catch (Exception e) {
        }
        this.carrito = carrito;
    }

    /**
     * Calcula y obtiene el total del carrito de compras
     *
     * @return double: Total del carrito de compras
     */
    public double getTotalCarrito() {
        /* Calcular total del carrito */
        totalCarrito = 0;

        Iterator iterator = carrito.iterator();

        while (iterator.hasNext()) {
            totalCarrito += ((Producto) iterator.next()).getSubtotal();
        }

        String rounder = "" + totalCarrito;
        int dotPos = rounder.indexOf(".");
        if (dotPos > 0) {
            try {
                rounder = rounder.substring(0, dotPos + 3);
                totalCarrito = Double.parseDouble(rounder);
            } catch (IndexOutOfBoundsException ioobe) {
            } catch (NumberFormatException nfe) {
            }
        }

        /* Establecer el total del carrito como total de la venta */
        call.setTotalVenta(totalCarrito);

        return totalCarrito;
    }

    /**
     * Establece el total del carrito de compras
     *
     * @param totalCarrito double: Total del carrito de compras
     */
    public void setTotalCarrito(double totalCarrito) {
        this.totalCarrito = totalCarrito;
    }

    /**
     * Obtiene el producto seleccionado del carrito de compras
     *
     * @return Producto: Producto seleccionado del carrito de compras
     * @see Producto
     */
    public Producto getCarritoProduct() {
        return carritoProduct;
    }

    /**
     * Establece el producto seleccionado del carrito de compras
     *
     * @param carritoProduct Producto: Producto seleccionado del carrito de
     * compras
     * @see Producto
     */
    public void setCarritoProduct(Producto carritoProduct) {
        this.carritoProduct = carritoProduct;
    }

    /**
     * Obtiene el modelo de selección de producto del carrito de compras
     *
     * @return ProductoModel: Modelo de selección del modelo Producto
     * @see ProductoModel
     * @see Producto
     */
    public ProductoModel getCarritoModel() {
        return carritoModel;
    }

    /**
     * Establece el modelo de selección de producto del carrito de compras
     *
     * @param carritoModel ProductoModel: Modelo de selección del modelo
     * Producto
     * @see ProductoModel
     * @see Producto
     */
    public void setCarritoModel(ProductoModel carritoModel) {
        this.carritoModel = carritoModel;
    }
}