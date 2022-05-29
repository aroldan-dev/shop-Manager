
import java.io.Serializable;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.Objects;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
/**
 *
 * @author alex
 */
public abstract class Persona implements Serializable {

    //Atributos de la clase Persona
    private String nombre;
    private String apellidos;
    private String dni;
    private GregorianCalendar fechaNacimiento;

    //Getters y setters de la clase Persona
    public String getNombre() {
        return new String(nombre);
    }

    public void setNombre(String nombre) {
        this.nombre = new String(nombre);
    }

    public String getApellidos() {
        return new String(apellidos);
    }

    public void setApellidos(String apellidos) {
        this.apellidos = new String(apellidos);
    }

    public String getDni() {
        return new String(dni);
    }

    public void setDni(String dni) {
        this.dni = new String(dni);
    }

    //getter de la FechaDeNacimiento que devuelve un objeto del tipo GregorianCalendar, la clase da mucha informacion
    //por lo tanto usaré un metodo especifico para sacar los datos que me interesan
    public GregorianCalendar getFechaNacimiento() {
        GregorianCalendar g = new GregorianCalendar();
        g.set(fechaNacimiento.get(Calendar.YEAR), fechaNacimiento.get(Calendar.MONTH), fechaNacimiento.get(Calendar.DAY_OF_MONTH));
        return g;
    }

    public void setFechaNacimiento(int anio, int mes, int dia) {
        this.fechaNacimiento = new GregorianCalendar(anio, mes - 1, dia);
    }

    //Constructores de la clase Persona
    public Persona(String nombre, String apellidos, String dni, int anio, int mes, int dia) {
        setNombre(nombre);
        setApellidos(apellidos);
        setDni(comprobarDNI(dni));//Llamamos al método comprobarDni para siempre tener almacenados dnis correctos
        setFechaNacimiento(anio, mes, dia);
    }

    public Persona() {
    }

    public Persona(Persona P) {
        setNombre(P.getNombre());
        setApellidos(P.getApellidos());
        setDni(P.getDni());
        this.fechaNacimiento = P.getFechaNacimiento();
    }

    //toString de la clase Persona
    @Override
    public String toString() {
        return "Persona{" + "nombre=" + nombre + ", apellidos=" + apellidos + ", dni=" + dni + ", fechaNacimiento=" + getFechaFormateada(fechaNacimiento) + '}';
    }

    //equals de la clase Persona
    @Override
    public boolean equals(Object obj) {
        if (obj instanceof Persona) {
            Persona tmp = (Persona) obj;
            if (tmp.getDni().toLowerCase().equals(this.getDni().toLowerCase())) {
                return true;
            } else {
                return false;
            }
        } else {
            return false;
        }
    }

    //HashCode de la clase Persona
    @Override
    public int hashCode() {
        return 31 * Objects.hash(getNombre(), getApellidos(), getDni(), getFechaNacimiento());
    }

    //Método privado y estatico que devuelve el dni con su letra correspondiente haciendo uso de patrones
    private static String comprobarDNI(String p_dni) {
        String letras = "TRWAGMYFPDXBNJZSQVHLCKE";
        Pattern p = Pattern.compile("([0-9]{8})((?i)[a-z]{1})");
        Matcher m = p.matcher(p_dni);
        m.find();
        int nroDni = Integer.parseInt(p_dni.substring(0, p_dni.indexOf(m.group(2))));
        char letraCorrecta = letras.charAt(nroDni % 23);
        return String.valueOf(nroDni) + letraCorrecta;
    }

    //Método para sacar la fecha de nacimiento formateada
    public String getFechaFormateada(GregorianCalendar g) {
        return String.valueOf(g.get(g.DAY_OF_MONTH) + "/" + (g.get(g.MONTH) + 1) + "/" + g.get(g.YEAR));
    }
}
