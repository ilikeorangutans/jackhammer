# Jackhammer - JCR Power Tools

## Introduction

Jackhammer is a command line app that connects to Jackrabbit based JCR repositories to perform file upload or download
tasks. Other functionality might follow if I find something that is useful.

Currently Jackhammer connects to Apache Jackrabbit (tested with Jackrabbit 2.6.3) and Adobe CRX 2.x.

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

