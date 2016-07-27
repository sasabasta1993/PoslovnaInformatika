package projekat.forms;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.FocusEvent;
import java.awt.event.FocusListener;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;
import java.sql.Date;
import java.sql.SQLException;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.swing.AbstractAction;
import javax.swing.BoxLayout;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JComboBox;
import javax.swing.JDialog;
import javax.swing.JLabel;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JPopupMenu;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.JToolBar;



import javax.swing.ListSelectionModel;
import javax.swing.SwingConstants;
import javax.swing.border.BevelBorder;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;

import projekat.form.actions.AddAction;
import projekat.form.actions.CommitAction;
import projekat.form.actions.DeleteAction;
import projekat.form.actions.FirstAction;
import projekat.form.actions.HelpAction;
import projekat.form.actions.LastAction;
import projekat.form.actions.NextFormAction;
import projekat.form.actions.PreviousAction;
import projekat.form.actions.RefreshAction;
import projekat.form.actions.RollbackAction;
import projekat.form.actions.SearchAction;
import app.MainFrame;
import net.miginfocom.swing.MigLayout;
import util.ColumnList;

public abstract class AbstractForm extends JDialog {
	
	/**
	 * 
	 */
	protected int clickedZoom = 0;
	protected HashMap<String, ArrayList<JTextField>> mapaPomocnihPolja = new HashMap<String, ArrayList<JTextField>>();
	private static final long serialVersionUID = 1L;
	public static final int MODE_EDIT = 1;
	public static final int MODE_ADD = 2;
	public static final int MODE_SEARCH = 3;
	protected int mode = MODE_EDIT;
	protected JToolBar toolBar;
	protected JButton btnAdd, btnCommit, btnDelete, btnFirst, btnLast, btnHelp,
			btnNext, btnNextForm, btnPickup, btnRefresh, btnRollback,
			btnSearch, btnPrevious;
	protected JTextField tfSifra = new JTextField(5);
	protected JTextField tfNaziv = new JTextField(20);
	protected JTable tblGrid = new JTable();
	JPanel bottomPanel;
	JPanel dataPanel;
	JLabel statusLabel = new JLabel();
	
	public JTable getTblGrid() {
		return tblGrid;
	}

	public void setTblGrid(JTable tblGrid) {
		this.tblGrid = tblGrid;
	}
	
	public int getMode() {
		return mode;
	}

	public void setMode(int mode) {
		this.mode = mode;
	}



	protected static ColumnList columns = new ColumnList();
	
	
	
	

	protected String insertSql = "";
	protected String deleteSql = "";
	protected String updateSql = "";
	protected String searchSql = "";
	protected ArrayList<ArrayList<String>> listaKolona;
	protected ArrayList<ArrayList<Integer>> listaIndeksa;
	protected ArrayList<String> listaKlasa;
	protected ArrayList<String> listaImenaKlasa;
	protected ArrayList<String> listaKlasaZoom;
	
	protected ArrayList<Integer> indexImena = new ArrayList<Integer>();
	
	public ArrayList<String> getListaKlasaZoom() {
		return listaKlasaZoom;
	}

	public void setListaKlasaZoom(ArrayList<String> listaKlasaZoom) {
		this.listaKlasaZoom = listaKlasaZoom;
	}

	protected int startBroj = 0;
	protected int brojacTxtFieldova = 0;
	protected JDialog objekat = null;
	protected JDialog dijalog = null;
	protected String nazivKlase = "";
	protected ArrayList<JTextField> lookupTextFields = new ArrayList<JTextField>(); 
	protected ArrayList<JTextField> sifreTextFields = new ArrayList<JTextField>();
	protected ArrayList<JTextField> pomocniSifreTextFields = new ArrayList<JTextField>();
	
	public ArrayList<JTextField> getPomocniSifreTextFields() {
		return pomocniSifreTextFields;
	}

	protected AbstractForm parentForm;
	protected ArrayList<Integer> listaKljucevaZaBlokiranje;
	protected JLabel labelaGreska = new JLabel("!");
	
	
	


	public AbstractForm(String insertSql, String deleteSql, String updateSql, String searchSql,
			ArrayList<ArrayList<String>> listaKolona, ArrayList<ArrayList<Integer>> listaIndeksa, ArrayList<String> listaKlasa,
			ArrayList<String> listaImenaKlasa, ArrayList<String> listaKlasaZoom, ArrayList<Integer> listaKljucevaZaBlokiranje) throws SQLException {
		mode = MODE_EDIT;
		setLayout(new MigLayout("fill"));
		
		setSize(new Dimension(800, 600));
		setLocationRelativeTo(MainFrame.getInstance());
		setModal(true);
		
		initToolbar();
		initGui();
		setStatusBar();
		this.insertSql = insertSql;
		this.deleteSql = deleteSql;
		this.updateSql = updateSql;
		this.searchSql = searchSql;
		this.listaKolona = listaKolona;
		this.listaIndeksa = listaIndeksa;
		this.listaKlasa = listaKlasa;
		this.listaImenaKlasa = listaImenaKlasa;
		this.listaKlasaZoom = listaKlasaZoom;
		this.listaKljucevaZaBlokiranje = listaKljucevaZaBlokiranje;
		
	//popup = new JPopupMenu();
		
				
	}

	
	
