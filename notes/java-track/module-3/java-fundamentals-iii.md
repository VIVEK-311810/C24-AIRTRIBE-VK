# Java Fundamentals - III

- Track: Backend - Java Track  |  Module: Module 3
- Speaker: Aashray
- Status: PAST
- Recording length: 105 min

## Session notes (AI summary from Airtribe)

# Session Overview

These notes cover the JVM’s class-loading process, class-loader roles, and the difference between loading classes and resolving dependencies.  
They also explain JVM memory areas, garbage collection, bytecode execution through interpretation and JIT compilation, and a Java example of integer overflow.

## JVM architecture and class loading

The JVM has three broad components:

- The **class loader** makes class definitions available to the JVM.
- **Runtime memory areas** hold class information, objects, and method-execution state.
- The **execution engine** runs the program’s bytecode.

A **class** must be available to the JVM before the JVM can use its definition and bytecode. But that does not mean every class in a project or library must be loaded when the program starts. Classes are generally loaded as they are needed.

> The JVM loads the definitions required by the program—not every Java class or library available.

Suppose application class `A` uses class `B`, and `B` uses class `C`. As execution reaches code that needs those classes, their definitions may need to be loaded too. The JVM does not need to load an unrelated library just because it is present in the project.

**Key takeaway:** Class loading makes a class definition available; it does not create an object or require loading every class up front.

## Class loaders and dependencies

A **class loader** locates class definitions and loads them into the JVM. The familiar loader roles are:

- **Bootstrap class loader:** loads core Java classes, such as `java.lang.String`.
- **Platform class loader:** loads platform classes. In Java 8 and earlier, the corresponding role was called the **extension class loader**.
- **Application class loader:** loads application classes and commonly loads third-party libraries available on the application’s classpath or module path.

Java class loaders generally follow **parent-first delegation**: a loader checks its parent loaders before trying to define a class itself. This helps ensure that core Java classes come from the appropriate loader. It does not mean the JVM loads all bootstrap classes first, then all platform classes, then all application classes; loaders handle requests for particular classes.

Third-party libraries are not automatically handled by a special extension loader in modern Java. Their loader depends on how the application is configured.

### How a library becomes available

An `import` statement lets source code refer to a type by its name. It does **not** download a library, connect to a remote repository, or itself cause a class to load at runtime.

A build tool such as Maven uses the project configuration—often `pom.xml`—to resolve dependencies. Libraries are commonly distributed as JAR files, which can be downloaded from a repository and cached locally. The application can then use them through its runtime classpath or module path; packaging may also include them in the deployable application. The version comes from the build configuration and dependency-management rules; a build tool does not universally choose the latest version.

At runtime, compiled class files contain references to the types they use. The JVM requests those classes through the class-loading mechanism as they are needed. It does not need to discover runtime dependencies by scanning source-level imports.

**Key takeaway:** Imports name types in source code; dependency management makes library code available to the build and runtime.

## Loading, linking, and initialization

A class’s lifecycle is commonly described in phases:

1. **Loading** brings the class definition into the JVM.
2. **Linking** prepares the class for use and includes verification, preparation, and resolution.
3. **Initialization** runs the class’s static initialization code.

### Verification

During **verification**, the JVM checks that the loaded class file is structurally valid and that its bytecode follows JVM rules—for example, that instructions and types are used consistently.

Verification is not a check that a program is harmless or has no malicious intent. Whether code can access particular resources is a separate security concern.

### Preparation

During **preparation**, the JVM allocates storage for a class’s static fields and assigns their default values. An ordinary static field’s explicit initializer runs later, during initialization.

```java
static int XYZ = 4;
```

During preparation, storage is created and `XYZ` has the default value `0`. The assignment of `4` happens during initialization. Compile-time constants are a special case.

**Instance fields** belong to individual objects, so their storage is associated with each object rather than allocated as part of class preparation.

### Resolution

Class files contain **symbolic references** to other classes, fields, and methods. During **resolution**, the JVM turns those references into forms it can use at runtime. For example, a reference in class `A` to class `B` can be resolved to the runtime representation of `B`.

Resolution does not necessarily mean writing a physical memory address into the class definition. The JVM’s representation is implementation-dependent, and resolution may happen lazily or earlier.

### Initialization

During **initialization**, static field initializers and static initializer blocks execute. A static method is different: it does not run automatically just because its class is initialized.

A class’s `main` method is the entry point only when that class is selected as the program’s launch target. If multiple classes define `main`, choosing one does not make the others’ methods entry points.

**Key takeaway:** Preparation gives static fields default values; initialization applies their explicit values and runs static blocks.

## JVM memory areas

The main memory areas introduced were the **method area**, **heap**, and **stack**.

| Area | Main role |
|---|---|
| Method area | Logical area associated with class-level information, such as class structures and method information. |
| Heap | Holds objects allocated at runtime. |
| Stack | Tracks a thread’s active method calls, execution state, and local variables. |

The **method area** is a logical area defined by the JVM specification; the physical placement of class metadata and static fields depends on the JVM implementation. It is therefore too strong to assume that static fields always occupy one particular physical location.

The **heap** is where objects are generally allocated. If a method creates an object, a local variable may hold a reference to it in a stack frame while the object itself is on the heap.

Each thread has its own **stack**. When one method calls another, a new **stack frame** tracks that call. When the called method finishes, its frame is removed and execution returns to the caller.

