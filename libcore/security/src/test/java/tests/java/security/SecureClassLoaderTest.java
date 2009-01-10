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

/**
 * @author Alexander V. Astapchuk
 * @version $Revision$
 */

package tests.java.security;

import dalvik.annotation.TestLevel;
import dalvik.annotation.TestTargetClass;
import dalvik.annotation.TestTargetNew;

import junit.framework.TestCase;

import java.net.URL;
import java.net.URLClassLoader;
import java.nio.ByteBuffer;
import java.security.CodeSource;
import java.security.Permission;
import java.security.PermissionCollection;
import java.security.ProtectionDomain;
import java.security.SecureClassLoader;
import java.security.cert.Certificate;
@TestTargetClass(value=SecureClassLoader.class,
        untestedMethods={
            @TestTargetNew(
                    level = TestLevel.NOT_FEASIBLE,
                    notes = "cannot be tested",
                    method = "defineClass",
                    args = {
                        java.lang.String.class, byte[].class, int.class, 
                        int.class, java.security.CodeSource.class}
            ),
            @TestTargetNew(
                    level = TestLevel.NOT_FEASIBLE,
                    notes = "cannot be tested",
                    method = "defineClass",
                    args = {
                        java.lang.String.class, java.nio.ByteBuffer.class,
                        java.security.CodeSource.class}
            )           
})
/**
 * Unit test for SecureClassLoader.
 * 
 */

public class SecureClassLoaderTest extends TestCase {
    /**
     * Entry point for stand alone runs.
     * 
     * @param args
     *            command line parameters
     */
    public static void main(String[] args) {
        junit.textui.TestRunner.run(SecureClassLoaderTest.class);
    }

    /**
     * A class name for the class presented as {@link #klassData bytecode below}
     */
    private static final String klassName = "HiWorld";

    /**
     * Some class presented as bytecode<br>
     * Class src:<br>
     * <p>
     * <code>public class HiWorld {
     *     public static void main(String[] args) 
     *         {System.out.println("Hi, world!"); } 
     *    }
     * </code>
     */

