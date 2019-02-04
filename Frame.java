import java.awt.Color;
import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.JTextField;
import javax.swing.JButton;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;

public class Frame {

	private JFrame frmMovieRecommender;
	private JTextField txtUserd;
	private static Operations o= new Operations();
	private int user;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					Frame window = new Frame();
					window.frmMovieRecommender.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	/**
	 * Create the application.
	 */
	public Frame() {
		initialize();
	}

	/**
	 * Initialize the contents of the frame.
	 */
	private void initialize() {
		frmMovieRecommender = new JFrame();
		frmMovieRecommender.setTitle("Movie Recommender");
		frmMovieRecommender.setBounds(100, 100, 688, 222);
		frmMovieRecommender.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frmMovieRecommender.getContentPane().setLayout(null);
		frmMovieRecommender.getContentPane().setBackground(new Color(128, 128, 128));
		
		
		txtUserd = new JTextField();
		txtUserd.addKeyListener(new KeyAdapter() {
			@Override
			public void keyReleased(KeyEvent e) {
				user=Integer.parseInt(txtUserd.getText());
			}
		});

		txtUserd.addMouseListener(new MouseAdapter() {
			@Override
			public void mousePressed(MouseEvent e) {
				txtUserd.setText("");
			}
		});
		
		txtUserd.setText("UserID");
		txtUserd.setBounds(46, 35, 127, 29);
		frmMovieRecommender.getContentPane().add(txtUserd);
		txtUserd.setColumns(10);
		
		JButton btnUserbased = new JButton("User Based");
		btnUserbased.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				int a=Integer.parseInt(JOptionPane.showInputDialog("How many movies would you like to see?"));
				o.getRecommendations(o.data,user, a);
				String[]mes=new String[a*2];
				for (int i = 0; i < mes.length; i=i+2) {
					mes[i]=("Movie "+(i/2+1)+": "+o.MovScoreF.get(i)+" ");
					mes[i+1]=("Score: "+o.MovScoreF.get(i+1)+"\n");		
				}
				String mess="";
				for (int j = 0; j < mes.length; j++) {
					mess=mess+mes[j];
				}
				JOptionPane.showMessageDialog(null, mess);
			}
		});
		btnUserbased.setBounds(268, 38, 111, 23);
		frmMovieRecommender.getContentPane().add(btnUserbased);
		
		JButton btnMoviebased = new JButton("Movie Based");
		btnMoviebased.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				int a=Integer.parseInt(JOptionPane.showInputDialog("How many movies would you like to see?"));
				BST<String, BST<Integer, Integer>> movieData= o.transformPrefs(o.data);
				o.getRecommendedItems(o.data, movieData,user, a);
				String[]mes=new String[a*2];
				for (int i = 0; i < mes.length; i=i+2) {
					mes[i]=("Movie "+(i/2+1)+": "+o.MovScoreF.get(i)+" ");
					mes[i+1]=("Score: "+o.MovScoreF.get(i+1)+"\n");		
				}
				String mess="";
				for (int j = 0; j < mes.length; j++) {
					mess=mess+mes[j];
				}
				JOptionPane.showMessageDialog(null, mess);
			}
		});
		btnMoviebased.setBounds(456, 38, 111, 23);
		frmMovieRecommender.getContentPane().add(btnMoviebased);
	}
}
