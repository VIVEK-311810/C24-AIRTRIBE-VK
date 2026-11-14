# Fundamentals of Java - I

- Track: Backend - Java Track  |  Module: Module 2
- Speaker: Aashray
- Status: PAST
- Recording length: 125 min

## Session notes (AI summary from Airtribe)

# Session Overview

The notes introduce the difference between high-level and low-level design, then build a foundation in Java classes, objects, fields, methods, and data types.  
They also cover variable scope, memory and references, persistence, and how these fundamentals fit into the course’s broader study of design principles and patterns.

## High-level and low-level design

**High-level design (HLD)** is about choosing the overall architecture for a feature or system. **Low-level design (LLD)** turns that design into code. Working code is not the only goal: good LLD also considers whether the code is clean, scalable, extensible, and guided by sound design practices.

> LLD is about writing code that is not only functional, but also clean and adaptable.

**Key takeaway:** HLD establishes the overall structure; LLD translates that structure into maintainable code.

## Classes, objects, and modeling a problem

A **class** defines the structure and behavior that objects of a particular kind can have. An **object** is an individual instance of that class, with its own values.

> A class is the blueprint; an object is a particular instance built from it.

For example, a `Movie` class might define fields such as `title`, `duration`, and `genre`. Two movie objects use the same structure but hold different values:

- Movie 1: title `T1`, duration `100`, genre `comedy`
- Movie 2: title `T2`, duration `110`, genre `...`

The class describes which fields are available. If the class does not define a `description` field, an object cannot use that field in this fixed-schema model until the class definition is changed. If the field exists but one movie has no description, that object’s value can be left unset; for a Java reference field, its default value is `null`.

### Walk through the problem before designing classes

A movie-booking app provides a way to identify the information and concepts a program might need to represent:

1. A user opens the app and sees movies.
2. They choose a city and a movie.
3. They see theaters in that city showing the movie.
4. They choose a theater and view its showtimes.
5. They choose a show and see available and booked seats.
6. They select seats and proceed to payment.

This is a starting point for understanding the problem and its objects—not a complete class model.

**Key insight:** Begin by understanding the objects and interactions in the problem. The initial LLD discussion focuses on in-memory representation, leaving database schema design for later.

**Key takeaway:** A class defines the structure available to its objects; each object holds values for one particular entity.

## Data types and fields

A **data type** determines what kind of value a field can hold. For example, a movie might have a text `title`, an integer `duration`, and a list of genres. A field’s type constrains the values that can be stored there.

Two broad categories were introduced:

- **Primitive types**, such as integers, floating-point numbers, characters, and booleans.
- **Reference types**, such as `String`, arrays, and class types, which refer to objects.

A `Map` is another kind of structure: it stores key–value pairs. A class defines named fields such as `title` and `duration`; a map associates keys with values. The appropriate structure depends on how the data is organized.

### Choosing capacity by considering the range

Computers represent data using **bits**, each of which stores `0` or `1`. The number of bits available limits the values that can be represented. For example, four bits can represent the unsigned values `0` through `15`:

```text
Bit position:  3   2   1   0
Value:         1   1   1   1
Contribution:  8 + 4 + 2 + 1 = 15
```

This is why it helps to think about a value’s possible range. A movie’s duration is not known exactly in advance, but it may be reasonable to expect it to fall within a bounded range, such as 0 to 1,000 minutes. Text such as a title or description can vary in length, so it presents a different storage problem.

The primitive integer sizes introduced were:

| Java type | Size |
|---|---:|
| `byte` | 1 byte = 8 bits |
| `short` | 2 bytes = 16 bits |
| `int` | 4 bytes = 32 bits |
| `long` | 8 bytes = 64 bits |

A Java `int` is a 32-bit signed integer, so it cannot represent arbitrarily large values. `float` and `double` represent decimal-style numbers with limited precision; `double` generally offers greater precision and range than `float`, but neither represents every possible decimal value exactly. A `char` represents one character, while a `boolean` represents `true` or `false`.

