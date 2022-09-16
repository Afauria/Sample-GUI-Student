package com.afauria.ui;

import javax.swing.BoxLayout;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JProgressBar;

import com.afauria.entity.ClassScoreCount;

public class ClassScoreCountPanel extends JPanel {

	private ClassScoreCount classScoreCount;

	public ClassScoreCountPanel(ClassScoreCount classScoreCount) {
		super();
		this.classScoreCount = classScoreCount;
		setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));
		add(new JLabel(classScoreCount.getClazz()));
		int total = classScoreCount.getTotal();
		add(getRow("90-100: ", classScoreCount.getM90() + "人", classScoreCount.getM90() * 100.0f / total));
		add(getRow("80-89: ", classScoreCount.getM80() + "人", classScoreCount.getM80() * 100.0f / total));
		add(getRow("60-79: ", classScoreCount.getM60() + "人", classScoreCount.getM60() * 100.0f / total));
		add(getRow("0-59: ", classScoreCount.getM0() + "人", classScoreCount.getM0() * 100.0f / total));
	}

	private JPanel getRow(String range, String count, float rate) {
		JPanel panel = new JPanel();
		panel.add(new JLabel(range));
		JProgressBar p = new JProgressBar();
		p.setValue((int) rate);
		panel.add(p);
		panel.add(new JLabel(count));
		panel.add(new JLabel(rate + "%"));
		return panel;
	}
}
