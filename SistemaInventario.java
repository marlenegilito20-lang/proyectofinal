/**
 * Interfaz Promocionable
 * Define el comportamiento para aplicar descuentos a los productos.
 */
interface Promocionable {
    double aplicarDescuento(double porcentaje);
}

/**
 * Clase abstracta Producto
 * Define la estructura base y comportamientos comunes e individuales de los productos.
 */
abstract class Producto implements Promocionable {
    protected String id;
    protected String nombre;
    protected double precio;
    protected int stock;

    // Constructor base
    public Producto(String id, String nombre, double precio, int stock) {
        this.id = id;
        this.nombre = nombre;
        this.precio = precio;
        this.stock = stock;
    }

    // Métodos abstractos que deben ser implementados por las subclases (Polimorfismo)
    public abstract double calcularValorTotal();
    public abstract void mostrarDetalles();

    // Método concreto reutilizado por herencia
    public void actualizarStock(int cantidad) {
        this.stock += cantidad;
        System.out.println("Stock actualizado para [" + nombre + "]. Nuevo stock: " + stock);
    }

    // Getters y Setters
    public String getId() { return id; }
    public String getNombre() { return nombre; }
    public double getPrecio() { return precio; }
    public void setPrecio(double precio) { this.precio = precio; }
    public int getStock() { return stock; }
}

/**
 * Subclase Electronico
 * Hereda de Producto e implementa lógica específica para productos electrónicos.
 */
class Electronico extends Producto {
    private int mesesGarantia;

    public Electronico(String id, String nombre, double precio, int stock, int mesesGarantia) {
        super(id, nombre, precio, stock);
        this.mesesGarantia = mesesGarantia;
    }

    @Override
    public double calcularValorTotal() {
        return this.precio * this.stock;
    }

    @Override
    public void mostrarDetalles() {
        System.out.println("[Electrónico] ID: " + id + 
                           " | Nombre: " + nombre + 
                           " | Precio: $" + String.format("%.2f", precio) + 
                           " | Stock: " + stock + 
                           " | Garantía: " + mesesGarantia + " meses");
    }

    @Override
    public double aplicarDescuento(double porcentaje) {
        this.precio -= this.precio * (porcentaje / 100);
        return this.precio;
    }

    public int getMesesGarantia() { return mesesGarantia; }
}

/**
 * Subclase Alimento
 * Hereda de Producto e incluye la fecha de caducidad.
 */
class Alimento extends Producto {
    private String fechaCaducidad;

    public Alimento(String id, String nombre, double precio, int stock, String fechaCaducidad) {
        super(id, nombre, precio, stock);
        this.fechaCaducidad = fechaCaducidad;
    }

    @Override
    public double calcularValorTotal() {
        return this.precio * this.stock;
    }

    @Override
    public void mostrarDetalles() {
        System.out.println("[Alimento] ID: " + id + 
                           " | Nombre: " + nombre + 
                           " | Precio: $" + String.format("%.2f", precio) + 
                           " | Stock: " + stock + 
                           " | Caducidad: " + fechaCaducidad);
    }

    @Override
    public double aplicarDescuento(double porcentaje) {
        this.precio -= this.precio * (porcentaje / 100);
        return this.precio;
    }

    public String getFechaCaducidad() { return fechaCaducidad; }
}

/**
 * Subclase Ropa
 * Hereda de Producto e incluye la talla y el material de confección.
 */
class Ropa extends Producto {
    private String talla;
    private String material;

    public Ropa(String id, String nombre, double precio, int stock, String talla, String material) {
        super(id, nombre, precio, stock);
        this.talla = talla;
        this.material = material;
    }

    @Override
    public double calcularValorTotal() {
        return this.precio * this.stock;
    }

    @Override
    public void mostrarDetalles() {
        System.out.println("[Ropa] ID: " + id + 
                           " | Nombre: " + nombre + 
                           " | Precio: $" + String.format("%.2f", precio) + 
                           " | Stock: " + stock + 
                           " | Talla: " + talla + 
                           " | Material: " + material);
    }

    @Override
    public double aplicarDescuento(double porcentaje) {
        this.precio -= this.precio * (porcentaje / 100);
        return this.precio;
    }

    public String getTalla() { return talla; }
    public String getMaterial() { return material; }
}

/**
 * Clase principal SistemaInventario
 * Contiene el método main para ejecutar las pruebas y demostración de polimorfismo.
 */
public class SistemaInventario {
    public static void main(String[] args) {
        System.out.println("=============================================================");
        System.out.println("   SISTEMA DE GESTIÓN DE INVENTARIOS (DEMO DE POLIMORFISMO)   ");
        System.out.println("=============================================================\n");

        // Polimorfismo: Una lista de tipo abstracto Producto conteniendo objetos de subclases
        java.util.List<Producto> inventario = new java.util.ArrayList<>();

        // Instanciación de las subclases
        inventario.add(new Electronico("E001", "Laptop HP Pavilion", 14500.00, 5, 12));
        inventario.add(new Alimento("A001", "Café de Grano Gourmet 1kg", 280.50, 25, "15/12/2027"));
        inventario.add(new Ropa("R001", "Camisa Algodón Manga Larga", 520.00, 15, "M", "Algodón 100%"));
        inventario.add(new Electronico("E002", "Monitor LG 27 Pulgadas", 4300.00, 8, 24));

        double valorTotalInventario = 0;

        // Iteración polimórfica: invocación uniforme de métodos sobreescribibles
        System.out.println("--- LISTADO GENERAL DE PRODUCTOS EN INVENTARIO ---");
        for (Producto p : inventario) {
            p.mostrarDetalles();
            double valorProducto = p.calcularValorTotal();
            valorTotalInventario += valorProducto;
            System.out.println("    -> Valor acumulado en stock: $" + String.format("%.2f", valorProducto));
            System.out.println("-------------------------------------------------------------");
        }

        System.out.println("\nVALOR TOTAL DE INVENTARIO GENERAL: $" + String.format("%.2f", valorTotalInventario));

        // Demostración de aplicación de interfaz Promocionable y actualización de stock
        System.out.println("\n=== PRUEBAS DE OPERACIONES (DESCUENTOS Y STOCK) ===");
        
        System.out.println("\nAplicando 15% de descuento al producto: " + inventario.get(0).getNombre());
        inventario.get(0).aplicarDescuento(15.0);
        inventario.get(0).mostrarDetalles();

        System.out.println("\nActualizando inventario de: " + inventario.get(1).getNombre());
        inventario.get(1).actualizarStock(10);
        inventario.get(1).mostrarDetalles();
    }
}
