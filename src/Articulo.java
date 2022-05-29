
import java.io.Serializable;
import java.util.Objects;

/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
/**
 *
 * @author alex
 */
public abstract class Articulo implements Comparable<Articulo>, Serializable {

    //Atributos de la clase Articulo
    private static int contadorArticulo = 0;
    private int idArticulo;
    private String nombre;
    private double precio;
    private int stock;
    private String descripcion;
    private String marca;
    private String color;

    //Getters y setters clase Articulo
    public String getColor() {
        return color;
    }

    public void setColor(String color) {
        this.color = new String(color);
    }

    public int getIdArticulo() {
        return idArticulo;
    }

    public String getNombre() {
        return new String(nombre.toUpperCase());
    }

    public void setNombre(String nombre) {
        this.nombre = new String(nombre);
    }

    public double getPrecio() {
        return precio;
    }

    public void setPrecio(double precio) {
        this.precio = precio;
    }

    public int getStock() {
        return stock;
    }

    public void setStock(int stock) {
        this.stock = stock;
    }

    public String getDescripcion() {
        return new String(descripcion);
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = new String(descripcion);
    }

    public String getMarca() {
        return new String(marca);
    }

    public void setMarca(String marca) {
        this.marca = new String(marca);
    }

    //Constructores clase Articulo
    public Articulo(String nombre, double precio, int stock, String descripcion, String marca, String color) {
        this.idArticulo = contadorArticulo++;
        setNombre(nombre);
        setPrecio(precio);
        setStock(stock);
        setDescripcion(descripcion);
        setMarca(marca);
        setColor(color);
    }

    public Articulo() {
    }

    public Articulo(Articulo A) {
        this.idArticulo = contadorArticulo++;
        setNombre(A.getNombre());
        setPrecio(A.getPrecio());
        setStock(A.getStock());
        setDescripcion(A.getDescripcion());
        setMarca(A.getMarca());
        setColor(A.getColor());
    }

    //toString de la clase Articulo
    @Override
    public String toString() {
        return "Articulo{" + "idArticulo=" + idArticulo + ", nombre=" + nombre + ", precio=" + precio + ", stock=" + stock + ", descripcion=" + descripcion + ", marca=" + marca + ", color=" + color + '}';
    }

    //hashCode de la clase Articulo
    @Override
    public int hashCode() {
        return 31 * Objects.hash(getIdArticulo(), getNombre(), getMarca(), getStock(), getPrecio(), getDescripcion());
    }

    //equals de la clase Articulo
    @Override
    public boolean equals(Object obj) {
        if (obj instanceof Articulo) {
            Articulo tmp = (Articulo) obj;
            if (tmp.getIdArticulo() == this.getIdArticulo()) {
                return true;
            } else {
                return false;
            }
        } else {
            return false;
        }
    }

    //Metodo para añadir stock
    public void reponerStock(int cantidad) {
        setStock(getStock() + cantidad);
    }

    //Metodo para modificarPrecio
    public void modificarPrecio(double nuevoPrecio) {
        setPrecio(nuevoPrecio);
    }

    //Metodo para modificar nombre
    public void modificarNombre(String nuevoNombre) {
        setNombre(nuevoNombre);
    }

    //Metodo a sobreescribir en las clases hijas
    public abstract String getTallaArticulo();

    //compareTo para ordenar los articulos de id menor a mayor
    @Override
    public int compareTo(Articulo A) {
        if (A.getIdArticulo() < this.getIdArticulo()) {
            return 1;
        } else if (A.getIdArticulo() > this.getIdArticulo()) {
            return -1;
        } else {
            return 0;
        }
    }
}
