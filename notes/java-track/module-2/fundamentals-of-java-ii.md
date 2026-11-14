# Fundamentals of Java - II

- Track: Backend - Java Track  |  Module: Module 2
- Speaker: Aashray
- Status: PAST
- Recording length: 111 min

## Session notes (AI summary from Airtribe)

# Session Overview

The notes cover Java primitive conversions and operators, then build up through conditionals, loops, constructors, and collections.  
They also bring these ideas together in a console-based learner-management exercise.  
The final sections review how Java programs run on the JVM, the roles of the JDK/JRE/JVM, and practical ways to look up unfamiliar syntax.

## Primitive types and type conversion

Java primitive types have fixed sizes. For example, an `int` uses 4 bytes, while a `long` and a `double` each use 8 bytes. When you assign a value from one primitive type to another, Java distinguishes between **widening** and **narrowing** conversions.

### Widening: moving to a type with more range

A **widening conversion** moves a value to a type that can represent a larger range. Java generally performs widening conversions automatically:

```java
int smallValue = 15;
long largerType = smallValue;
```

The value is preserved.

> Think of widening as moving a value into a larger space: the value still fits without needing to change.

### Narrowing: moving to a type with less range

A **narrowing conversion** moves a value to a type that may not be able to represent the original value. Java requires an explicit cast because information may be lost:

```java
double decimalValue = 15.7;
int wholeValue = (int) decimalValue;
```

Casting a `double` to an `int` discards the fractional part; it does **not** round to the nearest integer. For example, `15.7` becomes `15`.

A `long`-to-`int` assignment also requires a cast, even when the current value appears small enough:

```java
long count = 300;
int smallerCount = (int) count;
```

Java checks the declared types, not whether the particular value seems safe.

> **Key insight:** An explicit cast signals that you understand the conversion may lose information. Java does not infer that a value is safe to narrow just because it looks small.

### What happens to bits during integer narrowing?

For **narrowing conversions between integer types**, Java keeps the low-order bits—the bits on the right—that fit in the destination type, and discards the higher-order bits on the left. For example, narrowing a `long` to an `int` retains the low 32 bits.

The retained pattern is then interpreted as a value of the destination type. Java represents signed integers using **two’s complement**, so the leftmost bit is part of the signed representation. If that bit is `1`, the resulting value is negative.

For example, in a 4-bit two’s-complement representation:

- `0101` represents `5`.
- `1101` represents `-3`, not `-5`.

A negative result is **not** the discarded portion of the original value. It comes from interpreting the retained bits as a signed value. Narrowing can also change a negative value into a positive one, depending on the retained bit pattern.

> Keep the bits that fit; interpret the resulting pattern as the destination type.

**Watch out:** This bit-retention explanation applies to integer narrowing such as `long` to `int` or `int` to `byte`. It is not a rule for every type conversion; floating-point conversions follow different rules.

**Key takeaway:** Widening conversions generally happen automatically; narrowing requires a cast and can change the value, including its sign.

## Operators

Java provides familiar groups of operators. Their general purpose is shared across many languages, though the syntax can differ.

- **Arithmetic:** addition (`+`), subtraction (`-`), multiplication (`*`), division (`/`), and remainder (`%`).
- **Comparison:** greater than (`>`), less than (`<`), equal to (`==`), and not equal to (`!=`). Comparisons produce a boolean result.
- **Logical:** AND (`&&`), OR (`||`), and NOT (`!`).
- **Assignment:** `=` assigns a value. Compound assignment combines an operation with assignment:
  - `score += 10` adds 10 to `score`.
  - `score -= 5` subtracts 5 from `score`.
- **Increment and decrement:** `++` increases a variable by one; `--` decreases it by one. Both have prefix and postfix forms, such as `++x` and `x++`.

**Key takeaway:** Recognize the operator groups and what type of result they produce—especially that comparisons produce a boolean.

## Conditional control flow

