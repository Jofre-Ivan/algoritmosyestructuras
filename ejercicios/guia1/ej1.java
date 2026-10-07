/*necesito implementar un algoritmo para buscar el menor de un vector, necesito que recorras todo el vector y ir comparando cada vector con el anterior y bueno si hay uno menor que ese nuevo 
menor se guarde en una variable por ejemplo con el nombre vectorMenor.
 usando un for de orden 1 ya que son pocos los datos. 
 necesito que este algortimos este documentado con java docs. 
 decime si es lo mas eficiente o no y explicame porque es eficaz */

package ejercicios.guia1;

/**
 * Clase que resuelve el problema de encontrar el valor mínimo de un vector
 * de números enteros mediante un recorrido secuencial.
 *
 * <p>La estrategia consiste en recorrer el arreglo una sola vez y mantener
 * una variable que almacena el menor valor encontrado hasta el momento.
 * Esta solución es eficiente porque no requiere ordenar el vector ni crear
 * estructuras auxiliares.</p>
 */
public class ej1 {

    /**
     * Busca el menor valor contenido en un vector de enteros.
     *
     * <p>Se recorre el arreglo una sola vez y se compara cada elemento con el
     * menor valor encontrado hasta ese momento. Si aparece un valor más pequeño,
     * se actualiza la variable de seguimiento. La complejidad temporal es O(n)
     * y la complejidad espacial es O(1).</p>
     *
     * @param vector arreglo de enteros sobre el cual se desea buscar el mínimo
     * @return el menor valor presente en el vector
     * @throws IllegalArgumentException si el vector está vacío
     */
    public static int minimoVector(int[] vector) {
        if (vector == null || vector.length == 0) {
            throw new IllegalArgumentException("El vector está vacío. No existe un valor mínimo.");
        }

        int minimo = vector[0];

        for (int i = 1; i < vector.length; i++) {
            if (vector[i] < minimo) {
                minimo = vector[i];
            }
        }

        return minimo;
    }

    /**
     * Método principal de prueba para verificar el funcionamiento del algoritmo.
     *
     * @param args argumentos de la línea de comandos
     */
    public static void main(String[] args) {
        int[] caso1 = {5, 8, 2, 10, 1};
        int[] caso2 = {12, -3, 7, -9, 4};
        int[] caso3 = {7};

        System.out.println("Caso 1: " + minimoVector(caso1));
        System.out.println("Caso 2: " + minimoVector(caso2));
        System.out.println("Caso 3: " + minimoVector(caso3));

        try {
            int[] vectorVacio = {};
            System.out.println("Caso 4: " + minimoVector(vectorVacio));
        } catch (IllegalArgumentException e) {
            System.out.println(e.getMessage());
        }
    }
}
