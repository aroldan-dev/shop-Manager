
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
public class Vendedor extends Persona {
    //Atributos de la clase Vendedor
    private static int contadorVendedor = 0;//Contador de Vendedores
    private int idVendedor;
    
    //Getter de la clase Vendedor
    public int getIdVendedor() {
        return idVendedor;
    }


    //Construotres de la clase Vendedor
    public Vendedor(String nombre, String apellidos, String dni, int anio, int mes, int dia) {
        super(nombre, apellidos, dni, anio, mes, dia);
        idVendedor = contadorVendedor++;
    }

    public Vendedor() {
    }

    public Vendedor(Vendedor V) {
        super((Persona) V);
        idVendedor = contadorVendedor++;
    }
    
    //toString de la clase Vendedor
    @Override
    public String toString() {
        return "Vendedor{" + super.toString() + "idVendedor=" + idVendedor + ", ventasRealizadas="  + '}';
    }
    
    //hashCode de la clase Vendedor
    @Override
    public int hashCode() {
        return Objects.hash(getIdVendedor(), super.hashCode());
    }   
    //equals de la clase Vendedor
    @Override
    public boolean equals(Object obj) {
        if (obj instanceof Vendedor) {
            Vendedor tmp = (Vendedor) obj;
            if (super.equals(obj) && getIdVendedor() == tmp.getIdVendedor()) {
                return true;
            } else {
                return false;
            }
        } else {
            return false;
        }
    }

    //Método para hacer socio a un cliente
    public void hacerSocio(Cliente c) {
        c.setEsSocio(true);
    }

    //Añadir un articulo al stockTotal
    public void altaArticulo(Articulo A, Tienda T) {
        //En caso de que el articulo ya exista no lo metemos
        if (T.getStockTotal().contains(A)) {
            System.out.println("ARTICULO YA AÑADIDO");
        } else {
            //Si no existe llamamos al metodo anadirArticulo de la clase Tienda y añadimos el articulo
            //No lo añadimos desde aqui porque no tenemos acceso
            T.anadirArticulo(A);//
        }
    }
    
    //Método para dar de baja a un articulo
    public void bajaArticulo(Articulo A, Tienda T) {
        //Si el articulo existe lo eliminamos
        if(T.getStockTotal().contains(A)){
            //Llamamos al metodo eliminarArticulo de Tienda para poder hacerlo
            //No lo eliminamos desde aqui porque no tenemos acceso
            T.eliminarArticulo(A);
        
        }else{
            //En caso de que no exist no podemos eliminarlo
            System.out.println("EL ARTICULO NO EXISTE");
        }
        
    }
    
    //Método para añadir venta a la tienda
    public void anadirVenta(Venta V, Tienda T) {
        //LLamamos al método anadirVenta de la clase Tienda que si que tiene acceso al mapa de ventas
        T.anadirVenta(V);
    }
    
}
