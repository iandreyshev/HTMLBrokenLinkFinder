package com.javacore.brokenLinksFinder;

import static org.junit.Assert.*;

import org.junit.Before;
import org.junit.Test;

public class WebWorkerTest {
    private WebWorker worker;

    @Before
    public void resetWorker() {
        worker = new WebWorker();
    }
}
