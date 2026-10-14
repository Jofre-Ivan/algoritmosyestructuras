package ejercicios.guia1;
import java.util.ArrayList;


/**
 * @author Ivan Jofre
 * @version 1.0
 * Este programa implementa una búsqueda lineal sobre un ArrayList de enteros.
 * Recorre la lista secuencialmente, compara cada elemento con el valor buscado
 * y devuelve la posición si existe, o informa que no se encontró y cuántas
 * posiciones se recorrieron.
 */

/**
 * Algoritmo utilizado: búsqueda lineal.
 *
 * <p>Se recorre el ArrayList desde la primera posición hasta la última,
 * comparando cada elemento con el valor buscado. Esta estrategia es la más
 * adecuada cuando la lista está desordenada, porque no es posible aplicar una
 * búsqueda binaria sin ordenar los datos primero. Ordenar la lista tendría una
 * complejidad mayor, O(n log n), mientras que la búsqueda lineal mantiene una
 * complejidad temporal O(n) y espacial O(1).</p>
 *
 * <p>Análisis de complejidad:</p>
 * <ul>
 *   <li>Mejor caso: el elemento se encuentra en la primera posición, O(1).</li>
 *   <li>Peor caso: el elemento no existe o está en la última posición, O(n).</li>
 *   <li>Caso promedio: se revisan aproximadamente la mitad de los elementos, O(n).</li>
 *   <li>Espacio: O(1), porque solo se usa una variable de control.</li>
 * </ul>
 */
public class ej2 {

    /**
     * Busca un valor dentro de un ArrayList de enteros usando búsqueda lineal.
     *
     * @param lista ArrayList de enteros a recorrer
     * @param elemento valor que se desea buscar
     * @return un mensaje descriptivo con la posición encontrada y la cantidad de
     *         posiciones recorridas
     * @throws IllegalArgumentException si la lista está vacía
     */
    public static String buscarElemento(ArrayList<Integer> lista, int elemento) {
        if (lista == null || lista.isEmpty()) {
            throw new IllegalArgumentException("La lista está vacía. No se puede buscar ningún elemento.");
        }

        int posicionesRecorridas = 0;

        for (int i = 0; i < lista.size(); i++) {
            posicionesRecorridas++;
            if (lista.get(i) == elemento) {
                return "Elemento encontrado: " + elemento
                        + " en la posición " + i
                        + ". Posiciones recorridas: " + posicionesRecorridas;
            }
        }

        return "Elemento no encontrado: " + elemento
                + ". Posiciones recorridas: " + posicionesRecorridas;
    }

    /**
     * Método principal con casos de prueba para verificar la búsqueda lineal.
     *
     * @param args argumentos de la línea de comandos
     */
    public static void main(String[] args) {
        ArrayList<Integer> caso1 = new ArrayList<>();
        caso1.add(5);
        caso1.add(8);
        caso1.add(2);
        caso1.add(10);
        caso1.add(1);

        ArrayList<Integer> caso2 = new ArrayList<>();
        caso2.add(12);
        caso2.add(-3);
        caso2.add(7);
        caso2.add(-9);
        caso2.add(4);

        ArrayList<Integer> caso3 = new ArrayList<>();
        caso3.add(7);

        ArrayList<Integer> caso4 = new ArrayList<>();
        caso4.add(4);
        caso4.add(6);
        caso4.add(8);
        caso4.add(10);

        ArrayList<Integer> caso5 = new ArrayList<>();

        System.out.println(buscarElemento(caso1, 1));
        System.out.println(buscarElemento(caso2, -9));
        System.out.println(buscarElemento(caso3, 7));
        System.out.println(buscarElemento(caso4, 3));

        try {
            System.out.println(buscarElemento(caso5, 10));
        } catch (IllegalArgumentException e) {
            System.out.println(e.getMessage());
        }
    }
}
