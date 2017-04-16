// Databricks notebook source
// MAGIC %md
// MAGIC 
// MAGIC # Introduction To Scala Programming
// MAGIC [Sina Sheikholeslami](https://www.linkedin.com/in/sinasheikholeslami/), [Big Data R&D Engineer @ Digikala.com](mailto:ssheikholeslami@gmail.com)</br>
// MAGIC [Mohammad Mazrae](https://www.linkedin.com/in/mohammadmazraeh/), [Big Data Engineer & Data Scientist @ Digikala.com](mailto:mazraeh.mohammad@gmail.com)</br>
// MAGIC (Last Edited: April 15 2017)
// MAGIC 
// MAGIC 
// MAGIC Welcome to Scala, and let's get started!</br>
// MAGIC We'll demonstrate the fundamental concepts and the essential syntax of Scala in this notebook.</br>

// COMMAND ----------

// MAGIC %md
// MAGIC 
// MAGIC ## But First... Let's Say Hello!

// COMMAND ----------

println("Hello World!")

// COMMAND ----------

// MAGIC %md
// MAGIC Oh, the traditions! 😁

// COMMAND ----------

// MAGIC %md
// MAGIC ##Interactive Scala: The REPL
// MAGIC - One of the advantages of Scala is its **Read, Eval, Print Loop (REPL)** that let's you play with the language in an interactive manner.</br>
// MAGIC - The de facto build tool for Scala is called **SBT (Simple Build Tool)** that provides its own REPL and has lots of functionalities. While in SBT, you can start its REPL with `console` command.</br>
// MAGIC - You can also use the **Worksheet** feature of IDE's like Intellij IDEA and Eclipse. NetBeans offers the same functionality by the name of *Interactive Console*. </br>
// MAGIC - **Notebooks** let you play with Scala in a similiar fashion to a REPL. For this tutorial, we will be using a **Databricks Notebook** that has a free, community edition suitable for learning Scala and Spark (and it's sanction-free!).
// MAGIC 
// MAGIC Remember, using the REPL is a very fun, easy, and effective way to get yourself familiar with Scala features and syntax.

// COMMAND ----------

// MAGIC %md
// MAGIC # Scala Basics
// MAGIC Okay. Let's explore the basic syntax.

// COMMAND ----------

// MAGIC %md 
// MAGIC ## Defining Variables
// MAGIC - Use `val` keyword to define *immutable* variables. We can't change what a val points to, but if the object itself is mutable, we can change it.
// MAGIC - Use `var` keyword to define *mutable* variables.
// MAGIC 
// MAGIC 
// MAGIC Variables defined using `val` and `var` must be initialized immediately.</br>
// MAGIC **Functional Programming** encourages us to use **immutable** variables whenever possible. Mutable variables are common sources of bugs, and actually you can't use them that much in distributed programs, such as the ones we run using Spark.</br>
// MAGIC Let's define a variable called `workshopTitle` and assign it a `String`, `"Introduction to Scala Programming"`:

// COMMAND ----------

val workshopTitle = "Introduction to Scala Programming"

// COMMAND ----------

// MAGIC %md
// MAGIC Let's print its value:

// COMMAND ----------

println(workshopTitle)

// COMMAND ----------

// MAGIC %md
// MAGIC Suppose its tomorrow and we're at "Stream Processing and Machine Learning with Spark" workshop, so we should change the value of `workshopTitle`. Let's do that.

// COMMAND ----------

workshopTitle = "Stream Processing and Machine Learning with Spark"

// COMMAND ----------

// MAGIC %md
// MAGIC ###Oops!
// MAGIC Remember, immutable means "unchanging over time", so you simply can't reassign or change the value of an immutable variable.</br>
// MAGIC This was probably your first Scala compile error, but the good news is, it was on purpose! 😉

// COMMAND ----------

// MAGIC %md
// MAGIC ### Type Inference
// MAGIC 
// MAGIC As you may have noticed, we didn't explicitly defined the type of `workshopTitle`. Type information is often inferred in Scala. This let's us write codes faster, but caution should be taken when you are not sure about the type Scala is going to infer about your variable.</br>We could have explicitly defined `workshopTitle` as a `String`:</br>
// MAGIC `val workshopTitle: String = "Introduction to Scala Programming"`

// COMMAND ----------

// MAGIC %md
// MAGIC ## Semicolons and Comments
// MAGIC You don't need to place a semicolon at the end of the lines.</br>
// MAGIC Scala follows the same commenting conventions of Java.

// COMMAND ----------

