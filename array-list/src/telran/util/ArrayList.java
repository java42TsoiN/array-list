package telran.util;

import java.util.Arrays;
import java.util.function.Predicate;

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
		boolean res = false;
		if (index == size) {
			add(element);
			res = true;
			
		} else if(isValidIndex(index)) {
			res = true;
			if (size == array.length) {
				allocate();
			}
			System.arraycopy(array, index, array, index + 1, size - index);
			array[index] = element;
			size++;
		}
		return res;
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
		return tmp;
	}
	@Override
	public boolean contains(T pattern) {
		boolean res = false;
		for(int i = 0; i < size; i++) {
			if (array[i].equals(pattern)) {
				res = true;
				break;
			}
		}
		return res;
	}
	@Override
	public int indexOf(T pattern) {
		int res = -1;
		for(int i = 0; i < size; i++) {
			if (array[i].equals(pattern)) {
				res = i;
				break;
			}
		}
		return res;
	}
	// V.R. The method name is lastIndexOf
	@Override
//	public int LastIndexOf(T pattern) {
	public int lastIndexOf(T pattern) {
		int res = -1;
		for(int i =size-1; i >=0; i--) {
			if (array[i].equals(pattern)) {
				res = i;
				break;
			}
			
		}
		
		return res;
	}
	@Override
	public boolean contains(Predicate<T> predicate) {
		boolean res = false;
		for(int i = 0; i < size; i++) {
			if (predicate.test(array[i])) {
				res = true;
				break;
			}
		}
		return res;
	}
	@Override
	public int indexOf(Predicate<T> predicate) {
		int res = -1;
		for(int i = 0; i < size; i++) {
			if (predicate.test(array[i])) {
				res = i;
				
				break;
			}
		}
		return res;
	}
	// V.R. The method name is lastIndexOf
	@Override
	public int lastIndexOf(Predicate<T> predicate) {
//	public int LastIndexOf(Predicate<T> predicate) {
		int res = -1;
		for(int i = size-1; i >= 0; i--) {
			if (predicate.test(array[i])) {
				res = i;
				break;
			}
		}
		return res;
	}
	@Override
	public boolean removeIf(Predicate<T> predicate) {
		boolean res = false;
		for(int i = size-1; i >=0; i--) {
			if (predicate.test(array[i])) {
				res = true;
				remove(i);
			}
		}
		return res;
	}
	/* V.R. Compare the following implementation with yours.
	   The is the single difference between these implementations. Look at line 154.
	   Each iteration causes execution res=true. 
	   It isn't big deal, but a liitle bit significant.
	   
		public boolean removeIf(Predicate<T> predicate) {
			int prevSize = size;
			for (int index = size - 1; index >= 0; index--) {
				if (predicate.test(array[index])) {
					remove(index);
				}
			}
			
			return prevSize > size;
		}
	 */

	
}