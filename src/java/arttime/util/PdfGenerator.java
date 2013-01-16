/*
 * Software desarrollado bajo requerimientos de ByPhone,
 * entregado con licencia GNU GPL Version 3
 * Para más información sobre la licencia visitar http://www.gnu.org/licenses/gpl-3.0.html
 */
package arttime.util;

import arttime.model.Cliente;
import arttime.model.Llamada;
import arttime.model.Producto;
import com.itextpdf.text.Document;
import com.itextpdf.text.DocumentException;
import com.itextpdf.text.Element;
import com.itextpdf.text.Paragraph;
import com.itextpdf.text.Phrase;
import com.itextpdf.text.pdf.PdfPCell;
import com.itextpdf.text.pdf.PdfPTable;
import com.itextpdf.text.pdf.PdfWriter;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.Serializable;
import java.util.Iterator;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * Utilitario para la generación de reportes PDF
 *
 * @author Daniel Gonzabay
 * @version 1.0.9
 */
public class PdfGenerator implements Serializable {
    /* 
     * Implementación: Serializable
     * Todas las clases de un proyecto Web basado en sesiones deben ser serializadas 
     */

    /**
     * Genera un archivo PDF con el pedido guardado de la llamada actual
     *
     * @param llamada Llamada: Llamada actual
     * @see Llamada
     */
    public void generarPedido(Llamada llamada) {
        /* Controlar si se trata de una venta */
        if (llamada.getCategoria().trim().equals("VENTAS")) {
            try {
                File file = new File("/artetiempo/pdf/" + llamada.getFecha().toString() + "/" + llamada.getUsuario());

                if (!file.exists()) {
                    file.mkdirs();
                }

                String sourcePath = "/artetiempo/pdf/" + llamada.getFecha().toString() + "/" + llamada.getUsuario()
                        + "/" + getOrderFormat(llamada.getNumeroOrden()) + "-" + llamada.getCliente().getCedula() + ".pdf";

                file = new File(sourcePath);
                if (!file.exists()) {
                    file.createNewFile();
                }

                /* Crear el documento PDF */

                Document document = new Document();
                ByteArrayOutputStream baos = new ByteArrayOutputStream();
                PdfWriter.getInstance(document, baos);
                document.open();

                /* Agregar el número de orden */

                Paragraph numeroOrden = new Paragraph("ORDEN No. " + getOrderFormat(llamada.getNumeroOrden()));
                numeroOrden.setAlignment(Element.ALIGN_RIGHT);
                numeroOrden.setSpacingAfter(18);
                numeroOrden.setSpacingBefore(18);
                document.add(numeroOrden);

                /* Agregar los datos del cliente */

                Cliente cliente = llamada.getCliente();

                Paragraph clienteTitulo = new Paragraph("DATOS DEL CLIENTE");
                clienteTitulo.setAlignment(Element.ALIGN_CENTER);
                clienteTitulo.setSpacingBefore(36);
                clienteTitulo.setSpacingAfter(18);
                document.add(clienteTitulo);

                document.add(new Paragraph("Cédula: " + cliente.getCedula()));
                document.add(new Paragraph("Nombre: " + cliente.getNombreCliente()));

                Paragraph direccion = new Paragraph("Dirección: (" + cliente.getTipoDireccion() + ")  "
                        + cliente.getCallePrincipal() + " "
                        + cliente.getNumeroEdificio() + " Y "
                        + cliente.getCalleSecundaria());
                direccion.setSpacingBefore(8);
                document.add(direccion);

                Paragraph sector = new Paragraph("Sector o barrio: " + cliente.getSector());
                sector.setSpacingAfter(8);
                document.add(sector);

                Paragraph referencia = new Paragraph("Referencia: " + cliente.getReferencia());
                referencia.setSpacingAfter(8);
                document.add(referencia);

                Paragraph telefonos = new Paragraph("Teléfono: " + cliente.getTelefono() + "                              Celular: " + cliente.getCelular());
                telefonos.setSpacingAfter(8);
                document.add(telefonos);

                document.add(new Paragraph("Forma de pago: " + cliente.getTipoTarjeta()));
                document.add(new Paragraph("Número de tarjeta: " + cliente.getNumeroTarjeta()));
                document.add(new Paragraph("Fecha de caducidad: " + cliente.getCaducidadTarjeta()));
                document.add(new Paragraph("Código de seguridad: " + cliente.getCodigoSeguridad()));
                document.add(new Paragraph("Nombre del plástico: " + cliente.getNombrePlastico()));

                /* Agregar los datos del pedido */

                Paragraph pedidoTitulo = new Paragraph("ORDEN DE PEDIDO");
                pedidoTitulo.setAlignment(Element.ALIGN_CENTER);
                pedidoTitulo.setSpacingBefore(36);
                pedidoTitulo.setSpacingAfter(18);
                document.add(pedidoTitulo);

                Paragraph fechaTexto = new Paragraph("Fecha: " + llamada.getFecha());
                fechaTexto.setSpacingAfter(18);

                document.add(fechaTexto);

                /* Crear y agregar la tabla de productos del pedido */

                PdfPTable table = new PdfPTable(5);
                table.setTotalWidth(new float[]{72, 108, 216, 72, 72});
                table.setLockedWidth(true);

                PdfPCell celdaCantidad = new PdfPCell(new Phrase("CANTIDAD"));
                celdaCantidad.setHorizontalAlignment(Element.ALIGN_CENTER);
                PdfPCell celdaCodigo = new PdfPCell(new Phrase("CODIGO"));
                celdaCodigo.setHorizontalAlignment(Element.ALIGN_CENTER);
                PdfPCell celdaDescripcion = new PdfPCell(new Phrase("DESCRIPCION"));
                celdaDescripcion.setHorizontalAlignment(Element.ALIGN_CENTER);
                PdfPCell celdaPrecio = new PdfPCell(new Phrase("PRECIO UNITARIO"));
                celdaPrecio.setHorizontalAlignment(Element.ALIGN_CENTER);
                PdfPCell celdaValor = new PdfPCell(new Phrase("VALOR TOTAL"));
                celdaValor.setHorizontalAlignment(Element.ALIGN_CENTER);

                table.addCell(celdaCantidad);
                table.addCell(celdaCodigo);
                table.addCell(celdaDescripcion);
                table.addCell(celdaPrecio);
                table.addCell(celdaValor);

                /* Agregar los productos a la tabla */

                Iterator iterator = llamada.getCarrito().iterator();

                while (iterator.hasNext()) {
                    Producto producto = (Producto) iterator.next();
                    if (producto.getCantidad() > 0) {
                        PdfPCell celdaDetCant = new PdfPCell(new Phrase("" + producto.getCantidad()));
                        celdaDetCant.setHorizontalAlignment(Element.ALIGN_RIGHT);
                        PdfPCell celdaDetCod = new PdfPCell(new Phrase("" + producto.getCodigoProducto()));
                        PdfPCell celdaDetDesc = new PdfPCell(new Phrase("" + producto.getNombreProducto()));
                        PdfPCell celdaDetPrec = new PdfPCell(new Phrase(getPriceFormat(producto.getValorUnitario())));
                        celdaDetPrec.setHorizontalAlignment(Element.ALIGN_RIGHT);
                        PdfPCell celdaDetVal = new PdfPCell(new Phrase(getPriceFormat(producto.getSubtotal())));
                        celdaDetVal.setHorizontalAlignment(Element.ALIGN_RIGHT);

                        table.addCell(celdaDetCant);
                        table.addCell(celdaDetCod);
                        table.addCell(celdaDetDesc);
                        table.addCell(celdaDetPrec);
                        table.addCell(celdaDetVal);
                    }
                }

                /* 
                 * Agregar una celda en blanco unificada en 3 filas y 
                 * 3 columnas para formato de los totales, luego agregar los 
                 * totales: subtotal, iva y total
                 */

                PdfPCell whiteCell = new PdfPCell(new Phrase(""));
                whiteCell.setColspan(3);
                whiteCell.setRowspan(3);
                table.addCell(whiteCell);

                /* Calcular y agregar subtotal, iva, y total */
                double total = llamada.getTotalVenta();
                double subtotal = total / 1.12;
                double iva = total - subtotal;

                String subtotalString = getPriceFormat(subtotal);
                String ivaString = getPriceFormat(iva);

                /* Corregir suma +- 1 centavo */
                try {
                    iva = Double.parseDouble(ivaString);
                    subtotal = Double.parseDouble(subtotalString);
                    
                    if ((iva + subtotal) != total) {
                        iva = total - subtotal;
                        ivaString = getPriceFormat(iva);
                    }
                } catch (Exception e) {
                    ;
                }

                PdfPCell celdaSubtotal = new PdfPCell(new Phrase(subtotalString));
                celdaSubtotal.setHorizontalAlignment(Element.ALIGN_RIGHT);
                PdfPCell celdaIva = new PdfPCell(new Phrase(ivaString));
                celdaIva.setHorizontalAlignment(Element.ALIGN_RIGHT);
                PdfPCell celdaTotal = new PdfPCell(new Phrase(getPriceFormat(total)));
                celdaTotal.setHorizontalAlignment(Element.ALIGN_RIGHT);

                table.addCell("SUBTOTAL");
                table.addCell(celdaSubtotal);

                table.addCell("IVA (12%)");
                table.addCell(celdaIva);

                table.addCell("TOTAL");
                table.addCell(celdaTotal);

                document.add(table);

                /* Agregar las observaciones */

                Paragraph observaciones = new Paragraph("OBSERVACIONES:  " + llamada.getObservacion());
                observaciones.setSpacingBefore(36);
                observaciones.setSpacingAfter(18);
                document.add(observaciones);

                document.close();
                FileOutputStream fos = new FileOutputStream(file);
                fos.write(baos.toByteArray());
                fos.close();

                /* Copiar el archivo PDF a la carpeta de red de acceso libre */

                String destinationPath = "\\\\10.10.10.61\\Libre_acceso\\ARTE-Y-TIEMPO\\OrdenesCompra/"
                        + llamada.getFecha().toString() + "/" + llamada.getUsuario();
                String destinationFileName = getOrderFormat(llamada.getNumeroOrden()) + "-" + llamada.getCliente().getCedula() + ".pdf";

                copiar(sourcePath, destinationFileName, destinationPath);

            } catch (IOException ex) {
                Logger.getLogger(PdfGenerator.class.getName()).log(Level.SEVERE, null, ex);
            } catch (DocumentException ex) {
                Logger.getLogger(PdfGenerator.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
    }

    /**
     * Crea una copia de un archivo
     *
     * @param sourceFile String: Path completo del archivo de origen
     * @param destinationFileName String: Nombre del archivo de destino incluida
     * extension
     * @param destinationPath String: Path de destino, no incluye el nombre del
     * archivo
     */
    private void copiar(String sourceFile, String destinationFileName, String destinationPath) {
        FileInputStream in = null;
        FileOutputStream out = null;

        try {
            File inFile = new File(sourceFile);
            String destinationFile = destinationPath + "/" + destinationFileName;
            File outFile = new File(destinationFile);
            File outPath = new File(destinationPath);

            if (!outPath.exists()) {
                outPath.mkdirs();
            }
            if (!outFile.exists()) {
                outFile.createNewFile();
            }

            in = new FileInputStream(inFile);
            out = new FileOutputStream(outFile);
            int c;
            while ((c = in.read()) != -1) {
                out.write(c);
            }
        } catch (Exception ex) {
            Logger.getLogger(PdfGenerator.class.getName()).log(Level.SEVERE, null, ex);
        } finally {
            try {
                in.close();
            } catch (Exception ex) {
                Logger.getLogger(PdfGenerator.class.getName()).log(Level.SEVERE, null, ex);
            }
            try {
                out.close();
            } catch (Exception ex) {
                Logger.getLogger(PdfGenerator.class.getName()).log(Level.SEVERE, null, ex);
            }
        }

    }

    /**
     * Obtiene un String con el número de orden formateado a 10 dígitos
     * (0000000001, por ejemplo)
     *
     * @param orderNumber int: Número de orden
     * @return String: Numero de orden en el formato de número de orden
     */
    private String getOrderFormat(int orderNumber) {
        String stringOrderNumber = "" + orderNumber;
        String result = "";
        int ceros = 10 - stringOrderNumber.length();

        for (int i = 0; i < ceros; i++) {
            result += "0";
        }

        result += stringOrderNumber;

        return result;
    }

    /**
     * Obtiene un precio formateado con dos decimales
     *
     * @param price double: Precio
     * @return String: Precio en formato de dos decimales
     */
    private String getPriceFormat(double price) {
        double aux = price * 100;
        aux -= (int) aux;

        if (aux >= 0.5) {
            price += 0.01;
        }

        String formatedPrice = "" + price;

        if (formatedPrice.contains(".")) {
            try {
                formatedPrice = formatedPrice.substring(0, formatedPrice.indexOf(".") + 3);
            } catch (IndexOutOfBoundsException iobe) {
                formatedPrice += "0";
            }
        } else {
            formatedPrice = formatedPrice + ".00";
        }
        return formatedPrice;
    }
}