An **`if` statement** runs a block when its condition is true. An `else` block provides an alternative when the condition is false:

```java
if (age >= 18) {
    // Runs when age is at least 18
} else {
    // Runs otherwise
}
```

If `age` is `10`, the first block does not run and the `else` block does.

An **`if-else-if` ladder** checks multiple conditions in order. The first condition that is true selects its corresponding block. An optional final `else` handles cases where none of the earlier conditions match.

### `switch`

A **`switch`** selects a branch by matching a value against cases. A traditional `switch` performs equality-based matching; an `if` statement is more suitable when you need to test ranges, such as whether a value is greater than another value.

In a traditional Java `switch`, execution starts at the matching `case` and continues into later cases unless a `break` or another control-flow statement stops it. This is called **fall-through**.

> Use `break` when execution should stop at the end of a case; without it, execution can fall through into subsequent cases.

Fall-through can be intentional, but omitting `break` accidentally can produce unexpected output.

**Key takeaway:** Use `if` for conditions and ranges; use `switch` for matching cases, and remember that traditional switch cases fall through without a stop.

## Loops

Loops repeat a block of code. The covered forms were `for`, `while`, and `do-while`.

### `for` loop

A `for` loop puts three parts together:

1. **Initialization** — set up the loop variable.
2. **Condition** — check whether the loop should continue.
3. **Increment or update** — change the variable after each iteration.

For example, a loop starting with `i` equal to 1 and continuing while `i <= 5` runs its body for values 1 through 5. After each iteration, it updates `i`; it stops when `i` becomes greater than 5.

### `while` loop

A `while` loop checks its condition **before** running the body. Initialization is usually done beforehand, and the body needs to update the relevant variable or state so the loop can eventually stop.

### `do-while` loop

A `do-while` loop runs its body first and checks the condition afterward. Its body therefore executes **at least once**, even if the condition is initially false. A useful example is prompting for user input: the program must ask once before it can decide whether to ask again.

`for` and `while` can often serve the same purpose; choose the form that makes the logic clearest. Use `do-while` when the body must run at least once. `for` loops are commonly used in production code.

**Key takeaway:** Choose the loop form that makes the control flow clearest; use `do-while` when at least one execution is required.

## Constructors and object initialization

A **constructor** initializes a new object and runs when the object is created with `new`. It has the same name as its class and does not declare a return type. Constructors can accept parameters so a new object can be initialized with supplied values.

```java
class Learner {
    String name;
    int xp;

    Learner(String name, int xp) {
        this.name = name;
        this.xp = xp;
    }
}
```

Here, `this.name` refers to the object’s `name` field, while `name` on the right-hand side is the constructor parameter. The same distinction applies to `this.xp` and `xp`.

```java
Learner learner = new Learner("ABC", 10);
```

This creates a `Learner` whose name is `"ABC"` and whose XP is `10`.

### No-argument and parameterized constructors

A **no-argument constructor** takes no parameters. It can assign chosen starting values to fields, but those assignments are optional. A **parameterized constructor** accepts values and uses them to initialize the object.

If a field is not explicitly initialized, Java supplies a default field value:

- Reference-type fields, such as a `String`, default to `null`.
- Primitive fields receive the default for their type; for example, numeric primitive fields default to zero.

A `null` reference does not refer to an object. It is not an object containing the text `"null"`.

> **Key insight:** If you need a particular starting value, initialize it explicitly; Java’s default field values may not match what your program needs.

### Constructor overloading and copy constructors

A class can have multiple constructors. This is called **constructor overloading**. Each constructor needs a distinct parameter list, based on the number, types, or order of its parameters; different parameter names alone do not make signatures distinct. Overloading lets callers create objects in different ways, such as with no arguments or with a name and XP.

A **copy constructor** is a common pattern: a constructor takes an existing object and initializes a new object with corresponding values.

```java
class Learner {
    String name;
    int xp;

    Learner(Learner other) {
        this.name = other.name;
        this.xp = other.xp;
    }
}
```

