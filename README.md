# ilanHascalCantinaJava
**Ilan Hascals Java submission for Cantinas take home assignment**

Prerequisites
Java SDK and Eclipse IDE
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
