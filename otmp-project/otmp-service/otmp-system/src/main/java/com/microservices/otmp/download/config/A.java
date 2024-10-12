package com.microservices.otmp.download.config;

public interface A {
    void test1();

    void test2();
}

interface B {
    void test1();

    void test3();
}

interface C extends A, B {
}

class D implements C {
    @Override
    public void test1() {

    }

    @Override
    public void test2() {

    }

    @Override
    public void test3() {

    }

    public static void main(String[] args) {
        D d = new D();
        d.test1();
        d.test2();
        d.test3();
    }
}

class A1 implements A {
    @Override
    public void test1() {
        System.out.println("test1 A1");
    }

    @Override
    public void test2() {
        System.out.println("test2 A1");
    }
}

class B1 implements B {
    @Override
    public void test1() {
        System.out.println("test1 B1");
    }

    @Override
    public void test3() {
        System.out.println("test3 B1");
    }
}