**Watch out:** A primitive type’s range and precision are limited. Do not assume a numeric type can hold any number or represent every decimal exactly.

**Key takeaway:** Choose a type that fits the kind of value and the range or precision it needs.

## Methods and behavior

A **method** is a block of code that performs an operation or contains business logic. A class can group methods with the data they use.

A method is described by its:

- **Return type** — the kind of result it produces
- **Name** — what it is called
- **Parameters** — the inputs supplied to it; a method may have none or several

For example, a movie-related method might find the “top joiners” for a movie. In another example, a movie’s `topGenres` method could return the first two genres in its list. The choice to return two, three, or more genres is part of the method’s business logic and could change over time.

A car class might combine data such as color, engine, and mileage with methods such as start, stop, and apply brakes.

**Key takeaway:** Classes group data with the operations that work on that data; methods express those operations.

## Variable kinds and scope

Java variables differ by **where they are declared** and **what they belong to**.

| Variable kind | Belongs to | What that means |
|---|---|---|
| Local variable | A method or block | Usable only within its scope |
| Instance variable | One object | Each object has its own value |
| Static variable | The class | Shared at the class level |

For example:

```java
class VariableScopeDemo {
    static int totalStudents;
    String studentName;
    int xp;
}
```

Here, `totalStudents` is static; `studentName` and `xp` are instance variables. If two student objects represent Alice and Bob, their names and XP values can differ. But both objects share the class’s `totalStudents` value. You can access a static field through the class name:

```java
VariableScopeDemo.totalStudents
```

A local variable, by contrast, is confined to the method or block where it is declared. Code outside that scope cannot use its name. In Java, local variables must be assigned before they are read. Fields and array elements receive default values: numeric primitives default to zero, `boolean` to `false`, and reference types to `null`.

> A static variable is a class-wide shared value; an instance variable belongs to one particular object.

**Watch out:** Scope tells you where a variable’s name can be used. It does not, by itself, tell you exactly when the memory for an object referenced by that variable is reclaimed.

**Key takeaway:** Use instance fields for per-object state, static fields for class-level shared state, and local variables for values needed only within a method or block.

## Memory, references, and persistence

When an object is created, it occupies memory. An object’s **address** identifies a location; it is not the object’s contents. In Java, you work with references to objects rather than directly manipulating literal memory addresses.

Primitive values and references are different:

- A primitive field holds a value of its primitive type.
- A reference field refers to an object, such as a `String`.

A title can vary in length, so a useful conceptual picture is that the field holds a reference to the string object:

```text
title  ──>  "ABCDE"
```

If the value assigned to `title` changes, the field refers to the newly assigned string value. The exact physical layout of Java objects and their fields depends on the runtime; the diagram is a model for understanding the distinction between a reference and the data it refers to.

### Addresses and 32-bit versus 64-bit systems

An address is itself represented as a value. A 32-bit address can represent up to \(2^{32}\) distinct address values; a 64-bit address can represent up to \(2^{64}\). Assuming byte-addressable memory, those correspond to theoretical address spaces of 4 GiB and \(2^{64}\) bytes.

These are limits on the addresses that can be represented, not promises about how much physical RAM a machine has. Actual usable memory depends on hardware, the operating system, and other limits. Address width is also distinct from the size of a number being stored: a 64-bit value can be stored at a location identified by a 32-bit address.

> More address bits mean a much larger possible address space—not necessarily more physical memory or a guaranteed performance increase.

### RAM, disk, and garbage collection

**RAM** is working memory used while programs run. **Disk storage** keeps data for longer-term use, including across a shutdown and restart. An application may fetch movie information from a server and hold it in RAM while displaying it.

When a method returns, its local variables are no longer accessible from that method. If a local variable referred to an object, that object may still remain in memory if another reference points to it. When no live references to an object remain, it becomes eligible for garbage collection. Java does not guarantee that eligible objects will be collected immediately.

**Watch out:** Do not confuse memory allocation with concurrency. A program using RAM does not, by itself, mean that it is using multiple threads. Likewise, a 64-bit system is not automatically faster in every task.

### Keeping data across restarts

