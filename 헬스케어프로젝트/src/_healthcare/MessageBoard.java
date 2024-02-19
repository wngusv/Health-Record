package _healthcare;

import java.awt.BorderLayout;
import java.awt.Component;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import javax.swing.DefaultCellEditor;
import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JToggleButton;
import javax.swing.SwingConstants;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableCellEditor;
import javax.swing.table.TableCellRenderer;

import dbutil.MySqlConnectionProvider;

public class MessageBoard extends JFrame {
	private DefaultTableModel tableModel;
	private JTable table;
	private String loginId;

	public MessageBoard(String loginId) {
		this.loginId = loginId;
		setTitle("게시판");
		setSize(600, 400);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		System.out.println("로그인한 ID:" + loginId);
		initializeTable();
		addComponents();
		loadMessagesFromDatabase();
	}

	private void initializeTable() {
		tableModel = new DefaultTableModel(new Object[] { "", "아이디", "내용", "날짜", "좋아요", "" }, 0) {
			@Override
			public Class<?> getColumnClass(int columnIndex) {
				if (columnIndex == 5) {
					return Boolean.class;
				} else {
					return super.getColumnClass(columnIndex);
				}
			}

			@Override
			public boolean isCellEditable(int row, int column) {
				return column == 5;
			}
		};

		table = new JTable(tableModel);
		table.setRowHeight(50); // 테이블 셀 크기 !!!!!!!!!!!!!!!!!!!!!!!!
		table.getColumnModel().getColumn(0).setPreferredWidth(50);
		table.getColumnModel().getColumn(1).setPreferredWidth(100);
		table.getColumnModel().getColumn(2).setPreferredWidth(300);
		table.getColumnModel().getColumn(3).setPreferredWidth(100);
		table.getColumnModel().getColumn(4).setPreferredWidth(100);
		table.getColumnModel().getColumn(5).setPreferredWidth(100);

		table.getColumnModel().getColumn(5).setCellRenderer(new ToggleButtonRenderer());
		table.getColumnModel().getColumn(5).setCellEditor(new ToggleButtonEditor());
	}

	private void addComponents() {
		JScrollPane scrollPane = new JScrollPane(table);
		add(scrollPane, BorderLayout.CENTER);

		JButton addButton = new JButton("글쓰기");
		addButton.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				addMessage();
			}
		});

		add(addButton, BorderLayout.SOUTH);
	}

	private void loadMessagesFromDatabase() {
		try (Connection connection = MySqlConnectionProvider.getConnection()) {
			String sql = "SELECT * FROM messageboard";
			PreparedStatement preparedStatement = connection.prepareStatement(sql);
			ResultSet resultSet = preparedStatement.executeQuery();

			while (resultSet.next()) {
				String id = resultSet.getString("member_id");
				String content = resultSet.getString("content");
				// 날짜 정보 등을 가져와서 테이블에 추가하는 작업을 수행
				Object[] rowData = { tableModel.getRowCount() + 1, id, content, "Date", 0, false };
				tableModel.addRow(rowData);
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

	private void addMessage() {
		String id = loginId; // 아이디 가져와야됨
		String content = JOptionPane.showInputDialog(this, "Enter Content:");
		if (id != null && content != null && !id.isEmpty() && !content.isEmpty()) {
			Object[] rowData = { tableModel.getRowCount() + 1, id, content, "Date", 0, false };
			tableModel.addRow(rowData);
			try (Connection connection = MySqlConnectionProvider.getConnection()) {
				String sql = "INSERT INTO messageboard (member_id, content, date) VALUES ((SELECT id FROM member WHERE id = ?), ?, CURDATE())";
				;
				PreparedStatement preparedStatement = connection.prepareStatement(sql);
				preparedStatement.setString(1, id);
				preparedStatement.setString(2, content);
				preparedStatement.executeUpdate();
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
	}

	private class ToggleButtonEditor extends DefaultCellEditor implements TableCellEditor {
		private JToggleButton button;

		public ToggleButtonEditor() {
			super(new JCheckBox());
			button = new JToggleButton();
			button.addItemListener(new ItemListener() {
				@Override
				public void itemStateChanged(ItemEvent e) {
					fireEditingStopped();
				}
			});
		}

		@Override
		public Component getTableCellEditorComponent(JTable table, Object value, boolean isSelected, int row,
				int column) {
			if (value == null) {
				return null;
			}
			button.setSelected((Boolean) value);
			return button;
		}

		// 좋아요 토글버튼 좋아요 횟수
		@Override
		public Object getCellEditorValue() {
			if (button.isSelected()) {
				int Likes = (int) tableModel.getValueAt(table.getSelectedRow(), 4); // "좋아요 횟수" 열의 인덱스 4
				tableModel.setValueAt(Likes + 1, table.getSelectedRow(), 4); // 좋아요 숫자 증가
			} else if (!button.isSelected()) {
				int Likes = (int) tableModel.getValueAt(table.getSelectedRow(), 4);
				if (Likes > 0) {
					tableModel.setValueAt(Likes - 1, table.getSelectedRow(), 4);
				}
			}

			return button.isSelected();
		}
	}

	private class ToggleButtonRenderer extends JToggleButton implements TableCellRenderer {
		public ToggleButtonRenderer() {
			setHorizontalAlignment(SwingConstants.CENTER);
		}

		@Override
		public Component getTableCellRendererComponent(JTable table, Object value, boolean isSelected, boolean hasFocus,
				int row, int column) {
			setSelected((Boolean) value);
			return this;
		}
	}
}
