

import java.util.ArrayList;
import java.util.Collections;

public class Parser {
		
	public void createEntity (String[] array, LSQLDB db) {
		Entity entity = new Entity(array[1], Integer.parseInt(array[2]), Integer.parseInt(array[3]));
		for(int i = 4; i < 4 + (2 * entity.noAttributes); i=i+2) {
			Attribute atribut = null;
			if(array[i+1].equals("String")) {
				atribut = new Attribute("String", array[i]);
			}else if(array[i+1].equals("Integer")) {
				atribut = new Attribute("Integer",array[i]);
			} else if(array[i+1].equals("Float")) {
				atribut = new Attribute("Float", array[i]);
			}
			entity.atributes.add(atribut);
		}
		db.entitys.add(entity);
	}

	public void insertInstance (String[] array, LSQLDB db) {		
		int counterRF = 0;
		ArrayList<Integer> noduriOcupate = new ArrayList<Integer>();
		Entity newEntity = null;		
		//		caut in arraylist ul de entitys tipul entitatii mele si clonez informatia in newEntity
		//		informatia inseamna numele entitatii, rf, nr de atribute si tipul acestora, nu si valori
		for(Entity entity : db.entitys) {
			if(entity.type.equals(array[1])) {
				newEntity = new Entity(entity, false);
				newEntity.timestamp = System.nanoTime();
				break;
			}
		}
		setAttributes(array, newEntity);
		//		pun informatia din entitatea copy in noduri
		while(counterRF < newEntity.replicationFactor){
			int position = nodeNumber(db, newEntity, noduriOcupate);
			noduriOcupate.add(position);
			Entity backUp = new Entity(newEntity, true);
			db.nodes.get(position).entities.add(0,backUp);
			Collections.sort(db.nodes.get(position).entities);
			counterRF++;
		}
	}

	public int nodeNumber (LSQLDB db, Entity entity, ArrayList<Integer> noduriOcupate) {
		int max = -1;
		int position = 0;
		boolean found = false;
		for(int i = 0; i < db.noNodes; i++) {
			if(db.nodes.get(i).entities.size() < db.nodes.get(i).capacity && 
					db.nodes.get(i).entities.size() > max && noduriOcupate.contains(i) == false) {
				position = i;
				max = db.nodes.get(i).entities.size();
				found = true;
			}
		}
		if (found == true)
			return position;
		else return fullDB(db);
	}

	public void setAttributes (String[] array, Entity entity) {
		for(int i = 0; i < entity.atributes.size(); i++) {
			entity.atributes.get(i).setupValue(array[i+2]);
		}
	}

	public void deleteInstance(String[] array, LSQLDB db) {
		boolean found = false;
		for(Node node: db.nodes) {
			for(int i = 0; i < node.entities.size(); i++) {
				if(node.entities.get(i).type.equals(array[1])) {
					if (verifyFirstAttribute(array, node.entities.get(i)) == 1) {
						node.entities.remove(node.entities.get(i));
						found = true;
					}
				}
			}
		}
		if(found == false) System.out.println("NO INSTANCE TO DELETE");
	}

	public int verifyFirstAttribute (String[] array, Entity entity) {
		if (entity.atributes.get(0).getValue(entity.atributes.get(0).type).equals(array[2]))
			return 1;
		else 
			return 0;
	}

	public void updateInstance(String[] array, LSQLDB db) {
		for(Node node: db.nodes) {
			for( int i=0; i<node.entities.size(); i++
					) {
				if(node.entities.get(i).type.equals(array[1])) {
					if (verifyFirstAttribute(array, node.entities.get(i)) == 1) {	
						node.entities.get(i).timestamp = System.nanoTime();
						updateAttribute(node.entities.get(i), array);
					}
				}
			}
			Collections.sort(node.entities);
		}
	}

	public void updateAttribute (Entity entity, String[] array){
		for(Attribute attribute: entity.atributes) {
			if(attribute.name.equals(array[3])) {
				attribute.setupValue(array[4]);
			}
		}
	}

	public void getInstance(String[] array, LSQLDB db) {
		int nrNod = 0;
		boolean found = false; 
		Entity copy = null;
		for(Node node: db.nodes) {
			nrNod++;
			for(Entity entity : node.entities) {
				if(entity.type.equals(array[1])) {
					if (verifyFirstAttribute(array, entity) == 1) {
						copy = entity;
						found = true;
						System.out.printf("Nod%d ", nrNod);					
					}
				}
			}
		}
		if(found == false)
			System.out.println("NO INSTANCE FOUND");
		else {
			System.out.printf("%s",copy.type);
			for(Attribute attribute : copy.atributes) {
				System.out.printf( " %s:%s", attribute.name, attribute.getValue(attribute.type));
			}
			System.out.printf("\n");
		}
	}

	public void printDB(LSQLDB db) {
		int nrNod = 1;
		boolean empty = true;
		for(Node node: db.nodes) {
			if(node.entities.size()!=0) {
				System.out.println("Nod" + nrNod);
				empty = false;
			}
			for(Entity entity : node.entities) {
				System.out.printf("%s",entity.type);
				for(Attribute attribute : entity.atributes) {
					System.out.printf( " %s:%s", attribute.name, attribute.getValue(attribute.type));
				}
				System.out.printf("\n");
			}
			nrNod++;
		}
		if(empty == true) System.out.println("EMPTY DB");
	}
	public void cleanup(String[] array, LSQLDB db, long startTime) {
		System.out.println(startTime);
		for(Node node: db.nodes) {
			for(int i = 0; i < node.entities.size(); i++) {
				if(node.entities.get(i).timestamp - startTime < Long.parseLong(array[2])) {
					node.entities.remove(node.entities.get(i));
					i--;
				}
			}
		}
	}
	
	public int fullDB(LSQLDB db) {
		Node newNode = new Node(db.maxCapacity);
		db.nodes.add(newNode);
		db.noNodes++;
		return (db.noNodes-1);
	}
}
