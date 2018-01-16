package com.paresh.diff.util.test;

import com.paresh.diff.annotations.Description;
import com.paresh.diff.annotations.Identifier;

import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class TestDataProvider {

    public static Person getPerson(final int age, final String name, final List<Address> addresses,
                                   final Map<String, String> attributes) {
        return new PersonBuilder().setAge(age).setName(name).setAddresses(addresses).setAttributes(attributes).build();
    }

    public static List<Person> getBeforePersonList() {
        List<Address> addressList1 = Arrays.asList(new AddressBuilder().setCity("Delhi").build(),
                new AddressBuilder().setCity("Hong Kong").build());

        Map<String, String> attributes1 = new HashMap<>();
        attributes1.put("Height", "175");
        attributes1.put("Weight", "72");
        attributes1.put("Hair Color", "Black");

        Map<String, String> attributes2 = new HashMap<>();
        attributes2.put("Height", "180");
        attributes2.put("Weight", "95");

        List<Address> addressList2 = Arrays.asList(new AddressBuilder().setCity("Delhi").build(),
                new AddressBuilder().setCity("Mumbai").build());
        return Arrays.asList(
                new PersonBuilder().setAge(36).setName("Sam Adams").setAddresses(addressList1).setAttributes(attributes1).build(),
                new PersonBuilder().setAge(32).setName("Jolly Adams").setAddresses(addressList2).setAttributes(attributes2).build());

    }

    public static List<Person> getAfterPersonList() {
        Map<String, String> attributes1 = new HashMap<>();
        attributes1.put("Height", "175");
        attributes1.put("Weight", "78");

        Map<String, String> attributes2 = new HashMap<>();
        attributes2.put("Height", "180");
        attributes2.put("Weight", "100");
        attributes2.put("Hair Color", "Red");
        List<Address> addressList3 = Arrays.asList(new AddressBuilder().setCity("Delhi").build(),
                new AddressBuilder().setCity("New York").build());

        return Arrays.asList(new PersonBuilder().setAge(37).setName("Sam Adams").setAttributes(attributes1).build(),
                new PersonBuilder().setAge(33).setName("Jolly Adams").setAttributes(attributes2).setAddresses(addressList3).build());
    }

    private static class PersonBuilder {
        private String name;
        private int age;
        private List<Address> addresses;
        private Map<String, String> attributes;

        public PersonBuilder setAttributes(Map<String, String> attributes) {
            this.attributes = attributes;
            return this;
        }

        public PersonBuilder setAddresses(List<Address> addresses) {
            this.addresses = addresses;
            return this;
        }

        public PersonBuilder setName(String name) {
            this.name = name;
            return this;
        }

        public PersonBuilder setAge(int age) {
            this.age = age;
            return this;
        }

        public Person build() {
            Person person = new Person();
            person.setAge(age);
            person.setName(name);
            person.setAddresses(addresses);
            person.setAttributes(attributes);
            return person;
        }

    }

    public static class AddressBuilder {
        private String city;

        public AddressBuilder setCity(String city) {
            this.city = city;
            return this;
        }

        public Address build() {
            Address address = new Address();
            address.setCity(city);
            return address;
        }

    }

    public static class Address {
        private String city;

        @Identifier
        public String getCity() {
            return city;
        }

        public void setCity(String city) {
            this.city = city;
        }

        @Override
        public String toString() {
            return "Address{" + "city=" + city + '}';
        }

        @Override
        public boolean equals(Object o) {
            if (this == o)
                return true;
            if (o == null || getClass() != o.getClass())
                return false;

            Address address = (Address) o;

            return city != null ? city.equals(address.city) : address.city == null;
        }

        @Override
        public int hashCode() {
            return city != null ? city.hashCode() : 0;
        }
    }

    @Description(userFriendlyDescription = "Demographics")
    public static class Person {
        private String name;
        private int age;
        private List<Address> addresses;
        private Map<String, String> attributes;

        public Map<String, String> getAttributes() {
            return attributes;
        }

        public void setAttributes(Map<String, String> attributes) {
            this.attributes = attributes;
        }

        public List<Address> getAddresses() {
            return addresses;
        }

        public void setAddresses(List<Address> addresses) {
            this.addresses = addresses;
        }

        @Identifier
        @Description(userFriendlyDescription = "Short Name")
        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }

        public int getAge() {
            return age;
        }

        public void setAge(int age) {
            this.age = age;
        }

        @Override
        public boolean equals(Object o) {
            if (this == o)
                return true;
            if (o == null || getClass() != o.getClass())
                return false;

            Person person = (Person) o;

            if (age != person.age)
                return false;
            if (name != null ? !name.equals(person.name) : person.name != null)
                return false;
            return addresses != null ? addresses.equals(person.addresses) : person.addresses == null;
        }

        @Override
        public int hashCode() {
            int result = name != null ? name.hashCode() : 0;
            result = 31 * result + age;
            result = 31 * result + (addresses != null ? addresses.hashCode() : 0);
            return result;
        }

        @Override
        public String toString() {
            return "Person{" + "name=" + name + '}';
        }

    }
}
