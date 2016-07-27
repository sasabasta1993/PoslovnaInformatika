package app;



import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.event.KeyEvent;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;

import javax.imageio.ImageIO;
import javax.swing.BoxLayout;
import javax.swing.ImageIcon;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.SwingConstants;
import javax.swing.border.BevelBorder;

import projekat.actions.AnalitikaIzvodaAction;
import projekat.actions.AnalitikaPresekaAction;
import projekat.actions.BankaAction;
import projekat.actions.DnevnoStanjeRacunaAction;
import projekat.actions.DrzaveAction;
import projekat.actions.KlijentAction;
import projekat.actions.KursUValutiAction;
import projekat.actions.KursnaListaAction;
import projekat.actions.NaseljenaMestaAction;
import projekat.actions.PresekAction;
import projekat.actions.PosaljiPresekAction;
import projekat.actions.RacuniPravnihLicaAction;
import projekat.actions.SifrarnikDelatnostiAction;
import projekat.actions.SpisakRacunaSaStanjemAction;
import projekat.actions.StavkaClearingRtgsAction;
import projekat.actions.UkidanjeAction;
import projekat.actions.ValutaAction;
import projekat.actions.VrstaPlacanjaAction;
import projekat.actions.ZaglavljeClearingRtgsAction;

import database.DBConnection;

public class MainFrame  extends JFrame{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;


	public static MainFrame instance;
	private JMenuBar menuBar;
	
	
	
