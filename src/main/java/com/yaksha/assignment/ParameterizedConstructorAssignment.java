package com.yaksha.assignment;

class Person {
 String name;

 // Parameterized constructor that accepts a name argument
 public Person(String name) {
     this.name = name;  // Initializing the name with the passed argument
 }
}

public class ParameterizedConstructorAssignment {
 public static void main(String[] args) {
     // Task 2: Use Parameterized Constructor in the Main Method
     
     Person person = new Person("Alice");  // Creating an object of Person using the parameterized constructor
     System.out.println("Person's name: " + person.name);  // Printing the initialized name
 }
}