// MAGIC %md
// MAGIC ## If clause
// MAGIC Scala `if` clauses are like Java's. The difference, however, is that every `if` statement is actually an expression that returns value, so we can assign the result to a variable.</br>
// MAGIC Consider this example:

// COMMAND ----------

val configFile = new java.io.File("SomeFile.txt")

val configPath = if(configFile.exists()){
  configFile.getAbsolutePath()
} else{
  configFile.createNewFile()
  configFile.getAbsolutePath()
}

// COMMAND ----------

// MAGIC %md
// MAGIC ## For-Comprehensions
// MAGIC 
// MAGIC In Scala, `for` control structure is very feature rich. Let's explore it through a number of examples.</br>
// MAGIC We'll start by a basic `for` expression:

// COMMAND ----------

val weekDays = List("Saturday", "Sunday", "Monday", "Tuesday", "Wednesday", "Thursday", "Friday")
for(day <- weekDays)
  println(day)


// COMMAND ----------

// MAGIC %md
// MAGIC So we iterated through the list of weekdays and for each element (each weekday), we created a temporary variable called `day` with the value of the element, and printed it.</br>
// MAGIC The `day <- weekDays` expression is called a *generator expression*. The `<-` (left arrow) operator is used to iterate through collections.</br>
// MAGIC We can also leverage `Range` to quickly write a `for` loop:

// COMMAND ----------

for (i <- 1 to 10) println(i)

// COMMAND ----------

// MAGIC %md
// MAGIC ### Guards
// MAGIC We can add `if` expressions to filter for the elements we want to keep. These expressions are called *guards*.</br>
// MAGIC Let's filter for just the days that start with "S", and notice the difference in syntax:

// COMMAND ----------

for(day <- weekDays
  if day.startsWith("S"))
println(day)

// COMMAND ----------

// MAGIC %md
// MAGIC Just so you know, the above `for` loop can be re-written using curly braces:

// COMMAND ----------

for{ day <- weekDays
  if day.startsWith("S")
}{
  println(day)
}

// COMMAND ----------

// MAGIC %md
// MAGIC Remember that it's gonna take a while to get familiar with all the different forms of writing expressions.

// COMMAND ----------

// MAGIC %md
// MAGIC ### Yielding
// MAGIC The `yield` keyword is used to generate new collections with `for` expressions:

// COMMAND ----------

val filteredDays = for{
  day <- weekDays
  if day.startsWith("S")
}yield day

filteredDays.foreach(println)

// COMMAND ----------

// MAGIC %md
// MAGIC Note that if you do not specify the type of the yielded collection, it is inferred from the type of the original collection being iterated over.

// COMMAND ----------

// MAGIC %md
// MAGIC ## Method Definition
// MAGIC 
// MAGIC Method definitions begin with the `def` keyword, followed by an optional list of arguments. After that, you can place an optional return type. If you don't provide the return type, it will be inferred. After that, a `=` acts as the separator between method signature and method body.</br>
// MAGIC Let's define a method that takes a *variable-length* list of strings and returns the upper-cased version of the input list:

// COMMAND ----------

def upper(strings: String*): Seq[String] = {
  strings.map((s: String) => s.toUpperCase())
}

// COMMAND ----------

upper("First String", "Second String").foreach(println)

// COMMAND ----------

// MAGIC %md
// MAGIC In Scala, the last *expression* in a function or method is the return value.</br> However, Scala contains `return` keyword for interoperability with Java, but it is rarely used in methods.</br>
// MAGIC If a method takes no parameters, you can define it without parantheses. However, the convention is to omit parantheses for no-argument methods that have no side effects (for example, a method that returns the size of a collection) and add empty parantheses if side effects (e.g. I/O) are performed, as a caution to the user.</br>
// MAGIC #### A Note on Method Return Type Inferrence
// MAGIC Although you can leave out the return type specification, the best practice is to always define the return type explicitly. Explicit return types express what you expect the method to return. This is specially important when writing public APIs.

// COMMAND ----------

// MAGIC %md
// MAGIC ## Anonymous Functions
// MAGIC 
// MAGIC We could achieve the same functionality of the `upper` method with just defined, without explicitly defining a method, and using what is called an *anonymous function*:

// COMMAND ----------

val listOfStrings: List[String] = List("First", "Second", "Third", "Fourth") // we could also just write:   val listOfStrings = List("First","Second,"Third","Fourth")

listOfStrings.map(s => s.toUpperCase()).foreach(println)

// COMMAND ----------

listOfStrings.map(_.toUpperCase)

// COMMAND ----------

// MAGIC %md
// MAGIC ## Higher-Order Functions
// MAGIC 
// MAGIC When a function accepts other functions as arguments or returns functions as values, it is called a *higher-order function* (HOF).

