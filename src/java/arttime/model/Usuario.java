/*
 * Software desarrollado bajo requerimientos de ByPhone,
 * entregado con licencia GNU GPL Version 3
 * Para más información sobre la licencia visitar http://www.gnu.org/licenses/gpl-3.0.html
 */
package arttime.model;

import java.io.Serializable;

/**
 * Modelo para un usuario, Referencia tabla 'arttime'.'usuarios'
 *
 * @author Daniel Gonzabay
 * @version 1.0.3
 */
public class Usuario implements Serializable {
    /* 
     * Implementación: Serializable
     * Todas las clases de un proyecto Web basado en sesiones deben ser serializadas 
     */

    private int userId;         //Identificador del usuario
    private String userName;    //Nombre del usuario
    private String fullName;    //Nombre completo del usuario
    private String passWord;    //Contraseña del usuario
    private int accessLevel;    //Nivel de acceso del usuario: 1. Usuario Común - 2. Supervisor - 6. Administrador
    private boolean logedIn;    //Bandera si ha iniciado sesión

    /**
     * Obtiene el nivel de acceso 1: Usuario Común 2: Supervisor 6:
     * Administrador
     *
     * @return int: Nivel de acceso
     */
    public int getAccessLevel() {
        return accessLevel;
    }

    /**
     * Establece el nivel de acceso 1: Usuario Común 2: Supervisor 6:
     * Administrador
     *
     * @param accessLevel int: Nivel de acceso
     */
    public void setAccessLevel(int accessLevel) {
        this.accessLevel = accessLevel;
    }

    /**
     * Obtiene el nombre completo del usuario
     *
     * @return String: Nombre completo del usuario
     */
    public String getFullName() {
        /* Todo se maneja en mayusculas, excepto las contraseñas */
        if (null != fullName) {
            fullName = fullName.toUpperCase();
        }
        return fullName;
    }

    /**
     * Establece el nombre completo del usuario
     *
     * @param fullName String: Nombre completo del usuario
     */
    public void setFullName(String fullName) {
        /* Todo se maneja en mayusculas, excepto las contraseñas */
        if (null != fullName) {
            fullName = fullName.toUpperCase();
        }
        this.fullName = fullName;
    }

    /**
     * Obtiene si el usuario ha iniciado sesión
     *
     * @return boolean: Verdadero si el usuario ha iniciado sesión
     */
    public boolean isLogedIn() {
        return logedIn;
    }

    /**
     * Establece si el usuario ha iniciado sesión
     *
     * @param logedIn boolean: Verdadero si el usuario ha iniciado sesión
     */
    public void setLogedIn(boolean logedIn) {
        this.logedIn = logedIn;
    }

    /**
     * Obtiene el nombre del usuario
     *
     * @return String: Nombre del usuario
     */
    public String getUserName() {
        /* Todo se maneja en mayusculas, excepto las contraseñas */
        if (null != userName) {
            userName = userName.toUpperCase();
        }
        return userName;
    }

    /**
     * Establece el nombre del usuario
     *
     * @param userName String: Nombre del usuario
     */
    public void setUserName(String userName) {
        /* Todo se maneja en mayusculas, excepto las contraseñas */
        if (null != userName) {
            userName = userName.toUpperCase();
        }
        this.userName = userName;
    }

    /**
     * Obtiene la contraseña del usuario
     *
     * @return String: Contraseña del usuario
     */
    public String getPassWord() {
        return passWord;
    }

    /**
     * Establece la contraseña del usuario
     *
     * @param passWord String: Contraseña del usuario
     */
    public void setPassWord(String passWord) {
        this.passWord = passWord;
    }

    /**
     * Obtiene el ID del usuario
     *
     * @return int: ID del usuario
     */
    public int getUserId() {
        return userId;
    }

    /**
     * Establece el ID del usuario
     *
     * @param userId int: ID del usuario
     */
    public void setUserId(int userId) {
        this.userId = userId;
    }
}
