import java.applet.*;
import java.awt.*;
import java.util.*;

//
//
// LocationDemo - Demonstrates the use of location/exit objects
//
// Last modification date : October 08, 1997
//
public class Main extends Applet
{
	Location currentLocation;
	String command;

	TextField commandInput;
	TextArea  displayOutput;
	Button    buttonInput;

	// Constructor
	public Main()
	{
		super();
	}

	// Initialisation method
	public void init()
	{
		super.init();

		// Define colours
		setBackground(Color.white);
		setForeground(Color.black);

		Panel appletPanel = new Panel();

		// Use a border layout
		BorderLayout b = new BorderLayout();
		appletPanel.setLayout (b);
		add(appletPanel);
		
		// Define UI items
		commandInput  = new TextField(20);
		displayOutput = new TextArea( 20, 100); // 20 rows x 100 chars
		buttonInput   = new Button("Input");
		Panel inputPanel = new Panel();

		// Add components to our layout / panels
		inputPanel.add(commandInput);
		inputPanel.add(buttonInput);
		appletPanel.add("North", displayOutput);
		appletPanel.add("South", inputPanel);

		/*
		 * 
		 * Room locations. 
		 * Labled individually for ease of use.
		 * 
		 */
		
		// Beginning of game, might evolve into tutorial level.
		Location l1 = new Location ("Main Street", "You are standing on a sidewalk in front of a sushi bar, not quite satisfied by the meal you just consumed.\nYou're tired, so you decide to go home.");
		
		// Home, where you're able to drink some sake (which will make you drunk), or sleep, where each scenario
		// will lead to you to being kidnapped.
		Location l2 = new Location ("Your apartment", "You walk into your apartment, which is cluttered from your roommate who is noticebly absent.\nOn the table is a lone bottle of sake, and a cup.\nYou're also incredibly tired.");
		
		Location l3 = new Location ("You decide not to drink the sake, so you, instead, go to bed.");

		Location l4 = new Location("You take a swallow of the alcohol, and realize how good Japanese drinks were.\nYou drink so much, that you collapse on the floor drunk.");
		
		Location l5 = new Location("You are suddenly awakened, surrounded by 4 masked men and in a van.\nThey were of medium build, all wearing dark blue suits, and holding machine guns.\nYou are, somehow, level-headed, and wondering if you should ask what's going on, or attempt to flee..");
		
		Location l6 = new Location("'Where am I?' you ask.\nThey were all silent, not even glancing your way.");
		
		Location l7 = new Location("You take a deep breath, and slam your body against the back door.\nIt was bolted shut, and didn't budge an inch.\nThe men, curiously, didn't even move.");
	    
		Location l8 = new Location("After a few minutes of awkward silence, the van suddenly stops, the men open the door, and shove you into a large room.\nAs you stumble into this large antichamber, you look behind you as the van speeds off, and a large vault-like door shuts.\nBut not before you hear a large explosion.");
		
		Location l9 = new Location("Thanks for showing interest in this immense project, that will dramatically increase in size over the coming weeks.\n");
		// Exit for l1 (Main Street)
		l1.addExit (new Exit(Exit.GO, l2));
        
		// Exit for l2 (your apartment)
		l2.addExit (new Exit(Exit.DRINK, l4));
		l2.addExit(new Exit(Exit.SLEEP, l3));	
		
		// Exit for l3 (sleeping)
		l3.addExit(new Exit(Exit.WAKE, l5));
		
		// Exit for l4
		l4.addExit(new Exit(Exit.WAKE, l5));
		
		// Exit for l5 (in the van)
		l5.addExit(new Exit(Exit.ASK, l6));
		l5.addExit(new Exit(Exit.FLEE, l7));
		
		// Exit for l6 (asking where you are)
		l6.addExit(new Exit(Exit.TBC, l8));
		
		// Exit for l7 (Attempting to flee)
		l7.addExit(new Exit(Exit.TBC, l8));
		
		// Exit for l8 (VAULT)
		l8.addExit(new Exit(Exit.VENTURE, l9));

		// Set up room locations
		currentLocation = l1;

		// Show first location
		showLocation();
		repaint();
	}

	private void showLocation()
	{
		// Show room title
		displayOutput.appendText( "\n" + currentLocation.getTitle() + "\n" );
		displayOutput.appendText( "\n" );
		
		// Show room description 
		displayOutput.appendText( currentLocation.getDescription() + "\n" );

		// Show available exits
		displayOutput.appendText( "\nWhat will you do? \n" );
		for (Enumeration e = currentLocation.getExits().elements(); 
e.hasMoreElements();)
		{
			Exit an_exit = (Exit) e.nextElement();
			displayOutput.appendText (an_exit + "\n");
		}		
		
	}

	public boolean action (Event evt, Object focus)
	{
		String command;

		// Was a button pressed ? 
		if (evt.target instanceof Button)
		{
			// Obtain string
			command = commandInput.getText();

			// Don't parse blank commands
			if (command.length() == 0)
				return true;

			// Convert to uppercase for comparison
			command = command.toUpperCase();

			// Search for an exit match
			for (Enumeration e = currentLocation.getExits().elements(); 
e.hasMoreElements();)
			{
				Exit an_exit = (Exit) e.nextElement();

				if ( (an_exit.getDirectionName().compareTo(command) == 0) ||
					 (an_exit.getShortDirectionName().compareTo(command) == 0 )
				   )
				{
					// Set location to the location pointed to by exit
					currentLocation = an_exit.getLeadsTo();

					// Show new location
					showLocation();

					// Clear text area
					commandInput.setText (new String());

					// Event handled
					return true;
				}				
			}

			// If code reaches here, direction is invalid
			displayOutput.appendText ("\nHuh? Invalid direction!\n");

			// Clear text area
			commandInput.setText (new String());

			// Event handled
			return true;
		}
		// Event not handled
		else return false;
	}

}