// COMMAND ----------

// MAGIC %md
// MAGIC ## Tuples
// MAGIC Ever wanted to return more than one value from a method? Scala provides a concise and powerful data structure for this purpose, called **TupleN** (*N* can be between 1 and 22).

// COMMAND ----------

val t2: Tuple2[String, String] = ("One", "Two") 

println("First is: " + t2._1)
println("Second is: " + t2._2)

// COMMAND ----------

val t3 = ("This", "is", "third")
println(t3)

// COMMAND ----------

val t4 = Tuple4("This", "is", "Tuple", 4)
println(t4)

// COMMAND ----------

// MAGIC %md
// MAGIC ## Classes
// MAGIC In Scala, a class definition begins with the `class` keyword and the body is placed inside the outermost curly braces `{}`.

// COMMAND ----------

class Upper{
  def upper(strings: String*): Seq[String] = {
    strings.map((s: String) => s.toUpperCase())
  }
}
val up = new Upper
println(up.upper("Scala", "is", "cool!")) // print the actual list

// COMMAND ----------

// MAGIC %md
// MAGIC Let's define a class named `Person` with two fields: `name` and `age`:

// COMMAND ----------

class Person(val name: String, var age: Int = 12)

// COMMAND ----------

val p = new Person("Sina", 24)

println(p.name)
println(p.age)

// COMMAND ----------

// MAGIC %md
// MAGIC Let's change the `age`:

// COMMAND ----------

p.age = 25
println(p.age)

// COMMAND ----------

// MAGIC %md
// MAGIC ### Objects
// MAGIC Take a look at this block of code:

// COMMAND ----------

object UpperObject{
  def upper(strings: String*) = strings.map(_.toUpperCase())
}
println(UpperObject.upper("Scala", "is", "cool!"))

// COMMAND ----------

// MAGIC %md
// MAGIC When we declare something with the `object` keyword, it will be considered a *singleton* (Remember the Singleton Design Pattern?). We are declaring a class, but the Scala runtime will only create one instance of it, thus we cannot write `new Upper`.</br>
// MAGIC Also, in Scala, the `main` method must be placed inside an `object`. (In Java, `main` must be a `static` method in a `class`.)

// COMMAND ----------

// MAGIC %md
// MAGIC ## Pattern Matching
// MAGIC 
// MAGIC Pattern matching is a powerful feature of Scala, and can be used for decomposing data structures. It can also be used for exception handling.</br>

// COMMAND ----------

val bools = Seq(true, false)

for(bool <- bools){
  bool match{
    case true => println("It's true")
    case false => println("It's false")
  }
}

// COMMAND ----------

// MAGIC %md
// MAGIC Another, more sophisticated example:

// COMMAND ----------

