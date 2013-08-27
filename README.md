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

* Write scripts for bash, command line, etc to start Jackhammer
* Complete file management, that means not just creating files but updating, deleting, potentially moving files (even
  though I'm not sure if I can determine this from the filesystem)
* Nicer configuration management and profiles
* Support for more complex node structures and XML serialization/deserialization
* Play well with Adobe vlt, support Adobe WEM structures
* Interactive shell to browse the repository
* And many more...

## Usage

Jackhammer is a command line application. Here's a few samples of how to use it:

### Test Connection

Test connection with default profile (connecting to a CRX instance):

    $ jackhammer connect
        Profile: ImmutableProfile{name=<system default>, host=http://localhost:4402/crx/server, username=admin, password=yes}
        Session: org.apache.jackrabbit.jcr2spi.SessionImpl@22b5d678

### Profiles

Profiles store connection configuration. List profiles:

    $ jackhammer profile
    Available ProfileFacade:

    Default Profile:
        <system default> admin <password set> http://localhost:4402/crx/server

Add a profile:

    $ jackhammer profile add test -h http://localhost:4402/crx/server -u admin -p admin
    Saving profile test...
    name = test

Set default profile:

    jackhammer profile default test


### Listing

Jackhammer can list nodes in the JCR:

    $ jackhammer list /
    / [rep:root]
      rep:repoPolicy       [rep:ACL]
      META-INF             [nt:folder]
      crx                  [nt:unstructured]
      bin                  [nt:folder]
      rep:policy           [rep:ACL]
      jcr:system           [rep:system]
      var                  [sling:Folder]
      libs                 [nt:folder]
      etc                  [sling:Folder]
      apps                 [nt:folder]
      content              [sling:OrderedFolder]
      home                 [rep:AuthorizableFolder]
      tmp                  [sling:Folder]

### Uploading

And the primary reason you'll want to use Jackhammer: watching folders and uploading files. Let's monitor the test
folder and mirror all changes from it to ``/tmp``:

    jackhammer upload watch test --to /tmp
    Watching /Users/jakobk/Documents/personal/jackhammer/cli/test and uploading changes to /tmp

In a different terminal, we create a new folder in the test folder:

    mkdir test/jackhammer

And Jackhammer promptly responds with:

    + jackhammer/

Same goes for files:

    echo "Jackhammer is awesome!" > test/jackhammer/awesome.txt

Results in:

    + jackhammer/awesome.txt

And now we can check if they exist:

    $ jackhammer list /tmp/jackhammer
    Connecting to http://localhost:4402/crx/server with username admin...
    /tmp/jackhammer [nt:folder]
      awesome.txt          [nt:file]
