# Project Euler Answers

This repository tends to solve the problems on [Project Euler](https://projecteuler.net) using Java (and of course, mathematics). Since many problems there involve enormous numbers, programming is allowed - or even encouraged - while these problems are being solved.

## Main Aims

This repository have the following main aims:

- Solve problems correctly. (Needless to say)
- Solve problems quickly. (Which, in fact, is a great challenge)
  - Using better algorithms with lower time complexity.
  - Using acceleration techniques such as multi-threading and GPU programming.
  - Using an appropriate amount of programming skills (a.k.a. hacks) to accelerate the program.
- Write problem-solving programs elegantly.
  - Using functional-programming. (Yeah, in Java 8)
  - Achieve lowest memory cost while guaranteeing the speed.
  - Make the program robust and able to conform to different arguments.

## How were answers written

For every solved problem number `X` on Project Euler, there exists a class under package `io.github.std4453.projecteuler` named `ProblemXXX`, e.g. class `Problem037` solves problem #37.

There is / will be a package named `io.github.std4453.projecteuler.utils` containing utility classes which are often used and therefore shared between problems.
 
In every `ProblemXXX` class, the `public static void main(String[] args)` method is the entrance of the problem. Invoking it will by default solve the problem and print the answer to the console. Most problems consist of a function `f` and several arguments, and the answer required is to evaluate `f(a, b, c)`, where `a`, `b` and `c` are randomly given arguments which are often very large. These arguments are specified by `private static final` fields instead of hardcoded.
   
In the code there will be comments starting with `//` explaining how was the problem solved and why was the code written in this way. 

After the program that solves the problem, the correct answer is given and the time complexity of the problem is calculated. After that, I usually write several homework problems, which are either problems I met or interesting ideas I come up with while solving this problem. They are mostly about more programming than maths, trying to get the reader (and also myself) a better understanding of the Java 8 Stream API.  

## Something more

I admit that I'm not very strong in maths. As a programmer, I often tend to solve difficult problems using brute force and the computer does the remaining things for me. Sometimes I simply got tired of maths and want to sink into my so-called comfort zone. Thus, some solutions I give might seem funny and clumsy and stupid. However it will work (I hope). I review my solution from time to time and sometimes when I've found a better solution I might rewrite the previous code. So now, my friend, forgive me for releasing bad answers by this moment - it will get better sooner or later.  

## Author
Harry Zhang [std4453@foxmail.com](mailto:std4453@foxmail.com)