    private static final byte[] klassData = { (byte) 0xCA, (byte) 0xFE,
            (byte) 0xBA, (byte) 0xBE, (byte) 0x00, (byte) 0x00, (byte) 0x00,
            (byte) 0x2E, (byte) 0x00, (byte) 0x22, (byte) 0x01, (byte) 0x00,
            (byte) 0x07, (byte) 0x48, (byte) 0x69, (byte) 0x57, (byte) 0x6F,
            (byte) 0x72, (byte) 0x6C, (byte) 0x64, (byte) 0x07, (byte) 0x00,
            (byte) 0x01, (byte) 0x01, (byte) 0x00, (byte) 0x10, (byte) 0x6A,
            (byte) 0x61, (byte) 0x76, (byte) 0x61, (byte) 0x2F, (byte) 0x6C,
            (byte) 0x61, (byte) 0x6E, (byte) 0x67, (byte) 0x2F, (byte) 0x4F,
            (byte) 0x62, (byte) 0x6A, (byte) 0x65, (byte) 0x63, (byte) 0x74,
            (byte) 0x07, (byte) 0x00, (byte) 0x03, (byte) 0x01, (byte) 0x00,
            (byte) 0x06, (byte) 0x3C, (byte) 0x69, (byte) 0x6E, (byte) 0x69,
            (byte) 0x74, (byte) 0x3E, (byte) 0x01, (byte) 0x00, (byte) 0x03,
            (byte) 0x28, (byte) 0x29, (byte) 0x56, (byte) 0x01, (byte) 0x00,
            (byte) 0x04, (byte) 0x43, (byte) 0x6F, (byte) 0x64, (byte) 0x65,
            (byte) 0x0C, (byte) 0x00, (byte) 0x05, (byte) 0x00, (byte) 0x06,
            (byte) 0x0A, (byte) 0x00, (byte) 0x04, (byte) 0x00, (byte) 0x08,
            (byte) 0x01, (byte) 0x00, (byte) 0x0F, (byte) 0x4C, (byte) 0x69,
            (byte) 0x6E, (byte) 0x65, (byte) 0x4E, (byte) 0x75, (byte) 0x6D,
            (byte) 0x62, (byte) 0x65, (byte) 0x72, (byte) 0x54, (byte) 0x61,
            (byte) 0x62, (byte) 0x6C, (byte) 0x65, (byte) 0x01, (byte) 0x00,
            (byte) 0x12, (byte) 0x4C, (byte) 0x6F, (byte) 0x63, (byte) 0x61,
            (byte) 0x6C, (byte) 0x56, (byte) 0x61, (byte) 0x72, (byte) 0x69,
            (byte) 0x61, (byte) 0x62, (byte) 0x6C, (byte) 0x65, (byte) 0x54,
            (byte) 0x61, (byte) 0x62, (byte) 0x6C, (byte) 0x65, (byte) 0x01,
            (byte) 0x00, (byte) 0x04, (byte) 0x74, (byte) 0x68, (byte) 0x69,
            (byte) 0x73, (byte) 0x01, (byte) 0x00, (byte) 0x09, (byte) 0x4C,
            (byte) 0x48, (byte) 0x69, (byte) 0x57, (byte) 0x6F, (byte) 0x72,
            (byte) 0x6C, (byte) 0x64, (byte) 0x3B, (byte) 0x01, (byte) 0x00,
            (byte) 0x04, (byte) 0x6D, (byte) 0x61, (byte) 0x69, (byte) 0x6E,
            (byte) 0x01, (byte) 0x00, (byte) 0x16, (byte) 0x28, (byte) 0x5B,
            (byte) 0x4C, (byte) 0x6A, (byte) 0x61, (byte) 0x76, (byte) 0x61,
            (byte) 0x2F, (byte) 0x6C, (byte) 0x61, (byte) 0x6E, (byte) 0x67,
            (byte) 0x2F, (byte) 0x53, (byte) 0x74, (byte) 0x72, (byte) 0x69,
            (byte) 0x6E, (byte) 0x67, (byte) 0x3B, (byte) 0x29, (byte) 0x56,
            (byte) 0x01, (byte) 0x00, (byte) 0x10, (byte) 0x6A, (byte) 0x61,
            (byte) 0x76, (byte) 0x61, (byte) 0x2F, (byte) 0x6C, (byte) 0x61,
            (byte) 0x6E, (byte) 0x67, (byte) 0x2F, (byte) 0x53, (byte) 0x79,
            (byte) 0x73, (byte) 0x74, (byte) 0x65, (byte) 0x6D, (byte) 0x07,
            (byte) 0x00, (byte) 0x10, (byte) 0x01, (byte) 0x00, (byte) 0x03,
            (byte) 0x6F, (byte) 0x75, (byte) 0x74, (byte) 0x01, (byte) 0x00,
            (byte) 0x15, (byte) 0x4C, (byte) 0x6A, (byte) 0x61, (byte) 0x76,
            (byte) 0x61, (byte) 0x2F, (byte) 0x69, (byte) 0x6F, (byte) 0x2F,
            (byte) 0x50, (byte) 0x72, (byte) 0x69, (byte) 0x6E, (byte) 0x74,
            (byte) 0x53, (byte) 0x74, (byte) 0x72, (byte) 0x65, (byte) 0x61,
            (byte) 0x6D, (byte) 0x3B, (byte) 0x0C, (byte) 0x00, (byte) 0x12,
            (byte) 0x00, (byte) 0x13, (byte) 0x09, (byte) 0x00, (byte) 0x11,
            (byte) 0x00, (byte) 0x14, (byte) 0x01, (byte) 0x00, (byte) 0x0A,
            (byte) 0x48, (byte) 0x69, (byte) 0x2C, (byte) 0x20, (byte) 0x77,
            (byte) 0x6F, (byte) 0x72, (byte) 0x6C, (byte) 0x64, (byte) 0x21,
            (byte) 0x08, (byte) 0x00, (byte) 0x16, (byte) 0x01, (byte) 0x00,
            (byte) 0x13, (byte) 0x6A, (byte) 0x61, (byte) 0x76, (byte) 0x61,
            (byte) 0x2F, (byte) 0x69, (byte) 0x6F, (byte) 0x2F, (byte) 0x50,
            (byte) 0x72, (byte) 0x69, (byte) 0x6E, (byte) 0x74, (byte) 0x53,
            (byte) 0x74, (byte) 0x72, (byte) 0x65, (byte) 0x61, (byte) 0x6D,
            (byte) 0x07, (byte) 0x00, (byte) 0x18, (byte) 0x01, (byte) 0x00,
            (byte) 0x07, (byte) 0x70, (byte) 0x72, (byte) 0x69, (byte) 0x6E,
            (byte) 0x74, (byte) 0x6C, (byte) 0x6E, (byte) 0x01, (byte) 0x00,
            (byte) 0x15, (byte) 0x28, (byte) 0x4C, (byte) 0x6A, (byte) 0x61,
            (byte) 0x76, (byte) 0x61, (byte) 0x2F, (byte) 0x6C, (byte) 0x61,
            (byte) 0x6E, (byte) 0x67, (byte) 0x2F, (byte) 0x53, (byte) 0x74,
            (byte) 0x72, (byte) 0x69, (byte) 0x6E, (byte) 0x67, (byte) 0x3B,
            (byte) 0x29, (byte) 0x56, (byte) 0x0C, (byte) 0x00, (byte) 0x1A,
            (byte) 0x00, (byte) 0x1B, (byte) 0x0A, (byte) 0x00, (byte) 0x19,
            (byte) 0x00, (byte) 0x1C, (byte) 0x01, (byte) 0x00, (byte) 0x04,
            (byte) 0x61, (byte) 0x72, (byte) 0x67, (byte) 0x73, (byte) 0x01,
            (byte) 0x00, (byte) 0x13, (byte) 0x5B, (byte) 0x4C, (byte) 0x6A,
            (byte) 0x61, (byte) 0x76, (byte) 0x61, (byte) 0x2F, (byte) 0x6C,
            (byte) 0x61, (byte) 0x6E, (byte) 0x67, (byte) 0x2F, (byte) 0x53,
            (byte) 0x74, (byte) 0x72, (byte) 0x69, (byte) 0x6E, (byte) 0x67,
            (byte) 0x3B, (byte) 0x01, (byte) 0x00, (byte) 0x0A, (byte) 0x53,
            (byte) 0x6F, (byte) 0x75, (byte) 0x72, (byte) 0x63, (byte) 0x65,
            (byte) 0x46, (byte) 0x69, (byte) 0x6C, (byte) 0x65, (byte) 0x01,
            (byte) 0x00, (byte) 0x0C, (byte) 0x48, (byte) 0x69, (byte) 0x57,
            (byte) 0x6F, (byte) 0x72, (byte) 0x6C, (byte) 0x64, (byte) 0x2E,
            (byte) 0x6A, (byte) 0x61, (byte) 0x76, (byte) 0x61, (byte) 0x00,
            (byte) 0x21, (byte) 0x00, (byte) 0x02, (byte) 0x00, (byte) 0x04,
            (byte) 0x00, (byte) 0x00, (byte) 0x00, (byte) 0x00, (byte) 0x00,
            (byte) 0x02, (byte) 0x00, (byte) 0x01, (byte) 0x00, (byte) 0x05,
            (byte) 0x00, (byte) 0x06, (byte) 0x00, (byte) 0x01, (byte) 0x00,
            (byte) 0x07, (byte) 0x00, (byte) 0x00, (byte) 0x00, (byte) 0x2F,
            (byte) 0x00, (byte) 0x01, (byte) 0x00, (byte) 0x01, (byte) 0x00,
            (byte) 0x00, (byte) 0x00, (byte) 0x05, (byte) 0x2A, (byte) 0xB7,
            (byte) 0x00, (byte) 0x09, (byte) 0xB1, (byte) 0x00, (byte) 0x00,
            (byte) 0x00, (byte) 0x02, (byte) 0x00, (byte) 0x0A, (byte) 0x00,
            (byte) 0x00, (byte) 0x00, (byte) 0x06, (byte) 0x00, (byte) 0x01,
            (byte) 0x00, (byte) 0x00, (byte) 0x00, (byte) 0x14, (byte) 0x00,
            (byte) 0x0B, (byte) 0x00, (byte) 0x00, (byte) 0x00, (byte) 0x0C,
            (byte) 0x00, (byte) 0x01, (byte) 0x00, (byte) 0x00, (byte) 0x00,
            (byte) 0x05, (byte) 0x00, (byte) 0x0C, (byte) 0x00, (byte) 0x0D,
            (byte) 0x00, (byte) 0x00, (byte) 0x00, (byte) 0x09, (byte) 0x00,
            (byte) 0x0E, (byte) 0x00, (byte) 0x0F, (byte) 0x00, (byte) 0x01,
            (byte) 0x00, (byte) 0x07, (byte) 0x00, (byte) 0x00, (byte) 0x00,
            (byte) 0x37, (byte) 0x00, (byte) 0x02, (byte) 0x00, (byte) 0x01,
            (byte) 0x00, (byte) 0x00, (byte) 0x00, (byte) 0x09, (byte) 0xB2,
            (byte) 0x00, (byte) 0x15, (byte) 0x12, (byte) 0x17, (byte) 0xB6,
            (byte) 0x00, (byte) 0x1D, (byte) 0xB1, (byte) 0x00, (byte) 0x00,
            (byte) 0x00, (byte) 0x02, (byte) 0x00, (byte) 0x0A, (byte) 0x00,
            (byte) 0x00, (byte) 0x00, (byte) 0x0A, (byte) 0x00, (byte) 0x02,
            (byte) 0x00, (byte) 0x00, (byte) 0x00, (byte) 0x17, (byte) 0x00,
            (byte) 0x08, (byte) 0x00, (byte) 0x18, (byte) 0x00, (byte) 0x0B,
            (byte) 0x00, (byte) 0x00, (byte) 0x00, (byte) 0x0C, (byte) 0x00,
            (byte) 0x01, (byte) 0x00, (byte) 0x00, (byte) 0x00, (byte) 0x09,
            (byte) 0x00, (byte) 0x1E, (byte) 0x00, (byte) 0x1F, (byte) 0x00,
            (byte) 0x00, (byte) 0x00, (byte) 0x01, (byte) 0x00, (byte) 0x20,
            (byte) 0x00, (byte) 0x00, (byte) 0x00, (byte) 0x02, (byte) 0x00,
            (byte) 0x21, };

