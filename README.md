# GetOffline
GetOffline is an Android application, which has been made purely for educational purposes.
 Open the app, click on the button to take a webpage offline and enter the URL of the page
 you want to take offline. Since this is for educational purposes only, the graphics of the
 page taken offline may be different and maybe in only readable format. This app focuses on
 the problem which geeks suffer that in absence of internet access, they are'nt able to use
 the free time they have.(Just kidding about the geek stuff. I too suffer through such problems.
 And mate trust me, If you visit the web for learning purposes, This is what you need) Make sure
 that you are connected to the internet while you enter the URL of the page you want to take offline.
 This app is a result of some stuff that I learnt in past 2 days. Jsoup has been used to take
 the source code of the given URL only.  Later I am planing to take the list of anchor tags present
 in the page and take them offline too.   


status:-

1. user enters the url and the name. The data is stored in the shared prefrences. 

2. if the url provided is non faulty, the app will run successfully and after a lag of some 3-5 secs,
the source code will apear in the textview given. 

3. put the extraction part in the async's doInBackground method.

4. create a folder on the app's first deployment. store all the files in this folder with .html extension.

5. uncomment the recyclerview from main activity.xml. code the adapter class for it. on the list, names
given by the user at the time of downloading a page will be shown. when clicked on this option,
the respective file will run in the browser. 

6. check for network connectivity.

7. ask the user about storing it into internal storage or sd card.

8. store the scraped source in an html file in the destination folder. 


Cheers! Happy Coding!
