/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */

/**
 *
 * @author alex
 */
public class Accesorio extends Articulo{
    //Atributos de la clase Accesorio
    private String talla;
    private String Categoria;

    //Getters y setters de la clase Accesorio
    public String getTalla() {
        return talla;
    }

    public void setTalla(String talla) {
        this.talla = talla;
    }

    public String getCategoria() {
        return new String(Categoria);
    }

    public void setCategoria(String Categoria) {
        this.Categoria = new String (Categoria);
    }
    
    //3 Constructores de la clase Accesorio
    public Accesorio(String talla, String nombre, String Categoria, double precio, int stock, String descripcion, String marca, String color) {
        super(nombre, precio, stock, descripcion, marca, color);
        setCategoria(Categoria);
        setTalla(talla);
    }
    
    public Accesorio(){
        
    }
    
    public Accesorio(Accesorio A){
       super((Articulo) A);
       setCategoria(A.getCategoria());
       setTalla(A.getTalla());
        
    }
    
    //toString clase Accesorio
    @Override
    public String toString() {
        return "Accesorio{"+super.toString() + "talla=" + talla + ", Categoria=" + Categoria + '}';
    }
    
    
    //Método sobreescrito de la clase Articulo
    @Override
    public String getTallaArticulo() {
        return "["+getTalla()+"]";
    }

    
    
}
