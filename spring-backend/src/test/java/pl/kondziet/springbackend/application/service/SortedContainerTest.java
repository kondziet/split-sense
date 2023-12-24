package pl.kondziet.springbackend.application.service;

import org.junit.jupiter.api.Test;

import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class SortedContainerTest {

    @Test
    public void testCreation() {
        List<Integer> data = Arrays.asList(5, 3, 4, 1, 2);
        SortedContainer<Integer> container = SortedContainer.of(data);
        assertEquals(5, container.size());
        assertFalse(container.isEmpty());
    }

    @Test
    public void testGetMax() {
        List<Integer> data = Arrays.asList(5, 3, 4, 1, 2);
        SortedContainer<Integer> container = SortedContainer.of(data);
        assertEquals(5, container.getMax());
    }

    @Test
    public void testGetMin() {
        List<Integer> data = Arrays.asList(5, 3, 4, 1, 2);
        SortedContainer<Integer> container = SortedContainer.of(data);
        assertEquals(1, container.getMin());
    }

    @Test
    public void testRemoveMax() {
        List<Integer> data = Arrays.asList(5, 3, 4, 1, 2);
        SortedContainer<Integer> container = SortedContainer.of(data);
        assertEquals(5, container.removeMax());
        assertEquals(4, container.getMax());
    }

    @Test
    public void testRemoveMin() {
        List<Integer> data = Arrays.asList(5, 3, 4, 1, 2);
        SortedContainer<Integer> container = SortedContainer.of(data);
        assertEquals(1, container.removeMin());
        assertEquals(2, container.getMin());
    }

    @Test
    public void testUpdateMax() {
        List<Integer> data = Arrays.asList(5, 3, 4, 1, 2);
        SortedContainer<Integer> container = SortedContainer.of(data);
        container.updateMax(6);
        assertEquals(6, container.getMax());
    }

    @Test
    public void testUpdateMin() {
        List<Integer> data = Arrays.asList(5, 3, 4, 1, 2);
        SortedContainer<Integer> container = SortedContainer.of(data);
        container.updateMin(0);
        assertEquals(0, container.getMin());
    }

    @Test
    public void testGet() {
        List<Integer> data = Arrays.asList(5, 3, 4, 1, 2);
        SortedContainer<Integer> container = SortedContainer.of(data);
        assertEquals(5, container.get(0));
        assertEquals(2, container.get(3));
    }

    @Test
    public void testSize() {
        List<Integer> data = Arrays.asList(5, 3, 4, 1, 2);
        SortedContainer<Integer> container = SortedContainer.of(data);
        assertEquals(5, container.size());
    }

    @Test
    public void testIsEmpty() {
        List<Integer> emptyList = List.of();
        SortedContainer<Integer> container = SortedContainer.of(emptyList);
        assertTrue(container.isEmpty());
    }

    @Test
    public void testOrderAfterRemoveMax() {
        List<Integer> data = Arrays.asList(5, 3, 4, 1, 2);
        SortedContainer<Integer> container = SortedContainer.of(data);
        assertEquals(5, container.removeMax());
        Comparable<Integer>[] comparable = container.getData();
        Integer[] integers = Arrays.stream(comparable).map(e -> (Integer) e).toArray(Integer[]::new);
        List<Integer> actualData = Arrays.asList(integers);
        assertEquals(Arrays.asList(4, 3, 2, 1), actualData);
    }

    @Test
    public void testOrderAfterRemoveMin() {
        List<Integer> data = Arrays.asList(5, 3, 4, 1, 2);
        SortedContainer<Integer> container = SortedContainer.of(data);
        assertEquals(1, container.removeMin());
        Comparable<Integer>[] comparable = container.getData();
        Integer[] integers = Arrays.stream(comparable).map(e -> (Integer) e).toArray(Integer[]::new);
        List<Integer> actualData = Arrays.asList(integers);
        assertEquals(Arrays.asList(5, 4, 3, 2), actualData);
    }

    @Test
    public void testOrderAfterUpdateMax() {
        List<Integer> data = Arrays.asList(5, 3, 4, 1, 2);
        SortedContainer<Integer> container = SortedContainer.of(data);
        container.updateMax(6);
        Comparable<Integer>[] comparable = container.getData();
        Integer[] integers = Arrays.stream(comparable).map(e -> (Integer) e).toArray(Integer[]::new);
        List<Integer> actualData = Arrays.asList(integers);
        assertEquals(Arrays.asList(6, 4, 3, 2, 1), actualData);
    }

    @Test
    public void testOrderAfterUpdateMin() {
        List<Integer> data = Arrays.asList(5, 3, 4, 1, 2);
        SortedContainer<Integer> container = SortedContainer.of(data);
        container.updateMin(0);
        Comparable<Integer>[] comparable = container.getData();
        Integer[] integers = Arrays.stream(comparable).map(e -> (Integer) e).toArray(Integer[]::new);
        List<Integer> actualData = Arrays.asList(integers);
        assertEquals(Arrays.asList(5, 4, 3, 2, 0), actualData);
    }

}