# Arrays + Hashing 

- Track: Master Data Structures & Algorithms  |  Module: Module 1
- Speaker: Prerna Sharma
- Status: PAST

prefix sum, frequency maps
- Recording length: 110 min

## Session notes (AI summary from Airtribe)

# Session Overview

This guide covers how to build prefix sums, answer repeated range-sum queries, and find a pivot index efficiently.  
It also explains when to use a `HashSet` versus a `HashMap`, with examples for duplicate detection and finding a majority element.  
The final section collects practical advice for recognizing patterns and revising DSA problems.

## Prefix Sums: Building Cumulative Totals

A **running sum**, or **prefix sum**, at index `i` is the sum of all elements from index `0` through `i`, inclusive. For example, the prefix sums of `[1, 2, 3, 4, 5, 6]` are `[1, 3, 6, 10, 15, 21]`.

The recurrence is simple:

- `runningSum[0] = nums[0]`
- For `i > 0`: `runningSum[i] = runningSum[i - 1] + nums[i]`

The first index needs special handling because it has no previous prefix value.

> Rather than summing all earlier elements again, reuse the prefix sum already calculated for the previous index.

### Recalculating each sum

A direct approach starts from index `0` again for every result:

```text
for i from 0 to nums.length - 1:
    sum = 0
    for j from 0 to i:
        sum += nums[j]
    runningSum[i] = sum
```

This repeats work: finding the sum through index `2`, for example, recalculates the sum through index `1`.

- **Time:** `O(n²)`, because the total work is `1 + 2 + ... + n`.
- **Space:** `O(n)` for the result array.

### Reusing the previous prefix sum

Instead, build each result from the previous one:

```java
int[] runningSum = new int[nums.length];
runningSum[0] = nums[0];

for (int i = 1; i < nums.length; i++) {
    runningSum[i] = runningSum[i - 1] + nums[i];
}

return runningSum;
```

This takes **O(n)** time and **O(n)** space. A separate result array also preserves the input. You can store the prefix sums directly in `nums`, but avoid modifying the input unless the problem allows or requires it.

**Key takeaway:** Build cumulative totals in one pass by adding each element to the previous prefix sum.

## Range-Sum Queries

Suppose you need to answer many queries asking for the sum of `nums[left]` through `nums[right]`, inclusive. Scanning the range for every query is straightforward, but repeated scans can take **O(qn)** time for `q` queries over an array of length `n`.

A prefix sum lets you subtract the part before the requested range from the total through its right endpoint.

For the array:

```text
nums       = [-2, 0, 3, -5, 2, -1]
prefix sum = [-2, -2, 1, -4, -2, -3]
```

Each prefix value includes all elements from index `0` through that index. For an inclusive range `[left, right]`:

- If `left > 0`: `prefixSum[right] - prefixSum[left - 1]`
- If `left == 0`: `prefixSum[right]`

For the range from index `1` through index `4`:

```text
prefixSum[4] - prefixSum[0] = -2 - (-2) = 0
```

Subtracting `prefixSum[left - 1]` removes the elements before the requested range while keeping `nums[left]`.

An alternative is a prefix array with a leading zero. In that representation, the formula is `prefix[right + 1] - prefix[left]`, including when `left == 0`.

### Storing prefixes for repeated queries

If queries are handled by a class, store the prefix sums in an **instance field** so query methods can reuse them. A variable declared only inside the constructor is local to that constructor and cannot be accessed by another method.

```java
class NumArray {
    private final int[] prefixSum;

    public NumArray(int[] nums) {
        prefixSum = new int[nums.length];

        if (nums.length > 0) {
            prefixSum[0] = nums[0];

            for (int i = 1; i < nums.length; i++) {
                prefixSum[i] = prefixSum[i - 1] + nums[i];
            }
        }
    }

    public int sumRange(int left, int right) {
        if (left == 0) {
            return prefixSum[right];
        }

        return prefixSum[right] - prefixSum[left - 1];
    }
}
```

