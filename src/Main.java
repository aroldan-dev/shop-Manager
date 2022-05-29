
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Scanner;
import java.util.TreeMap;
import java.util.TreeSet;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Main.java to edit this template
 */
/**
 *
 * @author alex
 */
public class Main {

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        // TODO code application logic here

        Scanner s = new Scanner(System.in);//Scanner que vamos a usar a la hora de alta de objetos

        //Objeto tienda que será la piedra angular de todo
        Tienda t = new Tienda();

        //Lista de Clientes a la cual se le añadirán clientes desde un fichero Serializado
        LinkedList<Cliente> clientes = new LinkedList<>();

        //Método muy importante que se encarga de cargar todos los datos desde ficheros serializados
        //tanto en la tienda (stock y ventas ya realizadas) como en la lista de clientes
        cargarDatos(t, clientes);

        //Lista de vendedores, esta vez no serializados solo tendremos disponibles los que instanciemos en el main
        LinkedList<Vendedor> vendedores = new LinkedList<>();
        vendedores.add(new Vendedor("Amaia", "Cerezo", "92503475P", 2000, 3, 1));
        vendedores.add(new Vendedor("Maximino", "Llopis", "60533895L", 1999, 3, 11));
        vendedores.add(new Vendedor("Damián", "Gallart ", "48408394H", 1978, 12, 10));
        vendedores.add(new Vendedor("Demetrio", "Vallejo", "29250561N", 1989, 5, 9));
        vendedores.add(new Vendedor("Eloísa", "Contreras Blasco", "47086890U", 1988, 2, 18));

        int opcion;//Variable que almacenará la eleccion en nuestro menú

        /**
         * Como sobre nuestro set de articulos no podemos realizar operaciones
         * para obtener los elementos de forma posicional vamos a crearnos un
         * ArrayList de forma auxiliar para este acceso posicional. SOLO ESTA
         * USADO DURANTE EL MENÚ y de forma AUXILIAR.
         */
        List<Articulo> articulos = new ArrayList<>();
//        articulos.addAll(t.getStockTotal());//Añadimos todos los articulos de el stock de la tienda

