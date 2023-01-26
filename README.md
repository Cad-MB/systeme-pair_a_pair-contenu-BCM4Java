Outils servant à la réalisation du projet

D’abord, l’archive contenant le code et la documentation du modèle à composants BCM4Java (https://drive.google.com/file/d/1C73QiCpD-A9OZSE_2OeXyMxPoxVrZi3J/view), version du 19 septembre 2022. Cette archive peut vous servir de projet à installer sous Eclipse. Il est conseillé d’utiliser cette archive de la manière suivante :

- Créez sous Eclipse un projet Java 1.8 appelé BCM4Java avec des répertoires séparés pour les sources et les binaires.
- Récupérez l’archive et déballez-la dans un répertoire.
- Copiez tous les répertoires inclus dans l’archive sauf src dans le répertoire BCM4Java.
- Copiez le sous-répertoire fr du répertoire src de l’archive dans le répertoire src du projet BCM4Java.
- Modifiez le statut des répertoires examples, src-basic-plugins et examples-basic-plugins pour qu’ils soient reconnus comme répertoires de sources dans le projet BCM4Java.
- Ajoutez les jars des outils mentionnés ci-après comme bibliothèques accèdes par le projet.

Une fois le projet installé et compilé, vous pourrez l’utiliser pour en explorer le contenu, voir la documentation et récupérer les scripts et autres fichiers utiles dans vos applications. Dans cette archive, vous allez trouver :

- dans le répertoire src, les paquetages fr.sorbonne_u.components.* des sources du modèle à composants (https://drive.google.com/file/d/1C73QiCpD-A9OZSE_2OeXyMxPoxVrZi3J/view) ;
- dans le répertoire examples, les paquetages fr.sorbonne_u.components.examples.* les sources d’exemples d’utilisation du modèle ;
- dans le répertoire src-basic-plugins, les paquetages fr.sorbonne_u.components.plugins.* des sources de greffons de base pour le modèle à composants (https://drive.google.com/file/d/1C73QiCpD-A9OZSE_2OeXyMxPoxVrZi3J/view) ;
- dans le répertoire examples-basic-plugins, les paquetages fr.sorbonne_u.components.plugins.*.examples.* les sources d’exemples d’utilisation des greffons de base du modèle ;
- dans le répertoire bash-scripts, des scripts Unix pour l’exécution en multi-JVM ;
- dans le répertoire config, la grammaire Relax NG pour la validation du fichier de configuration XML des déploiements en multi-JVM ;
- dans le répertoire policies, un fichier de politique de sécurité Java pour l’exécution en multi-JVM ;
- dans le répertoire doc la documentation de BCM et des exemples, à consulter à la fois pour l’utilisation du modèle et pour comprendre les exemples et les faire tourner ;
- et dans le répertoire images, les images utilisées dans la documentions du répertoire doc.

Ensuite, le jar contenant les sources et les binaires du modèle à composants (https://drive.google.com/file/d/1C73QiCpD-A9OZSE_2OeXyMxPoxVrZi3J/view), version du 19 septembre 2022. Ce jar devra être importé dans vos projets et utilisé lors de vos déploiements (exécutions multi-JVM). Ainsi, quand vous aurez créé le projet Eclipse qui va contenir les sources de votre travail de semestre, vous pourrez importer le jar de BCM4Java (avec les jars des outils mentionnés ci-après) pour en utiliser le contenu. Pour faciliter la mise au point de vos programmes, il sera parfois utile de passer de l’importation du jar BCM4Java au projet BCM4Java comme il sera expliqué en TD/TME.

Le modèle utilise quelques bibliothèques que vous pourrez également récupérer :

- le validateur des schémas Relax NG jing (https://drive.google.com/file/d/1J5-WJf-FX-bYS0BETunAM67hfulKRjTS/view) et
- le jar de javassist (https://drive.google.com/file/d/1gxABpJRxhe-HG2kNMUqahWuN2HPQjb-x/view). Vous pourrez trouver de la documentation et un tutoriel de Javassist sur le site du projet à l’URL https://www.javassist.org/.
Les exemples utilisent également la bibliothèque commons-math v3.6.1 (https://drive.google.com/file/d/1gc_5ACQ3pnq36kYLo0pkOfO8_Cpz2-oB/view) que vous pourrez aussi récupérer.