	protected void initTable(GenericTableModel tableModel, String sqlQuery, ColumnList colList) throws SQLException {
		JScrollPane scrollPane = new JScrollPane(tblGrid);
		add(scrollPane, "grow, wrap");

		tblGrid.setModel(tableModel);
		
		if (colList != null)
		{
			
			tableModel.openAsChildForm(colList.getWhereClause());
			
			uredjivanjeTabele();
			//goFirst();
		}
		else
		{
			tableModel.open(sqlQuery);
			uredjivanjeTabele(); 
		}

		
	}
	
	
	public void uredjivanjeTabele()
	{
		// Dozvoljeno selektovanje redova
				tblGrid.setRowSelectionAllowed(true);
				// Ali ne i selektovanje kolona
				tblGrid.setColumnSelectionAllowed(false);

				// Dozvoljeno selektovanje samo jednog reda u jedinici vremena
				tblGrid.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
				tblGrid.getSelectionModel().addListSelectionListener(
						new ListSelectionListener() {
							public void valueChanged(ListSelectionEvent e) {
								if (e.getValueIsAdjusting())
									return;
								sync();
							}
						});
	}
	
	
	
	
	public void odblokiranjeKljuceva()
	{
		
			((JTextField)dataPanel.getComponent(1)).requestFocus();
		//kada se klikne na dodavanje, da se oslobode sva polja
		for (int i=0; i<dataPanel.getComponentCount(); ++i)
		{
			if (dataPanel.getComponent(i) instanceof JTextField)
			{
				//ako je kompenta koja prethodi razlicita od JButton onda da
				if (!(dataPanel.getComponent(i-1) instanceof JButton))
					((JTextField)dataPanel.getComponent(i)).setEnabled(true);
				((JTextField)dataPanel.getComponent(i)).setText("");
			}
			else if (dataPanel.getComponent(i) instanceof JCheckBox)
			{
				((JCheckBox)dataPanel.getComponent(i)).setSelected(false);
			}
			else if (dataPanel.getComponent(i) instanceof JComboBox)
			{
				((JComboBox<String>)dataPanel.getComponent(i)).setSelectedIndex(0);
			}
			else if (dataPanel.getComponent(i) instanceof JButton)
			{
				((JButton)dataPanel.getComponent(i)).setEnabled(true);
			}
		}
	}
	
	
	
