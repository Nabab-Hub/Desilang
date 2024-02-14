
<h1 align="center">Desi Language</h1>

<p align="center">
It is a funny language that converts our Desi (Hindi/Bengali or your own language) to machine language. It is designed for demonstration and motivation for junior programmers, showing them how they can create their own language.
</p>

<h2 align="center">Installation</h2>

`
To run this language you have to first install java in your system. download from  
`
[here](https://www.oracle.com/in/java/technologies/downloads/) .

`After installing Java, download the Desilang.jar file from this repository. After downloading, double click on the Desilang.jar file. An editor (similar to Notepad) will open; now, write your Desi language code and save the file with the extension '.desi' (e.g., example.desi), or it will result in a compile-time error.`

`To run the code, you can right-click and tap on the 'Build & Run' button, or you can find another button in the 'Execute' menu.`

<h2 align="center">Usage</h2>
<p align="center">
Create a new file into the Desipad editor with extension .desi 
<code>eg: temp.desi</code> </p>

```desi
  suru korlam
    dekhao to "Hello"," ","World"
  ses korlam
```
Output : 
```desi
    Hello World
```
<h2 align="center">Documentation</h2>

<p align="center">Everything must be written between <code>suru korlam</code> and <code>ses korlam</code>; otherwise, it will result in a compile-time error. You can think of <code>suru korlam</code> and <code>ses korlam</code> as the entry and end points of Desilang, respectively.</p>

```desi
we can't write somethig here

suru korlam
    # Everything we have to write here
ses korlam

here also we can't write
```

<h2 align="center">Variables <code>eta holo</code></h2>
- variable can be declared using `eta holo` keyword (or you can write your own keyword to modifying the keyword interface).
- variable should be intialise at the time of creation else it give compile-time error.
- Variable name only should be alphabet (no special symbol and numeric values are allowed)


`Eg:`
```desi
suru korlam
    eta holo a = 10
    eta holo b = 20.5
    eta holo c = "Ahidulla"
    dekhao to a,b,c
ses korlam
```

Output:
```desi
10,20.5,Ahidulla
```

<h2 align="center">Dta Types</h2>

<p align="center">To declare numbers, string we have only one keyword that is <code>eta holo</code>. To create boolean and null values some special keyword are there they are <code>thik</code>, <code>vul</code> & <code>kichuna</code> instead of <code>true</code>, <code>false</code> & <code>null</code>.</p>

`Eg:`

```desi
suru korlam
    eta holo a = thik
    eta holo b = vul
    eta holo c = kichuna
    eta holo d = "Hello"
    eta holo e = 10
    eta holo f = 20.5
ses korlam
```

<h2 align = "center">Printing somethig into the console</h2>

<p align="center">To print anything into the console use <code>dekhao to</code> keyword</p>

`Eg:`

```desi
suru korlam
    eta holo a = 10
    eta holo b = 20.5
    eta holo c = "Ahidulla"
    dekhao to a
    dekhao to "Hello"
    dekhao to b,c
ses korlam
```

<h2 align="center">Conditionls keywords</h2>

<p align="center">It support if, if-else and if-else ladder construct, <code>joid</code>, <code>nahoi jodi</code> & <code>nahole</code> keywords are used instead of if , else if, else respectively.</p>

`Eg:`

```desi
suru korlam
    eta holo a = 10
    
    jodi(a>5)
    {
        dekhao to "It is greater than 5"
    }
    nahoi jodi(a==20)
    {
        dekhao to "It is equal to 20"
    }
    nahole
    {
        dekhao to "False"
    }
ses korlam
```

<h2 align="center">Loops</h2>

<p align="center">It support only one loop that is equivalent to while loop in other programing language. You can create loop using <code>joto khon</code> keyword.
It support two special keywords with loop that are <code>porer ta dehk</code> & <code>dariye ja</code> are equivalent to <code>continue</code> & <code>break</code> statement respectively.</p>

`Eg:`
```desi
suru korlam
    eta holo n = 10
    eta holo i = 1
    joto khon(i<=n)
    {
        dekhao to i

        jodi(i==5)
            porer ta dehk
        
        jodi(i==9)
            dariye ja

        i++
    }
ses korlam
```

<h2 align="center">Pause execution</h2>

<p align="center">If you want to stop some time before executing next statement then you can use `dara` keyword.</p>

```
syntax : dara <number in miliseconds>
```

`Eg:`

```desi
suru korlam
    eta holo n = 10
    eta holo i = 1
    joto khon(i<=n)
    {
        dekhao to i
        dara 1000
        i++
    }
ses korlam
```
This loop print the values of i from 1 to 10  taking 1 second of pause for it's each iteration.

<h2 align="center">Example</h2>

```desi
suru korlam
eta holo a= 10
eta holo b= 0
eta holo c = thik
eta holo str ="who are you"
dekhao to "Hello","Hi bro"
dekhao to "Hi","Bro who are you"
dekhao to a
dekhao to c
eta holo d=a+a
dekhao to d
a++
a=10+20

jodi (vul)
{
	dekhao to "Hello"
}
nahoi jodi (thik)

{
	dekhao to "hello"
}
nahole
{
	dekhao to str
}
eta holo i=0
joto khon (i<10)
{
	i++
	jodi (i==2)
	porer ta dehk
jodi (i==5)
dariye ja
dekhao to i
dara 1000
}
ses korlam

```
<h2 align="center">Add your own keywords </h2>
<p align="center">If you want to add your own keyword in this language, then you can change with your own keyword. Because here I provide all the source code .java files. 
To create your own <code>keywords</code> just edit keywords.java file.
there you can find all the keywords with there usage, just modify the keywords name with your own language.
</p>
