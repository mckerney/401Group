4/27 update /n
Got the sliders more or less working, the time slider doesn't update position while playing, so that needs some attention. Got a basic style sheet written in css loaded into the sourse folder as well as some direct style line code you'll see in the .java. Put the grid back in the bottom row and will work on formatting after a break. there's a ton of color and button customization we can do with css as well.\n

update:2 \n
So I laid out the code that more or less outlines what we had discussed on Monday evening. I included the src file with the .java as well as a png for the play button and some sample media, the file path in the start of the program will need to be changed for the media if you're testing it on a different system so be aware. I did have to use a BorderPane instead of an HBox for the controls at the bottom as there was no way to stack elements in a HBox. I made a basic play button so we can get an idea of what we have to work with, it does look like even though the picture is a circle with an alpha channel surrounding it the method to apply a graphic to the button still makes it appear recangular, not a big deal but worth noting. We still need quite a bit of functionality and I have code for the original HBox setup commented out so ignore that, I'll clean it up. Paramount I think is figuring out how to queue a new file when you press the file button so we need to get that rolling ASAP as it may change some logic flow with how we're feeding the scenes.
4/25/18 -Jim

update:1 \n
I uploaded MP3Player.java. I got a basic framework that should so how we need to handle the class flow and polymorphism so we can get the MediaPlayer methods present in our instance of the player. Feel free to check it out and add anything. I had a few extra hours today so I got it up, I will try and put a few more hours to flesh out the framework tomorrow and see if we can get some button graphics rolling as well.
-Jim

# 401Group
Final Group Project for CISP 401
Group Members:
Jim McKerney, Alek Hernes, Charles Mancuso, Drew Lewis