	public void panel(int brojStavki, ArrayList<String> imenaStavki, ArrayList<String> tipovi, HashMap<String, ArrayList<String>> listaComboVrednosti) //value je informacija da li je 'text' ili 'zoom'
	{
		labelaGreska.setVisible(false);
		final AbstractForm activeForm = this;
		for (int i = 0; i<brojStavki; i++)
		{
			boolean racun = false;
			boolean broj1 = false;
			boolean broj2 = false;
			
			if(tipovi.get(i).equals("lookup"))
				continue;
			
			if  (imenaStavki.get(i).contains(","))  {
				String [] parts = imenaStavki.get(i).split(",");
				if (parts[1].equals("int") || parts[1].equals("double"))
				{
					broj1 = true;
				}
				if (parts[1].equals("broj"))
				{
					broj2 = true;
				}
				if (parts[1].equals("racun"))
				{
					racun = true;
					broj2 = true;
				}
				JLabel lbl = new JLabel(parts[0]);
				lbl.setName("lbl" + imenaStavki.get(i));
				dataPanel.add(lbl);
			} else {
				JLabel lbl = new JLabel(imenaStavki.get(i));
				lbl.setName("lbl" + imenaStavki.get(i));
				dataPanel.add(lbl);
			}
			
			if(tipovi.get(i).equals("check")){
				JCheckBox cb = new JCheckBox();
				cb.setName("cb" + imenaStavki.get(i));
				if (i<brojStavki-1 && (!tipovi.get(i).equals("zoom") && !tipovi.get(i).equals("doubleZoom")))
					dataPanel.add(cb, "wrap, gapx 15px");
				else
					dataPanel.add(cb, "gapx 15px");
			}else if(tipovi.get(i).equals("combo")){
				JComboBox<String> cb = new JComboBox<String>();
				cb.setName("cb" + imenaStavki.get(i));
				
				for(String str : listaComboVrednosti.get((imenaStavki).get(i))){
					cb.addItem(str);
				}
				
				if (i<brojStavki-1 && (!tipovi.get(i).equals("zoom") && !tipovi.get(i).equals("doubleZoom")))
					dataPanel.add(cb, "wrap, gapx 15px");
				else
					dataPanel.add(cb, "gapx 15px");
			}
			else {
				final JTextField tf;
			/*	if (racun)
				{
					 tf = new JTextField(20);
					 tf.addFocusListener(new FocusListener() {
						
						@Override
						public void focusLost(FocusEvent e) {
							// TODO Auto-generated method stub
							String text = tf.getText();
							Pattern pattern1 = Pattern.compile("[0-9]{18}");
							Matcher matcher1 = pattern1.matcher(text);
							
							if (!matcher1.find()){
				                //JOptionPane.showMessageDialog(null, "Broj racuna mora da bude tacno 18 cifara!");
								labelaGreska.setVisible(true);
				                //tf.requestFocus();
				            }    
						}
						
						@Override
						public void focusGained(FocusEvent e) {
							// TODO Auto-generated method stub
							labelaGreska.setVisible(false);
						}
					});
				}*/
				//else
				//{
					 tf = new JTextField(10);
			//	}
				tf.setName("tf" +  imenaStavki.get(i));
				if (broj1 == true)
				{
				tf.addKeyListener(new KeyAdapter() {
	                public void keyTyped(KeyEvent e) {
	                    char vChar = e.getKeyChar();
	                    if (!(Character.isDigit(vChar)
	                            || (vChar == KeyEvent.VK_BACK_SPACE)
	                            || (vChar == KeyEvent.VK_DELETE)
	                            || (vChar == KeyEvent.VK_PERIOD))) {
	                        e.consume();
	                    }
	                }
	            });
				}
				

				if (broj2 == true)
				{
				tf.addKeyListener(new KeyAdapter() {
	                public void keyTyped(KeyEvent e) {
	                    char vChar = e.getKeyChar();
	                    if (!(Character.isDigit(vChar)
	                            || (vChar == KeyEvent.VK_BACK_SPACE)
	                            || (vChar == KeyEvent.VK_DELETE))) {
	                        e.consume();
	                    }
	                }
	            });
				}
				
				if (i<brojStavki-1 && (!tipovi.get(i).equals("zoom") && !tipovi.get(i).equals("doubleZoom")))
				{
					if (racun)
					{
						dataPanel.add(tf, "gapx 15px");
						dataPanel.add(labelaGreska, "wrap");
						labelaGreska.setToolTipText("18 cifara!");
					}
					else	
						dataPanel.add(tf, "wrap, gapx 15px");
					
					
				}
				else{
					dataPanel.add(tf, " gapx 15px"); // naredna komponenta ne ide ispo nego pored, u ovom slucaju dugme ...
				}
		
				if(tipovi.get(i).equals("zoom")|| tipovi.get(i).equals("doubleZoom")){
					sifreTextFields.add(tf);
					
					if(tipovi.get(i).equals("doubleZoom")){
						ArrayList<JTextField> pomocnaPolja = new ArrayList<JTextField>();
						//int brojacTxtFieldova = 0;
						int j;
						for(j = startBroj; j < dataPanel.getComponentCount(); j++){
							if(dataPanel.getComponent(j) instanceof JTextField ){
								if(brojacTxtFieldova >= i){
									++brojacTxtFieldova;
									break; 
								}
									
								pomocniSifreTextFields.add((JTextField)dataPanel.getComponent(j));
								pomocnaPolja.add((JTextField)dataPanel.getComponent(j));
								++brojacTxtFieldova;
							}
							
						}
						
						startBroj = j + 1;
						mapaPomocnihPolja.put(tf.getName(), pomocnaPolja);
					}
					else
						startBroj = dataPanel.getComponentCount();
					
					final int indexOfSifraField = sifreTextFields.size();
					JButton btnZoom = new JButton("...");
					
					if(tipovi.get(i).equals("doubleZoom") || this.getClass().getName().equals("projekat.forms.DnevnoStanjeRacunaStandardForm") ||
							(imenaStavki.get(i).equals("Broj racuna") && this.getClass().getName().equals("projekat.forms.PresekStandardForm"))
							|| this.getClass().getName().equals("projekat.forms.StavkaClearingRtgsStandardForm") ||
							(imenaStavki.get(i).equals("Šifra klijenta,int") && this.getClass().getName().equals("projekat.forms.RacuniPravnihLicaStandardForm")))
						dataPanel.add(btnZoom, "wrap");
					else
						dataPanel.add(btnZoom);
					
					btnZoom.addActionListener(new ActionListener() {
						
						@Override
						public void actionPerformed(ActionEvent e) {
							// TODO Auto-generated method stub
							//addColumns(listaKolona, listaIndeksa);
							clickedZoom = indexOfSifraField - 1;
							Class<?> class1 = null;
							try {
								String imeKlase = listaKlasaZoom.get(indexOfSifraField - 1);
								if(imeKlase.contains("1")) //izbrisati ovo sa 1
									class1 = Class.forName(imeKlase.substring(0, imeKlase.indexOf("1")));
								else
									class1 = Class.forName(imeKlase);
								
							} catch (ClassNotFoundException e1) {
								// TODO Auto-generated catch block
								e1.printStackTrace();
							}
							Constructor<?> constructor = null;
							try {
								constructor = class1.getConstructor(util.ColumnList.class,projekat.forms.AbstractForm.class);
							} catch (NoSuchMethodException e1) {
								// TODO Auto-generated catch block
								e1.printStackTrace();
							} catch (SecurityException e1) {
								// TODO Auto-generated catch block
								e1.printStackTrace();
							}
							Object instance = null;
							try {
								instance = constructor.newInstance(null, activeForm);
							} catch (InstantiationException e1) {
								// TODO Auto-generated catch block
								e1.printStackTrace();
							} catch (IllegalAccessException e1) {
								// TODO Auto-generated catch block
								e1.printStackTrace();
							} catch (IllegalArgumentException e1) {
								// TODO Auto-generated catch block
								e1.printStackTrace();
							} catch (InvocationTargetException e1) {
								// TODO Auto-generated catch block
								e1.printStackTrace();
							}
								
							JDialog form = (JDialog) instance;
							form.setVisible(true);
						}
					});
					
					if(tipovi.get(i).equals("doubleZoom")){
						JTextField tfForeign = new JTextField(10);
						tfForeign.setName("tf" + imenaStavki.get(i) + "doubleZoom");
						lookupTextFields.add(tfForeign);
						
					} else if (this.getClass().getName().equals("projekat.forms.DnevnoStanjeRacunaStandardForm") ||
							(imenaStavki.get(i).equals("Broj računa") && this.getClass().getName().equals("projekat.forms.PresekStandardForm"))
							|| this.getClass().getName().equals("projekat.forms.StavkaClearingRtgsStandardForm") ||
							(imenaStavki.get(i).equals("Šifra klijenta,int") && this.getClass().getName().equals("projekat.forms.RacuniPravnihLicaStandardForm")) ||
							(imenaStavki.get(i).equals("ID klijenta,int") && this.getClass().getName().equals("projekat.forms.PresekStandardForm"))){
						JTextField tfForeign = new JTextField(10);
						tfForeign.setName("tf" + imenaStavki.get(i) + "Zoom");
						lookupTextFields.add(tfForeign);
						
					} else {
						JTextField tfForeign = new JTextField(10);
						tfForeign.setName("tf" + imenaStavki.get(i) + "Zoom");
						lookupTextFields.add(tfForeign);
						dataPanel.add(tfForeign, " wrap, pushx");
						tfForeign.setEditable(false);
					}
					
				
			}
			/*	else if(tipovi.get(i).equals("text")){
					startBroj = dataPanel.getComponentCount();
				}*/
			}
		
			
		}
		
		bottomPanel.add(dataPanel);
	
	}
	
	
	