```java
Learner l3 = new Learner(l2);
```

Java has no special built-in copy-constructor feature. This is simply another constructor; “copy constructor” describes its pattern and intended use.

A constructor can also accept an object of another class and use it however the code specifies. The input object’s fields do not need to match the fields of the new object.

### Java’s implicit default constructor

If a class declares **no constructors**, Java provides an implicit no-argument constructor, so code such as `new Learner()` can compile. If the class declares any constructor, Java does **not** also provide that implicit no-argument constructor. Define one yourself if you still need `new Learner()`.

**Key takeaway:** Constructors establish an object’s initial state; use parameters for supplied values, `this` to distinguish fields from parameters, and define a no-argument constructor explicitly when needed alongside other constructors.

## Collections and the learner-management exercise

The **Java Collections Framework** provides library tools for commonly used data structures, including lists, queues, maps, and sets. Lists and maps are common in data structures and algorithms practice and in low-level design. The framework is conceptually comparable to C++’s STL, though the APIs and syntax are not identical.

A practical exercise applied the earlier concepts in a console-based **learner management system**. Each learner has a name, age, and XP. A `Learner` class can represent each learner as an object; a collection holds the learners; a menu and loops let the program continue until the user exits.

### Menu requirements

The program repeatedly offers these options:

1. **Add a learner**
   - Ask for the learner’s name, age, and XP.
   - Validate the age: the stated requirement is **18–100**.
   - If the age is outside the accepted range, show an invalid-input message.
   - Create a `Learner` object and add it to the collection only when the input is valid.

2. **Display all learners**
   - If there are no learners, display an appropriate message.
   - Otherwise, loop through the collection and display each learner’s details.

3. **Calculate average XP**
   - If there are no learners, display an appropriate message.
   - Otherwise, total the XP values and divide by the number of learners.

4. **Exit**
   - Print a goodbye message and stop the program.

The menu can be implemented with a `switch`, while a loop allows the user to return to the menu after each operation. The exercise also practises creating objects with constructors, storing them in a collection, using getters to retrieve values, and validating input.

### Walkthrough and testing

A sample run demonstrated rejecting an invalid age, adding valid learners, displaying saved details, calculating average XP, and exiting. With XP values of **20** and **10**, the average is **15**.

**Watch out:** The stated age range was 18–100, but the demonstrated invalid-age message referred to 18–20. Treat the written requirement as the exercise specification; the conflicting example message should be corrected in an implementation.

The exercise also did not yet handle a **non-numeric age**. If age is read as an integer, entering text instead can cause an input error. Validating that a number is within a range and handling input that is not a number are separate needs.

> **Key insight:** A program can handle invalid values while still needing protection against invalid input types.

**Key takeaway:** Use the exercise to practise the full flow: collect input, validate it, create objects, store them, handle empty collections, and calculate results.

## Looking up syntax and documentation

You do not need to memorize every syntax detail to write useful Java programs. For example, console input can be read using standard Java tools such as `Scanner` or `BufferedReader`; when you need a specific pattern, look it up and understand how it fits into your solution.

Java library methods also have documentation. If you are unsure what a method does or whether you need it, check its documentation. For example, `trim()` removes leading and trailing whitespace from a string:

```java
String cleaned = input.trim();
```

For a value such as `"  Rishav   "`, `trim()` produces `"Rishav"`. It is optional; use it when extra whitespace should not be included in the value.

> Use documentation or an AI tool to clarify a specific part you do not understand—not to outsource the whole program.

For a group exercise, one person can share a screen while the group discusses the solution and divides up parts of the task, such as input handling or implementing a menu option. Any familiar IDE is suitable; IntelliJ IDEA, Visual Studio, and Eclipse were all acceptable choices.

**Key takeaway:** Look up focused syntax questions, then make sure you understand, adapt, and run the code yourself.

## Compilation, the JVM, and runtime memory

