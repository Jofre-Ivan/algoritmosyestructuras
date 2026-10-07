package ejercicios.guia1;

/**
 * Implementa una búsqueda binaria sobre vectores ordenados de menor a mayor.
 */
public class ej3 {

	 /**
	  * Busca un número dividiendo el rango de búsqueda por la mitad en cada iteración.
	  * Complejidad esperada: tiempo O(log n) y espacio O(1).
	  *
	  * @param vector vector ordenado donde se realizará la búsqueda
	  * @param numero número que se desea encontrar
	  * @return posición del número o -1 si no existe
	  */
	public static int buscarBinario(int[] vector, int numero) {
		if (vector == null || vector.length == 0) {
			System.out.println("Vector vacío");
			return -1;
		}

		int inicio = 0;
		int fin = vector.length - 1;

		while (inicio <= fin) {
			int medio = inicio + (fin - inicio) / 2;
			int valorMedio = vector[medio];

			System.out.println("inicio=" + inicio
					+ ", fin=" + fin
					+ ", medio=" + medio
					+ ", valorMedio=" + valorMedio);

			if (valorMedio == numero) {
				System.out.println("Decisión: elemento encontrado");
				return medio;
			}

			if (valorMedio < numero) {
				System.out.println("Decisión: buscar en la mitad derecha");
				inicio = medio + 1;
			} else {
				System.out.println("Decisión: buscar en la mitad izquierda");
				fin = medio - 1;
			}
		}

		System.out.println("Decisión: elemento inexistente");
		return -1;
	}

	/**
	 * Ejecuta una búsqueda y muestra su evolución y el resultado.
	 *
	 * @param nombre descripción del caso de prueba
	 * @param vector vector ordenado donde se buscará
	 * @param numero número que se desea encontrar
	 */
	public static void ejecutarCaso(String nombre, int[] vector, int numero) {
		System.out.println("\n" + nombre + ": buscar " + numero);
		int posicion = buscarBinario(vector, numero);
		System.out.println("Resultado: " + posicion);
	}

	/**
	 * Prueba la búsqueda con números en los extremos, ausentes y vectores límite.
	 *
	 * @param args argumentos de la línea de comandos
	 */
	public static void main(String[] args) {
		int[] vector = {2, 5, 8, 12, 16, 21, 27};

		ejecutarCaso("Número al inicio", vector, 2);
		ejecutarCaso("Número al final", vector, 27);
		ejecutarCaso("Número inexistente", vector, 10);
		ejecutarCaso("Vector con un solo elemento", new int[]{7}, 7);
		ejecutarCaso("Vector vacío", new int[]{}, 10);
	}
}
