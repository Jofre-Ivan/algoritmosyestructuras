package ejercicios.guia2;
/*Quiero implementar en Java una función recursiva que determine si una palabra es un palíndromo.

Este problema puede resolverse recursivamente porque una palabra es un palíndromo si su primer y su último carácter son iguales y, además, lo que queda en el medio también es un palíndromo. Por ejemplo, `"neuquen"` es palíndromo porque `'n' == 'n'` y `"euque"` también lo es, que a su vez cumple `'e' == 'e'` y `"uqu"` es palíndromo, y así sucesivamente. Es decir, `esPalindromo(s) = (s.charAt(0) == s.charAt(s.length() - 1)) && esPalindromo(s.substring(1, s.length() - 1))`.

Hay que comparar el primer y el último carácter porque son los que ocupan posiciones simétricas: en un palíndromo, la palabra se lee igual de izquierda a derecha que de derecha a izquierda, así que el primero tiene que coincidir con el último, el segundo con el penúltimo, y así sucesivamente.

Si el primer y el último carácter son distintos, la palabra no es un palíndromo y la función devuelve `false` de inmediato, sin necesidad de seguir revisando el resto, porque basta una sola posición simétrica que no coincida para descartarlo.

El caso base es una cadena vacía (`""`) o de un solo carácter (`s.length() <= 1`), porque no quedan pares de caracteres por comparar y, por lo tanto, no hay nada que pueda contradecir la simetría. En ese caso la función devuelve `true` sin hacer otra llamada recursiva. Además, es la condición a la que siempre se llega al eliminar dos caracteres (uno de cada extremo) por llamada, por lo que la recursión termina.

El caso recursivo es comparar los extremos y, si coinciden, llamar a `esPalindromo(s.substring(1, s.length() - 1))`, porque en cada llamada la cadena se reduce en dos caracteres (se eliminan el primero y el último), acercando el problema al caso base. Cada llamada devuelve:
- `true` cuando `s.length() <= 1` (caso base);
- `false` cuando el primer y el último carácter son distintos;
- el resultado de `esPalindromo(s.substring(1, s.length() - 1))` cuando los extremos coinciden (caso recursivo).

La función debe devolver un `boolean`. Debe validar la entrada: si `s` es `null`, debe lanzar una `IllegalArgumentException` con un mensaje claro. La comparación distingue mayúsculas de minúsculas y no ignora espacios (por ejemplo, `"Ana"` no es palíndromo porque `'A' != 'a'`); quiero que esta decisión quede aclarada en un comentario. No debe usar `StringBuilder.reverse()`, ni bucles, ni comparar contra la cadena invertida.

Quiero que el código tenga comentarios explicando el caso base, el caso recursivo y la pila de llamadas. En particular, quiero que incluya un ejemplo de la pila de llamadas para `esPalindromo("neuquen")`, mostrando cómo se apilan `esPalindromo("neuquen")`, `esPalindromo("euque")`, `esPalindromo("uqu")` y `esPalindromo("q")`, cómo en cada nivel se comparan los extremos (`'n' == 'n'`, `'e' == 'e'`, `'u' == 'u'`), y cómo luego se resuelven al desapilar: `esPalindromo("q")` devuelve `true`, y esa respuesta se propaga hacia arriba hasta que `esPalindromo("neuquen")` devuelve `true`. También quiero un ejemplo corto de un caso que termina antes, como `esPalindromo("hola")`, donde `'h' != 'a'` y devuelve `false` en la primera llamada.

Antes del código, quiero una explicación breve de la estrategia y de la complejidad temporal y espacial (incluyendo el costo de la pila de llamadas, que es proporcional a la mitad de la longitud de la cadena, y el costo de `substring` en cada llamada, que hace que el total sea O(n²) en tiempo en el peor caso).

 * Determina recursivamente si una cadena es palíndroma comparando sus extremos
 * y continuando con la subcadena interior. En el peor caso el tiempo es O(n^2)
 * por el costo acumulado de substring; la pila usa O(n) espacio, equivalente
 * a O(n / 2) niveles recursivos.
 */
public class ej9 {

	/**
	 * Comprueba si la cadena se lee igual de izquierda a derecha y de derecha a izquierda.
	 * La comparación distingue mayúsculas de minúsculas y considera los espacios
	 * como caracteres normales; no normaliza ni modifica la cadena.
	 *
	 * @param s cadena que se desea comprobar
	 * @return true si s es palíndroma; false en caso contrario
	 * @throws IllegalArgumentException si s es null
	 */
	public static boolean esPalindromo(String s) {
		if (s == null) {
			throw new IllegalArgumentException("La cadena no puede ser null.");
		}

		// Caso base: si queda cero o un carácter, no hay pares que contradigan
		// la simetría, por lo que la parte restante es palíndroma.
		if (s.length() <= 1) {
			return true;
		}

		// Si los extremos no coinciden, basta esta diferencia para responder false
		// inmediatamente, sin revisar el contenido restante.
		if (s.charAt(0) != s.charAt(s.length() - 1)) {
			return false;
		}

		// Caso recursivo: al coincidir los extremos, se comprueba la parte interior.
		// Para esPalindromo("neuquen"), la pila contiene:
		// esPalindromo("neuquen"): 'n' == 'n'
		// esPalindromo("euque"):   'e' == 'e'
		// esPalindromo("uqu"):     'u' == 'u'
		// esPalindromo("q"): caso base, devuelve true.
		// Al desapilar, true se propaga por "uqu", "euque" y "neuquen".
		// Por ejemplo, esPalindromo("hola") compara 'h' con 'a' y devuelve
		// false en la primera llamada, sin crear una llamada recursiva.
		return esPalindromo(s.substring(1, s.length() - 1));
	}

	/**
	 * Ejecuta una prueba y muestra el resultado esperado y el obtenido.
	 *
	 * @param entrada cadena que se comprobará
	 * @param esperado resultado esperado
	 */
	private static void probarCaso(String entrada, boolean esperado) {
		boolean obtenido = esPalindromo(entrada);
		System.out.println("esPalindromo(\"" + entrada + "\")"
				+ " | esperado: " + esperado
				+ " | obtenido: " + obtenido
				+ " | correcto: " + (esperado == obtenido));
	}

	/**
	 * Prueba cadenas vacías, palíndromos, no palíndromos y sensibilidad a mayúsculas.
	 *
	 * @param args argumentos de la línea de comandos
	 */
	public static void main(String[] args) {
		probarCaso("", true);
		probarCaso("a", true);
		probarCaso("aa", true);
		probarCaso("ab", false);
		probarCaso("neuquen", true);
		probarCaso("reconocer", true);
		probarCaso("abba", true);
		probarCaso("hola", false);
		probarCaso("abca", false);
		probarCaso("Ana", false);

		System.out.println("esPalindromo(null) | esperado: IllegalArgumentException");
		try {
			boolean obtenido = esPalindromo(null);
			System.out.println("  obtenido: " + obtenido + " (se esperaba una excepción)");
		} catch (IllegalArgumentException excepcion) {
			System.out.println("  obtenido: " + excepcion.getClass().getSimpleName()
					+ " - " + excepcion.getMessage());
		}

		System.out.println("Complejidad temporal en el peor caso: O(n^2), por substring.");
		System.out.println("Complejidad espacial: O(n), por la pila (hasta n / 2 llamadas).");
	}
}