	public MainFrame() {
		setSize(new Dimension(800, 600));
		setLayout(new BorderLayout());
	
		setLocationRelativeTo(null);
		setTitle("Poslovna banka");
		setDefaultCloseOperation(DO_NOTHING_ON_CLOSE);
		setExtendedState(MAXIMIZED_BOTH);
		setUpMenu();
		setStatusBar();
		BufferedImage glavnaIkonica = null;
		try {
			glavnaIkonica = ImageIO.read(getClass().getResource("/img/glavnaIkonica.png"));
		} catch (IOException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
		setIconImage(glavnaIkonica);
		setContentPane(new JLabel(new ImageIcon(getClass().getResource("/img/stock.png"))));
		addWindowListener(new WindowAdapter() {
			@Override
			public void windowClosing(WindowEvent e) {
			
				if (JOptionPane.showConfirmDialog(MainFrame.getInstance(),
						"Da li ste sigurni?", "Pitanje",
						JOptionPane.YES_NO_OPTION) == JOptionPane.YES_OPTION) {
				
					DBConnection.close();
					System.exit(0);
				}
			}
		});

		setJMenuBar(menuBar);

	}
	
	
	
	public void setStatusBar() {
		JPanel statusPanel = new JPanel();
		statusPanel.setBorder(new BevelBorder(BevelBorder.LOWERED));
		this.add(statusPanel, BorderLayout.SOUTH);
		statusPanel.setPreferredSize(new Dimension(this.getWidth(), 16));
		statusPanel.setLayout(new BoxLayout(statusPanel, BoxLayout.X_AXIS));
		
		JLabel statusLabel = new JLabel();
		statusLabel.setHorizontalAlignment(SwingConstants.LEFT);
		DateFormat dateFormat = new SimpleDateFormat("dd.MM.yyyy");

		statusLabel.setText((dateFormat.format(new java.util.Date()))
				.toString());
		//statusLabel.setHorizontalAlignment(SwingConstants.LEFT);
		statusPanel.add(statusLabel);

	}
	
	
	private void setUpMenu(){
		menuBar = new JMenuBar();

		JMenu tabele = new JMenu("Tabele");
		tabele.setMnemonic(KeyEvent.VK_T);
		
		JMenuItem drzaveMI = new JMenuItem(new DrzaveAction());
		tabele.add(drzaveMI);
		drzaveMI.setText("Drzave");
		
		JMenuItem mestoMI = new JMenuItem(new NaseljenaMestaAction());
		tabele.add(mestoMI);
		mestoMI.setText("Naseljena mesta");
		
		

		JMenuItem bankeMI = new JMenuItem(new BankaAction());
		tabele.add(bankeMI);
		bankeMI.setText("Banke");

		JMenuItem klijentiMI = new JMenuItem(new KlijentAction());
		tabele.add(klijentiMI);
		klijentiMI.setText("Klijenti");

		JMenuItem analitikeIzvodaMI = new JMenuItem(new AnalitikaIzvodaAction());
		tabele.add(analitikeIzvodaMI);
		analitikeIzvodaMI.setText("Analitike izvoda");
		
		
		JMenuItem sifrarnik_delatnosti = new JMenuItem(new SifrarnikDelatnostiAction());
		tabele.add(sifrarnik_delatnosti);
		sifrarnik_delatnosti.setText("Sifrarnik delatnosti");
		

		JMenuItem valutaMI = new JMenuItem(new ValutaAction());
		tabele.add(valutaMI);
		valutaMI.setText("Valute");
		
		
		
		JMenuItem vrstaPlacanjaMI = new JMenuItem(new VrstaPlacanjaAction());
		tabele.add(vrstaPlacanjaMI);
		vrstaPlacanjaMI.setText("Vrste plaÄ‡anja");
		
		JMenuItem kursnaListaMI = new JMenuItem(new KursnaListaAction());
		tabele.add(kursnaListaMI);
		kursnaListaMI.setText("Kursna lista");
		
		JMenuItem kursUValutiMI = new JMenuItem(new KursUValutiAction());
		tabele.add(kursUValutiMI);
		kursUValutiMI.setText("Kurs u valuti");

		JMenuItem presekMI = new JMenuItem(new PresekAction());
		tabele.add(presekMI);
		presekMI.setText("Presek izvoda");
		
		

		JMenuItem racuniMI = new JMenuItem(new RacuniPravnihLicaAction());
		tabele.add(racuniMI);
		racuniMI.setText("RaÄ�uni pravnih lica");
		

		JMenuItem ukidanjeMI = new JMenuItem(new UkidanjeAction());
		tabele.add(ukidanjeMI);
		ukidanjeMI.setText("Ukidanje raÄ�una");
		

		JMenuItem dnevnoStanjeRacunaMI = new JMenuItem(
				new DnevnoStanjeRacunaAction());
		tabele.add(dnevnoStanjeRacunaMI);
		dnevnoStanjeRacunaMI.setText("Dnevno stanje racuna");

		JMenuItem analitikaPresekaMI = new JMenuItem(
				new AnalitikaPresekaAction());
		tabele.add(analitikaPresekaMI);
		analitikaPresekaMI.setText("Analitika preseka");
		
		
		JMenuItem zaglavnjeclearingrtgs = new JMenuItem(
				new ZaglavljeClearingRtgsAction());
		tabele.add(zaglavnjeclearingrtgs);
		zaglavnjeclearingrtgs.setText("Clearing/rtgs");
		
		
		JMenuItem clearing = new JMenuItem(
				new StavkaClearingRtgsAction());
		tabele.add(clearing);
		clearing.setText("Clearing/RTGS");
		
		
		
		JMenu izvestajMenu = new JMenu("Izveštaji");
		
		JMenuItem izvestaj1=new JMenuItem(new SpisakRacunaSaStanjemAction());
		
		izvestajMenu.add(izvestaj1);
		
		
	    JMenu presekMenu = new JMenu("Presek");
		
		JMenuItem presek = new JMenuItem(new PosaljiPresekAction());
		
		presek.setText("Posalji presek");
		presekMenu.add(presek);
		
		

		menuBar.add(tabele);
		menuBar.add(izvestajMenu);
		menuBar.add(presekMenu);
		
		
	}
	
	
	
	
	
	
	
	public static MainFrame getInstance(){
		if (instance==null)
			instance=new MainFrame();
		return instance;

	}
	
	
}
