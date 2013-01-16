/*
 * Software desarrollado bajo requerimientos de ByPhone,
 * entregado con licencia GNU GPL Version 3
 * Para más información sobre la licencia visitar http://www.gnu.org/licenses/gpl-3.0.html
 */
package arttime.connection;

import arttime.model.Categoria;
import arttime.model.Cliente;
import arttime.model.Llamada;
import arttime.model.Producto;
import arttime.model.Usuario;
import java.io.Serializable;
import java.sql.*;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

/**
 * Clase para la conexión a la base de datos 'arttime'
 *
 * @author Daniel Gonzabay
 * @version 1.0.6
 */
public class DBConnection implements Serializable {
    /* 
     * Implementación: Serializable
     * Todas las clases de un proyecto Web basado en sesiones deben ser serializadas 
     */

    private static Connection con = null;                               //Conexión con la base de datos
    private static final String url = "jdbc:mysql://localhost:3306/";   //URL de acceso del JDBC
    private static final String dbName = "arttime";                     //Nombre de la base de datos
    private static final String driver = "com.mysql.jdbc.Driver";       //Driver de conexión
    private static final String userName = "root";                      //Nombre de usuario de la base de datos
    private static final String password = "root";                      //Contraseña del usuario de la base de datos

    /**
     * Realiza limpieza
     */
    public synchronized void cleanUp() {
        try {
            con.close();
        } catch (Exception e) {
        }
    }

    /**
     * Obtiene el producto 'COSTO DE ENVIO'
     *
     * @return Producto: Costo de envío
     * @see Producto
     */
    public synchronized Producto obtenerCostoEnvio() {
        try {
            /* Establecer la conexión */
            Class.forName(driver).newInstance();
            con = DriverManager.getConnection(url + dbName, userName, password);

            try {
                /* Obtener el producto y almacenarlo en el modelo Producto */
                Statement st = con.createStatement();

                ResultSet resProductos = st.executeQuery("SELECT * FROM producto WHERE descripcion = 'COSTO DE ENVIO'");

                if (resProductos.next()) {
                    Producto producto = new Producto();
                    producto.setIdCategoria(1);
                    producto.setIdProducto(resProductos.getInt(1));
                    producto.setNombreProducto(resProductos.getString("descripcion"));
                    producto.setImagen(resProductos.getString("urlimagen"));
                    producto.setValorUnitario(resProductos.getDouble("precio"));
                    producto.setStock(resProductos.getInt("stock"));
                    producto.setCodigoProducto(resProductos.getString("codigoproducto"));
                    producto.setCaracteristicas(resProductos.getString("caracteristicas"));

                    return producto;
                }
            } catch (SQLException s) {
                System.out.println("SQL code does not execute.");
            }

            con.close();
        } catch (Exception e) {
            e.printStackTrace();
        }

        return null;
    }

    /**
     * Obtiene las categorías de productos
     *
     * @return List::Categoria:: : Lista de categorías de productos
     * @deprecated Obsoleto: Las categorías salieron de uso por definicion del
     * proyecto
     * @see Categoria
     */
    @Deprecated
    public synchronized List<Categoria> obtenerCategorias() {
        try {
            /* Establecer la conexión */
            Class.forName(driver).newInstance();
            con = DriverManager.getConnection(url + dbName, userName, password);

            try {
                /* Obtener las categorías y almacenarlas en una lista del modelo Categoria */

                Statement st = con.createStatement();
                ResultSet resCategoria = st.executeQuery("SELECT * FROM categoria");

                List<Categoria> listaCategorias = new ArrayList<Categoria>();

                while (resCategoria.next()) {
                    Categoria categoria = new Categoria();
                    categoria.setIdCategoria(resCategoria.getInt(1));
                    categoria.setNombreCategoria(resCategoria.getString(2));

                    listaCategorias.add(categoria);
                }

                return listaCategorias;


            } catch (SQLException s) {
                System.out.println("SQL code does not execute.");
            }
            con.close();
        } catch (Exception e) {
            e.printStackTrace();
        }

        return null;
    }

