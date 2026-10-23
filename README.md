# Yet Another Minesweeper

A simple Minesweeper clone written in Java (Swing GUI, NetBeans "Java with Ant" project) by a team of 5
for the **CSD201 – Data Structures and Algorithms** course.
The goal is a playable game built on **our own** data structures (linked list, stack, queue) and
classic algorithms (BFS/DFS graph traversal, Fisher–Yates shuffle, sorted insertion).

---

## 1. The game

The board is a grid of hidden cells, and some of them contain mines.
**You win by opening every cell that does not contain a mine.**

| Difficulty   | Rows × Cols | Mines |
|--------------|-------------|-------|
| Beginner     | 9 × 9       | 10    |
| Intermediate | 16 × 16     | 40    |
| Expert       | 16 × 30     | 99    |

### Basic features

- **Reveal (left click):** opens a hidden cell.
  - **Mine:** you lose. All mines are shown and wrong flags are marked.
  - **Number 1–8:** how many of the 8 surrounding cells contain a mine.
  - **Empty (0):** its neighbors open automatically, and the same happens for every new empty cell (*flood fill*).
- **Flag (right click):** marks or unmarks a hidden cell as a suspected mine. A flagged cell cannot be opened.
- **Chord (middle click, or left click on a number):** if the number of flags around an opened number
  equals that number, all its other hidden neighbors open at once.
- **Safe first click:** mines are placed *after* the first click, never on that cell or its neighbors.
- **Mine counter:** total mines minus flags placed (it can go negative).
- **Timer:** starts on the first click and stops when the game ends (max 999 s).
- **Win:** every safe cell is open. The remaining mines are flagged automatically.
- **New game:** the face button or *Game → New*. The face shows the state: `:)` playing, `B)` won, `X(` lost.
- **Difficulty:** chosen from the *Game* menu.
- **Best times:** the 10 fastest wins per difficulty are saved in `leaderboard.txt`.

---

## 2. Data structures & algorithms used

| Topic                        | Where                                          | Notes                                                        |
|------------------------------|------------------------------------------------|--------------------------------------------------------------|
| 2D array                     | `model.Board` (`Cell[][]`)                     | O(1) access by (row, col)                                    |
| Singly linked list, iterator | `ds.MyLinkedList`                              | neighbor lists, leaderboard; works with for-each             |
| Stack (linked)               | `ds.MyStack`                                   | iterative DFS flood fill                                     |
| Queue (linked)               | `ds.MyQueue`                                   | BFS flood fill                                               |
| Graph traversal: BFS / DFS   | `logic.FloodFill`                              | grid = graph, 8 edges per cell; both must open the same cells |
| Fisher–Yates shuffle         | `logic.MinePlacer`                             | uniform random mine layout in O(rows × cols)                 |
| Sorted insertion             | `MyLinkedList.addSorted`, `score.Leaderboard`  | the list is always sorted best-first                         |
| Linear search / filtering    | `Leaderboard.getTopScores`, `qualifies`        | entries filtered by difficulty                               |

> **Team rule:** do not use `java.util` collections (`ArrayList`, `LinkedList`, `Stack`, `ArrayDeque`,
> `HashMap`, `Collections.sort`, ...) in the game code. Use the classes in `yam.ds`.
> Allowed from `java.util`: `Random`, `Comparator`, `Iterator`, `NoSuchElementException`.

---

## 3. Build & run

- **NetBeans:** open the project folder and press **F6** (Run Project). The main class is `yam.Main`.
- **Command line (Ant):** `ant clean jar`, then `java -jar dist/yam.jar`.
- **JDK:** Every member needs JDK 21 or newer.
- `leaderboard.txt` is created in the working directory (the project folder when run from NetBeans).
