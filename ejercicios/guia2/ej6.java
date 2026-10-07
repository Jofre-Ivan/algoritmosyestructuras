package ejercicios.guia2;
/*Quiero implementar en Java una función recursiva que cuente la cantidad de dígitos de un número entero positivo.

Este problema puede resolverse recursivamente porque la cantidad de dígitos de un número es 1 más la cantidad de dígitos del número que queda al quitarle el último dígito. Quitar el último dígito equivale a hacer una división entera por 10. Por ejemplo, `1234 / 10 = 123`, y `123` tiene un dígito menos que `1234`. Es decir, `contarDigitos(n) = 1 + contarDigitos(n / 10)`.

El caso base es `n < 10`, porque un número menor que 10 (del 0 al 9) se escribe con un solo dígito. En ese caso la función devuelve `1` sin hacer otra llamada recursiva. Además, es la condición a la que siempre se llega al dividir por 10 repetidamente, por lo que la recursión termina.

El caso recursivo es `1 + contarDigitos(n / 10)`, porque en cada llamada el número se reduce a un décimo de su valor (se descarta el último dígito) y se suma 1 por el dígito descartado, acercando el problema al caso base.

La función debe devolver un `int` con la cantidad de dígitos. Cada llamada devuelve:
- `1` cuando `n < 10` (caso base);
- `1 + contarDigitos(n / 10)` cuando `n >= 10` (caso recursivo), es decir, el resultado de la llamada más chica incrementado en 1.

La función debe validar la entrada: si `n` es negativo, debe lanzar una `IllegalArgumentException` con un mensaje claro, porque el enunciado trabaja con enteros positivos. El valor `0` se acepta y devuelve 1, porque se escribe con un solo dígito. No debe usar conversión a `String`, ni `Math.log10`, ni bucles.

Quiero que el código tenga comentarios explicando el caso base, el caso recursivo y la pila de llamadas. En particular, quiero que incluya un ejemplo de la pila de llamadas para `contarDigitos(1234)`, mostrando cómo se apilan `1 + contarDigitos(123)`, `1 + contarDigitos(12)`, `1 + contarDigitos(1)`, y cómo luego se resuelven al desapilar: `contarDigitos(1)` devuelve `1`, luego `2`, luego `3` y finalmente `4`.

Antes del código, quiero una explicación breve de la estrategia y de la complejidad temporal y espacial (incluyendo el costo de la pila de llamadas, que es proporcional a la cantidad de dígitos).

También quiero probarlo con estos casos, mostrando en un `main` el resultado esperado y el obtenido:
- `contarDigitos(7)` → 1 (un solo dígito, caso base)
- `contarDigitos(0)` → 1 (cero se escribe con un dígito)
- `contarDigitos(9)` → 1 (límite superior del caso base)
- `contarDigitos(10)` → 2 (límite inferior del caso recursivo)
- `contarDigitos(99)` → 2 (dos dígitos)
- `contarDigitos(1234)` → 4 (caso general)
- `contarDigitos(100000)` → 6 (con ceros)
- `contarDigitos(2147483647)` → 10 (máximo `int`)
- `contarDigitos(-5)` → debe lanzar `IllegalArgumentException` (valor negativo) */
/**
 * Cuenta recursivamente los dígitos de un entero no negativo.
 * La estrategia descarta el último dígito mediante división entera por 10
 * y suma uno por cada llamada. Si d es la cantidad de dígitos, la complejidad
 * temporal es O(d) y el espacio es O(d) debido a la pila recursiva.
 */
public class ej6 {

	/**
	 * Devuelve la cantidad de dígitos del número recibido.
	 *
	 * @param n entero no negativo cuyos dígitos se contarán
	 * @return cantidad de dígitos de n; para 0 devuelve 1
	 * @throws IllegalArgumentException si n es negativo
	 */
	public static int contarDigitos(int n) {
		if (n < 0) {
			throw new IllegalArgumentException("El número debe ser un entero no negativo.");
		}

		// Caso base: cualquier número entre 0 y 9 tiene exactamente un dígito.
		if (n < 10) {
			return 1;
		}

		// Caso recursivo: n / 10 descarta el último dígito y se suma 1
		// para contar ese dígito descartado.
		// Para contarDigitos(1234), la pila acumula:
		// 1 + contarDigitos(123)
		// 1 + contarDigitos(12)
		// 1 + contarDigitos(1)
		// contarDigitos(1) es el caso base y devuelve 1; al desapilar,
		// las llamadas devuelven 2, luego 3 y finalmente 4.
		return 1 + contarDigitos(n / 10);
	}

	/**
	 * Muestra el resultado esperado y el obtenido para un número no negativo.
	 *
	 * @param n número a probar
	 * @param esperado cantidad de dígitos esperada
	 */
	private static void probarCaso(int n, int esperado) {
		int obtenido = contarDigitos(n);
		System.out.println("contarDigitos(" + n + ")"
				+ " | esperado: " + esperado
				+ " | obtenido: " + obtenido
				+ " | correcto: " + (esperado == obtenido));
	}

	/**
	 * Ejecuta los casos límite y generales solicitados.
	 *
	 * @param args argumentos de la línea de comandos
	 */
	public static void main(String[] args) {
		probarCaso(7, 1);
		probarCaso(0, 1);
		probarCaso(9, 1);
		probarCaso(10, 2);
		probarCaso(99, 2);
		probarCaso(1234, 4);
		probarCaso(100000, 6);
		probarCaso(2147483647, 10);

		System.out.println("contarDigitos(-5) | esperado: IllegalArgumentException");
		try {
			int obtenido = contarDigitos(-5);
			System.out.println("  obtenido: " + obtenido + " (se esperaba una excepción)");
		} catch (IllegalArgumentException excepcion) {
			System.out.println("  obtenido: " + excepcion.getClass().getSimpleName()
					+ " - " + excepcion.getMessage());
		}

		System.out.println("Complejidad temporal: O(d), donde d es la cantidad de dígitos.");
		System.out.println("Complejidad espacial: O(d), por la pila recursiva.");
	}
}