The prefix array is built once in **O(n)** time and uses **O(n)** extra space. Each query takes **O(1)** time, so `q` queries take **O(n + q)** overall after accounting for preprocessing.

> Build the cumulative sums once; then each query is just a lookup and a subtraction.

**Key takeaway:** Use prefix sums when repeated range queries would otherwise redo the same additions.

## Pivot Index

A **pivot index** is an index where the sum of all elements strictly to its left equals the sum of all elements strictly to its right. The pivot element is excluded from both sums. If no index balances, return `-1`; if several do, return the leftmost one.

For `[1, 7, 3, 6, 5, 6]`, the total sum is `28`. At index `3`, the left side sums to `1 + 7 + 3 = 11` and the right side sums to `5 + 6 = 11`, so the answer is `3`.

### Using prefix sums

With a prefix-sum array, for index `i`:

- `leftSum` is `0` at index `0`; otherwise, it is `prefix[i - 1]`.
- `rightSum` is `totalSum - prefix[i]`.

Subtracting the prefix through the current element leaves only the values strictly to the right.

### One-pass running-sum approach

A prefix array is unnecessary if you only need the current left-side sum. First calculate the total, then scan from left to right while keeping `leftSum`.

```java
int pivotIndex(int[] nums) {
    int totalSum = 0;
    for (int num : nums) {
        totalSum += num;
    }

    int leftSum = 0;

    for (int i = 0; i < nums.length; i++) {
        int rightSum = totalSum - leftSum - nums[i];

        if (leftSum == rightSum) {
            return i;
        }

        leftSum += nums[i];
    }

    return -1;
}
```

The order matters: compare the sums **before** adding `nums[i]` to `leftSum`, because the current element belongs to neither side. Scanning left to right and returning immediately ensures you get the leftmost pivot.

- **Time:** `O(n)` across two passes.
- **Extra space:** `O(1)`.

> To get the right-side sum, subtract the left-side sum and the current element from the total.

**Key takeaway:** For one scan that only needs a running total, use a variable instead of storing every prefix sum.

## Choosing Between HashSet and HashMap

A **HashSet** stores distinct values and is useful for checking whether a value has appeared. A **HashMap** stores **key-value pairs** and is useful when you need to associate information with a key, such as an element and its frequency.

| Structure | Use it when you need to… |
|---|---|
| `HashSet` | Check whether a value is present or has already been seen |
| `HashMap` | Store a value associated with each key, such as a frequency |

In Java, `HashSet.contains(element)` checks for an element, while `HashMap.containsKey(key)` checks for a key. Hash-based lookups are typically **O(1) on average**.

**Key takeaway:** Choose the structure based on the information you need to store: presence alone suggests a set; associated data suggests a map.

## Detecting Duplicates with a HashSet

To determine whether an array contains any repeated value, keep a set of values already seen. If the current value is already in the set, return `true`; otherwise, add it and continue.

```java
Set<Integer> seen = new HashSet<>();

for (int num : nums) {
    if (seen.contains(num)) {
        return true;
    }
    seen.add(num);
}

return false;
```

For `[1, 2, 3, 1]`, the final `1` is already in the set, so the method returns `true`. If the loop finishes without finding a repeat, return `false`.

With average-case constant-time lookups, the algorithm takes **O(n)** average time and uses **O(n)** extra space in the worst case. A `HashMap` could also track seen values, but its key-value structure is unnecessary when you only need membership.

**Key takeaway:** For a duplicate check, store each value you have seen and test membership as you scan.

## Finding a Majority Element with a HashMap

A **majority element** appears **more than `n / 2` times**, where `n` is the array length. A frequency map stores each element as a key and its count as the value.

```java
Map<Integer, Integer> counts = new HashMap<>();

for (int num : nums) {
    counts.put(num, counts.getOrDefault(num, 0) + 1);
}

for (Map.Entry<Integer, Integer> entry : counts.entrySet()) {
    if (entry.getValue() > nums.length / 2) {
        return entry.getKey();
    }
}

return -1;
```

