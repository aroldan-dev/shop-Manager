
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.Serializable;
import java.util.AbstractCollection;
import java.util.Collection;
import java.util.LinkedHashMap;
import java.util.Map.Entry;
import java.util.TreeMap;
import java.util.TreeSet;

/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
/**
 *
 * @author alex
 */
public class Tienda implements Serializable {

    //Atributos de la clase Tienda
    private TreeSet<Articulo> stockTotal;//Almacenamos aqui todos nuestros articulos
    private TreeMap<Integer, Venta> ventas;//TreeMap que tendra nuestras ventas

    //getters y setters de la clase Tienda
    public void setStockTotal(TreeSet<Articulo> stockTotal) {
        this.stockTotal = new TreeSet<Articulo>();//Creamos espacio en memoria para el conjunto de Articulos
        this.stockTotal.addAll(stockTotal);//Añadimos todos los que nos han pasado a nuestro stockTotal
    }

    public void setVentas(TreeMap<Integer, Venta> ventas) {
        this.ventas = new TreeMap<Integer, Venta>();//Creamos espacio en memoria para el mapa de ventas
        this.ventas.putAll(ventas);//Añadimos todas las que nos han pasado a nuestro mapa de ventas
    }

    public TreeMap<Integer, Venta> getVentas() {
        TreeMap<Integer, Venta> auxVenta = new TreeMap<>();
        auxVenta.putAll(ventas);
        return auxVenta;//Returneamos un TreeMap auxiliar para mantener la encapsulacion ya que para bajas y altas sobre las ventas tenemos otros métodos
    }

    public TreeSet<Articulo> getStockTotal() {
        TreeSet<Articulo> auxStockTotal = new TreeSet();
        auxStockTotal.addAll(stockTotal);
        return auxStockTotal;//Returneamos un TreeSet auxiliar para mantener la encapsulacion ya que para las bajas y altas sobre el stock tenemos otros métodos
    }
    
        //Constructores de la clase Tienda
    public Tienda(TreeSet<Articulo> stockTotal, TreeMap<Integer, Venta> ventas) {
        this.setStockTotal(stockTotal);
        this.setVentas(ventas);
    }

    public Tienda() {
        this.stockTotal = new TreeSet<>();
        this.ventas = new TreeMap<>();
    }
    
    //Metodo para añadir Articulo al stockTotal
    public void anadirArticulo(Articulo A) {
        this.stockTotal.add(A);
    }

    //Metodo sobreecargado para añadir un conjunto de Articulos al stockTotal
    public void anadirArticulo(Collection C) {
        stockTotal.addAll(C);
    }

    //Metodo para eliminar Articulo del stockTotal
    public void eliminarArticulo(Articulo A) {
        this.stockTotal.remove(A);
    }

    //Metodo para añadir una Venta a la Tienda
    public void anadirVenta(Venta V) {
        //Todo el proceso dentro de un try catch para capturar posibles excepciones
        //Una de ellas la excepcion de stock, para controlar que los articulos que
        //se compren esten en stock
        try {
            LinkedHashMap<Articulo, Integer> articulosCliente = V.getArticulos();//Carrito del cliente que recorreremos
            for (Entry<Articulo, Integer> e : articulosCliente.entrySet()) {//Recorremos el mapa con Entry
                Articulo articulo = e.getKey();
                Integer cantidad = e.getValue();
                if (articulo.getStock() - cantidad < 0) {//En caso de que el stock - la cantidad comprada sea < 0 lanzamos nuestra propia excepcion
                    System.out.print("ID --> " + articulo.getIdArticulo() + " ");
                    throw new excepcionStock();
                }
                //En caso de que el stock sea correcto continuamos con la venta
                stockTotal.remove(articulo);//Eliminamos el articulo del stockTotal para trabajar con el
                articulo.reponerStock(-cantidad);//Reponemos negativamente el stock, lo mismo que eliminar stock
                stockTotal.add(articulo);//Volvemos a añadir el articulo a nuestroSet
            }
            //Añadimos la venta a nuestro mapa con formato <ID,VENTA>
            this.ventas.put(V.getIdVenta(), V);
        } catch (excepcionStock e) {
            System.out.println(e.getMessage());
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }

    }
    

    
    //Metodo imprimirFactura que imprime por pantalla la factura de la Venta pasada como parámetro
    //y además genera la factura en formato .txt en nuestra carpeta facturas
    public String imprimirFactura(Venta v) {
        double total = 0;//Variable que almacena el precio total de nuestra compra
        //En el la variable r iremos concatenando información para generar la factura con formato visual
        String r = "/*********************************************/\n";
        //        String r = "/=============================================/\n";
        r += String.format("/ID VENTA --> %3d %7s %20s/\n", v.getIdVenta(), "", v.getFechaFormateada(v.getFechaVenta()));
        r += "/=============================================/\n";
        String resultado = "ID = " + v.getIdVenta() + " " + v.getFechaFormateada(v.getFechaVenta()) + "\n";
        for (Entry e : v.getArticulos().entrySet()) {
            r += "/" + String.format("%-25s x%2d %-6s %07.2f", (((Articulo) e.getKey()).getNombre()), ((Integer) e.getValue()), ((Articulo) e.getKey()).getTallaArticulo(), (((Articulo) e.getKey()).getPrecio() * Double.parseDouble(e.getValue().toString()))) + "€/\n";
            resultado += ((Articulo) e.getKey()).getNombre() + "-----" + (((Articulo) e.getKey()).getPrecio() * Double.parseDouble(e.getValue().toString())) + "€\n";
            total += ((Articulo) e.getKey()).getPrecio() * Double.parseDouble(e.getValue().toString());
        }
        //En caso de que el cliente sea socio
        if (v.getCliente().getEsSocio()) {
            total = total * 0.95;//Aplicamos 5% a los socios
        }
        r += String.format("/%45s/\n/%27s %-8s %07.02f€/\n", "", "", "TOTAL", total);
        r += "/*********************************************/";
        
        //**************************************************************
        //GENERAMOS LA FACTURA EN TXT con el formato ID_VENTAFECHA
        String nombre = v.getIdVenta() + "_VENTA"
                + v.getFechaFormateada(v.getFechaVenta()).substring(0, v.getFechaFormateada(v.getFechaVenta()).indexOf("-")).replaceAll("/", "").trim()
                + ".txt";
        try {
            File f = new File("./facturas/" + nombre);//Creamos el archivo
            BufferedWriter bw = new BufferedWriter(new FileWriter(f, false));
            bw.write(r);//Escribimos el resultado de la factura
            bw.close();
        } catch (Exception e) {
        }
        //**************************************************************
        return r;
    }
    
    //Nuestra propia excepcion que se encarga de la gestionar si el stock que un cliente compra es correcto
    public class excepcionStock extends Exception {
        
        //Constructor
        public excepcionStock() {
            super();
        }
        //Mensaje que se mostrará en caso de intentar llevarse mas articulos de los disponibles
        public String getMessage() {
            return "LA CANTIDAD DE ARTICULOS DE LA VENTA ES SUPERIOR AL STOCK DISPONIBLE";
        }
    }
}
