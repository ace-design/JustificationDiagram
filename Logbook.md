# Scientific logbook
Corinne Pulgar

### 2020-03-22 - Premières décisions de design

La restriction est une propriété optionnelle de la conclusion. Incidemment, la conclusion est le seul `type` du diagramme pouvant avoir une propriété autre que le `label`. Il serait possible de créer un `type` 'restriction' ayant son propre `label` et lier cet élément à la conclusion. Toutefois on s'éloignerait alors de la spécification qui sous-entend qu'il n'existe qu'une restriction par diagramme (à vérifier). Pour l'instant, j'ai choisis de lier la restriction à la déclaration uniquement. **Il faudra éventuellement ajouter une vérification au compilateur pour que seule la conclusion ait accès à la restriction.**

À qui revient la responsabilité de créer la structure de classes dans Java? Est-ce 1) au compilateur ANTLR, 2) à la classe parsing.JDCompiler dans Java (ou une sous-classe quelconque) ou encore 3) à une *factory* agissant comme intermédiaire? Dans tous les cas, si on choisi d'ajouter un `type` au diagramme, il faudra modifier toutes les étapes du programme. Entre la *factory* et une sous-classe du parsing.JDCompiler, la *factory* semble présenter une solution au couplage plus faible. La responsabilité de la création peut revenir à l'entité ayant les données nécessaires, soit le compilateur. Mais lui donner cette responsabilité me semble le surcharger et violer le principe de responsabilité unique. Allons-y pour la *factory* pour l'instant. 

