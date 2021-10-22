package telran.util;

import java.util.Arrays;

public class ArrayList<T> implements List<T> {
	private static final int DEFAULT_CAPACITY = 16;
	private T[] array;
	private int size = 0; 
	@SuppressWarnings("unchecked")
	public ArrayList(int capacity) {
		array = (T[]) new Object[capacity];
	}
	public ArrayList() {
		this(DEFAULT_CAPACITY);
	}
	@Override
	public void add(T element) {
		if (size == array.length) {
			//size is capacity
			allocate();
		}
		array[size++] = element;
		
		
	}

	private void allocate() {
		array = Arrays.copyOf(array, array.length * 2);
		
	}
	@Override
	public boolean add(int index, T element) {
		if(!isValidIndex(index)) {
			return false;
		}if(size==array.length){
			allocate();
		}
		System.arraycopy(array, index, array, index+1, size-index);
		array[index]=element;
		size++;
		return true;
	}

	@Override
	public int size() {
		return size;
	}

	@Override
	public T get(int index) {
		
		return isValidIndex(index) ? array[index] : null;
	}

	private boolean isValidIndex(int index) {
		
		return index >= 0 && index < size;
	}
	@Override
	public T remove(int index) {
		T tmp = array[index];
		if(!isValidIndex(index)) {
			return null;
		}
		System.arraycopy(array, index+1, array, index, size-index-1);
		size--;
		System.out.println(Arrays.toString(array));
		return tmp;
	}

}