    /**
     * Obtiene el stock de un producto por su ID
     *
     * @param idProducto int: ID del producto
     * @return int: Stock del producto
     */
    public synchronized int obtenerStock(int idProducto) {
        try {
            /* Establecer la conexión */
            Class.forName(driver).newInstance();
            con = DriverManager.getConnection(url + dbName, userName, password);

            try {
                /* Obtener el stock */
                Statement st = con.createStatement();

                ResultSet resProducto = st.executeQuery("SELECT stock FROM producto WHERE idproducto=" + idProducto);

                if (resProducto.next()) {
                    return resProducto.getInt(1);
                }
            } catch (SQLException s) {
                System.out.println("SQL code does not execute.");
            }

            con.close();
        } catch (Exception e) {
            e.printStackTrace();
        }

        return 0;
    }

    /**
     * Obtiene la lista de productos de la categoría establecida
     *
     * @param categoria String: Nombre de la categoría de los productos
     * @return List::Producto:: : Productos de la categoría establecida
     * @deprecated Obsoleto: Las categorías salieron de uso por definicion del
     * proyecto
     * @see Producto
     */
    @Deprecated
    public synchronized List<Producto> obtenerProductos(String categoria) {
        try {
            /* Establecer la conexión */
            Class.forName(driver).newInstance();
            con = DriverManager.getConnection(url + dbName, userName, password);

            try {
                /* Obtener el ID de la categoría por el nombre */
                Statement st = con.createStatement();
                ResultSet resCategoria = st.executeQuery("SELECT idcategoria FROM categoria WHERE nombrecategoria = '" + categoria + "'");

                if (resCategoria.next()) {
                    int idCategoria = resCategoria.getInt(1);

                    /* Obtener productos por el ID de la categoría y almacenarlos en una lista del modelo Producto */
                    ResultSet resProductos = st.executeQuery("SELECT * FROM producto WHERE idcategoria = " + idCategoria);

                    List<Producto> listaProductos = new ArrayList<Producto>();

                    while (resProductos.next()) {
                        Producto producto = new Producto();
                        producto.setIdCategoria(idCategoria);
                        producto.setIdProducto(resProductos.getInt(1));
                        producto.setNombreProducto(resProductos.getString("descripcion"));
                        producto.setImagen(resProductos.getString("urlimagen"));
                        producto.setValorUnitario(resProductos.getDouble("precio"));
                        producto.setStock(resProductos.getInt("stock"));
                        producto.setCaracteristicas(resProductos.getString("caracteristicas"));

                        listaProductos.add(producto);
                    }

                    return listaProductos;
                }
            } catch (SQLException s) {
                System.out.println("SQL code does not execute.");
            }

            con.close();
        } catch (Exception e) {
            e.printStackTrace();
        }

        return null;
    }

