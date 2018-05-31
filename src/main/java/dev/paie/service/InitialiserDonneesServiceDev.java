package dev.paie.service;

import java.time.LocalDate;
import java.time.YearMonth;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Map;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import dev.paie.entite.Cotisation;
import dev.paie.entite.Entreprise;
import dev.paie.entite.Grade;
import dev.paie.entite.Periode;
import dev.paie.entite.ProfilRemuneration;
import dev.paie.entite.Utilisateur;
import dev.paie.entite.Utilisateur.ROLES;

@Service
public class InitialiserDonneesServiceDev implements InitialiserDonneesService {

	@PersistenceContext
	@Autowired
	private EntityManager em;

	@Autowired
	private PasswordEncoder passwordEncoder;

	@Override
	@Transactional
	public void initialiser() {
		ClassPathXmlApplicationContext context = new ClassPathXmlApplicationContext("cotisations-imposables.xml",
				"cotisations-non-imposables.xml", "entreprises.xml", "grades.xml", "profils-remuneration.xml");
		// recupère les cotisations dans les fichiers XML
		Map<String, Cotisation> ctos = context.getBeansOfType(Cotisation.class);
		Collection<Cotisation> cotis = ctos.values();
		// Récupère les entreprises dans les fichier XML
		Map<String, Entreprise> entos = context.getBeansOfType(Entreprise.class);
		Collection<Entreprise> entis = entos.values();
		// Récupère les grades dans les fichier XML
		Map<String, Grade> grados = context.getBeansOfType(Grade.class);
		Collection<Grade> gradis = grados.values();
		// récupère les ProfilRémuneration (équivalent des employer), dans le
		// fichier XML
		Map<String, ProfilRemuneration> proRemLis = context.getBeansOfType(ProfilRemuneration.class);
		Collection<ProfilRemuneration> proRemColl = proRemLis.values();

		// Envoi tous en BDD
		for (Cotisation cotisation : cotis) {
			em.persist(cotisation);
		}
		for (Entreprise entreprise : entis) {
			em.persist(entreprise);
		}
		for (Grade grade : gradis) {
			em.persist(grade);
		}
		for (ProfilRemuneration prof : proRemColl) {
			em.persist(prof);
		}
		// création de douze périodes qui représente les douzes mois de l'année
		List<Periode> annee = new ArrayList<>();
		int anneeCourante = LocalDate.now().getYear();
		for (int i = 1; i <= 12; i++) {
			Periode pp = new Periode();
			pp.setDateDebut(LocalDate.of(anneeCourante, i, 1));
			pp.setDateFin(YearMonth.of(anneeCourante, i).atEndOfMonth());
			annee.add(pp);
		}

		for (Periode p : annee) {
			em.persist(p);
		}

		// création d'utilisateur avec diffrent profil, 1 Admin 2 User

		List<Utilisateur> listMembre = new ArrayList<>();
		Utilisateur admin = new Utilisateur();
		admin.setEstActif(true);
		admin.setNomUtilisateur("holt zark");
		admin.setMotDePasse(passwordEncoder.encode("zark"));
		admin.setRole(ROLES.ROLE_ADMINISTRATEUR);

		listMembre.add(admin);

		Utilisateur user1 = new Utilisateur();
		user1.setEstActif(true);
		user1.setMotDePasse(passwordEncoder.encode("user"));
		user1.setNomUtilisateur("User");
		user1.setRole(ROLES.ROLE_UTILISATEUR);
		Utilisateur user2 = new Utilisateur();
		user2.setEstActif(false);
		user2.setMotDePasse(passwordEncoder.encode("hallfinder"));
		user2.setNomUtilisateur("sword");
		user2.setRole(ROLES.ROLE_UTILISATEUR);
		listMembre.add(user1);
		listMembre.add(user2);

		for (Utilisateur u : listMembre) {
			em.persist(u);
		}

		context.close();

	}

}
