
import java.io.Serializable;
import java.util.LinkedHashMap;
import java.util.Objects;

/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
/**
 *
 * @author alex
 */
public class Cliente extends Persona {

    //Atributos de la clase Cliente
    private static int contadorClientes = 0;
    private int idCliente;
    private boolean esSocio;

    //Getters y setters de la clase Cliente
    public int getIdCliente() {
        return idCliente;
    }

    public boolean getEsSocio() {
        return esSocio;
    }

    public void setEsSocio(boolean p) {
        this.esSocio = p;
    }

    //Constructores de la clase Cliente
    public Cliente(String nombre, String apellidos, String dni, int anio, int mes, int dia) {
        super(nombre, apellidos, dni, anio, mes, dia);
        idCliente = contadorClientes++;
        setEsSocio(false);
    }

    public Cliente() {
    }

    public Cliente(Cliente C) {
        super((Persona) C);
        idCliente = contadorClientes++;
        setEsSocio(C.esSocio);
    }

    //toString de la clase Cliente
    @Override
    public String toString() {
        return "Cliente{" + super.toString() + "idCliente=" + idCliente + ", esSocio=" + esSocio + '}';
    }

    //equals de la clase Cliente
    @Override
    public boolean equals(Object obj) {
        if (obj instanceof Cliente) {
            Cliente tmp = (Cliente) obj;
            if (super.equals(obj) && getIdCliente() == tmp.getIdCliente()) {
                return true;
            } else {
                return false;
            }
        } else {
            return false;
        }
    }

    //hashCode de la clase Cliente que usa el hashCode de Persona + el id del cliente
    @Override
    public int hashCode() {
        return Objects.hash(idCliente, super.hashCode());
    }

    //Método para añadir articulos al carrito de una Cliente
    public void anadirAlCarrito(Articulo A, Integer cantidad, LinkedHashMap<Articulo, Integer> carrito) {
        carrito.put(A, cantidad);
    }
}