	public void blokiranjeKljuceva()
	{
		int brojacZaKljuc = 0;
		for (int i=0; i<dataPanel.getComponentCount(); ++i)
		{
			//this.absListaIndexa = listaIndexa;
			if (i == listaKljucevaZaBlokiranje.get(brojacZaKljuc))
			{
				if (dataPanel.getComponent(i) instanceof JTextField)
					((JTextField)dataPanel.getComponent(i)).setEnabled(false);
				else if (dataPanel.getComponent(i) instanceof JButton)
					((JButton)dataPanel.getComponent(i)).setEnabled(false);
				brojacZaKljuc++;
				if (brojacZaKljuc == listaKljucevaZaBlokiranje.size())
					break;
			}
		}
	}
	
	
private void initToolbar() {
		
		toolBar = new JToolBar();
		btnSearch = new JButton(new SearchAction(this));
		toolBar.add(btnSearch);
		
		btnSearch.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				// TODO Auto-generated method stub
				mode = MODE_SEARCH;
				odblokiranjeKljuceva();
				promeniStatus();
			}
		});

		btnRefresh = new JButton(new RefreshAction());								
		toolBar.add(btnRefresh);
		
		
		btnPickup = new JButton(/*new PickupAction(this)*/);
		toolBar.add(btnPickup);
		btnPickup.setIcon(new ImageIcon(getClass().getResource("/img/zoom-pickup.gif")));
		final JDialog activeForm = this;
		final String imeKlase = this.getClass().getName();
		//System.out.println(imeKlase);
		
		/*btnPickup.addActionListener(new ActionListener() {
			
			
			@Override
			public void actionPerformed(ActionEvent e) {
				// TODO Auto-generated method stub
				if(parentForm != null){
					
					int num = 0;
					
					num = parentForm.clickedZoom;
					
					if(((AbstractForm)parentForm).getLookupTextFields().get(num).getName().contains("doubleZoom")){
						((AbstractForm)parentForm).getSifreTextFields().get(num).setText(tblGrid.getValueAt(tblGrid.getSelectedRow(), ((AbstractForm)parentForm).getMapaPomocnihPolja().get(((AbstractForm)parentForm).getSifreTextFields().get(num).getName()).size()).toString());
						
						
						
						
							for (int k = 0; k < ((AbstractForm) parentForm).getMapaPomocnihPolja().get(((AbstractForm)parentForm).getSifreTextFields().get(num).getName()).size(); ++k) {
								((AbstractForm) parentForm).getMapaPomocnihPolja().get(((AbstractForm)parentForm).getSifreTextFields().get(num).getName()).get(k).setText(tblGrid.getValueAt(tblGrid.getSelectedRow(),k).toString());
							}
						
					} else {
						((AbstractForm)parentForm).getSifreTextFields().get(num).setText(tblGrid.getValueAt(tblGrid.getSelectedRow(), 0).toString());
						
						if(!imeKlase.equals("projekat.gui.form.RacuniPravnihLicaStandardForm") && !imeKlase.equals("projekat.gui.form.ZaglavljeClearingRtgsStandardForm"))
							((AbstractForm)parentForm).getLookupTextFields().get(num).setText(tblGrid.getValueAt(tblGrid.getSelectedRow(), indexImena.get(0)).toString());
						
					}
			
				}
				activeForm.setVisible(false);
			}
		});*/

		btnHelp = new JButton(new HelpAction());
		toolBar.add(btnHelp);

		toolBar.addSeparator();

		btnFirst = new JButton(new FirstAction(this));
		toolBar.add(btnFirst);
		
		btnFirst.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				// TODO Auto-generated method stub
				goFirst();
			}
		});
		
		btnRollback = new JButton(new RollbackAction(this));
		btnRollback.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				// TODO Auto-generated method stub
				/*if (mode == MODE_SEARCH)
				{
					try {
					//ovaj deo je da se vrate sve torke kad se izvrsi izmena
						((GenericTableModel)tblGrid.getModel()).refresh();
					} catch (SQLException e1) {
						// TODO Auto-generated catch block
						e1.printStackTrace();
					}
				}*/
				blokiranjeKljuceva();
				mode = MODE_EDIT;
				promeniStatus();
				goFirst();
				sync();
			}
		});
		
		btnCommit = new JButton(new CommitAction(this));
		btnCommit.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				// TODO Auto-generated method stub
				if (mode == MODE_ADD)
				{
					//if (cekiranje())
					//{
						addRow(insertSql);
						mode = MODE_ADD;
					//}
					//else
					//	JOptionPane.showMessageDialog(null, "Niste uneli sva polja korektno!");
				
				}
				else if (mode == MODE_EDIT)
				{
					updateRow(updateSql);
					mode = MODE_EDIT;
				}
				else if (mode == MODE_SEARCH)
				{
					findRow(searchSql);
					mode = MODE_EDIT;
				}
				//mode = MODE_EDIT;
				promeniStatus();
			}
		});
		
		btnFirst.setIcon(new ImageIcon(getClass().getResource("/img/first.gif")));

		btnPrevious = new JButton(new PreviousAction(this));
		toolBar.add(btnPrevious);
		
		btnPrevious.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				// TODO Auto-generated method stub
				goPrevious();
			}
		});
		
		btnPrevious.setIcon(new ImageIcon(getClass().getResource("/img/prev.gif")));
		
		btnNext = new JButton();
		toolBar.add(btnNext);
		
		btnNext.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				// TODO Auto-generated method stub
				goNext();
			}
		});
		//bolje je ovako, nego preko akcija, jer se tamo mora proveravati sa instance of
		btnNext.setIcon(new ImageIcon(getClass().getResource("/img/next.gif")));

		btnLast = new JButton(new LastAction(this));
		toolBar.add(btnLast);
		
		btnLast.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				// TODO Auto-generated method stub
				goLast();
			}
		});
		btnLast.setIcon(new ImageIcon(getClass().getResource("/img/last.gif")));


		toolBar.addSeparator();

		btnAdd = new JButton(new AddAction(this));
		toolBar.add(btnAdd);
		
		btnAdd.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				// TODO Auto-generated method stub
				mode = MODE_ADD;
				odblokiranjeKljuceva();
				promeniStatus();
			}
		});

		btnDelete = new JButton(new DeleteAction(this));
		toolBar.add(btnDelete);
		
		btnDelete.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				// TODO Auto-generated method stub
				removeRow(deleteSql);
			}
		});

		toolBar.addSeparator();

		btnNextForm = new JButton(new NextFormAction(this));
		toolBar.add(btnNextForm);
		

		
	/*	 btnNextForm.addMouseListener(new MouseAdapter() {
	            public void mousePressed(MouseEvent e) {
	            	if (tblGrid.getSelectedRow() != -1)
	            	{
	            	popup.removeAll();
	            	//if (listaKlasa.size() > 1)
	        		//{
	        			for (brojac=0; brojac<listaKlasa.size(); ++brojac)
	        			{
	        				
	        				AbstractAction aa = new AbstractAction(listaImenaKlasa.get(brojac)) {
	        		            public void actionPerformed(ActionEvent e) {
	        		            	System.out.println("action comm " + e.getActionCommand());
	        		            	int brojacZaNext = 0;
	        		            	for (int i=0; i<listaImenaKlasa.size(); ++i)
	        		            	{
	        		            		if (listaImenaKlasa.get(i).equals(e.getActionCommand()))
	        		            		{
	        		            			brojacZaNext = i;
	        		            			break;
	        		            		}
	        		            	}
	        		            	System.out.println("brojac za next " + brojacZaNext);
	        		            	izvrsiNext(brojacZaNext);
	        		            	
	        		            }
	        		        };
	        		        JMenuItem mi = new JMenuItem(aa);
	        				popup.add(mi);
	        			}
	        		//}
	                popup.show(e.getComponent(), e.getX(), e.getY());
	            	}
	            	else
	            	{
	            		JOptionPane.showMessageDialog(null, "Morate obeleziti jedan red u tabeli!");
	            	}
	            }
	        });*/

		add(toolBar, "dock north");
	}
	