Objects held only in memory belong to a running program. When the process stops, those objects are lost. Applications use a database or other persistent storage for data that must survive restarts.

The initial design discussion assumes that data such as movies and theaters is available in memory. The design should still be adaptable so that data can later be retrieved from a database instead. That adaptability is part of extensibility.

**Key takeaway:** References connect code to objects in memory; persistent storage is needed when data must survive the end of a program.

## Learning path, practice, and participation

The learning path builds from Java fundamentals toward writing cleaner, more extensible code:

1. **Java basics:** data types, classes, objects, and syntax such as loops.
2. **Java internals:** compilation, execution, and memory allocation.
3. **Object-oriented programming:** concepts for structuring Java code.
4. **Design principles:** why they matter and what can go wrong when they are broken.
5. **Design patterns:** reusable approaches to recurring design problems.

A **design pattern** is a reusable approach to a common design problem. Systems often need similar capabilities—such as login, notifications, or tracking a delivery partner’s location—but patterns are starting points, not rules to apply blindly.

> Patterns help you recognize familiar problems; context determines whether a pattern is suitable.

Group practice is part of the course. Learners work in groups of roughly five or six on a problem statement, with time depending on its complexity. Groups may present their solutions and discuss possible improvements and what a strong solution could look like.

To keep discussion manageable in a large class, raise a hand before speaking rather than unmuting unexpectedly. Short questions can also go in the chat. Sessions are planned for two hours, with the main material intended to finish around 1 hour 45–50 minutes and roughly the final 10 minutes reserved for general questions.

**Key takeaway:** Build from Java fundamentals toward design principles and patterns, and use group work to practise applying them to problems.

## Quick reference

- **HLD:** Choose the system’s overall architecture. **LLD:** Turn that design into clean, adaptable code.
- **Class:** Defines available structure and behavior. **Object:** An instance with its own values.
- **Field type:** Constrains what kind of value a field can hold; consider range and precision.
- **Method:** Named block of code with a return type, name, and possible parameters.
- **Local variable:** Confined to its declaring method or block. **Instance field:** Per object. **Static field:** Shared at class level.
- **Primitive value:** Holds a primitive value. **Reference:** Refers to an object.
- **RAM:** Working memory for running programs. **Persistent storage:** Keeps data available after a process ends.
- **Garbage collection:** Reclaims objects when they are no longer reachable; collection timing is not guaranteed.
- **Design pattern:** A reusable starting point for a recurring problem; choose based on context.

## Timeline

- 00:00:00 Instructor introduction and experience
- 00:00:40 Low-level design course goals
- 00:02:19 Java foundations and course roadmap
- 00:04:03 Object-oriented programming fundamentals
- 00:04:42 Design principles
- 00:05:09 Design patterns
- 00:06:39 Breakout room activities
- 00:07:35 Class participation and session guidelines
- 00:10:24 Surveying the audience
- 00:15:00 Introducing classes and objects
- 00:19:02 Low-level design assumptions and movie schema
- 00:21:20 Classes as schemas and objects as instances
- 00:26:59 Optional fields, null values, and defaults
- 00:30:31 Changing class definitions and restarting programs
- 00:35:45 Object and field questions
- 00:38:10 Introducing Java data types
- 00:39:53 Primitive types and binary memory
- 00:54:09 Reference types and memory addresses
- 01:03:01 32-bit versus 64-bit memory capacity
- 01:12:25 How reference fields store variable-sized data
- 01:23:03 Memory allocation and type questions
- 01:26:01 Methods and business logic
- 01:30:13 Variable scopes and static variables
- 01:47:32 Local variables and scope
- 01:50:12 Reviewing primitive and reference memory
- 01:57:28 Local variable scope and garbage collection
- 02:02:48 Practice, IDEs, and course wrap-up
- 02:04:48 Closing and feedback

## Attachment: document-export-27-11-2025-22_59_02.txt (ADDITIONAL_READ)

