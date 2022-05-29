
import java.io.Serializable;
import java.util.Calendar;
import java.util.GregorianCalendar;
import java.util.LinkedHashMap;
import java.util.Objects;
import java.util.TreeSet;

/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */

/**
 *
 * @author alex
 */
public class Venta implements Comparable<Venta>, Serializable{
    //Atributos de la clase Venta
    private static int contadorVentas = 0;
    private int idVenta;
    private Cliente cliente;
    private Vendedor vendedor;
    private LinkedHashMap<Articulo, Integer> articulos;
    private GregorianCalendar fechaVenta;
    
    //Getters y setter de la clase Venta
    public int getIdVenta() {
        return idVenta;
    }

    public Cliente getCliente() {
        return new Cliente(cliente);
    }

    public void setCliente(Cliente cliente) {
        this.cliente = cliente;
    }

    public Vendedor getVendedor() {
        return new Vendedor(vendedor);
    }

    public void setVendedor(Vendedor vendedor) {
        this.vendedor = vendedor;
    }

    public LinkedHashMap<Articulo, Integer> getArticulos() {
        //En este caso si que devolvemos el Mapa ya que debermos trabajar sobre el en métodos futuros
        return articulos;
    }

    public void setArticulos(LinkedHashMap<Articulo, Integer> articulos) {
        this.articulos = articulos;
    }

    public GregorianCalendar getFechaVenta() {
        //En este caso si que devolvemos el objeto ya que debermos trabajar sobre el en el futuro
        return fechaVenta;
    }

    public void setFechaVenta(GregorianCalendar fechaVenta) {
        this.fechaVenta = fechaVenta;
    }
    
    //Constructores de la clase Venta
    public Venta(Cliente cliente, Vendedor vendedor, LinkedHashMap<Articulo, Integer> articulos) {
        this.idVenta = contadorVentas++;
        setCliente(cliente);
        setVendedor(vendedor);
        setArticulos(articulos);
        setFechaVenta(new GregorianCalendar());
    }
    
    public Venta(Venta v){
        this.idVenta = contadorVentas++;
        setCliente(v.getCliente());
        setVendedor(v.getVendedor());
        setArticulos(v.getArticulos());
        setFechaVenta(v.getFechaVenta());
    }
    
    //toString de la clase Venta
    @Override
    public String toString() {
        return "Venta{" + "idVenta=" + idVenta + ", cliente=" + cliente.getNombre() + ", vendedorID=" + vendedor.getIdVendedor() + ", articulos=" + articulos.size() + ", fechaVenta=" + getFechaFormateada(fechaVenta) + '}';
    }
    
    //hashCode de la clase Venta
    @Override
    public int hashCode() {
        return 31*Objects.hash(getIdVenta(),getVendedor(),getCliente(),getFechaVenta());
    }

    //equals de la clase Venta
    @Override
    public boolean equals(Object obj) {
        if(obj instanceof Venta){
            Venta tmp = (Venta) obj;
            if(this.getIdVenta() == tmp.getIdVenta()){
                return true;
            }else{
                return false;
            }
        }else{
            return false;
        }
    }
    
    //Método para obtener la fecha formateada es decir DD/MM/YYYY
    public String getFechaFormateada(GregorianCalendar g) {
        return String.valueOf(g.get(g.DAY_OF_MONTH) + "/" + (g.get(g.MONTH) + 1) + "/" + g.get(g.YEAR)+" - ["+g.get(Calendar.HOUR_OF_DAY)+":"+g.get(Calendar.MINUTE)+"]");
    }
    
    //compareTo para poder ordenar las ventas segun id de menor a mayor
    @Override
    public int compareTo(Venta V) {
        Integer i = this.getIdVenta();
        return i.compareTo(V.getIdVenta());
    }
}
