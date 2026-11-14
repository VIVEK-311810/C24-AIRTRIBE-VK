# Introduction to Web development and Backend Engineering

- Track: Backend - Java Track  |  Module: Module 1
- Speaker: Airtribe
- Status: PAST
- Recording length: 84 min

## Session notes (AI summary from Airtribe)

# Session Overview

These notes distinguish front-end and back-end responsibilities, including where business logic should run and how each side faces different scaling challenges.  
They also introduce the basic components of a backend and explain how DNS resolves a domain name before a client connects to a website.  
The notes finish with a practical DNS lookup example and commands for inspecting DNS results.

## Learning context and participation

Mentorship can help learners make progress more efficiently by providing direction and support, especially when they lack guidance at work. Airtribe’s stated goal is to make that support more accessible through programs in product management, software engineering, and generative AI.

Cameras are encouraged because seeing learners’ reactions helps an instructor judge whether an explanation is landing. Practical constraints, such as joining from an office, are understood. Chat is optional: it can support questions and peer answers, but notifications may distract, so you can keep it aside or disable it.

The planned learning path included front-end versus back-end development, what happens when you enter a domain such as `google.com`, an early-course checklist for the first two weeks, and frequently asked questions.

**Key takeaway:** Use the participation options that help you learn; the course’s technical foundations begin with how applications divide work between client and server.

## Front-end and back-end responsibilities

The **front end** is the user-facing part of an application: the interface and the behavior that responds to user actions. It commonly runs in a browser, but “client” is broader than “browser”—a mobile app or another application can also act as a client. The browser is where a web front end runs; it is not itself the front end.

Front-end work includes rendering interfaces, handling interactions such as clicks and navigation, and making an application accessible to people with different needs and ways of interacting. For browser-based interfaces, the core technologies are **HTML, CSS, and JavaScript**. **React** and **Next.js** are tools in the JavaScript ecosystem, not programming languages.

The **back end** is the server-side part of an application. It can receive requests from clients, process them, interact with data stores when needed, and return responses. A programming language such as Python, Scala, or Java is not itself “the back end”; it is one tool used to build back-end components.

A typical interaction looks like this:

```text
User action → front-end request → back-end processing → response → front-end update
```

For example, a user action may trigger an API request. The back end processes it and returns data that the front end can display.

> Think of the front end as the part closest to the user; the back end provides server-side services behind it.

**Key takeaway:** Front end and back end describe different responsibilities in an application—not particular programming languages or devices.

## Where business logic belongs

**Business logic** is the code that implements what an application does for its users. It can run on the client, in the browser, or on the back end. It does not all have to live on the back end; the right location depends on the logic’s purpose, sensitivity, and requirements.

For a small, non-sensitive task, a back-end request may be unnecessary. If a site has about 50 programs, for instance, it could load the data and let the browser filter the list by name or topic. But client-side code runs in an environment the application does not control. It should not be trusted to enforce security.

Authentication and password handling involve sensitive information and security decisions, so they belong on the back end. The back end can perform checks in an environment controlled by the application rather than relying on client code to enforce them.

> Keep simple, non-sensitive interactions on the client when practical; keep sensitive and security-critical logic on the back end.

**Watch out:** “All business logic belongs on the back end” is too absolute. But client-side logic should never be treated as a security boundary.

**Key takeaway:** Choose where logic runs based on what it does and how sensitive or demanding it is.

## Front-end and back-end challenges and scaling

There is no universal answer to whether front-end or back-end development is easier. They face different constraints.

The front end must work across browsers, operating systems, and devices that developers do not control. Hardware and performance can vary, and complex interfaces must remain responsive. Large datasets or demanding computations can also exceed what is practical to handle in the browser.

The back end faces a different kind of scaling problem: it is shared infrastructure serving requests from many clients. Each user has a client-side instance running in their own browser, while back-end services and data systems handle demand from many users. As usage grows, those shared services may need to handle more requests.

For example, filtering 100 items in the browser may be straightforward. If the dataset becomes much larger and client-side searching becomes slow, the search can be moved to the back end, where the compute environment is more controllable. This is a rule of thumb, not a requirement to move every operation server-side.

Other delivery patterns have their own scaling considerations:

- A **CDN** (content delivery network) can distribute static assets such as images, videos, and files.
- **Server-side rendering (SSR)** means servers generate HTML for clients, so the rendering infrastructure may need to scale as demand grows. SSR servers are still servers, even when their work is rendering pages rather than handling conventional API requests.

