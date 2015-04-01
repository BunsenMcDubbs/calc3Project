Calc 3 Project by Andrew Dai, William Greenleaf and Lixin Wang

This project is written in Java. To compile the project, navigate to the root
folder then compile the project with:

$ mkdir out
$ javac src/*/*.java -d out

Before running the project navigate into the "out" directory:

$ cd out

Run Part Three (this will output the help dialog)

$ java urbanPop/PartThree

Perform power method on an augmented matrix (matrix defined in a file).
Defaults to error tolerance of 0.00000001.
NOTE: the files should be in the "out" folder

$ java urbanPop/PartThree -power <path/to/file.dat>

Perform power method on an augmented matrix with specific error tolerance
(matrix defined in a file)
NOTE: the files should be in the "out" folder

$ java urbanPop/PartThree -power <path/to/file.dat> double

Perform power method on standard matrix with default error and starting vector
NOTE: the files should be in the "out" folder

$ java urbanPop/PartThree -default <path/to/file.dat>