val sequence = Seq(1, 2, 2.7, "one", "two", 'four)
for(x <- sequence)
{ val str = x match {
    case 1 => "int 1"
    case i: Int => "other int: "+i
    case d: Double => "a double: "+d
    case "one" => "string one"
    case s: String => "other string: "+s
    case unexpected => "unexpected value: "+unexpected
  }
  println(str)
 }

// COMMAND ----------

// MAGIC %md
// MAGIC ## Partial Functions
// MAGIC A Partial Function consists only of `case` clauses, and the entire body must be enclosed in braces.</br>When one of the patterns matches, the expressions after the `=>` up to the next `case` keyword (or, the end of function) are evaluated.</br>
// MAGIC If the function is called with an input that doesn't match one of the `case` clauses, a `MathError` will be thrown at the runtime.</br>
// MAGIC You can test if a partial function matches its input using `isDefinedAt` method.</br>
// MAGIC A classic example of a partial function in mathematics is division (x/y) which is undefined when *y* is 0.</br>

// COMMAND ----------

val one: PartialFunction[Int, String] = { case 1 => "one"}
println(one.isDefinedAt(1))
println(one.isDefinedAt(0))

// COMMAND ----------

val two: PartialFunction[Any, String] = {
  case 2 => "literal for two"
  case s: String if (s.length < 5) => "Length is less than 5"
  case s: String => "You entered a string"
  case d: Double => "It's a Double"
  case unexpected => "Unexpected input"
}

// COMMAND ----------

println(two(2))
println(two("Test"))
println(two("Long String"))
println(two(3.0d))
println(two('a'))

// COMMAND ----------

// MAGIC %md
// MAGIC You can chain several partial functions together using the following syntax: `pf1 orElse pf2 orElse pf3 ...`. A `MathError` is only thrown if none of the partial functions match the input.

// COMMAND ----------

// MAGIC %md
// MAGIC ## Ranges
// MAGIC 
// MAGIC A `Range` literal makes it very easy to create sequences of numbers and characters. You can create ranges with an inclusive and exclusive upper bounds and arbitrary intervals. Let's explore this through some examples:

// COMMAND ----------

1 to 10

// COMMAND ----------

val range1 = 1 until 10
range1.foreach(println)
range1(0)

// COMMAND ----------

1f to 11f by 3.1f

// COMMAND ----------

val x = ('a' to 'g' by 3).toArray

// COMMAND ----------

for (i <- 1 to 3) println(i)

// COMMAND ----------

// MAGIC %md
// MAGIC 
// MAGIC ##while and do-while Loops
// MAGIC 
// MAGIC These loops are somewhat similiar to Java. Let's write a do-while loop that uses a mutable variable and counts up to 10:

// COMMAND ----------

var count = 0

do{
  count += 1
  println(count)
} while (count < 10)

// COMMAND ----------

// MAGIC %md
// MAGIC ## Collections
// MAGIC Scala defines several collection classes:</br>
// MAGIC ### Base Classes
// MAGIC - `Iterable` (collections you can iterate on)
// MAGIC - `Seq` (ordered Sequences)
// MAGIC - `Set`
// MAGIC - `Map` (Lookup Data Structure)
// MAGIC ### Immutable Collections
// MAGIC - `List` (linked list, fast sequential access)
// MAGIC - `Stream` (same as `List`, except that the tail is evaluated only on demand)
// MAGIC - `Vector` (array-like type, implemented as tree of blocks, provides fast random access)
// MAGIC - `Range` (ordered sequence of integers with equal spacing)
// MAGIC - `String` (Java type, implicitly converted to a character sequence, so you can treat every string like a `Seq[Char]`)
// MAGIC - `Map` (collection that maps keys to values)
// MAGIC - `Set` (collection without duplicate elements)
// MAGIC ### Mutable Collections
// MAGIC - `Array` (fixed-size sequential collection of elements of the same type)

// COMMAND ----------

val m = Map(1 -> "Scala", 2 -> "Spark")
println(m(1))

// COMMAND ----------

// MAGIC %md
// MAGIC ### Functional Combinators
// MAGIC The most notables are:
// MAGIC - `map`: Apply a function over each element in the list
// MAGIC - `flatten`: It collapses one level of nested structure
// MAGIC - `flatMap`: It's map + flatten
// MAGIC - `foreach`: It's like map but returns nothing

// COMMAND ----------

val numbers = List(1, 2, 3, 4)


// COMMAND ----------

numbers.map(i => i*2)

// COMMAND ----------

List(List(1,2),List(3,4)).flatten

// COMMAND ----------

// MAGIC %md
// MAGIC ## Arrays

// COMMAND ----------

val array: Array[String] = new Array(5)

// COMMAND ----------

// MAGIC %md
// MAGIC We've declared the `array` variable with the `val` keyword so it's immutable. This means that the `array` reference cannot be changed to point to a different `Array`, but the array elements themselves are mutable and can be changed.

// COMMAND ----------

array(0) = "Hello"

// COMMAND ----------

array(4) = "Bye"

// COMMAND ----------

array.foreach(println)

// COMMAND ----------

// MAGIC %md
// MAGIC # Acknowledgements
// MAGIC 
// MAGIC The design and content of this notebook has been **heavily** influenced by the following (In some cases, direct content has been placed here).</br>
// MAGIC We thank these authors and contributors for their awesome material!</br>
// MAGIC 
// MAGIC - “**[Programming Scala, Second Edition](https://deanwampler.github.io/books/programmingscala2.html)** by Dean Wampler and Alex Payne. Copyright 2015 Kevin Dean Wampler and Alex Payne, 978-1-491-94985-6”
// MAGIC - [Dr. Amir H. Payberah](http://www.cs.ox.ac.uk/people/amir.payberah/web/index.html)'s **[Slides](http://www.cs.ox.ac.uk/people/amir.payberah/web/files/download/slides/sharif.pdf)** and **[Courses](https://www.sics.se/~amir/id2221/)**, most notably his [workshop](http://www.paravid.com/lecture/an-introduction-to-data-intensive-computing-platforms) at Sharif Univ. of Technology in August 2016
// MAGIC - [Scala Cheat Sheet](https://github.com/lampepfl/progfun-wiki/blob/gh-pages/CheatSheet.md) by the guys at **EPFL's Programming Methods Laboratory**

// COMMAND ----------