    /**
     * Tests default ctor
     */
    @TestTargetNew(
        level = TestLevel.COMPLETE,
        notes = "",
        method = "SecureClassLoader",
        args = {}
    )
    public void testSecureClassLoader() {
        new MyClassLoader();
        
        class TestSecurityManager extends SecurityManager {
            boolean called;
            @Override
            public void checkCreateClassLoader() {
                called = true;
                super.checkCreateClassLoader();
            }
            
            @Override
            public void checkPermission(Permission permission) {
                if (permission instanceof RuntimePermission) {
                    if (permission.getName().equals("createClassLoader")) {
                        throw new SecurityException();
                    }
                }
            }
        }
        
        TestSecurityManager sm = new TestSecurityManager();
        try {
            System.setSecurityManager(sm);
            new MyClassLoader();
            fail("expected SecurityException");
        } catch (SecurityException e) {
            assertTrue("checkCreateClassLoader was not called", sm.called);
            // ok
        } finally {
            System.setSecurityManager(null);
        }
    }

    /**
     * Tests SecureClassLoader(ClassLoader)
     */
    @TestTargetNew(
        level = TestLevel.COMPLETE,
        notes = "Verification with null parameter missed",
        method = "SecureClassLoader",
        args = {java.lang.ClassLoader.class}
    )
    public void testSecureClassLoaderClassLoader() throws Exception {
        URL[] urls = new URL[] { new URL("http://localhost") };
        URLClassLoader ucl = URLClassLoader.newInstance(urls);
        new MyClassLoader(ucl);
        
        try {
            new MyClassLoader(null);
        } catch (Exception e) {
            fail("unexpected exception: " + e);
        }
        
        class TestSecurityManager extends SecurityManager {
            boolean called;
            @Override
            public void checkCreateClassLoader() {
                called = true;
                super.checkCreateClassLoader();
            }
            
            @Override
            public void checkPermission(Permission permission) {
                if (permission instanceof RuntimePermission) {
                    if (permission.getName().equals("createClassLoader")) {
                        throw new SecurityException();
                    }
                }
            }
        }
        
        TestSecurityManager sm = new TestSecurityManager();
        try {
            System.setSecurityManager(sm);
            new MyClassLoader(ucl);
            fail("expected SecurityException");
        } catch (SecurityException e) {
            // ok
            assertTrue("checkCreateClassLoader was not called", sm.called);
            
        } finally {
            System.setSecurityManager(null);
        }
    }

