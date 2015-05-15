XLicenser REST API
==================

Welcome to the XLicenser REST API source code! 

From this repository you can build the licensing server REST API. 

Prerequisities
--------------
- Latest version of Spring - Eclipse
- Apache Tomcat 7

Quickstart
----------
The XLicenser API can be deployed once Apache Tomcat7 is running.

- Using the Maven build configuration build the Spring project to **.war** file
- Copy the **.war** file to the webapps location in your Tomcat directory
- The project uses OAuth2 authentication so in order to test the API you will require to follow the authentication process

Instead of modifying **global.props**, you can create **user.props**, more information [here](https://github.com/KeenSoftwareHouse/SpaceEngineers/wiki/Initial-setup).

How to contribute
-----------------

One way to contribute changes is to send a GitHub [Pull Request](https://help.github.com/articles/using-pull-requests).

**To get started using GitHub:**

- Create your own XLicenser API **fork** by clicking the __Fork button__ in the top right of this page.
- [Install a Git client](http://help.github.com/articles/set-up-git) on your computer.
- Use the GitHub program to **Sync** the project's files to a folder on your computer.
- Open up the project in Spring.
- Modify the source codes and test your changes.
- Using the GitHub program, you can easily **submit contributions** back up to your **fork**.  These files will be visible to all subscribers.
- When you're ready to send the changes for review, simply create a [Pull Request](https://help.github.com/articles/using-pull-requests).

Common issues
-------------

None at the moment
