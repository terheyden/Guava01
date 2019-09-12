package com.terheyden;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.Map;

import javax.annotation.ParametersAreNonnullByDefault;

import com.google.common.collect.HashBasedTable;
import com.google.common.collect.Table;

import org.junit.jupiter.api.Test;

@ParametersAreNonnullByDefault
public class Tables
{
    @Test
    public void test()
    {
        // Guava Tables are like Maps with two keys.
        Table<Integer, Integer, String> grid = HashBasedTable.create();

        grid.put(0, 0, "(0, 0)");
        grid.put(0, 1, "(0, 1)");
        grid.put(1, 0, "(1, 0)");
        grid.put(1, 1, "(1, 1)");

        assertEquals("(1, 1)", grid.get(1, 1));

        // (row, col)
        assertTrue(grid.containsRow(1));
        assertTrue(grid.containsColumn(1));
        assertTrue(grid.contains(1, 1));
        assertTrue(grid.containsValue("(0, 0)"));

        // You can get a single row or col as a normal map.
        Map<Integer, String> row0 = grid.row(0);
        Map<Integer, String> col1 = grid.column(1);
    }

    @Test
    public void testNulls()
    {
        Table<Integer, Integer, String> grid = HashBasedTable.create();

        // Just like Java Maps, get() returns null if not found.
        // Most other actions return an empty set, though.
        assertNull(grid.get(9, 9));

        // row(N) and column(N) return empty:
        assertTrue(grid.row(9).isEmpty());
        assertTrue(grid.column(9).isEmpty());

        // I assume the key value collections are just empty.
        assertTrue(grid.rowKeySet().isEmpty());
        assertTrue(grid.rowMap().isEmpty());
        assertTrue(grid.columnKeySet().isEmpty());
        assertTrue(grid.columnMap().isEmpty());
    }

}
