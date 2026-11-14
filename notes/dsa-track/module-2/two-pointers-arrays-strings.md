# Two pointers (arrays + strings)

- Track: Master Data Structures & Algorithms  |  Module: Module 2
- Speaker: Prerna Sharma
- Status: PAST


- Recording length: 147 min

## Session notes (AI summary from Airtribe)

# Session Overview

These notes cover two-pointer pattern recognition and its applications to sorted two-sum, sorted squares, three-way partitioning, palindrome checks, and partition labels.  
They also connect maximum-subarray reasoning to the Codeforces **Flipping Game**.

## Recognizing the Two-Pointer Pattern

**Two pointers** is a family of techniques, not one fixed algorithm. Two indices track positions in a sequence and move according to the problem’s structure. They might start at opposite ends and move inward, scan at different rates, or mark the boundaries of a growing region.

Sorted input is a useful clue: its order can make each pointer movement eliminate many possibilities at once. Palindrome checks are another common fit because you compare opposite ends and move toward the center. But these are clues, not guarantees—practice helps you recognize when pointer movement is useful.

> Think about what each pointer represents and why it moves; don’t rely only on the label “two pointers.”

**Key takeaway:** Use two pointers when their positions help you systematically narrow, scan, or partition the input.

## Two Sum on a Sorted Array

Given a sorted, non-decreasing array and a target, find two **distinct** elements whose values add to the target. In the version discussed, exactly one solution is guaranteed, and the answer must use **1-based indices**.

### Brute force

Check every pair once. Start the inner loop at `i + 1` so you neither pair an element with itself nor repeat pairs in reverse order.

```text
for i from 0 to n - 2:
    for j from i + 1 to n - 1:
        if a[i] + a[j] == target:
            return [i, j]
```

For four elements, the pairs checked are `(0, 1)`, `(0, 2)`, `(0, 3)`, `(1, 2)`, `(1, 3)`, and `(2, 3)`.

This works, but may check every possible pair:

- **Time:** `O(n²)`
- **Auxiliary space:** `O(1)`

### Two-pointer approach

Start `left` at the first element and `right` at the last. Compare their sum with the target:

- If the sum is **too large**, move `right` left to reduce it.
- If the sum is **too small**, move `left` right to increase it.
- If the sum matches, return the pair.

Sorted order makes those moves informative: each comparison rules out a group of possible pairs.

For `[2, 7, 11, 15]` with target `9`, start with `2 + 15 = 17`; move `right` left. Next, `2 + 11 = 13`; move `right` left again. Then `2 + 7 = 9`, so the answer is found.

```java
public int[] twoSum(int[] numbers, int target) {
    int left = 0;
    int right = numbers.length - 1;

    while (left < right) {
        int sum = numbers[left] + numbers[right];

        if (sum == target) {
            return new int[] {left + 1, right + 1};
        } else if (sum > target) {
            right--;
        } else {
            left++;
        }
    }

    return new int[] {-1, -1};
}
```

The condition `left < right` keeps the two indices distinct. The `+ 1` converts zero-based array positions to the problem’s required 1-based indices. If exactly one solution is guaranteed, the fallback result is not reached.

- **Time:** `O(n)`; each pointer moves inward at most once through the array.
- **Auxiliary space:** `O(1)`.

**Key takeaway:** On a sorted array, compare the outermost remaining pair and move the pointer that can bring the sum closer to the target.

## Two Sum on an Unsorted Array

The two-pointer scan above depends on sorted order. If you sort an unsorted array first, the values’ positions change. When the answer must refer to the **original indices**, preserve each value’s original index alongside it.

For example, sort value-index pairs rather than values alone:

```text
Before: (2, 0), (11, 1), (7, 2), (15, 3)
After:  (2, 0), (7, 2), (11, 1), (15, 3)
```

Then two pointers can search the sorted values while the saved indices identify the original positions.

### Approaches compared

- **Brute force:** `O(n²)` time, `O(1)` auxiliary space.
- **Sort pairs, then use two pointers:** `O(n log n)` time for sorting, followed by an `O(n)` scan; preserving original indices takes `O(n)` extra space.
- **Hash map:** expected `O(n)` time and `O(n)` extra space.

With a hash map, scan the original array and look for the complement—the value needed to reach the target. For `[3, 2, 4]` and target `6`:

1. At `3`, the complement is `3`; it has not been seen, so store the needed value and index.
2. At `2`, the complement is `4`; store it and the current index.
3. At `4`, the map already contains `4` as a needed value, so the pair is found at indices `1` and `2` (zero-based).

