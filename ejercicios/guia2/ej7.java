package ejercicios.guia2;
/*Quiero implementar en Java una función recursiva que calcule la suma de los dígitos de un número entero positivo.

Este problema puede resolverse recursivamente porque la suma de los dígitos de un número es su último dígito más la suma de los dígitos del número que queda al quitarlo. El último dígito se obtiene con el módulo (`n % 10`) y el número sin ese dígito se obtiene con la división entera (`n / 10`). Por ejemplo, para `1234`: `1234 % 10 = 4` y `1234 / 10 = 123`, así que `sumaDigitos(1234) = 4 + sumaDigitos(123)`. Es decir, `sumaDigitos(n) = (n % 10) + sumaDigitos(n / 10)`.

El caso base es `n < 10`, porque un número menor que 10 tiene un solo dígito y la suma de sus dígitos es el número mismo. En ese caso la función devuelve `n` sin hacer otra llamada recursiva. Además, es la condición a la que siempre se llega al dividir por 10 repetidamente, por lo que la recursión termina.

El caso recursivo es `(n % 10) + sumaDigitos(n / 10)`, porque en cada llamada el número se reduce a un décimo de su valor (se descarta el último dígito) y ese dígito descartado se guarda para sumarlo al resultado de la llamada más chica, acercando el problema al caso base.

La función debe devolver un `int` con la suma de los dígitos. Los resultados se combinan al desapilar: cada llamada espera el resultado de la siguiente y le suma el dígito que ella extrajo. Cada llamada devuelve:
- `n` cuando `n < 10` (caso base);
- `(n % 10) + sumaDigitos(n / 10)` cuando `n >= 10` (caso recursivo).

La función debe validar la entrada: si `n` es negativo, debe lanzar una `IllegalArgumentException` con un mensaje claro, porque el enunciado trabaja con enteros positivos. El valor `0` se acepta y devuelve 0. No debe usar conversión a `String`, ni bucles.

Quiero que el código tenga comentarios explicando el caso base, el caso recursivo y la pila de llamadas. En particular, quiero que incluya un ejemplo de la pila de llamadas para `sumaDigitos(1234)`, mostrando cómo se apilan `4 + sumaDigitos(123)`, `3 + sumaDigitos(12)`, `2 + sumaDigitos(1)`, y cómo luego se resuelven al desapilar: `sumaDigitos(1)` devuelve `1`, luego `2 + 1 = 3`, luego `3 + 3 = 6` y finalmente `4 + 6 = 10`.

Antes del código, quiero una explicación breve de la estrategia y de la complejidad temporal y espacial (incluyendo el costo de la pila de llamadas, que es proporcional a la cantidad de dígitos).

También quiero probarlo con estos casos, mostrando en un `main` el resultado esperado y el obtenido:
- `sumaDigitos(7)` → 7 (un solo dígito, caso base)
- `sumaDigitos(0)` → 0 (cero)
- `sumaDigitos(9)` → 9 (límite superior del caso base)
- `sumaDigitos(10)` → 1 (límite inferior del caso recursivo)
- `sumaDigitos(99)` → 18 (dígitos repetidos)
- `sumaDigitos(1234)` → 10 (caso general)
- `sumaDigitos(100000)` → 1 (con ceros)
- `sumaDigitos(2147483647)` → 46 (máximo `int`)
- `sumaDigitos(-5)` → debe lanzar `IllegalArgumentException` (valor negativo) */
/**
 * Suma recursivamente los dígitos de un entero no negativo.
 * En cada llamada extrae el último dígito con el módulo 10 y procesa
 * el resto mediante división entera por 10. Para d dígitos, el tiempo
 * es O(d) y la pila recursiva ocupa O(d) espacio.
 */
public class ej7 {

	/**
	 * Calcula la suma de los dígitos de n sin usar bucles ni convertirlo a texto.
	 *
	 * @param n entero no negativo cuyos dígitos se sumarán
	 * @return suma de los dígitos; devuelve 0 para n igual a 0
	 * @throws IllegalArgumentException si n es negativo
	 */
	public static int sumaDigitos(int n) {
		if (n < 0) {
			throw new IllegalArgumentException("El número debe ser un entero no negativo.");
		}

		// Caso base: un número menor que 10 tiene un único dígito,
		// por lo que la suma de sus dígitos es el propio número.
		if (n < 10) {
			return n;
		}

		// Caso recursivo: n % 10 extrae el último dígito y n / 10
		// elimina ese dígito para reducir el problema.
		// Para sumaDigitos(1234), la pila acumula:
		// 4 + sumaDigitos(123)
		// 3 + sumaDigitos(12)
		// 2 + sumaDigitos(1)
		// sumaDigitos(1) devuelve 1; al desapilar se calcula
		// 2 + 1 = 3, luego 3 + 3 = 6 y finalmente 4 + 6 = 10.
		return (n % 10) + sumaDigitos(n / 10);
	}

	/**
	 * Muestra el valor esperado y el obtenido para un caso de prueba.
	 *
	 * @param n número cuyos dígitos se suman
	 * @param esperado suma esperada
	 */
	private static void probarCaso(int n, int esperado) {
		int obtenido = sumaDigitos(n);
		System.out.println("sumaDigitos(" + n + ")"
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
		probarCaso(7, 7);
		probarCaso(0, 0);
		probarCaso(9, 9);
		probarCaso(10, 1);
		probarCaso(99, 18);
		probarCaso(1234, 10);
		probarCaso(100000, 1);
		probarCaso(2147483647, 46);

		System.out.println("sumaDigitos(-5) | esperado: IllegalArgumentException");
		try {
			int obtenido = sumaDigitos(-5);
			System.out.println("  obtenido: " + obtenido + " (se esperaba una excepción)");
		} catch (IllegalArgumentException excepcion) {
			System.out.println("  obtenido: " + excepcion.getClass().getSimpleName()
					+ " - " + excepcion.getMessage());
		}

		System.out.println("Complejidad temporal: O(d), donde d es la cantidad de dígitos.");
		System.out.println("Complejidad espacial: O(d), por la pila recursiva.");
	}
}
