# Dopredny_produkcny_system

Vstupné údaje teda fakty sa načítavajú zo súboru facts.txt a pravidlá sa načítavajú zo súboru rules.txt.

Formátovanie pravidiel:

Na prvom riadku je názov pravidla. Na druhom sa nachádzajú podmienky za slovom AK. 
Podmienka je ohraničená jednoduchými zátvorkami a každá podpodmienka v nej je tiež uzavretá v zátvorkách. 
Na treťom riadku sú potom akcie, kde zátvorkovanie je rovnaké ako pri podmienke.
Premenné sa začínajú znakom otáznik „?“. Špeciálna podmienka nerovná sa má tvar 
„<> prvá_premenná_ktorá_sa_nemá_rovnať druhá_premenná_ktorá_sa_nemá_rovnať“

Formátovanie faktov:
Na každom riadku je v zátvorkách uvedený jeden fakt.

Po spustení programu sa pravidlá aj fakty načítajú do polí. Následne sa začnú kontrolovať postupne všetky pravidlá. 
Ak nejaké pravidlo obsahuje premennú nájde sa k nemu prislúchajúci fakt, vytvorí sa nový objekt kde sa táto premenná 
nahradí príslušným údajom a tento proces sa zopakuje prehľadávaním do hĺbky na ňom.

Ak už pravidlo neobsahuje žiadnu premennú skontroluje sa či obsahuje špeciálny znak „<>“. Ak áno skontroluje sa či sa 
v pravidle premenné ktoré majú byť odlišné nerovnajú. Ak sa teda nerovnajú tak sa skontroluje či všetky pravidlá
zodpovedajú príslušným faktom a ak áno prejde sa na vyhodnotenie akcie. To prebieha tak, že ak je kľúčové 
slovo „pridaj“ tak sa prejdú všetky fakty a ak sa fakt ktorý sa má pridať v dostupných faktoch nenachádza 
tak sa pridá. Ak je kľúčové slovo „sprava“ tak sa vypíše požadovaný text. Ak je kľúčové slovo „vymaz“ tak sa prejde zoznam 
faktov a ak sa nejaký zhoduje s tým čo sa má vymazať tak sa odstráni.

Tento princíp ide stále dookola kým sa pridávajú nové fakty. Ak sa žiadny už nepridá program nemá čo ďalšie prehľadávať a ukončí sa.
