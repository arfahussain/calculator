package com.example.hello;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.*;
import org.springframework.boot.autoconfigure.*;
import org.springframework.stereotype.*;
import org.springframework.web.bind.annotation.*;

import java.awt.BorderLayout;
import java.awt.Container;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
 
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JTextField;

@Controller
@EnableAutoConfiguration
@SpringBootApplication
 
public class HelloApplication extends JPanel implements ActionListener {
 @RequestMapping("/")
	@ResponseBody
	String home() {
		return "Hello World!";
	}
  private JTextField disp = new JTextField("0");
  private double res = 0;
  private String op= "=";
  private boolean cal = true;
 
  public HelloApplication() {
    setLayout(new BorderLayout());
 
    disp.setEditable(false);
    add(disp, "North");
 
    JPanel panel = new JPanel();
    panel.setLayout(new GridLayout(4, 4));
 
    String buttonLabels = "789/456*123-0.=+";
    for (int i = 0; i < buttonLabels.length(); i++) {
      JButton b = new JButton(buttonLabels.substring(i, i + 1));
      panel.add(b);
      b.addActionListener(this);
    }
    add(panel, "Center");
  }
 
  public void actionPerformed(ActionEvent evt) {
    String cmd = evt.getActionCommand();
    if ('0' <= cmd.charAt(0) && cmd.charAt(0) <= '9' || cmd.equals(".")) {
      if (cal)
        disp.setText(cmd);
      else
        disp.setText(disp.getText() + cmd);
      cal = false;
    } else {
      if (cal) {
        if (cmd.equals("-")) {
          disp.setText(cmd);
          cal = false;
        } else
          op= cmd;
      } else {
        double x = Double.parseDouble(disp.getText());
        calculate(x);
        op= cmd;
        cal = true;
      }
    }
  }
 
  private void calculate(double n) {
    if (op.equals("+"))
      res += n;
    else if (op.equals("-"))
      res -= n;
    else if (op.equals("*"))
      res *= n;
    else if (op.equals("/"))
      res /= n;
    else if (op.equals("="))
      res = n;
    disp.setText("" + res);
  }
 
  public static void main(String[] args) {
      SpringApplication.run(HelloApplication.class, args);
    JFrame.setDefaultLookAndFeelDecorated(true);
    JFrame frame = new JFrame();
    frame.setTitle("Calculator");
    frame.setSize(200, 200);
    frame.addWindowListener(new WindowAdapter() {
      public void windowClosing(WindowEvent e) {
        System.exit(0);
      }
    });
 
    Container contentPane = frame.getContentPane();
    contentPane.add(new HelloApplication());
    frame.show();
  }
}