package com.afauria;

import java.util.ArrayList;
import java.util.List;

import javax.swing.table.AbstractTableModel;

import com.afauria.dao.StudentDao;
import com.afauria.entity.Student;

public class StudentTableModel extends AbstractTableModel {
	private List<Student> list = new ArrayList<>();
	private String[] columnNames = { "学号", "名称", "班级", "语文", "数学", "英语", "总分", "语数外平均分" };

	public StudentTableModel(List<Student> list) {
		super();
		this.list = list;
	}

	public List<Student> getList() {
		return list;
	}

	public void setList(List<Student> list) {
		this.list = list;
	}

	@Override
	public int getRowCount() {
		return list.size();
	}

	@Override
	public int getColumnCount() {
		return columnNames.length;
	}

	@Override
	public String getColumnName(int column) {
		return columnNames[column];
	}

	@Override
	public Object getValueAt(int rowIndex, int columnIndex) {
		Student s = list.get(rowIndex);
		switch (columnIndex) {
		case 0:
			return s.getStudNum();
		case 1:
			return s.getName();
		case 2:
			return s.getClazz();
		case 3:
			return s.getChinese();
		case 4:
			return s.getMath();
		case 5:
			return s.getEnglish();
		case 6:
			return s.getTotalScore();
		case 7:
			return String.format("%.2f", s.getAvgScore());
		}
		return "";
	}

	@Override
	public boolean isCellEditable(int rowIndex, int columnIndex) {
		return (columnIndex >= 3 && columnIndex <= 5) || columnIndex == 1;
	}

	private StudentDao mDao = new StudentDao();

	@Override
	public void setValueAt(Object aValue, int rowIndex, int columnIndex) {
		Student s = list.get(rowIndex);
		int id = s.getId();
		if (columnIndex == 1) {
			String name = "name";
			mDao.updateScore(id, aValue.toString(), name);
			s.setName(aValue.toString());
		} else {
			int v = Integer.valueOf(aValue.toString());
			if (v >= 0 && v <= 100) {
				if (columnIndex == 3) {
					String course = "chinese";
					mDao.updateScore(id, v, course);
					s.setChinese(v);
					//TODO 总分、平均分没有跟着更新
				} else if (columnIndex == 4) {
					String course = "math";
					mDao.updateScore(id, v, course);
					s.setMath(v);
				} else if (columnIndex == 5) {
					String course = "english";
					mDao.updateScore(id, v, course);
					s.setEnglish(v);
				}
			}
		}
	}
}
