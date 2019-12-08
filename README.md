# SoftServeTask
Task from Soft Serve: testing [Reddit](http://reddit.com) site

## Steps to run tests:
1. Create **credentials.cred** file into main directory of project (nearby pom.xml file)
2. Open that file via any text editor, like **notepad**
3. Enter text such format:
  > {"username":"username","password":"password"}
4. Close file and open the project;
5. Import all dependencies;
6. Try to run tests (different variants):
    * Run **groups.xml** file;
    * Run **TaskSuite.xml** file;
    * Run **MainTest.java** file.


## Implemented features:
* User log in;
* See subscribed subreddits;
* View one of subscribed subreddits;
* Able to create comment on a post;
* Able to upvote a post;
* Able to downvote a post.

## Future plans:
* Complete driver factory;
* Add data provider;
* Add some test of features.

## Remark:
If you run test of creating comment, you can get fail result, because you receive different times creating comment and current local time.

**To solve that problem, rerun tests again :)**
