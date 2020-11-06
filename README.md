# 2020-132

**Group Details**

**SMART SAFARI** - Safari Traveling Guidance System (Mobile Application)

2020-132

Supervisor - Ms. Lasantha Abeysiri

Co - Supervisor - Dr. Pradeep Abeygunawardana

**Group Members**

* Lawrence D.P  |  IT17053242	| 0770644526 |  dimuthpriyasad@gmail.com

* Ariyarathna T.M.M.D | IT17035286 | 0719301166 | deepashikaariyarathna16@gmail.com
 
* De Silva W.K.N | IT17095518 | 0769784516 | niluni97@gmail.com
 
* De Silva M.D.S.C | IT17025690 | 0711015119 | shankadesilva.c@gmail.com


**Project Description**

Mobile application for "YAALA" safari travelers which is facilitating users to get a better safari experience with newly added features to make their way easy.
There are four facilities including, map with temporary locations adding function, automatic comment section , animal identification system and an optimal path generator.



**Main Research Questions**


*  “YAALA” is the second biggest national park in Sri Lanka and it is very famous among local and foreign travelers because of the host of animals that live in the park and the variety of the environment. Most of the travelers, especially foreign travelers willing to plan their safari trip based on other feedbacks. But there is no option available for the travelers to add their comments and view other comments, feedbacks in a proper manner.
When it comes to a safari tour, travelers can’t get better experience in safari tour. Most of the time animals are live in inside the jungle. Because of that, travelers can’t get the expected experience from the safari tour. There are no mobile applications to notify the locations to watch animals when they are in the sanctuary.
There are many numbers of animals in this park. Most of the travelers, especially foreigners they enthusiastic to wildlife viewing, wildlife photography, Birdwatching are some of the most interesting hobbies. But sometimes identifying animals, as an example mammal correctly and viewing them with knowing the correctly information about them is more interesting. And, it will be very important for children as well for their school studies also. Also, this information will be needed for the university students in Sri Lanka as well as the students from foreign universities for their studies and researches. If there is a method of identifying mammals by their movement and life patterns it will be much easier for the above-mentioned individuals.
The main research problem we discuss in this paper is there is not implemented any app yet to generate an optimal route for travelers in traveling national parks. To identify which path goes in the park to fulfill the traveler requirements is still doing manually. It is very time consuming and travelers cannot choose his path properly with less information in the park.
Our proposed system is the solution for these research problems.


**Individual Research Question**


1.    When it comes to a safari tour, travelers cannot get better experience in safari tour. Most of the time animals are live in inside the sanctuary. Because of that, travelers cannot get the expected experience from the safari tour. There are no mobile application to notify the locations when in a safari tour to watch animals when they are outside of the sanctuary.

2.   Most of the travelers, especially foreign travelers willing to plan their safari trip based on other feedbacks. But there is no option available for the travelers to add their comments and view other comments, feedbacks in a proper manner. If there is a section to facilitate travelers to add their comments into a categorized section as “Good comments” and “Bad comments” and view all comments under the specified score range. There is no option available to facilitate travelers to view added comments under categorized section as “Positive” and “Negative” and view added feedbacks in order to “very bad” level from “very good” level to find the best feedbacks to plan their safari travel better than before. In this proposed system, I wanted to come up with a solution to overcome this problem and to let the travelers get a better safari experience than before. In this proposed system, it facilitates to travelers, to add their comments and the system will automatically categorize those comments into negative and positive sections. It the system will add those comments into a pre-defined score range. Then it will be more accurate and very useful for all travelers to get a best safari experience better than before.

3.   There are variety of animals in this national park. Most of the travelers, especially foreigners are enthusiastic to wildlife viewing, wildlife photography, bird watching. These are some of the most interesting hobbies that are done by most people. But sometimes identifying animals, as an example, mammals correctly and viewing them with knowing the correct information about them is more interesting. And, it will be very important for children as well for their school studies also. Also, this information will be needed for the university students in Sri Lanka as well as the students from foreign universities for their studies and researches. If there is a method of identifying mammals by their movement and life patterns it will be much easier for the above-mentioned individuals. In this proposed system, this component is to come up with a solution to overcome this problem. By using this system travelers can enter for a new experience of going in a safari. With this system, it facilitates the travelers to take a snapshot or video periodically of the animal using computer vision and image processing. And the system will provide a brief introduction of the animal including important details of the animal. A cloud storage will be used to identify the patterns.

