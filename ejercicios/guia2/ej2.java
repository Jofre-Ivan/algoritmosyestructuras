/*p promp : implementar en java una funcion recursiva que resuelva la suma de 
los primeros N numeros, desde N hasta 1, el caso mas simple es cuando N vale 1(sum(1)=1), el caso recursivo  
suma(n) debe pensarse como n + suma(n-1) porque va sumando y llamandose a si misma hasta el caso base que seria cuando n sea 0, en ese caso la funcion recursiva deberia terminar y ejecutar la pila de llamadas, 
para validar que funcione vamos a hacer una prueba para los primeros 100 numeros, la funcion deberia devolver 5050 */
package ejercicios.guia2;

public class ej2 {

	/**
	 * Calcula recursivamente la suma de los enteros desde n hasta 1.
	 *
	 * @param n límite superior de la suma; debe ser no negativo
	 * @return suma de los enteros desde n hasta 1
	 * @throws IllegalArgumentException si n es negativo
	 */
	public static int suma(int n) {
		if (n < 0) {
			throw new IllegalArgumentException("N debe ser un entero no negativo.");
		}

		// Caso base: suma(0) vale 0. Detiene la recursión; desde aquí
		// las llamadas que quedaron pendientes comienzan a resolverse.
		if (n == 0) {
			return 0;
		}

		// Caso recursivo: suma el valor actual al resultado de suma(n - 1).
		// La pila acumula suma(n), suma(n - 1), ..., suma(1) hasta llegar
		// a suma(0). Al regresar, se resuelven en orden inverso:
		// suma(1) = 1 + 0, suma(2) = 2 + suma(1), y así sucesivamente.
		return n + suma(n - 1);
	}

	/**
	 * Prueba la suma para los primeros cien números naturales.
	 *
	 * @param args argumentos de la línea de comandos
	 */
	public static void main(String[] args) {
		int n = 100;
		int resultado = suma(n);

		System.out.println("Suma desde 1 hasta " + n + ": " + resultado);
		System.out.println("Resultado esperado: 5050");
		System.out.println("Complejidad temporal: O(n).");
		System.out.println("Complejidad espacial: O(n), por la pila de llamadas recursivas.");
	}
}