private void initGui() {

	bottomPanel = new JPanel();
	bottomPanel.setLayout(new MigLayout("fillx"));
	dataPanel = new JPanel();
	dataPanel.setLayout(new MigLayout(/*"gapx 15px"*/));

	JPanel buttonsPanel = new JPanel();
	
	buttonsPanel.setLayout(new MigLayout("wrap"));
	buttonsPanel.add(btnCommit);
	buttonsPanel.add(btnRollback);
	bottomPanel.add(buttonsPanel, "dock east");

	add(bottomPanel, "grow, wrap");
}


public void setStatusBar() {
	JPanel statusPanel = new JPanel();
	statusPanel.setBorder(new BevelBorder(BevelBorder.LOWERED));
	this.add(statusPanel, BorderLayout.SOUTH);
	statusPanel.setPreferredSize(new Dimension(this.getWidth(), 40));
	statusPanel.setLayout(new BoxLayout(statusPanel, BoxLayout.X_AXIS));
	String moda = "Mode: ";  
		statusLabel.setText(moda + "EDIT");
	statusLabel.setHorizontalAlignment(SwingConstants.LEFT);
	JLabel statusLabel2 = new JLabel();
	DateFormat dateFormat = new SimpleDateFormat("dd.MM.yyyy");

	statusLabel2.setText((dateFormat.format(new java.util.Date()))
			.toString());
	statusPanel.add(statusLabel);
	statusPanel.add(statusLabel2);
	
	

}