For example, if `A` calls `B`, and `B` calls `C`, the active calls are represented by frames for `A`, `B`, and `C`. When `C` finishes, execution resumes in `B`; when `B` finishes, execution resumes in `A`. Recursion also creates a sequence of active calls that the stack tracks.

**Key takeaway:** Objects generally live on the heap; each thread’s stack tracks its active method calls and local execution state.

## Static and instance data; garbage collection

An **instance variable** belongs to an individual object. A **static variable** belongs to the class and is shared across its instances.

For example, if each `Student` has a `name`, that should vary per object. If all students share a class-wide limit such as `maxStudents`, a static field can represent that shared value rather than storing a copy on every object.

Use a static field when the value belongs to the class as a whole—not simply because it seems useful to share something.

Objects may remain on the heap after the code that created them has finished. The JVM’s **garbage collector (GC)** can reclaim heap memory occupied by objects that are no longer reachable by the application.

> The important question for garbage collection is whether an object is still reachable—not simply how old it is or whether a request has ended.

For example, a web application may create registration objects while handling requests. Once an object is no longer reachable, the JVM can reclaim its memory for later allocations. Class metadata can also be reclaimed if a class is unloaded, so it is not accurate to say that garbage collection can never affect class-related memory.

A static initializer can perform one-time setup when its class is initialized, such as loading fixed configuration into memory. This can avoid repeating setup for every request. However, class initialization typically happens before the class’s first active use; it does not mean every class in an application is initialized before the program starts.

**Key takeaway:** Static fields hold class-wide data; instance fields belong to objects, and GC can reclaim objects that are no longer reachable.

## Interpreting and JIT-compiling bytecode

A processor executes **native machine code**, not Java bytecode directly. The JVM provides the runtime that executes Java bytecode, using interpretation, compilation, or a combination of both.

An **interpreter** executes bytecode instructions as the program runs. This lets execution begin without first compiling the whole program to native code, but frequently executed code can keep incurring interpretation overhead.

A **just-in-time (JIT) compiler** identifies frequently executed code, or a **hot path**, and compiles it to native machine code. The JVM can then reuse the compiled code instead of repeatedly interpreting the same bytecode. Compilation can improve performance, but it takes time and uses memory to store compiled code.

The JVM gathers runtime information and can use it to help decide which code to compile. Profiling tools can also report information such as method execution counts, CPU usage, and memory use.

> Interpretation can help code start quickly; JIT compilation can speed up code that runs frequently.

**Key takeaway:** The JVM can interpret code initially and compile hot paths later, balancing startup, execution speed, and memory costs.

## Java narrowing conversions and overflow

A **narrowing conversion** stores a value in a type with a smaller representable range. If the value does not fit, information can be lost and the result can change.

In Java, arithmetic on `byte` values produces an `int`. Assigning the result to a `byte` therefore requires an explicit cast:

```java
byte result = (byte) (127 + 1);
```

The cast allows the narrowing conversion; because `128` is outside the signed 8-bit `byte` range, the result is `-128`.

For signed 8-bit values, the range is **−128 to 127**. You can picture the representable values as a ring: stepping past one end wraps to the other.

- `127 + 1` narrows to `-128`.
- `127 + 2` narrows to `-127`.
- `-128 - 1` narrows to `127`.

Without the cast, Java rejects assigning the `int` result directly to a `byte`. Other languages may have different rules.

**Key takeaway:** In Java, byte arithmetic produces an `int`; an explicit narrowing cast can wrap a value that lies outside the byte range.

## Quick reference

- **Load classes on demand:** A class must be available before use, but unused classes do not need to load.
- **Loader roles:** Bootstrap → Platform (Extension in older Java) → Application; delegation is generally parent-first.
- **Imports vs. dependencies:** `import` names a type; build configuration resolves libraries; runtime loading makes required classes available.
- **Lifecycle:** Loading → Linking (verification, preparation, resolution) → Initialization.
- **Preparation vs. initialization:** Preparation assigns static fields default values; initialization runs explicit static initializers and blocks.
- **Memory:** Heap holds objects; each thread has its own stack; the method area is a logical area for class-level information.
- **Garbage collection:** Unreachable objects can be reclaimed; age alone does not determine whether an object is collectible.
- **Execution:** Interpreter runs bytecode; JIT compiles frequently executed code to native machine code.
- **Java `byte`:** Arithmetic promotes to `int`; narrowing back to `byte` requires a cast and can change the value.

## Timeline

- 00:00:00 JVM architecture overview
- 00:01:36 Class loading and required dependencies
- 00:07:11 Class loaders, libraries, and class categories
- 00:15:19 Class-loader delegation and dependency loading
- 00:30:25 Platform loader, class-loading timing, and IDE tooling
- 00:35:01 Course notes and shared resources
- 00:37:00 Linking and class verification
- 00:40:08 Preparation phase and method-area memory
- 00:48:27 Resolution phase and class references
- 01:01:58 Why static variables are useful
- 01:08:27 Initialization phase and startup recap
- 01:15:46 JVM memory areas and heap garbage collection
- 01:20:35 Stack memory, local variables, and call stacks
- 01:26:22 Execution engine and bytecode interpretation
- 01:31:14 JIT compilation and hot-path optimization
- 01:37:38 Session recap and next class
- 01:38:33 Integer overflow and narrowing conversions
- 01:43:29 Upcoming OOP topics and wrap-up

## Attachment: Week 2 Session 1 and Session 2 - BEL.pptx.pdf (SESSION_DECK)
Saved: `week-2-session-1-and-session-2-bel-pptx.pdf`

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