    /**
     * Tests getPermission
     */
    @TestTargetNew(
        level = TestLevel.SUFFICIENT,
        notes = "",
        method = "getPermissions",
        args = {java.security.CodeSource.class}
    )
    public void testGetPermissions() throws Exception {
        URL url = new URL("http://localhost");
        CodeSource cs = new CodeSource(url, (Certificate[]) null);
        MyClassLoader ldr = new MyClassLoader();
        ldr.getPerms(null);
        ldr.getPerms(cs);
    }

    /**
     * Tests defineClass(String, byte[], int, int, CodeSource)
     */
    @TestTargetNew(
        level = TestLevel.NOT_FEASIBLE,
        notes = "ClassFormatError, IndexOutOfBoundsException, SecurityException checking missed",
        method = "defineClass",
        args = {java.lang.String.class, byte[].class, int.class, int.class, java.security.CodeSource.class}
    )
    public void _testDefineClassStringbyteArrayintintCodeSource() {
        MyClassLoader ldr = new MyClassLoader();
        Class klass = ldr.define(null, klassData, 0, klassData.length, null);
        assertEquals(klass.getName(), klassName);
    }

    /**
     * Tests defineClass(String, ByteBuffer, CodeSource)
     */
    @TestTargetNew(
        level = TestLevel.NOT_FEASIBLE,
        notes = "ClassFormatError, SecurityException checking missed",
        method = "defineClass",
        args = {java.lang.String.class, java.nio.ByteBuffer.class, java.security.CodeSource.class}
    )
    public void _testDefineClassStringByteBufferCodeSource() {
        MyClassLoader ldr = new MyClassLoader();
        ByteBuffer bbuf = ByteBuffer.wrap(klassData);
        Class klass = ldr.define(null, bbuf, null);
        assertEquals(klass.getName(), klassName);
    }

    class MyClassLoader extends SecureClassLoader {

        public MyClassLoader() {
            super();
        }

        public MyClassLoader(ClassLoader parent) {
            super(parent);
        }

        public PermissionCollection getPerms(CodeSource codesource) {
            return super.getPermissions(codesource);
        }

        public Class define(String name, byte[] bytes) {
            return defineClass(name, bytes, 0, bytes.length,
                    (ProtectionDomain) null);
        }

        public Class define(String name, ByteBuffer b, CodeSource cs) {
            return defineClass(name, b, cs);
        }

        public Class define(String name, byte[] b, int off, int len,
                CodeSource cs) {
            return defineClass(name, b, off, len, cs);
        }

    }
}
