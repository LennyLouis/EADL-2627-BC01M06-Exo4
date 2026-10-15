package fr.ekod.assurance;

/**
 * Le conducteur à assurer.
 *
 * @param age                        âge en années
 * @param anneesPermis               années écoulées depuis l'obtention du permis
 * @param sinistresResponsables3Ans  nombre de sinistres responsables sur les trois dernières années
 * @param coefficientBonusMalus      coefficient de bonus-malus, de 0,50 (meilleur bonus) à 3,50 (malus maximal)
 */
public record Conducteur(int age, int anneesPermis, int sinistresResponsables3Ans, double coefficientBonusMalus) {
}
