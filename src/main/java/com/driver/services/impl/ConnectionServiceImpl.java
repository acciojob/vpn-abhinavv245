package com.driver.services.impl;

import com.driver.model.*;
import com.driver.repository.ConnectionRepository;
import com.driver.repository.ServiceProviderRepository;
import com.driver.repository.UserRepository;
import com.driver.services.ConnectionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ConnectionServiceImpl implements ConnectionService {
    @Autowired
    UserRepository userRepository2;
    @Autowired
    ServiceProviderRepository serviceProviderRepository2;
    @Autowired
    ConnectionRepository connectionRepository2;

    @Override
    public User connect(int userId, String countryName) throws Exception{
             User user=userRepository2.findById(userId).get();

             //if already connected to some VPN
             if(user.getMaskedIp()!=null) throw new Exception("User is already connected");

             //if trying to connect in the same country, return the user
             else if(countryName.equalsIgnoreCase(user.getOriginalCountry().getCountryName().toString())) return user;

             else{
                 //if no service provider available
                 if(user.getServiceProviderList()==null) throw  new Exception("Can't connect right now");


                 List<ServiceProvider> serviceProviderList=user.getServiceProviderList();

                 int id=Integer.MAX_VALUE;
                 ServiceProvider serviceProvider=null;
                 Country currCountry=null;
             for(ServiceProvider serviceProvider1:serviceProviderList) {

                 List<Country> countryList = serviceProvider1.getCountryList();
                 for (Country country : countryList) {
                     if (countryName.equalsIgnoreCase(country.getCountryName().toString()) && id < serviceProvider1.getId())
                         id = serviceProvider1.getId();
                     serviceProvider = serviceProvider1;
                     currCountry = country;
                 }
             }
                 if (serviceProvider != null) {
                     Connection connection = new Connection(user, serviceProvider);

                     String countryCode = currCountry.getCode();
                     int givenId = serviceProvider.getId();
                     String mask = countryCode + "." + givenId + "." + userId;

                     user.setMaskedIp(mask);


                     user.setConnected(true);
                     List<Connection> connectionList = serviceProvider.getConnectionList();
                     connectionList.add(connection);

                     user.getConnectionList().add(connection);

                     userRepository2.save(user);
                     serviceProviderRepository2.save(serviceProvider);
                 }
             }
             return user;

    }
    @Override
    public User disconnect(int userId) throws Exception {
     User user= userRepository2.findById(userId).get();
    if(user.getConnected()==false) throw new Exception("User not connected");
    user.setMaskedIp(null);
    user.setConnected(false);
    userRepository2.save(user);
     return user;
    }
    @Override
    public User communicate(int senderId, int receiverId) throws Exception {
     User user1=userRepository2.findById(senderId).get();
     User user2=userRepository2.findById(receiverId).get();
     return user1;
    }
}
