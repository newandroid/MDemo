package com.example.mrxjava.css;

import com.example.mrxjava.reactive.IObserver;

import org.junit.Test;

import java.util.List;

import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;

public class MockitoTest {
    @Test
    public void hoh() {
        List list = mock(List.class);
        list.add("fuck");
        list.clear();
        verify(list).add("fuck");
        verify(list).clear();
    }
}
