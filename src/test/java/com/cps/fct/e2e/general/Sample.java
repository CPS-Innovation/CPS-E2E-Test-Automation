package com.cps.fct.e2e.general;

import static com.cps.fct.e2e.utils.common.FakerUtils.faker;

public class Sample {

    public static void main(String[] args){
//        System.out.println("Hello World");
        county();
    }

    public static void fullName() {
//        return faker.name().fullName();
//        System.out.println(faker.name().firstName());
//        System.out.println(faker.name().lastName().toUpperCase());

        String fullName = faker.name().lastName().toUpperCase() + " " + faker.name().firstName();
        System.out.println(fullName);

    }


    public static void county() {
//        return faker.name().fullName();
//        System.out.println(faker.name().firstName());
//        System.out.println(faker.name().lastName().toUpperCase());

//        String fullName = faker.name().lastName().toUpperCase() + " " + faker.name().firstName();
        System.out.println(faker.bothify("??## #??").toUpperCase());

    }



}


