#INSTRUCTIONS
Please follow below steps to run test suite. <br> A few points before actually starting with exceution<br>
<br> -------------------------------------------------------------------------
<br> ------------------------------------------------------------------------- <br>
<h4> Important Information : </h4> 
1. Starting point of the Script would be Data Capture Page. It is basically a HTML page (index.html) stored inside resources directory.
2. All the default address for different locales are maintained inside the index.html Page.
3. This framework is built with Maven as Build tool & TestNg as execution environment. Feel free to add testcases to testng.xml file in future
4. Execution reports would be captured inside target folder -> test-classes -> index.html file (Default location for TestNg Projects)
5. <h4> Script Execution Order </h4>
   <ul> 1st step is Data Capture Page, where user has to provide necessary information for all the required fields like Locale, Name, Email and Password for sign up.
   <br> Address is populated by default but you can still change it, most probably fields like Phone no etc </ul>
   <ul>Then user would be Signed up on the appropriate Website based on locale </ul>
   <ul>Post that script would sign in the user and do the necessary actions like country change</ul>

<h4> How to run ? </h4> </br>
Simply go to command prompt, move to your project directory where project exists and hit command - mvn clean install
It should start executing the Test suite. post completion your browser would close

If you want to execute code via a Jar file, please use the below command <b>
java -cp "target/amazon-1.0.0.jar:target/amazon-1.0.0-tests.jar:target/libs/*" org.testng.TestNG testng.xml </b>

<h4> Other Prerequisites </h4> 
<ul> Java Version - 8 </ul>
<ul> Maven - 3.6.1 or higher</ul>
<ul>Chrome - Latest version available</ul>
<ul>IDE  - Intellij / Eclipse </ul>