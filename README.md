

My code to attempt to solve: https://www.hackerrank.com/challenges/queens-revised

Representing the queens position by an array of ints.  The index into the array represents the y position, and the values in the array represent the x position.  By assuring each value 0..N appears once in the array, we can skip checking for vertical and horizontal queen attacks.  Diagonals aren't hard to check either.

Initial attempts: 

Using randomly shuffled arrays, yield ok results on small N, but wont scale to 999 as 999 factorial is huge, something like 2000 digits in size, and the number of solutions is relatively small.

Tried genetic algorithm, but finding a descent method to cross breed two boards seems unlikely, as a board has high connectivety.  Also just checking a boards fitnes is an O(n^3) operation.

Current attempt:

Using a constraint satisfier library, has yielded the best results so far, it can solve n=25 in under 5 minutes.  Reports of 999 solutions took 11 hours, so this might work.  Committing code to try on stronger machine.

