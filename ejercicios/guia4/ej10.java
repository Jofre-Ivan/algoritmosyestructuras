/*Necesito una clase ColaCircular en Java que implemente una cola de enteros con un arreglo int[] de tamaño fijo, sin usar Queue, LinkedList, ArrayList ni ArrayDeque. La capacidad se pasa por constructor y si es menor o igual a 0 lanza IllegalArgumentException. Tiene que reutilizar las posiciones liberadas al inicio del arreglo, que en una cola simple se desperdician.

Operaciones: enqueue, dequeue, front, isEmpty, isFull y size.

Uso dos índices. front es la posición del primer elemento (el próximo en salir) y rear es la posición del último elemento agregado. Al crear la cola, front vale 0, rear vale -1 y un contador size vale 0. Para mover los índices uso el operador módulo con la capacidad. En enqueue, rear = (rear + 1) % capacidad, guardo el valor en arreglo[rear] y aumento size. En dequeue, leo arreglo[front], hago front = (front + 1) % capacidad, disminuyo size y devuelvo el valor. El módulo hace que cuando un índice llega a la última posición (capacidad - 1), al avanzar vuelva a 0 en lugar de salirse del arreglo, y así el arreglo se comporta como un anillo y se reutilizan los lugares de los elementos ya sacados. front devuelve arreglo[front] sin modificar ningún índice.

Para detectar el estado uso el contador size, porque con front y rear solos no se puede distinguir cola llena de cola vacía (en ambos casos los índices quedan en posiciones contiguas). isEmpty es size == 0 e isFull es size == capacidad. Quiero que el comentario explique también la otra alternativa (dejar siempre una posición libre y comparar índices) y por qué elegí el contador, porque aprovecha todas las posiciones del arreglo.

Casos límite: enqueue con la cola llena y dequeue/front con la cola vacía lanzan una excepción con un mensaje claro ("Cola llena" o "Cola vacía"), no devuelven -1 porque puede ser un dato válido. Tiene que funcionar bien cuando los índices dan la vuelta, cuando la cola se llena y se vacía varias veces seguidas, y con capacidad 1.

Después de cada operación quiero que el programa muestre el arreglo posición por posición con los índices, marcando las posiciones libres y los valores de front, rear y size, por ejemplo: "[ 60 | 70 | 30 | 40 | 50 ]  front=2  rear=1  size=5".

Comentá el código  explicando para qué sirve cada índice, cómo se mueve con el módulo y cómo se detecta cola llena o vacía. Hacé un main con una demostración fija con capacidad 5: encolar 10, 20, 30, 40 y 50 hasta llenar, intentar un enqueue de más, desencolar tres veces, encolar 60 y 70 (que ahora sí entran, en las posiciones 0 y 1 que estaban libres, y rear da la vuelta), verificar que la cola vuelve a estar llena con solo 5 elementos, desencolar todo comprobando que el orden de salida es 30, 40, 50, 60, 70, y probar un dequeue con la cola vacía. Que la conclusión final compare con la cola simple, que habría dicho "llena" con 2 elementos. */
package ejercicios.guia4;

/**
 * Demuestra una cola circular: front señala el próximo valor que sale y rear
 * el último que entró. El módulo permite volver al inicio y reutilizar espacios.
 * Se usa size para distinguir lleno y vacío y aprovechar todas las posiciones;
 * la otra alternativa es dejar siempre una celda libre y comparar índices, lo
 * que reduce en uno la capacidad utilizable. Aquí isEmpty es size == 0 e isFull
 * es size == capacidad. Ambas condiciones se basan en el contador, no solo índices.
 */
