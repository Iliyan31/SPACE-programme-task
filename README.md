# SPACE-programme-task
### Description
This project is console application which is meant for space shuttle launch! <i>&#128640;</i> \
To use the app there are some things we need to know first! \
\
There is support for **English** and **German** and for each language there are different input commands (they have the same functionality, but in different language)

The supported operations are: 
|       English      |        German       |                                             Description                                               |
|:------------------:|:-------------------:|-------------------------------------------------------------------------------------------------------|
| `start`            | `start`             | Starts the application and setups the needed configurations.                                          |
| `stop`             | `stoppen`           | Stops the application.                                                                                |
| `EN`               | `EN`                | Switches to English.                                                                                  |
| `GE`               | `GE`                | Switches to German.                                                                                   |
| `find_perfect_day` | `perfekten_tag`     | Finds the perfect day to launch the space shuttle according to the given file.                        |
| `generate_report`  | `bericht_generieren`| Generates csv report with aggregated statistics data according to the parameters from the input file. |
| `send_email`       | `email_senden`      | Sends email with the generated report and text with the perfect day to launch to the given receiver using SMTP connection. 

**Note:** You can only use the commands supported by the language you have switched to! \
\
For SMTP connection as a host provider, Outlook's host is being used on port 587.

### To run the application you need to have:  
- *Java version 19*

**For SMTP connection:**
- *JavaMail API* which is provided in the lib folder.
- *Activation version 1.1.1* in order to make JavaMail API run, which is also provided in the lib folder.

**And for unit tests:**
- *JUnit5.8.1*
