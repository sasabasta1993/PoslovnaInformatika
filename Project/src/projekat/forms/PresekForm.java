package projekat.forms;

import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.math.BigDecimal;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextField;

import app.MainFrame;
import database.DBConnection;
import net.miginfocom.swing.MigLayout;


public class PresekForm extends JDialog {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	public PresekForm() {
		setSize(new Dimension(300, 100));

		setLocationRelativeTo(MainFrame.getInstance());
		setModal(true);

		JLabel lIdKlijenta = new JLabel();
		lIdKlijenta.setText("ID klijenta:");

		JLabel lDatum = new JLabel();
		lDatum.setText("Datum:");

		final JTextField tfIdKlijenta = new JTextField(15);
		tfIdKlijenta.setPreferredSize(new Dimension(50, 20));

		final JTextField tfDatum = new JTextField(15);

		JButton OKbtn = new JButton("OK");

		JPanel p = new JPanel(new MigLayout());

		p.add(lIdKlijenta);
		p.add(tfIdKlijenta);
		p.add(lDatum);
		p.add(tfDatum);
		p.add(OKbtn);

		add(p);

		OKbtn.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				String idKlijenta = tfIdKlijenta.getText();
				String datum = tfDatum.getText();

				if (idKlijenta.equals("") || idKlijenta == null) {
					JOptionPane.showMessageDialog(PresekForm.this,
							"Upisite ID klijenta", "Upozorenje",
							JOptionPane.WARNING_MESSAGE);
					return;
				}

				if (datum.equals("") || datum == null) {
					JOptionPane.showMessageDialog(PresekForm.this,
							"Upisite datum", "Upozorenje",
							JOptionPane.WARNING_MESSAGE);
					return;
				}

				String ra_br_racun = null;
				BigDecimal kl_idklijent = new BigDecimal(idKlijenta);
				java.sql.Date pr_datum = null;
				BigDecimal dsr_izvod = null;

				SimpleDateFormat pattern = new SimpleDateFormat("yyyy-MM-dd");

				Date datumNaloga = null;

				try {
					datumNaloga = (Date) pattern.parse(datum);

				} catch (ParseException e1) {
					JOptionPane.showMessageDialog(PresekForm.this,
							"Datum upisan u nepravilnom obliku", "Upozorenje",
							JOptionPane.WARNING_MESSAGE);
					return; // /?
				}

				pr_datum = new java.sql.Date(datumNaloga.getTime()); //postavlja datum na danasnji dan

