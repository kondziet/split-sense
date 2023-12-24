package pl.kondziet.springbackend.application.service;

import java.util.Arrays;
import java.util.Comparator;
import java.util.List;

public class SortedContainer<T extends Comparable<T>> {

    private final T[] sortedData;
    private int leftIndex;
    private int rightIndex;

    private SortedContainer(T[] data, int left, int right) {
        this.sortedData = data;
        this.leftIndex = left;
        this.rightIndex = right;
    }

    public static <T extends Comparable<T>> SortedContainer<T> of(List<T> balances) {
        T[] sortedArray = balances.stream().sorted(Comparator.reverseOrder()).toArray(size -> (T[]) new Comparable[size]);

        return new SortedContainer<>(sortedArray, 0, sortedArray.length - 1);
    }

    public void updateMax(T updated) {
        sortedData[leftIndex] = updated;

        int sourceIndex = leftIndex;
        int targetIndex = leftIndex + 1;
        while (targetIndex <= rightIndex && sortedData[sourceIndex].compareTo(sortedData[targetIndex]) < 0) {
            swap(sourceIndex, targetIndex);
            sourceIndex++;
            targetIndex++;
        }
    }

    public void updateMin(T updated) {
        sortedData[rightIndex] = updated;

        int sourceIndex = rightIndex;
        int targetIndex = rightIndex - 1;
        while (targetIndex >= leftIndex && sortedData[sourceIndex].compareTo(sortedData[targetIndex]) > 0) {
            swap(sourceIndex, targetIndex);
            sourceIndex--;
            targetIndex--;
        }
    }

    public T removeMax() {
        if (isEmpty()) {
            throw new IllegalStateException("SortedContainer is empty");
        }

        T max = sortedData[leftIndex];
        sortedData[leftIndex] = null;
        leftIndex++;

        return max;
    }

    public T removeMin() {
        if (isEmpty()) {
            throw new IllegalStateException("SortedContainer is empty");
        }

        T min = sortedData[rightIndex];
        sortedData[rightIndex] = null;
        rightIndex--;

        return min;
    }

    public T getMax() {
        if (isEmpty()) {
            throw new IllegalStateException("SortedContainer is empty");
        }

        return sortedData[leftIndex];
    }

    public T getMin() {
        if (isEmpty()) {
            throw new IllegalStateException("SortedContainer is empty");
        }

        return sortedData[rightIndex];
    }

    public T get(int index) {
        if (leftIndex + index < leftIndex || leftIndex + index > rightIndex) {
            throw new IndexOutOfBoundsException("Index out of bounds: " + index);
        }

        return sortedData[leftIndex + index];
    }

    public T[] getData() {
        return Arrays.copyOfRange(sortedData, leftIndex, rightIndex + 1);
    }

    public int size() {
        return (rightIndex - leftIndex) + 1;
    }

    public boolean isEmpty() {
        return leftIndex > rightIndex;
    }

    private void swap(int sourceIndex, int targetIndex) {
        T tmp = sortedData[sourceIndex];
        sortedData[sourceIndex] = sortedData[targetIndex];
        sortedData[targetIndex] = tmp;
    }

    @Override
    public String toString() {
        return "MinMaxArray{" +
                "sortedData=" + Arrays.toString(sortedData) +
                ", leftIndex=" + leftIndex +
                ", rightIndex=" + rightIndex +
                '}';
    }

}
