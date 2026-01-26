import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.DisplayName;
import static org.junit.jupiter.api.Assertions.*;
import java.io.ByteArrayOutputStream;
import java.io.PrintStream;

class FacturadorTest {
    
	@Test
	@DisplayName("Test constructor válido")
    public void testConstructorValido() {
        Facturador facturador = new Facturador();
		assertNotNull(facturador, "El constructor devuelve null.");
    }
	
	@Test
	@DisplayName("Test cabecera correcta")
    void testFacturaCompleta() {
        // Guardamos la salida de la consola original en "salidaConsola"
        PrintStream salidaConsola = System.out;
		
        // Creamos un flujo de salida en memoria donde se guardará lo que se imprima
        ByteArrayOutputStream salida = new ByteArrayOutputStream();
		
		// Creamos un PrintStream ("salidaTest") que escribirá dentro del ByteArrayOutputStream
		PrintStream salidaTest = new PrintStream(salida);
        
		// Redirigimos System.out para que apunte a nuestra salida ("salidaTest")
		System.setOut(salidaTest);
		
		try {
			// Ejecutamos el main (Todo lo que imprima va a salida)
			Facturador.main(new String[]{});
		} catch (Exception e) {
			fail("Excepción inesperada.");
		}
        
		// Restauramos la salida estándar de la consola (buena práctica)
        System.setOut(salidaConsola);
		
		// Convertimos todo lo impreso a texto
        String output = salida.toString();
		
        // Comprobamos que el programa imprimió lo correcto
        assertTrue(output.contains("FACTURA DE ACTUACIONES"));
		assertTrue(output.contains("Cliente: Ayuntamiento de Badajoz"));
		assertTrue(output.contains("BASE IMPONIBLE: 72800.0 euros"));
		assertTrue(output.contains("IVA (21%): 15288,00 euros"));
		assertTrue(output.contains("TOTAL FACTURA: 88088,00 euros"));
		assertTrue(output.contains("Créditos obtenidos: 4108"));
    }
}