@SuppressWarnings("unchecked")
protected void sync() {
    int index = tblGrid.getSelectedRow();
	int brojacTf = 0;
	ArrayList<Object> listaKomponenata = new ArrayList<Object>();
	for (int i=0; i<dataPanel.getComponentCount(); ++i)
	{
		if ((  dataPanel.getComponent(i) instanceof JTextField) || (dataPanel.getComponent(i) instanceof JComboBox<?>) || (dataPanel.getComponent(i) instanceof JCheckBox))
		{
			brojacTf++;
			listaKomponenata.add(dataPanel.getComponent(i));
		} 
	}
	
	if (index < 0) {
		for (int j = 0; j < brojacTf; ++j) {
			if (listaKomponenata.get(j) instanceof JTextField)
				((JTextField) listaKomponenata.get(j)).setText("");
			else if (listaKomponenata.get(j) instanceof JComboBox<?>)
				((JComboBox<String>) listaKomponenata.get(j)).setSelectedIndex(0);
			else if (listaKomponenata.get(j) instanceof JCheckBox)
				((JCheckBox) listaKomponenata.get(j)).setSelected(false);
			
		}
		return;
	}
    // mislim da ne radi za lokup
    for (int i=0; i<brojacTf; ++i)
    {
    	if(   tblGrid.getValueAt(index, i) instanceof String || tblGrid.getValueAt(index, i) instanceof Integer || tblGrid.getValueAt(index, i) instanceof Double){
    		String str = tblGrid.getValueAt(index, i).toString();
    		
    		if(listaKomponenata.get(i) instanceof JTextField)
    			((JTextField)listaKomponenata.get(i)).setText(str);
    		else if(listaKomponenata.get(i) instanceof JComboBox<?>)
    			((JComboBox<String>)listaKomponenata.get(i)).setSelectedItem(str);
    		else if (listaKomponenata.get(i) instanceof JCheckBox){
    			if(str.equals("1"))
    				((JCheckBox)listaKomponenata.get(i)).setSelected(true);
    			else
    				((JCheckBox)listaKomponenata.get(i)).setSelected(false);
    		}
    			
    	} else if(tblGrid.getValueAt(index, i) instanceof Boolean){ //obrisati
    		Boolean bool = (Boolean)tblGrid.getValueAt(index, i);
    		if(bool)
    			((JCheckBox)listaKomponenata.get(i)).setSelected(true);
    		else
    			((JCheckBox)listaKomponenata.get(i)).setSelected(false);
    	} 
    }
   
}


public void goLast() {
    int rowCount = tblGrid.getModel().getRowCount(); 
    if (rowCount > 0)
      tblGrid.setRowSelectionInterval(rowCount - 1, rowCount - 1);
    
    sync();
  }


public  void goFirst() {
	
	
	int rowCount = tblGrid.getModel().getRowCount();
	if (rowCount > 0) {
		
		tblGrid.setRowSelectionInterval(0, 0);
		
	}
	sync();
}

public void goNext()  {
	
	int row = tblGrid.getSelectedRow();
	int rowCount = tblGrid.getModel().getRowCount();
	
	
	
	if (row + 1 < rowCount) {
		tblGrid.setRowSelectionInterval(row +1, row+1);
		
	}
	else {
		tblGrid.setRowSelectionInterval(row, row);
	}
	
	sync();
	
}


public void goPrevious() {
	
	
	int row = tblGrid.getSelectedRow();
	if (row -1 >= 0) {
		
		tblGrid.setRowSelectionInterval(row-1, row-1);
	}
	
	else {
		
		tblGrid.setRowSelectionInterval(row, row);

	}
	
	sync();
}



public void promeniStatus()
{
	String moda = "Mode: ";
	if (mode == 1)
		statusLabel.setText(moda + "EDIT");
	else if (mode == 2)
		statusLabel.setText(moda + "ADD");
	else
		statusLabel.setText(moda + "SEARCH");
}



@SuppressWarnings("unchecked")
public void findRow(String searchSql) {
	try {
		GenericTableModel dtm = (GenericTableModel) tblGrid.getModel();
		int brojacTf = 0;

	    ArrayList<Object> listaZaSQL = new ArrayList<Object>();
	 //   System.out.println("ukupno kompoenenti " + dataPanel.getComponentCount());
	    for (int i=0; i<dataPanel.getComponentCount(); ++i)
	    {
	    	if (dataPanel.getComponent(i) instanceof JTextField)
    		{
    			
    				brojacTf++;
    				
    				if((((JTextField)dataPanel.getComponent(i)).getText().trim().equals(""))){
    					listaZaSQL.add("");
    					continue;
    				}
    				
    				String tfName = ((JTextField)dataPanel.getComponent(i)).getName();
    				if(tfName.contains(",")){ //provera da li su tekstualna polja numericka
    					
    					
    					String [] nameParts = tfName.split(",");
    					//System.out.println(nameParts[0]);
    					//System.out.println(nameParts[1]);
    					if(nameParts[1].equals("int")){
    						
    						Integer numericVal = Integer.parseInt(((JTextField)dataPanel.getComponent(i)).getText().trim());
    						listaZaSQL.add(numericVal);
    						
    					} else if (nameParts[1].equals("double")){
    						System.out.println("brojac ide do " + i);
    						
    						Double numericVal = Double.parseDouble(((JTextField)dataPanel.getComponent(i)).getText().trim());
    						listaZaSQL.add(numericVal);
    						
    					} else if (nameParts[1].equals("date")){
    						
    						String pattern = "dd/MM/yyyy";
    					    SimpleDateFormat format = new SimpleDateFormat(pattern);
    					    java.util.Date parsed = null;
    					    try {
    					      parsed = format.parse(((JTextField)dataPanel.getComponent(i)).getText().trim());
    					      java.sql.Date date = new java.sql.Date(parsed.getTime());
    					      listaZaSQL.add(date);
    					    } catch (ParseException e) {
    					      e.printStackTrace();
    					    }
    						
    						
    					} 
    				} else {
    					if (!((JTextField)dataPanel.getComponent(i)).getText().trim().equals(""))
						{
    					listaZaSQL.add(((JTextField)dataPanel.getComponent(i)).getText().toString());
						}
    					else
							listaZaSQL.add("");
    				}
        			
    			
    			
    		} else if (dataPanel.getComponent(i) instanceof JCheckBox)
    		{
    			brojacTf++;
    			listaZaSQL.add(((JCheckBox)dataPanel.getComponent(i)).isSelected());
    		} else if (dataPanel.getComponent(i) instanceof JComboBox<?>){
    			brojacTf++;
    			listaZaSQL.add(((JComboBox<String>)dataPanel.getComponent(i)).getSelectedItem().toString());
    		}
	    }
	    	
	    Object [] nizZaSQL = new Object [brojacTf];
		
		for (int j=0; j<brojacTf; ++j)
		{
			if(listaZaSQL.get(j) instanceof String)
				nizZaSQL[j] = listaZaSQL.get(j).toString().trim();
			else if(listaZaSQL.get(j) instanceof Boolean)
				nizZaSQL[j] = (Boolean)listaZaSQL.get(j);
			else if(listaZaSQL.get(j) instanceof Integer)
				nizZaSQL[j] = (Integer)listaZaSQL.get(j);
			else if(listaZaSQL.get(j) instanceof Double)
				nizZaSQL[j] = (Double)listaZaSQL.get(j);
			else if(listaZaSQL.get(j) instanceof Date)
				nizZaSQL[j] = (Date)listaZaSQL.get(j);
		}
	
	      
	      
		dtm.findRow(nizZaSQL, searchSql);
		dtm.fireTableDataChanged(); //proslediti sqlQuery
		//dtm.refresh();
	      
		  
	    } catch (SQLException ex) {
	      JOptionPane.showMessageDialog(this, ex.getMessage(), "Greska", 
	          JOptionPane.ERROR_MESSAGE);
	    }
}

