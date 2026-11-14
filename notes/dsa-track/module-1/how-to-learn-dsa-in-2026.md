# How to learn DSA in 2026

- Track: Master Data Structures & Algorithms  |  Module: Module 1
- Speaker: Prerna Sharma
- Status: PAST

1. spaced retrieval
2. AI as hint-not-answer 
3. how interviews actually work now
4. Complexity (Big-O/Θ, amortized) 
5. brute→optimal mindset
- Recording length: 141 min

## Session notes (AI summary from Airtribe)

# Session Overview

These notes cover AI-assisted coding interviews, how to direct and review AI-generated work, and why DSA skills still matter. They also explain core time-complexity analysis, practical DSA study habits, and what to expect in LLD, HLD, and behavioral interviews.

## AI-assisted coding interviews

An **AI-enabled coding round** may give you a pre-existing repository and ask you to implement a feature, investigate a bug, or complete several smaller tasks. The interview is not simply about whether AI can generate code: you are expected to understand the codebase, guide the tool, and judge whether its output meets the task requirements.

The rules and tools can vary by company, role, and interview format. One described format allowed AI to respond in chat but not edit code files directly. Workplace use may be less restricted, but you should not assume that interview rules match your normal workflow.

**Before the interview, clarify what is allowed**—including whether syntax help is permitted and what the AI tool can access. Using AI when it is prohibited can be treated as cheating. Policies may change, so check the instructions for your specific interview.

AI-enabled rounds may be added to, or replace, one round in some processes; that does not mean DSA interviews have generally been replaced. Traditional DSA rounds and AI-enabled repository tasks assess different things, and interview formats vary.

> **Key insight:** AI assistance does not remove your responsibility for the solution. You still need to understand what you implement and verify that it works.

**Key takeaway:** Confirm the rules first, then treat AI as a tool you are responsible for directing and checking.

## Directing AI and reviewing its work

A reliable AI-assisted workflow starts with understanding the task—not with asking the tool to make changes immediately.

1. **Explore the repository.** If it is unfamiliar, first ask for an overview of its structure and relevant components.
2. **Trace the behavior involved.** Work out where the task belongs and how the relevant code interacts with other parts of the system.
3. **Clarify requirements.** Ask questions about missing details before implementation. A technical design document or other clear specification can help make expectations explicit.
4. **Give AI focused context.** Share the relevant files or functions, rather than an unnecessarily large, unfocused codebase.
5. **Request an implementation and relevant tests.** Consider edge cases and possible effects on connected features.
6. **Review the diff and test results.** Read the generated code, understand its logic and complexity, and accept changes deliberately rather than taking them wholesale.

The approach resembles delegating to a junior colleague: explain the requirements, the tasks to perform, and the tests to use, then review the work. In some workplace workflows, a technical design document guides implementation, and the AI-generated change is reviewed as a PR or, in some organizations, a CL.

### Prompt with a hypothesis

A vague prompt such as “Fix the failing tests” gives little direction. A more useful prompt explains what you suspect and asks the AI to investigate:

> “I suspect the implementation missed these edge cases. Please check whether they are handled and update the code or tests as appropriate.”

Before prompting, ask yourself:

- Which cases are failing, and why might they be failing?
- Is the implementation wrong, or could the test be wrong?
- What change would address the underlying issue?
- What cases might the current tests miss?

The goal is not merely to make tests pass. You should be able to explain what failed and why, then give the AI a focused next step.

### Test the relevant scope

Test the feature you are implementing. If it could affect other features, include relevant tests for those impacts; broader or end-to-end tests may be appropriate when the change has wider effects. Passing tests do not prove that the code is correct, so inspect the code and consider what the tests do **not** cover.

Selecting a few relevant files can help focus the AI on where a bug is likely to be. It does not guarantee that the tool will find every issue or prevent mistakes.

**Watch out:** Do not assume that AI’s “looks good” assessment, a passing test suite, or an explanation of complexity is proof. Check the reasoning yourself.

**Key takeaway:** Give AI a clear task and relevant context, then verify the implementation, tests, and assumptions yourself.

## DSA knowledge and technical interviews

