# Maven Badge Generator

## Purpose
This is a very simple Spring Boot application that will generate the well known Maven badges as PNG images.

Although there are maven badge generators available in the internet, I needed one that I can install
within the company premises that I can configure for the local Nexus
to generate badges that are displayed on the internal Gitlab Readme files.

It is highly inspired by this [maven badge project](https://github.com/jirutka/maven-badges) that is written in Ruby
but since I don't have any experience in hosting ruby applications in a secure way
I created this Java application.

## Running
You can clown the repo and with the command
```
$ mvn package
```
 
you can create a runnable jar that can then be copied to the target system.
 
You can run it by invoking
```
$ java -jar "-Dmaven.url=https://<yourNexusHere>" MavenBadgeApplication.jar
```
 
Usually Sonatype Nexus supports a web service to query the repo.
It looks something like this:

https://search.maven.org/solrsearch/select?q=g:org.cognitor.cassandra+AND+a:cassandra-migration&rows=1&core=gav

The url you have to provide here is everything up to the /solrsearch... part.

## Usage

Once the application is started you can generate URLs that follow this scheme:
```
http://localhost:8080/maven/<groupId>/<artifactId>/badge.png
```
This will return the picture that can be referenced inside any HTML or Markdown page.


This application uses [Shield.io](https://www.shield.io) to generate the badge.