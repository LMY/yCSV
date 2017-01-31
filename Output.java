
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import javax.swing.*;


public class Output implements ActionListener
{
	private static Output instance = null;
	
	private JFrame frame;
	private JTextArea textarea;
	private JButton button_clear;
	private JButton button_save;
	
	
	private Output(JFrame frame)
	{
		this.frame = frame;
		this.textarea = null;
		this.button_clear = null;
		this.button_save = null;
	}
	
	public static void Initialize(JFrame frame)						{ Initialize(frame, false); }
	public static void Initialize(JFrame frame, boolean force)		{ if (instance == null || force) instance = new Output(frame); }
	public static Output getInstance()								{ return instance; }
	
	public void MessageBox(String text)						{ MessageBox("Debug", text); }
	public void MessageBox(String title, String text)		{ JOptionPane.showMessageDialog(frame, text, title, JOptionPane.INFORMATION_MESSAGE); }
	public void outline(String line)						{ out(line+"\n"); }
	public void out(String line)							{ if (textarea != null) textarea.setText(textarea.getText()+line); }
	
	
	public boolean Save(String filename)
	{
		BufferedWriter out = null;
		try { out = new BufferedWriter(new FileWriter(new File(filename))); }
		catch (IOException e) { return false; }		
		
		try { out.write(textarea.getText()); }
		catch (IOException e) { return false; }
		
		try { out.close(); }
		catch (IOException e) { return false; }
		
		return true;		
	}
	
		
	
	public JPanel CreateOutputPanel()
	{
		JPanel panel = new JPanel();
		panel.setLayout(new BorderLayout());
		
		textarea = new JTextArea();
		textarea.setEditable(false);
		
		JScrollPane textareaScroller = new JScrollPane(textarea, JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED, JScrollPane.HORIZONTAL_SCROLLBAR_AS_NEEDED);
		
		panel.add(textareaScroller, BorderLayout.CENTER);
		
		JPanel south = new JPanel();
		south.setLayout(new FlowLayout());
		
		button_clear = new JButton("Clear");
		button_clear.addActionListener(this);
		button_save = new JButton("Save");
		button_save.addActionListener(this);
		
		south.add(button_clear);
		south.add(button_save);
		panel.add(south, BorderLayout.SOUTH);
		
		
		return panel;		
	}
	
	
	
    public void actionPerformed(ActionEvent e)
    {
		if (e.getSource().equals(button_clear))
			textarea.setText("");
		else if (e.getSource().equals(button_save)) {
			final JFileChooser fc = new JFileChooser();
			fc.setCurrentDirectory(new File("."));

        	if (fc.showOpenDialog(frame) == JFileChooser.APPROVE_OPTION)
        		Save(fc.getSelectedFile().getAbsolutePath());
		}
    }
}