**Data structures and algorithms (DSA)** still matter when AI is available. AI-generated code uses algorithms and data structures; you need enough DSA knowledge to judge correctness, recognize inefficient approaches, and suggest better ones. For instance, knowing the difference between an \(O(n^2)\) and an \(O(n)\) approach helps you assess whether a proposed solution can be improved.

Traditional DSA rounds generally ask you to develop and explain an algorithm yourself; AI is not intended to be used unless the interviewer explicitly permits it. AI-enabled repository tasks, by contrast, may ask you to implement or debug code using AI. Do not assume the same tools or rules apply to both.

In a DSA interview, your first solution may be valid without being optimal. If the interviewer asks you to improve it, treat that as a prompt to explore alternatives—but do not assume every solution has a better asymptotic complexity available. Focus on algorithmic growth rather than online-judge timings such as 10 ms or 2 ms, which can depend on implementation details and the environment.

### Recognize a pattern—and implement it

There is a difference between recognizing a familiar algorithm in code and generating a solution from a problem statement. For example, code with `low`, `high`, `mid`, and a `while` loop may be recognizable as binary search. But you must also be able to identify when binary search applies and implement the solution yourself.

A **topic** is something like arrays; a **pattern** is a reusable approach, such as sliding window, two pointers, or merge intervals. Many problems use arrays but require different patterns. Learning common patterns helps you decide how to approach a problem, but recognizing the technique is only part of the work: you also need to determine how to use it for the specific problem.

**Key takeaway:** DSA helps you both produce solutions independently and assess or improve solutions produced with AI.

## DSA practice and revision

Understanding an explanation is not the same as being able to solve the problem independently. Practise moving from a problem statement to an approach and then to working code—without relying on a recording, blog, notes, or copied solution.

A useful revision routine is spaced practice:

1. After solving a problem, record its **key insight** and your confidence from **1 to 3**.
2. Re-solve it from a blank file on **day 1, day 3, day 7, and day 21**.
3. Try to recall the approach and implement it without looking at your earlier solution. If you need help early on, use it, then try to rely more on recall in later reviews.

For each problem, practise identifying the likely pattern—such as binary search, sliding window, prefix sums, or a hash map—and writing the solution yourself. Teaching a concept to someone else can also reveal whether you truly understand it.

Keep a sustainable routine around work and other commitments. One suggested goal was to make time for a problem a day when preparing for interviews; another personal routine was about three questions a week. These are examples, not universal requirements. The important point is consistent practice and revision rather than chasing a large problem count.

For a course organized around 32 sessions, a question tracker can help you follow planned problems. Use the provided problem set if useful, or another sheet or set of problems you prefer. Progress can move from easy to medium to hard, adjusting the pace if a level feels too difficult. For a beginner who already knows a programming language, the focus is on the DSA concepts and implementation, not on complex object-oriented design. Arrays, strings, and basic data structures are useful foundations.

> **Key insight:** Don’t stop at recognizing a solution. Practise producing it yourself.

**Key takeaway:** Re-solve problems from a blank page, revisit them over time, and focus on applying patterns independently.

## Time complexity

**Time complexity** describes how an algorithm’s work grows as its input size grows. It is not the wall-clock time on a particular machine. A useful introductory model is to count basic operations—such as assignments, comparisons, additions, or prints—and then ask how that count changes with input size \(n\).

For example, this method performs a fixed amount of work:

```java
int solve(int a, int b) {
    int c = a + b;
    return c;
}
```

Its time complexity is **\(O(1)\)**, or constant time. A loop with a fixed bound independent of the input size is also \(O(1)\) asymptotically, even if the bound is large. That does **not** mean the work is necessarily quick in practice.

If a method processes each element of an array, the work grows with the array length:

```java
void print(int[] arr) {
    for (int i = 0; i < arr.length; i++) {
        System.out.print(arr[i]);
    }
}
```

For an array of length \(n\), the method prints \(n\) values, so its time complexity is **\(O(n)\)**. A particular run on a four-element array takes a fixed number of operations; the method’s general complexity describes what happens as the input size varies.

### Best, average, and worst case

- **Best case:** the input that leads to the least work.
- **Average case:** expected work over inputs, given an assumed input distribution.
- **Worst case:** the input that leads to the most work.

