package com.jaycodes.rebtel_assignment.repository.database;

import android.content.Context;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;

import static org.junit.Assert.*;

public class countryDatabaseTest {
    countryDatabase countryDatabase;

    @Mock
    Context mockContext;

    @Before
    public void setUp() throws Exception {
        countryDatabase = countryDatabase.getInstance(mockContext);
    }

    @After
    public void tearDown() throws Exception {
    }

    @Test
    public void getInstance() {
        assertNotNull(countryDatabase);
    }
}