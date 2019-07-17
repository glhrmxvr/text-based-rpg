package gui;

import java.awt.Color;

import javax.swing.BorderFactory;
import javax.swing.BoxLayout;
import javax.swing.JLabel;
import javax.swing.JPanel;

import characters.Enemy;

public class EnemyStatusPanel extends JPanel{
    
	private JLabel name;
	private JLabel health;

	public EnemyStatusPanel() {
            setBackground(Color.black);
            setBorder(BorderFactory.createLineBorder(Color.black, 2));
            setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));

            name = new JLabel("Name: ");
            name.setForeground(Color.white);
            health = new JLabel("Health: ");
            health.setForeground(Color.white);
            add(name);
            add(health);
	}
	
	public void refresh(Enemy enemie) {
            name.setText("Nome: " + enemie.getName());
            health.setText("Health: " + enemie.getHealth());
	}

	public void clear() {
            name.setText("Name: ");
            health.setText("Health: ");
	}
}
