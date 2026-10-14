package ejercicios.arbolesbinarios;
/* @file:Nodo.java implementa en la clase nodo, 
que tenga de atributos el nodo izquierdo el derecho
 y el valor . y un metodo publico que sea el constructor, 
 luego en la clase @file:ArbolBinario.java con el nodo raiz, 
 su constructor y luego en la clase main @file:Main.java 
 implementa un armado manual con algunos valores y todo esto englobalo como 
 comentario como ejercicio 1.*/

public class Main {
    public static void main(String[] args) {
        ArbolBinario arbol = new ArbolBinario();

        // Construcción manual de un árbol binario de ejemplo:
        //             10
        //            /  \
        //           5    15
        //          / \     \
        //         3   7     20
        Nodo raiz = new Nodo(10);
        Nodo nodo5 = new Nodo(5);
        Nodo nodo15 = new Nodo(15);
        Nodo nodo3 = new Nodo(3);
        Nodo nodo7 = new Nodo(7);
        Nodo nodo20 = new Nodo(20);

        raiz.setIzquierdo(nodo5);
        raiz.setDerecho(nodo15);
        nodo5.setIzquierdo(nodo3);
        nodo5.setDerecho(nodo7);
        nodo15.setDerecho(nodo20);
        arbol.setRaiz(raiz);

        System.out.println("Árbol armado manualmente.");
        System.out.println("Valor de la raíz: " + arbol.getRaiz().getValor());
        System.out.println("Hijo izquierdo de la raíz: " + arbol.getRaiz().getIzquierdo().getValor());
        System.out.println("Hijo derecho de la raíz: " + arbol.getRaiz().getDerecho().getValor());
    }
}
