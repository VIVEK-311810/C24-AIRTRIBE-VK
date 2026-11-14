# Campus Cafe Inventory Manager

A console program that keeps track of cafe stock. It uses only plain Java and `java.util.Scanner`.


## Files

| File | What it is |
|---|---|
| `Product.java` | The `Product` class: fields, three constructors, `stockValue()`, `display()` |
| `Main.java` | The menu-driven program (options 1 to 6, plus 0 to exit) |
| `Warmup.java` | The prediction exercise (casting, bytes, switch fall-through) |
| `output.txt` | One full test session, with typed inputs shown after each prompt |
| `warmup_output.txt` | What `Warmup.java` actually printed |


## How to run

```
javac *.java
java Main
```

To run the warm-up:

```
java Warmup
```


## What the test session covers

`output.txt` uses every menu option, plus these invalid inputs:

- Viewing products when the list is empty
- A price of -5 and a quantity of -1
- A restock with a bad product number and with a quantity of 0
- A duplicate with a bad product number
- Menu choice 9


## Answers

### 1. Warmup.java: prediction vs actual

I expected `7 5.0 -126` on the first line, then `Two` and `Three`. It printed exactly that.

- `(int) 7.9` is 7, not 8. A narrowing cast drops the decimal part; it does not round.
- `double d = x` is a widening conversion, so `5` becomes `5.0` automatically.
- `(byte) 130` is the surprising one. A byte holds -128 to 127, so Java keeps only the low 8 bits of 130 (`10000010`). In two's complement the leading 1 means negative, which gives -126.
- The `switch` has no `break`, so after matching `case 2` it falls through into `case 3`. That is why both `Two` and `Three` print.

### 2. static `totalProducts` vs instance `name`

`totalProducts` is a static variable: there is one copy, owned by the class and shared by every `Product`. Every constructor increments that same counter.

`name` is an instance variable: every `Product` object has its own copy, so changing one product's name does not affect another.

### 3. The copy constructor, and why `p2.name` does not change `p1.name`

The copy constructor `Product(Product other)` creates a new `Product` object and copies the name, price and quantity from `other` into the new object's own fields. It also increments `totalProducts`.

Because `p2` is a separate object, setting `p2.name = "Coffee"` only changes `p2`'s own `name` field. `p1.name` stays "Tea". (Strings are immutable, so sharing the original text is safe.)

### 4. Why `(int) average` is required

Converting a `double` to an `int` is a narrowing conversion, because an `int` cannot hold a fractional part. Java requires an explicit cast so the programmer acknowledges the loss.

The cast truncates: the decimal part is discarded, so 12.75 becomes 12, not 13.