				try {
					PreparedStatement stBrojRacuna = DBConnection
							.getConnection()
							.prepareStatement(
									"SELECT id_racuna FROM racuni_pravnih_lica WHERE id_klijenta like ?");

					stBrojRacuna.setBigDecimal(1, kl_idklijent);

					ResultSet rsBrojRacuna = stBrojRacuna.executeQuery();

					if (!rsBrojRacuna.isBeforeFirst()) {
						JOptionPane.showMessageDialog(PresekForm.this,
								"Ne postoji klijent sa tim Id-em.",
								"Upozorenje", JOptionPane.WARNING_MESSAGE);
						return; // /?
					} else {

						while (rsBrojRacuna.next()) {
							ra_br_racun = rsBrojRacuna.getString("id_racuna");
						}

					}

					stBrojRacuna.close();
					//select dnevno stanje racuna za dati broj racuna i datum
					PreparedStatement stDSR = DBConnection
							.getConnection()
							.prepareStatement(
									"SELECT dsr_izvod, dsr_prethodno, dsr_ukorist,"
											+ " dsr_nateret, dsr_novostanje FROM dnevno_stanje_racuna WHERE id_racuna like ? and dsr_datum like ?");

					stDSR.setString(1, ra_br_racun);
					stDSR.setDate(2, pr_datum);

					ResultSet rsDSR = stDSR.executeQuery();

					while (rsDSR.next()) {
						dsr_izvod = rsDSR.getBigDecimal("dsr_izvod");

					}

					stDSR.close();

					///select analitika izvoda, za dnevno stanje racuna i broj racuna
					PreparedStatement stBrojStavke = DBConnection
							.getConnection()
							.prepareStatement(
									"SELECT asi_brojstavke, asi_racpov, asi_racduz, asi_iznos FROM analitika_izvoda WHERE dsr_izvod like ? and id_racuna like ?");
					stBrojStavke.setBigDecimal(1, dsr_izvod);
					stBrojStavke.setString(2, ra_br_racun);

					ResultSet rsBrojStavke = stBrojStavke.executeQuery();

					String insertAnalitika = "INSERT INTO analitika_preseka (id_klijenta, pre_id_racuna, bnp_datum,"
							+ " bnp_presek, apr_rbr, id_racuna, dsr_izvod, asi_brojstavke) VALUES (? ,?, ?, ?, ?, ?, ?, ?)";

					PreparedStatement stAnalitikaPresjeka = DBConnection
							.getConnection().prepareStatement(insertAnalitika);

					int i = 1;
					int j = 1;
					int br_presjeka = 0;
					int uKorist = 0;
					int naTeret = 0;
					BigDecimal ukupnoUKorist = new BigDecimal(0);
					BigDecimal ukupnoNaTeret = new BigDecimal(0);
					int apr_rbr = 0;

					while (rsBrojStavke.next()) {
						
						apr_rbr = i%3;
						if(apr_rbr==0 ){
							apr_rbr = 3;
						}
						
						stAnalitikaPresjeka.setBigDecimal(1, kl_idklijent);
						stAnalitikaPresjeka.setString(2, ra_br_racun);
						stAnalitikaPresjeka.setDate(3, pr_datum);
						stAnalitikaPresjeka.setBigDecimal(4, new BigDecimal(j));
						br_presjeka = j;
						stAnalitikaPresjeka.setBigDecimal(5, new BigDecimal(apr_rbr));
						stAnalitikaPresjeka.setString(6, ra_br_racun);
						stAnalitikaPresjeka.setBigDecimal(7, dsr_izvod);
						BigDecimal ai_brojstavke = rsBrojStavke
								.getBigDecimal("ai_brojstavke");
						stAnalitikaPresjeka.setBigDecimal(8, ai_brojstavke);

						String ai_racduz = rsBrojStavke.getString("ai_racduz");
						BigDecimal ai_iznos = rsBrojStavke
								.getBigDecimal("ai_iznos");
						if (ai_racduz.equals(ra_br_racun)) {
							naTeret++;
							ukupnoNaTeret = ukupnoNaTeret.add(ai_iznos);
						} else {
							uKorist++;
							ukupnoUKorist = ukupnoUKorist.add(ai_iznos);
						}

						if (i % 3 == 0) {

							String insertPresjek = "INSERT INTO prenos_izvoda___presek (id_klijenta, id_racuna, bnp_datum, bnp_presek,"
									+ " dne_id_racuna, dsr_izvod, bnp_brukorist, bnp_u_korist, bnp_brnateret, bnp_ukteret, bnp_brpogk, "
									+ "bnp_brpogt, bnp_status) VALUES (? ,?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)";

							PreparedStatement stPresjek = DBConnection
									.getConnection().prepareStatement(
											insertPresjek);
							stPresjek.setBigDecimal(1, kl_idklijent);
							stPresjek.setString(2, ra_br_racun);
							stPresjek.setDate(3, pr_datum);
							stPresjek.setBigDecimal(4, new BigDecimal(
									br_presjeka));
							stPresjek.setString(5, ra_br_racun);
							stPresjek.setBigDecimal(6, dsr_izvod);
							stPresjek.setBigDecimal(7, new BigDecimal(uKorist));
							stPresjek.setBigDecimal(8, ukupnoUKorist);
							stPresjek.setBigDecimal(9, new BigDecimal(naTeret));
							stPresjek.setBigDecimal(10, ukupnoNaTeret);
							stPresjek.setBigDecimal(11, new BigDecimal(0));
							stPresjek.setBigDecimal(12, new BigDecimal(0));
							stPresjek.setString(13, "F");

							stPresjek.execute();
							DBConnection.getConnection().commit();

							j++;

							uKorist = 0;
							naTeret = 0;
							ukupnoUKorist = new BigDecimal(0);
							ukupnoNaTeret = new BigDecimal(0);
						}

						i++;

						stAnalitikaPresjeka.execute();
						DBConnection.getConnection().commit();

					}

					stBrojStavke.close();
					stAnalitikaPresjeka.close();

					// JOptionPane.showMessageDialog(PresjekForm.this,"Izvod je formiran","",
					// JOptionPane.INFORMATION_MESSAGE);

				} catch (SQLException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}

			}
		});

		PresekForm.this.setVisible(false);

	}

}