```
# Airtibe: Introduction to Java and Backend Development - 1

## **What is Java?**
- High-level, **object-oriented** programming language.
- **Platform independent** (Write Once, Run Anywhere).
- **Compiled + Interpreted**: `.java`  → bytecode → JVM executes.
- **Strong memory management** via garbage collector.
- Used for backend, Android, enterprise apps, cloud, big data, etc.
---

- **Language Hierarchy:**
    - **C / C++**: Compile -> Machine Code (Specific to Windows/Mac).
    - **Java**: Compile -> **Bytecode** ->**JVM**.

- **Slogan:** "Write Once, Run Anywhere."
- **Context:** Before Java, if you wrote a game in C++, you had to rewrite/recompile it for every operating system.
- **The Solution:** Java adds a "middleman"—the **Java Virtual Machine (JVM)**.
- **Analogy:**
    - **C++:** Writing a letter in a specific local dialect. Only locals understand it.
    - **Java:** Writing a letter in Esperanto (Bytecode). You hire a translator (JVM) for every village to translate it to the local dialect on the fly.

---

### **The Ecosystem (JDK vs JRE)**
- **JVM (Virtual Machine):** The Engine (Runs the code).
- **JRE (Runtime Environment):** JVM + Libraries (For Users).
- **JDK (Development Kit):** JRE + Compiler (`javac` ) + Tools (For You).
# **JVM (Java Virtual Machine)**
**What it is:**

- An abstract machine that runs Java **bytecode**.
- Converts bytecode → machine code (via JIT).
- Platform-dependent implementation, but bytecode is platform-independent.
- Components:
    - Class Loader Subsystem
    - Runtime Data Areas: Heap, Stack, Method Area, etc
    - Execution Engine: Interpreter, JIT Compiler
    - Native Interface & Libraries: .dll, .so to work with OS

# **JRE (Java Runtime Environment)**
**What it is:**

- Environment required to **run** Java programs.
- Contains **JVM + libraries**, but **NOT** development tools.
- Components:
    - JVM
    - Core Libraries (Java Class Library):** **`java.lang` , `java.io` , `java.util` , `java.net` , `java.sql` , etc.
    - Supporting Files: Security files, Configuration files, Resource files

**Important:**
 JRE **cannot compile** Java programs — it can only run them.



# **JDK (Java Development Kit)**
**What it is:**

- Full development toolkit — required to **write, compile, debug, and run** Java programs.
- Contains **JRE + JVM + developer tools**.
- Components:
    - Development Tools
        - **javac** – Java compiler (source → bytecode)
        - **java** – JVM launcher
        - **javadoc** – documentation generator
        - **jdb** – debugger
        - **jar** – package tool (create JAR files)
        - **jshell** – interactive shell (Java 9+)

    - **Additional Libraries & Header Files**
        - Needed for development
        - For JNI, native code, etc.


---

- **Common Confusion:** Students often install just the JRE and wonder why `javac`  command doesn't work.
- **Hierarchy:** JDK > JRE > JVM.
**JDK = Full Car Factory + Tools + The Finished Car**

>>>>

**JRE = Engine + Fuel + All Necessary Car Parts to Move**

>>>>

**JVM = The Engine**

---

## **Major Features of Java**
- **Simple** syntax (C/C++ style).
- **Object-Oriented** (everything is class & object).
- **Portable** (runs on JVM).
- **Robust & Secure** (memory safety, exception handling).
- **Multithreaded**.
- **High Performance** (JIT compilation).
- **Distributed** (RMI, networking built-in).
- **Automatic garbage collection**.


---

## **Anatomy of a Program**
`﻿public class MyApp {
    public static void main(String args) {
        System.out.println("Hello");
    }
}` 

**Keywords:**

1. `public` 
2. `class` 
3. `static` 
4. `void` 
5. `String args` 
- `class` : In Java, **nothing** exists outside a class. It's a "Kingdom of Nouns".
- `public` : Access modifier. The JVM (which is outside your code) needs permission to enter.
- `static` : **CRITICAL CONCEPT.** It means "No Object Needed." The JVM can call this method without building a `new MyApp()`  object first. It's the "Ignition Button".
- `void` : The main method hits a dead end. It doesn't return a value to the OS.
- `String args` : Command line arguments. Even if empty, the signature must match exactly what the JVM looks for.
---

## Basic Building Blocks
**Identifiers:** Names for classes (PascalCase), variables, methods.

**Keywords:** Reserved words (e.g., class, int, if, for, static).

**Comments:**

`// Single line
/* Multi-line _/_
_/_* Documentation comment */` 



Java is a **strongly typed** language, meaning every variable must be declared with a data type, and that type cannot change during the variable's lifetime. This constraint allows the compiler to optimize memory usage and catch errors (like trying to multiply a text string by a number) before the program runs.

---

### Data Types
| Type | Size | **Minimum Value** | **Maximum Value** |
| ----- | ----- | ----- | ----- |
| byte | 1 byte | -128 | 127 |
| short | 2 bytes | -32,768 | 32,767 |
| int | 4 bytes | -2,147,483,648 | 2,147,483,647 |
| long | 8 bytes | -9,223,372,036,854,770,000 | 9,223,372,036,854,770,000 |
| float | 4 bytes | 1.4 × 10⁻⁴⁵ (approx) | 3.4 × 10³⁸ (approx) |
| double | 8 bytes | 4.9 × 10⁻³²⁴ (approx) | 1.7 × 10³⁰⁸ (approx) |
| char | 2 bytes | \u0000' (0) | \uFFFF' (65,535) |
| boolean | not fixed | FALSE | TRUE |
**Suffixes:** To specify a `long` or `float` we must put suffixes in front of them.

float - f or F

long - l or L

Without the suffix, the compiler treats the number as an `int` and will throw an error if it exceeds the `int` range

**Critical Warning:** Floating-point numbers are approximations. They cannot precisely represent values like 0.1. Therefore, `float` and `double` should **never** be used for financial calculations (currency). The `BigDecimal` class is required for such precision

---

`char` : A 16-bit unsigned integer representing a single Unicode character (UTF-16). It ranges from `\u0000`  (0) to `\uffff`  (65,535). Because it is technically an integer type, one can perform arithmetic on characters (e.g., `'A' + 1`  results in `'B'` ).   

`boolean` : Represents a logical truth value: `true`  or `false` . While it conceptually requires only 1 bit, its actual size in memory is JVM-dependent (often padded to a byte for addressing efficiency). It cannot be cast to or from numeric types (unlike in C where 1 is true and 0 is false)



Note: There is no sizeof() in Java, you can use <type>.BYTES



## Java Type Casting
Type casting means converting one data type into another. For example, turning an `int` into a `double`.

In Java, there are two main types of casting:

- **Widening Casting** (automatic) - converting a smaller type to a larger type size
`byte`  -> `short`  -> `char`  -> `int`  -> `long`  -> `float`  -> `double` 


- **Narrowing Casting** (manual) - converting a larger type to a smaller type size
`double`  -> `float`  -> `long`  -> `int`  -> `char`  -> `short`  -> `byte` 
---

## **Operators**
#### **Arithmetic**
`+, -, *, /, %` 

#### **Relational**
`>, <, < =, > =, = =, ! =` 

#### **Logical**
`&&, ||, !` 

#### **Assignment**
`=, +=, -=, *=, /=` .

- _Note:_ Comparing objects (like Strings) with `==`  checks if they are the _same object in memory_, not if they contain the same text. Always use `.equals()`  for object value comparison.   
- **Integer Division Pitfall:** Dividing two integers results in an integer. `5 / 2`  equals `2` , not `2.5` . The fractional part is truncated. To get a decimal result, one operand must be a float/double: `5.0 / 2`  equals `2.5` .   
- **Modulus (**`%` **):** Returns the remainder of division. Useful for determining if a number is even (`x % 2 == 0` ) or cycling through array indices.
- **Increment/Decrement (**`++` **, **`--` **):** These modify the variable directly.
    - **Prefix (**`++i` **):** Increments, _then_ uses the value.
    - **Postfix (**`i++` **):** Uses the value, _then_ increments. This distinction is subtle but critical in loops and complex expressions.   

**Logical:** `&&`  (AND), `||`  (OR), `!`  (NOT).

- **Short-Circuit Evaluation:** Java optimizes logical checks. In the expression `(A && B)` , if `A`  is false, Java stops and returns false immediately without evaluating `B` . This is often used for null safety: `if (str!= null && str.length() > 0)`  prevents a crash.   

### The `==` String Comparison Error
- **Wrong:** `if (input == "yes")` 
- **Right:** `if (input.equals("yes"))`  The `==`  operator compares the memory address (are these the _same_ object?), whereas `.equals()`  compares the actual character content. This is a subtle but disastrous bug in logic. 
---

# Control Flow Statements
## **if Statement**
```java
if (age >= 18) {
    System.out.println("Adult");
}
```
## B. **if-else**
```java
if (marks >= 50) {
    System.out.println("Pass");
} else {
    System.out.println("Fail");
}
```
## C. **else-if Ladder**
```java
if (marks >= 90) {
    System.out.println("A");
} else if (marks >= 75) {
    System.out.println("B");
} else {
    System.out.println("C");
}
```
## D. **Nested if**
```java
if (age > 18) {
    if (citizen) {
        System.out.println("Eligible");
    }
}
```
**Java****_Best Practice:_**** **Always use braces `{}`  even for single-line blocks to prevent maintenance errors.


---

# **Looping Statements**
## A. **for Loop**
```java
for (int i = 1; i <= 5; i++) {
    System.out.println(i);
}
```
## B. **while Loop**
```java
int i = 1;
while (i <= 5) {
    System.out.println(i);
    i++;
}
```
## C. **do-while Loop**
(Executes at least once)

```java
int i = 1;
do {
    System.out.println(i);
    i++;
} while (i <= 5);
```
## D. **Enhanced for Loop (for-each)**
```java
int[] nums = {10, 20, 30};
for (int n : nums) {
    System.out.println(n);
}
```
---

# **Switch Statement**
```java
int day = 3;