public class ej10 {
	public static void main(String[] args) {
		ColaCircular cola = new ColaCircular(5);
		System.out.println("=== Cola circular: capacidad 5 ===");
		mostrarEstado(cola, "Estado inicial");

		for (int valor = 10; valor <= 50; valor += 10) {
			cola.enqueue(valor);
			mostrarEstado(cola, "enqueue(" + valor + ")");
		}

		System.out.println("front() -> " + cola.front());
		mostrarEstado(cola, "Después de consultar front()");

		try {
			cola.enqueue(60);
		} catch (IllegalStateException exception) {
			System.out.println("Intento de enqueue adicional: " + exception.getMessage());
			mostrarEstado(cola, "Después del intento fallido");
		}

		for (int i = 0; i < 3; i++) {
			System.out.println("dequeue() -> " + cola.dequeue());
			mostrarEstado(cola, "Después de dequeue");
		}

		cola.enqueue(60);
		mostrarEstado(cola, "enqueue(60): rear vuelve a la posición 0");
		cola.enqueue(70);
		mostrarEstado(cola, "enqueue(70): rear avanza a la posición 1 y la cola vuelve a llenarse");
		System.out.println("¿Cola llena con cinco elementos?: " + cola.isFull());

		int[] salidaEsperada = {30, 40, 50, 60, 70};
		boolean ordenCorrecto = true;
		System.out.println("\nVaciando la cola en orden FIFO:");
		for (int valorEsperado : salidaEsperada) {
			int valor = cola.dequeue();
			System.out.println("dequeue() -> " + valor);
			if (valor != valorEsperado) {
				ordenCorrecto = false;
			}
			mostrarEstado(cola, "Después de dequeue");
		}
		System.out.println("Verificación del orden de salida: "
				+ (ordenCorrecto ? "CORRECTA" : "INCORRECTA"));

		try {
			cola.dequeue();
		} catch (IllegalStateException exception) {
			System.out.println("Intento de dequeue con cola vacía: " + exception.getMessage());
			mostrarEstado(cola, "Después del intento fallido");
		}

		System.out.println("\nConclusión: después de quitar tres elementos quedaron dos en la cola. "
				+ "La cola simple habría dicho que estaba llena porque rear llegó al final; "
				+ "la cola circular reutilizó las posiciones 0 y 1, volvió a ocupar las cinco "
				+ "posiciones y mantuvo el orden FIFO.");
	}

	private static void mostrarEstado(ColaCircular cola, String operacion) {
		System.out.print(operacion + "\nArreglo: [");
		for (int i = 0; i < cola.capacidad(); i++) {
			if (i > 0) {
				System.out.print(" | ");
			}
			System.out.print(i + ": " + (cola.contieneIndice(i) ? cola.valorEn(i) : "libre"));
		}
		System.out.println("]  front=" + cola.getFront() + "  rear=" + cola.getRear()
				+ "  size=" + cola.size());
	}
}

/** Cola FIFO de enteros implementada con un arreglo circular de tamaño fijo. */
class ColaCircular {
	private final int[] arreglo;
	private final int capacidad;
	// front es el siguiente índice que se atenderá; rear es el último índice ocupado.
	private int front = 0;
	private int rear = -1;
	// size elimina la ambigüedad entre lleno y vacío y permite usar toda la capacidad.
	private int size = 0;

	public ColaCircular(int capacidad) {
		if (capacidad <= 0) {
			throw new IllegalArgumentException("La capacidad debe ser mayor que cero");
		}
		this.capacidad = capacidad;
		this.arreglo = new int[capacidad];
	}

	public void enqueue(int valor) {
		if (isFull()) {
			throw new IllegalStateException("Cola llena");
		}
		// El módulo hace que rear vuelva a 0 cuando alcanza la última posición.
		rear = (rear + 1) % capacidad;
		arreglo[rear] = valor;
		size++;
	}

	public int dequeue() {
		if (isEmpty()) {
			throw new IllegalStateException("Cola vacía");
		}
		int valor = arreglo[front];
		// front también avanza circularmente; el espacio retirado puede reutilizarse.
		front = (front + 1) % capacidad;
		size--;
		return valor;
	}

	public int front() {
		if (isEmpty()) {
			throw new IllegalStateException("Cola vacía");
		}
		return arreglo[front];
	}

	public boolean isEmpty() {
		return size == 0;
	}

	public boolean isFull() {
		return size == capacidad;
	}

	public int size() {
		return size;
	}

	int capacidad() {
		return capacidad;
	}

	int getFront() {
		return front;
	}

	int getRear() {
		return rear;
	}

	boolean contieneIndice(int indice) {
		for (int i = 0; i < size; i++) {
			if ((front + i) % capacidad == indice) {
				return true;
			}
		}
		return false;
	}

	int valorEn(int indice) {
		return arreglo[indice];
	}
}
