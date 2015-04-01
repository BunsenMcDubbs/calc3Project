Calc 3 Project by Andrew Dai, William Greenleaf and Lixin Wang

This project is written in Java. To compile the project, navigate to the root
folder then compile the project with:

$ mkdir out
$ javac src/*/*.java -d out

Before running the project navigate into the "out" directory:

$ cd out

Run Part One (this will output the help dialog)

$ java hilbert/PartOne

Perform LU decompositions on a matrix (matrix defined in a file).
NOTE: the files should be in the "out" folder

$ java hilbert/PartOne -lu <path/to/file.dat>

Perform QR decompositions on a matrix (matrix defined in a file).
This will find the QR decomposition of the matrix twice, once with
Householder's reflections and once with Given's rotations.
NOTE: the files should be in the "out" folder

$ java hilbert/PartOne -qr <path/to/file.dat>

Solve for x in Ax = b given an augmented matrix A|b

$ java hilbert/PartOne -solve <path/to.file.dat>