These describe different cases; they are not definitions of Big O. **Big O** is an upper bound, **Theta (\(\Theta\))** is a tight bound, and **little-o** is a strict upper bound. In interview analysis, worst-case behavior is commonly reported unless another case is requested.

### Simplifying expressions

Big O focuses on asymptotic growth:

- Ignore fixed constant factors: \(O(100n)\) simplifies to \(O(n)\).
- For a sum, keep the fastest-growing term: \(n^2+n\) is \(O(n^2)\).
- Do not discard a factor that grows with \(n\): \(n^2 \times n=n^3\), and \(n\log n\) is not \(O(n)\).

This simplification helps compare growth rates; it does not claim that constant factors never affect real runtime.

### Sequential loops and nested loops

If two loops run sequentially, their work adds:

```text
for i = 1 to n:
    do constant-time work

for j = 1 to n:
    do constant-time work
```

The total is \(n+n=2n\), which simplifies to **\(O(n)\)**.

If one loop is nested inside another and both run \(n\) times, the work multiplies:

```text
for i = 1 to n:
    for j = 1 to n:
        do constant-time work
```

The inner loop runs \(n\) times for each of the \(n\) outer iterations, for \(n^2\) total work: **\(O(n^2)\)**.

A dependent inner loop can have a changing iteration count:

```text
for i = 1 to n:
    for j = i to n:
        do constant-time work
```

The inner loop runs \(n\) times when \(i=1\), \(n-1\) times when \(i=2\), and so on, down to 1 time when \(i=n\). The total is:

\[
n+(n-1)+\cdots+1
=\frac{n(n+1)}{2}
\]

The dominant term is \(n^2\), so the complexity is **\(O(n^2)\)**.

### Early termination and function calls

A `break` can change the count. If an inner loop immediately breaks on every outer-loop iteration, it performs only one inner iteration each time. With \(n\) outer iterations, the total is **\(O(n)\)**, not \(O(n^2)\).

Likewise, a function call inside a loop is not automatically constant-time. If the outer loop runs \(n\) times and the called function itself does \(O(n)\) work, the total is \(O(n^2)\). The same counting approach applies to `for`, `while`, and `do-while` loops. An empty loop still performs its loop mechanics.

### Binary search

Binary search has **\(O(\log n)\)** time complexity. The detailed derivation was deferred to a separate treatment of binary search.

**Key takeaway:** Count how many times the work executes as \(n\) grows; add sequential work, multiply nested work, and account for early exits and function calls.

## LLD and HLD interviews

**Low-level design (LLD)** focuses on code structure and how a design can accommodate new features with minimal changes. You may be asked to design and write code—for example, for a parking lot. Interviewers may assess your design-pattern choices, how you apply SOLID principles, and whether the result is maintainable and extensible. Be ready to explain why a pattern such as Strategy or Singleton fits the problem.

**High-level design (HLD)** is primarily a technical discussion rather than an implementation-coding round. You may need to sketch an architecture quickly and explain how its parts work together.

A structured approach to HLD is:

1. **Clarify requirements** and set the scope, including what is out of scope.
2. **Estimate scale and traffic**, such as expected users and read/write throughput.
3. **Sketch a basic architecture**, such as clients, servers, and a database.
4. **Add components as the requirements and estimates justify them.** Examples might include Kafka or another pub/sub approach, sharding, or partitioning.
5. **Explain and justify the choices.** Say what each component does and why it fits better than alternatives.

> **Key insight:** Naming technologies is not enough; explain why each component is needed and why you chose it.

HLD rounds may be time-bound—about an hour—so practise drawing a clear diagram quickly and explaining it. The depth expected depends on the role and level.

**Key takeaway:** LLD asks you to design code structure; HLD asks you to explain a system architecture and justify its components.

## Interview communication and behavioral rounds

Interview rounds are not necessarily a progression from easy topics to hard ones. Different interviewers may choose different topics and difficulty levels, so an advanced problem can appear in one round even if another round is easier.

In technical interviews, explain your reasoning as you work. Thinking aloud lets the interviewer understand your approach and gives them a chance to redirect you if needed.

