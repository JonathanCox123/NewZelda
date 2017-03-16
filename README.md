# NewZelda

The NewZelda game is my early attempt at trying to make a multi-level, 2D game that incorporated several key aspects of modern game programming including bounding boxes, "AI" generation and movement, as well as asset recording (sprites, points, damage, etc).

The game utilizes Touhou anime characters and in truth, I was inspired by this kid Brian Ramos in my Senior year of High School who was also attempting to do the same thing but a much deeper extent.

# Development

I started development of this game with I think 7 levels and I just never really fully completed it. Basic functionality is there and can easily be expanded on. Assets such as Touhou sprites and environment images if added should be meticulously edited in Photoshop.

As I had started developing this at like 16 years old give some leeway when it comes how it was coded out ;) I tried using parent and child classes whenever appropriate. All of the core files should be smack-dab in the directory and intuitively named (i.e. GraphicsPanel.java controls screen rendering, Hero is the main character's damage, health,etc, and Lazer controls where the lazer is at all times and how it gets terminated). 

All site map levels are in the /LEVELS directory as .txt files. I will eventually add which sprite is which because I'm looking at this code 4 years later I don't know off the top of my head but don't worry it will be done!

What my vision for this is illustrated graphically in the /Sprites/NewZeldaGrid.psd file where I also pinpointed which level is supposed to be where.

# Installation

Just clone or download the repo into a directory and enter "Java Game". Everything should already be precompiled. If you want to go a step further you can convert this repo into a .jar file and run an executable. Just haven't had time to get around to doing that in Eclipse.