@SuppressWarnings("unchecked")
public void updateRow(String updateSql) {
	int index = tblGrid.getSelectedRow(); 
    if (index == -1) //Ako nema selektovanog reda (tabela prazna)
      return;        // izlazak 
    
    try {
		GenericTableModel dtm = (GenericTableModel) tblGrid.getModel();
		String kljuc = (String) dtm.getValueAt(index, 0);// ista prica kao
															// za brisanje

		int brojacTf = 0;

		ArrayList<Object> listaZaSQL = new ArrayList<Object>();// treba dodati
															// comboboxove i
															// checkboxove
		for (int i = 0; i < dataPanel.getComponentCount(); ++i) {
			if (dataPanel.getComponent(i) instanceof JTextField) {
				if (((JTextField) dataPanel.getComponent(i)).isEditable()) { // zaobilazak
																			// lookup
																			// polja
					brojacTf++;
					String tfName = ((JTextField) dataPanel.getComponent(i)).getName();
					if (tfName.contains(",")) { // provera da li su
												// tekstualna polja
												// numericka
						String[] nameParts = tfName.split(",");
						if (nameParts[1].equals("int")) {
							Integer numericVal = Integer.parseInt(((JTextField)dataPanel.getComponent(i)).getText().trim());
							listaZaSQL.add(numericVal);
						} else if (nameParts[1].equals("double")) {
							Double numericVal = Double.parseDouble(((JTextField)dataPanel.getComponent(i)).getText().trim());
							listaZaSQL.add(numericVal);
						} else if (nameParts[1].equals("date")){
							String pattern = "yyyy-MM-dd";
    					    SimpleDateFormat format = new SimpleDateFormat(pattern);
    					    java.util.Date parsed = null;
    					    try {
    					      //(((JTextField)dataPanel.getComponent(i)).getText().trim());
    					      parsed = format.parse(((JTextField)dataPanel.getComponent(i)).getText().trim());
    					      java.sql.Date date = new java.sql.Date(parsed.getTime());
    					      listaZaSQL.add(date);
    					    } catch (ParseException e) {
    					      e.printStackTrace();
    					    }
    						
    					} 
					} else {
						listaZaSQL.add(((JTextField) dataPanel.getComponent(i)).getText().toString());
					}

				}

			} else if (dataPanel.getComponent(i) instanceof JCheckBox) {
				brojacTf++;
				listaZaSQL.add(((JCheckBox) dataPanel.getComponent(i))
						.isSelected());
			} else if (dataPanel.getComponent(i) instanceof JComboBox<?>) {
				brojacTf++;
				listaZaSQL.add(((JComboBox<String>) dataPanel.getComponent(i)).getSelectedItem().toString());
			}
		}
	    	
		Object [] nizZaSQL = new Object [brojacTf];
			
		for (int j=0; j<brojacTf; ++j){
			if(listaZaSQL.get(j) instanceof String)
				nizZaSQL[j] = listaZaSQL.get(j).toString().trim();
			else if(listaZaSQL.get(j) instanceof Boolean)
				nizZaSQL[j] = (Boolean)listaZaSQL.get(j);
			else if(listaZaSQL.get(j) instanceof Integer)
				nizZaSQL[j] = (Integer)listaZaSQL.get(j);
			else if(listaZaSQL.get(j) instanceof Double)
				nizZaSQL[j] = (Double)listaZaSQL.get(j);
			else if(listaZaSQL.get(j) instanceof Date)
				nizZaSQL[j] = (Date)listaZaSQL.get(j);
		}
			
		  dtm.updateRow(nizZaSQL, kljuc, index, updateSql);
		  dtm.fireTableDataChanged();
		  dtm.refresh();
	      tblGrid.setRowSelectionInterval(index, index);
	      mode = MODE_EDIT;
		  
	    } catch (SQLException ex) {
	      JOptionPane.showMessageDialog(this, ex.getMessage(), "Greska", 
	          JOptionPane.ERROR_MESSAGE);
	    }
}

public abstract boolean checkup();
public abstract boolean cekiranje();

