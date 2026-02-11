import java.util.*;

public class Facturador{

	private static final Float PORCENTAJE_IVA = 0.21f;
	private static final Double BASE_HEAVY = 4000d;
	private static final Double BASE_ROCK = 3000d;
	private static final Integer UMBRAL_HEAVY = 20;
	private static final Integer UMBRAL_ROCK = 30;
	private static final Integer EXTRA_HEAVY = 500;
	private static final Integer EXTRA_ROCK = 1000;
	private static final Integer RELACION_ASISTENCIAS_CREDITOS = 5;
	
	private enum TipoConcierto { HEAVY, ROCK };

	//Repertorio de conciertos del grupo
	static String[][] conciertos = {
		 {"Tributo Robe", "heavy"}
		,{"Homenaje Queen", "rock"}
		,{"Magia Knoppler", "rock"}
		,{"Demonios Rojos", "heavy"}
	};

	//Actuaciones realizadas indicando el concierto ofrecido y audiencias obtenidas.
	static Integer[][] datosActuaciones = {{0, 2000}, {2, 1200}, {0, 950}, {3, 1140}};
	
	static String cliente = "Ayuntamiento de Badajoz";
	
	public static void main(String[] args) throws Exception{
		Double totalFactura = 0d;
		Integer creditos = 0;

		System.out.println("FACTURA DE ACTUACIONES");
		System.out.println("Cliente: " + cliente);
		
		List<Actuacion> listaActuaciones = crearListaActuaciones(datosActuaciones);

		for (Actuacion actuacion : listaActuaciones) {
			Integer indiceConcierto = actuacion.indiceConcierto();
			Integer asistentes = actuacion.asistentes();
			
			String tipoActuacion = conciertos[indiceConcierto][1];
			
			totalFactura += calcularImporteActuacion(tipoActuacion, asistentes);
			creditos += calcularCreditos(tipoActuacion, asistentes);
			
			System.out.println("\tConcierto: " + conciertos[indiceConcierto][0]);
			System.out.println("\t\tAsistentes: " + asistentes);
			
		}
		System.out.println("BASE IMPONIBLE: " + totalFactura + " euros");
		System.out.printf("IVA (21%%): %.2f euros\n", totalFactura * PORCENTAJE_IVA);
		System.out.printf("TOTAL FACTURA: %.2f euros\n", totalFactura * (1 + PORCENTAJE_IVA));
		System.out.println("Créditos obtenidos: " + creditos);
	}
	
	public static List<Actuacion> crearListaActuaciones(Integer[][] datosActuaciones) {
		List<Actuacion> listaActuaciones = new ArrayList<>();
		for (Integer[] datosActuacion : datosActuaciones) {
			Integer indiceConcierto = datosActuacion[0];
			Integer asistentes = datosActuacion[1];
			Actuacion actuacion = new Actuacion(indiceConcierto, asistentes);
			listaActuaciones.add(actuacion);
		}
		return listaActuaciones;
	}
	
	public static Double calcularImporteActuacion(String tipo, Integer asistentes) throws Exception {
		Double importeActuacion = 0d;
		TipoConcierto tipoConcierto = TipoConcierto.valueOf(tipo.trim().toUpperCase());
		switch (tipoConcierto){
			case HEAVY:
				importeActuacion = BASE_HEAVY;
				if (asistentes > EXTRA_HEAVY)
					importeActuacion += UMBRAL_HEAVY * (asistentes - EXTRA_HEAVY);
				break;
			case ROCK:
				importeActuacion = BASE_ROCK;
				if (asistentes > EXTRA_ROCK)
					importeActuacion += UMBRAL_ROCK * (asistentes - EXTRA_ROCK);
				break;
			default:
				throw new Exception("Tipo de concierto desconocido.");
		}
		return importeActuacion;
	}
	
	public static Integer calcularCreditos(String tipo, Integer asistentes) throws Exception {
		Integer creditos = 0;
		creditos += Math.max(asistentes - EXTRA_HEAVY, 0);
		if (tipo.equalsIgnoreCase("heavy"))
			creditos += asistentes / RELACION_ASISTENCIAS_CREDITOS;
		return creditos;
	}
}

record Actuacion(Integer indiceConcierto, Integer asistentes) {}