A Java program starts as a **`.java` source file**. The Java compiler converts it into a **`.class` file** containing bytecode. The JVM executes that bytecode; it does not directly execute the original source file.

```text
.java source file
       |
       | Java compiler
       v
.class file (bytecode)
       |
       | JVM
       v
Program execution
```

At a high level, the JVM loads the class definitions the program needs, manages runtime memory, and executes the program’s bytecode.

### Class loading

The **class loader** loads class definitions needed by the program, including the programmer’s own classes and library classes such as `Scanner`. Class loading involves multiple steps; the important high-level point is that the JVM needs the relevant classes available before it can use them.

### Runtime memory and execution

The JVM uses different runtime memory areas rather than treating memory as one undifferentiated block. The **method area**, **heap**, and **stack** are examples of areas to know at a high level. Objects are allocated at runtime, while method calls use stack frames.

The **execution engine** runs the program’s bytecode. The detailed mechanics of these components—including deeper JVM internals—were left for further study.

> The JVM is where the main runtime work happens: it loads classes, manages memory, and executes the program.

**Key takeaway:** Remember the overall flow: Java source is compiled into bytecode, classes are loaded for use, runtime memory is managed, and the JVM executes the bytecode.

## JDK, JRE, and JVM

These terms describe related parts of the Java development and runtime environment. A useful traditional model is:

```text
JDK
├── Development tools (such as compilers and debuggers)
└── Runtime components
    ├── Java libraries
    └── JVM
```

- **JDK (Java Development Kit):** Tools and runtime components used to develop Java programs.
- **JRE (Java Runtime Environment):** The traditional term for the runtime environment, including Java libraries and the JVM.
- **JVM (Java Virtual Machine):** Loads and executes Java bytecode.

In current Java distributions, these pieces may not be packaged as a separate JRE inside the JDK, but the model remains useful for understanding their roles.

Java bytecode can run on a compatible JVM. The JVM is the runtime layer that loads and executes it.

**Key takeaway:** The JDK supports development, the JRE is the traditional runtime environment, and the JVM runs bytecode.

## Continuing toward interview preparation

The material covered here is a foundation in Java, not the entire path to interview readiness. Further study includes topics such as JVM internals and object-oriented programming before interview preparation becomes a main focus.

It is fine to start interview problems early, but struggling before covering the necessary concepts is not a reason to lose confidence. The suggested estimate was at least another **three to four weeks** before learners would be ready to focus on solving interview questions in earnest.

**Key takeaway:** Build the foundations first; early difficulty with interview problems is not evidence that you cannot learn the material.

## Quick reference

- **Conversions:** Widening is generally automatic; narrowing requires an explicit cast.
- **Integer narrowing:** Keep the low-order bits that fit, then interpret them using the destination type’s signed representation.
- **Control flow:** `if` handles conditions and ranges; traditional `switch` uses matching cases and can fall through without `break`.
- **Loops:** `for` groups initialization, condition, and update; `while` checks before the body; `do-while` runs the body at least once.
- **Constructors:** Same name as the class, no return type. Use parameters to initialize fields; use `this.field` when a parameter shares the field’s name.
- **Implicit no-argument constructor:** Available only when the class declares no constructors.
- **Collections:** Use a collection to hold multiple objects; check for an empty collection before displaying results or calculating an average.
- **Input handling:** Validate both the value (such as an age range) and the input type (such as text entered where a number is expected).
- **Java execution:** `.java` source → compiler → `.class` bytecode → JVM.
- **Tool use:** Look up focused syntax and documentation, then understand and run the code yourself.

## Timeline

