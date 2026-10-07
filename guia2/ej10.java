package ejercicios.guia2;
/*Quiero implementar en Java una función recursiva que busque un número dentro de un arreglo de enteros, sin usar ciclos.

Este problema puede resolverse recursivamente porque buscar un valor en un arreglo equivale a revisar la posición actual y, si ahí no está, buscar el mismo valor en el resto del arreglo. Para no copiar el arreglo, el "resto" se representa con un índice que avanza. Es decir, `buscar(arreglo, valor, indice)` revisa `arreglo[indice]` y, si no coincide, delega en `buscar(arreglo, valor, indice + 1)`.

La búsqueda empieza desde la posición `0`. Para que quien use la función no tenga que pasar ese índice, quiero un método público `buscar(int[] arreglo, int valor)` que valide la entrada y llame a un método auxiliar privado `buscar(int[] arreglo, int valor, int indice)` con `indice = 0`. El método auxiliar es el que contiene la recursión.

Para avanzar en el arreglo sin usar `for` ni `while`, en cada llamada recursiva el índice aumenta en 1 (`indice + 1`). La repetición la produce la propia recursión: cada llamada revisa una sola posición y se encarga de las siguientes mediante una nueva llamada.

Hay dos casos base:
- Cuando encuentra el valor (`arreglo[indice] == valor`), la función devuelve `indice`, porque ya cumplió su objetivo y no hace falta seguir buscando.
- Cuando llega al final (`indice == arreglo.length`), la función devuelve `-1`, porque se revisaron todas las posiciones sin encontrar el valor. Esta condición se chequea antes de acceder a `arreglo[indice]`, para evitar un `ArrayIndexOutOfBoundsException`.

El caso recursivo es `buscar(arreglo, valor, indice + 1)`, porque en cada llamada el índice avanza una posición y queda menos arreglo por revisar, acercando el problema al caso base de fin de arreglo. Además, la recursión termina siempre: o se encuentra el valor antes, o el índice llega a `arreglo.length`.

La función debe devolver un `int`:
- el índice de la primera aparición del valor, si está en el arreglo;
- `-1` si el valor no está en el arreglo o si el arreglo está vacío.

La función debe validar la entrada: si `arreglo` es `null`, debe lanzar una `IllegalArgumentException` con un mensaje claro. No debe usar `for`, `while`, `do-while`, streams, `Arrays.binarySearch`, `Arrays.asList(...).indexOf` ni ninguna otra función de búsqueda de la librería estándar.

Quiero que el código tenga comentarios explicando los dos casos base, el caso recursivo y la pila de llamadas. En particular, quiero que incluya un ejemplo de la pila de llamadas para `buscar([4, 8, 15, 16], 15)`, mostrando cómo se apilan `buscar(..., 15, 0)`, `buscar(..., 15, 1)` y `buscar(..., 15, 2)`, cómo en cada nivel se compara `arreglo[indice]` con el valor (`4 != 15`, `8 != 15`, `15 == 15`), y cómo en el índice 2 se devuelve `2`, resultado que se propaga hacia arriba sin modificarse hasta la llamada original. También quiero un ejemplo corto de un caso en que no se encuentra, como `buscar([4, 8], 99)`, donde se apilan los índices 0, 1 y 2, y en el índice 2 (`indice == arreglo.length`) se devuelve `-1`.

Antes del código, quiero una explicación breve de la estrategia y de la complejidad temporal y espacial (tiempo O(n) en el peor caso, cuando el valor está al final o no está, y espacio O(n) por la pila de llamadas, ya que cada posición revisada deja una llamada apilada).
 */
import java.util.Arrays;

/**
 * Busca recursivamente la primera aparición de un valor en un arreglo de enteros.
 * Cada llamada inspecciona una posición y delega la búsqueda restante en la
 * siguiente llamada, sin utilizar ciclos. En el peor caso el tiempo es O(n)
 * y el espacio es O(n) debido a la pila de llamadas recursivas.
 */
