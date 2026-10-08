/*Necesito una clase ColaEnteros en Java que implemente una cola de enteros (FIFO) con un arreglo int[] de tamaño fijo, sin usar Queue, LinkedList, ArrayList ni ArrayDeque. La capacidad se pasa por constructor y si es menor o igual a 0 lanza IllegalArgumentException.

Operaciones: enqueue, dequeue, front, isEmpty, isFull y size.

Uso dos índices. front es la posición del primer elemento de la cola, o sea el próximo que va a salir. rear es la posición del último elemento agregado. Al crear la cola, front vale 0, rear vale -1 y size vale 0. En enqueue, rear avanza una posición con rear = (rear + 1) % capacidad, guardo el valor en arreglo[rear] y aumento size. En dequeue, leo arreglo[front], avanzo front con front = (front + 1) % capacidad, disminuyo size y devuelvo el valor. front devuelve arreglo[front] sin modificar ningún índice. El módulo hace que el arreglo sea circular: cuando un índice llega al final vuelve a la posición 0, y así se reutilizan los lugares que quedaron libres al sacar elementos. Sin eso, la cola se llenaría aunque el arreglo estuviera casi vacío. Uso un contador size separado, porque con front y rear solos no puedo distinguir entre cola llena y cola vacía (en ambos casos los índices quedan en posiciones parecidas). isEmpty es size == 0 e isFull es size == capacidad.

Casos límite: enqueue con la cola llena y dequeue/front con la cola vacía lanzan una excepción con un mensaje claro ("Cola llena" o "Cola vacía"), no devuelven -1, porque -1 puede ser un dato válido. Tiene que funcionar bien cuando los índices dan la vuelta: llenar la cola, sacar algunos elementos, agregar otros y comprobar que se mantiene el orden FIFO.

Comentá el código  explicando para qué sirve cada índice, cómo cambia en cada operación y por qué se usa el módulo. Hacé un main que pruebe: encolar hasta llenar, un enqueue de más, sacar dos elementos, encolar dos más (para que rear dé la vuelta), sacar todo verificando el orden de salida, y un dequeue con la cola vacía. Que muestre front, rear y size después de cada paso. */
package ejercicios.guia4;

public class ej6 {
	public static void main(String[] args) {
		ColaEnteros cola = new ColaEnteros(5);
		System.out.println("Cola circular FIFO de capacidad 5");
		mostrarEstado(cola, "Estado inicial");

		for (int valor = 10; valor <= 50; valor += 10) {
			cola.enqueue(valor);
			mostrarEstado(cola, "enqueue(" + valor + ")");
		}
		System.out.println("front() -> " + cola.front() + " (consulta sin retirar el elemento)");
		mostrarEstado(cola, "Después de front()");

		try {
			cola.enqueue(60);
		} catch (IllegalStateException exception) {
			System.out.println("Intento de enqueue adicional: " + exception.getMessage());
			mostrarEstado(cola, "Después del intento fallido");
		}

		for (int i = 0; i < 2; i++) {
			System.out.println("dequeue() -> " + cola.dequeue());
			mostrarEstado(cola, "Después de dequeue");
		}

		cola.enqueue(60);
		mostrarEstado(cola, "enqueue(60), rear vuelve al inicio del arreglo");
		cola.enqueue(70);
		mostrarEstado(cola, "enqueue(70), rear vuelve a avanzar circularmente");

		System.out.println("\nSalida restante en orden FIFO:");
		while (!cola.isEmpty()) {
			System.out.println("dequeue() -> " + cola.dequeue());
			mostrarEstado(cola, "Después de dequeue");
		}

		try {
			cola.dequeue();
		} catch (IllegalStateException exception) {
			System.out.println("Intento de dequeue con cola vacía: " + exception.getMessage());
			mostrarEstado(cola, "Después del intento fallido");
		}
	}

	private static void mostrarEstado(ColaEnteros cola, String operacion) {
		System.out.println(operacion + " | front=" + cola.getFrontIndex()
				+ ", rear=" + cola.getRearIndex() + ", size=" + cola.size());
	}
}

/** Cola FIFO circular de enteros, almacenada en un arreglo de capacidad fija. */
class ColaEnteros {
	private final int[] arreglo;
	private final int capacidad;
	// front indica el siguiente elemento que saldrá; rear indica el último agregado.
	private int front;
	private int rear;
	// size distingue la cola vacía de la llena aunque los índices coincidan.
	private int size;

	public ColaEnteros(int capacidad) {
		if (capacidad <= 0) {
			throw new IllegalArgumentException("La capacidad debe ser mayor que cero");
		}
		this.capacidad = capacidad;
		this.arreglo = new int[capacidad];
		this.front = 0;
		this.rear = -1;
		this.size = 0;
	}

	public void enqueue(int valor) {
		if (isFull()) {
			throw new IllegalStateException("Cola llena");
		}
		// El módulo hace que rear vuelva a cero al llegar al final y reutilice espacios libres.
		rear = (rear + 1) % capacidad;
		arreglo[rear] = valor;
		size++;
	}

	public int dequeue() {
		if (isEmpty()) {
			throw new IllegalStateException("Cola vacía");
		}
		int valor = arreglo[front];
		// front avanza circularmente para señalar el próximo elemento que se retirará.
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

	public int getFrontIndex() {
		return front;
	}

	public int getRearIndex() {
		return rear;
	}
}
