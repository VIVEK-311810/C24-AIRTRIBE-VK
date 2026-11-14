# OOPS -II

- Track: Backend - Java Track  |  Module: Module 4
- Speaker: Aashray
- Status: UPCOMING

_No AI summary available for this session yet (recording not processed or not uploaded)._

## Attachment: C22- oops.pdf (SESSION_DECK)
Saved: `c22-oops.pdf`

### Deck text

```
Week- 1

Backend Engineering
    Launchpad
      Aashray Agarwal
  Object Oriented
Programming in Java
Meet your Instructor
Aashray, a talented Software Engineer at Uber, brings
rich experience from his previous roles at Google and
Amazon. As a dedicated Backend Engineer, he holds a
strong interest in Distributed Systems, System Design,
Data   Structures,   Algorithms,   and   Competitive
Programming. Proﬁcient in Java, C, and C++, Aashray
combines technical excellence with a collaborative
and disciplined approach, making him a valuable               Aashray Agarwal
mentor for aspiring engineers.
                                                         Software engineer Uber
Agenda

  Classes And Objects   Inheritance
  Packages in Java      Abstraction
  OOPS pillars          Polymorphism
  Encapsulation         Live Coding
  QA
Introduction to Classes and Objects

  Class:

   ●   Blueprint for creating objects
   ●   Defines properties (fields) and
       behaviors (methods)

  Object:

   ●   Instance of a class
   ●   Holds state (fields) and
       behavior (methods)
Understanding Classes And
Objects
Constructors
A constructor is a special method used to initialize objects in Java. It is called when
an object of a class is created. Constructors are essential because they set the
initial state of an object by assigning values to its attributes. Unlike regular methods,
constructors do not have a return type, not even void.
Types of constructors:
 ●   Default constructor
 ●   No argument constructor
 ●   Parameterized constructor
 ●   All Argument constructor
 ●   Copy constructor
Class Deﬁnition
                   Class Name


                  Instance variables


                     Local variables

                      Constructor

                     Class body


                     Instance method
Object creation



                                     Object creation by
                                     invoking constructor




                  Invoking instance method on
                  myCar object
Variable Scope
Local Variables
  Definition: Variables declared within a method, constructor, or block.

  Scope: Accessible only within the method/block where they are declared.

  Lifetime: Exist only during the execution of the method/block. They are

  destroyed once the method exits.

  Default Value: No default value (must be initialized before use).
Instance Variables
  Definition: Variables declared inside a class but outside any method,

  constructor, or block.

  Scope: Each instance (object) of the class has its own copy. Accessible by all

  methods of the class.

  Lifetime: Exist as long as the object (instance) exists. When the object is

  destroyed (garbage collected), instance variables are destroyed.

  Default Value: Have default values (e.g., 0 for numbers, null for objects).
Class variables
  Definition: Declared with the keyword static inside a class but outside any

  method/block.

  Scope: Shared among all instances of the class (single copy per class).

  Lifetime: Exists for the entire lifetime of the class (till the program ends or

  class is unloaded).

  Default Value: Have default values similar to instance variables.
Packages in
java
  In Java, a package is a container for a collection of classes, sub-packages,
  and interfaces. Packages are used for the following purposes:


  Keeping name problems at bay. For example, in two packages,
  college.staff.cse.Employee and college.staff.ee.Employee, there could be two
  classes named Employee.
   ●   Making it easier to search for, locate, and use classes, interfaces,
       enumerations, and annotations
Packages in java

  Controlling access: package level access control is available in both protected
  and default modes. Classes in the same package and its subclasses can
  access a protected member. Only classes in the same package have access
  to a default member (which has no access specifier).

  Packages help in data encapsulation (or data-hiding).

  Subpackages - Subpackages are packages that are contained within another
  package. These are not automatically imported; they must be imported
  manually. Members of a subpackage also have no access privileges, therefore
  they are treated differently by protected and default access specifiers.
                    Packages in
                    java

Package Benefits:

Namespace Management: Avoids naming conflicts.

Access Protection: Controls access to classes and interfaces.

Code Organization: Organizes related classes and interfaces.
Packages in java
In Built Packages In Java
Object Oriented
Programming
  A programming paradigm based on the concept of "objects", which can
  contain data and methods.

  Emphasizes modularity, reusability, and flexibility.

  Objects are instances of classes, which define the properties and
  behaviors.

  Pillars of Object Oriented Programming

   ●   Encapsulation
   ●   Inheritance
   ●   Polymorphism
   ●   Abstraction
Object Oriented
Programming
Encapsulation
  Bundling of data (variables) and methods (functions) that operate on the
  data into a single unit called a class.

  Protects the internal state of an object from unintended modifications.

  Access to the data is restricted through public methods (getters and
  setters).

  Ensures data integrity and security.

  Increases code maintainability and flexibility.
Access Modiﬁers

  Access modifiers in Java determine the visibility and accessibility of
  classes, methods, and variables. They help implement encapsulation and
  control the exposure of class members to other parts of the program.
  Java provides four access modifiers: public, protected, default
  (package-private), and private.

  The public access modifier makes the class, method, or variable
  accessible from any other class.

  The protected access modifier makes the method or variable accessible
  within the same package and by subclasses in different packages.
Access Modiﬁers

  When no access modifier is specified, Java uses the default access level
  (package-private), making the method or variable accessible only within
  the same package.

  The private access modifier makes the method or variable accessible only
  within the class it is declared in.
Access
Modiﬁers
Access Modiﬁers
  Scope of Access Modifiers:

  Public: Accessible from any other class.

  Protected: Accessible within the same package and by subclasses
  in other packages.

  Default Package-private): Accessible only within the same
  package.

  Private: Accessible only within the class it is declared in.
Visibility of access Modiﬁers
              Visibility               Private        Default    Protected     Public


Same Class                       YES             YES            YES          YES

Same Package non-Subclass        NO              YES            YES          YES

Same Package Subclass            NO              YES            YES          YES

Different Package Non-Subclass   NO              NO             NO           YES

Different Package Subclass       NO              NO             YES          YES
Inheritance
  Mechanism where a new class (derived class) inherits properties and
  behaviors from an existing class (base class).

  Promotes code reuse and the creation of hierarchical relationships
  between classes.

  Allows the derived class to override or

  extend the functionalities of the base class.

  Supports polymorphic behavior.

  Reduces code redundancy and enhances code organization
Types Of Inheritance
Single Inheritance
Multilevel Inheritance
Hybrid Inheritance
Diamond Problem - Multiple Inheritance
  The diamond problem occurs in multiple inheritance when two classes
  inherit from a common superclass and a third class inherits from these two
  classes, leading to ambiguity. Java avoids this problem by not supporting
  multiple inheritance with classes.

  Java does not allow a class to inherit from more than one superclass. This
  restriction prevents the diamond problem, which can occur when there is
  ambiguity in the inheritance hierarchy. If multiple inheritance were
  allowed, a class could inherit the same method from multiple
  superclasses, causing confusion about which method to execute.

     A
    /\
    B C
    \/
     D
This and super keyword
  The this keyword in Java is a reference to the current object. It is used to
  eliminate confusion between class attributes and parameters with the
  same name, to call other constructors in the same class, and to pass the
  current object as a parameter to other methods.

  The super keyword in Java is a reference to the parent class object. It is
  used to call the superclass's methods and constructors, and to access the
  superclass variables.
This and super keyword
  Uses of this keyword

      Referencing Instance Variables
       ○ When parameter names are the same as instance variable
            names, this is used to differentiate between the instance
            variable and the parameter.
      Calling Another Constructor
       ○ this() can be used to call another constructor in the same class.
            This is known as constructor chaining within the same class.
      Passing Current Object as a Parameter
       ○ this can be used to pass the current object as a parameter to
            another method.
This and super keyword
  Uses of super keyword

      Calling Superclass Methods
       ○ super is used to call a method from the superclass that has
            been overridden in the subclass.
      Accessing Superclass Variables
       ○ super is used to access superclass variables when they are
            shadowed by subclass variables.
      Calling Superclass Constructor
       ○ super() is used to call the superclass's constructor. This is
            useful for initializing the superclass part of the subclass object.
It’s time for the QUIZ
Abstraction
  Hiding the complex implementation details and showing only the essential
  features of the object.

  Focuses on what an object does rather than how it does it.

  Abstract classes and interfaces

  are used to achieve abstraction.

  Simplifies complex systems by breaking

  them into smaller, manageable units.

  Enhances code readability and reduces complexity.
Abstraction using abstract class

  An abstract class is a class that cannot be instantiated on its own and is
  meant to be subclassed. It can have abstract methods (methods without a
  body) and concrete methods (methods with a body). Abstract classes are
  used to define a common interface for subclasses.

  Characteristics:

   ●   Can have both abstract and concrete methods.
   ●   Can have member variables.
   ●   Can have constructors.
   ●   Can implement methods from an interface.
   ●   Cannot be instantiated.
Abstraction using Interface

  An interface in Java is a reference type, similar to a class, that can contain only
  constants, method signatures, default methods, static methods, and nested types.
  Interfaces cannot contain instance fields or constructors. All methods in an
  interface are abstract by default (except default and static methods).

  Characteristics:

   ●   Can only have abstract methods (until Java 7.
   ●   From Java 8, can have default and static methods.
   ●   From Java 9, can have private methods.
   ●   Cannot have constructors.
   ●   Cannot have instance variables, only static and final variables.
   ●   A class can implement multiple interfaces.
Abstract class vs interface
Interface versus Concrete class
     Feature                   Interface                       Concrete Class




Instantiation    Cannot be instantiated                  Can be instantiated




Methods         Can have abstract, default, and static   Can have concrete methods,
                methods                                  static methods




Variables       Can only have static and final       
```
