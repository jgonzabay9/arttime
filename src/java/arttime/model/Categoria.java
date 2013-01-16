/*
 * Software desarrollado bajo requerimientos de ByPhone,
 * entregado con licencia GNU GPL Version 3
 * Para más información sobre la licencia visitar http://www.gnu.org/licenses/gpl-3.0.html
 */
package arttime.model;

import java.io.Serializable;

/**
 * Modelo para las categorías de los productos, referencia tabla
 * 'arttime'.'categoria'
 *
 * @author Daniel Gonzabay
 * @deprecated Obsoleto: Solo existe una categoría, desaparece esta entidad por
 * definiciones en el proyecto
 */
@Deprecated
public class Categoria implements Serializable {
    /* 
     * Implementación: Serializable
     * Todas las clases de un proyecto Web basado en sesiones deben ser serializadas 
     */

    private int idCategoria;        //ID de la categoría
    private String nombreCategoria; //Nombre de la categoría

    /**
     * Obtiene el ID de la categoría
     * @return int: ID de la categoría
     * @deprecated Obsoleto: Las categorías salieron de uso por definicion del proyecto
     */
    @Deprecated
    public int getIdCategoria() {
        return idCategoria;
    }

    /**
     * Establece el ID de la categoría
     * @param idCategoria int: ID de la categoría
     * @deprecated Obsoleto: Las categorías salieron de uso por definicion del proyecto
     */
    @Deprecated
    public void setIdCategoria(int idCategoria) {
        this.idCategoria = idCategoria;
    }

    /**
     * Obtiene el nombre de la categoría
     * @return String: Nombre de la categoría
     * @deprecated Obsoleto: Las categorías salieron de uso por definicion del proyecto
     */
    @Deprecated
    public String getNombreCategoria() {
        return nombreCategoria;
    }

    /**
     * Establece el nombre de la categoría
     * @param nombreCategoria String: Nombre de la categoría
     * @deprecated Obsoleto: Las categorías salieron de uso por definicion del proyecto
     */
    @Deprecated
    public void setNombreCategoria(String nombreCategoria) {
        this.nombreCategoria = nombreCategoria;
    }

    /**
     * Retorna un valor String que representa al objeto de la clase
     * @return String: Clase.toString()
     * @deprecated Obsoleto: Las categorías salieron de uso por definicion del proyecto
     */
    @Deprecated
    @Override
    public String toString() {
        return nombreCategoria;
    }
}
