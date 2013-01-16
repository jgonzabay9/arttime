/*
 * Software desarrollado bajo requerimientos de ByPhone,
 * entregado con licencia GNU GPL Version 3
 * Para más información sobre la licencia visitar http://www.gnu.org/licenses/gpl-3.0.html
 */
package arttime.beans;

import arttime.connection.DBConnection;
import java.io.Serializable;
import java.util.List;
import javax.faces.bean.ManagedBean;

/**
 * Bean administrado para la GUI de inicio: index.xhtml
 *
 * @author Daniel Gonzabay
 * @version 1.0.2
 */
@ManagedBean
public class IndexBean implements Serializable {
    /* 
     * Implementación: Serializable
     * Todas las clases de un proyecto Web basado en sesiones deben ser serializadas 
     */

    private List<String> images;    //Lista de URLs de imágenes de productos

    /**
     * Crea una nueva instancia de la clase e inicializa variables
     */
    public IndexBean() {
        images = new DBConnection().obtenerImagenes();
    }

    /**
     * Obtiene la lista de URLs de imágenes
     *
     * @return List::String:: : Lista de URLs de imágenes
     */
    public List<String> getImages() {
        return images;
    }

    /**
     * Establece la lista de URLs de imágenes
     *
     * @param images List::String:: : Lista de URLs de imágenes
     */
    public void setImages(List<String> images) {
        this.images = images;
    }
}
