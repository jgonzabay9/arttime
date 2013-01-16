/*
 * Software desarrollado bajo requerimientos de ByPhone,
 * entregado con licencia GNU GPL Version 3
 * Para más información sobre la licencia visitar http://www.gnu.org/licenses/gpl-3.0.html
 */
package arttime.beans.models;

import arttime.model.Producto;
import java.io.Serializable;
import java.util.List;
import javax.faces.model.ListDataModel;
import org.primefaces.model.SelectableDataModel;

/**
 * Modelo de selección de productos para el modelo Producto, para selección
 * desde objetos visuales JSF PrimeFaces en las GUIs
 *
 * @author Daniel Gonzabay
 * @version 1.0.1
 * @see Producto
 */
public class ProductoModel extends ListDataModel<Producto> implements SelectableDataModel<Producto>, Serializable {
    /* 
     * Implementación: Serializable
     * Todas las clases de un proyecto Web basado en sesiones deben ser serializadas 
     * 
     * Implementación: SelectableDataModel<P>
     * Permite implementar las funcionalidades necesarias para que un objeto modelo sea 
     * seleccionable en un objeto gráfico JSF PrimeFaces en una GUI
     */

    /**
     * Permite crear una instancia en blanco de la clase
     */
    public ProductoModel() {
    }

    /**
     * Crea una nueva instancia de la clase, y llama a la superclase
     * SelectableDataModel
     *
     * @param data List::Producto:: : Lista de productos seleccionables
     * @see Producto
     */
    public ProductoModel(List<Producto> data) {
        super(data);
    }

    /**
     * Obtiene la clave primaria de un modelo Producto
     *
     * @param t Producto: Producto del que se obtendrá la clave
     * @return Object: Clave primaria del producto
     * @see Producto
     */
    @Override
    public Object getRowKey(Producto t) {
        return t.getIdProducto();
    }

    /**
     * Obtiene los datos de un producto
     *
     * @param string String: Clave primaria del producto seleccionado
     * @return Producto: Producto seleccionado
     * @see Producto
     */
    @Override
    public Producto getRowData(String string) {
        List<Producto> productos = (List<Producto>) getWrappedData();

        for (Producto producto : productos) {
            if (("" + producto.getIdProducto()).equals(string)) {
                return producto;
            }
        }

        return null;
    }
}
