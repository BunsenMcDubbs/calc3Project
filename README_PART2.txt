Calc 3 Project by Andrew Dai, William Greenleaf and Lixin Wang

This project is written in Java. To compile the project, navigate to the root
folder then compile the project with:

$ mkdir out
$ javac src/*/*.java -d out

Before running the project navigate into the "out" directory:

$ cd out

Run Part Two (this will output the help dialog)

$ java convolution/PartTwo

Solve for x given a matrix A, vector b, vector x0 and tolerance
The file should contain an augmented matrix A|b, and then column
vector x0 (elements seperated by new lines) and a tolerance
on the last line.

% java convolution/PartTwo -iteration <path/to/file.dat>

Generate a random bit stream (x) and encode into y0 and y1.
This will default to a bit stream 20 bits long.

% java convolution/PartTwo -e <number of elements to generate and encode>
// This will also print out the A0 and A1 matrices
% java convolution/PartTwo -ep <number of elements to generate and encode>

Decode x given a multiplexed bit stream y (from a file)

% java convolution/PartTwo -d <path/to/file.dat>
