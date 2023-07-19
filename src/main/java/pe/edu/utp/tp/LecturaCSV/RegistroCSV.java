package pe.edu.utp.tp.LecturaCSV;

import pe.edu.utp.tp.Auditoria.Auditoria;
import pe.edu.utp.tp.Utilidades.ProcesadorStrings;

import java.util.Arrays;

// Autor del modulo: Diego Alexis Llacsahuanga Buques

public class RegistroCSV {

	// una cabecera es el nombre de una columna csv
	// | Departamento | Edad | ... | <- cabeceras
	// | Lambayeque   | 17   | ... | <- valor de una fila
	private static String[] cabeceras;
	private String[] valores;

	public RegistroCSV() {
	}

	public static String[] getCabeceras() {
		return cabeceras;
	}

	public String[] getValores() {
		return valores;
	}

	public static void setCabeceras(String[] cabeceras) {
		RegistroCSV.cabeceras = (String[]) Arrays.stream(cabeceras)
				.map(ProcesadorStrings::EliminaCaracteresInvisibles)
				.toArray(String[]::new);
	}

	public static void setCabecerasVacias() {
		RegistroCSV.cabeceras = new String[0];
	}

	// copia los valores del argumento, pero trunca la longitud para que sea igual a la del
	// arreglo de cabeceras (no pueden haber más valores que cabeceras).
	public void setValores(String[] valores) {

		if (RegistroCSV.cabeceras == null) {
			Auditoria audi = new Auditoria();
			audi.RegistrarExcepcion(new Exception("No se pueden inicializar los valores de un registro si no se han definido las cabeceras."));
		}

		// TODO: Esto quizás deba ser optimizado para cuando this.valores != null
		this.valores = Arrays.copyOfRange(valores, 0, RegistroCSV.cabeceras.length);
	}

	private int BuscarCampoEnCabeceras(String campo) {
		for (int i = 0; i < cabeceras.length; i++)
			if (campo.trim().equals(cabeceras[i].strip())) {
				return i;
			}
		return -1;
	}

	public String ValorDeCampo(String campo) {
		int i = this.BuscarCampoEnCabeceras(campo);
		return (i == -1) ? null : this.valores[i];
	}

	@Override
	public String toString() {
		return "RegistroCSV{" +
				"valores=" + Arrays.toString(valores) +
				'}';
	}
}
