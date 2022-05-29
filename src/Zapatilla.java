/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */

/**
 *
 * @author alex
 */
public class Zapatilla extends Articulo{
    //Atributos de la clase Zapatilla
    private double talla;
    private String categoria;
    private String material;

    //Getters y setters de la clase Zapatilla
    public double getTalla() {
        return talla;
    }

    public void setTalla(double talla) {
        if (talla > 50 || talla < 20){
            talla = 40;
        }
        this.talla = talla;
    }

    public String getCategoria() {
        return new String (categoria);
    }

    public void setCategoria(String categoria) {
        this.categoria = new String (categoria);
    }

    public String getMaterial() {
        return new String (material);
    }

    public void setMaterial(String material) {
        this.material = new String (material);
    }
    
    //Constructores de la clase Zapatilla
    public Zapatilla(double talla, String categoria, String material, String nombre, double precio, int stock, String descripcion, String marca, String color) {
        super(nombre, precio, stock, descripcion, marca, color);
        setTalla(talla);
        setCategoria(categoria);
        setMaterial(material);
    }
    
    public Zapatilla(){
       super();
    }
    
    public Zapatilla(Zapatilla Z){
       super((Articulo)Z);
       setTalla(Z.getTalla());
       setCategoria(Z.getCategoria());
       setMaterial(Z.getMaterial());
    }
    
    //toString de la clase Zapatilla
    @Override
    public String toString() {
        return "Zapatilla{"+super.toString() + "talla=" + talla + ", categoria=" + categoria + ", material=" + material + '}';
    }

    
    //Método para obtener la talla de la clase Zapatilla sobreescrito de Articulo (metodo abstracto en Articulo)
    @Override
    public String getTallaArticulo() {
        return "["+getTalla()+"]";
    }
    
    
}
