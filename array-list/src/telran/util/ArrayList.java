package telran.util;

import java.util.Arrays;
import java.util.Comparator;
import java.util.Iterator;
import java.util.NoSuchElementException;
import java.util.function.Predicate;


public class ArrayList<T> extends  AbstractList<T> {
	private static final int DEFAULT_CAPACITY = 16;
	private T[] array;
	private class ArrayListIterator implements Iterator<T>{
		int current = 0;
		boolean doNext =false;
		@Override
		public boolean hasNext() {
			return current < size;
		}
		
		@Override
		public T next() {
			if(!hasNext()) throw new NoSuchElementException();
			doNext=true;
			return array[current++];
				
		}
		
		@Override
		public void remove() {
			 if (doNext==false) {
				 throw  new IllegalStateException();
				
				} 
			 ArrayList.this.remove(--current);
			 doNext=false;
				
			
			
		}
	}
	@SuppressWarnings("unchecked")
	public ArrayList(int capacity) {
		array = (T[]) new Object[capacity];
	}
	public ArrayList() {
		this(DEFAULT_CAPACITY);
	}
	
	@Override
	public void add(T element) {
		//O[1]
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
		//O[N]
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
	public T get(int index) {
		//O[1]
		return isValidIndex(index) ? array[index] : null;
	}

	
	@Override
	public T remove(int index) {
		//O[N]
		T res = null;
		if (isValidIndex(index)) {
			res = array[index];
			size--;
			System.arraycopy(array, index + 1, array, index, size - index);
			//FIXME regarding setting null
		}
		
		return res;
	}
	
	
	
	
	@Override
	public int indexOf(Predicate<T> predicate) {
		//O[N]
				int res = -1;
				for (int i = 0; i < size; i++) {
					if (predicate.test(array[i])) {
						res = i;
						break;
					}
				}
				return res;
	}
	@Override
	public int lastIndexOf(Predicate<T> predicate) {
		//O[N]
				int res = -1;
				for (int i = size - 1; i >=0 ; i--) {
					if (predicate.test(array[i])) {
						res = i;
						break;
					}
				}
				return res;
	}
	@Override
	public boolean removeIf(Predicate<T> predicate) {
		//O[N^2]
		int oldSize = size;
		for (int i = size - 1; i >= 0; i--) {
			if (predicate.test(array[i])) {
				remove(i);
			}
		}
		return oldSize > size;
	}
	@Override
	public void sort(Comparator<T> comp) {
		//O[N*logN]
		Arrays.sort(array, 0, size,comp);
		
	}
	@Override
	public int sortedSearch(T pattern, Comparator comp) {
		// implied array is sorted in accordance with a given comparator
		int left = 0;
		int right = size - 1;
		int middle = 0;
		int res = -1;
		while (left <= right) {
			middle = (left + right) / 2;
			int resComp = comp.compare(pattern, array[middle]);
			if (resComp == 0) {
				res = middle;
				break;
			}
			if (resComp > 0) {
				left = middle + 1;
			} else {
				right = middle - 1;
			}
		}
		return  left > right ? -(left + 1) : res;
	}
	@Override
	public void clear() {
		array = (T[]) new Object[DEFAULT_CAPACITY];
		size = 0;
	}
	@Override
	public Iterator<T> iterator() {
		return new ArrayListIterator();
	}
	

}