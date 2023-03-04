package com.driver.model;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "user")
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;


    private String username;

    private String password;

    private String originalIP;


    private String maskedIp;

    private boolean connected;

    @OneToMany(mappedBy = "user",cascade = CascadeType.ALL)
    List<Connection> connectionList= new ArrayList<>();

    @ManyToMany
    @JoinColumn
    List<ServiceProvider> serviceProviderList= new ArrayList<>();

    @OneToOne(mappedBy = "user",cascade = CascadeType.ALL)
    private Country originalCountry;

    public User() {
    }

    public User(int id, String username, String password, String originalIP, String maskedIp, boolean connected, List<Connection> connectionList, List<ServiceProvider> serviceProviderList, Country country) {
        this.id = id;
        this.username = username;
        this.password = password;
        this.originalIP = originalIP;
        this.maskedIp = maskedIp;
        this.connected = connected;
        this.connectionList = connectionList;
        this.serviceProviderList = serviceProviderList;
        this.originalCountry = country;
    }



    public User(String username, String password) {
        this.username=username;
        this.password=password;
    }


    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getOriginalIP() {
        return originalIP;
    }

    public void setOriginalIP(String originalIP) {
        this.originalIP = originalIP;
    }

    public String getMaskedIp() {
        return maskedIp;
    }

    public void setMaskedIp(String maskedIp) {
        this.maskedIp = maskedIp;
    }

    public boolean getConnected() {
        return connected;
    }

    public void setConnected(boolean connected) {
        this.connected = connected;
    }

    public List<Connection> getConnectionList() {
        return connectionList;
    }

    public void setConnectionList(List<Connection> connectionList) {
        this.connectionList = connectionList;
    }

    public List<ServiceProvider> getServiceProviderList() {
        return serviceProviderList;
    }

    public void setServiceProviderList(List<ServiceProvider> serviceProviderList) {
        this.serviceProviderList = serviceProviderList;
    }

    public Country getOriginalCountry() {
        return originalCountry;
    }

    public void setOriginalCountry(Country originalCountry) {
        this.originalCountry = originalCountry;
    }
}