`getOrDefault(num, 0)` returns the existing count or `0` if `num` is not yet a key. Adding `1` and storing the result initializes a first occurrence with count `1`, or increments an existing count.

An entry contains both the key and value: `entry.getKey()` is the number and `entry.getValue()` is its frequency. Iterating over `entrySet()` is convenient when you need both. Iterating over `keySet()` gives only the keys, so you would need a separate lookup for each count.

The comparison is strictly `>`: a frequency equal to half the array length is not a majority. There can be **at most one** majority element, because two distinct values cannot each occur more than half the time. A `HashMap` does not guarantee iteration order, so if a problem accepts any majority element, any matching key is fine. If the problem guarantees a majority exists, the fallback is not reached.

**Key takeaway:** Use a frequency map when the solution depends on how often each distinct value appears.

## Problem-Solving and Revision Habits

When approaching a new problem, inspect the input and output and work through the examples. Ask how each output follows from its input, then look for a reusable pattern. Keywords can suggest a technique, but the examples may reveal the pattern even when no keyword is obvious.

**Brute force** means checking possibilities directly without using a specialized method to reduce the work. It is often a useful starting point, but it is not necessarily the worst possible time complexity.

A spaced-review schedule of **1, 3, 7, and 14 days** was suggested as one option, not a fixed rule. Another useful approach is to revisit a manageable set of problems until you understand the patterns they teach, then try new questions. For example, reviewing a small set of sliding-window problems can expose both fixed-size and variable-size versions of the technique.

**Watch out:** Early problems may feel easy; difficulty rises as topics advance. The aim of revision is to understand how to rebuild an approach, not merely to memorize a solution.

**Key takeaway:** Study examples for their reusable patterns, and revisit problems often enough to retain the reasoning.

## Quick Reference

- **Build a prefix sum:** `prefix[i] = prefix[i - 1] + nums[i]`; initialize index `0` separately.
- **Inclusive range sum:** `prefix[right] - prefix[left - 1]`; if `left == 0`, use `prefix[right]`.
- **Prefix-sum trade-off:** `O(n)` preprocessing and space; `O(1)` per range query.
- **Pivot index:** `rightSum = totalSum - leftSum - nums[i]`; compare before adding `nums[i]` to `leftSum`.
- **Duplicate detection:** Use a `HashSet` to track values already seen.
- **Frequency counting:** Use a `HashMap` with `map.put(x, map.getOrDefault(x, 0) + 1)`.
- **Majority element:** Its count must be strictly greater than `n / 2`.
- **Hash-based operations:** Typically `O(1)` on average.

## Timeline

- 00:00:00 Running Sum: Definition and Examples
- 00:01:50 Brute-Force Running Sum and Repeated Work
- 00:05:10 Running Sum Prefix Recurrence
- 00:07:19 Implement and Test the Optimized Running Sum
- 00:12:29 Range-Sum Queries and the Brute-Force Approach
- 00:16:03 Answer Range Queries with Prefix Sums
- 00:24:56 Implement NumArray and Analyze Complexity
- 00:33:06 Pivot Index: Definition and Prefix-Sum Derivation
- 00:44:56 Implement Pivot Index with a Prefix Array
- 00:48:35 Optimize Pivot Index with Constant Extra Space
- 01:00:27 Transition to the Contains Duplicate Problem
- 01:01:36 Practice Plans and Learning Strategies
- 01:14:44 HashSet and HashMap Fundamentals for Duplicate Detection
- 01:20:10 Implement Contains Duplicate and Discuss Alternatives
- 01:25:57 Majority Element and Frequency Counting
- 01:35:11 Find the Majority Element and Clarify Map Behavior
- 01:44:11 Moore’s Voting Algorithm and Course Wrap-Up
- 01:48:45 Sharing Solutions and Reviewing Java Collections