public void removeRow(String deleteSql) {
	/*if(!checkup()){ //ako je povezan podatak
		/*String[] options = new String[2];
		options[0] = new String("Da");
		options[1] = new String("Ne");
		 int reply = JOptionPane.showOptionDialog(this, "Ukoliko obrišete ovaj entitet, obrisaćete sve koji ga referenciraju. Da li ste sigurni da želite da obrišete?", "Upozorenje", 0, JOptionPane.INFORMATION_MESSAGE, null, options, null);
	        if (reply != JOptionPane.YES_OPTION) {
	        	return;
	        }*/
	/*	JOptionPane.showMessageDialog(null, "Ovaj entitet je referenciran u nekom drugom entitetu. Obrišite prvo sve entitete koji ga referenciraju.", "Upozorenje", JOptionPane.INFORMATION_MESSAGE);
		return;
	}*/
    int index = tblGrid.getSelectedRow(); 
    if (index == -1) //Ako nema selektovanog reda (tabela prazna)
      return;        // izlazak 
    //kada obrisemo tekuci red, selektovacemo sledeci (newindex):
    int newIndex = index;  
    //sem ako se obrise poslednji red, tada selektujemo prethodni
    if (index == tblGrid.getRowCount() - 1) 
       newIndex--; 
    try {
      GenericTableModel gtm = (GenericTableModel)tblGrid.getModel(); 
      gtm.deleteRow(index, deleteSql); 
      gtm.refresh();
      
      if (tblGrid.getRowCount() > 0)
        tblGrid.setRowSelectionInterval(newIndex, newIndex);
    } catch (SQLException ex) {
      JOptionPane.showMessageDialog(this, ex.getMessage(), "Greska", 
          JOptionPane.ERROR_MESSAGE);
    }
	
  }





@SuppressWarnings("unchecked")
public void addRow(String insertSql) {       
	//treba dodati i za checkboxove
	int brojacTf = 0; //U NAZIVE TEXTFIELDA DODATI I TIPOVE, ZAOBICI LOOKUP POLJA (moze mozda ako je disabled??)
	ArrayList<Object> listaZaSQL = new ArrayList<Object>();
	
	for (int i=0; i<dataPanel.getComponentCount(); ++i)
	{
		if (dataPanel.getComponent(i) instanceof JTextField)
		{
			if(((JTextField)dataPanel.getComponent(i)).isEditable()){ //zaobilazak lookup polja 
				brojacTf++;
				String tfName = ((JTextField)dataPanel.getComponent(i)).getName();
				if(tfName.contains(",")){ //provera da li su tekstualna polja numericka
					String [] nameParts = tfName.split(",");
					if(nameParts[1].equals("int")){
						Integer numericVal = Integer.parseInt(((JTextField)dataPanel.getComponent(i)).getText().trim());
						listaZaSQL.add(numericVal);
					} else if (nameParts[1].equals("double")){
						Double numericVal = Double.parseDouble(((JTextField)dataPanel.getComponent(i)).getText().trim());
						listaZaSQL.add(numericVal);
					} else if (nameParts[1].equals("date")){
						String pattern = "yyyy-MM-dd";
					    SimpleDateFormat format = new SimpleDateFormat(pattern);
					    java.util.Date parsed = null;
					    try {
					      //(((JTextField)dataPanel.getComponent(i)).getText().trim());
					      parsed = format.parse(((JTextField)dataPanel.getComponent(i)).getText().trim());
					      java.sql.Date date = new java.sql.Date(parsed.getTime());
					      listaZaSQL.add(date);
					    } catch (ParseException e) {
					      e.printStackTrace();
					    }
						
					}
					else if (nameParts[1].equals("racun") || nameParts[1].equals("broj"))
					{
						listaZaSQL.add(((JTextField)dataPanel.getComponent(i)).getText().toString());
					}
					else if (nameParts[1].equals("bool"))
					{
						listaZaSQL.add(((JCheckBox)dataPanel.getComponent(i)).isSelected());
					}
				} else {
					listaZaSQL.add(((JTextField)dataPanel.getComponent(i)).getText().toString());
				}
    			
			}
			
		} else if (dataPanel.getComponent(i) instanceof JCheckBox)
		{
			brojacTf++;
			listaZaSQL.add(((JCheckBox)dataPanel.getComponent(i)).isSelected());
		} else if (dataPanel.getComponent(i) instanceof JComboBox<?>){
			brojacTf++;
			listaZaSQL.add(((JComboBox<String>)dataPanel.getComponent(i)).getSelectedItem().toString());
		}
	}
	
	Object [] nizZaSQL = new Object [brojacTf];
	
	for (int j=0; j<brojacTf; ++j)
	{
		if(listaZaSQL.get(j) instanceof String)
			nizZaSQL[j] = listaZaSQL.get(j).toString().trim();
		else if(listaZaSQL.get(j) instanceof Boolean)
			nizZaSQL[j] = (Boolean)listaZaSQL.get(j);
		else if(listaZaSQL.get(j) instanceof Integer)
			nizZaSQL[j] = (Integer)listaZaSQL.get(j);
		else if(listaZaSQL.get(j) instanceof Double)
			nizZaSQL[j] = (Double)listaZaSQL.get(j);
		else if(listaZaSQL.get(j) instanceof Date)
			nizZaSQL[j] = (Date)listaZaSQL.get(j);
	}

	
	   try {
	       //DrzaveTableModel dtm = (DrzaveTableModel)tblGrid.getModel();
		   GenericTableModel gtm = (GenericTableModel)tblGrid.getModel();
	       //((GenericTableModel) tblGrid.getModel()).insertRow(nizStringova);
		   
	       int index = gtm.insertRow(nizZaSQL, insertSql);
	       gtm.refresh();
	       tblGrid.setRowSelectionInterval(index, index);
	       //setMode(MODE_EDIT);
	      } catch (SQLException ex) {
	          JOptionPane.showMessageDialog(this, ex.getMessage(),"Greska", JOptionPane.ERROR_MESSAGE);
	   }
	 }






}