        boolean dentro = true;//Variable para mantenernos en el Main al menos que no salgamos
        while (dentro) {
            articulos.clear();//Vaciamos la lista de articulos por cada vuelta de el bucle

            //Añadimos todos desde la tienda, ya que si damos de alta un articulo nuevo y volvemos a entrar en el bucle,
            //debemos volver a cargar todo desde el stock de la tienda al ser articulos un elemento AUXILIAR
            articulos.addAll(t.getStockTotal());
            //Todo dentro de un try catch en caso de cualquier error durante la inserción de datos, volveremos al INICIO
            //Por eso para salir desde cualquier parte pulsamos 'X'
            try {
                //El método imprimirMenu imprime las opciones y devuelve la opcion elegida
                opcion = imprimirMenu();//Asignamos la eleccion a la variable opcion
                //Segun la opcion entraremos en un proceso u otro, gracias al SWITCH
                switch (opcion) {
                    //CASE 1 ---> HACER UNA NUEVA VENTA
                    case 1:
                        Vendedor v;//Vendedor que realizará la venta
                        int seleccionVendedor = 0;//numero para seleccionar al vendedor que queramos
                        int i = 0;
                        //Bucle que muestra todos los vendedores disponibles
                        for (Vendedor ve : vendedores) {
                            System.out.printf("%2d -- %s\n", i, ve.getNombre().toUpperCase());
                            i++;
                        }
                        //Seleccionamos el vendedor siempre que este sea un número correcto
                        do {
                            System.out.print("VENDEDOR ELEGIDO --> ");
                            seleccionVendedor = Integer.parseInt(s.nextLine());

                            if (seleccionVendedor < 0 || seleccionVendedor > vendedores.size() - 1) {
                                System.out.print("ELIGE UN NUEVO VENDEDOR --> ");
                                seleccionVendedor = Integer.parseInt(s.nextLine());
                            }
                        } while (seleccionVendedor < 0 || seleccionVendedor > vendedores.size() - 1);
                        //Asignamos el vendedor elegido al vendedor que hara nuestra Venta
                        v = vendedores.get(seleccionVendedor);

                        Cliente c;//Cliente que realizará la compra
                        int seleccionCliente;//Valor para seleccionar al cliente
                        i = 0;
                        //Mostramos los clientes
                        for (Cliente ce : clientes) {
                            System.out.printf("%2d -- %s\n", i, ce.getNombre().toUpperCase());
                            i++;
                        }
                        //Seleccionamos al cliente elegido
                        do {
                            System.out.print("CLIENTE ELEGIDO --> ");
                            seleccionCliente = Integer.parseInt(s.nextLine());

                            if (seleccionCliente < 0 || seleccionCliente > clientes.size() - 1) {
                                System.out.print("ELIGE UN CLIENTE --> ");
                                seleccionCliente = Integer.parseInt(s.nextLine());
                            }
                        } while (seleccionCliente < 0 || seleccionCliente > clientes.size() - 1);

                        //Asignamos al cliente que hara la compra el cliente elegido
                        c = clientes.get(seleccionCliente);

                        //Creamos un nuevo mapa que contendrá el carrito del cliente con estructura ARTICULO(key),CANTIDAD(value)
                        LinkedHashMap<Articulo, Integer> carrito_cliente = new LinkedHashMap<>();

                        //Llamamos al método mostrarArticulos de la tienda t para que nos muestre con formato todos los articulos que tenemos actualmente
                        mostrarArticulos(t);
                        //Preguntamos los articulos diferentes que desea llevarse INDEPENDIENTEMENTE de la cantidad de cada uno
                        int totalArticulos = 0;
                        do {
                            System.out.print("\nNúmero de articulos diferentes a agregar: ");
                            totalArticulos = Integer.parseInt(s.nextLine());
                            if (totalArticulos < 0 || totalArticulos > articulos.size() - 1) {
                                System.out.println("NUMERO INCORRECTO PRUEBE DE NUEVO --> ");
                                totalArticulos = Integer.parseInt(s.nextLine());
                            }
                        } while (totalArticulos < 0 || totalArticulos > articulos.size() - 1);

                        int cont = 1;//Inicializamos cont simplemente para ir mostrando el articulo que vamos añadiendo
                        //Bucle que va preguntando articulo a articulo, con su identificador y cantidad a agregar
                        while (totalArticulos != 0) {
                            int nArticulo = 0;
                            int cantidad = 0;
                            do {
                                System.out.println("ARTICULO " + cont);//Para saber por que articulo vamos 
                                System.out.print("NUMERO   --> ");//Preguntamos sobre el identificador del articulo
                                nArticulo = Integer.parseInt(s.nextLine());
                                System.out.print("CANTIDAD --> ");
                                cantidad = Integer.parseInt(s.nextLine());
                                if (nArticulo < 0 || nArticulo > articulos.size() || cantidad < 1) {
                                    System.out.print("Articulo o cantidad incorrecto, prueba de nuevo \n");
                                }
                            } while (nArticulo < 0 || nArticulo > articulos.size() || cantidad < 0);

                            //Si todos los datos han sido correctamente introducimos añadimos al carrita el ARTICULO,CANTIDAD
                            carrito_cliente.put(articulos.get(nArticulo), cantidad);
                            totalArticulos--;//Restamos 1 al total de articulos para seguir con el bucle
                            cont++;//Sumamos uno al contador para mostrar el siguiente articulo 
                        }
                        //Cuando terminamos de rellenar el carrtio creamos la nueva venta
                        Venta venta = new Venta(c, v, carrito_cliente);//Con el cliente c, vendedor v y el carrito_cliente
                        v.anadirVenta(venta, t);//El vendedor añade la venta a la tienda
                        System.out.println(t.imprimirFactura(venta));//Imprimimos la venta
                        System.out.println("\n\nVENTA FINALIZADA -- IMPRIMIENDO FACTURA ...");
                        limpiarCLI();//Llamamo a limpiar CLI [COMMAND LINE INTERFACE] para tener la terminal un poco mas limpia
                        break;
                    //Alta Articulo
                    case 2:
                        int op2;//Selector de opcion para añadir ropa zapatillas o accesorios
                        System.out.println("1.ROPA\n2.ZAPATILLAS\n3.ACCESORIOS");
                        do {
                            System.out.print("TIPO ARTICULO --> ");
                            op2 = Integer.parseInt(s.nextLine());
                        } while (op2 < 1 || op2 > 3);
                        //Segun la opcion elegida
                        switch (op2) {
                            //Añadir ROPA
                            case 1:
                                //Vamos preguntado atributo a atributo segun la clase
                                System.out.printf("%-10s -->", "TALLA");
                                String tallaR = s.nextLine();
                                System.out.printf("%-10s -->", "TIPO TALLA");
                                String tipoTallajeR = s.nextLine();
                                System.out.printf("%-10s -->", "TEMPORADA");
                                String temporadaR = s.nextLine();
                                System.out.printf("%-10s -->", "CATEGORIA");
                                String CategoriaR = s.nextLine();
                                System.out.printf("%-10s -->", "NOMBRE");
                                String nombreR = s.nextLine();
                                System.out.printf("%-10s -->", "PRECIO");
                                double precioR = Double.parseDouble(s.nextLine());
                                System.out.printf("%-10s -->", "STOCK");
                                int stockR = Integer.parseInt(s.nextLine());
                                System.out.printf("%-10s -->", "DESC");
                                String descripcionR = s.nextLine();
                                System.out.printf("%-10s -->", "MARCA");
                                String marcaR = s.nextLine();
                                System.out.printf("%-10s -->", "COLOR");
                                String colorR = s.nextLine();
                                //Instanciamos el nuevo Articulo en este caso ROPA
                                Articulo aR = new Ropa(tallaR, tipoTallajeR, temporadaR, CategoriaR, nombreR, precioR, stockR, descripcionR, marcaR, colorR);
                                //El primer vendedor añade el articuloa la tienda
                                vendedores.get(0).altaArticulo(aR, t);
                                System.out.println("ROPA AÑADIDA...");
                                limpiarCLI();
                                break;
                            //Añadir ZAPATILLA
                            case 2:
                                //Vamos preguntado atributo a atributo segun la clase
                                System.out.printf("%-10s -->", "TALLA");
                                double tallaZ = Double.parseDouble(s.nextLine());
                                System.out.printf("%-10s -->", "CATEGORIA");
                                String CategoriaZ = s.nextLine();
                                System.out.printf("%-10s -->", "NOMBRE");
                                String nombreZ = s.nextLine();
                                System.out.printf("%-10s -->", "PRECIO");
                                double precioZ = Double.parseDouble(s.nextLine());
                                System.out.printf("%-10s -->", "STOCK");
                                int stockZ = Integer.parseInt(s.nextLine());
                                System.out.printf("%-10s -->", "DESC");
                                String descripcionZ = s.nextLine();
                                System.out.printf("%-10s -->", "MARCA");
                                String marcaZ = s.nextLine();
                                System.out.printf("%-10s -->", "COLOR");
                                String colorZ = s.nextLine();
                                //Instanciamos el nuevo Articulo en este caso ZAPATAILLA
                                Articulo aZ = new Zapatilla(tallaZ, CategoriaZ, marcaZ, nombreZ, precioZ, stockZ, descripcionZ, marcaZ, colorZ);
                                //El primer vendedor añade el articuloa la tienda
                                vendedores.get(0).altaArticulo(aZ, t);
                                System.out.println("ZAPATILLA AÑADIDA...");
                                limpiarCLI();
                                break;
                            //Añadir ACCESORIO
                            case 3:
                                //Vamos preguntado atributo a atributo segun la clase
                                System.out.printf("%-10s -->", "TALLA");
                                String tallaA = s.nextLine();
                                System.out.printf("%-10s -->", "CATEGORIA");
                                String CategoriaA = s.nextLine();
                                System.out.printf("%-10s -->", "NOMBRE");
                                String nombreA = s.nextLine();
                                System.out.printf("%-10s -->", "PRECIO");
                                double precioA = Double.parseDouble(s.nextLine());
                                System.out.printf("%-10s -->", "STOCK");
                                int stockA = Integer.parseInt(s.nextLine());
                                System.out.printf("%-10s -->", "DESC");
                                String descripcionA = s.nextLine();
                                System.out.printf("%-10s -->", "MARCA");
                                String marcaA = s.nextLine();
                                System.out.printf("%-10s -->", "COLOR");
                                String colorA = s.nextLine();
                                //Instanciamos el nuevo Articulo en este caso ACCESORIO
                                Articulo aA = new Accesorio(tallaA, nombreA, CategoriaA, precioA, stockA, descripcionA, marcaA, colorA);
                                //El primer vendedor añade el articuloa la tienda
                                vendedores.get(0).altaArticulo(aA, t);
                                System.out.println("ACCESORIO AÑADIDO...");
                                limpiarCLI();
                                break;
                            default:

                        }
                        break;
                    //BAJA DE ARTICULO
                    case 3:
                        //Mostramos todos los articulos
                        mostrarArticulos(t);
                        int nArticuloAborrar;//Variable que almacena el articulo a borrar
                        //Preguntamos por el articulo
                        do {
                            System.out.print("N ARTICULO A BORRAR --> ");
                            nArticuloAborrar = Integer.parseInt(s.nextLine());
                        } while (nArticuloAborrar < 0 || nArticuloAborrar > t.getStockTotal().size() - 1);
                        //El primer vendedor da de baja el articulo de la tienda
                        vendedores.get(0).bajaArticulo(articulos.get(nArticuloAborrar), t);
                        limpiarCLI();
                        break;
                    //REPONER STOCK
                    case 4:
                        //Mostramos todos los articulos
                        mostrarArticulos(t);
                        int nArticuloStock;//Variable que almacena el articulo a reponer el stock
                        int cantidad;//Cantidad a reponer
                        //Preguntamos por el numero de articulo y la cantidad
                        do {
                            System.out.print("N ARTICULO A REPONER --> ");
                            nArticuloStock = Integer.parseInt(s.nextLine());
                            System.out.print("CANTIDAD              --> ");
                            cantidad = Integer.parseInt(s.nextLine());
                        } while ((nArticuloStock < 0 || nArticuloStock > t.getStockTotal().size() - 1) || (cantidad < 1));
                        //Accedemos al articulo de forma posicional y reponemos el stock introducido
                        articulos.get(nArticuloStock).reponerStock(cantidad);
                        limpiarCLI();
                        break;
                    //DAR DE ALTA UN NUEVO CLIENTE    
                    case 5:
                        //Preguntamos todos los datos para instanciar un nuevo CLIENTE
                        System.out.printf("%-10s -->", "NOMBRE");
                        String nombreC = s.nextLine();
                        System.out.printf("%-10s -->", "APELLIDOS");
                        String apellidosC = s.nextLine();
                        String dniC = "";
                        String patron = "(?i)[0-9]{7,9}[a-z]{1}";
                        Pattern p = Pattern.compile(patron);
                        Matcher m;
                        do {
                            System.out.printf("%-10s -->", "DNI");
                            dniC = s.nextLine();
                            m = p.matcher(dniC);
                        } while (!m.matches());
                        System.out.printf("%-10s -->", "AÑO NACIMIENTO");
                        int anioC = Integer.parseInt(s.nextLine());
                        System.out.printf("%-10s -->", "MES NACIMIENTO");
                        int mesC = Integer.parseInt(s.nextLine());
                        System.out.printf("%-10s -->", "DIA NACIMIENTO");
                        int diaC = Integer.parseInt(s.nextLine());
                        //Creamos el cliente
                        Cliente cli = new Cliente(nombreC, apellidosC, dniC, anioC, mesC, diaC);
                        //Añadimos el cliente creado a nuestro conjunto de clientes
                        clientes.add(cli);
                        System.out.println("CLIENTE AÑADIDO");
                        limpiarCLI();
                        break;
                    //HACER SOCIO A UN CLIENTE
                    case 6:
                        //Los socios gozan de un 5% sobre sus compras
                        int selecCliente;//Variable que almacena el socio elegido
                        i = 0;
                        //IMPRIMIMOS LOS SOCIOS CON LA ESTRUCTURA NUMERO NOMBRE SOCIO (true|false)
                        for (Cliente ce : clientes) {
                            System.out.printf("%2d -- %15s -- SOCIO [%b]\n", i, ce.getNombre().toUpperCase(), ce.getEsSocio());
                            i++;
                        }
                        do {
                            System.out.print("CLIENTE ELEGIDO --> ");
                            selecCliente = Integer.parseInt(s.nextLine());
                            if (selecCliente < 0 || selecCliente > clientes.size() - 1) {
                                System.out.print("Cliente incorrecto, pruebe de nuevo --> ");
                                selecCliente = Integer.parseInt(s.nextLine());
                            }
                        } while (selecCliente < 0 || selecCliente > clientes.size() - 1);
                        //Una vez obtenido el cliente desdeado le asignamos true a esSocio
                        clientes.get(selecCliente).setEsSocio(true);
                        limpiarCLI();
                        break;
                    //MOSTRAR ELEMENTOS
                    case 7:
                        int selecMostrar = 0;//Variable que guarda la seleccion 1 --> MOSTRAR STOCK ||| 2 ---> MOSTRAR CLIENTES
                        System.out.println("1.STOCK\n2.CLIENTES\n3.VENTAS");
                        do {
                            System.out.print("Escoja una opción: ");
                            selecMostrar = Integer.parseInt(s.nextLine());
                            if (selecMostrar == 1) {
                                mostrarArticulos(t);//Llamamos al método mostrarArticulo
                                limpiarCLI();
                            } else if (selecMostrar == 2) {//CLIENTE
                                System.out.printf("\n%-9s    %-15s    %-20s %11s  %5s\n", "DNI", "NOMBRE", "APELLIDOS", "FECHA NAC", "SOCIO");
                                System.out.println("=======================================================================");
                                for (Cliente client : clientes) {
                                    //Mostramos lo clientes formateados
                                    System.out.printf("%9s -- %-15s -- %-20s %11s  %5s\n", client.getDni(), client.getNombre().toUpperCase(), client.getApellidos().toUpperCase(), client.getFechaFormateada(client.getFechaNacimiento()), client.getEsSocio());
                                }
                            } else if (selecMostrar == 3) {//Ventas
                                for (Entry<Integer, Venta> ventas : t.getVentas().entrySet()) {
                                    Integer id_entry = ventas.getKey();
                                    Venta venta_entry = ventas.getValue();
                                    System.out.printf("ID[%2d] -- %s\n", id_entry, venta_entry.getFechaFormateada(venta_entry.getFechaVenta()));
                                }
                                int selecVenta;
                                do {
                                    System.out.print("ID VENTA ---> ");
                                    selecVenta = Integer.parseInt(s.nextLine());
                                } while (selecVenta < 0 || selecVenta > t.getVentas().size());
                                System.out.println(t.imprimirFactura(t.getVentas().get(selecVenta)));
                            } else {
                                System.out.println("ERROR DE SELECCION");
                                selecMostrar = 0;
                            }
                        } while (selecMostrar == 0);
                        limpiarCLI();
                        break;

                    //GENERAR TODAS LAS FACTURAS .txt
                    case 8:
                        //Recorre todas las ventas que tenemos almacenadas en la tienda
                        for (Entry<Integer, Venta> ventas : t.getVentas().entrySet()) {
                            Venta venta_entry = ventas.getValue();
                            //Llama al metodo imprimir factura sin imprimirlo por pantalla, es decir solo genera los .txt de cada venta 
                            t.imprimirFactura(venta_entry);
                        }
                        System.out.println("TODAS LAS FACTURAS HAN SIDO GENERADAS EN EL DIRECTORIO --> ./facturas/ ");
                        limpiarCLI();
                        break;
                    //SALIR DEL SISTEMA
                    case 9:
                        descargarDatos(t, clientes);//DESCARGAMOS TODOS LOS DATOS EN LOS FICHEROS SERIALIZADOS
                        dentro = false;//Salimos del bucle while grande para terminar la ejecución
                    default:
                }

            } catch (ArrayIndexOutOfBoundsException e) {
                System.out.println("OPCION INCORRECTA SALIENDO ...");
                limpiarCLI();
            } catch (Exception e) {
                System.err.println("VOLVIENDO AL MENÚ --ERROR--");
                limpiarCLI();

            }
        }
    }

    //Metodo para limpiar la terminal imprimiendo muchos saltos de línea
    public static void limpiarCLI() {
        System.out.print("\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n");
    }

    //Método para mostrar todos los articulos de la tienda pasada por parametro
    public static void mostrarArticulos(Tienda t) {
        int i = 0;
        //Bucle for para recorrer todos los articulos
        for (Articulo A : t.getStockTotal()) {
            System.out.printf("N[%2d] STOCK [%4d] -- %-20s -- %6s -- %-10s\n", i, A.getStock(), A.getNombre(), A.getTallaArticulo(), A.getColor());
            i++;
        }
    }

    //Método para imprimir el menú principal que devuelve un numero como selección
    public static int imprimirMenu() {
        System.out.println("========================================================");
        System.out.printf("=%25s%s%24s=\n", "", "STOCK", "");
        System.out.println("========================================================");
        System.out.printf("%d- %-15s %15s %d- %-10s\n", 1, "NUEVA VENTA", "", 6, "HACER SOCIO");
        System.out.printf("%d- %-15s %15s %d- %-10s\n", 2, "ALTA ARTICULO", "", 7, "CONSULTAR DATOS");
        System.out.printf("%d- %-15s %15s %d- %-10s\n", 3, "BAJA ARTICULO", "", 8, "GENERAR FACTURAS");
        System.out.printf("%d- %-15s %15s %d- %-10s\n", 4, "REPONER STOCK", "", 9, "SALIR");
        System.out.printf("%d- %-15s\n\n", 5, "NUEVO CLIENTE");
        System.out.print("Escoja una opción : ");
        int opcion;
        Scanner s = new Scanner(System.in);
        do {
            opcion = Integer.parseInt(s.nextLine());
            if (opcion < 1 || opcion > 10) {
                System.out.print("Escoja una opción válida: ");
            }

        } while (opcion < 1 || opcion > 10);
        return opcion;
    }

    //Método para cargarDatos desde ficheros serializados y meterlos en la tienda y Lista pasados como parametros
    public static void cargarDatos(Tienda T, LinkedList<Cliente> C) {
        //Creamos un conjunto y un set que mas tarde usaremos y asignaremos a nuestra Tienda T
        TreeSet<Articulo> stockTienda = new TreeSet<>();
        TreeMap<Integer, Venta> ventasTienda = new TreeMap<>();
        //Leemos el objeto Tienda desde el fichero serializado Tienda.ser
        try {
            FileInputStream fs = new FileInputStream("tienda.ser");
            ObjectInputStream ois = new ObjectInputStream(fs);
            //Objeto tienda que he leido y usare para importar los datos en mi nueva tienda
            Tienda tiendaAux = (Tienda) ois.readObject();

            //Como queremos mantener los id's de productos deberemos copiar todos los Articulos
            //Si no lo hicieramos los id's coincidirian y al comparar dos tipo Articulo por id's los nuevos nunca serian añadidos
            TreeSet<Articulo> stockAux = new TreeSet<>();//TreeSet intermediario para encapsular los datos correctamente
            for (Articulo A : tiendaAux.getStockTotal()) {
                if (A instanceof Ropa) {
                    stockAux.add(new Ropa((Ropa) A));
                }
                if (A instanceof Zapatilla) {
                    stockAux.add(new Zapatilla((Zapatilla) A));
                }
                if (A instanceof Accesorio) {
                    stockAux.add(new Accesorio((Accesorio) A));
                }
            }

            //Realizamos lo mismo pero con las ventas, de esta forma mantendremos los id's fechas y datos de cada Venta
            TreeMap<Integer, Venta> ventasAux = new TreeMap<>();//TreeMap intermediario para encapsular los datos correctamente
            for (Map.Entry<Integer, Venta> e : tiendaAux.getVentas().entrySet()) {
                Integer id = e.getKey();
                Venta v = e.getValue();

                ventasAux.put(id, new Venta(v));
            }

            FileInputStream fs2 = new FileInputStream("Clientes.ser");
            ObjectInputStream ois2 = new ObjectInputStream(fs2);
            LinkedList<Cliente> aux = (LinkedList<Cliente>) ois2.readObject();
            for (Cliente c : aux) {
                C.add(new Cliente(c));
            }
            /**
             * stockTienda es el TreeSet que añadiremos a nuestra Tienda, le
             * añadimos todos los articulos que hemos recogido en stockAux
             *
             * ventasTienda es el TreeMap que añadiremos a nuestra Tienda, le
             * añadimos todas las ventas que hemos recogido en ventasAux
             */
            stockTienda.addAll(stockAux);
            ventasTienda.putAll(ventasAux);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }

        //Por último asignamos como stockTotal y Ventas las estructuras de datos recién terminadas y rellenas
        T.setStockTotal(stockTienda);
        T.setVentas(ventasTienda);
    }

    //Método estatico para volcar todos los datos de la Tienda T en nuestro fichero tienda.ser
    public static void descargarDatos(Tienda T, LinkedList<Cliente> C) {

        try {
            //En el constreuctor de FileOutputStream añadimos el parametro false para que sobreescriba los datos
            //Y asi siempre tengamos los datos correctamente almacenados
            FileOutputStream fs = new FileOutputStream("tienda.ser", false);
            ObjectOutputStream os = new ObjectOutputStream(fs);
            os.writeObject(T);//Serializamos el objeto Tienda
            os.close();
            fs.close();

            FileOutputStream fs2 = new FileOutputStream("Clientes.ser", false);
            ObjectOutputStream os2 = new ObjectOutputStream(fs2);
            os2.writeObject(C);//Serializamos el objeto Tienda
            os2.close();
            fs2.close();

        } catch (FileNotFoundException e) {
            System.out.println(e.getMessage());
        } catch (IOException e) {
            System.out.println(e.getMessage());
        }
    }
}
