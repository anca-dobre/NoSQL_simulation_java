	Clasa LSQLDB - baza de date
		proprietati: nume, numar noduri si capacitatea maxima
		arraylist de noduri
		arraylist de entitati, unde vom avea "sabloanele" entitatilor necesare la inserarea unei noi entitati
		constructorul

	Clasa Node - nodul in care avem pastrate instantele
		capacitate
		arraylist de entitati
		constructorul

	Clasa Entity - entitatea
		proprietati: tipul, RF, numarul de atribute, timestamp
		arraylist de atribute
		constructorul
		copy constructor - argumentul boolean hasValue are scopul de a copia sau nu valoarea atributelor din entitate
				   In cazul in care copiem o entitate de tip sablon di arraylistul de entitati din baza de date
		nu va trebui sa copiem si valoarea atributelor, din moment ce nu exista
		suprascrierea metodei compareTo, folosita pentru a sorta entitatile in noduri in functie de timestamp

	Clasa Attribute 
		proprietati: tipul(int/float/string), nume
		valoare de tipul Value
		constructorul
		copy constructorul
		setupValue - schimba valoarea unui atribut
		getValue - returneaza valoarea atributului convertita in string pt afisare
			 - in cazul in care avem un float de tipul 8.0 vom afisa 8

	Clasa Value
		3 proprietati intValue, floatValue sistringValue initilaizate cu null
			- pt fiecare atribut vom avea un singur camp din cele 3 != null
		constructorul care in functie de tipul atributului seteaza unul din cele 3 campuri
		copy constructorul

	Clasa Main
		pastram in startTime timestamp ul asociat momentului inceperii rularii programului
		deschidem fisierul
		citim linie cu linie
		cream baza de date
		cat timp avem comenzi necitite in fisier apelam functia readInstruction care identifica instructiunea
	si apeleaza metoda din Clasa Parser

	Clasa Parser - contine metodele de prelucrare a bazei de date
		createEntity - creeaza o noua entitate
			-apelam constructorul, apoi adaugam atributele (prin constructor), formand arraylist ul de atribute
		insertInstance - adauga o noua instanta
			-in counterRF avem salvat nr inserarilor pt instanta ce trebuie adaugata
			-arraylist noduriOcupate - vom pastra pozitia nodurilor in care am adaugat deja entitatea
						 - il vom folosi in metoda in care cautam pozitia nodului in care trebuie sa inseram 
			-parcurgem lista de entitati din baza de date unde cautam 'sablonul'(tipul) entitatii pe care urmeaza sa o inseram
			-cream o noua entitate, setam timestamp ul si valoarea atributelor(metoda setAttributes)
			-calculam pozitia nodului unde trebuie sa inseram(metoda nodeNumber)
			-cream o copie a entitatii si o adaugam in nodul dorit
			-repetam pana cand nr inserarilor = rf
		nodeNumber 
			-cautam nodul cel mai ocupat din baza de date care nu contine deja entitatea si care nu este plin
 		deleteInstance
			-parcurgem nodurile din baza de date, verificam primul atribut si daca este acelasi vom sterge entitatea
			-daca nu exista vom afisa mesajul "no instance to delete"
		updateInstance
			-parcurgem nodurile din baza de date si cautam in arraylist ul de entitati entitatea ce contine primary key
			-modificam valoarea atributului(updateAttribute), actualizam timestamp ul si sortam entitatile
		getInstance
			-cautam instanta ce contine primary key dat ca parametru al comenzii in toate nodurile din baza de date
			-afisam nodurile in care se afla
		printDB
			-parcurgem toate nodurile din baza de date si afisam toate entitatile in ordine
			-daca baza de date este goala afisam mesaj
		cleanup
			-parcurgem toate nodurile din baza de data si vom sterge entitatile care au fost introduse/modificate
		cu mai mult de valoarea in nanosecunde primita ca parametru
		fullDB
			-in cazul in care nu avem loc pt a adauga o instanta vom crea un nou nod
		
		