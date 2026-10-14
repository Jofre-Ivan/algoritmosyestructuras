package ejercicios.guia2;
/*Quiero implementar en Java una función recursiva que invierta un `String`.

Este problema puede resolverse recursivamente porque invertir una palabra equivale a invertir todo lo que viene después del primer carácter y colocar ese primer carácter al final. Por ejemplo, para `"hola"`: el primer carácter es `'h'`, el resto es `"ola"`, y `invertir("hola") = invertir("ola") + 'h'`. Es decir, `invertir(s) = invertir(s.substring(1)) + s.charAt(0)`.

El caso base es una cadena vacía (`""`) o de un solo carácter (`s.length() <= 1`), porque una cadena sin caracteres no tiene nada que invertir y una de un solo carácter es igual a su inversa. En ese caso la función devuelve `s` sin hacer otra llamada recursiva. Además, es la condición a la que siempre se llega al quitar un carácter por llamada, por lo que la recursión termina.

El primer carácter se separa del resto con `s.charAt(0)` y `s.substring(1)`. El caso recursivo es `invertir(s.substring(1)) + s.charAt(0)`, porque en cada llamada la cadena se reduce en un carácter (se descarta el primero), acercando el problema al caso base.

El resultado se reconstruye al volver de la recursión (al desapilar): cada llamada espera la inversa del resto y le concatena al final el primer carácter que ella separó. Cada llamada devuelve:
- `s` cuando `s.length() <= 1` (caso base);
- `invertir(s.substring(1)) + s.charAt(0)` cuando `s.length() > 1` (caso recursivo).

La función debe validar la entrada: si `s` es `null`, debe lanzar una `IllegalArgumentException` con un mensaje claro. No debe usar `StringBuilder.reverse()`, ni bucles, ni ninguna otra función de inversión de la librería estándar.

Quiero que el código tenga comentarios explicando el caso base, el caso recursivo y la pila de llamadas. En particular, quiero que incluya un ejemplo de la pila de llamadas para `invertir("hola")`, mostrando cómo se apilan `invertir("ola") + 'h'`, `invertir("la") + 'o'`, `invertir("a") + 'l'`, y cómo luego se resuelven al desapilar: `invertir("a")` devuelve `"a"`, luego `"a" + 'l' = "al"`, luego `"al" + 'o' = "alo"` y finalmente `"alo" + 'h' = "aloh"`.

Antes del código, quiero una explicación breve de la estrategia y de la complejidad temporal y espacial (incluyendo el costo de la pila de llamadas, que es proporcional a la longitud de la cadena, y el costo de `substring` y de la concatenación en cada llamada, que hace que el total sea O(n²) en tiempo).

También quiero probarlo con estos casos, mostrando en un `main` el resultado esperado y el obtenido:
- `invertir("")` → `""` (cadena vacía, caso base)
- `invertir("a")` → `"a"` (un solo carácter, caso base)
- `invertir("ab")` → `"ba"` (dos caracteres, caso mínimo recursivo)
- `invertir("hola")` → `"aloh"` (caso general)
- `invertir("recursion")` → `"noisrucer"` (palabra más larga)
- `invertir("neuquen")` → `"neuquen"` (palíndromo, igual a su inversa)
- `invertir("Java 21")` → `"12 avaJ"` (con espacios, mayúsculas y números)
- `invertir(null)` → debe lanzar `IllegalArgumentException` (entrada nula) */
/**
 * Invierte cadenas mediante recursión: invierte el resto de la cadena y coloca
 * el primer carácter al final. Para una cadena de longitud n, la pila ocupa
 * O(n) espacio. Como substring y la concatenación copian caracteres en cada
 * nivel, el tiempo total es O(n^2).
 */
public class ej8 {

	/**
	 * Devuelve la cadena invertida sin utilizar métodos de inversión ni bucles.
	 *
	 * @param s cadena que se desea invertir
	 * @return cadena con sus caracteres en orden inverso
	 * @throws IllegalArgumentException si s es null
	 */
	public static String invertir(String s) {
		if (s == null) {
			throw new IllegalArgumentException("La cadena no puede ser null.");
		}

		// Caso base: una cadena vacía o de un carácter ya es su propia inversa.
		if (s.length() <= 1) {
			return s;
		}

		// Caso recursivo: se invierte la subcadena que empieza en la posición 1
		// y se concatena al final el primer carácter de la cadena actual.
		// Para invertir("hola"), la pila acumula:
		// invertir("ola") + 'h'
		// invertir("la") + 'o'
		// invertir("a") + 'l'
		// Al desapilar: invertir("a") devuelve "a"; después "a" + 'l' = "al",
		// "al" + 'o' = "alo" y finalmente "alo" + 'h' = "aloh".
		return invertir(s.substring(1)) + s.charAt(0);
	}

	/**
	 * Ejecuta una prueba y muestra el valor esperado y el resultado obtenido.
	 *
	 * @param entrada cadena de entrada
	 * @param esperado cadena invertida esperada
	 */
	private static void probarCaso(String entrada, String esperado) {
		String obtenido = invertir(entrada);
		System.out.println("invertir(\"" + entrada + "\")"
				+ " | esperado: \"" + esperado + "\""
				+ " | obtenido: \"" + obtenido + "\""
				+ " | correcto: " + esperado.equals(obtenido));
	}

	/**
	 * Prueba cadenas vacías, cortas, largas, palíndromas y con espacios.
	 * También comprueba que una entrada null provoque IllegalArgumentException.
	 *
	 * @param args argumentos de la línea de comandos
	 */
	public static void main(String[] args) {
		probarCaso("", "");
		probarCaso("a", "a");
		probarCaso("ab", "ba");
		probarCaso("hola", "aloh");
		probarCaso("recursion", "noisrucer");
		probarCaso("neuquen", "neuquen");
		probarCaso("Java 21", "12 avaJ");

		System.out.println("invertir(null) | esperado: IllegalArgumentException");
		try {
			String obtenido = invertir(null);
			System.out.println("  obtenido: " + obtenido + " (se esperaba una excepción)");
		} catch (IllegalArgumentException excepcion) {
			System.out.println("  obtenido: " + excepcion.getClass().getSimpleName()
					+ " - " + excepcion.getMessage());
		}

		System.out.println("Complejidad temporal: O(n^2), por substring y concatenaciones en cada nivel.");
		System.out.println("Complejidad espacial: O(n), por la pila de llamadas recursivas.");
	}
}
