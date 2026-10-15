package fr.ekod.assurance;

/**
 * Le véhicule à assurer.
 *
 * @param puissanceCh  puissance en chevaux
 * @param electrique   vrai si le véhicule est électrique
 * @param garageFerme  vrai si le véhicule dort dans un garage fermé
 */
public record Vehicule(int puissanceCh, boolean electrique, boolean garageFerme) {
}
