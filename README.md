# ilanHascalCantinaJava
**Ilan Hascals Java submission for Cantinas take home assignment**

Prerequisites
Java JDK 13 and Eclipse IDE
Downloadable from:
[https://www.oracle.com/technetwork/java/javase/downloads/index.html]
[eclipse.org]



Problem: 
Write a command line program in a language of your choosing that parses the following JSON file from stdin or from command line argument:
https://github.com/jdolan/quetoo/blob/master/src/cgame/default/ui/settings/SystemViewController.json
Your program may assume that the file resides locally on the same filesystem as the program itself, or it may retrieve it via web request if you prefer.
The JSON file represents a view hierarchy in a user interface. You can think of it as a tree of nodes, just like elements in an HTML DOM. Like in HTML, views have certain attributes that are selectable, ala CSS selectors.
Once parsed, your program should wait for user input on stdin. Each line the user enters on stdin should be treated as a selector, and your program should print the matching views, as JSON, to stdout. The exact format of your output is entirely up to you.
The program must support simple selectors for the following view attributes:
1.     class - The view class name, e.g. "StackView"

2.     classNames - CSS class names, e.g. ".container"

3.     identifier - The view identifier, e.g. "#videoMode"

Design Choices:
1. Pulling from web - have never done this before in Java, so it was interesting to interact with the URL/BufferedReader class
2. Modular method for selectors - wrote one method that processes either the view class name,CSS class names,or the view identifier (ln 
3. Verification of program - purposefully used an integer to keep track of how many views the program has traversed through

Execution of program:
1.Install Java JDK 13 & eclipse

2)Create a new workspace 

3)```Drag and drop files``` from the cloned repo into the work space..make sure to replace any existing files with incoming ones

4)Make sure that build path is configured with correct .jar file for org.JSON.simple package

5)If so, ``right click`` on the project in package explorer

6)Hover on ``Run As`` ``1 Java Application``

7)Proceed to enter selectors into Console
