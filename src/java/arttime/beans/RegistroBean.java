/*
 * Software desarrollado bajo requerimientos de ByPhone,
 * entregado con licencia GNU GPL Version 3
 * Para más información sobre la licencia visitar http://www.gnu.org/licenses/gpl-3.0.html
 */
package arttime.beans;

import arttime.callmanagement.CallManager;
import arttime.connection.DBConnection;
import arttime.model.Llamada;
import arttime.security.SecurityTaskManager;
import arttime.util.PdfGenerator;
import java.io.IOException;
import java.io.Serializable;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.faces.bean.ManagedBean;
import javax.faces.context.FacesContext;
import javax.servlet.http.HttpServletRequest;

/**
 * Bean administrado para la GUI de registro de llamadas: registro.xhtml
 *
 * @author Daniel Gonzabay
 * @version 1.0.5
 */
@ManagedBean
public class RegistroBean implements Serializable {
    /* 
     * Implementación: Serializable
     * Todas las clases de un proyecto Web basado en sesiones deben ser serializadas 
     */

    private List<String> categorias;    //Lista de categorías de llamadas
    private List<String> definiciones;  //Lista de definiciones de llamadas
    private String selectedCategoria;   //Categoría de llamadas seleccionada
    private String selectedDefinicion;  //Definición de llamadas seleccionada
    private Llamada call;               //Llamada actual

    /**
     * Crea una nueva instancia de la clases e inicializa las variables
     */
    public RegistroBean() {
        categorias = new DBConnection().obtenerCategoriasCall();
        String ipAddress = ((HttpServletRequest) FacesContext.getCurrentInstance().getExternalContext().getRequest()).getRemoteAddr();
        call = new CallManager().getActiveCall(new SecurityTaskManager().getUserName(ipAddress));
        if (null != call.getCategoria() && !call.getCategoria().trim().equals("")) {
            selectedCategoria = call.getCategoria();
            if (null != selectedCategoria && !selectedCategoria.trim().equals("")) {
                definiciones = new DBConnection().obtenerDefinicionCall(selectedCategoria);
            }
        }
        if (null != call.getDefinicion() && !call.getDefinicion().trim().equals("")) {
            selectedDefinicion = call.getDefinicion();
        }
    }

    /**
     * Maneja el cambio de categoría de llamadas, cuando se selecciona una nueva
     * categoría se deben cargar las definiciones para esa categoría
     */
    public void onCategoriaChange() {
        if (null != selectedCategoria && !selectedCategoria.trim().equals("")) {
            definiciones = new DBConnection().obtenerDefinicionCall(selectedCategoria);
            call.setCategoria(selectedCategoria);
        }
    }

    /**
     * Maneja el cambio de definición de llamadas
     */
    public void onDefinicionChange() {
        if (null != selectedDefinicion && !selectedDefinicion.trim().equals("")) {
            call.setDefinicion(selectedDefinicion);
        }
    }

    /**
     * Registra la llamada en la base de datos, genera el reporte y cierra la
     * llamada
     */
    public void guardarRegistroLlamada() {
        call.setCategoria(selectedCategoria);
        call.setDefinicion(selectedDefinicion);
        call.setHoraFin(new DBConnection().obtenerHora());

        if (selectedCategoria.equals("VENTAS")) {
            /* Si se trata de una venta, asignar un número de orden */
            call.setNumeroOrden(new DBConnection().obtenerNumeroOrden());
        }

        /* Registrar la llamada en la base de datos*/
        new DBConnection().guardarLlamada(call);
        /* Generar reporte de pedido o venta */
        new PdfGenerator().generarPedido(call);
        /* Cerrar llamada*/
        new CallManager().closeCall(call);

        try {
            /* Regresar a la página de bienvenida */
            FacesContext.getCurrentInstance().getExternalContext().redirect("./welcome.xhtml");
        } catch (IOException ex) {
            Logger.getLogger(ClienteBean.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    /**
     * Obtiene las categorías de llamadas
     *
     * @return List::String:: : Lista de categorías de llamadas
     */
    public List<String> getCategorias() {
        return categorias;
    }

    /**
     * Establece las categorías de llamadas
     *
     * @param categorias List::String:: : Lista de categorías de llamadas
     */
    public void setCategorias(List<String> categorias) {
        this.categorias = categorias;
    }

    /**
     * Obtiene las definciones de llamadas
     *
     * @return List::String:: : Definiciones de llamadas
     */
    public List<String> getDefiniciones() {
        return definiciones;
    }

    /**
     * Establece las definiciones de llamadas
     *
     * @param definiciones List::String:: : Definiciones de llamadas
     */
    public void setDefiniciones(List<String> definiciones) {
        this.definiciones = definiciones;
    }

    /**
     * Obtiene la categoría de llamadas seleccionada
     *
     * @return String: Categoría de llamadas seleccionada
     */
    public String getSelectedCategoria() {
        return selectedCategoria;
    }

    /**
     * Establece la categoría de llamadas seleccionada
     *
     * @param selectedCategoria String: Categoría de llamadas seleccionada
     */
    public void setSelectedCategoria(String selectedCategoria) {
        this.selectedCategoria = selectedCategoria;
        call.setCategoria(selectedCategoria);
    }

    /**
     * Obtiene la definición de llamadas seleccionada
     *
     * @return String: Definición de llamadas seleccionada
     */
    public String getSelectedDefinicion() {
        call.setDefinicion(selectedDefinicion);
        return selectedDefinicion;
    }

    /**
     * Establece la definición de llamadas seleccionada
     *
     * @param selectedDefinicion String: Definición de llamadas seleccionada
     */
    public void setSelectedDefinicion(String selectedDefinicion) {
        this.selectedDefinicion = selectedDefinicion;
    }

    /**
     * Obtiene la llamada actual
     *
     * @return Llamada: Llamada actual
     * @see Llamada
     */
    public Llamada getCall() {
        return call;
    }

    /**
     * Establece la llamada actual
     *
     * @param call Llamada: Llamada actual
     * @see Llamada
     */
    public void setCall(Llamada call) {
        this.call = call;
    }
}
