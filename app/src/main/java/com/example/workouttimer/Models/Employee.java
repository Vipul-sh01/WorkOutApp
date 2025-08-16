package com.example.workouttimer.Models;

public class Employee {
    private int id;
    private String name;
    private String company;
    private String username;
    private String email;
    private String address;
    private String zip;
    private String state;
    private String country;
    private String phone;
    private String photo;

    public Employee() {}

    public Employee(int id, String name, String company, String username, String email,
                    String address, String zip, String state, String country,
                    String phone, String photo) {
        this.id = id;
        this.name = name;
        this.company = company;
        this.username = username;
        this.email = email;
        this.address = address;
        this.zip = zip;
        this.state = state;
        this.country = country;
        this.phone = phone;
        this.photo = photo;
    }

    public int getId() { return id; }
    public String getName() { return name; }
    public String getCompany() { return company; }
    public String getUsername() { return username; }
    public String getEmail() { return email; }
    public String getAddress() { return address; }
    public String getZip() { return zip; }
    public String getState() { return state; }
    public String getCountry() { return country; }
    public String getPhone() { return phone; }
    public String getPhoto() { return photo; }

    public void setId(int id) { this.id = id; }
    public void setName(String name) { this.name = name; }
    public void setCompany(String company) { this.company = company; }
    public void setUsername(String username) { this.username = username; }
    public void setEmail(String email) { this.email = email; }
    public void setAddress(String address) { this.address = address; }
    public void setZip(String zip) { this.zip = zip; }
    public void setState(String state) { this.state = state; }
    public void setCountry(String country) { this.country = country; }
    public void setPhone(String phone) { this.phone = phone; }
    public void setPhoto(String photo) { this.photo = photo; }

    @Override
    public String toString() {
        return "Employee{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", company='" + company + '\'' +
                ", username='" + username + '\'' +
                ", email='" + email + '\'' +
                ", address='" + address + '\'' +
                ", zip='" + zip + '\'' +
                ", state='" + state + '\'' +
                ", country='" + country + '\'' +
                ", phone='" + phone + '\'' +
                ", photo='" + photo + '\'' +
                '}';
    }
}