A **monolith** is an application built as one deployable unit. “Centralized,” in this context, refers more broadly to clients depending on shared back-end services. A back end can use microservices and still be shared infrastructure; centralized does not necessarily mean one machine or one monolithic application.

**Key takeaway:** Client-side work must fit users’ varied devices; back-end services must handle shared demand from many clients.

## Backend foundations and scaling choices

A useful starting point for a back end is to identify two core components:

1. **Server-side execution:** Where the application code runs, including choices about the language, framework, and server environment.
2. **Data and storage:** Where the application stores and retrieves data, including databases, files, images, videos, or other assets.

An application may also use **APIs** to let clients or other services communicate with the back end. Not every back-end component exposes an API: a background worker, for example, may run tasks without directly serving client requests. **Infrastructure** such as networking and configuration connects components and helps control access, security, and scaling. A firewall might prevent public access to a database while allowing the back-end server to reach it.

The server and storage choices should fit the workload. A machine-learning workload or an application hosting an LLM may need GPU-capable machines. An in-memory workload, such as a Redis-like setup, may need ample memory. Language choices also depend on the problem: Node.js and Go can be suitable for asynchronous work, while Python is often practical for AI-related work because of its libraries and ecosystem.

> There is no universally best language, framework, or server configuration—there is a best fit for a particular problem and workload.

Add scaling components when you can identify a bottleneck, rather than adding complexity preemptively. Possible responses include:

- If one server cannot handle incoming demand, a **load balancer** can distribute requests across servers.
- If the database is struggling with reads, consider a **read replica** or **caching** (for example, with Redis).
- If the data or workload calls for it, **sharding** may be an option.
- If heavy writes are burdening the database, move work into a background queue so workers can process jobs asynchronously or in batches. A system such as Kafka may be used for this kind of work.

Load balancers, caches, CDNs, queues, and tools such as Kafka are additions to the basic server-and-storage setup. Which ones help depends on the specific bottleneck.

**Key takeaway:** Start with server-side execution and storage, then add components that address observed workload or scaling needs.

## DNS: resolving a domain name

**DNS (Domain Name System)** maps a human-readable domain name, such as `google.com`, to DNS records that can include an IP address. An **IP address** identifies a network endpoint that a system can contact.

> DNS is like a phone book: you look up a name to find the address associated with it.

The lookup is a preparation step, not the website visit itself. Once the client has an IP address, it can use that address to connect to the destination server.

A typical lookup involves a **recursive resolver**, which may be provided by an ISP or a public DNS service. Examples include Cloudflare’s `1.1.1.1` and Google’s `8.8.8.8`. A home router may forward DNS requests to a resolver, but the resolver is not necessarily located in the router.

If the resolver does not already have a cached answer, it follows referrals through the DNS hierarchy:

1. **Root name servers** direct the resolver to the relevant **top-level domain (TLD)**, such as `.com`.
2. **TLD name servers** direct it to the authoritative name servers for a domain, such as `google.com`.
3. An **authoritative name server** provides the requested DNS record, such as the record for `www.google.com`.
4. The resolver returns the answer to the client and may cache it.

```text
Client → recursive resolver → root → TLD → authoritative name server
       ←                 DNS answer ←
```

The root server does not provide Google’s final IP address; it points the resolver toward `.com`. The `.com` server then points it toward Google’s authoritative name servers, which supply the requested record.

### Why DNS is distributed

A single DNS server for the entire internet would create performance and reliability problems: it could become overloaded, be slow to reach from distant regions, or become a single point of failure. DNS instead distributes responsibilities across multiple levels and many servers. This also means clients do not need every domain-to-address mapping stored locally.

There are **13 named root-server identities**, labelled A through M. This does not mean there are only 13 physical machines: each identity is served by multiple distributed instances. Root servers provide the same root-zone information, so a resolver can use an available root server to find the relevant TLD servers.

A **TLD** is the final label in a domain name, such as `.com`, `.in`, or `.live`. TLD name servers hold delegation information about which authoritative name servers serve domains under that TLD. They do not normally hold every record for those domains. For example, `.com` can direct a resolver to the name servers for `google.com`, while Google’s authoritative DNS provides records for hostnames such as `www.google.com` or `mail.google.com`.