4.   The main research problem we discuss in this paper is there is not implemented any app yet to generate an optimal route for travelers in traveling national parks. To identify which path goes in the park to fulfill the traveler requirements is still doing manually. It is very time consuming and travelers cannot choose his path properly with less information in the park. Travelers didn't have any information about what are the suitable places to watch that they are interested in the park. As an example, if a traveler wants to watch birds and tanks in the park, there is not enough information to select the traveler which place is watch on the trip. If there is enough information is provided to the traveler, then the traveler can reduce his time and can fulfill his requirements.


**Main Objective**


*  To implement a complete safari traveling guidance mobile application with including animal identifying section, automatic comment rating section, map with temporary locations adding function and optimal path generator.


**Individual Objectives**

1.  To create a map for the national park and develop temporary locations adding function to the map.

2.  To create a comment and reviews categorizing and rating section to categorize comments as “good” and “bad” and add ranking to comments by the system automatically using machine learning and sentiment analysis algorithms

3.  To identify animals and get a description by the system by analyzing their movements and life patterns from a real time captured snap using image processing computer vision and machine learning.

4.  To generate best optimal routes in the park for the travelers according to their requirements by using machine learning algorithms.


**Summary of individual components**

**1. Map with Temporary Locations Adding Function (IT17053242)**
	
   1.1 Module description and system architecture:
       
   There are many mobile applications in the traveling industry with many features. But when it comes to safari traveling, there are few mobile applications to guide the safari tour. In these mobile applications, there is no specific function to travelers to watch animals live. When in a safari tour, travelers missing animals and they are not satisfying about the tour. To overcome from these difficulties, we proposed a mobile application to “YALA” National Park in Sri Lanka. In that application, we proposed a map with a temporary locations adding function as a solution for above difficulty. Because of this function, traveler can watch animals without wasting time and get locations where the animals are in that current time.

   In the procedure of the implementation, first part is to create the map. The map will be create using Google Maps API.

   The first method is to provide a map for the park and giving the facility to add temporary locations. The map will be creating using Google Map API and the temporary locations adding function will be creating using java code. Because of this users can view the map easily and they can fill the details in a simple form interface.
   
   Google Map API - Google maps API is available in android. With the Maps SDK for android, can add maps to any android application. The API automatically handles access to google maps servers. The API allows adding Tile Overlays, Ground Overlays, Markers and etc.
   Form data will store in a database and for that we are using Firebase Realtime Database to manage data. Because of this database, it can properly manage the data that filled in the form by the user.
   
   Firebase Realtime Database – Firebase realtime database is a cloud hosted database. Data is stored as JSON and synchronized in realtime to every connected client.
   For the notification sending purpose, we are using Firebase Cloud Messaging to send the details of the current location to the other user devices. This will send the location details and the form details to the other users. Because of that, other users can access the notification through the device to view the details.
   
   Firebase Messaging API – Firebase Cloud Messaging clients require devices running 4.1 or higher that also have the Google Play Store app installed, or and emulator running android 4.1 with Google API.
   
   After receiving the notification on the device, user can view by clicking the notification. Details will be open in Google Maps in the mobile application.
   
   1.2 Necessary Metadata:
   
    Operating System - Android
   
    Java Function
   
    Google Map API
   
    Firebase Realtime Database
   
    Firebase Cloud Messaging API
    
  
   1.3 Other Necessary Instructions:
    
    Still in the development process.
 
    1.) Download the code.
    
    2.) Open the project in android studio.
     
    3.) Build the gradle file.
    
    4.) Run the code with emulator.


   1.4 Dependencies (if any): 
   
     Compile SDK Version - 29
     
     firebase-auth:19.3.1
     
     firebase-database:19.4.0
     
     firebase-analytics:17.5.0
     
     firebase-messaging:20.2.4



 **2. Automatic Comment Categorizing and Rating System (IT17035286)**

   2.1 Module description and system architecture:
    
