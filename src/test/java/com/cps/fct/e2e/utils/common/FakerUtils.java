package com.cps.fct.e2e.utils.common;

import com.github.javafaker.Faker;

import java.security.SecureRandom;
import java.time.LocalDate;
import java.time.ZoneId;
import java.util.Date;
import java.util.Locale;
import java.util.Random;
import java.util.UUID;

public class FakerUtils {

    public static final Faker faker = new Faker(new Locale("en"));
    private static final Random random = new Random();

    public static String uuid() {
        return UUID.randomUUID().toString();
    }

    public static String fullName() {
        return faker.name().fullName();
    }

    public static String firstName() {
        return faker.name().firstName();
    }

    public static String middleName() {
        return faker.name().lastName();
    }

    public static String lastName() {
        return faker.name().lastName();
    }

    public static String buildingNumber() {
        return faker.address().buildingNumber();
    }

    public static String streetName() {
        return faker.address().streetName();
    }

    public static String streetAddress() {
        return faker.address().streetAddress();
    }

    public static String cityName() {
        return faker.address().cityName();
    }


    public static String populateSentences() {
        return faker.yoda().quote();

    }

    public static String gender() {
        return faker.demographic().sex();
    }

    public static String address() {
        return faker.address().fullAddress();
    }

    public static LocalDate dateOfBirth() {
        Date dob = faker.date().birthday();
        return dob.toInstant().atZone(ZoneId.systemDefault()).toLocalDate();
    }

    public static String email() {
        return faker.internet().emailAddress();
    }

    public static String homePhone() {
        return "01" + faker.numerify("#########");
    }

    public static String mobilePhone() {
        return "07" + faker.numerify("#########");
    }

    public static String ukPostCode() {
        return faker.bothify("??## #??");
    }

    public static String companyName() {
        return faker.company().name();
    }

    public static String jobTitle() {
        return faker.job().title();
    }


    public static String twoDigitNumber() {
        return String.valueOf(random.nextInt(90) + 10);
    }

    public static String twoLetterCode() {
        return faker.letterify("??").toUpperCase();
    }

    public static String fiveDigitNumber() {
        return String.valueOf(random.nextInt(90000) + 10000);
    }

    public static String generateFiveDigitNumber() {
        return String.valueOf(10000 + random.nextInt(90000));
    }

    public static String generateTenDigitNumber() {
        return String.valueOf(1000000000L + (long) (random.nextDouble() * 9000000000L));
    }

    public static String generateUppercaseAlphaNumeric(int length) {
        String CHARS = "ABCDEFGHIJKLMNOPQRSTUVWXYZ0123456789";
        SecureRandom RANDOM = new SecureRandom();
        return RANDOM.ints(length, 0, CHARS.length())
                .mapToObj(CHARS::charAt)
                .collect(StringBuilder::new, StringBuilder::append, StringBuilder::append)
                .toString();
    }


    public static String uniqueId() {
        String pattern = "??-#-??-#";
        return faker.bothify(pattern).toUpperCase();
    }


    public static String yearYY() {
        int year = random.nextInt(2025 - 2020 + 1) + 2020; // Random year between 2010 and 2025
        return String.format("%02d", year % 100);
    }


    public static void main(String[] args) {
        Faker faker1 = new Faker();
        System.out.println("01" + faker1.numerify("########")); // work phone
        System.out.println("07" + faker1.numerify("#########"));

    }

}