public class ej10 {

	/**
	 * Inicia la búsqueda recursiva desde el índice cero.
	 *
	 * @param arreglo arreglo de enteros donde se buscará
	 * @param valor valor que se desea encontrar
	 * @return índice de la primera aparición o -1 si el valor no está
	 * @throws IllegalArgumentException si arreglo es null
	 */
	public static int buscar(int[] arreglo, int valor) {
		if (arreglo == null) {
			throw new IllegalArgumentException("El arreglo no puede ser null.");
		}

		return buscar(arreglo, valor, 0);
	}

	/**
	 * Busca el valor desde el índice indicado mediante llamadas recursivas.
	 *
	 * @param arreglo arreglo en el que se realiza la búsqueda
	 * @param valor valor que se desea encontrar
	 * @param indice posición actual que se examina
	 * @return índice de la primera aparición o -1 si ya se revisó todo el arreglo
	 */
	private static int buscar(int[] arreglo, int valor, int indice) {
		// Primer caso base: se revisó todo el arreglo sin encontrar el valor.
		// Esta condición se comprueba antes de acceder a arreglo[indice].
		if (indice == arreglo.length) {
			return -1;
		}

		// Segundo caso base: se encontró el valor y se devuelve su índice.
		if (arreglo[indice] == valor) {
			return indice;
		}

		// Caso recursivo: se continúa la búsqueda desde la posición siguiente.
		// Para buscar 15 en [4, 8, 15, 16], se apilan las llamadas con índices
		// 0 (4 != 15), 1 (8 != 15) y 2 (15 == 15). En el índice 2 se devuelve 2;
		// ese resultado retorna sin cambios por las llamadas de índices 1 y 0.
		// Si se busca 99 en [4, 8], se apilan los índices 0 y 1; el índice 2
		// coincide con arreglo.length, devuelve -1 y el resultado se propaga.
		return buscar(arreglo, valor, indice + 1);
	}

	/**
	 * Muestra el arreglo y compara el resultado obtenido con el esperado.
	 *
	 * @param arreglo arreglo en el que se probará la búsqueda
	 * @param valor valor buscado
	 * @param esperado índice esperado o -1
	 */
	private static void probarCaso(int[] arreglo, int valor, int esperado) {
		int obtenido = buscar(arreglo, valor);
		System.out.println("buscar(" + Arrays.toString(arreglo) + ", " + valor + ")"
				+ " | esperado: " + esperado
				+ " | obtenido: " + obtenido
				+ " | correcto: " + (esperado == obtenido));
	}

	/**
	 * Prueba arreglos vacíos, búsquedas exitosas, ausentes y valores repetidos.
	 *
	 * @param args argumentos de la línea de comandos
	 */
	public static void main(String[] args) {
		probarCaso(new int[]{}, 5, -1);
		probarCaso(new int[]{5}, 5, 0);
		probarCaso(new int[]{5}, 3, -1);
		probarCaso(new int[]{4, 8, 15, 16}, 4, 0);
		probarCaso(new int[]{4, 8, 15, 16}, 15, 2);
		probarCaso(new int[]{4, 8, 15, 16}, 16, 3);
		probarCaso(new int[]{4, 8, 15, 16}, 99, -1);
		probarCaso(new int[]{7, 3, 7, 7}, 7, 0);
		probarCaso(new int[]{-2, 0, 9}, -2, 0);

		System.out.println("buscar(null, 5) | esperado: IllegalArgumentException");
		try {
			int obtenido = buscar(null, 5);
			System.out.println("  obtenido: " + obtenido + " (se esperaba una excepción)");
		} catch (IllegalArgumentException excepcion) {
			System.out.println("  obtenido: " + excepcion.getClass().getSimpleName()
					+ " - " + excepcion.getMessage());
		}

		System.out.println("Complejidad temporal en el peor caso: O(n).");
		System.out.println("Complejidad espacial: O(n), por la pila de llamadas.");
	}
}
