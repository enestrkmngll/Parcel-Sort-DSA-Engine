# Parcel Sort Data Structures Engine

**A configurable logistics sorting simulation engine implemented in Java.**

This project demonstrates the practical application of advanced data structures—specifically **Binary Search Trees (BST)**, **Hash Tables**, **Stacks**, **Queues**, and **Circular Linked Lists**—built from scratch to solve complex sorting and routing problems without relying on the standard Java Collections Framework.

## 📌 Project Overview

The simulation models the operations of a dynamic cargo distribution center. Parcels are generated, queued, sorted by destination, and routed to active terminals. The system handles real-world scenarios such as parcel misrouting (error handling) and dynamic terminal rotation, all configured via an external configuration file.

### Core Technical Concepts

* [cite_start]**Fast Lookup (Hash Table):** A custom **Hash Table** implementation (`ParcelTracker`) provides O(1) access to track the real-time status and history of thousands of parcels[cite: 1643].
* [cite_start]**Efficient Sorting (Binary Search Tree):** A **BST** (`DestinationSorter`) organizes parcels by destination city, allowing for efficient categorization and retrieval based on priority[cite: 1643].
* [cite_start]**Error Handling (Stack):** A **Stack**-based system (`ReturnStack`) manages misrouted parcels, ensuring that the most recently failed items are re-processed first (LIFO)[cite: 1643].
* [cite_start]**Flow Management (Queue):** A custom **Queue** (`ArrivalBuffer`) acts as a buffer for incoming parcels, ensuring a smooth First-In-First-Out (FIFO) processing flow[cite: 1643].
* [cite_start]**Dynamic Routing (Circular Linked List):** To simulate shifting dispatch points, a **Circular Linked List** (`TerminalRotator`) manages the cyclic rotation of active cargo terminals[cite: 1643].

## 🚀 Features

* [cite_start]**Fully Configurable:** Simulation parameters (ticks, capacities, cities, error rates) are controlled via `config.txt`[cite: 1643].
* [cite_start]**Complex Simulation Logic:** Simulates the entire lifecycle of a parcel: Arrival -> Buffering -> BST Sorting -> Terminal Routing -> Dispatch or Return[cite: 1643].
* [cite_start]**Event Logging:** Automatically generates a `log.txt` for tick-by-tick events and a `report.txt` for final performance metrics like BST height, load factors, and average delays[cite: 1643].
* [cite_start]**Robust Tracking:** Monitors metrics such as maximum queue sizes, stack depth, and total misrouting events[cite: 1643].

## 📂 Project Structure

* `main`: Contains the entry point of the application (`ParcelSortXMain.java`).
* `DataStructures`: Custom implementations of `BST`, `HashTable`, `Stack`, `Queue`, etc.
* `Parcel`: Data model representing individual cargo items.
* `ParcelSortXSimulation`: Core engine handling the simulation loop and logic.
* `ConfigReader`: Parser for the `config.txt` file.
* `Logger`: System for generating logs and final performance reports.

## 🛠️ How to Run

1. **Clone the repository:**
   ```bash
   git clone [https://github.com/YOUR_USERNAME/ParcelSortX-Data-Structures.git](https://github.com/YOUR_USERNAME/ParcelSortX-Data-Structures.git)

2. **Navigate to the source directory:**
   ```bash
   cd "Source code files"

3. **Compile the project:**
   ```bash
   javac main/ParcelSortXMain.java

4. **Run the simulation:**
   ```bash
   java main.ParcelSortXMain

## 👥 Contributors & Team

This project was a collaborative effort developed as a group assignment.

* **[İbrahim Çobanköse](https://github.com/IbrahimCobankose)** 
* **[Emir Karayılan](https://github.com/emirkarayilan)** 
* **[Enes Türkmenoğlu](https://github.com/enestrkmngll)** 

---

*Thank you for visiting our repository!*
