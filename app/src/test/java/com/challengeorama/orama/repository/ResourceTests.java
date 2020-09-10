package com.challengeorama.orama.repository;

import org.junit.Test;

import static org.junit.Assert.*;

public class ResourceTests {


    @Test
    public void result_success() {

        Resource<String> inTest = Resource.success("testando123");
        assertEquals("testando123", inTest.data);
        assertEquals(Resource.Status.SUCCESS, inTest.status);
        assertNull(inTest.message);

    }

    @Test
    public void result_loading() {

        Resource<String> inTest = Resource.loading(null);
        assertNull(inTest.data);
        assertEquals(Resource.Status.LOADING, inTest.status);
        assertNull(inTest.message);

    }

    @Test
    public void result_error() {

        Resource<String> inTest = Resource.error("aconteceu um erro", null);
        assertNull(inTest.data);
        assertEquals(Resource.Status.ERROR, inTest.status);
        assertEquals("aconteceu um erro", inTest.message);

    }

}