A domain’s authoritative DNS provider can be separate from its registrar. The domain owner can manage records for subdomains, such as `shop.example.com`, through the authoritative DNS service; those subdomains do not each need a separate entry at the TLD level.

**Watch out:** A DNS name server is a service, not an IP address. To communicate with a name server, a system needs an IP address for one of its network endpoints. A name server may have multiple addresses, and its address is not necessarily changing continuously.

**Key takeaway:** DNS is a distributed hierarchy: root servers point to TLD servers, TLD servers point to authoritative servers, and authoritative servers provide a domain’s records.

## Following a DNS lookup with `dig`

You can inspect DNS results from a command line.

A basic lookup shows the answer without showing the full delegation path:

```bash
dig google.com
```

To display the lookup path, use:

```bash
dig google.com +trace
```

The trace shows the delegation stages—root, the relevant TLD, and the domain’s authoritative name servers. In practice, caching can avoid some or all of these steps; the trace is useful for seeing how the hierarchy fits together.

**Key insight:** A root-server response that points to `.com` is a referral, not a statement that Google’s records are hosted on that root server.

**Key takeaway:** Use `dig` to inspect a DNS answer and `dig +trace` to see the delegation path that leads to it.

## Quick reference

- **Front end:** User-facing interface and client-side behavior.
- **Back end:** Server-side processing, data access, and supporting services.
- **Business logic:** Can run on either side; keep security-critical decisions on the back end.
- **Backend starting point:** Identify server-side execution and data/storage before adding scaling components.
- **Scaling:** Add remedies for bottlenecks—such as a load balancer, cache, read replica, or background queue—when needed.
- **DNS order:** Resolver → root → TLD → authoritative name server → DNS answer.
- **DNS vs. connection:** DNS finds an address; the client then uses it to connect to the server.
- **Commands:** `dig google.com` shows a DNS answer; `dig google.com +trace` shows the delegation path.

## Timeline

- 00:00:00 Welcome and Session Setup
- 00:07:57 AI-Assisted Coding and Developer Questions
- 00:09:03 Dhaval’s Engineering Background and Airtribe
- 00:12:06 Session Participation and Camera Guidelines
- 00:15:51 Session Agenda
- 00:17:39 What Is Front-End Development?
- 00:19:46 What Is Back-End Development?
- 00:21:24 Where Business Logic Belongs
- 00:24:20 Front-End and Back-End Components
- 00:26:54 Front-End and Back-End Scalability
- 00:29:37 Scalability Questions and Client-Side Limits
- 00:33:31 Core Elements of a Backend Architecture
- 00:37:36 Choosing Servers, Languages, and Frameworks
- 00:40:21 What Happens When You Visit Google.com?
- 00:42:17 Why DNS and IP Addresses Are Needed
- 00:47:08 Inside DNS: From a Single Lookup to a Distributed System
- 00:53:41 DNS Resolver, Root, TLD, and Authoritative Servers
- 01:08:30 Root Name Servers Around the World
- 01:11:26 Tracing a Google.com DNS Request
- 01:17:17 DNS Lookup Demonstration with dig
- 01:23:06 Questions About DNS Responses and Routing

## Attachment: Intro to Web development.pdf (SESSION_DECK)
Saved: `intro-to-web-development.pdf`

### Deck text

```
Turn on your camera
         Week- 1

Backend Engineering
    Launchpad
       Dhaval Trivedi
Agenda
●   Deep dive into HTTP

●   Web-servers

●   Question and answers
Client-server architecture
●   Request

●   Response
HTTP response example
Design APIs
You are a backend engineer at ShopEase, a fictional e-commerce platform and it

allows users to browse items, add them to a shopping cart, and make purchases.

It also has features like order tracking, product reviews, and account

management.
Design APIs
The main features of ShopEase include:

User Management: Users should be able to register, login, logout, and manage their

account details.
Design APIs
The main features of ShopEase include:

User Management: Users should be able to register, login, logout, and manage their

account details.



Register:

Method: POST

Endpoint: /api/v1/users/register

Headers: –

Response - {user, token}, 201
Design APIs
●   Product Browsing: Users should be able to browse

    products by categories and search for specific

    products.

●   Shopping Cart: Users should be able to add,

    remove, or change the quantity of items in their

    shopping cart.

●   Order Processing: Users should be able to make

    purchases, view their past orders, and track their

    current orders.
Questions?
```
