package com.jaycodes.rebtel_assignment.viewModels;

import android.content.Context;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;

import androidx.test.core.app.ApplicationProvider;

import static org.junit.Assert.*;

    public class MainActivityViewModelTest {
    MainActivityViewModel mainActivityViewModel;
//    private Context context = ApplicationProvider.getApplicationContext();

    @Mock
    Context mockContext;

    @Before
    public void setUp() throws Exception {
        mainActivityViewModel = new MainActivityViewModel();
        mainActivityViewModel.init(mockContext);
    }

    @After
    public void tearDown() throws Exception {
        assertNotNull(mainActivityViewModel.getCountryList());
    }

    @Test
    public void getCountryList() {
        mainActivityViewModel = null;
    }
}