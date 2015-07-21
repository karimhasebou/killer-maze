package puzzle2;

import javax.swing.*;
import java.awt.*;
import java.io.*;
import java.awt.event.*;

public class Console extends JPanel implements KeyListener{
	private Window window;
	private JTextArea display;
	private JTextField input;

	public Console(Window window){
		super(new BorderLayout());
		this.window = window;

		display = new JTextArea();
		input = new JTextField();


		display.setEditable(false);
		display.setPreferredSize(new Dimension(200,getHeight()-input
			.getHeight()));

		input.setFocusable(true);
		input.addKeyListener(this);

		Color darkPurple = new Color(63,25,44);
		input.setBackground(darkPurple);
		display.setBackground(darkPurple);
		display.setForeground(Color.WHITE);
		input.setForeground(Color.WHITE);



		add(display);
		add(input,BorderLayout.PAGE_END);

	}

	/**@param input text to  display on console
	*/
	public void println(String input){
		display.append(input+"\n");
	}

	public void keyTyped(KeyEvent e){
	}

	public void keyPressed(KeyEvent e){
		int keyCode = e.getKeyCode();
		if(keyCode == KeyEvent.VK_ENTER){
			String command = input.getText();
			println(command);
			parseInput(command);
			input.setText("");
		}
	}

	public void keyReleased(KeyEvent e){

	}

	public void parseInput(String input){
		String[] formattedInput = input.split("\\s+");

		if(formattedInput[0].equals(OPEN)){
			int shortestSideLength = Math.min(window.getWidth()-getWidth(),getHeight());
			Scene scene = new Scene(formattedInput[1],shortestSideLength);
			window.putMainScreen(scene);
			scene.requestFocus();
		}else
			println("Invalid command");
	}

	private final static String OPEN = "open";
}
