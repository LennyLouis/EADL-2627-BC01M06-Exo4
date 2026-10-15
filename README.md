# Exo.4 – Refactoring d'un code legacy sous tests

## Contexte

Un assureur auto calcule la prime annuelle d'un contrat avec `CalculPrime.calculerPrime(Conducteur, Vehicule)`.
La méthode fait plus de soixante-dix lignes, enchaîne une douzaine de `if` et traîne des nombres partout.
Elle est juste, 12 tests verts le prouvent, mais personne n'ose y toucher. Le mois prochain, la direction veut ajouter
une réduction « jeune conducteur accompagné ». Avant d'y toucher, il faut rendre ce code lisible, sans rien casser.

## Les règles métier (telles que la direction les énonce)

- La prime de base est de 500,00 €.
- Un conducteur de moins de 18 ans est refusé (`IllegalArgumentException("conducteur mineur")`).
- Un nombre d'années de permis supérieur à l'âge moins 18 ans est incohérent (`IllegalArgumentException("permis incohérent")`).
- Majoration d'âge : + 40 % en dessous de 25 ans, + 20 % au-dessus de 70 ans, rien entre les deux.
- Majoration de permis : + 25 % quand le permis a moins de 3 ans.
- Majoration de sinistres : + 15 % par sinistre responsable sur les 3 dernières années, plafonnée à 3 sinistres (donc 45 % au maximum).
- Majoration de véhicule : + 30 % au-dessus de 150 chevaux.
- Réductions : − 10 % pour un véhicule électrique, − 5 % pour un garage fermé. Elles se cumulent et s'appliquent sur le montant après majorations.
- Les pourcentages s'additionnent par famille, puis s'appliquent dans cet ordre :
  `base × (1 + majorations âge + permis + sinistres + véhicule) × (1 − réductions) × coefficient bonus-malus`.
  Le coefficient bonus-malus va de 0,50 à 3,50.
- Le résultat est en euros, arrondi au centime (arrondi commercial), et retourné en `double`.

Trois exemples de référence :

| Situation | Calcul | Prime |
|---|---|---|
| 30 ans, 10 ans de permis, 0 sinistre, 110 ch, thermique, sans garage, coefficient 1,00 | 500 | 500,00 € |
| 22 ans, 2 ans de permis, 0 sinistre, 100 ch, coefficient 1,00 | 500 × (1 + 0,40 + 0,25) | 825,00 € |
| 40 ans, 15 ans de permis, 0 sinistre, 120 ch, électrique, garage fermé, coefficient 0,80 | 500 × 1,00 × (1 − 0,15) × 0,80 | 340,00 € |

## Lancer les tests et mesurer la couverture

Prérequis : JDK 17+ et Maven 3.9+.

```bash
mvn verify
```

Le rapport JaCoCo se trouve ensuite dans `target/site/jacoco/index.html`. Ouvrez-le dans un navigateur et descendez
jusqu'à la classe `CalculPrime`, puis jusqu'à la méthode `calculerPrime` : les lignes vertes sont couvertes,
les jaunes ont une branche non exercée, les rouges ne sont jamais exécutées.

## La consigne (50 minutes, en binômes)

1. Lancez `mvn verify`. Les 12 tests doivent être verts. Notez la couverture de lignes et de branches de `CalculPrime`.
2. Lisez `CalculPrime` et listez les mauvaises odeurs (smells) que vous y trouvez. Pour chacune, notez le refactoring qui la soigne.
3. Refactorez par petits pas : **un seul refactoring**, puis `mvn test`, puis **un commit** dont le message commence par `REFACTOR`.
   Si les tests passent au rouge, annulez le pas (`git restore .`) au lieu de corriger à la volée.
4. Interdits : modifier un test existant, changer un résultat, corriger une règle « en passant », réécrire la méthode à côté.
5. Relancez `mvn verify` et comparez la couverture avec celle de départ. Que remarquez-vous sur les branches ?
6. Remplissez le tableau ci-dessous.

## Tableau avant / après

| Mesure | Avant | Après |
|---|---|---|
| Couverture de lignes (`CalculPrime`) | | |
| Couverture de branches (`CalculPrime`) | | |
| Longueur de la plus longue méthode (en lignes) | | |
| Nombre de constantes nommées | | |
