Run the following commands for each test:

.Test 1:
java -jar videopoker.jar -d 10000 ./TESTS/cmd-file1.txt ./TESTS/card-file1.txt
output: test1-output.txt

.Test 2:
java -jar videopoker.jar -d 10000 ./TESTS/cmd-file2.txt ./TESTS/card-file2.txt
output: test2-output.txt

.Test 3:
java -jar videopoker.jar -d 10000 ./TESTS/cmd-file3.txt ./TESTS/card-file3.txt
output: test3-output.txt

.Test 4:
java -jar videopoker.jar -d 200 ./TESTS/cmd-file4.txt ./TESTS/card-file4.txt
output: test4-output.txt

.Test 5:
java -jar videopoker.jar -d 10000 ./TESTS/cmd-file5.txt ./TESTS/card-file5.txt
output: test5-output.txt

 -------------------------------------------------------------------------------------------------------
| card-file1: test difficult hands, 1-40;										     	|
| card-file2: test difficult hands, 41-81;										|
| card-file3: test all possible commands, hands and returns. no errors or invalid commands forced;	|
| card-file4: test a regular game played by a human (https://youtu.be/Z4K3AD-X5rY). we can see he 	|
| 		  sometimes fails to apply the best strategy;								|
| card-file5: test with forced invalid commands and errors.								|
 -------------------------------------------------------------------------------------------------------