- 00:00:00 Primitive Types and Integer Overflow
- 00:02:21 Narrowing and Data Loss
- 00:04:29 Widening Conversions
- 00:09:11 Explicit Typecasting
- 00:11:58 Why Narrowing Can Produce Negative Values
- 00:16:54 Java Operators
- 00:24:22 Conditional Control Flow
- 00:25:58 Switch Fall-Through and Break
- 00:28:11 For, While, and Do-While Loops
- 00:30:03 Choosing a Loop
- 00:31:33 Constructor Basics and No-Argument Constructors
- 00:34:24 Parameterized Constructors and this
- 00:36:34 Copy Constructors
- 00:38:46 Java’s Default Constructor
- 00:40:44 Passing Objects to Constructors
- 00:43:09 Why Java Provides an Empty Constructor
- 00:45:03 Constructor Questions and Course Roadmap
- 00:46:16 Learner Management System Exercise
- 00:50:39 Breakout-Room Coding Activity
- 01:24:54 Reviewing the Learner Management Solution
- 01:30:49 Running and Testing the Solution
- 01:33:02 Input Handling, trim, and Documentation
- 01:38:19 From Java Source to JVM Execution
- 01:40:58 JVM Class Loading, Memory, and Execution
- 01:46:26 Garbage Collection and Collections Framework Questions
- 01:47:51 Interview Readiness and Java Collections
- 01:48:59 JDK, JRE, and JVM
- 01:50:41 Session Closing and Assignments

## Attachment: Week 1 Session 2 - BEL_Java.pptx.pdf (SESSION_DECK)
Saved: `week-1-session-2-bel-java-pptx.pdf`

### Deck text

