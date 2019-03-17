package view;

import java.awt.Color;
import java.awt.EventQueue;
import java.awt.Font;
import java.awt.Image;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;

import javax.swing.DefaultListModel;
import javax.swing.GroupLayout;
import javax.swing.GroupLayout.Alignment;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextField;
import javax.swing.JToggleButton;
import javax.swing.LayoutStyle.ComponentPlacement;
import javax.swing.ListSelectionModel;
import javax.swing.SwingConstants;
import javax.swing.border.EmptyBorder;
import javax.swing.border.LineBorder;
import javax.swing.border.TitledBorder;
import javax.swing.filechooser.FileNameExtensionFilter;

import model.SrtFile;

public class MainFrame extends JFrame {
	
	private static final long serialVersionUID = 1L;
	private JPanel contentPane;
	ArrayList<SrtFile> listOfSubtitles = new ArrayList<SrtFile>();
	JList<String> list = new JList<String>();
	DefaultListModel<String> listModel = new DefaultListModel<String>();
	JButton btnConfirm = new JButton("Confirm");
	JCheckBox chckbxOverwrite = new JCheckBox("Overwrite");
	private JTextField offsetTextField;
	
	KeyListener onlyNumbers = new KeyListener() {

		@Override
		public void keyTyped(KeyEvent arg0) {
			char caractere = arg0.getKeyChar();
			if (Character.isDigit(caractere) || Character.compare(caractere, '-') == 0);
			else arg0.consume();
		}

		@Override
		public void keyReleased(KeyEvent arg0) {
			// TODO Auto-generated method stub

		}

		@Override
		public void keyPressed(KeyEvent arg0) {
			//if (arg0.getKeyCode() == 8) arg0.consume();
		}
	};
	
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					MainFrame frame = new MainFrame();
					frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	/**
	 * Create the frame.
	 */
	public MainFrame() {
		Image img = new ImageIcon(this.getClass().getResource("/icon_16x16.png")).getImage();
		setIconImage(img);
		setTitle("Subtitle synchronizer");
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 800, 450);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		
		JButton btnLoad = new JButton("Load subtitle file(s)");
		btnLoad.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent arg0) {
				openChoser();
				refreshList();
			}
		});
		
		JScrollPane scrollPane = new JScrollPane();
		
		JPanel panel = new JPanel();
		panel.setBorder(new TitledBorder(new LineBorder(new Color(0, 0, 0), 2, true), "Configuration", TitledBorder.LEADING, TitledBorder.TOP, null, null));
		
		JToggleButton toggleButton = new JToggleButton("+");
		toggleButton.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent arg0) {
				if (toggleButton.isSelected()) toggleButton.setText("-");
				else toggleButton.setText("+");
			}
		});
		
		btnConfirm.setEnabled(false);
		btnConfirm.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent arg0) {
				if (offsetTextField.getText().isEmpty()) {
					JOptionPane.showMessageDialog(null, "No time seted to adjust", "Time missing", JOptionPane.WARNING_MESSAGE);
				} else {
					for (int i = 0; i < listOfSubtitles.size(); i++) {
						int offset = Integer.parseInt(offsetTextField.getText());
						if (toggleButton.isSelected()) offset *= -1;
						listOfSubtitles.get(i).setLines(offset);
						createNewSrtFile(listOfSubtitles.get(i), chckbxOverwrite.isSelected());
					}
					String message = listOfSubtitles.size() + " files edited";
					JOptionPane.showMessageDialog(rootPane, message, "SUCCESS", JOptionPane.PLAIN_MESSAGE);
				}
			}
		});
		
		JButton btnRemove = new JButton("Remove");
		btnRemove.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent arg0) {
				if (list.getSelectedIndex() >= 0) {
					listOfSubtitles.remove(list.getSelectedIndex());
					refreshList();
				}
			}
		});
		
		JLabel lblHttpsgithubcomrenanbtm = new JLabel("github.com/renanbtm");
		lblHttpsgithubcomrenanbtm.setFont(new Font("Tahoma", Font.BOLD, 12));
		lblHttpsgithubcomrenanbtm.setHorizontalAlignment(SwingConstants.TRAILING);
		
		
		GroupLayout gl_contentPane = new GroupLayout(contentPane);
		gl_contentPane.setHorizontalGroup(
			gl_contentPane.createParallelGroup(Alignment.LEADING)
				.addGroup(gl_contentPane.createSequentialGroup()
					.addContainerGap()
					.addGroup(gl_contentPane.createParallelGroup(Alignment.TRAILING)
						.addGroup(gl_contentPane.createSequentialGroup()
							.addComponent(scrollPane, GroupLayout.DEFAULT_SIZE, 312, Short.MAX_VALUE)
							.addPreferredGap(ComponentPlacement.RELATED)
							.addComponent(panel, GroupLayout.PREFERRED_SIZE, 436, GroupLayout.PREFERRED_SIZE))
						.addGroup(gl_contentPane.createSequentialGroup()
							.addComponent(btnLoad)
							.addGap(377)
							.addComponent(lblHttpsgithubcomrenanbtm, GroupLayout.PREFERRED_SIZE, 254, GroupLayout.PREFERRED_SIZE))
						.addGroup(gl_contentPane.createSequentialGroup()
							.addComponent(btnRemove)
							.addPreferredGap(ComponentPlacement.RELATED, 614, Short.MAX_VALUE)
							.addComponent(btnConfirm)))
					.addContainerGap())
		);
		gl_contentPane.setVerticalGroup(
			gl_contentPane.createParallelGroup(Alignment.TRAILING)
				.addGroup(gl_contentPane.createSequentialGroup()
					.addContainerGap()
					.addGroup(gl_contentPane.createParallelGroup(Alignment.TRAILING)
						.addComponent(btnLoad)
						.addComponent(lblHttpsgithubcomrenanbtm))
					.addGap(20)
					.addGroup(gl_contentPane.createParallelGroup(Alignment.LEADING)
						.addComponent(scrollPane, GroupLayout.DEFAULT_SIZE, 307, Short.MAX_VALUE)
						.addComponent(panel, GroupLayout.DEFAULT_SIZE, GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
					.addPreferredGap(ComponentPlacement.RELATED)
					.addGroup(gl_contentPane.createParallelGroup(Alignment.BASELINE)
						.addComponent(btnConfirm)
						.addComponent(btnRemove))
					.addContainerGap())
		);
		
		chckbxOverwrite.setSelected(true);
		
		JLabel lblTimeOffset = new JLabel("Time offset (+ Subtitle appears later / - Subtitle appears sooner)");
		
		offsetTextField = new JTextField();
		offsetTextField.setColumns(10);
		offsetTextField.addKeyListener(onlyNumbers);
		
		JLabel lblMs = new JLabel("ms");
		lblMs.setFont(new Font("Tahoma", Font.BOLD, 12));
		
		GroupLayout gl_panel = new GroupLayout(panel);
		gl_panel.setHorizontalGroup(
			gl_panel.createParallelGroup(Alignment.LEADING)
				.addGroup(gl_panel.createSequentialGroup()
					.addContainerGap()
					.addGroup(gl_panel.createParallelGroup(Alignment.LEADING)
						.addComponent(lblTimeOffset, GroupLayout.DEFAULT_SIZE, 404, Short.MAX_VALUE)
						.addComponent(chckbxOverwrite, GroupLayout.PREFERRED_SIZE, 86, GroupLayout.PREFERRED_SIZE)
						.addGroup(gl_panel.createSequentialGroup()
							.addComponent(toggleButton, GroupLayout.PREFERRED_SIZE, 56, GroupLayout.PREFERRED_SIZE)
							.addPreferredGap(ComponentPlacement.RELATED)
							.addComponent(offsetTextField, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
							.addPreferredGap(ComponentPlacement.RELATED)
							.addComponent(lblMs)))
					.addContainerGap())
		);
		gl_panel.setVerticalGroup(
			gl_panel.createParallelGroup(Alignment.LEADING)
				.addGroup(gl_panel.createSequentialGroup()
					.addContainerGap()
					.addComponent(chckbxOverwrite)
					.addGap(18)
					.addComponent(lblTimeOffset)
					.addPreferredGap(ComponentPlacement.RELATED)
					.addGroup(gl_panel.createParallelGroup(Alignment.BASELINE)
						.addComponent(toggleButton)
						.addComponent(offsetTextField, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
						.addComponent(lblMs))
					.addContainerGap(194, Short.MAX_VALUE))
		);
		panel.setLayout(gl_panel);
		list.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
		
		list.setBorder(new TitledBorder(new LineBorder(new Color(0, 0, 0), 2, true), "Files", TitledBorder.LEADING, TitledBorder.TOP, null, new Color(0, 0, 0)));
		scrollPane.setViewportView(list);
		contentPane.setLayout(gl_contentPane);
	}
	
	public void refreshList() {
		listModel.clear();
		for (int i = 0; i < listOfSubtitles.size(); i++) {
			listModel.addElement(listOfSubtitles.get(i).getName());
		}
		list.setModel(listModel);
		if (!listOfSubtitles.isEmpty()) btnConfirm.setEnabled(true);
		else btnConfirm.setEnabled(false);
	}

	public void openChoser() {
		boolean repeatedFile = false;
		JFileChooser fc = new JFileChooser();
		fc.setMultiSelectionEnabled(true);
		FileNameExtensionFilter filter = new FileNameExtensionFilter("SRT subtitle", "srt");
		fc.setFileFilter(filter);
		fc.showOpenDialog(this);
		File[] files = fc.getSelectedFiles();
		for (int i = 0; i < files.length; i++) {
			SrtFile srtFile = new SrtFile();
			srtFile.setPath(files[i].getAbsolutePath());
			srtFile.setName(files[i].getName());
			for (int j = 0; j < listOfSubtitles.size(); j++) {
				if (listOfSubtitles.get(j).getName().equals(srtFile.getName())) repeatedFile = true;
			}
			if (!repeatedFile) listOfSubtitles.add(srtFile);
			repeatedFile = false;
		}
	}
	
	public void createNewSrtFile(SrtFile file, boolean overwrite) {
		String path = file.getPath();
		if (!overwrite) { // not overwrite
			StringBuilder sb = new StringBuilder();
			String splited[] = file.getPath().split("\\\\");
			for (int i = 0; i < (splited.length - 1); i++) {
				sb.append(splited[i]);
				sb.append("/");
			}
			sb.append("resynced " + file.getName());
			
			path = sb.toString();
		}
		try {
			File newFile = new File(path);
			FileWriter fw = new FileWriter(newFile, false);
			ArrayList<String> lines =  (ArrayList<String>) file.getLines();
			for (int i = 0; i < lines.size(); i++) fw.write(lines.get(i) + System.lineSeparator());
			fw.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
}
