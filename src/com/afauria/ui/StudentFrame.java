package com.afauria.ui;

import java.awt.BorderLayout;
import java.awt.EventQueue;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;
import java.util.ArrayList;
import java.util.List;
import java.util.Vector;

import javax.swing.BoxLayout;
import javax.swing.ComboBoxModel;
import javax.swing.DefaultComboBoxModel;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.border.EmptyBorder;
import javax.swing.event.ListDataListener;
import javax.swing.event.MenuEvent;
import javax.swing.event.MenuListener;

import com.afauria.StudentTableModel;
import com.afauria.dao.StudentDao;
import com.afauria.entity.ClassScoreCount;
import com.afauria.entity.Student;

public class StudentFrame extends JFrame {

	private JPanel contentPane;

	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					StudentFrame frame = new StudentFrame();
					frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	public StudentFrame() {
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 450, 300);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		contentPane.setLayout(new BorderLayout(0, 0));
		setContentPane(contentPane);
		init();
	}

	private List<Student> allList = new ArrayList<>();
	private JTable table = new JTable();
	private StudentTableModel model = new StudentTableModel(allList);
	private StudentDao mDao = new StudentDao();
	private JComboBox<String> clazzCombo = new JComboBox<>();
	private JComboBox<String> courseCombo = new JComboBox<>();

	JScrollPane scrollPane = new JScrollPane(table);

	private void init() {
		table.setModel(model);
		table.setAutoCreateRowSorter(true);

		Vector<String> clazzVector = new Vector<>(mDao.getAllClazz());
		clazzVector.add(0, "所有");
		clazzCombo.setModel(new DefaultComboBoxModel<>(clazzVector));
		clazzCombo.addItemListener(new ItemListener() {

			@Override
			public void itemStateChanged(ItemEvent e) {
				if (1 == e.getStateChange()) {
					String str = (String) clazzCombo.getSelectedItem();
					filterTableByClass(str);
				}
			}
		});
		Vector<String> courseVector = new Vector<>();
		courseVector.add("所有");
		courseVector.add("chinese");
		courseVector.add("math");
		courseVector.add("english");
		courseCombo.setModel(new DefaultComboBoxModel<>(courseVector));
		courseCombo.addItemListener(new ItemListener() {

			@Override
			public void itemStateChanged(ItemEvent e) {
				if (1 == e.getStateChange()) {
					String str = (String) courseCombo.getSelectedItem();
					filterTableByCourse(str);
				}
			}
		});
		JPanel btnPanel = new JPanel();
		btnPanel.add(clazzCombo);
		btnPanel.add(courseCombo);
		
		contentPane.add(btnPanel, BorderLayout.NORTH);
		contentPane.add(scrollPane, BorderLayout.CENTER);
		updateTable();
		
		JMenuBar menuBar = new JMenuBar();
		JMenu m = new JMenu("说明");

		m.addMenuListener(new MenuListener() {
			
			@Override
			public void menuSelected(MenuEvent e) {
				JOptionPane.showMessageDialog(StudentFrame.this, "点击单元格修改数据\n点击表头排序");
			}
			
			@Override
			public void menuDeselected(MenuEvent e) {
				// TODO Auto-generated method stub
				
			}
			
			@Override
			public void menuCanceled(MenuEvent e) {
				// TODO Auto-generated method stub
				
			}
		});
		menuBar.add(m);
		setJMenuBar(menuBar);
	}

	private void filterTableByCourse(String str) {
		if (str.equals("所有")) {
			clazzCombo.setSelectedIndex(0);
			model.setList(allList);
			model.fireTableDataChanged();
			scrollPane.setViewportView(table);
			table.setVisible(true);
			return;
		}
		List<ClassScoreCount> classList = mDao.getCountByCourse(str);
		JPanel p = new JPanel();
		p.setLayout(new BoxLayout(p, BoxLayout.Y_AXIS));
		for (ClassScoreCount c : classList) {
			p.add(new ClassScoreCountPanel(c));
		}
		scrollPane.setViewportView(p);
		table.setVisible(false);
		contentPane.updateUI();
	}

	private void filterTableByClass(String str) {
		if (str.equals("所有")) {
			courseCombo.setSelectedIndex(0);
			model.setList(allList);
			model.fireTableDataChanged();
			return;
		}
		List<Student> filterList = new ArrayList<>();
		for (Student s : allList) {
			if (str.equals(s.getClazz())) {
				filterList.add(s);
			}
		}
		model.setList(filterList);
		model.fireTableDataChanged();
	}

	private void updateTable() {
		allList.clear();
		allList.addAll(mDao.getStudents());
		model.fireTableDataChanged();
	}
}