```
Week- 1

Backend Engineering
    Launchpad
      Aashray Agarwal
Backend Development
 Fundamentals of Java
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
 Introduction to Java
 Java Features
 Fundamentals of Java
 Basic syntax and control structures
 Why use java ?
 Exception handling in java
 Live coding
What is java
  Java is a high-level, class-based, object-oriented programming language

  designed to have as few implementation dependencies as possible.

  Created by Sun Microsystems, now owned by Oracle.

  Known for its "write once, run anywhere" capability due to its

  platform-independent bytecode.
Key features of java
  Simple

  Object Oriented

  Platform Independent

  Robust and secure

  Multithreaded

  High performance

  Distributed
Why Java
   Platform Independence

   Scalability and Performance

   Enterprise-Ready

   Community Support



Where to use Java ?
   CPU intensive applications
Java
Fundamentals
 JDK (Java Development Kit):

 Includes tools for developing Java applications.

 Compiler (javac), Javadoc, Java debugger.

 JRE (Java Runtime Environment):

 Provides the runtime environment for Java applications.

 Includes JVM, core libraries, and runtime environment to execute Java bytecode.

 JVM (Java Virtual Machine):

 Executes Java bytecode.

 Provides platform independence by running bytecode on different platforms.
Java Fundamentals
 Bytecode:

 Intermediate representation of Java source code.

 Generated by the Java compiler (javac).

 Platform Independence:

 Java bytecode is platform-independent.

 It can be executed on any system with a compatible JVM.
Platform
Independence
JDK Components
JDK Components
How does JVM work ?
      JVM Components




            JIT
          Compiler



Interpreting bytecode




       Interpreter
JVM Components - 100 feet view
Class Loader Subsystem

 ●   Loads .class files into memory.
 ●   Performs Loading, Linking, and Initialization.
 ●   Ensures bytecode verification before execution.

Runtime Memory Areas JVM Memory)

Manages memory for class metadata, objects, and execution. It includes:

 ●   Method Area → Stores class metadata, static variables, method code.
 ●   Heap → Stores objects and instance variables.
 ●   Java Stack → Stores method call frames, local variables.
 ●   PC Register → Keeps track of the current instruction being executed.
 ●   Native Method Stack → Manages native JNI) method execution.
JVM Components - 100 feet view
Execution Engine

The core of the JVM that runs the program:

 ●   Interpreter → Executes bytecode line-by-line (slow but startup-friendly).
 ●   Just-In-Time JIT Compiler → Converts bytecode to native machine code for
     better performance.
 ●   Garbage Collector GC → Automatically frees up memory by removing unused
     objects.

Native Interface JNI  Java Native Interface)

 ●   Enables Java code to call native code written in C, C, etc.
 ●   Used for system-level operations and performance optimizations.
JVM Components - 100 feet view
Native Libraries & Java Native Libraries JVM Support)

 ●   Loads essential system libraries required for JVM execution.
 ●   Supports OS-specific interactions via native code.
Class Loader Subsystem
Different JVM Class Loaders
Bootstrap ClassLoader Primordial ClassLoader)

 ●   Loads: Core Java classes (rt.jar in Java 8, java.base module in Java
     9
 ●   Example: java.lang.String, java.util.List
 ●   Parent: None (it's part of the JVM itself, written in C
 ●   Returns null when queried
 ●   Code Example:
     System.out.println(String.class.getClassLoader()); // Output: null
Different JVM Class Loaders
Extension Platform) ClassLoader

 ●   Loads: Classes from lib/ext/ Java 8) or java.ext.dirs Removed in
     Java 9
 ●   Example: com.sun.nio.zipfs.ZipFileSystemProvider.class
 ●   Parent: Bootstrap ClassLoader
 ●   Java 9 Equivalent: Platform ClassLoader
 ●   Code Example:
     System.out.println(ClassLoader.getSystemClassLoader().getParent()
     );
Different JVM Class Loaders
Application System ClassLoader

 ●   Loads: Classes from the classpath (-cp or CLASSPATH env variable)
 ●   Example: Custom classes (com.example.MyClass)
 ●   Parent: Extension Platform) ClassLoader
 ●   Code Example:
     System.out.println(LoaderExample.class.getClassLoader());// Output:
     sun.misc.Launcher$AppClassLoader@..
Class Loading Process
Class Loading Process - Parent Delegation
Model
  Whenever a class needs to be loaded:

   ●   The Application ClassLoader first asks Platform ClassLoader.
   ●   The Platform ClassLoader asks Bootstrap ClassLoader.
   ●   If Bootstrap ClassLoader can't find it, control returns to Platform
       ClassLoader.
   ●   If Platform ClassLoader can't find it, Application ClassLoader attempts
       to load it.
Linking Phase
Linking Phase
The Linking Phase in Java occurs after Loading and before Initialization. It
ensures that the class is properly prepared for execution by verifying,
preparing, and resolving its components.

The Linking Phase consists of three subphases:

 ●   Verification – Ensures the class file is valid.
 ●   Preparation – Allocates memory for static variables and assigns default
     values.
 ●   Resolution – Converts symbolic references into actual memory
     addresses.
Linking - Veriﬁcation, Preparation,
Resolution
  Verification
   ●   Ensures the class file is correctly formatted and does not contain any
       illegal operations.
   ●   Prevents security risks (e.g., memory corruption, unauthorized access).
   ●   Uses the Bytecode Verifier to check the correctness of the .class file.

  Preparation
   ●   This phase allocates memory for static variables and assigns default
       values (not user-defined values).
   ●   The Method Area is updated with memory for class-level variables.

  Resolution
   ●   Converts symbolic references in the .class file to actual memory
       addresses (runtime references).
Resolution
Initialization Phase
 ●   The Initialization Phase is the final step of class loading in JVM. This phase
     ensures that all static variables and static blocks are executed in a
     well-defined order before the class is used.

Steps of Initialization

 ●   Execution of static variable assignments
 ●   Execution of static blocks in the order they appear
 ●   Ensuring superclass initialization before subclass
Runtime access area
Compilation and execution process
JVM End to End
Architecture
Java Compilation
Process
JIT vs Interpreter
Interpreter:
 ●   Function: Executes Java bytecode one instruction at a time.
 ●   Performance: Slower execution due to repetitive translation.
 ●   Memory Usage: Lower memory usage; no storage of compiled code.
 ●   Startup Time: Quick, no initial compilation overhead.
 ●   Use Case: Ideal for quickly starting execution and handling rarely used code paths.

JIT Compiler:

 ●   Function: Compiles Java bytecode to native machine code at runtime.
 ●   Performance: Faster execution after initial compilation; applies optimizations.
 ●   Memory Usage: Higher memory usage; stores compiled machine code.
 ●   Startup Time: Slower due to compilation overhead, but faster subsequent execution.
 ●   Use Case: Best for frequently executed code paths (hot spots) for long-term
     performance gains.
                Compilation                                        Interpretation


Compilation is the process of converting the Java   Interpretation is the process of executing the Java
source code into an executable form, known as       bytecode directly by the JVM.
bytecode.


The Java compiler takes the source code and         The bytecode is loaded into the JVM, and the JVM
produces bytecode, which is then executed by        interprets the bytecode and executes the
the Java Virtual Machine (JVM).                     program.



The code cannot be compiled because of              Debugging is mainly done in run-time.
compilation errors.


Compared to interpreted compiled programs           When an interpreted program executes, changes
operate faster.                                     can be made.
Compilation vs Interpretation
  Compilation converts source code into bytecode, whereas interpretation
  executes bytecode directly by the JVM.

  The compilation is done once during the development process, while
  interpretation happens each time the program is run.


  Compilation allows for the optimization of the code, while interpretation
  provides greater ﬂexibility and dynamic behavior.


  Overall, both compilation and interpretation play important roles in Java
  programming, and the choice of which to use will depend on the speciﬁc
  needs of the program.
Compilation vs Interpretation

                             Command                             Purpose



java -Xint                             Runs only in interpreted mode (JIT disabled)

java -Xcomp                            Runs with aggressive JIT compilation



java -XX:+PrintCompilation             Shows which methods JIT compiled



java -XX:+LogCompilation               Logs detailed JIT compilation info



java -XX:+PrintInterpreter             Prints methods executed in interpreted mode
It’s time for Breakout Rooms!
What is the one programming
 language & framework you
 are most comfortable with
     and what are its top
    qualities/pros & cons
     according to you ?
Visualizing class loading, bytecode, VM
memory and other stats
 Javap -c <<Test.class>>
 Java -verbose:class <<Test.class>>
 Jps -l
 Jcmd <pid> <<Flags>>
 JvisualVM
Exception Handling
 Exceptions are events that disrupt the normal ﬂow of program execution.
 Examples: ArithmeticException, NullPointerException, IOException, etc

 Why Handle Exceptions?

 Improve robustness: Prevent abrupt program termination.

 Graceful error recovery.

 Debugging aid: Understand and ﬁx issues more effectively.
Exception hierarchy Java
   Java's Exception Hierarchy

   Throwable: Base class for all exceptions.

   Error: Irrecoverable issues (e.g., OutOfMemoryError).

   Exception: Recoverable issues.

    ●   Checked Exceptions: Compiler-enforced handling (e.g.,
        IOException).
    ●   Unchecked Exceptions (Runtime Exceptions): Runtime issues
        (e.g., NullPointerException).
Checked Exceptions
 Checked Exceptions:

 These are exceptions that are checked at compile-time by the
 Java compiler.

 Any method that might throw a checked exception must
 declare it using the throws keyword.

 Examples include IOException, SQLException,
 ClassNotFoundException, etc.

 Checked exceptions typically represent conditions that a
 well-behaved application should anticipate and recover from.
Un-Checked Exceptions
 Un-Checked Exceptions:

 These are exceptions that are not checked at compile-time; hence, the
 compiler does not force the caller to handle or declare them.

 They usually occur due to programming errors or conditions that are
 outside the programmer's control (e.g., null pointer dereference,
 arithmetic division by zero).

 Examples include NullPointerException, ArithmeticException,
 ArrayIndexOutOfBoundsException, etc.
Time for some
  ACTION
 Airtribe Learner Manager
Associated session - session #1 (Introduction to java basics)

You need to develop a simple Learner Management System (LMS) for Airtribe. The
system should manage learner records, including adding new learners, displaying
learner details, and calculating the
```
