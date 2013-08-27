# Jackhammer - JCR Power Tools

## Introduction

Jackhammer is a command line app that connects to Jackrabbit based JCR repositories to perform file upload or download
tasks. Other functionality might follow if I find something that is useful.

Currently Jackhammer connects to Apache Jackrabbit (tested with Jackrabbit 2.6.3) and Adobe CRX 2.x.

## Use case

Jackhammer is written primarily for developers that work with JCR based systems. For example, with Adobe CQ (or WEM or
whatever it is called now) your choices are either [CRXDE](https://www.day.com/day/en/products/crx/download/downloadcrxde.html)
, a heavy Eclipse based IDE (which hasn't been updated since 2011), or [CRXDE lite](http://dev.day.com/docs/en/crx/current/developing/development_tools/developing_with_crxde_lite.html),
which runs in your
 browser and is rather limited in terms of editing functionality. Plus, you always have the problem that you need to
 download your changes from the JCR using the [vlt tool](http://dev.day.com/docs/en/crx/current/how_to/how_to_use_the_vlttool.html)
 (which I personally don't like using).

At the end of the day developers want to use the editor that makes them the most productive and they don't want to
perform extra steps to get changes *into* the system and *out* of the system into version control. Jackhammer
is built exactly for this purpose. It allows you to automatically upload changes from your filesystem into a running
JCR instance.

## Goal

As of now, Jackhammer uploads and downloads files from the JCR. It's all still in development, but kind of works. With
Jackhammer you can:

* Download single files from a JCR
* Upload a single file into the JCR
* Watch a local directory for changes. New local directories, and files will automatically be created on the server.

Planned functionality:

* Complete file management, that means not just creating files but updating, deleting, potentially moving files (even
  though I'm not sure if I can determine this from the filesystem)
* Nicer configuration management and profiles
* Support for more complex node structures and XML serialization/deserialization
* Play well with Adobe vlt, support Adobe WEM structures
* Interactive shell to browse the repository
* And many more...

