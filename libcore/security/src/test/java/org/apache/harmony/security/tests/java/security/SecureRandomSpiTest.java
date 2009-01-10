/*
 *  Licensed to the Apache Software Foundation (ASF) under one or more
 *  contributor license agreements.  See the NOTICE file distributed with
 *  this work for additional information regarding copyright ownership.
 *  The ASF licenses this file to You under the Apache License, Version 2.0
 *  (the "License"); you may not use this file except in compliance with
 *  the License.  You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 *  Unless required by applicable law or agreed to in writing, software
 *  distributed under the License is distributed on an "AS IS" BASIS,
 *  WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 *  See the License for the specific language governing permissions and
 *  limitations under the License.
 */

package org.apache.harmony.security.tests.java.security;

import dalvik.annotation.TestTargetClass;
import dalvik.annotation.TestTargets;
import dalvik.annotation.TestLevel;
import dalvik.annotation.TestTargetNew;

import java.security.SecureRandomSpi;

import junit.framework.TestCase;

/**
 * Tests for <code>SecureRandomSpi</code> class constructors
 * and methods.
 * 
 */
@TestTargetClass(SecureRandomSpi.class)
public class SecureRandomSpiTest extends TestCase {

    /**
     * Constructor for SecureRandomSpiTest.
     * 
     * @param name
     */
    public SecureRandomSpiTest(String name) {
        super(name);
    }

    /**
     * Test for <code>SecureRandomSpi</code> constructor
     * Assertion: constructs SecureRandomSpi
     */
    @TestTargets({
        @TestTargetNew(
            level = TestLevel.COMPLETE,
            notes = "",
            method = "SecureRandomSpi",
            args = {}
        ),
        @TestTargetNew(
            level = TestLevel.COMPLETE,
            notes = "",
            method = "engineGenerateSeed",
            args = {int.class}
        ),
        @TestTargetNew(
            level = TestLevel.COMPLETE,
            notes = "",
            method = "engineNextBytes",
            args = {byte[].class}
        ),
        @TestTargetNew(
            level = TestLevel.COMPLETE,
            notes = "",
            method = "engineSetSeed",
            args = {byte[].class}
        )
    })
    public void testSecureRandomSpi() {
        try {
            MySecureRandomSpi srs = new MySecureRandomSpi();
            assertTrue(srs instanceof SecureRandomSpi);
        } catch (Exception e) {
            fail("Unexpected exception");
        }
        
        try {
            MySecureRandomSpi srs = new MySecureRandomSpi();
            srs.engineGenerateSeed(10);
            srs.engineNextBytes(new byte[10]);
            srs.engineSetSeed(new byte[3]);
        } catch (Exception e) {
            fail("Unexpected exception");
        }
    }

    public class MySecureRandomSpi extends SecureRandomSpi {
        protected void engineSetSeed(byte[] seed) {}
        protected void engineNextBytes(byte[] bytes) {}
        protected byte[] engineGenerateSeed(int numBytes) {
            return null;
        }
    }
}