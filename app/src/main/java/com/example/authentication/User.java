package com.example.authentication;

public class User {
     String fullName,age,email;
    public  User(){}
    public User(String fullName,String age,String email){
        this.fullName = fullName;
        this.age = age;
        this.email = email;
    }


    public String getFullname(){
        return fullName;
    }
//    public String getFullName() {
//        return fullName;
//    }

    public String getAge() {
        return age;
    }

    public String getEmail() {
        return email;
    }

//    public void setFullName(String fullName) {
//        this.fullName = fullName;
//    }
    public void setFullname(String fullName) {
        this.fullName = fullName;
    }

    public void setAge(String age) {
        this.age = age;
    }

    public void setEmail(String email) {
        this.email = email;
    }
}
