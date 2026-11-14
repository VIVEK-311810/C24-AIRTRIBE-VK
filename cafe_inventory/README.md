# Campus Cafe Inventory Manager

1. Warmup.java
```
Expected : 

8 5.0 130
Two
Three
```
```
Actual:

7 5.0 -126
Two
Three
```
2. Static variable totalProducts will check the total products for all the instances where the instance variable name is unique for each product so no duplicates and conflicts will occur.

3. Copy contructor uses the already created instance of the same obj and creates a new Product obj and use the values of already existing obj instead of adding it manually. After copy we can change the values of each obj seperately without any conflict. If we change the name of the copu obj will not effect the obj it is copied from since the instances are different.

4. Converting the the double to int can lose some of the data. So we need to explicitly mention the conversion using (int) with explicit cast, so it will confirm user has not problem and it will convert. This cast will discard the decimal not round off.