**Key takeaway:** For unsorted two-sum, use a hash map for expected linear time, or sort value-index pairs if you want to use two pointers.

## Squaring a Sorted Array

Squaring a sorted array does not necessarily leave it sorted: a large-magnitude negative value can have a larger square than a positive value nearer zero. However, the largest remaining square must be at one of the two ends.

Use pointers at both ends and fill a result array **from right to left**. Compare the endpoint squares, write the larger one at the current result position, then move the pointer that supplied it inward.

> The largest remaining square is at an endpoint. Put it at the back of the result, then repeat.

For `[-4, -1, 0, 3, 10]`:

- Compare `(-4)² = 16` with `10² = 100`; place `100` at the end.
- Compare `(-4)² = 16` with `3² = 9`; place `16` next.
- Compare `(-1)² = 1` with `3² = 9`; place `9` next.
- Compare `(-1)² = 1` with `0² = 0`; place `1` next.
- Place the remaining `0`.

The result is `[0, 1, 9, 16, 100]`. If endpoint squares are equal, either endpoint can be chosen.

- **Time:** `O(n)`
- **Extra space:** `O(n)` for the result

A brute-force alternative is to square every element and sort the results, which takes `O(n log n)` time. If the input is not sorted, endpoint comparisons alone are not sufficient.

**Key takeaway:** For a sorted input, fill the squared result from largest to smallest by repeatedly comparing the two endpoint squares.

## Sorting 0s, 1s, and 2s: Dutch National Flag

The **Sort Colors** problem asks you to sort an array containing only `0`, `1`, and `2`, in place and without using a library sort. Use three pointers:

- `left`: the next position for a `0`.
- `i`: the current value being examined.
- `right`: the next position for a `2`.

The regions evolve like this:

```text
[ 0s | 1s | unexamined values | 2s ]
      left      i              right
```

Process `nums[i]` as follows:

- If it is `0`, swap it with `nums[left]`, then advance both `left` and `i`.
- If it is `1`, leave it in place and advance `i`.
- If it is `2`, swap it with `nums[right]` and decrement `right`. **Do not** advance `i`: the value swapped into that position has not been examined.

```java
private void sortColors(int[] nums) {
    int left = 0;
    int i = 0;
    int right = nums.length - 1;

    while (i <= right) {
        if (nums[i] == 1) {
            i++;
        } else if (nums[i] == 2) {
            swap(nums, i, right);
            right--;
            // The value swapped into i still needs to be checked.
        } else { // nums[i] == 0
            swap(nums, left, i);
            left++;
            i++;
        }
    }
}

private void swap(int[] nums, int x, int y) {
    int temp = nums[x];
    nums[x] = nums[y];
    nums[y] = temp;
}
```

The loop condition `i <= right` ensures the last unexamined element is processed. When `i` passes `right`, there is no unexamined region left.

A counting approach is also possible: count the `0`s, `1`s, and `2`s, then overwrite the array in order. Because there are only three possible values, separate counters are enough.

**Key takeaway:** After moving a `2` to the right, recheck the current index; after moving a `0` to the left, advance both `left` and `i`.

## Valid Palindrome

A palindrome reads the same forward and backward. In this version, compare strings after converting letters to lowercase and ignoring non-alphanumeric characters.

Start one pointer at each end. Skip non-alphanumeric characters, compare the characters that remain without regard to case, then move inward. Return `false` on a mismatch; if the pointers meet or cross without a mismatch, return `true`.

```java
public boolean isPalindrome(String s) {
    int left = 0;
    int right = s.length() - 1;

    while (left < right) {
        while (left < right && !Character.isLetterOrDigit(s.charAt(left))) {
            left++;
        }

        while (left < right && !Character.isLetterOrDigit(s.charAt(right))) {
            right--;
        }

        if (Character.toLowerCase(s.charAt(left))
                != Character.toLowerCase(s.charAt(right))) {
            return false;
        }

        left++;
        right--;
    }

    return true;
}
```

Use `while` loops to skip unwanted characters because several may appear consecutively. The pointers only move inward, so even though the code has nested loops, the total number of pointer moves is linear.

- **Time:** `O(n)`
- **Extra space:** `O(1)`

**Key takeaway:** Skip unusable characters from both ends, compare the next valid pair, and stop once the pointers meet or cross.

## Partition Labels

The task is to split a string into as many contiguous parts as possible so that each character appears in at most one part. A character’s **last occurrence** determines how far a partition containing it must extend.