> Don’t just present an answer; make your reasoning visible.

For behavioral interviews, keep answers focused and use **STAR**:

- **Situation:** What was happening?
- **Task:** What were you responsible for?
- **Action:** What did you do?
- **Result:** What happened?

Questions often explore a problem you faced, how you handled it, and how you might handle a similar situation. Prepare real examples from your own experience and avoid recounting every detail. Interview scope varies, so check whether a role or company has specific expectations.

For candidates seeking interviews, building relevant connections on LinkedIn and seeking referrals may help. A referral can be more useful when someone is also able to bring your candidacy to the attention of the recruiter or hiring manager.

**Key takeaway:** Make your reasoning easy to follow in technical rounds, and keep behavioral answers concise, structured, and grounded in your experience.

## Quick reference

- **AI interview rules:** Confirm what tools and kinds of AI use are allowed before relying on them.
- **AI workflow:** Understand the code → give focused context and requirements → request relevant tests → review the diff and verify behavior.
- **Prompting:** State your hypothesis or risk; ask for investigation, not just “fix it.”
- **Traditional DSA vs. AI-enabled round:** Algorithm design from a problem statement vs. repository work with AI assistance.
- **DSA practice:** Re-solve from a blank file; try spaced review on days 1, 3, 7, and 21.
- **Complexity:** Fixed work \(O(1)\); one input-sized loop \(O(n)\); two independent sequential \(n\)-loops \(O(n)\); nested \(n\)-loops \(O(n^2)\); binary search \(O(\log n)\).
- **Counting loops:** Sequential work adds; nested work multiplies; an early `break` can reduce the iteration count.
- **Design interviews:** LLD focuses on code structure and extensibility; HLD focuses on architecture, scale, and justified choices.
- **Behavioral answers:** Use STAR and keep the story concise.

## Timeline

- 00:00:00 Welcome, Introductions, and Session Agenda
- 00:03:50 AI Interviews: Policies and Interview Formats
- 00:07:05 The AI Interview Environment and Engineering Skills
- 00:10:17 Syntax Help, AI Across Roles, and Real-World Engineering
- 00:12:59 Why DSA Still Matters When Coding with AI
- 00:14:37 Prompting AI and Comparing AI-Assisted with DSA Rounds
- 00:17:03 Prompting AI to Diagnose Problems and Understand Requirements
- 00:21:59 Testing AI Output, Thinking Aloud, and Reviewing Code
- 00:26:22 Keeping AI Context Focused
- 00:28:29 Building DSA Consistency and Coding Ability
- 00:33:37 AI Interview FAQs
- 00:37:01 Interview Difficulty and Spaced-Repetition Practice
- 00:41:25 Preparing for AI Rounds and Evaluating Runtime
- 00:44:22 Data Structures and Problem-Solving Patterns
- 00:45:44 Expectations for DSA and AI-Assisted Rounds
- 00:49:53 Whether AI Is Allowed in DSA Interviews
- 00:50:48 Low-Level Design, Interview Languages, and Behavioral Rounds
- 00:55:01 DSA, LLD, and HLD Interview Formats
- 01:00:54 Course Plan and Practice Resources
- 01:01:56 Starting the Time-Complexity Lesson
- 01:03:21 Time Complexity, Cases, and Big O
- 01:09:39 Counting Loop Work: Constant and Linear Complexity
- 01:15:02 Input Size and Fixed Versus Dynamic Loops
- 01:23:45 Independent Loops and Adding Complexities
- 01:27:56 Dependent Nested Loops and the O(N²) Sum
- 01:41:32 Independent Nested Loops and Clarifying Loop Bounds
- 01:53:52 Break Statements and Function Calls in Loop Analysis
- 02:02:57 Course Practice, Resources, and Session Wrap-Up
- 02:06:00 Teaching Approach and Language Preferences
- 02:08:15 Staying Consistent with DSA While Working
- 02:11:43 Beginner DSA Study Strategy and Prerequisites
- 02:13:38 Referrals and Turning Patterns into Solutions
- 02:18:28 Using Older Java Syntax in Interviews
- 02:20:07 Learning to Identify DSA Patterns
- 02:21:07 Closing Remarks
