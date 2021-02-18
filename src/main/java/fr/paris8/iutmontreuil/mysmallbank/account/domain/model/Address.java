package fr.paris8.iutmontreuil.mysmallbank.account.domain.model;

public class Address {

    private final String street;
    private final String zipCode;
    private final String city;
    private final String country;

    public Address(String street, String zipCode, String city, String country) {
        this.street = street;
        this.zipCode = zipCode;
        this.city = city;
        this.country = country;
    }

    public String getStreet() {
        return street;
    }

    public String getZipCode() {
        return zipCode;
    }

    public String getCity() {
        return city;
    }

    public String getCountry() {
        return country;
    }

    public boolean estIncomplet() {
        return this.street == null || this.street.length() == 0
                || this.zipCode == null || this.zipCode.length() == 0
                || this.city == null || this.city.length() == 0
                || this.country == null || this.country.length() == 0;
    }
}