First, record the last index of every character. Then scan the string while tracking:

- `partitionStart`: where the current partition begins.
- `partitionEnd`: the farthest index the current partition must reach.

For each character, extend `partitionEnd` to include that character’s last occurrence. When the scan index reaches `partitionEnd`, every character encountered in the current partition has all its occurrences included, so the partition can close.

> Keep extending the boundary when a character inside the partition appears farther to the right. Close the partition only when the scan reaches that boundary.

For lowercase English letters, a 26-element array can store last indices; `ch - 'a'` maps a letter to its array position. A `HashMap` is also suitable.

```java
List<Integer> partitionLabels(String s) {
    List<Integer> result = new ArrayList<>();
    int[] lastIndex = new int[26];

    for (int i = 0; i < s.length(); i++) {
        lastIndex[s.charAt(i) - 'a'] = i;
    }

    int partitionStart = 0;
    int partitionEnd = 0;

    for (int i = 0; i < s.length(); i++) {
        partitionEnd = Math.max(
            partitionEnd,
            lastIndex[s.charAt(i) - 'a']
        );

        if (partitionEnd == i) {
            result.add(partitionEnd - partitionStart + 1);
            partitionStart = i + 1;
        }
    }

    return result;
}
```

For `ABABCC`, the last indices are `A → 2`, `B → 3`, and `C → 5`.

- The first partition starts at index `0`. `A` requires it to reach index `2`; `B` extends it to `3`. It closes at index `3`, giving length `4`.
- The next partition starts at index `4`. `C` requires it to reach index `5`, giving length `2`.

The result is `[4, 2]`.

- **Time:** `O(n)`; the string is scanned twice.
- **Auxiliary space:** `O(1)` for the fixed-size last-index array, excluding the result list.

**Key takeaway:** Close a partition only when the scan reaches the farthest last occurrence required by every character inside it.

## Flipping Game: Maximum Gain from One Segment

In this problem, you must flip exactly one **nonempty contiguous segment** of a binary array: `0` becomes `1`, and `1` becomes `0`. The goal is to maximize the number of ones afterward.

Assign each element the change in the number of ones caused by flipping it:

- `0` contributes `+1`.
- `1` contributes `−1`.

The best segment to flip is the contiguous segment with the greatest total gain. Find that gain using the **maximum-subarray** approach, such as Kadane’s algorithm, then add it to the original number of ones.

For `[1, 0, 0, 1, 0]`, the initial number of ones is `2`, and the gains are:

```text
-1 +1 +1 -1 +1
```

The segment covering the two consecutive zeros has gain `+2`. Flipping it produces `[1, 1, 1, 1, 0]`, for a total of `4` ones.

The segment must be nonempty. If the array contains only ones, the best move flips one element, for a gain of `-1`.

**Watch out:** A segment with many zeros is not necessarily best if it also flips many ones. Account for both the gains (`0`s) and the costs (`1`s).

**Key takeaway:** Map each bit to its flip gain, find the maximum-sum nonempty subarray, and add that gain to the original count of ones.

## Quick Reference

- **Sorted two-sum:** Start at both ends; sum too large → move right inward; sum too small → move left inward. `O(n)` time, `O(1)` extra space.
- **Unsorted two-sum:** Use a hash map for expected `O(n)` time, or sort value-index pairs before using two pointers.
- **Sorted squares:** Compare endpoint squares; fill the result from right to left. `O(n)` time.
- **Sort 0s, 1s, 2s:** Three-way partition with `left`, `i`, and `right`. After swapping a `2`, recheck `i`.
- **Valid palindrome:** Skip non-alphanumeric characters at both ends; compare case-insensitively while moving inward.
- **Partition labels:** Track each character’s last index; extend the current boundary until the scan reaches it.
- **Flipping Game:** Map `0 → +1`, `1 → −1`; maximize a nonempty subarray sum.

## Timeline

- 00:00:00 Session Opening and Recap
- 00:19:07 When to Consider Two Pointers
- 00:22:15 Two Sum: Two-Pointer and Hash-Map Approaches
- 00:47:59 Sorted Squares: Two-Pointer Solution
- 01:06:51 Dutch National Flag: Three-Pointer Sort
- 01:27:03 Valid Palindrome: Two-Pointer Check
- 01:43:46 Partition Labels: Last-Occurrence Method
- 02:10:39 Recognizing Two-Pointer Techniques
- 02:11:58 Flipping Game: Sliding-Window Approach
- 02:24:35 Session Wrap-Up
