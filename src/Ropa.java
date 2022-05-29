/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */

/**
 *
 * @author alex
 */
public class Ropa extends Articulo {

    //Atributos de la clase Ropa
    private String talla;
    private String tipoTallaje;
    private String temporada;
    private String Categoria;

    //Getters y setters de la clase Ropa
    public String getTalla() {
        return talla;
    }

    public void setTalla(String talla) {
        this.talla = talla;
    }

    public String getTipoTallaje() {
        return new String(tipoTallaje);
    }

    public void setTipoTallaje(String tipoTallaje) {
        this.tipoTallaje = new String(tipoTallaje);
    }

    public String getTemporada() {
        return new String(temporada);
    }

    public void setTemporada(String temporada) {
        this.temporada = new String(temporada);
    }

    public String getCategoria() {
        return new String(Categoria);
    }

    public void setCategoria(String Categoria) {
        this.Categoria = new String(Categoria);
    }

    //Constructores de la clase Ropa
    public Ropa(String talla, String tipoTallaje, String temporada, String Categoria, String nombre, double precio, int stock, String descripcion, String marca, String color) {
        super(nombre, precio, stock, descripcion, marca, color);
        setTalla(talla);
        setTipoTallaje(tipoTallaje);
        setTemporada(temporada);
        setCategoria(Categoria);

    }

    public Ropa() {
        super();
    }

    public Ropa(Ropa R) {
        super((Articulo) R);
        setTalla(R.getTalla());
        setTipoTallaje(R.getTipoTallaje());
        setTemporada(R.getTemporada());
        setCategoria(R.getCategoria());
    }

    //toString de la clase Ropa
    @Override
    public String toString() {
        return "Ropa{" + super.toString() + "talla=" + talla + ", tipoTallaje=" + tipoTallaje + ", temporada=" + temporada + ", Categoria=" + Categoria + '}';
    }

    //Metodo para obtener la talla sobreescrito de la clase Ropa
    @Override
    public String getTallaArticulo() {
        return "[" + getTalla() + "]";
    }

}
