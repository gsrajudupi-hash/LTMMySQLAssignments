package assignment3.model;
import java.util.Objects;
public class Customer {
 private final int customerId; private String name; private String email; private String city; private String phone; private String customerType;
 public Customer(int customerId,String name,String email,String city,String phone,String customerType){
  if(customerId<=0) throw new IllegalArgumentException("Customer ID must be positive");
  setName(name); setEmail(email); setCity(city); setPhone(phone); setCustomerType(customerType); this.customerId=customerId;
 }
 public int getCustomerId(){return customerId;} public String getName(){return name;} public String getEmail(){return email;}
 public String getCity(){return city;} public String getPhone(){return phone;} public String getCustomerType(){return customerType;}
 public void setName(String v){name=require(v,"name");} public void setEmail(String v){email=require(v,"email");}
 public void setCity(String v){city=require(v,"city");} public void setPhone(String v){phone=require(v,"phone");}
 public void setCustomerType(String v){v=require(v,"customer type").toUpperCase(); if(!v.equals("PREMIUM")&&!v.equals("REGULAR")) throw new IllegalArgumentException("Type must be PREMIUM or REGULAR"); customerType=v;}
 private static String require(String v,String n){if(v==null||v.isBlank()) throw new IllegalArgumentException(n+" is required"); return v.trim();}
 @Override public boolean equals(Object o){return this==o||(o instanceof Customer c&&customerId==c.customerId);} @Override public int hashCode(){return Objects.hash(customerId);}
 @Override public String toString(){return "Customer{"+customerId+", '"+name+"', "+city+", "+customerType+"}";}
}
