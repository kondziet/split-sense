package pl.kondziet.springbackend.application.domain.service;

import pl.kondziet.springbackend.application.domain.model.entity.Balance;

import java.util.Arrays;
import java.util.Comparator;
import java.util.List;

public class SortedBalanceContainer {

    private final Balance[] sortedData;
    private int leftIndex;
    private int rightIndex;

    private SortedBalanceContainer(Balance[] data, int left, int right) {
        this.sortedData = data;
        this.leftIndex = left;
        this.rightIndex = right;
    }

    public static SortedBalanceContainer of(List<Balance> balances) {
        Balance[] sortedArray = balances.stream().sorted(Comparator.reverseOrder()).toArray(Balance[]::new);

        return new SortedBalanceContainer(sortedArray, 0, sortedArray.length - 1);
    }

    public void updateMax(Balance updated) {
        sortedData[leftIndex] = updated;

        int sourceIndex = leftIndex;
        int targetIndex = leftIndex + 1;
        while (targetIndex <= rightIndex && sortedData[sourceIndex].compareTo(sortedData[targetIndex]) < 0) {
            swap(sourceIndex, targetIndex);
            sourceIndex++;
            targetIndex++;
        }
    }

    public void updateMin(Balance updated) {
        sortedData[rightIndex] = updated;

        int sourceIndex = rightIndex;
        int targetIndex = rightIndex - 1;
        while (targetIndex >= leftIndex && sortedData[sourceIndex].compareTo(sortedData[targetIndex]) > 0) {
            swap(sourceIndex, targetIndex);
            sourceIndex--;
            targetIndex--;
        }
    }

    public Balance removeMax() {
        if (isEmpty()) {
            throw new IllegalStateException("SortedBalanceContainer is empty");
        }

        Balance max = sortedData[leftIndex];
        sortedData[leftIndex] = null;
        leftIndex++;

        return max;
    }

    public Balance removeMin() {
        if (isEmpty()) {
            throw new IllegalStateException("SortedBalanceContainer is empty");
        }

        Balance min = sortedData[rightIndex];
        sortedData[rightIndex] = null;
        rightIndex--;

        return min;
    }

    public Balance getMax() {
        if (isEmpty()) {
            throw new IllegalStateException("SortedBalanceContainer is empty");
        }

        return sortedData[leftIndex];
    }

    public Balance getMin() {
        if (isEmpty()) {
            throw new IllegalStateException("SortedBalanceContainer is empty");
        }

        return sortedData[rightIndex];
    }

    public Balance get(int index) {
        if (leftIndex + index < leftIndex || leftIndex + index > rightIndex) {
            throw new IndexOutOfBoundsException("Index out of bounds: " + index);
        }

        return sortedData[leftIndex + index];
    }

    public int size() {
        return (rightIndex - leftIndex) + 1;
    }

    public boolean isEmpty() {
        return leftIndex > rightIndex;
    }

    private void swap(int sourceIndex, int targetIndex) {
        Balance tmp = sortedData[sourceIndex];
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
