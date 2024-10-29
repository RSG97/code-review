****************
* Project 3: Hashing Experiments
* CS321
* 10/21/24
* Ryan Graham
**************** 

## Overview:

This project implements and analyzes different hash table collision resolution strategies - specifically linear probing and double hashing. It examines how different load factors affect the average number of probes required for successful insertions across various types of input data (random numbers, dates, and words). The project demonstrates the practical differences between these two open addressing techniques and provides empirical data about their performance characteristics.

## Reflection:

This was likely the most challenging project I've ever had to do. I believe this is due to the fact
that up until now, I haven't completed a project with zero code to start out with. The biggest challenge
I faced was simply getting started with writing any code at all. After reading the project documentation,
I sort of just stared at my screen for a while, not knowing exactly what to do. However, I was able to continuously
fumble my way through to the checkpoint, which made me increasingly comfortable with this project.

What I enjoyed the most about this project was exactly what I found to be the most difficult. Being able to complete this
has made me feel significantly more confident in my coding abilities, and I think I now have a better grasp on solo project development.
I would like to call out one debugging issue I had that took me an embarrassingly long time to figure out, which was my average probe count calculation. 
I think there was something in the way I set everything up that made this a little more difficult than it needed to be, but I was
stuck on this for quite some time. Eventually, I was able to get the answer to my problem, but it was completely on accident, but a win is a win. 



## Included Files:

* Hashtable.java - abstract base class implementing core hash table functionality
* LinearProbing.java - implementation of linear probing collision resolution
* DoubleHashing.java - implementation of double hashing collision resolution
* HashObject.java - class representing objects stored in the hash table
* HashtableExperiment.java - driver class for running the hashing experiments
* TwinPrimeGenerator.java - utility class for generating appropriate hash table sizes

## Compiling and Running:

From the directory containing all source files, compile the project with the command:
$ javac HashtableExperiment.java

Run the compiled class file with the command:
java HashtableExperiment <data-source> <load-factor> [<debug-level>]

### Where:

* <data-source>: Type of data to use in the experiment

  * 1 = random numbers
  * 2 = date values
  * 3 = word list


* <load-factor>: The ratio of objects to table size (α = n/m), between 0 and 1
* <debug-level>: Optional parameter to control output detail

  * 0 = print summary of experiment (default)
  * 1 = save the hash tables to files at the end
  * 2 = print debugging output for each insert

## Program Design and Important Concepts:

#### The program consists of several key components:

1. **Abstract Hashtable**: Base class providing core functionality for open addressing hash tables, allowing different probing strategies to be implemented through inheritance.
2. **Linear Probing**: Implementation using the probe sequence h(k,i) = (h1(k) + i) mod m, where consecutive slots are checked until an empty slot is found.
3. **Double Hashing**: Implementation using the probe sequence h(k,i) = (h1(k) + i*h2(k)) mod m, where the step size is determined by a second hash function.
4. **HashObject**: Wrapper class for stored elements that tracks frequency of duplicates and probe counts for performance analysis.
5. **Twin Prime Generation**: Utility for finding appropriate table sizes that optimize double hashing performance by ensuring relative primality between table size and probe sequence.

#### Important concepts demonstrated in this project include:

- Open addressing collision resolution
- Hash function design and analysis
- Performance implications of load factors
- Handling of duplicate keys
- Statistical analysis of probing sequences
- Inheritance-based design for algorithm variants

## Results

Here are the experimental results showing average number of probes for different load factors:

### Data Source 1: Random Numbers
| Load Factor | Linear Probing | Double Hashing |
|-------------|----------------|----------------|
| 0.50        | 1.50           | 1.39           |
| 0.60        | 1.75           | 1.52           |
| 0.70        | 2.16           | 1.72           |
| 0.80        | 3.10           | 2.01           |
| 0.90        | 5.46           | 2.56           |
| 0.95        | 10.56          | 3.15           |
| 0.99        | 43.81          | 4.67           |

### Data Source 2: Dates
| Load Factor | Linear Probing | Double Hashing |
|-------------|----------------|----------------|
| 0.50        | 1.28           | 1.46           |
| 0.60        | 1.44           | 1.67           |
| 0.70        | 1.60           | 2.00           |
| 0.80        | 1.82           | 2.45           |
| 0.90        | 2.18           | 3.21           |
| 0.95        | 2.70           | 3.82           |
| 0.99        | 5.41           | 5.64           |

### Data Source 3: Word List
| Load Factor | Linear Probing | Double Hashing |
|-------------|----------------|----------------|
| 0.50        | 1.60           | 1.39           |
| 0.60        | 2.15           | 1.53           |
| 0.70        | 3.60           | 1.72           |
| 0.80        | 6.71           | 2.02           |
| 0.90        | 19.81          | 2.57           |
| 0.95        | 110.59         | 3.19           |
| 0.99        | 471.67         | 4.70           |


## Sources Used:
* Algorithms Textbook (Introduction to Algorithms)