package ejercicios.guia3;

import java.text.Collator;
import java.util.Arrays;
import java.util.Locale;

public class ej4 {
	public static void main(String[] args) {
		String[] nombres = {"Valentina", "Andrés", "María", "Álvaro", "Sofía", "Gabriel", "Ñusta"};
		Collator ordenEspanol = Collator.getInstance(Locale.forLanguageTag("es"));

		System.out.println("Nombres originales: " + Arrays.toString(nombres));
		selectionSort(nombres, ordenEspanol);
		System.out.println("Nombres ordenados alfabéticamente: " + Arrays.toString(nombres));
	}

	/** Ordena los nombres de menor a mayor alfabéticamente usando Selection Sort. */
	private static void selectionSort(String[] nombres, Collator ordenEspanol) {
		int comparaciones = 0;
		int intercambios = 0;

		for (int i = 0; i < nombres.length - 1; i++) {
			int indiceMenor = i;

			// Busca el nombre que debería ocupar la siguiente posición.
			for (int j = i + 1; j < nombres.length; j++) {
				comparaciones++;
				if (ordenEspanol.compare(nombres[j], nombres[indiceMenor]) < 0) {
					indiceMenor = j;
				}
			}

			if (indiceMenor != i) {
				String temporal = nombres[i];
				nombres[i] = nombres[indiceMenor];
				nombres[indiceMenor] = temporal;
				intercambios++;
			}

			System.out.println("Pasada " + (i + 1) + ": " + Arrays.toString(nombres));
		}

		System.out.println("Comparaciones: " + comparaciones);
		System.out.println("Intercambios: " + intercambios);
	}
}
