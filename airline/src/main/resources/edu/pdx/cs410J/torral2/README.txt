The purpose of this program is made to be a simple way to store flights associated with certain airlines.
For example, if you were to be traveling via plane soon, it would be a good idea to run this with information
about the flight number, the departing and arrival airport code, the departure date and time, as well as
the arrival date and time, and the name of the airline you will be flying with. Just so you can stay extra organized!

This program was written by Christian Torralba for the Advanced Java class (CS410P) taught by David Whitlock
Winter term of 2023.

This program will take necessary information related to an Airline and its flights. Each flight will have a flight number,
a three letter source code for the airport the flight is leaving from, a departure date and time,  an arrival date and time,
and three letter arrival airport code.

The date and time must be passed in with the following format: MM/dd/yyyy h/mm AM|PM
An example of this could be: 07/26/2001 7:56 pm or 12/01/1996 12:03 AM
Be careful to have the am/pm separated from the actual time, as if you do not then an error will occur.
This program does NOT support 24-hour time format.

If you chose to use the option to write to an external file, do keep in mind that if the file does not exist, one will
be created for you. If a file already does exist, and it contains misconfigured information, the program will NOT write to
the file, but exit instead.

The pretty print option will work in a similar way. If the file does not exist, one will be created. If a file does exist,
but with information in it already, it will be overwritten, please be aware of this before you run the program.

The source and arrival airport codes must be EXACTLY three characters, no numbers allowed. If it is not, then the program
will exit and prompt you to run it again.

To see command line usage for this program please run with no arguments.