switch (day) {
    case 1:
        System.out.println("Mon");
        break;
    case 2:
        System.out.println("Tue");
        break;
    case 3:
        System.out.println("Wed");
        break;
    default:
        System.out.println("Invalid");
}
```
- **Fall-Through:** A notorious feature of the C-style switch. Once a matching `case`  is found, the code executes _and continues executing all subsequent cases_ unless a `break`  statement is encountered. While sometimes useful, forgetting the `break`  is a common bug.   
- _Modern Context:_ Java 14 introduced **Switch Expressions** (using `->` ) which do not fall through and can return values, simplifying this structure significantly, though beginners should learn the classic syntax first.
---

# **Arrays**
```java
int[] numbers = {1, 2, 3, 4};
String[] names = new String[3];
names[0] = "John";
```
---

# **Methods**
```java
public static int add(int a, int b) {
    return a + b;
}
```
---

# Object-Oriented Fundamentals: Classes and Objects
The transition from procedural code (variables and loops) to Object-Oriented Programming (OOP) is the most significant conceptual leap for beginners. Java is built entirely on this paradigm.

### The Blueprint vs. The House
The definitive analogy for understanding Classes and Objects is architectural :   

- **The Class (Blueprint):** A logical template. It describes what a "Car" looks like—it has wheels, a color, and an engine. It also describes what a "Car" can do—accelerate, brake, turn. The blueprint itself cannot be driven; it is just a specification.
- **The Object (House/Car):** A concrete instance built from the blueprint. It takes up physical space (memory). You can build thousands of cars from one blueprint. One car might be red, another blue. They share the same structure (Class) but have different data (State).
### Defining a Class
A class in Java is composed of **Fields** (variables/state) and **Methods** (functions/behavior).   

Java

```
public class Car {
    // Fields (State)
    String color;
    int currentSpeed;

    // Method (Behavior)
    void accelerate(int increase) {
        currentSpeed = currentSpeed + increase;
    }
}
```
### Creating and Using Object
```