This module is built in a neural network, which is made by using a set of algorithms to recognize a relationship between given data through a process. In this component, the model has been trained using a set of positive and negative comments and feedback. Then using several naive bayes classification algorithms( MNB , Bernoulli , Logistic Regression, Multinomial...) the texts are classified as "Negative" and "Positive" .Then the system is calculating the accuracy using those algorithms and testing data and then the system is generating a specific score rate for the text.
    When a user enters a comment and a star rate, the system will analyses the comment using those classification algorithms and the system will categorize the comment as "Negative" or "Positive" by analyzing the pre trained data also and display the comment under categorized section with the system generated predicted score rate.

    
   2.2 Necessary Metadata:
       
     Mobile Operating System - Android
     
	 Pycharm
	 
     Python 3.7.7
     
	 Natural Language Toolkit Libraries
	 
	 Sklearn Libraries


   2.3 Other Necessary Instructions:
    
      Completed project
      
      1. Download the "Smart Safari Updated" project
       
	  2. Open the project in Android Studio
	  
	  3. Generate the apk file and get it into your mobile.
			             		      
	  4. Install and run the apk.


      Backend 
      
      1. Download and install pycharm and set the python version as 3.7.3
      
	  2. Deploy the "Backend" project in pycharm.
	  
	  3. Run the .py files.
	 

  2.4 Dependencies (if any):
    
      No dependencies.
      
    
 **3. Identify Animal with Knowledge (IT17095518)**
 
    
 3.1 Module description and system architecture:

Identifying the mammals from scanning, camera images or video is very challenging. Firstly, Created Database including mammals name and descriptions. That is relevant to the mammal’s images and includes a few pre-proceeded snaps for each mammal’s images to identify animals correctly. 
The implementation trained the model using machine learning by feeding the animal's image.
When the image is fed to the result then the result will be shown. 
That result will be calling the rest API and details of the relevant animal will be taken from the database.
Those details will be previewed in the interface.
Cloud storage providers are responsible for keeping the data available and accessible.
Then identified images using neural networks. (Recurrent neural network – RNN). 
Neural Network is one of the most popular machine learning algorithms at present. 
Then have an input that goes through a neural network and obtain an output. 
As the next step, comparing identified images with the trained data set and identifying the mammal. 
Then Load the identified mammal name and description into the client side.



 3.2 Necessary Metadata:
    
      Mobile Operating System - Android
      
	  Python
		
	  Java
		
	  Matlab
		
	  Tensorflow
		
	  Neural Network Model
   
    
 3.3 Other Necessary Instructions:
    
    Backend & API: Because of the git size limitation I was not able to push Full project in git, committed only controller, views and models inside the "Backend & API" in the folder.
    I have put the complete project on google drive and here attached link for any further purposes.
    
    (If any needed)
    Link- https://drive.google.com/drive/folders/1qD7iOzfEqNwHmA5XXEZ3i1YLe3aQDwc1?usp=sharing


    Android App : Because of the git size limitation I was not able to push Full project in git.committed only src Folder in android.

    
    
    
 3.4 Dependencies (if any):
    
    No dependencies.
    
    
    
**4. Optimal Path Generator (IT17025690)**

  
  4.1 Module description and system architecture:
    
Generate optimal route in the "Yaala" national park according to the traveler’s requirements using "A star algorithm". Get the traveler location and the traveler can select the destination (Bungalow). The traveler can select the animal category and path will be generated using that requirement.
   
  
 
  4.2 Necessary Metadata:
  
    Mobile operating system - Android
    
    Visual Studio Code
    
    Python 3.6.8
    
    PostgreSQL 12

  

  4.3 Other Necessary Instructions:
  
   Complete project:
   
     1. Download the "Smart Safari3" project
     
	 2. Open the project in Android studio
	 
	 3. Run the project in virtual device

   Backend:
   
     1. Download and install Visual Studio Code
     
	 2. Install dependencies in "requirements.txt" file.
	 
	 3. Deploy the "Flask_API" project in VS code.
	 
	 4. Run the .py file.

   Database: 
   
     1. Download and install PostgreSQL 12.
     
	 2. Import database to the PostgreSQL. 

  

  4.4 Dependencies (if any):
  
      No dependencies.