    /**
     * Obtiene una lista de URLs de imágenes de productos
     *
     * @return List::String:: : Lista de URLs de imágenes de productos
     */
    public synchronized List<String> obtenerImagenes() {
        List<String> images = new ArrayList<String>();
        try {
            /* Establecer la conexión */
            Class.forName(driver).newInstance();
            con = DriverManager.getConnection(url + dbName, userName, password);

            try {
                /* Obtener las URLs de las imágenes y almacenarlas en una lista */
                Statement st = con.createStatement();

                ResultSet resImagenes = st.executeQuery("SELECT urlimagen FROM producto");

                while (resImagenes.next()) {
                    String urlImg = resImagenes.getString(1);
                    if (null != urlImg && !urlImg.trim().equals("")) {
                        images.add(urlImg);
                    }
                }

            } catch (SQLException s) {
                System.out.println("SQL code does not execute.");
            }

            con.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return images;
    }

    /**
     * Obtiene una lista de todos los productos
     *
     * @return List::Producto:: : Lista de productos
     * @see Producto
     */
    public synchronized List<Producto> obtenerProductos() {
        try {
            /* Establecer la conexión */
            Class.forName(driver).newInstance();
            con = DriverManager.getConnection(url + dbName, userName, password);

            try {
                /* Obtener todos los productos y almacenarlos en una lista del modelo Producto */
                Statement st = con.createStatement();

                ResultSet resProductos = st.executeQuery("SELECT * FROM producto");

                List<Producto> listaProductos = new ArrayList<Producto>();

                while (resProductos.next()) {
                    Producto producto = new Producto();
                    producto.setIdCategoria(1);
                    producto.setIdProducto(resProductos.getInt(1));
                    producto.setNombreProducto(resProductos.getString("descripcion"));
                    producto.setImagen(resProductos.getString("urlimagen"));
                    producto.setValorUnitario(resProductos.getDouble("precio"));
                    producto.setStock(resProductos.getInt("stock"));
                    producto.setCodigoProducto(resProductos.getString("codigoproducto"));
                    producto.setCaracteristicas(resProductos.getString("caracteristicas"));
                    listaProductos.add(producto);
                }

                return listaProductos;

            } catch (SQLException s) {
                System.out.println("SQL code does not execute.");
            }

            con.close();
        } catch (Exception e) {
            e.printStackTrace();
        }

        return null;
    }

    /**
     * Obtiene un cliente por su cédula
     *
     * @param cedula String: Cédula del cliente
     * @return Cliente: Cliente
     * @see Cliente
     */
    public synchronized Cliente obtenerCliente(String cedula) {
        Cliente cliente = new Cliente();
        cliente.setCedula(cedula);

        try {
            /* Establecer la conexión */
            Class.forName(driver).newInstance();
            con = DriverManager.getConnection(url + dbName, userName, password);

            try {
                /* Obtener el cliente por el número de cédula y almacenarlo en un modelo Cliente*/
                Statement st = con.createStatement();

                ResultSet resCliente = st.executeQuery("SELECT * FROM cliente WHERE cedula = '" + cedula + "'");

                if (resCliente.next()) {
                    cliente.setCaducidadTarjeta(resCliente.getString("caducidadtarjeta"));
                    cliente.setCelular(resCliente.getString("celular"));
                    cliente.setCodigoSeguridad(resCliente.getString("codigoseguridad"));
                    cliente.setFechaRegistroCl(resCliente.getDate("fecharegistrocl"));
                    cliente.setMedio(resCliente.getString("medio"));
                    cliente.setNombreCliente(resCliente.getString("nombrecliente"));
                    cliente.setNombrePlastico(resCliente.getString("nombreplastico"));
                    cliente.setNumeroTarjeta(resCliente.getString("numerotarjeta"));
                    cliente.setTelefono(resCliente.getString("telefono"));
                    cliente.setTipoDireccion(resCliente.getString("tipodireccion"));
                    cliente.setTipoTarjeta(resCliente.getString("tipotarjeta"));
                    cliente.setCallePrincipal(resCliente.getString("dcalleprincipal"));
                    cliente.setCalleSecundaria(resCliente.getString("dcallesecundaria"));
                    cliente.setNumeroEdificio(resCliente.getString("dnumeroedificio"));
                    cliente.setReferencia(resCliente.getString("dreferencia"));
                    cliente.setSector(resCliente.getString("dsector"));
                }
            } catch (SQLException s) {
                System.out.println("SQL code does not execute.");
            }

            con.close();
        } catch (Exception e) {
            e.printStackTrace();
        }

        return cliente;
    }

    /**
     * Cliente Update or Create: Actualiza un cliente existente o crea uno nuevo
     *
     * @param cliente Cliente: Cliente
     * @see Cliente
     */
    public synchronized void clienteUoC(Cliente cliente) {
        try {
            /* Establecer la conexión */
            Class.forName(driver).newInstance();
            con = DriverManager.getConnection(url + dbName, userName, password);

            try {
                /* Obtener un cliente por su cédula */
                Statement st = con.createStatement();

                ResultSet resCliente = st.executeQuery("SELECT * FROM cliente WHERE cedula = '" + cliente.getCedula() + "'");

                if (resCliente.next()) {
                    /* Si el cliente existe, actualizar cliente */
                    st.execute("UPDATE cliente SET caducidadtarjeta = '" + cliente.getCaducidadTarjeta() + "', "
                            + "celular = '" + cliente.getCelular() + "', "
                            + "codigoseguridad = '" + cliente.getCodigoSeguridad() + "', "
                            + "medio = '" + cliente.getMedio() + "', "
                            + "nombrecliente = '" + cliente.getNombreCliente() + "', "
                            + "nombreplastico = '" + cliente.getNombrePlastico() + "', "
                            + "numerotarjeta = '" + cliente.getNumeroTarjeta() + "', "
                            + "telefono = '" + cliente.getTelefono() + "', "
                            + "tipodireccion = '" + cliente.getTipoDireccion() + "', "
                            + "tipotarjeta = '" + cliente.getTipoTarjeta() + "', "
                            + "dcalleprincipal = '" + cliente.getCallePrincipal() + "', "
                            + "dcallesecundaria = '" + cliente.getCalleSecundaria() + "', "
                            + "dnumeroedificio = '" + cliente.getNumeroEdificio() + "', "
                            + "dreferencia = '" + cliente.getReferencia() + "', "
                            + "dsector = '" + cliente.getSector() + "' "
                            + "WHERE cedula = '" + cliente.getCedula() + "'");
                } else {
                    /* Si el cliente no existe, crear cliente */
                    st.execute("INSERT INTO cliente VALUES('" + cliente.getCedula() + "', "
                            + "'" + cliente.getNombreCliente() + "', "
                            + "'" + cliente.getTelefono() + "', "
                            + "'" + cliente.getCelular() + "', "
                            + "'" + cliente.getMedio() + "', "
                            + "'" + cliente.getNumeroTarjeta() + "', "
                            + "'" + cliente.getTipoTarjeta() + "', "
                            + "'" + cliente.getCaducidadTarjeta() + "', "
                            + "CURRENT_TIMESTAMP, "
                            + "'" + cliente.getTipoDireccion() + "', "
                            + "'" + cliente.getCallePrincipal() + "', "
                            + "'" + cliente.getCalleSecundaria() + "', "
                            + "'" + cliente.getNumeroEdificio() + "', "
                            + "'" + cliente.getReferencia() + "', "
                            + "'" + cliente.getCodigoSeguridad() + "', "
                            + "'" + cliente.getNombrePlastico() + "', "
                            + "'" + cliente.getSector() + "')");
                }
            } catch (SQLException s) {
                System.out.println("SQL code does not execute.");
            }

            con.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * Obtiene un usuario por el nombre de usuario
     *
     * @param nombreUsuario String: Nombre de usuario
     * @return Usuario: Usuario
     * @see Usuario
     */
    public synchronized Usuario obtenerUsuario(String nombreUsuario) {
        Usuario usuario = new Usuario();

        try {
            /* Establecer la conexión */
            Class.forName(driver).newInstance();
            con = DriverManager.getConnection(url + dbName, userName, password);

            try {
                /* Obtener un usuario por el nombre de usuario y almacenarlo en un modelo Usuario */
                Statement st = con.createStatement();

                ResultSet resUsuarios = st.executeQuery("SELECT * FROM usuarios WHERE nombreusuario = '" + nombreUsuario.trim().toUpperCase() + "'");

                if (resUsuarios.next()) {
                    usuario.setAccessLevel(resUsuarios.getInt("nivel"));
                    usuario.setFullName(resUsuarios.getString("nombrecompleto"));
                    usuario.setUserId(resUsuarios.getInt("idusuario"));
                    usuario.setLogedIn(false);
                    usuario.setPassWord(resUsuarios.getString("password"));
                    usuario.setUserName(nombreUsuario);
                }
            } catch (SQLException s) {
                System.out.println("SQL code does not execute.");
            }

            con.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return usuario;
    }

    /**
     * Obtiene las categorías de llamadas
     *
     * @return List::String:: : Lista de categorías de llamadas
     */
    public synchronized List<String> obtenerCategoriasCall() {
        List<String> categoriasCall = new ArrayList<String>();

        try {
            /* Establecer la conexión */
            Class.forName(driver).newInstance();
            con = DriverManager.getConnection(url + dbName, userName, password);

            try {
                /* Obtener las categorías de las llamadas y almacenarlas en una lista */
                Statement st = con.createStatement();

                ResultSet resCategorias = st.executeQuery("SELECT nombrecatcall FROM categoriacall");

                while (resCategorias.next()) {
                    categoriasCall.add(resCategorias.getString(1));
                }

            } catch (SQLException s) {
                System.out.println("SQL code does not execute.");
            }

            con.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return categoriasCall;
    }

    /**
     * Obtiene las definiciones de llamadas por el nombre de la categoría de
     * llamadas
     *
     * @param categoriaCall String: Nombre de la categoría de llamadas
     * @return List::String:: : Definiciones de llamadas
     */
    public synchronized List<String> obtenerDefinicionCall(String categoriaCall) {
        List<String> definicionesCall = new ArrayList<String>();

        try {
            /* Establecer la conexión */
            Class.forName(driver).newInstance();
            con = DriverManager.getConnection(url + dbName, userName, password);

            try {
                /* Obtener el ID de la categoría de llamadas por el nombre de la categoría de llamadas */
                Statement st = con.createStatement();

                ResultSet resCategoriaCall = st.executeQuery("SELECT idcategoriacall FROM categoriacall WHERE nombrecatcall = '" + categoriaCall + "'");

                if (resCategoriaCall.next()) {
                    int idCategoriaCall = resCategoriaCall.getInt(1);

                    /* Obtener las definiciones de llamadas por el ID de la categoría de llamadas y almacenarlas en una lista */
                    ResultSet resDefinicionesCall = st.executeQuery("SELECT nombredefin FROM definicioncall WHERE idcategoriacall = " + idCategoriaCall);
                    while (resDefinicionesCall.next()) {
                        definicionesCall.add(resDefinicionesCall.getString(1));
                    }
                }
            } catch (SQLException s) {
                System.out.println("SQL code does not execute.");
            }

            con.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return definicionesCall;
    }

    /**
     * Obtiene la fecha actual
     *
     * @return Date: Fecha actual
     */
    public synchronized Date obtenerFecha() {
        try {
            /* Establecer la conexión */
            Class.forName(driver).newInstance();
            con = DriverManager.getConnection(url + dbName, userName, password);

            try {
                /* Obtener la fecha actual del servidor de base de datos y almacenarla en un objeto Date */
                Statement st = con.createStatement();

                ResultSet resDate = st.executeQuery("SELECT CURRENT_DATE");

                if (resDate.next()) {
                    return resDate.getDate(1);
                }
            } catch (SQLException s) {
                System.out.println("SQL code does not execute.");
            }

            con.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    /**
     * Obtiene la hora actual
     *
     * @return Time: Hora actual
     */
    public synchronized Time obtenerHora() {
        try {
            /* Establecer la conexión */
            Class.forName(driver).newInstance();
            con = DriverManager.getConnection(url + dbName, userName, password);

            try {
                /* Obtener la hora actual del servidor de base de datos y almacenarla en un objeto Time */
                Statement st = con.createStatement();

                ResultSet resDate = st.executeQuery("SELECT CURRENT_TIME");

                if (resDate.next()) {
                    return resDate.getTime(1);
                }
            } catch (SQLException s) {
                System.out.println("SQL code does not execute.");
            }

            con.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    /**
     * Obtiene la fecha y hora actuales
     *
     * @return Date: Fecha y hora actuales
     */
    public synchronized Date obtenerFechaHora() {
        try {
            /* Establecer la conexión */
            Class.forName(driver).newInstance();
            con = DriverManager.getConnection(url + dbName, userName, password);

            try {
                /* Obtener la fecha y hora actuales del servidor de base de datos y almacenarlas en un objeto Date */
                Statement st = con.createStatement();

                ResultSet resDate = st.executeQuery("SELECT CURRENT_TIMESTAMP");

                if (resDate.next()) {
                    return resDate.getDate(1);
                }
            } catch (SQLException s) {
                System.out.println("SQL code does not execute.");
            }

            con.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    /**
     * Guarda una llamada
     *
     * @param call Llamada: Llamada
     * @see Llamada
     */
    public synchronized void guardarLlamada(Llamada call) {
        try {
            /* Establecer la conexión */
            Class.forName(driver).newInstance();
            con = DriverManager.getConnection(url + dbName, userName, password);

            try {
                /* Registrar llamada en la tabla 'arttime'.'llamada' */
                Statement st = con.createStatement();

                String query = ("INSERT INTO llamada (codigollamada, "
                        + "llamadacategoria, "
                        + "llamadadefinicion, "
                        + "fechallamada, "
                        + "horainicio, "
                        + "totalventa, "
                        + "idusuario, "
                        + "idcliente, "
                        + "llamadaobservacion, "
                        + (call.getCategoria().trim().equals("VENTAS") ? "numeroorden, " : "")
                        + "horafin) "
                        + "VALUES('" + call.getCodigoLlamada() + "', "
                        + "'" + call.getCategoria() + "', "
                        + "'" + call.getDefinicion() + "', "
                        + "'" + call.getFecha() + "', "
                        + "'" + call.getHora() + "', "
                        + "" + call.getTotalVenta() + ", "
                        + obtenerUsuario(call.getUsuario()).getUserId() + ", "
                        + "'" + call.getCliente().getCedula() + "', "
                        + "'" + call.getObservacion() + "', "
                        + (call.getCategoria().trim().equals("VENTAS") ? call.getNumeroOrden() + ", " : "")
                        + "'" + call.getHoraFin() + "')");

                st.execute(query);
                Iterator iterator = call.getCarrito().iterator();

                /* Registrar los productos agregados al carrito, independiente si se trata de una venta o no */
                int idCall = obtenerIdLlamada(call.getCodigoLlamada());
                while (iterator.hasNext()) {
                    Producto producto = (Producto) iterator.next();

                    st.execute("INSERT INTO venta VALUES(" + idCall + ", "
                            + producto.getIdProducto() + ", "
                            + producto.getCantidad() + ", "
                            + producto.getSubtotal() + ")");

                    if (call.getCategoria().trim().equals("VENTAS")) {
                        /* Si se trata de una venta, actualizar stocks */
                        st.execute("UPDATE producto SET stock=" + (obtenerStock(producto.getIdProducto()) - producto.getCantidad()) + " "
                                + "WHERE idproducto=" + producto.getIdProducto());
                    }
                }
            } catch (SQLException s) {
                System.out.println("SQL code does not execute.");
            }

            con.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * Obtiene el ID de una llamada por el código de llamada
     *
     * @param codigoLlamada String: Código de llamada
     * @return int: ID de la llamada
     */
    public synchronized int obtenerIdLlamada(String codigoLlamada) {
        try {
            /* Establecer la conexión */
            Class.forName(driver).newInstance();
            con = DriverManager.getConnection(url + dbName, userName, password);

            try {
                /* Obtener el ID de la llamada por el código de llamada */
                Statement st = con.createStatement();

                ResultSet resLlamada = st.executeQuery("SELECT idllamada FROM llamada WHERE codigollamada = '" + codigoLlamada + "'");

                if (resLlamada.next()) {
                    return resLlamada.getInt(1);
                }
            } catch (SQLException s) {
                System.out.println("SQL code does not execute.");
            }

            con.close();
        } catch (Exception e) {
            e.printStackTrace();
        }

        return 0;
    }

    /**
     * Obtiene el siguiente número de orden para una nueva orden
     *
     * @return int: Número de orden
     */
    public synchronized int obtenerNumeroOrden() {
        try {
            /* Establecer la conexión */
            Class.forName(driver).newInstance();
            con = DriverManager.getConnection(url + dbName, userName, password);

            try {
                /* Obtener el siguiente número de orden */
                Statement st = con.createStatement();

                ResultSet resLlamada = st.executeQuery("SELECT MAX(numeroorden) FROM llamada");

                if (resLlamada.next()) {
                    return (resLlamada.getInt(1) + 1);
                }
            } catch (SQLException s) {
                System.out.println("SQL code does not execute.");
            }

            con.close();
        } catch (Exception e) {
            e.printStackTrace();
        }

        return 1;
    }
}
